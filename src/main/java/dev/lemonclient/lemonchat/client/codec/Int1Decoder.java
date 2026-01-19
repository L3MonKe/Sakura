package dev.lemonclient.lemonchat.client.codec;

import dev.lemonclient.lemonchat.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Int1Decoder extends MessageToByteEncoder<ByteBuf> {
    private static final int MAX_BYTES = 3;

    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int readableBytes = msg.readableBytes();
        int length = PacketByteBuf.getVarIntLength(readableBytes);
        if (length > 3) {
            return;
        } else {
            PacketByteBuf buf = new PacketByteBuf(out);
            buf.ensureWritable(length + readableBytes);
            buf.writeVarInt(readableBytes);
            buf.writeBytes(msg, msg.readerIndex(), readableBytes);
        }
    }
}
