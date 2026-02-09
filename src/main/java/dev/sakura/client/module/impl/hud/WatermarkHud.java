package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
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
        Sakura
    }

    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Normal);
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
    private final NumberValue<Double> sakuraSize = new NumberValue<>("SakuraSize", "Sakura-大小", 40.0, 5.0, 100.0, 1.0, () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraTextColor1 = new ColorValue("SakuraTextColor1", "Sakura-文本色1", new Color(255, 192, 203), () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraTextColor2 = new ColorValue("SakuraTextColor2", "Sakura-文本色2", new Color(255, 105, 180), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraGradientSpeed = new NumberValue<>("SakuraAnimSpeed", "Sakura-渐变速度", 2.0, 0.1, 10.0, 0.1, () -> mode.is(ListMode.Sakura));
    private final BoolValue sakuraTextGlow = new BoolValue("SakuraTextGlow", "Sakura-发光", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraGlowRadius = new NumberValue<>("SakuraGlowRadius", "Sakura-发光半径", 5.0, 1.0, 20.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraTextGlow.get());
    private final NumberValue<Integer> sakuraGlowIntensity = new NumberValue<>("SakuraGlowIntensity", "Sakura-发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Sakura) && sakuraTextGlow.get());
    private final BoolValue sakuraBlur = new BoolValue("SakuraBlur", "Sakura-背景模糊", true, () -> mode.is(ListMode.Sakura));
    private final ColorValue sakuraBackgroundColor = new ColorValue("SakuraBgColor", "Sakura-背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraBackgroundRadius = new NumberValue<>("SakuraBgRadius", "Sakura-背景圆角", 5.0, 0.0, 20.0, 1.0, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraPaddingX = new NumberValue<>("SakuraPaddingX", "Sakura-宽(间距)", 5.0, 0.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraPaddingY = new NumberValue<>("SakuraPaddingY", "Sakura-高(间距)", 2.0, 0.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraTextOffsetX = new NumberValue<>("SakuraTextOffsetX", "Sakura-文字X偏移", 0.0, -50.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraTextOffsetY = new NumberValue<>("SakuraTextOffsetY", "Sakura-文字Y偏移", 0.0, -50.0, 50.0, 0.5, () -> mode.is(ListMode.Sakura));
    private final BoolValue sakuraTopLine = new BoolValue("SakuraTopLine", "Sakura-顶部线条", false, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> sakuraLineHeight = new NumberValue<>("SakuraLineHeight", "Sakura-线条高度", 2.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());
    private final ColorValue sakuraLineColor1 = new ColorValue("SakuraLineColor1", "Sakura-线条色1", new Color(0, 255, 255), () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());
    private final ColorValue sakuraLineColor2 = new ColorValue("SakuraLineColor2", "Sakura-线条色2", new Color(255, 0, 255), () -> mode.is(ListMode.Sakura) && sakuraTopLine.get());

    private int iconImage = -1;
    private float rotationAngle = 0.0f;
    private long lastUpdateTime = 0;
    private final List<Particle> particles = new ArrayList<>();

    public WatermarkHud() {
        super("Watermark", "水印", 10, 10);
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void onRender(DrawContext context) {
        update();
        if (mode.is(ListMode.Sakura) && sakuraBlur.get()) {
            renderSakuraBlur();
        }
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
    }

    private void update() {
        updateRotation();
        updateParticles();
        if (iconImage == -1) {
            boolean showIcon = mode.is(ListMode.Normal) ? normalShowIcon.get() : gradientShowIcon.get();
            if (showIcon) {
                loadIcon();
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

    private void renderContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float s = hudScale.get().floatValue();

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

            // Gradient Paint Logic for Sakura
            // Calculate dynamic colors
            double offset = (System.currentTimeMillis() * sakuraGradientSpeed.get()) / 20.0;
            float factor1 = (float) (Math.sin(Math.toRadians(offset)) + 1) / 2;
            float factor2 = (float) (Math.sin(Math.toRadians(offset + 180)) + 1) / 2;

            Color c1_base = sakuraTextColor1.get();
            Color c2_base = sakuraTextColor2.get();

            Color c1 = interpolateColor(c1_base, c2_base, factor1);
            Color c2 = interpolateColor(c1_base, c2_base, factor2);

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