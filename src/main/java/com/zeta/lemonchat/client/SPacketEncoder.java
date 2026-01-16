package com.zeta.lemonchat.client;

import com.zeta.lemonchat.network.NetworkPacketRegistry;
import com.zeta.lemonchat.network.Packet;
import com.zeta.lemonchat.network.PacketByteBuf;
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
