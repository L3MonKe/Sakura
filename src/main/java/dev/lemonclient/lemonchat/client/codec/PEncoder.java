package dev.lemonclient.lemonchat.client.codec;

import dev.lemonclient.lemonchat.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;

public class PEncoder extends MessageToByteEncoder<ByteBuf> {
    private final byte[] encodeBuf = new byte[8192];
    private final Deflater deflater;
    private int threshold;

    public PEncoder(int threshold) {
        this.threshold = threshold;
        this.deflater = new Deflater();
    }

    protected void encode(ChannelHandlerContext context, ByteBuf byteBuf, ByteBuf out) {
        int readableBytes = byteBuf.readableBytes();
        PacketByteBuf buf = new PacketByteBuf(out);
        if (readableBytes < this.threshold) {
            buf.writeVarInt(0);
            buf.writeBytes(byteBuf);
        } else {
            byte[] abyte = new byte[readableBytes];
            byteBuf.readBytes(abyte);
            buf.writeVarInt(abyte.length);
            this.deflater.setInput(abyte, 0, readableBytes);
            this.deflater.finish();

            while (!this.deflater.finished()) {
                int j = this.deflater.deflate(this.encodeBuf);
                buf.writeBytes(this.encodeBuf, 0, j);
            }

            this.deflater.reset();
        }
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
