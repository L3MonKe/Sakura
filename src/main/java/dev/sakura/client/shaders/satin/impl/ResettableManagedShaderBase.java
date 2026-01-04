package dev.sakura.client.shaders.satin.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.shaders.satin.api.uniform.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public abstract class ResettableManagedShaderBase<S extends AutoCloseable> implements UniformFinder {

    private final Identifier location;
    private final Map<String, ManagedUniform> managedUniforms = new HashMap<>();
    private final List<ManagedUniformBase> allUniforms = new ArrayList<>();
    private boolean errored;
    protected S shader;

    public ResettableManagedShaderBase(Identifier location) {
        this.location = location;
    }

    public void initializeOrLog(ResourceFactory mgr) {
        try {
            this.initialize(mgr);
        } catch (IOException e) {
            this.errored = true;
            this.logInitError(e);
        }
    }

    protected abstract void logInitError(IOException e);

    protected void initialize(ResourceFactory resourceManager) throws IOException {
        this.release();
        MinecraftClient mc = MinecraftClient.getInstance();
        this.shader = parseShader(resourceManager, mc, this.location);
        this.setup(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
    }

    protected abstract S parseShader(ResourceFactory resourceFactory, MinecraftClient mc, Identifier location) throws IOException;

    public void release() {
        if (this.isInitialized()) {
            try {
                assert this.shader != null;
                this.shader = null;
            } catch (Exception e) {
                throw new RuntimeException("Failed to release shader " + this.location, e);
            }
        }
        this.errored = false;
    }

    protected Collection<ManagedUniformBase> getManagedUniforms() {
        return this.allUniforms;
    }

    protected abstract boolean setupUniform(ManagedUniformBase uniform, S shader);

    public boolean isInitialized() {
        return this.shader != null;
    }

    public boolean isErrored() {
        return this.errored;
    }

    public Identifier getLocation() {
        return location;
    }

    protected <U extends ManagedUniformBase> U manageUniform(Map<String, U> uniformMap, Function<String, U> factory, String uniformName, String uniformKind) {
        U existing = uniformMap.get(uniformName);
        if (existing != null) {
            return existing;
        }
        U ret = factory.apply(uniformName);
        if (this.shader != null) {
            boolean found = setupUniform(ret, shader);
            if (!found) {
                Sakura.LOGGER.warn("No {} found with name {} in shader {}", uniformKind, uniformName, this.location);
            }
        }
        uniformMap.put(uniformName, ret);
        allUniforms.add(ret);
        return ret;
    }

    @Override
    public Uniform1i findUniform1i(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 1), uniformName, "uniform");
    }

    @Override
    public Uniform2i findUniform2i(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 2), uniformName, "uniform");
    }

    @Override
    public Uniform3i findUniform3i(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 3), uniformName, "uniform");
    }

    @Override
    public Uniform4i findUniform4i(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 4), uniformName, "uniform");
    }

    @Override
    public Uniform1f findUniform1f(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 1), uniformName, "uniform");
    }

    @Override
    public Uniform2f findUniform2f(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 2), uniformName, "uniform");
    }

    @Override
    public Uniform3f findUniform3f(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 3), uniformName, "uniform");
    }

    @Override
    public Uniform4f findUniform4f(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 4), uniformName, "uniform");
    }

    @Override
    public UniformMat4 findUniformMat4(String uniformName) {
        return manageUniform(this.managedUniforms, name -> new ManagedUniform(name, 16), uniformName, "uniform");
    }

    public abstract void setup(int newWidth, int newHeight);

    @Override
    public String toString() {
        return "%s[%s]".formatted(this.getClass().getSimpleName(), this.location);
    }
}
