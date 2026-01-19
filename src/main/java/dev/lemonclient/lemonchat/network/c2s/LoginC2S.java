package dev.lemonclient.lemonchat.network.c2s;

import dev.lemonclient.lemonchat.network.NetHandler;
import dev.lemonclient.lemonchat.network.Packet;
import dev.lemonclient.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class LoginC2S extends Packet {
    public String name, hwid, token, serverIp;

    public LoginC2S() {
    }

    public LoginC2S(String name, String hwid, String token, String serverIp) {
        this.name = name;
        this.hwid = hwid;
        this.token = token;
        this.serverIp = serverIp;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.name = buf.readString();
        this.hwid = buf.readString();
        this.token = buf.readString();
        this.serverIp = buf.readString();
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(name);
        buf.writeString(hwid);
        buf.writeString(token);
        buf.writeString(serverIp);
        return true;
    }

    @Override
    public void processPacket(NetHandler client) throws IOException {
        client.onLoginC2S(this);
    }
}
