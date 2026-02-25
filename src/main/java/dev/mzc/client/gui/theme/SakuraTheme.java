package dev.mzc.client.gui.theme;

import org.lwjgl.nanovg.NVGColor;

import java.awt.*;

public class SakuraTheme {
    public static final Color PRIMARY = new Color(255, 175, 200); // Sakura Pink
    public static final Color PRIMARY_HOVER = new Color(255, 195, 220);
    public static final Color BACKGROUND = new Color(18, 18, 18, 200); // Darker Dim
    public static final Color PANEL_BG = new Color(33, 33, 33, 126); // Dark Gray Panel with Higher Transparency (30% less opaque than 180)
    public static final Color TEXT = new Color(240, 240, 240); // Off-White Text
    public static final Color TEXT_SECONDARY = new Color(170, 170, 170);
    public static final Color TEXT_ON_PRIMARY = new Color(30, 30, 30); // Dark Text on Pink
    public static final Color BUTTON_BG = new Color(55, 55, 55);
    public static final Color BUTTON_BORDER = new Color(80, 80, 80);
    public static final Color INPUT_BG = new Color(45, 45, 45);
    public static final Color INPUT_BORDER = new Color(80, 80, 80);
    public static final Color SELECTION = new Color(255, 255, 255, 25); // White selection

    public static final float ROUNDING = 4.0f;
    public static final float PANEL_ROUNDING = 15.0f; // More rounded corners for panels (10% look)

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
