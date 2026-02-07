package dev.sakura.verification.packet.implemention.serverbound;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

public class ServerBoundRechargePacket implements IRCPacket {
    @ProtocolField("u")
    private String username;

    @ProtocolField("c")
    private String cardKey;

    public ServerBoundRechargePacket() {
    }

    public ServerBoundRechargePacket(String username, String cardKey) {
        this.username = username;
        this.cardKey = cardKey;
    }

    public String getUsername() {
        return username;
    }

    public String getCardKey() {
        return cardKey;
    }
}

