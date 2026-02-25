package dev.mzc.client.render.smoothswap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class SmoothSwapManager {
    public static final int ASSUME_CURSOR_STACK_SLOT_INDEX = -2;
    public static boolean clickSwap;
    public static Integer clickSwapStack;
    public static Map<Integer, List<InventorySwap>> swaps = new HashMap<>();
    public static DefaultedList<ItemStack> oldStacks = DefaultedList.of();
    public static DefaultedList<ItemStack> currentStacks = DefaultedList.of();
    public static ItemStack oldCursorStack;
    public static AtomicReference<ItemStack> currentCursorStack = new AtomicReference<>(null);
    public static final ReentrantLock currentCursorStackLock = new ReentrantLock();

    public static void init() {
        swaps = new HashMap<>();
        oldStacks = DefaultedList.of();
        currentStacks = DefaultedList.of();
    }
}
