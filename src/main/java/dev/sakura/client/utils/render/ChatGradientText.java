package dev.sakura.client.utils.render;

import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.color.ColorUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.WeakHashMap;

public class ChatGradientText {
    private static final WeakHashMap<OrderedText, String> RAW_LINE_MAP = new WeakHashMap<>();

    public static void putRawLine(OrderedText orderedText, String rawLine) {
        if (orderedText == null || rawLine == null || rawLine.isEmpty()) return;
        RAW_LINE_MAP.put(orderedText, rawLine);
    }

    public static @Nullable String getRawLine(OrderedText orderedText) {
        return RAW_LINE_MAP.get(orderedText);
    }

    public static boolean isGradientLine(@Nullable String rawLine) {
        return rawLine != null && rawLine.startsWith(ChatUtil.GRADIENT_SYNC_CODE);
    }

    public static void drawPulseLine(DrawContext context, TextRenderer textRenderer, int x, int y, float opacity, String rawLine) {
        Color start = ClickGui.mainColor.get();
        Color end = ClickGui.secondColor.get();

        int alpha = MathHelper.clamp((int) (opacity * 255.0f), 0, 255);
        start = new Color(start.getRed(), start.getGreen(), start.getBlue(), alpha);
        end = new Color(end.getRed(), end.getGreen(), end.getBlue(), alpha);

        int speed = Math.max(1, ClickGui.colorSpeed.get().intValue());
        int separation = Math.max(1, ClickGui.colorIndex.get().intValue());

        char[] chars = rawLine.toCharArray();
        int visibleCount = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '§' && i + 1 < chars.length) {
                i++;
                continue;
            }
            visibleCount++;
        }

        float drawX = x;
        boolean parsingCode = false;
        StringBuilder activeCodes = new StringBuilder();
        int visibleIndex = 0;

        for (char c : chars) {
            if (c == '§') {
                parsingCode = true;
                continue;
            }
            if (parsingCode) {
                if (c == 'r') {
                    activeCodes.setLength(0);
                } else {
                    activeCodes.append('§').append(c);
                }
                parsingCode = false;
                continue;
            }

            visibleIndex++;
            Color pulse = ColorUtil.interpolateColorsBackAndForth(speed, separation * visibleIndex, start, end, false);
            String draw = activeCodes.isEmpty() ? String.valueOf(c) : activeCodes + String.valueOf(c);

            context.drawTextWithShadow(textRenderer, draw, (int) drawX, y, pulse.getRGB());
            drawX += textRenderer.getWidth(draw);
        }
    }

    public static void drawPulsePlain(DrawContext context, TextRenderer textRenderer, int x, int y, float opacity, String text) {
        if (text == null || text.isEmpty()) return;

        Color start = ClickGui.mainColor.get();
        Color end = ClickGui.secondColor.get();

        int alpha = MathHelper.clamp((int) (opacity * 255.0f), 0, 255);
        start = new Color(start.getRed(), start.getGreen(), start.getBlue(), alpha);
        end = new Color(end.getRed(), end.getGreen(), end.getBlue(), alpha);

        int speed = Math.max(1, ClickGui.colorSpeed.get().intValue());
        int separation = Math.max(1, ClickGui.colorIndex.get().intValue());

        float drawX = x;
        int index = 0;
        for (char c : text.toCharArray()) {
            index++;
            Color pulse = ColorUtil.interpolateColorsBackAndForth(speed, separation * index, start, end, false);
            String s = String.valueOf(c);
            context.drawTextWithShadow(textRenderer, s, (int) drawX, y, pulse.getRGB());
            drawX += textRenderer.getWidth(s);
        }
    }
}

