package dev.mahiro.client.utils.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.awt.*;
import java.util.List;

import static dev.mahiro.client.Mahiro.mc;
import static org.lwjgl.nanovg.NanoVG.*;

public class Render3DUtil {


    // TODO: 应该使用自己的pipeline覆盖（例如RenderLayers.linesTranslucent()很可能会把blend设回translucent），想要稳定的additive，应该做自定义 RenderPipeline/RenderLayer来保证blend固化，，


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
        drawBoxOutline(stack, box, lineColor, thickness);
    }

    public static void drawBatchBoxes(MatrixStack stack, List<Box> boxes, List<Color> sideColors, List<Color> lineColors, float thickness) {
        if (boxes.isEmpty()) return;
        setup3D();

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();

        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            int color = sideColors.get(i).getRGB();
            addBoxVertices(bufferBuilder, matrix, box, camPos, color, color);
        }
        RenderLayers.debugQuads().draw(bufferBuilder.end());

        BufferBuilder lineBuffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            int color = lineColors.get(i).getRGB();
            addBoxLineVertices(lineBuffer, matrix, box, camPos, color, thickness);
        }
        RenderLayers.lines().draw(lineBuffer.end());

        cleanup3D();
    }

    private static void addBoxVertices(BufferBuilder bufferBuilder, Matrix4f matrix, Box box, Vec3d camPos, int c, int c1) {
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

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
    }

    private static void addBoxLineVertices(BufferBuilder buffer, Matrix4f matrix, Box box, Vec3d camPos, int color, float lineWidth) {
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        vertexLine(buffer, matrix, minX, minY, minZ, maxX, minY, minZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, minY, maxZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, minY, maxZ, minX, minY, maxZ, color, lineWidth);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, minY, minZ, color, lineWidth);

        vertexLine(buffer, matrix, minX, maxY, minZ, maxX, maxY, minZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, maxY, minZ, maxX, maxY, maxZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, maxY, maxZ, minX, maxY, maxZ, color, lineWidth);
        vertexLine(buffer, matrix, minX, maxY, maxZ, minX, maxY, minZ, color, lineWidth);

        vertexLine(buffer, matrix, minX, minY, minZ, minX, maxY, minZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, maxY, minZ, color, lineWidth);
        vertexLine(buffer, matrix, maxX, minY, maxZ, maxX, maxY, maxZ, color, lineWidth);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, maxY, maxZ, color, lineWidth);
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
        setup3D();
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

        RenderLayers.debugQuads().draw(bufferBuilder.end());
        cleanup3D();
    }

    public static void drawBoxOutline(MatrixStack stack, Box box, int color, float thickness) {
        setup3D();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();

        vertexLine(buffer, matrix, minX, minY, minZ, maxX, minY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, maxZ, minX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, minY, minZ, color, thickness);

        vertexLine(buffer, matrix, minX, maxY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, maxY, minZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, maxX, maxY, maxZ, minX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, maxY, maxZ, minX, maxY, minZ, color, thickness);

        vertexLine(buffer, matrix, minX, minY, minZ, minX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, maxZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, maxY, maxZ, color, thickness);

        RenderLayers.lines().draw(buffer.end());
        cleanup3D();
    }

    public static void drawBoxOutlineAdditive(MatrixStack stack, Box box, int color, float thickness) {
        setup3DAdditive();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxY = (float) (box.maxY - camPos.getY());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();

        vertexLine(buffer, matrix, minX, minY, minZ, maxX, minY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, maxZ, minX, minY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, minY, minZ, color, thickness);

        vertexLine(buffer, matrix, minX, maxY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, maxY, minZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, maxX, maxY, maxZ, minX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, maxY, maxZ, minX, maxY, minZ, color, thickness);

        vertexLine(buffer, matrix, minX, minY, minZ, minX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, maxY, minZ, color, thickness);
        vertexLine(buffer, matrix, maxX, minY, maxZ, maxX, maxY, maxZ, color, thickness);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, maxY, maxZ, color, thickness);

        RenderLayers.linesTranslucent().draw(buffer.end());
        cleanup3D();
    }

    public static void drawBottomOutline(MatrixStack stack, Box box, int color) {
        setup3D();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float minX = (float) (box.minX - camPos.getX());
        float minY = (float) (box.minY - camPos.getY());
        float minZ = (float) (box.minZ - camPos.getZ());
        float maxX = (float) (box.maxX - camPos.getX());
        float maxZ = (float) (box.maxZ - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();

        vertexLine(buffer, matrix, minX, minY, minZ, maxX, minY, minZ, color, 1.0f);
        vertexLine(buffer, matrix, maxX, minY, minZ, maxX, minY, maxZ, color, 1.0f);
        vertexLine(buffer, matrix, maxX, minY, maxZ, minX, minY, maxZ, color, 1.0f);
        vertexLine(buffer, matrix, minX, minY, maxZ, minX, minY, minZ, color, 1.0f);

        RenderLayers.lines().draw(buffer.end());
        cleanup3D();
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, Color color, float thickness) {
        drawLine(stack, start, end, color.getRGB(), thickness);
    }

    public static void drawLine(MatrixStack stack, Vec3d start, Vec3d end, int color, float thickness) {
        setup3D();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH);

        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getCameraPos();
        float x1 = (float) (start.x - camPos.getX());
        float y1 = (float) (start.y - camPos.getY());
        float z1 = (float) (start.z - camPos.getZ());
        float x2 = (float) (end.x - camPos.getX());
        float y2 = (float) (end.y - camPos.getY());
        float z2 = (float) (end.z - camPos.getZ());

        Matrix4f matrix = stack.peek().getPositionMatrix();

        vertexLine(buffer, matrix, x1, y1, z1, x2, y2, z2, color, thickness);

        RenderLayers.lines().draw(buffer.end());
        cleanup3D();
    }

    public static void setup3D() {
//        GlStateManager._enableBlend();
//        GlStateManager._blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
//        GlStateManager._disableCull();
    }

    public static void setup3DAdditive() {
//        GlStateManager._enableBlend();
//        GlStateManager._blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
//        GlStateManager._disableCull();
    }

    public static void cleanup3D() {
//        GlStateManager._enableCull();
//        GlStateManager._disableBlend();
    }

    private static void vertex(BufferBuilder buffer, Matrix4f matrix, float x, float y, float z, int color) {
        buffer.vertex(matrix, x, y, z).color(color);
    }

    private static void vertexLine(BufferBuilder buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color, float lineWidth) {
        Vector3f normal = getNormal(x1, y1, z1, x2, y2, z2);
        buffer.vertex(matrix, x1, y1, z1).color(color).normal(normal.x, normal.y, normal.z).lineWidth(lineWidth);
        buffer.vertex(matrix, x2, y2, z2).color(color).normal(normal.x, normal.y, normal.z).lineWidth(lineWidth);
    }

    private static Vector3f getNormal(float x1, float y1, float z1, float x2, float y2, float z2) {
        float xNormal = x2 - x1;
        float yNormal = y2 - y1;
        float zNormal = z2 - z1;
        float normalSqrt = MathHelper.sqrt(xNormal * xNormal + yNormal * yNormal + zNormal * zNormal);
        if (normalSqrt == 0.0f) {
            return new Vector3f(1.0f, 0.0f, 0.0f);
        }
        return new Vector3f(xNormal / normalSqrt, yNormal / normalSqrt, zNormal / normalSqrt);
    }

    public static void drawText(String text, @NotNull Vec3d pos, double offX, double offY, double textOffset, @NotNull Color color) {
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
