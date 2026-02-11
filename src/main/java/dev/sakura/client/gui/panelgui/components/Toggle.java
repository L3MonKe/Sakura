package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.Colors;
import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.function.Consumer;

public class Toggle extends Component {
    private boolean value;
    private final SmoothAnimationTimer valueAnimation = new SmoothAnimationTimer(0.0f);
    private final SmoothAnimationTimer hoverAnimation = new SmoothAnimationTimer(0.0f);
    private final SmoothAnimationTimer popAnimation = new SmoothAnimationTimer(0.0f);
    private Consumer<Boolean> onValueChanged;

    private final float trackWidth = 22.0f;
    private final float trackHeight = 11.0f;
    private final float thumbSize = 9.0f;
    private final float cornerRadius = 5.5f;

    public Toggle(float x, float y) {
        super(x, y, 26.0f, 13.0f);
    }

    public Toggle(float x, float y, boolean initialValue) {
        this(x, y);
        setValue(initialValue, false);
        valueAnimation.value = initialValue ? 255.0f : 0.0f;
        valueAnimation.target = initialValue ? 255.0f : 0.0f;
    }

    public Toggle(float x, float y, boolean initialValue, Consumer<Boolean> onValueChanged) {
        this(x, y, initialValue);
        this.onValueChanged = onValueChanged;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        boolean hovered = isHovered(mouseX, mouseY);
        hoverAnimation.target = hovered ? 255.0f : 0.0f;
        hoverAnimation.update(true);
        valueAnimation.update(true);
        popAnimation.update(true);

        float valueProgress = valueAnimation.value / 255.0f;
        float hoverProgress = hoverAnimation.value / 255.0f;
        float popProgress = popAnimation.value / 255.0f;

        float thumbX = x + 2.0f + valueProgress * (trackWidth - thumbSize - 4.0f);
        float thumbY = y + (trackHeight - thumbSize) / 2.0f;

        float popScale = 1.0f + popProgress * 0.15f;
        float popOffset = (thumbSize * popScale - thumbSize) / 2.0f;

        Color trackOff = Colors.withAlpha(GlassmorphismColors.BACKGROUND, 0.65f);
        Color trackOn = Colors.withAlpha(GlassmorphismColors.TOGGLE_TRACK_ON_BLUE, 0.95f);
        Color track = Colors.blendColors(trackOff, trackOn, valueProgress);

        NanoVGHelper.drawRoundRect(x, y, trackWidth, trackHeight, cornerRadius, track);
        if (valueProgress > 0.01f) {
            Color glow = Colors.withAlpha(GlassmorphismColors.TOGGLE_TRACK_ON_BLUE, 0.25f * valueProgress);
            NanoVGHelper.drawRoundRect(x - 1.0f, y - 1.0f, trackWidth + 2.0f, trackHeight + 2.0f, cornerRadius + 1.0f, glow);
        }
        if (hoverProgress > 0.01f) {
            Color hover = Colors.withAlpha(GlassmorphismColors.TEXT_PRIMARY, hoverProgress * 0.1f);
            NanoVGHelper.drawRoundRect(x, y, trackWidth, trackHeight, cornerRadius, hover);
        }

        float thumbDrawX = thumbX - popOffset;
        float thumbDrawY = thumbY - popOffset;
        float thumbDrawSize = thumbSize * popScale;

        NanoVGHelper.drawRoundRect(thumbDrawX + 0.5f, thumbDrawY + 1.0f, thumbDrawSize, thumbDrawSize, thumbDrawSize / 2.0f, new Color(0, 0, 0, 50));
        NanoVGHelper.drawRoundRect(thumbDrawX, thumbDrawY, thumbDrawSize, thumbDrawSize, thumbDrawSize / 2.0f, new Color(255, 254, 253, 255));
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            setValue(!value, true);
            return true;
        }
        return false;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean newValue) {
        setValue(newValue, true);
    }

    private void setValue(boolean newValue, boolean fireCallback) {
        if (value == newValue) return;
        value = newValue;
        valueAnimation.target = newValue ? 255.0f : 0.0f;
        popAnimation.value = 0.0f;
        popAnimation.target = 255.0f;
        if (fireCallback && onValueChanged != null) {
            onValueChanged.accept(newValue);
        }
    }

    public void setOnValueChanged(Consumer<Boolean> onValueChanged) {
        this.onValueChanged = onValueChanged;
    }
}

