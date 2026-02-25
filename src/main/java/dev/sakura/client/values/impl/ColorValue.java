package dev.sakura.client.values.impl;

import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.values.Value;

import java.awt.*;

public class ColorValue extends Value<Color> {
    private float hue = 0;
    private float saturation = 1;
    private float brightness = 1;
    private float alpha = 1;
    private boolean allowAlpha = false;
    private boolean rainbow = false;
    public boolean expand;
    private final Color defaultColor;

    public ColorValue(String name, String chineseName, Color defaultValue, boolean allowAlpha, Dependency dependency) {
        super(name, chineseName, dependency);
        this.allowAlpha = allowAlpha;
        this.defaultColor = defaultValue;
        set(defaultValue);
    }

    public ColorValue(String name, String chineseName, Color defaultValue, Dependency dependency) {
        this(name, chineseName, defaultValue, true, dependency);
    }

    public ColorValue(String name, String chineseName, Color defaultValue) {
        this(name, chineseName, defaultValue, true, () -> true);
    }

    public Color get() {
        return ColorUtil.applyOpacity(Color.getHSBColor(hue, saturation, brightness), alpha);
    }

    public void set(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
        alpha = color.getAlpha() / 255.0f;
    }

    public boolean allowAlpha() {
        return allowAlpha;
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @Override
    public void reset() {
        if (defaultColor != null) {
            set(defaultColor);
            rainbow = false;
        }
    }
}
