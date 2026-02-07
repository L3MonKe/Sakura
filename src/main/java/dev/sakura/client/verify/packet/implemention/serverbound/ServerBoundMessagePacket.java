package dev.sakura.client.verify.packet.implemention.serverbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

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

