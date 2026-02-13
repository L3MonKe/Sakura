package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.exception.UsernameEmptyNullPointerException;
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
import dev.sakura.client.verify.AuthState;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class WatermarkHud extends HudModule {

    public enum ListMode {
        Normal,
        Gradient,
        Sakura,
        Xylitol
    }

    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Xylitol);
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

    // Gradient Mode Settings
    private final BoolValue gradientShowIcon = new BoolValue("GradientShowIcon", "显示图标", true, () -> mode.is(ListMode.Gradient));
    private final ColorValue gradientColor1 = new ColorValue("GradientColor1", "渐变色1", new Color(0, 255, 255), () -> mode.is(ListMode.Gradient));
    private final ColorValue gradientColor2 = new ColorValue("GradientColor2", "渐变色2", new Color(255, 0, 255), () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> gradientAngle = new NumberValue<>("GradientAngle", "渐变方向", 0.0, 0.0, 360.0, 5.0, () -> mode.is(ListMode.Gradient));
    private final BoolValue gradientTextGlow = new BoolValue("GradientTextGlow", "文本发光", true, () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> gradientGlowRadius = new NumberValue<>("GradientGlowRadius", "发光半径", 3.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Gradient) && gradientTextGlow.get());
    private final NumberValue<Integer> gradientGlowIntensity = new NumberValue<>("GradientGlowIntensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Gradient) && gradientTextGlow.get());

    // Sakura Mode Settings
    private final NumberValue<Double> sakuraSize = new NumberValue<>("Size", "大小", 40.0, 5.0, 100.0, 1.0, () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraTextColor1 = new ColorValue("TextColor1", "文本色1", new Color(255, 192, 203), () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraTextColor2 = new ColorValue("TextColor2", "文本色2", new Color(255, 105, 180), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraGradientSpeed = new NumberValue<>("AnimSpeed", "渐变速度", 2.0, 0.1, 10.0, 0.1, () -> mode.is(ListMode.Sakura));
    private final BoolValue sakuraTextGlow = new BoolValue("TextGlow", "-发光", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraGlowRadius = new NumberValue<>("GlowRadius", "发光半径", 5.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraTextGlow.get());
    private final NumberValue<Integer> sakuraGlowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Sakura) && sakuraTextGlow.get());
    private final BoolValue sakuraBlur = new BoolValue("Blur", "背景模糊", true, () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraBackgroundColor = new ColorValue("BgColor", "背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraBackgroundRadius = new NumberValue<>("BgRadius", "背景圆角", 5.0, 0.0, 20.0, 1.0, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraPaddingX = new NumberValue<>("PaddingX", "宽(间距)", 5.0, 0.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraPaddingY = new NumberValue<>("PaddingY", "高(间距)", 2.0, 0.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraTextOffsetX = new NumberValue<>("TextOffsetX", "文字X偏移", 0.0, -50.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraTextOffsetY = new NumberValue<>("TextOffsetY", "文字Y偏移", 0.0, -50.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final BoolValue sakuraTopLine = new BoolValue("TopLine", "顶部线条", false, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraLineHeight = new NumberValue<>("LineHeight", "线条高度", 2.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());
    private final ColorValue sakuraLineColor1 = new ColorValue("LineColor1", "线条色1", new Color(0, 255, 255), () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());
    private final ColorValue sakuraLineColor2 = new ColorValue("LineColor2", "线条色2", new Color(255, 0, 255), () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());

    private final BoolValue sakuraShadow = new BoolValue("Shadow", "阴影", false, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraShadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 1.0, () -> mode.is(ListMode.Sakura) && sakuraShadow.get());
    private final NumberValue<Double> sakuraShadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.05, () -> mode.is(ListMode.Sakura) && sakuraShadow.get());

    public enum SakuraShadowMode {Solid, Gradient}

    private final EnumValue<SakuraShadowMode> sakuraShadowMode = new EnumValue<>("ShadowMode", "阴影模式", SakuraShadowMode.Solid, () -> mode.is(ListMode.Sakura) && sakuraShadow.get());

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
    private final BoolValue xylitolAnimateText = new BoolValue("Animate", "-文字动画", true, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Integer> xylitolAnimDelayMs = new NumberValue<>("AnimDelay", "动画间隔", 90, 20, 600, 10, () -> mode.is(ListMode.Xylitol) && xylitolAnimateText.get());
    private final NumberValue<Double> xylitolMainFontSize = new NumberValue<>("MainSize", "主文字大小", 14.0, 8.0, 32.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolInfoFontSize = new NumberValue<>("InfoSize", "信息文字大小", 10.0, 6.0, 24.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final EnumValue<XylitolSakuraFontMode> xylitolSakuraFontMode = new EnumValue<>("Font", "字体", XylitolSakuraFontMode.Bold, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolSakuraTextOffsetY = new NumberValue<>("OffsetY", "文字Y偏移", -1.0, -30.0, 30.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final ColorValue xylitolSakuraColor1 = new ColorValue("Color1", "渐变色1", new Color(255, 192, 203), () -> mode.is(ListMode.Xylitol));
    private final ColorValue xylitolSakuraColor2 = new ColorValue("Color2", "渐变色2", new Color(255, 105, 180), () -> mode.is(ListMode.Xylitol));
    private final BoolValue xylitolSakuraUseLineGradient = new BoolValue("UseLineGrad", "使用线渐变", true, () -> mode.is(ListMode.Xylitol));
    private final BoolValue xylitolSakuraGradientMove = new BoolValue("GradMove", "渐变移动", true, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolSakuraGradientMoveSpeed = new NumberValue<>("GradMoveSpeed", "移动速度", 0.6, 0.0, 5.0, 0.05, () -> mode.is(ListMode.Xylitol) && xylitolSakuraGradientMove.get());
    private final NumberValue<Integer> xylitolSakuraGradientSpread = new NumberValue<>("GradSpread", "颜色跨度", 15, 1, 400, 1, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Integer> xylitolSakuraBlockDistance = new NumberValue<>("BlockDistance", "色块距离", 100, 1, 100, 1, () -> mode.is(ListMode.Xylitol));
    private final BoolValue xylitolSakuraGlow = new BoolValue("Glow", "发光", true, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolSakuraGlowRadius = new NumberValue<>("GlowRadius", "发光半径", 5.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Xylitol) && xylitolSakuraGlow.get());
    private final NumberValue<Integer> xylitolSakuraGlowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Xylitol) && xylitolSakuraGlow.get());
    private final NumberValue<Double> xylitolPaddingX = new NumberValue<>("PaddingX", "宽(间距)", 6.0, 0.0, 30.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolPaddingY = new NumberValue<>("PaddingY", "高(间距)", 4.0, 0.0, 30.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolGap = new NumberValue<>("Gap", "文字间距", 2.0, 0.0, 20.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolRadius = new NumberValue<>("Radius", "圆角", 0.0, 0.0, 20.0, 0.5, () -> mode.is(ListMode.Xylitol));
    private final BoolValue xylitolBlur = new BoolValue("Blur", "背景模糊", true, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolBlurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Xylitol) && xylitolBlur.get());
    private final BoolValue xylitolShadow = new BoolValue("Shadow", "阴影", true, () -> mode.is(ListMode.Xylitol));
    private final NumberValue<Double> xylitolShadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 1.0, () -> mode.is(ListMode.Xylitol) && xylitolShadow.get());
    private final NumberValue<Double> xylitolShadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.05, () -> mode.is(ListMode.Xylitol) && xylitolShadow.get());

    public enum XylitolShadowMode {Solid, Gradient}

    private final EnumValue<XylitolShadowMode> xylitolShadowMode = new EnumValue<>("ShadowMode", "阴影模式", XylitolShadowMode.Solid, () -> mode.is(ListMode.Xylitol) && xylitolShadow.get());
    private final ColorValue xylitolBackgroundColor = new ColorValue("BgColor", "背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Xylitol));

    private int iconImage = -1;
    private float rotationAngle = 0.0f;
    private long lastUpdateTime = 0;
    private final List<Particle> particles = new ArrayList<>();

    private long xylitolLastAnimTime;
    private int xylitolCharIndex;
    private boolean xylitolBackward;
    private String xylitolMarkStr = "";

    public WatermarkHud() {
        super("Watermark", "水印", 10, 10);
        this.lastUpdateTime = System.currentTimeMillis();
        setInitialState(true);
    }

    @Override
    public void onRender(DrawContext context) {
        update();
        if (mode.is(ListMode.Sakura) && sakuraBlur.get()) {
            renderSakuraBlur();
        }
        if (mode.is(ListMode.Xylitol) && xylitolBlur.get()) {
            renderXylitolBlur();
        }
        if (mode.is(ListMode.Xylitol) && xylitolShadow.get()) {
            renderXylitolShadow();
        }
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
    }

    private void update() {
        if (mode.is(ListMode.Normal) || mode.is(ListMode.Gradient)) {
            updateRotation();
            updateParticles();
            if (iconImage == -1) {
                boolean showIcon = mode.is(ListMode.Normal) ? normalShowIcon.get() : gradientShowIcon.get();
                if (showIcon) {
                    loadIcon();
                }
            }
        }
        if (mode.is(ListMode.Xylitol)) {
            updateXylitolText();
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

            boolean showIcon = mode.is(ListMode.Normal) ? normalShowIcon.get() : gradientShowIcon.get();
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

    private void updateXylitolText() {
        if (!xylitolAnimateText.get()) {
            xylitolMarkStr = XYLITOL_MAIN_TEXT;
            return;
        }

        String clientName = XYLITOL_MAIN_TEXT;

        long now = System.currentTimeMillis();
        int delay = xylitolAnimDelayMs.get();
        if (now - xylitolLastAnimTime < delay) return;
        xylitolLastAnimTime = now;

        int len = clientName.length();
        if (!xylitolBackward) {
            xylitolCharIndex++;
            if (xylitolCharIndex >= len) {
                xylitolCharIndex = len;
                xylitolBackward = true;
            }
        } else {
            xylitolCharIndex--;
            if (xylitolCharIndex <= 0) {
                xylitolCharIndex = 0;
                xylitolBackward = false;
            }
        }

        int end = Math.max(0, Math.min(len, xylitolCharIndex));
        xylitolMarkStr = clientName.substring(0, end);
    }

    private void renderContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float s = hudScale.get().floatValue();

        if (mode.is(ListMode.Xylitol)) {
            renderXylitol(vg, s);
            return;
        }

        if (mode.is(ListMode.Sakura)) {
            String text = "Sakura";
            float fontSize = sakuraSize.get().floatValue() * s;
            int font = FontLoader.bold();
            float fontW = NanoVGHelper.getTextWidth(text, font, fontSize);
            float fontH = NanoVGHelper.getFontHeight(font, fontSize);

            float paddingX = sakuraPaddingX.get().floatValue() * s;
            float textOffsetX = sakuraTextOffsetX.get().floatValue() * s;
            float textOffsetY = sakuraTextOffsetY.get().floatValue() * s;

            float currentX = x + textOffsetX;
            float currentY = y + textOffsetY;
            float textY = currentY + fontH;

            Color[] colors = getSakuraTextGradientColors();
            Color c1 = colors[0];
            Color c2 = colors[1];

            // Create gradient across the text width
            NVGPaint paint = NVGPaint.create();
            nvgLinearGradient(vg, currentX, currentY, currentX + fontW, currentY, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);

            if (sakuraTextGlow.get()) {
                float radius = sakuraGlowRadius.get().floatValue() * s;
                int intensity = sakuraGlowIntensity.get();

                nvgFontFaceId(vg, font);
                nvgFontSize(vg, fontSize);
                nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

                nvgFontBlur(vg, radius);
                nvgFillPaint(vg, paint);
                for (int i = 0; i < intensity; i++) {
                    nvgText(vg, currentX, textY, text);
                }
                nvgFontBlur(vg, 0);
            }

            nvgFontFaceId(vg, font);
            nvgFontSize(vg, fontSize);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);
            nvgFillPaint(vg, paint);
            nvgText(vg, currentX, textY, text);

            this.width = fontW + (paddingX * 2); // Include padding in total width for drag
            this.height = fontH;
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
        boolean showIcon = mode.is(ListMode.Normal) ? normalShowIcon.get() : gradientShowIcon.get();
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

        if (mode.is(ListMode.Normal)) {
            Color color = normalRainbowColor.get() ? ClickGui.color(0) : Color.WHITE;
            if (normalTextGlow.get()) {
                NanoVGHelper.drawGlowingString(text, currentX, textY, font, fontSize, color, normalGlowRadius.get().floatValue() * s, normalGlowIntensity.get());
            } else {
                NanoVGHelper.drawString(text, currentX, textY, font, fontSize, color);
            }
        } else {
            // Gradient Mode
            Color c1 = gradientColor1.get();
            Color c2 = gradientColor2.get();
            float angle = gradientAngle.get().floatValue();

            // Calculate gradient coordinates based on angle and text dimensions
            double rad = Math.toRadians(angle);
            float cx = currentX + fontW / 2;
            float cy = textY - fontH / 2;

            // Length of the gradient vector should cover the text
            float length = Math.max(fontW, fontH);

            float sx = (float) (cx - Math.cos(rad) * length / 2);
            float sy = (float) (cy - Math.sin(rad) * length / 2);
            float ex = (float) (cx + Math.cos(rad) * length / 2);
            float ey = (float) (cy + Math.sin(rad) * length / 2);

            NVGPaint paint = NVGPaint.create();
            nvgLinearGradient(vg, sx, sy, ex, ey, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);

            if (gradientTextGlow.get()) {
                // Manual glow with gradient paint
                float radius = gradientGlowRadius.get().floatValue() * s;
                int intensity = gradientGlowIntensity.get();

                nvgFontFaceId(vg, font);
                nvgFontSize(vg, fontSize);
                nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

                nvgFontBlur(vg, radius);
                nvgFillPaint(vg, paint);
                for (int i = 0; i < intensity; i++) {
                    nvgText(vg, currentX, textY, text);
                }

                nvgFontBlur(vg, 0);
            } else {
                nvgFontFaceId(vg, font);
                nvgFontSize(vg, fontSize);
                nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);
            }
            nvgFillPaint(vg, paint);
            nvgText(vg, currentX, textY, text);
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
        float r = xylitolRadius.get().floatValue() * s;
        BlurShader.drawRoundedBlur(m.bgX, m.bgY, m.bgW, m.bgH, r, xylitolBlurStrength.get().floatValue());
    }

    private void renderXylitol(long vg, float s) {
        XylitolMetrics m = calculateXylitolMetrics(s);

        double offsetDeg = 0.0;
        if (xylitolSakuraGradientMove.get()) {
            offsetDeg = (System.currentTimeMillis() / 20.0) * xylitolSakuraGradientMoveSpeed.get();
        }
        int colorStepDeg = xylitolSakuraGradientSpread.get();
        float blockW = Math.max(1.0f, xylitolSakuraBlockDistance.get() * s);

        drawXylitolTopLine(vg, m, offsetDeg, colorStepDeg, blockW);
        NanoVGHelper.drawRoundRect(m.bgX, m.bgY, m.bgW, m.bgH, m.radius * s, xylitolBackgroundColor.get());

        String displayName = xylitolAnimateText.get() ? xylitolMarkStr : XYLITOL_MAIN_TEXT;
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

        if (xylitolSakuraUseLineGradient.get()) {
            renderXylitolSakuraStringLineGradient(vg, mainX, mainBaseY, mainFont, mainSize, displayName, offsetDeg, colorStepDeg, textW, blockW);
        } else {
            Color[] grad = getXylitolSakuraGradientColors();
            Color c1 = grad[0];
            Color c2 = grad[1];
            c1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255);
            c2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255);
            renderXylitolSakuraStringTwoColor(vg, mainX, mainBaseY, mainFont, mainSize, displayName, c1, c2, textW, blockW);
        }

        NanoVGHelper.drawString(m.infoText, m.bgX + m.padX + m.mainW + m.gap, infoBaseY, infoFont, infoSize, new Color(255, 255, 255, 255));

        this.width = m.totalW;
        this.height = m.totalH;
    }

    private void renderXylitolShadow() {
        float s = hudScale.get().floatValue();
        XylitolMetrics m = calculateXylitolMetrics(s);
        float r = m.radius * s;
        float[] rects = new float[]{m.bgX, m.bgY, m.bgW, m.bgH};
        float[] radii = new float[]{r};

        if (xylitolShadowMode.is(XylitolShadowMode.Gradient)) {
            Color start = ClickGui.color(1);
            Color end = ClickGui.color(20);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
            ShadowShader.drawStairShadowGradient(m.bgX, m.bgY, m.bgW, m.bgH, xylitolShadowRange.get().floatValue() * s, xylitolShadowStrength.get().floatValue(), start, end, rects, radii, 1);
            return;
        }

        ShadowShader.drawStairShadow(m.bgX, m.bgY, m.bgW, m.bgH, xylitolShadowRange.get().floatValue() * s, xylitolShadowStrength.get().floatValue(), new Color(0, 0, 0), rects, radii, 1);
    }

    private XylitolMetrics calculateXylitolMetrics(float s) {
        int mainFont = getXylitolSakuraFont();
        int infoFont = FontLoader.regular();
        float mainSize = xylitolMainFontSize.get().floatValue() * s;
        float infoSize = xylitolInfoFontSize.get().floatValue() * s;

        String clientName = xylitolAnimateText.get() ? xylitolMarkStr : XYLITOL_MAIN_TEXT;
        if (clientName == null) clientName = "";

        String username;
        if (mc.player == null || mc.world == null) {
            username = "Player";
        } else {
            username = AuthState.getCurrentUser();
            if (username == null || username.isBlank()) {
                UsernameEmptyNullPointerException e = new UsernameEmptyNullPointerException();
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
        float radius = xylitolRadius.get().floatValue();

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

    private void renderSakuraBlur() {
        float s = hudScale.get().floatValue();
        float fontSize = sakuraSize.get().floatValue() * s;
        String text = "Sakura";
        int font = FontLoader.bold();
        float fontW = NanoVGHelper.getTextWidth(text, font, fontSize);
        float fontH = NanoVGHelper.getFontHeight(font, fontSize);

        float paddingX = sakuraPaddingX.get().floatValue() * s;
        float paddingY = sakuraPaddingY.get().floatValue() * s;

        float bgX = x - paddingX;
        float bgY = y - paddingY;
        float bgW = fontW + (paddingX * 2);
        float bgH = fontH + (paddingY * 2);
        float radius = sakuraBackgroundRadius.get().floatValue() * s;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        if (sakuraShadow.get()) {
            float[] rects = new float[]{bgX, bgY, bgW, bgH};
            float[] radii = new float[]{radius};

            if (sakuraShadowMode.is(SakuraShadowMode.Gradient)) {
                Color[] colors = getSakuraTextGradientColors();
                Color start = new Color(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), 255);
                Color end = new Color(colors[1].getRed(), colors[1].getGreen(), colors[1].getBlue(), 255);
                ShadowShader.drawStairShadowGradient(bgX, bgY, bgW, bgH, sakuraShadowRange.get().floatValue() * s, sakuraShadowStrength.get().floatValue(), start, end, rects, radii, 1);
            } else {
                ShadowShader.drawStairShadow(bgX, bgY, bgW, bgH, sakuraShadowRange.get().floatValue() * s, sakuraShadowStrength.get().floatValue(), new Color(0, 0, 0), rects, radii, 1);
            }
        }

        if (sakuraBlur.get()) {
            BlurShader.drawRoundedBlur(bgX, bgY, bgW, bgH, radius, 10);
        }

        // Draw background color
        nvgBeginPath(vg);
        nvgRoundedRect(vg, bgX, bgY, bgW, bgH, radius);
        nvgFillColor(vg, NanoVGHelper.nvgColor(sakuraBackgroundColor.get()));
        nvgFill(vg);

        if (sakuraTopLine.get()) {
            renderSakuraGradientLine(bgX, bgY, bgW, radius);
        }
    }

    private Color[] getSakuraTextGradientColors() {
        double offset = (System.currentTimeMillis() * sakuraGradientSpeed.get()) / 20.0;
        float factor1 = (float) (Math.sin(Math.toRadians(offset)) + 1) / 2;
        float factor2 = (float) (Math.sin(Math.toRadians(offset + 180)) + 1) / 2;

        Color c1Base = sakuraTextColor1.get();
        Color c2Base = sakuraTextColor2.get();

        return new Color[]{interpolateColor(c1Base, c2Base, factor1), interpolateColor(c1Base, c2Base, factor2)};
    }

    private void renderSakuraGradientLine(float x, float y, float w, float radius) {
        float s = hudScale.get().floatValue();
        float h = sakuraLineHeight.get().floatValue() * s;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        // Calculate dynamic colors
        double offset = (System.currentTimeMillis() * sakuraGradientSpeed.get()) / 20.0;
        float factor1 = (float) (Math.sin(Math.toRadians(offset)) + 1) / 2;
        float factor2 = (float) (Math.sin(Math.toRadians(offset + 180)) + 1) / 2;

        Color c1_base = sakuraLineColor1.get();
        Color c2_base = sakuraLineColor2.get();

        Color c1 = interpolateColor(c1_base, c2_base, factor1);
        Color c2 = interpolateColor(c1_base, c2_base, factor2);

        NVGPaint paint = NVGPaint.create();
        nvgLinearGradient(vg, x, y, x + w, y, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);

        nvgBeginPath(vg);
        // Draw custom rounded rect: Top-Left and Top-Right rounded, Bottom flat (if line height is small, or just follow shape)
        // To be safe and look good, let's draw a path that follows the top curve

        // Start from bottom-left of the line rect
        nvgMoveTo(vg, x, y + h);
        // Line to top-left start of arc
        nvgLineTo(vg, x, y + radius);
        // Arc top-left
        nvgArcTo(vg, x, y, x + radius, y, radius);
        // Line to top-right start of arc
        nvgLineTo(vg, x + w - radius, y);
        // Arc top-right
        nvgArcTo(vg, x + w, y, x + w, y + radius, radius);
        // Line to bottom-right of the line rect
        nvgLineTo(vg, x + w, y + h);
        // Close shape
        nvgLineTo(vg, x, y + h);

        nvgClosePath(vg);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
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

    private void renderXylitolSakuraStringTwoColor(long vg, float x, float baseY, int font, float size, String text, Color c1, Color c2, float textW, float blockW) {
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
            float mid = (segX - x) + (segW * 0.5f);
            float t = mid / totalW;
            Color col = interpolateColor(c1, c2, t);
            col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 255);

            nvgSave(vg);
            nvgScissor(vg, segX - overlap, scissorY, segW + overlap * 2.0f, scissorH);
            nvgFillColor(vg, NanoVGHelper.nvgColor(col));
            nvgText(vg, x, baseY, text);
            nvgRestore(vg);
        }
    }

    private void drawXylitolTopLine(long vg, XylitolMetrics m, double offsetDeg, int colorStepDeg, float blockW) {
        Color left;
        int segments = (int) Math.max(8, Math.min(300, Math.ceil(m.lineW / blockW)));
        float segW = m.lineW / segments;
        float overlap = 0.75f;

        for (int i = 0; i < segments; i++) {
            if (xylitolSakuraUseLineGradient.get()) {
                left = getXylitolSakuraStepColor(offsetDeg + (double) i * colorStepDeg);
            } else {
                float t = segments <= 1 ? 0.0f : (float) i / (segments - 1);
                left = interpolateColor(xylitolSakuraColor1.get(), xylitolSakuraColor2.get(), t);
            }
            left = new Color(left.getRed(), left.getGreen(), left.getBlue(), 255);

            float segX = m.lineX + i * segW;
            nvgBeginPath(vg);
            nvgRect(vg, segX - overlap, m.lineY, segW + overlap * 2.0f, m.lineH);
            nvgFillColor(vg, NanoVGHelper.nvgColor(left));
            nvgFill(vg);
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
