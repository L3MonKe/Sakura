package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.Colors;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingItem extends Component {
    private static final float TOOLTIP_PADDING = 4.0f;
    private static final float TOOLTIP_OFFSET_X = 10.0f;
    private static final float TOOLTIP_OFFSET_Y = 6.0f;
    private static final List<SettingItem> TOOLTIP_QUEUE = new ArrayList<>();

    private String label;
    private Component control;
    private float itemHeight = 20.0f;
    private float labelWidth = 118.0f;
    private float controlWidth = 160.0f;
    private final float labelSpacing = 3.0f;
    private final float rightPadding = 4.0f;

    private int tooltipMouseX;
    private int tooltipMouseY;
    private String cachedLabelInput = "";
    private String cachedDisplayLabel = "";
    private float cachedMaxLabelArea = -1.0f;
    private boolean cachedLabelOverflow = false;

    public SettingItem(float x, float y, float width, String label, Component control) {
        super(x, y, width, 24.0f);
        this.label = label;
        this.control = control;
        invalidateLabelCache();
    }

    public static void renderQueuedTooltips(DrawContext context) {
        if (TOOLTIP_QUEUE.isEmpty()) return;
        List<SettingItem> pending = new ArrayList<>(TOOLTIP_QUEUE);
        TOOLTIP_QUEUE.clear();
        for (SettingItem item : pending) {
            item.renderLabelTooltip();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;
        this.height = itemHeight;

        float labelArea = Math.min(labelWidth, width * 0.42f);
        float maxLabelArea = Math.max(labelArea - 4.0f, 0.0f);

        if (control != null) {
            float minWidth;
            if (control instanceof Slider) {
                minWidth = 90.0f;
            } else if (control instanceof Dropdown) {
                minWidth = 54.0f;
            } else {
                minWidth = 60.0f;
            }

            float usableWidth = Math.max(width - (labelArea + labelSpacing) - rightPadding, 56.0f);
            float effectiveControlWidth = Math.min(controlWidth, usableWidth);
            if (effectiveControlWidth < minWidth) {
                effectiveControlWidth = Math.min(Math.max(minWidth, 0.0f), usableWidth);
            }

            boolean shouldStretch = !(control instanceof Toggle);
            float intrinsicWidth = control.getWidth() > 0.0f ? control.getWidth() : minWidth;
            float controlWidthToUse = shouldStretch ? effectiveControlWidth : intrinsicWidth;
            float controlX = x + width - rightPadding - controlWidthToUse;
            float controlY = y + (itemHeight - control.getHeight()) / 2.0f;

            control.setX(controlX);
            control.setY(controlY);
            if (shouldStretch) {
                control.setWidth(controlWidthToUse);
            }
        }

        float labelX = x + 3.0f;
        float fontSize = 8.0f;
        float labelHeight = NanoVGHelper.getFontHeight(FontLoader.regular(), fontSize);
        float labelTop = y + (itemHeight - labelHeight) / 2.0f;
        float labelBaseline = labelTop + labelHeight;
        String displayLabel = resolveLabelDisplay(maxLabelArea);
        if (!displayLabel.isEmpty()) {
            NanoVGHelper.drawString(displayLabel, labelX, labelBaseline, FontLoader.regular(), fontSize, GlassmorphismColors.TEXT_PRIMARY);
        }

        if (control != null) {
            control.render(context, mouseX, mouseY, deltaTicks);
        }

        if (cachedLabelOverflow && isMouseOverLabel(mouseX, mouseY, labelX, labelTop, labelHeight, maxLabelArea)) {
            queueTooltip(mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!enabled || !visible) return false;

        boolean hovered = isHovered(mouseX, mouseY);
        if (hovered && control != null && control.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (control instanceof Dropdown dropdown) {
            if (dropdown.isOpen()) {
                return dropdown.mouseClicked(mouseX, mouseY, button);
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        return control != null && control.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        return control != null && control.mouseDragged(mouseX, mouseY, button, offsetX, offsetY);
    }

    @Override
    public void setWidth(float width) {
        if (this.width == width) return;
        super.setWidth(width);
        invalidateLabelCache();
    }

    public Component getControl() {
        return control;
    }

    public void setLabelWidth(float labelWidth) {
        this.labelWidth = labelWidth;
        invalidateLabelCache();
    }

    public void setControlWidth(float controlWidth) {
        this.controlWidth = controlWidth;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    private boolean isMouseOverLabel(double mouseX, double mouseY, float labelX, float labelTopY, float labelHeight, float maxLabelArea) {
        float areaWidth = Math.max(maxLabelArea, 0.0f) + 4.0f;
        return mouseX >= labelX && mouseX <= labelX + areaWidth && mouseY >= labelTopY && mouseY <= labelTopY + labelHeight;
    }

    private void queueTooltip(int mouseX, int mouseY) {
        tooltipMouseX = mouseX;
        tooltipMouseY = mouseY;
        if (!TOOLTIP_QUEUE.contains(this)) {
            TOOLTIP_QUEUE.add(this);
        }
    }

    private void invalidateLabelCache() {
        cachedLabelInput = label != null ? label : "";
        cachedDisplayLabel = cachedLabelInput;
        cachedMaxLabelArea = -1.0f;
        cachedLabelOverflow = false;
    }

    private String resolveLabelDisplay(float maxLabelArea) {
        if (label == null || label.isEmpty()) {
            cachedLabelOverflow = false;
            return "";
        }

        if (maxLabelArea <= 0.0f) {
            cachedLabelOverflow = false;
            cachedDisplayLabel = label;
            cachedMaxLabelArea = maxLabelArea;
            cachedLabelInput = label;
            return cachedDisplayLabel;
        }

        boolean cacheHit = label.equals(cachedLabelInput) && Math.abs(maxLabelArea - cachedMaxLabelArea) < 0.5f;
        if (cacheHit) {
            return cachedDisplayLabel;
        }

        cachedLabelInput = label;
        cachedMaxLabelArea = maxLabelArea;
        cachedDisplayLabel = label;
        float fontSize = 8.0f;
        cachedLabelOverflow = NanoVGHelper.getTextWidth(label, FontLoader.regular(), fontSize) > maxLabelArea;

        if (cachedLabelOverflow) {
            String truncated = label;
            while (!truncated.isEmpty() && NanoVGHelper.getTextWidth(truncated + "...", FontLoader.regular(), fontSize) > maxLabelArea) {
                truncated = truncated.substring(0, truncated.length() - 1);
            }
            cachedDisplayLabel = truncated + "...";
        }

        return cachedDisplayLabel;
    }

    private void renderLabelTooltip() {
        if (label == null || label.isEmpty()) return;

        float fontSize = 8.0f;
        float textWidth = NanoVGHelper.getTextWidth(label, FontLoader.regular(), fontSize);
        float textHeight = NanoVGHelper.getFontHeight(FontLoader.regular(), fontSize);
        float tooltipWidth = textWidth + TOOLTIP_PADDING * 2.0f;
        float tooltipHeight = textHeight + TOOLTIP_PADDING * 2.0f;

        float tooltipX = tooltipMouseX + TOOLTIP_OFFSET_X;
        float tooltipY = tooltipMouseY - tooltipHeight - TOOLTIP_OFFSET_Y;
        tooltipY = Math.max(tooltipY, 2.0f);

        Color bgColor = Colors.withAlpha(GlassmorphismColors.BACKGROUND, 0.98f);
        NanoVGHelper.drawRoundRect(tooltipX, tooltipY, tooltipWidth, tooltipHeight, 4.0f, bgColor);
        NanoVGHelper.drawString(label, tooltipX + TOOLTIP_PADDING, tooltipY + TOOLTIP_PADDING + textHeight, FontLoader.regular(), fontSize, GlassmorphismColors.TEXT_PRIMARY);
    }
}
