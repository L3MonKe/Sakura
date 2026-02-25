package dev.mzc.client.render.smoothswap;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

import static dev.mzc.client.render.smoothswap.SmoothSwapManager.ASSUME_CURSOR_STACK_SLOT_INDEX;
import static dev.mzc.client.render.smoothswap.SmoothSwapManager.currentStacks;
import static java.lang.Math.PI;

public class SwapUtil {

    public static boolean hasArrived(InventorySwap swap) {
        int quadrant = getQuadrant(swap.getAngle());
        double x = swap.getX();
        double y = swap.getY();
        if (quadrant == 0 && x > 0 && y > 0) {
            return true;
        } else if (quadrant == 1 && x < 0 && y > 0) {
            return true;
        } else if (quadrant == 2 && x < 0 && y < 0) {
            return true;
        } else return quadrant == 3 && x > 0 && y < 0;
    }

    public static int getSlotIndex(ItemStack stack) {
        if (currentStacks == null) return -1;
        for (int i = 0; i < currentStacks.size(); i++) {
            ItemStack s = currentStacks.get(i);
            if (s.hashCode() == stack.hashCode())
                return i;
        }
        return -1;
    }

    public static void setRenderToTrue(List<InventorySwap> swapList) {
        for (InventorySwap swap : swapList) {
            swap.setRenderDestinationSlot(true);
        }
    }

    private static int getQuadrant(double angle) {
        return (int) (Math.floor(2 * angle / PI) % 4 + 4) % 4;
    }

    public static double map(double in, double inMin, double inMax, double outMax, double outMin) {
        return (in - inMin) / (inMax - inMin) * (outMax - outMin) + outMin;
    }

    public static void addI2IInventorySwap(int index, Slot fromSlot, Slot toSlot, boolean checked, int amount) {
        List<InventorySwap> swaps = SmoothSwapManager.swaps.getOrDefault(index, new ArrayList<>());

        if (ItemStack.areItemsEqual(toSlot.getStack(), Items.AIR.getDefaultStack()))
            return;

        ItemStack swapStack = toSlot.getStack().copy();
        if ((Object) swapStack instanceof ISmoothSwapItemStack) {
            ((ISmoothSwapItemStack) (Object) swapStack).smooth_Swapping$setIsSwapStack(true);
        }

        swaps.add(new ItemToItemInventorySwap(fromSlot, toSlot, checked, amount, swapStack));
        SmoothSwapManager.swaps.put(index, swaps);
    }

    public static void assignI2CSwaps(List<SwapStacks> lessStacks, Vec2 mousePos, ScreenHandler handler) {
        ItemStack cursorStack = handler.getCursorStack();

        for (SwapStacks lessStack : lessStacks) {
            Slot lessSlot = handler.getSlot(lessStack.getSlotID());
            List<InventorySwap> swaps = SmoothSwapManager.swaps.getOrDefault(ASSUME_CURSOR_STACK_SLOT_INDEX, new ArrayList<>());

            if (ItemStack.areItemsEqual(cursorStack, Items.AIR.getDefaultStack()))
                return;

            ItemStack swapStack = lessStack.getOldStack().copy();
            if ((Object) swapStack instanceof ISmoothSwapItemStack) {
                ((ISmoothSwapItemStack) (Object) swapStack).smooth_Swapping$setIsSwapStack(true);
            }

            swaps.add(new ItemToCursorInventorySwap(lessSlot, mousePos, lessStack.getOldStack(), false, lessStack.itemCountToChange));
            SmoothSwapManager.swaps.put(ASSUME_CURSOR_STACK_SLOT_INDEX, swaps);
        }
    }

    public static void assignI2ISwaps(List<SwapStacks> moreStacks, List<SwapStacks> lessStacks, ScreenHandler handler){
        for (int i = 0; i < moreStacks.size(); i++) {
            SwapStacks moreStack = moreStacks.get(i);
            if (moreStack.itemCountToChange == 0){
                moreStacks.remove(moreStack);
            }
            Slot moreSlot = handler.getSlot(moreStack.getSlotID());

            int c = 0;
            while (moreStack.itemCountToChange < 0 && c < 64) {
                c++;
                for (int j = 0; j < lessStacks.size(); j++) {
                    SwapStacks lessStack = lessStacks.get(j);
                    Slot lessSlot = handler.getSlot(lessStack.getSlotID());

                    if (ItemStack.areItemsEqual(moreStack.getNewStack(), lessStack.getOldStack())
                            && lessStack.itemCountToChange > 0) {
                        int amount = Math.min(Math.abs(moreStack.itemCountToChange), lessStack.itemCountToChange);
                        moreStack.itemCountToChange += amount;
                        lessStack.itemCountToChange -= amount;

                        addI2IInventorySwap(moreStack.getSlotID(), lessSlot, moreSlot, false, amount);
                    }
                }
            }
        }
    }
    
    public static void copyStacks(DefaultedList<ItemStack> from, DefaultedList<ItemStack> to) {
        to.clear();
        for (ItemStack stack : from) {
            to.add(stack.copy());
        }
    }
    
    public static void reset() {
        SmoothSwapManager.swaps.clear();
        if (SmoothSwapManager.currentStacks != null && SmoothSwapManager.oldStacks != null) {
            copyStacks(SmoothSwapManager.currentStacks, SmoothSwapManager.oldStacks);
        }
    }

    public static int getCount(ItemStack stack) {
        return stack.isEmpty() ? 0 : stack.getCount();
    }
}
