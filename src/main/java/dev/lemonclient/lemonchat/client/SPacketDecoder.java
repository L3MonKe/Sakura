package dev.lemonclient.lemonchat.client;

import dev.lemonclient.lemonchat.network.NetworkPacketRegistry;
import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class SPacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readableBytes();
        if (len == 0) return;

        PacketByteBuf packetByteBuf = new PacketByteBuf(in);
        int pid = packetByteBuf.readVarInt();
        Class<? extends Packet> pKlass = NetworkPacketRegistry.INSTANCE.getS2C(pid);
        if (pKlass == null) {
            return;
        }
        Packet packet = pKlass.getConstructor().newInstance();
        packet.buf = packetByteBuf;
        packet.read(packetByteBuf);
        out.add(packet);
    }
}
