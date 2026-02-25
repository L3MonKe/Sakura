package dev.mzc.client.mixin.entity;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.entity.EntityPushEvent;
import dev.mzc.client.events.player.MoveEvent;
import dev.mzc.client.events.player.RayTraceEvent;
import dev.mzc.client.events.player.StrafeEvent;
import dev.mzc.client.events.player.UpdateVelocityEvent;
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
import dev.mzc.client.module.impl.movement.NoSlow;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static dev.mzc.client.Sakura.mc;

import dev.mzc.client.events.player.SlowdownEvent;
import dev.mzc.client.module.impl.render.Freelook;
import net.minecraft.block.BlockState;

import net.minecraft.block.Blocks;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void onChangeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        if ((Object) this == mc.player && Sakura.MODULES.getModule(Freelook.class).isEnabled()) {
            Sakura.MODULES.getModule(Freelook.class).onMouseUpdate(cursorDeltaX, cursorDeltaY);
            ci.cancel();
        }
    }

    @Shadow
    public abstract Vec3d getRotationVector(float pitch, float yaw);

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

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

    @Redirect(method = "getRotationVec", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVector(FF)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d redirectGetRotationVector(Entity instance, float pitch, float yaw) {
        if (instance == mc.player) {
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

    @Inject(method = "slowMovement", at = @At("HEAD"), cancellable = true)
    private void onSlowMovement(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            SlowdownEvent.Type type = SlowdownEvent.Type.Web;
            if (state.getBlock() == Blocks.SOUL_SAND) {
                type = SlowdownEvent.Type.SoulSand;
            } else if (state.getBlock() == Blocks.SWEET_BERRY_BUSH) {
                type = SlowdownEvent.Type.BerryBush;
            }
            
            SlowdownEvent event = new SlowdownEvent(type, false);
            Sakura.EVENT_BUS.post(event);
            if (!event.isSlowdown()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "getVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if ((Object) this == mc.player) {
            if (Sakura.MODULES.getModule(NoSlow.class).isEnabled()) {
                 BlockPos pos = new BlockPos((int) Math.floor(this.getX()), (int) Math.floor(this.getY() - 0.5000001D), (int) Math.floor(this.getZ()));
                 BlockState state = this.getWorld().getBlockState(pos);
                 if (state.getBlock() == Blocks.SOUL_SAND && Sakura.MODULES.getModule(NoSlow.class).isSoulSand()) {
                     cir.setReturnValue(1.0F);
                 }
            }
        }
    }
}
