package dev.sakura.client.utils.player;

import net.minecraft.network.packet.Packet;

import java.util.HashSet;
import java.util.Set;

public class PacketUtil {
    public static Set<Packet<?>> passthroughsPackets = new HashSet<>();

    /*public static void sendPacketNoEvent(Packet<?> packet) {
        Sakura.LOGGER.info("Sending: {}", packet.getClass().getName());
        if (packet instanceof CustomPayloadC2SPacket sb) {
            Sakura.LOGGER.info("RE custompayload, {}", sb.payload().toString());
            if (sb.payload().toString().equals("heypixelmod:s2cevent")) {
                FriendlyByteBuf data = sb.getData();
                data.markReaderIndex();
                int id = data.readVarInt();
                Sakura.LOGGER.info("after packet ({}", id);
                if (id == 2) {
                    Sakura.LOGGER.info("after packet");
                    Sakura.LOGGER.info(Arrays.toString(MixinProtectionUtils.readByteArray(data, data.readableBytes())));
                }

                data.resetReaderIndex();
            }
        }

        passthroughsPackets.add(packet);
        mc.getNetworkHandler().sendPacket(packet);
    }

    public static byte[] readByteArray(FriendlyByteBuf buf, int maxSize) {
        int i = buf.readVarInt() - 1;
        if (i > maxSize) {
            throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxSize);
        } else {
            byte[] abyte = new byte[i];
            buf.readBytes(abyte);
            return abyte;
        }
    }*/
}
