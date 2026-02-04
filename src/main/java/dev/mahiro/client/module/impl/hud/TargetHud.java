package dev.mahiro.client.module.impl.hud;

import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.gui.hud.HudEditorScreen;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.combat.KillAura;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.EaseOutSine;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TargetHud extends HudModule {
    public enum HPmodeEn {
        HP, Percentage
    }

    public enum StyleEn {
        ThunderHack, Modern, Mahiro
    }

    public enum ImageModeEn {
        None, Anime, Custom
    }

    public enum AvatarPosEn {
        Left, OnBar
    }

    private final EnumValue<StyleEn> style = new EnumValue<>("Style", "样式", StyleEn.ThunderHack);
    private final NumberValue<Double> blurRadius = new NumberValue<>("BallonBlur", "气泡模糊", 10.0, 1.0, 10.0, 1.0, () -> style.get() == StyleEn.ThunderHack);

    // Mahiro Settings
    private final NumberValue<Double> MahiroScale = new NumberValue<>("Scale", "整体缩放", 1.0, 0.5, 2.0, 0.1, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroWidth = new NumberValue<>("Width", "宽度", 150.0, 100.0, 300.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroHeight = new NumberValue<>("Height", "高度", 50.0, 30.0, 100.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroRadius = new NumberValue<>("Radius", "圆角半径", 10.0, 0.0, 20.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroBlurRadius = new NumberValue<>("BlurRadius", "模糊半径", 10.0, 1.0, 50.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroBarHeight = new NumberValue<>("BarHeight", "血条粗细", 10.0, 2.0, 30.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroBarRadius = new NumberValue<>("BarRadius", "血条圆角", 4.0, 0.0, 15.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final EnumValue<AvatarPosEn> MahiroAvatarPos = new EnumValue<>("AvatarPos", "头像位置", AvatarPosEn.Left, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroNameSize = new NumberValue<>("NameSize", "名字大小", 14.0, 8.0, 24.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroNameX = new NumberValue<>("NameX", "名字X偏移", 0.0, -50.0, 50.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroNameY = new NumberValue<>("NameY", "名字Y偏移", 0.0, -50.0, 50.0, 1.0, () -> style.get() == StyleEn.Mahiro);
    private final NumberValue<Double> MahiroOnBarHeight = new NumberValue<>("OnBarHeight", "悬浮高度", 15.0, 0.0, 50.0, 1.0, () -> style.get() == StyleEn.Mahiro && MahiroAvatarPos.get() == AvatarPosEn.OnBar);

    // Mahiro Delay Settings
    private final BoolValue MahiroDelay = new BoolValue("DelayBar", "延迟血条", true, () -> style.get() == StyleEn.Mahiro);
    private final BoolValue MahiroDelayWait = new BoolValue("WaitMode", "受伤等待", true, () -> style.get() == StyleEn.Mahiro && MahiroDelay.get());
    private final NumberValue<Integer> MahiroDelayTime = new NumberValue<>("DelayTime", "延迟时间(ms)", 600, 0, 2000, 50, () -> style.get() == StyleEn.Mahiro && MahiroDelay.get() && MahiroDelayWait.get());
    private final NumberValue<Double> MahiroDelaySpeed = new NumberValue<>("DelaySpeed", "延迟动画速度", 2.0, 0.1, 10.0, 0.1, () -> style.get() == StyleEn.Mahiro && MahiroDelay.get());
    private final ColorValue MahiroDelayColor = new ColorValue("DelayColor", "延迟血条颜色", new Color(255, 255, 0, 150), () -> style.get() == StyleEn.Mahiro && MahiroDelay.get());

    // Modern Settings
    private final NumberValue<Integer> modernBgAlpha = new NumberValue<>("BgAlpha", "背景透明度", 100, 0, 255, 1, () -> style.get() == StyleEn.Modern);
    private final NumberValue<Double> modernBlur = new NumberValue<>("ModernBlur", "背景模糊", 10.0, 0.0, 50.0, 1.0, () -> style.get() == StyleEn.Modern);
    private final NumberValue<Double> modernBloom = new NumberValue<>("ModernBloom", "背景光晕", 10.0, 0.0, 50.0, 1.0, () -> style.get() == StyleEn.Modern);
    private final BoolValue bloomFollowHP = new BoolValue("BloomFollowHP", "光晕跟随血量", true, () -> style.get() == StyleEn.Modern);
    private final ColorValue modernBloomColor = new ColorValue("BloomColor", "光晕颜色", new Color(0, 0, 0, 180), () -> style.get() == StyleEn.Modern && !bloomFollowHP.get());

    private final EnumValue<HPmodeEn> hpMode = new EnumValue<>("HP Mode", "血量模式", HPmodeEn.HP);
    private final EnumValue<ImageModeEn> imageMode = new EnumValue<>("Image", "图片模式", ImageModeEn.Anime, () -> style.get() == StyleEn.ThunderHack);

    // 3D ESP Settings
    private final BoolValue espEnabled = new BoolValue("ESP", "3D透视", true);
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 0, 0, 255), espEnabled::get);
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(0, 255, 255, 255), espEnabled::get);
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, espEnabled::get);

    // Health Bar Settings
    private final ColorValue healthColor = new ColorValue("HealthColor", "血条颜色", new Color(0, 255, 0), () -> true);
    private final BoolValue healthGradient = new BoolValue("HealthGradient", "血条渐变", false);
    private final ColorValue healthColor2 = new ColorValue("HealthColor2", "渐变颜色2", new Color(0, 255, 255), healthGradient::get);
    private final NumberValue<Double> gradientSpeed = new NumberValue<>("GradientSpeed", "渐变速度", 3.0, 0.1, 10.0, 0.1, healthGradient::get);
    private final NumberValue<Double> colorLength = new NumberValue<>("ColorLength", "颜色长度", 1.0, 0.1, 5.0, 0.1, () -> healthGradient.get() || espEnabled.get());

    // Glow Settings
    private final BoolValue glow = new BoolValue("Glow", "发光效果", true);
    private final NumberValue<Double> glowStrength = new NumberValue<>("GlowStrength", "发光强度", 5.0, 1.0, 20.0, 1.0, glow::get);
    private final BoolValue showArmor = new BoolValue("Armor", "显示装备", true);

    // Renamed or kept for particles
    private final ColorValue color = new ColorValue("Color1", "颜色1", new Color(4, 59, 95));
    private final ColorValue color2 = new ColorValue("Color2", "颜色2", new Color(4, 59, 95));
    // Removed old healthColor definition to avoid conflict
    private final BoolValue funTimeHP = new BoolValue("FunTimeHP", "FunTime血量", false);
    private final BoolValue absorp = new BoolValue("Absorption", "伤害吸收", true);


    private static final Identifier TARGET_TEX = Identifier.of("mahiro", "textures/particles/target.png");
    private static final Identifier THUD_TEX = Identifier.of("mahiro", "textures/hud/thud.png");

    // Animations
    private final Animation animation = new EaseOutSine(300, 1.0, Direction.BACKWARDS);
    private final Animation damageAnim = new EaseOutSine(150, 1.0, Direction.BACKWARDS);

    private float displayHealth = -1;
    private float lastTargetHealth = -1;
    private float delayHealth = -1;
    private final TimerUtil damageTimer = new TimerUtil();

    private LivingEntity target;
    private float rotation = 0f;
    private final Map<Integer, Integer> skinImageCache = new ConcurrentHashMap<>();

    // Particles
    private final ArrayList<Particles> particles = new ArrayList<>();
    private final TimerUtil timer = new TimerUtil();
    private boolean sentParticles = false;
    private float ticks = 0;
    private boolean needsCacheClear = false;

    public TargetHud() {
        super("TargetHud", "目标显示", 150, 50);
        this.width = 150;
        this.height = 50;
    }

    @Override
    protected void onEnable() {
        target = null;
        animation.setDirection(Direction.BACKWARDS);
        damageAnim.setDirection(Direction.BACKWARDS);
        displayHealth = -1;
        lastTargetHealth = -1;
        delayHealth = -1;
        particles.clear();
        needsCacheClear = true; // Ensure clean state on enable
    }

    @Override
    protected void onDisable() {
        needsCacheClear = true;
        particles.clear();
    }

    private LivingEntity getCurrentTarget() {
        KillAura killAura = Mahiro.MODULES.getModule(KillAura.class);
        if (killAura.isEnabled()) {
            Entity target = killAura.getCurrentTarget();
            if (target instanceof LivingEntity living) {
                return living;
            }
        }
        if (mc.currentScreen instanceof HudEditorScreen) {
            return mc.player;
        }
        return null;
    }

    @Override
    public void onRender(DrawContext context) {
        if (needsCacheClear) {
            for (int imageId : skinImageCache.values()) {
                NanoVGHelper.deleteTexture(imageId);
            }
            skinImageCache.clear();
            needsCacheClear = false;
        }

        LivingEntity currentTarget = getCurrentTarget();
        boolean hasTarget = currentTarget != null;

        if (hasTarget) {
            target = currentTarget;
            animation.setDirection(Direction.FORWARDS);
        } else {
            animation.setDirection(Direction.BACKWARDS);
        }

        if (animation.getOutput().floatValue() <= 0.01f && !hasTarget) {
            target = null;
            return;
        }

        float animValue = animation.getOutput().floatValue();

        // Update Health
        float health = 0;
        float maxHealth = 20;
        if (target != null) {
            health = Managers.HEALTH.getHealth(target);
            if (absorp.get()) {
                health += target.getAbsorptionAmount();
            }
            maxHealth = target.getMaxHealth() + target.getAbsorptionAmount();
            health = Math.min(maxHealth, health);

            // Damage Animation Logic
            if (lastTargetHealth == -1) lastTargetHealth = health;
            if (health < lastTargetHealth) {
                damageAnim.setDirection(Direction.FORWARDS);
                damageTimer.reset(); // Reset timer on damage
            }
            lastTargetHealth = health;

            // Damage Pulse Logic (Reset if done)
            if (damageAnim.getDirection() == Direction.FORWARDS && damageAnim.isDone()) {
                damageAnim.setDirection(Direction.BACKWARDS);
            }
        }

        // Smooth Health Logic
        if (displayHealth == -1) displayHealth = health;
        if (delayHealth == -1) delayHealth = health;

        // DrawContext doesn't have getTickDelta() directly in some mappings/versions
        // Usually we can get it from RenderTickCounter or just use a fixed step for smoothing
        // Since we are in onRender(DrawContext), let's check if we can get partial ticks from MC
        float tickDelta = mc.getRenderTickCounter().getTickProgress(false);
        displayHealth = MathHelper.lerp(tickDelta * 0.2f, displayHealth, health);

        // Delay Health Logic
        if (MahiroDelay.get()) {
            if (health < delayHealth) {
                // If WaitMode is ON, check timer. If OFF, bypass timer.
                if (!MahiroDelayWait.get() || damageTimer.passedMS(MahiroDelayTime.get())) {
                    // Slowly decrease delayHealth
                    delayHealth = MathHelper.lerp(tickDelta * MahiroDelaySpeed.get().floatValue() * 0.05f, delayHealth, health);
                }
            } else if (health > delayHealth) {
                // Heal or new target, reset delay bar immediately
                delayHealth = health;
            }
        } else {
            delayHealth = health;
        }

        // Render Background and Main Elements via NanoVG
        final LivingEntity renderTarget = target;
        final float finalHealth = displayHealth; // Use smooth health
        final float finalMaxHealth = maxHealth;
        final float damageFactor = damageAnim.getOutput().floatValue();

        /*
        renderKawaseBloom(context, animValue);
        */

        if (style.get() == StyleEn.Mahiro) {
            renderMahiroBackground(animValue);
        }

        // 1. Render NanoVG elements (Backgrounds, Bars, Text)
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.save();

            // No custom X/Y animation translation anymore as per request

            // Scale Animation
            float centerX = x + width / 2f;
            float centerY = y + height / 2f;
            NanoVGHelper.translate(vg, centerX, centerY);
            NanoVGHelper.scale(vg, animValue, animValue);
            NanoVGHelper.translate(vg, -centerX, -centerY);

            if (style.get() == StyleEn.Modern) {
                renderModern(vg, renderTarget, finalHealth, finalMaxHealth, animValue, damageFactor);
            } else if (style.get() == StyleEn.Mahiro) {
                renderMahiro(vg, renderTarget, finalHealth, finalMaxHealth, animValue, damageFactor);
            } else {
                renderThunderHack(vg, renderTarget, finalHealth, finalMaxHealth, animValue, damageFactor);
            }

            NanoVGHelper.restore();
        });

        // 2. Render Items (Armor, Hands) - Must be done outside NanoVG frame usually to use DrawContext
        if (target instanceof PlayerEntity player && animValue > 0.1f) {
            context.getMatrices().pushMatrix();

            // No custom X/Y animation translation anymore as per request

            // Scale from center
            float centerX = x + width / 2f;
            float centerY = y + height / 2f;
            context.getMatrices().translate(centerX, centerY);
            context.getMatrices().scale(animValue, animValue);
            context.getMatrices().translate(-centerX, -centerY);

            renderThunderHackItems(context, player);

            context.getMatrices().popMatrix();
        }
    }

    // ====================================================================================
    //                                  RENDER LOGIC
    // ====================================================================================

    private void renderModern(long vg, LivingEntity target, float health, float maxHealth, float animationFactor, float damageFactor) {
        this.width = 150;
        this.height = 50;

        // Bloom (Outer Glow/Shadow) - Removed in favor of Kawase Bloom
        /*
        float bloom = modernBloom.get().floatValue();
        if (bloom > 0) {
            // Use bloom as a colored glow matching the health bar if enabled
            Color baseColor;
            if (bloomFollowHP.get()) {
                Color hpC = healthColor.get();
                // If following HP, use HP color with some transparency (e.g. 100)
                baseColor = new Color(hpC.getRed(), hpC.getGreen(), hpC.getBlue(), 100);
            } else {
                // Otherwise use custom color
                baseColor = modernBloomColor.get();
            }
            NanoVGHelper.drawShadow(x, y, width, height, bloom, baseColor, 8, 0, 0);
        }
        */

        // Background (Semi-transparent with Blur support)
        int alpha = modernBgAlpha.get();
        float blur = modernBlur.get().floatValue();
        Color bgColor = new Color(0, 0, 0, alpha);

        if (blur > 0) {
            // Use drawShadow to simulate a blurred/feathered background rect
            // We draw it multiple times or mix with rect to ensure core opacity if needed,
            // but simple shadow usually works for fuzzy rect.
            NanoVGHelper.drawShadow(x, y, width, height, blur, bgColor, 8, 0, 0);
        } else {
            NanoVGHelper.drawRoundRect(x, y, width, height, 8, bgColor);
        }

        // Particles Logic (reuse)
        updateParticles(vg);

        if (target instanceof PlayerEntity player) {
            // Damage Effect: Shrink scale
            float damageScale = 1.0f - (damageFactor * 0.15f);
            drawPlayerAvatar(player, x + 5, y + 5, 40, 5, damageScale, damageFactor);
        }

        // Adjust Y positions based on showArmor
        float yOffset = showArmor.get() ? 0 : 5;

        // Health Bar
        float healthWidth = MathHelper.clamp(90 * (health / maxHealth), 3, 90);

        // Background for health bar
        NanoVGHelper.drawGradientRRect(x + 50, y + 25 + yOffset, 90, 8, 4, new Color(20, 20, 20), new Color(40, 40, 40));

        // Calculate gradient colors
        Color c1 = healthColor.get();
        Color c2 = healthColor.get().darker();

        if (healthGradient.get()) {
            double speed = gradientSpeed.get();
            // Use System.nanoTime() for higher precision, but millis is usually fine.
            // Ensure speed is actually used correctly.
            float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);

            // Adjust length factor: larger colorLength -> smaller frequency -> longer waves
            float length = colorLength.get().floatValue();
            // Default frequency is 1.0 (for sine). Divide by length to stretch the wave.
            float frequency = 1.0f / length;

            // Use sine wave for smooth transition 0 -> 1 -> 0
            float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
            // Phase shift for gradient end color.
            // The phase difference determines how "fast" the color changes across the bar length visually.
            // If we want to adjust the "length" of one color segment, we are essentially adjusting the wavelength.
            // Here we are interpolating two points in time/space.
            float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);

            c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
            c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
        }

        // Health Bar Glow
        if (glow.get()) {
            float strength = glowStrength.get().floatValue();

            // Loop for glow layers
            for (float i = 0.5f; i <= strength; i += 0.5f) {
                float normalizedDist = i / (strength + 2);
                float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                float a = alphaFactor * 0.15f;
                int alphaInt = MathHelper.clamp((int) (a * 255), 0, 255);

                if (alphaInt > 0) {
                    if (healthGradient.get()) {
                        // Use gradient glow
                        // We need new Colors with alpha
                        Color gc1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt);
                        Color gc2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alphaInt);

                        NanoVGHelper.drawGradientRRect2(x + 50 - i, y + 25 + yOffset - i, healthWidth + i * 2, 8 + i * 2, 4 + i, gc1, gc2);
                    } else {
                        // Use static color glow (c1)
                        Color baseGlow = c1;
                        Color c = new Color(baseGlow.getRed(), baseGlow.getGreen(), baseGlow.getBlue(), alphaInt);
                        NanoVGHelper.drawRoundRect(x + 50 - i, y + 25 + yOffset - i, healthWidth + i * 2, 8 + i * 2, 4 + i, c);
                    }
                }
            }
        }

        // Draw Health Bar
        if (healthGradient.get()) {
            NanoVGHelper.drawGradientRRect2(x + 50, y + 25 + yOffset, healthWidth, 8, 4, c1, c2);
        } else {
            NanoVGHelper.drawGradientRRect(x + 50, y + 25 + yOffset, healthWidth, 8, 4, c1, c2);
        }

        // HP Text
        String hpText = hpMode.get() == HPmodeEn.HP ? String.format("%.1f", health) : String.format("%.0f%%", (health / maxHealth) * 100);
        // Moved down from 26f to 29f
        NanoVGHelper.drawCenteredString(hpText, x + 95, y + 29f + yOffset, FontLoader.bold(10), 10, Color.WHITE);

        // Name
        if (glow.get()) {
            float strength = glowStrength.get().floatValue();
            NanoVGHelper.drawGlowingString(target.getName().getString(), x + 50, y + 15 + yOffset, FontLoader.bold(14), 14, Color.WHITE, strength, 2);
        } else {
            NanoVGHelper.drawString(target.getName().getString(), x + 50, y + 15 + yOffset, FontLoader.bold(14), 14, -1, Color.WHITE);
        }
    }

    private void renderThunderHack(long vg, LivingEntity target, float health, float maxHealth, float animationFactor, float damageFactor) {
        NanoVGHelper.drawRoundRect(x, y, 70, 50, 6, new Color(0, 0, 0, 139));
        NanoVGHelper.drawRoundRect(x + 50, y, 100, 50, 6, new Color(0, 0, 0, 255));
        this.width = 150;
        this.height = 50;

        // Custom Image Logic
        if (imageMode.get() != ImageModeEn.None) {
            if (imageMode.get() == ImageModeEn.Anime || imageMode.get() == ImageModeEn.Custom) {
                // Draw THUD_TEX in the background rect
                int imageId = getSkinImageId(THUD_TEX);
                if (imageId != -1) {
                    NanoVGHelper.save();
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                        // Pattern fill for the image
                        NanoVG.nvgImagePattern(vg, x + 50, y, 100, 50, 0, imageId, 1f, paint);

                        // Masking with rounded rect
                        NanoVG.nvgBeginPath(vg);
                        NanoVG.nvgRoundedRect(vg, x + 50, y, 100, 50, 6);
                        NanoVG.nvgFillPaint(vg, paint);
                        NanoVG.nvgFill(vg);

                        // Dark overlay if needed
                        NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(0, 0, 0, 80)));
                        NanoVG.nvgFill(vg);
                    }
                    NanoVGHelper.restore();
                }
            }
        }

        // Particles Logic
        updateParticles(vg);

        if (target instanceof PlayerEntity player) {
            // Damage Effect: Shrink scale (1.0 -> 0.85 -> 1.0)
            float damageScale = 1.0f - (damageFactor * 0.15f);
            drawPlayerAvatar(player, x + 2.5f, y + 2.5f, 45, 5, damageScale, damageFactor);
        }

        // NanoVGHelper.drawShadow(x + 55, y + 22, 90, 8, blurRadius.get().floatValue(), new Color(0, 0, 0), 5, 0, 0);

        // Adjust Y positions based on showArmor
        float yOffset = showArmor.get() ? 0 : 5;

        float healthWidth = MathHelper.clamp(90 * (health / maxHealth), 3, 90);

        NanoVGHelper.drawGradientRRect(x + 55, y + 21 + yOffset, 90, 10, 2, new Color(20, 20, 20), new Color(40, 40, 40));

        // Calculate gradient colors
        Color c1 = healthColor.get();
        Color c2 = healthColor.get().darker();

        if (healthGradient.get()) {
            double speed = gradientSpeed.get();
            float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);

            float length = colorLength.get().floatValue();
            float frequency = 1.0f / length;

            float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
            float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);

            c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
            c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
        }

        // Health Bar Glow
        if (glow.get()) {
            // Draw manual bloom for stronger effect
            float strength = glowStrength.get().floatValue();

            // Smoother glow loop: use float steps and lower alpha per layer
            // Start from 0 to strength, step 0.5 for smoother gradient
            for (float i = 0.5f; i <= strength; i += 0.5f) {
                // Non-linear alpha falloff for "glowing core" look
                // (1 - (i/strength)^2) gives a sharper core and softer edge
                float normalizedDist = i / (strength + 2);
                float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                // Base alpha lowered to prevent over-saturation when stacking
                float alpha = alphaFactor * 0.15f;

                int alphaInt = MathHelper.clamp((int) (alpha * 255), 0, 255);
                if (alphaInt > 0) {
                    if (healthGradient.get()) {
                        Color gc1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt);
                        Color gc2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alphaInt);
                        NanoVGHelper.drawGradientRRect2(x + 55 - i, y + 21 + yOffset - i, healthWidth + i * 2, 10 + i * 2, 2 + i, gc1, gc2);
                    } else {
                        Color glowColor = c1;
                        Color c = new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), alphaInt);
                        NanoVGHelper.drawRoundRect(x + 55 - i, y + 21 + yOffset - i, healthWidth + i * 2, 10 + i * 2, 2 + i, c);
                    }
                }
            }
        }

        // Draw Health Bar
        if (healthGradient.get()) {
            NanoVGHelper.drawGradientRRect2(x + 55, y + 21 + yOffset, healthWidth, 10, 2, c1, c2);
        } else {
            NanoVGHelper.drawGradientRRect(x + 55, y + 21 + yOffset, healthWidth, 10, 2, c1, c2);
        }

        String hpText = hpMode.get() == HPmodeEn.HP ? String.format("%.1f", health) : String.format("%.0f%%", (health / maxHealth) * 100);

        // HP Text
        NanoVGHelper.drawCenteredString(hpText, x + 102, y + 24f + 3 + yOffset, FontLoader.bold(10), 10, Color.WHITE);

        // Name Glow
        if (glow.get()) {
            float strength = glowStrength.get().floatValue();
            NanoVGHelper.drawGlowingString(target.getName().getString(), x + 55, y + 14 + yOffset, FontLoader.bold(12), 12, Color.WHITE, strength, 2);
        } else {
            NanoVGHelper.drawString(target.getName().getString(), x + 55, y + 14 + yOffset, FontLoader.bold(12), 12, -1, Color.WHITE);
        }
    }

    private void renderThunderHackItems(DrawContext context, PlayerEntity target) {
        if (!showArmor.get()) {
            return;
        }

        // Armor
        ItemStack[] items = new ItemStack[]{target.getMainHandStack(), target.getEquippedStack(EquipmentSlot.HEAD), target.getEquippedStack(EquipmentSlot.CHEST), target.getEquippedStack(EquipmentSlot.LEGS), target.getEquippedStack(EquipmentSlot.FEET), target.getOffHandStack()};

        float xItemOffset = x + 60;
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) continue;
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(xItemOffset, y + 35);
            context.getMatrices().scale(0.75f, 0.75f);
            context.drawItem(itemStack, 0, 0);
            context.getMatrices().popMatrix();
            xItemOffset += 14;
        }
    }

    private void updateParticles(long vg) {
        if (timer.passedMS(1000D / 60D)) {
            ticks += 0.1f;
            for (int i = 0; i < particles.size(); i++) {
                Particles p = particles.get(i);
                p.updatePosition();
                if (p.opacity < 1) {
                    particles.remove(i);
                    i--;
                }
            }
            timer.reset();
        }

        if (target != null && target.hurtTime == 9 && !sentParticles) {
            for (int i = 0; i <= 6; i++) {
                Particles p = new Particles();
                // Mixing colors
                Color c1 = color.get();
                Color c2 = color2.get();
                float mixFactor = (float) ((Math.sin(ticks + x * 0.4f + i) + 1) * 0.5f);
                Color c = ColorUtil.interpolateColor(c1, c2, mixFactor);

                p.init(x + 75, y + 25, ThreadLocalRandom.current().nextFloat() * 6 - 3, ThreadLocalRandom.current().nextFloat() * 6 - 3, 20, c);
                particles.add(p);
            }
            sentParticles = true;
        }
        if (target != null && target.hurtTime == 8) sentParticles = false;

        for (Particles p : particles) {
            if (p.opacity > 4) {
                p.render(vg);
            }
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!espEnabled.get()) return;
        LivingEntity target = getCurrentTarget();
        if (target == null) return;

        rotation -= rotationSpeed.get().floatValue();
        if (rotation <= -360f) rotation += 360f;

        MatrixStack matrices = event.getMatrices();
        Vec3d cam = mc.getEntityRenderDispatcher().camera.getCameraPos();

        double ex = MathHelper.lerp(event.getTickDelta(), target.lastX, target.getX()) - cam.x;
        double ey = MathHelper.lerp(event.getTickDelta(), target.lastY, target.getY()) - cam.y;
        double ez = MathHelper.lerp(event.getTickDelta(), target.lastZ, target.getZ()) - cam.z;

        float entityHeight = target.getHeight();
        float size = espSize.get().floatValue() * 0.5f;

        matrices.push();
        matrices.translate(ex, ey + entityHeight * 0.5, ez);

        Camera camera = mc.gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

        drawTextureQuad(matrices, size);

        matrices.pop();
    }

    private void drawTextureQuad(MatrixStack matrices, float size) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        Color c1 = getColorForProgress(0);
        Color c2 = getColorForProgress(0.25f);
        Color c3 = getColorForProgress(0.5f);
        Color c4 = getColorForProgress(0.75f);

        buffer.vertex(matrix, -size, -size, 0).texture(0, 0).color(c1.getRGB());
        buffer.vertex(matrix, -size, size, 0).texture(0, 1).color(c2.getRGB());
        buffer.vertex(matrix, size, size, 0).texture(1, 1).color(c3.getRGB());
        buffer.vertex(matrix, size, -size, 0).texture(1, 0).color(c4.getRGB());

        RenderLayers.entityTranslucent(TARGET_TEX).draw(buffer.end());
    }

    private Color getColorForProgress(float progress) {
        float time = (float) ((System.currentTimeMillis() % 2000000) * waveSpeed.get() / 1000.0);
        float length = colorLength.get().floatValue();
        float frequency = 1.0f / length;
        float spatialPhase = progress * (float) Math.PI * 2 * frequency;
        float wave = (float) ((Math.sin(time + spatialPhase) + 1.0) / 2.0);
        return ColorUtil.interpolateColor(espColor1.get(), espColor2.get(), wave);
    }

    // ====================================================================================
    //                                  HELPERS
    // ====================================================================================

    private void drawPlayerAvatar(PlayerEntity player, float x, float y, float size, float radius, float scale, float damageFactor) {
        CompletableFuture<Optional<SkinTextures>> skinFuture = mc.getSkinProvider().fetchSkinTextures(player.getGameProfile());
        skinFuture.thenAccept(skinTextures -> {
            if (skinTextures.isEmpty()) return;
            Identifier skinTexture = skinTextures.get().body().texturePath();
            int imageId = getSkinImageId(skinTexture);
            if (imageId != -1) {
                long vg = NanoVGRenderer.INSTANCE.getContext();
                NanoVGHelper.save();

                float cx = x + size / 2f;
                float cy = y + size / 2f;
                NanoVGHelper.translate(vg, cx, cy);
                NanoVGHelper.scale(vg, scale, scale);
                NanoVGHelper.translate(vg, -cx, -cy);

                try (MemoryStack stack = MemoryStack.stackPush()) {
                    org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);

                    float faceScale = 8.0f;
                    float ox = x - size;
                    float oy = y - size;
                    float ex = size * faceScale;
                    float ey = size * faceScale;

                    NanoVG.nvgImagePattern(vg, ox, oy, ex, ey, 0, imageId, 1f, paint);
                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgRoundedRect(vg, x, y, size, size, radius);
                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgFill(vg);

                    // Red Damage Overlay
                    if (damageFactor > 0.01f) {
                        NanoVG.nvgBeginPath(vg);
                        NanoVG.nvgRoundedRect(vg, x, y, size, size, radius);
                        // Use damageFactor for alpha (max 0.6 to not fully obscure)
                        int alpha = (int) (damageFactor * 150);
                        NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 0, 0, alpha)));
                        NanoVG.nvgFill(vg);
                    }
                }
                NanoVGHelper.restore();
            } else {
                NanoVGHelper.drawRoundRect(x, y, size, size, radius, new Color(80, 80, 80, 200));
            }
        });
    }

    private int getSkinImageId(Identifier skinTexture) {
        int glId = 0;// TODO: mc.getTextureManager().getTexture(skinTexture).getGlId();
        Integer cached = skinImageCache.get(glId);
        if (cached != null) {
            return cached;
        }
        int imageId = NanoVGHelper.createImageFromHandle(glId, 64, 64);
        if (imageId != -1) {
            skinImageCache.put(glId, imageId);
        }
        return imageId;
    }

    private void renderMahiroBackground(float animValue) {
        float globalScale = MahiroScale.get().floatValue();

        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();

        AvatarPosEn avatarPos = MahiroAvatarPos.get();
        float heightIncrease = 0;
        float contentYOffset = 0;

        if (avatarPos == AvatarPosEn.OnBar) {
            float offset = MahiroOnBarHeight.get().floatValue();
            contentYOffset = offset; // Shift content down
            heightIncrease = offset; // Increase background height
        }

        this.width = baseW * globalScale;
        this.height = (baseH + heightIncrease) * globalScale;

        float cx = x + width / 2f;
        float cy = y + height / 2f;

        float w = width * animValue;
        float h = height * animValue;
        float rx = cx - w / 2f;
        float ry = cy - h / 2f;

        float r = MahiroRadius.get().floatValue() * globalScale * animValue;
        float blur = MahiroBlurRadius.get().floatValue() * globalScale;

        Shader2DUtil.drawRoundedBlur(
                rx, ry, w, h,
                r,
                new Color(0, 0, 0, 0),
                blur,
                1.0f
        );
    }

    private void renderMahiro(long vg, LivingEntity target, float health, float maxHealth, float animationFactor, float damageFactor) {
        float globalScale = MahiroScale.get().floatValue();

        NanoVGHelper.save();
        NanoVGHelper.translate(vg, x, y);
        NanoVGHelper.scale(vg, globalScale, globalScale);
        NanoVGHelper.translate(vg, -x, -y);

        float baseW = MahiroWidth.get().floatValue();
        float baseH = MahiroHeight.get().floatValue();
        float radius = MahiroRadius.get().floatValue();
        float barRadius = MahiroBarRadius.get().floatValue();
        float nameSize = MahiroNameSize.get().floatValue();
        AvatarPosEn avatarPos = MahiroAvatarPos.get();

        float padding = 6f;
        float avatarSize = baseH - padding * 2;
        float avatarX = x + padding;
        float avatarY = y + padding; // Avatar stays at top

        float contentYOffset = 0;
        float heightIncrease = 0;

        if (avatarPos == AvatarPosEn.OnBar) {
            float offset = MahiroOnBarHeight.get().floatValue();
            contentYOffset = offset; // Shift text/bar down
            heightIncrease = offset; // Increase BG height
        }

        float totalH = baseH + heightIncrease;

        // Background Rect
        NanoVGHelper.drawRoundRect(x, y, baseW, totalH, radius, new Color(0, 0, 0, 80));

        float contentX = x + padding + avatarSize + padding;
        float contentW = baseW - (padding + avatarSize + padding + padding);

        if (avatarPos == AvatarPosEn.OnBar) {
            contentX = x + padding;
            contentW = baseW - (padding + padding);
        }

        float barH = MahiroBarHeight.get().floatValue();

        // Name
        float nameXOffset = MahiroNameX.get().floatValue();
        float nameYOffset = MahiroNameY.get().floatValue();

        float nameY = y + padding + (nameSize / 2) + 2 + contentYOffset + nameYOffset;

        float textX = contentX + nameXOffset;
        if (avatarPos == AvatarPosEn.OnBar) {
            textX = x + padding + 2 + nameXOffset;
        }

        if (glow.get()) {
            NanoVGHelper.drawGlowingString(target.getName().getString(), textX, nameY, FontLoader.bold((int) nameSize), nameSize, Color.WHITE, glowStrength.get().floatValue(), 2);
        } else {
            NanoVGHelper.drawString(target.getName().getString(), textX, nameY, FontLoader.bold((int) nameSize), nameSize, Color.WHITE);
        }

        // HP Text
        String hpText = hpMode.get() == HPmodeEn.HP ? String.format("%.1f", health) : String.format("%.0f%%", (health / maxHealth) * 100);
        float hpW = NanoVGHelper.getTextWidth(hpText, FontLoader.bold((int) nameSize), nameSize);
        NanoVGHelper.drawString(hpText, x + baseW - padding - hpW, nameY, FontLoader.bold((int) nameSize), nameSize, Color.WHITE);

        // Health Bar
        // barY calculation: start from bottom of total height
        float barY = y + totalH - padding - barH;

        float healthPct = MathHelper.clamp(health / maxHealth, 0f, 1f);
        float delayPct = MathHelper.clamp(delayHealth / maxHealth, 0f, 1f);

        float barW = contentW * healthPct;
        float delayBarW = contentW * delayPct;

        // Bar Bg
        NanoVGHelper.drawRoundRect(contentX, barY, contentW, barH, barRadius, new Color(30, 30, 30));

        // Delay Bar
        if (MahiroDelay.get() && delayHealth > health) {
            NanoVGHelper.drawRoundRect(contentX, barY, delayBarW, barH, barRadius, MahiroDelayColor.get());
        }

        // Bar Gradient Logic
        Color c1 = healthColor.get();
        Color c2 = healthColor.get().darker();
        if (healthGradient.get()) {
            double speed = gradientSpeed.get();
            float time = (float) ((System.currentTimeMillis() % 2000000) * speed / 1000.0);
            float length = colorLength.get().floatValue();
            float frequency = 1.0f / length;
            float t1 = (float) ((Math.sin(time) + 1.0) / 2.0);
            float t2 = (float) ((Math.sin(time + frequency) + 1.0) / 2.0);
            c1 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t1);
            c2 = ColorUtil.interpolateColor(healthColor.get(), healthColor2.get(), t2);
        }

        // Bar Glow
        if (glow.get()) {
            float strength = glowStrength.get().floatValue();
            for (float i = 0.5f; i <= strength; i += 0.5f) {
                float normalizedDist = i / (strength + 2);
                float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                float a = alphaFactor * 0.15f;
                int alphaInt = MathHelper.clamp((int) (a * 255), 0, 255);

                if (alphaInt > 0) {
                    if (healthGradient.get()) {
                        Color gc1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt);
                        Color gc2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alphaInt);
                        NanoVGHelper.drawGradientRRect2(contentX - i, barY - i, barW + i * 2, barH + i * 2, barRadius + i, gc1, gc2);
                    } else {
                        Color glowColor = c1;
                        Color c = new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), alphaInt);
                        NanoVGHelper.drawRoundRect(contentX - i, barY - i, barW + i * 2, barH + i * 2, barRadius + i, c);
                    }
                }
            }
        }

        // Draw Main Bar
        if (healthGradient.get()) {
            NanoVGHelper.drawGradientRRect2(contentX, barY, barW, barH, barRadius, c1, c2);
        } else {
            NanoVGHelper.drawGradientRRect(contentX, barY, barW, barH, barRadius, c1, c2);
        }

        // Draw Avatar last if it's "OnBar" so it overlays
        if (target instanceof PlayerEntity player) {
            float damageScale = 1.0f - (damageFactor * 0.15f);
            drawPlayerAvatar(player, avatarX, avatarY, avatarSize, 6f, damageScale, damageFactor);
        } else {
            NanoVGHelper.drawRoundRect(avatarX, avatarY, avatarSize, avatarSize, 6f, new Color(80, 80, 80));
        }

        NanoVGHelper.restore();
    }

    // ====================================================================================
    //                                  PARTICLES
    // ====================================================================================

    private static class Particles {
        float x, y;
        float motionX, motionY;
        float opacity;
        Color color;

        public void init(float x, float y, float motionX, float motionY, float opacity, Color color) {
            this.x = x;
            this.y = y;
            this.motionX = motionX;
            this.motionY = motionY;
            this.opacity = opacity;
            this.color = color;
        }

        public void updatePosition() {
            this.x += this.motionX;
            this.y += this.motionY;
            this.opacity -= 0.5f; // Decay
        }

        public void render(long vg) {
            if (opacity <= 0) return;
            // Map opacity 0-20 to alpha 0-255 roughly
            int alpha = (int) MathHelper.clamp(opacity * 12, 0, 255);
            Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
            NanoVGHelper.drawCircle(x, y, 2f, c);
        }
    }
}
