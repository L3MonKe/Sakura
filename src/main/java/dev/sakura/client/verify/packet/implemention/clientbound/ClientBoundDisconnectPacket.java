package dev.sakura.client.verify.packet.implemention.clientbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

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

