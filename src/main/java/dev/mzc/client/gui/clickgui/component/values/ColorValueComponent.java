package dev.mzc.client.gui.clickgui.component.values;

import dev.mzc.client.gui.Component;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Animation;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseOutSine;
import dev.mzc.client.values.impl.ColorValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

public class ColorValueComponent extends Component {
    private final ColorValue setting;
    private final Animation open = new EaseOutSine(250, 1);
    private boolean opened, pickingHue, pickingOthers;
    private boolean pickingR, pickingG, pickingB, pickingA;

    private enum EditField {NONE, R, G, B, A}

    private EditField editField = EditField.NONE;
    private String tempText = "";
    private int cursorPos = 0;
    private long lastBlinkTime = 0;
    private boolean cursorVisible = true;

    public ColorValueComponent(ColorValue setting) {
        this.setting = setting;
        open.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        open.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        float fontHeight = NanoVGHelper.getFontHeight(FontLoader.regular(titleFontSize), titleFontSize);

        float collapsedHeight = 14 * scale;
        float padding = 2 * scale;
        float gap = 3 * scale;
        float panelHeight = 52 * scale;
        float hueBarWidth = 10 * scale;
        float rowGap = 3 * scale;
        float rowHeight = 12 * scale;
        int rows = setting.allowAlpha() ? 4 : 3;
        float bottomSpacing = 6 * scale;

        float totalExpandedHeight = (fontHeight + 2 * scale) + panelHeight + 6 * scale + rows * rowHeight + (rows - 1) * rowGap + 2 * scale + bottomSpacing;

        this.setHeight(collapsedHeight + (Math.max(0, totalExpandedHeight - collapsedHeight) * open.getOutput().floatValue()));
        final float[] hsb = new float[]{setting.getHue(), setting.getSaturation(), setting.getBrightness()};

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlinkTime > 530) {
            cursorVisible = !cursorVisible;
            lastBlinkTime = currentTime;
        }

        Color currentColor = setting.get();
        int r = currentColor.getRed();
        int g = currentColor.getGreen();
        int b = currentColor.getBlue();

        NanoVGRenderer.INSTANCE.draw(canvas -> {
            NanoVGHelper.drawString(setting.getDisplayName(), getX(), getY(), FontLoader.regular(titleFontSize), titleFontSize, new Color(255, 255, 255, 255));

            NanoVGHelper.drawCircle(getX() + getWidth() - 5 * scale, getY() - 3 * scale, 4 * scale, setting.get());

            if (opened || open.getOutput() > 0) {
                NanoVGHelper.save();
                NanoVGHelper.intersectScissor(getX(), getY(), getWidth(), getHeight());

                float contentX = getX() + padding;
                float contentY = getY() + fontHeight + 2 * scale;
                float contentWidth = getWidth() - 2 * padding;

                float panelWidth = contentWidth - hueBarWidth - gap;
                float panelX = contentX;
                float panelY = contentY;
                float hueX = panelX + panelWidth + gap;
                float hueY = panelY;

                float radius = 2.5f * scale;
                drawRoundedGradientRect3(panelX, panelY, panelWidth, panelHeight, radius,
                        Color.getHSBColor(0, 0, 0),
                        Color.getHSBColor(0, 0, 1),
                        Color.getHSBColor(0, 0, 0),
                        Color.getHSBColor(hsb[0], 1, 1));
                NanoVGHelper.drawRoundRectOutline(panelX, panelY, panelWidth, panelHeight, radius, 0.75f * scale, new Color(0, 0, 0, 120));

                drawVerticalHueBar(hueX, hueY, hueBarWidth, panelHeight, hsb[0], radius);

                float pickerY = panelY + (panelHeight * (1 - hsb[2]));
                float pickerX = panelX + (panelWidth * hsb[1] - 1);
                pickerY = Math.max(Math.min(panelY + panelHeight - 2, pickerY), panelY - 2);
                pickerX = Math.max(Math.min(panelX + panelWidth - 2, pickerX), panelX - 2);

                if (pickingHue) {
                    setting.setHue(clamp01((mouseY - hueY) / panelHeight));
                }

                if (pickingOthers) {
                    setting.setBrightness(clamp01(1 - ((mouseY - panelY) / panelHeight)));
                    setting.setSaturation(clamp01((mouseX - panelX) / panelWidth));
                }

                float rowsStartY = panelY + panelHeight + 6 * scale;
                float rowX = contentX;
                float rowWidth = contentWidth;
                float labelWidth = 10 * scale;
                float inputWidth = 30 * scale;
                float labelPad = 3 * scale;
                float betweenGap = 4 * scale;
                float sliderX = rowX + labelWidth + labelPad;
                float inputX = rowX + rowWidth - inputWidth;
                float sliderWidth = Math.max(0.0f, inputX - betweenGap - sliderX);

                float alphaValue = setting.getAlpha();

                float rY = rowsStartY;
                float gY = rY + rowHeight + rowGap;
                float bY = gY + rowHeight + rowGap;
                float aY = bY + rowHeight + rowGap;

                drawChannelRow(rowX, rY, rowWidth, labelWidth, sliderX, sliderWidth, inputWidth, rowHeight, "R", EditField.R, r, 0, 255, new Color(0, g, b), new Color(255, g, b));
                drawChannelRow(rowX, gY, rowWidth, labelWidth, sliderX, sliderWidth, inputWidth, rowHeight, "G", EditField.G, g, 0, 255, new Color(r, 0, b), new Color(r, 255, b));
                drawChannelRow(rowX, bY, rowWidth, labelWidth, sliderX, sliderWidth, inputWidth, rowHeight, "B", EditField.B, b, 0, 255, new Color(r, g, 0), new Color(r, g, 255));

                if (setting.allowAlpha()) {
                    int a = Math.max(0, Math.min(255, Math.round(alphaValue * 255.0f)));
                    drawAlphaRow(rowX, aY, rowWidth, labelWidth, sliderX, sliderWidth, inputWidth, rowHeight, "A", EditField.A, a, new Color(r, g, b));
                }

                if (pickingR) {
                    int newR = Math.round(255.0f * clamp01((mouseX - sliderX) / sliderWidth));
                    setColorRGB(newR, g, b);
                }
                if (pickingG) {
                    int newG = Math.round(255.0f * clamp01((mouseX - sliderX) / sliderWidth));
                    setColorRGB(r, newG, b);
                }
                if (pickingB) {
                    int newB = Math.round(255.0f * clamp01((mouseX - sliderX) / sliderWidth));
                    setColorRGB(r, g, newB);
                }
                if (pickingA && setting.allowAlpha()) {
                    float newAlpha = clamp01((mouseX - sliderX) / sliderWidth);
                    setting.setAlpha(newAlpha);
                }

                NanoVGHelper.drawRect(pickerX, pickerY, 2, 2, new Color(255, 255, 255));

                NanoVGHelper.restore();
            }
        });
    }

    private void drawVerticalHueBar(float x, float y, float width, float height, float hue, float radius) {
        NanoVGHelper.drawRoundRect(x, y, width, height, radius, new Color(25, 25, 25));
        float inset = 1.0f * scale;
        float innerX = x + inset;
        float innerY = y + inset;
        float innerW = Math.max(0.0f, width - inset * 2);
        float innerH = Math.max(0.0f, height - inset * 2);

        if (innerW > 0.0f && innerH > 0.0f) {
            int segments = Math.max(64, Math.round(96.0f * scale));
            float segH = innerH / segments;
            float innerRadius = Math.max(0.0f, radius - inset);
            long vg = NanoVGRenderer.INSTANCE.getContext();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                NVGPaint paint = NVGPaint.malloc(stack);
                NVGColor c1 = NVGColor.malloc(stack);
                NVGColor c2 = NVGColor.malloc(stack);

                for (int i = 0; i < segments; i++) {
                    float h1 = i / (float) segments;
                    float h2 = (i + 1) / (float) segments;

                    Color col1 = Color.getHSBColor(h1, 1f, 1f);
                    Color col2 = Color.getHSBColor(h2, 1f, 1f);
                    NanoVG.nvgRGBA((byte) col1.getRed(), (byte) col1.getGreen(), (byte) col1.getBlue(), (byte) 255, c1);
                    NanoVG.nvgRGBA((byte) col2.getRed(), (byte) col2.getGreen(), (byte) col2.getBlue(), (byte) 255, c2);

                    float y0 = innerY + i * segH;
                    float y1 = (i == segments - 1) ? (innerY + innerH) : (y0 + segH);
                    float hSeg = (y1 - y0) + 0.75f * scale;

                    NanoVG.nvgLinearGradient(vg, innerX, y0, innerX, y0 + hSeg, c1, c2, paint);
                    NanoVG.nvgBeginPath(vg);

                    if (innerRadius > 0.0f) {
                        if (i == 0) {
                            NanoVG.nvgRoundedRectVarying(vg, innerX, y0, innerW, hSeg, innerRadius, innerRadius, 0.0f, 0.0f);
                        } else if (i == segments - 1) {
                            NanoVG.nvgRoundedRectVarying(vg, innerX, y0, innerW, hSeg, 0.0f, 0.0f, innerRadius, innerRadius);
                        } else {
                            NanoVG.nvgRect(vg, innerX, y0 - 0.5f, innerW, hSeg + 1.0f);
                        }
                    } else {
                        NanoVG.nvgRect(vg, innerX, y0 - 0.5f, innerW, hSeg + 1.0f);
                    }

                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgFill(vg);
                }
            }
        }
        NanoVGHelper.drawRoundRectOutline(x, y, width, height, radius, 0.75f * scale, new Color(0, 0, 0, 120));

        float handleY = y + hue * height;
        handleY = Math.max(y + 1, Math.min(y + height - 1, handleY));
        NanoVGHelper.drawRect(x - 1 * scale, handleY - 0.5f * scale, width + 2 * scale, 1.25f * scale, Color.WHITE);
        NanoVGHelper.drawRect(x - 1 * scale, handleY + 0.75f * scale, width + 2 * scale, 0.75f * scale, new Color(0, 0, 0, 100));
    }

    private void drawRoundedGradientRect3(float x, float y, float w, float h, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        float r = Math.max(0.0f, Math.min(radius, Math.min(w, h) / 2f));
        if (w <= 0.0f || h <= 0.0f) return;

        int strips = Math.max(64, Math.round(80.0f * scale));
        float stripH = h / strips;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor c1 = NVGColor.malloc(stack);
            NVGColor c2 = NVGColor.malloc(stack);

            for (int i = 0; i < strips; i++) {
                float y0 = y + i * stripH;
                float y1 = (i == strips - 1) ? (y + h) : (y0 + stripH);
                float yMid = (y0 + y1) * 0.5f;
                float t = (yMid - y) / h;

                float inset = roundedInsetAtY(yMid - y, h, r);
                float x0 = x + inset;
                float w0 = w - inset * 2f;
                if (w0 <= 0.0f) continue;

                Color leftColor = lerpColor(topLeft, bottomLeft, t);
                Color rightColor = lerpColor(topRight, bottomRight, t);

                NanoVG.nvgRGBA((byte) leftColor.getRed(), (byte) leftColor.getGreen(), (byte) leftColor.getBlue(), (byte) leftColor.getAlpha(), c1);
                NanoVG.nvgRGBA((byte) rightColor.getRed(), (byte) rightColor.getGreen(), (byte) rightColor.getBlue(), (byte) rightColor.getAlpha(), c2);

                float segH = (y1 - y0) + 1.0f;
                NanoVG.nvgLinearGradient(vg, x0, y0, x0 + w0, y0, c1, c2, paint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, x0, y0 - 0.5f, w0, segH + 1.0f);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);
            }
        }
    }

    private float roundedInsetAtY(float yFromTop, float height, float radius) {
        if (radius <= 0.0f) return 0.0f;
        float dyTop = Math.max(0.0f, Math.min(radius, yFromTop));
        float dyBottom = Math.max(0.0f, Math.min(radius, height - yFromTop));
        float dy = Math.min(dyTop, dyBottom);
        if (dy >= radius) return 0.0f;
        float v = radius - dy;
        float inside = Math.max(0.0f, radius * radius - v * v);
        return radius - (float) Math.sqrt(inside);
    }

    private Color lerpColor(Color a, Color b, float t) {
        float tt = Math.max(0.0f, Math.min(1.0f, t));
        int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * tt);
        int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * tt);
        int bl = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * tt);
        int al = Math.round(a.getAlpha() + (b.getAlpha() - a.getAlpha()) * tt);
        return new Color(r, g, bl, al);
    }

    private void drawChannelRow(float rowX, float rowY, float rowWidth, float labelWidth, float sliderX, float sliderWidth, float inputWidth, float rowHeight,
                                String label, EditField field, int value, int min, int max, Color startColor, Color endColor) {
        float barHeight = 6 * scale;
        float barY = rowY + (rowHeight - barHeight) / 2f;
        float radius = 2.5f * scale;

        float labelFontSize = (float) ClickGui.getFontSize() * 0.6f;
        NanoVGHelper.drawString(label, rowX, rowY + rowHeight / 2f + 2 * scale, FontLoader.regular(labelFontSize), labelFontSize, new Color(210, 210, 210));

        NanoVGHelper.drawRoundRect(sliderX, barY, sliderWidth, barHeight, radius, new Color(20, 20, 20, 180));
        NanoVGHelper.drawGradientRRect2(sliderX, barY, sliderWidth, barHeight, radius, startColor, endColor);

        float t = (value - min) / (float) (max - min);
        float filled = sliderWidth * clamp01(t);
        drawRightMask(sliderX, barY, sliderWidth, barHeight, radius, filled, new Color(0, 0, 0, 120));

        float handleX = sliderX + filled;
        handleX = Math.max(sliderX + 1, Math.min(sliderX + sliderWidth - 1, handleX));
        NanoVGHelper.drawCircle(handleX, barY + barHeight / 2f, 3.25f * scale, new Color(255, 255, 255));
        NanoVGHelper.drawCircleOutline(handleX, barY + barHeight / 2f, 3.25f * scale, 0.75f * scale, new Color(0, 0, 0, 120));

        float inputX = rowX + rowWidth - inputWidth;
        float inputHeight = 12 * scale;
        float inputY = rowY + (rowHeight - inputHeight) / 2f;

        if (editField == field) {
            NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale, new Color(60, 60, 80));
            NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.75f * scale, new Color(100, 100, 150));

            float textFontSize = (float) ClickGui.getFontSize() * 0.6f;
            float textWidth = NanoVGHelper.getTextWidth(tempText, FontLoader.regular(textFontSize), textFontSize);
            float textX = inputX + (inputWidth - textWidth) / 2f;
            float textY = inputY + inputHeight / 2f + 2 * scale;
            NanoVGHelper.drawString(tempText, textX, textY, FontLoader.regular(textFontSize), textFontSize, Color.WHITE);

            if (cursorVisible) {
                float underlineY = inputY + inputHeight - 2.25f * scale;
                float maxW = Math.max(1 * scale, inputWidth - 4 * scale);
                float underlineW = Math.max(6 * scale, Math.min(maxW, textWidth));
                float underlineX = inputX + (inputWidth - underlineW) / 2f;
                NanoVGHelper.drawRect(underlineX, underlineY, underlineW, 0.75f * scale, Color.WHITE);
            }
        } else {
            NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale, new Color(30, 30, 30, 200));
            NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.75f * scale, new Color(0, 0, 0, 120));
            float textFontSize = (float) ClickGui.getFontSize() * 0.6f;
            String text = String.valueOf(value);
            float textWidth = NanoVGHelper.getTextWidth(text, FontLoader.regular(textFontSize), textFontSize);
            NanoVGHelper.drawString(text, inputX + (inputWidth - textWidth) / 2f, inputY + inputHeight / 2f + 2 * scale, FontLoader.regular(textFontSize), textFontSize, new Color(230, 230, 230));
        }
    }

    private void drawAlphaRow(float rowX, float rowY, float rowWidth, float labelWidth, float sliderX, float sliderWidth, float inputWidth, float rowHeight,
                              String label, EditField field, int value, Color baseRgb) {
        float barHeight = 6 * scale;
        float barY = rowY + (rowHeight - barHeight) / 2f;
        float radius = 2.5f * scale;

        float labelFontSize = (float) ClickGui.getFontSize() * 0.6f;
        NanoVGHelper.drawString(label, rowX, rowY + rowHeight / 2f + 2 * scale, FontLoader.regular(labelFontSize), labelFontSize, new Color(210, 210, 210));

        NanoVGHelper.drawRoundRect(sliderX, barY, sliderWidth, barHeight, radius, new Color(20, 20, 20, 180));
        float inset = 1.0f * scale;
        drawCheckerboardRounded(sliderX + inset, barY + inset, Math.max(0.0f, sliderWidth - inset * 2), Math.max(0.0f, barHeight - inset * 2), Math.max(0.0f, radius - inset));
        NanoVGHelper.drawGradientRRect2(sliderX, barY, sliderWidth, barHeight, radius,
                new Color(baseRgb.getRed(), baseRgb.getGreen(), baseRgb.getBlue(), 0),
                new Color(baseRgb.getRed(), baseRgb.getGreen(), baseRgb.getBlue(), 255));
        NanoVGHelper.drawRoundRectOutline(sliderX, barY, sliderWidth, barHeight, radius, 0.75f * scale, new Color(0, 0, 0, 120));

        float filled = sliderWidth * clamp01(value / 255.0f);
        drawRightMask(sliderX, barY, sliderWidth, barHeight, radius, filled, new Color(0, 0, 0, 120));

        float handleX = sliderX + filled;
        handleX = Math.max(sliderX + 1, Math.min(sliderX + sliderWidth - 1, handleX));
        NanoVGHelper.drawCircle(handleX, barY + barHeight / 2f, 3.25f * scale, new Color(255, 255, 255));
        NanoVGHelper.drawCircleOutline(handleX, barY + barHeight / 2f, 3.25f * scale, 0.75f * scale, new Color(0, 0, 0, 120));

        float inputX = rowX + rowWidth - inputWidth;
        float inputHeight = 12 * scale;
        float inputY = rowY + (rowHeight - inputHeight) / 2f;

        if (editField == field) {
            NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale, new Color(60, 60, 80));
            NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.75f * scale, new Color(100, 100, 150));

            float textFontSize = (float) ClickGui.getFontSize() * 0.6f;
            float textWidth = NanoVGHelper.getTextWidth(tempText, FontLoader.regular(textFontSize), textFontSize);
            float textX = inputX + (inputWidth - textWidth) / 2f;
            float textY = inputY + inputHeight / 2f + 2 * scale;
            NanoVGHelper.drawString(tempText, textX, textY, FontLoader.regular(textFontSize), textFontSize, Color.WHITE);

            if (cursorVisible) {
                float underlineY = inputY + inputHeight - 2.25f * scale;
                float maxW = Math.max(1 * scale, inputWidth - 4 * scale);
                float underlineW = Math.max(6 * scale, Math.min(maxW, textWidth));
                float underlineX = inputX + (inputWidth - underlineW) / 2f;
                NanoVGHelper.drawRect(underlineX, underlineY, underlineW, 0.75f * scale, Color.WHITE);
            }
        } else {
            NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale, new Color(30, 30, 30, 200));
            NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.75f * scale, new Color(0, 0, 0, 120));
            float textFontSize = (float) ClickGui.getFontSize() * 0.6f;
            String text = String.valueOf(value);
            float textWidth = NanoVGHelper.getTextWidth(text, FontLoader.regular(textFontSize), textFontSize);
            NanoVGHelper.drawString(text, inputX + (inputWidth - textWidth) / 2f, inputY + inputHeight / 2f + 2 * scale, FontLoader.regular(textFontSize), textFontSize, new Color(230, 230, 230));
        }
    }

    private void setColorRGB(int r, int g, int b) {
        float currentAlpha = setting.getAlpha();
        Color newColor = new Color(r, g, b, (int) (currentAlpha * 255));
        setting.set(newColor);
        setting.setAlpha(currentAlpha);
    }

    private void drawCheckerboard(float x, float y, float width, float height) {
        NanoVGHelper.drawRect(x, y, width, height, new Color(200, 200, 200));

        int squareSize = 4;
        boolean white = true;
        for (int i = 0; i < width; i += squareSize) {
            for (int j = 0; j < height; j += squareSize) {
                if (!white) {
                    Color color = new Color(150, 150, 150);
                    float drawWidth = Math.min(squareSize, width - i);
                    float drawHeight = Math.min(squareSize, height - j);

                    if (i > 2 && i < width - 2 || j > 0 && j < height - 0) {
                        NanoVGHelper.drawRect(x + i, y + j, drawWidth, drawHeight, color);
                    }
                }
                white = !white;
            }
            if (height / squareSize % 2 == 0) {
                white = !white;
            }
        }
    }

    private void drawCheckerboardRounded(float x, float y, float width, float height, float radius) {
        if (width <= 0.0f || height <= 0.0f) return;
        float r = Math.max(0.0f, Math.min(radius, Math.min(width, height) / 2f));

        int squareSize = 4;
        int cols = (int) Math.ceil(width / squareSize);
        int rows = (int) Math.ceil(height / squareSize);

        for (int cx = 0; cx < cols; cx++) {
            for (int cy = 0; cy < rows; cy++) {
                boolean dark = ((cx + cy) & 1) == 1;
                if (!dark) continue;

                float sx = x + cx * squareSize;
                float sy = y + cy * squareSize;
                float sw = Math.min(squareSize, width - cx * squareSize);
                float sh = Math.min(squareSize, height - cy * squareSize);
                if (sw <= 0.0f || sh <= 0.0f) continue;

                float px = sx + sw * 0.5f;
                float py = sy + sh * 0.5f;
                if (!insideRoundedRect(px, py, x, y, width, height, r)) continue;

                NanoVGHelper.drawRect(sx, sy, sw, sh, new Color(150, 150, 150));
            }
        }
    }

    private boolean insideRoundedRect(float px, float py, float x, float y, float w, float h, float r) {
        if (r <= 0.0f) return px >= x && px <= x + w && py >= y && py <= y + h;

        float ix = x + r;
        float iy = y + r;
        float ax = x + w - r;
        float ay = y + h - r;

        if (px >= ix && px <= ax && py >= y && py <= y + h) return true;
        if (py >= iy && py <= ay && px >= x && px <= x + w) return true;

        float dx, dy;

        dx = px - ix;
        dy = py - iy;
        if (dx * dx + dy * dy <= r * r) return true;

        dx = px - ax;
        dy = py - iy;
        if (dx * dx + dy * dy <= r * r) return true;

        dx = px - ix;
        dy = py - ay;
        if (dx * dx + dy * dy <= r * r) return true;

        dx = px - ax;
        dy = py - ay;
        return dx * dx + dy * dy <= r * r;
    }

    private void drawRightMask(float x, float y, float w, float h, float radius, float filled, Color mask) {
        float clampedFilled = Math.max(0.0f, Math.min(w, filled));
        if (clampedFilled >= w) return;

        float rx = x + clampedFilled;
        float rw = w - clampedFilled;
        float r = Math.max(0.0f, Math.min(radius, Math.min(w, h) / 2f));

        long vg = NanoVGRenderer.INSTANCE.getContext();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGColor c = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) mask.getRed(), (byte) mask.getGreen(), (byte) mask.getBlue(), (byte) mask.getAlpha(), c);

            NanoVG.nvgBeginPath(vg);
            if (clampedFilled <= 0.0f) {
                NanoVG.nvgRoundedRect(vg, x, y, w, h, r);
            } else if (r > 0.0f) {
                NanoVG.nvgRoundedRectVarying(vg, rx, y, rw, h, 0.0f, r, r, 0.0f);
            } else {
                NanoVG.nvgRect(vg, rx, y, rw, h);
            }
            NanoVG.nvgFillColor(vg, c);
            NanoVG.nvgFill(vg);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovering(getX() + getWidth() - 9 * scale, getY() - 7 * scale, 8 * scale, 8 * scale, mouseX, mouseY)) {
            opened = !opened;
            if (!opened) {
                editField = EditField.NONE;
            }
        }

        if (opened && open.getOutput() > 0.5) {
            float baseFontSize = (float) ClickGui.getFontSize();
            float titleFontSize = baseFontSize * 0.75f;
            float fontHeight = NanoVGHelper.getFontHeight(FontLoader.regular(titleFontSize), titleFontSize);

            float padding = 2 * scale;
            float gap = 3 * scale;
            float panelHeight = 52 * scale;
            float hueBarWidth = 10 * scale;
            float contentX = getX() + padding;
            float contentY = getY() + fontHeight + 2 * scale;
            float contentWidth = getWidth() - 2 * padding;
            float panelWidth = contentWidth - hueBarWidth - gap;
            float panelX = contentX;
            float panelY = contentY;
            float hueX = panelX + panelWidth + gap;
            float hueY = panelY;

            if (isHovering(panelX, panelY, panelWidth, panelHeight, mouseX, mouseY) && mouseButton == 0) {
                pickingOthers = true;
                editField = EditField.NONE;
            }

            if (isHovering(hueX, hueY, hueBarWidth, panelHeight, mouseX, mouseY) && mouseButton == 0) {
                pickingHue = true;
                editField = EditField.NONE;
            }

            float rowGap = 3 * scale;
            float rowHeight = 12 * scale;
            float rowsStartY = panelY + panelHeight + 6 * scale;
            float rowX = contentX;
            float rowWidth = contentWidth;
            float labelWidth = 10 * scale;
            float inputWidth = 30 * scale;
            float labelPad = 3 * scale;
            float betweenGap = 4 * scale;
            float sliderX = rowX + labelWidth + labelPad;
            float inputX = rowX + rowWidth - inputWidth;
            float sliderWidth = Math.max(0.0f, inputX - betweenGap - sliderX);
            float inputHeight = 12 * scale;

            Color currentColor = setting.get();
            float alphaValue = setting.getAlpha();
            int a = Math.max(0, Math.min(255, Math.round(alphaValue * 255.0f)));

            float rY = rowsStartY;
            float gY = rY + rowHeight + rowGap;
            float bY = gY + rowHeight + rowGap;
            float aY = bY + rowHeight + rowGap;

            float barHeight = 6 * scale;
            float barY = rY + (rowHeight - barHeight) / 2f;
            if (isHovering(sliderX, barY, sliderWidth, barHeight, mouseX, mouseY) && mouseButton == 0) {
                pickingR = true;
                editField = EditField.NONE;
            }
            barY = gY + (rowHeight - barHeight) / 2f;
            if (isHovering(sliderX, barY, sliderWidth, barHeight, mouseX, mouseY) && mouseButton == 0) {
                pickingG = true;
                editField = EditField.NONE;
            }
            barY = bY + (rowHeight - barHeight) / 2f;
            if (isHovering(sliderX, barY, sliderWidth, barHeight, mouseX, mouseY) && mouseButton == 0) {
                pickingB = true;
                editField = EditField.NONE;
            }
            if (setting.allowAlpha()) {
                barY = aY + (rowHeight - barHeight) / 2f;
                if (isHovering(sliderX, barY, sliderWidth, barHeight, mouseX, mouseY) && mouseButton == 0) {
                    pickingA = true;
                    editField = EditField.NONE;
                }
            }

            if (mouseButton == 0) {
                float inputY = rY + (rowHeight - inputHeight) / 2f;
                if (isHovering(inputX, inputY, inputWidth, inputHeight, mouseX, mouseY)) {
                    startEditing(EditField.R, currentColor.getRed());
                    return true;
                }
                inputY = gY + (rowHeight - inputHeight) / 2f;
                if (isHovering(inputX, inputY, inputWidth, inputHeight, mouseX, mouseY)) {
                    startEditing(EditField.G, currentColor.getGreen());
                    return true;
                }
                inputY = bY + (rowHeight - inputHeight) / 2f;
                if (isHovering(inputX, inputY, inputWidth, inputHeight, mouseX, mouseY)) {
                    startEditing(EditField.B, currentColor.getBlue());
                    return true;
                }
                if (setting.allowAlpha()) {
                    inputY = aY + (rowHeight - inputHeight) / 2f;
                    if (isHovering(inputX, inputY, inputWidth, inputHeight, mouseX, mouseY)) {
                        startEditing(EditField.A, a);
                        return true;
                    }
                }

                if (editField != EditField.NONE) {
                    finishEditing();
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void startEditing(EditField field, int value) {
        editField = field;
        tempText = String.valueOf(value);
        cursorPos = tempText.length();
        lastBlinkTime = System.currentTimeMillis();
        cursorVisible = true;
    }

    private void finishEditing() {
        if (editField == EditField.NONE) return;

        try {
            int value = Integer.parseInt(tempText.trim());
            value = Math.max(0, Math.min(255, value));

            Color currentColor = setting.get();
            int r = currentColor.getRed();
            int g = currentColor.getGreen();
            int b = currentColor.getBlue();

            switch (editField) {
                case R -> r = value;
                case G -> g = value;
                case B -> b = value;
                case A -> {
                    if (setting.allowAlpha()) {
                        setting.setAlpha(value / 255.0f);
                    }
                    editField = EditField.NONE;
                    return;
                }
            }

            setColorRGB(r, g, b);
        } catch (NumberFormatException ignored) {
        }

        editField = EditField.NONE;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        if (state == 0) {
            pickingHue = false;
            pickingOthers = false;
            pickingR = false;
            pickingG = false;
            pickingB = false;
            pickingA = false;
        }
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (editField == EditField.NONE) return false;

        switch (keyCode) {
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                finishEditing();
                return true;
            }
            case GLFW.GLFW_KEY_ESCAPE -> {
                editField = EditField.NONE;
                return true;
            }
            case GLFW.GLFW_KEY_BACKSPACE -> {
                if (cursorPos > 0) {
                    tempText = tempText.substring(0, cursorPos - 1) + tempText.substring(cursorPos);
                    cursorPos--;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_DELETE -> {
                if (cursorPos < tempText.length()) {
                    tempText = tempText.substring(0, cursorPos) + tempText.substring(cursorPos + 1);
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_LEFT -> {
                if (cursorPos > 0) {
                    cursorPos--;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                if (cursorPos < tempText.length()) {
                    cursorPos++;
                    resetCursor();
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (editField == EditField.NONE) return false;

        if (chr >= '0' && chr <= '9' && tempText.length() < 3) {
            tempText = tempText.substring(0, cursorPos) + chr + tempText.substring(cursorPos);
            cursorPos++;
            resetCursor();
            return true;
        }

        return false;
    }

    private void resetCursor() {
        lastBlinkTime = System.currentTimeMillis();
        cursorVisible = true;
    }

    @Override
    public boolean isVisible() {
        return this.setting.isAvailable();
    }

    private boolean isHovering(double x, double y, double width, double height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private float clamp01(double v) {
        return (float) Math.max(0.0, Math.min(1.0, v));
    }
}
