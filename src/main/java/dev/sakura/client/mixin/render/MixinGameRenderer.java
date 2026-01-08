package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.AspectRatio;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.utils.math.FrameRateCounter;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Shadow
    private float zoom;
    @Shadow
    private float zoomX;
    @Shadow
    private float viewDistance;
    @Shadow
    private float zoomY;

    @Inject(method = "render", at = @At("TAIL"))
    private void postHudRenderHook(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        FrameRateCounter.INSTANCE.recordFrame();
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void tiltViewWhenHurtHook(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (Sakura.MODULES.getModule(NoRender.class).getNoHurtCam()) {
            ci.cancel();
        }
    }

    @Inject(method = "getBasicProjectionMatrix", at = @At("TAIL"), cancellable = true)
    public void getBasicProjectionMatrix(float fovDegrees, CallbackInfoReturnable<Matrix4f> info) {
        if (Sakura.MODULES.getModule(AspectRatio.class).isEnabled()) {
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.peek().getPositionMatrix().identity();
            if (zoom != 1.0f) {
                matrixStack.translate(zoomX, -zoomY, 0.0f);
                matrixStack.scale(zoom, zoom, 1.0f);
            }

            matrixStack.peek().getPositionMatrix().mul(new Matrix4f().setPerspective((float) (fovDegrees * 0.01745329238474369), Sakura.MODULES.getModule(AspectRatio.class).ratio.get().floatValue(), 0.05f, viewDistance * 4.0f));
            info.setReturnValue(matrixStack.peek().getPositionMatrix());
        }
    }
}
