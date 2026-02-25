package dev.sakura.client.nanovg.font;

public class FontLoader {
    public static int regular(float size) {
        return FontManager.fontWithCJK("regular.otf", size);
    }

    public static int bold(float size) {
        return FontManager.fontWithCJK("regular_bold.otf", size);
    }

    public static int medium(float size) {
        return FontManager.fontWithCJK("regular_medium.otf", size);
    }

    public static int greycliffSemi(float size) {
        return FontManager.fontWithCJK("regular_semi.otf", size);
    }

    public static int solid(float size) {
        return FontManager.font("solid.ttf", size);
    }

    public static int icons(float size) {
        return FontManager.font("icon.ttf", size);
    }

    public static int cjk(float size) {
        return FontManager.font("kuriyama.ttf", size);
    }
}
