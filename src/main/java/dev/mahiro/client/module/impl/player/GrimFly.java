package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.misc.WorldLoadEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.util.LinkedList;
import java.util.Queue;

public class GrimFly extends Module {
    private boolean triggerStartFallFlying;
    private boolean active;
    private int ticks;
    private boolean flushing;

    private static final Queue<Packet<?>> queuedPackets = new LinkedList<>();

    public GrimFly() {
        super("GrimFly", "Grim飞行", Category.Player);
    }

    @Override
    protected void onEnable() {
        resetInternal();
    }

    @Override
    protected void onDisable() {
        resetInternal();
        flushQueuedPackets();
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        resetInternal();
        if (isEnabled()) {
            setState(false);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!triggerStartFallFlying) return;

        ticks++;
        if (ticks >= 8) {
            triggerStartFallFlying = false;
            ticks = 0;
            if (mc.getNetworkHandler() != null) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        Packet<?> packet = event.getPacket();

        if (event.getType() == EventType.SEND) {
            if (!flushing && active && packet instanceof CommonPongC2SPacket) {
                event.cancel();
                if (queuedPackets.isEmpty()) {
                    triggerStartFallFlying = true;
                }
                queuedPackets.add(packet);
            }

            if (packet instanceof PlayerInteractEntityC2SPacket) {
                if (active && !queuedPackets.isEmpty()) {
                    flushQueuedPackets();
                }
            }
        }

        if (event.getType() == EventType.RECEIVE) {
            if (packet instanceof PlayerPositionLookS2CPacket) {
                if (active && !queuedPackets.isEmpty()) {
                    flushQueuedPackets();
                }
            }

            if (packet instanceof EntityVelocityUpdateS2CPacket v && v.getEntityId() == mc.player.getId()) {
                if (active || !queuedPackets.isEmpty()) {
                    return;
                }
                ticks = 0;
                active = true;
                event.cancel();
            }
        }
    }

    private void resetInternal() {
        ticks = 0;
        triggerStartFallFlying = false;
        active = false;
        queuedPackets.clear();
        flushing = false;
    }

    private void flushQueuedPackets() {
        if (mc.getNetworkHandler() == null) return;

        flushing = true;
        try {
            while (!queuedPackets.isEmpty()) {
                Packet<?> p = queuedPackets.poll();
                mc.getNetworkHandler().sendPacket(p);
            }
        } finally {
            flushing = false;
        }
    }
}
