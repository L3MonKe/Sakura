package dev.sakura.client.nanovg.font;

public class FontLoader {
    public static int regular() {
        return FontManager.fontWithCJK("regular.otf");
    }

    public static int bold() {
        return FontManager.fontWithCJK("regular_bold.otf");
    }

    public static int medium() {
        return FontManager.fontWithCJK("regular_medium.otf");
    }

    public static int greycliffSemi() {
        return FontManager.fontWithCJK("regular_semi.otf");
    }

    public static int solid() {
        return FontManager.font("solid.ttf");
    }

    public static int icons() {
        return FontManager.font("woqubuzaoshuo.ttf");
    }

    public static int newIc() {
        return FontManager.font("icon.ttf");
    }

    public static int cjk() {
        return FontManager.font("kuriyama.ttf");
    }

    public static int comfortaa() {
        return FontManager.fontWithCJK("Comfortaa.ttf");
    }

    public static int ax() {
        return FontManager.fontWithCJK("ax-regular.ttf");
    }

    public static int geologica() {
        return FontManager.fontWithCJK("geologica.ttf");
    }

    public static int material() {
        return FontManager.fontWithCJK("material.ttf");
    }

    public static int tenacity() {
        return FontManager.fontWithCJK("tenacity-bold.ttf");
    }

    public static int monaBold() {
        return FontManager.fontWithCJK("Mona-Bold-2.ttf");
    }
}
