package dev.lemonclient.client.events.packet;

import dev.lemonclient.client.events.Cancellable;
import dev.lemonclient.client.events.EventType;
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
