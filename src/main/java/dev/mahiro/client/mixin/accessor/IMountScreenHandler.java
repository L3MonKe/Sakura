package dev.mahiro.client.mixin.accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.screen.MountScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MountScreenHandler.class)
public interface IMountScreenHandler {
    @Accessor("mount")
    LivingEntity getMount();
}
