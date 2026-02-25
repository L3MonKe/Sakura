package dev.mzc.client.module.impl.player;

import dev.mzc.client.utils.player.SlotUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.math.MathUtil;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class AutoTotem extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private int swapCooldown = 0;
    private int clickDelay = 0;
    private int step = 0; // 0: Idle, 1: Picked Up, 2: Placing, 3: Refill Picked Up, 4: Refill Placing, 5: Hotbar Swapping
    private int targetSlotId = -1;

    private final NumberValue<Integer> minDelay = new NumberValue<>("MinDelay", "最小任务延迟", 0, 0, 20, 1);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("MaxDelay", "最大任务延迟", 2, 0, 20, 1);
    private final NumberValue<Integer> clickDelayValue = new NumberValue<>("ClickDelay", "点击间隔延迟", 1, 0, 10, 1);
    public final NumberValue<Double> healthThreshold = new NumberValue<>("HealthThreshold", "血量阈值", 20.0, 1.0, 30.0, 0.5);
    private final BoolValue checkContainer = new BoolValue("CheckContainer", "容器检查", true);
    private final BoolValue strictMode = new BoolValue("StrictMode", "严格模式", false);
    private final BoolValue antiCheat = new BoolValue("AntiCheat", "反作弊优化", true);
    private final BoolValue autoRefill = new BoolValue("HotbarTotemRefill", "自动补图腾", true);
    private final BoolValue hotbarSwap = new BoolValue("HotbarTotem", "快捷栏切图腾", true);

    private final boolean[] hotbarTotems = new boolean[9];
    private int refillSlot = -1; // 正在补位的目标槽位
    private int preSlot = -1; // 切手前的原槽位
    private int hotbarTotemSlot = -1; // 计划用于交换到副手的快捷栏图腾槽

    public AutoTotem() {
        super("AutoTotem", "自动图腾", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled() || mc.player == null) return;
            handleAutoTotem();
        });
    }

    @Override
    public void onEnable() {
        resetState();
    }

    @Override
    public void onDisable() {
        resetState();
    }

    private void resetState() {
        swapCooldown = 0;
        clickDelay = 0;
        step = 0;
        targetSlotId = -1;
        refillSlot = -1;
        preSlot = -1;
        for (int i = 0; i < 9; i++) hotbarTotems[i] = false;
    }

    private void handleAutoTotem() {
        if (mc.player == null) return;

        // 更新快捷栏图腾记忆
        if (autoRefill.get()) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                    hotbarTotems[i] = true;
                } else if (!stack.isEmpty() && mc.currentScreen instanceof InventoryScreen) {
                    // 如果玩家在背包界面手动往快捷栏放了非图腾物品，停止记录该位置为图腾位
                    hotbarTotems[i] = false;
                }
            }
        }

        if (swapCooldown > 0) {
            swapCooldown--;
            return;
        }

        if (clickDelay > 0) {
            clickDelay--;
            return;
        }

        // 状态机处理
        switch (step) {
            case 0: // Idle - 查找并开始
                startProcess();
                break;
            case 1: // Picked Up - 准备放入副手
                placeInOffhand();
                break;
            case 2: // Placing - 放入副手后，如果光标还有东西则放回
                cleanupCursor();
                break;
            case 3: // Refill Picked Up
                placeInHotbar();
                break;
            case 4: // Refill Placing
                cleanupRefill();
                break;
            case 5: // Hotbar Swapping
                finishHotbarSwap();
                break;
        }
    }

    private void startProcess() {
        // 容器检查
        if (checkContainer.get() && mc.currentScreen instanceof HandledScreen && !(mc.currentScreen instanceof InventoryScreen)) {
            return;
        }

        // 优先检查副手是否需要补图腾
        float currentHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        boolean offhandNeedsTotem = !mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING) && 
                                  (currentHealth <= healthThreshold.get() || true); // 始终尝试保持副手图腾

        if (offhandNeedsTotem) {
            // 1. 优先尝试快捷栏切手
            if (hotbarSwap.get()) {
                int hotbarTotem = -1;
                for (int i = 0; i < 9; i++) {
                    if (mc.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                        hotbarTotem = i;
                        break;
                    }
                }

                if (hotbarTotem != -1) {
                    preSlot = mc.player.getInventory().selectedSlot;
                    mc.player.getInventory().selectedSlot = hotbarTotem;
                    hotbarTotemSlot = hotbarTotem;
                    step = 5;
                    setClickDelay();
                    return;
                }
            }

            // 2. 快捷栏没图腾，走背包点击逻辑
            // 严格模式：必须打开背包
            if (strictMode.get() && !(mc.currentScreen instanceof InventoryScreen)) return;

            int totemSlot = findTotem(9); // 从第9格开始找，避免拿快捷栏的
            if (totemSlot == -1) totemSlot = findTotem(0); // 找不到就从头找
            
            if (totemSlot != -1) {
                int slotId = SlotUtil.indexToId(totemSlot);
                if (slotId != -1) {
                    targetSlotId = slotId;
                    click(targetSlotId);
                    step = 1;
                    setClickDelay();
                    return;
                }
            }
        }

        // 如果副手不需要，且打开了背包，检查快捷栏补位
        if (autoRefill.get() && mc.currentScreen instanceof InventoryScreen) {
            for (int i = 0; i < 9; i++) {
                if (hotbarTotems[i] && !mc.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                    int totemInMain = findTotem(9); // 必须从主背包找
                    if (totemInMain != -1) {
                        int sourceId = SlotUtil.indexToId(totemInMain);
                        refillSlot = SlotUtil.indexToId(i);
                        
                        targetSlotId = sourceId;
                        click(targetSlotId);
                        step = 3;
                        setClickDelay();
                        return;
                    }
                }
            }
        }
    }

    private void finishHotbarSwap() {
        if (mc.player == null || mc.player.networkHandler == null) {
            resetState();
            return;
        }

        // 如果副手已经是图腾则不再交换
        if (mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            step = 0;
            preSlot = -1;
            hotbarTotemSlot = -1;
            swapCooldown = MathUtil.getRandom(minDelay.get(), maxDelay.get());
            return;
        }

        // 防呆：确保主手选中的是图腾
        if (!mc.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            // 如果当前选中不是图腾，尝试切回记录的图腾槽
            if (hotbarTotemSlot >= 0 && hotbarTotemSlot < 9
                    && mc.player.getInventory().getStack(hotbarTotemSlot).isOf(Items.TOTEM_OF_UNDYING)) {
                mc.player.getInventory().selectedSlot = hotbarTotemSlot;
            }
        }

        // 再次确认主手是图腾，否则放弃本次交换，避免把奇怪物品放到副手
        if (!mc.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            step = 0;
            preSlot = -1;
            hotbarTotemSlot = -1;
            swapCooldown = MathUtil.getRandom(minDelay.get(), maxDelay.get());
            return;
        }

        // 发送换手包
        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
        
        // 切回原槽位
        if (preSlot != -1) {
            mc.player.getInventory().selectedSlot = preSlot;
        }

        step = 0;
        preSlot = -1;
        hotbarTotemSlot = -1;
        swapCooldown = MathUtil.getRandom(minDelay.get(), maxDelay.get());
    }

    private void placeInOffhand() {
        int offhandSlotId = 45;
        click(offhandSlotId);
        
        step = 2;
        setClickDelay();
    }

    private void placeInHotbar() {
        if (refillSlot != -1) {
            click(refillSlot);
        }
        step = 4;
        setClickDelay();
    }

    private void cleanupRefill() {
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            click(targetSlotId);
        }
        step = 0;
        refillSlot = -1;
        targetSlotId = -1;
        swapCooldown = MathUtil.getRandom(minDelay.get(), maxDelay.get());
    }

    private void cleanupCursor() {
        // 如果光标上还有东西（比如之前副手上的东西），放回到刚才拿图腾的位置
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            click(targetSlotId);
        }
        
        step = 0;
        targetSlotId = -1;
        swapCooldown = MathUtil.getRandom(minDelay.get(), maxDelay.get());
    }

    private void setClickDelay() {
        clickDelay = clickDelayValue.get();
        if (antiCheat.get()) clickDelay += MathUtil.getRandom(0, 1);
    }

    private void click(int id) {
        if (mc.interactionManager == null || mc.player == null) return;
        
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, id, 0, SlotActionType.PICKUP, mc.player);
    }

    private int findTotem(int startSlot) {
        PlayerInventory inv = mc.player.getInventory();
        for (int i = startSlot; i < 36; i++) {
            if (inv.getStack(i).isOf(Items.TOTEM_OF_UNDYING)) return i;
        }
        if (startSlot > 0) { // 如果从后面没找到，再从前面找
            for (int i = 0; i < startSlot; i++) {
                if (inv.getStack(i).isOf(Items.TOTEM_OF_UNDYING)) return i;
            }
        }
        return -1;
    }
}
