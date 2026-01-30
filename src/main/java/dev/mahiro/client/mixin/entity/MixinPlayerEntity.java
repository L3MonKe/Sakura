package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.movement.KeepSprint;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void hookSetVelocity(PlayerEntity instance, Vec3d vec3) {
        if (!Mahiro.MODULES.getModule(KeepSprint.class).isEnabled()) {
            instance.setVelocity(vec3);
        }
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"))
    private void hookSetSprinting(PlayerEntity instance, boolean sprinting) {
        if (!Mahiro.MODULES.getModule(KeepSprint.class).isEnabled()) {
            instance.setSprinting(sprinting);
        }
    }
}
