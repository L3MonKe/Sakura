package dev.sakura.client.mixin.entity;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.movement.KeepSprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "knockbackTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"), cancellable = true)
    private void onKnockbackTargetSetSprinting(Entity target, float strength, Vec3d playerTargetVelocity, CallbackInfo ci) {
        // 检查是否是客户端玩家且 KeepSprint 模块已启用
        if ((Object) this == MinecraftClient.getInstance().player) {
            KeepSprint keepSprint = Sakura.MODULES.getModule(KeepSprint.class);
            if (keepSprint != null && keepSprint.isEnabled()) {
                // 直接取消 setSprinting(false) 的调用
                // 这样我们就不需要恢复了，疾跑状态保持不变
                // 但是我们仍然需要处理减速效果
                keepSprint.onAttackKnockback();
                ci.cancel();
            }
        }
    }
}
