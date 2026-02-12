package dev.sakura.client.shaders.program;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;

final class LazyFramebuffer {
    private final String name;
    private Framebuffer framebuffer;

    LazyFramebuffer(String name) {
        this.name = name;
    }

    Framebuffer ensure(int width, int height) {
        if (this.framebuffer == null) {
            this.framebuffer = new SimpleFramebuffer(this.name, width, height, false);
            return this.framebuffer;
        }
        if (this.framebuffer.textureWidth != width || this.framebuffer.textureHeight != height) {
            this.framebuffer.resize(width, height);
        }
        return this.framebuffer;
    }

    Framebuffer get() {
        return this.framebuffer;
    }
}

