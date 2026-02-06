package dev.sakura.client.mixin.entity;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.movement.KeepSprint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Redirect(method = "knockbackTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void knockbackTarget$hookSetVelocity(Entity instance, Vec3d vec3) {
        if (!Sakura.MODULES.getModule(KeepSprint.class).isEnabled()) {
            instance.setVelocity(vec3);
        }
    }

    @Redirect(method = "knockbackTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"))
    private void knockbackTarget$hookSetSprinting(PlayerEntity instance, boolean sprinting) {
        if (!Sakura.MODULES.getModule(KeepSprint.class).isEnabled()) {
            instance.setSprinting(sprinting);
        }
    }
}
