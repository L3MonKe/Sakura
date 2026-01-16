package com.zeta.satin.impl;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gl.ShaderProgramDefinition;

import java.util.List;

public interface SamplerAccess {
    boolean sakura$hasSampler(String name);

    List<ShaderProgramDefinition.Sampler> getSamplers();

    IntList getSamplerLocations();
}
