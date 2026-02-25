package dev.sakura.lemonchat.network.c2s;

import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class ServerMessageC2S extends Packet {
    public String server;

    public ServerMessageC2S() {
    }

    public ServerMessageC2S(String serverIP) {
        this.server = serverIP;

    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.server = buf.readString();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(this.server);
        return true;
    }
}
