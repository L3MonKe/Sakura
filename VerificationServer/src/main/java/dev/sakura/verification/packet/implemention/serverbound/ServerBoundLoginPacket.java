package dev.sakura.verification.packet.implemention.serverbound;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.annotations.ProtocolField;

import java.util.Set;

public class ServerBoundLoginPacket implements IRCPacket {
    @ProtocolField("u")
    private String username;

    @ProtocolField("p")
    private String password;

    @ProtocolField("h")
    private String hwid;

    @ProtocolField("q")
    private Set<String> qqSet;

    @ProtocolField("ph")
    private String phone;

    public ServerBoundLoginPacket() {
    }

    public ServerBoundLoginPacket(String username, String password, String hwid, Set<String> qqSet, String phone) {
        this.username = username;
        this.password = password;
        this.hwid = hwid;
        this.qqSet = qqSet;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHwid() {
        return hwid;
    }

    public Set<String> getQqSet() {
        return qqSet;
    }

    public String getPhone() {
        return phone;
    }
}

