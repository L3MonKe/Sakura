package dev.mzc.client.module.impl.client;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TextReplacer {
    // Unicode for "布吉岛" (Bujidao) to avoid encoding issues
    private static final String BUJIDAO = "\u5e03\u5409\u5c9b";

    // Priority list of targets to replace. Longer matches first to consume color codes.
    // 1. §d布吉岛 (Section sign d + Bujidao)
    // 2. &d布吉岛 (Ampersand d + Bujidao)
    // 3. 布吉岛 (Bujidao raw)
    private static final List<String> TARGETS = Arrays.asList(
            "\u00a7d" + BUJIDAO,
            "&d" + BUJIDAO,
            BUJIDAO
    );

    public static boolean containsTarget(String text) {
        if (text == null) return false;
        for (String target : TARGETS) {
            if (text.contains(target)) return true;
        }
        return false;
    }

    public static Text replace(String original) {
        if (original == null) return null;

        String bestTarget = null;
        int firstIndex = -1;

        // Find the first occurring target
        for (String target : TARGETS) {
            int index = original.indexOf(target);
            if (index != -1) {
                if (firstIndex == -1 || index < firstIndex) {
                    firstIndex = index;
                    bestTarget = target;
                }
            }
        }

        if (bestTarget == null) return null;

        MutableText result = Text.empty();
        int lastIndex = 0;
        int index = firstIndex;
        String target = bestTarget;

        while (index != -1) {
            result.append(Text.of(original.substring(lastIndex, index)));
            result.append(getGradientMahiro());

            lastIndex = index + target.length();

            // Search for next occurrence
            // We need to check which target matches next
            index = -1;
            String nextTarget = null;
            int nextMinIndex = -1;

            for (String t : TARGETS) {
                int currIdx = original.indexOf(t, lastIndex);
                if (currIdx != -1) {
                    if (nextMinIndex == -1 || currIdx < nextMinIndex) {
                        nextMinIndex = currIdx;
                        nextTarget = t;
                    }
                }
            }

            if (nextMinIndex != -1) {
                index = nextMinIndex;
                target = nextTarget;
            }
        }
        result.append(Text.of(original.substring(lastIndex)));

        return result;
    }

    public static Text getGradientText(String content) {
        MutableText text = Text.empty();
        long time = System.currentTimeMillis();

        for (int i = 0; i < content.length(); i++) {
            int color = getPinkWhiteColor(i, time);
            text.append(Text.literal(String.valueOf(content.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(color)));
        }
        return text;
    }

    private static Text getGradientMahiro() {
        return getGradientText("Mahiro");
    }

    private static int getPinkWhiteColor(int offset, long time) {
        // Dynamic gradient between Pink and White
        double speed = 2.0;
        double width = 300.0;

        // Sine wave for smooth transition
        double progress = (Math.sin((time * 0.003 * speed + offset * 0.5)) + 1.0) / 2.0;

        Color pink = new Color(255, 180, 225); // Lighter Pink
        Color white = Color.WHITE;

        int r = (int) (pink.getRed() + (white.getRed() - pink.getRed()) * progress);
        int g = (int) (pink.getGreen() + (white.getGreen() - pink.getGreen()) * progress);
        int b = (int) (pink.getBlue() + (white.getBlue() - pink.getBlue()) * progress);

        return (r << 16) | (g << 8) | b;
    }
}
