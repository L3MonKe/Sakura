package dev.sakura.client.utils.network.blockage.block;

import net.minecraft.network.packet.Packet;

public interface PacketValidator {
    boolean isValid(Packet<?> packet);
}
