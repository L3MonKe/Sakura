package dev.lemonclient.satin.api;

import dev.lemonclient.satin.api.uniform.UniformFinder;
import net.minecraft.client.gl.ShaderProgram;

public interface ManagedCoreShader extends UniformFinder {
    ShaderProgram getProgram();

    void release();
}
