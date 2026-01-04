package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Easing;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static dev.sakura.client.module.impl.hud.DynamicIslandHud.Size.INVENTORY_BG_COLOR;

public class DynamicIslandHud extends HudModule {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    static final class Size {
        static final float BASE_W = 65, BASE_H = 19;
        static final float EXPANDED_W = 90, EXPANDED_H = 25;
        static final float ELEMENT_SPACING = 20;
        static final float ELEMENT_WIDTH = 50;
        static final float LOGO_FONT_SIZE = 12;
        static final float INFO_FONT_SIZE = 10;
        static final float GLOW_RADIUS = 3.0f;
        static final Color INVENTORY_BG_COLOR = new Color(18, 18, 18, 70);
    }

    private static final class Timing {
        static final long EXPAND = 300L;
        static final long DISPLAY = 1500L;
        static final long COLLAPSE_1 = 300L;
        static final long COLLAPSE_2 = 400L;
        static final long TOTAL = EXPAND + DISPLAY + COLLAPSE_1 + COLLAPSE_2;

    }

    private enum Phase {
        IDLE,
        EXPANDING,
        DISPLAY,
        COLLAPSE_1,
        COLLAPSE_2
    }

    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);


    private static ToggleInfo currentToggle;
    private static ToggleInfo pendingToggle;
    private long toggleStartTime = -1L;
    private float targetExpandedWidth = Size.EXPANDED_W;

    private Phase phase = Phase.IDLE;
    private float progress;
    private float blurOpacity = 1f;
    private float animX, animY, animW, animH;

    public DynamicIslandHud() {
        super("DynamicIsland", "灵动岛", 0, 0);
        this.width = Size.BASE_W;
        this.height = Size.BASE_H;
    }

    public static void onModuleToggle(Module module, boolean enabled) {
        pendingToggle = new ToggleInfo(module.getEnglishName(), enabled);
    }

    @Override
    public void onRenderContent() {
        update();
        NanoVGRenderer.INSTANCE.withRawCoords(() -> {
            renderBlur(currentContext);
            renderSideBlurs(currentContext, 1f);
        });
        renderContent();
    }

    private void update() {
        processToggle();
        calculateState();
    }

    private void processToggle() {
        if (pendingToggle != null) {
            currentToggle = pendingToggle;
            pendingToggle = null;
            toggleStartTime = System.currentTimeMillis();
            targetExpandedWidth = calculateExpandedWidth();
        } else if (currentToggle != null && elapsed() >= Timing.TOTAL) {
            currentToggle = null;
            toggleStartTime = -1L;
        }
    }

    private void calculateState() {
        int screenWidth = mc.getWindow().getScaledWidth();
        long dt = elapsed();

        if (currentToggle == null && toggleStartTime == -1L) {
            setPhase(Phase.IDLE, 0f, Size.BASE_W, Size.BASE_H, 1f);
        } else if (dt < Timing.EXPAND) {
            float p = easeOut(dt / (float) Timing.EXPAND);
            setPhase(Phase.EXPANDING, p,
                    lerp(Size.BASE_W, targetExpandedWidth, p),
                    lerp(Size.BASE_H, Size.EXPANDED_H, p),
                    lerp(1f, 1f, p));
        } else if (dt < Timing.EXPAND + Timing.DISPLAY) {
            float p = (dt - Timing.EXPAND) / (float) Timing.DISPLAY;
            setPhase(Phase.DISPLAY, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
        } else if (dt < Timing.EXPAND + Timing.DISPLAY + Timing.COLLAPSE_1) {
            float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY) / (float) Timing.COLLAPSE_1);
            setPhase(Phase.COLLAPSE_1, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
        } else {
            float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY - Timing.COLLAPSE_1) / (float) Timing.COLLAPSE_2);
            setPhase(Phase.COLLAPSE_2, p,
                    lerp(targetExpandedWidth, Size.BASE_W, p),
                    lerp(Size.EXPANDED_H, Size.BASE_H, p),
                    1f);
        }

        animX = (screenWidth - animW) / 2f;
        animY = y;
        this.width = animW;
        this.height = animH;
        this.x = animX;
    }

    public float getRadius() {
        return radius.get().floatValue();
    }

    private void setPhase(Phase p, float prog, float w, float h, float blur) {
        this.phase = p;
        this.progress = prog;
        this.animW = w;
        this.animH = h;
        this.blurOpacity = interpolateBlurOpacity(blur);
    }

    private float interpolateBlurOpacity(float targetBlur) {
        float delta = targetBlur - this.blurOpacity;
        float interpolationFactor = 0.15f;
        return this.blurOpacity + delta * interpolationFactor;
    }

    private void renderBlur(DrawContext context) {
        if (!blur.get()) return;
        if (mc.currentScreen instanceof ClickGuiScreen || mc.currentScreen instanceof GameMenuScreen) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity));
        if (clampedBlurOpacity <= 0.005f) return;

        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), animX, animY, animW, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderSideBlurs(DrawContext context, float expandProgress) {
        if (!blur.get()) return;
        if (mc.currentScreen instanceof ClickGuiScreen || mc.currentScreen instanceof GameMenuScreen) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity * expandProgress));
        if (clampedBlurOpacity <= 0.005f) return;

        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;
        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;
        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(), nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderContent() {
        switch (phase) {
            case IDLE -> renderIdle();
            case EXPANDING -> renderExpanding();
            case DISPLAY -> renderDisplay();
            case COLLAPSE_1 -> renderCollapse1();
            case COLLAPSE_2 -> renderCollapse2();
        }
    }

    private void renderIdle() {
        drawBackground(INVENTORY_BG_COLOR);
        drawSideInfo(0f);
        drawCenteredTitle();
    }

    private void renderExpanding() {
        drawBackground(INVENTORY_BG_COLOR);
        drawSideInfo(progress);
        if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(progress), 0f);
        }
    }

    private void renderDisplay() {
        drawBackground(INVENTORY_BG_COLOR);
        drawSideInfo(1f);
        if (currentToggle != null) {
            drawToggleInfo(255, progress);
        }
    }

    private void renderCollapse1() {
        drawBackground(INVENTORY_BG_COLOR);
        drawSideInfo(1f);
        if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(1f - progress), 1f);
        }
    }

    private void renderCollapse2() {
        drawBackground(INVENTORY_BG_COLOR);
        drawSideInfo(0f);
    }

    private void drawBackground(Color color) {
        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(animX, animY, animW, animH, getRadius(), color);
        } else {
            NanoVGHelper.drawRoundRect(animX, animY, animW, animH, getRadius(), color);
        }
    }

    private void drawCenteredTitle() {
        int font = FontLoader.bold((int) Size.LOGO_FONT_SIZE);
        String name = Sakura.MOD_NAME;
        float textW = NanoVGHelper.getTextWidth(name, font, Size.LOGO_FONT_SIZE);
        NanoVGHelper.drawGlowingString(name, animX + (animW - textW) / 2f, animY + animH / 2f + 4, font, Size.LOGO_FONT_SIZE, ClickGui.color(0), Size.GLOW_RADIUS);
    }

    private void drawSideInfo(float expandProgress) {
        int font = FontLoader.medium(9);
        Color color = Color.WHITE;
        float centerY = animY + animH / 2f + 3;
        Color bgColor = INVENTORY_BG_COLOR;
        String time = LocalTime.now().format(TIME_FORMAT);
        float timeW = NanoVGHelper.getTextWidth(time, font, Size.INFO_FONT_SIZE);
        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;
        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(time, timeBgX + (Size.ELEMENT_WIDTH - timeW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);
        String username = "FPS:" + mc.getCurrentFps();
        float nameW = NanoVGHelper.getTextWidth(username, font, Size.INFO_FONT_SIZE);
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;
        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(username, nameBgX + (Size.ELEMENT_WIDTH - nameW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);
    }

    private void drawToggleInfo(int alpha, float timeProgress) {
        if (currentToggle == null) return;
        float padding = 6, iconSize = 16;
        float centerY = animY + (animH - 3) / 2f;
        int iconFont = FontLoader.icons(iconSize);
        String icon = currentToggle.enabled ? "U" : "T";
        Color iconColor = currentToggle.enabled ? ClickGui.color(0) : ClickGui.color2(0);
        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        NanoVGHelper.drawString(icon, animX + padding + 6, centerY + iconSize * 0.35f, iconFont, iconSize, iconColor);
        int textFont = FontLoader.medium((int) Size.LOGO_FONT_SIZE);
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");
        NanoVGHelper.drawString(status, animX + padding + iconW + 14, centerY + Size.LOGO_FONT_SIZE * 0.35f, textFont, Size.LOGO_FONT_SIZE - 2f, Color.WHITE);

        drawProgressBar(alpha, timeProgress);
    }

    private void drawProgressBar(int alpha, float timeProgress) {
        float padding = 8, barH = 1.5f;
        float barY = animY + animH - barH - 3;
        float maxW = Math.max(0, animW - padding * 2);
        float progress = Math.max(0f, Math.min(1f, 1f - timeProgress));
        float currentW = maxW * progress;

        NanoVGHelper.drawRoundRect(animX + padding, barY, maxW, barH, barH / 2,
                withAlpha(ClickGui.color(0), 50));
        if (currentW > 0) {
            NanoVGHelper.drawRoundRect(animX + padding, barY, currentW, barH, barH / 2,
                    withAlpha(ClickGui.color(0), 220));
        }
    }

    private float calculateExpandedWidth() {
        if (currentToggle == null) return Size.EXPANDED_W;

        float padding = 6, iconSize = 16, textSize = Size.LOGO_FONT_SIZE;
        int iconFont = FontLoader.icons(iconSize);
        int textFont = FontLoader.medium(textSize);

        String icon = currentToggle.enabled ? "U" : "T";
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");

        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        float textW = NanoVGHelper.getTextWidth(status, textFont, textSize);
        float needed = padding * 2 + iconW + 4 + textW;

        return Math.max(Size.EXPANDED_W, Math.max(needed, 41));
    }

    private long elapsed() {
        return toggleStartTime == -1L ? 0 : System.currentTimeMillis() - toggleStartTime;
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private static float easeOut(float t) {
        return (float) Easing.CUBIC_OUT.ease(t);
    }

    private static int alphaFromProgress(float p) {
        return (int) (255 * p);
    }

    private static Color withAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }

    private record ToggleInfo(String name, boolean enabled) {
    }
}