package dev.sakura.verification.server.handler;

import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.implemention.c2s.*;
import dev.sakura.verification.server.handler.implemention.*;
import dev.sakura.verification.server.interfaces.Connection;
import dev.sakura.verification.server.interfaces.PacketHandler;
import dev.sakura.verification.server.user.User;
import dev.sakura.verification.server.user.UserManager;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class HandlerManager {
    private final Map<Class<? extends IRCPacket>, PacketHandler<?>> classToHandlerMap = new HashMap<>();

    public HandlerManager() {
        classToHandlerMap.put(ServerBoundHandshakePacket.class, new HandshakeHandler());
        classToHandlerMap.put(ServerBoundUpdateIgnPacket.class, new UpdateIGNHandler());
        classToHandlerMap.put(ServerBoundMessagePacket.class, new MessageHandler());
        classToHandlerMap.put(ServerBoundLoginPacket.class, new LoginHandler());
        classToHandlerMap.put(ServerBoundRegisterPacket.class, new RegisterHandler());
        classToHandlerMap.put(ServerBoundRechargePacket.class, new RechargeHandler());
        classToHandlerMap.put(ServerBoundCloudConfigPacket.class, new CloudConfigHandler());
    }

    public boolean allowNull(IRCPacket packet) {
        if (!classToHandlerMap.containsKey(packet.getClass())) {
            Logger.warn("No {} handler found.", packet.getClass().getSimpleName());
            return false;
        }
        return classToHandlerMap.get(packet.getClass()).allowNull();
    }

    @SuppressWarnings("unchecked")
    public void handlePacket(IRCPacket packet, Connection connection, UserManager userManager, User user) {
        PacketHandler<IRCPacket> handler = (PacketHandler<IRCPacket>) classToHandlerMap.get(packet.getClass());
        if (handler == null) {
            Logger.warn("No {} handler found.", packet.getClass().getSimpleName());
            return;
        }
        handler.handle(packet, connection, userManager, user);
    }
}

