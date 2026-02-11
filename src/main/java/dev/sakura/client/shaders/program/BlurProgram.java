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
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.gl.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static dev.sakura.client.Sakura.mc;

public class BlurProgram {
    private static final Identifier VERTEX_SHADER = Identifier.of("sakura", "core/screen_triangle");
    private static final Identifier FRAGMENT_SHADER = Identifier.of("sakura", "core/blur");
    private static final Identifier MASK_FRAGMENT_SHADER = Identifier.of("sakura", "core/blur_mask");
    private static final int MAX_SEGMENTS = 64;
    private static final int UNIFORMS_SIZE = getUniformSize();

    private RenderPipeline blurPipeline;
    private RenderPipeline maskPipeline;
    private MappableRingBuffer uniforms;
    private Framebuffer input;
    private Framebuffer mask;

    private static int getUniformSize() {
        Std140SizeCalculator calc = new Std140SizeCalculator().putVec4().putVec4().putVec4().putVec4().putVec4();
        for (int i = 0; i < MAX_SEGMENTS; i++) {
            calc.putVec4();
        }
        for (int i = 0; i < MAX_SEGMENTS; i++) {
            calc.putVec4();
        }
        return calc.get();
    }

    private void ensureProgram() {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura BlurUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        if (this.blurPipeline == null) {
            this.blurPipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/blur"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(FRAGMENT_SHADER)
                    .withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER)
                    .withSampler("InputSampler")
                    .withSampler("MaskSampler")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
        if (this.maskPipeline == null) {
            this.maskPipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/blur_mask"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(MASK_FRAGMENT_SHADER)
                    .withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
    }

    private void ensureInputBuffer(int width, int height) {
        if (this.input == null) {
            this.input = new SimpleFramebuffer("Sakura Blur Input", width, height, false);
            return;
        }
        if (this.input.textureWidth != width || this.input.textureHeight != height) {
            this.input.resize(width, height);
        }
    }

    private void ensureMaskBuffer(int width, int height) {
        if (this.mask == null) {
            this.mask = new SimpleFramebuffer("Sakura Blur Mask", width, height, false);
            return;
        }
        if (this.mask.textureWidth != width || this.mask.textureHeight != height) {
            this.mask.resize(width, height);
        }
    }

    public void render(float x, float y, float width, float height, float radius, Color color, float blurStrength, float blurOpacity) {
        this.renderWithSegments(x, y, width, height, radius, color, blurStrength, blurOpacity, null, null, 0);
    }

    public void renderWithSegments(float x, float y, float width, float height, float radius, Color color, float blurStrength, float blurOpacity, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        this.ensureProgram();

        if (this.blurPipeline == null || this.maskPipeline == null || this.uniforms == null) {
            return;
        }

        Framebuffer framebuffer = mc.getFramebuffer();
        if (framebuffer.getColorAttachment() == null || framebuffer.getColorAttachmentView() == null) {
            return;
        }

        int fbWidth = mc.getWindow().getFramebufferWidth();
        int fbHeight = mc.getWindow().getFramebufferHeight();
        this.ensureInputBuffer(fbWidth, fbHeight);
        this.ensureMaskBuffer(fbWidth, fbHeight);
        if (this.input.getColorAttachment() == null || this.input.getColorAttachmentView() == null) {
            return;
        }
        if (this.mask.getColorAttachment() == null || this.mask.getColorAttachmentView() == null) {
            return;
        }

        this.renderInternal(framebuffer, x, y, width, height, radius, color, blurStrength, blurOpacity, segmentRects, segmentRadii, segmentCount);
    }

    private void renderInternal(Framebuffer framebuffer, float x, float y, float width, float height, float radius, Color color, float blurStrength, float blurOpacity, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        int fbWidth = mc.getWindow().getFramebufferWidth();
        int fbHeight = mc.getWindow().getFramebufferHeight();

        float scale = (float) mc.getWindow().getScaleFactor();
        float scaledHeight = mc.getWindow().getScaledHeight();
        float pxX = x * scale;
        float pxY = (-y + mc.getWindow().getScaledHeight() - height) * scale;
        float pxW = width * scale;
        float pxH = height * scale;

        float rPx = Math.max(0.0f, radius * scale);
        float quality = Math.max(0.0f, blurStrength);
        float alpha = Math.max(0.0f, Math.min(1.0f, blurOpacity));

        int count = Math.max(0, Math.min(MAX_SEGMENTS, segmentCount));

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        encoder.copyTextureToTexture(framebuffer.getColorAttachment(), this.input.getColorAttachment(), 0, 0, 0, 0, 0, framebuffer.textureWidth, framebuffer.textureHeight);
        GpuTexture maskColor = this.mask.getColorAttachment();
        if (maskColor != null) {
            encoder.clearColorTexture(maskColor, count > 0 ? ColorHelper.getArgb(0, 0, 0, 0) : ColorHelper.getArgb(255, 255, 255, 255));
        }

        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec4(framebuffer.textureWidth, framebuffer.textureHeight, quality, alpha);
            builder.putVec4(pxW, pxH, pxX, pxY);
            builder.putVec4(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
            builder.putVec4(rPx, 0.0f, 0.0f, 0.0f);
            builder.putVec4((float) count, 0.0f, 0.0f, 0.0f);

            for (int i = 0; i < MAX_SEGMENTS; i++) {
                if (segmentRects != null && i < count) {
                    int base = i * 4;
                    float segX = segmentRects[base];
                    float segY = segmentRects[base + 1];
                    float segW = segmentRects[base + 2];
                    float segH = segmentRects[base + 3];

                    float segPxX = segX * scale;
                    float segPxY = (scaledHeight - (segY + segH)) * scale;
                    float segPxW = segW * scale;
                    float segPxH = segH * scale;
                    builder.putVec4(segPxX, segPxY, segPxW, segPxH);
                } else {
                    builder.putVec4(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }

            for (int i = 0; i < MAX_SEGMENTS; i++) {
                if (segmentRadii != null && i < count) {
                    float r = Math.max(0.0f, segmentRadii[i] * scale);
                    builder.putVec4(r, 0.0f, 0.0f, 0.0f);
                } else {
                    builder.putVec4(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }
        }

        if (count > 0) {
            try (RenderPass maskPass = encoder.createRenderPass(() -> "Sakura Blur Mask", this.mask.getColorAttachmentView(), OptionalInt.empty(), null, OptionalDouble.empty())) {
                maskPass.setPipeline(this.maskPipeline);
                RenderSystem.bindDefaultUniforms(maskPass);
                maskPass.setUniform("BlurUniforms", this.uniforms.getBlocking());
                maskPass.draw(0, 3);
            }
        }

        int paddingPx = (int) Math.ceil(10.0f * scale);
        int scissorX = Math.max(0, (int) Math.floor(pxX) - paddingPx);
        int scissorY = Math.max(0, (int) Math.floor(pxY) - paddingPx);
        int scissorW = Math.min(fbWidth - scissorX, (int) Math.ceil(pxW) + paddingPx * 2);
        int scissorH = Math.min(fbHeight - scissorY, (int) Math.ceil(pxH) + paddingPx * 2);

        try (RenderPass renderPass = encoder.createRenderPass(() -> "Sakura Blur", framebuffer.getColorAttachmentView(), OptionalInt.empty(), framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null, OptionalDouble.empty())) {
            renderPass.setPipeline(this.blurPipeline);
            renderPass.enableScissor(scissorX, scissorY, Math.max(0, scissorW), Math.max(0, scissorH));
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("BlurUniforms", this.uniforms.getBlocking());
            renderPass.bindTexture("InputSampler", this.input.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.bindTexture("MaskSampler", this.mask.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.draw(0, 3);
        }
        this.uniforms.rotate();
    }
}
