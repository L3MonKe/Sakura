package dev.sakura.lemonchat.client;

import dev.sakura.lemonchat.network.NetworkPacketRegistry;
import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SPacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        if (packet == null) {
            return;
        }

        int pid = NetworkPacketRegistry.INSTANCE.getC2SPid(packet.getClass());
        if (pid == -1) {
            return;
        }
        PacketByteBuf packetByteBuf = new PacketByteBuf(out);
        packet.buf = packetByteBuf;
        packetByteBuf.writeVarInt(pid);
        packet.write(packetByteBuf);
    }
}
