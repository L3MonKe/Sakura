package dev.sakura.client.mixin.accessor;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerEntity.class)
public interface IClientPlayerEntity {
    @Invoker("sendMovementPackets")
    void invokeSendMovementPackets();
}
