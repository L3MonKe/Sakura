package dev.mahiro.client.module.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
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
import dev.mahiro.client.utils.animations.impl.EaseOutBack;
import dev.mahiro.client.utils.animations.impl.EaseOutCirc;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TargetHud extends HudModule {

    public enum HPmodeEn {
        HP, Percentage
    }

    public enum ImageModeEn {
        None, Anime, Custom
    }

    private final NumberValue<Double> blurRadius = new NumberValue<>("BallonBlur", "气泡模糊", 10.0, 1.0, 10.0, 1.0);
    private final EnumValue<HPmodeEn> hpMode = new EnumValue<>("HP Mode", "血量模式", HPmodeEn.HP);
    private final EnumValue<ImageModeEn> imageMode = new EnumValue<>("Image", "图片模式", ImageModeEn.Anime);
    
    // Glow Settings
    private final BoolValue glow = new BoolValue("Glow", "发光效果", true);
    private final NumberValue<Double> glowStrength = new NumberValue<>("GlowStrength", "发光强度", 5.0, 1.0, 20.0, 1.0, glow::get);
    
    // Renamed or kept for particles
    private final ColorValue color = new ColorValue("Color1", "颜色1", new Color(4, 59, 95));
    private final ColorValue color2 = new ColorValue("Color2", "颜色2", new Color(4, 59, 95));
    private final ColorValue healthColor = new ColorValue("HealthColor", "血条颜色", new Color(0, 255, 0), () -> true);
    private final BoolValue funTimeHP = new BoolValue("FunTimeHP", "FunTime血量", false);
    private final BoolValue absorp = new BoolValue("Absorption", "伤害吸收", true);

    private final BoolValue espEnabled = new BoolValue("ESP", "3D透视", true);
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 0, 0, 255), espEnabled::get);
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(0, 255, 255, 255), espEnabled::get);
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, espEnabled::get);

    private static final Identifier TARGET_TEX = Identifier.of("mahiro", "textures/particles/target.png");
    private static final Identifier THUD_TEX = Identifier.of("mahiro", "textures/hud/thud.png");

    // Animations
    private final Animation animation = new EaseOutBack(300, 1.0, Direction.BACKWARDS);
    private final Animation damageAnim = new EaseOutCirc(150, 1.0, Direction.BACKWARDS);
    
    private float displayHealth = -1;
    private float lastTargetHealth = -1;

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
        particles.clear();
        needsCacheClear = true; // Ensure clean state on enable
    }

    @Override
    protected void onDisable() {
        // Schedule cache clear for next render or enable to ensure thread safety
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
    
    @EventHandler
    public void onTick(TickEvent.Pre event) {
        // Animation updates if needed independently
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
            }
            lastTargetHealth = health;
            
            // Damage Pulse Logic (Reset if done)
            if (damageAnim.getDirection() == Direction.FORWARDS && damageAnim.isDone()) {
                damageAnim.setDirection(Direction.BACKWARDS);
            }
        }
        
        // Smooth Health Logic
        if (displayHealth == -1) displayHealth = health;
        // DrawContext doesn't have getTickDelta() directly in some mappings/versions
        // Usually we can get it from RenderTickCounter or just use a fixed step for smoothing
        // Since we are in onRender(DrawContext), let's check if we can get partial ticks from MC
        float tickDelta = mc.getRenderTickCounter().getTickDelta(false);
        displayHealth = MathHelper.lerp(tickDelta * 0.2f, displayHealth, health);
        
        // Render Background and Main Elements via NanoVG
        final LivingEntity renderTarget = target;
        final float finalHealth = displayHealth; // Use smooth health
        final float finalMaxHealth = maxHealth;
        final float damageFactor = damageAnim.getOutput().floatValue();
        
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
            
            renderThunderHack(vg, renderTarget, finalHealth, finalMaxHealth, animValue, damageFactor);
            
            NanoVGHelper.restore();
        });
        
        // 2. Render Items (Armor, Hands) - Must be done outside NanoVG frame usually to use DrawContext
        if (target instanceof PlayerEntity player && animValue > 0.1f) {
             context.getMatrices().push();
             
             // No custom X/Y animation translation anymore as per request
             
             // Scale from center
             float centerX = x + width / 2f;
             float centerY = y + height / 2f;
             context.getMatrices().translate(centerX, centerY, 0);
             context.getMatrices().scale(animValue, animValue, 1f);
             context.getMatrices().translate(-centerX, -centerY, 0);

             renderThunderHackItems(context, player);

             context.getMatrices().pop();
        }
    }

    // ====================================================================================
    //                                  RENDER LOGIC
    // ====================================================================================

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

        NanoVGHelper.drawShadow(x + 55, y + 22, 90, 8, blurRadius.get().floatValue(), new Color(0, 0, 0), 5, 0, 0);

        float healthWidth = MathHelper.clamp(90 * (health / maxHealth), 3, 90);

        NanoVGHelper.drawGradientRRect(x + 55, y + 21, 90, 10, 2, new Color(20, 20, 20), new Color(40, 40, 40));
        
        // Health Bar Glow
        if (glow.get()) {
            // Draw manual bloom for stronger effect
            float strength = glowStrength.get().floatValue();
            Color glowColor = healthColor.get();
            // Smoother glow loop: use float steps and lower alpha per layer
            // Start from 0 to strength, step 0.5 for smoother gradient
            for (float i = 0.5f; i <= strength; i += 0.5f) {
                // Non-linear alpha falloff for "glowing core" look
                // (1 - (i/strength)^2) gives a sharper core and softer edge
                float normalizedDist = i / (strength + 2);
                float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                // Base alpha lowered to prevent over-saturation when stacking
                float alpha = alphaFactor * 0.15f; 
                
                int alphaInt = MathHelper.clamp((int)(alpha * 255), 0, 255);
                if (alphaInt > 0) {
                    Color c = new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), alphaInt);
                    NanoVGHelper.drawRoundRect(x + 55 - i, y + 21 - i, healthWidth + i * 2, 10 + i * 2, 2 + i, c);
                }
            }
        }
        
        NanoVGHelper.drawGradientRRect(x + 55, y + 21, healthWidth, 10, 2, healthColor.get(), healthColor.get().darker());

        String hpText = hpMode.get() == HPmodeEn.HP ? String.format("%.1f", health) : String.format("%.0f%%", (health / maxHealth) * 100);
        
        // HP Text
        NanoVGHelper.drawCenteredString(hpText, x + 102, y + 24f + 3, FontLoader.bold(10), 10, Color.WHITE);

        // Name Glow
        if (glow.get()) {
            float strength = glowStrength.get().floatValue();
            NanoVGHelper.drawGlowingString(target.getName().getString(), x + 55, y + 14, FontLoader.bold(12), 12, Color.WHITE, strength, 2);
        } else {
            NanoVGHelper.drawString(target.getName().getString(), x + 55, y + 14, FontLoader.bold(12), 12, -1, Color.WHITE);
        }
    }
    
    private void renderThunderHackItems(DrawContext context, PlayerEntity target) {
        // Armor
        List<ItemStack> armor = target.getInventory().armor;
        ItemStack[] items = new ItemStack[]{target.getMainHandStack(), armor.get(3), armor.get(2), armor.get(1), armor.get(0), target.getOffHandStack()};

        float xItemOffset = x + 60;
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) continue;
            context.getMatrices().push();
            context.getMatrices().translate(xItemOffset, y + 35, 0);
            context.getMatrices().scale(0.75f, 0.75f, 0.75f);
            context.drawItem(itemStack, 0, 0);
            context.getMatrices().pop();
            xItemOffset += 14;
        }

        // Potions
        drawPotionEffect(context, target);
    }
    
    private void updateParticles(long vg) {
        if (timer.passedMS(1000 / 60)) {
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

    private void drawPotionEffect(DrawContext context, PlayerEntity entity) {
        StringBuilder finalString = new StringBuilder();
        for (StatusEffectInstance potionEffect : entity.getStatusEffects()) {
            StatusEffect potion = potionEffect.getEffectType().value();
            if ((potion != StatusEffects.REGENERATION.value()) && (potion != StatusEffects.SPEED.value()) && (potion != StatusEffects.STRENGTH.value()) && (potion != StatusEffects.WEAKNESS.value())) {
                continue;
            }
            boolean potRanOut = (double) potionEffect.getDuration() != 0.0;
            if (!entity.hasStatusEffect(potionEffect.getEffectType()) || !potRanOut) continue;
            
            String name = getPotionName(potion);
            finalString.append(name).append(potionEffect.getAmplifier() < 1 ? "" : potionEffect.getAmplifier() + 1).append(" ").append(getDurationString(potionEffect)).append(" ");
        }
        
        if (finalString.length() > 0) {
            context.drawText(mc.textRenderer, finalString.toString(), (int)(x + 55), (int)(y + 15), new Color(0x8D8D8D).getRGB(), true);
        }
    }

    public String getDurationString(StatusEffectInstance pe) {
        if (pe.isInfinite()) {
            return "*:*";
        } else return pe.getDuration() / 1200 + ":" + (pe.getDuration() % 1200) / 20;
    }

    public static String getPotionName(StatusEffect p) {
        if (p == StatusEffects.REGENERATION.value()) return "Reg";
        else if (p == StatusEffects.STRENGTH.value()) return "Str";
        else if (p == StatusEffects.SPEED.value()) return "Spd";
        else if (p == StatusEffects.HASTE.value()) return "H";
        else if (p == StatusEffects.WEAKNESS.value()) return "W";
        else if (p == StatusEffects.RESISTANCE.value()) return "Res";
        return "pon";
    }

    // ====================================================================================
    //                                  3D ESP
    // ====================================================================================
    
    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!espEnabled.get()) return;
        LivingEntity target = getCurrentTarget();
        if (target == null) return;

        rotation -= rotationSpeed.get().floatValue();
        if (rotation <= -360f) rotation += 360f;

        MatrixStack matrices = event.getMatrices();
        Vec3d cam = mc.getEntityRenderDispatcher().camera.getPos();

        double ex = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX()) - cam.x;
        double ey = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY()) - cam.y;
        double ez = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ()) - cam.z;

        float entityHeight = target.getHeight();
        float size = espSize.get().floatValue() * 0.5f;

        matrices.push();
        matrices.translate(ex, ey + entityHeight * 0.5, ez);

        Camera camera = mc.gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);

        RenderSystem.setShaderTexture(0, TARGET_TEX);

        drawTextureQuad(matrices, size);

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

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

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private Color getColorForProgress(float progress) {
        float wave = (float) Math.sin((progress * Math.PI * 2) + (System.currentTimeMillis() / 1000f * waveSpeed.get()));
        wave = (wave + 1f) / 2f;
        return ColorUtil.interpolateColor(espColor1.get(), espColor2.get(), wave);
    }

    // ====================================================================================
    //                                  HELPERS
    // ====================================================================================

    private void drawPlayerAvatar(PlayerEntity player, float x, float y, float size, float radius, float scale, float damageFactor) {
        Identifier skinTexture = mc.getSkinProvider().getSkinTextures(player.getGameProfile()).texture();
        int imageId = getSkinImageId(skinTexture);
        if (imageId != -1) {
             long vg = NanoVGRenderer.INSTANCE.getContext();
             NanoVGHelper.save();
             
             // Apply scale centered on avatar
             float cx = x + size / 2f;
             float cy = y + size / 2f;
             NanoVGHelper.translate(vg, cx, cy);
             NanoVGHelper.scale(vg, scale, scale);
             NanoVGHelper.translate(vg, -cx, -cy);
             
             try (MemoryStack stack = MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);
                
                // Calculate pattern to focus on the face (8, 8) with size (8, 8) in a 64x64 texture
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
                    int alpha = (int)(damageFactor * 150); 
                    NanoVG.nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 0, 0, alpha)));
                    NanoVG.nvgFill(vg);
                }
             }
             NanoVGHelper.restore();
        } else {
             NanoVGHelper.drawRoundRect(x, y, size, size, radius, new Color(80, 80, 80, 200));
        }
    }

    private int getSkinImageId(Identifier skinTexture) {
        int glId = mc.getTextureManager().getTexture(skinTexture).getGlId();
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
