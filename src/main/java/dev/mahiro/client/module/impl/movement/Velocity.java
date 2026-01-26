package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.input.MoveInputEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.combat.AntiBot;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.utils.vector.Vector3d;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "反击退", Category.Movement);
    }

    private enum Mode {
        Legit,
        NoXZ
    }

    private enum VelocityStage {
        NONE,
        DELAY,
        ATTACK,
        CLEAR,
        LAG
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.NoXZ);
    private final NumberValue<Integer> attacks = new NumberValue<>("Attack Counts", "攻击计数", 4, 1, 5, 1, () -> mode.is(Mode.NoXZ));
    private final NumberValue<Double> alinkTime = new NumberValue<>("Max Alink Time (ms)", "最大Alink时间(ms)", 2500.0, 50.0, 10000.0, 50.0, () -> mode.is(Mode.NoXZ));
    private final BoolValue render = new BoolValue("Render", "渲染", false);
    private final BoolValue debug = new BoolValue("Debug", "调试", false);
    /*public final BoolValue blockPush = new BoolValue("BlockPush", "阻止方块推动", true);
        public final BoolValue entityPush = new BoolValue("EntityPush", "阻止实体推动", true);
        public final BoolValue waterPush = new BoolValue("WaterPush", "阻止水流推动", true);*/

    public boolean lag;
    private boolean jump;
    private Vec3d velocity;
    private long velocityTime;
    private PlayerEntity target;
    private VelocityStage stage;
    private final Map<Entity, Vector3d> targets = new ConcurrentHashMap<>();
    private final Queue<Packet<? super ClientPlayNetworkHandler>> packets = new ConcurrentLinkedQueue<>();

    @Override
    public void onEnable() {
        jump = false;
        lag = false;
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;
    }

    @Override
    public void onDisable() {
        jump = false;
        lag = false;
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;
        clear(true);
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        switch (mode.get()) {
            case NoXZ -> {
                if (stage == VelocityStage.ATTACK) {
                    if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
                        var motionXZ = 1.0D;
                        for (int i = 0; i < attacks.get(); i++) {
                            if (mc.player.isSprinting()) mc.player.setSprinting(false);
                            mc.interactionManager.attackEntity(mc.player, target);
                            mc.player.swingHand(Hand.MAIN_HAND);
                            motionXZ *= 0.6D;
                        }
                        mc.player.setVelocity(velocity.x * motionXZ, velocity.y, velocity.z * motionXZ);
                        debug("执行反击，motionXZ=" + motionXZ + "，velocity=" + formatVec(velocity));
                        stage = VelocityStage.CLEAR;
                    }
                } else if (System.currentTimeMillis() - velocityTime >= alinkTime.get() && stage == VelocityStage.DELAY) {
                    mc.player.setVelocity(velocity.x, velocity.y, velocity.z);
                    debug("延迟超时应用速度，velocity=" + formatVec(velocity));
                    stage = VelocityStage.CLEAR;
                }

                if (lag && mc.player.hurtTime == 0) {
                    lag = false;
                }
            }
        }

        this.setSuffix(mode.get() + (stage == VelocityStage.DELAY ? " Alink " + (System.currentTimeMillis() - velocityTime) / 50 + "Ticks" : ""));
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        if (nullCheck()) return;

        if (stage == VelocityStage.CLEAR) {
            debug("进入 CLEAR");
            clear(true);
        }
        if (stage == VelocityStage.LAG) {
            mc.player.setVelocity(velocity.x, velocity.y, velocity.z);
            debug("LAG 应用速度，velocity=" + formatVec(velocity));
            clear(true);
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;

        switch (mode.get()) {
            case NoXZ -> {
                if (event.getPacket() instanceof PlayerPositionLookS2CPacket && stage == VelocityStage.NONE) {
                    lag = true;
                    debug("收到位置包，设置 lag");
                    return;
                }
                if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                    if (stage == VelocityStage.NONE) {
                        if (!lag) {
                            stage = VelocityStage.DELAY;
                            velocityTime = System.currentTimeMillis();
                            event.setCancelled(true);
                            velocity = new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
                            debug("进入 DELAY，velocity=" + formatVec(velocity));

                        } else {
                            lag = false;
                            debug("清除 lag 标记");
                        }
                        return;
                    } else {
                        velocity = new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
                        stage = VelocityStage.LAG;
                        event.setCancelled(true);
                        debug("进入 LAG，velocity=" + formatVec(velocity));
                        return;
                    }
                }
                if (stage != VelocityStage.NONE && event.getType() == EventType.RECEIVE) {
                    Packet<? super ClientPlayNetworkHandler> packet = (Packet<? super ClientPlayNetworkHandler>) event.getPacket();

                    if (packet instanceof PlayerPositionLookS2CPacket) {
                        stage = VelocityStage.LAG;
                        debug("收到位置包，进入 LAG");
                        return;
                    }

                    if (packet instanceof LookAtS2CPacket) {
                        stage = VelocityStage.LAG;
                        debug("收到 LookAt，进入 LAG");
                        return;
                    }

                    if (packet instanceof DisconnectS2CPacket || packet instanceof PlayerRespawnS2CPacket) {
                        clear(false);
                        return;
                    }

                    if (!(packet instanceof CommonPingS2CPacket) && !(packet instanceof EntityS2CPacket) && !(packet instanceof EntityPositionS2CPacket)) {
                        return;
                    }

                    if (packet instanceof EntityS2CPacket movePacket) {
                        Entity entity = movePacket.getEntity(mc.world);
                        if (entity != null) {
                            Vector3d currentPos = targets.getOrDefault(entity, new Vector3d(entity.getX(), entity.getY(), entity.getZ()));

                            if (movePacket.isPositionChanged()) {
                                double dx = movePacket.getDeltaX() / 4096.0D;
                                double dy = movePacket.getDeltaY() / 4096.0D;
                                double dz = movePacket.getDeltaZ() / 4096.0D;

                                targets.put(entity, new Vector3d(currentPos.getX() + dx, currentPos.getY() + dy, currentPos.getZ() + dz));
                            }
                        }
                    }

                    if (packet instanceof EntityPositionS2CPacket teleportPacket) {
                        Entity entity = mc.world.getEntityById(teleportPacket.entityId());
                        Vec3d newPos = teleportPacket.change().position();
                        targets.put(entity, new Vector3d(newPos.x, newPos.y, newPos.z));
                    }

                    packets.add(packet);
                    event.setCancelled(true);
                }
            }
            case Legit -> {
                if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                    jump = true;
                }
            }
        }
    }


    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.DELAY && velocity != null && mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
                event.setForward(1);
                event.setStrafe(0);
                stage = VelocityStage.ATTACK;
                this.target = player;
                debug("进入 ATTACK，目标=" + player.getName().getString());
            }
        }

        if (jump) {
            if (mc.player.isOnGround() && MoveUtil.isMoving()) event.setJump(true);
            jump = false;
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (!render.get()) return;
        if (stage == VelocityStage.NONE) return;
        for (Entity entity : targets.keySet()) {
            if (!(entity instanceof PlayerEntity)) continue;
            Vector3d pos = targets.get(entity);

            double width = entity.getWidth();
            double height = entity.getHeight();

            Box box = new Box(
                    pos.x - width / 2.0, pos.y, pos.z - width / 2.0,
                    pos.x + width / 2.0, pos.y + height, pos.z + width / 2.0
            );

            Render3DUtil.drawFilledBox(event.getMatrices(), box, entity.equals(target) ? new Color(200, 0, 0, 60) : new Color(0, 200, 0, 60));
        }
    }

    public void clear(boolean handle) {
        lag = false;
        stage = VelocityStage.NONE;
        targets.clear();
        target = null;
        if (!handle) {
            packets.clear();
            return;
        }
        while (!packets.isEmpty()) {
            Packet<? super ClientPlayNetworkHandler> packet = packets.poll();
            if (packet != null && mc.getNetworkHandler() != null) {
                packet.apply(mc.getNetworkHandler());
            }
        }
    }

    private void debug(String message) {
        if (debug.get()) {
            ChatUtil.addChatMessage("§7[Velocity] §f" + message);
        }
    }

    private String formatVec(Vec3d vec) {
        return String.format("%.3f, %.3f, %.3f", vec.x, vec.y, vec.z);
    }
}
