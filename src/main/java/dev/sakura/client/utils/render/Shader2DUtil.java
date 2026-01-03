package dev.sakura.client.utils.render;

import dev.sakura.client.shaders.BlurShader;
import net.minecraft.client.render.*;

public class Shader2DUtil {

    public static void drawQuadBlur(float x, float y, float width, float height, float blurStrength, float blurOpacity) {
        BlurShader.use(() -> {
            BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
            setRectanglePoints(buffer, x, y, x + width, y + height);

            BufferRenderer.drawWithGlobalProgram(buffer.end());
        });
        BlurShader.draw(blurStrength);
    }

    public static void drawRoundedBlur(float x, float y, float width, float height, float radius, float blurStrength, float blurOpacity) {
        BlurShader.use(() -> {
            BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION);

            float r = Math.min(radius, Math.min(width / 2, height / 2));

            addRect(buffer, x, y + r, x + width, y + height - r);
            addRect(buffer, x + r, y, x + width - r, y + r);
            addRect(buffer, x + r, y + height - r, x + width - r, y + height);

            int chengdu = 12;

            addCorner(buffer, x + r, y + r, r, 180, 270, chengdu);
            addCorner(buffer, x + width - r, y + r, r, 270, 360, chengdu);
            addCorner(buffer, x + width - r, y + height - r, r, 0, 90, chengdu);
            addCorner(buffer, x + r, y + height - r, r, 90, 180, chengdu);

            BufferRenderer.drawWithGlobalProgram(buffer.end());
        });
        BlurShader.draw(blurStrength);
    }

    private static void addRect(BufferBuilder buffer, float x1, float y1, float x2, float y2) {
        buffer.vertex(x1, y1, 0); // 左上
        buffer.vertex(x1, y2, 0); // 左下
        buffer.vertex(x2, y2, 0); // 右下

        buffer.vertex(x2, y2, 0); // 右下
        buffer.vertex(x2, y1, 0); // 右上
        buffer.vertex(x1, y1, 0); // 左上
    }

    private static void addCorner(BufferBuilder buffer, float cx, float cy, float r, double startAngle, double endAngle, int segments) {
        double step = (endAngle - startAngle) / segments;

        for (int i = 0; i < segments; i++) {
            double a1 = Math.toRadians(startAngle + step * i);
            double a2 = Math.toRadians(startAngle + step * (i + 1));

            buffer.vertex(cx, cy, 0);
            buffer.vertex(cx + (float) Math.cos(a1) * r, cy + (float) Math.sin(a1) * r, 0);
            buffer.vertex(cx + (float) Math.cos(a2) * r, cy + (float) Math.sin(a2) * r, 0);
        }
    }

    public static void setRectanglePoints(BufferBuilder buffer, float x, float y, float x1, float y1) {
        buffer.vertex(x, y, 0);
        buffer.vertex(x, y1, 0);
        buffer.vertex(x1, y1, 0);
        buffer.vertex(x1, y, 0);
    }
}
