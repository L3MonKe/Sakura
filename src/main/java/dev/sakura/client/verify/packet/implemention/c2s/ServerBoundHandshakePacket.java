package dev.sakura.client.verify.packet.implemention.c2s;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public class ServerBoundHandshakePacket implements IRCPacket {
    @ProtocolField("u")
    private String username;

    @ProtocolField("t")
    private String token;

    public ServerBoundHandshakePacket() {
    }

    public ServerBoundHandshakePacket(String username, String token) {
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
