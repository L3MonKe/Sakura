package dev.sakura.client.verify.packet.implemention.s2c;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public class ClientBoundMessagePacket implements IRCPacket {
    @ProtocolField("s")
    private String sender;

    @ProtocolField("m")
    private String message;

    public ClientBoundMessagePacket() {
    }

    public ClientBoundMessagePacket(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
