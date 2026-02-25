package dev.mzc.client.module.impl.hud;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.utils.render.Shader2DUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.client.texture.Sprite;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PotionHud extends HudModule {
    private final java.util.Map<String, EaseInOutQuad> animations = new java.util.HashMap<>();
    private final java.util.Map<String, Row> rowCache = new java.util.HashMap<>();
    private java.util.Set<String> previousKeys = new java.util.HashSet<>();
    private final java.util.Map<String, Integer> durationMax = new java.util.HashMap<>();
    private enum DisplayMode {
        List("列表"),
        Card("卡片"),
        Split("分割"),
        LiquidGlass("LiquidGlass");

        private final String cnName;

        DisplayMode(String cnName) {
            this.cnName = cnName;
        }
    }
    private final EnumValue<DisplayMode> displayMode = new EnumValue<>("Mode", "模式", DisplayMode.List, DisplayMode.class);
    private final ColorValue backgroundColor = new ColorValue("Background", "背景颜色", new Color(0, 0, 0, 110));
    private final ColorValue progressColor = new ColorValue("ProgressColor", "进度条颜色", new Color(0, 0, 0, 50));
    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final NumberValue<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);
    private final NumberValue<Integer> animDuration = new NumberValue<>("AnimDuration", "动画时长", 250, 50, 1000, 50);
    private final NumberValue<Double> listPadding = new NumberValue<>("ListPadding", "列表内边距", 8.0, 4.0, 16.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listRowHeight = new NumberValue<>("ListRowHeight", "列表行高", 18.0, 12.0, 26.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listIconSize = new NumberValue<>("ListIconSize", "列表图标大小", 18.0, 10.0, 32.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listIconGap = new NumberValue<>("ListIconGap", "列表图标间距", 6.0, 2.0, 12.0, 0.5, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> cardPadding = new NumberValue<>("CardPadding", "卡片内边距", 8.0, 4.0, 16.0, 1.0, () -> !displayMode.is(DisplayMode.List));
    private final NumberValue<Double> cardRowHeight = new NumberValue<>("CardRowHeight", "卡片高度", 22.0, 14.0, 30.0, 1.0, () -> !displayMode.is(DisplayMode.List));
    private final NumberValue<Double> cardIconSize = new NumberValue<>("CardIconSize", "卡片图标大小", 18.0, 10.0, 32.0, 1.0, () -> !displayMode.is(DisplayMode.List));
    private final NumberValue<Double> cardIconGap = new NumberValue<>("CardIconGap", "卡片图标间距", 6.0, 2.0, 12.0, 0.5, () -> !displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listItemGap = new NumberValue<>("ListItemGap", "列表行间距", 2.0, 0.0, 10.0, 0.5, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> liquidGlassBlurStrength = new NumberValue<>("LGBlurStrength", "模糊强度", 18.0, 4.0, 280.0, 1.0, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassRefractionAmount = new NumberValue<>("LGRefractionAmount", "折射强度", 0.6, 0.0, 0.9, 0.001, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassRefractionBand = new NumberValue<>("LGRefractionBand", "折射带宽", 2.8, 0.0, 8.0, 0.1, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassRefractionStrength = new NumberValue<>("LGRefractionStrength", "折射混合强度", 0.85, 0.0, 1.5, 0.05, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassLensCurvature = new NumberValue<>("LGLensCurvature", "透镜曲率", 1.6, 0.5, 4.0, 0.1, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassSaturation = new NumberValue<>("LGSaturation", "饱和度", 100.0, 0.0, 200.0, 1.0, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassBrightness = new NumberValue<>("LGBrightness", "亮度", 100.0, 0.0, 200.0, 1.0, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final BoolValue liquidGlassBloom = new BoolValue("LGBloom", "光晕", true, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final NumberValue<Double> liquidGlassBloomRadius = new NumberValue<>("LGBloomRadius", "光晕半径", 8.0, 0.0, 15.0, 1.0, () -> displayMode.is(DisplayMode.LiquidGlass) && liquidGlassBloom.get());
    private final ColorValue liquidGlassBloomColor = new ColorValue("LGBloomColor", "光晕颜色", new Color(0, 0, 0, 50), () -> displayMode.is(DisplayMode.LiquidGlass) && liquidGlassBloom.get());
    private final BoolValue liquidGlassBg = new BoolValue("LGBg", "背景", true, () -> displayMode.is(DisplayMode.LiquidGlass));
    private final ColorValue liquidGlassBgColor = new ColorValue("LGBgColor", "背景颜色", new Color(0, 0, 0, 50), () -> displayMode.is(DisplayMode.LiquidGlass) && liquidGlassBg.get());
    private final NumberValue<Double> iconOffsetX = new NumberValue<>("IconOffsetX", "图标X偏移", 0.0, -20.0, 20.0, 1.0);
    private final NumberValue<Double> iconOffsetY = new NumberValue<>("IconOffsetY", "图标Y偏移", 0.0, -20.0, 20.0, 1.0);

    public PotionHud() {
        super("PotionHud", "药水HUD", 8, 80);
        this.width = 150;
        this.height = 80;
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.player == null) return;

        java.util.List<Row> currentRows = collectRows();
        java.util.Map<String, Row> currentMap = new java.util.HashMap<>();
        for (Row r : currentRows) currentMap.put(r.key, r);
        rowCache.putAll(currentMap);

        java.util.Set<String> currentKeys = currentMap.keySet();
        for (String k : currentKeys) {
            boolean isNew = !previousKeys.contains(k);
            EaseInOutQuad a = animations.computeIfAbsent(k, kk -> new EaseInOutQuad(animDuration.get(), 1.0));
            a.setDirection(Direction.FORWARDS);
            if (isNew) a.reset();
        }
        for (String k : new java.util.ArrayList<>(previousKeys)) {
            if (!currentKeys.contains(k)) {
                EaseInOutQuad a = animations.get(k);
                if (a != null) {
                    a.setDirection(Direction.BACKWARDS);
                    a.reset();
                }
            }
        }
        animations.entrySet().removeIf(e -> e.getValue().finished(Direction.BACKWARDS));
        previousKeys = new java.util.HashSet<>(currentKeys);

        java.util.List<Row> renderRows = new java.util.ArrayList<>();
        for (String k : animations.keySet()) {
            Row r = currentMap.getOrDefault(k, rowCache.get(k));
            if (r != null) renderRows.add(r);
        }

        double scale = hudScale.get();
        boolean isLiquidGlass = displayMode.is(DisplayMode.LiquidGlass);
        boolean isList = displayMode.is(DisplayMode.List);
        boolean isSplit = displayMode.is(DisplayMode.Split);
        float pad = (isList ? listPadding.get() : cardPadding.get()).floatValue();
        float rh = (isList ? listRowHeight.get() : cardRowHeight.get()).floatValue();
        float isz = (isList ? listIconSize.get() : cardIconSize.get()).floatValue();
        float igap = (isList ? listIconGap.get() : cardIconGap.get()).floatValue();

        float contentW = width;
        float drawW = (float) (contentW * scale);

        if (isList) {
            float contentH = pad * 2;
            for (Row r : renderRows) {
                EaseInOutQuad a = animations.get(r.key);
                float v = a == null ? 1f : a.getOutput().floatValue();
                contentH += (rh + listItemGap.get().floatValue()) * v;
            }
            float drawH = (float) (contentH * scale);
            this.height = drawH;
            if (renderRows.isEmpty() || drawH <= pad * 2 * scale + 0.5f) return;

            Color bg = backgroundColor.get();
            NanoVGRenderer.INSTANCE.draw(vg -> {
                if (enableBloom.get()) {
                    NanoVGHelper.drawRoundRectBloom(x, y, drawW, drawH, radius.get().floatValue(), bg);
                } else {
                    NanoVGHelper.drawRoundRect(x, y, drawW, drawH, radius.get().floatValue(), bg);
                }
            });

            int baseX = (int) (x + pad * scale);
            float baseY = (float) (y + pad * scale);

            float fontSize = 11f;
            int font = FontLoader.medium(fontSize);
            int fontGrey = FontLoader.regular(10f);
            NanoVGRenderer.INSTANCE.draw(vg -> {
                float yOffset = 0f;
                for (Row r : renderRows) {
                    EaseInOutQuad a = animations.get(r.key);
                    float v = a == null ? 1f : a.getOutput().floatValue();
                    float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                    int rowY = (int) (baseY + yOffset + slideOffset);
                    int iconWH = (int) (isz * scale);
                    int textX = (int) (baseX + (isz + igap) * scale - (1f - v) * 8f);
                    float baselineY = rowY + iconWH / 2f + fontSize * 0.35f;
                    NanoVGHelper.drawString(r.left.getString(), textX, baselineY, font, fontSize, new Color(255, 255, 255, (int) (255 * v)));
                    String rightStr = r.right.getString();
                    float rightW = NanoVGHelper.getTextWidth(rightStr, fontGrey, 10f);
                    float rightX = (float) (x + drawW - pad * scale - rightW);
                    float rightBaselineY = rowY + iconWH / 2f + 10f * 0.35f;
                    NanoVGHelper.drawString(rightStr, rightX, rightBaselineY, fontGrey, 10f, new Color(160, 160, 160, (int) (255 * v)));
                    yOffset += (rh + listItemGap.get().floatValue()) * scale * v;
                }
            });
            {
                float yOffset = 0f;
                for (Row r : renderRows) {
                    EaseInOutQuad a = animations.get(r.key);
                    float v = a == null ? 1f : a.getOutput().floatValue();
                    float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                    int rowY = (int) (baseY + yOffset + slideOffset);
                    int iconWH = (int) (isz * scale);
                    int iconX = (int) (baseX + iconOffsetX.get().floatValue() * scale);
                    int iconY = (int) (rowY + iconOffsetY.get().floatValue() * scale);
                    drawEffectSprite(context, r.effectEntry, iconX, iconY, iconWH, v);
                    yOffset += (rh + listItemGap.get().floatValue()) * scale * v;
                }
            }
        } else {
            float gap = pad * 0.5f;
            float cardHBase = rh;
            float totalH = pad;
            for (Row r : renderRows) {
                EaseInOutQuad a = animations.get(r.key);
                float v = a == null ? 1f : a.getOutput().floatValue();
                totalH += (cardHBase + gap) * v;
            }
            totalH += pad - gap;
            float drawH = (float) (totalH * scale);
            this.height = drawH;
            if (renderRows.isEmpty()) return;

            int baseX = (int) (x + pad * scale);
            final float[] curY = {(float) (y + pad * scale)};
            float fontSize = 11f;
            int font = FontLoader.medium(fontSize);
            int fontGrey = FontLoader.regular(10f);
            Color bg = backgroundColor.get();

            if (isLiquidGlass) {
                float curYBlur = curY[0];
                float sat = liquidGlassSaturation.get().floatValue() / 100.0f;
                float bri = liquidGlassBrightness.get().floatValue() / 100.0f;
                Color baseBg = liquidGlassBg.get() ? liquidGlassBgColor.get() : new Color(0, 0, 0, 0);
                Color glassColor = applySaturationBrightness(baseBg, sat, bri);

                Shader2DUtil.setRefraction(liquidGlassRefractionAmount.get().floatValue(), liquidGlassRefractionBand.get().floatValue());
                Shader2DUtil.setRefractionStrength(liquidGlassRefractionStrength.get().floatValue());
                Shader2DUtil.setLensCurvature(liquidGlassLensCurvature.get().floatValue());

                for (Row r : renderRows) {
                    EaseInOutQuad a = animations.get(r.key);
                    float v = a == null ? 1f : a.getOutput().floatValue();
                    float slideOffset = (float) ((1f - v) * cardHBase * scale * 0.4f);
                    float cardH = (float) (cardHBase * scale);
                    float cardY = curYBlur + slideOffset;
                    float cardX = (float) (x + pad * scale);
                    float cardW = (float) (drawW - pad * 2 * scale);

                    float splitGap = 3f * (float) scale;
                    float leftW = cardH;
                    float rightX = cardX + leftW + splitGap;
                    float rightW = cardW - leftW - splitGap;

                    Shader2DUtil.drawRoundedBlur(context.getMatrices(), cardX, cardY, leftW, cardH, radius.get().floatValue(), glassColor, liquidGlassBlurStrength.get().floatValue(), 1.0f);
                    Shader2DUtil.drawRoundedBlur(context.getMatrices(), rightX, cardY, rightW, cardH, radius.get().floatValue(), glassColor, liquidGlassBlurStrength.get().floatValue(), 1.0f);

                    curYBlur += (cardHBase + gap) * scale * v;
                }
            }

            NanoVGRenderer.INSTANCE.draw(vg -> {
                for (Row r : renderRows) {
                    EaseInOutQuad a = animations.get(r.key);
                    float v = a == null ? 1f : a.getOutput().floatValue();
                    float slideOffset = (float) ((1f - v) * cardHBase * scale * 0.4f);
                    float cardH = (float) (cardHBase * scale);
                    float cardY = curY[0] + slideOffset;
                    float cardX = (float) (x + pad * scale);
                    float cardW = (float) (drawW - pad * 2 * scale);

                    if (isSplit || isLiquidGlass) {
                        float splitGap = 3f * (float) scale;
                        float leftW = cardH;
                        float rightX = cardX + leftW + splitGap;
                        float rightW = cardW - leftW - splitGap;

                        if (isLiquidGlass) {
                            if (liquidGlassBloom.get()) {
                                float bloomR = liquidGlassBloomRadius.get().floatValue();
                                Color bloomC = liquidGlassBloomColor.get();
                                NanoVGHelper.drawRoundRectBloomOutline(cardX, cardY, leftW, cardH, radius.get().floatValue(), bloomC, bloomR);
                                NanoVGHelper.drawRoundRectBloomOutline(rightX, cardY, rightW, cardH, radius.get().floatValue(), bloomC, bloomR);
                            }
                        } else {
                            // Backgrounds
                            if (enableBloom.get()) {
                                NanoVGHelper.drawRoundRectBloom(cardX, cardY, leftW, cardH, radius.get().floatValue(), bg);
                                NanoVGHelper.drawRoundRectBloom(rightX, cardY, rightW, cardH, radius.get().floatValue(), bg);
                            } else {
                                NanoVGHelper.drawRoundRect(cardX, cardY, leftW, cardH, radius.get().floatValue(), bg);
                                NanoVGHelper.drawRoundRect(rightX, cardY, rightW, cardH, radius.get().floatValue(), bg);
                            }

                            // Progress
                            Integer maxDur = durationMax.getOrDefault(r.key, r.durationTicks);
                            float prog = Math.max(0f, Math.min(1f, r.durationTicks / (float) Math.max(1, maxDur)));
                            NanoVGHelper.drawRoundRect(rightX, cardY, rightW * prog, cardH, radius.get().floatValue(), progressColor.get());
                        }

                        // Text
                        int textX = (int) (rightX + pad * 0.5f * scale);
                        float baselineY = (float) (cardY + cardH / 2f + fontSize * 0.35f);
                        NanoVGHelper.drawString(r.left.getString(), textX, baselineY, font, fontSize, new Color(255, 255, 255, (int) (255 * v)));

                        String rightStr = r.right.getString();
                        float rightWVal = NanoVGHelper.getTextWidth(rightStr, fontGrey, 10f);
                        float rightTextX = (float) (rightX + rightW - pad * 0.5f * scale - rightWVal);
                        float rightBaselineY = (float) (cardY + cardH / 2f + 10f * 0.35f);
                        NanoVGHelper.drawString(rightStr, rightTextX, rightBaselineY, fontGrey, 10f, new Color(160, 160, 160, (int) (255 * v)));
                    } else {
                        if (enableBloom.get()) {
                            NanoVGHelper.drawRoundRectBloom(cardX, cardY, cardW, cardH, radius.get().floatValue(), bg);
                        } else {
                            NanoVGHelper.drawRoundRect(cardX, cardY, cardW, cardH, radius.get().floatValue(), bg);
                        }

                        Integer maxDur = durationMax.getOrDefault(r.key, r.durationTicks);
                        float prog = Math.max(0f, Math.min(1f, r.durationTicks / (float) Math.max(1, maxDur)));
                        Color overlay = new Color(r.iconColor.getRed(), r.iconColor.getGreen(), r.iconColor.getBlue(), backgroundColor.get().getAlpha());
                        NanoVGHelper.drawRoundRect(cardX, cardY, cardW * prog, cardH, radius.get().floatValue(), overlay);

                        int iconWH = (int) (isz * scale);
                        int iconX = (int) (cardX + pad * 0.5f * scale + iconOffsetX.get().floatValue() * scale);
                        int iconY = (int) (cardY + (cardH - iconWH) / 2f + iconOffsetY.get().floatValue() * scale);
                        // defer sprite drawing outside NVG block
                        int textX = (int) (iconX + iconWH + igap * scale);
                        float baselineY = (float) (cardY + cardH / 2f + fontSize * 0.35f);
                        NanoVGHelper.drawString(r.left.getString(), textX, baselineY, font, fontSize, new Color(255, 255, 255, (int) (255 * v)));

                        String rightStr = r.right.getString();
                        float rightW = NanoVGHelper.getTextWidth(rightStr, fontGrey, 10f);
                        float rightX = (float) (cardX + cardW - pad * 0.5f * scale - rightW);
                        float rightBaselineY = (float) (cardY + cardH / 2f + 10f * 0.35f);
                        NanoVGHelper.drawString(rightStr, rightX, rightBaselineY, fontGrey, 10f, new Color(160, 160, 160, (int) (255 * v)));
                    }

                    curY[0] += (cardHBase + gap) * scale * v;
                }
            });
            {
                float curY2 = (float) (y + pad * scale);
                for (Row r : renderRows) {
                    EaseInOutQuad a = animations.get(r.key);
                    float v = a == null ? 1f : a.getOutput().floatValue();
                    float slideOffset = (float) ((1f - v) * cardHBase * scale * 0.4f);
                    float cardH = (float) (cardHBase * scale);
                    float cardY = curY2 + slideOffset;
                    float cardX = (float) (x + pad * scale);
                    int iconWH = (int) (isz * scale);

                    if (isSplit || isLiquidGlass) {
                        float leftW = cardH;
                        int iconX = (int) (cardX + (leftW - iconWH) / 2f + iconOffsetX.get().floatValue() * scale + 2 * scale);
                        int iconY = (int) (cardY + (cardH - iconWH) / 2f + iconOffsetY.get().floatValue() * scale);
                        drawEffectSprite(context, r.effectEntry, iconX, iconY, iconWH, v);
                    } else {
                        int iconX = (int) (cardX + pad * 0.5f * scale + iconOffsetX.get().floatValue() * scale);
                        int iconY = (int) (cardY + (cardH - iconWH) / 2f + iconOffsetY.get().floatValue() * scale);
                        drawEffectSprite(context, r.effectEntry, iconX, iconY, iconWH, v);
                    }
                    curY2 += (cardHBase + gap) * scale * v;
                }
            }
        }
    }

    private List<Row> collectRows() {
        List<Row> rows = new ArrayList<>();
        for (StatusEffectInstance se : mc.player.getStatusEffects()) {
            String name = getEffectName(se);
            String time = formatDuration(se.getDuration());
            String key = name;
            int ticks = se.getDuration();
            durationMax.put(key, Math.max(durationMax.getOrDefault(key, 0), ticks));
            RegistryEntry<StatusEffect> entry = se.getEffectType();
            rows.add(new Row(key, Text.of(name), Text.of(time), getIconColor(se), ticks, entry));
        }
        return rows;
    }

    private String getEffectName(StatusEffectInstance se) {
        int amp = se.getAmplifier();
        String ampStr = amp <= 0 ? "" : " " + (amp + 1);
        return se.getEffectType().value().getName().getString() + ampStr;
    }

    private String formatDuration(int ticks) {
        int seconds = Math.max(0, ticks / 20);
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%d:%02d", m, s);
    }

    private Color getIconColor(StatusEffectInstance se) {
        try {
            int argb = se.getEffectType().value().getColor();
            int rgba = (argb & 0xFFFFFF) | 0xFF000000;
            return new Color(rgba, true);
        } catch (Throwable t) {
            return new Color(120, 120, 255, 180);
        }
    }

    private static Color applySaturationBrightness(Color color, float saturation, float brightness) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float s = Math.max(0f, Math.min(1f, hsb[1] * saturation));
        float b = Math.max(0f, Math.min(1f, hsb[2] * brightness));
        int rgb = Color.HSBtoRGB(hsb[0], s, b);
        // 使用 color 的 alpha 重新组合颜色
        int alpha = color.getAlpha();
        return new Color((rgb & 0xFFFFFF) | (alpha << 24), true);
    }

    private static class Row {
        final String key;
        final Text left;
        final Text right;
        final Color iconColor;
        final int durationTicks;
        final RegistryEntry<StatusEffect> effectEntry;
        Row(String key, Text left, Text right, Color iconColor, int durationTicks, RegistryEntry<StatusEffect> effectEntry) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.iconColor = iconColor;
            this.durationTicks = durationTicks;
            this.effectEntry = effectEntry;
        }
    }

    private void drawEffectSprite(DrawContext context, RegistryEntry<StatusEffect> entry, int x, int y, int size, float alpha) {
        Sprite sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(entry);
        if (sprite == null) return;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, Math.max(0f, Math.min(1f, alpha)));
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        Identifier atlas = sprite.getAtlasId();
        RenderSystem.setShaderTexture(0, atlas);

        org.joml.Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        net.minecraft.client.render.BufferBuilder buffer = net.minecraft.client.render.Tessellator.getInstance().begin(net.minecraft.client.render.VertexFormat.DrawMode.QUADS, net.minecraft.client.render.VertexFormats.POSITION_TEXTURE_COLOR);

        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        float u2 = sprite.getMaxU();
        float v2 = sprite.getMaxV();
        int color = new java.awt.Color(255, 255, 255, 255).getRGB();

        buffer.vertex(matrix, x, y, 0).texture(u1, v1).color(color);
        buffer.vertex(matrix, x, y + size, 0).texture(u1, v2).color(color);
        buffer.vertex(matrix, x + size, y + size, 0).texture(u2, v2).color(color);
        buffer.vertex(matrix, x + size, y, 0).texture(u2, v1).color(color);

        net.minecraft.client.render.BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }
}
