package dev.sakura.client.shaders.satin.impl;

import com.google.common.base.Preconditions;
import dev.sakura.client.Sakura;
import dev.sakura.client.shaders.satin.api.ManagedCoreShader;
import dev.sakura.client.shaders.satin.api.uniform.SamplerUniform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ResettableManagedCoreShader extends ResettableManagedShaderBase<ShaderProgram> implements ManagedCoreShader {
    private final Consumer<ManagedCoreShader> initCallback;
    private final VertexFormat vertexFormat;
    private final Map<String, ManagedSamplerUniformV1> managedSamplers = new HashMap<>();

    public ResettableManagedCoreShader(Identifier location, VertexFormat vertexFormat, Consumer<ManagedCoreShader> initCallback) {
        super(location);
        this.vertexFormat = vertexFormat;
        this.initCallback = initCallback;
    }

    @Override
    protected ShaderProgram parseShader(ResourceFactory resourceManager, MinecraftClient mc, Identifier location) throws IOException {
        ShaderProgram program = mc.getShaderLoader().getOrCreateProgram(new ShaderProgramKey(location, this.vertexFormat, Defines.EMPTY));
        if (program == null) {
            throw new IOException("Failed to load shader program: " + location);
        }
        return program;
    }

    @Override
    public void setup(int newWidth, int newHeight) {
        Preconditions.checkNotNull(this.shader);
        for (ManagedUniformBase uniform : this.getManagedUniforms()) {
            setupUniform(uniform, this.shader);
        }
        this.initCallback.accept(this);
    }

    @Override
    public ShaderProgram getProgram() {
        return this.shader;
    }


    @Override
    protected boolean setupUniform(ManagedUniformBase uniform, ShaderProgram shader) {
        return uniform.findUniformTarget(shader);
    }

    @Override
    public SamplerUniform findSampler(String samplerName) {
        return manageUniform(this.managedSamplers, ManagedSamplerUniformV1::new, samplerName, "sampler");
    }

    @Override
    protected void logInitError(IOException e) {
        Sakura.LOGGER.error("Could not create shader program {}", this.getLocation(), e);
    }
}
