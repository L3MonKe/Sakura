package dev.sakura.client.mixin.accessor;

import net.minecraft.client.particle.BillboardParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BillboardParticle.class)
public interface IBillboardParticle {
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
