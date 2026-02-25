package dev.mzc.client.gui.clickgui.component;

import dev.mzc.client.gui.Component;
import dev.mzc.client.gui.IComponent;
import dev.mzc.client.gui.clickgui.component.values.*;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.utils.animations.impl.EaseOutSine;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.render.RenderUtil;
import dev.mzc.client.values.Value;
import dev.mzc.client.values.impl.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleComponent implements IComponent {
    private static final int MODULE_HEIGHT = 18;

    private float x, y, width, height = MODULE_HEIGHT;
    private float scale = 1.0f;
    private final Module module;
    private boolean opened;
    private boolean listening = false;
    private boolean previewEnabled = false;
    private final EaseInOutQuad openAnimation = new EaseInOutQuad(250, 1);
    private final EaseOutSine toggleAnimation = new EaseOutSine(300, 1);
    private final EaseOutSine hoverAnimation = new EaseOutSine(200, 1);
    private final EaseInOutQuad visibilityAnimation = new EaseInOutQuad(250, 1);
    private final CopyOnWriteArrayList<Component> settings = new CopyOnWriteArrayList<>();

    public ModuleComponent(Module module) {
        this.module = module;
        openAnimation.setDirection(Direction.BACKWARDS);
        toggleAnimation.setDirection(Direction.BACKWARDS);
        hoverAnimation.setDirection(Direction.BACKWARDS);
        visibilityAnimation.setDirection(Direction.FORWARDS);
        visibilityAnimation.timerUtil.setTime(0); // Default to visible immediately for normal lists
        refreshSettings();
    }

    public void refreshSettings() {
        settings.clear();
        for (Value<?> value : module.getValues()) {
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
        float baseFontSize = (float) ClickGui.getFontSize();
        float scaledHeight = MODULE_HEIGHT * scale;
        float yOffset = scaledHeight;
        openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        toggleAnimation.setDirection(module.isEnabled() || previewEnabled ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDirection(isHovered(mouseX, mouseY) ? Direction.FORWARDS : Direction.BACKWARDS);

        boolean hasVisibleSettings = false;
        for (Component component : settings) {
            if (!component.isVisible()) continue;
            hasVisibleSettings = true;
            component.setScale(scale);
            yOffset += (float) (component.getHeight() * openAnimation.getOutput());
        }

        if (hasVisibleSettings && openAnimation.getOutput() > 0) {
            yOffset += (float) (3 * scale * openAnimation.getOutput());
        }

        this.height = yOffset;

        final boolean finalHasVisibleSettings = hasVisibleSettings;
        final float finalYOffset = yOffset;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float visibility = visibilityAnimation.getOutput().floatValue();
            if (visibility < 1.0f) {
                NanoVG.nvgSave(vg);
                // Center vertical scaling or just top-down? Top-down is standard for list.
                // Scissor to clip content as it shrinks
                NanoVG.nvgScissor(vg, x, y, width, scaledHeight * visibility);
                NanoVG.nvgGlobalAlpha(vg, visibility);
            }

            if (module.isEnabled() || previewEnabled) {
                NanoVGHelper.drawGradientRRect2(x, y, width, scaledHeight, 0, ClickGui.color(0), ClickGui.color2(0));
            }
            NanoVGHelper.drawRect(x, y, width, scaledHeight, ColorUtil.applyOpacity(ClickGui.backgroundColor.get(), 0.4f));

            if (finalHasVisibleSettings && openAnimation.getOutput() > 0) {
                float expandedHeight = (float) ((finalYOffset - scaledHeight) * openAnimation.getOutput());
                NanoVGHelper.drawRect(x, y + scaledHeight, width, expandedHeight,
                        ColorUtil.applyOpacity(ClickGui.expandedBackgroundColor.get(), (float) (0.3f * openAnimation.getOutput())));
            }

            NanoVGHelper.drawString(module.getDisplayName(), x + 4 * scale, y + 11 * scale, FontLoader.regular(baseFontSize * 0.75f), baseFontSize * 0.75f, Color.WHITE);

            float boxWidth = 18 * scale;
            float boxHeight = 8 * scale;
            float boxX = x + width - boxWidth - 4 * scale;
            float boxY = y + (scaledHeight - boxHeight) / 2;

            int keyCode = module.getKey();
            boolean hasKey = keyCode != 0 && keyCode != GLFW.GLFW_KEY_UNKNOWN;
            boolean isHold = module.getBindMode() == Module.BindMode.Hold;

            Color themeColor = ClickGui.color(0);
            Color bgColor;
            Color borderColor;

            if (listening) {
                bgColor = new Color(255, 100, 100, 180);
                borderColor = new Color(255, 150, 150, 220);
            } else {
                bgColor = ColorUtil.applyOpacity(themeColor, hasKey ? 0.6f : 0.3f);
                borderColor = ColorUtil.applyOpacity(themeColor, hasKey ? 0.9f : 0.5f);
            }

            NanoVGHelper.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 2 * scale, bgColor);
            NanoVGHelper.drawRoundRectOutline(boxX, boxY, boxWidth, boxHeight, 2 * scale, 0.5f * scale, borderColor);

            float fontSize = 5 * scale;
            int font = FontLoader.regular(fontSize);
            String displayText = listening ? "..." : (hasKey ? getKeyName(keyCode) : "");
            float textWidth = NanoVGHelper.getTextWidth(displayText, font, fontSize);
            float textX = boxX + (boxWidth - textWidth) / 2;
            float textY = boxY + boxHeight - 2;
            if (!displayText.isEmpty()) NanoVGHelper.drawString(displayText, textX, textY, font, fontSize, Color.WHITE);

            if (isHold && !listening) {
                float lineWidth = hasKey ? textWidth + scale : 6 * scale;
                float lineX = hasKey ? textX - 0.5f * scale : boxX + (boxWidth - lineWidth) / 2;
                float lineY = boxY + boxHeight - scale;
                NanoVGHelper.drawRect(lineX, lineY, lineWidth, 0.5f * scale, Color.WHITE);
            }

            if (visibility < 1.0f) {
                NanoVG.nvgRestore(vg);
            }
        });

        if (visibilityAnimation.getOutput().floatValue() <= 0.01f) return;

        float componentYOffset = scaledHeight;
        for (Component component : settings) {
            if (!component.isVisible()) continue;
            component.setX(x + 4 * scale);
            component.setY(y + 10 * scale + componentYOffset);
            component.setWidth(width - 8 * scale);
            if (openAnimation.getOutput() > .7f) {
                component.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
            componentYOffset += component.getHeight();
        }

        IComponent.super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (visibilityAnimation.getOutput().floatValue() <= 0.01f) return false;

        if (isBindBoxHovered((int) mouseX, (int) mouseY)) {
            if (mouseButton == 0) {
                listening = !listening;
                return true;
            } else if (mouseButton == 2) {
                module.setBindMode(module.getBindMode() == Module.BindMode.Toggle ? Module.BindMode.Hold : Module.BindMode.Toggle);
                return true;
            } else if (listening) {
                module.setKey(-100 - mouseButton);
                listening = false;
                return true;
            }
        } else if (listening) {
            // Support mouse binding
            module.setKey(-100 - mouseButton);
            listening = false;
            return true;
        }

        if (isHovered((int) mouseX, (int) mouseY) && !isBindBoxHovered((int) mouseX, (int) mouseY)) {
            switch (mouseButton) {
                case 0 -> module.toggle();
                case 1 -> opened = !opened;
            }
            return true;
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
        if (listening) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                module.setKey(0);
            } else if (keyCode != GLFW.GLFW_KEY_UNKNOWN) {
                module.setKey(keyCode);
            }
            listening = false;
            return true;
        }
        if (opened) {
            for (Component component : settings) {
                if (component.keyPressed(keyCode, scanCode, modifiers)) {
                    return true;
                }
            }
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
        return RenderUtil.isHovering(x, y, width, MODULE_HEIGHT * scale, mouseX, mouseY);
    }

    public boolean isBindBoxHovered(int mouseX, int mouseY) {
        float boxWidth = 18 * scale;
        float boxHeight = 8 * scale;
        float boxX = x + width - boxWidth - 4 * scale;
        float boxY = y + (MODULE_HEIGHT * scale - boxHeight) / 2;
        return RenderUtil.isHovering(boxX, boxY, boxWidth, boxHeight, mouseX, mouseY);
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Module getModule() {
        return module;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
        openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        if (!opened) {
            openAnimation.timerUtil.setTime(0);
        }
    }

    public EaseInOutQuad getOpenAnimation() {
        return openAnimation;
    }

    public EaseOutSine getToggleAnimation() {
        return toggleAnimation;
    }

    public EaseOutSine getHoverAnimation() {
        return hoverAnimation;
    }

    public CopyOnWriteArrayList<Component> getSettings() {
        return settings;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setVisible(boolean visible) {
        visibilityAnimation.setDirection(visible ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public boolean shouldRemove() {
        return !visibilityAnimation.getDirection().forwards() && visibilityAnimation.isDone();
    }

    public double getVisibilityOutput() {
        return visibilityAnimation.getOutput();
    }

    public void resetVisibilityAnimation() {
        visibilityAnimation.reset();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPreviewEnabled(boolean previewEnabled) {
        this.previewEnabled = previewEnabled;
    }

    private String getKeyName(int keyCode) {
        if (keyCode < 0) {
            return "M" + (-100 - keyCode);
        }
        try {
            InputUtil.Key key = InputUtil.Type.KEYSYM.createFromCode(keyCode);
            String name = key.getLocalizedText().getString();
            if (name.length() > 6) {
                name = name.substring(0, 5) + ".";
            }
            return name.toUpperCase();
        } catch (Exception e) {
            return "?";
        }
    }
}
