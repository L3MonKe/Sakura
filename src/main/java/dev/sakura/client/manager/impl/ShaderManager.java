package dev.sakura.client.manager.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.mixin.accessor.IGameRenderer;
import dev.sakura.client.mixin.accessor.IPostEffectProcessor;
import dev.sakura.client.module.impl.render.Shaders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static dev.sakura.client.Sakura.mc;

public class ShaderManager {
    private final static List<RenderTask> tasks = new ArrayList<>();
    private SakuraBuffer shaderBuffer;

    public float time = 0;

    private final Map<Shader, PostEffectProcessor> postEffects = new EnumMap<>(Shader.class);

    public void renderShader(Runnable runnable, Shader mode) {
        tasks.add(new RenderTask(runnable, mode));
    }

    public void renderShaders() {
        if (tasks.isEmpty()) return;
        Framebuffer mainBuffer = mc.getFramebuffer();
        ensureShaderBuffer(mainBuffer);
        float tickDelta = mc.getRenderTickCounter().getTickDelta(true);

        tasks.forEach(t -> applyShader(t.task(), t.shader(), tickDelta));
        tasks.clear();
    }

    public void applyShader(Runnable runnable, Shader mode, float tickDelta) {
        RenderSystem.assertOnRenderThreadOrInit();

        Framebuffer mainBuffer = MinecraftClient.getInstance().getFramebuffer();
        ensureShaderBuffer(mainBuffer);

        shaderBuffer.setClearColor(0f, 0f, 0f, 0f);
        shaderBuffer.beginWrite(false);
        runnable.run();
        shaderBuffer.endWrite();

        PostEffectProcessor effect = getOrCreatePostEffect(mode);
        if (effect == null) {
            shaderBuffer.clear();
            mainBuffer.beginWrite(false);
            return;
        }

        setupShader(mode, effect, tickDelta);
        List<PostEffectPass> passes = ((IPostEffectProcessor) effect).getPasses();
        for (PostEffectPass pass : passes) {
            ShaderProgram program = pass.getProgram();
            program.addSamplerTexture("MaskSampler", shaderBuffer.getColorAttachment());
        }
        effect.render(mainBuffer, ((IGameRenderer) mc.gameRenderer).getPool());

        shaderBuffer.clear();
        mainBuffer.beginWrite(false);
    }

    public boolean renderOutlineShader(Shader shader, float tickDelta, FrameGraphBuilder frameGraphBuilder, int width, int height, PostEffectProcessor.FramebufferSet framebufferSet) {
        RenderSystem.assertOnRenderThreadOrInit();

        PostEffectProcessor effect = getOrCreatePostEffect(shader);
        if (effect == null) return false;
        setupShader(shader, effect, tickDelta);
        try {
            effect.render(frameGraphBuilder, width, height, framebufferSet);
            return true;
        } catch (Throwable t) {
            Sakura.LOGGER.error("Failed to render outline shader {}", shader, t);
            return false;
        }
    }

    public void setupShader(Shader shader, PostEffectProcessor effect, float tickDelta) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        if (shaders == null) return;
        if (effect == null) return;

        List<PostEffectPass> passes = ((IPostEffectProcessor) effect).getPasses();
        if (passes.isEmpty()) return;

        Color outlineColor = shaders.outlineColor.get();
        Color outlineColor1 = shaders.outlineColor1.get();
        Color outlineColor2 = shaders.outlineColor2.get();
        Color fillColor1 = shaders.fillColor1.get();
        Color fillColor2 = shaders.fillColor2.get();
        Color fillColor3 = shaders.fillColor3.get();

        if (shader == Shader.Gradient) {
            float alpha0 = shaders.glow.get() ? -1.0f : outlineColor.getAlpha() / 255.0f;
            float alpha1 = shaders.fillAlpha.get() / 255f;
            float alpha2 = shaders.alpha2.get() / 255f;
            int lineWidth = shaders.lineWidth.get();
            int oct = shaders.octaves.get();
            int quality = shaders.quality.get();
            float factor = shaders.factor.get().floatValue();
            float moreGradient = shaders.gradient.get().floatValue();
            float width = (float) mc.getWindow().getScaledWidth();
            float height = (float) mc.getWindow().getScaledHeight();

            for (PostEffectPass pass : passes) {
                ShaderProgram program = pass.getProgram();
                safeSet(program, "alpha0", alpha0);
                safeSet(program, "alpha1", alpha1);
                safeSet(program, "alpha2", alpha2);
                safeSet(program, "lineWidth", lineWidth);
                safeSet(program, "oct", oct);
                safeSet(program, "quality", quality);
                safeSet(program, "factor", factor);
                safeSet(program, "moreGradient", moreGradient);
                safeSet(program, "resolution", width, height);
                safeSet(program, "time", time);
            }
            time += 0.008f;
        } else if (shader == Shader.Smoke) {
            float alpha0 = shaders.glow.get() ? -1.0f : outlineColor.getAlpha() / 255.0f;
            float alpha1 = shaders.fillAlpha.get() / 255f;
            int lineWidth = shaders.lineWidth.get();
            int quality = shaders.quality.get();
            float width = (float) mc.getWindow().getScaledWidth();
            float height = (float) mc.getWindow().getScaledHeight();
            int oct = shaders.octaves.get();

            float or = outlineColor.getRed() / 255f;
            float og = outlineColor.getGreen() / 255f;
            float ob = outlineColor.getBlue() / 255f;
            float oa = outlineColor.getAlpha() / 255f;

            float o1r = outlineColor1.getRed() / 255f;
            float o1g = outlineColor1.getGreen() / 255f;
            float o1b = outlineColor1.getBlue() / 255f;

            float o2r = outlineColor2.getRed() / 255f;
            float o2g = outlineColor2.getGreen() / 255f;
            float o2b = outlineColor2.getBlue() / 255f;

            float fr = fillColor1.getRed() / 255f;
            float fg = fillColor1.getGreen() / 255f;
            float fb = fillColor1.getBlue() / 255f;
            float fa = fillColor1.getAlpha() / 255f;

            float f2r = fillColor2.getRed() / 255f;
            float f2g = fillColor2.getGreen() / 255f;
            float f2b = fillColor2.getBlue() / 255f;

            float f3r = fillColor3.getRed() / 255f;
            float f3g = fillColor3.getGreen() / 255f;
            float f3b = fillColor3.getBlue() / 255f;

            for (PostEffectPass pass : passes) {
                ShaderProgram program = pass.getProgram();
                safeSet(program, "alpha0", alpha0);
                safeSet(program, "alpha1", alpha1);
                safeSet(program, "lineWidth", lineWidth);
                safeSet(program, "quality", quality);
                safeSet(program, "first", or, og, ob, oa);
                safeSet(program, "second", o1r, o1g, o1b);
                safeSet(program, "third", o2r, o2g, o2b);
                safeSet(program, "ffirst", fr, fg, fb, fa);
                safeSet(program, "fsecond", f2r, f2g, f2b);
                safeSet(program, "fthird", f3r, f3g, f3b);
                safeSet(program, "oct", oct);
                safeSet(program, "resolution", width, height);
                safeSet(program, "time", time);
            }
            time += 0.008f;
        } else if (shader == Shader.Default) {
            float alpha0 = shaders.glow.get() ? -1.0f : outlineColor.getAlpha() / 255.0f;
            int lineWidth = shaders.lineWidth.get();
            int quality = shaders.quality.get();
            float fr = fillColor1.getRed() / 255f;
            float fg = fillColor1.getGreen() / 255f;
            float fb = fillColor1.getBlue() / 255f;
            float fa = fillColor1.getAlpha() / 255f;
            float or = outlineColor.getRed() / 255f;
            float og = outlineColor.getGreen() / 255f;
            float ob = outlineColor.getBlue() / 255f;
            float oa = outlineColor.getAlpha() / 255f;

            for (PostEffectPass pass : passes) {
                ShaderProgram program = pass.getProgram();
                safeSet(program, "alpha0", alpha0);
                safeSet(program, "lineWidth", lineWidth);
                safeSet(program, "quality", quality);
                safeSet(program, "color", fr, fg, fb, fa);
                safeSet(program, "outlinecolor", or, og, ob, oa);
            }
        } else if (shader == Shader.Snow) {
            float width = (float) mc.getWindow().getScaledWidth();
            float height = (float) mc.getWindow().getScaledHeight();
            int quality = shaders.quality.get();
            float fr = fillColor1.getRed() / 255f;
            float fg = fillColor1.getGreen() / 255f;
            float fb = fillColor1.getBlue() / 255f;
            float fa = fillColor1.getAlpha() / 255f;

            for (PostEffectPass pass : passes) {
                ShaderProgram program = pass.getProgram();
                safeSet(program, "color", fr, fg, fb, fa);
                safeSet(program, "quality", quality);
                safeSet(program, "resolution", width, height);
                safeSet(program, "time", time);
            }
            time += 0.008f;
        } else if (shader == Shader.Fade) {
            float alpha0 = shaders.glow.get() ? -1.0f : outlineColor.getAlpha() / 255.0f;
            float fillAlpha = shaders.fillAlpha.get() / 255f;
            int lineWidth = shaders.lineWidth.get();
            int quality = shaders.quality.get();
            float or = outlineColor.getRed() / 255f;
            float og = outlineColor.getGreen() / 255f;
            float ob = outlineColor.getBlue() / 255f;
            float oa = outlineColor.getAlpha() / 255f;
            float p1r = fillColor1.getRed() / 255f;
            float p1g = fillColor1.getGreen() / 255f;
            float p1b = fillColor1.getBlue() / 255f;
            float p1a = fillColor1.getAlpha() / 255f;
            float p2r = fillColor2.getRed() / 255f;
            float p2g = fillColor2.getGreen() / 255f;
            float p2b = fillColor2.getBlue() / 255f;
            float p2a = fillColor2.getAlpha() / 255f;
            float time = (System.currentTimeMillis() % 100000) / 1000f;

            for (PostEffectPass pass : passes) {
                ShaderProgram program = pass.getProgram();
                safeSet(program, "alpha0", alpha0);
                safeSet(program, "fillAlpha", fillAlpha);
                safeSet(program, "lineWidth", lineWidth);
                safeSet(program, "quality", quality);
                safeSet(program, "outlinecolor", or, og, ob, oa);
                safeSet(program, "primaryColor", p1r, p1g, p1b, p1a);
                safeSet(program, "secondaryColor", p2r, p2g, p2b, p2a);
                safeSet(program, "time", time);
            }
        }
    }

    private static void safeSet(ShaderProgram program, String uniform, float v) {
        try {
            var u = program.getUniform(uniform);
            if (u != null) u.set(v);
        } catch (Throwable ignored) {
        }
    }

    private static void safeSet(ShaderProgram program, String uniform, int v) {
        try {
            var u = program.getUniform(uniform);
            if (u != null) u.set(v);
        } catch (Throwable ignored) {
        }
    }

    private static void safeSet(ShaderProgram program, String uniform, float x, float y) {
        try {
            var u = program.getUniform(uniform);
            if (u != null) u.set(x, y);
        } catch (Throwable ignored) {
        }
    }

    private static void safeSet(ShaderProgram program, String uniform, float x, float y, float z) {
        try {
            var u = program.getUniform(uniform);
            if (u != null) u.set(x, y, z);
        } catch (Throwable ignored) {
        }
    }

    private static void safeSet(ShaderProgram program, String uniform, float x, float y, float z, float w) {
        try {
            var u = program.getUniform(uniform);
            if (u != null) u.set(x, y, z, w);
        } catch (Throwable ignored) {
        }
    }

    public void reloadShaders() {
        for (PostEffectProcessor effect : postEffects.values()) {
            try {
                effect.getClass().getMethod("close").invoke(effect);
            } catch (Throwable ignored) {
            }
        }
        postEffects.clear();
    }

    public static class SakuraBuffer extends Framebuffer {
        public SakuraBuffer(int width, int height) {
            super(false);
            RenderSystem.assertOnRenderThreadOrInit();
            resize(width, height);
            setClearColor(0f, 0f, 0f, 0f);
        }
    }

    private void ensureShaderBuffer(Framebuffer mainBuffer) {
        if (shaderBuffer == null) {
            shaderBuffer = new SakuraBuffer(mainBuffer.textureWidth, mainBuffer.textureHeight);
            return;
        }
        if (shaderBuffer.textureWidth != mainBuffer.textureWidth || shaderBuffer.textureHeight != mainBuffer.textureHeight) {
            shaderBuffer.resize(mainBuffer.textureWidth, mainBuffer.textureHeight);
            shaderBuffer.setClearColor(0f, 0f, 0f, 0f);
        }
    }

    private PostEffectProcessor getOrCreatePostEffect(@NotNull Shader mode) {
        return postEffects.computeIfAbsent(mode, s -> {
            try {
                return mc.getShaderLoader().loadPostEffect(getPostEffectId(s), DefaultFramebufferSet.MAIN_ONLY);
            } catch (Throwable t) {
                Sakura.LOGGER.error("Failed to load post effect {}", getPostEffectId(s), t);
                return null;
            }
        });
    }

    private Identifier getPostEffectId(@NotNull Shader mode) {
        return switch (mode) {
            case Smoke -> Identifier.of("sakura", "smoke");
            case Gradient -> Identifier.of("sakura", "gradient");
            case Snow -> Identifier.of("sakura", "snow");
            case Fade -> Identifier.of("sakura", "fade");
            default -> Identifier.of("sakura", "outline");
        };
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
}
