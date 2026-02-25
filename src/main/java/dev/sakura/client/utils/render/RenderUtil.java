package dev.sakura.client.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.math.MathUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.awt.*;

public class RenderUtil {
    public static boolean isHovering(float x, float y, float width, float height, double mouseX, double mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static double deltaTime() {
        return MinecraftClient.getInstance().getCurrentFps() > 0 ? (1.0000 / MinecraftClient.getInstance().getCurrentFps()) : 1;
    }

    public static int colorSwitch(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed, double alpha) {
        long now = (long) (speed * System.currentTimeMillis() + index * timePerIndex);

        float redDiff = (firstColor.getRed() - secondColor.getRed()) / time;
        float greenDiff = (firstColor.getGreen() - secondColor.getGreen()) / time;
        float blueDiff = (firstColor.getBlue() - secondColor.getBlue()) / time;
        int red = Math.round(secondColor.getRed() + redDiff * (now % (long) time));
        int green = Math.round(secondColor.getGreen() + greenDiff * (now % (long) time));
        int blue = Math.round(secondColor.getBlue() + blueDiff * (now % (long) time));

        float redInverseDiff = (secondColor.getRed() - firstColor.getRed()) / time;
        float greenInverseDiff = (secondColor.getGreen() - firstColor.getGreen()) / time;
        float blueInverseDiff = (secondColor.getBlue() - firstColor.getBlue()) / time;
        int inverseRed = Math.round(firstColor.getRed() + redInverseDiff * (now % (long) time));
        int inverseGreen = Math.round(firstColor.getGreen() + greenInverseDiff * (now % (long) time));
        int inverseBlue = Math.round(firstColor.getBlue() + blueInverseDiff * (now % (long) time));

        if (now % ((long) time * 2) < (long) time)
            return ColorUtil.getColor(inverseRed, inverseGreen, inverseBlue, (int) alpha);
        else return ColorUtil.getColor(red, green, blue, (int) alpha);
    }

    public static int colorSwitch(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed) {
        return colorSwitch(firstColor, secondColor, time, index, timePerIndex, speed, 255);
    }

    public static void drawTracer(DrawContext guiGraphics, float x, float y, float size, float widthDiv, float heightDiv, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        MatrixStack poseStack = guiGraphics.getMatrices();
        Matrix4f matrix = poseStack.peek().getPositionMatrix();

        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, x - size / widthDiv, y + size, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, x, y + size / heightDiv, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, x + size / widthDiv, y + size, 0.0F).color(r, g, b, a);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }

    public static int getRainbow(long currentMillis, int speed, int offset, float alpha) {
        int rainbow = Color.HSBtoRGB(1.0F - ((currentMillis + (offset * 100)) % speed) / (float) speed,
                0.9F, 0.9F);
        int r = (rainbow >> 16) & 0xFF;
        int g = (rainbow >> 8) & 0xFF;
        int b = rainbow & 0xFF;
        int a = (int) (alpha * 255.0F);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    public static int getRainbow(long currentMillis, int speed, int offset) {
        return getRainbow(currentMillis, speed, offset, 1.0F);
    }

    public static double animate(double value, double target, double speed, boolean minedelta) {
        double c = value + (target - value) / (3 + speed * deltaTime());
        double v = value
                + ((target - value)) / (2 + speed);
        return minedelta ? v : c;
    }

    public static double animate(double value, double target) {
        return animate(value, target, 1, false);
    }

    public static float animate(float end, float start, float multiple) {
        return (1 - MathUtil.clamp((float) (deltaTime() * multiple), 0, 1)) * end + MathUtil.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }
}
