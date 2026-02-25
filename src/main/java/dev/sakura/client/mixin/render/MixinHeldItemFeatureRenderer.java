package dev.sakura.client.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.Chams;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public class MixinHeldItemFeatureRenderer {
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/ArmedEntityRenderState;FF)V", at = @At("HEAD"))
    private void onRenderHead(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmedEntityRenderState state, float limbAngle, float limbDistance, CallbackInfo ci) {
        Chams chams = Sakura.MODULES.getModule(Chams.class);
        if (!chams.shouldApplyHand(state)) return;

        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.depthMask(false);
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/ArmedEntityRenderState;FF)V", at = @At("RETURN"))
    private void onRenderReturn(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmedEntityRenderState state, float limbAngle, float limbDistance, CallbackInfo ci) {
        Chams chams = Sakura.MODULES.getModule(Chams.class);
        if (!chams.shouldApplyHand(state)) return;

        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}
