package dev.mzc.client.module.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.render.Shader2DUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TargetHud extends HudModule {

    public enum ColorMode {
        Rainbow("彩虹"),
        Wave("波浪"),
        Single("单色");

        private final String cnName;

        ColorMode(String cnName) {
            this.cnName = cnName;
        }
    }

    public enum HealthBarColorMode {
        Single("单色"),
        Client("客户端"),
        Rainbow("彩虹"),
        Astolfo("Astolfo");

        private final String cnName;

        HealthBarColorMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final BoolValue hudEnabled = new BoolValue("HUD", "面板", true);
    private final BoolValue teamCheck = new BoolValue("Team Check", "队伍检测", true);
    private final ColorValue hudColor = new ColorValue("Background", "背景颜色", new Color(30, 30, 30, 180), hudEnabled::get);
    private final BoolValue hudBlur = new BoolValue("Blur", "模糊", true, hudEnabled::get);
    private final BoolValue avatarHitAnim = new BoolValue("AvatarHitAnim", "头像受伤动画", true, hudEnabled::get);
    private final NumberValue<Double> hudBlurStrength = new NumberValue<>("BlurStrength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> hudEnabled.get() && hudBlur.get());
    private final EnumValue<HealthBarColorMode> healthBarColorMode = new EnumValue<>("HealthBarMode", "血量条模式", HealthBarColorMode.Client, hudEnabled::get);
    private final ColorValue healthBarColor = new ColorValue("HealthBarColor", "血量条颜色", new Color(255, 100, 100, 255), () -> hudEnabled.get() && healthBarColorMode.is(HealthBarColorMode.Single));

    private final BoolValue espEnabled = new BoolValue("ESP", "透视", true);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("ESPMode", "透视模式", ColorMode.Rainbow, espEnabled::get);
    private final ColorValue espColor1 = new ColorValue("ESPColor1", "透视颜色1", new Color(255, 0, 0, 255), () -> espEnabled.get() && colorMode.is(ColorMode.Single));
    private final ColorValue espColor2 = new ColorValue("ESPColor2", "透视颜色2", new Color(0, 255, 255, 255), () -> espEnabled.get() && colorMode.is(ColorMode.Single));
    private final NumberValue<Double> espSize = new NumberValue<>("ESPSize", "透视大小", 1.2, 0.5, 3.0, 0.1, espEnabled::get);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotSpeed", "旋转速度", 2.0, 0.5, 10.0, 0.1, espEnabled::get);
    private final NumberValue<Double> waveSpeed = new NumberValue<>("WaveSpeed", "波动速度", 3.0, 0.5, 10.0, 0.1, () -> espEnabled.get() && colorMode.is(ColorMode.Wave));
    private final NumberValue<Double> targetRange = new NumberValue<>("TargetRange", "锁定范围", 6.0, 1.0, 20.0, 0.1, () -> true);
    private final NumberValue<Double> maxTargets = new NumberValue<>("MaxTargets", "最大名牌数", 1.0, 1.0, 3.0, 1.0, hudEnabled::get);

    private float rotation = 0f;

    private static final float HUD_WIDTH = 160f;
    private static final float HUD_HEIGHT = 50f;
    private static final float RADIUS = 12f;
    private static final float AVATAR_RADIUS = 8f;
    private static final float AVATAR_SIZE = 40f;
    private static final float PADDING = 5f;

    private static final Identifier TARGET_TEX = Identifier.of("sakura", "textures/particles/target.png");
    private static final Identifier TARGET1_TEX = Identifier.of("sakura", "textures/particles/target1.png");

    private static class TargetEntry {
        private final LivingEntity entity;
        private float fade;
        private float animatedHealth;
        private float lastHealth;
        private float hitAnim;

        private TargetEntry(LivingEntity entity, float fade, float animatedHealth) {
            this.entity = entity;
            this.fade = fade;
            this.animatedHealth = animatedHealth;
            this.lastHealth = animatedHealth;
            this.hitAnim = 0f;
        }
    }

    private final List<TargetEntry> entries = new ArrayList<>();

    public TargetHud() {
        super("TargetHud", "目标显示", 100, 100);
        this.width = HUD_WIDTH;
        this.height = HUD_HEIGHT;
    }

    @Override
    protected void onEnable() {
        rotation = 0f;
        entries.clear();
    }

    private List<LivingEntity> getTargets() {
        List<LivingEntity> result = new ArrayList<>();
        if (mc.player == null || mc.world == null) return result;

        double maxDist = targetRange.get() * targetRange.get();

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living == mc.player) continue;
            if (!living.isAlive()) continue;
            if (!(living instanceof PlayerEntity) && !(living instanceof HostileEntity)) continue;
            if (teamCheck.get() && !isEnemy(living)) continue;

            double dist = mc.player.squaredDistanceTo(living);
            if (dist > maxDist) continue;

            result.add(living);
        }

        result.sort(Comparator.comparingDouble(e -> mc.player.squaredDistanceTo(e)));

        int limit = maxTargets.get().intValue();
        if (result.size() > limit) {
            return new ArrayList<>(result.subList(0, limit));
        }

        return result;
    }

    private LivingEntity getCurrentTarget() {
        List<LivingEntity> list = getTargets();
        if (list.isEmpty()) return null;
        return list.get(0);
    }


    @Override
    public void renderInGame(DrawContext context) {
        if (!hudEnabled.get()) return;

        List<LivingEntity> targets = getTargets();

        for (int i = 0; i < entries.size(); i++) {
            TargetEntry entry = entries.get(i);
            if (!targets.contains(entry.entity) || !entry.entity.isAlive()) {
                entry.fade = MathHelper.lerp(0.12f, entry.fade, 0f);
                if (entry.fade < 0.01f) {
                    entries.remove(i--);
                }
            }
        }

        for (LivingEntity target : targets) {
            TargetEntry entry = null;
            for (TargetEntry e : entries) {
                if (e.entity == target) {
                    entry = e;
                    break;
                }
            }
            if (entry == null) {
                entry = new TargetEntry(target, 0f, target.getHealth());
                entries.add(entry);
            }
            entry.fade = MathHelper.lerp(0.12f, entry.fade, 1f);

            float currentHealth = target.getHealth();
            if (avatarHitAnim.get()) {
                if (currentHealth < entry.lastHealth - 0.1f) {
                    entry.hitAnim = 1.0f;
                }
                if (entry.hitAnim > 0f) {
                    entry.hitAnim = Math.max(0f, entry.hitAnim - (1f / 30f));
                }
            }

            entry.animatedHealth = MathHelper.lerp(0.1f, entry.animatedHealth, currentHealth);
            entry.lastHealth = currentHealth;
        }

        int limit = maxTargets.get().intValue();
        if (entries.size() > limit) {
            entries.sort(Comparator.comparingDouble(e -> (double) e.fade));
            while (entries.size() > limit) {
                entries.remove(0);
            }
        }

        if (entries.isEmpty()) return;

        if (mc.player != null) {
            entries.sort(Comparator.comparingDouble(e -> mc.player.squaredDistanceTo(e.entity)));
        }

        float spacing = 4f;

        if (hudBlur.get()) {
            for (int i = 0; i < entries.size(); i++) {
                TargetEntry entry = entries.get(i);
                if (entry.fade <= 0.99f) continue;
                float offsetY = y + i * (HUD_HEIGHT + spacing);
                Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, offsetY, width, HUD_HEIGHT, RADIUS, hudColor.get(), hudBlurStrength.get().floatValue(), 0.9f);
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            for (int i = 0; i < entries.size(); i++) {
                TargetEntry entry = entries.get(i);
                float offsetY = y + i * (HUD_HEIGHT + spacing);
                float alpha = entry.fade;
                float scale = entry.fade;
                renderHudContent(vg, entry, x, offsetY, alpha, scale);
            }
        });

        for (int i = 0; i < entries.size(); i++) {
            TargetEntry entry = entries.get(i);
            if (!(entry.entity instanceof PlayerEntity player)) continue;

            Identifier skin = mc.getSkinProvider().getSkinTextures(player.getGameProfile()).texture();
            MatrixStack matrices = context.getMatrices();

            float offsetY = y + i * (HUD_HEIGHT + spacing);
            float scale = entry.fade;
            float alpha = entry.fade;

            float avatarScale = scale;
            float r = 1f, g = 1f, b = 1f;
            if (avatarHitAnim.get() && entry.hitAnim > 0f) {
                float t = entry.hitAnim;
                float squash = 1f - 0.2f * t;
                avatarScale = scale * squash;
                g = 1f - 0.8f * t;
                b = 1f - 0.8f * t;
            }

            float centerX = x + PADDING + AVATAR_SIZE / 2f;
            float centerY = offsetY + PADDING + AVATAR_SIZE / 2f;

            matrices.push();
            matrices.translate(centerX, centerY, 0f);
            matrices.scale(avatarScale, avatarScale, 1f);
            matrices.translate(-centerX, -centerY, 0f);

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(r, g, b, alpha);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderTexture(0, skin);
            drawRoundedAvatar(matrices, x + PADDING, offsetY + PADDING, AVATAR_SIZE, AVATAR_RADIUS, alpha);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            matrices.pop();
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

    private void renderHudContent(long vg, TargetEntry entry, float baseX, float baseY, float alpha, float scale) {
        if (scale <= 0f) return;

        float centerX = baseX + width / 2f;
        float centerY = baseY + HUD_HEIGHT / 2f;

        org.lwjgl.nanovg.NanoVG.nvgSave(vg);
        org.lwjgl.nanovg.NanoVG.nvgTranslate(vg, centerX, centerY);
        org.lwjgl.nanovg.NanoVG.nvgScale(vg, scale, scale);
        org.lwjgl.nanovg.NanoVG.nvgTranslate(vg, -centerX, -centerY);

        NanoVGHelper.drawRoundRect(baseX, baseY, width, HUD_HEIGHT, RADIUS, withAlpha(hudColor.get(), alpha));

        if (!(entry.entity instanceof PlayerEntity)) {
            NanoVGHelper.drawRoundRect(baseX + PADDING, baseY + PADDING, AVATAR_SIZE, AVATAR_SIZE, AVATAR_RADIUS, withAlpha(new Color(80, 80, 80, 200), alpha));
        }

        float textX = baseX + PADDING + AVATAR_SIZE + 8f;
        String name = entry.entity.getName().getString();
        if (name.length() > 14) {
            name = name.substring(0, 14) + "...";
        }
        NanoVGHelper.drawString(name, textX, baseY + 16f, FontLoader.medium(14), 14f, withAlpha(Color.WHITE, alpha));

        float barX = textX;
        float barY = baseY + HUD_HEIGHT - 18f;
        float barWidth = width - AVATAR_SIZE - PADDING * 3 - 8f;
        float barHeight = 10f;
        float barRadius = barHeight / 2f;

        NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, barRadius, withAlpha(new Color(50, 50, 50, 200), alpha));

        float maxHealth = entry.entity.getMaxHealth();
        float healthPercent = Math.min(1f, Math.max(0f, entry.animatedHealth / maxHealth));
        float healthWidth = barWidth * healthPercent;

        if (healthWidth > 0) {
            Color hpColor = withAlpha(getHealthBarColor(0), alpha);
            NanoVGHelper.drawRoundRect(barX, barY, healthWidth, barHeight, barRadius, hpColor);
        }

        String healthText = String.format("%.0f", healthPercent * 100);
        NanoVGHelper.drawCenteredString(healthText, barX + barWidth / 2f, barY + barHeight / 2f + 1f, FontLoader.medium(10), 10f, withAlpha(new Color(50, 50, 50, 255), alpha));

        org.lwjgl.nanovg.NanoVG.nvgRestore(vg);
    }

    private Color getHealthBarColor(long timeOffset) {
        return switch (healthBarColorMode.get()) {
            case Single -> healthBarColor.get();
            case Client -> ClickGui.color(0);
            case Rainbow -> {
                float hue = ((System.currentTimeMillis() + timeOffset) % 3000 / 3000f);
                yield Color.getHSBColor(hue, 0.8f, 1f);
            }
            case Astolfo -> {
                double speed = 0.5;
                double hue = (System.currentTimeMillis() * speed + timeOffset) / 1000.0;
                hue = hue % 1.0;
                if (hue > 0.5) hue = 0.5 - (hue - 0.5);
                hue = hue + 0.5;
                yield Color.getHSBColor((float) hue, 0.5f, 1f);
            }
        };
    }

    private Color withAlpha(Color color, float alpha) {
        int a = (int) (color.getAlpha() * alpha);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, a)));
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

    private void drawRoundedAvatar(MatrixStack matrices, float ax, float ay, float size, float radius, float alpha) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float fromX = ax;
        float fromY = ay;
        float toX = ax + size;
        float toY = ay + size;
        float width = toX - fromX;
        float height = toY - fromY;
        float r = radius;
        if (r > width / 2f || r > height / 2f) {
            r = Math.min(width / 2f, height / 2f);
        }
        float maxRadius = Math.min(width, height) / 2f;
        r = Math.min(r, maxRadius);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_TEXTURE_COLOR);
        float u0 = 8f / 64f;
        float v0 = 8f / 64f;
        float u1 = 16f / 64f;
        float v1 = 16f / 64f;
        float centerX = (fromX + toX) / 2f;
        float centerY = (fromY + toY) / 2f;
        float cu = (u0 + u1) * 0.5f;
        float cv = (v0 + v1) * 0.5f;
        bufferBuilder.vertex(matrix, centerX, centerY, 0.0F).texture(cu, cv).color(1f, 1f, 1f, alpha);
        double samples = 10.0;
        double[][] corners = new double[][]{
                new double[]{toX - r, toY - r, r},
                new double[]{toX - r, fromY + r, r},
                new double[]{fromX + r, fromY + r, r},
                new double[]{fromX + r, toY - r, r}
        };
        for (int i = 0; i < 4; i++) {
            double[] current = corners[i];
            double rad = current[2];
            for (double deg = i * 90d; deg <= (90 + i * 90d); deg += (90 / samples)) {
                float rad1 = (float) Math.toRadians(deg);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                float px = (float) current[0] + sin;
                float py = (float) current[1] + cos;
                float tu = u0 + ((px - fromX) / width) * (u1 - u0);
                float tv = v0 + ((py - fromY) / height) * (v1 - v0);
                bufferBuilder.vertex(matrix, px, py, 0.0F).texture(tu, tv).color(1f, 1f, 1f, alpha);
            }
            float rad1 = (float) Math.toRadians(90 + i * 90d);
            float sin = (float) (Math.sin(rad1) * (float) rad);
            float cos = (float) (Math.cos(rad1) * (float) rad);
            float px = (float) current[0] + sin;
            float py = (float) current[1] + cos;
            float tu = u0 + ((px - fromX) / width) * (u1 - u0);
            float tv = v0 + ((py - fromY) / height) * (v1 - v0);
            bufferBuilder.vertex(matrix, px, py, 0.0F).texture(tu, tv).color(1f, 1f, 1f, alpha);
        }
        float rad1 = (float) Math.toRadians(0);
        float sin = (float) (Math.sin(rad1) * (float) corners[0][2]);
        float cos = (float) (Math.cos(rad1) * (float) corners[0][2]);
        float px = (float) corners[0][0] + sin;
        float py = (float) corners[0][1] + cos;
        float tu = u0 + ((px - fromX) / width) * (u1 - u0);
        float tv = v0 + ((py - fromY) / height) * (v1 - v0);
        bufferBuilder.vertex(matrix, px, py, 0.0F).texture(tu, tv).color(1f, 1f, 1f, alpha);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
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
            case Single -> {
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

    private boolean isEnemy(Entity entity) {
        if (!teamCheck.get()) return true;
        if (!(entity instanceof PlayerEntity player)) return true;
        if (mc.player == null) return false;

        int myColor = getLeatherArmorColor(mc.player);
        int theirColor = getLeatherArmorColor(player);

        if (myColor == -1 || theirColor == -1) return true;

        return myColor != theirColor;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }
}
