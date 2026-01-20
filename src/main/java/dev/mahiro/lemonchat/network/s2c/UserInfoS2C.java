package dev.mahiro.lemonchat.network.s2c;

import dev.mahiro.lemonchat.network.Packet;
import dev.mahiro.lemonchat.network.PacketByteBuf;
import dev.mahiro.lemonchat.utils.Rank;

import java.io.IOException;

public class UserInfoS2C extends Packet {
    public Rank rank;

    public UserInfoS2C() {
    }

    public UserInfoS2C(Rank rank) {
        this.rank = rank;
    }

    @Override
    public boolean read(PacketByteBuf buf) throws IOException {
        this.rank = Rank.getByName(buf.readString());
        return true;
    }

    @Override
    public boolean write(PacketByteBuf buf) throws IOException {
        buf.writeString(rank.name);
        return true;
    }
}
