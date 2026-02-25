package dev.sakura.client.gui.theme;

import org.lwjgl.nanovg.NVGColor;

import java.awt.*;

public class SakuraTheme {
    public static final Color PRIMARY = new Color(255, 255, 255); // White
    public static final Color PRIMARY_HOVER = new Color(240, 240, 240);
    public static final Color BACKGROUND = new Color(20, 20, 20, 150); // Screen Dim
    public static final Color PANEL_BG = new Color(255, 255, 255, 255); // Opaque White Panel
    public static final Color TEXT = new Color(0, 0, 0); // Pure Black Text
    public static final Color TEXT_SECONDARY = new Color(100, 100, 100);
    public static final Color TEXT_ON_PRIMARY = new Color(30, 30, 30);
    public static final Color BUTTON_BG = new Color(245, 245, 245);
    public static final Color BUTTON_BORDER = new Color(200, 200, 200);
    public static final Color INPUT_BG = new Color(240, 240, 240);
    public static final Color INPUT_BORDER = new Color(200, 200, 200);
    public static final Color SELECTION = new Color(0, 0, 0, 20); // Light Gray Selection

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
