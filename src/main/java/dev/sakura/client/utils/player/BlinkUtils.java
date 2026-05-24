package dev.sakura.client.utils.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

import java.util.concurrent.LinkedBlockingDeque;

import static dev.sakura.client.Sakura.mc;

public class BlinkUtils {
    private static final LinkedBlockingDeque<Packet<ClientPlayPacketListener>> serverPackets = new LinkedBlockingDeque<>();
    private static final LinkedBlockingDeque<Packet<?>> clientPackets = new LinkedBlockingDeque<>();
    public static boolean blinking = false;
    public static boolean delaying = false;

    public static void register() {
        Sakura.EVENT_BUS.subscribe(new BlinkUtils());
    }

    public static void startBlink() {
        if (blinking) return;
        clientPackets.clear();
        blinking = true;
    }

    public static void stopBlink() {
        if (!blinking) return;
        sendBlinkPackets();
        blinking = false;
    }

    public static boolean isClientPacketsEmpty() {
        return clientPackets.isEmpty();
    }

    public static void startDelay() {
        if (delaying) return;
        serverPackets.clear();
        delaying = true;
    }

    public static void stopDelay() {
        if (!delaying) return;
        handleDelayPackets();
        delaying = false;
    }

    public static void sendBlinkPackets() {
        synchronized (clientPackets) {
            while (!clientPackets.isEmpty()) {
                Packet<?> packet = clientPackets.poll();
                PacketUtil.sendPacketNoEvent(packet);
            }
        }
    }

    public static void handleMove(PlayerMoveC2SPacket packet, OtherClientPlayerEntity clonePlayer) {
        double x = packet.getX(clonePlayer.getX());
        double y = packet.getY(clonePlayer.getY());
        double z = packet.getZ(clonePlayer.getZ());

        float yaw = packet.getYaw(clonePlayer.getYaw());
        float pitch = packet.getPitch(clonePlayer.getPitch());

        clonePlayer.updatePositionAndAngles(x, y, z, yaw, pitch);

        if (packet.changesLook()) {
            clonePlayer.setYaw(yaw);
            clonePlayer.setHeadYaw(yaw);
            clonePlayer.setPitch(pitch);
        }
    }

    public static void releaseTick(OtherClientPlayerEntity clonePlayer) {
        synchronized (clientPackets) {
            while (!clientPackets.isEmpty()) {
                Packet<?> poll = clientPackets.poll();
                PacketUtil.sendPacketNoEvent(poll);
                if (poll instanceof PlayerMoveC2SPacket) {
                    handleMove((PlayerMoveC2SPacket) poll, clonePlayer);
                    break;
                }
            }
        }
    }

    public static void handleDelayPackets() {
        synchronized (serverPackets) {
            while (!serverPackets.isEmpty()) {
                Packet<ClientPlayPacketListener> packet = serverPackets.poll();
                packet.apply(mc.getNetworkHandler());
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.isCancelled()) return;
        if (mc.player == null || mc.world == null || mc.getNetworkHandler() == null || mc.player.isDead()) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof HandshakeC2SPacket
                || packet instanceof QueryRequestC2SPacket
                || packet instanceof QueryPingC2SPacket
                || packet instanceof GameMessageS2CPacket
                || packet instanceof DisconnectS2CPacket
                || packet instanceof EntityS2CPacket) {
            return;
        }

        if (event.getType() == EventType.SEND && blinking) {
            event.setCancelled(true);
            synchronized (clientPackets) {
                clientPackets.add(packet);
            }
        }

        if (event.getType() == EventType.RECEIVE && delaying) {
            event.setCancelled(true);
            synchronized (serverPackets) {
                serverPackets.add((Packet<ClientPlayPacketListener>) packet);
            }
        }
    }
}
