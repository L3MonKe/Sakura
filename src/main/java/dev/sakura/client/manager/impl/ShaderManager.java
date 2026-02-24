package dev.sakura.client.manager.impl;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.ChestESP;
import dev.sakura.client.module.impl.render.GlowESP;
import dev.sakura.client.module.impl.render.Shaders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.util.Identifier;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class ShaderManager {
    private static final Identifier SHADER_SCREENQUAD = Identifier.of("sakura", "post/screenquad");
    private static final Identifier OUTLINE_FSH = Identifier.of("sakura", "post/outline");
    private static final Identifier GLOW_FSH = Identifier.of("sakura", "post/glow");
    private static final Identifier MASK_FSH = Identifier.of("sakura", "post/mask");
    private static final Identifier GRADIENT_FSH = Identifier.of("sakura", "post/gradient");
    private static final Identifier SMOKE_FSH = Identifier.of("sakura", "post/smoke");
    private static final Identifier SNOW_FSH = Identifier.of("sakura", "post/snow");
    private static final Identifier FADE_FSH = Identifier.of("sakura", "post/fade");

    private static final int CLEAR_COLOR_TRANSPARENT = 0x00000000;
    private static final BlendFunction REPLACE_BLEND = new BlendFunction(SourceFactor.ONE, DestFactor.ZERO);
    private static final BlendFunction MASK_BLEND = new BlendFunction(SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_ALPHA);

    private final List<RenderTask> tasks = new ArrayList<>();

    private float time;

    private SimpleFramebuffer handInput;
    private SimpleFramebuffer handOutput;
    private SimpleFramebuffer genericOutput;

    private RenderPipeline pipelineOutline;
    private RenderPipeline pipelineGlow;
    private RenderPipeline pipelineMask;
    private RenderPipeline pipelineGradient;
    private RenderPipeline pipelineSmoke;
    private RenderPipeline pipelineSnow;
    private RenderPipeline pipelineFade;

    private ShaderParamsBuffer shaderParamsBuffer;

    public void renderShader(Runnable runnable, Shader mode) {
        if (runnable == null || mode == null) return;
        tasks.add(new RenderTask(runnable, mode));
    }

    public void renderShaders(float tickDelta, boolean renderHands) {
        if (!renderHands || tasks.isEmpty()) {
            tasks.clear();
            return;
        }

        ensurePipelines();
        ensureHandFramebuffers();

        for (RenderTask task : tasks) {
            applyHandShader(task.task, task.shader, tickDelta);
        }
        tasks.clear();
    }

    public void renderEntityOutlineShader(Framebuffer entityOutlineFramebuffer, Shader mode, float tickDelta) {
        if (entityOutlineFramebuffer == null || mode == null) return;

        ensurePipelines();
        ensureGenericOutput(entityOutlineFramebuffer.textureWidth, entityOutlineFramebuffer.textureHeight);

        GpuTexture inColor = entityOutlineFramebuffer.getColorAttachment();
        GpuTextureView inColorView = entityOutlineFramebuffer.getColorAttachmentView();
        GpuTexture outColor = genericOutput.getColorAttachment();
        GpuTextureView outColorView = genericOutput.getColorAttachmentView();

        if (inColor == null || inColorView == null || outColor == null || outColorView == null) return;

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        encoder.clearColorTexture(outColor, CLEAR_COLOR_TRANSPARENT);
        if (genericOutput.getDepthAttachment() != null) {
            encoder.clearDepthTexture(genericOutput.getDepthAttachment(), 1.0);
        }

        // Check if GlowESP is enabled to use the 2-pass blur
        boolean useGlowLogic = mode == Shader.Glow && Sakura.MODULES.getModule(GlowESP.class).isEnabled();

        ChestESP chestESP = Sakura.MODULES.getModule(ChestESP.class);
        boolean useChestGlow = mode == Shader.Glow && chestESP.isEnabled() && chestESP.isGlowEnabled();

        if (useGlowLogic || useChestGlow) {
            ensureHandFramebuffers();
            GpuTexture handInColor = handInput.getColorAttachment();
            GpuTextureView handInColorView = handInput.getColorAttachmentView();

            if (handInColor != null && handInColorView != null) {
                encoder.clearColorTexture(handInColor, CLEAR_COLOR_TRANSPARENT);

                // Pass 1: Horizontal
                renderPostPass(encoder, inColorView, handInColorView, mode, tickDelta, entityOutlineFramebuffer.textureWidth, entityOutlineFramebuffer.textureHeight, false, new Vector4f(1, 0, 0, 0));

                // Pass 2: Vertical
                renderPostPass(encoder, handInColorView, outColorView, mode, tickDelta, entityOutlineFramebuffer.textureWidth, entityOutlineFramebuffer.textureHeight, false, new Vector4f(0, 1, 0, 0));

                // Pass 3: Masking (Subtract Entity)
                renderPostPass(encoder, inColorView, outColorView, Shader.Mask, tickDelta, entityOutlineFramebuffer.textureWidth, entityOutlineFramebuffer.textureHeight, false, new Vector4f(0, 0, 0, 0));
            }
        } else {
            renderPostPass(encoder, inColorView, outColorView, mode, tickDelta, entityOutlineFramebuffer.textureWidth, entityOutlineFramebuffer.textureHeight, false, new Vector4f(0, 0, 0, 0));
        }

        GpuTextureView mainColorView = mc.getFramebuffer().getColorAttachmentView();
        if (mainColorView != null) {
            genericOutput.drawBlit(mainColorView);
        }
    }

    private void applyHandShader(Runnable runnable, Shader mode, float tickDelta) {
        GpuTexture handInColor = handInput.getColorAttachment();
        GpuTextureView handInColorView = handInput.getColorAttachmentView();
        GpuTexture handOutColor = handOutput.getColorAttachment();
        GpuTextureView handOutColorView = handOutput.getColorAttachmentView();
        if (handInColor == null || handInColorView == null || handOutColor == null || handOutColorView == null) return;

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        encoder.clearColorTexture(handInColor, CLEAR_COLOR_TRANSPARENT);
        encoder.clearColorTexture(handOutColor, CLEAR_COLOR_TRANSPARENT);

        GpuTextureView prevColor = RenderSystem.outputColorTextureOverride;
        RenderSystem.outputColorTextureOverride = handInColorView;

        try {
            runnable.run();
        } finally {
            RenderSystem.outputColorTextureOverride = prevColor;
        }

        if (mode == Shader.Glow) {
            ensureGenericOutput(handInput.textureWidth, handInput.textureHeight);
            GpuTextureView tempView = genericOutput.getColorAttachmentView();
            if (tempView != null) {
                encoder.clearColorTexture(genericOutput.getColorAttachment(), CLEAR_COLOR_TRANSPARENT);

                renderPostPass(encoder, handInColorView, tempView, mode, tickDelta, handInput.textureWidth, handInput.textureHeight, true, new Vector4f(1, 0, 0, 0));
                renderPostPass(encoder, tempView, handOutColorView, mode, tickDelta, handInput.textureWidth, handInput.textureHeight, true, new Vector4f(0, 1, 0, 0));
            }
        } else {
            renderPostPass(encoder, handInColorView, handOutColorView, mode, tickDelta, handInput.textureWidth, handInput.textureHeight, true, new Vector4f(0, 0, 0, 0));
        }

        GpuTextureView mainColorView = mc.getFramebuffer().getColorAttachmentView();
        if (mainColorView != null) {
            handOutput.drawBlit(mainColorView);
        }
    }

    private void renderPostPass(CommandEncoder encoder, GpuTextureView inColorView, GpuTextureView outColorView, Shader mode, float tickDelta, int inW, int inH, boolean isHands, Vector4f direction) {
        Shaders shaders = Sakura.MODULES.getModule(Shaders.class);
        GlowESP glowESP = Sakura.MODULES.getModule(GlowESP.class);

        RenderPipeline pipeline = getPipeline(mode);
        if (pipeline == null) return;

        ShaderParams params;
        if (mode == Shader.Glow && glowESP.isEnabled()) {
            params = ShaderParams.fromGlow(glowESP, tickDelta, inW, inH, time, isHands, direction);
        } else if (mode == Shader.Glow && Sakura.MODULES.getModule(ChestESP.class).isEnabled() && Sakura.MODULES.getModule(ChestESP.class).isGlowEnabled()) {
            params = ShaderParams.fromChestGlow(Sakura.MODULES.getModule(ChestESP.class), tickDelta, inW, inH, time, isHands, direction);
        } else {
            params = ShaderParams.from(shaders, mode, tickDelta, inW, inH, time, isHands, direction);
        }

        time = params.nextTime;
        GpuTextureView depth = null;
        GpuBuffer paramsBuffer = shaderParamsBuffer.write(encoder, params);
        try (RenderPass pass = encoder.createRenderPass(() -> "Sakura Shader " + mode.name(), outColorView, java.util.OptionalInt.empty(), depth, java.util.OptionalDouble.empty())) {
            pass.setPipeline(pipeline);
            RenderSystem.bindDefaultUniforms(pass);
            pass.setUniform("ShaderParams", paramsBuffer);
            pass.bindTexture("DiffuseSampler", inColorView, RenderSystem.getSamplerCache().get(FilterMode.NEAREST));
            pass.draw(0, 3);
        }
    }

    private RenderPipeline getPipeline(Shader mode) {
        return switch (mode) {
            case Gradient -> pipelineGradient;
            case Smoke -> pipelineSmoke;
            case Snow -> pipelineSnow;
            case Fade -> pipelineFade;
            case Glow -> pipelineGlow;
            case Mask -> pipelineMask;
            default -> pipelineOutline;
        };
    }

    private void ensurePipelines() {
        if (pipelineOutline != null) return;

        pipelineOutline = createPipeline("pipeline/sakura_shader_outline", OUTLINE_FSH);
        pipelineGlow = createPipeline("pipeline/sakura_shader_glow", GLOW_FSH);
        pipelineMask = createPipeline("pipeline/sakura_shader_mask", MASK_FSH, MASK_BLEND);
        pipelineGradient = createPipeline("pipeline/sakura_shader_gradient", GRADIENT_FSH);
        pipelineSmoke = createPipeline("pipeline/sakura_shader_smoke", SMOKE_FSH);
        pipelineSnow = createPipeline("pipeline/sakura_shader_snow", SNOW_FSH);
        pipelineFade = createPipeline("pipeline/sakura_shader_fade", FADE_FSH);

        shaderParamsBuffer = new ShaderParamsBuffer();
    }

    private RenderPipeline createPipeline(String location, Identifier fragmentShader) {
        return createPipeline(location, fragmentShader, REPLACE_BLEND);
    }

    private RenderPipeline createPipeline(String location, Identifier fragmentShader, BlendFunction blend) {
        return RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                .withLocation(location)
                .withVertexShader(SHADER_SCREENQUAD)
                .withFragmentShader(fragmentShader)
                .withSampler("DiffuseSampler")
                .withUniform("ShaderParams", net.minecraft.client.gl.UniformType.UNIFORM_BUFFER)
                .withBlend(blend)
                .build());
    }

    private void ensureHandFramebuffers() {
        int w = mc.getWindow().getFramebufferWidth();
        int h = mc.getWindow().getFramebufferHeight();
        if (handInput == null || handInput.textureWidth != w || handInput.textureHeight != h) {
            if (handInput != null) handInput.delete();
            if (handOutput != null) handOutput.delete();
            handInput = new SimpleFramebuffer("Sakura Hands In", w, h, false);
            handOutput = new SimpleFramebuffer("Sakura Hands Out", w, h, false);
        }
    }

    private void ensureGenericOutput(int w, int h) {
        if (genericOutput == null || genericOutput.textureWidth != w || genericOutput.textureHeight != h) {
            if (genericOutput != null) genericOutput.delete();
            genericOutput = new SimpleFramebuffer("Sakura Shader Out", w, h, false);
        }
    }

    public void resetTime() {
        time = 0.0f;
    }

    private record RenderTask(Runnable task, Shader shader) {
    }

    public enum Shader {
        Default,
        Smoke,
        Gradient,
        Snow,
        Fade,
        Glow,
        Mask
    }

    private static final class ShaderParams {
        final Vector4f inSize;
        final Vector4f resolution;
        final Vector4f color;
        final Vector4f outlineColor;
        final Vector4f outlineColor1;
        final Vector4f outlineColor2;
        final Vector4f fillColor1;
        final Vector4f fillColor2;
        final Vector4f fillColor3;
        final Vector4f primaryColor;
        final Vector4f secondaryColor;
        final Vector4f params1;
        final Vector4f params2;
        final Vector4f params3;
        final float nextTime;

        private ShaderParams(Vector4f inSize, Vector4f resolution, Vector4f color, Vector4f outlineColor, Vector4f outlineColor1, Vector4f outlineColor2,
                             Vector4f fillColor1, Vector4f fillColor2, Vector4f fillColor3, Vector4f primaryColor, Vector4f secondaryColor,
                             Vector4f params1, Vector4f params2, Vector4f params3, float nextTime) {
            this.inSize = inSize;
            this.resolution = resolution;
            this.color = color;
            this.outlineColor = outlineColor;
            this.outlineColor1 = outlineColor1;
            this.outlineColor2 = outlineColor2;
            this.fillColor1 = fillColor1;
            this.fillColor2 = fillColor2;
            this.fillColor3 = fillColor3;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
            this.params1 = params1;
            this.params2 = params2;
            this.params3 = params3;
            this.nextTime = nextTime;
        }

        static ShaderParams fromChestGlow(ChestESP c, float tickDelta, int inW, int inH, float time, boolean isHands, Vector4f direction) {
            int scaledW = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int scaledH = MinecraftClient.getInstance().getWindow().getScaledHeight();

            Color color = c.glowColor.get();
            Vector4f colorV = toVec4(color);
            Vector4f outlineV = colorV;

            float exposure = c.glowExposure.get().floatValue();
            float radius = c.glowRadius.get().floatValue();

            Vector4f zero = new Vector4f(0, 0, 0, 0);
            Vector4f params1 = new Vector4f(-1.0f, 0.0f, 0.0f, 0.0f);
            Vector4f params2 = new Vector4f(time, exposure, 0.0f, 3.0f);
            Vector4f params3 = new Vector4f(radius, 10.0f, direction.x, direction.y);

            Vector4f inSize = new Vector4f(inW, inH, 0.0f, 0.0f);
            Vector4f res = new Vector4f(scaledW, scaledH, 0.0f, 0.0f);

            return new ShaderParams(inSize, res, colorV, outlineV, zero, zero, colorV, zero, zero, colorV, zero, params1, params2, params3, time);
        }

        static ShaderParams fromGlow(GlowESP g, float tickDelta, int inW, int inH, float time, boolean isHands, Vector4f direction) {
            int scaledW = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int scaledH = MinecraftClient.getInstance().getWindow().getScaledHeight();

            Color color = g.color.get();
            Vector4f colorV = toVec4(color);
            // Reusing OutlineColor for Glow Color
            Vector4f outlineV = colorV;

            // Glow specific params
            float exposure = g.exposure.get().floatValue();
            float radius = g.radius.get().floatValue();

            // Dummy values for others
            Vector4f zero = new Vector4f(0, 0, 0, 0);
            Vector4f params1 = new Vector4f(-1.0f, 0.0f, 0.0f, 0.0f); // alpha0 = -1 for glow mode in shader logic? No, let's check.

            Vector4f params2 = new Vector4f(time, exposure, 0.0f, 3.0f); // Quality 3
            Vector4f params3 = new Vector4f(radius, 10.0f, direction.x, direction.y); // Octaves 10

            Vector4f inSize = new Vector4f(inW, inH, 0.0f, 0.0f);
            Vector4f res = new Vector4f(scaledW, scaledH, 0.0f, 0.0f);

            return new ShaderParams(inSize, res, colorV, outlineV, zero, zero, colorV, zero, zero, colorV, zero, params1, params2, params3, time);
        }

        static ShaderParams from(Shaders s, Shader shader, float tickDelta, int inW, int inH, float time, boolean isHands, Vector4f direction) {
            int scaledW = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int scaledH = MinecraftClient.getInstance().getWindow().getScaledHeight();

            Color outline = s.outlineColor.get();
            Color outline1 = s.outlineColor1.get();
            Color outline2 = s.outlineColor2.get();
            Color fill1 = s.fillColor1.get();
            Color fill2 = s.fillColor2.get();
            Color fill3 = s.fillColor3.get();

            Vector4f outlineV = toVec4(outline);
            Vector4f outline1V = toVec4(outline1);
            Vector4f outline2V = toVec4(outline2);
            Vector4f fill1V = toVec4(fill1);
            Vector4f fill2V = toVec4(fill2);
            Vector4f fill3V = toVec4(fill3);

            float alpha0 = s.glow.get() ? -1.0f : outline.getAlpha() / 255.0f;
            float alpha1 = s.fillAlpha.get() / 255.0f;
            float alpha2 = s.alpha2.get() / 255.0f;
            float fillAlpha = s.fillAlpha.get() / 255.0f;
            float factor = s.factor.get().floatValue();
            float moreGradient = s.gradient.get().floatValue();

            float quality = s.quality.get();
            float lineWidth = s.lineWidth.get();
            float oct = s.octaves.get();

            Vector4f primaryV = toVec4(fill1);
            Vector4f secondaryV = toVec4(fill2);

            Vector4f params1 = new Vector4f(alpha0, alpha1, alpha2, fillAlpha);
            float shaderTime = shader == Shader.Fade ? (System.currentTimeMillis() % 100000L) / 1000.0f : time;
            Vector4f params2 = new Vector4f(shaderTime, factor, moreGradient, quality);
            Vector4f params3 = new Vector4f(lineWidth, oct, direction.x, direction.y);

            float nextTime = time;
            if (shader == Shader.Gradient || shader == Shader.Smoke || shader == Shader.Snow) {
                nextTime += 0.008f;
            }

            Vector4f inSize = new Vector4f(inW, inH, 0.0f, 0.0f);
            Vector4f res = new Vector4f(scaledW, scaledH, 0.0f, 0.0f);

            return new ShaderParams(inSize, res, fill1V, outlineV, outline1V, outline2V, fill1V, fill2V, fill3V, primaryV, secondaryV, params1, params2, params3, nextTime);
        }
    }

    private static Vector4f toVec4(Color color) {
        return new Vector4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    private static final class ShaderParamsBuffer {
        private static final int UBO_USAGE = GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST;
        private static final Std140SizeCalculator SIZE_CALCULATOR = new Std140SizeCalculator()
                .putVec4()  // InSize
                .putVec4()  // Resolution
                .putVec4()  // Color
                .putVec4()  // OutlineColor
                .putVec4()  // OutlineColor1
                .putVec4()  // OutlineColor2
                .putVec4()  // FillColor1
                .putVec4()  // FillColor2
                .putVec4()  // FillColor3
                .putVec4()  // PrimaryColor
                .putVec4()  // SecondaryColor
                .putVec4()  // Params1
                .putVec4()  // Params2
                .putVec4(); // Params3

        private final int byteSize = SIZE_CALCULATOR.get();
        private final GpuBuffer buffer;

        private ShaderParamsBuffer() {
            RenderSystem.assertOnRenderThread();
            buffer = RenderSystem.getDevice().createBuffer(() -> "Sakura ShaderParams", UBO_USAGE, byteSize);
        }

        private GpuBuffer write(CommandEncoder encoder, ShaderParams params) {
            RenderSystem.assertOnRenderThread();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                Std140Builder builder = Std140Builder.onStack(stack, byteSize);
                builder.putVec4(params.inSize);
                builder.putVec4(params.resolution);
                builder.putVec4(params.color);
                builder.putVec4(params.outlineColor);
                builder.putVec4(params.outlineColor1);
                builder.putVec4(params.outlineColor2);
                builder.putVec4(params.fillColor1);
                builder.putVec4(params.fillColor2);
                builder.putVec4(params.fillColor3);
                builder.putVec4(params.primaryColor);
                builder.putVec4(params.secondaryColor);
                builder.putVec4(params.params1);
                builder.putVec4(params.params2);
                builder.putVec4(params.params3);
                ByteBuffer data = builder.get();
                data.rewind();

                encoder.writeToBuffer(buffer.slice(), data);
                return buffer;
            }
        }
    }
}
