package dev.mzc.client.mixin.accessor;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface IClientPlayerInteractionManager {
    @Accessor("blockBreakingCooldown")
    void setBlockBreakingCooldown(int cooldown);

    @Accessor("blockBreakingCooldown")
    int getBlockBreakingCooldown();
}
