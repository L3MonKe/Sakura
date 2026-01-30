package dev.mahiro.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.*;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL30;

import java.awt.*;

import static dev.mahiro.client.Mahiro.mc;

public class KawaseBloomProgram {
    private static final ShaderProgramKey PROGRAM_KEY = new ShaderProgramKey(Identifier.of("mahiro", "core/kawase_bloom"), VertexFormats.POSITION, Defines.EMPTY);

    private ShaderProgram program;
    private GlUniform uSize;
    private GlUniform uLocation;
    private GlUniform radius;
    private GlUniform inputResolution;
    private GlUniform brightness;
    private GlUniform quality;
    private GlUniform color1;

    private Framebuffer input;

    public KawaseBloomProgram() {
        WindowResizeCallback.EVENT.register((client, window) -> {
            if (input != null) {
                input.resize(window.getFramebufferWidth(), window.getFramebufferHeight());
            }
        });
    }

    private boolean ensureProgram() {
        ShaderProgram loaded = mc.getShaderLoader().getOrCreateProgram(PROGRAM_KEY);
        if (loaded == null) {
            return false;
        }
        if (loaded != this.program) {
            this.program = loaded;
            this.inputResolution = loaded.getUniform("InputResolution");
            this.brightness = loaded.getUniform("Brightness");
            this.quality = loaded.getUniform("Quality");
            this.color1 = loaded.getUniform("color1");
            this.uSize = loaded.getUniform("uSize");
            this.uLocation = loaded.getUniform("uLocation");
            this.radius = loaded.getUniform("radius");
        }
        return true;
    }

    public void setParameters(float x, float y, float width, float height, float r, Color c1, float blurStrenth, float blurOpacity) {
        if (input == null) {
            input = new SimpleFramebuffer(mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight(), false);
        }

        if (!ensureProgram()) {
            return;
        }

        float factor = (float) mc.getWindow().getScaleFactor();
        if (radius != null) radius.set(r * factor);
        // Use getFramebufferHeight directly to avoid scaling rounding errors
        if (uLocation != null) uLocation.set(x * factor, -y * factor + mc.getWindow().getFramebufferHeight() - height * factor);
        if (uSize != null) uSize.set(width * factor, height * factor);
        if (brightness != null) brightness.set(blurOpacity);
        if (quality != null) quality.set(blurStrenth);
        if (color1 != null) color1.set(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, 1f);
        program.addSamplerTexture("InputSampler", input.getColorAttachment());
    }

    public void use() {
        if (!ensureProgram()) {
            return;
        }

        var buffer = mc.getFramebuffer();

        input.beginWrite(false);
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, buffer.fbo);
        GL30.glBlitFramebuffer(0, 0, buffer.textureWidth, buffer.textureHeight, 0, 0, buffer.textureWidth, buffer.textureHeight, GL30.GL_COLOR_BUFFER_BIT, GL30.GL_LINEAR);
        buffer.beginWrite(false);

        if (input != null && (input.textureWidth != mc.getWindow().getFramebufferWidth() || input.textureHeight != mc.getWindow().getFramebufferHeight())) {
            input.resize(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
        }

        if (inputResolution != null) {
            inputResolution.set((float) buffer.textureWidth, (float) buffer.textureHeight);
        }
        program.addSamplerTexture("InputSampler", input.getColorAttachment());

        RenderSystem.setShader(program);
    }
}
