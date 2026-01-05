package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.input.MoveInputEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.network.packet.s2c.query.PingResultS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VelocityBJD extends Module {
    public VelocityBJD() {
        super("Velocity", "反击退", Category.Movement);
    }

    private enum Mode {Legit, NoXZ}

    private enum VelocityStage {NONE, DELAY, ATTACK, CLEAR, LAG}

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Legit);
    private final NumberValue<Integer> attacks = new NumberValue<>("Attack Counts", "攻击计数", 3, 1, 5, 1, () -> mode.is(Mode.NoXZ));
    private final NumberValue<Double> alinkTime = new NumberValue<>("Max Alink Time", "最大啊领克时间", 5000.0, 50.0, 10000.0, 50.0, () -> mode.is(Mode.NoXZ));

    private final Queue<Packet<? super ClientPlayPacketListener>> packets = new ConcurrentLinkedQueue<>();
    private boolean lag;
    private boolean jump;
    private Vec3d velocity;
    private long velocityTime;
    private PlayerEntity target;
    private VelocityStage stage = VelocityStage.NONE;

    @Override
    protected void onEnable() {
        jump = false;
        lag = false;
        target = null;
        stage = VelocityStage.NONE;
        packets.clear();
    }

    @Override
    protected void onDisable() {
        jump = false;
        lag = false;
        target = null;
        stage = VelocityStage.NONE;
        clear(true);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (event.getType() != EventType.RECEIVE) return;

        switch (mode.get()) {
            case NoXZ -> {
                Packet<?> p = event.getPacket();

                if (p instanceof PlayerPositionLookS2CPacket && stage == VelocityStage.NONE) {
                    lag = true;
                    return;
                }

                if (p instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                    if (stage == VelocityStage.NONE) {
                        if (!lag) {
                            stage = VelocityStage.DELAY;
                            velocityTime = System.currentTimeMillis();
                            event.cancel();
                            velocity = new Vec3d(packet.getVelocityX() / 8000.0, packet.getVelocityY() / 8000.0, packet.getVelocityZ() / 8000.0);
                        } else {
                            lag = false;
                        }
                        return;
                    } else {
                        velocity = new Vec3d(packet.getVelocityX() / 8000.0, packet.getVelocityY() / 8000.0, packet.getVelocityZ() / 8000.0);
                        stage = VelocityStage.LAG;
                        event.cancel();
                        return;
                    }
                }

                if (stage == VelocityStage.NONE) return;

                if (p instanceof PlayerPositionLookS2CPacket) {
                    stage = VelocityStage.LAG;
                    return;
                }

                if (p instanceof DisconnectS2CPacket || p instanceof PlayerRespawnS2CPacket) {
                    clear(false);
                    return;
                }

                if (!(p instanceof KeepAliveS2CPacket)
                        && !(p instanceof PingResultS2CPacket)
                        && !(p instanceof EntityS2CPacket)
                        && !(p instanceof EntityPositionS2CPacket)) {
                    return;
                }

                packets.add((Packet<? super ClientPlayPacketListener>) p);
                event.cancel();
            }

            case Legit -> {
                if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                    jump = true;
                }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.ATTACK) {
                if (mc.crosshairTarget instanceof EntityHitResult ehr && ehr.getEntity() instanceof PlayerEntity player) {
                    double motionXZ = 1.0;
                    int times = Math.max(1, attacks.get());
                    for (int i = 0; i < times; i++) {
                        if (mc.player.isSprinting()) mc.player.setSprinting(false);
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        motionXZ *= 0.6;
                    }
                    if (velocity != null) {
                        mc.player.setVelocity(velocity.x * motionXZ, velocity.y, velocity.z * motionXZ);
                    }
                    stage = VelocityStage.CLEAR;
                }
            } else if (stage == VelocityStage.DELAY && System.currentTimeMillis() - velocityTime >= alinkTime.get()) {
                if (velocity != null) {
                    mc.player.setVelocity(velocity);
                }
                stage = VelocityStage.CLEAR;
            }

            if (lag && mc.player.hurtTime == 0) lag = false;
        }

        setSuffix(mode.get().name() + (stage == VelocityStage.DELAY ? " Alink " + ((System.currentTimeMillis() - velocityTime) / 50) + "Ticks" : ""));
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        if (!mode.is(Mode.NoXZ)) return;

        if (stage == VelocityStage.CLEAR) {
            clear(true);
        } else if (stage == VelocityStage.LAG) {
            if (velocity != null) {
                mc.player.setVelocity(velocity);
            }
            clear(true);
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.DELAY && velocity != null && mc.crosshairTarget instanceof EntityHitResult ehr && ehr.getEntity() instanceof PlayerEntity player) {
                event.setForward(1);
                event.setStrafe(0);
                stage = VelocityStage.ATTACK;
                this.target = player;
            }
        }

        if (jump) {
            if (mc.player.isOnGround() && MovementUtil.isMoving()) {
                event.setJump(true);
            }
            jump = false;
        }
    }

    private void clear(boolean handle) {
        lag = false;
        stage = VelocityStage.NONE;
        target = null;

        if (!handle) {
            packets.clear();
            return;
        }

        while (!packets.isEmpty()) {
            Packet<? super ClientPlayPacketListener> packet = packets.poll();
            if (packet != null && mc.getNetworkHandler() != null) {
                try {
                    packet.apply(mc.getNetworkHandler());
                } catch (RuntimeException ignored) {
                }
            }
        }
    }
}
