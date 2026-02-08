package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.c2s.ServerBoundRegisterPacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundConnectedPacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundRegisterResultPacket;
import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.auth.AuthService;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

import java.util.Set;
import java.util.stream.Collectors;

public class RegisterHandler implements PacketHandler<ServerBoundRegisterPacket> {
    @Override
    public void handle(ServerBoundRegisterPacket packet, Connection connection, UserManager userManager, User user) {
        AuthService authService = IRCServer.getInstance().getAuthService();
        AuthService.AuthResult result = authService.register(
                packet.getUsername(),
                packet.getPassword(),
                packet.getHwid(),
                packet.getQqSet(),
                packet.getPhone(),
                packet.getCardKey()
        );
        connection.sendPacket(new ClientBoundRegisterResultPacket(result.success(), result.expireAt(), result.timeWindow(), result.message()));

        if (!result.success()) {
            return;
        }

        userManager.putUser(connection.getSessionId(), new User(connection.getSessionId(), packet.getUsername(), "", result.expireAt(), ""));
        connection.sendPacket(new ClientBoundConnectedPacket());

        Set<String> qqSet = packet.getQqSet();
        String qq = qqSet == null || qqSet.isEmpty() ? "" : qqSet.stream().collect(Collectors.joining(","));
        String phone = packet.getPhone() == null ? "" : packet.getPhone();
        String hwid = packet.getHwid() == null ? "" : packet.getHwid();
        Logger.info("Register success: user={} ip={} hwid={} qq={} phone={}", packet.getUsername(), connection.getIPAddress(), hwid, qq, phone);
    }

    @Override
    public boolean allowNull() {
        return true;
    }
}

