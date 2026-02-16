package dev.sakura.verify.packet.implemention.c2s;

import dev.sakura.verify.packet.IRCPacket;
import dev.sakura.verify.packet.annotations.ProtocolField;

public class MessageC2S implements IRCPacket {
    @ProtocolField("m")
    private String message;

    public MessageC2S() {
    }

    public MessageC2S(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
