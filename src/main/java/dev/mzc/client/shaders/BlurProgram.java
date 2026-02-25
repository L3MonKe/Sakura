package dev.mzc.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.satin.api.ManagedCoreShader;
import dev.mzc.satin.api.ShaderEffectManager;
import dev.mzc.satin.api.uniform.SamplerUniform;
import dev.mzc.satin.api.uniform.Uniform1f;
import dev.mzc.satin.api.uniform.Uniform2f;
import dev.mzc.satin.api.uniform.Uniform4f;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL30;

import java.awt.*;

import static dev.mzc.client.Sakura.mc;

public class BlurProgram {
    private final Uniform2f uSize;
    private final Uniform2f uLocation;
    private final Uniform1f radius;
    private final Uniform2f inputResolution;
    private final Uniform1f brightness;
    private final Uniform1f quality;
    private final Uniform4f color1;
    private final Uniform1f refractionAmount;
    private final Uniform1f refractionBand;
    private final Uniform1f refractionStrength;
    private final Uniform1f lensCurvature;
    private final SamplerUniform sampler;

    private Framebuffer input;
    private float currentRefractionAmount = 0.004f;
    private float currentRefractionBand = 2.0f;
    private float currentRefractionStrength = 0.75f;
    private float currentLensCurvature = 1.6f;

    public static final ManagedCoreShader BLUR = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of("sakura", "core/blur"), VertexFormats.POSITION);

    public BlurProgram() {
        this.inputResolution = BLUR.findUniform2f("InputResolution");
        this.brightness = BLUR.findUniform1f("Brightness");
        this.quality = BLUR.findUniform1f("Quality");
        this.color1 = BLUR.findUniform4f("color1");
        this.uSize = BLUR.findUniform2f("uSize");
        this.uLocation = BLUR.findUniform2f("uLocation");
        this.radius = BLUR.findUniform1f("radius");
        this.refractionAmount = BLUR.findUniform1f("RefractionAmount");
        this.refractionBand = BLUR.findUniform1f("RefractionBand");
        this.refractionStrength = BLUR.findUniform1f("RefractionStrength");
        this.lensCurvature = BLUR.findUniform1f("LensCurvature");
        sampler = BLUR.findSampler("InputSampler");

        WindowResizeCallback.EVENT.register((client, window) -> {
            if (input != null) {
                input.resize(window.getFramebufferWidth(), window.getFramebufferHeight());
            }
        });
    }

    public void setParameters(float x, float y, float width, float height, float r, Color c1, float blurStrenth, float blurOpacity) {
        if (input == null) {
            input = new SimpleFramebuffer(mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(), false);
        }

        float factor = (float) mc.getWindow().getScaleFactor();
        radius.set(r * factor);
        uLocation.set(x * factor, -y * factor + mc.getWindow().getScaledHeight() * factor - height * factor);
        uSize.set(width * factor, height * factor);
        brightness.set(blurOpacity);
        quality.set(blurStrenth);
        color1.set(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, c1.getAlpha() / 255f);
        refractionAmount.set(currentRefractionAmount);
        refractionBand.set(currentRefractionBand);
        refractionStrength.set(currentRefractionStrength);
        lensCurvature.set(currentLensCurvature);
        sampler.set(input.getColorAttachment());
    }

    public void use() {
        var buffer = mc.getFramebuffer();

        input.beginWrite(false);
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, buffer.fbo);
        GL30.glBlitFramebuffer(0, 0, buffer.textureWidth, buffer.textureHeight, 0, 0, buffer.textureWidth, buffer.textureHeight, GL30.GL_COLOR_BUFFER_BIT, GL30.GL_LINEAR);
        buffer.beginWrite(false);

        if (input != null && (input.textureWidth != mc.getWindow().getFramebufferWidth() || input.textureHeight != mc.getWindow().getFramebufferHeight())) {
            input.resize(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
        }

        inputResolution.set((float) buffer.textureWidth, (float) buffer.textureHeight);
        sampler.set(input.getColorAttachment());

        RenderSystem.setShader(BLUR.getProgram());
    }

    public void setRefractionParams(float amount, float band) {
        this.currentRefractionAmount = amount;
        this.currentRefractionBand = band;
        // Uniforms applied in setParameters per draw call
    }

    public void setRefractionStrength(float strength) {
        this.currentRefractionStrength = strength;
    }

    public void setLensCurvature(float curvature) {
        this.currentLensCurvature = curvature;
    }
}
