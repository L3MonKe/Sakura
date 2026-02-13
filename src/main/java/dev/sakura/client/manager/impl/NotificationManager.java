package dev.sakura.client.manager.impl;

import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.utils.animations.Easing;
import dev.sakura.client.verify.util.ExitUtil;
import org.joml.Matrix3x2fStack;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;

public class NotificationManager {
    public enum RenderMode {
        Xylitol3,
        Xylitol4,
        Legacy
    }

    public enum Alignment {
        LEFT,
        RIGHT
    }

    private static final long DEFAULT_TIMEOUT = 2000L;
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

    public static final Xylitol4Offsets XYLITOL4_HARDCODED_OFFSETS = new Xylitol4Offsets(-3.0f, -3.0f, -2.0f, -4.0f, -1.5f, 0.0f, -3.0f);
    public static final Alignment XYLITOL3_HARDCODED_ALIGNMENT = Alignment.RIGHT;
    public static final float XYLITOL3_HARDCODED_SCALE = 1.85f;
    public static final float XYLITOL3_HARDCODED_FONT_SIZE = 10.0f;
    public static final float XYLITOL3_HARDCODED_MAX_WIDTH = 500.0f;
    public static final boolean XYLITOL3_HARDCODED_BLUR = true;
    public static final float XYLITOL3_HARDCODED_BLUR_STRENGTH = 4.0f;
    public static final Color XYLITOL3_HARDCODED_PRIMARY_COLOR = Color.getHSBColor(0.9675926f, 0.15294118f, 1.0f);
    public static final Color XYLITOL3_HARDCODED_BACKGROUND_COLOR = new Color(
            Color.getHSBColor(0.0f, 0.0f, 0.21489775f).getRed(),
            Color.getHSBColor(0.0f, 0.0f, 0.21489775f).getGreen(),
            Color.getHSBColor(0.0f, 0.0f, 0.21489775f).getBlue(),
            45
    );
    public static final Alignment XYLITOL4_HARDCODED_ALIGNMENT = Alignment.RIGHT;
    public static final float XYLITOL4_HARDCODED_SCALE = 1.85f;
    public static final float XYLITOL4_HARDCODED_FONT_SIZE = 10.0f;
    public static final float XYLITOL4_HARDCODED_LINE_LENGTH = 5.5f;
    public static final float XYLITOL4_HARDCODED_MAX_WIDTH = 500.0f;
    public static final boolean XYLITOL4_HARDCODED_BLUR = true;
    public static final float XYLITOL4_HARDCODED_BLUR_STRENGTH = 4.0f;
    private static final float HARD_SHADOW_RANGE = 8.0f;
    private static final float HARD_SHADOW_STRENGTH = 0.6f;

    public record ShadowSettings(boolean enabled, float range, float strength) {
        public static final ShadowSettings DEFAULT = new ShadowSettings(true, HARD_SHADOW_RANGE, HARD_SHADOW_STRENGTH);
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
        ExitUtil.ensureVerifiedOrExit();
        synchronized (notificationMap) {
            Notification existing = notificationMap.get(id);
            if (existing != null && !existing.isTimeout()) {
                existing.update(message, length);
            } else {
                Notification notification = new Notification(message, length, id);
                notificationMap.put(id, notification);
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
        if (mode == RenderMode.Legacy) {
            return renderPreviewLegacy(x, y, alignment == Alignment.LEFT, primaryColor, backgroundColor, maxWidth, blur, blurStrength);
        }
        return renderPreviewXylitol(x, y, maxWidth, mode, primaryColor, backgroundColor, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, cornerRadius);
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
        if (mode == RenderMode.Legacy) {
            renderLegacy(x, y, alignment == Alignment.LEFT, primaryColor, backgroundColor, maxWidth, blur, blurStrength);
            return;
        }
        renderXylitol(x, y, maxWidth, mode, primaryColor, backgroundColor, blur, blurStrength, scale, fontSize, xylitol4LineLength, alignment, xylitol4Offsets, shadowSettings, cornerRadius);
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

    private enum XylitolType {
        SUCCESS(new Color(20, 250, 90), "A"),
        DISABLE(new Color(255, 30, 30), "B"),
        INFO(Color.DARK_GRAY, "C"),
        WARNING(Color.YELLOW, "D");

        private final Color color;
        private final String icon;

        XylitolType(Color color, String icon) {
            this.color = color;
            this.icon = icon;
        }
    }

    private static XylitolType inferXylitolType(String message) {
        String m = message.toLowerCase();
        if (m.contains("§a") || m.contains(" enabled") || m.contains("已开启")) return XylitolType.SUCCESS;
        if (m.contains("§c") || m.contains(" disabled") || m.contains("已关闭")) return XylitolType.DISABLE;
        return XylitolType.INFO;
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

    private static float[] computeXylitolSize(RenderMode mode, String title, String description, float scale, float fontSize) {
        scale = Math.max(0.1f, scale);
        fontSize = Math.max(6.0f, fontSize);

        if (mode == RenderMode.Xylitol3) {
            int font = FontLoader.medium();
            float textW = NanoVGHelper.getTextWidth(description, font, fontSize);
            float textH = NanoVGHelper.getFontHeight(font, fontSize);
            float w = textW + 15.0f * scale;
            float h = Math.max(23.0f * scale, textH + 10.0f * scale);
            return new float[]{w, h};
        }

        int titleFont = FontLoader.ax();
        float titleSize = fontSize + 2.0f;
        float titleH = NanoVGHelper.getFontHeight(titleFont, titleSize);

        int descFont = FontLoader.medium();
        float descSize = fontSize;
        float descW = NanoVGHelper.getTextWidth(description, descFont, descSize);
        float descH = NanoVGHelper.getFontHeight(descFont, descSize);

        float w = descW + 20.0f * scale;
        float h = titleH + descH + 4.0f * scale;
        return new float[]{w, h};
    }

    private static float[] renderPreviewXylitol(float x, float y, float maxWidth, RenderMode mode, Color primaryColor, Color backgroundColor, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius) {
        String message = "KillAura §a enabled";
        String plain = Notification.stripFormatting(message);
        Notification.ParsedLines lines = Notification.parseLines(plain);

        String title = mode == RenderMode.Xylitol4 ? "Module" : lines.title;
        String desc = mode == RenderMode.Xylitol4 ? plain : plain;

        float[] size = computeXylitolSize(mode, title, desc, scale, fontSize);
        float w = size[0];
        float h = size[1];

        float progress = 0.65f;
        float output = 1.0f;
        float drawX;
        if (alignment == Alignment.RIGHT) {
            float anchorRight = x + Math.max(0.0f, maxWidth);
            drawX = anchorRight - w * output;
        } else {
            drawX = x - w * (1.0f - output);
        }
        float drawY = y;

        float safeScale = Math.max(0.1f, scale);
        float r = mode == RenderMode.Xylitol4 ? Math.max(0.0f, cornerRadius) * safeScale : 0.0f;
        r = Math.min(r, Math.min(w, h) * 0.5f);

        if (shadowSettings != null && shadowSettings.enabled) {
            float[] rects = new float[]{drawX, drawY, w, h};
            float[] radii = new float[]{r};
            float range = Math.max(0.0f, shadowSettings.range) * safeScale;
            float strength = shadowSettings.strength;
            if (mode == RenderMode.Xylitol4) {
                Color start = ClickGui.color(1);
                Color end = ClickGui.color2(1);
                start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
                ShadowShader.drawStairShadowGradient(drawX, drawY, w, h, range, strength, start, end, rects, radii, 1);
            } else if (mode == RenderMode.Xylitol3) {
                Color c = backgroundColor != null ? backgroundColor : new Color(0, 0, 0, 70);
                c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
                ShadowShader.drawStairShadow(drawX, drawY, w, h, range, strength, c, rects, radii, 1);
            }
        }

        if (blur) {
            BlurShader.drawRoundedBlur(drawX, drawY, w, h, r, blurStrength);
        }

        XylitolType type = inferXylitolType(message);
        NanoVGRenderer.INSTANCE.draw(vg -> drawXylitolNotification(mode, type, drawX, drawY, w, h, title, desc, primaryColor, backgroundColor, progress, scale, fontSize, xylitol4LineLength, xylitol4Offsets, cornerRadius));
        return new float[]{w, h};
    }

    private static void renderXylitol(float x, float y, float maxWidth, RenderMode mode, Color primaryColor, Color backgroundColor, boolean blur, float blurStrength, float scale, float fontSize, float xylitol4LineLength, Alignment alignment, Xylitol4Offsets xylitol4Offsets, ShadowSettings shadowSettings, float cornerRadius) {
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

            float output;
            if (!hide) {
                output = easeOutDecelerate(Math.min(1.0f, elapsed / (float) ANIMATION_TIME_MS));
            } else {
                float t = Math.min(1.0f, (elapsed - notification.length) / (float) ANIMATION_TIME_MS);
                output = 1.0f - easeOutDecelerate(t);
            }

            if (hide && (elapsed - notification.length) >= ANIMATION_TIME_MS && output <= 0.001f) {
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

            String title = mode == RenderMode.Xylitol4 ? "Module" : lines.title;
            String desc = plain;

            float[] size = computeXylitolSize(mode, title, desc, scale, fontSize);
            float w = size[0];
            float h = size[1];

            float drawX;
            if (alignment == Alignment.RIGHT) {
                float anchorRight = x + Math.max(0.0f, maxWidth);
                drawX = anchorRight - w * output;
            } else {
                drawX = x - w * (1.0f - output);
            }
            float drawY = y - yOffset;

            float progress = Math.max(0.0f, Math.min(1.0f, elapsed / (float) Math.max(1L, notification.length)));
            XylitolType type = inferXylitolType(notification.message);

            float r = mode == RenderMode.Xylitol4 ? Math.max(0.0f, cornerRadius) * safeScale : 0.0f;
            r = Math.min(r, Math.min(w, h) * 0.5f);

            if (shadowSettings != null && shadowSettings.enabled) {
                float[] rects = new float[]{drawX, drawY, w, h};
                float[] radii = new float[]{r};
                float range = Math.max(0.0f, shadowSettings.range) * safeScale;
                float strength = shadowSettings.strength;
                if (mode == RenderMode.Xylitol4) {
                    Color start = ClickGui.color(1);
                    Color end = ClickGui.color2(1);
                    start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                    end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
                    ShadowShader.drawStairShadowGradient(drawX, drawY, w, h, range, strength, start, end, rects, radii, 1);
                } else if (mode == RenderMode.Xylitol3) {
                    Color c = backgroundColor != null ? backgroundColor : new Color(0, 0, 0, 70);
                    c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
                    ShadowShader.drawStairShadow(drawX, drawY, w, h, range, strength, c, rects, radii, 1);
                }
            }

            if (blur) {
                BlurShader.drawRoundedBlur(drawX, drawY, w, h, r, blurStrength);
            }

            float finalX = drawX;
            float finalY = drawY;
            float finalW = w;
            float finalH = h;
            float finalProgress = progress;
            NanoVGRenderer.INSTANCE.draw(vg -> drawXylitolNotification(mode, type, finalX, finalY, finalW, finalH, title, desc, primaryColor, backgroundColor, finalProgress, scale, fontSize, xylitol4LineLength, xylitol4Offsets, cornerRadius));

            yOffset += (h + spacing) * (hide ? output : 1.0f);
        }
    }

    private static void drawXylitolNotification(RenderMode mode, XylitolType type, float x, float y, float width, float height, String title, String description, Color primaryColor, Color backgroundColor, float progress, float scale, float fontSize, float xylitol4LineLength, Xylitol4Offsets xylitol4Offsets, float cornerRadius) {
        scale = Math.max(0.1f, scale);
        fontSize = Math.max(6.0f, fontSize);

        if (mode == RenderMode.Xylitol3) {
            Color fill = withAlpha(lerp(Color.BLACK, type.color, 0.65f), (int) (255.0f * 0.7f));

            NanoVGHelper.drawRect(x, y, width, height, backgroundColor != null ? backgroundColor : new Color(0, 0, 0, 70));
            NanoVGHelper.drawRect(x, y, 1.0f * scale, height, fill.brighter());
            NanoVGHelper.drawRect(x, y, width * progress, height, withAlpha(fill.brighter(), 50));

            Color textColor = withAlpha(Color.WHITE, (int) (255.0f * 0.8f));
            int font = FontLoader.medium();
            NanoVGHelper.drawString(description, x + 6.0f * scale, y + 8.0f * scale, font, fontSize, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, textColor);
            return;
        }

        String icon;
        switch (title) {
            case "Friend Manager" -> icon = "\uEC2D";
            case "Config" -> icon = "\uEA21";
            case "Module" -> icon = "\uEB13";
            case "IRC" -> icon = "\uEA20";
            default -> icon = "\uEBF8";
        }

        float textX = x + 7.0f * scale;
        float textY = y + 5.0f * scale;
        float lineW = 1.0f * scale;
        float lineH = Math.max(0.0f, xylitol4LineLength) * scale;
        float iconOffsetX = xylitol4Offsets.iconOffsetX * scale;
        float iconOffsetY = xylitol4Offsets.iconOffsetY * scale;
        float titleOffsetX = xylitol4Offsets.titleOffsetX * scale;
        float titleOffsetY = xylitol4Offsets.titleOffsetY * scale;
        float descriptionOffsetX = xylitol4Offsets.descriptionOffsetX * scale;
        float descriptionOffsetY = xylitol4Offsets.descriptionOffsetY * scale;
        float lineOffsetY = xylitol4Offsets.lineOffsetY * scale;

        float r = Math.max(0.0f, cornerRadius) * scale;
        r = Math.min(r, Math.min(width, height) * 0.5f);
        if (r > 0.001f) {
            NanoVGHelper.drawRoundRect(x, y, width, height, r, new Color(0, 0, 0, 76));
        } else {
            NanoVGHelper.drawRect(x, y, width, height, new Color(0, 0, 0, 76));
        }
        if (mode == RenderMode.Xylitol4) {
            Color start = ClickGui.color(1);
            Color end = ClickGui.color2(1);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);

            long vg = NanoVGRenderer.INSTANCE.getContext();
            NVGPaint paint = NVGPaint.create();
            NVGColor nvgStart = NanoVGHelper.nvgColor(start);
            NVGColor nvgEnd = NanoVGHelper.nvgColor(end);
            nvgLinearGradient(vg, 0, textY + lineOffsetY, 0, textY + lineOffsetY + lineH, nvgStart, nvgEnd, paint);

            nvgBeginPath(vg);
            nvgRect(vg, x, textY + lineOffsetY, lineW, lineH);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        } else {
            NanoVGHelper.drawRect(x, textY + lineOffsetY, lineW, lineH, primaryColor);
        }

        int iconFont = FontLoader.material();
        float iconSize = fontSize;
        NanoVGHelper.drawString(icon, textX - 1.0f * scale + iconOffsetX, textY + 2.0f * scale + iconOffsetY, iconFont, iconSize, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, Color.WHITE);

        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);

        int titleFont = FontLoader.ax();
        float titleSize = fontSize + 2.0f;
        NanoVGHelper.drawString(title, textX + iconW + 2.0f * scale + titleOffsetX, y + 6.0f * scale + titleOffsetY, titleFont, titleSize, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, Color.WHITE);

        float titleH = NanoVGHelper.getFontHeight(titleFont, titleSize);

        int descFont = FontLoader.medium();
        float descSize = fontSize;
        NanoVGHelper.drawString(description, textX + descriptionOffsetX, textY + (titleH - 2.0f * scale) + descriptionOffsetY, descFont, descSize, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, Color.WHITE);
    }

    public static class Notification {
        private String message;
        private long length;
        public final long id;
        private long startTime = -1L;
        private float cachedWidth = -1.0f;

        public Notification(String message, long length, long id) {
            this.message = message;
            this.length = length;
            this.id = id;
        }

        public boolean isTimeout() {
            if (startTime == -1L) return false;
            return System.currentTimeMillis() - startTime > length;
        }

        public void update(String message, long length) {
            this.message = message;
            this.length = length + (System.currentTimeMillis() - startTime);
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
