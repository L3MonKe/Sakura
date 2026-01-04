package dev.sakura.client.module.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.Render2DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.shaders.satin.api.ManagedCoreShader;
import dev.sakura.client.shaders.satin.api.ShaderEffectManager;
import dev.sakura.client.shaders.satin.api.uniform.SamplerUniform;
import dev.sakura.client.shaders.satin.api.uniform.Uniform1f;
import dev.sakura.client.shaders.satin.api.uniform.Uniform2f;
import dev.sakura.client.shaders.satin.api.uniform.Uniform4f;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import java.awt.*;

public class Glow extends Module {
    public static Glow INSTANCE;

    private final BoolValue players = new BoolValue("Players", "玩家", true);
    private final BoolValue self = new BoolValue("Self", "自己", false);
    private final ColorValue color = new ColorValue("Color", "颜色", new Color(255, 180, 220, 180));
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 12.0, 4.0, 25.0, 1.0);
    private final NumberValue<Double> colorIntensity = new NumberValue<>("ColorIntensity", "颜色强度", 0.4, 0.0, 1.0, 0.05);
    private final BoolValue nativeGlow = new BoolValue("NativeGlow", "原生发光", false);

    private Framebuffer beforeEntityBuffer;
    private Framebuffer afterEntityBuffer;

    private static ManagedCoreShader MASKED_BLUR;
    private Uniform2f inputResolution;
    private Uniform1f brightness;
    private Uniform1f quality;
    private Uniform4f color1Uniform;
    private SamplerUniform inputSampler;
    private SamplerUniform maskSampler;

    private boolean shaderInitialized = false;
    private boolean hasGlowTarget = false;

    public Glow() {
        super("Glow", "发光", Category.Render);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        initShader();
    }

    @Override
    protected void onDisable() {
        cleanup();
    }

    private void initShader() {
        if (shaderInitialized) return;
        try {
            MASKED_BLUR = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of("sakura", "core/blur_masked"), VertexFormats.POSITION);
            inputResolution = MASKED_BLUR.findUniform2f("InputResolution");
            brightness = MASKED_BLUR.findUniform1f("Brightness");
            quality = MASKED_BLUR.findUniform1f("Quality");
            color1Uniform = MASKED_BLUR.findUniform4f("color1");
            inputSampler = MASKED_BLUR.findSampler("InputSampler");
            maskSampler = MASKED_BLUR.findSampler("MaskSampler");
            shaderInitialized = true;
        } catch (Exception e) {
            Sakura.LOGGER.error("Failed to init glow shader", e);
        }
    }

    private void ensureFramebuffers() {
        int w = mc.getWindow().getFramebufferWidth();
        int h = mc.getWindow().getFramebufferHeight();

        if (beforeEntityBuffer == null) {
            beforeEntityBuffer = new SimpleFramebuffer(w, h, false);
        } else if (beforeEntityBuffer.textureWidth != w || beforeEntityBuffer.textureHeight != h) {
            beforeEntityBuffer.resize(w, h);
        }

        if (afterEntityBuffer == null) {
            afterEntityBuffer = new SimpleFramebuffer(w, h, false);
        } else if (afterEntityBuffer.textureWidth != w || afterEntityBuffer.textureHeight != h) {
            afterEntityBuffer.resize(w, h);
        }
    }

    private void cleanup() {
        if (beforeEntityBuffer != null) {
            beforeEntityBuffer.delete();
            beforeEntityBuffer = null;
        }
        if (afterEntityBuffer != null) {
            afterEntityBuffer.delete();
            afterEntityBuffer = null;
        }
    }

    public void captureBeforeEntities() {
        if (!isEnabled()) return;
        hasGlowTarget = false;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (shouldGlow(player)) {
                hasGlowTarget = true;
                break;
            }
        }
        if (!hasGlowTarget) return;

        ensureFramebuffers();
        Framebuffer mainBuffer = mc.getFramebuffer();

        GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, mainBuffer.fbo);
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, beforeEntityBuffer.fbo);
        GlStateManager._glBlitFrameBuffer(0, 0, mainBuffer.textureWidth, mainBuffer.textureHeight,
                0, 0, beforeEntityBuffer.textureWidth, beforeEntityBuffer.textureHeight,
                GL30.GL_COLOR_BUFFER_BIT, GL30.GL_NEAREST);
        mainBuffer.beginWrite(false);
    }

    public void captureAfterEntities() {
        if (!isEnabled() || !hasGlowTarget) return;

        ensureFramebuffers();
        Framebuffer mainBuffer = mc.getFramebuffer();

        GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, mainBuffer.fbo);
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, afterEntityBuffer.fbo);
        GlStateManager._glBlitFrameBuffer(0, 0, mainBuffer.textureWidth, mainBuffer.textureHeight,
                0, 0, afterEntityBuffer.textureWidth, afterEntityBuffer.textureHeight,
                GL30.GL_COLOR_BUFFER_BIT, GL30.GL_NEAREST);
        mainBuffer.beginWrite(false);
    }

    @EventHandler
    public void onRender2D(Render2DEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (!hasGlowTarget || !shaderInitialized) return;
        if (beforeEntityBuffer == null || afterEntityBuffer == null) return;

        MatrixStack matrices = event.getContext().getMatrices();
        Framebuffer mainBuffer = mc.getFramebuffer();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();

        Color c = color.get();
        float intensity = colorIntensity.get().floatValue();

        inputResolution.set((float) mainBuffer.textureWidth, (float) mainBuffer.textureHeight);
        brightness.set(0.9f);
        quality.set(blurStrength.get().floatValue());
        color1Uniform.set(c.getRed() / 255f * intensity, c.getGreen() / 255f * intensity, c.getBlue() / 255f * intensity, intensity);
        inputSampler.set(afterEntityBuffer.getColorAttachment());
        maskSampler.set(beforeEntityBuffer.getColorAttachment());

        RenderSystem.setShader(MASKED_BLUR.getProgram());

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        int w = mc.getWindow().getScaledWidth();
        int h = mc.getWindow().getScaledHeight();

        buffer.vertex(matrix, 0, 0, 0);
        buffer.vertex(matrix, 0, h, 0);
        buffer.vertex(matrix, w, h, 0);
        buffer.vertex(matrix, w, 0, 0);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public boolean shouldGlow(Entity entity) {
        if (!isEnabled()) return false;
        if (entity instanceof PlayerEntity) {
            if (entity == mc.player) {
                return self.get();
            }
            return players.get();
        }
        return false;
    }

    public int getGlowColor(Entity entity) {
        Color c = color.get();
        return (255 << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
    }

    public boolean useNativeGlow() {
        return nativeGlow.get();
    }

    public Color getColor() {
        return color.get();
    }

    public float getColorIntensity() {
        return colorIntensity.get().floatValue();
    }
}