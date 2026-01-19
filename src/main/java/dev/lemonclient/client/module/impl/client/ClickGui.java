package dev.lemonclient.client.module.impl.client;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;
import dev.lemonclient.client.utils.color.ColorUtil;
import dev.lemonclient.client.utils.render.RenderUtil;
import dev.lemonclient.client.values.Value;
import dev.lemonclient.client.values.impl.BoolValue;
import dev.lemonclient.client.values.impl.ColorValue;
import dev.lemonclient.client.values.impl.EnumValue;
import dev.lemonclient.client.values.impl.NumberValue;

import java.awt.*;

public class ClickGui extends Module {
    public enum ColorMode {
        Fade, Rainbow, Astolfo, Dynamic, Tenacity, Static, Double
    }

    public enum Language {
        Chinese, English
    }

    public static Value<Double> guiScale = new NumberValue<>("Gui Scale", "界面缩放", 1.0, 0.5, 2.0, 0.05);
    public static Value<Double> fontSize = new NumberValue<>("Font Size", "字体大小", 11.0, 6.0, 20.0, 0.5);
    public static EnumValue<Language> language = new EnumValue<>("Language", "语言", Language.English);

    public static Value<Color> backgroundColor = new ColorValue("Background Color", "背景颜色", new Color(28, 28, 28));
    public static Value<Color> expandedBackgroundColor = new ColorValue("Expanded Background", "展开背景颜色", new Color(20, 20, 20));
    public static EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Tenacity);
    public static ColorValue mainColor = new ColorValue("Main Color", "主色调", new Color(255, 183, 197), () -> !colorMode.is(ColorMode.Rainbow));
    public static ColorValue secondColor = new ColorValue("Second Color", "次色调", new Color(255, 133, 161), () -> colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Double));
    public static final Value<Double> colorSpeed = new NumberValue<>("Color Speed", "颜色速度", 4.0, 1.0, 10.0, 0.5, () -> colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Dynamic));
    public static final Value<Double> colorIndex = new NumberValue<>("Color Separation", "颜色间隔", 20.0, 1.0, 100.0, 1.0, () -> colorMode.is(ColorMode.Tenacity));
    public static final Value<Double> rainbowSpeed = new NumberValue<>("Rainbow Speed", "彩虹速度", 2000.0, 500.0, 5000.0, 100.0, () -> colorMode.is(ColorMode.Rainbow));
    public static final Value<Double> fadeSpeed = new NumberValue<>("Fade Speed", "渐变速度", 5.0, 1.0, 10.0, 0.5, () -> colorMode.is(ColorMode.Fade));
    public static final Value<Double> astolfoSaturation = new NumberValue<>("Saturation", "饱和度", 0.8, 0.0, 1.0, 0.05, () -> colorMode.is(ColorMode.Astolfo));
    public static final Value<Double> astolfoBrightness = new NumberValue<>("Brightness", "亮度", 1.0, 0.0, 1.0, 0.05, () -> colorMode.is(ColorMode.Astolfo));

    public static Value<Boolean> backgroundBlur = new BoolValue("Background Blur", "背景模糊", true);
    public static Value<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> backgroundBlur.get());
    public static final Value<Boolean> bjdOnly = new BoolValue("BJD Only", "布吉岛筛选", false, () -> false);

    public ClickGui() {
        super("ClickGui", "点击GUI", Category.Client);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null && mc.mouse == null) {
            this.toggle();
            return;
        }
        mc.setScreen(LemonClient.CLICKGUI);
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
        return guiScale.get();
    }

    public static double getFontSize() {
        return fontSize.get();
    }
}
