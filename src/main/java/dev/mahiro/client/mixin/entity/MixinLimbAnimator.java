package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.entity.LimbAnimationEvent;
import net.minecraft.entity.LimbAnimator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LimbAnimator.class)
public final class MixinLimbAnimator {
    @Inject(method = "getSpeed()F", at = @At("HEAD"), cancellable = true)
    private void hookGetSpeed(CallbackInfoReturnable<Float> cir) {
        LimbAnimationEvent limbAnimationEvent = new LimbAnimationEvent();
        Mahiro.EVENT_BUS.post(limbAnimationEvent);
        if (limbAnimationEvent.isCancelled()) {
            cir.cancel();
            cir.setReturnValue(limbAnimationEvent.getSpeed());
        }
    }

    @Inject(method = "getSpeed(F)F", at = @At("HEAD"), cancellable = true)
    private void hookGetSpeed(float tickDelta, CallbackInfoReturnable<Float> cir) {
        LimbAnimationEvent limbAnimationEvent = new LimbAnimationEvent();
        Mahiro.EVENT_BUS.post(limbAnimationEvent);
        if (limbAnimationEvent.isCancelled()) {
            cir.cancel();
            cir.setReturnValue(limbAnimationEvent.getSpeed());
        }
    }
}
