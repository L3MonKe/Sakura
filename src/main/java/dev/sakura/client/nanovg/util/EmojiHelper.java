package dev.sakura.client.nanovg.util;

import dev.sakura.client.Sakura;
import dev.sakura.client.nanovg.NanoVGRenderer;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.nanovg.NanoVG.*;

public class EmojiHelper {

    private static final String TWEMOJI_CDN_BASE = "https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/72x72/";
    private static final int CACHE_MAX_SIZE = 64;
    private static final long RETRY_INTERVAL_MS = 60000;
    private static final Map<String, Integer> textureCache = new LinkedHashMap<>();
    private static final Set<String> pendingDownloads = ConcurrentHashMap.newKeySet();
    private static final Map<String, byte[]> completedDownloads = new ConcurrentHashMap<>();
    private static final Map<String, Long> failedDownloads = new ConcurrentHashMap<>();
    private static final ExecutorService downloadExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "Sakura-EmojiDownloader");
        t.setDaemon(true);
        return t;
    });

    public static class TextSegment {
        public final String text;
        public final boolean isEmoji;

        public TextSegment(String text, boolean isEmoji) {
            this.text = text;
            this.isEmoji = isEmoji;
        }
    }

    public static List<TextSegment> splitText(String text) {
        List<TextSegment> segments = new ArrayList<>();
        if (text == null || text.isEmpty()) return segments;

        StringBuilder regularText = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int codePoint = text.codePointAt(i);
            int codeUnits = Character.charCount(codePoint);

            if (isEmojiStart(codePoint)) {
                if (regularText.length() > 0) {
                    segments.add(new TextSegment(regularText.toString(), false));
                    regularText.setLength(0);
                }

                int emojiLen = getEmojiSequenceLength(text, i);
                String emoji = text.substring(i, i + emojiLen);
                segments.add(new TextSegment(emoji, true));
                i += emojiLen;
            } else {
                regularText.appendCodePoint(codePoint);
                i += codeUnits;
            }
        }

        if (regularText.length() > 0) {
            segments.add(new TextSegment(regularText.toString(), false));
        }

        return segments;
    }

    private static boolean isEmojiStart(int codePoint) {
        return (codePoint >= 0x1F000 && codePoint <= 0x1FFFF) ||
                (codePoint >= 0x2600 && codePoint <= 0x27BF) ||
                (codePoint >= 0x2300 && codePoint <= 0x23FF) ||
                (codePoint >= 0x2B50 && codePoint <= 0x2B55) ||
                (codePoint >= 0x203C && codePoint <= 0x3299) ||
                isSpecificEmojiSymbol(codePoint);
    }

    private static boolean isSpecificEmojiSymbol(int codePoint) {
        return codePoint == 0x26A7 ||
                codePoint == 0x2640 ||
                codePoint == 0x2642 ||
                codePoint == 0x2695 ||
                codePoint == 0x262E ||
                codePoint == 0x262F ||
                codePoint == 0x2622 ||
                codePoint == 0x2623 ||
                codePoint == 0x2764 ||
                codePoint == 0x2B50 ||
                codePoint == 0x2B55 ||
                codePoint == 0x2934 ||
                codePoint == 0x2935 ||
                codePoint == 0x25AA ||
                codePoint == 0x25AB ||
                codePoint == 0x25B6 ||
                codePoint == 0x25C0 ||
                codePoint == 0x25FB ||
                codePoint == 0x25FC ||
                codePoint == 0x25FD ||
                codePoint == 0x25FE;
    }

    private static int getEmojiSequenceLength(String text, int start) {
        int i = start;
        int codePoint = text.codePointAt(i);
        int len = Character.charCount(codePoint);
        i += len;

        if (codePoint >= 0x1F1E6 && codePoint <= 0x1F1FF && i < text.length()) {
            int nextCp = text.codePointAt(i);
            if (nextCp >= 0x1F1E6 && nextCp <= 0x1F1FF) {
                len += Character.charCount(nextCp);
                i += Character.charCount(nextCp);
            }
        }

        while (i < text.length()) {
            int nextCp = text.codePointAt(i);
            if (nextCp == 0x200D) {
                len += Character.charCount(nextCp);
                i += Character.charCount(nextCp);
                if (i < text.length()) {
                    int afterZwj = text.codePointAt(i);
                    len += Character.charCount(afterZwj);
                    i += Character.charCount(afterZwj);
                    while (i < text.length()) {
                        int vsCp = text.codePointAt(i);
                        if (vsCp == 0xFE0F || vsCp == 0xFE0E) {
                            len += Character.charCount(vsCp);
                            i += Character.charCount(vsCp);
                        } else {
                            break;
                        }
                    }
                }
            } else if (nextCp == 0xFE0F || nextCp == 0xFE0E) {
                len += Character.charCount(nextCp);
                i += Character.charCount(nextCp);
            } else {
                break;
            }
        }

        return len;
    }

    public static boolean containsEmoji(String text) {
        if (text == null || text.isEmpty()) return false;
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            if (isEmojiStart(codePoint)) return true;
            i += Character.charCount(codePoint);
        }
        return false;
    }

    public static float getEmojiWidth(float fontSize) {
        return fontSize;
    }

    public static float getTextWidthWithEmoji(String text, int font, float size) {
        List<TextSegment> segments = splitText(text);
        float totalWidth = 0;
        for (TextSegment seg : segments) {
            if (seg.isEmoji) {
                totalWidth += getEmojiWidth(size);
            } else {
                totalWidth += NanoVGHelper.getTextWidth(seg.text, font, size);
            }
        }
        return totalWidth;
    }

    public static void drawStringWithEmoji(String text, float x, float y, int font, float size, Color color) {
        List<TextSegment> segments = splitText(text);
        float currentX = x;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        for (TextSegment seg : segments) {
            if (seg.isEmoji) {
                drawEmojiTexture(vg, seg.text, currentX, y, size);
                currentX += getEmojiWidth(size);
            } else {
                NanoVGHelper.drawString(seg.text, currentX, y, font, size, color);
                currentX += NanoVGHelper.getTextWidth(seg.text, font, size);
            }
        }
    }

    public static void drawGlowingStringWithEmoji(String text, float x, float y, int font, float size, Color color, float glowRadius, int intensity) {
        List<TextSegment> segments = splitText(text);
        float currentX = x;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        for (TextSegment seg : segments) {
            if (seg.isEmoji) {
                for (int g = 0; g < intensity; g++) {
                    drawEmojiTexture(vg, seg.text, currentX, y, size, 0.15f);
                }
                drawEmojiTexture(vg, seg.text, currentX, y, size);
                currentX += getEmojiWidth(size);
            } else {
                NanoVGHelper.drawGlowingString(seg.text, currentX, y, font, size, color, glowRadius, intensity);
                currentX += NanoVGHelper.getTextWidth(seg.text, font, size);
            }
        }
    }

    public static void drawGradientStringWithEmoji(long vg, float x, float baseY, int font, float size,
                                                   String text, double offsetDeg, int colorStepDeg,
                                                   float totalTextW, float blockW,
                                                   ColorSupplier colorSupplier) {
        if (text == null || text.isEmpty()) return;

        List<TextSegment> segments = splitText(text);
        float fontH = NanoVGHelper.getFontHeight(font, size);
        float scissorY = baseY - fontH - 2.0f;
        float scissorH = fontH + 4.0f;
        float overlap = 0.75f;

        float currentX = x;
        for (TextSegment seg : segments) {
            if (seg.isEmoji) {
                drawEmojiTexture(vg, seg.text, currentX, baseY, size);
                currentX += getEmojiWidth(size);
            } else {
                float segW = NanoVGHelper.getTextWidth(seg.text, font, size);
                if (segW > 0 && !seg.text.isEmpty()) {
                    int segCount = (int) Math.max(4, Math.min(60, Math.ceil(segW / Math.max(1.0f, blockW))));
                    float subSegW = segW / segCount;

                    nvgFontFaceId(vg, font);
                    nvgFontSize(vg, size);
                    nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

                    for (int i = 0; i < segCount; i++) {
                        float subSegX = currentX + i * subSegW;
                        double absOffset = offsetDeg + ((subSegX - x) / Math.max(1.0f, blockW)) * colorStepDeg;
                        Color col = colorSupplier.getColor(absOffset);
                        col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 255);

                        nvgSave(vg);
                        nvgScissor(vg, subSegX - overlap, scissorY, subSegW + overlap * 2.0f, scissorH);
                        nvgFillColor(vg, NanoVGHelper.nvgColor(col));
                        nvgText(vg, currentX, baseY, seg.text);
                        nvgRestore(vg);
                    }
                }
                currentX += segW;
            }
        }
    }

    @FunctionalInterface
    public interface ColorSupplier {
        Color getColor(double offsetDeg);
    }

    private static void drawEmojiTexture(long vg, String emoji, float x, float baseY, float fontSize) {
        drawEmojiTexture(vg, emoji, x, baseY, fontSize, 1.0f);
    }

    private static void drawEmojiTexture(long vg, String emoji, float x, float baseY, float fontSize, float alpha) {
        int texture = getOrCreateEmojiTexture(emoji);
        if (texture <= 0) return;

        float emojiSize = fontSize;
        float drawY = baseY - emojiSize * 0.8f;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            nvgImagePattern(vg, x, drawY, emojiSize, emojiSize, 0, texture, alpha, paint);
            nvgBeginPath(vg);
            nvgRect(vg, x, drawY, emojiSize, emojiSize);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    private static String emojiToTwemojiCode(String emoji) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < emoji.length()) {
            int cp = emoji.codePointAt(i);
            if (sb.length() > 0) sb.append('-');
            sb.append(String.format("%04x", cp));
            i += Character.charCount(cp);
        }
        return sb.toString();
    }

    private static Path getCacheDir() {
        Path dir = Paths.get(System.getProperty("user.dir"), "sakura", "emoji_cache");
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {
        }
        return dir;
    }

    private static int getOrCreateEmojiTexture(String emoji) {
        if (textureCache.containsKey(emoji)) {
            return textureCache.get(emoji);
        }

        byte[] pngData = completedDownloads.remove(emoji);
        if (pngData != null) {
            int texture = createTextureFromPng(pngData);
            if (texture > 0) {
                while (textureCache.size() >= CACHE_MAX_SIZE) {
                    Map.Entry<String, Integer> eldest = textureCache.entrySet().iterator().next();
                    nvgDeleteImage(NanoVGRenderer.INSTANCE.getContext(), eldest.getValue());
                    textureCache.remove(eldest.getKey());
                }
                textureCache.put(emoji, texture);
                return texture;
            } else {
                Sakura.LOGGER.warn("EmojiHelper: nvgCreateImageMem failed for emoji: {}", emojiToTwemojiCode(emoji));
                failedDownloads.put(emoji, System.currentTimeMillis());
            }
        }

        startDownload(emoji);
        return -1;
    }

    private static void startDownload(String emoji) {
        if (pendingDownloads.contains(emoji)) return;
        if (textureCache.containsKey(emoji)) return;

        Long failTime = failedDownloads.get(emoji);
        if (failTime != null) {
            if (System.currentTimeMillis() - failTime < RETRY_INTERVAL_MS) return;
            failedDownloads.remove(emoji);
        }

        String code = emojiToTwemojiCode(emoji);
        Path cacheFile = getCacheDir().resolve(code + ".png");

        if (Files.exists(cacheFile)) {
            try {
                byte[] data = Files.readAllBytes(cacheFile);
                if (data.length > 0) {
                    completedDownloads.put(emoji, data);
                    Sakura.LOGGER.info("EmojiHelper: loaded emoji {} from local cache", code);
                    return;
                }
            } catch (IOException ignored) {
            }
        }

        if (!pendingDownloads.add(emoji)) return;

        downloadExecutor.submit(() -> {
            try {
                String url = TWEMOJI_CDN_BASE + code + ".png";
                byte[] downloaded = downloadUrl(url);

                if (downloaded == null || downloaded.length == 0) {
                    String fallbackCode = code.replace("-fe0f", "");
                    if (!fallbackCode.equals(code)) {
                        String fallbackUrl = TWEMOJI_CDN_BASE + fallbackCode + ".png";
                        downloaded = downloadUrl(fallbackUrl);
                    }
                }

                if (downloaded != null && downloaded.length > 0) {
                    try {
                        Files.createDirectories(cacheFile.getParent());
                        Files.write(cacheFile, downloaded);
                    } catch (IOException ignored) {
                    }
                    completedDownloads.put(emoji, downloaded);
                    Sakura.LOGGER.info("EmojiHelper: downloaded emoji {} from CDN", code);
                } else {
                    failedDownloads.put(emoji, System.currentTimeMillis());
                    Sakura.LOGGER.warn("EmojiHelper: failed to download emoji {}", code);
                }
            } catch (Exception e) {
                failedDownloads.put(emoji, System.currentTimeMillis());
                Sakura.LOGGER.warn("EmojiHelper: error downloading emoji {}: {}", code, e.getMessage());
            } finally {
                pendingDownloads.remove(emoji);
            }
        });
    }

    private static byte[] downloadUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Sakura-Client/1.0");
            if (conn.getResponseCode() != 200) return null;
            try (InputStream is = conn.getInputStream();
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) != -1) {
                    bos.write(buf, 0, n);
                }
                return bos.toByteArray();
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static int createTextureFromPng(byte[] pngData) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(pngData.length);
        buffer.put(pngData);
        buffer.flip();
        long vg = NanoVGRenderer.INSTANCE.getContext();
        return nvgCreateImageMem(vg, NVG_IMAGE_GENERATE_MIPMAPS, buffer);
    }

    public static void cleanup() {
        downloadExecutor.shutdownNow();
        for (int textureId : textureCache.values()) {
            nvgDeleteImage(NanoVGRenderer.INSTANCE.getContext(), textureId);
        }
        textureCache.clear();
        completedDownloads.clear();
        failedDownloads.clear();
        pendingDownloads.clear();
    }
}
