package dev.sakura.client.verify.packet.implemention.c2s;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

public class RechargeC2S implements IRCPacket {
    @ProtocolField("u")
    private String username;

    @ProtocolField("c")
    private String cardKey;

    public RechargeC2S() {
    }

    public RechargeC2S(String username, String cardKey) {
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
