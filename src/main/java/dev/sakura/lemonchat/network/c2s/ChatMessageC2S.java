package dev.sakura.lemonchat.network.c2s;

import dev.sakura.lemonchat.network.NetHandler;
import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class ChatMessageC2S extends Packet {
    public String message;

    public ChatMessageC2S() {
    }

    public ChatMessageC2S(String message) {
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

    @Override
    public void processPacket(NetHandler client) throws IOException {
        client.onMessageC2S(this);
    }
}
