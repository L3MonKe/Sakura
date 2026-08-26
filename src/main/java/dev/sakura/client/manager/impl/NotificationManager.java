package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.hud.NotificationHud;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.utils.animations.Easing;
import org.joml.Matrix3x2fStack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;

public class NotificationManager {
    public enum RenderMode {
        Sakura,
        Trollhack
    }

    public enum Alignment {
        LEFT,
        RIGHT
    }

    private static final long DEFAULT_TIMEOUT = 2000L;
    private static final long MAX_TIMEOUT = 10000L;
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Map<Long, Notification> notificationMap = new HashMap<>();
    private static final long ANIMATION_TIME_MS = 200L;
    private static final Map<Character, Color> COLOR_CODES = new HashMap<>();

    static {
        COLOR_CODES.put('0', new Color(0, 0, 0));
        COLOR_CODES.put('1', new Color(0, 0, 170));
        COLOR_CODES.put('2', new Color(0, 170, 0));
        COLOR_CODES.put('3', new Color(0, 170, 170));
        COLOR_CODES.put('4', new Color(170, 0, 0));
        COLOR_CODES.put('5', new Color(170, 0, 170));
        COLOR_CODES.put('6', new Color(255, 170, 0));
        COLOR_CODES.put('7', new Color(170, 170, 170));
        COLOR_CODES.put('8', new Color(85, 85, 85));
        COLOR_CODES.put('9', new Color(85, 85, 255));
        COLOR_CODES.put('a', new Color(85, 255, 85));
        COLOR_CODES.put('b', new Color(85, 255, 255));
        COLOR_CODES.put('c', new Color(255, 85, 85));
        COLOR_CODES.put('d', new Color(255, 85, 255));
        COLOR_CODES.put('e', new Color(255, 255, 85));
        COLOR_CODES.put('f', new Color(255, 255, 255));
        COLOR_CODES.put('r', new Color(255, 255, 255));
    }

    public record Xylitol4Offsets(float titleOffsetX, float titleOffsetY, float iconOffsetX, float iconOffsetY,
                                  float descriptionOffsetX, float descriptionOffsetY, float lineOffsetY) {
        public static final Xylitol4Offsets ZERO = new Xylitol4Offsets(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }


    private static final float HARD_SHADOW_RANGE = 8.0f;
    private static final float HARD_SHADOW_STRENGTH = 0.6f;

    public enum ShadowMode {
        Solid,
        Gradient
    }

    public record ShadowSettings(boolean enabled, float range, float strength, ShadowMode mode) {
        public static final ShadowSettings DEFAULT = new ShadowSettings(true, HARD_SHADOW_RANGE, HARD_SHADOW_STRENGTH, ShadowMode.Solid);
    }

    public enum SakuraAnimationMode {
        Classic,
        Enhanced
    }

    public record SimpleIconSettings(
            SakuraAnimationMode animationMode,
            float textShadowDistance,
            boolean iconGlowEnabled,
            float iconGlowRange,
            int iconGlowIntensity,
            boolean line1GlowEnabled,
            float line1GlowRange,
            int line1GlowIntensity,
            boolean line2GlowEnabled,
            float line2GlowRange,
            int line2GlowIntensity,
            boolean imageEnabled
    ) {
        public static final SimpleIconSettings DEFAULT = new SimpleIconSettings(SakuraAnimationMode.Enhanced, 1.0f, false, 4.0f, 2, false, 4.0f, 2, false, 4.0f, 2, true);
    }

    private static int SIMPLE_IMAGE_ID = -1;
    private static final float SAKURA_ICON_SIZE = 21.5f;
    private static final float SAKURA_ICON_OFFSET_X = 0.0f;
    private static final float SAKURA_ICON_OFFSET_Y = 1.5f;
    private static final float SAKURA_LINE1_SIZE = 11.5f;
    private static final float SAKURA_LINE1_OFFSET_X = 0.0f;
    private static final float SAKURA_LINE1_OFFSET_Y = 0.0f;
    private static final float SAKURA_LINE2_SIZE = 10.5f;
    private static final float SAKURA_LINE2_OFFSET_X = 0.0f;
    private static final float SAKURA_LINE2_OFFSET_Y = 2.0f;
    private static final float SAKURA_IMAGE_WIDTH = 77.0f;
    private static final float SAKURA_IMAGE_HEIGHT = 214.0f;
    private static final float SAKURA_IMAGE_OFFSET_X = 47.0f;
    private static final float SAKURA_IMAGE_OFFSET_Y = 0.0f;
    private static float SIMPLE_IMAGE_SOURCE_WIDTH = 1.0f;
    private static float SIMPLE_IMAGE_SOURCE_HEIGHT = 1.0f;
    private static boolean SIMPLE_IMAGE_SOURCE_SIZE_LOADED = false;

    private static void ensureSimpleImageSourceSize() {
        if (SIMPLE_IMAGE_SOURCE_SIZE_LOADED) return;
        SIMPLE_IMAGE_SOURCE_SIZE_LOADED = true;
        try (InputStream in = NotificationManager.class.getResourceAsStream("/assets/sakura/textures/hud/noti.png")) {
            if (in == null) return;
            BufferedImage image = ImageIO.read(in);
            if (image == null) return;
            SIMPLE_IMAGE_SOURCE_WIDTH = Math.max(1.0f, image.getWidth());
            SIMPLE_IMAGE_SOURCE_HEIGHT = Math.max(1.0f, image.getHeight());
        } catch (Exception ignored) {
        }
    }

    private static int getSimpleImageId() {
        if (SIMPLE_IMAGE_ID != -1) return SIMPLE_IMAGE_ID;
        ensureSimpleImageSourceSize();
        SIMPLE_IMAGE_ID = NanoVGHelper.loadTexture("/assets/sakura/textures/hud/noti.png");
        return SIMPLE_IMAGE_ID;
    }

    private static long normalizeTimeout(long length) {
        return Math.max(1L, Math.min(MAX_TIMEOUT, length));
    }

    private static boolean isSakuraModeActive() {
        try {
            NotificationHud hud = Sakura.MODULES.getModule(NotificationHud.class);
            return hud != null && hud.isSakuraMode();
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void send(String message) {
        send(message.hashCode(), message, DEFAULT_TIMEOUT);
    }

    public static void send(String message, long length) {
        send(message.hashCode(), message, length);
    }

    public static void send(Object identifier, String message, long length) {
        send(identifier.hashCode(), message, length);
    }

    public static void send(long id, String message, long length) {
        long safeLength = normalizeTimeout(length);
        boolean forceNew = isSakuraModeActive();
        long finalId = forceNew ? (id ^ System.nanoTime()) : id;
        synchronized (notificationMap) {
            Notification existing = notificationMap.get(finalId);
            if (!forceNew && existing != null && !existing.isTimeout()) {
                existing.update(message, safeLength);
            } else {
                Notification notification = new Notification(message, safeLength, finalId);
                notificationMap.put(finalId, notification);
                notifications.add(notification);
            }
        }
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment) {
        return renderPreview(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, Xylitol4Offsets.ZERO, ShadowSettings.DEFAULT);
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets) {
        return renderPreview(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, ShadowSettings.DEFAULT);
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings) {
        return renderPreview(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, 0.0f);
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius) {
        return renderPreview(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, cornerRadius, SimpleIconSettings.DEFAULT);
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius, SimpleIconSettings simpleIconSettings) {
        if (mode == RenderMode.Trollhack) {
            return renderPreviewLegacy(x, y, alignment == Alignment.LEFT, primaryColor, backgroundColor, maxWidth, blur, blurStrength);
        }
        return renderPreviewSimple(x, y, maxWidth, backgroundColor, blur, blurStrength, scale, alignment, cornerRadius, shadowSettings, simpleIconSettings);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment) {
        render(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, Xylitol4Offsets.ZERO, ShadowSettings.DEFAULT);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets) {
        render(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, ShadowSettings.DEFAULT);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings) {
        render(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, 0.0f);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius) {
        render(matrices, x, y, mode, primaryColor, backgroundColor, maxWidth, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, cornerRadius, SimpleIconSettings.DEFAULT);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius, SimpleIconSettings simpleIconSettings) {
        if (mode == RenderMode.Trollhack) {
            renderLegacy(x, y, alignment == Alignment.LEFT, primaryColor, backgroundColor, maxWidth, blur, blurStrength);
            return;
        }
        renderSimple(x, y, maxWidth, backgroundColor, blur, blurStrength, scale, alignment, cornerRadius, shadowSettings, simpleIconSettings);
    }

    public static float[] renderPreview(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment) {
        return renderPreview(matrices, x, y, mode, primaryColor, backgroundColor, 300.0f, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment);
    }

    public static void render(Matrix3x2fStack matrices, float x, float y, RenderMode mode, Color primaryColor, Color backgroundColor, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment) {
        render(matrices, x, y, mode, primaryColor, backgroundColor, 300.0f, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment);
    }

    private static float[] renderPreviewLegacy(float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength) {
        String previewMessage = "Preview Notification";
        float padding = 4.0f;
        int font = FontLoader.medium();
        float fontSize = 12.0f;
        float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
        float height = textHeight * 2.5f;
        float minWidth = 150.0f;
        float textWidth = padding * 3.0f + NanoVGHelper.getTextWidth(previewMessage, font, fontSize);
        float width = Math.min(Math.max(minWidth, textWidth), Math.max(minWidth, maxWidth));

        if (blur) {
            BlurShader.drawRoundedBlur(x, y, width, height, 0.0f, blurStrength);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRect(x, y, width, height, backgroundColor);
            if (leftAligned) {
                NanoVGHelper.drawRect(x + width - padding, y, padding, height, primaryColor);
            } else {
                NanoVGHelper.drawRect(x, y, padding, height, primaryColor);
            }
            float stringPosX = leftAligned ? x + padding * 1.5f : x + padding * 2.5f;
            float stringPosY = y + height * 0.5f + textHeight * 0.3f;
            NanoVGHelper.drawString(previewMessage, stringPosX, stringPosY, font, fontSize, Color.WHITE);
        });

        return new float[]{width, height};
    }

    private static void renderLegacy(float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength) {
        if (blur) {
            float offsetY = 0.0f;
            for (int i = notifications.size() - 1; i >= 0; i--) {
                Notification notification = notifications.get(i);
                float[] bounds = notification.getLegacyBounds(x, y + offsetY, maxWidth, leftAligned);
                if (bounds != null) {
                    BlurShader.drawRoundedBlur(bounds[0], bounds[1], bounds[2], bounds[3], 0.0f, blurStrength);
                    offsetY += bounds[3] + 4.0f;
                }
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float offsetY = 0.0f;
            for (int i = notifications.size() - 1; i >= 0; i--) {
                Notification notification = notifications.get(i);
                float height = notification.renderLegacy(x, y + offsetY, leftAligned, primaryColor, backgroundColor, maxWidth);

                if (height == -1.0f) {
                    synchronized (notificationMap) {
                        if (notificationMap.get(notification.id) == notification) {
                            notificationMap.remove(notification.id);
                        }
                    }
                    notifications.remove(i);
                } else {
                    offsetY += height;
                }
            }
        });
    }


    private static Color withAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, Math.min(255, alpha)));
    }

    private static Color lerp(Color a, Color b, float t) {
        t = Math.max(0.0f, Math.min(1.0f, t));
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        int al = (int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t);
        return new Color(Math.max(0, Math.min(255, r)), Math.max(0, Math.min(255, g)), Math.max(0, Math.min(255, bl)), Math.max(0, Math.min(255, al)));
    }

    private static float easeOutDecelerate(float t) {
        t = Math.max(0.0f, Math.min(1.0f, t));
        return 1.0f - (1.0f - t) * (1.0f - t);
    }

    private static float easeOutBack(float t) {
        t = Math.max(0.0f, Math.min(1.0f, t));
        float c1 = 1.70158f;
        float c3 = c1 + 1.0f;
        float p = t - 1.0f;
        return 1.0f + c3 * p * p * p + c1 * p * p;
    }

    private static float clamp01(float v) {
        return Math.max(0.0f, Math.min(1.0f, v));
    }

    private static String inferSimpleTitle(String message) {
        String m = message.toLowerCase();
        if (m.contains("§c") || m.contains(" disabled") || m.contains("已关闭")) return "Disabled Module";
        return "Enabled Module";
    }

    private static Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0.0f, Math.min(1.0f, t));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(Math.max(0, Math.min(255, r)), Math.max(0, Math.min(255, g)), Math.max(0, Math.min(255, b)), Math.max(0, Math.min(255, a)));
    }

    private static void renderSimpleGradientStringTwoColor(long vg, float x, float y, int font, float size, String text, Color c1, Color c2) {
        if (text == null || text.isEmpty()) return;
        float textW = Math.max(1.0f, NanoVGHelper.getTextWidth(text, font, size));
        int segments = (int) Math.max(16, Math.min(260, Math.ceil(textW / 6.0f)));
        float segW = textW / segments;
        float fontH = NanoVGHelper.getFontHeight(font, size);
        float overlap = 0.75f;

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);

        for (int i = 0; i < segments; i++) {
            float segX = x + i * segW;
            float mid = (segX - x) + (segW * 0.5f);
            float t = mid / textW;
            Color col = interpolateColor(c1, c2, t);
            col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 255);
            nvgSave(vg);
            nvgScissor(vg, segX - overlap, y - 1.0f, segW + overlap * 2.0f, fontH + 2.0f);
            nvgFillColor(vg, NanoVGHelper.nvgColor(col));
            nvgText(vg, x, y, text);
            nvgRestore(vg);
        }
    }

    private static void drawSimpleGlowString(long vg, String text, float x, float y, int font, float size, Color color, float glowRange, int intensity) {
        if (text == null || text.isEmpty()) return;
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFontBlur(vg, Math.max(0.0f, glowRange));
        nvgFillColor(vg, NanoVGHelper.nvgColor(color));
        for (int i = 0; i < Math.max(1, intensity); i++) {
            nvgText(vg, x, y, text);
        }
        nvgFontBlur(vg, 0.0f);
    }

    private static class SimpleMetrics {
        float width;
        float height;
        float iconX;
        float iconY;
        float iconSize;
        float line1X;
        float line1Y;
        float line1Size;
        float line2X;
        float line2Y;
        float line2Size;
        float line1W;
        float line2W;
    }

    private static SimpleMetrics computeSimpleMetrics(String line1, String line2, float maxWidth, float scale) {
        float safeScale = Math.max(0.1f, scale);
        int iconFont = FontLoader.ico();
        int textFont = FontLoader.medium();
        float iconSize = Math.max(6.0f, SAKURA_ICON_SIZE) * safeScale;
        float line1Size = Math.max(6.0f, SAKURA_LINE1_SIZE) * safeScale;
        float line2Size = Math.max(6.0f, SAKURA_LINE2_SIZE) * safeScale;

        float iconW = NanoVGHelper.getTextWidth("D", iconFont, iconSize);
        float iconH = NanoVGHelper.getFontHeight(iconFont, iconSize);
        float line1W = NanoVGHelper.getTextWidth(line1, textFont, line1Size);
        float line1H = NanoVGHelper.getFontHeight(textFont, line1Size);
        float line2W = NanoVGHelper.getTextWidth(line2, textFont, line2Size);
        float line2H = NanoVGHelper.getFontHeight(textFont, line2Size);

        float padX = 6.0f * safeScale;
        float padY = 4.0f * safeScale;
        float gapX = 4.0f * safeScale;
        float lineGapY = 2.0f * safeScale;
        float textBaseX = padX + iconW + gapX;

        float iconXRaw = padX + SAKURA_ICON_OFFSET_X * safeScale;
        float iconYRaw = padY + SAKURA_ICON_OFFSET_Y * safeScale;
        float line1XRaw = textBaseX + SAKURA_LINE1_OFFSET_X * safeScale;
        float line1YRaw = padY + SAKURA_LINE1_OFFSET_Y * safeScale;
        float line2XRaw = textBaseX + SAKURA_LINE2_OFFSET_X * safeScale;
        float line2YRaw = line1YRaw + line1H + lineGapY + SAKURA_LINE2_OFFSET_Y * safeScale;

        float minX = Math.min(iconXRaw, Math.min(line1XRaw, line2XRaw));
        float minY = Math.min(iconYRaw, Math.min(line1YRaw, line2YRaw));
        float maxX = Math.max(iconXRaw + iconW, Math.max(line1XRaw + line1W, line2XRaw + line2W));
        float maxY = Math.max(iconYRaw + iconH, Math.max(line1YRaw + line1H, line2YRaw + line2H));

        float shiftX = padX - minX;
        float shiftY = padY - minY;

        SimpleMetrics m = new SimpleMetrics();
        m.iconX = iconXRaw + shiftX;
        m.iconY = iconYRaw + shiftY;
        m.iconSize = iconSize;
        m.line1X = line1XRaw + shiftX;
        m.line1Y = line1YRaw + shiftY;
        m.line1Size = line1Size;
        m.line2X = line2XRaw + shiftX;
        m.line2Y = line2YRaw + shiftY;
        m.line2Size = line2Size;
        m.line1W = line1W;
        m.line2W = line2W;

        float rawW = (maxX + shiftX) + padX;
        float rawH = (maxY + shiftY) + padY;
        float minW = 120.0f * safeScale;
        m.width = Math.min(Math.max(minW, rawW), Math.max(minW, maxWidth));
        m.height = Math.max(30.0f * safeScale, rawH);
        return m;
    }

    private static float[] renderPreviewSimple(float x, float y, float maxWidth, Color backgroundColor, boolean blur, float blurStrength, float scale, Alignment alignment, float cornerRadius, ShadowSettings shadowSettings, SimpleIconSettings settings) {
        String message = "KillAura §a enabled";
        String plain = Notification.stripFormatting(message);
        Notification.ParsedLines lines = Notification.parseLines(plain);
        String moduleName = lines.title == null || lines.title.isBlank() ? plain : lines.title;
        String line1Text = "Enabled Module";
        SimpleMetrics metrics = computeSimpleMetrics(line1Text, moduleName, maxWidth, scale);
        float w = metrics.width;
        float h = metrics.height;
        float output = 1.0f;
        float drawX;
        if (alignment == Alignment.RIGHT) {
            float anchorRight = x + Math.max(0.0f, maxWidth);
            drawX = anchorRight - w * output;
        } else {
            drawX = x - w * (1.0f - output);
        }
        float drawY = y;
        float r = Math.max(0.0f, cornerRadius) * Math.max(0.1f, scale);
        r = Math.min(r, Math.min(w, h) * 0.5f);
        if (shadowSettings != null && shadowSettings.enabled) {
            float[] rects = new float[]{drawX, drawY, w, h};
            float[] radii = new float[]{r};
            float range = Math.max(0.0f, shadowSettings.range) * Math.max(0.1f, scale);
            float strength = shadowSettings.strength;
            if (shadowSettings.mode == ShadowMode.Gradient) {
                Color start = ClickGui.color(1);
                Color end = ClickGui.color2(1);
                start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
                ShadowShader.drawStairShadowGradient(drawX, drawY, w, h, range, strength, start, end, rects, radii, 1);
            } else {
                Color c = new Color(128, 128, 128, 255);
                ShadowShader.drawStairShadow(drawX, drawY, w, h, range, strength, c, rects, radii, 1);
            }
        }
        if (blur) {
            BlurShader.drawRoundedBlur(drawX, drawY, w, h, r, blurStrength);
        }
        String m = message.toLowerCase();
        String icon = (m.contains("§c") || m.contains(" disabled") || m.contains("已关闭")) ? "C" : "D";
        NanoVGRenderer.INSTANCE.draw(vg -> drawSimpleNotification(vg, drawX, drawY, line1Text, moduleName, backgroundColor, scale, cornerRadius, metrics, settings, 1.0f, 1.0f, icon));
        return new float[]{w, h};
    }

    private static void renderSimple(float x, float y, float maxWidth, Color backgroundColor, boolean blur, float blurStrength, float scale, Alignment alignment, float cornerRadius, ShadowSettings shadowSettings, SimpleIconSettings settings) {
        float yOffset = 0.0f;
        float safeScale = Math.max(0.1f, scale);
        float spacing = 6.0f * safeScale;
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            if (notification.startTime == -1L) {
                notification.startTime = System.currentTimeMillis();
            }
            long now = System.currentTimeMillis();
            long elapsed = now - notification.startTime;
            boolean hide = elapsed >= notification.length;
            float enterT = clamp01(elapsed / (float) ANIMATION_TIME_MS);
            float exitT = hide ? clamp01((elapsed - notification.length) / (float) ANIMATION_TIME_MS) : 0.0f;
            boolean classicMode = settings.animationMode() == SakuraAnimationMode.Classic;
            float output;
            float drawAlpha;
            float contentAlpha;
            if (classicMode) {
                output = hide ? (1.0f - easeOutDecelerate(exitT)) : easeOutDecelerate(enterT);
                output = clamp01(output);
                drawAlpha = 1.0f;
                contentAlpha = 1.0f;
            } else {
                output = hide ? (1.0f - easeOutDecelerate(exitT)) : easeOutBack(enterT);
                output = clamp01(output);
                drawAlpha = hide ? (1.0f - easeOutDecelerate(exitT)) : easeOutDecelerate(enterT);
                drawAlpha = clamp01(drawAlpha);
                float delayedT = hide ? drawAlpha : clamp01((elapsed - 30.0f) / (float) ANIMATION_TIME_MS);
                contentAlpha = hide ? drawAlpha : easeOutDecelerate(delayedT);
                contentAlpha = clamp01(contentAlpha);
            }
            if (hide && exitT >= 1.0f && (classicMode ? output : drawAlpha) <= 0.001f) {
                synchronized (notificationMap) {
                    if (notificationMap.get(notification.id) == notification) {
                        notificationMap.remove(notification.id);
                    }
                }
                notifications.remove(i);
                i--;
                continue;
            }
            String plain = Notification.stripFormatting(notification.message);
            Notification.ParsedLines lines = Notification.parseLines(plain);
            String moduleName = lines.title == null || lines.title.isBlank() ? plain : lines.title;
            String line1Text = inferSimpleTitle(notification.message);
            SimpleMetrics metrics = computeSimpleMetrics(line1Text, moduleName, maxWidth, scale);
            float w = metrics.width;
            float h = metrics.height;
            float drawX;
            if (alignment == Alignment.RIGHT) {
                float anchorRight = x + Math.max(0.0f, maxWidth);
                drawX = anchorRight - w * output;
            } else {
                drawX = x - w * (1.0f - output);
            }
            float enterLift = classicMode ? 0.0f : (hide ? 0.0f : (1.0f - enterT) * (6.0f * safeScale));
            float drawY = y - yOffset + enterLift;
            float r = Math.max(0.0f, cornerRadius) * safeScale;
            r = Math.min(r, Math.min(w, h) * 0.5f);
            if (shadowSettings != null && shadowSettings.enabled) {
                float[] rects = new float[]{drawX, drawY, w, h};
                float[] radii = new float[]{r};
                float range = Math.max(0.0f, shadowSettings.range) * safeScale;
                float strength = shadowSettings.strength;
                if (shadowSettings.mode == ShadowMode.Gradient) {
                    Color start = ClickGui.color(1);
                    Color end = ClickGui.color2(1);
                    start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                    end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
                    ShadowShader.drawStairShadowGradient(drawX, drawY, w, h, range, strength, start, end, rects, radii, 1);
                } else {
                    Color c = new Color(128, 128, 128, 255);
                    ShadowShader.drawStairShadow(drawX, drawY, w, h, range, strength, c, rects, radii, 1);
                }
            }
            if (blur) {
                float blurFactor = classicMode ? 1.0f : (0.6f + 0.4f * drawAlpha);
                BlurShader.drawRoundedBlur(drawX, drawY, w, h, r, blurStrength * blurFactor);
            }
            float finalX = drawX;
            float finalY = drawY;
            String finalLine1Text = line1Text;
            String finalModuleName = moduleName;
            SimpleMetrics finalMetrics = metrics;
            float finalDrawAlpha = drawAlpha;
            float finalContentAlpha = contentAlpha;
            String m = notification.message.toLowerCase();
            String finalIcon = (m.contains("§c") || m.contains(" disabled") || m.contains("已关闭")) ? "C" : "D";
            NanoVGRenderer.INSTANCE.draw(vg -> drawSimpleNotification(vg, finalX, finalY, finalLine1Text, finalModuleName, backgroundColor, scale, cornerRadius, finalMetrics, settings, finalDrawAlpha, finalContentAlpha, finalIcon));
            yOffset += (h + spacing) * (hide ? output : 1.0f);
        }
    }

    private static void drawSimpleNotification(long vg, float x, float y, String line1Text, String moduleName, Color backgroundColor, float scale, float cornerRadius, SimpleMetrics metrics, SimpleIconSettings settings, float drawAlpha, float contentAlpha, String icon) {
        scale = Math.max(0.1f, scale);
        drawAlpha = clamp01(drawAlpha);
        contentAlpha = clamp01(contentAlpha);
        float width = metrics.width;
        float height = metrics.height;
        float r = Math.max(0.0f, cornerRadius) * scale;
        r = Math.min(r, Math.min(width, height) * 0.5f);
        nvgSave(vg);
        nvgGlobalAlpha(vg, drawAlpha);
        Color fill = backgroundColor != null ? backgroundColor : new Color(0, 0, 0, 140);
        if (r > 0.001f) {
            NanoVGHelper.drawRoundRect(x, y, width, height, r, fill);
        } else {
            NanoVGHelper.drawRect(x, y, width, height, fill);
        }
        if (settings.imageEnabled()) {
            int imageId = getSimpleImageId();
            if (imageId != -1) {
                float targetW = Math.max(1.0f, SAKURA_IMAGE_WIDTH) * scale;
                float targetH = Math.max(1.0f, SAKURA_IMAGE_HEIGHT) * scale;
                float sourceW = Math.max(1.0f, SIMPLE_IMAGE_SOURCE_WIDTH);
                float sourceH = Math.max(1.0f, SIMPLE_IMAGE_SOURCE_HEIGHT);
                float imageScale = Math.min(targetW / sourceW, targetH / sourceH);
                float imageW = sourceW * imageScale;
                float imageH = sourceH * imageScale;
                float imageX = x + SAKURA_IMAGE_OFFSET_X * scale;
                float imageY = y + SAKURA_IMAGE_OFFSET_Y * scale;
                nvgSave(vg);
                nvgScissor(vg, x, y, width, height);
                NanoVGHelper.drawImage(imageId, imageX, imageY, imageW, imageH, 0.0f, 1.0f);
                nvgRestore(vg);
            }
        }
        nvgRestore(vg);
        float visibleContentAlpha = clamp01(drawAlpha * contentAlpha);
        if (visibleContentAlpha <= 0.001f) {
            return;
        }
        nvgSave(vg);
        nvgGlobalAlpha(vg, visibleContentAlpha);
        int iconFont = FontLoader.ico();
        float iconX = x + metrics.iconX;
        float iconY = y + metrics.iconY;
        if (settings.iconGlowEnabled()) {
            drawSimpleGlowString(vg, icon, iconX, iconY, iconFont, metrics.iconSize, Color.WHITE, settings.iconGlowRange() * scale, settings.iconGlowIntensity());
        }
        NanoVGHelper.drawString(icon, iconX, iconY, iconFont, metrics.iconSize, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, Color.WHITE);
        int textFont = FontLoader.medium();
        float line1X = x + metrics.line1X;
        float line1Y = y + metrics.line1Y;
        float line2X = x + metrics.line2X;
        float line2Y = y + metrics.line2Y;

        Color g1 = ClickGui.color(1);
        Color g2 = ClickGui.color2(1);
        Color sAvg = new Color((g1.getRed() + g2.getRed()) / 2, (g1.getGreen() + g2.getGreen()) / 2, (g1.getBlue() + g2.getBlue()) / 2, 255);
        Color line1Shadow = new Color((int) (sAvg.getRed() * 0.5f), (int) (sAvg.getGreen() * 0.5f), (int) (sAvg.getBlue() * 0.5f), 255);
        float shadowDist = Math.max(0.0f, settings.textShadowDistance()) * scale;
        NanoVGHelper.drawString(line1Text, line1X + shadowDist, line1Y + shadowDist, textFont, metrics.line1Size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, line1Shadow);
        if (settings.line1GlowEnabled()) {
            drawSimpleGlowString(vg, line1Text, line1X, line1Y, textFont, metrics.line1Size, sAvg, settings.line1GlowRange() * scale, settings.line1GlowIntensity());
        }
        renderSimpleGradientStringTwoColor(vg, line1X, line1Y, textFont, metrics.line1Size, line1Text, g1, g2);
        if (settings.line2GlowEnabled()) {
            drawSimpleGlowString(vg, moduleName, line2X, line2Y, textFont, metrics.line2Size, Color.WHITE, settings.line2GlowRange() * scale, settings.line2GlowIntensity());
        }
        NanoVGHelper.drawString(moduleName, line2X, line2Y, textFont, metrics.line2Size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, Color.WHITE);
        nvgRestore(vg);
    }

    public static class Notification {
        private String message;
        private long length;
        public final long id;
        private long startTime = -1L;
        private float cachedWidth = -1.0f;

        public Notification(String message, long length, long id) {
            this.message = message;
            this.length = normalizeTimeout(length);
            this.id = id;
        }

        public boolean isTimeout() {
            if (startTime == -1L) return false;
            return System.currentTimeMillis() - startTime > length;
        }

        public void update(String message, long length) {
            this.message = message;
            long safeLength = normalizeTimeout(length);
            if (startTime == -1L) {
                this.length = safeLength;
            } else {
                this.length = Math.min(MAX_TIMEOUT, safeLength + (System.currentTimeMillis() - startTime));
            }
            this.cachedWidth = -1.0f;
        }

        private float getLegacyWidth(float padding, float minWidth, float maxWidth) {
            if (cachedWidth < 0.0f) {
                int font = FontLoader.medium();
                float fontSize = 12.0f;
                String plainText = stripFormatting(message);
                cachedWidth = Math.max(minWidth, padding * 3.0f + NanoVGHelper.getTextWidth(plainText, font, fontSize));
            }
            return Math.min(cachedWidth, Math.max(minWidth, maxWidth));
        }

        public float[] getLegacyBounds(float x, float y, float maxWidth, boolean leftAligned) {
            if (startTime == -1L) return null;

            float padding = 4.0f;
            int font = FontLoader.medium();
            float fontSize = 12.0f;
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
            float height = textHeight * 2.5f;
            float minWidth = 150.0f;
            float width = getLegacyWidth(padding, minWidth, maxWidth);

            long deltaTotal = System.currentTimeMillis() - startTime;
            if (deltaTotal >= length + 500L) return null;

            if (deltaTotal < 300L) {
                float delta = deltaTotal / 300.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                if (leftAligned) {
                    return new float[]{x, y, width * progress, height};
                }
                return new float[]{x + minWidth * (1.0f - progress), y, width, height};
            }

            if (deltaTotal < length + 200L) {
                return new float[]{x, y, width, height};
            }

            if (deltaTotal < length + 500L) {
                long endDelta = deltaTotal - length;
                float delta = (endDelta - 200L) / 300.0f;
                float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                if (leftAligned) {
                    return new float[]{x, y, width * progress, height};
                }
                return new float[]{x + minWidth * (1.0f - progress), y, width, height};
            }

            return null;
        }

        public float renderLegacy(float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth) {
            if (startTime == -1L) {
                startTime = System.currentTimeMillis();
            }

            float padding = 4.0f;
            int font = FontLoader.medium();
            float fontSize = 12.0f;
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
            float height = textHeight * 2.5f;
            float space = 4.0f;
            float minWidth = 150.0f;
            float width = getLegacyWidth(padding, minWidth, maxWidth);

            long deltaTotal = System.currentTimeMillis() - startTime;

            if (deltaTotal < 300L) {
                float delta = deltaTotal / 300.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                return renderLegacyStage1(x, y, width, height, space, progress, leftAligned, primaryColor, minWidth);
            }

            if (deltaTotal < 500L) {
                float delta = (deltaTotal - 300L) / 200.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                return renderLegacyStage2(x, y, width, height, space, padding, progress, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
            }

            if (deltaTotal < length) {
                return renderLegacyStage3(x, y, width, height, space, padding, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
            }

            long endDelta = deltaTotal - length;
            if (endDelta < 200L) {
                float delta = endDelta / 200.0f;
                float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                return renderLegacyStage2(x, y, width, height, space, padding, progress, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
            }

            if (endDelta < 500L) {
                float delta = (endDelta - 200L) / 300.0f;
                float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                return renderLegacyStage1(x, y, width, height, space, progress, leftAligned, primaryColor, minWidth);
            }

            return -1.0f;
        }

        private float renderLegacyStage1(float x, float y, float width, float height, float space, float progress, boolean leftAligned, Color color, float minWidth) {
            if (leftAligned) {
                NanoVGHelper.drawRect(x, y, width * progress, height, color);
            } else {
                NanoVGHelper.drawRect(x + minWidth * (1.0f - progress), y, width, height, color);
            }
            return (height + space) * progress;
        }

        private float renderLegacyStage2(float x, float y, float width, float height, float space, float padding, float progress, boolean leftAligned, Color primaryColor, Color backgroundColor, int font, float fontSize, float textHeight) {
            NanoVGHelper.drawRect(x, y, width, height, backgroundColor);

            int textAlpha = (int) (255.0f * progress);
            Color textColor = new Color(255, 255, 255, Math.max(0, Math.min(255, textAlpha)));

            float stringPosX = leftAligned ? x + padding * 1.5f : x + padding * 2.5f;
            float stringPosY = y + height * 0.5f + textHeight * 0.3f;
            drawColoredString(message, stringPosX, stringPosY, font, fontSize, textColor);

            if (leftAligned) {
                NanoVGHelper.drawRect(x + (width - padding) * progress, y, width - (width - padding) * progress, height, primaryColor);
            } else {
                NanoVGHelper.drawRect(x, y, padding + (width - padding) * (1.0f - progress), height, primaryColor);
            }

            return height + space;
        }

        private float renderLegacyStage3(float x, float y, float width, float height, float space, float padding, boolean leftAligned, Color primaryColor, Color backgroundColor, int font, float fontSize, float textHeight) {
            NanoVGHelper.drawRect(x, y, width, height, backgroundColor);

            if (leftAligned) {
                NanoVGHelper.drawRect(x + width - padding, y, padding, height, primaryColor);
            } else {
                NanoVGHelper.drawRect(x, y, padding, height, primaryColor);
            }

            float stringPosX = leftAligned ? x + padding * 1.5f : x + padding * 2.5f;
            float stringPosY = y + height * 0.5f + textHeight * 0.3f;
            drawColoredString(message, stringPosX, stringPosY, font, fontSize, Color.WHITE);

            return height + space;
        }

        private void drawColoredString(String text, float x, float y, int font, float fontSize, Color defaultColor) {
            float currentX = x;
            Color currentColor = defaultColor;
            StringBuilder segment = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == '§' && i + 1 < text.length()) {
                    if (!segment.isEmpty()) {
                        Color drawColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), defaultColor.getAlpha());
                        NanoVGHelper.drawString(segment.toString(), currentX, y, font, fontSize, drawColor);
                        currentX += NanoVGHelper.getTextWidth(segment.toString(), font, fontSize);
                        segment.setLength(0);
                    }
                    char code = Character.toLowerCase(text.charAt(i + 1));
                    Color newColor = COLOR_CODES.get(code);
                    if (newColor != null) {
                        currentColor = newColor;
                    }
                    i++;
                } else {
                    segment.append(c);
                }
            }

            if (!segment.isEmpty()) {
                Color drawColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), defaultColor.getAlpha());
                NanoVGHelper.drawString(segment.toString(), currentX, y, font, fontSize, drawColor);
            }
        }

        private static String stripFormatting(String text) {
            return text.replaceAll("§.", "");
        }

        private record ParsedLines(String title, String subTitle) {
        }

        private static ParsedLines parseLines(String plainText) {
            String trimmed = plainText.trim();
            String[] keywords = new String[]{" enabled", " disabled", "已开启", "已关闭"};
            for (String keyword : keywords) {
                int idx = trimmed.indexOf(keyword);
                if (idx > 0) {
                    String left = trimmed.substring(0, idx).trim();
                    String right = trimmed.substring(idx).trim();
                    if (!left.isEmpty() && !right.isEmpty()) {
                        return new ParsedLines(left, right);
                    }
                }
            }
            return new ParsedLines(trimmed, "");
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Notification that = (Notification) other;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(id);
        }
    }
}
