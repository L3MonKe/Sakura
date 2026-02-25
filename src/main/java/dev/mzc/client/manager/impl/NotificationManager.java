package dev.mzc.client.manager.impl;

import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Easing;
import dev.mzc.client.utils.render.Shader2DUtil;
import net.minecraft.client.util.math.MatrixStack;
import static org.lwjgl.nanovg.NanoVG.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    private static final long DEFAULT_TIMEOUT = 3000L;
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Map<Long, Notification> notificationMap = new HashMap<>();
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
        synchronized (notificationMap) {
            Notification existing = notificationMap.get(id);
            if (existing != null && !existing.shouldRemove) {
                existing.update(message, length);
            } else {
                Notification notification = new Notification(message, length, id);
                notificationMap.put(id, notification);
                notifications.add(notification);
            }
        }
    }

    public static float[] renderPreview(MatrixStack matrices, float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float radius, boolean shadow, boolean progressBar) {
        String previewMessage = "Notification Preview";
        float padding = 8.0f;
        int font = FontLoader.bold(14);
        float fontSize = 14;
        float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
        float textWidth = NanoVGHelper.getTextWidth(previewMessage, font, fontSize);
        float width = Math.min(textWidth + padding * 3, maxWidth);
        float height = textHeight + padding * 2;

        if (blur) {
            Shader2DUtil.drawRoundedBlur(matrices, x, y, width, height, radius, new Color(0, 0, 0, 0), blurStrength, 1.0f);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            drawNotificationContent(x, y, width, height, previewMessage, 1.0f, primaryColor, backgroundColor, radius, shadow, progressBar, 0.5f, font, fontSize, leftAligned);
        });

        return new float[]{width, height};
    }

    public static void render(MatrixStack matrices, float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength, float radius, boolean shadow, boolean progressBar) {
        // First pass: Calculate positions and draw blur
        float currentY = y;
        List<RenderData> renderList = new ArrayList<>();

        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            
            // Animation logic
            long time = System.currentTimeMillis() - notification.startTime;
            float progress;
            float alpha = 1.0f;
            
            if (time < 300) {
                progress = (float) Easing.BACK_OUT.ease(time / 300.0);
            } else if (time > notification.length) {
                long endTime = time - notification.length;
                if (endTime > 300) {
                    notification.shouldRemove = true;
                    continue;
                }
                alpha = 1.0f - (float) Easing.BACK_IN.ease(endTime / 300.0);
                progress = alpha; 
            } else {
                progress = 1.0f;
            }

            // Calculate dimensions
            float padding = 8.0f;
            int font = FontLoader.bold(14);
            float fontSize = 14;
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
            float textWidth = notification.getWidth(padding, font, fontSize);
            float width = Math.min(textWidth, maxWidth);
            float height = textHeight + padding * 2;

            // Update vertical position for Upward Stacking
            // The first (oldest) notification stays at the anchor 'y'.
            // Newer notifications appear above (smaller Y).
            if (i > 0) {
                currentY -= (height + 5.0f);
            }

            // Calculate position
            float targetX;
            if (leftAligned) {
                targetX = x;
            } else {
                // Calculate preview width to align with the HUD box right edge
                String previewMessage = "Notification Preview";
                float previewTextWidth = NanoVGHelper.getTextWidth(previewMessage, font, fontSize);
                float previewWidth = Math.min(previewTextWidth + padding * 3, maxWidth);
                targetX = x + previewWidth - width;
            }

            // Slide animation (Left to Right Enter, Left to Right Exit)
            float offsetX;
            if (time < 300) {
                 float ease = (float) Easing.BACK_OUT.ease(time / 300.0);
                 offsetX = -width * (1.0f - ease);
            } else if (time > notification.length) {
                 long endTime = time - notification.length;
                 float ease = (float) Easing.BACK_IN.ease(endTime / 300.0);
                 offsetX = width * ease;
            } else {
                 offsetX = 0;
            }
            
            float drawX = targetX + offsetX;
            
            // Store render data
            renderList.add(new RenderData(notification, drawX, currentY, width, height, alpha, progress, font, fontSize, leftAligned));
            
            // Blur pass
            if (blur && alpha > 0.1f) {
                Shader2DUtil.drawRoundedBlur(matrices, drawX, currentY, width, height, radius, new Color(0, 0, 0, 0), blurStrength * alpha, alpha);
            }
        }

        // Second pass: Draw NanoVG content
        NanoVGRenderer.INSTANCE.draw(vg -> {
            for (RenderData data : renderList) {
                long time = System.currentTimeMillis() - data.notification.startTime;
                float timeProgress = Math.min(1.0f, (float) time / data.notification.length);
                
                drawNotificationContent(
                    data.x, data.y, data.width, data.height, 
                    data.notification.message, data.alpha, 
                    primaryColor, backgroundColor, radius, shadow, progressBar, 
                    1.0f - timeProgress, data.font, data.fontSize, data.leftAligned
                );
            }
        });

        // Cleanup
        notifications.removeIf(n -> n.shouldRemove);
        synchronized (notificationMap) {
            notificationMap.values().removeIf(n -> n.shouldRemove);
        }
    }

    private static void drawNotificationContent(float x, float y, float width, float height, String message, float alpha, Color primary, Color background, float radius, boolean shadow, boolean progressBar, float progress, int font, float fontSize, boolean leftAligned) {
        int alphaInt = (int) (255 * Math.max(0.0f, Math.min(1.0f, alpha)));
        Color bg = new Color(background.getRed(), background.getGreen(), background.getBlue(), (int) (background.getAlpha() * Math.max(0.0f, Math.min(1.0f, alpha))));
        Color prim = new Color(primary.getRed(), primary.getGreen(), primary.getBlue(), alphaInt);
        
        // Shadow
        if (shadow) {
            NanoVGHelper.drawShadow(x, y, width, height, radius, new Color(0, 0, 0, alphaInt / 2), 10.0f, 4.0f, 4.0f);
        }

        // Background
        NanoVGHelper.drawRoundRect(x, y, width, height, radius, bg);

        // Progress Bar (Bottom line)
        if (progressBar) {
            float barHeight = 2.0f;
            if (progress > 0) {
                 float barWidth = (width - radius) * progress;
                 if (leftAligned) {
                     NanoVGHelper.drawRoundRect(x + radius / 2.0f, y + height - 3.0f, barWidth, 2.0f, 1.0f, prim);
                 } else {
                     NanoVGHelper.drawRoundRect(x + width - radius / 2.0f - barWidth, y + height - 3.0f, barWidth, 2.0f, 1.0f, prim);
                 }
            }
        }
        
        // Vertical Accent Line
        if (leftAligned) {
            NanoVGHelper.drawRoundRect(x + 2.0f, y + 2.0f, 3.0f, height - 4.0f, 1.5f, prim);
        } else {
            NanoVGHelper.drawRoundRect(x + width - 5.0f, y + 2.0f, 3.0f, height - 4.0f, 1.5f, prim);
        }

        // Text
        float textX = leftAligned ? (x + 10 + 3) : (x + 10);
        float textY = y + height / 2.0f + 1.0f; // Center adjustment
        
        // Apply alpha to text
        drawColoredString(message, textX, textY, font, fontSize, new Color(255, 255, 255, alphaInt));
    }

    private static void drawColoredString(String text, float x, float y, int font, float fontSize, Color defaultColor) {
        float currentX = x;
        Color currentColor = defaultColor;
        StringBuilder segment = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '§' && i + 1 < text.length()) {
                if (!segment.isEmpty()) {
                    Color drawColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), defaultColor.getAlpha());
                    NanoVGHelper.drawString(segment.toString(), currentX, y, font, fontSize, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, drawColor);
                    currentX += NanoVGHelper.getTextWidth(segment.toString(), font, fontSize);
                    segment.setLength(0);
                }
                char code = Character.toLowerCase(text.charAt(i + 1));
                if (code == '#' && i + 7 < text.length()) {
                    try {
                        String hex = text.substring(i + 2, i + 8);
                        currentColor = new Color(Integer.parseInt(hex, 16));
                        i += 7;
                        continue;
                    } catch (NumberFormatException ignored) {
                    }
                }
                Color newColor = COLOR_CODES.get(code);
                if (newColor != null) {
                    currentColor = newColor;
                } else if (code == 'r') {
                    currentColor = new Color(255, 255, 255); // Reset to white, not defaultColor to keep it bright
                }
                i++;
            } else {
                segment.append(c);
            }
        }

        if (!segment.isEmpty()) {
            Color drawColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), defaultColor.getAlpha());
            NanoVGHelper.drawString(segment.toString(), currentX, y, font, fontSize, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, drawColor);
        }
    }

    private static class RenderData {
        Notification notification;
        float x, y, width, height, alpha, progress, fontSize;
        int font;
        boolean leftAligned;

        RenderData(Notification n, float x, float y, float w, float h, float a, float p, int f, float fs, boolean leftAligned) {
            this.notification = n;
            this.x = x; this.y = y; this.width = w; this.height = h;
            this.alpha = a; this.progress = p; this.font = f; this.fontSize = fs;
            this.leftAligned = leftAligned;
        }
    }

    public static class Notification {
        private String message;
        private long length;
        public final long id;
        public long startTime = -1L;
        public boolean shouldRemove = false;
        private float cachedWidth = -1f;

        public Notification(String message, long length, long id) {
            this.message = message;
            this.length = length;
            this.id = id;
            this.startTime = System.currentTimeMillis();
        }

        public void update(String message, long length) {
            this.message = message;
            this.length = length + (System.currentTimeMillis() - startTime); // Extend life
            this.cachedWidth = -1f;
        }
        
        public float getWidth(float padding, int font, float fontSize) {
            if (cachedWidth == -1f) {
                String plainText = message.replaceAll("§.", "");
                cachedWidth = padding * 3 + NanoVGHelper.getTextWidth(plainText, font, fontSize) + 10; // +10 for accent line
            }
            return cachedWidth;
        }
    }
}
