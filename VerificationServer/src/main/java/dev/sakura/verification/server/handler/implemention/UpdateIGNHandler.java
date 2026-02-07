package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.serverbound.ServerBoundUpdateIgnPacket;
import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

public class UpdateIGNHandler implements PacketHandler<ServerBoundUpdateIgnPacket> {
    @Override
    public void handle(ServerBoundUpdateIgnPacket packet, Connection connection, UserManager userManager, User user) {
        if (user == null) {
            return;
        }
        String prevIgn = user.getIgn();
        if (!prevIgn.equals(packet.getName())) {
            user.setIgn(packet.getName());
            IRCServer.getInstance().sendInGameUsername();
            Logger.info("User {} updated in-game-username {} -> {}", user.getUsername(), prevIgn, user.getIgn());
        }
    }
}

