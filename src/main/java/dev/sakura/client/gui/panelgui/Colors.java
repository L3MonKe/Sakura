package dev.sakura.client.gui.panelgui;

import java.awt.*;

public final class Colors {
    private Colors() {
    }

    public static Color getColor(int red, int green, int blue) {
        return new Color(red, green, blue, 255);
    }

    public static Color getColor(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha);
    }

    public static Color withAlpha(Color color, float alpha01) {
        float a = Math.max(0.0f, Math.min(1.0f, alpha01));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(a * 255.0f));
    }

    public static Color blendColors(Color c1, Color c2, float ratio01) {
        float t = Math.max(0.0f, Math.min(1.0f, ratio01));
        int r = Math.round(c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = Math.round(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = Math.round(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = Math.round(c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(clamp8(r), clamp8(g), clamp8(b), clamp8(a));
    }

    private static int clamp8(int v) {
        return Math.max(0, Math.min(255, v));
    }
}

