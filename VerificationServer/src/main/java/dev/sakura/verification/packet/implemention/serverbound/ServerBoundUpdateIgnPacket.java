package dev.sakura.verification.packet.implemention.serverbound;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

public class ServerBoundUpdateIgnPacket implements IRCPacket {
    @ProtocolField("n")
    private String name;

    public ServerBoundUpdateIgnPacket() {
    }

    public ServerBoundUpdateIgnPacket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

