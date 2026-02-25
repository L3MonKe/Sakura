package dev.mzc.satin.api;

import dev.mzc.satin.api.uniform.UniformFinder;
import net.minecraft.client.gl.ShaderProgram;

public interface ManagedCoreShader extends UniformFinder {
    ShaderProgram getProgram();

    void release();
}
