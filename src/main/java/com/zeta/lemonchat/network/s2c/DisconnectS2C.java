package com.zeta.lemonchat.network.s2c;

import com.zeta.lemonchat.network.Packet;
import com.zeta.lemonchat.network.PacketByteBuf;

import java.io.IOException;

public class DisconnectS2C extends Packet {
    public String reason;

    public DisconnectS2C() {
    }

    public DisconnectS2C(String reason) {
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
