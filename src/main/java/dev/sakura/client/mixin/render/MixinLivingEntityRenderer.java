package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.interfaces.IEntityRenderState;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.impl.render.Chams;
import dev.sakura.client.module.impl.render.NameTags;
import dev.sakura.client.utils.rotation.Rotation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static dev.sakura.client.Sakura.mc;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState> {
    @Shadow
    public abstract Identifier getTexture(S state);

    @ModifyArgs(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void modifySubmitModelArgs(Args args, S livingEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        final Chams chamsModule = Sakura.MODULES.getModule(Chams.class);

        if (!chamsModule.isEnabled() || !chamsModule.isColorOverlay() || !(((IEntityRenderState) livingEntityRenderState).getEntity() instanceof PlayerEntity player) || player == mc.player) {
            return;
        }

        // index 6 是因为 (model, state, matrices, layer, light, overlay, color, sprite, outlineColor, crumbling)
        args.set(6, chamsModule.getRGBAColor());
    }

    @ModifyReturnValue(method = "getRenderLayer", at = @At("RETURN"))
    private RenderLayer modifyRenderLayer(RenderLayer original, S state, boolean showBody, boolean translucent, boolean showOutline) {
        final Chams chamsModule = Sakura.MODULES.getModule(Chams.class);

        if (!chamsModule.isEnabled() || !(((IEntityRenderState) state).getEntity() instanceof PlayerEntity player) || player == mc.player) {
            return original;
        }

        Identifier texture = chamsModule.shouldKeepTextures() ? this.getTexture(state) : Identifier.of("sakura", "textures/blank.png");
        return Chams.getChamsLayer(texture);
    }


    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
    private void hookHasLabel(T livingEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (Sakura.MODULES.getModule(NameTags.class).isEnabled() && livingEntity instanceof PlayerEntity) {
            cir.setReturnValue(false);
        }
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;clampBodyYaw(Lnet/minecraft/entity/LivingEntity;FF)F"))
    private float hookBodyYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        Rotation rotation = Managers.ROTATION.animationRotation;
        Rotation lastRotation = Managers.ROTATION.lastAnimationRotation;
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

        Rotation rotation = Managers.ROTATION.animationRotation;
        Rotation lastRotation = Managers.ROTATION.lastAnimationRotation;
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

        Rotation rotation = Managers.ROTATION.animationRotation;
        Rotation lastRotation = Managers.ROTATION.lastAnimationRotation;
        if (Managers.ROTATION.isActive() && rotation != null && lastRotation != null) {
            return MathHelper.lerp(tickDelta, lastRotation.pitch, rotation.pitch);
        }

        return original;
    }
}
