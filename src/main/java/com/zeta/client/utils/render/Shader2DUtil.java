package com.zeta.client.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zeta.client.shaders.BlurProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.awt.*;

public class Shader2DUtil {
    public static BlurProgram BLUR_PROGRAM;

    public static void init() {
        BLUR_PROGRAM = new BlurProgram();
    }

    public static void drawQuadBlur(MatrixStack matrices, float x, float y, float width, float height, float blurStrength, float blurOpacity) {
        BufferBuilder bb = preShaderDraw(matrices, x - 10, y - 10, width + 20, height + 20);

        BLUR_PROGRAM.setParameters(x, y, width, height, 0f, new Color(0, 0, 0, 0), blurStrength, blurOpacity);
        BLUR_PROGRAM.use();

        BufferRenderer.drawWithGlobalProgram(bb.end());
        endRender();
    }

    public static void drawRoundedBlur(MatrixStack matrices, float x, float y, float width, float height, float radius, Color c1, float blurStrenth, float blurOpacity) {
        blurOpacity = Math.max(0f, Math.min(1f, blurOpacity));

        BufferBuilder bb = preShaderDraw(matrices, x - 10, y - 10, width + 20, height + 20);
        BLUR_PROGRAM.setParameters(x, y, width, height, radius, c1, blurStrenth, blurOpacity);
        BLUR_PROGRAM.use();

        BufferRenderer.drawWithGlobalProgram(bb.end());
        endRender();
    }

    public static void setRectanglePoints(BufferBuilder buffer, Matrix4f matrix, float x, float y, float x1, float y1) {
        buffer.vertex(matrix, x, y, 0).color(1f, 1f, 1f, 1f);
        buffer.vertex(matrix, x, y1, 0).color(1f, 1f, 1f, 1f);
        buffer.vertex(matrix, x1, y1, 0).color(1f, 1f, 1f, 1f);
        buffer.vertex(matrix, x1, y, 0).color(1f, 1f, 1f, 1f);
    }

    public static BufferBuilder preShaderDraw(MatrixStack matrices, float x, float y, float width, float height) {
        beginRender();
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        setRectanglePoints(buffer, matrix, x, y, x + width, y + height);
        return buffer;
    }

    public static void beginRender() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    public static void endRender() {
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }
}
