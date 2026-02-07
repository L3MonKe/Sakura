package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.clientbound.ClientBoundMessagePacket;
import dev.sakura.verification.packet.implemention.serverbound.ServerBoundMessagePacket;
import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

public class MessageHandler implements PacketHandler<ServerBoundMessagePacket> {
    @Override
    public void handle(ServerBoundMessagePacket packet, Connection connection, UserManager userManager, User user) {
        if (user == null) {
            return;
        }
        String prefix = user.getPrefix();
        String sender = prefix == null || prefix.isEmpty() ? user.getUsername() : prefix + " " + user.getUsername();
        IRCServer.getInstance().boardCastMessage(new ClientBoundMessagePacket(sender, packet.getMessage()));
        Logger.info("Chat Message: {} >> {}", user.getUsername(), packet.getMessage());
    }
}

