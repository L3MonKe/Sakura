package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Card extends Component {
    private final List<Component> children = new ArrayList<>();

    private float cornerRadius = 8.0f;
    private float padding = 10.0f;
    private boolean showBackground = true;
    private Color backgroundColor = new Color(40, 50, 70, 180);

    private float scrollOffset = 0.0f;
    private float maxScrollOffset = 0.0f;

    public Card(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        if (showBackground) {
            NanoVGHelper.drawRoundRect(x, y, width, height, cornerRadius, backgroundColor);
        }

        float contentStartY = y + padding;
        float contentHeight = height - padding * 2.0f;

        float totalContentHeight = 0.0f;
        for (Component child : children) {
            if (child.isVisible()) {
                totalContentHeight += child.getHeight() + 4.0f;
            }
        }

        maxScrollOffset = Math.max(0.0f, totalContentHeight - contentHeight);
        scrollOffset = Math.max(0.0f, Math.min(scrollOffset, maxScrollOffset));

        NanoVGHelper.save();
        NanoVGHelper.scissor(x - 10.0f, y - 2.0f, width + 20.0f, height + 4.0f);

        float childY = contentStartY - scrollOffset;
        for (Component child : children) {
            if (!child.isVisible()) continue;
            float childBottom = childY + child.getHeight();
            if (childBottom >= y && childY <= y + height) {
                child.setX(x + padding);
                child.setY(childY);
                child.setWidth(width - padding * 2.0f);
                child.setClipBounds(y, y + height);
                child.render(context, mouseX, mouseY, deltaTicks);
            }
            childY += child.getHeight() + 4.0f;
        }

        NanoVGHelper.restore();
        NanoVGHelper.resetScissor();
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        for (Component child : children) {
            if (child.isVisible() && child.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        boolean handled = false;
        for (Component child : children) {
            if (child.isVisible() && child.mouseReleased(mouseX, mouseY, button)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        boolean handled = false;
        for (Component child : children) {
            if (child.isVisible() && child.mouseDragged(mouseX, mouseY, button, offsetX, offsetY)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onMouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (maxScrollOffset > 0.01f) {
            float scrollSpeed = 20.0f;
            scrollOffset -= (float) verticalAmount * scrollSpeed;
            scrollOffset = Math.max(0.0f, Math.min(scrollOffset, maxScrollOffset));
            return true;
        }
        for (Component child : children) {
            if (child.isVisible() && child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
                return true;
            }
        }
        return false;
    }

    public void addChild(Component child) {
        if (child != null) {
            children.add(child);
        }
    }

    public void clearChildren() {
        children.clear();
    }

    public void resetScroll() {
        scrollOffset = 0.0f;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
