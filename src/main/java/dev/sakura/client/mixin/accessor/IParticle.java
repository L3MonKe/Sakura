package dev.sakura.client.mixin.accessor;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface IParticle {
    @Accessor("red")
    void setRed(float red);

    @Accessor("green")
    void setGreen(float green);

    @Accessor("blue")
    void setBlue(float blue);

    @Accessor("red")
    float getRed();

    @Accessor("green")
    float getGreen();

    @Accessor("blue")
    float getBlue();
}