package dev.mzc.client.module.impl.player;

import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

import java.util.Random;

public class MiddleClickExtra extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    private final NumberValue<Integer> swapDelay = new NumberValue<>("Swap Delay", "切换延迟", 0, 0, 10, 1);
    private final NumberValue<Integer> swapBackDelay = new NumberValue<>("Swap Back Delay", "切回延迟", 1, 0, 10, 1);

    private int currentSwapBackDelay = 0;
    private int currentSwapDelay = 0;
    private int originalSlot = -1;
    private boolean waitingSwapBack = false;
    private boolean waitingUse = false;

    public enum Mode {
        EnderPearl("末影珍珠"),
        FireworkRocket("烟花火箭"),
        WindCharge("风弹"),
        ExperienceBottle("经验瓶");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.EnderPearl);
    private final BoolValue onlyElytra = new BoolValue("OnlyElytra", "仅在飞行时触发", true, () -> mode.get() == Mode.FireworkRocket);

    public MiddleClickExtra() {
        super("MiddleClickExtra", "中键额外动作", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (!isEnabled() || mc.player == null || mc.interactionManager == null) return;
            onTick();
        });
    }

    @Override
    public void onEnable() {
        currentSwapBackDelay = 0;
        currentSwapDelay = 0;
        originalSlot = -1;
        waitingSwapBack = false;
        waitingUse = false;
    }

    @Override
    public void onDisable() {
        if ((waitingSwapBack || waitingUse) && mc.player != null && originalSlot != -1) {
            mc.player.getInventory().setSelectedSlot(originalSlot);
        }

        currentSwapBackDelay = 0;
        currentSwapDelay = 0;
        waitingSwapBack = false;
        waitingUse = false;
        originalSlot = -1;
    }

    private void onTick() {
        // ======== 处理使用延迟 ========
        if (waitingUse) {
            if (currentSwapDelay > 0) {
                currentSwapDelay--;
                return;
            }
            // 延迟结束，执行交互
            if (mc.interactionManager != null) {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            }
            waitingUse = false;
            
            // 设置切回延迟
            currentSwapBackDelay = swapBackDelay.get();
            waitingSwapBack = true;
            return;
        }

        // ======== 处理延迟切回 ========
        if (currentSwapBackDelay > 0) {
            currentSwapBackDelay--;
            if (currentSwapBackDelay == 0 && waitingSwapBack && originalSlot != -1) {
                mc.player.getInventory().setSelectedSlot(originalSlot);
                waitingSwapBack = false;
            }
            return;
        }

        // ======== 已经在等待切回，不再触发 ========
        if (waitingSwapBack) return;

        // ======== 中键触发一次 ========
        if (!mc.mouse.wasMiddleButtonClicked()) return;

        ItemStack main = mc.player.getMainHandStack();
        Item targetItem = null;

        // ======== 判断目标物品 ========
        switch (mode.get()) {
            case EnderPearl:
                targetItem = Items.ENDER_PEARL;
                break;
            case FireworkRocket:
                targetItem = Items.FIREWORK_ROCKET;
                break;
            case WindCharge:
                targetItem = Items.WIND_CHARGE;
                break;
            case ExperienceBottle:
                targetItem = Items.EXPERIENCE_BOTTLE;
                break;
        }

        if (targetItem == null) return;

        // ======== OnlyElytra 逻辑判断 ========
        if (mode.get() == Mode.FireworkRocket && onlyElytra.get()) {
            if (!mc.player.isGliding()) return; // 如果不是正在飞行状态，则不触发
        }

        // ======== 如果主手已经是目标物品，直接使用，不切换 ========
        if (main.getItem() == targetItem) {
            if (mc.interactionManager != null) {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            }
            return;
        }

        // ======== 准星检测 (如果是珍珠、火箭、风弹、经验瓶，通常需要指向方块或空气，这里统一处理) ========
        if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) return;

        // ======== 找目标物品所在的槽位 ========
        int targetSlot = findItemSlot(targetItem);
        if (targetSlot == -1) return;

        // ======== 执行切换并交互 ========
        originalSlot = mc.player.getInventory().selectedSlot;
        mc.player.getInventory().setSelectedSlot(targetSlot);
        handleInteraction();
    }

    private void handleInteraction() {
        if (swapDelay.get() == 0) {
            if (mc.interactionManager != null) {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            }
            currentSwapBackDelay = swapBackDelay.get();
            waitingSwapBack = true;
        } else {
            currentSwapDelay = swapDelay.get();
            waitingUse = true;
        }
    }

    private int findItemSlot(Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == item) return i;
        }
        return -1;
    }
}
