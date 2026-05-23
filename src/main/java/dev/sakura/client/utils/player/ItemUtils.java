package dev.sakura.client.utils.player;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemUtils {

    public static boolean isFood(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getComponents().get(DataComponentTypes.FOOD) != null;
    }

    public static boolean isConsumable(ItemStack stack) {
        return isFood(stack) || stack.isOf(Items.POTION) || stack.isOf(Items.MILK_BUCKET);
    }
}
