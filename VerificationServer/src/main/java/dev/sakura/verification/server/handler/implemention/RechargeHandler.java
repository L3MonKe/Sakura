package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.c2s.ServerBoundRechargePacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundRechargeResultPacket;
import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.auth.AuthService;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;

public class RechargeHandler implements PacketHandler<ServerBoundRechargePacket> {
    @Override
    public void handle(ServerBoundRechargePacket packet, Connection connection, UserManager userManager, User user) {
        if (user == null || !user.getUsername().equals(packet.getUsername())) {
            connection.sendPacket(new ClientBoundRechargeResultPacket(false, 0, System.currentTimeMillis() / 30000L, "未登录或账号不匹配"));
            return;
        }

        AuthService authService = IRCServer.getInstance().getAuthService();
        AuthService.AuthResult result = authService.recharge(packet.getUsername(), packet.getCardKey());
        connection.sendPacket(new ClientBoundRechargeResultPacket(result.success(), result.expireAt(), result.timeWindow(), result.message()));

        if (result.success()) {
            user.setExpireAt(result.expireAt());
        }
    }
}

