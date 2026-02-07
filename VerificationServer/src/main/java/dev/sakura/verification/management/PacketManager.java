package dev.sakura.verification.management;

import com.google.gson.JsonObject;
import dev.sakura.verification.packet.IRCPacket;
import dev.sakura.verification.packet.implemention.clientbound.*;
import dev.sakura.verification.packet.implemention.serverbound.*;
import dev.sakura.verification.util.UnsafeReflect;

import java.util.HashMap;
import java.util.Map;

public class PacketManager {
    private final Map<Integer, Class<? extends IRCPacket>> idToPacketMap = new HashMap<>();
    private final Map<Class<? extends IRCPacket>, Integer> packetToIdMap = new HashMap<>();
    private int id;

    public PacketManager() {
        register(ClientBoundDisconnectPacket.class, ClientBoundConnectedPacket.class, ClientBoundUpdateUserListPacket.class, ClientBoundMessagePacket.class);

        register(ServerBoundHandshakePacket.class, ServerBoundUpdateIgnPacket.class, ServerBoundMessagePacket.class);

        register(ClientBoundLoginResultPacket.class, ClientBoundRegisterResultPacket.class, ClientBoundRechargeResultPacket.class);
        register(ServerBoundLoginPacket.class, ServerBoundRegisterPacket.class, ServerBoundRechargePacket.class);

        register(ClientBoundCloudConfigPacket.class);
        register(ServerBoundCloudConfigPacket.class);
    }

    @SafeVarargs
    private void register(Class<? extends IRCPacket>... classes) {
        for (Class<? extends IRCPacket> clazz : classes) {
            idToPacketMap.put(id, clazz);
            packetToIdMap.put(clazz, id);
            id++;
        }
    }

    public IRCPacket readPacket(JsonObject object) {
        if (object.has("id") && object.has("cxt")) {
            int id = object.get("id").getAsInt();
            IRCPacket packet = create(id);
            packet.readPacket(object.get("cxt").getAsJsonObject());
            return packet;
        }
        throw new RuntimeException("Unknown packet");
    }

    public JsonObject writePacket(IRCPacket packet) {
        JsonObject jsonObject = new JsonObject();
        JsonObject packetJson = packet.writePacket();
        jsonObject.addProperty("id", packetToIdMap.get(packet.getClass()));
        jsonObject.add("cxt", packetJson);
        return jsonObject;
    }

    public IRCPacket create(int id) {
        if (!idToPacketMap.containsKey(id)) {
            throw new IllegalArgumentException("Unknown packet: " + id);
        }
        Class<? extends IRCPacket> clazz = idToPacketMap.get(id);
        return create(clazz);
    }

    public IRCPacket create(Class<? extends IRCPacket> clazz) {
        try {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException ignored) {
                return (IRCPacket) UnsafeReflect.theUnsafe.allocateInstance(clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

