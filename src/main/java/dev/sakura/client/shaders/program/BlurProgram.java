package dev.sakura.client.shaders.program;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import net.minecraft.client.gl.*;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static dev.sakura.client.Sakura.mc;

public class BlurProgram {
    private static final Identifier VERTEX_SHADER = Identifier.of("sakura", "core/screen_triangle");
    private static final Identifier FRAGMENT_SHADER = Identifier.of("sakura", "core/blur");
    private static final int UNIFORMS_SIZE = new Std140SizeCalculator().putVec4().putVec4().putVec4().putVec4().get();

    private RenderPipeline pipeline;
    private MappableRingBuffer uniforms;
    private Framebuffer input;

    private void ensureProgram() {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura BlurUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        if (this.pipeline == null) {
            this.pipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/blur"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(FRAGMENT_SHADER)
                    .withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER)
                    .withSampler("InputSampler")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
    }

    private void ensureInputFramebuffer(int width, int height) {
        if (this.input == null) {
            this.input = new SimpleFramebuffer("Sakura Blur Input", width, height, false);
            return;
        }
        if (this.input.textureWidth != width || this.input.textureHeight != height) {
            this.input.resize(width, height);
        }
    }

    public void renderRoundedBlur(float x, float y, float width, float height, float radius, Color color, float blurStrength, float blurOpacity) {
        this.ensureProgram();

        if (this.pipeline == null || this.uniforms == null) {
            return;
        }

        Framebuffer framebuffer = mc.getFramebuffer();
        if (framebuffer.getColorAttachment() == null || framebuffer.getColorAttachmentView() == null) {
            return;
        }

        int fbWidth = mc.getWindow().getFramebufferWidth();
        int fbHeight = mc.getWindow().getFramebufferHeight();
        this.ensureInputFramebuffer(fbWidth, fbHeight);
        if (this.input.getColorAttachment() == null || this.input.getColorAttachmentView() == null) {
            return;
        }

        float scale = (float) mc.getWindow().getScaleFactor();
        float pxX = x * scale;
        float pxY = (-y + mc.getWindow().getScaledHeight() - height) * scale;
        float pxW = width * scale;
        float pxH = height * scale;

        float rPx = Math.max(0.0f, radius * scale);
        float quality = Math.max(0.0f, blurStrength);
        float alpha = Math.max(0.0f, Math.min(1.0f, blurOpacity));

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        encoder.copyTextureToTexture(framebuffer.getColorAttachment(), this.input.getColorAttachment(), 0, 0, 0, 0, 0, framebuffer.textureWidth, framebuffer.textureHeight);

        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec4(framebuffer.textureWidth, framebuffer.textureHeight, quality, alpha);
            builder.putVec4(pxW, pxH, pxX, pxY);
            builder.putVec4(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
            builder.putVec4(rPx, 0.0f, 0.0f, 0.0f);
        }

        int paddingPx = (int) Math.ceil(10.0f * scale);
        int scissorX = Math.max(0, (int) Math.floor(pxX) - paddingPx);
        int scissorY = Math.max(0, (int) Math.floor(pxY) - paddingPx);
        int scissorW = Math.min(fbWidth - scissorX, (int) Math.ceil(pxW) + paddingPx * 2);
        int scissorH = Math.min(fbHeight - scissorY, (int) Math.ceil(pxH) + paddingPx * 2);

        try (RenderPass renderPass = encoder.createRenderPass(() -> "Sakura Blur", framebuffer.getColorAttachmentView(), OptionalInt.empty(), framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null, OptionalDouble.empty())) {
            renderPass.setPipeline(this.pipeline);
            renderPass.enableScissor(scissorX, scissorY, Math.max(0, scissorW), Math.max(0, scissorH));
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("BlurUniforms", this.uniforms.getBlocking());
            renderPass.bindTexture("InputSampler", this.input.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.draw(0, 3);
        }
        this.uniforms.rotate();
    }
}
