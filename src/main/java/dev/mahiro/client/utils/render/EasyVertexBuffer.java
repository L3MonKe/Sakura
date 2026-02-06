package dev.mahiro.client.utils.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;

public final class EasyVertexBuffer implements AutoCloseable {
    private final RenderSystem.ShapeIndexBuffer shapeIndexBuffer;
    private final GpuBuffer vertexBuffer;
    private final int indexCount;

    /**
     * Drop-in replacement for {@code VertexBuffer.createAndUpload()}.
     */
    public static EasyVertexBuffer createAndUpload(DrawMode drawMode, VertexFormat format, Consumer<VertexConsumer> callback) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(drawMode, format);
        callback.accept(bufferBuilder);

        try (BuiltBuffer buffer = bufferBuilder.build()) {
            if (buffer == null)
                return new EasyVertexBuffer(drawMode);

            return new EasyVertexBuffer(buffer, drawMode);
        }
    }

    private EasyVertexBuffer(BuiltBuffer buffer, DrawMode drawMode) {
        BuiltBuffer.DrawParameters drawParams = buffer.getDrawParameters();
        shapeIndexBuffer = RenderSystem.getSequentialBuffer(drawParams.mode());
        indexCount = drawParams.indexCount();

        vertexBuffer = RenderSystem.getDevice().createBuffer(null, GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_COPY_DST, buffer.getBuffer());
    }

    private EasyVertexBuffer(DrawMode drawMode) {
        shapeIndexBuffer = null;
        indexCount = 0;
        vertexBuffer = null;
    }

    public void draw(MatrixStack matrixStack, RenderLayer layer) {
        draw(matrixStack, layer, 1, 1, 1, 1);
    }

    public void draw(MatrixStack matrixStack, RenderLayer layer, int argb) {
        float alpha = (argb >> 24 & 0xFF) / 255F;
        float red = (argb >> 16 & 0xFF) / 255F;
        float green = (argb >> 8 & 0xFF) / 255F;
        float blue = (argb & 0xFF) / 255F;
        draw(matrixStack, layer, red, green, blue, alpha);
    }

    public void draw(MatrixStack matrixStack, RenderLayer layer, float[] rgba) {
        draw(matrixStack, layer, rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    public void draw(MatrixStack matrixStack, RenderLayer layer, float[] rgb,
                     float alpha) {
        draw(matrixStack, layer, rgb[0], rgb[1], rgb[2], alpha);
    }

    /*
     * Similar to {@link RenderLayer#draw(BuiltBuffer)}.
     */
    public void draw(MatrixStack matrixStack, RenderLayer layer, float red, float green, float blue, float alpha) {
        if (vertexBuffer == null)
            return;

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.mul(matrixStack.peek().getPositionMatrix());

        GpuBufferSlice gpuBufferSlice = RenderSystem.getDynamicUniforms().write(RenderSystem.getModelViewMatrix(), new Vector4f(red, green, blue, alpha), new Vector3f(), TextureTransform.DEFAULT_TEXTURING.getTransformSupplier());

        Framebuffer framebuffer = OutputTarget.ITEM_ENTITY_TARGET.getFramebuffer();
        RenderPipeline pipeline = layer.getRenderPipeline();
        GpuBuffer indexBuffer = shapeIndexBuffer.getIndexBuffer(indexCount);

        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "something from Mahiro", framebuffer.getColorAttachmentView(), OptionalInt.empty(), framebuffer.getDepthAttachmentView(), OptionalDouble.empty())) {
            renderPass.setPipeline(pipeline);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", gpuBufferSlice);
            renderPass.setVertexBuffer(0, vertexBuffer);
            renderPass.setIndexBuffer(indexBuffer, shapeIndexBuffer.getIndexType());
            renderPass.drawIndexed(0, 0, indexCount, 1);
        }

        modelViewStack.popMatrix();
    }

    @Override
    public void close() {
        if (vertexBuffer != null) {
            vertexBuffer.close();
        }
    }
}