package dev.sakura.client.utils.player;

import net.minecraft.network.packet.Packet;

import java.util.HashSet;
import java.util.Set;

import static dev.sakura.client.Sakura.mc;

public class PacketUtil {
    public static Set<Packet<?>> passthroughsPackets = new HashSet<>();

    public static void sendPacketNoEvent(Packet<?> packet) {
        if (packet == null) return;
        passthroughsPackets.add(packet);
        mc.getNetworkHandler().sendPacket(packet);
    }
}
