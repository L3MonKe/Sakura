package dev.sakura.client.gui.mainmenu;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.MainMenuShader;
import dev.sakura.client.utils.animations.AnimationUtil;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.SmoothStepAnimation;
import dev.sakura.client.utils.color.ColorUtil;
import net.minecraft.client.gui.Click;
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
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class MainMenuScreen extends Screen {
    private static final Color SIDEBAR_BG_TOP = new Color(20, 10, 30, 100);
    private static final Color SIDEBAR_BG_BOTTOM = new Color(20, 10, 30, 50);
    private static final Color SIDEBAR_BLUR = new Color(20, 10, 30, 0);
    private static final Color SIDEBAR_BORDER = new Color(255, 200, 220, 30);

    private static final Color BUTTON_NORMAL = new Color(255, 255, 255, 10);
    private static final Color BUTTON_HOVER = new Color(255, 255, 255, 40);
    private static final Color BUTTON_TEXT = new Color(255, 255, 255, 220);
    private static final Color BUTTON_TEXT_HOVER = new Color(255, 255, 255, 255);

    private static final Color TITLE_COLOR = new Color(255, 240, 245, 255);
    private static final Color ACCENT_COLOR = new Color(255, 150, 180, 255);
    private static final Color WHITE = new Color(255, 255, 255, 255);

    private static final List<String> CHANGE_LOGS = List.of(
            "LemonClientDevelopment",
            "Minecraft 1.21.11",
            "Changelog :",
            "* 你知道吗",
            "* 这是第一个版本",
            "* 可是大部分外挂都打不过这个外挂",
            "* 包括Zen"
    );

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
    private boolean introEverStarted = false;

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
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    protected void init() {
        ClickGui clickGui = Sakura.MODULES.getModule(ClickGui.class);
        if (clickGui != null && clickGui.getKey() == -1) {
            mc.setScreen(new WelcomeScreen());
            return;
        }

        long now = Util.getMeasuringTimeMs();

        if (!postAuthIntroActive) {
            postAuthIntroStartTime = -1L;
            suppressFadeOverlay = false;
        } else if (postAuthIntroStartTime <= 0L) {
            postAuthIntroStartTime = now;
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
        entries.add(new MainMenuEntry("Alt Manager", "C", null));
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
            if (!postAuthIntroActive) {
                externalEntranceStartTime = Util.getMeasuringTimeMs();
            } else {
                externalEntranceStartTime = -1L;
            }
        }
        this.externalEntranceTarget = v;
    }

    public void startIntro() {
        MainMenuShader.getSharedInstance().switchShaderType(MainMenuShader.MainMenuShaderType.SAKURA);
        long now = Util.getMeasuringTimeMs();
        postAuthIntroActive = true;
        postAuthIntroStartTime = now;
        suppressFadeOverlay = true;
        introEverStarted = true;
        localEntranceStartTime = -1L;
        externalEntranceStartTime = -1L;
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
        float sidebarW = 300f * scale;
        return new Layout(sidebarW, height, scale);
    }

    private void drawSidebar(Layout layout, float opacity) {
        // Gradient Background
        NanoVGHelper.drawGradientRRect(0, 0, layout.width, layout.height, 0,
                applyAlpha(SIDEBAR_BG_TOP, opacity), applyAlpha(SIDEBAR_BG_BOTTOM, opacity));

        // Right Border
        NanoVGHelper.drawRect(layout.width - 1, 0, 1, layout.height, applyAlpha(SIDEBAR_BORDER, opacity));
    }

    private void drawTitle(Layout layout, float opacity) {
        float titleSize = refFont(60f, layout.scale);
        float subSize = refFont(16f, layout.scale);

        float startX = 40f * layout.scale;
        float startY = 80f * layout.scale;

        // "Sakura" Title
        NanoVGHelper.drawString("Sakura", startX, startY, FontLoader.bold(), titleSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BOTTOM, applyAlpha(TITLE_COLOR, opacity));

        // "Client" Subtitle
        NanoVGHelper.drawString("Client", startX + NanoVGHelper.getTextWidth("Sakura", FontLoader.bold(), titleSize) + 10f * layout.scale, startY - 8f * layout.scale, FontLoader.regular(), subSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BOTTOM, applyAlpha(ACCENT_COLOR, opacity * 0.8f));

        // Separator
        NanoVGHelper.drawRect(startX, startY + 10f * layout.scale, 60f * layout.scale, 2f * layout.scale, applyAlpha(ACCENT_COLOR, opacity * 0.6f));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        long now = Util.getMeasuringTimeMs();

        // Update Shader Mouse
        MainMenuShader.getSharedInstance().setMouse((float) mouseX / width, 1.0f - (float) mouseY / height);

        if (postAuthIntroActive) {
            if (postAuthIntroStartTime <= 0L) postAuthIntroStartTime = now;
            float t = MathHelper.clamp((float) (now - postAuthIntroStartTime) / (float) POST_AUTH_INTRO_DURATION_MS, 0f, 1f);
            float shaderT = AnimationUtil.smoothstep(0.0f, 1.0f, t);
            MainMenuShader.getSharedInstance().render(this.width, this.height, shaderT);
            if (t < 0.999f) return;
            postAuthIntroActive = false;
            postAuthIntroStartTime = -1L;
            localEntranceStartTime = now;
        }

        boolean fromSplash = externalEntranceTarget >= 0f || externalEntranceProgress >= 0f;
        float p = resolveEntranceProgress();
        MainMenuShader.getSharedInstance().render(this.width, this.height, fromSplash && !introEverStarted ? AnimationUtil.smoothstep(0.0f, 1.0f, p) : 1.0f);
        if (suppressFadeOverlay && p >= 0.999f) {
            suppressFadeOverlay = false;
        }

        float scale = getScale();
        Layout layout = resolveLayout(scale);

        float contentP = AnimationUtil.smoothstep(0.1f, 1.0f, p);

        // Draw Sidebar Blur (Outside NanoVG context to prevent flickering/state corruption)
        if (contentP > 0.02f) {
            BlurShader.drawQuadBlur(0, 0, layout.width, layout.height, 15f * layout.scale * contentP);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            drawSidebar(layout, contentP);
            drawTitle(layout, contentP);

            float entriesP = AnimationUtil.smoothstep(0.3f, 1.0f, p);
            renderEntries(mouseX, mouseY, layout, entriesP);

            float socialP = AnimationUtil.smoothstep(0.5f, 1.0f, p);
            renderSocialLinks(layout, socialP);

            // Draw Version Info bottom right (outside sidebar)
            float versionAlpha = AnimationUtil.smoothstep(0.8f, 1.0f, p);
            if (versionAlpha > 0.01f) {
                String ver = "Sakura Client " + Sakura.MOD_VER;
                float verSize = refFont(14f, scale);
                NanoVGHelper.drawString(ver, width - 20f * scale, height - 20f * scale, FontLoader.regular(), verSize, NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_BOTTOM, applyAlpha(WHITE, versionAlpha * 0.5f));
            }
        });


    }

    private void renderSocialLinks(Layout layout, float opacity) {
        if (opacity <= 0.01f || socialLinks.isEmpty()) return;

        float iconSize = refFont(24f, layout.scale);
        float spacing = 40f * layout.scale;
        float startX = 40f * layout.scale;
        float startY = layout.height - 60f * layout.scale;

        for (int i = 0; i < socialLinks.size(); i++) {
            SocialLink link = socialLinks.get(i);
            float x = startX + i * spacing;
            float y = startY;
            float size = 30f * layout.scale;

            link.setBounds(x, y, size, size);

            float mX = (float) (mc.mouse.getX() * mc.getWindow().getScaledWidth() / mc.getWindow().getWidth());
            float mY = (float) (mc.mouse.getY() * mc.getWindow().getScaledHeight() / mc.getWindow().getHeight());
            boolean hovered = link.isHovered(mX, mY);

            Color color = hovered ? ACCENT_COLOR : BUTTON_TEXT;
            NanoVGHelper.drawString(link.glyph, x + size / 2f, y + size / 2f, FontLoader.newIc(), iconSize, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, applyAlpha(color, opacity));
        }
    }


    private void renderEntries(int mouseX, int mouseY, Layout layout, float entranceProgress) {
        float startX = 20f * layout.scale;
        float startY = 180f * layout.scale;
        float buttonW = layout.width - 40f * layout.scale;
        float buttonH = 45f * layout.scale;
        float spacing = 10f * layout.scale;

        for (int i = 0; i < entries.size(); i++) {
            MainMenuEntry entry = entries.get(i);
            float y = startY + i * (buttonH + spacing);

            // Entrance Animation (Slide in from left)
            float itemP = (entranceProgress - 0.2f - i * 0.05f) / 0.5f;
            itemP = MathHelper.clamp(itemP, 0f, 1f);
            itemP = AnimationUtil.easeOutCubic(itemP);

            float alpha = itemP;
            float offsetX = (1f - itemP) * (-50f * layout.scale);

            float drawX = startX + offsetX;

            entry.setBounds(drawX, y, buttonW, buttonH);

            boolean hovered = mouseX >= drawX && mouseX <= drawX + buttonW && mouseY >= y && mouseY <= y + buttonH;
            entry.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
            float hoverP = entry.hoverAnimation.getOutput().floatValue();

            // Glass Button Background
            Color bgColor = ColorUtil.interpolateColorC(BUTTON_NORMAL, BUTTON_HOVER, hoverP);
            NanoVGHelper.drawRoundRect(drawX, y, buttonW, buttonH, 8f * layout.scale, applyAlpha(bgColor, alpha));

            // Hover Glow/Border
            if (hoverP > 0.01f) {
                Color borderColor = applyAlpha(ACCENT_COLOR, hoverP * 0.5f * alpha);
                NanoVGHelper.drawRoundRectOutline(drawX, y, buttonW, buttonH, 8f * layout.scale, 1.5f, borderColor);

                // Left Accent Bar
                NanoVGHelper.drawRoundRect(drawX, y + 8f * layout.scale, 3f * layout.scale, buttonH - 16f * layout.scale, 1.5f, applyAlpha(ACCENT_COLOR, hoverP * alpha));
            }

            // Text & Icon
            float contentOffsetX = 20f * layout.scale + (hoverP * 5f * layout.scale); // Slide text slightly on hover
            float fontSize = refFont(22f, layout.scale);

            Color textColor = ColorUtil.interpolateColorC(BUTTON_TEXT, BUTTON_TEXT_HOVER, hoverP);

            // Icon
            NanoVGHelper.drawString(entry.iconGlyph, drawX + contentOffsetX, y + buttonH / 2f, FontLoader.newIc(), fontSize * 1.2f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, applyAlpha(textColor, alpha));

            // Text
            NanoVGHelper.drawString(entry.label, drawX + contentOffsetX + 35f * layout.scale, y + buttonH / 2f + 1f, FontLoader.bold(), fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, applyAlpha(textColor, alpha));
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == 0) {
            for (SocialLink link : socialLinks) {
                if (link.isHovered((float) click.x(), (float) click.y())) {
                    openLink(link.url);
                    return true;
                }
            }

            for (MainMenuEntry entry : entries) {
                if (entry.isHovered((float) click.x(), (float) click.y())) {
                    entry.action.run();
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
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
        private final float width;
        private final float height;
        private final float scale;

        private Layout(float width, float height, float scale) {
            this.width = width;
            this.height = height;
            this.scale = scale;
        }
    }


}
