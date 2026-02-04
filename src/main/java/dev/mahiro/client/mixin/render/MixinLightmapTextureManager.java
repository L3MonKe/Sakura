package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.Fullbright;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.math.ColorHelper;
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
    private GpuTexture glTexture;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private void update$skip(float tickProgress, CallbackInfo ci, @Local Profiler profiler) {
        if (Mahiro.MODULES.getModule(Fullbright.class).isGamma()) {
            RenderSystem.getDevice().createCommandEncoder().clearColorTexture(glTexture, ColorHelper.getArgb(255, 255, 255, 255));
            profiler.pop();
            ci.cancel();
        }
    }
}
