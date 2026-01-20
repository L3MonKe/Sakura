package dev.mahiro.lemonchat.network.s2c;

import dev.mahiro.lemonchat.client.ClientSession;
import dev.mahiro.lemonchat.client.LemonList;
import dev.mahiro.lemonchat.network.Packet;
import dev.mahiro.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class LemonListS2C extends Packet {
    public LemonList lemons;

    public LemonListS2C() {
    }

    public LemonListS2C(LemonList lemons) {
        this.lemons = lemons;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.lemons = ClientSession.jsonToList(buf.readString());
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(ClientSession.listToJson(this.lemons));
        return true;
    }
}
