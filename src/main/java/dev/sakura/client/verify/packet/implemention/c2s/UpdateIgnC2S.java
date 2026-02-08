package dev.sakura.client.verify.packet.implemention.c2s;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

public class UpdateIgnC2S implements IRCPacket {
    @ProtocolField("n")
    private String name;

    public UpdateIgnC2S() {
    }

    public UpdateIgnC2S(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
