package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.Locale;
import java.util.function.Consumer;

public class Slider extends Component {
    private final float minValue;
    private final float maxValue;
    private float currentValue;
    private float step = 0.01f;

    private final SmoothAnimationTimer fillAnimation = new SmoothAnimationTimer(0.0f);
    private final SmoothAnimationTimer hoverAnimation = new SmoothAnimationTimer(0.0f);
    private Consumer<Float> onValueChanged;

    private boolean dragging;
    private int displayPrecision = 2;

    private final float trackHeight = 3.0f;
    private final float thumbSize = 8.0f;
    private final float cornerRadius = 1.5f;
    private final float trackHorizontalPadding = 5.0f;

    public Slider(float x, float y, float width, float minValue, float maxValue, float initialValue) {
        super(x, y, width, 18.0f);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = clamp(initialValue);
        float trackWidth = getTrackWidth();
        float progressWidth = getProgress() * trackWidth;
        fillAnimation.value = progressWidth;
        fillAnimation.target = progressWidth;
        updateDisplayPrecision();
    }

    public Slider(float x, float y, float width, float minValue, float maxValue, float initialValue, Consumer<Float> onValueChanged) {
        this(x, y, width, minValue, maxValue, initialValue);
        this.onValueChanged = onValueChanged;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        boolean hovered = isHovered(mouseX, mouseY);
        hoverAnimation.target = hovered ? 255.0f : 0.0f;
        hoverAnimation.update(true);
        fillAnimation.update(true);

        float hoverProgress = hoverAnimation.value / 255.0f;

        float trackWidth = getTrackWidth();
        float trackX = x + trackHorizontalPadding;
        float trackY = y + 6.0f;

        height = Math.max(thumbSize + 6.0f, 18.0f);

        NanoVGHelper.drawRoundRect(trackX, trackY, trackWidth, trackHeight, cornerRadius, GlassmorphismColors.SLIDER_TRACK_BG);

        float fillWidth = Math.max(0.0f, Math.min(trackWidth, fillAnimation.value));
        if (fillWidth > 0.1f) {
            NanoVGHelper.drawRoundRect(trackX, trackY, fillWidth, trackHeight, cornerRadius, GlassmorphismColors.SLIDER_FILL_START);
        }

        float thumbX = trackX + fillWidth - thumbSize / 2.0f;
        float thumbY = trackY + (trackHeight - thumbSize) / 2.0f;

        float thumbScale = 1.0f + (hoverProgress + (dragging ? 0.2f : 0.0f)) * 0.1f;
        float thumbDrawSize = thumbSize * thumbScale;
        float thumbOffset = (thumbDrawSize - thumbSize) / 2.0f;

        NanoVGHelper.drawRoundRect(thumbX - thumbOffset, thumbY - thumbOffset, thumbDrawSize, thumbDrawSize, thumbDrawSize / 2.0f, GlassmorphismColors.SLIDER_THUMB_BG);

        String valueText = formatValue(currentValue);
        float textSize = 9.0f;
        float textWidth = NanoVGHelper.getTextWidth(valueText, FontLoader.regular(), textSize);
        NanoVGHelper.drawString(valueText, x + width - textWidth, trackY + trackHeight + 10.0f, FontLoader.regular(), textSize, new Color(233, 249, 255, 185));
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = true;
            updateValueFromMouse(mouseX);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
            return true;
        }
        return false;
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        if (dragging && button == 0) {
            updateValueFromMouse(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        float trackWidth = getTrackWidth();
        float progressWidth = getProgress() * trackWidth;
        fillAnimation.value = progressWidth;
        fillAnimation.target = progressWidth;
    }

    public void setStep(float step) {
        this.step = step > 0 ? step : 0.01f;
        updateDisplayPrecision();
        setValue(currentValue);
    }

    public float getValue() {
        return currentValue;
    }

    public void setValue(float newValue) {
        float clamped = clamp(newValue);
        float snapped = snapToStep(clamped);
        if (currentValue == snapped) return;
        currentValue = snapped;
        float trackWidth = getTrackWidth();
        fillAnimation.target = getProgress() * trackWidth;
        if (onValueChanged != null) {
            onValueChanged.accept(currentValue);
        }
    }

    public void setOnValueChanged(Consumer<Float> onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    private float getProgress() {
        if (maxValue == minValue) return 0.0f;
        return (currentValue - minValue) / (maxValue - minValue);
    }

    private float getTrackWidth() {
        return Math.max(0.0f, width - trackHorizontalPadding * 2.0f);
    }

    private void updateValueFromMouse(double mouseX) {
        float trackWidth = getTrackWidth();
        if (trackWidth <= 0.0f) return;
        float relativeX = (float) ((mouseX - (x + trackHorizontalPadding)) / trackWidth);
        float progress = Math.max(0.0f, Math.min(1.0f, relativeX));
        float newValue = minValue + progress * (maxValue - minValue);
        setValue(newValue);
    }

    private float clamp(float v) {
        return Math.max(minValue, Math.min(maxValue, v));
    }

    private float snapToStep(float value) {
        if (step <= 0.0f) return clamp(value);
        float offset = value - minValue;
        float steps = Math.round(offset / step);
        return clamp(minValue + steps * step);
    }

    private void updateDisplayPrecision() {
        if (step <= 0) {
            displayPrecision = 2;
            return;
        }
        float normalized = step;
        int precision = 0;
        while (precision < 4 && Math.abs(Math.round(normalized) - normalized) > 0.00001f) {
            normalized *= 10.0f;
            precision++;
        }
        displayPrecision = precision;
    }

    private String formatValue(float value) {
        int precision = Math.max(0, Math.min(4, displayPrecision));
        return String.format(Locale.ROOT, "%." + precision + "f", value);
    }
}
