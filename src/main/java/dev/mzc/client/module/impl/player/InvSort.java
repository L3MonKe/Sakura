package dev.mzc.client.module.impl.player;

import dev.mzc.client.utils.player.MovementUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.EnumValue;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import net.minecraft.screen.slot.SlotActionType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InvSort extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    // 模式枚举
    public enum Mode {
        InvOpen("背包打开"),
        NoMove("非移动时"),
        Always("保持开启"), // 始终执行
        InvClose("背包关闭");
        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }
    // 设置当前模式
    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "选择整理模式", Mode.Always);
    // 物品整理延迟
    private final NumberValue<Double> sortDelay = new NumberValue<>("SortDelay", "整理延迟", 1.0, 1.0, 10.0, 1.0);
    private final BoolValue dropMaster = new BoolValue("DropMaster", "丢弃开关", true);
    private final BoolValue dropHotbar = new BoolValue("DropHotbar", "丢弃物品栏", true, dropMaster::get);
    private final BoolValue throwStick = new BoolValue("Stick", "木棍", true, dropMaster::get);
    private final BoolValue throwRottenFlesh = new BoolValue("RottenFlesh", "腐肉", true, dropMaster::get);
    private final BoolValue throwSpiderEye = new BoolValue("SpiderEye", "蜘蛛眼", true, dropMaster::get);
    private final BoolValue throwGlassBottle = new BoolValue("GlassBottle", "玻璃瓶", true, dropMaster::get);
    private final BoolValue throwVines = new BoolValue("DEAD_BUSH", "灌木", true, dropMaster::get);
    private final BoolValue throwString = new BoolValue("String", "线", true, dropMaster::get);

    private int delay = 0; // 当前倒计时

    private long nextDelayMs = 0L; // 用于控制丢弃物品的延迟
    private final TimerUtil timer = new TimerUtil();

    public InvSort() {
        super("InvSort", "背包整理", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled() || mc.player == null || mc.world == null) return;

            if (readyToAct()) {
                // 判断是否执行丢弃或整理操作
                if (shouldRun()) {
                    tryDropUnnecessary();  // 丢弃物品
                    tickSort();  // 整理物品
                }
            }
        });
    }

    @Override
    public void onEnable() {
        delay = 0;
    }

    @Override
    public void onDisable() {
        delay = 0;
    }

    // 判断是否执行丢弃和整理操作
    private boolean shouldRun() {
        // 判断当前模式是否符合执行条件
        return (mode.is(Mode.InvOpen.name()) && mc.currentScreen instanceof InventoryScreen)  // 只有在背包界面打开时
                || (mode.is(Mode.NoMove.name()) && !MovementUtil.isMoving())  // 只有在玩家不移动时
                || mode.is(Mode.Always.name())  // 始终执行
                || (mode.is(Mode.InvClose.name()) && mc.currentScreen == null);  // 只有在背包界面关闭时
    }

    // 尝试丢弃背包和物品栏中的多余物品
    private void tryDropUnnecessary() {
        if (mc.player == null) return;

        List<Integer> slotIdsToThrow = new ArrayList<>();

        // 遍历物品栏（快捷栏：0到8）
        if (dropHotbar.get()) {
            for (int hotbarSlot = 0; hotbarSlot <= 8; hotbarSlot++) {
                ItemStack stack = mc.player.getInventory().getStack(hotbarSlot);
                if (!stack.isEmpty() && shouldDropUnnecessary(stack)) {
                    slotIdsToThrow.add(36 + hotbarSlot);  // 丢弃物品栏物品（screenId 映射）
                }
            }
        }

        // 遍历背包（9到35）
        for (int i = 9; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(Items.BUNDLE)) continue; // 不动储物袋格子
            if (shouldDropUnnecessary(stack)) {
                slotIdsToThrow.add(i);  // 背包 screenId 与索引一致
            }
        }

        // 如果需要丢弃的物品槽位不为空，则一次性丢弃所有物品
        if (!slotIdsToThrow.isEmpty()) {
            throwAllItems(slotIdsToThrow);
        }
    }

    // 判断物品是否是多余的，符合条件的物品会被丢弃
    private boolean shouldDropUnnecessary(ItemStack stack) {
        if (!dropMaster.get()) return false;
        return (ItemStack.areItemsEqual(stack, new ItemStack(Items.STICK)) && throwStick.get()) ||
                (ItemStack.areItemsEqual(stack, new ItemStack(Items.ROTTEN_FLESH)) && throwRottenFlesh.get()) ||
                (ItemStack.areItemsEqual(stack, new ItemStack(Items.SPIDER_EYE)) && throwSpiderEye.get()) ||
                (ItemStack.areItemsEqual(stack, new ItemStack(Items.GLASS_BOTTLE)) && throwGlassBottle.get()) ||
                (ItemStack.areItemsEqual(stack, new ItemStack(Items.DEAD_BUSH)) && throwVines.get()) ||
                (ItemStack.areItemsEqual(stack, new ItemStack(Items.STRING)) && throwString.get());  // 检查是否是需要丢弃的物品
    }

    // 一次性丢弃所有符合条件的物品
    private void throwAllItems(List<Integer> slotIdsToThrow) {
        int syncId = mc.player.currentScreenHandler.syncId;
        for (Integer screenId : slotIdsToThrow) {
            if (screenId == null) continue;
            click(screenId, syncId);  // 丢弃物品
        }
    }

    // 执行丢弃物品的操作
    private void click(int screenId, int syncId) {
        if (mc.interactionManager == null || mc.player == null) return;

        // 丢弃物品
        mc.interactionManager.clickSlot(syncId, screenId, 1, SlotActionType.THROW, mc.player);
    }

    // 控制延迟，防止操作过于频繁
    private boolean readyToAct() {
        if (nextDelayMs <= 0L) {
            nextDelayMs = randomDelayMs();
        }
        return timer.passedMS(nextDelayMs);
    }

    // 随机生成延迟时间
    private long randomDelayMs() {
        int min = 100; // 最小延迟时间
        int max = 200; // 最大延迟时间
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    private void tickSort() {

        // 根据选择的模式来判断是否执行
        boolean shouldRun = switch (mode.get()) {
            case InvOpen -> mc.currentScreen instanceof InventoryScreen;
            case NoMove -> !MovementUtil.isMoving();
            case Always -> true;
            case InvClose -> mc.currentScreen == null;
        };

        if (!shouldRun) return;

        if (delay > 0) {
            delay--;
            return;
        }

        PlayerInventory inv = mc.player.getInventory();

        // ================== 0. 同类物品自动合并（一次一对） ==================
        if (mergeOnce(inv)) {
            delay = (int) Math.round(sortDelay.get()); // 使用 Math.round() 来转换 Double -> int
            return;
        }

        // ================== 1. 统计同类物品总数 ==================
        Map<ItemKey, Integer> totalCount = new HashMap<>();

        for (int i = 9; i < inv.main.size(); i++) {  // 更新为 `inv.main.size()`
            ItemStack stack = inv.getStack(i);
            if (stack.isEmpty()) continue;
            if (!stack.isStackable()) continue;

            ItemKey key = new ItemKey(stack);
            totalCount.put(key, totalCount.getOrDefault(key, stack.getCount()) + stack.getCount());
        }

        // ================== 2. 收集槽位并分组（跳过储物袋） ==================
        List<Integer> stackableSlots = new ArrayList<>();
        LinkedHashMap<ItemKey, List<Integer>> nonStackGroups = new LinkedHashMap<>();
        Set<Integer> protectedIndices = new HashSet<>();

        for (int i = 9; i < inv.main.size(); i++) {
            ItemStack s = inv.getStack(i);
            if (s.isEmpty()) continue;
            if (s.isOf(Items.BUNDLE)) {
                protectedIndices.add(i);
                continue;
            }
            if (s.isStackable()) {
                stackableSlots.add(i);
            } else {
                ItemKey key = new ItemKey(s);
                nonStackGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(i);
            }
        }

        // ================== 3. 排序（可堆叠优先，按总数降序） ==================
        stackableSlots.sort((a, b) -> {
            ItemStack sa = inv.getStack(a);
            ItemStack sb = inv.getStack(b);
            int ca = totalCount.getOrDefault(new ItemKey(sa), sa.getCount());
            int cb = totalCount.getOrDefault(new ItemKey(sb), sb.getCount());
            return Integer.compare(cb, ca);
        });

        // 目标顺序：先所有可堆叠，再将不可堆叠的同类物品分组相邻（不覆盖储物袋）
        List<Integer> orderedSlots = new ArrayList<>(stackableSlots.size() + nonStackGroups.size() * 2);
        orderedSlots.addAll(stackableSlots);
        for (List<Integer> group : nonStackGroups.values()) {
            orderedSlots.addAll(group);
        }
        List<Integer> targetIndices = new ArrayList<>();
        for (int i = 9; i < inv.main.size(); i++) {
            if (!protectedIndices.contains(i)) {
                targetIndices.add(i);
            }
        }

        // ================== 4. 像人一样交换 ==================
        for (int t = 0; t < orderedSlots.size() && t < targetIndices.size(); t++) {
            int from = orderedSlots.get(t);
            int to = targetIndices.get(t);

            if (from == to) continue;

            clickSwap(from, to);
            delay = (int) Math.round(sortDelay.get()); // 使用 Math.round() 来转换 Double -> int
            return;
        }
    }

    /* ================= 同类物品合并（一次一对） ================= */
    private boolean mergeOnce(PlayerInventory inv) {
        int syncId = mc.player.currentScreenHandler.syncId;

        for (int i = 9; i < inv.main.size(); i++) {
            ItemStack a = inv.getStack(i);
            if (a.isEmpty() || !a.isStackable() || a.getCount() >= a.getMaxCount()) continue;
            if (a.isOf(Items.BUNDLE)) continue;

            for (int j = i + 1; j < inv.main.size(); j++) {
                ItemStack b = inv.getStack(j);
                if (b.isEmpty() || !ItemStack.areItemsAndComponentsEqual(a, b)) continue;
                if (b.isOf(Items.BUNDLE)) continue;

                mc.interactionManager.clickSlot(syncId, j, 0, SlotActionType.PICKUP, mc.player);
                mc.interactionManager.clickSlot(syncId, i, 0, SlotActionType.PICKUP, mc.player);
                mc.interactionManager.clickSlot(syncId, j, 0, SlotActionType.PICKUP, mc.player);

                return true; // 一次只合并一对
            }
        }
        return false;
    }

    /* ================= 底层点击（防幽灵） ================= */
    private void clickSwap(int a, int b) {
        int syncId = mc.player.currentScreenHandler.syncId;
        mc.interactionManager.clickSlot(syncId, a, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, b, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, a, 0, SlotActionType.PICKUP, mc.player);
    }

    /* ================= 同类判断 Key ================= */
    private static class ItemKey {
        private final ItemStack stack;
        ItemKey(ItemStack stack) { this.stack = stack; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ItemKey k)) return false;
            return ItemStack.areItemsAndComponentsEqual(stack, k.stack);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stack.getItem(), stack.getComponents());
        }
    }

    private class TimerUtil {
        private long lastMs;

        public TimerUtil() {
            this.lastMs = -1L;
        }

        public boolean passedMS(long ms) {
            return System.currentTimeMillis() - lastMs >= ms;
        }

        public void reset() {
            lastMs = System.currentTimeMillis();
        }
    }
}
