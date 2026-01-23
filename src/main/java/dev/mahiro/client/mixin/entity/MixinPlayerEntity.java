package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.OldHitting;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}
