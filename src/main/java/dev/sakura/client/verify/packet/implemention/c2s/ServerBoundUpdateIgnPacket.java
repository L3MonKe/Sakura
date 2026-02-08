package dev.sakura.client.verify.packet.implemention.c2s;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
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
