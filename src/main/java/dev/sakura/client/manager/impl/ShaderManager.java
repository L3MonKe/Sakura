package dev.sakura.client.manager.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.mixin.accessor.IPostEffectProcessor;
import dev.sakura.client.module.impl.render.Shaders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL30C;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static dev.sakura.client.Sakura.mc;

public class ShaderManager {
    private final static List<RenderTask> tasks = new ArrayList<>();
    private ThunderHackFramebuffer shaderBuffer;

    public float time = 0;

    public static PostEffectProcessor DEFAULT_OUTLINE;
    public static PostEffectProcessor SMOKE_OUTLINE;
    public static PostEffectProcessor GRADIENT_OUTLINE;
    public static PostEffectProcessor SNOW_OUTLINE;
    public static PostEffectProcessor FADE_OUTLINE;

    public static PostEffectProcessor DEFAULT;
    public static PostEffectProcessor SMOKE;
    public static PostEffectProcessor GRADIENT;
    public static PostEffectProcessor SNOW;
    public static PostEffectProcessor FADE;

    public void renderShader(Runnable runnable, Shader mode) {
        tasks.add(new RenderTask(runnable, mode));
    }

    public void renderShaders() {
        if (ensureLoaded()) return;

        tasks.forEach(t -> applyShader(t.task(), t.shader()));
        tasks.clear();
    }

    public void applyShader(Runnable runnable, Shader mode) {
        Framebuffer MCBuffer = MinecraftClient.getInstance().getFramebuffer();
        RenderSystem.assertOnRenderThreadOrInit();
        if (shaderBuffer.textureWidth != MCBuffer.textureWidth || shaderBuffer.textureHeight != MCBuffer.textureHeight)
            shaderBuffer.resize(MCBuffer.textureWidth, MCBuffer.textureHeight);
        GlStateManager._glBindFramebuffer(GL30C.GL_DRAW_FRAMEBUFFER, shaderBuffer.fbo);
        shaderBuffer.beginWrite(true);
        runnable.run();
        shaderBuffer.endWrite();
        PostEffectProcessor effect = getShader(mode);
        if (effect != null) {
            setupShader(mode, effect);
            effect.render(shaderBuffer, ObjectAllocator.TRIVIAL);
        }

        GlStateManager._glBindFramebuffer(GL30C.GL_DRAW_FRAMEBUFFER, MCBuffer.fbo);
        MCBuffer.beginWrite(false);
        shaderBuffer.clear();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
        RenderSystem.backupProjectionMatrix();
        shaderBuffer.draw(shaderBuffer.textureWidth, shaderBuffer.textureHeight);
        RenderSystem.restoreProjectionMatrix();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public PostEffectProcessor getShader(@NotNull Shader mode) {
        return switch (mode) {
            case Gradient -> GRADIENT;
            case Smoke -> SMOKE;
            case Snow -> SNOW;
            case Fade -> FADE;
            default -> DEFAULT;
        };
    }

    public PostEffectProcessor getShaderOutline(@NotNull Shader mode) {
        return switch (mode) {
            case Gradient -> GRADIENT_OUTLINE;
            case Smoke -> SMOKE_OUTLINE;
            case Snow -> SNOW_OUTLINE;
            case Fade -> FADE_OUTLINE;
            default -> DEFAULT_OUTLINE;
        };
    }

    public void setupShader(Shader shader, PostEffectProcessor effect) {
        if (effect == null) return;
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (shaders == null) return;
        if (shader == Shader.Gradient) {
            setFloat(effect, "alpha0", shaders.glow.get() ? -1.0f : shaders.outlineColor.get().getAlpha() / 255.0f);
            setFloat(effect, "alpha1", shaders.fillAlpha.get() / 255f);
            setFloat(effect, "alpha2", shaders.alpha2.get() / 255f);
            setInt(effect, "lineWidth", shaders.lineWidth.get());
            setInt(effect, "oct", shaders.octaves.get());
            setInt(effect, "quality", shaders.quality.get());
            setFloat(effect, "factor", shaders.factor.get().floatValue());
            setFloat(effect, "moreGradient", shaders.gradient.get().floatValue());
            setVec2(effect, "resolution", (float) mc.getWindow().getScaledWidth(), (float) mc.getWindow().getScaledHeight());
            setFloat(effect, "time", time);
            time += 0.008f;
        } else if (shader == Shader.Smoke) {
            setFloat(effect, "alpha0", shaders.glow.get() ? -1.0f : shaders.outlineColor.get().getAlpha() / 255.0f);
            setFloat(effect, "alpha1", shaders.fillAlpha.get() / 255f);
            setInt(effect, "lineWidth", shaders.lineWidth.get());
            setInt(effect, "quality", shaders.quality.get());
            Color outline = shaders.outlineColor.get();
            Color outline1 = shaders.outlineColor1.get();
            Color outline2 = shaders.outlineColor2.get();
            Color fill1 = shaders.fillColor1.get();
            Color fill2 = shaders.fillColor2.get();
            Color fill3 = shaders.fillColor3.get();
            setVec4(effect, "first", outline.getRed() / 255f, outline.getGreen() / 255f, outline.getBlue() / 255f, outline.getAlpha() / 255f);
            setVec3(effect, "second", outline1.getRed() / 255f, outline1.getGreen() / 255f, outline1.getBlue() / 255f);
            setVec3(effect, "third", outline2.getRed() / 255f, outline2.getGreen() / 255f, outline2.getBlue() / 255f);
            setVec4(effect, "ffirst", fill1.getRed() / 255f, fill1.getGreen() / 255f, fill1.getBlue() / 255f, fill1.getAlpha() / 255f);
            setVec3(effect, "fsecond", fill2.getRed() / 255f, fill2.getGreen() / 255f, fill2.getBlue() / 255f);
            setVec3(effect, "fthird", fill3.getRed() / 255f, fill3.getGreen() / 255f, fill3.getBlue() / 255f);
            setInt(effect, "oct", shaders.octaves.get());
            setVec2(effect, "resolution", (float) mc.getWindow().getScaledWidth(), (float) mc.getWindow().getScaledHeight());
            setFloat(effect, "time", time);
            time += 0.008f;
        } else if (shader == Shader.Default) {
            setFloat(effect, "alpha0", shaders.glow.get() ? -1.0f : shaders.outlineColor.get().getAlpha() / 255.0f);
            setInt(effect, "lineWidth", shaders.lineWidth.get());
            setInt(effect, "quality", shaders.quality.get());
            Color fill = shaders.fillColor1.get();
            Color outline = shaders.outlineColor.get();
            setVec4(effect, "color", fill.getRed() / 255f, fill.getGreen() / 255f, fill.getBlue() / 255f, fill.getAlpha() / 255f);
            setVec4(effect, "outlinecolor", outline.getRed() / 255f, outline.getGreen() / 255f, outline.getBlue() / 255f, outline.getAlpha() / 255f);
        } else if (shader == Shader.Snow) {
            Color fill = shaders.fillColor1.get();
            setVec4(effect, "color", fill.getRed() / 255f, fill.getGreen() / 255f, fill.getBlue() / 255f, fill.getAlpha() / 255f);
            setInt(effect, "quality", shaders.quality.get());
            setVec2(effect, "resolution", (float) mc.getWindow().getScaledWidth(), (float) mc.getWindow().getScaledHeight());
            setFloat(effect, "time", time);
            time += 0.008f;
        } else if (shader == Shader.Fade) {
            setFloat(effect, "alpha0", shaders.glow.get() ? -1.0f : shaders.outlineColor.get().getAlpha() / 255.0f);
            setFloat(effect, "fillAlpha", shaders.fillAlpha.get() / 255f);
            setInt(effect, "lineWidth", shaders.lineWidth.get());
            setInt(effect, "quality", shaders.quality.get());
            Color outline = shaders.outlineColor.get();
            Color primary = shaders.fillColor1.get();
            Color secondary = shaders.fillColor2.get();
            setVec4(effect, "outlinecolor", outline.getRed() / 255f, outline.getGreen() / 255f, outline.getBlue() / 255f, outline.getAlpha() / 255f);
            setVec4(effect, "primaryColor", primary.getRed() / 255f, primary.getGreen() / 255f, primary.getBlue() / 255f, primary.getAlpha() / 255f);
            setVec4(effect, "secondaryColor", secondary.getRed() / 255f, secondary.getGreen() / 255f, secondary.getBlue() / 255f, secondary.getAlpha() / 255f);
            setFloat(effect, "time", (System.currentTimeMillis() % 100000) / 1000f);
        }
    }

    public void reloadShaders() {
        DEFAULT = null;
        SMOKE = null;
        GRADIENT = null;
        SNOW = null;
        FADE = null;
        DEFAULT_OUTLINE = null;
        SMOKE_OUTLINE = null;
        GRADIENT_OUTLINE = null;
        SNOW_OUTLINE = null;
        FADE_OUTLINE = null;

        try {
            DEFAULT = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "outline"), Set.of(PostEffectProcessor.MAIN));
            SMOKE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "smoke"), Set.of(PostEffectProcessor.MAIN));
            GRADIENT = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "gradient"), Set.of(PostEffectProcessor.MAIN));
            SNOW = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "snow"), Set.of(PostEffectProcessor.MAIN));
            FADE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "fade"), Set.of(PostEffectProcessor.MAIN));

            Identifier entityOutline = Identifier.of("minecraft", "entity_outline");
            Set<Identifier> outlineTargets = Set.of(PostEffectProcessor.MAIN, entityOutline);
            DEFAULT_OUTLINE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "outline_entity"), outlineTargets);
            SMOKE_OUTLINE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "smoke_entity"), outlineTargets);
            GRADIENT_OUTLINE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "gradient_entity"), outlineTargets);
            SNOW_OUTLINE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "snow_entity"), outlineTargets);
            FADE_OUTLINE = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "fade_entity"), outlineTargets);
        } catch (Exception e) {
            Sakura.LOGGER.error("CANT LOAD::FUCKFUCKFUCK", e);
        }
    }

    public static class ThunderHackFramebuffer extends Framebuffer {
        public ThunderHackFramebuffer(int width, int height) {
            super(false);
            RenderSystem.assertOnRenderThreadOrInit();
            resize(width, height);
            setClearColor(0f, 0f, 0f, 0f);
        }
    }

    public boolean fullNullCheck() {
        return ensureLoaded();
    }

    public record RenderTask(Runnable task, Shader shader) {
    }

    public enum Shader {
        Default,
        Smoke,
        Gradient,
        Snow,
        Fade
    }

    private static void setFloat(PostEffectProcessor effect, String name, float value) {
        for (PostEffectPass pass : ((IPostEffectProcessor) effect).getPasses()) {
            GlUniform uniform = pass.getProgram().getUniform(name);
            if (uniform != null) {
                uniform.set(value);
            }
        }
    }

    private static void setInt(PostEffectProcessor effect, String name, int value) {
        for (PostEffectPass pass : ((IPostEffectProcessor) effect).getPasses()) {
            GlUniform uniform = pass.getProgram().getUniform(name);
            if (uniform != null) {
                uniform.set(value);
            }
        }
    }

    private static void setVec2(PostEffectProcessor effect, String name, float x, float y) {
        for (PostEffectPass pass : ((IPostEffectProcessor) effect).getPasses()) {
            GlUniform uniform = pass.getProgram().getUniform(name);
            if (uniform != null) {
                uniform.set(x, y);
            }
        }
    }

    private static void setVec3(PostEffectProcessor effect, String name, float x, float y, float z) {
        for (PostEffectPass pass : ((IPostEffectProcessor) effect).getPasses()) {
            GlUniform uniform = pass.getProgram().getUniform(name);
            if (uniform != null) {
                uniform.set(x, y, z);
            }
        }
    }

    private static void setVec4(PostEffectProcessor effect, String name, float x, float y, float z, float w) {
        for (PostEffectPass pass : ((IPostEffectProcessor) effect).getPasses()) {
            GlUniform uniform = pass.getProgram().getUniform(name);
            if (uniform != null) {
                uniform.set(x, y, z, w);
            }
        }
    }

    private boolean ensureLoaded() {
        if (shaderBuffer == null) {
            shaderBuffer = new ThunderHackFramebuffer(mc.getFramebuffer().textureWidth, mc.getFramebuffer().textureHeight);
            reloadShaders();
            return true;
        }

        if (DEFAULT == null || SMOKE == null || GRADIENT == null || SNOW == null || FADE == null) {
            reloadShaders();
            return true;
        }

        if (DEFAULT_OUTLINE == null || SMOKE_OUTLINE == null || GRADIENT_OUTLINE == null || SNOW_OUTLINE == null || FADE_OUTLINE == null) {
            reloadShaders();
            return true;
        }

        return false;
    }
}
