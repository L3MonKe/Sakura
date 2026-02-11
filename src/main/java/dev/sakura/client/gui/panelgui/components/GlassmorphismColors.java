package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.Colors;

import java.awt.*;

public final class GlassmorphismColors {
    private GlassmorphismColors() {
    }

    public static final Color PRIMARY_NEON_CYAN = Colors.getColor(58, 107, 255);

    public static final Color BACKGROUND_DARK_PURPLE = Colors.getColor(10, 10, 18);
    public static final Color BACKGROUND = new Color(0, 0, 0, 50);
    public static final Color MODULE_BACKGROUND = new Color(255, 255, 255, 50);
    public static final Color DROPDOWN_BACKGROUND = new Color(0, 0, 0, 255);

    public static final Color TEXT_PRIMARY = Colors.getColor(233, 249, 255);
    public static final Color TEXT_SECONDARY = Colors.getColor(233, 249, 255, 185);
    public static final Color TEXT_TERTIARY = Colors.getColor(233, 249, 255, 125);

    public static final Color BORDER_COLOR = Colors.getColor(58, 107, 255, 18);
    public static final Color HIGHLIGHT = Colors.getColor(58, 107, 255, 12);
    public static final Color SHADOW_COLOR = Colors.getColor(0, 0, 0, 180);

    public static final Color TOGGLE_TRACK_ON_BLUE = PRIMARY_NEON_CYAN;
    public static final Color SLIDER_TRACK_BG = Colors.getColor(0, 0, 0, 140);
    public static final Color SLIDER_FILL_START = PRIMARY_NEON_CYAN;
    public static final Color SLIDER_THUMB_BG = Colors.getColor(255, 255, 255, 255);

    public static final Color HOVER = new Color(255, 255, 255, 50);
}

