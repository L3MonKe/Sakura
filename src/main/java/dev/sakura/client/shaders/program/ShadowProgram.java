package dev.sakura.client.shaders.program;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static dev.sakura.client.Sakura.mc;

public class ShadowProgram {
    private static final Identifier VERTEX_SHADER = Identifier.of("sakura", "core/screen_triangle");
    private static final Identifier MASK_FRAGMENT_SHADER = Identifier.of("sakura", "core/blur_mask");
    private static final Identifier SHADOW_FRAGMENT_SHADER = Identifier.of("sakura", "core/shadow");

    private static final int UNIFORMS_SIZE = SegmentUniforms.uniformSize();

    private RenderPipeline maskPipeline;
    private RenderPipeline shadowPipeline;
    private MappableRingBuffer uniforms;
    private final LazyFramebuffer mask = new LazyFramebuffer("Sakura Shadow Mask");
    private final float[] tmpTopBottom = new float[2];

    private void ensureProgram() {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura ShadowUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        if (this.maskPipeline == null) {
            this.maskPipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/shadow_mask"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(MASK_FRAGMENT_SHADER)
                    .withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
        if (this.shadowPipeline == null) {
            this.shadowPipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/shadow"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(SHADOW_FRAGMENT_SHADER)
                    .withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER)
                    .withSampler("MaskSampler")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
    }

    public void renderStairShadow(float x, float y, float width, float height, float range, float strength, Color color, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        this.renderStairShadow(x, y, width, height, range, strength, color, color, false, segmentRects, segmentRadii, segmentCount);
    }

    public void renderStairShadow(float x, float y, float width, float height, float range, float strength, Color startColor, Color endColor, boolean gradient, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        this.ensureProgram();
        if (this.maskPipeline == null || this.shadowPipeline == null || this.uniforms == null) {
            return;
        }

        Framebuffer framebuffer = mc.getFramebuffer();
        if (framebuffer.getColorAttachment() == null || framebuffer.getColorAttachmentView() == null) {
            return;
        }

        int fbWidth = mc.getWindow().getFramebufferWidth();
        int fbHeight = mc.getWindow().getFramebufferHeight();
        Framebuffer mask = this.mask.ensure(fbWidth, fbHeight);
        if (mask.getColorAttachment() == null || mask.getColorAttachmentView() == null) {
            return;
        }

        float scale = (float) mc.getWindow().getScaleFactor();
        float scaledHeight = mc.getWindow().getScaledHeight();

        float rangePx = Math.max(0.0f, range * scale);
        float strengthClamped = Math.max(0.0f, Math.min(1.0f, strength));

        float pxX = x * scale;
        float pxY = (scaledHeight - (y + height)) * scale;
        float pxW = width * scale;
        float pxH = height * scale;

        int count = SegmentUniforms.clampCount(segmentCount);
        SegmentUniforms.computeVerticalBounds(scale, scaledHeight, pxY, pxH, segmentRects, count, this.tmpTopBottom);
        float topY = this.tmpTopBottom[0];
        float bottomY = this.tmpTopBottom[1];

        int paddingPx = (int) Math.ceil(Math.max(2.0f, rangePx * 2.0f));
        int scissorX = Math.max(0, (int) Math.floor(pxX) - paddingPx);
        int scissorY = Math.max(0, (int) Math.floor(pxY) - paddingPx);
        int scissorW = Math.min(fbWidth - scissorX, (int) Math.ceil(pxW) + paddingPx * 2);
        int scissorH = Math.min(fbHeight - scissorY, (int) Math.ceil(pxH) + paddingPx * 2);

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture maskColor = mask.getColorAttachment();
        if (maskColor != null) {
            encoder.clearColorTexture(maskColor, ColorHelper.getArgb(0, 0, 0, 0));
        }

        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec4(framebuffer.textureWidth, framebuffer.textureHeight, rangePx, strengthClamped);
            builder.putVec4(topY, bottomY, 0.0f, 0.0f);
            builder.putVec4(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, 1.0f);
            builder.putVec4(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, gradient ? 1.0f : 0.0f);
            builder.putVec4((float) count, 0.0f, 0.0f, 0.0f);
            SegmentUniforms.putSegments(builder, scale, scaledHeight, segmentRects, segmentRadii, count);
        }

        try (RenderPass maskPass = encoder.createRenderPass(() -> "Sakura Shadow Mask", mask.getColorAttachmentView(), OptionalInt.empty(), null, OptionalDouble.empty())) {
            maskPass.setPipeline(this.maskPipeline);
            RenderSystem.bindDefaultUniforms(maskPass);
            maskPass.setUniform("BlurUniforms", this.uniforms.getBlocking());
            maskPass.draw(0, 3);
        }

        try (RenderPass shadowPass = encoder.createRenderPass(() -> "Sakura Shadow", framebuffer.getColorAttachmentView(), OptionalInt.empty(), framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null, OptionalDouble.empty())) {
            shadowPass.setPipeline(this.shadowPipeline);
            shadowPass.enableScissor(scissorX, scissorY, Math.max(0, scissorW), Math.max(0, scissorH));
            RenderSystem.bindDefaultUniforms(shadowPass);
            shadowPass.setUniform("BlurUniforms", this.uniforms.getBlocking());
            shadowPass.bindTexture("MaskSampler", mask.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            shadowPass.draw(0, 3);
        }

        this.uniforms.rotate();
    }
}
