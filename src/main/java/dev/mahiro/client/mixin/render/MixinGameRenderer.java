package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.AspectRatio;
import dev.mahiro.client.module.impl.render.NoRender;
import dev.mahiro.client.utils.math.FrameRateCounter;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    public abstract float getFarPlaneDistance();

    @Inject(method = "render", at = @At("TAIL"))
    private void postHudRenderHook(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        FrameRateCounter.INSTANCE.recordFrame();
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void tiltViewWhenHurtHook(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (Mahiro.MODULES.getModule(NoRender.class).getNoHurtCam()) {
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "getBasicProjectionMatrix", at = @At("RETURN"))
    private Matrix4f getBasicProjectionMatrix(Matrix4f original, float fovDegrees) {
        AspectRatio aspectRatio = Mahiro.MODULES.getModule(AspectRatio.class);
        if (!aspectRatio.isEnabled()) {
            return original;
        }
        return new Matrix4f().setPerspective((float) (fovDegrees * 0.01745329238474369), aspectRatio.ratio.get().floatValue(), 0.05f, getFarPlaneDistance());
    }
}
