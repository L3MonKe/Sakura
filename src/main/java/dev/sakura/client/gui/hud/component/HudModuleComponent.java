package dev.sakura.client.gui.hud.component;

import dev.sakura.client.gui.Component;
import dev.sakura.client.gui.IComponent;
import dev.sakura.client.gui.clickgui.component.values.*;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.animations.impl.EaseOutSine;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.*;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class HudModuleComponent implements IComponent {
    private static final int MODULE_HEIGHT = 18;

    private float x, y, width, height = MODULE_HEIGHT;
    private float guiScale = 1.0f;
    private float fontSize = 10.0f;
    private final HudModule hudModule;
    private boolean opened;
    private final EaseInOutQuad openAnimation = new EaseInOutQuad(250, 1);
    private final EaseOutSine toggleAnimation = new EaseOutSine(300, 1);
    private final EaseOutSine hoverAnimation = new EaseOutSine(200, 1);
    private final CopyOnWriteArrayList<Component> settings = new CopyOnWriteArrayList<>();

    public HudModuleComponent(HudModule hudModule) {
        this.hudModule = hudModule;
        openAnimation.setDirection(Direction.BACKWARDS);
        toggleAnimation.setDirection(Direction.BACKWARDS);
        hoverAnimation.setDirection(Direction.BACKWARDS);

        for (Value<?> value : hudModule.getValues()) {
            if (value instanceof BoolValue boolValue) {
                settings.add(new BoolValueComponent(boolValue));
            } else if (value instanceof NumberValue<?> numberValue) {
                settings.add(new NumberValueComponent(numberValue));
            } else if (value instanceof EnumValue<?> modeComponent) {
                settings.add(new EnumValueComponent(modeComponent));
            } else if (value instanceof ColorValue colorSetting) {
                settings.add(new ColorValueComponent(colorSetting));
            } else if (value instanceof MultiBoolValue multiBoolValue) {
                settings.add(new MultiBoolValueComponent(multiBoolValue));
            } else if (value instanceof StringValue stringValue) {
                settings.add(new StringValueComponent(stringValue));
            }
        }
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float scaledModuleHeight = MODULE_HEIGHT * guiScale;
        float yOffset = scaledModuleHeight;
        openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        toggleAnimation.setDirection(hudModule.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDirection(isHovered(mouseX, mouseY) ? Direction.FORWARDS : Direction.BACKWARDS);

        boolean hasVisibleSettings = false;
        for (Component component : settings) {
            if (!component.isVisible()) continue;
            hasVisibleSettings = true;
            yOffset += (float) (component.getHeight() * openAnimation.getOutput());
        }

        if (hasVisibleSettings && openAnimation.getOutput() > 0) {
            yOffset += (float) (4 * guiScale * openAnimation.getOutput());
        }

        this.height = yOffset;

        final boolean finalHasVisibleSettings = hasVisibleSettings;
        final float finalYOffset = yOffset;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (hudModule.isEnabled()) {
                NanoVGHelper.drawGradientRRect2(x, y, width, scaledModuleHeight, 0, ClickGui.color(0), ClickGui.color2(0));
            }
            NanoVGHelper.drawRect(x, y, width, scaledModuleHeight, ColorUtil.applyOpacity(ClickGui.backgroundColor.get(), 0.4f));

            if (finalHasVisibleSettings && openAnimation.getOutput() > 0) {
                float expandedHeight = (float) ((finalYOffset - scaledModuleHeight) * openAnimation.getOutput());
                NanoVGHelper.drawRect(x, y + scaledModuleHeight, width, expandedHeight,
                        ColorUtil.applyOpacity(ClickGui.expandedBackgroundColor.get(), (float) (0.3f * openAnimation.getOutput())));
            }

            float textFontSize = fontSize * 0.75f;
            NanoVGHelper.drawString(hudModule.getDisplayName(), x + 4 * guiScale, y + 11 * guiScale, FontLoader.regular(textFontSize), textFontSize, Color.WHITE);
        });

        float componentYOffset = scaledModuleHeight;
        for (Component component : settings) {
            if (!component.isVisible()) continue;
            component.setX(x + 4 * guiScale);
            component.setY((float) (y + 10 * guiScale + componentYOffset * openAnimation.getOutput()));
            component.setWidth(width - 8 * guiScale);
            if (openAnimation.getOutput() > .7f) {
                component.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
            componentYOffset += component.getHeight();
        }

        IComponent.super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovered((int) mouseX, (int) mouseY)) {
            switch (mouseButton) {
                case 0 -> hudModule.toggle();
                case 1 -> opened = !opened;
            }
        }
        if (opened && !isHovered((int) mouseX, (int) mouseY)) {
            settings.forEach(setting -> setting.mouseClicked(mouseX, mouseY, mouseButton));
        }
        return IComponent.super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        if (opened && !isHovered((int) mouseX, (int) mouseY)) {
            settings.forEach(setting -> setting.mouseReleased(mouseX, mouseY, state));
        }
        return IComponent.super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (opened) {
            settings.forEach(setting -> setting.keyPressed(keyCode, scanCode, modifiers));
        }
        return IComponent.super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (opened) {
            for (Component setting : settings) {
                if (setting.charTyped(chr, modifiers)) {
                    return true;
                }
            }
        }
        return IComponent.super.charTyped(chr, modifiers);
    }

    public boolean isHovered(int mouseX, int mouseY) {
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

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public HudModule getHudModule() {
        return hudModule;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setGuiScale(float guiScale) {
        this.guiScale = guiScale;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
}
