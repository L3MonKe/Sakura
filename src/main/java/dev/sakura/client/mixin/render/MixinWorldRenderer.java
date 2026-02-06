package dev.sakura.client.mixin.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import dev.sakura.client.Sakura;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.module.impl.render.Shaders;
import dev.sakura.client.module.impl.render.WorldTweaks;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderLoader;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Shadow
    private Framebuffer entityOutlineFramebuffer;

    @Shadow
    @Final
    private WorldRenderState worldRenderState;

    @Unique
    private static final Identifier vanillaOutline = Identifier.ofVanilla("entity_outline");

    @ModifyArg(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FramePass;setRenderer(Ljava/lang/Runnable;)V"), index = 0)
    private Runnable renderSky$wrapRenderer(Runnable original) {
        if (Sakura.MODULES == null) return original;
        WorldTweaks worldTweaks = Sakura.MODULES.getModule(WorldTweaks.class);
        if (worldTweaks == null || !worldTweaks.isEnabled()) return original;

        boolean modifySkyColor = worldTweaks.fogModify.get();
        boolean applySkyShader = worldTweaks.shaderMode.is(WorldTweaks.Shader.Grid);
        if (!modifySkyColor && !applySkyShader) return original;

        int targetColor = modifySkyColor ? worldTweaks.fogColor.get().getRGB() : 0;
        SkyRenderState skyRenderState = this.worldRenderState.skyRenderState;
        return () -> {
            int oldColor = skyRenderState.skyColor;
            if (modifySkyColor) {
                skyRenderState.skyColor = targetColor;
            }
            try {
                original.run();
            } finally {
                if (modifySkyColor) {
                    skyRenderState.skyColor = oldColor;
                }
            }

            if (applySkyShader && Managers.SHADER != null) {
                Managers.SHADER.renderSkyGridShader(Sakura.mc.getRenderTickCounter().getTickProgress(true), worldTweaks.skyGridAlpha.get(), worldTweaks.facing.get().yawOffsetDegrees);
            }
        };
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noWeather()) ci.cancel();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderLoader;loadPostEffect(Lnet/minecraft/util/Identifier;Ljava/util/Set;)Lnet/minecraft/client/gl/PostEffectProcessor;"))
    private PostEffectProcessor onRender(ShaderLoader shaderLoader, Identifier id, Set<Identifier> availableExternalTargets) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (shaders != null && shaders.isEnabled() && vanillaOutline.equals(id)) {
            return null;
        }
        return shaderLoader.loadPostEffect(id, availableExternalTargets);
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("HEAD"), cancellable = true)
    private void onDrawEntityOutlinesFramebuffer(CallbackInfo ci) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (shaders == null || !shaders.isEnabled()) return;
        if (entityOutlineFramebuffer == null) return;

        Managers.SHADER.renderEntityOutlineShader(entityOutlineFramebuffer, shaders.mode.get(), Sakura.mc.getRenderTickCounter().getTickProgress(true));
        ci.cancel();
    }
}
