package dev.sakura.client.verify.packet.implemention.clientbound;

import dev.sakura.client.verify.packet.IRCPacket;
import dev.sakura.client.verify.packet.annotations.ProtocolField;

import java.util.List;

public class ClientBoundCloudConfigPacket implements IRCPacket {
    @ProtocolField("a")
    private String action;

    @ProtocolField("ok")
    private boolean success;

    @ProtocolField("o")
    private String owner;

    @ProtocolField("n")
    private String name;

    @ProtocolField("c")
    private String content;

    @ProtocolField("l")
    private List<String> names;

    @ProtocolField("m")
    private int max;

    @ProtocolField("r")
    private String message;

    public ClientBoundCloudConfigPacket() {
    }

    public ClientBoundCloudConfigPacket(String action, boolean success, String owner, String name, String content, List<String> names, int max) {
        this.action = action;
        this.success = success;
        this.owner = owner;
        this.name = name;
        this.content = content;
        this.names = names;
        this.max = max;
    }

    public ClientBoundCloudConfigPacket(String action, boolean success, String owner, String name, String content, List<String> names, int max, String message) {
        this.action = action;
        this.success = success;
        this.owner = owner;
        this.name = name;
        this.content = content;
        this.names = names;
        this.max = max;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public boolean isSuccess() {
        return success;
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

    public List<String> getNames() {
        return names;
    }

    public int getMax() {
        return max;
    }

    public String getMessage() {
        return message;
    }
}

