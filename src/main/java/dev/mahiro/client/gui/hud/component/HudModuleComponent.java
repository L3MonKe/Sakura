package dev.mahiro.client.gui.hud.component;

import dev.mahiro.client.gui.Component;
import dev.mahiro.client.gui.IComponent;
import dev.mahiro.client.gui.clickgui.component.values.*;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.EaseInOutQuad;
import dev.mahiro.client.utils.animations.impl.EaseOutSine;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.RenderUtil;
import dev.mahiro.client.values.Value;
import dev.mahiro.client.values.impl.*;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

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
            NanoVGHelper.drawString(hudModule.getDisplayName(), x + 4 * guiScale, y + 11 * guiScale, FontLoader.regular(), textFontSize, Color.WHITE);
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
    public boolean mouseClicked(Click click, boolean doubled) {
        if (isHovered((int) click.x(), (int) click.y())) {
            switch (click.button()) {
                case 0 -> hudModule.toggle();
                case 1 -> opened = !opened;
            }
        }
        if (opened && !isHovered((int) click.x(), (int) click.y())) {
            settings.forEach(setting -> setting.mouseClicked(click, doubled));
        }
        return IComponent.super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (opened && !isHovered((int) click.x(), (int) click.y())) {
            settings.forEach(setting -> setting.mouseReleased(click));
        }
        return IComponent.super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (opened) {
            settings.forEach(setting -> setting.keyPressed(input));
        }
        return IComponent.super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (opened) {
            for (Component setting : settings) {
                if (setting.charTyped(input)) {
                    return true;
                }
            }
        }
        return IComponent.super.charTyped(input);
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
