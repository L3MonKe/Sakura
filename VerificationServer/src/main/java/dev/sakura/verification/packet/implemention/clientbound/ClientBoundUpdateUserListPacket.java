package dev.sakura.verification.packet.implemention.clientbound;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

import java.util.Map;

public class ClientBoundUpdateUserListPacket implements IRCPacket {
    @ProtocolField("u")
    private Map<String, String> userMap;

    public ClientBoundUpdateUserListPacket() {
    }

    public ClientBoundUpdateUserListPacket(Map<String, String> userMap) {
        this.userMap = userMap;
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }
}

