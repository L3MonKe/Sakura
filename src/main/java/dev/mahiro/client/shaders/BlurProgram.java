package dev.mahiro.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mahiro.client.mixin.accessor.IGameRenderer;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.util.Identifier;

import static dev.mahiro.client.Mahiro.mc;
import static org.lwjgl.nanovg.NanoVG.nvgDeleteImage;

public class BlurProgram {
    private static final Identifier BLUR_IDENTIFIER = Identifier.ofVanilla("blur");

    public static final CustomUniform CUSTOM_UNIFORM = new CustomUniform();

    private Framebuffer blurFramebuffer;
    private PostEffectProcessor postEffectProcessor;
    private int blurTextureHandle;
    private int blurTextureWidth;
    private int blurTextureHeight;
    private int nvgImageId = -1;

    public BlurProgram() {
        WindowResizeCallback.EVENT.register((client, window) -> {
            onResized(window.getFramebufferWidth(), window.getFramebufferHeight());
        });
    }

    private void onResized(int width, int height) {
        if (blurFramebuffer != null) {
            blurFramebuffer.delete();
        }
        blurFramebuffer = new WindowFramebuffer(width, height);
        blurTextureWidth = width;
        blurTextureHeight = height;
        blurTextureHandle = 0;
        if (nvgImageId != -1) {
            nvgDeleteImage(NanoVGRenderer.INSTANCE.getContext(), nvgImageId);
            nvgImageId = -1;
        }
    }

    private void ensureFramebuffer() {
        int width = mc.getWindow().getFramebufferWidth();
        int height = mc.getWindow().getFramebufferHeight();

        if (blurFramebuffer == null) {
            onResized(width, height);
            return;
        }

        if (blurFramebuffer.textureWidth != width || blurFramebuffer.textureHeight != height) {
            blurFramebuffer.resize(width, height);
            blurTextureWidth = width;
            blurTextureHeight = height;
            blurTextureHandle = 0;
            if (nvgImageId != -1) {
                nvgDeleteImage(NanoVGRenderer.INSTANCE.getContext(), nvgImageId);
                nvgImageId = -1;
            }
        }
    }

    private boolean ensureProcessor() {
        if (postEffectProcessor != null) {
            return true;
        }

        if (mc.getOverlay() instanceof SplashOverlay) {
            return false;
        }

        postEffectProcessor = mc.getShaderLoader().loadPostEffect(BLUR_IDENTIFIER, DefaultFramebufferSet.MAIN_ONLY);
        return postEffectProcessor != null;
    }

    private void ensureTextureHandle() {
        if (blurFramebuffer == null || blurTextureHandle != 0) {
            return;
        }
        String label = blurFramebuffer.getColorAttachment().getLabel();
        if (label == null || label.isEmpty()) {
            return;
        }
        try {
            blurTextureHandle = Integer.parseInt(label);
        } catch (NumberFormatException ignored) {
            blurTextureHandle = 0;
        }
        if (blurTextureHandle != 0 && nvgImageId == -1) {
            int created = NanoVGHelper.createImageFromHandle(blurTextureHandle, blurTextureWidth, blurTextureHeight);
            nvgImageId = created == 0 ? -1 : created;
        }
    }

    public void applyBlur(int radius) {
        if (radius <= 0) {
            return;
        }

        ensureFramebuffer();
        if (blurFramebuffer == null) {
            return;
        }
        if (!ensureProcessor()) {
            return;
        }

        Framebuffer mainBuffer = mc.getFramebuffer();
        RenderSystem.getDevice().createCommandEncoder().copyTextureToTexture(
            mainBuffer.getColorAttachment(),
            blurFramebuffer.getColorAttachment(),
            0, 0, 0,
            0, 0,
            blurFramebuffer.textureWidth, blurFramebuffer.textureHeight
        );

        FrameGraphBuilder frameGraphBuilder = new FrameGraphBuilder();
        PostEffectProcessor.FramebufferSet framebufferSet = PostEffectProcessor.FramebufferSet.singleton(
            Identifier.ofVanilla("main"),
            frameGraphBuilder.createObjectNode("main", blurFramebuffer)
        );

        CUSTOM_UNIFORM.use(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight(), radius, () -> {
            postEffectProcessor.render(frameGraphBuilder, blurFramebuffer.textureWidth, blurFramebuffer.textureHeight, framebufferSet);
            frameGraphBuilder.run(((IGameRenderer) mc.gameRenderer).getPool());
        });

        ensureTextureHandle();
    }

    public int getBlurTextureHandle() {
        ensureFramebuffer();
        ensureTextureHandle();
        return blurTextureHandle;
    }

    public int getNvgImageId() {
        ensureFramebuffer();
        ensureTextureHandle();
        return nvgImageId;
    }

    public int getBlurTextureWidth() {
        ensureFramebuffer();
        return blurTextureWidth;
    }

    public int getBlurTextureHeight() {
        ensureFramebuffer();
        return blurTextureHeight;
    }
}
