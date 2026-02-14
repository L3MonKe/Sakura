package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.combat.AutoThrow;
import dev.sakura.client.module.impl.render.CameraClip;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Unique
    private Entity focusedEntity;
    @Unique
    private float tickProgress;

    @Shadow
    protected abstract float clipToSpace(float desiredCameraDistance);

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdateTail(World area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        if (AutoThrow.isRotating) {
            float yaw = MathHelper.lerp(tickProgress, AutoThrow.lastRenderYaw, AutoThrow.renderYaw);
            float pitch = MathHelper.lerp(tickProgress, AutoThrow.lastRenderPitch, AutoThrow.renderPitch);
            this.setRotation(yaw, pitch);
        }
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;moveBy(FFF)V", ordinal = 0))
    private void modifyCameraDistance(Args args) {
        if (Sakura.MODULES.getModule(CameraClip.class).isNormal()) {
            args.set(0, -clipToSpace(Sakura.MODULES.getModule(CameraClip.class).getDistance()));
        }
    }

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    private void onClipToSpace(float f, CallbackInfoReturnable<Float> cir) {
        CameraClip clip = Sakura.MODULES.getModule(CameraClip.class);
        if (clip.isNormal()) {
            cir.setReturnValue(clip.getDistance());
        } else if (clip.isAction()) {
            cir.setReturnValue(clip.getActionDistance());
        }
    }

    @Inject(method = "update", at = @At("HEAD"))
    private void onUpdateHead(World area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        this.focusedEntity = focusedEntity;
        this.tickProgress = tickProgress;
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void onSetCameraPosition(Args args) {
        CameraClip actionCamera = Sakura.MODULES.getModule(CameraClip.class);

        if (actionCamera != null && actionCamera.shouldModifyCamera() && focusedEntity != null) {
            Vec3d playerPos = focusedEntity.getLerpedPos(this.tickProgress);
            actionCamera.update(playerPos, this.tickProgress, focusedEntity);
            Vec3d cameraPos = actionCamera.getCameraPos();
            if (cameraPos != null) {
                args.set(0, cameraPos.x);
                args.set(1, cameraPos.y);
                args.set(2, cameraPos.z);
            }
        }
    }
}
