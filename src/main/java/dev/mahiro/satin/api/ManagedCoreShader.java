package dev.mahiro.satin.api;

import dev.mahiro.satin.api.uniform.UniformFinder;
import net.minecraft.client.gl.ShaderProgram;

public interface ManagedCoreShader extends UniformFinder {
    ShaderProgram getProgram();

    void release();
}
