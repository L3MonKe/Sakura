package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class TotemParticles extends Module {
    public TotemParticles() {
        super("TotemParticles", "图腾粒子", Category.Render);
    }

    public enum ColorMode {
        Static,
        Gradient,
        Rainbow
    }

    private final BoolValue noRender = new BoolValue("No Render", "不渲染", false);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Gradient, () -> !noRender.get());
    private final ColorValue color1 = new ColorValue("Color 1", "颜色1", new Color(255, 100, 100), () -> !noRender.get());
    private final ColorValue color2 = new ColorValue("Color 2", "颜色2", new Color(100, 255, 255), () -> !noRender.get() && !colorMode.is(ColorMode.Static));
    private final NumberValue<Double> rainbowSpeed = new NumberValue<>("Rainbow Speed", "彩虹速度", 5.0, 1.0, 20.0, 0.5, () -> !noRender.get() && colorMode.is(ColorMode.Rainbow));

    private int particleIndex = 0;

    public boolean isNoRender() {
        return noRender.get();
    }

    public void resetIndex() {
        particleIndex = 0;
    }

    public Color getNextColor() {
        particleIndex++;
        return calculateColor(particleIndex);
    }

    private Color calculateColor(int index) {
        switch (colorMode.get()) {
            case Static -> {
                return color1.get();
            }
            case Gradient -> {
                float progress = (index % 100) / 100f;
                float r = color1.get().getRed() + (color2.get().getRed() - color1.get().getRed()) * progress;
                float g = color1.get().getGreen() + (color2.get().getGreen() - color1.get().getGreen()) * progress;
                float b = color1.get().getBlue() + (color2.get().getBlue() - color1.get().getBlue()) * progress;
                return new Color(
                        Math.max(0, Math.min(255, (int) r)),
                        Math.max(0, Math.min(255, (int) g)),
                        Math.max(0, Math.min(255, (int) b))
                );
            }
            case Rainbow -> {
                float hue = (float) ((System.currentTimeMillis() / 1000.0 * rainbowSpeed.get() * 0.1 + index * 0.02) % 1.0);
                return Color.getHSBColor(hue, 0.8f, 1.0f);
            }
        }
        return color1.get();
    }
}