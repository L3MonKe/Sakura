package dev.mahiro.client.utils.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.render.BufferBuilder;
<<<<<<< HEAD
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayers;
=======
>>>>>>> 29a39dbff8f1e9022164526e67187b83ac1ff09a
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import java.awt.*;

import static dev.mahiro.client.Mahiro.mc;
import static org.lwjgl.nanovg.NanoVG.*;

public class Render3DUtil {
<<<<<<< HEAD
    private static final Matrix4f lastProjMat = new Matrix4f();
    private static final Matrix4f lastModMat = new Matrix4f();
    private static final Matrix4f lastWorldSpaceMatrix = new Matrix4f();

    public static void updateMatrices(Matrix4f proj, Matrix4f mod) {
        lastProjMat.set(proj);
        lastModMat.set(mod);
        lastWorldSpaceMatrix.set(proj).mul(mod);
    }


    // TODO: 应该使用自己的pipeline覆盖（例如RenderLayers.linesTranslucent()很可能会把blend设回translucent），想要稳定的additive，应该做自定义 RenderPipeline/RenderLayer来保证blend固化，，


=======
>>>>>>> 29a39dbff8f1e9022164526e67187b83ac1ff09a
    public static void drawFullBox(MatrixStack stack, BlockPos blockPos, Color sideColor, Color lineColor) {
        drawFullBox(stack, blockPos, sideColor, lineColor, 2f);
    }

    public static void drawFullBox(MatrixStack stack, Box box, Color sideColor, Color lineColor) {
        drawFullBox(stack, box, sideColor, lineColor, 2f);
    }

    public static void drawFullBox(MatrixStack stack, BlockPos blockPos, Color sideColor, Color lineColor, float lineWidth) {
        drawFullBox(stack, new Box(blockPos), sideColor, lineColor, lineWidth);
    }

    public static void drawFullBox(MatrixStack stack, Box box, Color sideColor, Color lineColor, float lineWidth) {
        drawFullBox(stack, box, sideColor.getRGB(), lineColor.getRGB(), lineWidth);
    }

    public static void drawFullBox(MatrixStack stack, Box box, int sideColor, int lineColor, float thickness) {
        drawFilledBox(stack, box, sideColor);
        drawOutlineBox(stack, box, lineColor, thickness);
    }

    public static void drawFilledBox(MatrixStack stack, BlockPos blockPos, Color color) {
        drawFilledBox(stack, new Box(blockPos), color.getRGB());
    }

    public static void drawFilledBox(MatrixStack stack, Box box, Color color) {
        int c = color.getRGB();
        drawFilledFadeBox(stack, box, c, c);
    }

    public static void drawFilledBox(MatrixStack stack, Box box, int c) {
        drawFilledFadeBox(stack, box, c, c);
    }

    public static void drawFilledFadeBox(MatrixStack stack, Box box, int c, int c1) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();

        vertex(bufferBuilder, matrix, minX, minY, minZ, c);
        vertex(bufferBuilder, matrix, minX, minY, maxZ, c);
        vertex(bufferBuilder, matrix, maxX, minY, maxZ, c);
        vertex(bufferBuilder, matrix, maxX, minY, minZ, c);

        vertex(bufferBuilder, matrix, minX, maxY, minZ, c1);
        vertex(bufferBuilder, matrix, maxX, maxY, minZ, c1);
        vertex(bufferBuilder, matrix, maxX, maxY, maxZ, c1);
        vertex(bufferBuilder, matrix, minX, maxY, maxZ, c);

        vertex(bufferBuilder, matrix, minX, minY, minZ, c);
        vertex(bufferBuilder, matrix, minX, maxY, minZ, c1);
        vertex(bufferBuilder, matrix, maxX, maxY, minZ, c1);
        vertex(bufferBuilder, matrix, maxX, minY, minZ, c);

        vertex(bufferBuilder, matrix, maxX, minY, minZ, c);
        vertex(bufferBuilder, matrix, maxX, maxY, minZ, c1);
        vertex(bufferBuilder, matrix, maxX, maxY, maxZ, c1);
        vertex(bufferBuilder, matrix, maxX, minY, maxZ, c);

        vertex(bufferBuilder, matrix, minX, minY, maxZ, c);
        vertex(bufferBuilder, matrix, maxX, minY, maxZ, c);
        vertex(bufferBuilder, matrix, maxX, maxY, maxZ, c1);
        vertex(bufferBuilder, matrix, minX, maxY, maxZ, c1);

        vertex(bufferBuilder, matrix, minX, minY, minZ, c);
        vertex(bufferBuilder, matrix, minX, minY, maxZ, c);
        vertex(bufferBuilder, matrix, minX, maxY, maxZ, c1);
        vertex(bufferBuilder, matrix, minX, maxY, minZ, c1);

        MahiroPipelines.FILLED_BOX.draw(bufferBuilder.end());
    }

    public static void drawOutlineBox(MatrixStack stack, Box box, int color, float thickness) {
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();
        MatrixStack.Entry entry = stack.peek();

        vertexLine(buffer, matrix, entry, minX, minY, minZ, maxX, minY, minZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, minY, minZ, maxX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, minY, maxZ, minX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, entry, minX, minY, maxZ, minX, minY, minZ, color, thickness);

        vertexLine(buffer, matrix, entry, minX, maxY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, maxY, minZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, maxY, maxZ, minX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, entry, minX, maxY, maxZ, minX, maxY, minZ, color, thickness);

        vertexLine(buffer, matrix, entry, minX, minY, minZ, minX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, minY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, entry, maxX, minY, maxZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, entry, minX, minY, maxZ, minX, maxY, maxZ, color, thickness);

        MahiroPipelines.LINES.draw(buffer.end());
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, Color color, float thickness) {
        drawLine(stack, start, end, color.getRGB(), thickness);
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, int color, float thickness) {
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float x1 = (float) (start.x - camPos.getX());
        float y1 = (float) (start.y - camPos.getY());
        float z1 = (float) (start.z - camPos.getZ());
        float x2 = (float) (end.x - camPos.getX());
        float y2 = (float) (end.y - camPos.getY());
        float z2 = (float) (end.z - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();
        MatrixStack.Entry entry = stack.peek();

        vertexLine(buffer, matrix, entry, x1, y1, z1, x2, y2, z2, color, thickness);

        MahiroPipelines.LINES.draw(buffer.end());
    }

    private static void vertex(BufferBuilder buffer, Matrix4f matrix, float x, float y, float z, int color) {
        buffer.vertex(matrix, x, y, z).color(color);
    }

    private static void vertexLine(BufferBuilder buffer, Matrix4f matrix, MatrixStack.Entry entry, float x1, float y1, float z1, float x2, float y2, float z2, int color, float thickness) {
        Vector3f normal = getNormal(x1, y1, z1, x2, y2, z2);
        buffer.vertex(matrix, x1, y1, z1).color(color).normal(entry, normal.x, normal.y, normal.z).lineWidth(thickness);
        buffer.vertex(matrix, x2, y2, z2).color(color).normal(entry, normal.x, normal.y, normal.z).lineWidth(thickness);
    }

    private static Vector3f getNormal(float x1, float y1, float z1, float x2, float y2, float z2) {
        float xNormal = x2 - x1;
        float yNormal = y2 - y1;
        float zNormal = z2 - z1;
        float normalSqrt = MathHelper.sqrt(xNormal * xNormal + yNormal * yNormal + zNormal * zNormal);
        return new Vector3f(xNormal / normalSqrt, yNormal / normalSqrt, zNormal / normalSqrt);
    }

    public static void drawText(String text, Vec3d pos, double offX, double offY, double textOffset, Color color) {
        Vec3d screenPos = worldToScreen(pos.add(offX, offY, 0));
        if (screenPos != null) {
            float finalScale = (float) screenPos.z;

            finalScale *= 5.0f;
            finalScale = Math.max(finalScale, 0.5f);

            float s = finalScale;

            NanoVGRenderer.INSTANCE.draw(vg -> {
                nvgSave(vg);
                nvgTranslate(vg, (float) screenPos.x, (float) screenPos.y);
                nvgScale(vg, s, s);
                nvgTranslate(vg, -(float) screenPos.x, -(float) screenPos.y);
                NanoVGHelper.drawCenteredString(text, (float) screenPos.x + (float) textOffset, (float) screenPos.y, FontLoader.medium(), 12, color);
                nvgRestore(vg);
            });
        }
    }

    public static Vec3d worldToScreen(Vec3d worldPos) {
        Camera camera = mc.gameRenderer.getCamera();
        Vec3d camPos = camera.getCameraPos();
        Vec3d relPos = worldPos.subtract(camPos);

        Vector4f vec = new Vector4f((float) relPos.x, (float) relPos.y, (float) relPos.z, 1.0f);
        vec.mul(lastWorldSpaceMatrix);

        if (vec.w <= 0.0f) {
            return null;
        }

        float w = vec.w;
        vec.div(w);

        float x = (vec.x + 1.0f) * 0.5f * mc.getWindow().getScaledWidth();
        float y = (1.0f - vec.y) * 0.5f * mc.getWindow().getScaledHeight();

        float scale = (1.0f / w) * lastProjMat.m11();
        return new Vec3d(x, y, scale);
    }
}
