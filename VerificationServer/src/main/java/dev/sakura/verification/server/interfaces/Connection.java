package dev.sakura.verification.server.interfaces;

import dev.sakura.verification.packet.IRCPacket;

public interface Connection {
    void sendPacket(IRCPacket packet);

    String getIPAddress();

    String getSessionId();

    void disconnect(String message);

    void disconnect();
}

