package dev.lemonclient.lemonchat.network.c2s;

import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class DisconnectC2S extends Packet {
    public String reason;

    public DisconnectC2S() {
    }

    public DisconnectC2S(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.reason = buf.readString();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(reason);
        return true;
    }
}
