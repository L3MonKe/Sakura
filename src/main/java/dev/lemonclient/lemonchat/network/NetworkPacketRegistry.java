package dev.lemonclient.lemonchat.network;

import dev.lemonclient.lemonchat.network.c2s.*;
import dev.lemonclient.lemonchat.network.s2c.*;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class NetworkPacketRegistry {
    public final HashMap<Integer, Class<? extends Packet>> s2c = new HashMap<>();
    public final HashMap<Integer, Class<? extends Packet>> c2s = new HashMap<>();

    public static NetworkPacketRegistry INSTANCE;

    public NetworkPacketRegistry() {
        INSTANCE = this;
    }

    public void load() {
        registerC2S(HandShakeC2S.class);
        registerC2S(LoginC2S.class);
        registerC2S(ChatMessageC2S.class);
        registerC2S(PlayPongC2S.class);
        registerC2S(DisconnectC2S.class);
        registerC2S(ServerMessageC2S.class);
        registerC2S(EntityInfoC2S.class);

        registerS2C(PlayPingS2C.class);
        registerS2C(ChatMessageS2C.class);
        registerS2C(DisconnectS2C.class);
        registerS2C(StatusLoginS2C.class);
        registerS2C(LemonListS2C.class);
        registerS2C(UserInfoS2C.class);
    }

    public Class<? extends Packet> getS2C(int pid) {
        return s2c.getOrDefault(pid, null);
    }

    public Class<? extends Packet> getC2S(int pid) {
        return c2s.getOrDefault(pid, null);
    }

    private void registerS2C(Class<? extends Packet> packetClass) {
        s2c.put(s2c.size() + 1, packetClass);
    }

    private void registerC2S(Class<? extends Packet> packetClass) {
        c2s.put(c2s.size() + 1, packetClass);
    }

    public int getS2CPid(Class<? extends Packet> packetClass) {
        AtomicInteger pid = new AtomicInteger(-1);
        s2c.forEach((id, p) -> {
            if (p == packetClass) {
                pid.set(id);
            }
        });
        return pid.get();
    }

    public int getC2SPid(Class<? extends Packet> packetClass) {
        AtomicInteger pid = new AtomicInteger(-1);
        c2s.forEach((id, p) -> {
            if (p == packetClass) {
                pid.set(id);
            }
        });
        return pid.get();
    }
}
