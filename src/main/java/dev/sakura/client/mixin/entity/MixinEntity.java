package dev.sakura.client.mixin.entity;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.entity.EntityPushEvent;
import dev.sakura.client.events.player.MoveEvent;
import dev.sakura.client.events.player.RayTraceEvent;
import dev.sakura.client.events.player.StrafeEvent;
import dev.sakura.client.events.player.UpdateVelocityEvent;
import dev.sakura.client.module.impl.render.Glow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.sakura.client.Sakura.mc;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public abstract Vec3d getRotationVector(float pitch, float yaw);

    @Shadow
    protected static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        double d = movementInput.lengthSquared();
        if (d < 1.0E-7) {
            return Vec3d.ZERO;
        } else {
            Vec3d vec3d = (d > 1.0 ? movementInput.normalize() : movementInput).multiply((double) speed);
            float f = MathHelper.sin(yaw * 0.017453292F);
            float g = MathHelper.cos(yaw * 0.017453292F);
            return new Vec3d(vec3d.x * (double) g - vec3d.z * (double) f, vec3d.y, vec3d.z * (double) g + vec3d.x * (double) f);
        }
    }

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void onIsGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (Glow.INSTANCE != null && Glow.INSTANCE.shouldGlow((Entity) (Object) this) && Glow.INSTANCE.useNativeGlow()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getTeamColorValue", at = @At("HEAD"), cancellable = true)
    private void onGetTeamColorValue(CallbackInfoReturnable<Integer> cir) {
        if (Glow.INSTANCE != null && Glow.INSTANCE.shouldGlow((Entity) (Object) this) && Glow.INSTANCE.useNativeGlow()) {
            cir.setReturnValue(Glow.INSTANCE.getGlowColor((Entity) (Object) this));
        }
    }

    @Redirect(method = "getRotationVec", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVector(FF)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d redirectGetRotationVector(Entity instance, float pitch, float yaw) {
        if ((Object) instance == mc.player) {
            RayTraceEvent event = new RayTraceEvent(instance, yaw, pitch);
            Sakura.EVENT_BUS.post(event);
            return this.getRotationVector(event.getPitch(), event.getYaw());
        }
        return this.getRotationVector(pitch, yaw);
    }

    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    public void updateVelocityHook(float speed, Vec3d movementInput, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            UpdateVelocityEvent event = new UpdateVelocityEvent(movementInput, speed, mc.player.getYaw(), movementInputToVelocity(movementInput, speed, mc.player.getYaw()));
            Sakura.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
                mc.player.setVelocity(mc.player.getVelocity().add(event.getVelocity()));
            }
        }
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

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void onPushAwayFrom(Entity entity, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            EntityPushEvent event = new EntityPushEvent((Entity) (Object) this, entity);
            Sakura.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
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