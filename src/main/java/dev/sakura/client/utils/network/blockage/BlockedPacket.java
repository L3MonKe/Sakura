package dev.sakura.client.utils.network.blockage;

import net.minecraft.network.packet.Packet;

public final class BlockedPacket {
    private final Packet<?> packet;
    private final long id;

    public BlockedPacket(Packet<?> packet, long id) {
        this.packet = packet;
        this.id = id;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public long getId() {
        return id;
    }
}
