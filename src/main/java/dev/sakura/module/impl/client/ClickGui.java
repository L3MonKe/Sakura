package dev.sakura.module.impl.client;

import dev.sakura.Sakura;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.utils.color.ColorUtil;
import dev.sakura.utils.render.RenderUtils;
import dev.sakura.values.Value;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.ColorValue;
import dev.sakura.values.impl.EnumValue;
import dev.sakura.values.impl.NumberValue;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class ClickGui extends Module {
    public enum ColorMode {
        Fade, Rainbow, Astolfo, Dynamic, Tenacity, Static, Double
    }

    public static EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", ColorMode.Tenacity);
    public static Value<Color> mainColor = new ColorValue("Main Color", new Color(255, 183, 197), () -> !colorMode.is(ColorMode.Rainbow));
    public static Value<Color> secondColor = new ColorValue("Second Color", new Color(255, 133, 161), () -> colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Double));
    public static final Value<Double> colorSpeed = new NumberValue<>("Color Speed", 4.0, 1.0, 10.0, 0.5, () -> colorMode.is(ColorMode.Tenacity) || colorMode.is(ColorMode.Dynamic));
    public static final Value<Double> colorIndex = new NumberValue<>("Color Separation", 20.0, 1.0, 100.0, 1.0, () -> colorMode.is(ColorMode.Tenacity));
    public static final Value<Double> rainbowSpeed = new NumberValue<>("Rainbow Speed", 2000.0, 500.0, 5000.0, 100.0, () -> colorMode.is(ColorMode.Rainbow));
    public static final Value<Double> fadeSpeed = new NumberValue<>("Fade Speed", 5.0, 1.0, 10.0, 0.5, () -> colorMode.is(ColorMode.Fade));
    public static final Value<Double> astolfoSaturation = new NumberValue<>("Saturation", 0.8, 0.0, 1.0, 0.05, () -> colorMode.is(ColorMode.Astolfo));
    public static final Value<Double> astolfoBrightness = new NumberValue<>("Brightness", 1.0, 0.0, 1.0, 0.05, () -> colorMode.is(ColorMode.Astolfo));
    public static Value<Color> backgroundColor = new ColorValue("Background Color", new Color(28, 28, 28));
    public static Value<Color> expandedBackgroundColor = new ColorValue("Expanded Background", new Color(20, 20, 20));

    public static Value<Boolean> backgroundBlur = new BoolValue("Background Blur", true);
    public static Value<Double> blurStrength = new NumberValue<>("Blur Strength", 8.0, 1.0, 20.0, 0.5, () -> backgroundBlur.get());

    public ClickGui() {
        super("ClickGui", Category.Client);
        setKey(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null && mc.mouse == null) {
            this.toggle();
            return;
        }
        mc.setScreen(Sakura.CLICKGUI);
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
            case Fade ->
                    ColorUtil.fade(fadeSpeed.getValue().intValue(), tick * 20, new Color(mainColor.get().getRGB()), 1);
            case Static -> mainColor.get();
            case Astolfo ->
                    new Color(ColorUtil.swapAlpha(astolfoRainbow(tick, astolfoSaturation.getValue().floatValue(), astolfoBrightness.getValue().floatValue()), 255));
            case Rainbow ->
                    new Color(RenderUtils.getRainbow(System.currentTimeMillis(), rainbowSpeed.getValue().intValue(), tick));
            case Tenacity ->
                    ColorUtil.interpolateColorsBackAndForth(colorSpeed.getValue().intValue(), colorIndex.get().intValue() * tick, mainColor.get(), secondColor.get(), false);
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
}
