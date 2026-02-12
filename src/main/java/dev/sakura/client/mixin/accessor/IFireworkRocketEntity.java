package dev.sakura.client.mixin.accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FireworkRocketEntity.class)
public interface IFireworkRocketEntity {
    @Accessor("shooter")
    @Nullable LivingEntity getShooter();
}
