package dev.sakura.client.gui.mainmenu;

import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;

import java.awt.*;

public class MenuButton {
    public int x, y, width, height;
    public String text;
    public Runnable action;
    public boolean enabled;

    private float hoverProgress = 0f;

    public MenuButton(int x, int y, int width, int height, String text, Runnable action) {
        this(x, y, width, height, text, action, true);
    }

    public MenuButton(int x, int y, int width, int height, String text, Runnable action, boolean enabled) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
        this.enabled = enabled;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (enabled && isHovered((int) mouseX, (int) mouseY) && button == 0) {
            onClick();
            return true;
        }
        return false;
    }

    public void onClick() {
        if (enabled && action != null) {
            action.run();
        }
    }

    public void render(boolean hovered, float alpha, float scale) {
        if (alpha <= 0) return;

        float targetHover = hovered && enabled ? 1.0f : 0.0f;
        hoverProgress += (targetHover - hoverProgress) * 0.2f;

        int baseAlpha = (int) (alpha * 255);

        int bgAlphaVal = enabled ? (int) (75 + hoverProgress * 53) : 50;
        bgAlphaVal = Math.min(255, (int) (bgAlphaVal * alpha));

        Color bgColor = new Color(0, 0, 0, bgAlphaVal);
        NanoVGHelper.drawRoundRectScaled(x, y, width, height, 4, bgColor, scale);

        int borderAlphaVal = enabled ? (int) (38 + hoverProgress * 38) : 25;
        borderAlphaVal = Math.min(255, (int) (borderAlphaVal * alpha));

        Color borderColor = new Color(255, 255, 255, borderAlphaVal);
        NanoVGHelper.drawRoundRectOutlineScaled(x, y, width, height, 4, 1, borderColor, scale);

        float fontSize = Math.max(10f, Math.min(15f, height * 0.7f)) * scale;
        int font = FontLoader.regular((int) fontSize);

        float availableWidth = width - 10 * scale;
        float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize);

        if (textWidth > availableWidth) {
            fontSize = fontSize * (availableWidth / textWidth);
            textWidth = NanoVGHelper.getTextWidth(text, font, fontSize);
        }

        float textX = x + (width - textWidth) / 2f;
        float textY = y + height / 2f + fontSize / 3;

        int textAlpha = enabled ? baseAlpha : (int) (baseAlpha * 0.5f);
        Color textColor = new Color(255, 255, 255, textAlpha);
        NanoVGHelper.drawString(text, textX, textY, font, fontSize, textColor);
    }
}
