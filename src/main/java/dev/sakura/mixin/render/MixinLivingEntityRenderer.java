package dev.sakura.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.manager.impl.RotationManager;
import dev.sakura.utils.vector.Vector2f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/18 00:56
 * @Filename：MixinLivingEntityRenderer
 */
@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {


    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;clampBodyYaw(Lnet/minecraft/entity/LivingEntity;FF)F"))
    private float hookBodyYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != MinecraftClient.getInstance().player || RotationManager.lastRotations == null || RotationManager.rotations == null) {
            return original;
        }

        if (RotationManager.isActive()) {
            return MathHelper.lerpAngleDegrees(tickDelta, RotationManager.rotations.x, RotationManager.lastRotations.x);
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerpAngleDegrees(FFF)F"))
    private float hookHeadYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != MinecraftClient.getInstance().player || RotationManager.lastRotations == null || RotationManager.rotations == null) {
            return original;
        }

        if (RotationManager.isActive()) {
            return MathHelper.lerpAngleDegrees(tickDelta, RotationManager.rotations.x, RotationManager.lastRotations.x);
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getLerpedPitch(F)F"))
    private float hookPitch(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != MinecraftClient.getInstance().player || RotationManager.lastRotations == null || RotationManager.rotations == null) {
            return original;
        }

        if (RotationManager.isActive()) {
            return MathHelper.lerpAngleDegrees(tickDelta, RotationManager.rotations.y, RotationManager.lastRotations.y);
        }

        return original;
    }
}
