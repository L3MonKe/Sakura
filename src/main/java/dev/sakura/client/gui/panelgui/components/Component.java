package dev.sakura.client.gui.panelgui.components;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

public abstract class Component {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected float clipTop = Float.NEGATIVE_INFINITY;
    protected float clipBottom = Float.POSITIVE_INFINITY;

    protected Component(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float deltaTicks);

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!enabled || !visible || !isHovered(mouseX, mouseY)) {
            return false;
        }
        return onMouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!enabled || !visible) {
            return false;
        }
        return onMouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        if (!enabled || !visible) {
            return false;
        }
        return onMouseDragged(mouseX, mouseY, button, offsetX, offsetY);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!enabled || !visible || !isHovered(mouseX, mouseY)) {
            return false;
        }
        return onMouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public boolean keyPressed(KeyInput input) {
        if (!enabled || !visible) {
            return false;
        }
        return onKeyPressed(input);
    }

    public boolean charTyped(CharInput input) {
        if (!enabled || !visible) {
            return false;
        }
        return onCharTyped(input);
    }

    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        return false;
    }

    protected boolean onMouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return false;
    }

    protected boolean onKeyPressed(KeyInput input) {
        return false;
    }

    protected boolean onCharTyped(CharInput input) {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void setClipBounds(float clipTop, float clipBottom) {
        this.clipTop = clipTop;
        this.clipBottom = clipBottom;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
