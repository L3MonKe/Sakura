package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class WatermarkHud extends HudModule {

    public enum ListMode {
        Normal,
        Gradient
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
        String text = Mahiro.MOD_NAME + " " + Mahiro.MOD_VER;
        float fontSize = 30 * s;
        int font = FontLoader.bold((int) fontSize);
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
        iconImage = NanoVGHelper.loadTexture("/assets/mahiro/icons/icon_32x32.png");
    }

    @Override
    public void onDisable() {
        if (iconImage != -1) {
            NanoVGHelper.deleteTexture(iconImage);
            iconImage = -1;
        }
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