package dev.mzc.client.module.impl.hud;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.lwjgl.nanovg.NanoVG;

import dev.mzc.client.nanovg.font.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.mzc.client.utils.player.EnchantmentUtil.getEnchantments;

public class HandItemHud extends HudModule {
    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final NumberValue<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);
    private final ColorValue backgroundColor = new ColorValue("Background", "背景颜色", new Color(0, 0, 0, 110));
    private final NumberValue<Integer> animDuration = new NumberValue<>("AnimDuration", "动画时长", 250, 50, 1000, 50);
    private final NumberValue<Double> cardPadding = new NumberValue<>("CardPadding", "卡片内边距", 8.0, 4.0, 16.0, 1.0);
    private final NumberValue<Double> cardHeight = new NumberValue<>("CardHeight", "卡片高度", 40.0, 28.0, 70.0, 1.0);
    private final NumberValue<Double> iconSize = new NumberValue<>("IconSize", "图标大小", 20.0, 12.0, 32.0, 1.0);
    private final NumberValue<Double> gap = new NumberValue<>("Gap", "图标间距", 6.0, 2.0, 12.0, 0.5);
    private final BoolValue showEnchantments = new BoolValue("ShowEnchantments", "显示附魔", true);
    private final BoolValue showColor = new BoolValue("ShowColor", "显示颜色", true);

    private boolean firstRender = true;
    private float anchorOffset = -1;
    private int lastAlign = -1;

    private final EaseInOutQuad animation = new EaseInOutQuad(animDuration.get(), 1.0);
    private final EaseInOutQuad widthAnim = new EaseInOutQuad(animDuration.get(), 1.0);
    private float prevDrawW = 0f;
    private float nextDrawW = 0f;
    private String lastKey = "";
    private ItemStack lastStack = ItemStack.EMPTY;

    public HandItemHud() {
        super("HandItemHud", "手持物品HUD", 8, 180);
        this.width = 180;
        this.height = 50;
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.player == null) return;
        ItemStack currentStack = mc.player.getMainHandStack();
        
        if (!currentStack.isEmpty()) {
            lastStack = currentStack;
            animation.setDirection(Direction.FORWARDS);
        } else {
            animation.setDirection(Direction.BACKWARDS);
        }

        if (animation.getOutput() == 0 && animation.getDirection() == Direction.BACKWARDS) {
            return;
        }

        ItemStack stack = lastStack;
        if (stack.isEmpty()) return;

        // Force animation completion on first render to prevent position drift
        if (firstRender && !stack.isEmpty()) {
            animation.timerUtil.setTime(0);
        }

        double v = animation.getOutput();
        double scale = hudScale.get();

        float pad = cardPadding.get().floatValue();
        float cardH = cardHeight.get().floatValue();

        float fontSize = 11f;
        int font = FontLoader.medium(fontSize);
        int fontGrey = FontLoader.regular(10f);
        
        float nameWUnscaled = stack.isEmpty() ? 0 : NanoVGHelper.getTextWidth(stack.getName().getString(), font, fontSize);
        String enchLineStr = "";
        if (showEnchantments.get()) {
            Object2IntMap<RegistryEntry<Enchantment>> enchantsMeasure = new Object2IntOpenHashMap<>();
            getEnchantments(stack, enchantsMeasure);
            if (!enchantsMeasure.isEmpty()) {
                List<String> linesM = new ArrayList<>();
                for (Object2IntMap.Entry<RegistryEntry<Enchantment>> e : enchantsMeasure.object2IntEntrySet()) {
                    String nameFull = fullEnchant(e.getKey());
                    int lvl = e.getIntValue();
                    if (nameFull.isEmpty()) continue;
                    linesM.add(nameFull + (lvl > 1 ? (" " + lvl) : ""));
                }
                enchLineStr = String.join(", ", linesM);
            }
        }
        float enchWUnscaled = enchLineStr.isEmpty() ? 0 : NanoVGHelper.getTextWidth(enchLineStr, fontGrey, 10f);
        float itemScale = (float) (iconSize.get() / 16.0);
        float iconWH = 16f * itemScale;
        float targetDrawW = pad + iconWH + gap.get().floatValue() + Math.max(nameWUnscaled, enchWUnscaled) + pad + 2;

        String key = stack.getItem().getTranslationKey() + "|" + nameWUnscaled + "|" + enchLineStr;
        float tPrev = widthAnim.getOutput().floatValue();
        float currentW = prevDrawW + (nextDrawW - prevDrawW) * tPrev;
        float baseW = pad + iconWH + gap.get().floatValue() + pad;
        if (!key.equals(lastKey)) {
            if (firstRender) {
                prevDrawW = Math.max(targetDrawW, baseW);
                nextDrawW = Math.max(targetDrawW, baseW);
            } else {
                prevDrawW = Math.max(currentW, baseW);
                nextDrawW = Math.max(targetDrawW, baseW);
                widthAnim.reset();
                widthAnim.setDirection(Direction.FORWARDS);
            }
            lastKey = key;
        } else {
            nextDrawW = Math.max(targetDrawW, baseW);
        }
        float drawW = prevDrawW + (nextDrawW - prevDrawW) * widthAnim.getOutput().floatValue();
        float scaledDrawW = drawW * (float) scale;

        // Alignment logic
        // 0: Left, 1: Center, 2: Right
        int align = 0;
        int screenWidth = mc.getWindow().getScaledWidth();
        float centerX = x + this.width / 2f;
        if (centerX > screenWidth * 0.66f) {
            align = 2;
        } else if (centerX > screenWidth * 0.33f) {
            align = 1;
        }

        if (dragging || firstRender || align != lastAlign) {
            if (align == 2) anchorOffset = screenWidth - (x + scaledDrawW);
            else if (align == 1) anchorOffset = x + scaledDrawW / 2f;
            else anchorOffset = x;

            lastAlign = align;
        } else {
            if (anchorOffset == -1) {
                if (align == 2) anchorOffset = screenWidth - (x + scaledDrawW);
                else if (align == 1) anchorOffset = x + scaledDrawW / 2f;
                else anchorOffset = x;
            }

            if (align == 2) x = screenWidth - anchorOffset - scaledDrawW;
            else if (align == 1) x = anchorOffset - scaledDrawW / 2f;
            else x = anchorOffset;
        }

        this.width = scaledDrawW; // Use scaled width for proper hitboxing and alignment tracking
        if (firstRender) firstRender = false;
        
        float nameLineH = 11f; // Approximate height for medium font
        float enchLineH = (!enchLineStr.isEmpty() ? 10f : 0f); // Approximate height for regular font
        float spacing = (!enchLineStr.isEmpty() ? 2f : 0f);
        float blockH = nameLineH + spacing + enchLineH;
        float drawH = Math.max(cardH, blockH + pad);
        this.height = drawH * (float) scale;

        Color bg = backgroundColor.get();
        // Modulate alpha based on animation
        int alpha = (int) (bg.getAlpha() * v);
        Color animatedBg = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), alpha);

        // Calculate pivot for animation based on alignment
        float pivotX;
        if (align == 2) { // Right
            pivotX = x + scaledDrawW;
        } else if (align == 1) { // Center
            pivotX = x + scaledDrawW / 2f;
        } else { // Left
            pivotX = x;
        }
        float pivotY = y + (drawH * (float) scale) / 2f;

        // Apply scale animation centered on anchor point
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.translate(pivotX, pivotY);
            NanoVGHelper.scale((float) (v * scale), (float) (v * scale));
            NanoVGHelper.translate(-pivotX, -pivotY);

            if (enableBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(x, y, drawW, drawH, radius.get().floatValue(), animatedBg);
            } else {
                NanoVGHelper.drawRoundRect(x, y, drawW, drawH, radius.get().floatValue(), animatedBg);
            }
        });

        // For item rendering (MatrixStack), we need to manually apply scale/alpha
        // Alpha for item rendering is tricky without shaders, but we can scale it.
        float iconX = x + pad;
        float iconY = y + (drawH - iconWH) / 2f;
        
        // Adjust icon position for center scaling if needed, but the NanoVG scaling above only affects NanoVG.
        // We need to apply similar transformation to MatrixStack for the item.

        context.getMatrices().push();
        context.getMatrices().translate(pivotX, pivotY, 0);
        context.getMatrices().scale((float) (v * scale), (float) (v * scale), 1f);
        context.getMatrices().translate(-pivotX, -pivotY, 0);

        context.getMatrices().push();
        context.getMatrices().translate(iconX, iconY, 200);
        context.getMatrices().scale(itemScale, itemScale, 1f);
        context.drawItem(stack, 0, 0);
        context.drawStackOverlay(mc.textRenderer, stack, 0, 0);
        context.getMatrices().pop();
        
        context.getMatrices().pop();

        float textBlockTop = y + (drawH - blockH) / 2f;
        float nameX = iconX + iconWH + gap.get().floatValue();
        
        String finalEnchLineStr = enchLineStr;
        int scissorW = (int) Math.ceil(Math.max(1, (x + drawW - pad) - nameX + 1));
        NanoVGRenderer.INSTANCE.draw(vg -> {
            // Apply same scale transform for text
            NanoVGHelper.translate(pivotX, pivotY);
            NanoVGHelper.scale((float) (v * scale), (float) (v * scale));
            NanoVGHelper.translate(-pivotX, -pivotY);

            NanoVGHelper.saveScissor();
            NanoVGHelper.intersectScissor(nameX, y, scissorW, drawH);
            
            float currentNameX = nameX;
            float currentNameY = textBlockTop + nameLineH / 2f;
            Color textColor = Color.WHITE;
            if (showColor.get()) {
                TextColor tc = stack.getName().getStyle().getColor();
                if (tc != null) {
                    textColor = new Color(tc.getRgb());
                } else {
                    Formatting formatting = stack.getRarity().getFormatting();
                    if (formatting != null && formatting.getColorValue() != null) {
                        textColor = new Color(formatting.getColorValue());
                    }
                }
            }
            // Modulate text alpha
            Color animTextColor = new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), (int)(255 * v));
            
            NanoVGHelper.drawString(stack.getName().getString(), currentNameX, currentNameY, font, fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, animTextColor);
            
            if (!finalEnchLineStr.isEmpty()) {
                float enchYScaled = textBlockTop + nameLineH + spacing + enchLineH / 2f;
                NanoVGHelper.drawString(finalEnchLineStr, currentNameX, enchYScaled, fontGrey, 10f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(160, 160, 160, (int)(255 * v)));
            }
            NanoVGHelper.restoreScissor();
        });
    }

    private String fullEnchant(RegistryEntry<Enchantment> enchant) {
        String id = enchant.getIdAsString();
        if (id == null) return "";
        int colon = id.indexOf(':');
        String key = colon >= 0 ? id.substring(colon + 1) : id;
        String[] parts = key.split("_");
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (p.isEmpty()) continue;
            b.append(Character.toUpperCase(p.charAt(0)));
            if (p.length() > 1) b.append(p.substring(1));
            if (i < parts.length - 1) b.append(' ');
        }
        return b.toString();
    }
}
