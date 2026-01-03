package dev.sakura.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.mixin.accessor.IGameRenderer;
import dev.sakura.client.mixin.accessor.IPostEffectProcessor;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.util.Identifier;

import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class BlurShader extends Framebuffer {
    private static BlurShader instance;

    private BlurShader(int width, int height) {
        super(false);
        RenderSystem.assertOnRenderThreadOrInit();
        this.resize(width, height);
        this.setClearColor(0f, 0f, 0f, 0f);
    }

    private static BlurShader obtain() {
        if (instance == null) {
            instance = new BlurShader(mc.getFramebuffer().textureWidth, mc.getFramebuffer().textureHeight);
        }
        return instance;
    }

    public static void use(Runnable runnable) {
        Framebuffer mainBuffer = mc.getFramebuffer();
        RenderSystem.assertOnRenderThreadOrInit();
        BlurShader buffer = obtain();
        if (buffer.textureWidth != mainBuffer.textureWidth || buffer.textureHeight != mainBuffer.textureHeight) {
            buffer.resize(mainBuffer.textureWidth, mainBuffer.textureHeight);
        }
        buffer.beginWrite(false);
        runnable.run();
        buffer.endWrite();
        mainBuffer.beginWrite(false);
    }

    public static void draw() {
        draw(4);
    }

    public static void draw(float radius) {
        Framebuffer mainBuffer = mc.getFramebuffer();
        BlurShader buffer = obtain();

        PostEffectProcessor gaussianShader = mc.getShaderLoader().loadPostEffect(Identifier.of("sakura", "blur"), DefaultFramebufferSet.MAIN_ONLY);
        List<PostEffectPass> allPasses = ((IPostEffectProcessor) gaussianShader).getPasses();
        PostEffectPass firstPass = allPasses.get(0);
        ShaderProgram firstPassProgram = firstPass.getProgram();

        firstPassProgram.getUniform("width").set(radius);
        firstPassProgram.addSamplerTexture("MaskSampler", buffer.colorAttachment);

        gaussianShader.render(mainBuffer, ((IGameRenderer) mc.gameRenderer).getPool());

        buffer.clear();

        mainBuffer.beginWrite(false);
    }
}
