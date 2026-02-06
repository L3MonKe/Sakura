package dev.mahiro.client.gui.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.gui.IComponent;
import dev.mahiro.client.gui.hud.component.HudModuleComponent;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.EaseInOutQuad;
import dev.mahiro.client.utils.render.RenderUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

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

        for (Module module : Mahiro.MODULES.getAllModules()) {
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
            NanoVGHelper.drawString("HUD", x + 4 * guiScale, y + 12f * guiScale, FontLoader.bold(), fontSize, new Color(255, 255, 255, 255));
            float iconSize = 15 * guiScale;
            NanoVGHelper.drawString("H", x + width - NanoVGHelper.getTextWidth("H", FontLoader.icons(), iconSize) - 3 * guiScale, y + 13f * guiScale, FontLoader.icons(), iconSize, new Color(255, 255, 255, 255));
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
    public boolean mouseClicked(Click click, boolean doubled) {
        if (isHovered((int) click.x(), (int) click.y())) {
            switch (click.button()) {
                case 0 -> {
                    dragging = true;
                    dragX = (float) (x - click.x());
                    dragY = (float) (y - click.y());
                }
                case 1 -> opened = !opened;
            }
            return true;
        }

        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.mouseClicked(click, doubled)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            dragging = false;
        }

        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.mouseReleased(click)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.keyPressed(input)) {
                handled = true;
            }
        }
        return handled || IComponent.super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        boolean handled = false;
        for (HudModuleComponent component : hudComponents) {
            if (component.charTyped(input)) {
                handled = true;
            }
        }
        return handled || IComponent.super.charTyped(input);
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
