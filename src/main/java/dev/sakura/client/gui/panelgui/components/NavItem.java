package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.Colors;
import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.function.Consumer;

public class NavItem extends Component {
    private String text;
    private String icon;
    private boolean selected;
    private final SmoothAnimationTimer hoverAnimation = new SmoothAnimationTimer(0.0f);
    private final SmoothAnimationTimer selectAnimation = new SmoothAnimationTimer(0.0f);
    private Consumer<NavItem> onClick;

    private final float textSize = 8.32f;
    private final float iconSize = 8.96f;

    public NavItem(float x, float y, float width, float height, String text, String icon, Consumer<NavItem> onClick) {
        super(x, y, width, height);
        this.text = text;
        this.icon = icon;
        this.onClick = onClick;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        boolean hovered = isHovered(mouseX, mouseY);
        hoverAnimation.target = hovered ? 255.0f : 0.0f;
        hoverAnimation.update(true);
        selectAnimation.target = selected ? 255.0f : 0.0f;
        selectAnimation.update(true);

        float hoverProgress = hoverAnimation.value / 255.0f;
        float selectProgress = selectAnimation.value / 255.0f;

        float finalHoverAlpha = hoverProgress * (1.0f - selectProgress * 0.7f);
        float finalSelectAlpha = selectProgress;

        if (finalSelectAlpha > 0.01f) {
            Color selectedBg = Colors.withAlpha(GlassmorphismColors.TEXT_PRIMARY, finalSelectAlpha * 0.08f);
            NanoVGHelper.drawRoundRect(x, y, width, height, 6.0f, selectedBg);
        }
        if (finalHoverAlpha > 0.01f) {
            Color hoverBg = Colors.withAlpha(GlassmorphismColors.TEXT_PRIMARY, finalHoverAlpha * 0.04f);
            NanoVGHelper.drawRoundRect(x, y, width, height, 6.0f, hoverBg);
        }

        Color iconColor = Colors.blendColors(GlassmorphismColors.TEXT_TERTIARY, GlassmorphismColors.TEXT_PRIMARY, selectProgress);
        Color textColor = iconColor;

        float contentX = x + 8.0f;
        if (icon != null && !icon.isEmpty()) {
            float iconH = NanoVGHelper.getFontHeight(FontLoader.icons(), iconSize);
            float iconTop = y + height / 2.0f - iconH / 2.0f;
            NanoVGHelper.drawString(icon, contentX, iconTop + iconH, FontLoader.icons(), iconSize, iconColor);
            contentX += 20.0f;
        }

        if (text != null && !text.isEmpty()) {
            float textH = NanoVGHelper.getFontHeight(FontLoader.regular(), textSize);
            float textTop = y + height / 2.0f - textH / 2.0f;
            NanoVGHelper.drawString(text, contentX, textTop + textH, FontLoader.regular(), textSize, textColor);
        }
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && onClick != null) {
            onClick.accept(this);
            return true;
        }
        return false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
