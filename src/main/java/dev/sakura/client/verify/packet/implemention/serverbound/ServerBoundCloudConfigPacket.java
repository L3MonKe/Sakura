package dev.sakura.client.verify.packet.implemention.serverbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

public class ServerBoundCloudConfigPacket implements IRCPacket {
    @ProtocolField("a")
    private String action;

    @ProtocolField("o")
    private String owner;

    @ProtocolField("n")
    private String name;

    @ProtocolField("c")
    private String content;

    public ServerBoundCloudConfigPacket() {
    }

    public ServerBoundCloudConfigPacket(String action, String owner, String name, String content) {
        this.action = action;
        this.owner = owner;
        this.name = name;
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}

