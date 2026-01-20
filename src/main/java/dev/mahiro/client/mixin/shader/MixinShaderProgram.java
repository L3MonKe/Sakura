package dev.mahiro.client.mixin.shader;

import dev.mahiro.satin.impl.SamplerAccess;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramDefinition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShaderProgram.class)
public abstract class MixinShaderProgram implements SamplerAccess {
    @Shadow
    @Final
    private List<ShaderProgramDefinition.Sampler> samplers;

    @Override
    public boolean sakura$hasSampler(String name) {
        for (ShaderProgramDefinition.Sampler sampler : this.samplers) {
            if (sampler.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Accessor("samplers")
    public abstract List<ShaderProgramDefinition.Sampler> getSamplers();

    @Override
    @Accessor("samplerLocations")
    public abstract IntList getSamplerLocations();
}
