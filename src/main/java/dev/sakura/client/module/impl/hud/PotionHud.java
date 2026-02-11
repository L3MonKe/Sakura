package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.hud.HudEditorScreen;
import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;

public class PotionHud extends HudModule {
    private final NumberValue<Double> scale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.05);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 5.0, 0.0, 15.0, 0.5);
    private final BoolValue enableShadow = new BoolValue("Shadow", "阴影", true);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final BoolValue hideVanilla = new BoolValue("HideVanilla", "隐藏原版图标", true);

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
            Color overlay = new Color(0, 0, 0, 50);

            for (RenderEntry e : entries) {
                if (enableShadow.get()) {
                    Color shadow = new Color(0, 0, 0, 70);
                    NanoVGHelper.drawRoundRectBloom(e.iconX, e.y, e.iconW, e.iconH, r, shadow);
                    NanoVGHelper.drawRoundRectBloom(e.infoX, e.y, e.infoW, e.infoH, r, shadow);
                }

                NanoVGHelper.drawRoundRect(e.iconX, e.y, e.iconW, e.iconH, r, overlay);
                NanoVGHelper.drawRoundRect(e.infoX, e.y, e.infoW, e.infoH, r, overlay);

                NanoVGHelper.scissor(e.baseX, e.y, e.progressW, e.iconH);
                NanoVGHelper.drawRoundRect(e.iconX, e.y, e.iconW, e.iconH, r, overlay);
                NanoVGHelper.drawRoundRect(e.infoX, e.y, e.infoW, e.infoH, r, overlay);
                NanoVGHelper.resetScissor();

                float textCenterY = e.y + (e.infoH / 2f);
                NanoVGHelper.drawString(e.name, e.infoX + (5f * s), textCenterY, e.nameFont, e.nameFontSize, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, new Color(255, 255, 255));
                NanoVGHelper.drawString(e.duration, e.infoX + (7f * s) + e.nameW, textCenterY, e.durationFont, e.durationFontSize, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE, new Color(200, 200, 200));
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
                    nameW
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
            float nameW
    ) {
    }
}
