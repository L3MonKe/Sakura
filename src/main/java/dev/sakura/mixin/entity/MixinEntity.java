package dev.sakura.mixin.entity;

import dev.sakura.Sakura;
import dev.sakura.events.player.StrafeEvent;
import dev.sakura.events.player.MoveEvent;
import dev.sakura.events.player.RayTraceEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.spongepowered.asm.mixin.injection.Redirect;

import static dev.sakura.Sakura.mc;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public abstract Vec3d getRotationVector(float pitch, float yaw);

    @Redirect(method = "getRotationVec", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVector(FF)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d redirectGetRotationVector(Entity instance, float pitch, float yaw) {
        if ((Object) instance == mc.player) {
            RayTraceEvent event = new RayTraceEvent(instance, yaw, pitch);
            Sakura.EVENT_BUS.post(event);
            return this.getRotationVector(event.getPitch(), event.getYaw());
        }
        return this.getRotationVector(pitch, yaw);
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
    private float redirectGetYawInUpdateVelocity(Entity instance) {
        if ((Object) instance == mc.player) {
            StrafeEvent event = new StrafeEvent(instance.getYaw());
            Sakura.EVENT_BUS.post(event);
            return event.getYaw();
        }
        return instance.getYaw();
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void onMove(MovementType type, Vec3d movement, CallbackInfo ci) {
        if ((Object) this == mc.player && type == MovementType.SELF) {
            MoveEvent event = new MoveEvent(movement);
            Sakura.EVENT_BUS.post(event);

            if (event.isCancelled()) {
                ci.cancel();
                return;
            }

            if (event.getX() != movement.x || event.getY() != movement.y || event.getZ() != movement.z) {
                ((Entity) (Object) this).move(type, event.getVec());
                ci.cancel();
            }
        }
    }
}