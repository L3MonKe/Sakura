package dev.sakura.verification.packet.implemention.clientbound;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

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

