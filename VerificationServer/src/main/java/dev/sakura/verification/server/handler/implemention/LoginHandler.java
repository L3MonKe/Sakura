package dev.sakura.verification.server.handler.implemention;

import dev.sakura.verification.packet.implemention.c2s.ServerBoundLoginPacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundConnectedPacket;
import dev.sakura.verification.packet.implemention.s2c.ClientBoundLoginResultPacket;
import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.auth.AuthService;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.storage.UserRepository;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

import java.util.Set;
import java.util.stream.Collectors;

public class LoginHandler implements PacketHandler<ServerBoundLoginPacket> {
    @Override
    public void handle(ServerBoundLoginPacket packet, Connection connection, UserManager userManager, User user) {
        AuthService authService = IRCServer.getInstance().getAuthService();
        AuthService.AuthResult result = authService.login(packet.getUsername(), packet.getPassword(), packet.getHwid(), packet.getQqSet(), packet.getPhone());
        connection.sendPacket(new ClientBoundLoginResultPacket(result.success(), result.expireAt(), result.timeWindow(), result.message()));

        if (!result.success()) {
            return;
        }

        String prefix = "";
        Set<String> qqSet = null;
        String phone = "";
        String hwid = packet.getHwid() == null ? "" : packet.getHwid();
        try (java.sql.Connection db = IRCServer.getInstance().getDatabase().openConnection()) {
            UserRepository.UserRow row = IRCServer.getInstance().getUserRepository().findByUsername(db, packet.getUsername());
            if (row != null) {
                prefix = row.prefix() == null ? "" : row.prefix();
                qqSet = row.qqSet();
                phone = row.phone() == null ? "" : row.phone();
                hwid = row.hwid() == null ? hwid : row.hwid();
            }
        } catch (Exception ignored) {
        }

        userManager.putUser(connection.getSessionId(), new User(connection.getSessionId(), packet.getUsername(), "", result.expireAt(), prefix));
        connection.sendPacket(new ClientBoundConnectedPacket());

        String qq = qqSet == null || qqSet.isEmpty() ? "" : qqSet.stream().collect(Collectors.joining(","));
        Logger.info("Login success: user={} ip={} hwid={} qq={} phone={}", packet.getUsername(), connection.getIPAddress(), hwid, qq, phone);
    }

    @Override
    public boolean allowNull() {
        return true;
    }
}
