package dev.sakura.client.utils.player;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

import java.util.HashSet;
import java.util.Set;

import static dev.sakura.client.Sakura.mc;

public class PacketUtil {
    public static Set<Packet<?>> bypassPackets = new HashSet<>();

    public static void sendPacketNoEvent(Packet<?> packet) {
        if (packet == null) return;
        ClientPlayNetworkHandler networkHandler = mc.getNetworkHandler();
        if (networkHandler == null) return;
        bypassPackets.add(packet);
        networkHandler.sendPacket(packet);
    }
}
