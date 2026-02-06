package dev.sakura.client.mixin.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IEntity {
    @Accessor("lastYaw")
    float getLastYaw();

    @Accessor("lastPitch")
    float getLastPitch();
}
