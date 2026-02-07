package dev.sakura.client.verify.packet.implemention.serverbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

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

