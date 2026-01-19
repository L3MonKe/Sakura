package dev.lemonclient.lemonchat.network.s2c;

import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class StatusLoginS2C extends Packet {
    public String message;

    public StatusLoginS2C() {
    }

    public StatusLoginS2C(String message) {
        this.message = message;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.message = buf.readString();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(message);
        return true;
    }
}
