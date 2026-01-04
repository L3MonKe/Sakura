package dev.sakura.client.nanovg.util;

import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.nanovg.NanoVG.*;

public class NanoVGHelper {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static long getContext() {
        return NanoVGRenderer.INSTANCE.getContext();
    }

    public static NVGColor nvgColor(Color color) {
        NVGColor nvgColor = NVGColor.create();
        nvgRGBA((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha(), nvgColor);
        return nvgColor;
    }

    /**
     * 创建NanoVG颜色对象（带alpha）
     */
    public static NVGColor nvgColor(int r, int g, int b, int a) {
        NVGColor nvgColor = NVGColor.create();
        nvgRGBA((byte) r, (byte) g, (byte) b, (byte) a, nvgColor);
        return nvgColor;
    }

    /**
     * 绘制字符串（指定对齐方式）
     */
    public static float drawText(String text, float x, float y, int font, float size, int align, Color color) {
        long vg = getContext();

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, align);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);

        nvgText(vg, x, y, text);

        return size;
    }

    /**
     * 绘制字符串
     */
    public static float drawString(String text, float x, float y, int font, Color color) {
        return drawText(text, x, y, font, 18, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, color);
    }

    /**
     * 绘制字符串（带边界测量）
     */
    public static float drawStringBounds(String text, float x, float y, int font, float size, Color color) {
        long vg = getContext();

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);

        // 测量文本边界
        float[] bounds = new float[4];
        nvgTextBounds(vg, 0, 0, text, bounds);
        float height = bounds[3] - bounds[1];

        nvgText(vg, x, y, text);

        return height;
    }

    /**
     * 绘制字符串（指定字体大小）
     */
    public static float drawString(String text, float x, float y, int font, float size, Color color) {
        return drawText(text, x, y, font, size, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE, color);
    }

    /**
     * 绘制居中字符串
     */
    public static float drawCenteredString(String text, float x, float y, int font, float size, Color color) {
        return drawText(text, x, y, font, size, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, color);
    }

    /**
     * 绘制发光字符串
     */
    public static float drawGlowingString(String text, float x, float y, int font, float size, Color color, float glowRadius) {
        long vg = getContext();

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

        // 绘制发光效果（模糊的背景文本）
        NVGColor glowColor = nvgColor(color);
        nvgFontBlur(vg, glowRadius);
        nvgFillColor(vg, glowColor);
        nvgText(vg, x, y, text);

        // 绘制清晰的前景文本
        nvgFontBlur(vg, 0);
        nvgText(vg, x, y, text);

        return size;
    }

    /**
     * 获取文本宽度
     */
    public static float getTextWidth(String text, int font, float size) {
        long vg = getContext();
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);

        float[] bounds = new float[4];
        return nvgTextBounds(vg, 0, 0, text, bounds);
    }

    /**
     * 获取文本高度
     */
    public static float getTextHeight(int font, String text) {
        long vg = getContext();
        nvgFontFaceId(vg, font);

        float[] bounds = new float[4];
        nvgTextBounds(vg, 0, 0, text, bounds);
        return bounds[3] - bounds[1];
    }

    /**
     * 绘制圆形
     */
    public static void drawCircle(float x, float y, float radius, Color color) {
        long vg = getContext();

        nvgBeginPath(vg);
        nvgCircle(vg, x, y, radius);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);
        nvgFill(vg);
    }

    /**
     * 绘制圆角矩形
     */
    public static void drawRoundRect(float x, float y, float w, float h, float radius, Color color) {
        long vg = getContext();

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);
        nvgFill(vg);
    }

    /**
     * 绘制带缩放的圆角矩形
     */
    public static void drawRoundRectScaled(float x, float y, float w, float h, float radius, Color color, float scale) {
        long vg = getContext();

        float centerX = x + w / 2f;
        float centerY = y + h / 2f;

        nvgSave(vg);
        nvgTranslate(vg, centerX, centerY);
        nvgScale(vg, scale, scale);
        nvgTranslate(vg, -centerX, -centerY);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);
        nvgFill(vg);

        nvgRestore(vg);
    }

    /**
     * 绘制带发光效果的圆角矩形
     */
    public static void drawRoundRectBloom(float x, float y, float w, float h, float radius, Color color) {
        long vg = getContext();

        // 使用多层半透明矩形模拟发光效果
        float glowSize = 8.0f;
        int glowSteps = 8;

        for (int i = glowSteps; i > 0; i--) {
            float offset = (glowSize / glowSteps) * i;
            int alpha = (int) (color.getAlpha() * (1.0f - (float) i / glowSteps) * 0.3f);
            Color glowColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);

            nvgBeginPath(vg);
            nvgRoundedRect(vg, x - offset, y - offset, w + offset * 2, h + offset * 2, radius + offset);
            NVGColor nvgGlowColor = nvgColor(glowColor);
            nvgFillColor(vg, nvgGlowColor);
            nvgFill(vg);
        }

        // 绘制实际的矩形
        drawRoundRect(x, y, w, h, radius, color);
    }

    public static void drawRect(float x, float y, float w, float h, Color color) {
        long vg = getContext();

        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);

        NVGColor nvgColor = nvgColor(color);
        nvgFillColor(vg, nvgColor);
        nvgFill(vg);
    }

    /**
     * 绘制圆角矩形轮廓线
     */
    public static void drawRoundRectOutline(float x, float y, float w, float h, float radius, float strokeWidth, Color color) {
        long vg = getContext();

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);

        NVGColor nvgColor = nvgColor(color);
        nvgStrokeWidth(vg, strokeWidth);
        nvgStrokeColor(vg, nvgColor);
        nvgStroke(vg);
    }

    /**
     * 绘制带缩放的圆角矩形轮廓线
     */
    public static void drawRoundRectOutlineScaled(float x, float y, float w, float h, float radius, float strokeWidth, Color color, float scale) {
        long vg = getContext();

        float centerX = x + w / 2f;
        float centerY = y + h / 2f;

        nvgSave(vg);
        nvgTranslate(vg, centerX, centerY);
        nvgScale(vg, scale, scale);
        nvgTranslate(vg, -centerX, -centerY);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);

        NVGColor nvgColor = nvgColor(color);
        nvgStrokeWidth(vg, strokeWidth);
        nvgStrokeColor(vg, nvgColor);
        nvgStroke(vg);

        nvgRestore(vg);
    }

    /**
     * 绘制矩形轮廓线
     */
    public static void drawRectOutline(float x, float y, float w, float h, float strokeWidth, Color color) {
        long vg = getContext();

        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);

        NVGColor nvgColor = nvgColor(color);
        nvgStrokeWidth(vg, strokeWidth);
        nvgStrokeColor(vg, nvgColor);
        nvgStroke(vg);
    }

    /**
     * 绘制渐变圆角矩形（垂直渐变）
     */
    public static void drawGradientRRect(float x, float y, float w, float h, float radius, Color startColor, Color endColor) {
        long vg = getContext();

        NVGPaint paint = NVGPaint.create();
        NVGColor color1 = nvgColor(startColor);
        NVGColor color2 = nvgColor(endColor);

        nvgLinearGradient(vg, x, y, x, y + h, color1, color2, paint);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
    }

    /**
     * 绘制渐变圆角矩形（水平渐变）
     */
    public static void drawGradientRRect2(float x, float y, float w, float h, float radius, Color startColor, Color endColor) {
        long vg = getContext();

        NVGPaint paint = NVGPaint.create();
        NVGColor color1 = nvgColor(startColor);
        NVGColor color2 = nvgColor(endColor);

        nvgLinearGradient(vg, x, y, x + w, y, color1, color2, paint);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, w, h, radius);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
    }

    public static void drawGradientRRect3(float x, float y, float w, float h, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        drawGradientRect3(x, y, w, h, radius, bottomLeft, topLeft, bottomRight, topRight);
    }

    public static void drawGradientRect3(float x, float y, float w, float h, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        drawGradientRect3(x, y, w, h, 0, bottomLeft, topLeft, bottomRight, topRight);
    }

    public static void drawGradientRect3(float x, float y, float w, float h, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        long vg = getContext();

        int strips = 40;
        float stripHeight = h / strips;

        for (int i = 0; i <= strips; i++) {
            float ty = (float) i / strips;
            float stripY = y + i * stripHeight;

            Color leftColor = interpolateColor(topLeft, bottomLeft, ty);
            Color rightColor = interpolateColor(topRight, bottomRight, ty);

            NVGPaint paint = NVGPaint.create();
            NVGColor color1 = nvgColor(leftColor);
            NVGColor color2 = nvgColor(rightColor);

            nvgLinearGradient(vg, x, stripY, x + w, stripY, color1, color2, paint);

            nvgBeginPath(vg);

            if (radius > 0) {
                if (i == 0) {
                    nvgRoundedRectVarying(vg, x, stripY, w, stripHeight + 0.5f, radius, radius, 0, 0);
                } else if (i == strips) {
                    nvgRoundedRectVarying(vg, x, stripY - 0.5f, w, stripHeight + 0.5f, 0, 0, radius, radius);
                } else {
                    nvgRect(vg, x, stripY - 0.5f, w, stripHeight + 1.0f);
                }
            } else {
                nvgRect(vg, x, stripY - 0.5f, w, stripHeight + 1.0f);
            }

            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    /**
     * 颜色插值辅助方法
     * 在两个颜色之间进行线性插值
     */
    private static Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0, Math.min(1, t));

        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);

        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));
        a = Math.max(0, Math.min(255, a));

        return new Color(r, g, b, a);
    }

    /**
     * 绘制阴影
     */
    public static void drawShadow(float x, float y, float w, float h, float radius, Color color, float blur, float offsetX, float offsetY) {
        long vg = getContext();

        NVGPaint shadowPaint = NVGPaint.create();
        NVGColor innerColor = nvgColor(color);
        NVGColor outerColor = nvgColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));

        nvgBoxGradient(vg, x + offsetX, y + offsetY, w, h, radius, blur, innerColor, outerColor, shadowPaint);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x + offsetX - blur, y + offsetY - blur, w + blur * 2, h + blur * 2, radius + blur);
        nvgFillPaint(vg, shadowPaint);
        nvgFill(vg);
    }

    /**
     * 绘制无裁剪阴影的矩形
     */
    public static void drawRectShadowNoClip(float x, float y, float w, float h, float radius, Color color, float blur, float offsetX, float offsetY) {
        drawShadow(x, y, w, h, radius, color, blur, offsetX, offsetY);
    }

    /**
     * 绘制无裁剪阴影的矩形（无圆角）
     */
    public static void drawRectShadowNoClip(float x, float y, float w, float h, Color color, float blur, float offsetX, float offsetY) {
        drawShadow(x, y, w, h, 0, color, blur, offsetX, offsetY);
    }

    /**
     * 绘制模糊矩形（使用着色器实现背景模糊）
     */
    public static void drawRectBlur(float x, float y, float w, float h, float blurRadius) {
        long vg = getContext();

        // 结束当前的 NanoVG 绘制
        nvgEndFrame(vg);

        // 应用背景模糊着色器
        Shader2DUtil.drawRoundedBlur(new MatrixStack(), x, y, w, h, 0f, new Color(255, 255, 255, 50), blurRadius, 0.8f);

        // 重新开始 NanoVG 帧
        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();
        nvgBeginFrame(vg, width, height, 1.0f);
    }

    /**
     * 获取字体高度
     */
    public static float getFontHeight(int font, float size) {
        long vg = getContext();
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);

        float[] ascender = new float[1];
        float[] descender = new float[1];
        float[] lineh = new float[1];
        nvgTextMetrics(vg, ascender, descender, lineh);

        return lineh[0];
    }

    /**
     * 加载图片为NanoV 纹理
     *
     * @param path 图片路径（以/开头）
     * @return NanoVG 图片ID，失败返回 -1
     */
    public static int loadTexture(String path) {
        try {
            InputStream is = NanoVGHelper.class.getResourceAsStream(path);
            if (is == null) return -1;

            byte[] bytes = is.readAllBytes();
            is.close();

            ByteBuffer imageBuffer = ByteBuffer.allocateDirect(bytes.length);
            imageBuffer.put(bytes);
            imageBuffer.flip();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                IntBuffer comp = stack.mallocInt(1);

                return nvgCreateImageMem(getContext(), 0, imageBuffer);
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static void drawCircleOutline(float x, float y, float radius, float strokeWidth, Color color) {
        long vg = getContext();
        nvgBeginPath(vg);
        nvgCircle(vg, x, y, radius);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGColor nvgColor = NVGColor.malloc(stack);
            nvgRGBA((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha(), nvgColor);

            nvgStrokeWidth(vg, strokeWidth);
            nvgStrokeColor(vg, nvgColor);
            nvgStroke(vg);
        }
    }

    public static void save() {
        nvgSave(getContext());
    }

    public static void restore() {
        nvgRestore(getContext());
    }

    public static void translate(float x, float y) {
        nvgTranslate(getContext(), x, y);
    }

    public static void translate(long vg, float x, float y) {
        nvgTranslate(vg, x, y);
    }

    public static void scale(float x, float y) {
        nvgScale(getContext(), x, y);
    }

    public static void scale(long vg, float x, float y) {
        nvgScale(vg, x, y);
    }

    public static void rotate(long vg, float angle) {
        nvgRotate(vg, angle);
    }

    public static void globalAlpha(long vg, float alpha) {
        nvgGlobalAlpha(vg, alpha);
    }

    /**
     * 绘制图片
     *
     * @param imageId NanoVG 图片 ID
     * @param x       X 坐标
     * @param y       Y 坐标
     * @param width   宽度
     * @param height  高度
     * @param alpha   透明度 (0-1)
     */
    public static void drawTexture(int imageId, float x, float y, float width, float height, float alpha) {
        if (imageId == -1) return;

        long vg = getContext();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            // Fix: Pattern origin should match rect origin for simple drawing
            nvgImagePattern(vg, x, y, width, height, 0, imageId, alpha, paint);

            nvgBeginPath(vg);
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    public static void drawTexture(int imageId, float x, float y, float width, float height, float alpha,
                                   float scaleX, float scaleY, float rotation) {
        if (imageId == -1) return;

        long vg = getContext();

        nvgSave(vg);

        // 移动到中心点
        nvgTranslate(vg, x + width / 2f, y + height / 2f);
        // 应用旋转
        nvgRotate(vg, rotation);
        // 应用缩放
        nvgScale(vg, scaleX, scaleY);
        // 移回原点
        nvgTranslate(vg, -width / 2f, -height / 2f);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            nvgImagePattern(vg, 0, 0, width, height, 0, imageId, alpha, paint);

            nvgBeginPath(vg);
            nvgRect(vg, 0, 0, width, height);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }

        nvgRestore(vg);
    }

    public static void deleteTexture(int imageId) {
        if (imageId != -1) {
            long vg = getContext();
            nvgDeleteImage(vg, imageId);
        }
    }

    public static void drawLine(float x1, float y1, float x2, float y2, float width, Color color) {
        long vg = getContext();
        nvgBeginPath(vg);
        nvgMoveTo(vg, x1, y1);
        nvgLineTo(vg, x2, y2);
        NVGColor nvgColor = nvgColor(color);
        nvgStrokeColor(vg, nvgColor);
        nvgStrokeWidth(vg, width);
        nvgStroke(vg);
    }

    public static void scissor(float x, float y, float w, float h) {
        nvgScissor(getContext(), x, y, w, h);
    }

    public static void resetScissor() {
        nvgResetScissor(getContext());
    }

    public static void intersectScissor(float x, float y, float w, float h) {
        nvgIntersectScissor(getContext(), x, y, w, h);
    }
}
