package dev.sakura.client.shaders.satin.impl;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gl.ShaderProgramDefinition;

import java.util.List;

public interface SamplerAccess {

    boolean sakura$hasSampler(String name);

    void sakura$addSamplerTexture(String name, int textureId);

    List<ShaderProgramDefinition.Sampler> getSamplers();

    IntList getSamplerLocations();
}
