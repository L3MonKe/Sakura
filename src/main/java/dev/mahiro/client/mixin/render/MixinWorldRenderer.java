package dev.mahiro.client.mixin.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.module.impl.render.NoRender;
import dev.mahiro.client.utils.render.Render3DUtil;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    /*TODO: @ModifyArg(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FramePass;setRenderer(Ljava/lang/Runnable;)V"), index = 0)
    private Runnable renderSky$wrapRenderer(Runnable original, @Local SkyRenderState skyRenderState) {
        Atmosphere atmosphere = Mahiro.MODULES.getModule(Atmosphere.class);
        if (!atmosphere.isEnabled() || !atmosphere.modifyFog.get()) {
            return original;
        }

        int targetColor = atmosphere.fogColor.get().getRGB();
        return () -> {
            int oldColor = skyRenderState.skyColor;
            skyRenderState.skyColor = targetColor;
            try {
                original.run();
            } finally {
                skyRenderState.skyColor = oldColor;
            }
        };
    }*/

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void onRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f basicProjectionMatrix, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
<<<<<<< HEAD
        Render3DUtil.updateMatrices(projectionMatrix, positionMatrix);
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void onRenderReturn(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f basicProjectionMatrix, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
        MatrixStack matrixStack = new MatrixStack();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.peek().getPositionMatrix());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));

        Mahiro.EVENT_BUS.post(new Render3DEvent(matrixStack, tickCounter.getTickProgress(true)));

=======
        RenderSystem.getModelViewStack().pushMatrix().mul(positionMatrix);
        Mahiro.EVENT_BUS.post(new Render3DEvent(new MatrixStack(), tickCounter.getTickProgress(true)));
>>>>>>> 29a39dbff8f1e9022164526e67187b83ac1ff09a
        RenderSystem.getModelViewStack().popMatrix();
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice, CallbackInfo ci) {
        NoRender noRender = Mahiro.MODULES.getModule(NoRender.class);
        if (noRender.noWeather()) ci.cancel();
    }
}
