package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.Fullbright;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {
    @Shadow
    @Final
    private SimpleFramebuffer lightmapFramebuffer;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private void update$skip(float tickProgress, CallbackInfo ci, @Local Profiler profiler) {
        if (Sakura.MODULES.getModule(Fullbright.class).isGamma()) {
            // 1.21.10版本
//            RenderSystem.getDevice().createCommandEncoder().clearColorTexture(glTexture, ColorHelper.getArgb(255, 255, 255, 255));
            this.lightmapFramebuffer.clear();
            profiler.pop();
            ci.cancel();
        }
    }
}
