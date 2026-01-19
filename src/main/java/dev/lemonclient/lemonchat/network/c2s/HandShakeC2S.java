package dev.lemonclient.lemonchat.network.c2s;

import dev.lemonclient.lemonchat.network.NetHandler;
import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class HandShakeC2S extends Packet {
    public String hwid;

    public HandShakeC2S() {
    }

    public HandShakeC2S(String hwid) {
        this.hwid = hwid;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.hwid = buf.readString();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(hwid);
        return true;
    }

    @Override
    public void processPacket(NetHandler client) throws IOException {
        client.onHandShakeC2S(this);
    }
}
