package dev.sakura.client.events.packet;

import dev.sakura.client.events.Cancellable;
import dev.sakura.client.events.type.EventType;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends Cancellable {
    private final EventType type;
    private Packet<?> packet;

    public EventType getType() {
        return type;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public PacketEvent(EventType type, Packet<?> packet) {
        this.type = type;
        this.packet = packet;
    }
}
