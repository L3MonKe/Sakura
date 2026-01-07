package dev.sakura.client.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.module.impl.render.Shaders;
import dev.sakura.client.utils.render.MSAAFramebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Inject(method = "render", at = @At(value = "RETURN"))
    private void hookRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        MatrixStack matrixStack = new MatrixStack();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.peek().getPositionMatrix());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));

        MSAAFramebuffer.use(() -> Sakura.EVENT_BUS.post(new Render3DEvent(matrixStack, tickCounter.getTickDelta(true))));

        RenderSystem.getModelViewStack().popMatrix();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(Lnet/minecraft/client/render/FrameGraphBuilder;IILnet/minecraft/client/gl/PostEffectProcessor$FramebufferSet;)V", ordinal = 0))
    private void replaceShaderHook(PostEffectProcessor instance, FrameGraphBuilder builder, int textureWidth, int textureHeight, PostEffectProcessor.FramebufferSet framebufferSet) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (shaders.isEnabled() && mc.world != null) {
            if (Managers.SHADER.fullNullCheck()) return;
            Managers.SHADER.setupShader(shaders.mode.get(), Managers.SHADER.getShaderOutline(shaders.mode.get()));
        } else {
            instance.render(tickDelta);
        }
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d pos, float tickDelta, Fog fog, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noWeather()) ci.cancel();
    }
}
