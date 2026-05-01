package dev.sakura.client.mixin.entity;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.movement.Sprint;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class MixinLocalPlayer {
    @Redirect(method = "shouldStopSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;hasForwardMovement()Z"))
    private boolean onShouldStopSprintingHasForwardMovement(Input instance) {
        Sprint sprint = Sakura.MODULES.getModule(Sprint.class);
        if (sprint != null && sprint.isEnabled() && sprint.isOmnidirectional()) {
            return true;
        }
        return instance.hasForwardMovement();
    }
}