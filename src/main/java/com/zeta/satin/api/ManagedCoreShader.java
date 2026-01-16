package com.zeta.satin.api;

import com.zeta.satin.api.uniform.UniformFinder;
import net.minecraft.client.gl.ShaderProgram;

public interface ManagedCoreShader extends UniformFinder {
    ShaderProgram getProgram();

    void release();
}
