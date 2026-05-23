package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

public class Blink extends Module {

    public enum BlinkMode {
        InstantRelease,
        SlowRelease,
        DelayRelease
    }

    public Blink() {
        super("Blink", "Blink", Category.Player);
    }

    private final EnumValue<BlinkMode> blinkMode = new EnumValue<>("BlinkMode", "Blink模式", BlinkMode.InstantRelease);
    private final NumberValue<Double> releaseInterval = new NumberValue<>("ReleaseInterval(ms)", "释放间隔(ms)", 350.0, 0.0, 1000.0, 10.0, () -> blinkMode.is(BlinkMode.SlowRelease));
    private final NumberValue<Double> delayTime = new NumberValue<>("DelayTime(ms)", "延迟时间(ms)", 1000.0, 0.0, 8000.0, 10.0, () -> blinkMode.is(BlinkMode.DelayRelease));
    private final BoolValue fakePlayer = new BoolValue("FakePlayer", "假人", true);

    private final TimerUtil releaseTimer = new TimerUtil();
    private final TimerUtil delayTimer = new TimerUtil();
    private OtherClientPlayerEntity clonePlayer = null;
    private final LinkedBlockingDeque<Packet<?>> blinkPackets = new LinkedBlockingDeque<>();

    @Override
    public void onEnable() {
        releaseTimer.reset();
        delayTimer.reset();
        if (mc.isInSingleplayer()) {
            setState(false);
            ChatUtil.clientMessage("You can't use blink in singleplayer!");
            return;
        }
        if (mc.world == null || mc.player == null) return;
        if (fakePlayer.get()) {
            OtherClientPlayerEntity clone = new OtherClientPlayerEntity(mc.world, mc.player.getGameProfile());
            clone.headYaw = mc.player.headYaw;
            clone.copyPositionAndRotation(mc.player);
            clone.setUuid(UUID.randomUUID());
            mc.world.addEntity(clone);
            clonePlayer = clone;
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.isCancelled()) return;
        if (nullCheck() || mc.getNetworkHandler() == null || mc.player.isDead()) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof HandshakeC2SPacket
                || packet instanceof QueryRequestC2SPacket
                || packet instanceof QueryPingC2SPacket
                || packet instanceof GameMessageS2CPacket
                || packet instanceof DisconnectS2CPacket) {
            return;
        }

        if (event.getType() == EventType.SEND) {
            event.cancel();
            synchronized (blinkPackets) {
                blinkPackets.add(packet);
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.world == null) {
            blinkPackets.clear();
        }
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() == EventType.POST) {
            if (mc.player == null) return;
            if (mc.player.isDead() || mc.player.age <= 10) {
                sendBlinkPacket();
            }
            if (delayTimer.passedMillise(delayTime.get()) && blinkMode.is(BlinkMode.DelayRelease)) {
                releaseTickPacket();
            }
            if (releaseTimer.passedMillise(releaseInterval.get()) && blinkMode.is(BlinkMode.SlowRelease)) {
                releaseTickPacket();
                releaseTimer.reset();
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        sendBlinkPacket();
    }

    private void sendBlinkPacket() {
        synchronized (blinkPackets) {
            while (!blinkPackets.isEmpty()) {
                Packet<?> packet = blinkPackets.poll();
                PacketUtil.sendPacketNoEvent(packet);
            }
        }

        if (clonePlayer != null) {
            deleteFakePlayer();
            clonePlayer = null;
        }
    }

    private void releaseTickPacket() {
        synchronized (blinkPackets) {
            while (!blinkPackets.isEmpty()) {
                Packet<?> packet = blinkPackets.poll();
                PacketUtil.sendPacketNoEvent(packet);
                if (packet instanceof PlayerMoveC2SPacket packet1) {
                    double x = packet1.getX(clonePlayer.getX());
                    double y = packet1.getY(clonePlayer.getY());
                    double z = packet1.getZ(clonePlayer.getZ());

                    float yaw = packet1.getYaw(clonePlayer.getYaw());
                    float pitch = packet1.getPitch(clonePlayer.getPitch());

                    clonePlayer.updatePositionAndAngles(x, y, z, yaw, pitch);

                    if (packet1.changesLook()) {
                        clonePlayer.setYaw(yaw);
                        clonePlayer.setHeadYaw(yaw);
                        clonePlayer.setPitch(pitch);
                    }
                    break;
                }
            }
        }
    }

    private void deleteFakePlayer() {
        if (clonePlayer == null || mc.world == null) return;
        OtherClientPlayerEntity clone = clonePlayer;

        mc.world.removeEntity(clone.getId(), Entity.RemovalReason.DISCARDED);
        clonePlayer = null;
    }
}
