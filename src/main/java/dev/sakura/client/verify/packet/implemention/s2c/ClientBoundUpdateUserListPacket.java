package dev.sakura.client.verify.packet.implemention.s2c;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

import java.util.Map;

@Native
@ZKMIndy
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
