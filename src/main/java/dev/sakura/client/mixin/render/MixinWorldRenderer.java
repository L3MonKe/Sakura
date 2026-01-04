package dev.sakura.client.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.module.impl.render.Glow;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.utils.render.MSAAFramebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Inject(method = "renderEntities", at = @At("HEAD"))
    private void onBeforeEntities(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Camera camera, RenderTickCounter tickCounter, List<?> entities, CallbackInfo ci) {
        if (Glow.INSTANCE != null && Glow.INSTANCE.isEnabled()) {
            Glow.INSTANCE.captureBeforeEntities();
        }
    }

    @Inject(method = "renderEntities", at = @At("RETURN"))
    private void onAfterEntities(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Camera camera, RenderTickCounter tickCounter, List<?> entities, CallbackInfo ci) {
        if (Glow.INSTANCE != null && Glow.INSTANCE.isEnabled()) {
            Glow.INSTANCE.captureAfterEntities();
        }
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void hookRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        MatrixStack matrixStack = new MatrixStack();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.peek().getPositionMatrix());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));

        MSAAFramebuffer.use(() -> Sakura.EVENT_BUS.post(new Render3DEvent(matrixStack, tickCounter.getTickDelta(true))));

        RenderSystem.getModelViewStack().popMatrix();
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d pos, float tickDelta, Fog fog, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noWeather()) ci.cancel();
    }
}