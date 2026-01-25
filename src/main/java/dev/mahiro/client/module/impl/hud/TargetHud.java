package dev.mahiro.client.module.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
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
import dev.mahiro.client.utils.animations.impl.DecelerateAnimation;
import dev.mahiro.client.utils.animations.impl.EaseInOutQuad;
import dev.mahiro.client.utils.animations.impl.EaseOutSine;
import dev.mahiro.client.utils.animations.impl.SmoothStepAnimation;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TargetHud extends HudModule {

    public enum ColorMode {
        Rainbow,
        Wave,
        Custom
    }

    private final BoolValue hudEnabled = new BoolValue("HUD", "面板", true);
    private final BoolValue hudBlur = new BoolValue("Blur", "模糊", true, hudEnabled::get);
    private final NumberValue<Double> hudBlurStrength = new NumberValue<>("BlurStrength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> hudEnabled.get() && hudBlur.get());
    private final ColorValue hudColor = new ColorValue("Background", "背景颜色", new Color(30, 30, 30, 180), hudEnabled::get);
    private final BoolValue animationEnabled = new BoolValue("Animations", "动画", true, hudEnabled::get);
    private final NumberValue<Double> nameScrollSpeed = new NumberValue<>("Name Scroll Speed", "名字滚动速度", 20.0, 5.0, 60.0, 1.0, hudEnabled::get);
    private final NumberValue<Double> nameScrollPause = new NumberValue<>("Name Scroll Pause", "名字停顿时间", 1000.0, 200.0, 3000.0, 50.0, hudEnabled::get);

    private final BoolValue espEnabled = new BoolValue("ESP", "透视", true);
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 0, 0, 255), espEnabled::get);
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(0, 255, 255, 255), espEnabled::get);
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, espEnabled::get);

    private float rotation = 0f;
    private float animatedHealth = 0f;
    private LivingEntity lastTarget = null;
    private float lastResolvedHealth = 0f;
    private final Animation appearAnimation = new EaseInOutQuad(300, 1.0, Direction.BACKWARDS);
    private final Animation stateAnimation = new SmoothStepAnimation(240, 1.0, Direction.BACKWARDS);
    private final Animation hurtAnimation = new DecelerateAnimation(220, 1.0, Direction.BACKWARDS);
    private final Animation healthPulseAnimation = new EaseOutSine(240, 1.0, Direction.BACKWARDS);
    private final Map<Integer, Integer> skinImageCache = new ConcurrentHashMap<>();
    private final Map<Integer, Float> nameScrollOffsets = new ConcurrentHashMap<>();
    private final Map<Integer, Long> nameScrollLastTimes = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> nameScrollDirections = new ConcurrentHashMap<>();
    private final Map<Integer, Long> nameScrollPauseUntil = new ConcurrentHashMap<>();

    private static final float HUD_WIDTH = 160f;
    private static final float HUD_HEIGHT = 50f;
    private static final float RADIUS = 12f;
    private static final float AVATAR_RADIUS = 8f;
    private static final float AVATAR_SIZE = 40f;
    private static final float PADDING = 5f;

    private static final Identifier TARGET_TEX = Identifier.of("mahiro", "textures/particles/target.png");

    public TargetHud() {
        super("TargetHud", "目标显示", 100, 100);
        this.width = HUD_WIDTH;
        this.height = HUD_HEIGHT;
    }

    @Override
    protected void onEnable() {
        rotation = 0f;
        animatedHealth = 0f;
        lastTarget = null;
        lastResolvedHealth = 0f;
        appearAnimation.setDirection(Direction.BACKWARDS);
        appearAnimation.reset();
        stateAnimation.setDirection(Direction.BACKWARDS);
        stateAnimation.reset();
        hurtAnimation.setDirection(Direction.BACKWARDS);
        hurtAnimation.reset();
        healthPulseAnimation.setDirection(Direction.BACKWARDS);
        healthPulseAnimation.reset();
    }

    @Override
    protected void onDisable() {
        for (int imageId : skinImageCache.values()) {
            NanoVGHelper.deleteTexture(imageId);
        }
        skinImageCache.clear();
    }

    private LivingEntity getCurrentTarget() {
        KillAura killAura = Mahiro.MODULES.getModule(KillAura.class);
        if (killAura.isEnabled()) {
            Entity target = killAura.getCurrentTarget();
            if (target instanceof LivingEntity living) {
                return living;
            }
        }

        return null;
    }

    @Override
    public void onRender(DrawContext context) {
        if (!hudEnabled.get() || mc.currentScreen instanceof HudEditorScreen) return;

        LivingEntity target = getCurrentTarget();
        boolean hasTarget = target != null;

        if (!animationEnabled.get() && !hasTarget) {
            animatedHealth = 0f;
            lastTarget = null;
            return;
        }

        if (animationEnabled.get()) {
            appearAnimation.setDirection(hasTarget ? Direction.FORWARDS : Direction.BACKWARDS);
        }

        float appear = animationEnabled.get() ? appearAnimation.getOutput().floatValue() : 1f;
        if (!hasTarget && appear <= 0.01f) {
            animatedHealth = 0f;
            lastTarget = null;
            return;
        }

        LivingEntity renderTarget = hasTarget ? target : lastTarget;
        if (renderTarget == null) return;

        float resolvedHealth = hasTarget ? Managers.HEALTH.getHealth(target) : lastResolvedHealth;
        if (hasTarget && lastTarget != target) {
            animatedHealth = resolvedHealth;
            lastTarget = renderTarget;
            if (animationEnabled.get()) {
                stateAnimation.setDirection(Direction.FORWARDS);
                stateAnimation.reset();
            }
        }

        if (hasTarget) {
            if (Math.abs(resolvedHealth - lastResolvedHealth) > 0.01f && animationEnabled.get()) {
                healthPulseAnimation.setDirection(Direction.FORWARDS);
                healthPulseAnimation.reset();
            } else if (animationEnabled.get()) {
                healthPulseAnimation.setDirection(Direction.BACKWARDS);
            }
            lastResolvedHealth = resolvedHealth;
        }

        if (animationEnabled.get() && stateAnimation.finished(Direction.FORWARDS)) {
            stateAnimation.setDirection(Direction.BACKWARDS);
        }

        if (animationEnabled.get()) {
            hurtAnimation.setDirection(hasTarget && target.hurtTime > 0 ? Direction.FORWARDS : Direction.BACKWARDS);
        }

        animatedHealth = MathHelper.lerp(0.1f, animatedHealth, resolvedHealth);

        if (hudBlur.get()) {
            Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, y, width, height, RADIUS, ColorUtil.applyOpacity(hudColor.get(), appear), hudBlurStrength.get().floatValue(), 0.9f * appear);
        }

        float stateT = animationEnabled.get() ? stateAnimation.getOutput().floatValue() : 0f;
        float hurtT = animationEnabled.get() ? hurtAnimation.getOutput().floatValue() : 0f;
        float pulseT = animationEnabled.get() ? healthPulseAnimation.getOutput().floatValue() : 0f;
        float baseScale = animationEnabled.get() ? (0.92f + 0.08f * appear) : 1f;
        float scale = baseScale + 0.02f * stateT + 0.015f * hurtT;

        final LivingEntity finalTarget = renderTarget;
        float displayHealth = animatedHealth;
        NanoVGRenderer.INSTANCE.draw(vg -> renderHudContent(finalTarget, displayHealth, appear, scale, stateT, hurtT, pulseT));
    }

    @Override
    public void onEditor(DrawContext context) {
        if (hudBlur.get()) {
            Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, y, width, height, RADIUS, hudColor.get(), hudBlurStrength.get().floatValue(), 0.9f);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRoundRect(x, y, width, height, RADIUS, hudColor.get());
            NanoVGHelper.drawRoundRect(x + PADDING, y + PADDING, AVATAR_SIZE, AVATAR_SIZE, AVATAR_RADIUS, new Color(80, 80, 80, 200));

            float textX = x + PADDING + AVATAR_SIZE + 8f;
            NanoVGHelper.drawString("Player", textX, y + 16f, FontLoader.medium(14), 14f, Color.WHITE);

            float barX = textX;
            float barY = y + height - 18f;
            float barWidth = width - AVATAR_SIZE - PADDING * 3 - 8f;
            float barHeight = 10f;
            float barRadius = barHeight / 2f;

            NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, barRadius, new Color(50, 50, 50, 200));
            NanoVGHelper.drawRoundRect(barX, barY, barWidth * 0.75f, barHeight, barRadius, new Color(255, 255, 255, 230));
            NanoVGHelper.drawCenteredString("75", barX + barWidth / 2f, barY + barHeight / 2f + 1f, FontLoader.medium(10), 10f, new Color(50, 50, 50, 255));

            NanoVGHelper.drawRect(x, y, width, height, dragging ? new Color(100, 100, 255, 80) : new Color(0, 0, 0, 50));
        });
    }

    private void renderHudContent(LivingEntity target, float displayHealth, float alpha, float scale, float stateT, float hurtT, float pulseT) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float cx = x + width / 2f;
        float cy = y + height / 2f;
        NanoVG.nvgSave(vg);
        NanoVG.nvgTranslate(vg, cx, cy);
        NanoVG.nvgScale(vg, scale, scale);
        NanoVG.nvgTranslate(vg, -cx, -cy);

        Color baseBg = ColorUtil.applyOpacity(hudColor.get(), alpha);
        Color stateBg = ColorUtil.interpolateColor(baseBg, ColorUtil.applyOpacity(new Color(255, 255, 255, 40), alpha), stateT * 0.6f);
        NanoVGHelper.drawRoundRect(x, y, width, height, RADIUS, stateBg);
        if (hurtT > 0.01f) {
            Color hurtOverlay = ColorUtil.applyOpacity(new Color(255, 70, 70, 120), hurtT * alpha);
            NanoVGHelper.drawRoundRect(x, y, width, height, RADIUS, hurtOverlay);
        }

        if (target instanceof PlayerEntity player) {
            Identifier skinTexture = mc.getSkinProvider().getSkinTextures(player.getGameProfile()).texture();
            drawPlayerAvatar(skinTexture, x + PADDING, y + PADDING, AVATAR_SIZE, AVATAR_RADIUS);
        } else {
            NanoVGHelper.drawRoundRect(x + PADDING, y + PADDING, AVATAR_SIZE, AVATAR_SIZE, AVATAR_RADIUS, ColorUtil.applyOpacity(new Color(80, 80, 80, 200), alpha));
        }

        float textX = x + PADDING + AVATAR_SIZE + 8f;
        String name = target.getName().getString();
        int nameFont = FontLoader.medium(14);
        float nameFontSize = 14f;
        float nameWidth = NanoVGHelper.getTextWidth(name, nameFont, nameFontSize);
        float nameAvailableWidth = (x + width - PADDING) - textX;
        if (nameWidth > nameAvailableWidth) {
            float maxScroll = nameWidth - nameAvailableWidth;
            int nameKey = name.hashCode();
            long now = System.currentTimeMillis();
            float scrollFactor = MathHelper.clamp((alpha - 0.9f) / 0.1f, 0f, 1f);
            float scrollX = nameScrollOffsets.getOrDefault(nameKey, 0f);
            long lastTime = nameScrollLastTimes.getOrDefault(nameKey, now);
            int direction = nameScrollDirections.getOrDefault(nameKey, 1);
            long pauseUntil = nameScrollPauseUntil.getOrDefault(nameKey, 0L);

            if (scrollFactor > 0f && now >= pauseUntil) {
                long deltaMs = Math.max(0L, now - lastTime);
                float delta = nameScrollSpeed.get().floatValue() * (deltaMs / 1000f) * direction;
                scrollX += delta;
                if (scrollX >= maxScroll) {
                    scrollX = maxScroll;
                    direction = -1;
                    pauseUntil = now + nameScrollPause.get().longValue();
                } else if (scrollX <= 0f) {
                    scrollX = 0f;
                    direction = 1;
                    pauseUntil = now + nameScrollPause.get().longValue();
                }
            }

            nameScrollOffsets.put(nameKey, scrollX);
            nameScrollLastTimes.put(nameKey, now);
            nameScrollDirections.put(nameKey, direction);
            nameScrollPauseUntil.put(nameKey, pauseUntil);

            NanoVG.nvgSave(vg);
            NanoVG.nvgIntersectScissor(vg, textX, y + 4f, nameAvailableWidth, 18f);
            NanoVG.nvgTranslate(vg, -scrollX, 0);
            NanoVGHelper.drawString(name, textX, y + 16f, nameFont, nameFontSize, ColorUtil.applyOpacity(Color.WHITE, alpha));
            NanoVG.nvgRestore(vg);
        } else {
            NanoVGHelper.drawString(name, textX, y + 16f, nameFont, nameFontSize, ColorUtil.applyOpacity(Color.WHITE, alpha));
        }

        float barX = textX;
        float barY = y + height - 18f;
        float barWidth = width - AVATAR_SIZE - PADDING * 3 - 8f;
        float barHeight = 10f;
        float barRadius = barHeight / 2f;

        NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, barRadius, ColorUtil.applyOpacity(new Color(50, 50, 50, 200), alpha));

        float maxHealth = Math.max(1f, target.getMaxHealth());
        if (displayHealth > maxHealth) {
            maxHealth = displayHealth;
        }
        float healthPercent = Math.min(1f, Math.max(0f, displayHealth / maxHealth));
        float healthWidth = barWidth * healthPercent;

        if (healthWidth > 0) {
            Color baseBar = new Color(255, 255, 255, 230);
            Color hurtBar = new Color(255, 170, 170, 230);
            Color barColor = ColorUtil.interpolateColor(baseBar, hurtBar, hurtT);
            NanoVGHelper.drawRoundRect(barX, barY, healthWidth, barHeight, barRadius, ColorUtil.applyOpacity(barColor, alpha));
            if (pulseT > 0.01f) {
                float pulseW = Math.min(barWidth, healthWidth + 6f * pulseT);
                Color pulseColor = ColorUtil.applyOpacity(new Color(255, 255, 255, 120), alpha * pulseT);
                NanoVGHelper.drawRoundRect(barX, barY, pulseW, barHeight, barRadius, pulseColor);
            }
        }

        String healthText = String.format("%.0f", healthPercent * 100);
        Color healthTextColor = ColorUtil.interpolateColor(new Color(245, 245, 245, 230), new Color(45, 45, 45, 255), healthPercent);
        Color pulseTextColor = ColorUtil.interpolateColor(healthTextColor, new Color(255, 255, 255, 255), pulseT);
        float textSize = 10f * (1f + 0.06f * pulseT);
        int textFont = FontLoader.medium(Math.round(textSize));
        NanoVGHelper.drawCenteredString(healthText, barX + barWidth / 2f, barY + barHeight / 2f + 1f, textFont, textSize, ColorUtil.applyOpacity(pulseTextColor, alpha));
        NanoVG.nvgRestore(vg);
    }

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

    private void drawPlayerAvatar(Identifier skinTexture, float ax, float ay, float size, float radius) {
        int imageId = getSkinImageId(skinTexture);
        if (imageId == -1) {
            NanoVGHelper.drawRoundRect(ax, ay, size, size, radius, new Color(80, 80, 80, 200));
            return;
        }

        long vg = NanoVGRenderer.INSTANCE.getContext();
        float patternSize = size * 8f;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);

            float baseX = ax - size;
            float baseY = ay - size;
            NanoVG.nvgImagePattern(vg, baseX, baseY, patternSize, patternSize, 0f, imageId, 1f, paint);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRoundedRect(vg, ax, ay, size, size, radius);
            NanoVG.nvgFillPaint(vg, paint);
            NanoVG.nvgFill(vg);

            float overlayX = ax - size * 5f;
            float overlayY = ay - size;
            NanoVG.nvgImagePattern(vg, overlayX, overlayY, patternSize, patternSize, 0f, imageId, 1f, paint);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRoundedRect(vg, ax, ay, size, size, radius);
            NanoVG.nvgFillPaint(vg, paint);
            NanoVG.nvgFill(vg);
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
}
