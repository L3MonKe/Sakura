package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.Crystal;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.model.EndCrystalEntityModel;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class MixinEndCrystalEntityRenderer {
    @Unique
    private Crystal crystal;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        crystal = Sakura.MODULES.getModule(Crystal.class);
    }

    @Shadow
    @Final
    @Mutable
    private static RenderLayer END_CRYSTAL;

    @Shadow
    @Final
    private static Identifier TEXTURE;

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void render$renderLayer(EndCrystalEntityRenderState endCrystalEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        END_CRYSTAL = RenderLayer.getEntityTranslucent((crystal.isEnabled() && crystal.modifyScale.get() && !crystal.Texture.get()) ? crystal.BLANK : TEXTURE);
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
    private void render$scale(EndCrystalEntityRenderState endCrystalEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        if (!crystal.isEnabled() || !crystal.modifyScale.get()) return;

        float v = ((Number) crystal.scale.get()).floatValue();

        if (crystal.enableBreathing.get()) {
            long time = System.currentTimeMillis();
            float breathingEffect = (float) (Math.sin(time * 0.001 * ((Number) crystal.breathingSpeed.get()).floatValue()) * ((Number) crystal.breathingAmount.get()).floatValue());
            v += breathingEffect;
        }
        if (crystal.enableRotation.get()) {
            long time = System.currentTimeMillis();
            float rotation = (time * 0.01f * ((Number) crystal.rotationSpeed.get()).floatValue()) % 360;
            matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        }

        matrixStack.scale(v, v, v);
    }

    @Shadow
    @Final
    private EndCrystalEntityModel model;

    @WrapWithCondition(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EndCrystalEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    private boolean render$color(EndCrystalEntityModel instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        if (crystal.isEnabled() && crystal.modifyScale.get()) {
            model.render(matrices, vertices, light, overlay, crystal.crystalColor.get().getRGB());
            return false;
        }
        return true;
    }
}