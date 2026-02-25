package dev.mzc.client.module.impl.hud;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.impl.client.HudEditor;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class HotbarHud extends HudModule {

    private final NumberValue<Double> scale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.05);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);

    private final BoolValue enableBloom = new BoolValue("Bloom", "光晕", true);
    private final NumberValue<Double> tension = new NumberValue<>("Tension", "张力", 0.25, 0.05, 0.5, 0.01);
    private final NumberValue<Double> friction = new NumberValue<>("Friction", "摩擦力", 0.65, 0.3, 0.9, 0.01);

    private final ColorValue selectorColor = new ColorValue("SelectorColor", "选择器颜色", new Color(255, 255, 255, 160));
    private final BoolValue showHandSlots = new BoolValue("ShowHandSlots", "显示手持物品", true);
    private final BoolValue selectorGlow = new BoolValue("SelectorGlow", "选择器发光", true);

    private final BoolValue showHealth = new BoolValue("ShowHealth", "显示生命值", true);
    private final BoolValue showArmor = new BoolValue("ShowArmor", "显示护甲值", true);
    private final BoolValue showHunger = new BoolValue("ShowHunger", "显示饱食度", true);
    private final BoolValue showExperience = new BoolValue("ShowExperience", "显示经验条", true);

    private static final int SLOT_SIZE = 18;
    private static final int SLOT_COUNT = 9;
    private static final int PADDING = 3;
    private static final int GAP = 2;
    private static final int HAND_SLOT_GAP = 6;

    private float animatedSlot = 0f;
    private float velocity = 0f;
    private float stretchFactor = 1f;

    private float animHealth = 0f;
    private float animAbsorption = 0f;
    private float animArmor = 0f;
    private float animFood = 0f;
    private float animExperience = 0f;
    
    private float animExpOffset = 0f;
    private float animAbsOffset = 0f;

    private float hotbarX, hotbarY, hotbarWidth, hotbarHeight;
    private float leftHandX, rightHandX, handSlotY, handSlotSize;
    private float slotSize, padding, gap, r, s;

    public HotbarHud() {
        super("HotbarHud", "物品栏", 0, 0);
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            animatedSlot = mc.player.getInventory().selectedSlot;
            velocity = 0f;
            stretchFactor = 1f;
        }
    }

    @Override
    public void renderInGame(DrawContext context) {
        HudEditor editor = Sakura.MODULES.getModule(HudEditor.class);
        if (editor != null && editor.isEnabled()) return;

        calculateLayout();
        updateAnimation();
        renderBloom(context);
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
        renderItems(context);
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

        calculateLayout();
        updateAnimation();
        renderBloom(context);
        NanoVGRenderer.INSTANCE.draw(vg -> {
            renderContent();
            NanoVGHelper.drawRect(x, y, width, height,
                    dragging ? new Color(100, 100, 255, 80) : new Color(0, 0, 0, 50));
        });
        renderItems(context);
    }

    private void updateAnimation() {
        if (mc.player == null) return;

        int targetSlot = mc.player.getInventory().selectedSlot;
        float t = tension.get().floatValue();
        float f = friction.get().floatValue();

        float distance = targetSlot - animatedSlot;
        float acceleration = distance * t;
        velocity += acceleration;
        velocity *= f;
        animatedSlot += velocity;

        float speed = Math.abs(velocity);
        float targetStretch = 1f + Math.min(speed * 0.15f, 0.3f);
        stretchFactor += (targetStretch - stretchFactor) * 0.2f;

        if (Math.abs(distance) < 0.005f && Math.abs(velocity) < 0.005f) {
            animatedSlot = targetSlot;
            velocity = 0f;
            stretchFactor = 1f;
        }

        float animationSpeed = 10f;
        animHealth = RenderUtil.animate(animHealth, mc.player.getHealth(), animationSpeed);
        animAbsorption = RenderUtil.animate(animAbsorption, mc.player.getAbsorptionAmount(), animationSpeed);
        animArmor = RenderUtil.animate(animArmor, mc.player.getArmor(), animationSpeed);
        animFood = RenderUtil.animate(animFood, mc.player.getHungerManager().getFoodLevel(), animationSpeed);
        animExperience = RenderUtil.animate(animExperience, mc.player.experienceProgress, animationSpeed);

        float barHeight = 8 * s;
        float gap = 3 * s;
        
        float targetExpOffset = 0f;
        if (showExperience.get()) {
            targetExpOffset = 4 * s + gap;
        }
        animExpOffset = RenderUtil.animate(animExpOffset, targetExpOffset, animationSpeed);
        
        float targetAbsOffset = 0f;
        if (showHealth.get() && mc.player.getAbsorptionAmount() > 0) {
            targetAbsOffset = barHeight + gap;
        }
        animAbsOffset = RenderUtil.animate(animAbsOffset, targetAbsOffset, animationSpeed);
    }

    private void calculateLayout() {
        s = scale.get().floatValue();
        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();

        slotSize = SLOT_SIZE * s;
        padding = PADDING * s;
        gap = GAP * s;
        r = getRadius() * s;

        hotbarWidth = SLOT_COUNT * slotSize + (SLOT_COUNT - 1) * gap + padding * 2;
        hotbarHeight = slotSize + padding * 2;
        hotbarX = (screenWidth - hotbarWidth) / 2f;
        hotbarY = screenHeight - hotbarHeight - 4 * s;

        handSlotSize = slotSize + padding * 2;
        float handSlotGap = HAND_SLOT_GAP * s;
        leftHandX = hotbarX - handSlotGap - handSlotSize;
        rightHandX = hotbarX + hotbarWidth + handSlotGap;
        handSlotY = hotbarY;

        this.x = hotbarX;
        this.y = hotbarY;
        this.width = hotbarWidth;
        this.height = hotbarHeight;
    }

    public float getRadius() {
        return radius.get().floatValue();
    }

    private void renderBloom(DrawContext context) {
        if (!enableBloom.get()) return;

        Color bloomColor = new Color(18, 18, 18, 70);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRoundRectBloom(hotbarX, hotbarY, hotbarWidth, hotbarHeight, getRadius(), bloomColor);

            if (showHandSlots.get()) {
                NanoVGHelper.drawRoundRectBloom(leftHandX, handSlotY, handSlotSize, handSlotSize, getRadius(), bloomColor);
                NanoVGHelper.drawRoundRectBloom(rightHandX, handSlotY, handSlotSize, handSlotSize, getRadius(), bloomColor);
            }
        });
    }

    private void renderContent() {
        Color bg = new Color(18, 18, 18, 70);
        Color selector = selectorColor.get();

        NanoVGHelper.drawRoundRect(hotbarX, hotbarY, hotbarWidth, hotbarHeight, getRadius() * s, bg);

        if (showHandSlots.get()) {
            NanoVGHelper.drawRoundRect(leftHandX, handSlotY, handSlotSize, handSlotSize, getRadius() * s, bg);
            NanoVGHelper.drawRoundRect(rightHandX, handSlotY, handSlotSize, handSlotSize, getRadius() * s, bg);
        }

        float selectorCenterX = hotbarX + padding + animatedSlot * (slotSize + gap) + slotSize / 2f;
        float selectorCenterY = hotbarY + padding + slotSize / 2f;
        float baseRadius = slotSize / 2f - 1;

        if (selectorGlow.get()) {
            for (int i = 3; i > 0; i--) {
                float glowRadius = baseRadius + i * 1.5f;
                int alpha = (int) (selector.getAlpha() * (1.0f - i * 0.25f) * 0.35f);
                Color glowColor = new Color(selector.getRed(), selector.getGreen(), selector.getBlue(), alpha);
                NanoVGHelper.drawCircle(selectorCenterX, selectorCenterY, glowRadius * stretchFactor, glowColor);
            }
        }

        NanoVGHelper.drawCircle(selectorCenterX, selectorCenterY, baseRadius * stretchFactor, selector);

        renderStats();
    }

    private void renderStats() {
        if (mc.player == null) return;

        float barHeight = 8 * s;
        float gap = 3 * s;
        float baseY = hotbarY - gap;
        float radius = 2 * s;
        
        // Experience Bar (Bottom Layer)
        if (showExperience.get()) {
            float expBarHeight = 4 * s;
            float xpY = baseY - expBarHeight;
            float xpWidth = hotbarWidth - 2 * padding; // Align with slots
            float xpX = hotbarX + padding;
            
            NanoVGHelper.drawRoundRect(xpX, xpY, xpWidth, expBarHeight, radius, new Color(0, 0, 0, 100));
            if (animExperience > 0) {
                NanoVGHelper.drawRoundRect(xpX, xpY, animExperience * xpWidth, expBarHeight, radius, new Color(0, 200, 50));
            }

            // Level Text
            if (mc.player.experienceLevel > 0) {
                String levelStr = String.valueOf(mc.player.experienceLevel);
                float fontSize = 14 * s;
                int fontId = FontLoader.regular(fontSize);
                float textWidth = NanoVGHelper.getTextWidth(levelStr, fontId, fontSize);
                float textX = xpX + (xpWidth - textWidth) / 2f;
                // Draw text above XP bar
                float textY = xpY - fontSize + 2 * s; 
                
                NanoVGHelper.drawString(levelStr, textX, textY, fontId, fontSize, new Color(128, 255, 128));
            }
        }
        
        // Health / Food (Middle Layer)
        float healthY = baseY - animExpOffset - barHeight;
        float barWidth = (hotbarWidth - 10 * s) / 2f;
        
        // Health
        if (showHealth.get()) {
            float maxHealth = mc.player.getMaxHealth();
            float healthWidth = (animHealth / maxHealth) * barWidth;
            
            NanoVGHelper.drawRoundRect(hotbarX, healthY, barWidth, barHeight, radius, new Color(0, 0, 0, 100));
            if (animHealth > 0) {
                NanoVGHelper.drawRoundRect(hotbarX, healthY, healthWidth, barHeight, radius, new Color(255, 60, 60));
            }
        }

        // Hunger
        if (showHunger.get()) {
            float maxFood = 20f;
            float foodWidth = (animFood / maxFood) * barWidth;
            float hungerX = hotbarX + hotbarWidth - barWidth;

            NanoVGHelper.drawRoundRect(hungerX, healthY, barWidth, barHeight, radius, new Color(0, 0, 0, 100));
            if (animFood > 0) {
                NanoVGHelper.drawRoundRect(hungerX + (barWidth - foodWidth), healthY, foodWidth, barHeight, radius, new Color(200, 150, 50));
            }
        }

        // Absorption (Upper Layer)
        // Positioned above Health.
        // If present, it draws at: healthY - gap - barHeight.
        float absY = healthY - gap - barHeight;
        
        if (showHealth.get() && animAbsorption > 0.1f) {
            float maxAbsorption = 20f;
            if (mc.player.getAbsorptionAmount() > maxAbsorption) maxAbsorption = mc.player.getAbsorptionAmount();
            
            float absorptionWidth = (animAbsorption / maxAbsorption) * barWidth;
            
            NanoVGHelper.drawRoundRect(hotbarX, absY, barWidth, barHeight, radius, new Color(0, 0, 0, 100));
            NanoVGHelper.drawRoundRect(hotbarX, absY, absorptionWidth, barHeight, radius, new Color(255, 200, 0));
        }

        // Armor (Top Layer)
        // Positioned above Absorption (or Health if no Absorption).
        // Base position (above Health): healthY - gap - barHeight.
        // Pushed up by Absorption: - animAbsOffset.
        // Wait, if animAbsOffset is 0, it is at base position.
        // But if animAbsOffset is 0, Absorption is not there, so Armor is at `healthY - gap - barHeight`.
        // If animAbsOffset is full (barHeight + gap), Armor is at `healthY - gap - barHeight - (barHeight + gap)`.
        // This seems correct?
        // Let's check visual:
        // Health at Y.
        // Gap.
        // Armor at Y - gap - barHeight.
        // This is correct for stacking.
        
        if (showArmor.get() && animArmor > 0.1f) {
            float armorY = healthY - gap - barHeight - animAbsOffset;
            float maxArmor = 20f;
            float armorWidth = (animArmor / maxArmor) * barWidth;

            NanoVGHelper.drawRoundRect(hotbarX, armorY, barWidth, barHeight, radius, new Color(0, 0, 0, 100));
            if (animArmor > 0) {
                NanoVGHelper.drawRoundRect(hotbarX, armorY, armorWidth, barHeight, radius, new Color(50, 150, 255));
            }
        }
    }

    private void renderItems(DrawContext context) {
        if (mc.player == null) return;

        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                float itemX = hotbarX + padding + i * (slotSize + gap) + (slotSize - 16 * s) / 2f;
                float itemY = hotbarY + padding + (slotSize - 16 * s) / 2f;

                context.getMatrices().push();
                context.getMatrices().translate(itemX, itemY, 200);
                context.getMatrices().scale(s, s, 1f);
                context.drawItem(stack, 0, 0);
                context.drawStackOverlay(mc.textRenderer, stack, 0, 0);
                context.getMatrices().pop();
            }
        }

        if (showHandSlots.get()) {
            ItemStack offhandStack = mc.player.getOffHandStack();
            if (!offhandStack.isEmpty()) {
                float itemX = leftHandX + padding + (slotSize - 16 * s) / 2f;
                float itemY = handSlotY + padding + (slotSize - 16 * s) / 2f;

                context.getMatrices().push();
                context.getMatrices().translate(itemX, itemY, 200);
                context.getMatrices().scale(s, s, 1f);
                context.drawItem(offhandStack, 0, 0);
                context.drawStackOverlay(mc.textRenderer, offhandStack, 0, 0);
                context.getMatrices().pop();
            }

            ItemStack mainhandStack = mc.player.getMainHandStack();
            if (!mainhandStack.isEmpty()) {
                float itemX = rightHandX + padding + (slotSize - 16 * s) / 2f;
                float itemY = handSlotY + padding + (slotSize - 16 * s) / 2f;

                context.getMatrices().push();
                context.getMatrices().translate(itemX, itemY, 200);
                context.getMatrices().scale(s, s, 1f);
                context.drawItem(mainhandStack, 0, 0);
                context.drawStackOverlay(mc.textRenderer, mainhandStack, 0, 0);
                context.getMatrices().pop();
            }
        }
    }
}
