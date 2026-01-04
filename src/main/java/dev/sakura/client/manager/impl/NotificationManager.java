package dev.sakura.client.manager.impl;

import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Easing;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.util.math.MatrixStack;

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
            if (existing != null && !existing.isTimeout()) {
                existing.update(message, length);
            } else {
                Notification notification = new Notification(message, length, id);
                notificationMap.put(id, notification);
                notifications.add(notification);
            }
        }
    }

    public static float[] renderPreview(MatrixStack matrices, float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength) {
        String previewMessage = "Preview Notification";
        float padding = 4.0f;
        int font = FontLoader.medium(12);
        float fontSize = 12;
        float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
        float height = textHeight * 2.5f;
        float minWidth = 150.0f;
        float textWidth = padding * 3 + NanoVGHelper.getTextWidth(previewMessage, font, fontSize);
        float width = Math.min(Math.max(minWidth, textWidth), maxWidth);

        if (blur) {
            Shader2DUtil.drawRoundedBlur(matrices, x, y, width, height, 0, new Color(0, 0, 0, 0), blurStrength, 1.0f);
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

    public static void render(MatrixStack matrices, float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth, boolean blur, float blurStrength) {
        if (blur) {
            float offsetY = 0;
            for (int i = notifications.size() - 1; i >= 0; i--) {
                Notification notification = notifications.get(i);
                float[] bounds = notification.getBounds(x, y + offsetY, maxWidth, leftAligned);
                if (bounds != null) {
                    Shader2DUtil.drawRoundedBlur(matrices, bounds[0], bounds[1], bounds[2], bounds[3], 0, new Color(0, 0, 0, 0), blurStrength, 1.0f);
                    offsetY += bounds[3] + 4.0f;
                }
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float offsetY = 0;
            for (int i = notifications.size() - 1; i >= 0; i--) {
                Notification notification = notifications.get(i);
                float height = notification.render(x, y + offsetY, leftAligned, primaryColor, backgroundColor, maxWidth);

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
        }, true);
    }

    public static class Notification {
        private String message;
        private long length;
        public final long id;
        private long startTime = -1L;
        private float cachedWidth = -1f;

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
            this.cachedWidth = -1f;
        }

        private float getWidth(float padding, float minWidth) {
            if (cachedWidth == -1f) {
                int font = FontLoader.medium(12);
                float fontSize = 12;
                String plainText = message.replaceAll("§.", "");
                cachedWidth = Math.max(minWidth, padding * 3 + NanoVGHelper.getTextWidth(plainText, font, fontSize));
            }
            return cachedWidth;
        }

        public float[] getBounds(float x, float y, float maxWidth, boolean leftAligned) {
            if (startTime == -1L) return null;

            float padding = 4.0f;
            int font = FontLoader.medium(12);
            float fontSize = 12;
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
            float height = textHeight * 2.5f;
            float minWidth = 150.0f;
            float width = Math.min(getWidth(padding, minWidth), maxWidth);

            long deltaTotal = System.currentTimeMillis() - startTime;
            if (deltaTotal >= length + 500L) return null;

            if (deltaTotal < 300L) {
                float delta = deltaTotal / 300.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                if (leftAligned) {
                    return new float[]{x, y, width * progress, height};
                } else {
                    return new float[]{x + minWidth * (1.0f - progress), y, width, height};
                }
            } else if (deltaTotal < length + 200L) {
                return new float[]{x, y, width, height};
            } else if (deltaTotal < length + 500L) {
                long endDelta = deltaTotal - length;
                float delta = (endDelta - 200L) / 300.0f;
                float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                if (leftAligned) {
                    return new float[]{x, y, width * progress, height};
                } else {
                    return new float[]{x + minWidth * (1.0f - progress), y, width, height};
                }
            }

            return null;
        }

        public float render(float x, float y, boolean leftAligned, Color primaryColor, Color backgroundColor, float maxWidth) {
            if (startTime == -1L) {
                startTime = System.currentTimeMillis();
            }

            float padding = 4.0f;
            int font = FontLoader.medium(12);
            float fontSize = 12;
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize);
            float height = textHeight * 2.5f;
            float space = 4.0f;
            float minWidth = 150.0f;
            float width = Math.min(getWidth(padding, minWidth), maxWidth);

            long deltaTotal = System.currentTimeMillis() - startTime;

            if (deltaTotal < 300L) {
                float delta = deltaTotal / 300.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                return renderStage1(x, y, width, height, space, progress, leftAligned, primaryColor, minWidth);
            } else if (deltaTotal < 500L) {
                float delta = (deltaTotal - 300L) / 200.0f;
                float progress = (float) Easing.CUBIC_OUT.ease(delta);
                return renderStage2(x, y, width, height, space, padding, progress, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
            } else if (deltaTotal < length) {
                return renderStage3(x, y, width, height, space, padding, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
            } else {
                long endDelta = deltaTotal - length;
                if (endDelta < 200L) {
                    float delta = endDelta / 200.0f;
                    float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                    return renderStage2(x, y, width, height, space, padding, progress, leftAligned, primaryColor, backgroundColor, font, fontSize, textHeight);
                } else if (endDelta < 500L) {
                    float delta = (endDelta - 200L) / 300.0f;
                    float progress = (float) (1.0 - Easing.CUBIC_OUT.ease(delta));
                    return renderStage1(x, y, width, height, space, progress, leftAligned, primaryColor, minWidth);
                } else {
                    return -1.0f;
                }
            }
        }

        private float renderStage1(float x, float y, float width, float height, float space, float progress, boolean leftAligned, Color color, float minWidth) {
            if (leftAligned) {
                NanoVGHelper.drawRect(x, y, width * progress, height, color);
            } else {
                NanoVGHelper.drawRect(x + minWidth * (1.0f - progress), y, width, height, color);
            }
            return (height + space) * progress;
        }

        private float renderStage2(float x, float y, float width, float height, float space, float padding, float progress, boolean leftAligned, Color primaryColor, Color backgroundColor, int font, float fontSize, float textHeight) {
            NanoVGHelper.drawRect(x, y, width, height, backgroundColor);

            int textAlpha = (int) (255.0f * progress);
            Color textColor = new Color(255, 255, 255, textAlpha);

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

        private float renderStage3(float x, float y, float width, float height, float space, float padding, boolean leftAligned, Color primaryColor, Color backgroundColor, int font, float fontSize, float textHeight) {
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
