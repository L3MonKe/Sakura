package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.c2s.ServerBoundHandshakePacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundConnectedPacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundDisconnectPacket;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

public class HandshakeHandler implements PacketHandler<ServerBoundHandshakePacket> {
    private static final String EXPECTED_TOKEN = "SakuraVerifyToken0123456789ABCDE";

    @Override
    public void handle(ServerBoundHandshakePacket packet, Connection connection, UserManager userManager, User user) {
        Logger.info("User {} start handshake", packet.getUsername());

        if (!EXPECTED_TOKEN.equals(packet.getToken())) {
            connection.sendPacket(new ClientBoundDisconnectPacket("验证失败"));
            return;
        }

        if (userManager.getUser(connection.getSessionId()) != null) {
            connection.sendPacket(new ClientBoundDisconnectPacket("你已经连接到了这个服务器！"));
        } else {
            userManager.putUser(connection.getSessionId(), new User(connection.getSessionId(), packet.getUsername(), packet.getToken()));
            connection.sendPacket(new ClientBoundConnectedPacket());
            Logger.info("Accepted user {} ({}/{})", connection.getSessionId(), connection.getIPAddress(), packet.getUsername());
        }
    }

    @Override
    public boolean allowNull() {
        return true;
    }
}
