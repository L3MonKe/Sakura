package dev.sakura.client.gui.theme;

import org.lwjgl.nanovg.NVGColor;

import java.awt.*;

public class SakuraTheme {
    public static final Color ACCENT = new Color(92, 124, 255);
    public static final Color DANGER = new Color(236, 72, 72);
    public static final Color TEXT = new Color(0, 0, 0);
    public static final Color TEXT_ON_PRIMARY = new Color(30, 30, 30);
    public static final Color BUTTON_BG = new Color(245, 245, 245);
    public static final Color BUTTON_BORDER = new Color(200, 200, 200);

    public static final float ROUNDING = 4.0f;

    public static NVGColor color(Color c, float alphaMod) {
        NVGColor color = NVGColor.create();
        color.r(c.getRed() / 255.0f);
        color.g(c.getGreen() / 255.0f);
        color.b(c.getBlue() / 255.0f);
        color.a((c.getAlpha() / 255.0f) * alphaMod);
        return color;
    }

    public static NVGColor color(Color c) {
        return color(c, 1.0f);
    }

    public static NVGColor color(int r, int g, int b, int a) {
        NVGColor color = NVGColor.create();
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);
        return color;
    }
}
