package dev.mzc.client.module.impl.hud;

import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmorHud extends HudModule {
    private final java.util.Map<String, EaseInOutQuad> animations = new java.util.HashMap<>();
    private final java.util.Map<String, Row> rowCache = new java.util.HashMap<>();
    private java.util.Set<String> previousKeys = new java.util.HashSet<>();
    
    private enum DisplayMode {
        List("列表"),
        Card("卡片");

        private final String cnName;

        DisplayMode(String cnName) {
            this.cnName = cnName;
        }
    }
    private final EnumValue<DisplayMode> displayMode = new EnumValue<>("Mode", "模式", DisplayMode.List, DisplayMode.class);
    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final NumberValue<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);
    private final ColorValue backgroundColor = new ColorValue("Background", "背景颜色", new Color(0, 0, 0, 110));
    private final NumberValue<Integer> animDuration = new NumberValue<>("AnimDuration", "动画时长", 250, 50, 1000, 50);
    
    // List Mode Settings
    private final NumberValue<Double> listPadding = new NumberValue<>("ListPadding", "列表内边距", 8.0, 4.0, 16.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listRowHeight = new NumberValue<>("ListRowHeight", "列表行高", 18.0, 12.0, 26.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listIconSize = new NumberValue<>("ListIconSize", "列表图标大小", 18.0, 10.0, 32.0, 1.0, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listIconGap = new NumberValue<>("ListIconGap", "列表图标间距", 6.0, 2.0, 12.0, 0.5, () -> displayMode.is(DisplayMode.List));
    private final NumberValue<Double> listItemGap = new NumberValue<>("ListItemGap", "列表行间距", 2.0, 0.0, 10.0, 0.5, () -> displayMode.is(DisplayMode.List));

    // Card Mode Settings
    private final NumberValue<Double> cardPadding = new NumberValue<>("CardPadding", "卡片内边距", 8.0, 4.0, 16.0, 1.0, () -> displayMode.is(DisplayMode.Card));
    private final NumberValue<Double> cardRowHeight = new NumberValue<>("CardRowHeight", "卡片高度", 22.0, 14.0, 30.0, 1.0, () -> displayMode.is(DisplayMode.Card));
    private final NumberValue<Double> cardIconSize = new NumberValue<>("CardIconSize", "卡片图标大小", 18.0, 10.0, 32.0, 1.0, () -> displayMode.is(DisplayMode.Card));
    private final NumberValue<Double> cardIconGap = new NumberValue<>("CardIconGap", "卡片图标间距", 6.0, 2.0, 12.0, 0.5, () -> displayMode.is(DisplayMode.Card));
    
    private final NumberValue<Double> iconOffsetX = new NumberValue<>("IconOffsetX", "图标X偏移", 0.0, -20.0, 20.0, 1.0);
    private final NumberValue<Double> iconOffsetY = new NumberValue<>("IconOffsetY", "图标Y偏移", 0.0, -20.0, 20.0, 1.0);

    public ArmorHud() {
        super("ArmorHud", "装备显示", 8, 80);
        this.width = 150;
        this.height = 80;
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.player == null) return;

        List<Row> currentRows = collectRows();
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

        List<Row> renderRows = new ArrayList<>();
        // Maintain order from collectRows
        for (Row r : currentRows) {
            renderRows.add(r);
        }
        // Also add fading out rows
        for (String k : animations.keySet()) {
            if (!currentMap.containsKey(k)) {
                Row r = rowCache.get(k);
                if (r != null) renderRows.add(r);
            }
        }

        double scale = hudScale.get();
        boolean isList = displayMode.is(DisplayMode.List);
        float pad = (isList ? listPadding.get() : cardPadding.get()).floatValue();
        float rh = (isList ? listRowHeight.get() : cardRowHeight.get()).floatValue();
        float isz = (isList ? listIconSize.get() : cardIconSize.get()).floatValue();
        float igap = (isList ? listIconGap.get() : cardIconGap.get()).floatValue();

        float contentW = width;
        float drawW = (float) (contentW * scale);
        
        // Calculate dimensions
        float contentH = pad;
        if (isList) {
            contentH = pad * 2; // initial padding
            for (Row r : renderRows) {
                EaseInOutQuad a = animations.get(r.key);
                float v = a == null ? 1f : a.getOutput().floatValue();
                contentH += (rh + listItemGap.get().floatValue()) * v;
            }
        } else {
            float gap = pad * 0.5f;
            contentH = pad;
            for (Row r : renderRows) {
                EaseInOutQuad a = animations.get(r.key);
                float v = a == null ? 1f : a.getOutput().floatValue();
                contentH += (rh + gap) * v;
            }
            contentH += pad - gap; // adjust final padding
        }
        
        float drawH = (float) (contentH * scale);
        this.height = drawH;
        
        if (renderRows.isEmpty() || drawH <= pad * 2 * scale + 0.5f) return;

        Color bg = backgroundColor.get();
        
        // Render Background and Text/Bars via NanoVG
        NanoVGRenderer.INSTANCE.draw(vg -> {
            // Background
            if (isList) {
                if (enableBloom.get()) {
                    NanoVGHelper.drawRoundRectBloom(x, y, drawW, drawH, radius.get().floatValue(), bg);
                } else {
                    NanoVGHelper.drawRoundRect(x, y, drawW, drawH, radius.get().floatValue(), bg);
                }
            }

            float fontSize = 11f;
            int font = FontLoader.medium(fontSize);
            int fontGrey = FontLoader.regular(10f);

            float currentY = (float) (y + pad * scale);
            float baseX = (float) (x + pad * scale);

            for (Row r : renderRows) {
                EaseInOutQuad a = animations.get(r.key);
                float v = a == null ? 1f : a.getOutput().floatValue();
                if (v <= 0.01f) continue;

                float rowH = rh * (float) scale;
                float itemScale = (float) (isz / 16.0);
                float iconWH = 16f * itemScale * (float) scale; // Actual rendered size
                
                if (isList) {
                    float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                    float rowY = currentY + slideOffset;
                    
                    // Text rendering
                    int textX = (int) (baseX + iconWH + igap * scale + iconOffsetX.get().floatValue() * scale);
                    float baselineY = rowY + rowH / 2f + fontSize * 0.35f;
                    
                    String durStr = r.durability.getString();
                    float rightW = NanoVGHelper.getTextWidth(durStr, fontGrey, 10f);
                    float rightX = (float) (x + drawW - pad * scale - rightW);
                    float rightBaselineY = rowY + rowH / 2f + 10f * 0.35f;
                    
                    NanoVGHelper.drawString(durStr, rightX, rightBaselineY, fontGrey, 10f, new Color(160, 160, 160, (int)(255 * v)));

                    float availableWidth = (float) (rightX - textX - 5f * scale);
                    if (availableWidth > 0) {
                        NanoVGHelper.save();
                        NanoVGHelper.intersectScissor(textX, rowY, availableWidth, rowH);
                        NanoVGHelper.drawString(r.name.getString(), textX, baselineY, font, fontSize, new Color(255, 255, 255, (int)(255 * v)));
                        NanoVGHelper.restore();
                    }
                    
                    currentY += (rh + listItemGap.get().floatValue()) * scale * v;
                } else {
                    // Card Mode
                    float gap = pad * 0.5f;
                    float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                    float cardY = currentY + slideOffset;
                    float cardW = (float) (drawW - pad * 2 * scale);
                    
                    // Card Background
                    if (enableBloom.get()) {
                        NanoVGHelper.drawRoundRectBloom(baseX, cardY, cardW, rowH, radius.get().floatValue(), bg);
                    } else {
                        NanoVGHelper.drawRoundRect(baseX, cardY, cardW, rowH, radius.get().floatValue(), bg);
                    }
                    
                    // Durability Bar Overlay
                    if (r.maxDamage > 0) {
                        float prog = 1f - (float) r.damage / (float) r.maxDamage;
                        prog = Math.max(0f, Math.min(1f, prog));
                        Color overlay = getDurabilityColor(prog, bg.getAlpha());
                        NanoVGHelper.drawRoundRect(baseX, cardY, cardW * prog, rowH, radius.get().floatValue(), overlay);
                    }
                    
                    // Text
                    int textX = (int) (baseX + pad * 0.5f * scale + iconWH + igap * scale + iconOffsetX.get().floatValue() * scale);
                    float baselineY = cardY + rowH / 2f + fontSize * 0.35f;
                    
                    // Full width available for name (minus right padding)
                    float availableWidth = (float) (cardW - (textX - baseX) - pad * 0.5f * scale);
                    if (availableWidth > 0) {
                        NanoVGHelper.save();
                        NanoVGHelper.intersectScissor(textX, cardY, availableWidth, rowH);
                        NanoVGHelper.drawString(r.name.getString(), textX, baselineY, font, fontSize, new Color(255, 255, 255, (int)(255 * v)));
                        NanoVGHelper.restore();
                    }
 
                    currentY += (rh + gap) * scale * v;
                }
            }
        });

        // Render Items (MatrixStack)
        context.getMatrices().push();
        // Global scaling for items
        // Since we are not scaling the context globally above (we scaled manually in NanoVG),
        // we need to handle item scaling carefully.
        // Actually, PotionHud does manual scaling logic.
        
        float currentY = (float) (y + pad * scale);
        float baseX = (float) (x + pad * scale);

        for (Row r : renderRows) {
            EaseInOutQuad a = animations.get(r.key);
            float v = a == null ? 1f : a.getOutput().floatValue();
            if (v <= 0.01f) continue;

            float rowH = rh * (float) scale;
            float itemScaleFactor = (float) (isz / 16.0); // Base scale relative to 16px
            float finalItemScale = itemScaleFactor * (float) scale; // Applied scale
            
            float iconX, iconY;
            
            if (isList) {
                float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                float rowY = currentY + slideOffset;
                
                iconX = baseX + iconOffsetX.get().floatValue() * (float) scale;
                iconY = rowY + (rowH - 16f * finalItemScale) / 2f + iconOffsetY.get().floatValue() * (float) scale;
                
                currentY += (rh + listItemGap.get().floatValue()) * scale * v;
            } else {
                float gap = pad * 0.5f;
                float slideOffset = (float) ((1f - v) * rh * scale * 0.4f);
                float cardY = currentY + slideOffset;
                
                iconX = baseX + pad * 0.5f * (float) scale + iconOffsetX.get().floatValue() * (float) scale;
                iconY = cardY + (rowH - 16f * finalItemScale) / 2f + iconOffsetY.get().floatValue() * (float) scale;
                
                currentY += (rh + gap) * scale * v;
            }

            context.getMatrices().push();
            context.getMatrices().translate(iconX, iconY, 0);
            context.getMatrices().scale(finalItemScale, finalItemScale, 1f);
            context.drawItem(r.stack, 0, 0);
            // Optional: draw stack overlay (cooldown/durability bar) - disabling for HUD cleanliness or enabling?
            // Usually HUDs don't show the standard durability bar if they show it elsewhere.
            // But let's keep it simple.
            // context.drawStackOverlay(mc.textRenderer, r.stack, 0, 0); 
            context.getMatrices().pop();
        }
        context.getMatrices().pop();
    }

    private List<Row> collectRows() {
        List<Row> rows = new ArrayList<>();
        // Iterate armor slots: 3 (Helmet), 2 (Chest), 1 (Legs), 0 (Boots)
        // Inventory armor list is usually order: feet, legs, chest, head
        List<ItemStack> armor = mc.player.getInventory().armor;
        
        // We want to display typically Head -> Feet
        for (int i = armor.size() - 1; i >= 0; i--) {
            ItemStack stack = armor.get(i);
            if (stack.isEmpty()) continue;
            
            String key = "armor_" + i;
            Text name = stack.getName();
            
            Text durText;
            int damage = 0;
            int maxDamage = 0;
            
            if (stack.isDamageable()) {
                damage = stack.getDamage();
                maxDamage = stack.getMaxDamage();
                int remaining = maxDamage - damage;
                durText = Text.of(remaining + "/" + maxDamage);
            } else {
                durText = Text.of(String.valueOf(stack.getCount()));
            }
            
            rows.add(new Row(key, name, durText, stack, damage, maxDamage));
        }
        return rows;
    }
    
    private Color getDurabilityColor(float percent, int alpha) {
        // Red to Green
        int r = (int) (255 * (1f - percent));
        int g = (int) (255 * percent);
        return new Color(Math.min(255, r * 2), Math.min(255, g * 2), 0, alpha);
    }

    private static class Row {
        final String key;
        final Text name;
        final Text durability;
        final ItemStack stack;
        final int damage;
        final int maxDamage;
        
        Row(String key, Text name, Text durability, ItemStack stack, int damage, int maxDamage) {
            this.key = key;
            this.name = name;
            this.durability = durability;
            this.stack = stack;
            this.damage = damage;
            this.maxDamage = maxDamage;
        }
    }
}
