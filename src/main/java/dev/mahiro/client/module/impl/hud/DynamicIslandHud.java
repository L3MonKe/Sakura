package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.module.impl.movement.Scaffold;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Easing;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicIslandHud extends HudModule {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static volatile Text capturedTabHeader;
    private static volatile Text capturedTabFooter;
    private static volatile List<PlayerListEntry> capturedTabEntries = List.of();

    private static final class Size {
        static final float BASE_W = 65, BASE_H = 19;
        static final float EXPANDED_W = 90, EXPANDED_H = 25;
        static final float SCAFFOLD_H = 25;
        static final float ELEMENT_SPACING = 20;
        static final float ELEMENT_WIDTH = 50;
        static final float LOGO_FONT_SIZE = 12;
        static final float INFO_FONT_SIZE = 10;
        static final float GLOW_RADIUS = 3.0f;
        static final Color INVENTORY_BG_COLOR = new Color(18, 18, 18, 70);

        static final float TAB_PLAYER_HEIGHT = 14;
        static final float TAB_PADDING = 8;
        static final float TAB_HEADER_Y = 12;
        static final float TAB_LIST_Y = 30;
        static final int TAB_COLUMNS = 1;
    }

    private static final class Timing {
        static final long EXPAND = 300L;
        static final long DISPLAY = 1500L;
        static final long COLLAPSE_1 = 300L;
        static final long COLLAPSE_2 = 400L;
        static final long TOTAL = EXPAND + DISPLAY + COLLAPSE_1 + COLLAPSE_2;
        static final long TAB_TRANSITION = 350L;
    }

    private enum Phase {
        IDLE,
        EXPANDING,
        DISPLAY,
        COLLAPSE_1,
        COLLAPSE_2,
        TAB_EXPAND,
        TAB_DISPLAY,
        TAB_COLLAPSE
    }

    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);

    private static ToggleInfo currentToggle;
    private static ToggleInfo pendingToggle;
    private long toggleStartTime = -1L;
    private long tabStartTime = -1L;
    private float targetExpandedWidth = Size.EXPANDED_W;

    private Phase phase = Phase.IDLE;
    private float progress;
    private float blurOpacity = 1f;
    private float animX, animY, animW, animH;
    private float tabMergeProgress;

    private List<PlayerListEntry> playerList;
    private float tabTargetW, tabTargetH;
    private float scaffoldBarProgress;
    private float scaffoldItemX, scaffoldItemY, scaffoldItemScale;
    private float scaffoldRightTextWidth;
    private float scaffoldBarMaxCount;
    private Color scaffoldBlockColor;
    private String scaffoldCountText;
    private String scaffoldSuffixText;
    private String scaffoldBpsText;
    private ItemStack scaffoldItem;
    private boolean scaffoldItemVisible;
    private int scaffoldLastCount = -1;
    private long scaffoldLastTime;
    private double scaffoldBpsValue;

    public static void hookVanillaTab(Text header, Text footer, List<PlayerListEntry> entries) {
        capturedTabHeader = header;
        capturedTabFooter = footer;
        capturedTabEntries = entries == null ? List.of() : List.copyOf(entries);
    }

    public DynamicIslandHud() {
        super("DynamicIsland", "灵动岛", 0, 0);
        this.width = Size.BASE_W;
        this.height = Size.BASE_H;
    }

    public static void onModuleToggle(Module module, boolean enabled) {
        pendingToggle = new ToggleInfo(module.getEnglishName(), enabled);
    }

    @Override
    public void onRender(DrawContext context) {
        update();

        renderBlur(context);
        renderSideBlurs(context, getSideBlurOpacity());

        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
        renderScaffoldItem(context);

        if (isTabPhase()) {
            renderCapturedTab(context);
        }
    }

    private void update() {
        handleTabInput();
        processToggle();
        calculateState();
    }

    private void handleTabInput() {
        boolean tabPressed = mc.options.playerListKey.isPressed();

        if (tabPressed && !isTabPhase()) {
            tabStartTime = System.currentTimeMillis();
            phase = Phase.TAB_EXPAND;
            updatePlayerList();
        } else if (tabPressed && isTabPhase()) {
            updatePlayerList();
        } else if (phase == Phase.TAB_DISPLAY || phase == Phase.TAB_EXPAND) {
            tabStartTime = System.currentTimeMillis();
            phase = Phase.TAB_COLLAPSE;
        }
    }

    private void updatePlayerList() {
        if (mc.getNetworkHandler() != null) {
            List<PlayerListEntry> source = capturedTabEntries.isEmpty() ? List.copyOf(mc.getNetworkHandler().getPlayerList()) : capturedTabEntries;
            playerList = source.stream()
                    .sorted(Comparator.comparingInt((PlayerListEntry e) -> e.getGameMode() == GameMode.SPECTATOR ? 1 : 0).thenComparing(e -> e.getProfile().name()))
                    .limit(80) // 可有可无吧。。。
                    .collect(Collectors.toList());

            int count = playerList.size();
            int rows = (int) Math.ceil((double) count / Size.TAB_COLUMNS);

            tabTargetW = Size.BASE_W + 2f * (Size.ELEMENT_WIDTH + Size.ELEMENT_SPACING);
            int innerW = (int) Math.max(0, tabTargetW - Size.TAB_PADDING * 2f);
            int fontH = mc.textRenderer.fontHeight;

            int headerLines;
            if (capturedTabHeader != null && !capturedTabHeader.getString().isEmpty()) {
                headerLines = mc.textRenderer.wrapLines(capturedTabHeader, innerW).size();
            } else {
                headerLines = 1;
            }

            int ftLine = 0;
            if (capturedTabFooter != null && !capturedTabFooter.getString().isEmpty()) {
                ftLine = mc.textRenderer.wrapLines(capturedTabFooter, innerW).size();
            }

            float headerH = headerLines * fontH;
            float footerH = ftLine * fontH;
            float listY = Math.max(Size.TAB_LIST_Y, Size.TAB_HEADER_Y + headerH + 8f);

            tabTargetH = listY + rows * Size.TAB_PLAYER_HEIGHT + Size.TAB_PADDING + (ftLine > 0 ? (footerH + 8f) : 0f);
            tabTargetH = Math.max(tabTargetH, Size.BASE_H * 2f);
        }
    }

    private void processToggle() {
        if (isTabPhase()) return;

        if (pendingToggle != null) {
            currentToggle = pendingToggle;
            pendingToggle = null;
            toggleStartTime = System.currentTimeMillis();
            targetExpandedWidth = calculateExpandedWidth();
        } else if (currentToggle != null && ela() >= Timing.TOTAL) {
            currentToggle = null;
            toggleStartTime = -1L;
        }
    }

    private void calculateState() {
        long dt = ela();
        long tabDt = elaTab();

        if (phase == Phase.TAB_EXPAND) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = MathHelper.clamp(tabDt / (Timing.TAB_TRANSITION * 0.45f), 0f, 1f);
                float expandT = MathHelper.clamp((tabDt - Timing.TAB_TRANSITION * 0.25f) / (Timing.TAB_TRANSITION * 0.75f), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_EXPAND, expandP,
                        MathHelper.lerp(mergeP, Size.BASE_W, tabTargetW),
                        MathHelper.lerp(expandP, Size.BASE_H, tabTargetH),
                        1f);
            } else {
                tabMergeProgress = 1f;
                setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
            }
        } else if (phase == Phase.TAB_COLLAPSE) {
            if (tabDt < Timing.TAB_TRANSITION) {
                float mergeT = MathHelper.clamp(1f - (tabDt / (Timing.TAB_TRANSITION * 0.45f)), 0f, 1f);
                float expandT = MathHelper.clamp(1f - ((tabDt - Timing.TAB_TRANSITION * 0.10f) / (Timing.TAB_TRANSITION * 0.90f)), 0f, 1f);
                float mergeP = easeOut(mergeT);
                float expandP = easeOut(expandT);
                tabMergeProgress = mergeP;
                setPhase(Phase.TAB_COLLAPSE, expandP,
                        MathHelper.lerp(mergeP, Size.BASE_W, tabTargetW),
                        MathHelper.lerp(expandP, Size.BASE_H, tabTargetH),
                        1f);
            } else {
                tabMergeProgress = 0f;
                setPhase(Phase.IDLE, 0f, Size.BASE_W, Size.BASE_H, 1f);
                tabStartTime = -1L;
            }
        } else if (phase == Phase.TAB_DISPLAY) {
            tabMergeProgress = 1f;
            setPhase(Phase.TAB_DISPLAY, 1f, tabTargetW, tabTargetH, 1f);
        } else {
            if (shouldRenderScaffold()) {
                updateScaffoldState();
                float scaffoldTargetW = calculateScaffoldWidth();
                float lerp = 0.2f;
                animW = MathHelper.lerp(lerp, animW, scaffoldTargetW);
                animH = MathHelper.lerp(lerp, animH, Size.SCAFFOLD_H);
                progress = 1f;
                phase = Phase.DISPLAY;
                blurOpacity = interpolateBlurOpacity(1f);

                animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
                animY = y;
                this.width = animW;
                this.height = animH;
                this.x = animX;
                return;
            }
            if (currentToggle == null && toggleStartTime == -1L) {
                setPhase(Phase.IDLE, 0f, Size.BASE_W, Size.BASE_H, 1f);
            } else if (dt < Timing.EXPAND) {
                float p = easeOut(dt / (float) Timing.EXPAND);
                setPhase(Phase.EXPANDING, p,
                        MathHelper.lerp(p, Size.BASE_W, targetExpandedWidth),
                        MathHelper.lerp(p, Size.BASE_H, Size.EXPANDED_H),
                        MathHelper.lerp(p, 1f, 1f));
            } else if (dt < Timing.EXPAND + Timing.DISPLAY) {
                float p = (dt - Timing.EXPAND) / (float) Timing.DISPLAY;
                setPhase(Phase.DISPLAY, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else if (dt < Timing.EXPAND + Timing.DISPLAY + Timing.COLLAPSE_1) {
                float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY) / (float) Timing.COLLAPSE_1);
                setPhase(Phase.COLLAPSE_1, p, targetExpandedWidth, Size.EXPANDED_H, 1f);
            } else {
                float p = easeOut((dt - Timing.EXPAND - Timing.DISPLAY - Timing.COLLAPSE_1) / (float) Timing.COLLAPSE_2);
                setPhase(Phase.COLLAPSE_2, p,
                        MathHelper.lerp(p, targetExpandedWidth, Size.BASE_W),
                        MathHelper.lerp(p, Size.EXPANDED_H, Size.BASE_H),
                        1f);
            }
        }

        animX = (mc.getWindow().getScaledWidth() - animW) / 2f;
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

    private boolean isTabPhase() {
        return phase == Phase.TAB_EXPAND || phase == Phase.TAB_DISPLAY || phase == Phase.TAB_COLLAPSE;
    }

    private float getMergeProgress() {
        if (phase == Phase.TAB_EXPAND || phase == Phase.TAB_DISPLAY || phase == Phase.TAB_COLLAPSE)
            return tabMergeProgress;
        return progress;
    }

    private float interpolateBlurOpacity(float targetBlur) {
        float delta = targetBlur - this.blurOpacity;
        float interpolationFactor = 0.15f;
        return this.blurOpacity + delta * interpolationFactor;
    }

    private float getSideBlurOpacity() {
        if (shouldRenderScaffold()) {
            return 0f;
        }
        if (isTabPhase()) {
            return (phase == Phase.TAB_EXPAND) ? (1f - tabMergeProgress) : (phase == Phase.TAB_COLLAPSE ? tabMergeProgress : 0f);
        }
        return 1f;
    }

    private void renderBlur(DrawContext context) {
        if (!blur.get()) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity));
        if (clampedBlurOpacity <= 0.005f) return;

        Shader2DUtil.drawRoundedBlur(
                animX, animY, animW, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderSideBlurs(DrawContext context, float opacity) {
        if (!blur.get() || opacity <= 0.05f) return;

        float clampedBlurOpacity = Math.max(0f, Math.min(1f, blurOpacity * opacity));
        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;
        if (phase == Phase.TAB_EXPAND) {
            timeBgX = MathHelper.lerp(tabMergeProgress, timeBgX, animX);
        } else if (phase == Phase.TAB_COLLAPSE) {
            timeBgX = MathHelper.lerp(tabMergeProgress, timeBgX, animX);
        }

        Shader2DUtil.drawRoundedBlur(
                timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;
        if (phase == Phase.TAB_EXPAND) {
            nameBgX = MathHelper.lerp(tabMergeProgress, nameBgX, animX + animW - Size.ELEMENT_WIDTH);
        } else if (phase == Phase.TAB_COLLAPSE) {
            nameBgX = MathHelper.lerp(tabMergeProgress, nameBgX, animX + animW - Size.ELEMENT_WIDTH);
        }

        Shader2DUtil.drawRoundedBlur(
                nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(),
                new Color(0, 0, 0, 0), blurStrength.get().floatValue(), clampedBlurOpacity
        );
    }

    private void renderContent() {
        if (shouldRenderScaffold()) {
            renderScaffold();
            return;
        }
        switch (phase) {
            case IDLE -> renderIdle();
            case EXPANDING -> renderExpanding();
            case DISPLAY -> renderDisplay();
            case COLLAPSE_1 -> renderCollapse1();
            case COLLAPSE_2 -> renderCollapse2();
            case TAB_EXPAND -> renderTabExpand();
            case TAB_DISPLAY -> renderTabDisplay();
            case TAB_COLLAPSE -> renderTabCollapse();
        }
    }

    private void renderIdle() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        drawSideInfo(0f, 1f);
        drawCenteredTitle(1f);
    }

    private void renderExpanding() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        drawSideInfo(progress, 1f);
        if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(progress), 0f);
        }
    }

    private void renderDisplay() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        drawSideInfo(1f, 1f);
        if (currentToggle != null) {
            drawToggleInfo(255, progress);
        }
    }

    private void renderCollapse1() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        drawSideInfo(1f, 1f);
        if (currentToggle != null) {
            drawToggleInfo(alphaFromProgress(1f - progress), 1f);
        }
    }

    private void renderCollapse2() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        drawSideInfo(0f, 1f);
    }

    private void renderTabExpand() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        float alpha = 1f - getMergeProgress();
        drawSideInfo(0f, alpha);
        drawCenteredTitle(alpha);
    }

    private void renderTabDisplay() {
        drawBackground(Size.INVENTORY_BG_COLOR);
    }

    private void renderTabCollapse() {
        drawBackground(Size.INVENTORY_BG_COLOR);
        float alpha = 1f - getMergeProgress();
        drawSideInfo(0f, alpha);
        drawCenteredTitle(alpha);
    }

    private void renderScaffold() {
        if (!updateScaffoldState()) {
            renderIdle();
            return;
        }

        float padding = 8f;
        float iconBox = 17f;
        float iconRadius = 8.5f;
        float iconX = animX + padding;
        float iconY = animY + (animH - iconBox) / 2f;

        float rightX = animX + animW - padding - scaffoldRightTextWidth;
        float barX = iconX + iconBox + 7f;
        float barW = Math.max(30f, rightX - barX - 8f);
        float barH = 3f;
        float barY = animY + animH / 2f - 1.5f;

        Color base = new Color(12, 12, 12, 80);
        Color base2 = new Color(20, 20, 20, 110);
        Color border = new Color(255, 255, 255, 18);

        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(animX, animY, animW, animH, getRadius(), new Color(18, 18, 18, 85));
        }
        NanoVGHelper.drawGradientRRect2(animX, animY, animW, animH, getRadius(), base, base2);
        NanoVGHelper.drawRoundRectOutline(animX, animY, animW, animH, getRadius(), 1f, border);

        Color iconBg = new Color(scaffoldBlockColor.getRed(), scaffoldBlockColor.getGreen(), scaffoldBlockColor.getBlue(), 120);
        NanoVGHelper.drawRoundRect(iconX, iconY, iconBox, iconBox, iconRadius, iconBg);
        NanoVGHelper.drawRoundRectOutline(iconX, iconY, iconBox, iconBox, iconRadius, 1f, new Color(255, 255, 255, 28));

        NanoVGHelper.drawRoundRect(barX, barY, barW, barH, barH / 2f, new Color(255, 255, 255, 22));
        float pct = scaffoldBarMaxCount <= 0f ? 0f : MathHelper.clamp(parseScaffoldCount() / scaffoldBarMaxCount, 0f, 1f);
        float barTarget = barW * pct;
        scaffoldBarProgress = MathHelper.lerp(0.2f, scaffoldBarProgress, barTarget);
        if (scaffoldBarProgress > 0.5f) {
            NanoVGHelper.drawGradientRRect2(barX, barY, scaffoldBarProgress, barH, barH / 2f, ClickGui.color(0), ClickGui.color2(0));
        }

        float countSize = 10f;
        float textSize = 9f;
        float subSize = 7f;
        int countFont = FontLoader.bold();
        int textFont = FontLoader.medium();

        float countY = animY + animH / 2f + 1f;
        NanoVGHelper.drawString(scaffoldCountText, rightX, countY, countFont, countSize, new Color(255, 255, 255, 230));
        float countW = NanoVGHelper.getTextWidth(scaffoldCountText, countFont, countSize);
        NanoVGHelper.drawString(scaffoldSuffixText, rightX + countW, countY, textFont, textSize, new Color(200, 200, 200, 220));
        NanoVGHelper.drawString(scaffoldBpsText, rightX, countY + 8.5f, textFont, subSize, new Color(160, 160, 160, 200));

        scaffoldItemScale = 0.8f;
        float itemSize = 16f * scaffoldItemScale;
        scaffoldItemX = iconX + (iconBox - itemSize) / 2f;
        scaffoldItemY = iconY + (iconBox - itemSize) / 2f;
    }

    private void renderScaffoldItem(DrawContext context) {
        if (!shouldRenderScaffold() || !scaffoldItemVisible || scaffoldItem.isEmpty()) return;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(scaffoldItemX, scaffoldItemY);
        context.getMatrices().scale(scaffoldItemScale, scaffoldItemScale);
        context.drawItem(scaffoldItem, 0, 0);
        context.getMatrices().popMatrix();
    }

    private void drawBackground(Color color) {
        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(animX, animY, animW, animH, getRadius(), color);
        } else {
            NanoVGHelper.drawRoundRect(animX, animY, animW, animH, getRadius(), color);
        }
    }

    private void drawCenteredTitle(float alpha) {
        if (alpha <= 0.05f) return;
        int font = FontLoader.bold();
        String title = "Mahiro";
        float textW = NanoVGHelper.getTextWidth(title, font, Size.LOGO_FONT_SIZE);
        NanoVGHelper.drawGlowingString(title, animX + (animW - textW) / 2f, animY + animH / 2f + 4, font, Size.LOGO_FONT_SIZE, withAlpha(ClickGui.color(0), (int) (255 * alpha)), Size.GLOW_RADIUS);
    }

    private void drawSideInfo(float expandProgress, float alpha) {
        if (alpha <= 0.05f) return;

        int font = FontLoader.medium();
        Color color = withAlpha(Color.WHITE, (int) (255 * alpha));
        float centerY = animY + animH / 2f + 3;
        Color bgColor = withAlpha(Size.INVENTORY_BG_COLOR, (int) (70 * alpha));

        // Time
        String time = LocalTime.now().format(TIME_FORMAT);
        float timeW = NanoVGHelper.getTextWidth(time, font, Size.INFO_FONT_SIZE);
        float timeBgX = animX - Size.ELEMENT_SPACING - Size.ELEMENT_WIDTH;

        if (phase == Phase.TAB_EXPAND) {
            timeBgX = MathHelper.lerp(tabMergeProgress, timeBgX, animX);
        } else if (phase == Phase.TAB_COLLAPSE) {
            timeBgX = MathHelper.lerp(tabMergeProgress, timeBgX, animX);
        }

        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(timeBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(time, timeBgX + (Size.ELEMENT_WIDTH - timeW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);

        // FPS
        String username = "FPS:" + mc.getCurrentFps();
        float nameW = NanoVGHelper.getTextWidth(username, font, Size.INFO_FONT_SIZE);
        float nameBgX = animX + animW + Size.ELEMENT_SPACING;

        if (phase == Phase.TAB_EXPAND) {
            nameBgX = MathHelper.lerp(tabMergeProgress, nameBgX, animX + animW - Size.ELEMENT_WIDTH);
        } else if (phase == Phase.TAB_COLLAPSE) {
            nameBgX = MathHelper.lerp(tabMergeProgress, nameBgX, animX + animW - Size.ELEMENT_WIDTH);
        }

        if (enableBloom.get()) {
            NanoVGHelper.drawRoundRectBloom(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        } else {
            NanoVGHelper.drawRoundRect(nameBgX, animY, Size.ELEMENT_WIDTH, animH, getRadius(), bgColor);
        }
        NanoVGHelper.drawString(username, nameBgX + (Size.ELEMENT_WIDTH - nameW) / 2, centerY, font, Size.INFO_FONT_SIZE, color);
    }

    private void renderCapturedTab(DrawContext context) {
        if (playerList == null) return;

        int innerX1 = (int) (animX + Size.TAB_PADDING);
        int innerY1 = (int) (animY + Size.TAB_PADDING);
        int innerX2 = (int) (animX + animW - Size.TAB_PADDING);
        int innerY2 = (int) (animY + animH - Size.TAB_PADDING);
        if (innerX2 <= innerX1 || innerY2 <= innerY1) return;

        context.enableScissor(innerX1, innerY1, innerX2, innerY2);

        int innerW = innerX2 - innerX1;
        int fontH = mc.textRenderer.fontHeight;

        int y = (int) (animY + Size.TAB_HEADER_Y);

        Text headerText = capturedTabHeader;
        if (headerText == null || headerText.getString().isEmpty()) {
            headerText = Text.literal("Players: " + playerList.size());
        }
        List<OrderedText> headerLines = mc.textRenderer.wrapLines(headerText, innerW);
        for (OrderedText line : headerLines) {
            int lineW = mc.textRenderer.getWidth(line);
            int x = (int) (animX + (animW - lineW) / 2f);
            context.drawTextWithShadow(mc.textRenderer, line, x, y, 0xFFFFFF);
            y += fontH;
        }
        y += 8;

        int listY = Math.max((int) (animY + Size.TAB_LIST_Y), y);
        int rowH = (int) Size.TAB_PLAYER_HEIGHT;
        int headSize = 10;
        int headYOffset = Math.max(0, (rowH - headSize) / 2);

        int i = 0;
        for (PlayerListEntry entry : playerList) {
            int rowY = listY + i * rowH;
            if (rowY + rowH > innerY2) break;

            int headX = innerX1;
            int headY = rowY + headYOffset;

            context.drawTexture(RenderPipelines.GUI_TEXTURED, entry.getSkinTextures().body().texturePath(), headX, headY, 8, 8, headSize, headSize, 8, 8, 64, 64);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, entry.getSkinTextures().body().texturePath(), headX, headY, 40, 8, headSize, headSize, 8, 8, 64, 64);

            String ping = entry.getLatency() + "ms";
            int pingW = mc.textRenderer.getWidth(ping);
            int pingX = innerX2 - pingW;
            int textY = rowY + Math.max(0, (rowH - fontH) / 2);
            context.drawTextWithShadow(mc.textRenderer, ping, pingX, textY, 0xA0A0A0);

            Text nameText = mc.inGameHud.getPlayerListHud().getPlayerName(entry);
            int nameX = headX + headSize + 4;
            int nameClipX2 = pingX - 6;
            if (nameClipX2 > nameX) {
                context.enableScissor(nameX, rowY, nameClipX2, rowY + rowH);
                context.drawTextWithShadow(mc.textRenderer, nameText, nameX, textY, 0xFFFFFF);
                context.disableScissor();
            }

            i++;
        }

        Text footerText = capturedTabFooter;
        if (footerText != null && !footerText.getString().isEmpty()) {
            List<OrderedText> footerLines = mc.textRenderer.wrapLines(footerText, innerW);
            int footerY = innerY2 - footerLines.size() * fontH;
            for (OrderedText line : footerLines) {
                int lineW = mc.textRenderer.getWidth(line);
                int x = (int) (animX + (animW - lineW) / 2f);
                context.drawTextWithShadow(mc.textRenderer, line, x, footerY, 0xFFFFFF);
                footerY += fontH;
            }
        }

        context.disableScissor();
    }

    private void drawToggleInfo(int alpha, float timeProgress) {
        if (currentToggle == null) return;
        float padding = 6, iconSize = 16;
        float centerY = animY + (animH - 3) / 2f;
        int iconFont = FontLoader.icons();
        String icon = currentToggle.enabled ? "U" : "T";
        Color iconColor = currentToggle.enabled ? ClickGui.color(0) : ClickGui.color2(0);
        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        NanoVGHelper.drawString(icon, animX + padding + 6, centerY + iconSize * 0.35f, iconFont, iconSize, withAlpha(iconColor, alpha));
        int textFont = FontLoader.medium();
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");
        NanoVGHelper.drawString(status, animX + padding + iconW + 14, centerY + Size.LOGO_FONT_SIZE * 0.35f, textFont, Size.LOGO_FONT_SIZE - 2f, withAlpha(Color.WHITE, alpha));

        drawProgressBar(alpha, timeProgress);
    }

    private void drawProgressBar(int alpha, float timeProgress) {
        float padding = 8, barH = 1.5f;
        float barY = animY + animH - barH - 3;
        float maxW = Math.max(0, animW - padding * 2);
        float progress = Math.max(0f, Math.min(1f, 1f - timeProgress));
        float currentW = maxW * progress;

        NanoVGHelper.drawRoundRect(animX + padding, barY, maxW, barH, barH / 2, withAlpha(ClickGui.color(0), (int) (50 * (alpha / 255f))));
        if (currentW > 0) {
            NanoVGHelper.drawRoundRect(animX + padding, barY, currentW, barH, barH / 2, withAlpha(ClickGui.color(0), (int) (220 * (alpha / 255f))));
        }
    }

    private float calculateExpandedWidth() {
        if (currentToggle == null) return Size.EXPANDED_W;

        float padding = 6, iconSize = 16, textSize = Size.LOGO_FONT_SIZE;
        int iconFont = FontLoader.icons();
        int textFont = FontLoader.medium();

        String icon = currentToggle.enabled ? "U" : "T";
        String status = currentToggle.name + (currentToggle.enabled ? " 已开启" : " 已关闭");

        float iconW = NanoVGHelper.getTextWidth(icon, iconFont, iconSize);
        float textW = NanoVGHelper.getTextWidth(status, textFont, textSize);
        float needed = padding * 2 + iconW + 4 + textW;

        return Math.max(Size.EXPANDED_W, Math.max(needed, 41));
    }

    private boolean shouldRenderScaffold() {
        if (isTabPhase()) return false;
        Scaffold scaffold = Mahiro.MODULES.getModule(Scaffold.class);
        return scaffold != null && scaffold.isEnabled();
    }

    private boolean updateScaffoldState() {
        Scaffold scaffold = Mahiro.MODULES.getModule(Scaffold.class);
        if (scaffold == null || !scaffold.isEnabled() || mc.player == null) {
            scaffoldItem = ItemStack.EMPTY;
            scaffoldItemVisible = false;
            return false;
        }

        ItemStack main = mc.player.getMainHandStack();
        ItemStack off = mc.player.getOffHandStack();
        scaffoldItem = main.getItem() instanceof BlockItem ? main : (off.getItem() instanceof BlockItem ? off : ItemStack.EMPTY);
        scaffoldItemVisible = !scaffoldItem.isEmpty();

        int count = 0;
        int maxCount = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                count += stack.getCount();
                maxCount += stack.getMaxCount();
            }
        }
        scaffoldBarMaxCount = Math.max(0, maxCount);
        scaffoldCountText = String.valueOf(count);
        scaffoldSuffixText = ClickGui.language.is(ClickGui.Language.Chinese) ? "块" : "s";

        long now = System.currentTimeMillis();
        if (count <= 0) {
            scaffoldBpsValue = 0.0;
            scaffoldLastCount = -1;
            scaffoldLastTime = now;
        } else if (scaffoldLastCount == -1) {
            scaffoldLastCount = count;
            scaffoldLastTime = now;
            scaffoldBpsValue = 0.0;
        } else {
            long dt = now - scaffoldLastTime;
            if (dt >= 250L) {
                int delta = scaffoldLastCount - count;
                double bpsRaw = delta > 0 ? (delta / (dt / 1000.0)) : 0.0;
                scaffoldBpsValue = MathHelper.lerp(0.35, scaffoldBpsValue, bpsRaw);
                scaffoldLastCount = count;
                scaffoldLastTime = now;
            }
        }
        scaffoldBpsText = String.format("%.1f b/s", scaffoldBpsValue);

        if (scaffoldItemVisible && scaffoldItem.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            int rgb = block.getDefaultMapColor().color;
            Color base = new Color(rgb | 0xFF000000, true);
            scaffoldBlockColor = new Color(base.getRed(), base.getGreen(), base.getBlue(), 180);
        } else {
            scaffoldBlockColor = new Color(255, 255, 255, 120);
        }
        return true;
    }

    private float calculateScaffoldWidth() {
        float countSize = 10f;
        float textSize = 9f;
        float subSize = 7f;
        int countFont = FontLoader.bold();
        int textFont = FontLoader.medium();

        float countW = NanoVGHelper.getTextWidth(scaffoldCountText, countFont, countSize);
        float suffixW = NanoVGHelper.getTextWidth(scaffoldSuffixText, textFont, textSize);
        float bpsW = NanoVGHelper.getTextWidth(scaffoldBpsText, textFont, subSize);

        scaffoldRightTextWidth = Math.max(countW + suffixW, bpsW);

        float padding = 8f;
        float iconBox = 17f;
        float gap1 = 7f;
        float barW = 70f;
        float gap2 = 8f;

        float total = padding + iconBox + gap1 + barW + gap2 + scaffoldRightTextWidth + padding;
        return Math.max(Size.EXPANDED_W, total);
    }

    private float parseScaffoldCount() {
        try {
            return Float.parseFloat(scaffoldCountText);
        } catch (NumberFormatException ignored) {
            return 0f;
        }
    }

    private long ela() {
        return toggleStartTime == -1L ? 0 : System.currentTimeMillis() - toggleStartTime;
    }

    private long elaTab() {
        return tabStartTime == -1L ? 0 : System.currentTimeMillis() - tabStartTime;
    }

    private static float easeOut(float t) {
        return (float) Easing.CUBIC_OUT.ease(t);
    }

    private static int alphaFromProgress(float p) {
        return (int) (255 * p);
    }

    private static Color withAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, Math.min(255, alpha)));
    }

    private record ToggleInfo(String name, boolean enabled) {
    }
}
