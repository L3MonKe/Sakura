package dev.sakura.client.nanovg.font;

import dev.sakura.client.Sakura;
import dev.sakura.client.nanovg.NanoVGRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.nanovg.NanoVG.nvgAddFallbackFontId;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;

public class FontManager {
    private static final Map<String, Integer> fontCache = new HashMap<>();
    private static final Map<String, FontData> fontDataCache = new HashMap<>();
    private static final Set<String> fallbackRegistered = new HashSet<>();

    public static int font(String fontName) {
        return fontCache.computeIfAbsent(fontName, FontManager::loadFont);
    }

    public static int fontWithCJK(String fontName) {
        int primaryFont = font(fontName);
        registerCJKFallback(fontName);
        return primaryFont;
    }

    private static void registerCJKFallback(String fontName) {
        String key = fontName + "-cjk";
        if (fallbackRegistered.contains(key)) return;

        long vg = NanoVGRenderer.INSTANCE.getContext();
        int cjkFont = FontLoader.cjk();
        int primaryFont = font(fontName);

        nvgAddFallbackFontId(vg, primaryFont, cjkFont);
        fallbackRegistered.add(key);
    }

    private static int loadFont(String fontName) {
        FontData fontData = fontDataCache.computeIfAbsent(fontName, FontManager::loadFontData);

        if (fontData == null) {
            throw new RuntimeException("无法加载字体: " + fontName);
        }

        long vg = NanoVGRenderer.INSTANCE.getContext();
        int fontId = nvgCreateFontMem(vg, fontName, fontData.buffer, false);

        if (fontId == -1) {
            throw new RuntimeException("无法创建字体: " + fontName);
        }

        return fontId;
    }

    private static FontData loadFontData(String fontName) {
        try {
            String path = "/assets/sakura/fonts/" + fontName;
            InputStream is = FontManager.class.getResourceAsStream(path);

            if (is == null) {
                Sakura.LOGGER.error("无法找到字体文件: {}", path);
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

    private record FontData(ByteBuffer buffer) {
    }
}
