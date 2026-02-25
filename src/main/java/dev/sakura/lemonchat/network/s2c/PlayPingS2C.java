package dev.sakura.lemonchat.network.s2c;

import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class PlayPingS2C extends Packet {
    public long ping;

    public PlayPingS2C() {
    }

    public PlayPingS2C(long ping) {
        this.ping = ping;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.ping = buf.readLong();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeLong(this.ping);
        return true;
    }
}
