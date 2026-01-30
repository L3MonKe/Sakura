package dev.mahiro.client.interfaces;

import net.minecraft.item.ItemStack;

public interface IHeldItemRenderer {
    float getEquippedProgressMainHand();

    void setEquippedProgressMainHand(float mainHand);

    float getEquippedProgressOffHand();

    void setEquippedProgressOffHand(float offHand);

    void setItemStackMainHand(ItemStack stack);

    void setItemStackOffHand(ItemStack stack);
}
