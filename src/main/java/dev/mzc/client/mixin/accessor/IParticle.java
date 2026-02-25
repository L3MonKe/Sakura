package dev.mzc.client.mixin.accessor;

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

    @Accessor("velocityX")
    void setVelocityX(double velocityX);

    @Accessor("velocityY")
    void setVelocityY(double velocityY);

    @Accessor("velocityZ")
    void setVelocityZ(double velocityZ);
}