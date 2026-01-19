package dev.lemonclient.lemonchat.network.c2s;

import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class EntityInfoC2S extends Packet {
    public int entityID;
    public String cape;

    public EntityInfoC2S() {
    }

    public EntityInfoC2S(int entityID, String cape) {
        this.entityID = entityID;
        this.cape = cape;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.entityID = buf.readVarInt();
        this.cape = buf.readString();
        return false;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(this.entityID);
        buf.writeString(this.cape);
        return false;
    }
}
