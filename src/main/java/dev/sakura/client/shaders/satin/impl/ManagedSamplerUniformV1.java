package dev.sakura.client.shaders.satin.impl;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.AbstractTexture;

public final class ManagedSamplerUniformV1 extends ManagedSamplerUniformBase {
    public ManagedSamplerUniformV1(String name) {
        super(name);
    }

    @Override
    public void set(AbstractTexture texture) {
        this.set((Object) texture);
    }

    @Override
    public void set(Framebuffer textureFbo) {
        this.set((Object) textureFbo);
    }

    @Override
    public void set(int textureName) {
        this.set((Object) textureName);
    }

    @Override
    protected void set(Object value) {
        SamplerAccess[] targets = this.targets;
        if (targets.length > 0) {
            int textureId = -1;
            if (value instanceof Framebuffer fb) {
                textureId = fb.getColorAttachment();
            } else if (value instanceof AbstractTexture tex) {
                textureId = tex.getGlId();
            } else if (value instanceof Integer i) {
                textureId = i;
            }
            if (textureId != -1) {
                for (SamplerAccess target : targets) {
                    target.sakura$addSamplerTexture(this.name, textureId);
                }
            }
            this.cachedValue = value;
        }
    }
}
