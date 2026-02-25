package dev.sakura.client.gui.component;

import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.TranslationManager;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.values.impl.ColorValue;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;

public class AdvancedColorPicker {
    private final String label;
    private final ColorValue primaryColorValue;
    private ColorValue secondaryColorValue;
    private ColorValue activeColorValue;
    private boolean isSecondActive = false;

    private float toggleAnim = 0f;
    private long lastFrameTime = 0;

    private final int x, y, width, height;

    private boolean draggingHue = false;
    private boolean draggingSV = false;
    private boolean draggingAlpha = false;

    private enum EditField {NONE, R, G, B, A, HEX}

    private EditField activeField = EditField.NONE;
    private String inputBuffer = "";

    private static final int HEADER_HEIGHT = 40;
    private static final int PADDING = 10;
    private static final int SV_BOX_HEIGHT = 150;
    private static final int STRIP_HEIGHT = 12;
    private static final int SPACING = 8;

    private float[] hsb = new float[3];

    public AdvancedColorPicker(String label, ColorValue colorValue, int x, int y) {
        this.label = label;
        this.primaryColorValue = colorValue;
        this.activeColorValue = colorValue;
        this.x = x;
        this.y = y;
        this.width = 230;

        int contentHeight = HEADER_HEIGHT + SV_BOX_HEIGHT + SPACING + STRIP_HEIGHT + SPACING + STRIP_HEIGHT + SPACING + 85;
        this.height = contentHeight;

        Color c = activeColorValue.get();
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
    }

    public void setSecondColor(ColorValue secondColor) {
        this.secondaryColorValue = secondColor;
    }

    public void render(int mouseX, int mouseY) {
        long now = System.currentTimeMillis();
        if (lastFrameTime == 0) lastFrameTime = now;
        float delta = (now - lastFrameTime) / 1000f;
        lastFrameTime = now;

        float target = isSecondActive ? 1f : 0f;
        if (Math.abs(target - toggleAnim) > 0.001f) {
            toggleAnim += (target - toggleAnim) * delta * 10f;
        } else {
            toggleAnim = target;
        }

        if (!draggingHue && !draggingSV && !draggingAlpha) {
            Color c = activeColorValue.get();
            float[] currentHsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
            if (Math.abs(currentHsb[0] - hsb[0]) > 0.01 || Math.abs(currentHsb[1] - hsb[1]) > 0.01 || Math.abs(currentHsb[2] - hsb[2]) > 0.01) {
                hsb = currentHsb;
            }
        }

        // 背景
        NanoVGHelper.drawRoundRect(x, y, width, height, 8, new Color(20, 20, 20, 245));
        NanoVGHelper.drawRoundRectOutline(x, y, width, height, 8, 1, new Color(255, 255, 255, 30));

        if (secondaryColorValue != null && secondaryColorValue.isAvailable()) {
            drawToggle(x + width / 2 - 70, y + 8, 140, 24, mouseX, mouseY);
        } else {
            String labelText = TranslationManager.get(label);
            int labelFont = FontLoader.bold(16);
            NanoVGHelper.drawCenteredString(labelText, x + width / 2f, y + HEADER_HEIGHT / 2f + 4, labelFont, 16, Color.WHITE);
        }

        int currentY = y + HEADER_HEIGHT + 5;
        int contentX = x + PADDING;
        int contentWidth = width - (PADDING * 2);

        drawSVBox(contentX, currentY, contentWidth, SV_BOX_HEIGHT, hsb[0]);

        int hueY = currentY + SV_BOX_HEIGHT + SPACING;
        drawHueStrip(contentX, hueY, contentWidth, STRIP_HEIGHT);

        int alphaY = hueY + STRIP_HEIGHT + SPACING;
        drawAlphaStrip(contentX, alphaY, contentWidth, STRIP_HEIGHT, activeColorValue.get());

        drawIndicators(mouseX, mouseY, contentX, currentY, contentWidth, hueY, alphaY);

        drawInfo(x, alphaY + STRIP_HEIGHT + SPACING + 5, activeColorValue.get());

        if (draggingSV) {
            updateSV(mouseX, mouseY, contentX, currentY, contentWidth);
        } else if (draggingHue) {
            updateHue(mouseX, contentX, contentWidth);
        } else if (draggingAlpha) {
            updateAlpha(mouseX, contentX, contentWidth);
        }
    }

    private void drawToggle(int x, int y, int w, int h, int mouseX, int mouseY) {
        NanoVGHelper.drawRoundRect(x, y, w, h, h / 2f, new Color(40, 40, 40));
        NanoVGHelper.drawRoundRectOutline(x, y, w, h, h / 2f, 1, new Color(60, 60, 60));

        float sliderWidth = w / 2f;
        float sliderX = x + (w - sliderWidth) * toggleAnim;

        Color c1 = primaryColorValue.get();
        Color c2 = secondaryColorValue.get();

        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * toggleAnim);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * toggleAnim);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * toggleAnim);
        Color sliderColor = new Color(r, g, b);

        NanoVGHelper.drawRoundRect(sliderX, y, sliderWidth, h, h / 2f, sliderColor);

        int font = FontLoader.bold(12);

        boolean isLight = (0.299 * sliderColor.getRed() + 0.587 * sliderColor.getGreen() + 0.114 * sliderColor.getBlue()) > 128;
        Color activeTextColor = isLight ? new Color(30, 30, 30) : Color.WHITE;

        float textAlpha1 = 1.0f - toggleAnim;
        Color textColor1;

        if (toggleAnim < 0.5f) {
            int alpha = (int) (255 * (1.0f - toggleAnim));
            textColor1 = new Color(activeTextColor.getRed(), activeTextColor.getGreen(), activeTextColor.getBlue(), Math.max(0, Math.min(255, alpha)));
        } else {
            int alpha = (int) (255 * (toggleAnim - 0.5f) * 2);
            textColor1 = new Color(180, 180, 180, Math.max(0, Math.min(255, alpha)));
        }

        Color inactiveColor = new Color(180, 180, 180);

        Color cMain = ColorUtil.interpolateColor(activeTextColor, inactiveColor, toggleAnim);
        Color cSecond = ColorUtil.interpolateColor(inactiveColor, activeTextColor, toggleAnim);

        if (!isLight) {
            NanoVGHelper.drawCenteredString(TranslationManager.get("theme.main"), x + w / 4f + 0.5f, y + h / 2f + 0.5f, font, 12, new Color(0, 0, 0, 100));
            NanoVGHelper.drawCenteredString(TranslationManager.get("theme.second"), x + 3 * w / 4f + 0.5f, y + h / 2f + 0.5f, font, 12, new Color(0, 0, 0, 100));
        }

        NanoVGHelper.drawCenteredString(TranslationManager.get("theme.main"), x + w / 4f, y + h / 2f, font, 12, cMain);
        NanoVGHelper.drawCenteredString(TranslationManager.get("theme.second"), x + 3 * w / 4f, y + h / 2f, font, 12, cSecond);
    }

    private void drawSVBox(float x, float y, float w, float h, float hue) {
        long vg = NanoVGRenderer.INSTANCE.getContext();

        Color baseColor = Color.getHSBColor(hue, 1.0f, 1.0f);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor c1 = NVGColor.malloc(stack);
            NVGColor c2 = NVGColor.malloc(stack);

            setNVGColor(c1, Color.WHITE);
            setNVGColor(c2, baseColor);

            nvgLinearGradient(vg, x, y, x + w, y, c1, c2, paint);
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgFillPaint(vg, paint);
            nvgFill(vg);

            setNVGColor(c1, 0, 0, 0, 0);
            setNVGColor(c2, Color.BLACK);

            nvgLinearGradient(vg, x, y, x, y + h, c1, c2, paint);
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    private void drawHueStrip(float x, float y, float w, float h) {
        NanoVGHelper.save();
        long vg = NanoVGRenderer.INSTANCE.getContext();

        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);
        nvgClosePath(vg);
        nvgScissor(vg, x, y, w, h);

        int segments = 64;
        for (int i = 0; i < segments; i++) {
            float h1 = (float) i / segments;
            float h2 = (float) (i + 1) / segments;
            Color c1 = Color.getHSBColor(h1, 1f, 1f);
            Color c2 = Color.getHSBColor(h2, 1f, 1f);

            float x1 = x + (i * w) / segments;
            float x2 = x + ((i + 1) * w) / segments;

            drawGradientRect(x1, y, x2 - x1 + 1, h, c1, c2, true);
        }

        nvgResetScissor(vg);
        NanoVGHelper.restore();

        NanoVGHelper.drawRectOutline(x, y, w, h, 1, new Color(255, 255, 255, 50));
    }

    private void drawAlphaStrip(float x, float y, float w, float h, Color c) {
        NanoVGHelper.save();
        long vg = NanoVGRenderer.INSTANCE.getContext();

        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);
        nvgClosePath(vg);
        nvgScissor(vg, x, y, w, h);

        NanoVGHelper.drawRect(x, y, w, h, Color.GRAY);

        Color left = new Color(c.getRed(), c.getGreen(), c.getBlue(), 0);
        Color right = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
        drawGradientRect(x, y, w, h, left, right, true);

        nvgResetScissor(vg);
        NanoVGHelper.restore();

        NanoVGHelper.drawRectOutline(x, y, w, h, 1, new Color(255, 255, 255, 50));
    }

    private void drawGradientRect(float x, float y, float w, float h, Color start, Color end, boolean horizontal) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor c1 = NVGColor.malloc(stack);
            NVGColor c2 = NVGColor.malloc(stack);

            setNVGColor(c1, start);
            setNVGColor(c2, end);

            nvgLinearGradient(vg, x, y, horizontal ? x + w : x, horizontal ? y : y + h, c1, c2, paint);
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    private void setNVGColor(NVGColor nvgColor, Color color) {
        nvgRGBA((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha(), nvgColor);
    }

    private void setNVGColor(NVGColor nvgColor, int r, int g, int b, int a) {
        nvgRGBA((byte) r, (byte) g, (byte) b, (byte) a, nvgColor);
    }

    private void drawIndicators(int mouseX, int mouseY, int startX, int startY, int width, int hueY, int alphaY) {
        float[] drawHsb = new float[3];
        int drawAlpha;

        if (secondaryColorValue != null && secondaryColorValue.isAvailable()) {
            float[] hsb1 = new float[3];
            Color c1 = primaryColorValue.get();
            Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), hsb1);
            int a1 = c1.getAlpha();

            float[] hsb2 = new float[3];
            Color c2 = secondaryColorValue.get();
            Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), hsb2);
            int a2 = c2.getAlpha();

            drawHsb[0] = hsb1[0] + (hsb2[0] - hsb1[0]) * toggleAnim;
            drawHsb[1] = hsb1[1] + (hsb2[1] - hsb1[1]) * toggleAnim;
            drawHsb[2] = hsb1[2] + (hsb2[2] - hsb1[2]) * toggleAnim;
            drawAlpha = (int) (a1 + (a2 - a1) * toggleAnim);
        } else {
            drawHsb[0] = hsb[0];
            drawHsb[1] = hsb[1];
            drawHsb[2] = hsb[2];
            drawAlpha = activeColorValue.get().getAlpha();
        }

        float svX = startX + drawHsb[1] * width;
        float svY = startY + (1 - drawHsb[2]) * 150;

        NanoVGHelper.drawCircle(svX, svY, 5, new Color(0, 0, 0, 100));
        NanoVGHelper.drawCircleOutline(svX, svY, 4, 2f, Color.WHITE);

        float hueX = startX + drawHsb[0] * width;
        NanoVGHelper.drawRect(hueX - 2, hueY - 2, 4, 12 + 4, Color.WHITE);

        float alphaX = startX + (drawAlpha / 255f) * width;
        NanoVGHelper.drawRect(alphaX - 2, alphaY - 2, 4, 12 + 4, Color.WHITE);
    }

    private void drawInfo(int x, int y, Color c) {
        int boxHeight = 18;
        int width = 230;
        int contentWidth = width - 20; // 210
        int startX = x + 10;

        String hexLabel = "Hex:";
        int labelFont = FontLoader.bold(14);
        float labelWidth = NanoVGHelper.getTextWidth(hexLabel, labelFont, 14);

        int hexInputWidth = 100;
        int hexGap = 10;

        float totalHexGroupWidth = labelWidth + hexGap + hexInputWidth;
        float hexGroupStartX = startX + (contentWidth - totalHexGroupWidth) / 2f;

        NanoVGHelper.drawString(hexLabel, hexGroupStartX, y + boxHeight / 2f + 4, labelFont, 14, Color.LIGHT_GRAY);
        drawInputBox((int) (hexGroupStartX + labelWidth + hexGap), y, hexInputWidth, boxHeight, "", 0, EditField.HEX);

        int rgbaY = y + boxHeight + 20;
        int rgbaInputWidth = 45;
        int rgbaGap = (contentWidth - (rgbaInputWidth * 4)) / 3;

        // R
        drawInputBox(startX, rgbaY, rgbaInputWidth, boxHeight, "R", c.getRed(), EditField.R);
        // G
        drawInputBox(startX + rgbaInputWidth + rgbaGap, rgbaY, rgbaInputWidth, boxHeight, "G", c.getGreen(), EditField.G);
        // B
        drawInputBox(startX + (rgbaInputWidth + rgbaGap) * 2, rgbaY, rgbaInputWidth, boxHeight, "B", c.getBlue(), EditField.B);
        // A
        drawInputBox(startX + (rgbaInputWidth + rgbaGap) * 3, rgbaY, rgbaInputWidth, boxHeight, "A", c.getAlpha(), EditField.A);
    }

    private void drawInputBox(int x, int y, int w, int h, String label, int value, EditField field) {
        boolean active = activeField == field;

        if (label != null && !label.isEmpty()) {
            int labelFont = FontLoader.bold(12);
            float labelWidth = NanoVGHelper.getTextWidth(label, labelFont, 12);
            NanoVGHelper.drawString(label, x + (w - labelWidth) / 2f, y - 5, labelFont, 12, new Color(200, 200, 200));
        }

        Color bg = active ? new Color(60, 60, 60) : new Color(40, 40, 40);
        Color border = active ? new Color(100, 100, 255) : new Color(80, 80, 80);

        NanoVGHelper.drawRect(x, y, w, h, bg);
        NanoVGHelper.drawRectOutline(x, y, w, h, 1, border);

        String text;
        if (active) {
            text = inputBuffer;
        } else {
            if (field == EditField.HEX) {
                Color c = activeColorValue.get();
                text = String.format("#%02X%02X%02X%02X", c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue());
            } else {
                text = String.valueOf(value);
            }
        }

        int font = FontLoader.regular(14);

        NanoVGHelper.drawCenteredString(text, x + w / 2f, y + h / 2f + 1, font, 14, Color.WHITE);

        if (active && (System.currentTimeMillis() / 500) % 2 == 0) {
            float textWidth = NanoVGHelper.getTextWidth(text, font, 14);
            float cursorX = x + (w + textWidth) / 2f + 1;
            NanoVGHelper.drawRect(cursorX, y + 4, 1, h - 8, Color.WHITE);
        }
    }

    private void updateSV(int mouseX, int mouseY, int startX, int startY, int width) {
        float s = (mouseX - startX) / (float) width;
        float v = 1 - (mouseY - startY) / 150f;
        hsb[1] = Math.max(0, Math.min(1, s));
        hsb[2] = Math.max(0, Math.min(1, v));
        updateColorValue();
    }

    private void updateHue(int mouseX, int startX, int width) {
        float h = (mouseX - startX) / (float) width;
        hsb[0] = Math.max(0, Math.min(1, h));
        updateColorValue();
    }

    private void updateAlpha(int mouseX, int startX, int width) {
        float a = (mouseX - startX) / (float) width;
        a = Math.max(0, Math.min(1, a));
        Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        activeColorValue.set(new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (a * 255)));
    }

    private void updateColorValue() {
        Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        int alpha = activeColorValue.get().getAlpha();
        activeColorValue.set(new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return false;

        activeField = EditField.NONE;

        if (secondaryColorValue != null && secondaryColorValue.isAvailable()) {
            int toggleW = 140;
            int toggleH = 24;
            int toggleX = x + width / 2 - 70;
            int toggleY = y + 8;

            if (isHovered(mouseX, mouseY, toggleX, toggleY, toggleW, toggleH)) {
                isSecondActive = !isSecondActive;
                activeColorValue = isSecondActive ? secondaryColorValue : primaryColorValue;

                Color c = activeColorValue.get();
                Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
                return true;
            }
        }

        int headerHeight = 40;
        int padding = 10;
        int svBoxHeight = 150;
        int stripHeight = 12;
        int spacing = 8;

        int currentY = y + headerHeight + 5;
        int contentX = x + padding;
        int contentWidth = width - (padding * 2);

        if (isHovered(mouseX, mouseY, contentX, currentY, contentWidth, svBoxHeight)) {
            draggingSV = true;
            return true;
        }

        int hueY = currentY + svBoxHeight + spacing;
        if (isHovered(mouseX, mouseY, contentX, hueY, contentWidth, stripHeight)) {
            draggingHue = true;
            return true;
        }

        int alphaY = hueY + stripHeight + spacing;
        if (isHovered(mouseX, mouseY, contentX, alphaY, contentWidth, stripHeight)) {
            draggingAlpha = true;
            return true;
        }

        int boxHeight = 18;
        int inputsY = alphaY + stripHeight + spacing + 5;

        String hexLabel = "Hex:";
        int labelFont = FontLoader.bold(14);
        float labelWidth = NanoVGHelper.getTextWidth(hexLabel, labelFont, 14);
        int hexInputWidth = 100;
        int hexGap = 10;

        float totalHexGroupWidth = labelWidth + hexGap + hexInputWidth;
        float hexGroupStartX = contentX + (contentWidth - totalHexGroupWidth) / 2f;
        int hexBoxX = (int) (hexGroupStartX + labelWidth + hexGap);

        if (checkInputClick(mouseX, mouseY, hexBoxX, inputsY, hexInputWidth, boxHeight, EditField.HEX)) return true;

        int rgbaY = inputsY + boxHeight + 25;
        int rgbaInputWidth = 45;
        int rgbaGap = (contentWidth - (rgbaInputWidth * 4)) / 3;

        if (checkInputClick(mouseX, mouseY, contentX, rgbaY, rgbaInputWidth, boxHeight, EditField.R)) return true;

        if (checkInputClick(mouseX, mouseY, contentX + rgbaInputWidth + rgbaGap, rgbaY, rgbaInputWidth, boxHeight, EditField.G))
            return true;

        if (checkInputClick(mouseX, mouseY, contentX + (rgbaInputWidth + rgbaGap) * 2, rgbaY, rgbaInputWidth, boxHeight, EditField.B))
            return true;

        if (checkInputClick(mouseX, mouseY, contentX + (rgbaInputWidth + rgbaGap) * 3, rgbaY, rgbaInputWidth, boxHeight, EditField.A))
            return true;

        return false;
    }

    private boolean checkInputClick(double mouseX, double mouseY, int x, int y, int w, int h, EditField field) {
        if (isHovered(mouseX, mouseY, x, y, w, h)) {
            activeField = field;
            Color c = activeColorValue.get();
            switch (field) {
                case R -> inputBuffer = String.valueOf(c.getRed());
                case G -> inputBuffer = String.valueOf(c.getGreen());
                case B -> inputBuffer = String.valueOf(c.getBlue());
                case A -> inputBuffer = String.valueOf(c.getAlpha());
                case HEX ->
                        inputBuffer = String.format("#%02X%02X%02X%02X", c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue());
            }
            return true;
        }
        return false;
    }

    public boolean keyPressed(int keyCode) {
        if (activeField == EditField.NONE) return false;

        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) {
            applyInput();
            activeField = EditField.NONE;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (!inputBuffer.isEmpty()) {
                inputBuffer = inputBuffer.substring(0, inputBuffer.length() - 1);
            }
            return true;
        }

        return false;
    }

    public boolean charTyped(char chr) {
        if (activeField == EditField.NONE) return false;

        if (activeField == EditField.HEX) {
            String validChars = "0123456789ABCDEFabcdef#";
            if (validChars.indexOf(chr) != -1) {
                if (chr == '#' && inputBuffer.contains("#")) return false;
                if (inputBuffer.length() >= 9) return false;

                inputBuffer += chr;
                return true;
            }
            return false;
        }

        if (Character.isDigit(chr)) {
            String newBuffer = inputBuffer + chr;
            try {
                int val = Integer.parseInt(newBuffer);
                if (val >= 0 && val <= 255) {
                    inputBuffer = newBuffer;
                }
            } catch (NumberFormatException ignored) {
            }
            return true;
        }

        return false;
    }

    private void applyInput() {
        if (inputBuffer.isEmpty()) return;

        if (activeField == EditField.HEX) {
            try {
                String hex = inputBuffer;
                if (hex.startsWith("#")) hex = hex.substring(1);

                long val;
                if (hex.length() == 6) {
                    val = Long.parseLong(hex, 16);
                    val |= 0xFF000000;
                } else if (hex.length() == 8) {
                    val = Long.parseLong(hex, 16);
                } else {
                    return;
                }

                int a = (int) ((val >> 24) & 0xFF);
                int r = (int) ((val >> 16) & 0xFF);
                int g = (int) ((val >> 8) & 0xFF);
                int b = (int) (val & 0xFF);

                Color newColor = new Color(r, g, b, a);
                activeColorValue.set(newColor);
                Color.RGBtoHSB(r, g, b, hsb);
            } catch (Exception ignored) {
            }
            return;
        }

        try {
            int val = Integer.parseInt(inputBuffer);
            val = Math.max(0, Math.min(255, val));

            Color c = activeColorValue.get();
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            int a = c.getAlpha();

            switch (activeField) {
                case R -> r = val;
                case G -> g = val;
                case B -> b = val;
                case A -> a = val;
            }

            Color newColor = new Color(r, g, b, a);
            activeColorValue.set(newColor);

            Color.RGBtoHSB(r, g, b, hsb);

        } catch (NumberFormatException ignored) {
        }
    }

    public void mouseReleased() {
        draggingHue = false;
        draggingSV = false;
        draggingAlpha = false;
    }

    private boolean isHovered(double mouseX, double mouseY, double x, double y, double w, double h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
