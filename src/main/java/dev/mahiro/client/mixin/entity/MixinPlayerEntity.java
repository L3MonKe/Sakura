package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.movement.KeepSprint;
import dev.mahiro.client.module.impl.render.OldHitting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void onAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cir) {
        OldHitting oldHitting = Mahiro.MODULES.getModule(OldHitting.class);
        if (oldHitting.isEnabled()) {
            cir.setReturnValue(1.0f);
        }
    }

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    private void onAttackCooldownProgressPerTick(CallbackInfoReturnable<Float> cir) {
        OldHitting oldHitting = Mahiro.MODULES.getModule(OldHitting.class);
        if (oldHitting.isEnabled()) {
            cir.setReturnValue(1.0f);
        }
    }

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
