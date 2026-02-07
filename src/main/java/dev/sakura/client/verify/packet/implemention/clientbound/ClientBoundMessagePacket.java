package dev.sakura.client.verify.packet.implemention.clientbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

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

