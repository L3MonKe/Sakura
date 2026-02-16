package dev.sakura.verify.packet.implemention.c2s;

import dev.sakura.verify.packet.IRCPacket;
import dev.sakura.verify.packet.annotations.ProtocolField;

public class HandshakeC2S implements IRCPacket {
    @ProtocolField("u")
    private String username;

    @ProtocolField("t")
    private String token;

    public HandshakeC2S() {
    }

    public HandshakeC2S(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
