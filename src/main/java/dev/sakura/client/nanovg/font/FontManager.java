package dev.sakura.client.nanovg.font;

import dev.sakura.client.nanovg.NanoVGRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.nanovg.NanoVG.*;

public class FontManager {
    private static final Map<String, Integer> fontCache = new HashMap<>();
    private static final Map<String, FontData> fontDataCache = new HashMap<>();
    private static final Set<String> fallbackRegistered = new HashSet<>();

    public static int font(String fontName, float size) {
        String key = fontName + "-" + size;
        return fontCache.computeIfAbsent(key, k -> loadFont(fontName, size));
    }

    public static int fontWithCJK(String fontName, float size) {
        int primaryFont = font(fontName, size);
        registerCJKFallback(fontName, size);
        return primaryFont;
    }

    private static void registerCJKFallback(String fontName, float size) {
        String key = fontName + "-" + size + "-cjk";
        if (fallbackRegistered.contains(key)) return;

        long vg = NanoVGRenderer.INSTANCE.getContext();
        int cjkFont = FontLoader.cjk(size);
        int primaryFont = font(fontName, size);

        nvgAddFallbackFontId(vg, primaryFont, cjkFont);
        fallbackRegistered.add(key);
    }

    private static int loadFont(String fontName, float size) {
        FontData fontData = fontDataCache.computeIfAbsent(fontName, FontManager::loadFontData);

        if (fontData == null) {
            throw new RuntimeException("无法加载字体: " + fontName);
        }

        long vg = NanoVGRenderer.INSTANCE.getContext();
        int fontId = nvgCreateFontMem(vg, fontName, fontData.buffer, false);

        if (fontId == -1) {
            throw new RuntimeException("无法创建字体: " + fontName);
        }

        nvgFontSize(vg, size);

        return fontId;
    }

    private static FontData loadFontData(String fontName) {
        try {
            String path = "/assets/sakura/fonts/" + fontName;
            InputStream is = FontManager.class.getResourceAsStream(path);

            if (is == null) {
                System.err.println("无法找到字体文件: " + path);
                return null;
            }

            byte[] bytes = is.readAllBytes();
            ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
            buffer.put(bytes);
            buffer.flip();

            return new FontData(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字体数据包装类
     */
    private static class FontData {
        final ByteBuffer buffer;

        FontData(ByteBuffer buffer) {
            this.buffer = buffer;
        }
    }
}
