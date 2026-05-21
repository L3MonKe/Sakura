package dev.sakura.client.shaders.program;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.utils.animations.AnimationUtil;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import static dev.sakura.client.Sakura.mc;

public class HealthBarProgram {
    private static final Identifier VERTEX_SHADER = Identifier.of("sakura", "core/screen_triangle");
    private static final Identifier FRAGMENT_SHADER = Identifier.of("sakura", "core/health_bar");
    private static final int UNIFORMS_SIZE = new Std140SizeCalculator()
            .putVec4()
            .putVec4()
            .putVec4()
            .putVec4()
            .get();

    private RenderPipeline pipeline;
    private MappableRingBuffer uniforms;
    private float timeSeconds;

    private void ensureProgram() {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura HealthBarUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        if (this.pipeline == null) {
            this.pipeline = RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                    .withLocation(Identifier.of("sakura", "pipeline/health_bar"))
                    .withVertexShader(VERTEX_SHADER)
                    .withFragmentShader(FRAGMENT_SHADER)
                    .withUniform("HealthBarUniforms", UniformType.UNIFORM_BUFFER)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withCull(false)
                    .build();
        }
    }

    public void render(float x, float y, float width, float height, float radius,
                       float hueMin, float hueMax, float satMin, float satMax,
                       float valMin, float valMax, float speed) {
        this.ensureProgram();
        if (this.pipeline == null || this.uniforms == null) {
            return;
        }

        if (width <= 0 || height <= 0) return;

        Framebuffer framebuffer = mc.getFramebuffer();
        if (framebuffer.getColorAttachment() == null || framebuffer.getColorAttachmentView() == null) {
            return;
        }

        int fbWidth = mc.getWindow().getFramebufferWidth();
        int fbHeight = mc.getWindow().getFramebufferHeight();

        float scale = (float) mc.getWindow().getScaleFactor();
        float scaledHeight = mc.getWindow().getScaledHeight();
        float pxX = x * scale;
        float pxY = (scaledHeight - y - height) * scale;
        float pxW = width * scale;
        float pxH = height * scale;
        float pxRadius = Math.max(0.0f, radius * scale);

        this.timeSeconds += AnimationUtil.deltaTime() * speed;

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();

        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec2((float) framebuffer.textureWidth, (float) framebuffer.textureHeight);
            builder.putFloat(this.timeSeconds);
            builder.putFloat(hueMin);
            builder.putFloat(hueMax);
            builder.putFloat(satMin);
            builder.putFloat(satMax);
            builder.putFloat(valMin);
            builder.putFloat(valMax);
            builder.putFloat(pxX);
            builder.putFloat(pxY);
            builder.putFloat(pxW);
            builder.putFloat(pxH);
            builder.putFloat(pxRadius);
        }

        int paddingPx = 2;
        int scissorX = Math.max(0, (int) Math.floor(pxX) - paddingPx);
        int scissorY = Math.max(0, (int) Math.floor(pxY) - paddingPx);
        scissorX = Math.min(scissorX, fbWidth);
        scissorY = Math.min(scissorY, fbHeight);

        int scissorW = Math.min(fbWidth - scissorX, (int) Math.ceil(pxW) + paddingPx * 2);
        int scissorH = Math.min(fbHeight - scissorY, (int) Math.ceil(pxH) + paddingPx * 2);
        scissorW = Math.max(0, scissorW);
        scissorH = Math.max(0, scissorH);

        try (RenderPass renderPass = encoder.createRenderPass(
                () -> "Sakura HealthBar",
                framebuffer.getColorAttachmentView(),
                OptionalInt.empty(),
                framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null,
                OptionalDouble.empty()
        )) {
            renderPass.setPipeline(this.pipeline);
            if (scissorW > 0 && scissorH > 0) {
                renderPass.enableScissor(scissorX, scissorY, scissorW, scissorH);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("HealthBarUniforms", this.uniforms.getBlocking());
            renderPass.draw(0, 3);
        }

        this.uniforms.rotate();
    }
}
