package dev.sakura.lemonchat.network.c2s;

import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class PlayPongC2S extends Packet {
    public long ping;

    public PlayPongC2S() {
    }

    public PlayPongC2S(long ping) {
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
