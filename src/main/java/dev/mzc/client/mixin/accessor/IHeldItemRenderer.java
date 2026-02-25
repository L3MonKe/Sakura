package dev.mzc.client.mixin.accessor;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HeldItemRenderer.class)
public interface IHeldItemRenderer {
    @Accessor("equipProgressMainHand")
    void setEquippedProgressMainHand(float progress);

    @Accessor("equipProgressMainHand")
    float getEquippedProgressMainHand();

    @Accessor("equipProgressOffHand")
    void setEquippedProgressOffHand(float progress);

    @Accessor("equipProgressOffHand")
    float getEquippedProgressOffHand();

    @Accessor("mainHand")
    void setItemStackMainHand(ItemStack stack);

    @Accessor("offHand")
    void setItemStackOffHand(ItemStack stack);
}
