package dev.sakura.mixin.entity;

import dev.sakura.Sakura;
import dev.sakura.events.entity.SwingSpeedEvent;
import dev.sakura.events.entity.UpdateServerPositionEvent;
import dev.sakura.events.player.JumpEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.spongepowered.asm.mixin.injection.Redirect;

import static dev.sakura.Sakura.mc;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    private float redirectGetYawInJump(LivingEntity instance) {
        if ((Object) instance == mc.player) {
            JumpEvent event = new JumpEvent(instance.getYaw());
            Sakura.EVENT_BUS.post(event);
            return event.getYaw();
        }
        return instance.getYaw();
    }

    @Inject(method = "getHandSwingDuration", at = @At("HEAD"), cancellable = true)
    private void hookGetHandSwingDuration(CallbackInfoReturnable<Integer> cir) {
        SwingSpeedEvent swingSpeedEvent = new SwingSpeedEvent();
        Sakura.EVENT_BUS.post(swingSpeedEvent);
        if (swingSpeedEvent.isCancelled()) {
            if (swingSpeedEvent.getSelfOnly() && ((Object) this != mc.player)) {
                return;
            }
            cir.cancel();
            cir.setReturnValue(swingSpeedEvent.getSwingSpeed());
        }
    }

    @Inject(method = "updateTrackedPositionAndAngles", at = @At(value = "HEAD"))
    private void hookUpdateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, CallbackInfo ci) {
        UpdateServerPositionEvent updateServerPositionEvent = new UpdateServerPositionEvent((LivingEntity) (Object) this, x, y, z, yaw, pitch);
        Sakura.EVENT_BUS.post(updateServerPositionEvent);
    }
}
