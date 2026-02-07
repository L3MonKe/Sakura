package dev.sakura.verification.server.interfaces;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;

public interface PacketHandler<T extends IRCPacket> {
    void handle(T packet, Connection connection, UserManager userManager, User user);

    default boolean allowNull() {
        return false;
    }
}

