package dev.mzc.client.module.impl.client;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.render.RenderUtil;
import dev.mzc.client.values.Value;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;

import dev.mzc.client.module.impl.misc.NameProtect;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.awt.*;

public class ClickGui extends Module {
    public enum ColorMode {
        Fade("渐变"),
        Rainbow("彩虹"),
        Astolfo("阿斯托尔福"),
        Dynamic("动态"),
        Tenacity("Tenacity"),
        Static("静态"),
        Double("双色");

        private final String cnName;

        ColorMode(String cnName) {
            this.cnName = cnName;
        }


    }

    public enum Language {
        Chinese("中文"),
        English("英文");

        private final String cnName;

        Language(String cnName) {
            this.cnName = cnName;
        }


    }

    public enum GuiStyle {
        Sakura("樱花"),
        MZC("MZC");

        private final String cnName;

        GuiStyle(String cnName) {
            this.cnName = cnName;
        }


    }

    public enum ModuleFilter {
        All("全部"),
        Safe("安全"),
        Hack("作弊");

        private final String cnName;

        ModuleFilter(String cnName) {
            this.cnName = cnName;
        }


    }

    public static EnumValue<GuiStyle> style = new EnumValue<>("Style", "风格", GuiStyle.Sakura);
    public static EnumValue<Language> language = new EnumValue<>("Language", "语言", Language.English);
    public static EnumValue<ModuleFilter> moduleFilter = new EnumValue<>("Module Filter", "模块筛选", ModuleFilter.Safe);
    
    // MZC Settings
    public static Value<Double> mzcScale = new NumberValue<>("MZC Scale", "缩放", 1.0, 0.5, 2.0, 0.05, () -> style.is(GuiStyle.MZC));
    public static Value<Boolean> mzcBlur = new BoolValue("Blur", "背景模糊", true, () -> style.is(GuiStyle.MZC));
    public static Value<Boolean> mzcGlow = new BoolValue("Glow", "发光", false, () -> style.is(GuiStyle.MZC));
    public static Value<Double> mzcCornerRadius = new NumberValue<>("Corner Radius", "圆角半径", 10.0, 0.0, 20.0, 1.0, () -> style.is(GuiStyle.MZC));
    public static Value<Color> mzcThemeColor = new ColorValue("Theme Color", "主题颜色", new Color(20, 220, 140), () -> style.is(GuiStyle.MZC));

    // Sakura Settings
    public static Value<Double> guiScale = new NumberValue<>("Gui Scale", "界面缩放", 1.0, 0.5, 2.0, 0.05, () -> style.is(GuiStyle.Sakura));
    public static Value<Double> fontSize = new NumberValue<>("Font Size", "字体大小", 11.0, 6.0, 20.0, 0.5, () -> style.is(GuiStyle.Sakura));
    public static Value<Boolean> globalFontReplacement = new BoolValue("Global Font", "全局字体", false);

    public static Value<Color> backgroundColor = new ColorValue("Background Color", "背景颜色", new Color(28, 28, 28), () -> style.is(GuiStyle.Sakura));
    public static Value<Color> expandedBackgroundColor = new ColorValue("Expanded Background", "展开背景颜色", new Color(20, 20, 20), () -> style.is(GuiStyle.Sakura));
    public static EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Tenacity, () -> style.is(GuiStyle.Sakura));
    public static ColorValue mainColor = new ColorValue("Main Color", "主色调", new Color(255, 183, 197), () -> style.is(GuiStyle.Sakura) && !colorMode.is(ColorMode.Rainbow));
    public static ColorValue secondColor = new ColorValue("Second Color", "次色调", new Color(255, 133, 161), () -> style.is(GuiStyle.Sakura) && (colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Double)));
    public static final Value<Double> colorSpeed = new NumberValue<>("Color Speed", "颜色速度", 4.0, 1.0, 10.0, 0.5, () -> style.is(GuiStyle.Sakura) && (colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Dynamic)));
    public static final Value<Double> colorIndex = new NumberValue<>("Color Separation", "颜色间隔", 20.0, 1.0, 100.0, 1.0, () -> style.is(GuiStyle.Sakura) && colorMode.is(ColorMode.Tenacity));
    public static final Value<Double> rainbowSpeed = new NumberValue<>("Rainbow Speed", "彩虹速度", 2000.0, 500.0, 5000.0, 100.0, () -> style.is(GuiStyle.Sakura) && colorMode.is(ColorMode.Rainbow));
    public static final Value<Double> fadeSpeed = new NumberValue<>("Fade Speed", "渐变速度", 5.0, 1.0, 10.0, 0.5, () -> style.is(GuiStyle.Sakura) && colorMode.is(ColorMode.Fade));
    public static final Value<Double> astolfoSaturation = new NumberValue<>("Saturation", "饱和度", 0.8, 0.0, 1.0, 0.05, () -> style.is(GuiStyle.Sakura) && colorMode.is(ColorMode.Astolfo));
    public static final Value<Double> astolfoBrightness = new NumberValue<>("Brightness", "亮度", 1.0, 0.0, 1.0, 0.05, () -> style.is(GuiStyle.Sakura) && colorMode.is(ColorMode.Astolfo));

    public static Value<Boolean> backgroundBlur = new BoolValue("Background Blur", "背景模糊", true, () -> style.is(GuiStyle.Sakura));
    public static Value<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> style.is(GuiStyle.Sakura) && backgroundBlur.get());
    public static final Value<Boolean> bjdOnly = new BoolValue("BJD Only", "布吉岛筛选", false, () -> false);

    private dev.mzc.client.gui.clickgui.vape.MZCClickGuiScreen mzcClickGui;

    public ClickGui() {
        super("ClickGui", "点击GUI", Category.Client);
        this.setType(ModuleType.All);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null && mc.mouse == null) {
            this.toggle();
            return;
        }

        if (style.get() == GuiStyle.MZC) {
            if (mzcClickGui == null) {
                mzcClickGui = new dev.mzc.client.gui.clickgui.vape.MZCClickGuiScreen();
            }
            mc.setScreen(mzcClickGui);
        } else {
            mc.setScreen(Sakura.CLICKGUI);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.currentScreen != null) {
            mc.setScreen(null);
        }
    }

    public static int colors(int tick) {
        return color(tick).getRGB();
    }

    public static int color() {
        return color(1).getRGB();
    }

    public static Color color(int tick) {
        return switch (colorMode.get()) {
            case Fade -> ColorUtil.fade(fadeSpeed.get().intValue(), tick * 20, new Color(mainColor.get().getRGB()), 1);
            case Static -> mainColor.get();
            case Astolfo ->
                    new Color(ColorUtil.swapAlpha(astolfoRainbow(tick, astolfoSaturation.get().floatValue(), astolfoBrightness.get().floatValue()), 255));
            case Rainbow ->
                    new Color(RenderUtil.getRainbow(System.currentTimeMillis(), rainbowSpeed.get().intValue(), tick));
            case Tenacity ->
                    ColorUtil.interpolateColorsBackAndForth(colorSpeed.get().intValue(), colorIndex.get().intValue() * tick, mainColor.get(), secondColor.get(), false);
            case Dynamic ->
                    new Color(ColorUtil.swapAlpha(ColorUtil.colorSwitch(mainColor.get(), new Color(ColorUtil.darker(mainColor.get().getRGB(), 0.25F)), 2000.0F, 0, 10, colorSpeed.get()).getRGB(), 255));
            case Double -> {
                tick *= 200;
                yield new Color(ColorUtil.colorSwitch2(mainColor.get(), secondColor.get(), 2000, -tick / 40, 75, 2));
            }
        };
    }

    public static Color color2(int tick) {
        return switch (colorMode.get()) {
            case Tenacity, Double -> color(tick + 50);
            default -> color(tick);
        };
    }

    public static int astolfoRainbow(final int offset, final float saturation, final float brightness) {
        double currentColor = Math.ceil((double) (System.currentTimeMillis() + offset * 20L)) / 6.0;
        return Color.getHSBColor(((float) ((currentColor %= 360.0) / 360.0) < 0.5) ? (-(float) (currentColor / 360.0)) : ((float) (currentColor / 360.0)), saturation, brightness).getRGB();
    }

    public static double getGuiScale() {
        if (style.get() == GuiStyle.MZC) {
            return mzcScale.get();
        }
        return guiScale.get();
    }

    public static double getFontSize() {
        return fontSize.get();
    }

    public static Text getMZCGradientText(Text original) {
        if (original == null) return null;
        return replaceMZCInText(original);
    }

    private static MutableText replaceMZCInText(Text text) {
        MutableText result;

        if (text.getContent() instanceof PlainTextContent) {
            String content = ((PlainTextContent) text.getContent()).string();
            if (content.toLowerCase().contains("mzc")) {
                result = replaceMZCStringWithStyle(content, text.getStyle());
            } else {
                result = Text.literal(content).setStyle(text.getStyle());
            }
        } else {
            result = text.copyContentOnly().setStyle(text.getStyle());
        }

        for (Text sibling : text.getSiblings()) {
            result.append(replaceMZCInText(sibling));
        }

        return result;
    }

    private static MutableText replaceMZCStringWithStyle(String original, Style style) {
        MutableText result = Text.empty();
        String lower = original.toLowerCase();
        int lastIndex = 0;
        int index = lower.indexOf("mzc");
        long time = System.currentTimeMillis();

        while (index != -1) {
            // Previous part
            String prefix = original.substring(lastIndex, index);
            if (!prefix.isEmpty()) {
                result.append(Text.literal(prefix).setStyle(style));
            }

            // MZC part
            String content = original.substring(index, index + 3);
            for (int i = 0; i < content.length(); i++) {
                int color = getMZCColor(i, time);
                result.append(Text.literal(String.valueOf(content.charAt(i)))
                        .setStyle(style.withColor(color)));
            }

            lastIndex = index + 3;
            index = lower.indexOf("mzc", lastIndex);
        }

        String suffix = original.substring(lastIndex);
        if (!suffix.isEmpty()) {
            result.append(Text.literal(suffix).setStyle(style));
        }

        return result;
    }

    private static int getMZCColor(int offset, long time) {
        if (style.get() == GuiStyle.MZC) {
            // Use MZC theme color for MZC style
            Color start = mzcThemeColor.get();
            Color end = Color.WHITE;
            
            double speed = 2.0;
            double progress = (Math.sin((time * 0.003 * speed + offset * 0.5)) + 1.0) / 2.0;
            
            int r = (int) (start.getRed() + (end.getRed() - start.getRed()) * progress);
            int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * progress);
            int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * progress);
            return (r << 16) | (g << 8) | b;
        } else {
            // Use ClickGui color mode for Sakura style
            // We use the 'colors(int tick)' method logic but adapted for offset
            // The 'tick' in colors(tick) is usually used for offset
            // We can map our character offset to the tick parameter
            
            return colors(offset);
        }
    }
}
