package dev.sakura.client.module.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Chams extends Module {
    public Chams() {
        super("Chams", "产慕斯", Category.Render);
    }

    private static final int COLOR = 0x80FFFFFF;

    public void renderPlayer(PlayerEntity player, LivingEntityRenderState state, MatrixStack matrices, int light, EntityModel<?> model, CallbackInfo ci) {
        if (player == mc.player) {
            return;
        }

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);

        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        Identifier texture = ((AbstractClientPlayerEntity) player).getSkinTextures().texture();

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(ShaderProgramKeys.RENDERTYPE_ENTITY_TRANSLUCENT);

        matrices.push();
        setupTransformsState(state, matrices);
        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.scale(0.9375f, 0.9375f, 0.9375f);
        matrices.translate(0.0f, -1.501f, 0.0f);

        @SuppressWarnings({"rawtypes", "unchecked"})
        EntityModel<LivingEntityRenderState> typedModel = (EntityModel) model;
        typedModel.setAngles(state);
        int overlay = LivingEntityRenderer.getOverlay(state, 0.0f);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
        typedModel.render(matrices, buffer, light, overlay, COLOR);
        BuiltBuffer builtBuffer = buffer.endNullable();
        if (builtBuffer != null) {
            BufferRenderer.drawWithGlobalProgram(builtBuffer);
        }

        matrices.pop();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

    }

    private void setupTransformsState(LivingEntityRenderState state, MatrixStack matrices) {
        if (!state.isInPose(EntityPose.SLEEPING)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - state.bodyYaw));
        }

        if (state.deathTime > 0) {
            float f = (state.deathTime - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f * 90.0F));
        } else if (state.usingRiptide) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - state.pitch));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.age * -75.0F));
        } else if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            float g = direction != null ? getYaw(direction) : state.bodyYaw;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270.0F));
        }
    }

    private static float getYaw(Direction direction) {
        return switch (direction) {
            case NORTH -> 270.0f;
            case SOUTH -> 90.0f;
            case EAST -> 180.0f;
            default -> 0.0f;
        };
    }
}
