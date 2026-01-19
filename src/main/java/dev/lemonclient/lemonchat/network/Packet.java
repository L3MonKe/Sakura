package dev.lemonclient.lemonchat.network;

import java.io.IOException;

public abstract class Packet {
    public PacketByteBuf buf;

    public abstract boolean read(PacketByteBuf buf) throws IOException;

    public abstract boolean write(PacketByteBuf buf) throws IOException;

    public void processPacket(NetHandler client) throws IOException {
    }
}
