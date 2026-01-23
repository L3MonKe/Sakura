package dev.mahiro.client.utils.packet;

import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.network.SequencedPacketCreator;

import static dev.mahiro.client.Mahiro.mc;

public class PacketUtil {
    public static void sendSequencedPacket(SequencedPacketCreator packetCreator) {
        try (PendingUpdateManager pendingUpdateManager = mc.world.getPendingUpdateManager().incrementSequence()) {
            mc.getNetworkHandler().sendPacket(packetCreator.predict(pendingUpdateManager.getSequence()));
        }
    }
}
