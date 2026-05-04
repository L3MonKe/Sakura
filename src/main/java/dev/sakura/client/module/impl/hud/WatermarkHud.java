package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import dev.sakura.verify.AuthState;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class WatermarkHud extends HudModule {

    public enum ListMode {
        Normal,
        Sakura
    }

    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Sakura);
    private final NumberValue<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);

    // Shared Settings
    private final BoolValue enableParticles = new BoolValue("Enable Particles", "启用粒子", true);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 1.0, 0.1, 5.0, 0.1);
    private final NumberValue<Integer> particleCount = new NumberValue<>("Particle Count", "粒子数量", 10, 0, 50, 1, enableParticles::get);
    private final NumberValue<Double> particleSize = new NumberValue<>("Particle Size", "粒子大小", 2.0, 1.0, 5.0, 0.1, enableParticles::get);
    private final NumberValue<Double> particleSpeed = new NumberValue<>("Particle Speed", "粒子速度", 1.0, 0.1, 3.0, 0.1, enableParticles::get);

    // Normal Mode Settings
    private final BoolValue normalShowIcon = new BoolValue("ShowIcon", "显示图标", true, () -> mode.is(ListMode.Normal));
    private final BoolValue normalRainbowColor = new BoolValue("RainbowColor", "彩虹色", false, () -> mode.is(ListMode.Normal));
    private final BoolValue normalTextGlow = new BoolValue("TextGlow", "文本发光", true, () -> mode.is(ListMode.Normal));
    private final NumberValue<Double> normalGlowRadius = new NumberValue<>("GlowRadius", "发光半径", 3.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Normal) && normalTextGlow.get());
    private final NumberValue<Integer> normalGlowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Normal) && normalTextGlow.get());

    

    

    public enum XylitolSakuraFontMode {
        Regular,
        Medium,
        Semi,
        Bold,
        Comfortaa,
        Ax,
        Geologica,
        Material,
        Tenacity,
        Cjk
    }

    private static final String XYLITOL_MAIN_TEXT = "Sakura";
    private final NumberValue<Double> xylitolMainFontSize = new NumberValue<>("MainSize", "主文字大小", 14.0, 8.0, 32.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolInfoFontSize = new NumberValue<>("InfoSize", "信息文字大小", 10.0, 6.0, 24.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final EnumValue<XylitolSakuraFontMode> xylitolSakuraFontMode = new EnumValue<>("Font", "字体", XylitolSakuraFontMode.Bold, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolSakuraTextOffsetY = new NumberValue<>("OffsetY", "文字Y偏移", -1.0, -30.0, 30.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final ColorValue xylitolSakuraColor1 = new ColorValue("Color1", "渐变色1", new Color(255, 192, 203), () -> mode.is(ListMode.Sakura));
    private final ColorValue xylitolSakuraColor2 = new ColorValue("Color2", "渐变色2", new Color(255, 105, 180), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> animationSpeed = new NumberValue<>("AnimationSpeed", "动画速度", 0.6, 0.0, 5.0, 0.05, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Integer> xylitolSakuraGradientSpread = new NumberValue<>("GradSpread", "颜色跨度", 15, 1, 400, 1, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Integer> xylitolSakuraBlockDistance = new NumberValue<>("BlockDistance", "色块距离", 100, 1, 100, 1, () -> mode.is(ListMode.Sakura));
    private final BoolValue xylitolSakuraGlow = new BoolValue("Glow", "发光", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolSakuraGlowRadius = new NumberValue<>("GlowRadius", "发光半径", 5.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura) && xylitolSakuraGlow.get());
    private final NumberValue<Integer> xylitolSakuraGlowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Sakura) && xylitolSakuraGlow.get());
    private final NumberValue<Double> xylitolPaddingX = new NumberValue<>("PaddingX", "宽(间距)", 6.0, 0.0, 30.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolPaddingY = new NumberValue<>("PaddingY", "高(间距)", 4.0, 0.0, 30.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolGap = new NumberValue<>("Gap", "文字间距", 2.0, 0.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final BoolValue xylitolBlur = new BoolValue("Blur", "背景模糊", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolBlurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura) && xylitolBlur.get());
    private final BoolValue xylitolShadow = new BoolValue("Shadow", "阴影", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> xylitolShadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 1.0, () -> mode.is(ListMode.Sakura) && xylitolShadow.get());
    private final NumberValue<Double> xylitolShadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.05, () -> mode.is(ListMode.Sakura) && xylitolShadow.get());
    private final BoolValue sakuraTextShadow = new BoolValue("TextShadow", "文本阴影", false, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraTextShadowDistance = new NumberValue<>("TextShadowDist", "阴影间距", 1.0, 0.0, 5.0, 0.1, () -> mode.is(ListMode.Sakura) && sakuraTextShadow.get());

    private final BoolValue sakuraSimpleMode = new BoolValue("SimpleMode", "简化模式", false, () -> mode.is(ListMode.Sakura));
    private final BoolValue sakuraShowVersion = new BoolValue("ShowVersion", "显示版本", true, () -> mode.is(ListMode.Sakura) && sakuraSimpleMode.get());
    private final NumberValue<Double> sakuraVersionSize = new NumberValue<>("VersionSize", "版本大小", 10.0, 6.0, 24.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraSimpleMode.get() && sakuraShowVersion.get());
    private final NumberValue<Double> sakuraVersionGap = new NumberValue<>("VersionGap", "版本间距", 2.0, 0.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraSimpleMode.get() && sakuraShowVersion.get());
    private final NumberValue<Double> sakuraVersionOffsetY = new NumberValue<>("VersionOffsetY", "版本Y偏移", 0.0, -10.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraSimpleMode.get() && sakuraShowVersion.get());
    private final ColorValue sakuraVersionColor = new ColorValue("VersionColor", "版本颜色", new Color(255, 255, 255, 255), () -> mode.is(ListMode.Sakura) && sakuraSimpleMode.get() && sakuraShowVersion.get());

    public enum XylitolShadowMode {Solid, Gradient}

    private final EnumValue<XylitolShadowMode> xylitolShadowMode = new EnumValue<>("ShadowMode", "阴影模式", XylitolShadowMode.Solid, () -> mode.is(ListMode.Sakura) && xylitolShadow.get());
    private final ColorValue xylitolBackgroundColor = new ColorValue("BgColor", "背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Sakura));

    private int iconImage = -1;
    private float rotationAngle = 0.0f;
    private long lastUpdateTime = 0;
    private final List<Particle> particles = new ArrayList<>();

    public WatermarkHud() {
        super("Watermark", "水印", 10, 10);
        this.lastUpdateTime = System.currentTimeMillis();
        setInitialState(true);
    }

    @Override
    public void onRender(DrawContext context) {
        update();
        if (mode.is(ListMode.Sakura) && xylitolBlur.get()) {
            renderXylitolBlur();
        }
        if (mode.is(ListMode.Sakura) && xylitolShadow.get()) {
            renderXylitolShadow();
        }
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
    }

    private void update() {
        if (mode.is(ListMode.Normal)) {
            updateRotation();
            updateParticles();
            if (iconImage == -1) {
                if (normalShowIcon.get()) {
                    loadIcon();
                }
            }
        }
    }

    private void updateRotation() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        rotationAngle += (deltaTime * 0.05f * rotationSpeed.get().floatValue()) % 360.0f;
        if (rotationAngle >= 360.0f) {
            rotationAngle -= 360.0f;
        }
    }

    private void updateParticles() {
        if (enableParticles.get()) {
            particles.removeIf(particle -> !particle.isAlive());

            boolean showIcon = normalShowIcon.get();
            if (particles.size() < particleCount.get()) {
                if (showIcon && iconImage != -1) {
                    float s = hudScale.get().floatValue();
                    float fontSize = 30 * s;
                    // Icon size logic from ModuleListHud adjusted for Watermark size
                    // Watermark uses larger font (30), let's make icon proportional
                    // Match font size roughly

                    float iconX = x + (2.5f * s); // Starting X
                    float iconY = y + (2 * s); // Move down slightly to match render position

                    for (int i = particles.size(); i < particleCount.get(); i++) {
                        float angle = (float) (Math.random() * Math.PI * 2);
                        float distance = (float) (Math.random() * fontSize * 0.8f);
                        float particleX = iconX + fontSize / 2 + (float) Math.cos(angle) * distance;
                        float particleY = iconY + fontSize / 2 + (float) Math.sin(angle) * distance;

                        Particle newParticle = new Particle(particleX, particleY);
                        newParticle.size = particleSize.get().floatValue();
                        float speed = particleSpeed.get().floatValue();
                        double particleAngle = Math.random() * Math.PI * 2;
                        newParticle.velocityX = (float) (Math.cos(particleAngle) * speed);
                        newParticle.velocityY = (float) (Math.sin(particleAngle) * speed);
                        particles.add(newParticle);
                    }
                }
            }

            for (Particle particle : particles) {
                particle.update();
            }
        }
    }

    private void renderContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float s = hudScale.get().floatValue();

        if (mode.is(ListMode.Sakura)) {
            renderNewSakura(vg, s);
            return;
        }

        String text = Sakura.MOD_NAME + " " + Sakura.MOD_VER;
        float fontSize = 30 * s;
        int font = FontLoader.bold();
        float fontW = NanoVGHelper.getTextWidth(text, font, fontSize);
        float fontH = NanoVGHelper.getFontHeight(font, fontSize);

        float currentX = x;
        float currentY = y;
        float contentHeight = fontH; // Approximation

        // Icon Logic
        boolean showIcon = normalShowIcon.get();
        // Icon matches text height
        float iconGap = 5 * s;

        if (showIcon && iconImage != -1) {
            float iconX = currentX;
            float iconY = currentY + (contentHeight - fontSize) / 2 + (2 * s); // Center vertically and move down slightly

            // Draw Icon Particles
            if (enableParticles.get()) {
                renderParticles(vg);
            }

            // Draw Icon
            float centerX = iconX + fontSize / 2;
            float centerY = iconY + fontSize / 2;

            nvgSave(vg);
            nvgTranslate(vg, centerX, centerY);
            nvgRotate(vg, (float) Math.toRadians(rotationAngle));
            nvgTranslate(vg, -fontSize / 2, -fontSize / 2);

            NVGPaint paint = NVGPaint.create();
            nvgImagePattern(vg, 0, 0, fontSize, fontSize, 0, iconImage, 1.0f, paint);
            nvgBeginPath(vg);
            nvgRect(vg, 0, 0, fontSize, fontSize);
            nvgFillPaint(vg, paint);
            nvgFill(vg);

            nvgRestore(vg);

            currentX += fontSize + iconGap;
        }

        // Text Logic
        float textY = currentY + fontH; // Baseline

        Color color = normalRainbowColor.get() ? ClickGui.color(0) : Color.WHITE;
        if (normalTextGlow.get()) {
            NanoVGHelper.drawGlowingString(text, currentX, textY, font, fontSize, color, normalGlowRadius.get().floatValue() * s, normalGlowIntensity.get());
        } else {
            NanoVGHelper.drawString(text, currentX, textY, font, fontSize, color);
        }

        // Update width/height for drag handling
        this.width = (currentX - x) + fontW; // Correct width calc
        if (!showIcon) this.width = fontW;
        else this.width = fontSize + iconGap + fontW;

        this.height = fontH;
    }

    private void renderXylitolBlur() {
        float s = hudScale.get().floatValue();
        XylitolMetrics m = calculateXylitolMetrics(s);
        BlurShader.drawRoundedBlur(m.bgX, m.bgY, m.bgW, m.bgH, m.radius * s, xylitolBlurStrength.get().floatValue());
    }

    private void renderNewSakura(long vg, float s) {
        // 简化模式：只显示 Sakura + 版本号
        if (sakuraSimpleMode.get()) {
            renderSakuraSimple(vg, s);
            return;
        }

        XylitolMetrics m = calculateXylitolMetrics(s);

        double offsetDeg = (System.currentTimeMillis() / 20.0) * animationSpeed.get();
        int colorStepDeg = xylitolSakuraGradientSpread.get();
        float blockW = Math.max(1.0f, xylitolSakuraBlockDistance.get() * s);

        NanoVGHelper.drawRoundRect(m.bgX, m.bgY, m.bgW, m.bgH, m.radius * s, xylitolBackgroundColor.get());

        String displayName = XYLITOL_MAIN_TEXT;
        if (displayName == null) displayName = "";

        int mainFont = getXylitolSakuraFont();
        int infoFont = FontLoader.regular();
        float mainSize = xylitolMainFontSize.get().floatValue() * s;
        float infoSize = xylitolInfoFontSize.get().floatValue() * s;

        float mainH = NanoVGHelper.getFontHeight(mainFont, mainSize);
        float infoH = NanoVGHelper.getFontHeight(infoFont, infoSize);
        float maxH = Math.max(mainH, infoH);

        float mainBaseY = m.bgY + m.padY + (maxH - mainH) / 2f + mainH + (xylitolSakuraTextOffsetY.get().floatValue() * s);
        float infoBaseY = m.bgY + m.padY + (maxH - infoH) / 2f + infoH;

        float mainX = m.bgX + m.padX;
        float textW = NanoVGHelper.getTextWidth(displayName, mainFont, mainSize);
        if (textW <= 0.0f) {
            textW = Math.max(1.0f, NanoVGHelper.getTextWidth(XYLITOL_MAIN_TEXT, mainFont, mainSize));
        }

        if (xylitolSakuraGlow.get()) {
            int glowIndex = (int) Math.max(0, Math.floor((textW * 0.5f) / blockW));
            Color glowC = getXylitolSakuraStepColor(offsetDeg + (double) glowIndex * colorStepDeg);
            glowC = new Color(glowC.getRed(), glowC.getGreen(), glowC.getBlue(), 220);
            NanoVGHelper.drawGlowingString(displayName, mainX, mainBaseY, mainFont, mainSize, glowC, xylitolSakuraGlowRadius.get().floatValue() * s, xylitolSakuraGlowIntensity.get());
        }

        if (sakuraTextShadow.get()) {
            Color c1 = xylitolSakuraColor1.get();
            Color c2 = xylitolSakuraColor2.get();
            Color avgColor = new Color((c1.getRed() + c2.getRed()) / 2, (c1.getGreen() + c2.getGreen()) / 2, (c1.getBlue() + c2.getBlue()) / 2);
            Color coloredShadow = new Color((int) (avgColor.getRed() * 0.5), (int) (avgColor.getGreen() * 0.5), (int) (avgColor.getBlue() * 0.5), 255);

            float dist = sakuraTextShadowDistance.get().floatValue() * s;
            NanoVGHelper.drawString(displayName, mainX + dist, mainBaseY + dist, mainFont, mainSize, coloredShadow);
        }

        renderXylitolSakuraStringLineGradient(vg, mainX, mainBaseY, mainFont, mainSize, displayName, offsetDeg, colorStepDeg, textW, blockW);

        NanoVGHelper.drawString(m.infoText, m.bgX + m.padX + m.mainW + m.gap, infoBaseY, infoFont, infoSize, new Color(255, 255, 255, 255));

        this.width = m.totalW;
        this.height = m.totalH;
    }

    private void renderSakuraSimple(long vg, float s) {
        String displayName = XYLITOL_MAIN_TEXT;
        if (displayName == null) displayName = "";

        int mainFont = getXylitolSakuraFont();
        float mainSize = xylitolMainFontSize.get().floatValue() * s;
        float mainH = NanoVGHelper.getFontHeight(mainFont, mainSize);

        double offsetDeg = (System.currentTimeMillis() / 20.0) * animationSpeed.get();
        int colorStepDeg = xylitolSakuraGradientSpread.get();
        float blockW = Math.max(1.0f, xylitolSakuraBlockDistance.get() * s);

        float mainX = x;
        float mainBaseY = y + mainH + (xylitolSakuraTextOffsetY.get().floatValue() * s);

        float textW = NanoVGHelper.getTextWidth(displayName, mainFont, mainSize);
        if (textW <= 0.0f) {
            textW = Math.max(1.0f, NanoVGHelper.getTextWidth(XYLITOL_MAIN_TEXT, mainFont, mainSize));
        }

        // 绘制发光效果
        if (xylitolSakuraGlow.get()) {
            int glowIndex = (int) Math.max(0, Math.floor((textW * 0.5f) / blockW));
            Color glowC = getXylitolSakuraStepColor(offsetDeg + (double) glowIndex * colorStepDeg);
            glowC = new Color(glowC.getRed(), glowC.getGreen(), glowC.getBlue(), 220);
            NanoVGHelper.drawGlowingString(displayName, mainX, mainBaseY, mainFont, mainSize, glowC, xylitolSakuraGlowRadius.get().floatValue() * s, xylitolSakuraGlowIntensity.get());
        }

        // 绘制文本阴影
        if (sakuraTextShadow.get()) {
            Color c1 = xylitolSakuraColor1.get();
            Color c2 = xylitolSakuraColor2.get();
            Color avgColor = new Color((c1.getRed() + c2.getRed()) / 2, (c1.getGreen() + c2.getGreen()) / 2, (c1.getBlue() + c2.getBlue()) / 2);
            Color coloredShadow = new Color((int) (avgColor.getRed() * 0.5), (int) (avgColor.getGreen() * 0.5), (int) (avgColor.getBlue() * 0.5), 255);

            float dist = sakuraTextShadowDistance.get().floatValue() * s;
            NanoVGHelper.drawString(displayName, mainX + dist, mainBaseY + dist, mainFont, mainSize, coloredShadow);
        }

        // 绘制渐变文本
        renderXylitolSakuraStringLineGradient(vg, mainX, mainBaseY, mainFont, mainSize, displayName, offsetDeg, colorStepDeg, textW, blockW);

        float totalWidth = textW;

        // 绘制版本号（不参与渐变）
        if (sakuraShowVersion.get()) {
            float gap = sakuraVersionGap.get().floatValue() * s;
            String version = Sakura.MOD_VER;
            int versionFont = FontLoader.regular();
            float versionSize = sakuraVersionSize.get().floatValue() * s;
            float versionW = NanoVGHelper.getTextWidth(version, versionFont, versionSize);
            float versionH = NanoVGHelper.getFontHeight(versionFont, versionSize);

            float versionX = mainX + textW + gap;
            // 调整版本号的垂直位置，使其与主文本对齐，并应用 Y 偏移
            float versionOffsetY = sakuraVersionOffsetY.get().floatValue() * s;
            float versionBaseY = y + mainH + (xylitolSakuraTextOffsetY.get().floatValue() * s) + (mainH - versionH) * 0.5f + versionH * 0.5f + versionOffsetY;

            NanoVGHelper.drawString(version, versionX, versionBaseY, versionFont, versionSize, sakuraVersionColor.get());

            totalWidth += gap + versionW;
        }

        this.width = totalWidth;
        this.height = mainH;
    }

    private void renderXylitolShadow() {
        float s = hudScale.get().floatValue();
        XylitolMetrics m = calculateXylitolMetrics(s);
        float r = m.radius * s;

        float shadowY = m.bgY;
        float shadowH = m.bgH;

        if (mode.is(ListMode.Sakura)) {
            float topOffset = 1.0f * s;
            float bottomOffset = 0.0f * s;
            shadowY += topOffset;
            shadowH = shadowH + bottomOffset - topOffset;
        }

        float[] rects = new float[]{m.bgX, shadowY, m.bgW, shadowH};
        float[] radii = new float[]{r};

        if (xylitolShadowMode.is(XylitolShadowMode.Gradient)) {
            Color start = ClickGui.color(1);
            Color end = ClickGui.color(20);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
            ShadowShader.drawStairShadowGradient(m.bgX, shadowY, m.bgW, shadowH, xylitolShadowRange.get().floatValue() * s, xylitolShadowStrength.get().floatValue(), start, end, rects, radii, 1);
            return;
        }

        ShadowShader.drawStairShadow(m.bgX, shadowY, m.bgW, shadowH, xylitolShadowRange.get().floatValue() * s, xylitolShadowStrength.get().floatValue(), new Color(0, 0, 0), rects, radii, 1);
    }

    private XylitolMetrics calculateXylitolMetrics(float s) {
        int mainFont = getXylitolSakuraFont();
        int infoFont = FontLoader.regular();
        float mainSize = xylitolMainFontSize.get().floatValue() * s;
        float infoSize = xylitolInfoFontSize.get().floatValue() * s;

        String clientName = XYLITOL_MAIN_TEXT;
        if (clientName == null) clientName = "";

        String username;
        if (mc.player == null || mc.world == null) {
            username = "Player";
        } else {
            username = AuthState.getCurrentUser();
            if (username == null || username.isBlank()) {
                NullPointerException e = new NullPointerException("路几把");
                Sakura.LOGGER.error("哎呦我去你真牛逼你咋裂的？", e);
                throw e;
            }
        }
        int fps = mc.getCurrentFps();
        String ver = Sakura.MOD_VER;
        String info = " | " + username + " | fps:" + fps + " | " + ver;

        float mainW = NanoVGHelper.getTextWidth(clientName, mainFont, mainSize);
        float infoW = NanoVGHelper.getTextWidth(info, infoFont, infoSize);
        float mainH = NanoVGHelper.getFontHeight(mainFont, mainSize);
        float infoH = NanoVGHelper.getFontHeight(infoFont, infoSize);

        float padX = xylitolPaddingX.get().floatValue() * s;
        float padY = xylitolPaddingY.get().floatValue() * s;
        float gap = xylitolGap.get().floatValue() * s;
        float radius = 10.0f;

        float maxH = Math.max(mainH, infoH);
        float lineH = Math.max(1f, 1f * s);
        float effectiveGap = clientName.isEmpty() ? 0.0f : gap;
        float bgW = padX * 2f + mainW + effectiveGap + infoW;
        float bgH = padY * 2f + maxH;

        float lineX = x;
        float lineY = y;
        float bgX = x;
        float bgY = y + lineH;

        XylitolMetrics m = new XylitolMetrics();
        m.lineX = lineX;
        m.lineY = lineY;
        m.lineW = bgW;
        m.lineH = lineH;
        m.bgX = bgX;
        m.bgY = bgY;
        m.bgW = bgW;
        m.bgH = bgH;
        m.padX = padX;
        m.padY = padY;
        m.gap = effectiveGap;
        m.radius = radius;
        m.mainW = mainW;
        m.infoW = infoW;
        m.infoText = info;
        m.totalW = bgW;
        m.totalH = lineH + bgH;
        return m;
    }

    private int getXylitolSakuraFont() {
        return switch (xylitolSakuraFontMode.get()) {
            case Regular -> FontLoader.regular();
            case Medium -> FontLoader.medium();
            case Semi -> FontLoader.greycliffSemi();
            case Bold -> FontLoader.bold();
            case Comfortaa -> FontLoader.comfortaa();
            case Ax -> FontLoader.ax();
            case Geologica -> FontLoader.geologica();
            case Material -> FontLoader.material();
            case Tenacity -> FontLoader.tenacity();
            case Cjk -> FontLoader.cjk();
        };
    }

    private static class XylitolMetrics {
        float lineX, lineY, lineW, lineH;
        float bgX, bgY, bgW, bgH;
        float padX, padY, gap;
        float radius;
        float mainW, infoW;
        String infoText;
        float totalW, totalH;
    }

    private void renderParticles(long vg) {
        for (Particle particle : particles) {
            if (particle.isAlive()) {
                Color particleColor = new Color(
                        particle.color.getRed(),
                        particle.color.getGreen(),
                        particle.color.getBlue(),
                        (int) (particle.color.getAlpha() * particle.alpha)
                );

                nvgBeginPath(vg);
                nvgCircle(vg, particle.x, particle.y, particle.size);

                nvgFillColor(vg, NanoVGHelper.nvgColor(particleColor));
                nvgFill(vg);

                nvgBeginPath(vg);
                nvgCircle(vg, particle.x, particle.y, particle.size * 1.5f);
                nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 255, 255, (int) (50 * particle.alpha))));
                nvgFill(vg);
            }
        }
    }

    private void loadIcon() {
        iconImage = NanoVGHelper.loadTexture("/assets/sakura/icons/icon_32x32.png");
    }

    @Override
    public void onDisable() {
        if (iconImage != -1) {
            NanoVGHelper.deleteTexture(iconImage);
            iconImage = -1;
        }
    }

    private Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(r, g, b, a);
    }

    private Color[] getXylitolSakuraGradientColors() {
        Color c1Base = xylitolSakuraColor1.get();
        Color c2Base = xylitolSakuraColor2.get();
        return new Color[]{c1Base, c2Base};
    }

    private void renderXylitolSakuraStringLineGradient(long vg, float x, float baseY, int font, float size, String text, double offsetDeg, int colorStepDeg, float textW, float blockW) {
        if (text == null || text.isEmpty()) return;

        float totalW = Math.max(1.0f, textW);
        int segments = (int) Math.max(16, Math.min(260, Math.ceil(totalW / Math.max(1.0f, blockW))));
        float segW = totalW / segments;
        float fontH = NanoVGHelper.getFontHeight(font, size);
        float scissorY = baseY - fontH - 2.0f;
        float scissorH = fontH + 4.0f;
        float overlap = 0.75f;

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

        for (int i = 0; i < segments; i++) {
            float segX = x + i * segW;
            Color col = getXylitolSakuraStepColor(offsetDeg + (double) i * colorStepDeg);
            col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 255);

            nvgSave(vg);
            nvgScissor(vg, segX - overlap, scissorY, segW + overlap * 2.0f, scissorH);
            nvgFillColor(vg, NanoVGHelper.nvgColor(col));
            nvgText(vg, x, baseY, text);
            nvgRestore(vg);
        }
    }

    

    private Color getXylitolSakuraStepColor(double offsetDeg) {
        double factor = (Math.sin(Math.toRadians(offsetDeg)) + 1.0) / 2.0;
        return interpolateColor(xylitolSakuraColor1.get(), xylitolSakuraColor2.get(), (float) factor);
    }

    private static class Particle {
        public float x, y;
        public float velocityX, velocityY;
        public float size;
        public Color color;
        public float alpha;
        public float life;
        public float maxLife;

        public Particle(float x, float y) {
            this.x = x;
            this.y = y;
            this.size = 1.0f + (float) (Math.random() * 2.0f);
            this.color = ClickGui.mainColor.get();
            this.alpha = 1.0f;
            this.maxLife = 100f + (float) (Math.random() * 100f);
            this.life = maxLife;
            float speed = 0.5f + (float) (Math.random() * 1.5f);
            double angle = Math.random() * Math.PI * 2;
            this.velocityX = (float) (Math.cos(angle) * speed);
            this.velocityY = (float) (Math.sin(angle) * speed);
        }

        public void update() {
            x += velocityX;
            y += velocityY;
            life -= 1.0f;
            alpha = life / maxLife;
        }

        public boolean isAlive() {
            return life > 0;
        }
    }
}
