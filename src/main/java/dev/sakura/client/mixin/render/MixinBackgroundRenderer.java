package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.module.impl.render.WorldTweaks;
import net.minecraft.client.render.fog.FogRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;

@Mixin(FogRenderer.class)
public abstract class MixinBackgroundRenderer {
    @Shadow
    protected abstract void applyFog(ByteBuffer buffer, int bufPos, Vector4f fogColor, float environmentalStart, float environmentalEnd, float renderDistanceStart, float renderDistanceEnd, float skyEnd, float cloudEnd);

    @ModifyExpressionValue(method = "getFogBuffer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/fog/FogRenderer;fogEnabled:Z"))
    private boolean modifyFogEnabled(boolean original) {
        if (Sakura.MODULES == null) return original;
        return original && !Sakura.MODULES.getModule(NoRender.class).noFog();
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogRenderer;applyFog(Ljava/nio/ByteBuffer;ILorg/joml/Vector4f;FFFFFF)V"))
    private void onApplyFog(FogRenderer instance, ByteBuffer buffer, int bufPos, Vector4f fogColor, float environmentalStart, float environmentalEnd, float renderDistanceStart, float renderDistanceEnd, float skyEnd, float cloudEnd) {
        if (Sakura.MODULES != null) {
            WorldTweaks worldTweaks = Sakura.MODULES.getModule(WorldTweaks.class);
            if (worldTweaks != null && worldTweaks.isEnabled() && worldTweaks.fogModify.get()) {
                int start = worldTweaks.fogStart.get();
                int end = worldTweaks.fogEnd.get();
                if (end <= start) {
                    end = Math.min(256, start + 1);
                }

                java.awt.Color color = worldTweaks.fogColor.get();
                Vector4f newColor = new Vector4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                this.applyFog(buffer, bufPos, newColor, environmentalStart, environmentalEnd, (float) start, (float) end, (float) end, (float) end);
                return;
            }
        }
        this.applyFog(buffer, bufPos, fogColor, environmentalStart, environmentalEnd, renderDistanceStart, renderDistanceEnd, skyEnd, cloudEnd);
    }
}
