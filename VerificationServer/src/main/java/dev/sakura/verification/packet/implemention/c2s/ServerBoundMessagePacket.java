package dev.sakura.verification.packet.implemention.c2s;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

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

