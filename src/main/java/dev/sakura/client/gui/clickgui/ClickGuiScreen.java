package dev.sakura.client.gui.clickgui;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.clickgui.panel.CategoryPanel;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseOutSine;
import dev.sakura.client.utils.animations.impl.SmoothStepAnimation;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class ClickGuiScreen extends Screen {
    public static Animation openingAnimation = new EaseOutSine(400, 1);
    private final List<CategoryPanel> panels = new ArrayList<>();
    public int scroll;
    private DrawContext currentContext;
    private float bjdBoxX, bjdBoxY, bjdBoxW, bjdBoxH;
    private float bjdToggleX, bjdToggleY, bjdToggleW, bjdToggleH;
    private final SmoothStepAnimation bjdToggleAnimation = new SmoothStepAnimation(175, 1);

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
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.currentContext = guiGraphics;
        final float wheel = getDWheel();
        if (wheel != 0) {
            scroll += wheel > 0 ? 15 : -15;
            for (CategoryPanel panel : panels) {
                if (!panel.isDragging()) {
                    panel.setY(panel.getY() + (wheel > 0 ? 15 : -15));
                }
            }
        }

        float guiScale = (float) ClickGui.getGuiScale();
        float baseFontSize = (float) ClickGui.getFontSize();
        float sw = mc.getWindow().getScaledWidth();
        float sh = mc.getWindow().getScaledHeight();
        float margin = 10 * guiScale;
        float padding = 6 * guiScale;

        bjdBoxH = 24 * guiScale;
        bjdBoxW = 165 * guiScale;
        bjdBoxX = sw - bjdBoxW - margin;
        bjdBoxY = sh - bjdBoxH - margin;

        bjdToggleW = 22 * guiScale;
        bjdToggleH = 10 * guiScale;
        bjdToggleX = bjdBoxX + bjdBoxW - padding - bjdToggleW;
        bjdToggleY = bjdBoxY + (bjdBoxH - bjdToggleH) / 2f;

        // 应用背景模糊在NanoVG绘制之外
        if (ClickGui.backgroundBlur.get()) {
            float blurStrength = ClickGui.blurStrength.get().floatValue();
            Shader2DUtil.drawQuadBlur(
                    guiGraphics.getMatrices(),
                    0, 0,
                    mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(),
                    blurStrength,
                    1.0f
            );
        }

        NanoVGRenderer.INSTANCE.draw(canvas -> NanoVGHelper.drawRect(0, 0, mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(), new Color(18, 18, 18, 50)));

        panels.forEach(panel -> panel.render(guiGraphics, mouseX, mouseY, partialTicks));

        bjdToggleAnimation.setDirection(ClickGui.bjdOnly.get() ? Direction.FORWARDS : Direction.BACKWARDS);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color bg = ClickGui.backgroundColor.get();
            Color boxBg = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 110);
            Color outline = new Color(255, 255, 255, 160);
            float radius = 6 * guiScale;
            float stroke = 0.8f * guiScale;

            NanoVGHelper.drawRoundRect(bjdBoxX, bjdBoxY, bjdBoxW, bjdBoxH, radius, boxBg);
            NanoVGHelper.drawRoundRectOutline(bjdBoxX, bjdBoxY, bjdBoxW, bjdBoxH, radius, stroke, outline);

            float textSize = baseFontSize * 0.75f;
            NanoVGHelper.drawString(ClickGui.language.is(ClickGui.Language.Chinese) ? "显示布吉岛模块" : "Show 78 IsLand", bjdBoxX + padding, bjdBoxY + bjdBoxH / 2f, FontLoader.regular(textSize), textSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);

            float toggleRadius = 5 * guiScale;
            float circleRadius = 4 * guiScale;
            float circleStartX = bjdToggleX + 5 * guiScale;
            float circleTravel = bjdToggleW - 10 * guiScale;
            NanoVGHelper.drawRoundRect(
                    bjdToggleX,
                    bjdToggleY,
                    bjdToggleW,
                    bjdToggleH,
                    toggleRadius,
                    ClickGui.bjdOnly.get() ? ClickGui.color(0).darker() : new Color(70, 70, 70)
            );
            NanoVGHelper.drawCircle(
                    circleStartX + (circleTravel * bjdToggleAnimation.getOutput().floatValue()),
                    bjdToggleY + bjdToggleH / 2f,
                    circleRadius,
                    ClickGui.bjdOnly.get() ? Color.WHITE : new Color(150, 150, 150)
            );
        });
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (currentContext != null) {
            if (mouseButton == 0 && RenderUtil.isHovering(bjdToggleX, bjdToggleY, bjdToggleW, bjdToggleH, mouseX, mouseY)) {
                ClickGui.bjdOnly.set(!ClickGui.bjdOnly.get());
                return true;
            }

            int finalMouseY = (int) mouseY;
            boolean handled = false;
            for (CategoryPanel panel : panels) {
                if (panel.mouseClicked(mouseX, finalMouseY, mouseButton)) {
                    handled = true;
                }
            }
            return handled || super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        if (currentContext != null) {
            int finalMouseY = (int) mouseY;
            boolean handled = false;
            for (CategoryPanel panel : panels) {
                if (panel.mouseReleased(mouseX, finalMouseY, state)) {
                    handled = true;
                }
            }

            return handled || super.mouseReleased(mouseX, mouseY, state);
        }

        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void close() {
        Sakura.MODULES.getModule(ClickGui.class).setState(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean handled = false;
        for (CategoryPanel panel : panels) {
            if (panel.keyPressed(keyCode, scanCode, modifiers)) {
                handled = true;
            }
        }
        return handled || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        boolean handled = false;
        for (CategoryPanel panel : panels) {
            if (panel.charTyped(chr, modifiers)) {
                handled = true;
            }
        }
        return handled || super.charTyped(chr, modifiers);
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
