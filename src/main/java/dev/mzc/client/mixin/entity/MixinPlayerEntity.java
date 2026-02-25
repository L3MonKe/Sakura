package dev.mzc.client.mixin.entity;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.impl.movement.AutoSprint;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"))
    private void preventSprintReset(PlayerEntity instance, boolean sprinting) {
        if (Sakura.MODULES.getModule(AutoSprint.class).isEnabled() && Sakura.MODULES.getModule(AutoSprint.class).isNoSlowAttack()) {
            return;
        }
        instance.setSprinting(sprinting);
    }

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private void preventAttackSlowdown(Args args) {
        if (Sakura.MODULES.getModule(AutoSprint.class).isEnabled() && Sakura.MODULES.getModule(AutoSprint.class).isNoSlowAttack()) {
            args.set(0, 1.0);
            args.set(2, 1.0);
        }
    }
}
