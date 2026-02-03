package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.gui.hud.HudEditorScreen;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PotionHud extends HudModule {
    private final NumberValue<Double> scale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.05);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 16.0, 1.0);
    private final BoolValue showBackground = new BoolValue("Background", "背景", false);
    private final BoolValue showHeader = new BoolValue("Header", "标题", true, showBackground::get);
    private final BoolValue showIcon = new BoolValue("Icon", "图标", true);
    private final BoolValue glowIcon = new BoolValue("IconGlow", "图标光晕", true, showIcon::get);
    private final BoolValue showProgress = new BoolValue("Progress", "进度条", true);
    private final BoolValue coloredName = new BoolValue("ColoredName", "名称随效果颜色", true);
    private final BoolValue backgroundBlur = new BoolValue("Blur", "模糊", true, showBackground::get);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 2.0, 24.0, 0.5, () -> backgroundBlur.get());
    private final BoolValue bloom = new BoolValue("Bloom", "光晕", true);
    private final EnumValue<Align> align = new EnumValue<>("Align", "对齐", Align.Left);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", "背景颜色", new Color(18, 18, 18, 120));
    private final ColorValue itemColor = new ColorValue("ItemBackground", "条目背景", new Color(20, 20, 20, 140));
    private final ColorValue textColor = new ColorValue("Text", "文字颜色", new Color(255, 255, 255, 230));
    private final ColorValue secondaryTextColor = new ColorValue("SecondaryText", "次级文字颜色", new Color(200, 200, 200, 200));

    private final Map<StatusEffect, EffectEntry> entries = new LinkedHashMap<>();
    private float animWidth = 0f;
    private float animHeight = 0f;

    public PotionHud() {
        super("PotionHud", "药水显示", 10, 220);
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.player == null) return;

        float s = scale.get().floatValue();
        boolean inEditor = mc.currentScreen instanceof HudEditorScreen;

        List<StatusEffectInstance> activeEffects = new ArrayList<>(mc.player.getStatusEffects());
        List<EffectEntry> activeEntries = updateEntries(activeEffects);

        boolean placeholder = activeEntries.isEmpty();
        if (placeholder && !inEditor) {
            animWidth = 0;
            animHeight = 0;
            width = 0;
            height = 0;
            return;
        }

        List<EffectEntry> layoutEntries = activeEntries;
        List<EffectEntry> renderEntries;
        if (placeholder) {
            EffectEntry sample = new EffectEntry(null);
            sample.name = "No Effects";
            sample.durationText = "--:--";
            sample.maxDuration = 1;
            sample.duration = 0;
            sample.progress = 0;
            sample.order = 0;
            layoutEntries = Collections.singletonList(sample);
            renderEntries = layoutEntries;
        } else {
            renderEntries = buildRenderEntries(activeEntries);
        }

        Layout layout = calculateLayout(layoutEntries, s);
        updateLayoutPositions(renderEntries, layout, s);

        float targetWidth = layout.panelWidth;
        float targetHeight = layout.panelHeight;
        animWidth = smooth(animWidth == 0 ? targetWidth : animWidth, targetWidth, 0.2f);
        animHeight = smooth(animHeight == 0 ? targetHeight : animHeight, targetHeight, 0.2f);

        if (showBackground.get() && backgroundBlur.get()) {
            Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, y, animWidth, animHeight, layout.panelRadius, new Color(0, 0, 0, 0), blurStrength.get().floatValue(), 0.9f);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (showBackground.get()) {
                if (bloom.get()) {
                    NanoVGHelper.drawRoundRectBloom(x, y, animWidth, animHeight, layout.panelRadius, backgroundColor.get());
                } else {
                    NanoVGHelper.drawRoundRect(x, y, animWidth, animHeight, layout.panelRadius, backgroundColor.get());
                }
            }

            if (layout.showHeader) {
                int headerFont = FontLoader.bold(12);
                float headerFontSize = 12 * s;
                float headerTextY = y + layout.paddingY + headerFontSize;
                float headerTextX = layout.alignRight
                        ? x + animWidth - layout.paddingX - NanoVGHelper.getTextWidth("Potions", headerFont, headerFontSize)
                        : x + layout.paddingX;
                NanoVGHelper.drawString("Potions", headerTextX, headerTextY, headerFont, headerFontSize, textColor.get());
                float lineY = y + layout.paddingY + layout.headerHeight - 2 * s;
                NanoVGHelper.drawGradientRRect2(x + layout.paddingX, lineY, animWidth - layout.paddingX * 2, 1.2f * s, 0, ClickGui.color(0), ClickGui.color2(0));
            }

            for (EffectEntry entry : renderEntries) {
                drawEntry(context, entry, layout, s);
            }
        });

        width = animWidth;
        height = animHeight;
    }

    private List<EffectEntry> updateEntries(List<StatusEffectInstance> activeEffects) {
        List<EffectEntry> ordered = new ArrayList<>();
        Set<StatusEffect> updated = new HashSet<>();
        int index = 0;
        for (StatusEffectInstance instance : activeEffects) {
            StatusEffect effect = instance.getEffectType().value();
            EffectEntry entry = entries.computeIfAbsent(effect, EffectEntry::new);
            entry.effectInstance = instance;
            entry.name = getEffectName(instance);
            entry.durationText = getDuration(instance);
            entry.amplifier = instance.getAmplifier();
            entry.duration = instance.getDuration();
            entry.maxDuration = Math.max(entry.maxDuration, entry.duration);
            entry.infinite = instance.isInfinite();
            entry.removing = false;
            entry.order = index++;
            updated.add(effect);
            ordered.add(entry);
        }

        for (Iterator<Map.Entry<StatusEffect, EffectEntry>> it = entries.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<StatusEffect, EffectEntry> entry = it.next();
            if (!updated.contains(entry.getKey())) {
                entry.getValue().removing = true;
                if (entry.getValue().offscreen) {
                    it.remove();
                }
            }
        }
        return ordered;
    }

    private List<EffectEntry> buildRenderEntries(List<EffectEntry> activeEntries) {
        List<EffectEntry> renderEntries = new ArrayList<>(activeEntries);
        for (EffectEntry entry : entries.values()) {
            if (entry.removing && !renderEntries.contains(entry)) {
                renderEntries.add(entry);
            }
        }
        renderEntries.sort(Comparator.comparingInt(e -> e.order));
        return renderEntries;
    }

    private Layout calculateLayout(List<EffectEntry> renderEntries, float s) {
        float paddingX = 6f * s;
        float paddingY = 6f * s;
        float headerFontSize = 12 * s;
        float headerHeight = (showBackground.get() && showHeader.get()) ? headerFontSize + 6f * s : 0f;
        float itemHeight = 20f * s;
        float itemGap = 6f * s;
        float iconBox = showIcon.get() ? 20f * s : 0f;
        float iconGap = showIcon.get() ? 4f * s : 0f;
        float infoPadding = 6f * s;
        float nameFontSize = 11f * s;
        float timeFontSize = 9f * s;
        int nameFont = FontLoader.medium(11);
        int timeFont = FontLoader.medium(9);

        float maxTimeWidth = 0f;
        for (EffectEntry entry : renderEntries) {
            maxTimeWidth = Math.max(maxTimeWidth, NanoVGHelper.getTextWidth(entry.durationText, timeFont, timeFontSize));
        }

        float maxItemWidth = 0f;
        for (EffectEntry entry : renderEntries) {
            float nameWidth = NanoVGHelper.getTextWidth(entry.name, nameFont, nameFontSize);
            float infoWidth = Math.max(60f * s, nameWidth + maxTimeWidth + infoPadding * 2 + 6f * s);
            float itemWidth = iconBox + iconGap + infoWidth;
            entry.targetInfoWidth = infoWidth;
            entry.targetWidth = itemWidth;
            maxItemWidth = Math.max(maxItemWidth, itemWidth);
        }

        float listHeight = renderEntries.size() * itemHeight + Math.max(0, renderEntries.size() - 1) * itemGap;
        float panelHeight = headerHeight + paddingY * 2 + listHeight;
        float panelWidth = maxItemWidth + paddingX * 2;
        float panelRadius = radius.get().floatValue() * s;

        return new Layout(panelWidth, panelHeight, paddingX, paddingY, headerHeight, itemHeight, itemGap, iconBox, iconGap, infoPadding, panelRadius, maxTimeWidth, showBackground.get() && showHeader.get(), align.get() == Align.Right);
    }

    private void updateLayoutPositions(List<EffectEntry> renderEntries, Layout layout, float s) {
        float listStartY = y + layout.paddingY + layout.headerHeight + 3f * s;
        for (EffectEntry entry : renderEntries) {
            float baseX = layout.alignRight ? x + layout.panelWidth - layout.paddingX - entry.width : x + layout.paddingX;
            float targetX = entry.removing ? x - entry.width - 30f * s : baseX;
            float targetY = entry.removing ? entry.y : listStartY + entry.order * (layout.itemHeight + layout.itemGap);
            entry.width = smooth(entry.width == 0 ? entry.targetWidth : entry.width, entry.targetWidth, 0.25f);
            entry.x = smooth(entry.x == 0 ? targetX : entry.x, targetX, 0.2f);
            entry.y = smooth(entry.y == 0 ? targetY : entry.y, targetY, 0.2f);
            entry.progress = smooth(entry.progress, entry.infinite ? 1f : MathHelper.clamp(entry.duration / (float) Math.max(1, entry.maxDuration), 0f, 1f), 0.25f);
            entry.offscreen = entry.x < x - entry.width - 40f * s;
        }
    }

    private void drawEntry(DrawContext context, EffectEntry entry, Layout layout, float s) {
        float itemX = entry.x;
        float itemY = entry.y;
        float itemHeight = layout.itemHeight;
        float iconBox = layout.iconBox;
        float iconGap = layout.iconGap;
        float infoX = itemX + iconBox + iconGap;
        float infoWidth = Math.max(10f * s, entry.targetInfoWidth);

        float itemRadius = Math.max(2f, layout.panelRadius * 0.6f);
        Color iconBg = ColorUtil.applyOpacity(itemColor.get(), 0.9f);
        Color infoBg = itemColor.get();
        NanoVGHelper.drawRoundRect(infoX, itemY, infoWidth, itemHeight, itemRadius, infoBg);

        if (iconBox > 0) {
            NanoVGHelper.drawRoundRect(itemX, itemY, iconBox, itemHeight, itemRadius, iconBg);
            if (entry.effectInstance != null) {
                float iconSize = 16f * s;
                float iconX = itemX + (iconBox - iconSize) / 2f;
                float iconY = itemY + (itemHeight - iconSize) / 2f;
                if (glowIcon.get()) {
                    drawIconGlow(entry.effectInstance, iconX + iconSize / 2f, iconY + iconSize / 2f, iconSize / 2f, entry.order, s);
                }
                drawEffectIcon(context, entry.effectInstance, iconX, iconY, iconSize);
            }
        }

        Color accent1 = ClickGui.color(0);
        Color accent2 = ClickGui.color2(0);
        NanoVGHelper.drawGradientRRect2(infoX, itemY, 2f * s, itemHeight, 0, accent1, accent2);

        int nameFont = FontLoader.medium(11);
        int timeFont = FontLoader.medium(9);
        float nameFontSize = 11f * s;
        float timeFontSize = 9f * s;
        float nameX = infoX + layout.infoPadding;
        float timeX = infoX + infoWidth - layout.infoPadding - layout.maxTimeWidth;
        float textY = itemY + itemHeight / 2f + nameFontSize / 2.8f;
        float timeY = itemY + itemHeight / 2f + timeFontSize / 2.8f;

        Color nameColor = textColor.get();
        if (coloredName.get() && entry.effectInstance != null) {
            Color effectColor = new Color(entry.effectInstance.getEffectType().value().getColor());
            nameColor = ColorUtil.interpolateColorC(effectColor, textColor.get(), 0.35f);
        }

        NanoVGHelper.drawString(entry.name, nameX, textY, nameFont, nameFontSize, nameColor);
        NanoVGHelper.drawString(entry.durationText, timeX, timeY, timeFont, timeFontSize, secondaryTextColor.get());

        float separatorX = timeX - 4f * s;
        NanoVGHelper.drawRect(separatorX, itemY + 4f * s, 1f * s, itemHeight - 8f * s, new Color(255, 255, 255, 40));

        if (showProgress.get()) {
            float barHeight = 2.2f * s;
            float barY = itemY + itemHeight - barHeight - 2f * s;
            float barWidth = infoWidth - layout.infoPadding * 2;
            float progressWidth = barWidth * entry.progress;
            float barX = infoX + layout.infoPadding;
            NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, barHeight / 2f, new Color(0, 0, 0, 90));
            NanoVGHelper.drawGradientRRect2(barX, barY, progressWidth, barHeight, barHeight / 2f, accent1, accent2);
        }
    }

    private void drawEffectIcon(DrawContext context, StatusEffectInstance instance, float x, float y, float size) {
        if (mc == null || mc.getStatusEffectSpriteManager() == null) return;
        int ix = Math.round(x);
        int iy = Math.round(y);
        int is = Math.round(size);
        Sprite sprite = mc.getStatusEffectSpriteManager().getSprite(instance.getEffectType());
        if (sprite == null) return;
        context.drawSpriteStretched(RenderLayer::getGuiTextured, sprite, ix, iy, is, is);
    }

    private void drawIconGlow(StatusEffectInstance instance, float cx, float cy, float radius, int order, float s) {
        Color base = new Color(instance.getEffectType().value().getColor());
        float t = (System.currentTimeMillis() / 280f) + (order * 0.7f);
        float pulse = (MathHelper.sin(t) + 1f) * 0.5f;
        Color outer = ColorUtil.applyOpacity(base, 0.12f + 0.28f * pulse);
        Color inner = ColorUtil.applyOpacity(ColorUtil.interpolateColorC(base, Color.WHITE, 0.25f), 0.08f + 0.2f * pulse);
        NanoVGHelper.drawCircle(cx, cy, radius + 2f * s, outer);
        NanoVGHelper.drawCircle(cx, cy, radius + 0.6f * s, inner);
    }

    private String getEffectName(StatusEffectInstance instance) {
        String name = instance.getEffectType().value().getName().getString();
        int amp = instance.getAmplifier();
        if (amp <= 0) return name;
        return name + " " + toRoman(amp + 1);
    }

    private String getDuration(StatusEffectInstance instance) {
        if (instance.isInfinite()) return "*:*";
        int ticks = instance.getDuration();
        int mins = ticks / 1200;
        int secs = (ticks % 1200) / 20;
        return mins + ":" + String.format("%02d", secs);
    }

    private String toRoman(int value) {
        return switch (value) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> String.valueOf(value);
        };
    }

    private float smooth(float current, float target, float speed) {
        return current + (target - current) * MathHelper.clamp(speed, 0f, 1f);
    }

    private static class Layout {
        final float panelWidth;
        final float panelHeight;
        final float paddingX;
        final float paddingY;
        final float headerHeight;
        final float itemHeight;
        final float itemGap;
        final float iconBox;
        final float iconGap;
        final float infoPadding;
        final float panelRadius;
        final float maxTimeWidth;
        final boolean showHeader;
        final boolean alignRight;

        Layout(float panelWidth, float panelHeight, float paddingX, float paddingY, float headerHeight, float itemHeight, float itemGap, float iconBox, float iconGap, float infoPadding, float panelRadius, float maxTimeWidth, boolean showHeader, boolean alignRight) {
            this.panelWidth = panelWidth;
            this.panelHeight = panelHeight;
            this.paddingX = paddingX;
            this.paddingY = paddingY;
            this.headerHeight = headerHeight;
            this.itemHeight = itemHeight;
            this.itemGap = itemGap;
            this.iconBox = iconBox;
            this.iconGap = iconGap;
            this.infoPadding = infoPadding;
            this.panelRadius = panelRadius;
            this.maxTimeWidth = maxTimeWidth;
            this.showHeader = showHeader;
            this.alignRight = alignRight;
        }
    }

    private enum Align {
        Left,
        Right
    }

    private static class EffectEntry {
        final StatusEffect effect;
        StatusEffectInstance effectInstance;
        String name = "";
        String durationText = "";
        float x;
        float y;
        float width;
        float targetWidth;
        float targetInfoWidth;
        float progress;
        int duration;
        int maxDuration;
        int amplifier;
        boolean infinite;
        boolean removing;
        boolean offscreen;
        int order;

        EffectEntry(StatusEffect effect) {
            this.effect = effect;
        }
    }
}
