package dev.mahiro.client.utils.render;

import dev.mahiro.client.utils.color.ColorUtil;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import static dev.mahiro.client.Mahiro.mc;

public record WorldRenderer(Camera camera, VertexConsumerProvider vcp) {
    public WorldRenderer(VertexConsumerProvider vcp) {
        this(mc.gameRenderer.getCamera(), vcp);
    }

    public void drawFilledQuad(MatrixStack stack, RenderLayer layer, Vec3d position, Vec2f dimensions, int color) {
        VertexConsumer buffer = vcp.getBuffer(layer);
        MatrixStack.Entry transform = stack.peek();
        Vector3f transformedRoot = position.subtract(camera.getCameraPos()).toVector3f();
        float x = transformedRoot.x;
        float y = transformedRoot.y;
        float z = transformedRoot.z;

        final int[] rgba = ColorUtil.hexToRGBA(color);
        float r = rgba[0] / 255f;
        float g = rgba[1] / 255f;
        float b = rgba[2] / 255f;
        float a = rgba[3] / 255f;

        Emitter._emit_quad__4xposition_color(transform, buffer,
                x + 0, y + 0, z + 0, r, g, b, a,
                x + dimensions.x, y + 0, z + 0, r, g, b, a,
                x + dimensions.x, y + dimensions.y, z + 0, r, g, b, a,
                x + 0, y + dimensions.y, z + 0, r, g, b, a);
    }

    public void drawFilledCube(MatrixStack stack, RenderLayer layer, Vec3d position, Vec3d dimensions, int color) {
        VertexConsumer buffer = vcp.getBuffer(layer);
        MatrixStack.Entry transform = stack.peek();
        Vector3f transformedRoot = position.subtract(camera.getCameraPos()).toVector3f();
        float x = transformedRoot.x;
        float y = transformedRoot.y;
        float z = transformedRoot.z;

        final int[] rgba = ColorUtil.hexToRGBA(color);
        float r = rgba[0] / 255f;
        float g = rgba[1] / 255f;
        float b = rgba[2] / 255f;
        float a = rgba[3] / 255f;

        Emitter._emit_cube__8xposition_color(transform, buffer,
                x + 0, y + 0, z + 0, r, g, b, a,
                (float) (x + dimensions.getX()), y + 0, z + 0, r, g, b, a,
                (float) (x + dimensions.getX()), y + 0, (float) (z + dimensions.getZ()), r, g, b, a,
                x + 0, y + 0, (float) (z + dimensions.getZ()), r, g, b, a,
                x + 0, (float) (y + dimensions.getY()), z + 0, r, g, b, a,
                (float) (x + dimensions.getX()), (float) (y + dimensions.getY()), z + 0, r, g, b, a,
                (float) (x + dimensions.getX()), (float) (y + dimensions.getY()), (float) (z + dimensions.getZ()), r, g, b, a,
                x + 0, (float) (y + dimensions.getY()), (float) (z + dimensions.getZ()), r, g, b, a);
    }

    public void drawLine(MatrixStack stack, RenderLayer layer, Vec3d start, Vec3d end, int color) {
        VertexConsumer buffer = vcp.getBuffer(layer);
        MatrixStack.Entry transform = stack.peek();
        Vector3f tfStart = start.subtract(camera.getCameraPos()).toVector3f();
        Vector3f tfEnd = end.subtract(camera.getCameraPos()).toVector3f();
        Vector3f direction = tfEnd.sub(tfStart, new Vector3f()).normalize();
        float x1 = tfStart.x;
        float y1 = tfStart.y;
        float z1 = tfStart.z;
        float x2 = tfEnd.x;
        float y2 = tfEnd.y;
        float z2 = tfEnd.z;

        final int[] rgba = ColorUtil.hexToRGBA(color);
        float r = rgba[0] / 255f;
        float g = rgba[1] / 255f;
        float b = rgba[2] / 255f;
        float a = rgba[3] / 255f;

        Emitter._emit_line__2xposition_color_normal(transform, buffer,
                x1, y1, z1, r, g, b, a, direction.x, direction.y, direction.z,
                x2, y2, z2, r, g, b, a, direction.x, direction.y, direction.z);
    }
}
