package dev.sakura.client.gui.mainmenu;

import dev.sakura.client.manager.Managers;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.ScreenWhiteTransition;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static dev.sakura.client.Sakura.mc;

public class NewMainMenuScreen extends Screen {
    private static final String BG_PATH = "/assets/sakura/textures/mainmenu/background.png";
    private static final String LOGO_PATH = "/assets/sakura/textures/mainmenu/logo.png";
    private static final float DESIGN_W = 1920f;
    private static final float DESIGN_H = 1080f;
    private static final int FIRST_ROW_COUNT = 4;
    private static final long EXIT_WARNING_ANIM_MS = 240L;
    private static boolean playReturnCancelNext;
    private final List<MenuEntry> entries = new ArrayList<>();
    private final List<AdjustButton> adjustButtons = new ArrayList<>();
    private int backgroundTexture = -1;
    private int logoTexture = -1;
    private boolean editorOpen = false;
    private float logoCenterXRatio = 0.765f;
    private float logoCenterYRatio = 0.650f;
    private float logoSizeScale = 0.850f;
    private float row1XRatio = 0.550f;
    private float row1YRatio = 0.825f;
    private float row1FontScale = 1.400f;
    private float row1GapScale = 1.000f;
    private float row2XRatio = 0.910f;
    private float row2YRatio = 0.890f;
    private float row2FontScale = 1.400f;
    private float row2GapScale = 1.000f;
    private float copyrightXRatio = 0.750f;
    private float copyrightYRatio = 0.950f;
    private float copyrightSizeScale = 1.250f;
    private float charSpacingScale = 5.000f;
    private float textGlowIntensity = 10.000f;
    private float whiteFadeXRatio = 0.880f;
    private float whiteFadeYRatio = 1.140f;
    private float whiteFadeRadiusScale = 1.200f;
    private float whiteFadeAlpha = 3.000f;
    private float panelX;
    private float panelY;
    private float panelW;
    private float panelH;
    private float exitWarningProgress;
    private float exitWarningAnimFrom;
    private float exitWarningAnimTo;
    private long exitWarningAnimStartMs;
    private boolean exitWarningCloseToQuit;
    private float exitConfirmX;
    private float exitConfirmY;
    private float exitConfirmW;
    private float exitConfirmH;
    private float exitCancelX;
    private float exitCancelY;
    private float exitCancelW;
    private float exitCancelH;

    public NewMainMenuScreen() {
        super(Text.of("NewMainMenuScreen"));
    }

    public static void requestReturnCancelOnce() {
        playReturnCancelNext = true;
    }

    @Override
    protected void init() {
        if (backgroundTexture == -1) {
            backgroundTexture = NanoVGHelper.loadTexture(BG_PATH);
        }
        if (logoTexture == -1) {
            logoTexture = NanoVGHelper.loadTexture(LOGO_PATH);
        }
        if (ScreenWhiteTransition.consumeNewMenuFadeRequest()) {
            ScreenWhiteTransition.startFromWhite(800L);
        }
        if (playReturnCancelNext) {
            playReturnCancelNext = false;
            mc.getSoundManager().stopSounds(Identifier.of("minecraft", "ui.button.click"), null);
            Managers.SOUND.playSound(Managers.SOUND.MENU_CANCEL, 1.0f, 1.0f);
        }
        entries.clear();
        entries.add(new MenuEntry("Singleplayer", () -> {
            Managers.SOUND.playSound(Managers.SOUND.MENU_CONFIRM, 1.0f, 1.0f);
            ScreenWhiteTransition.markFadeOnNextNewMenuOpen();
            ScreenWhiteTransition.startToScreen(new SelectWorldScreen(this), 800L);
        }));
        entries.add(new MenuEntry("Multiplayer", () -> {
            Managers.SOUND.playSound(Managers.SOUND.MENU_CONFIRM, 1.0f, 1.0f);
            ScreenWhiteTransition.markFadeOnNextNewMenuOpen();
            ScreenWhiteTransition.startToScreen(new MultiplayerScreen(this), 800L);
        }));
        entries.add(new MenuEntry("Start", () -> Managers.SOUND.playSound(Managers.SOUND.START_JI, 1.0f, 1.0f)));
        entries.add(new MenuEntry("Settings", () -> {
            Managers.SOUND.playSound(Managers.SOUND.MENU_CONFIRM, 1.0f, 1.0f);
            ScreenWhiteTransition.markFadeOnNextNewMenuOpen();
            ScreenWhiteTransition.startToScreen(new OptionsScreen(this, mc.options), 800L);
        }));
        entries.add(new MenuEntry("Exit", () -> {
            Managers.SOUND.playSound(Managers.SOUND.MENU_CONFIRM, 1.0f, 1.0f);
            openExitWarning();
        }));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateExitWarningAnimation();
        NanoVGRenderer.INSTANCE.draw(vg -> {
            float scale = Math.min(width / DESIGN_W, height / DESIGN_H);
            if (scale <= 0f) {
                scale = 1f;
            }

            if (backgroundTexture != -1) {
                float cover = Math.max(width / DESIGN_W, height / DESIGN_H);
                float drawW = DESIGN_W * cover;
                float drawH = DESIGN_H * cover;
                float drawX = (width - drawW) * 0.5f;
                float drawY = (height - drawH) * 0.5f;
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    NVGPaint paint = NVGPaint.malloc(stack);
                    NanoVG.nvgImagePattern(vg, drawX, drawY, drawW, drawH, 0f, backgroundTexture, 1f, paint);
                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgRect(vg, 0, 0, width, height);
                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgFill(vg);
                }
            } else {
                NanoVGHelper.drawRect(0, 0, width, height, new Color(20, 20, 20, 255));
            }

            NanoVGHelper.drawRect(0, 0, width, height, new Color(0, 0, 0, 24));

            drawWhiteFadeBackground(vg, scale);

            float logoW = Math.min(width * 0.42f, 860f * scale) * logoSizeScale;
            float logoH = logoW * (820f / 1500f);
            float logoX = width * logoCenterXRatio - logoW * 0.5f;
            float logoY = height * logoCenterYRatio - logoH * 0.5f;
            if (logoTexture != -1) {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    NVGPaint paint = NVGPaint.malloc(stack);
                    NanoVG.nvgImagePattern(vg, logoX, logoY, logoW, logoH, 0f, logoTexture, 1f, paint);
                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgRoundedRect(vg, logoX, logoY, logoW, logoH, 4f);
                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgFill(vg);
                }
            }

            float row1Y = height * row1YRatio;
            float row1StartX = width * row1XRatio;
            float row1FontSize = 52f * scale * 0.5f * row1FontScale;
            float row1Gap = 30f * scale * row1GapScale;
            float charSpacing = 1.0f * scale * charSpacingScale;
            float cursorX = row1StartX;
            int firstRowEnd = Math.min(FIRST_ROW_COUNT, entries.size());

            for (int i = 0; i < firstRowEnd; i++) {
                MenuEntry entry = entries.get(i);
                NanoVG.nvgFontFaceId(vg, FontLoader.monaBold());
                NanoVG.nvgFontSize(vg, row1FontSize);
                NanoVG.nvgTextLetterSpacing(vg, charSpacing);
                float[] bounds = new float[4];
                float textW = NanoVG.nvgTextBounds(vg, 0, 0, entry.text, bounds);
                NanoVG.nvgTextLetterSpacing(vg, 0);

                float hitX = cursorX - 6f * scale;
                float hitY = row1Y - 4f * scale;
                float hitW = textW + 12f * scale;
                float hitH = row1FontSize + 8f * scale;
                entry.setBounds(hitX, hitY, hitW, hitH);

                boolean hovered = mouseX >= hitX && mouseX <= hitX + hitW && mouseY >= hitY && mouseY <= hitY + hitH;
                drawMenuButton(vg, entry.text, cursorX, row1Y, row1FontSize, charSpacing, hovered, scale);
                cursorX += textW + row1Gap;
            }

            float row2Y = height * row2YRatio;
            float row2StartX = width * row2XRatio;
            float row2FontSize = 60f * scale * 0.5f * row2FontScale;
            float row2Gap = 26f * scale * row2GapScale;
            cursorX = row2StartX;
            for (int i = firstRowEnd; i < entries.size(); i++) {
                MenuEntry entry = entries.get(i);
                NanoVG.nvgFontFaceId(vg, FontLoader.monaBold());
                NanoVG.nvgFontSize(vg, row2FontSize);
                NanoVG.nvgTextLetterSpacing(vg, charSpacing);
                float[] bounds = new float[4];
                float textW = NanoVG.nvgTextBounds(vg, 0, 0, entry.text, bounds);
                NanoVG.nvgTextLetterSpacing(vg, 0);

                float hitX = cursorX - 6f * scale;
                float hitY = row2Y - 4f * scale;
                float hitW = textW + 12f * scale;
                float hitH = row2FontSize + 8f * scale;
                entry.setBounds(hitX, hitY, hitW, hitH);
                boolean hovered = mouseX >= hitX && mouseX <= hitX + hitW && mouseY >= hitY && mouseY <= hitY + hitH;
                drawMenuButton(vg, entry.text, cursorX, row2Y, row2FontSize, charSpacing, hovered, scale);
                cursorX += textW + row2Gap;
            }

            float copyrightSize = 26f * scale * 0.5f * copyrightSizeScale;
            NanoVGHelper.drawString(
                    "© MAKURA All Rights Reserved.",
                    width * copyrightXRatio,
                    height * copyrightYRatio,
                    FontLoader.monaBold(),
                    copyrightSize,
                    NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                    new Color(64, 64, 64, 220)
            );

            drawEditor(vg, scale);
            drawExitWarning(vg, scale, mouseX, mouseY);
        });
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (ScreenWhiteTransition.isActive()) {
            return true;
        }
        if (exitWarningProgress > 0f) {
            if (click.button() == 0) {
                float mx = (float) click.x();
                float my = (float) click.y();
                if (isHovered(mx, my, exitConfirmX, exitConfirmY, exitConfirmW, exitConfirmH)) {
                    Managers.SOUND.playSound(Managers.SOUND.MENU_CONFIRM, 1.0f, 1.0f);
                    closeExitWarning(true);
                    return true;
                }
                if (isHovered(mx, my, exitCancelX, exitCancelY, exitCancelW, exitCancelH)) {
                    Managers.SOUND.playSound(Managers.SOUND.MENU_CANCEL, 1.0f, 1.0f);
                    closeExitWarning(false);
                    return true;
                }
            }
            return true;
        }
        if (click.button() == 0) {
            float mx = (float) click.x();
            float my = (float) click.y();
            if (editorOpen) {
                for (AdjustButton button : adjustButtons) {
                    if (isHovered(mx, my, button.x, button.y, button.w, button.h)) {
                        applyDelta(button.type, button.delta);
                        return true;
                    }
                }
                if (isHovered(mx, my, panelX, panelY, panelW, panelH)) {
                    return true;
                }
            }
            for (MenuEntry entry : entries) {
                if (entry.isHovered(mx, my)) {
                    entry.action.run();
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public void removed() {
        if (backgroundTexture != -1) {
            NanoVGHelper.deleteTexture(backgroundTexture);
            backgroundTexture = -1;
        }
        if (logoTexture != -1) {
            NanoVGHelper.deleteTexture(logoTexture);
            logoTexture = -1;
        }
        super.removed();
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (ScreenWhiteTransition.isActive()) {
            return true;
        }
        if (exitWarningProgress > 0f) {
            if (input.getKeycode() == GLFW.GLFW_KEY_ESCAPE) {
                Managers.SOUND.playSound(Managers.SOUND.MENU_CANCEL, 1.0f, 1.0f);
                closeExitWarning(false);
            }
            return true;
        }
        if (input.getKeycode() == GLFW.GLFW_KEY_F6) {
            editorOpen = !editorOpen;
            return true;
        }
        return super.keyPressed(input);
    }

    private void openExitWarning() {
        startExitWarningAnimation(1f, false);
    }

    private void closeExitWarning(boolean quit) {
        startExitWarningAnimation(0f, quit);
    }

    private void startExitWarningAnimation(float target, boolean quit) {
        if (exitWarningAnimTo == target && exitWarningProgress == target) {
            return;
        }
        exitWarningAnimFrom = exitWarningProgress;
        exitWarningAnimTo = target;
        exitWarningAnimStartMs = System.currentTimeMillis();
        exitWarningCloseToQuit = target <= 0f && quit;
    }

    private void updateExitWarningAnimation() {
        if (exitWarningAnimFrom == exitWarningAnimTo) {
            return;
        }
        float t = Math.min(1f, (System.currentTimeMillis() - exitWarningAnimStartMs) / (float) EXIT_WARNING_ANIM_MS);
        float eased = t * t * (3f - 2f * t);
        exitWarningProgress = exitWarningAnimFrom + (exitWarningAnimTo - exitWarningAnimFrom) * eased;
        if (t >= 1f) {
            exitWarningProgress = exitWarningAnimTo;
            exitWarningAnimFrom = exitWarningAnimTo;
            if (exitWarningProgress <= 0f && exitWarningCloseToQuit) {
                exitWarningCloseToQuit = false;
                mc.scheduleStop();
            }
        }
    }

    private void drawExitWarning(long vg, float scale, int mouseX, int mouseY) {
        if (exitWarningProgress <= 0f) {
            exitConfirmW = 0f;
            exitCancelW = 0f;
            return;
        }
        float alpha = exitWarningProgress;
        NanoVGHelper.drawRect(0f, 0f, width, height, new Color(0, 0, 0, (int) (90f * alpha)));
        float panelW = width;
        float panelH = 190f * scale;
        float panelX = 0f;
        float panelY = (height - panelH) * 0.5f;
        float centerW = Math.min(width * 0.64f, 980f * scale);
        float centerX = (width - centerW) * 0.5f;
        int centerAlpha = (int) (196f * alpha);
        NanoVGHelper.drawRect(centerX, panelY, centerW, panelH, new Color(255, 255, 255, centerAlpha));
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor transparent = NVGColor.malloc(stack);
            NVGColor solid = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) 0, transparent);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) centerAlpha, solid);
            NanoVG.nvgLinearGradient(vg, 0f, 0f, centerX, 0f, transparent, solid, paint);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRect(vg, panelX, panelY, centerX, panelH);
            NanoVG.nvgFillPaint(vg, paint);
            NanoVG.nvgFill(vg);
            NanoVG.nvgLinearGradient(vg, centerX + centerW, 0f, width, 0f, solid, transparent, paint);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRect(vg, centerX + centerW, panelY, width - (centerX + centerW), panelH);
            NanoVG.nvgFillPaint(vg, paint);
            NanoVG.nvgFill(vg);
        }

        Color pinkGlow = new Color(255, 142, 196, (int) (240f * alpha));
        drawGlowCenterText(vg, "⚠", width * 0.5f, panelY + 6f * scale, 42f * scale, pinkGlow, alpha, FontLoader.monaBold());
        drawGlowCenterText(vg, "即 将 退 出 游 戏", width * 0.5f, panelY + 50f * scale, 34f * scale, pinkGlow, alpha, FontLoader.monaBold());
        drawGlowCenterText(vg, "End the gameplay?", width * 0.5f, panelY + 90f * scale, 16f * scale, pinkGlow, alpha, FontLoader.ax());

        float buttonY = panelY + 142f * scale;
        String confirmText = "○  确  定";
        String cancelText = "◆  取  消";
        NanoVG.nvgFontFaceId(vg, FontLoader.monaBold());
        NanoVG.nvgFontSize(vg, 52f * scale * 0.5f);
        NanoVG.nvgTextLetterSpacing(vg, 1.8f * scale);
        float[] bounds = new float[4];
        float confirmTextW = NanoVG.nvgTextBounds(vg, 0f, 0f, confirmText, bounds);
        float cancelTextW = NanoVG.nvgTextBounds(vg, 0f, 0f, cancelText, bounds);
        NanoVG.nvgTextLetterSpacing(vg, 0f);
        float groupGap = 140f * scale;
        float confirmCenterX = width * 0.5f - groupGap * 0.5f;
        float cancelCenterX = width * 0.5f + groupGap * 0.5f;
        float confirmTextX = confirmCenterX - confirmTextW * 0.5f;
        float cancelTextX = cancelCenterX - cancelTextW * 0.5f;

        exitConfirmX = confirmTextX - 8f * scale;
        exitConfirmY = buttonY - 4f * scale;
        exitConfirmW = confirmTextW + 16f * scale;
        exitConfirmH = 30f * scale;
        exitCancelX = cancelTextX - 8f * scale;
        exitCancelY = buttonY - 4f * scale;
        exitCancelW = cancelTextW + 16f * scale;
        exitCancelH = 30f * scale;

        boolean confirmHovered = isHovered(mouseX, mouseY, exitConfirmX, exitConfirmY, exitConfirmW, exitConfirmH);
        boolean cancelHovered = isHovered(mouseX, mouseY, exitCancelX, exitCancelY, exitCancelW, exitCancelH);
        NanoVGHelper.drawString(
                confirmText,
                confirmCenterX,
                buttonY,
                FontLoader.monaBold(),
                52f * scale * 0.5f,
                NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                new Color(255, 255, 255, (int) ((confirmHovered ? 255f : 225f) * alpha))
        );
        NanoVGHelper.drawString(
                cancelText,
                cancelCenterX,
                buttonY,
                FontLoader.monaBold(),
                52f * scale * 0.5f,
                NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                new Color(255, 255, 255, (int) ((cancelHovered ? 255f : 225f) * alpha))
        );
    }

    private void drawGlowCenterText(long vg, String text, float x, float y, float fontSize, Color glowColor, float alpha, int fontId) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NanoVG.nvgFontFaceId(vg, fontId);
            NanoVG.nvgFontSize(vg, fontSize);
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP);
            NVGColor nvgGlow = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) glowColor.getRed(), (byte) glowColor.getGreen(), (byte) glowColor.getBlue(), (byte) glowColor.getAlpha(), nvgGlow);
            NanoVG.nvgFontBlur(vg, 3.2f);
            NanoVG.nvgFillColor(vg, nvgGlow);
            NanoVG.nvgText(vg, x, y, text);
            NanoVG.nvgText(vg, x, y, text);
            NanoVG.nvgFontBlur(vg, 0f);
            NVGColor white = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) (255f * alpha), white);
            NanoVG.nvgFillColor(vg, white);
            NanoVG.nvgText(vg, x, y, text);
        }
    }

    private void drawEditor(long vg, float scale) {
        if (!editorOpen) {
            panelW = 0f;
            panelH = 0f;
            adjustButtons.clear();
            return;
        }

        panelX = 14f * scale;
        panelY = 14f * scale;
        panelW = 330f * scale;
        float rowHeight = 20f * scale;
        float spacing = 4f * scale;
        panelH = 34f * scale + AdjustType.values().length * (rowHeight + spacing) + 8f * scale;
        NanoVGHelper.drawRect(panelX, panelY, panelW, panelH, new Color(0, 0, 0, 170));

        NanoVGHelper.drawString(
                "F6 Toggle",
                panelX + 8f * scale,
                panelY + 8f * scale,
                FontLoader.monaBold(),
                13f * scale,
                NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                new Color(220, 220, 220, 240)
        );

        adjustButtons.clear();
        float rowY = panelY + 26f * scale;
        for (AdjustType type : AdjustType.values()) {
            float buttonW = 20f * scale;
            float plusX = panelX + panelW - buttonW - 8f * scale;
            float minusX = plusX - buttonW - 4f * scale;
            float buttonY = rowY + 1f * scale;
            NanoVGHelper.drawString(
                    type.label,
                    panelX + 8f * scale,
                    rowY + 4f * scale,
                    FontLoader.monaBold(),
                    12f * scale,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                    new Color(232, 232, 232, 245)
            );
            NanoVGHelper.drawString(
                    formatValue(type),
                    minusX - 54f * scale,
                    rowY + 4f * scale,
                    FontLoader.monaBold(),
                    12f * scale,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                    new Color(210, 210, 210, 245)
            );
            NanoVGHelper.drawRect(minusX, buttonY, buttonW, rowHeight - 2f * scale, new Color(255, 255, 255, 35));
            NanoVGHelper.drawRect(plusX, buttonY, buttonW, rowHeight - 2f * scale, new Color(255, 255, 255, 35));
            NanoVGHelper.drawString("-", minusX + 7f * scale, rowY + 3f * scale, FontLoader.monaBold(), 13f * scale, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, new Color(255, 255, 255, 255));
            NanoVGHelper.drawString("+", plusX + 6f * scale, rowY + 3f * scale, FontLoader.monaBold(), 13f * scale, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, new Color(255, 255, 255, 255));
            adjustButtons.add(new AdjustButton(type, -type.step, minusX, buttonY, buttonW, rowHeight - 2f * scale));
            adjustButtons.add(new AdjustButton(type, type.step, plusX, buttonY, buttonW, rowHeight - 2f * scale));
            rowY += rowHeight + spacing;
        }
    }

    private void drawWhiteFadeBackground(long vg, float scale) {
        float centerX = width * whiteFadeXRatio;
        float centerY = height * whiteFadeYRatio;
        float radius = 980f * scale * whiteFadeRadiusScale;
        float alphaStrength = Math.max(0f, Math.min(5.0f, whiteFadeAlpha));
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor inner = NVGColor.malloc(stack);
            NVGColor outer = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) 0, outer);
            int passes = Math.max(1, (int) Math.ceil(alphaStrength));
            for (int i = 0; i < passes; i++) {
                float remaining = alphaStrength - i;
                if (remaining <= 0f) {
                    break;
                }
                float passAlpha = Math.min(1f, remaining) * 0.95f;
                float passRadius = radius * Math.max(0.72f, 1f - i * 0.06f);
                NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) (passAlpha * 255f), inner);
                NanoVG.nvgRadialGradient(vg, centerX, centerY, 0f, passRadius, inner, outer, paint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, 0f, 0f, width, height);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);
            }
        }
    }

    private void drawMenuButton(long vg, String text, float x, float y, float fontSize, float charSpacing, boolean hovered, float scale) {
        NanoVG.nvgFontFaceId(vg, FontLoader.monaBold());
        NanoVG.nvgFontSize(vg, fontSize);
        NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
        NanoVG.nvgTextLetterSpacing(vg, charSpacing);

        // 顶部颜色：#B6A795
        // 底部颜色：#725B42
        Color topColor = new Color(182, 167, 149);
        Color bottomColor = new Color(114, 91, 66);
        Color glowColor = hovered ? bottomColor : new Color(255, 255, 255, 120);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            float[] bounds = new float[4];
            NanoVG.nvgTextBounds(vg, x, y, text, bounds);
            float textW = bounds[2] - bounds[0];
            float textH = bounds[3] - bounds[1];
            float realTop = bounds[1];

            NVGColor nvgGlow = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) glowColor.getRed(), (byte) glowColor.getGreen(), (byte) glowColor.getBlue(), (byte) glowColor.getAlpha(), nvgGlow);

            // 1. 绘制背景围边/发光 (强度可调)
            NanoVG.nvgFontBlur(vg, 1.5f * scale);
            NanoVG.nvgFillColor(vg, nvgGlow);
            // 通过多次绘制来增加强度
            int intensity = Math.round(textGlowIntensity);
            for (int i = 0; i < intensity; i++) {
                NanoVG.nvgText(vg, x, y, text);
            }
            NanoVG.nvgFontBlur(vg, 0);

            // 2. 绘制文字主体
            if (hovered) {
                NVGColor white = NVGColor.malloc(stack);
                NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) 255, white);
                NanoVG.nvgFillColor(vg, white);
                NanoVG.nvgText(vg, x, y, text);

                // 下划线
                float lineY = y + fontSize + 7f * scale;
                float lineHeight = 2.0f * scale;

                NVGPaint glowPaint = NVGPaint.calloc(stack);
                NVGColor trans = NVGColor.malloc(stack);
                NanoVG.nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 0, trans);
                NanoVG.nvgBoxGradient(vg, x, lineY, textW, lineHeight, 2f * scale, 10f * scale, nvgGlow, trans, glowPaint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, x - 15f * scale, lineY - 15f * scale, textW + 30f * scale, lineHeight + 30f * scale);
                NanoVG.nvgFillPaint(vg, glowPaint);
                NanoVG.nvgFill(vg);

                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, x, lineY, textW, lineHeight);
                NanoVG.nvgFillColor(vg, white);
                NanoVG.nvgFill(vg);
            } else {
                // 非悬停：使用高分辨率手动分层渲染，确保渐变平滑且 100% 可见
                // 使用 30 层渲染来消除“段落感”，达到丝滑效果
                int layers = 30;
                float stepH = textH / layers;
                for (int i = 0; i < layers; i++) {
                    float t = (float) i / (layers - 1);
                    Color layerCol = interpolateColor(topColor, bottomColor, t);

                    NanoVG.nvgSave(vg);
                    // 精确裁剪每一层
                    NanoVG.nvgIntersectScissor(vg, x - 10, realTop + i * stepH, textW + 20, stepH + 0.5f);

                    NVGColor nvgLayerCol = NVGColor.malloc(stack);
                    NanoVG.nvgRGBA((byte) layerCol.getRed(), (byte) layerCol.getGreen(), (byte) layerCol.getBlue(), (byte) layerCol.getAlpha(), nvgLayerCol);
                    NanoVG.nvgFillColor(vg, nvgLayerCol);
                    NanoVG.nvgText(vg, x, y, text);

                    NanoVG.nvgRestore(vg);
                }
            }
        }
        NanoVG.nvgTextLetterSpacing(vg, 0);
    }

    private Color interpolateColor(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        return new Color(r, g, b);
    }

    private String formatValue(AdjustType type) {
        return String.format(Locale.ROOT, "%.3f", getValue(type));
    }

    private float getValue(AdjustType type) {
        return switch (type) {
            case LOGO_X -> logoCenterXRatio;
            case LOGO_Y -> logoCenterYRatio;
            case LOGO_SIZE -> logoSizeScale;
            case ROW1_X -> row1XRatio;
            case ROW1_Y -> row1YRatio;
            case ROW1_SIZE -> row1FontScale;
            case ROW1_GAP -> row1GapScale;
            case ROW2_X -> row2XRatio;
            case ROW2_Y -> row2YRatio;
            case ROW2_SIZE -> row2FontScale;
            case ROW2_GAP -> row2GapScale;
            case COPYRIGHT_X -> copyrightXRatio;
            case COPYRIGHT_Y -> copyrightYRatio;
            case COPYRIGHT_SIZE -> copyrightSizeScale;
            case CHAR_SPACING -> charSpacingScale;
            case TEXT_GLOW -> textGlowIntensity;
            case WHITE_FADE_X -> whiteFadeXRatio;
            case WHITE_FADE_Y -> whiteFadeYRatio;
            case WHITE_FADE_RADIUS -> whiteFadeRadiusScale;
            case WHITE_FADE_ALPHA -> whiteFadeAlpha;
        };
    }

    private void applyDelta(AdjustType type, float delta) {
        float updated = clamp(getValue(type) + delta, type.min, type.max);
        switch (type) {
            case LOGO_X -> logoCenterXRatio = updated;
            case LOGO_Y -> logoCenterYRatio = updated;
            case LOGO_SIZE -> logoSizeScale = updated;
            case ROW1_X -> row1XRatio = updated;
            case ROW1_Y -> row1YRatio = updated;
            case ROW1_SIZE -> row1FontScale = updated;
            case ROW1_GAP -> row1GapScale = updated;
            case ROW2_X -> row2XRatio = updated;
            case ROW2_Y -> row2YRatio = updated;
            case ROW2_SIZE -> row2FontScale = updated;
            case ROW2_GAP -> row2GapScale = updated;
            case COPYRIGHT_X -> copyrightXRatio = updated;
            case COPYRIGHT_Y -> copyrightYRatio = updated;
            case COPYRIGHT_SIZE -> copyrightSizeScale = updated;
            case CHAR_SPACING -> charSpacingScale = updated;
            case TEXT_GLOW -> textGlowIntensity = updated;
            case WHITE_FADE_X -> whiteFadeXRatio = updated;
            case WHITE_FADE_Y -> whiteFadeYRatio = updated;
            case WHITE_FADE_RADIUS -> whiteFadeRadiusScale = updated;
            case WHITE_FADE_ALPHA -> whiteFadeAlpha = updated;
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private boolean isHovered(float mx, float my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    private enum AdjustType {
        LOGO_X("Logo X", 0.005f, 0.05f, 0.95f),
        LOGO_Y("Logo Y", 0.005f, 0.05f, 0.95f),
        LOGO_SIZE("Logo Size", 0.05f, 0.40f, 2.50f),
        ROW1_X("Row1 X", 0.005f, 0.05f, 0.95f),
        ROW1_Y("Row1 Y", 0.005f, 0.05f, 0.95f),
        ROW1_SIZE("Row1 Size", 0.05f, 0.40f, 2.50f),
        ROW1_GAP("Row1 Gap", 0.05f, 0.40f, 3.00f),
        ROW2_X("Row2 X", 0.005f, 0.05f, 0.95f),
        ROW2_Y("Row2 Y", 0.005f, 0.05f, 0.95f),
        ROW2_SIZE("Row2 Size", 0.05f, 0.40f, 2.50f),
        ROW2_GAP("Row2 Gap", 0.05f, 0.40f, 3.00f),
        COPYRIGHT_X("Copyright X", 0.005f, 0.05f, 0.95f),
        COPYRIGHT_Y("Copyright Y", 0.005f, 0.05f, 0.95f),
        COPYRIGHT_SIZE("Copyright Size", 0.05f, 0.40f, 3.00f),
        CHAR_SPACING("Char Spacing", 0.05f, 0.00f, 5.00f),
        TEXT_GLOW("Text Glow", 0.5f, 0.00f, 10.00f),
        WHITE_FADE_X("White X", 0.005f, 0.00f, 1.00f),
        WHITE_FADE_Y("White Y", 0.005f, 0.00f, 2.50f),
        WHITE_FADE_RADIUS("White Radius", 0.05f, 0.00f, 5.00f),
        WHITE_FADE_ALPHA("White Alpha", 0.05f, 0.00f, 5.00f);

        private final String label;
        private final float step;
        private final float min;
        private final float max;

        AdjustType(String label, float step, float min, float max) {
            this.label = label;
            this.step = step;
            this.min = min;
            this.max = max;
        }
    }

    private static final class AdjustButton {
        private final AdjustType type;
        private final float delta;
        private final float x;
        private final float y;
        private final float w;
        private final float h;

        private AdjustButton(AdjustType type, float delta, float x, float y, float w, float h) {
            this.type = type;
            this.delta = delta;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }

    private static final class MenuEntry {
        private final String text;
        private final Runnable action;
        private float x;
        private float y;
        private float w;
        private float h;

        private MenuEntry(String text, Runnable action) {
            this.text = text;
            this.action = action;
        }

        private void setBounds(float x, float y, float w, float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        private boolean isHovered(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }
}
