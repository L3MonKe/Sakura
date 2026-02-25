package dev.mzc.client.render.smoothswap;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ItemToItemInventorySwap extends InventorySwap {
    public ItemToItemInventorySwap(Slot fromSlot, Slot toSlot, boolean checked, int amount, ItemStack swapStack) {
        super(new Vec2(fromSlot.x, fromSlot.y), new Vec2(toSlot.x, toSlot.y), swapStack, checked, amount);
    }
}
