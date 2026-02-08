package dev.sakura.client.verify.packet.implemention.s2c;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public class ClientBoundDisconnectPacket implements IRCPacket {
    @ProtocolField("r")
    private String reason;

    public ClientBoundDisconnectPacket() {
    }

    public ClientBoundDisconnectPacket(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
