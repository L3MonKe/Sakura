package dev.mzc.satin.api.uniform;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.AbstractTexture;

public interface SamplerUniform {
    void set(AbstractTexture texture);

    void set(Framebuffer textureFbo);

    void set(int textureName);
}