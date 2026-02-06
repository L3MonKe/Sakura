package dev.mahiro.client.gui.clickgui;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.gui.clickgui.panel.CategoryPanel;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.EaseOutSine;
import dev.mahiro.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.mahiro.client.Mahiro.mc;

public class ClickGuiScreen extends Screen {
    public static Animation openingAnimation = new EaseOutSine(400, 1);
    private final List<CategoryPanel> panels = new ArrayList<>();
    public int scroll;
    private DrawContext currentContext;

    public ClickGuiScreen() {
        super(Text.literal("ClickGui"));
        openingAnimation.setDirection(Direction.BACKWARDS);
        float width = 0;
        for (Category category : Category.values()) {
            CategoryPanel panel = new CategoryPanel(category);
            panel.setX(50 + width);
            panel.setY(20);
            panels.add(panel);
            width += panel.getWidth() + 10;
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public void init() {
        openingAnimation.setDirection(Direction.FORWARDS);
        openingAnimation.reset();
        for (CategoryPanel panel : panels) {
            panel.setOpened(true);
            panel.getOpenAnimation().setDirection(Direction.BACKWARDS);
            panel.getOpenAnimation().timerUtil.setTime(0);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.currentContext = context;
        final float wheel = getDWheel();
        if (wheel != 0) {
            scroll += wheel > 0 ? 15 : -15;
            for (CategoryPanel panel : panels) {
                if (!panel.isDragging()) {
                    panel.setY(panel.getY() + (wheel > 0 ? 15 : -15));
                }
            }
        }

        if (ClickGui.backgroundBlur.get()) {
            float blurStrength = ClickGui.blurStrength.get().floatValue();
            Shader2DUtil.drawQuadBlur(
                    0, 0,
                    mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(),
                    blurStrength,
                    1.0f
            );
        }

        NanoVGRenderer.INSTANCE.draw(canvas -> NanoVGHelper.drawRect(0, 0, mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(), new Color(18, 18, 18, 50)));
        panels.forEach(panel -> panel.render(context, mouseX, mouseY, deltaTicks));
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (currentContext != null) {
            boolean handled = false;
            for (CategoryPanel panel : panels) {
                if (panel.mouseClicked(click, doubled)) {
                    handled = true;
                }
            }
            return handled || super.mouseClicked(click, doubled);
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (currentContext != null) {
            boolean handled = false;
            for (CategoryPanel panel : panels) {
                if (panel.mouseReleased(click)) {
                    handled = true;
                }
            }
            return handled || super.mouseReleased(click);
        }
        return super.mouseReleased(click);
    }

    @Override
    public void close() {
        Mahiro.MODULES.getModule(ClickGui.class).setState(false);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        boolean handled = false;
        for (CategoryPanel panel : panels) {
            if (panel.keyPressed(input)) {
                handled = true;
            }
        }
        return handled || super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        boolean handled = false;
        for (CategoryPanel panel : panels) {
            if (panel.charTyped(input)) {
                handled = true;
            }
        }
        return handled || super.charTyped(input);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private float accumulatedScroll = 0;

    private float getDWheel() {
        float scroll = accumulatedScroll;
        accumulatedScroll = 0;
        return scroll;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        accumulatedScroll += (float) scrollY;
        return true;
    }

    public List<CategoryPanel> getPanels() {
        return panels;
    }
}