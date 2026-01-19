package dev.lemonclient.lemonchat.client.codec;

import dev.lemonclient.lemonchat.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Int0Decoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        byte[] abyte = new byte[3];

        for (int i = 0; i < abyte.length; ++i) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return;
            }

            abyte[i] = in.readByte();
            if (abyte[i] >= 0) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.wrappedBuffer(abyte));

                try {
                    int varInt = buf.readVarInt();
                    if (in.readableBytes() >= varInt) {
                        out.add(in.readBytes(varInt));
                        return;
                    }

                    in.resetReaderIndex();
                } finally {
                    buf.release();
                }

                return;
            }
        }
    }
}
