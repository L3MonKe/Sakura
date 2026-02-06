package dev.sakura.client.mixin.accessor;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerInteractEntityC2SPacket.class)
public interface IPlayerInteractEntityC2SPacket {
    @Accessor("entityId")
    int getEntityId();
}
