package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.impl.render.Chams;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

import static dev.sakura.client.Sakura.mc;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void redirectRender(M model, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color, S state) {
        Chams chams = Sakura.MODULES.getModule(Chams.class);

        if (chams.isEnabled() && chams.players.get() && state instanceof PlayerEntityRenderState playerState && chams.alternativeBlending.get()) {
            Color c = chams.playerColor.get();
            float r = c.getRed() / 255f;
            float g = c.getGreen() / 255f;
            float b = c.getBlue() / 255f;
            float a = c.getAlpha() / 255f;

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(r, g, b, a);

            Identifier texture = playerState.skinTextures.texture();
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.setShader(ShaderProgramKeys.RENDERTYPE_ENTITY_TRANSLUCENT);

            BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

            model.render(matrices, buffer, light, overlay, color);

            BuiltBuffer builtBuffer = buffer.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }

            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.enableDepthTest();

            if (chams.playerTexture.get()) {
                return;
            }
        }

        model.render(matrices, vertices, light, overlay, color);
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;clampBodyYaw(Lnet/minecraft/entity/LivingEntity;FF)F"))
    private float hookBodyYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        if (Managers.ROTATION.isActive()) {
            return MathHelper.lerp(tickDelta, RotationManager.getPrevRenderYawOffset(), RotationManager.getRenderYawOffset());
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerpAngleDegrees(FFF)F"))
    private float hookHeadYaw(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        if (Managers.ROTATION.isActive()) {
            return MathHelper.lerpAngleDegrees(tickDelta, RotationManager.getPrevRotationYawHead(), RotationManager.getRotationYawHead());
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getLerpedPitch(F)F"))
    private float hookPitch(float original, LivingEntity entity, S state, float tickDelta) {
        if (entity != mc.player) {
            return original;
        }

        if (Managers.ROTATION.isActive()) {
            return MathHelper.lerp(tickDelta, RotationManager.getPrevRenderPitch(), RotationManager.getRenderPitch());
        }

        return original;
    }
}
