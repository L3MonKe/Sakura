package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.LemonClient;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.mixin.accessor.IExplosionS2CPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VelocityRubberband extends Module {
    public VelocityRubberband() {
        super("VelocityRubberband", "橡皮筋反冲", Category.Movement);
    }

    private enum Mode {
        Smooth,
        Rubberband,
        Reverse
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Smooth);
    private final NumberValue<Integer> freezeTicks = new NumberValue<>("FreezeTicks", "冻结时间", 4, 1, 15, 1);
    private final NumberValue<Double> strength = new NumberValue<>("Strength", "强度", 1.0, 0.1, 2.0, 0.1);
    private final NumberValue<Integer> packets = new NumberValue<>("Packets", "数据包数", 2, 1, 5, 1);
    private final BoolValue onlyHurt = new BoolValue("OnlyHurt", "仅受伤时", true);
    private final BoolValue groundOnly = new BoolValue("GroundOnly", "仅地面", false);
    private final BoolValue randomize = new BoolValue("Randomize", "随机化", true);
    private final BoolValue explosion = new BoolValue("Explosion", "爆炸", true);
    private final BoolValue reduceY = new BoolValue("ReduceY", "减少Y轴", true);
    private final BoolValue debug = new BoolValue("Debug", "调试", false);

    private final TimerUtil lagTimer = new TimerUtil();
    private final TimerUtil hurtTimer = new TimerUtil();
    private final List<PlayerMoveC2SPacket> frozenPackets = new ArrayList<>();

    private Vec3d savedPosition = null;
    private Vec3d velocityReceived = null;
    private int tickCounter = 0;
    private boolean freezing = false;
    private boolean wasHurt = false;
    private double lastHealth = 20.0;

    @Override
    protected void onEnable() {
        frozenPackets.clear();
        savedPosition = null;
        velocityReceived = null;
        tickCounter = 0;
        freezing = false;
        wasHurt = false;
        if (mc.player != null) {
            lastHealth = mc.player.getHealth();
        }
    }

    @Override
    protected void onDisable() {
        releasePackets();
        frozenPackets.clear();
        savedPosition = null;
        velocityReceived = null;
        freezing = false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPacketReceive(PacketEvent event) {
        if (nullCheck() || event.getType() != EventType.RECEIVE) return;

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            lagTimer.reset();
            releasePackets();
            frozenPackets.clear();
            savedPosition = null;
            velocityReceived = null;
            freezing = false;
            tickCounter = 0;
        }

        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
            if (packet.getEntityId() != mc.player.getId()) return;
            if (!lagTimer.passedMS(500)) return;
            if (groundOnly.get() && !mc.player.isOnGround()) return;
            if (onlyHurt.get() && !wasHurt) return;

            double vx = packet.getVelocityX() / 8000.0;
            double vy = packet.getVelocityY() / 8000.0;
            double vz = packet.getVelocityZ() / 8000.0;

            if (Math.abs(vx) < 0.005 && Math.abs(vz) < 0.005) return;

            startFreeze(new Vec3d(vx, vy, vz));
        }

        if (explosion.get() && event.getPacket() instanceof ExplosionS2CPacket) {
            if (!lagTimer.passedMS(500)) return;

            IExplosionS2CPacket accessor = (IExplosionS2CPacket) event.getPacket();
            Vec3d kb = accessor.getPlayerKnockback().orElse(null);
            if (kb == null) return;

            if (Math.abs(kb.x) < 0.005 && Math.abs(kb.z) < 0.005) return;

            startFreeze(kb);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPacketSend(PacketEvent event) {
        if (nullCheck() || event.getType() != EventType.SEND) return;
        if (!freezing) return;

        if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
            frozenPackets.add(packet);
            event.cancel();
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        double currentHealth = mc.player.getHealth();
        if (currentHealth < lastHealth) {
            wasHurt = true;
            hurtTimer.reset();
        }
        lastHealth = currentHealth;

        if (hurtTimer.passedMS(1000)) {
            wasHurt = false;
        }

        if (!freezing || savedPosition == null) return;

        tickCounter++;

        if (reduceY.get() && velocityReceived != null) {
            Vec3d vel = mc.player.getVelocity();
            if (vel.y > 0.1) {
                mc.player.setVelocity(vel.x, vel.y * 0.5, vel.z);
            }
        }

        if (tickCounter >= freezeTicks.get()) {
            executeRubberband();
        }
    }

    private void startFreeze(Vec3d velocity) {
        if (freezing) return;

        velocityReceived = velocity;
        savedPosition = mc.player.getPos();
        tickCounter = 0;
        freezing = true;
        frozenPackets.clear();

        if (debug.get()) {
            LemonClient.LOGGER.info("Freeze start, KB: " + velocity);
        }
    }

    private void executeRubberband() {
        if (savedPosition == null || mc.getNetworkHandler() == null) {
            finishRubberband();
            return;
        }

        Vec3d current = mc.player.getPos();
        double dx = savedPosition.x - current.x;
        double dz = savedPosition.z - current.z;
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance < 0.01) {
            finishRubberband();
            return;
        }

        switch (mode.get()) {
            case Smooth -> sendSmoothPackets(current, dx, dz);
            case Rubberband -> sendDirectPackets(current, dx, dz);
            case Reverse -> sendReversePackets(current, dx, dz);
        }

        mc.player.setPosition(savedPosition.x, mc.player.getY(), savedPosition.z);
        mc.player.setVelocity(0, mc.player.getVelocity().y * 0.3, 0);

        if (debug.get()) {
            LemonClient.LOGGER.info("Teleport back, distance: " + distance);
        }

        finishRubberband();
    }

    private void sendSmoothPackets(Vec3d current, double dx, double dz) {
        int count = packets.get();
        double str = strength.get();

        for (int i = 0; i < count; i++) {
            double factor = (double) (i + 1) / count;
            double x = current.x + dx * factor * str;
            double z = current.z + dz * factor * str;

            if (randomize.get()) {
                x += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.02;
                z += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.02;
            }

            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(
                    x, mc.player.getY(), z,
                    mc.player.getYaw(), mc.player.getPitch(),
                    mc.player.isOnGround(),
                    mc.player.horizontalCollision
            ));
        }
    }

    private void sendDirectPackets(Vec3d current, double dx, double dz) {
        double str = strength.get();
        double x = current.x + dx * str;
        double z = current.z + dz * str;

        if (randomize.get()) {
            x += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.01;
            z += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.01;
        }

        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(
                x, mc.player.getY(), z,
                mc.player.getYaw(), mc.player.getPitch(),
                mc.player.isOnGround(),
                mc.player.horizontalCollision
        ));
    }

    private void sendReversePackets(Vec3d current, double dx, double dz) {
        if (velocityReceived == null) {
            sendDirectPackets(current, dx, dz);
            return;
        }

        double str = strength.get();
        double reverseX = -velocityReceived.x * str * 0.3;
        double reverseZ = -velocityReceived.z * str * 0.3;

        double x = current.x + dx * str + reverseX;
        double z = current.z + dz * str + reverseZ;

        if (randomize.get()) {
            x += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.01;
            z += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.01;
        }

        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(
                x, mc.player.getY(), z,
                mc.player.getYaw(), mc.player.getPitch(),
                mc.player.isOnGround(),
                mc.player.horizontalCollision
        ));
    }

    private void releasePackets() {
        if (mc.getNetworkHandler() == null) return;
        for (PlayerMoveC2SPacket packet : frozenPackets) {
            mc.getNetworkHandler().sendPacket(packet);
        }
        frozenPackets.clear();
    }

    private void finishRubberband() {
        frozenPackets.clear();
        freezing = false;
        savedPosition = null;
        velocityReceived = null;
        tickCounter = 0;
    }

    public boolean isFreezing() {
        return freezing;
    }

    @Override
    public String getSuffix() {
        if (freezing) {
            return mode.get().name() + " [Freeze]";
        }
        return mode.get().name();
    }
}