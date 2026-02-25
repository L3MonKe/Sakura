package dev.mzc.client.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IEntity {
    @Accessor("prevYaw")
    float getPrevYaw();

    @Accessor("prevPitch")
    float getPrevPitch();

    @Accessor("movementMultiplier")
    Vec3d getMovementMultiplier();
}
