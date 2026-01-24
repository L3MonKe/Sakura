package dev.mahiro.client.utils.player;

import dev.mahiro.client.Mahiro;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import static dev.mahiro.client.Mahiro.mc;

public class EatingUtil {
    public static boolean isEating() {
        if (mc.player == null) return false;
        
        // 检查主手或副手是否正在使用物品（吃东西/拉弓/喝药水等）
        if (mc.player.isUsingItem()) {
            return mc.player.getActiveItem().contains(DataComponentTypes.FOOD) || 
                   mc.player.getActiveItem().getItem() == Items.GOLDEN_APPLE ||
                   mc.player.getActiveItem().getItem() == Items.ENCHANTED_GOLDEN_APPLE ||
                   mc.player.getActiveItem().getItem() == Items.POTION ||
                   mc.player.getActiveItem().getItem() == Items.MILK_BUCKET ||
                   mc.player.getActiveItem().getItem() == Items.CHORUS_FRUIT;
        }
        
        return false;
    }
}
