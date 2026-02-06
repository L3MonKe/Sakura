package dev.sakura.client.utils.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.render.BufferBuilder;
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

import java.awt.*;

import static dev.sakura.client.Sakura.mc;
import static org.lwjgl.nanovg.NanoVG.*;

public class Render3DUtil {
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

        SakuraPipelines.FILLED_BOX.draw(bufferBuilder.end());
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

        SakuraPipelines.LINES.draw(buffer.end());
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, Color color, float thickness) {
        drawLine(stack, start, end, color.getRGB(), thickness);
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, int color, float thickness) {
        drawLine(stack, start.x, start.y, start.z, end.x, end.y, end.z, color, thickness);
    }

    public static void drawLine(MatrixStack stack, double x1, double y1, double z1, double x2, double y2, double z2, Color color, float thickness) {
        drawLine(stack, x1, y1, z1, x2, y2, z2, color.getRGB(), thickness);
    }

    public static void drawLine(MatrixStack stack, double startX, double startY, double startZ, double endX, double endY, double endZ, int color, float thickness) {
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float x1 = (float) (startX - camPos.getX());
        float y1 = (float) (startY - camPos.getY());
        float z1 = (float) (startZ - camPos.getZ());
        float x2 = (float) (endX - camPos.getX());
        float y2 = (float) (endY - camPos.getY());
        float z2 = (float) (endZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();
        MatrixStack.Entry entry = stack.peek();

        vertexLine(buffer, matrix, entry, x1, y1, z1, x2, y2, z2, color, thickness);

        SakuraPipelines.LINES.draw(buffer.end());
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

    public static Vec3d worldToScreen(Vec3d vec) {
        var camera = mc.gameRenderer.getCamera();
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        Vec3d camPos = camera.getCameraPos();
        Vector3fc camLook = camera.getHorizontalPlane();
        Vector3fc camUp = camera.getVerticalPlane();
        Vector3f camLeft = new Vector3f();
        camLook.cross(camUp, camLeft);
        camLeft.normalize();

        float dx = (float) (vec.x - camPos.x);
        float dy = (float) (vec.y - camPos.y);
        float dz = (float) (vec.z - camPos.z);

        Vector3f toPos = new Vector3f(dx, dy, dz);

        float dotLook = toPos.dot(camLook);
        if (dotLook <= 0.01f) return null;

        float dotUp = toPos.dot(camUp);
        float dotLeft = toPos.dot(camLeft);

        float fov = mc.options.getFov().getValue().floatValue();
        float aspectRatio = (float) width / height;
        float tanHalfFov = (float) Math.tan(Math.toRadians(fov / 2.0));

        float screenX = width / 2f + (dotLeft / dotLook) / (tanHalfFov * aspectRatio) * (width / 2f);
        float screenY = height / 2f - (dotUp / dotLook) / tanHalfFov * (height / 2f);

        return new Vec3d(screenX, screenY, 1.0 / dotLook);
    }
}
