package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.hudeditor.HudEditorScreen;
import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class PotionHud extends HudModule {
    private final NumberValue<Double> scale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.05);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 5.0, 0.0, 15.0, 0.5);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final BoolValue hideVanilla = new BoolValue("HideVanilla", "隐藏原版图标", true);

    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 50));

    private final BoolValue textGradient = new BoolValue("TextGradient", "文本渐变", false);
    private final ColorValue gradientColor1 = new ColorValue("GradientColor1", "渐变颜色1", new Color(0, 255, 255));
    private final ColorValue gradientColor2 = new ColorValue("GradientColor2", "渐变颜色2", new Color(255, 0, 255));
    private final ColorValue nameColor = new ColorValue("NameColor", "名称颜色", Color.WHITE, () -> !textGradient.get());
    private final ColorValue durationColor = new ColorValue("DurationColor", "时长颜色", new Color(200, 200, 200), () -> !textGradient.get());

    private final BoolValue textGlow = new BoolValue("TextGlow", "文字发光", true);
    private final NumberValue<Double> glowRange = new NumberValue<>("GlowRange", "发光范围", 4.0, 0.0, 30.0, 0.5, textGlow::get);
    private final NumberValue<Integer> glowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, textGlow::get);

    private final BoolValue backgroundShadow = new BoolValue("BackgroundShadow", "背景阴影", true);
    private final NumberValue<Double> shadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 1.0, backgroundShadow::get);
    private final NumberValue<Double> shadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.05, backgroundShadow::get);

    public enum ShadowMode {Solid, Gradient}

    private final EnumValue<ShadowMode> shadowMode = new EnumValue<>("ShadowMode", "阴影模式", ShadowMode.Solid, backgroundShadow::get);
    private final BoolValue shadowAutoColor = new BoolValue("ShadowAutoColor", "阴影自动配色", false, () -> backgroundShadow.get() && shadowMode.is(ShadowMode.Gradient));
    private final NumberValue<Double> shadowGradientSpeed = new NumberValue<>("ShadowGradientSpeed", "阴影渐变速度", 1.0, 0.1, 10.0, 0.1, () -> backgroundShadow.get() && shadowMode.is(ShadowMode.Gradient));
    private final NumberValue<Double> shadowColorStep = new NumberValue<>("ShadowColorStep", "阴影颜色跨度", 15.0, 1.0, 100.0, 1.0, () -> backgroundShadow.get() && shadowMode.is(ShadowMode.Gradient));

    private final Map<RegistryEntry<StatusEffect>, EffectInfo> infos = new LinkedHashMap<>();

    public PotionHud() {
        super("PotionHud", "药水HUD", 10, 250);
    }

    public boolean shouldHideVanilla() {
        return isEnabled() && hideVanilla.get();
    }

    @Override
    public void renderInGame(DrawContext context) {
        if (nullCheck()) return;
        HudEditor editor = Sakura.MODULES.getModule(HudEditor.class);
        if (editor != null && editor.isEnabled() && mc.currentScreen instanceof HudEditorScreen) return;
        if (mc.currentScreen != null && mc.currentScreen.showsStatusEffects()) return;
        renderInternal(context, false, 0, 0);
    }

    @Override
    public void renderInEditor(DrawContext context, float mouseX, float mouseY) {
        if (dragging) {
            int gameWidth = mc.getWindow().getScaledWidth();
            int gameHeight = mc.getWindow().getScaledHeight();

            x = Math.max(0, Math.min(mouseX - dragX, gameWidth - width));
            y = Math.max(0, Math.min(mouseY - dragY, gameHeight - height));

            relativeX = x / gameWidth;
            relativeY = y / gameHeight;
        }

        renderInternal(context, true, mouseX, mouseY);
        NanoVGRenderer.INSTANCE.draw(vg -> NanoVGHelper.drawRect(x, y, width, height, dragging ? new Color(100, 100, 255, 80) : new Color(0, 0, 0, 50)));
    }

    private void renderInternal(DrawContext context, boolean editor, float mouseX, float mouseY) {
        float s = scale.get().floatValue();

        List<RenderEntry> entries = buildEntries(s, editor);
        if (entries.isEmpty()) {
            this.width = 50 * s;
            this.height = 20 * s;
            return;
        }

        this.width = entries.stream().map(e -> e.totalW).max(Float::compare).orElse(50f * s);
        this.height = entries.size() * (28f * s);

        if (backgroundShadow.get()) {
            renderBackgroundShadow(entries, s);
        }

        if (blur.get()) {
            float r = radius.get().floatValue() * s;
            float strength = blurStrength.get().floatValue();
            for (RenderEntry e : entries) {
                BlurShader.drawRoundedBlur(e.iconX, e.y, e.iconW, e.iconH, r, strength);
                BlurShader.drawRoundedBlur(e.infoX, e.y, e.infoW, e.infoH, r, strength);
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float r = radius.get().floatValue() * s;
            Color overlay = backgroundColor.get();
            boolean gradient = textGradient.get();
            boolean glow = textGlow.get();
            float glowRadius = glowRange.get().floatValue() * s;
            int glowPower = Math.max(1, glowIntensity.get());
            int align = NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE;

            for (RenderEntry e : entries) {
                NanoVGHelper.drawRoundRect(e.iconX, e.y, e.iconW, e.iconH, r, overlay);
                NanoVGHelper.drawRoundRect(e.infoX, e.y, e.infoW, e.infoH, r, overlay);

                NanoVGHelper.scissor(e.baseX, e.y, e.progressW, e.iconH);
                NanoVGHelper.drawRoundRect(e.iconX, e.y, e.iconW, e.iconH, r, overlay);
                NanoVGHelper.drawRoundRect(e.infoX, e.y, e.infoW, e.infoH, r, overlay);
                NanoVGHelper.resetScissor();

                float nameLineH = NanoVGHelper.getFontHeight(e.nameFont, e.nameFontSize);
                float textY = e.y + ((e.infoH - nameLineH) / 2f) + nameLineH;
                float nameX = e.infoX + (5f * s);
                float durationX = e.infoX + (7f * s) + e.nameW;

                if (gradient) {
                    float startX = nameX;
                    float endX = Math.max(startX + 1f, durationX + e.durationW);
                    float gy = textY;
                    NVGPaint paint = NVGPaint.create();
                    nvgLinearGradient(vg, startX, gy, endX, gy, NanoVGHelper.nvgColor(gradientColor1.get()), NanoVGHelper.nvgColor(gradientColor2.get()), paint);

                    drawTextGradient(vg, e.name, nameX, textY, e.nameFont, e.nameFontSize, align, paint, glow, glowRadius, glowPower);
                    drawTextGradient(vg, e.duration, durationX, textY, e.durationFont, e.durationFontSize, align, paint, glow, glowRadius, glowPower);
                } else {
                    drawTextSolid(vg, e.name, nameX, textY, e.nameFont, e.nameFontSize, align, nameColor.get(), glow, glowRadius, glowPower);
                    drawTextSolid(vg, e.duration, durationX, textY, e.durationFont, e.durationFontSize, align, durationColor.get(), glow, glowRadius, glowPower);
                }
            }
        });

        for (RenderEntry e : entries) {
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(e.iconX + (2f * s), e.y + (2f * s));
            context.getMatrices().scale(s, s);
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, InGameHud.getEffectTexture(e.effect), 0, 0, 16, 16);
            context.getMatrices().popMatrix();
        }
    }

    private List<RenderEntry> buildEntries(float s, boolean editor) {
        if (mc.player != null && mc.world != null) {
            updateInfosFromPlayer();
        }

        int nameFont = FontLoader.medium();
        int durationFont = FontLoader.regular();
        float nameFontSize = 10f * s;
        float durationFontSize = 8f * s;

        float iconW = 20f * s;
        float iconH = 20f * s;
        float gap = 5f * s;

        float startY = this.y;
        List<Map.Entry<RegistryEntry<StatusEffect>, EffectInfo>> snapshot = new ArrayList<>(infos.entrySet());
        List<RenderEntry> out = new ArrayList<>();

        for (var entry : snapshot) {
            RegistryEntry<StatusEffect> effect = entry.getKey();
            EffectInfo info = entry.getValue();

            String name = PotionContentsComponent.getEffectText(effect, info.amplifier).getString();
            String duration = info.infinite ? "∞" : formatDuration(info.duration);

            float nameW = NanoVGHelper.getTextWidth(name, nameFont, nameFontSize);
            float durationW = NanoVGHelper.getTextWidth(duration, durationFont, durationFontSize);

            float infoW = nameW + durationW + (12f * s);
            float totalW = (25f * s) + infoW;

            info.width = totalW;
            if (info.yTimer.value == -1f) {
                info.yTimer.value = startY;
            }

            if (!editor) {
                if (info.shouldDisappear) {
                    info.xTimer.target = -totalW - (20f * s);
                } else {
                    info.xTimer.target = this.x;
                    info.yTimer.target = startY;
                    info.yTimer.update(true);

                    float targetBar = totalW;
                    if (!info.infinite && info.maxDuration > 0) {
                        targetBar = (float) info.duration / (float) info.maxDuration * totalW;
                    }
                    info.durationTimer.target = targetBar;
                    if (info.durationTimer.value <= 0f) {
                        info.durationTimer.value = targetBar;
                    }
                }

                info.durationTimer.update(true);
                info.xTimer.update(true);
            } else {
                info.xTimer.value = this.x;
                info.yTimer.value = startY;
                float targetBar = totalW;
                if (!info.infinite && info.maxDuration > 0) {
                    targetBar = (float) info.duration / (float) info.maxDuration * totalW;
                }
                info.durationTimer.value = clampProgress(targetBar, totalW);
            }

            float baseX = info.xTimer.value;
            float y = info.yTimer.value;
            float iconX = baseX;
            float infoX = baseX + iconW + gap;

            out.add(new RenderEntry(
                    effect,
                    baseX, y,
                    iconX, infoX,
                    iconW, iconH,
                    infoW, iconH,
                    totalW,
                    clampProgress(info.durationTimer.value, totalW),
                    name, duration,
                    nameFont, durationFont,
                    nameFontSize, durationFontSize,
                    nameW,
                    durationW
            ));

            startY += 28f * s;
        }

        if (!editor) {
            List<RegistryEntry<StatusEffect>> toRemove = new ArrayList<>();
            for (var entry : infos.entrySet()) {
                EffectInfo info = entry.getValue();
                if (info.shouldDisappear && info.xTimer.value <= -info.width - (20f * s)) {
                    toRemove.add(entry.getKey());
                }
            }
            for (RegistryEntry<StatusEffect> k : toRemove) {
                infos.remove(k);
            }
        }

        return out;
    }

    private void updateInfosFromPlayer() {
        Collection<StatusEffectInstance> effects = mc.player.getStatusEffects();
        Set<RegistryEntry<StatusEffect>> active = new HashSet<>();
        float initialX = -60f * scale.get().floatValue();

        for (StatusEffectInstance instance : effects) {
            if (!instance.shouldShowIcon()) continue;
            RegistryEntry<StatusEffect> effect = instance.getEffectType();
            active.add(effect);

            EffectInfo info = infos.computeIfAbsent(effect, e -> new EffectInfo(initialX));
            info.infinite = instance.isInfinite();
            info.maxDuration = info.infinite ? -1 : Math.max(info.maxDuration, instance.getDuration());
            info.duration = instance.getDuration();
            info.amplifier = instance.getAmplifier();
            info.shouldDisappear = false;
        }

        for (var entry : infos.entrySet()) {
            if (!active.contains(entry.getKey())) {
                entry.getValue().shouldDisappear = true;
            }
        }
    }

    private static float clampProgress(float v, float max) {
        if (max <= 0f) return 0f;
        if (v < 0f) return 0f;
        if (v > max) return max;
        return v;
    }

    private static String formatDuration(int ticks) {
        if (ticks < 0) return "0:00";
        int totalSeconds = ticks / 20;
        int seconds = totalSeconds % 60;
        int minutesTotal = totalSeconds / 60;
        if (minutesTotal >= 60) {
            int hours = minutesTotal / 60;
            int minutes = minutesTotal % 60;
            return hours + ":" + String.format("%02d:%02d", minutes, seconds);
        }
        return minutesTotal + ":" + String.format("%02d", seconds);
    }

    private static final class EffectInfo {
        final SmoothAnimationTimer xTimer;
        final SmoothAnimationTimer yTimer;
        final SmoothAnimationTimer durationTimer;

        int maxDuration = -1;
        int duration;
        int amplifier;
        boolean infinite;
        boolean shouldDisappear;
        float width;

        EffectInfo(float initialX) {
            this.xTimer = new SmoothAnimationTimer(0f, initialX, 0.2f);
            this.yTimer = new SmoothAnimationTimer(0f, -1f, 0.2f);
            this.durationTimer = new SmoothAnimationTimer(0f, -1f, 0.2f);
        }
    }

    private record RenderEntry(
            RegistryEntry<StatusEffect> effect,
            float baseX, float y,
            float iconX, float infoX,
            float iconW, float iconH,
            float infoW, float infoH,
            float totalW,
            float progressW,
            String name, String duration,
            int nameFont, int durationFont,
            float nameFontSize, float durationFontSize,
            float nameW,
            float durationW
    ) {
    }

    private static void drawTextSolid(long vg, String text, float x, float y, int font, float size, int align, Color color, boolean glow, float glowRadius, int intensity) {
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, align);

        if (glow && glowRadius > 0f && intensity > 0) {
            nvgFontBlur(vg, glowRadius);
            nvgFillColor(vg, NanoVGHelper.nvgColor(color));
            for (int i = 0; i < intensity; i++) {
                nvgText(vg, x, y, text);
            }
            nvgFontBlur(vg, 0f);
        }

        nvgFillColor(vg, NanoVGHelper.nvgColor(color));
        nvgText(vg, x, y, text);
    }

    private static void drawTextGradient(long vg, String text, float x, float y, int font, float size, int align, NVGPaint paint, boolean glow, float glowRadius, int intensity) {
        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, align);

        if (glow && glowRadius > 0f && intensity > 0) {
            nvgFontBlur(vg, glowRadius);
            nvgFillPaint(vg, paint);
            for (int i = 0; i < intensity; i++) {
                nvgText(vg, x, y, text);
            }
            nvgFontBlur(vg, 0f);
        }

        nvgFillPaint(vg, paint);
        nvgText(vg, x, y, text);
    }

    private void renderBackgroundShadow(List<RenderEntry> entries, float s) {
        int count = Math.min(128, entries.size());
        if (count <= 0) return;

        float overlap = Math.max(0.5f, 0.5f * s);

        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < count; i++) {
            RenderEntry e = entries.get(i);
            minX = Math.min(minX, e.baseX);
            minY = Math.min(minY, e.y - overlap);
            maxX = Math.max(maxX, e.baseX + e.totalW);
            maxY = Math.max(maxY, e.y + e.iconH + overlap);
        }

        float shadowX = minX;
        float shadowY = minY;
        float shadowW = Math.max(0.0f, maxX - minX);
        float shadowH = Math.max(0.0f, maxY - minY);
        if (shadowW <= 0.0f || shadowH <= 0.0f) return;

        float[] rects = new float[count * 4];
        float[] radii = new float[count];
        float r = radius.get().floatValue() * s;
        for (int i = 0; i < count; i++) {
            RenderEntry e = entries.get(i);
            int base = i * 4;
            rects[base] = e.baseX;
            rects[base + 1] = e.y - overlap;
            rects[base + 2] = e.totalW;
            rects[base + 3] = e.iconH + overlap * 2.0f;
            radii[i] = r;
        }

        float range = shadowRange.get().floatValue();
        float strength = shadowStrength.get().floatValue();

        if (shadowMode.is(ShadowMode.Gradient)) {
            Color c1 = gradientColor1.get();
            Color c2 = gradientColor2.get();
            if (shadowAutoColor.get()) {
                float hue = (float) ((System.currentTimeMillis() * shadowGradientSpeed.get() / 5000.0) % 1.0);
                c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
                c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
            }

            double offset = (System.currentTimeMillis() * shadowGradientSpeed.get()) / 50.0;
            int lastIndex = Math.max(0, count - 1);
            double topOffset = offset;
            double bottomOffset = offset + (lastIndex * shadowColorStep.get());
            double topFactor = (Math.sin(Math.toRadians(topOffset)) + 1) / 2;
            double bottomFactor = (Math.sin(Math.toRadians(bottomOffset)) + 1) / 2;

            Color start = interpolateColor(c1, c2, (float) topFactor);
            Color end = interpolateColor(c1, c2, (float) bottomFactor);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);

            ShadowShader.drawStairShadowGradient(shadowX, shadowY, shadowW, shadowH, range, strength, start, end, rects, radii, count);
            return;
        }

        ShadowShader.drawStairShadow(shadowX, shadowY, shadowW, shadowH, range, strength, new Color(0, 0, 0), rects, radii, count);
    }

    private static Color interpolateColor(Color c1, Color c2, float factor) {
        factor = Math.max(0f, Math.min(1f, factor));
        int r = (int) (c1.getRed() + factor * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + factor * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + factor * (c2.getBlue() - c1.getBlue()));
        int a = (int) (c1.getAlpha() + factor * (c2.getAlpha() - c1.getAlpha()));
        return new Color(r, g, b, a);
    }
}
