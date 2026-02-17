package dev.sakura.client.mixin.network;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.entity.AttackBlockEvent;
import dev.sakura.client.event.impl.entity.AttackEntityEvent;
import dev.sakura.client.event.impl.player.BlockBreakingCooldownEvent;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Shadow
    private int blockBreakingCooldown;

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        AttackEntityEvent event = new AttackEntityEvent(player, target);
        Sakura.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        AttackBlockEvent event = new AttackBlockEvent(pos, direction);
        Sakura.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD, ordinal = 1))
    private void creativeBreakDelayChange(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = Sakura.EVENT_BUS.post(new BlockBreakingCooldownEvent(value));
        blockBreakingCooldown = event.getCooldown();
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD, ordinal = 2))
    private void survivalBreakDelayChange(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = Sakura.EVENT_BUS.post(new BlockBreakingCooldownEvent(value));
        blockBreakingCooldown = event.getCooldown();
    }

    @Redirect(method = "attackBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD))
    private void creativeBreakDelayChange2(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = Sakura.EVENT_BUS.post(new BlockBreakingCooldownEvent(value));
        blockBreakingCooldown = event.getCooldown();
    }
}