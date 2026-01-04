package dev.sakura.client.module.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.combat.CrystalAura;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
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

import java.awt.*;

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
    private final ColorValue hudAccentColor = new ColorValue("AccentColor", "强调色", new Color(255, 100, 100, 255), hudEnabled::get);

    private final BoolValue espEnabled = new BoolValue("ESP", "透视", true);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("ESPMode", "透视模式", ColorMode.Rainbow, espEnabled::get);
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 0, 0, 255), () -> espEnabled.get() && colorMode.is(ColorMode.Custom));
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(0, 255, 255, 255), () -> espEnabled.get() && colorMode.is(ColorMode.Custom));
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, () -> espEnabled.get() && colorMode.is(ColorMode.Wave));

    private float rotation = 0f;
    private float animatedHealth = 0f;
    private LivingEntity lastTarget = null;

    private static final float HUD_WIDTH = 160f;
    private static final float HUD_HEIGHT = 50f;
    private static final float RADIUS = 12f;
    private static final float AVATAR_RADIUS = 8f;
    private static final float AVATAR_SIZE = 40f;
    private static final float PADDING = 5f;

    private static final Identifier TARGET_TEX = Identifier.of("sakura", "textures/particles/target.png");
    private static final Identifier TARGET1_TEX = Identifier.of("sakura", "textures/particles/target1.png");

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
    }

    private LivingEntity getCurrentTarget() {
        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura != null && killAura.isEnabled()) {
            Entity target = killAura.getCurrentTarget();
            if (target instanceof LivingEntity living) {
                return living;
            }
        }

        CrystalAura crystalAura = Sakura.MODULES.getModule(CrystalAura.class);
        if (crystalAura != null && crystalAura.isEnabled()) {
            PlayerEntity target = crystalAura.getCurrentTarget();
            if (target != null) {
                return target;
            }
        }

        return null;
    }

    @Override
    public void renderInGame(DrawContext context) {
        if (!hudEnabled.get()) return;

        LivingEntity target = getCurrentTarget();
        if (target == null) {
            animatedHealth = 0f;
            lastTarget = null;
            return;
        }

        if (lastTarget != target) {
            animatedHealth = target.getHealth();
            lastTarget = target;
        }

        float targetHealth = target.getHealth();
        animatedHealth = MathHelper.lerp(0.1f, animatedHealth, targetHealth);

        this.currentContext = context;

        if (hudBlur.get()) {
            Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, y, width, height, RADIUS, hudColor.get(), hudBlurStrength.get().floatValue(), 0.9f);
        }

        final LivingEntity finalTarget = target;
        NanoVGRenderer.INSTANCE.draw(vg -> renderHudContent(vg, finalTarget));

        if (target instanceof PlayerEntity player) {
            RenderSystem.enableBlend();
            Identifier skinTexture = mc.getSkinProvider().getSkinTextures(player.getGameProfile()).texture();
            context.drawTexture(RenderLayer::getGuiTextured, skinTexture, (int) (x + PADDING), (int) (y + PADDING), 8, 8, (int) AVATAR_SIZE, (int) AVATAR_SIZE, 8, 8, 64, 64);
            RenderSystem.disableBlend();

            NanoVGRenderer.INSTANCE.draw(vg -> drawAvatarCornerMask(x + PADDING, y + PADDING, AVATAR_SIZE, AVATAR_RADIUS, hudColor.get()));
        }
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

        this.currentContext = context;

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

    @Override
    public void onRenderContent() {
    }

    private void renderHudContent(long vg, LivingEntity target) {
        NanoVGHelper.drawRoundRect(x, y, width, height, RADIUS, hudColor.get());

        if (!(target instanceof PlayerEntity)) {
            NanoVGHelper.drawRoundRect(x + PADDING, y + PADDING, AVATAR_SIZE, AVATAR_SIZE, AVATAR_RADIUS, new Color(80, 80, 80, 200));
        }

        float textX = x + PADDING + AVATAR_SIZE + 8f;
        String name = target.getName().getString();
        if (name.length() > 14) {
            name = name.substring(0, 14) + "...";
        }
        NanoVGHelper.drawString(name, textX, y + 16f, FontLoader.medium(14), 14f, Color.WHITE);

        float barX = textX;
        float barY = y + height - 18f;
        float barWidth = width - AVATAR_SIZE - PADDING * 3 - 8f;
        float barHeight = 10f;
        float barRadius = barHeight / 2f;

        NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, barRadius, new Color(50, 50, 50, 200));

        float maxHealth = target.getMaxHealth();
        float healthPercent = Math.min(1f, Math.max(0f, animatedHealth / maxHealth));
        float healthWidth = barWidth * healthPercent;

        if (healthWidth > 0) {
            NanoVGHelper.drawRoundRect(barX, barY, healthWidth, barHeight, barRadius, new Color(255, 255, 255, 230));
        }

        String healthText = String.format("%.0f", healthPercent * 100);
        NanoVGHelper.drawCenteredString(healthText, barX + barWidth / 2f, barY + barHeight / 2f + 1f, FontLoader.medium(10), 10f, new Color(50, 50, 50, 255));
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

        Identifier texture = colorMode.is(ColorMode.Rainbow) ? TARGET1_TEX : TARGET_TEX;
        RenderSystem.setShaderTexture(0, texture);

        drawTextureQuad(matrices, size);

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

        matrices.pop();
    }

    private void drawTextureQuad(MatrixStack matrices, float size) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        float halfSize = size;
        Color c1 = getColorForProgress(0);
        Color c2 = getColorForProgress(0.25f);
        Color c3 = getColorForProgress(0.5f);
        Color c4 = getColorForProgress(0.75f);

        if (colorMode.is(ColorMode.Rainbow)) {
            c1 = c2 = c3 = c4 = Color.WHITE;
        }

        buffer.vertex(matrix, -halfSize, -halfSize, 0).texture(0, 0).color(c1.getRGB());
        buffer.vertex(matrix, -halfSize, halfSize, 0).texture(0, 1).color(c2.getRGB());
        buffer.vertex(matrix, halfSize, halfSize, 0).texture(1, 1).color(c3.getRGB());
        buffer.vertex(matrix, halfSize, -halfSize, 0).texture(1, 0).color(c4.getRGB());

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private Color getColorForProgress(float progress) {
        switch (colorMode.get()) {
            case Rainbow -> {
                float hue = (progress + System.currentTimeMillis() / 5000f) % 1f;
                return Color.getHSBColor(hue, 0.8f, 1f);
            }
            case Wave -> {
                float wave = (float) Math.sin((progress * Math.PI * 2) + (System.currentTimeMillis() / 1000f * waveSpeed.get()));
                wave = (wave + 1f) / 2f;
                return ColorUtil.interpolateColor(espColor1.get(), espColor2.get(), wave);
            }
            case Custom -> {
                return ColorUtil.interpolateColor(espColor1.get(), espColor2.get(), progress);
            }
        }
        return Color.WHITE;
    }

    private void drawAvatarCornerMask(float ax, float ay, float size, float radius, Color bgColor) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        org.lwjgl.nanovg.NVGColor color = NanoVGHelper.nvgColor(bgColor);

        org.lwjgl.nanovg.NanoVG.nvgBeginPath(vg);
        org.lwjgl.nanovg.NanoVG.nvgRect(vg, ax - 1, ay - 1, size + 2, size + 2);
        org.lwjgl.nanovg.NanoVG.nvgPathWinding(vg, org.lwjgl.nanovg.NanoVG.NVG_HOLE);
        org.lwjgl.nanovg.NanoVG.nvgRoundedRect(vg, ax, ay, size, size, radius);
        org.lwjgl.nanovg.NanoVG.nvgFillColor(vg, color);
        org.lwjgl.nanovg.NanoVG.nvgFill(vg);
    }
}