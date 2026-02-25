package dev.mzc.satin.impl;

import net.minecraft.client.gl.ShaderProgram;

public abstract class ManagedUniformBase {
    protected final String name;

    public ManagedUniformBase(String name) {
        this.name = name;
    }

    public abstract boolean findUniformTarget(ShaderProgram shader);

    public String getName() {
        return name;
    }
}
