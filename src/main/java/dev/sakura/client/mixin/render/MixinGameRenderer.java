package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.interfaces.ISplashOverlayState;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IGameRenderer;
import dev.sakura.client.module.impl.render.AspectRatio;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.module.impl.render.Shaders;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.shaders.SplashShader;
import dev.sakura.client.utils.math.FrameRateCounter;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

import dev.sakura.client.module.impl.render.NoFov;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    public abstract float getFarPlaneDistance();

    @Unique
    private static GpuBufferSlice projectionSlice;

    @Unique
    private static final Matrix4f lastPosition = new Matrix4f();

    @Unique
    private static float lastTickDelta;

    @Unique
    private static boolean hasMatrices;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;applyCursorTo(Lnet/minecraft/client/util/Window;)V"))
    private void sakura$renderScreenNanoVgOnTop(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        NanoVGRenderer.INSTANCE.flushScreenQueue();

        if (mc.getOverlay() instanceof SplashOverlay && mc.getOverlay() instanceof ISplashOverlayState sakuraSplashOverlay && sakuraSplashOverlay.sakura$shouldRenderSplash()) {
            SplashShader.getInstance().render(mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(), sakuraSplashOverlay.sakura$getSplashProgress(), sakuraSplashOverlay.sakura$getSplashFadeOut(), sakuraSplashOverlay.sakura$getSplashZoom());
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void postHudRenderHook(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        FrameRateCounter.INSTANCE.recordFrame();
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;renderHand(FZLorg/joml/Matrix4f;)V", shift = At.Shift.AFTER))
    private void onRenderWorld(RenderTickCounter renderTickCounter, CallbackInfo ci) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (!shaders.isEnabled() || !shaders.isHandsEnabled()) {
            Managers.SHADER.renderShaders(renderTickCounter.getTickProgress(false), false);
            return;
        }

        float tickProgress = renderTickCounter.getTickProgress(false);
        boolean sleeping = mc.getCameraEntity() instanceof LivingEntity living && living.isSleeping();
        Matrix4f positionMatrix = new Matrix4f().rotation(mc.gameRenderer.getCamera().getRotation().conjugate(new Quaternionf()));

        Managers.SHADER.renderShader(() -> ((IGameRenderer) this).sakura$renderHand(tickProgress, sleeping, positionMatrix), shaders.handsMode.get());
        Managers.SHADER.renderShaders(tickProgress, true);
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = {"ldc=hand"}))
    private void onRenderWorldMatrices(RenderTickCounter renderTickCounter, CallbackInfo ci, @Local(ordinal = 1) Matrix4f position) {
        projectionSlice = RenderSystem.getProjectionMatrixBuffer();
        lastPosition.set(position);
        lastTickDelta = renderTickCounter.getTickProgress(true);
        hasMatrices = true;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/render/state/GuiRenderState;II)V", shift = At.Shift.BEFORE))
    private void onRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        if (!tick || mc.player == null || !hasMatrices) return;

        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(projectionSlice, ProjectionType.PERSPECTIVE);
        RenderSystem.getModelViewStack().pushMatrix().mul(lastPosition);

        Sakura.EVENT_BUS.post(new Render3DEvent(new MatrixStack(), lastTickDelta));

        RenderSystem.getModelViewStack().popMatrix();
        RenderSystem.restoreProjectionMatrix();
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void tiltViewWhenHurtHook(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (Sakura.MODULES.getModule(NoRender.class).getNoHurtCam()) {
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "getBasicProjectionMatrix", at = @At("RETURN"))
    private Matrix4f hookGetBasicProjectionMatrix(Matrix4f original, float fovDegrees) {
        NoFov noFov = Sakura.MODULES.getModule(NoFov.class);
        float fov = fovDegrees;

        if (noFov != null && noFov.isEnabled()) {
            fov = noFov.fov.get().floatValue();
        }

        AspectRatio aspectRatio = Sakura.MODULES.getModule(AspectRatio.class);
        if (!aspectRatio.isEnabled() && (noFov == null || !noFov.isEnabled())) {
            return original;
        }

        float ratio = aspectRatio.isEnabled() ? aspectRatio.ratio.get().floatValue() : (float) mc.getWindow().getFramebufferWidth() / (float) mc.getWindow().getFramebufferHeight();
        return new Matrix4f().setPerspective((float) (fov * 0.01745329238474369), ratio, 0.05f, getFarPlaneDistance());
    }
}
