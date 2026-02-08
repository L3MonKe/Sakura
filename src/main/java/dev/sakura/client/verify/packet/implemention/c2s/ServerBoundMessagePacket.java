package dev.sakura.client.verify.packet.implemention.c2s;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public class ServerBoundMessagePacket implements IRCPacket {
    @ProtocolField("m")
    private String message;

    public ServerBoundMessagePacket() {
    }

    public ServerBoundMessagePacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
