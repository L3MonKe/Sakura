package dev.sakura.client.shaders;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import static dev.sakura.client.Sakura.mc;

public class SplashShader {
    private static SplashShader INSTANCE;

    private static final Identifier FRAGMENT_SHADER = Identifier.of("sakura", "core/splash");
    private static final int UNIFORMS_SIZE = new Std140SizeCalculator().putVec4().putVec4().get();
    private static final float TRANSITION_DURATION = 2.0f; // 2s 过渡

    private RenderPipeline pipelineOpaque;
    private RenderPipeline pipelineBlend;
    private MappableRingBuffer uniforms;
    private float timeSeconds;
    private float currentProgress;

    private boolean transitionStarted = false;
    private float accumulatedTransitionTime = 0f;
    private long lastFrameTime = System.nanoTime();

    public static SplashShader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SplashShader();
        }
        return INSTANCE;
    }

    private SplashShader() {
    }

    public void init() {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura SplashUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        if (this.pipelineOpaque == null) {
            this.pipelineOpaque = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/splash_opaque"))
                    .withVertexShader(Identifier.of("sakura", "core/screen_triangle"))
                    .withFragmentShader(FRAGMENT_SHADER)
                    .withUniform("SplashUniforms", UniformType.UNIFORM_BUFFER)
                    .withoutBlend()
                    .withCull(false)
                    .build();
        }
        if (this.pipelineBlend == null) {
            this.pipelineBlend = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/splash_blend"))
                    .withVertexShader(Identifier.of("sakura", "core/screen_triangle"))
                    .withFragmentShader(FRAGMENT_SHADER)
                    .withUniform("SplashUniforms", UniformType.UNIFORM_BUFFER)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
    }

    public void render(int width, int height) {
        render(width, height, this.currentProgress, 0f, 1.0f);
    }

    public void render(int width, int height, float progress) {
        render(width, height, progress, 0f, 1.0f);
    }

    public void render(int width, int height, float progress, float fadeOut, float zoom) {
        this.init();
        if (this.uniforms == null || this.pipelineOpaque == null || this.pipelineBlend == null) {
            return;
        }

        long currentTime = System.nanoTime();
        float delta = (currentTime - this.lastFrameTime) / 1_000_000_000f;
        this.lastFrameTime = currentTime;
        this.timeSeconds += delta;
        if (this.transitionStarted && this.accumulatedTransitionTime < TRANSITION_DURATION) {
            this.accumulatedTransitionTime = Math.min(TRANSITION_DURATION, this.accumulatedTransitionTime + delta);
        }
        this.currentProgress = progress;

        Framebuffer framebuffer = mc.getFramebuffer();
        float scaleFactor = (float) mc.getWindow().getScaleFactor();
        float pxWidth = width * scaleFactor;
        float pxHeight = height * scaleFactor;

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec2(pxWidth, pxHeight);
            builder.putFloat(this.timeSeconds);
            builder.putFloat(progress);
            builder.putFloat(fadeOut);
            builder.putFloat(zoom);
            builder.putFloat(0.0f);
            builder.putFloat(0.0f);
        }

        RenderPipeline pipeline = zoom > 1.0f ? this.pipelineBlend : this.pipelineOpaque;
        try (RenderPass renderPass = encoder.createRenderPass(
                () -> "Sakura Splash",
                framebuffer.getColorAttachmentView(),
                OptionalInt.empty(),
                framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null,
                OptionalDouble.empty()
        )) {
            renderPass.setPipeline(pipeline);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("SplashUniforms", this.uniforms.getBlocking());
            renderPass.draw(0, 3);
        }
        this.uniforms.rotate();
    }

    public void startTransition() {
        this.transitionStarted = true;
        this.accumulatedTransitionTime = 0f;
    }

    public boolean isTransitionStarted() {
        return transitionStarted;
    }

    public float getTransitionProgress() {
        if (!transitionStarted) return 0f;
        return Math.min(1f, accumulatedTransitionTime / TRANSITION_DURATION);
    }

    public boolean isTransitionComplete() {
        return transitionStarted && accumulatedTransitionTime >= TRANSITION_DURATION;
    }

    public float getAccumulatedTime() {
        return timeSeconds;
    }

    public void reset() {
        this.transitionStarted = false;
        this.timeSeconds = 0f;
        this.accumulatedTransitionTime = 0f;
        this.lastFrameTime = System.nanoTime();
    }

    public void cleanup() {
        if (this.uniforms != null) {
            this.uniforms.close();
            this.uniforms = null;
        }
        INSTANCE = null;
    }

}
