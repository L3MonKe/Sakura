package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.interfaces.IEntityRenderState;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.impl.render.Chams;
import dev.mahiro.client.module.impl.render.NameTags;
import dev.mahiro.client.utils.vector.Rotation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static dev.mahiro.client.Mahiro.mc;
import static org.lwjgl.opengl.GL11.*;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @ModifyArgs(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void modifySubmitModelArgs(Args args, S livingEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        final Chams chamsModule = Mahiro.MODULES.getModule(Chams.class);

        if (!chamsModule.isEnabled() || !chamsModule.isColorOverlay() || !(((IEntityRenderState) livingEntityRenderState).getEntity() instanceof PlayerEntity player) || player == mc.player) {
            return;
        }

        // index 6 是因为 (model, state, matrices, layer, light, overlay, color, sprite, outlineColor, crumbling)
        args.set(6, chamsModule.getRGBAColor());
    }

    @ModifyReturnValue(method = "getRenderLayer", at = @At("RETURN"))
    private RenderLayer removePlayerTexture(RenderLayer original, S state, boolean showBody, boolean translucent, boolean showOutline) {
        final Chams chamsModule = Mahiro.MODULES.getModule(Chams.class);

        if (!chamsModule.isEnabled() || chamsModule.shouldKeepTextures() || !(((IEntityRenderState) state).getEntity() instanceof PlayerEntity player) || player == mc.player) {
            return original;
        }

        return RenderLayers.itemEntityTranslucentCull(Identifier.of("mahiro", "textures/blank.png"));
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"))
    private void setPolygonStates(S state, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo info) {
        if (!(((IEntityRenderState) state).getEntity() instanceof PlayerEntity player) || player == mc.player) return;

        if (Mahiro.MODULES.getModule(Chams.class).isEnabled()) {
            glEnable(GL_POLYGON_OFFSET_FILL);
            glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("TAIL"))
    private void revertPolygonStates(S state, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo info) {
        if (!(((IEntityRenderState) state).getEntity() instanceof PlayerEntity player) || player == mc.player) return;

        if (Mahiro.MODULES.getModule(Chams.class).isEnabled()) {
            glPolygonOffset(1.0f, 1100000.0f);
            glDisable(GL_POLYGON_OFFSET_FILL);
        }
    }

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
    private void hookHasLabel(T livingEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (Mahiro.MODULES.getModule(NameTags.class).isEnabled() && livingEntity instanceof PlayerEntity) {
            cir.setReturnValue(false);
        }
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;clampBodyYaw(Lnet/minecraft/entity/LivingEntity;FF)F"))
    private float hookBodyYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        Rotation rotation = RotationManager.animationRotation;
        Rotation lastRotation = RotationManager.lastAnimationRotation;
        if (Managers.ROTATION.isActive() && rotation != null && lastRotation != null) {
            return MathHelper.lerpAngleDegrees(tickDelta, lastRotation.yaw, rotation.yaw);
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerpAngleDegrees(FFF)F"))
    private float hookHeadYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        Rotation rotation = RotationManager.animationRotation;
        Rotation lastRotation = RotationManager.lastAnimationRotation;
        if (Managers.ROTATION.isActive() && rotation != null && lastRotation != null) {
            return MathHelper.lerpAngleDegrees(tickDelta, lastRotation.yaw, rotation.yaw);
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getLerpedPitch(F)F"))
    private float hookPitch(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        Rotation rotation = RotationManager.animationRotation;
        Rotation lastRotation = RotationManager.lastAnimationRotation;
        if (Managers.ROTATION.isActive() && rotation != null && lastRotation != null) {
            return MathHelper.lerp(tickDelta, lastRotation.pitch, rotation.pitch);
        }

        return original;
    }
}
