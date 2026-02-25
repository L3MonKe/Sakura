package dev.sakura.client.gui.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.IComponent;
import dev.sakura.client.gui.hud.component.HudModuleComponent;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.render.RenderUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class HudPanel implements IComponent {
    private float x, y, dragX, dragY;
    private float width = 110;
    private float height;
    private boolean dragging, opened;
    private final ObjectArrayList<HudModuleComponent> hudComponents = new ObjectArrayList<>();
    private final EaseInOutQuad openAnimation = new EaseInOutQuad(250, 1);

    public HudPanel() {
        this.opened = true;
        this.openAnimation.setDirection(Direction.BACKWARDS);

        for (Module module : Sakura.MODULES.getAllModules()) {
            if (module instanceof HudModule hudModule) {
                hudComponents.add(new HudModuleComponent(hudModule));
            }
        }
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        update(mouseX, mouseY);
        float guiScale = (float) ClickGui.getGuiScale();
        float fontSize = (float) ClickGui.getFontSize();
        width = 110 * guiScale;
        float headerHeight = 18 * guiScale;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color bgColor = ClickGui.backgroundColor.get();
            NanoVGHelper.drawRoundRectBloom(x, y - 1, width, headerHeight + ((height - headerHeight)), 7 * guiScale, new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 100));
            NanoVGHelper.drawString("HUD", x + 4 * guiScale, y + 12f * guiScale, FontLoader.bold((int) fontSize), fontSize, new Color(255, 255, 255, 255));
            float iconSize = 15 * guiScale;
            NanoVGHelper.drawString("H", x + width - NanoVGHelper.getTextWidth("H", FontLoader.icons((int) iconSize), iconSize) - 3 * guiScale, y + 13f * guiScale, FontLoader.icons((int) iconSize), iconSize, new Color(255, 255, 255, 255));
        });

        float componentOffsetY = headerHeight;

        for (HudModuleComponent component : hudComponents) {
            component.setX(x);
            component.setY(y + componentOffsetY);
            component.setWidth(width);
            component.setGuiScale(guiScale);
            component.setFontSize(fontSize);
            if (openAnimation.getOutput() > 0.7f) {
                component.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
            componentOffsetY += (float) (component.getHeight() * openAnimation.getOutput());
        }

        height = componentOffsetY + 9 * guiScale;

        IComponent.super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovered((int) mouseX, (int) mouseY)) {
            switch (mouseButton) {
                case 0 -> {
                    dragging = true;
                    dragX = (float) (x - mouseX);
                    dragY = (float) (y - mouseY);
                }
                case 1 -> opened = !opened;
            }
            return true;
        }

        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.mouseClicked(mouseX, mouseY, mouseButton)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        if (state == 0) dragging = false;

        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.mouseReleased(mouseX, mouseY, state)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.keyPressed(keyCode, scanCode, modifiers)) {
                handled = true;
            }
        }
        return handled || IComponent.super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.charTyped(chr, modifiers)) {
                handled = true;
            }
        }
        return handled || IComponent.super.charTyped(chr, modifiers);
    }

    public void update(int mouseX, int mouseY) {
        this.openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        if (dragging) {
            x = mouseX + dragX;
            y = mouseY + dragY;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        float guiScale = (float) ClickGui.getGuiScale();
        return RenderUtil.isHovering(x, y, width, 18 * guiScale, mouseX, mouseY);
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

    public float getHeight() {
        return height;
    }

    public boolean isDragging() {
        return dragging;
    }
}
