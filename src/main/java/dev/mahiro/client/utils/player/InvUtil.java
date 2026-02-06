package dev.mahiro.client.utils.player;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.function.Predicate;

import static dev.mahiro.client.Mahiro.mc;

public class InvUtil {
    public static int previousSlot = -1;
    public static int[] invSlots;

    public static boolean testInMainHand(Predicate<ItemStack> predicate) {
        return predicate.test(mc.player.getMainHandStack());
    }

    public static boolean testInMainHand(Item... items) {
        return testInMainHand(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static boolean testInOffHand(Predicate<ItemStack> predicate) {
        return predicate.test(mc.player.getOffHandStack());
    }

    public static boolean testInOffHand(Item... items) {
        return testInOffHand(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static boolean testInHands(Predicate<ItemStack> predicate) {
        return testInMainHand(predicate) || testInOffHand(predicate);
    }

    public static boolean testInHands(Item... items) {
        return testInMainHand(items) || testInOffHand(items);
    }

    public static boolean testInHotbar(Predicate<ItemStack> predicate) {
        if (testInHands(predicate)) return true;

        for (int i = SlotUtil.HOTBAR_START; i <= SlotUtil.HOTBAR_END; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (predicate.test(stack)) return true;
        }

        return false;
    }

    public static boolean testInHotbar(Item... items) {
        return testInHotbar(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static FindItemResult findEmpty() {
        return find(ItemStack::isEmpty);
    }

    public static FindItemResult findInHotbar(Item... items) {
        return findInHotbar(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static FindItemResult findInHotbar(Predicate<ItemStack> isGood) {
        if (testInOffHand(isGood)) {
            return new FindItemResult(SlotUtil.OFFHAND, mc.player.getOffHandStack().getCount(), mc.player.getOffHandStack().getMaxCount());
        }

        if (testInMainHand(isGood)) {
            return new FindItemResult(mc.player.getInventory().getSelectedSlot(), mc.player.getMainHandStack().getCount(), mc.player.getMainHandStack().getMaxCount());
        }

        return find(isGood, 0, 8);
    }

    public static FindItemResult find(Item... items) {
        return find(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static FindItemResult find(Predicate<ItemStack> isGood) {
        if (mc.player == null) return new FindItemResult(0, 0, 0);
        return find(isGood, 0, mc.player.getInventory().size());
    }

    public static FindItemResult find(Predicate<ItemStack> isGood, int start, int end) {
        if (mc.player == null) return new FindItemResult(0, 0, 0);

        int slot = -1, count = 0, maxCount = 0;

        for (int i = start; i <= end; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (isGood.test(stack)) {
                if (slot == -1) slot = i;
                count += stack.getCount();
                maxCount += stack.getMaxCount();
            }
        }

        return new FindItemResult(slot, count, maxCount);
    }

    public static FindItemResult findFastestTool(BlockState state, Boolean inv) {
        float bestScore = 1;
        int slot = -1;

        for (int i = 0; i < (inv ? mc.player.getInventory().size() : 9); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isSuitableFor(state)) continue;

            float score = stack.getMiningSpeedMultiplier(state);
            if (score > bestScore) {
                bestScore = score;
                slot = i;
            }
        }

        return new FindItemResult(slot, 1, 1);
    }

    public static boolean swap(int slot, boolean swapBack) {
        if (slot == SlotUtil.OFFHAND) return true;
        if (slot < 0 || slot > 8) return false;
        if (swapBack && previousSlot == -1) previousSlot = mc.player.getInventory().getSelectedSlot();
        else if (!swapBack) previousSlot = -1;

        mc.player.getInventory().setSelectedSlot(slot);
        //mc.interactionManager.syncSelectedSlot(); 或许这个不需要。。？
        return true;
    }

    public static boolean swapBack() {
        if (previousSlot == -1) return false;

        boolean return_ = swap(previousSlot, false);
        previousSlot = -1;
        return return_;
    }

    public static boolean invSwap(int slot) {
        if (slot >= 0) {
            int containerSlot = slot;
            if (slot < 9) containerSlot += 36;
            else if (slot == 40) containerSlot = 45;

            ScreenHandler handler = mc.player.currentScreenHandler;
            int selectedSlot = mc.player.getInventory().getSelectedSlot();

            mc.interactionManager.clickSlot(handler.syncId, containerSlot, selectedSlot, SlotActionType.SWAP, mc.player);

            invSlots = new int[]{containerSlot, selectedSlot};
            return true;
        }
        return false;
    }

    public static void invSwapBack() {
        if (invSlots == null || invSlots.length < 2) return;
        ScreenHandler handler = mc.player.currentScreenHandler;

        mc.interactionManager.clickSlot(handler.syncId, invSlots[0], invSlots[1], SlotActionType.SWAP, mc.player);
    }
}
