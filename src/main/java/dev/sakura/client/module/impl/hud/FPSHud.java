package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.math.FrameRateCounter;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class FPSHud extends HudModule {
    public enum RainbowMode {OFF, STATIC_HUE, GRADIENT_HUE, GRADIENT}

    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 3.0, 0.1);
    private final Value<Integer> delay = new NumberValue<>("Delay", "延迟", 10, 0, 40, 1);
    private final Value<Color> colorConfig = new ColorValue("Color", "颜色", new Color(255, 255, 255));
    private final Value<RainbowMode> rainbowModeConfig = new EnumValue<>("Rainbow", "彩虹模式", RainbowMode.OFF);
    private final Value<Color> gradientColorConfig = new ColorValue("Gradient Color", "渐变颜色", Color.WHITE, () -> rainbowModeConfig.get() == RainbowMode.GRADIENT);
    private final Value<Double> rainbowSpeedConfig = new NumberValue<>("Rainbow Speed", "彩虹速度", 5.0, 1.0, 20.0, 0.5, () -> rainbowModeConfig.get() != RainbowMode.OFF);
    private final Value<Double> rainbowSaturationConfig = new NumberValue<>("Rainbow Saturation", "彩虹饱和度", 35.0, 0.0, 100.0, 1.0, this::isRainbowWithSaturation);
    private final Value<Double> rainbowBrightnessConfig = new NumberValue<>("Rainbow Brightness", "彩虹亮度", 100.0, 0.0, 100.0, 1.0, this::isRainbowWithSaturation);
    private final Value<Double> rainbowDifferenceConfig = new NumberValue<>("Rainbow Difference", "彩虹差异", 40.0, 0.1, 100.0, 0.1, () -> rainbowModeConfig.get() != RainbowMode.OFF);

    private int cachedFps;
    private final TimerUtil timer = new TimerUtil();

    public FPSHud() {
        super("FPS", "FPS显示", 10, 40);
    }

    @Override
    protected void onEnable() {
        timer.reset();
        cachedFps = 0;
    }

    @Override
    public void onRenderContent() {
        float s = hudScale.get().floatValue();

        if (timer.delay(delay.get().floatValue())) {
            cachedFps = FrameRateCounter.INSTANCE.getFps();
            timer.reset();
        }

        String text = String.format("FPS %d", cachedFps);
        int font = FontLoader.bold(14);
        float fontSize = 14 * s;

        NanoVGHelper.drawString(text, x + 2 * s, y + fontSize, font, fontSize, new Color(calculateColor()));

        width = NanoVGHelper.getTextWidth(text, font, fontSize) + 4 * s;
        height = NanoVGHelper.getFontHeight(font, fontSize) + 4 * s;
    }

    private boolean isRainbowWithSaturation() {
        RainbowMode mode = rainbowModeConfig.get();
        return mode != RainbowMode.OFF && mode != RainbowMode.GRADIENT;
    }

    private int calculateColor() {
        long offset = Sakura.MODULES.getAllModules().stream()
                .filter(Module::isEnabled)
                .count();

        return switch (rainbowModeConfig.get()) {
            case OFF -> colorConfig.get().getRGB();
            case STATIC_HUE -> rainbow(1L);
            case GRADIENT_HUE -> rainbow(offset);
            case GRADIENT -> calculateGradientColor(offset);
        };
    }

    private int calculateGradientColor(long offset) {
        float speed = Math.max(100.0f - rainbowSpeedConfig.get().floatValue(), 0.1f);
        float difference = Math.max(100.0f - rainbowDifferenceConfig.get().floatValue(), 0.1f);

        double phase = Math.toRadians(offset * difference + System.currentTimeMillis() / speed);
        double factor = Math.abs(Math.sin(phase));

        return ColorUtil.interpolateColorC(
                colorConfig.get(),
                gradientColorConfig.get(),
                (float) MathHelper.clamp(factor, 0.0, 1.0)
        ).getRGB();
    }

    private int rainbow(long offset) {
        float speed = rainbowSpeedConfig.get().floatValue();
        float diff = rainbowDifferenceConfig.get().floatValue();

        float hue = ((System.currentTimeMillis() % (long) (10000 / speed)) / (10000f / speed) + offset * diff / 360f) % 1f;

        return Color.HSBtoRGB(
                hue,
                rainbowSaturationConfig.get().floatValue() / 100.0f,
                rainbowBrightnessConfig.get().floatValue() / 100.0f
        );
    }
}