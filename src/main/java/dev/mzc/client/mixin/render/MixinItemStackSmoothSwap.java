package dev.mzc.client.mixin.render;

import dev.mzc.client.render.smoothswap.ISmoothSwapItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class MixinItemStackSmoothSwap implements ISmoothSwapItemStack {
    @Unique
    private boolean smooth_Swapping$isSwapStack = false;

    @Override
    public boolean smooth_Swapping$isSwapStack() {
        return smooth_Swapping$isSwapStack;
    }

    @Override
    public void smooth_Swapping$setIsSwapStack(boolean value) {
        this.smooth_Swapping$isSwapStack = value;
    }
}
