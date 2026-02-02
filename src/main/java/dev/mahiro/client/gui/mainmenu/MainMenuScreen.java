package dev.mahiro.client.gui.mainmenu;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.gui.account.AccountSelectorScreen;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.shaders.MainMenuShader;
import dev.mahiro.client.utils.animations.AnimationUtil;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.SmoothStepAnimation;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.mahiro.client.Mahiro.mc;

public class MainMenuScreen extends Screen {
    private static final Color OVERLAY_TINT_1 = new Color(62, 137, 154, 50);
    private static final Color OVERLAY_TINT_2 = new Color(25, 25, 25, 50);
    private static final Color PANEL_GRADIENT_TOP = new Color(15, 32, 39, 255);
    private static final Color PANEL_GRADIENT_BOTTOM = new Color(15, 32, 39, 140);
    private static final Color PANEL_BLUR_COLOR = new Color(15, 32, 39, 0);
    private static final Color PANEL_OUTLINE = new Color(0, 0, 0, 60);
    private static final Color SHADOW_FAR = new Color(0, 0, 0, 90);
    private static final Color SHADOW_NEAR = new Color(0, 0, 0, 160);
    private static final Color ACCENT_GRADIENT_BOTTOM = new Color(55, 59, 68, 140);
    private static final Color TITLE_COLOR = new Color(220, 220, 220, 255);
    private static final Color URL_COLOR = new Color(200, 200, 200, 255);
    private static final Color SEPARATOR_COLOR = new Color(200, 200, 200, 255);
    private static final Color LOVE_COLOR = new Color(255, 255, 255, 100);
    private static final Color DEV_HIGHLIGHT = new Color(206, 206, 226, 255);
    private static final Color WHITE = new Color(255, 255, 255, 255);

    private static final long LOCAL_ENTRANCE_DURATION_MS = 1250L;
    private static final long EXTERNAL_ENTRANCE_DURATION_MS = 1400L;
    private static final long POST_AUTH_INTRO_DURATION_MS = 1500L;

    private final List<MainMenuEntry> entries = new ArrayList<>();
    private final List<SocialLink> socialLinks = new ArrayList<>();
    private long localEntranceStartTime = -1L;
    private float externalEntranceProgress = -1f;
    private float externalEntranceTarget = -1f;
    private long externalEntranceStartTime = -1L;
    private boolean postAuthIntroActive = false;
    private long postAuthIntroStartTime = -1L;
    private boolean suppressFadeOverlay = false;

    public MainMenuScreen() {
        super(Text.of("MainMenuScreen"));
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
    protected void init() {
        ClickGui clickGui = Mahiro.MODULES.getModule(ClickGui.class);
        if (clickGui != null && clickGui.getKey() == -1) {
            mc.setScreen(new WelcomeScreen());
            return;
        }

        long now = Util.getMeasuringTimeMs();
        if (AuthGate.consumeMainMenuIntro()) {
            postAuthIntroActive = true;
            postAuthIntroStartTime = now;
            suppressFadeOverlay = true;
            externalEntranceProgress = -1f;
            externalEntranceTarget = -1f;
            externalEntranceStartTime = -1L;
            localEntranceStartTime = -1L;
            setupEntries();
            setupSocialLinks();
            return;
        } else {
            postAuthIntroActive = false;
            postAuthIntroStartTime = -1L;
            suppressFadeOverlay = false;
        }

        if (externalEntranceTarget >= 0f) {
            localEntranceStartTime = -1L;
        } else {
            localEntranceStartTime = now;
        }
        setupEntries();
        setupSocialLinks();
    }

    private void setupEntries() {
        entries.clear();
        entries.add(new MainMenuEntry("Single Player", "A", () -> mc.setScreen(new SelectWorldScreen(this))));
        entries.add(new MainMenuEntry("Multi Player", "P", () -> mc.setScreen(new MultiplayerScreen(this))));
        entries.add(new MainMenuEntry("Alt Manager", "C", () -> mc.setScreen(new AccountSelectorScreen(this))));
        entries.add(new MainMenuEntry("Options", "D", () -> mc.setScreen(new OptionsScreen(this, mc.options))));
        entries.add(new MainMenuEntry("Shut down", "E", mc::scheduleStop));
    }

    private void setupSocialLinks() {
        socialLinks.clear();
        socialLinks.add(new SocialLink("W", "https://kook.vip/"));
        socialLinks.add(new SocialLink("V", "https://discord.gg/"));
        socialLinks.add(new SocialLink("Y", "https://www.youtube.com/"));
        socialLinks.add(new SocialLink("Z", "https://github.com/L3MonKe"));
    }

    private float getScale() {
        double rawWidth = mc.getWindow().getWidth();
        double rawHeight = mc.getWindow().getHeight();
        double logicalWidth = Math.max(1.0, width);
        double logicalHeight = Math.max(1.0, height);
        double effectiveScaleFactor = Math.min(rawWidth / logicalWidth, rawHeight / logicalHeight);

        float scaleToTargetGui = (float) (2.0 / Math.max(1.0, effectiveScaleFactor));
        float fitToScreen = Math.min(1.0f, Math.min(width / 854f, height / 480f));
        return Math.min(1.0f, scaleToTargetGui * fitToScreen);
    }

    private float refFont(float referenceSize, float scale) {
        return referenceSize * scale * 0.5f;
    }

    private Color applyAlpha(Color color, float alpha) {
        alpha = MathHelper.clamp(alpha, 0f, 1f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(color.getAlpha() * alpha));
    }

    public void setEntranceProgress(float p) {
        float v = MathHelper.clamp(p, 0f, 1f);
        if (externalEntranceTarget < 0f) {
            externalEntranceStartTime = Util.getMeasuringTimeMs();
        }
        this.externalEntranceTarget = v;
    }

    private float resolveEntranceProgress() {
        if (externalEntranceTarget >= 0f) {
            long now = Util.getMeasuringTimeMs();
            if (externalEntranceStartTime <= 0L) {
                externalEntranceStartTime = now;
            }

            float timeGate = MathHelper.clamp((float) (now - externalEntranceStartTime) / (float) EXTERNAL_ENTRANCE_DURATION_MS, 0f, 1f);
            timeGate = AnimationUtil.smoothstep(0.0f, 1.0f, timeGate);

            externalEntranceProgress = Math.min(externalEntranceTarget, timeGate);

            if (externalEntranceTarget >= 0.999f && externalEntranceProgress >= 0.999f) {
                externalEntranceTarget = -1f;
                externalEntranceProgress = -1f;
                externalEntranceStartTime = -1L;
                localEntranceStartTime = now - LOCAL_ENTRANCE_DURATION_MS;
                return 1f;
            }
            return externalEntranceProgress;
        }
        long now = Util.getMeasuringTimeMs();
        if (localEntranceStartTime <= 0L) {
            localEntranceStartTime = now;
        }
        return MathHelper.clamp((float) (now - localEntranceStartTime) / (float) LOCAL_ENTRANCE_DURATION_MS, 0f, 1f);
    }

    private Layout resolveLayout(float scale) {
        float centerX = width / 2f;
        float centerY = height / 2f;
        return new Layout(centerX, centerY, scale);
    }

    private void drawPanelBlur(DrawContext context, Layout layout, float opacity) {
        Shader2DUtil.drawRoundedBlur(
                context.getMatrices(),
                layout.panelX,
                layout.panelY,
                layout.panelW,
                layout.panelH,
                layout.panelR,
                PANEL_BLUR_COLOR,
                10f * layout.scale,
                0.8f * opacity
        );
    }

    private void drawBackgroundTints(Layout layout, float opacity) {
        NanoVGHelper.drawRoundRect(0, 0, width, height, 0, applyAlpha(OVERLAY_TINT_1, opacity));
        NanoVGHelper.drawRoundRect(0, 0, width, height, 0, applyAlpha(OVERLAY_TINT_2, opacity));
    }

    private void drawPanelChrome(Layout layout, float opacity) {
        NanoVGHelper.drawShadow(
                layout.panelX,
                layout.panelY,
                layout.panelW,
                layout.panelH,
                layout.panelR,
                applyAlpha(SHADOW_FAR, opacity),
                42f * layout.scale,
                0f,
                10f * layout.scale
        );

        NanoVGHelper.drawShadow(
                layout.panelX,
                layout.panelY,
                layout.panelW,
                layout.panelH,
                layout.panelR,
                applyAlpha(SHADOW_NEAR, opacity),
                18f * layout.scale,
                0f,
                3f * layout.scale
        );

        NanoVGHelper.drawGradientRRect(
                layout.panelX,
                layout.panelY,
                layout.panelW,
                layout.panelH,
                layout.panelR,
                applyAlpha(PANEL_GRADIENT_TOP, opacity),
                applyAlpha(PANEL_GRADIENT_BOTTOM, opacity)
        );

        NanoVGHelper.drawRoundRectOutline(
                layout.panelX,
                layout.panelY,
                layout.panelW,
                layout.panelH,
                layout.panelR,
                1.25f * layout.scale,
                applyAlpha(PANEL_OUTLINE, opacity)
        );
    }

    private void drawAccentBar(Layout layout, Color accent, float opacity) {
        NanoVGHelper.scissor(layout.scissorX, layout.scissorY, layout.scissorW, layout.scissorH);
        NanoVGHelper.drawGradientRRect(
                layout.panelX,
                layout.accentY,
                layout.panelW,
                layout.accentH,
                layout.panelR,
                applyAlpha(accent, opacity),
                applyAlpha(ACCENT_GRADIENT_BOTTOM, opacity)
        );
        NanoVGHelper.resetScissor();
    }

    private void drawTitleBlock(Layout layout, Color iconColor, float opacity, float socialOpacity) {
        float titleSize = refFont(40f, layout.scale);
        NanoVGHelper.drawString(
                "Minecraft 1.21.4",
                layout.centerX,
                layout.titleY,
                FontLoader.bold(titleSize),
                titleSize,
                NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(TITLE_COLOR, opacity)
        );

        float urlSize = refFont(15f, layout.scale);
        NanoVGHelper.drawString(
                "dev.mahiro",
                layout.centerX,
                layout.urlY,
                FontLoader.bold(urlSize),
                urlSize,
                NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(URL_COLOR, opacity)
        );

        NanoVGHelper.drawRect(layout.centerX - 110f * layout.scale, layout.sepY, 220f * layout.scale, layout.scale, applyAlpha(SEPARATOR_COLOR, opacity));

        float loveSize = refFont(15f, layout.scale);
        NanoVGHelper.drawString(
                "Love By Mahiro#1337",
                layout.centerX,
                layout.loveY,
                FontLoader.bold(loveSize),
                loveSize,
                NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(LOVE_COLOR, opacity)
        );

        renderSocialLinks(layout.centerX, layout.centerY, layout.scale, applyAlpha(iconColor, socialOpacity), socialOpacity);
    }

    private void drawVersion(Layout layout, Color color, float opacity) {
        float logoSize = refFont(80f, layout.scale);
        float versionX = width / 2f + NanoVGHelper.getTextWidth(Mahiro.MOD_NAME, FontLoader.bold(logoSize), logoSize) / 2f - 5f * layout.scale;
        float versionY = 7f * layout.scale + height / 2f - 50f * layout.scale - 100f * layout.scale;
        float versionSize = refFont(15f, layout.scale);
        NanoVGHelper.drawString(
                Mahiro.MOD_VER,
                versionX,
                versionY,
                FontLoader.greycliffSemi(versionSize),
                versionSize,
                NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(color, opacity)
        );
    }

    private void drawFadeOverlay(float opacity) {
        int alpha = Math.round(255 * (1 - opacity));
        NanoVGHelper.drawRoundRect(0, 0, width, height, 0, new Color(0, 0, 0, alpha));
    }

    private void drawLogo(Layout layout, float liftY, float opacity) {
        float logoSize = refFont(80f, layout.scale);
        float aWidth = NanoVGHelper.getTextWidth("M", FontLoader.bold(logoSize), logoSize);
        float baseY = 7f * layout.scale + height / 2f - 50f * layout.scale - liftY;

        float etaWidth = NanoVGHelper.getTextWidth("4h1r0", FontLoader.bold(logoSize), logoSize);
        float zX = width / 2f - aWidth / 2f - etaWidth / 2f;
        float etaX = width / 2f - etaWidth / 2f + aWidth / 2f;

        NanoVGHelper.drawString(
                "M",
                zX,
                baseY,
                FontLoader.bold(logoSize),
                logoSize,
                NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(WHITE, opacity)
        );

        NanoVGHelper.drawString(
                "4h1r0",
                etaX,
                baseY,
                FontLoader.bold(logoSize),
                logoSize,
                NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
                applyAlpha(WHITE, opacity)
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        long now = Util.getMeasuringTimeMs();
        if (postAuthIntroActive) {
            if (postAuthIntroStartTime <= 0L) postAuthIntroStartTime = now;
            float t = MathHelper.clamp((float) (now - postAuthIntroStartTime) / (float) POST_AUTH_INTRO_DURATION_MS, 0f, 1f);
            float shaderT = AnimationUtil.smoothstep(0.0f, 1.0f, t);
            MainMenuShader.getSharedInstance().render(this.width, this.height, shaderT);
            if (t < 0.999f) return;
            postAuthIntroActive = false;
            postAuthIntroStartTime = -1L;
            localEntranceStartTime = now;
        } else {
            MainMenuShader.getSharedInstance().render(this.width, this.height, 1.0f);
        }

        float p = resolveEntranceProgress();
        if (suppressFadeOverlay && p >= 0.999f) {
            suppressFadeOverlay = false;
        }

        float scale = getScale();
        Layout layout = resolveLayout(scale);

        float panelP = AnimationUtil.smoothstep(0.15f, 0.55f, p);
        drawPanelBlur(context, layout, panelP);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color accent = ClickGui.color(0);
            Color white = WHITE;

            float overlayAlpha = AnimationUtil.smoothstep(0.0f, 0.25f, p);
            drawBackgroundTints(layout, overlayAlpha);

            float panelEase = AnimationUtil.easeOutCubic(panelP);
            float panelOffsetY = (1f - panelEase) * (14f * layout.scale);
            float panelScale = MathHelper.lerp(panelEase, 0.985f, 1.0f);

            float titleAlpha = AnimationUtil.smoothstep(0.35f, 0.75f, p);
            float socialAlpha = AnimationUtil.smoothstep(0.60f, 1.0f, p);
            float versionAlpha = AnimationUtil.smoothstep(0.25f, 0.65f, p);
            float devAlpha = AnimationUtil.smoothstep(0.45f, 0.95f, p);

            NanoVGHelper.save();
            NanoVGHelper.translate(vg, 0f, panelOffsetY);
            NanoVGHelper.translate(vg, layout.centerX, layout.centerY);
            NanoVGHelper.scale(vg, panelScale, panelScale);
            NanoVGHelper.translate(vg, -layout.centerX, -layout.centerY);

            drawPanelChrome(layout, panelP);
            drawAccentBar(layout, accent, panelP);
            drawTitleBlock(layout, white, titleAlpha, socialAlpha);
            drawVersion(layout, white, versionAlpha);

            renderDevelopmentInfo(layout.centerX, layout.centerY, layout.scale, devAlpha);
            renderEntries(mouseX, mouseY, layout.centerX, layout.centerY, layout.scale, accent, p);

            NanoVGHelper.restore();

            boolean fromSplash = externalEntranceTarget >= 0f || externalEntranceProgress >= 0f;
            if (!fromSplash && !suppressFadeOverlay) {
                drawFadeOverlay(p);
            }

            float logoP = AnimationUtil.smoothstep(0.05f, 0.45f, p);
            float logoLift = AnimationUtil.easeOutCubic(logoP) * (95f * layout.scale);
            float logoAlpha = AnimationUtil.smoothstep(0.10f, 0.45f, p);
            drawLogo(layout, logoLift, logoAlpha);
        });
    }

    private void renderSocialLinks(float centerX, float centerY, float scale, Color color, float opacity) {
        if (opacity <= 0.01f || socialLinks.size() < 4) {
            for (SocialLink link : socialLinks) {
                link.setBounds(0f, 0f, 0f, 0f);
            }
            return;
        }
        float iconSize = refFont(60f, scale);
        float hitW = 25f * scale;
        float hitH = 25f * scale;
        float drawYOffset = -3f * scale;

        float wDrawX = centerX + 53f * scale;
        float wHitY = centerY + 16f * scale;
        socialLinks.getFirst().setBounds(wDrawX, wHitY, hitW, hitH);
        NanoVGHelper.drawString(socialLinks.getFirst().glyph, wDrawX, wHitY + drawYOffset, FontLoader.newIc(iconSize), iconSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, color);

        float vDrawX = centerX + (55f + 40f) * scale;
        float vDrawY = centerY + 15f * scale;
        float vHitX = centerX + (55f + 43f) * scale;
        float vHitY = vDrawY;
        socialLinks.get(1).setBounds(vHitX, vHitY, hitW, hitH);
        NanoVGHelper.drawString(socialLinks.get(1).glyph, vDrawX, vDrawY + drawYOffset, FontLoader.newIc(iconSize), iconSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, color);

        float yDrawX = centerX + 50f * scale;
        float yHitY = centerY + 50f * scale;
        socialLinks.get(2).setBounds(yDrawX, yHitY, hitW, hitH);
        NanoVGHelper.drawString(socialLinks.get(2).glyph, yDrawX, yHitY + drawYOffset, FontLoader.newIc(iconSize), iconSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, color);

        float zDrawX = centerX + (55f + 43f) * scale;
        float zDrawY = yHitY;
        float zHitX = centerX + (55f + 40f) * scale;
        float zHitY = zDrawY;
        socialLinks.get(3).setBounds(zHitX, zHitY, hitW, hitH);
        NanoVGHelper.drawString(socialLinks.get(3).glyph, zDrawX, zDrawY + drawYOffset, FontLoader.newIc(iconSize), iconSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, color);
    }

    private void renderDevelopmentInfo(float centerX, float centerY, float scale, float opacity) {
        if (opacity <= 0.01f) return;
        float offsetX = (1f - AnimationUtil.easeOutCubic(opacity)) * (10f * scale);

        NanoVGHelper.save();
        NanoVGHelper.translate(offsetX, 0f);

        float lineX = centerX + 130f * scale - 15f * scale;
        float topY = centerY - 100f * scale + 15.5f * scale;
        NanoVGHelper.drawRect(lineX, topY, scale, 78f * scale, applyAlpha(WHITE, opacity));

        List<String> lines = Arrays.asList(
                "LemonClientDevelopment",
                "Minecraft 1.21.4",
                "Changelog :",
                "* 你知道吗",
                "* 这是第一个版本",
                "* 可是大部分外挂都打不过这个外挂",
                "* 包括Zen"
        );

        float fontSize = refFont(15f, scale);
        float rightEdge = centerX + 130f * scale - 18f * scale;

        for (int i = 0; i < lines.size(); i++) {
            String text = lines.get(i);
            Color c = i >= 3 ? applyAlpha(DEV_HIGHLIGHT, opacity) : applyAlpha(WHITE, opacity);
            float y = centerY - 100f * scale + (17.5f + i * 10f) * scale;
            NanoVGHelper.drawString(
                    text,
                    rightEdge,
                    y,
                    FontLoader.bold(fontSize),
                    fontSize,
                    NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_TOP,
                    c
            );
        }

        NanoVGHelper.restore();
    }

    private void renderEntries(int mouseX, int mouseY, float centerX, float centerY, float scale, Color accent, float entranceProgress) {
        float x = centerX - 110f * scale;
        float yStart = centerY - 100f * scale + 17.5f * scale;
        float entryW = 160f * scale;
        float entryH = 35f * scale;
        float stepY = 35f * scale;
        Color entryColor = applyAlpha(accent, 1.0f);
        Color hoverColor = ColorUtil.darker(ColorUtil.darker(entryColor, 0.7f), 0.7f);

        for (int i = 0; i < entries.size(); i++) {
            MainMenuEntry entry = entries.get(i);
            float y = yStart + i * stepY;

            float itemP = (entranceProgress - 0.35f - i * 0.06f) / 0.45f;
            itemP = MathHelper.clamp(itemP, 0f, 1f);
            itemP = AnimationUtil.smoothstep(0.0f, 1.0f, itemP);

            float itemEase = AnimationUtil.easeOutCubic(itemP);
            float itemOffsetX = (1f - itemEase) * (-10f * scale);
            float drawX = x + itemOffsetX;
            float itemAlpha = itemP;

            entry.setBounds(drawX - 80f * scale, y - 15f * scale, entryW, entryH);

            float fontSize = refFont(20f, scale);
            float textWidth = NanoVGHelper.getTextWidth(entry.label, FontLoader.bold(fontSize), fontSize);
            boolean hovered = mouseX >= (drawX - 10f * scale) && mouseX <= (drawX - 10f * scale + textWidth + 30f * scale)
                    && mouseY >= (y - 10f * scale) && mouseY <= (y - 10f * scale + 28f * scale);

            entry.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
            float expand = (textWidth + 30f * scale) * entry.hoverAnimation.getOutput().floatValue();
            NanoVGHelper.drawRoundRect(drawX - 10f * scale, y - 10f * scale, expand, 28f * scale, 6f * scale, applyAlpha(hoverColor, itemAlpha));

            float rowCenterY = y + 4f * scale;
            NanoVGHelper.drawRect(drawX - 5f * scale, rowCenterY - 4f * scale, scale, 8f * scale, applyAlpha(WHITE, itemAlpha));
            NanoVGHelper.drawString(entry.label, drawX + 15f * scale, rowCenterY + scale, FontLoader.bold(fontSize), fontSize,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, applyAlpha(entryColor, itemAlpha));

            float iconSize = refFont(30f, scale);
            NanoVGHelper.drawString(entry.iconGlyph, drawX, rowCenterY, FontLoader.newIc(iconSize), iconSize,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, applyAlpha(entryColor, itemAlpha));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (SocialLink link : socialLinks) {
                if (link.isHovered((float) mouseX, (float) mouseY)) {
                    openLink(link.url);
                    return true;
                }
            }

            for (MainMenuEntry entry : entries) {
                if (entry.isHovered((float) mouseX, (float) mouseY)) {
                    entry.action.run();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void removed() {
        super.removed();
    }

    private void openLink(String url) {
        try {
            if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception ignored) {
        }
    }

    private static final class SocialLink {
        private final String glyph;
        private final String url;
        private float x;
        private float y;
        private float w;
        private float h;

        private SocialLink(String glyph, String url) {
            this.glyph = glyph;
            this.url = url;
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

    private static final class MainMenuEntry {
        private static final int HOVER_ANIM_MS = 250;
        private final String label;
        private final String iconGlyph;
        private final Runnable action;
        private final SmoothStepAnimation hoverAnimation = new SmoothStepAnimation(HOVER_ANIM_MS, 1);
        private float x;
        private float y;
        private float w;
        private float h;

        private MainMenuEntry(String label, String iconGlyph, Runnable action) {
            this.label = label;
            this.iconGlyph = iconGlyph;
            this.action = action;
            this.hoverAnimation.setDirection(Direction.BACKWARDS);
            this.hoverAnimation.timerUtil.setTime(this.hoverAnimation.timerUtil.getCurrentMS() - HOVER_ANIM_MS);
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

    private static final class Layout {
        private final float centerX;
        private final float centerY;
        private final float scale;
        private final float panelX;
        private final float panelY;
        private final float panelW;
        private final float panelH;
        private final float panelR;
        private final float scissorX;
        private final float scissorY;
        private final float scissorW;
        private final float scissorH;
        private final float accentY;
        private final float accentH;
        private final float titleY;
        private final float urlY;
        private final float sepY;
        private final float loveY;

        private Layout(float centerX, float centerY, float scale) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.scale = scale;

            this.panelX = centerX - 130f * scale;
            this.panelY = centerY - 150f * scale;
            this.panelW = 260f * scale;
            this.panelH = 300f * scale;
            this.panelR = 14f * scale;

            this.scissorX = centerX - 135f * scale;
            this.scissorY = centerY + 80f * scale;
            this.scissorW = 270f * scale;
            this.scissorH = 80f * scale;

            this.accentY = centerY + 50f * scale;
            this.accentH = 100f * scale;

            this.titleY = centerY + 90f * scale;
            this.urlY = centerY + 112.5f * scale;
            this.sepY = centerY + 125f * scale;
            this.loveY = centerY + 135f * scale;
        }
    }


}
