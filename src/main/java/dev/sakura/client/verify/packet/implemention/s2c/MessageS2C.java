package dev.sakura.client.verify.packet.implemention.s2c;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

public class MessageS2C implements IRCPacket {
    @ProtocolField("s")
    private String sender;

    @ProtocolField("m")
    private String message;

    public MessageS2C() {
    }

    public MessageS2C(String sender, String message) {
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
