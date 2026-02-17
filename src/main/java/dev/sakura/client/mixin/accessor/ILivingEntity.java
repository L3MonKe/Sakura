package dev.sakura.client.mixin.accessor;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface ILivingEntity {
    @Accessor("jumpingCooldown")
    void setJumpingCooldown(int jumpingCooldown);

    @Invoker("getJumpVelocity")
    float callGetJumpVelocity();
}
