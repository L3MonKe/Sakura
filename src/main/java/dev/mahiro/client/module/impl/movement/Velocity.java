package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.entity.BlockPushEvent;
import dev.mahiro.client.events.entity.EntityPushEvent;
import dev.mahiro.client.events.entity.EntityVelocityUpdateEvent;
import dev.mahiro.client.events.input.MoveInputEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.mixin.accessor.IEntityVelocityUpdateS2CPacket;
import dev.mahiro.client.mixin.accessor.IExplosionS2CPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.combat.AntiBot;
import dev.mahiro.client.utils.entity.EntityUtil;
import dev.mahiro.client.utils.player.MovementUtil;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "反击退", Category.Movement);
    }

    public enum Mode {
        Custom,
        BBTTGrim,
        Wall,
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
    private final NumberValue<Double> horizontal = new NumberValue<>("Horizontal", "水平", 0.0, 0.0, 100.0, 1.0, () -> mode.is(Mode.Custom));
    private final NumberValue<Double> vertical = new NumberValue<>("Vertical", "垂直", 0.0, 0.0, 100.0, 1.0, () -> mode.is(Mode.Custom));
    private final NumberValue<Integer> attacks = new NumberValue<>("Attack Counts", "攻击计数", 2, 1, 5, 1, () -> mode.is(Mode.NoXZ));
    private final NumberValue<Double> alinkTime = new NumberValue<>("Max Alink Time", "最大啊领克时间", 2500.0, 50.0, 10000.0, 50.0, () -> mode.is(Mode.NoXZ));
    public final BoolValue flagInWall = new BoolValue("Flag In Wall", "墙内标记", false, () -> mode.is(Mode.BBTTGrim) || mode.is(Mode.Wall));
    public final BoolValue noExplosions = new BoolValue("No Explosions", "无爆炸", false);
    public final BoolValue pauseInLiquid = new BoolValue("Pause In Liquid", "液体中暂停", false);
    public final BoolValue waterPush = new BoolValue("No Water Push", "无水推", false);
    public final BoolValue entityPush = new BoolValue("No Entity Push", "无实体推", false);
    public final BoolValue blockPush = new BoolValue("No Block Push", "无方块推", false);
    public final BoolValue fishBob = new BoolValue("No Fish Bob", "无鱼漂", false);

    private final TimerUtil timer = new TimerUtil();
    private boolean flag;
    private final Queue<Packet<? super ClientPlayPacketListener>> packets = new ConcurrentLinkedQueue<>();
    private final Map<Entity, Vec3d> targets = new ConcurrentHashMap<>();
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
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;
    }

    @Override
    protected void onDisable() {
        jump = false;
        lag = false;
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;
        clear(true);
    }

    @Override
    public String getSuffix() {
        if (mode.is(Mode.Custom)) {
            return horizontal.get().intValue() + "%, " + vertical.get().intValue() + "%";
        }
        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.DELAY) {
                return mode.get().name() + " Alink " + ((System.currentTimeMillis() - velocityTime) / 50) + "Ticks";
            }
            return mode.get().name();
        }
        return mode.get().name();
    }

    @EventHandler
    private void onEntityPush(EntityPushEvent event) {
        if (entityPush.get()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPush(BlockPushEvent event) {
        if (blockPush.get()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPacketEvent(PacketEvent event) {
        if ((mode.is(Mode.BBTTGrim) || mode.is(Mode.Wall))
                && event.getType() == EventType.RECEIVE
                && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            timer.reset();
        }
    }

    @EventHandler
    public void onVelocity(EntityVelocityUpdateEvent event) {
        if (nullCheck()) return;

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (mode.is(Mode.BBTTGrim) || mode.is(Mode.Wall)) {
            if (!timer.passedMS(100)) {
                return;
            }
            if (mode.is(Mode.Wall) && !EntityUtil.isInsideBlock()) return;
            event.cancel();
            flag = true;
        }
    }

    @EventHandler
    public void onReceivePacket(PacketEvent event) {
        if (nullCheck() || event.getType() != EventType.RECEIVE) return;

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (fishBob.get()) {
            if (event.getPacket() instanceof EntityStatusS2CPacket packet && packet.getStatus() == 31 && packet.getEntity(mc.world) instanceof FishingBobberEntity fishHook) {
                if (fishHook.getHookedEntity() == mc.player) {
                    event.setCancelled(true);
                }
            }
        }

        if (mode.is(Mode.NoXZ)) {
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

            if (stage == VelocityStage.NONE) {
                return;
            }

            if (p instanceof PlayerPositionLookS2CPacket) {
                stage = VelocityStage.LAG;
                return;
            }

            if (p instanceof LookAtS2CPacket) {
                stage = VelocityStage.LAG;
                return;
            }

            if (p instanceof DisconnectS2CPacket || p instanceof PlayerRespawnS2CPacket) {
                clear(false);
                return;
            }

            if (!(p instanceof CommonPingS2CPacket) && !(p instanceof EntityS2CPacket) && !(p instanceof EntityPositionS2CPacket)) {
                return;
            }

            if (p instanceof EntityS2CPacket entityPacket) {
                updateTargetPosition(entityPacket);
            } else if (p instanceof EntityPositionS2CPacket positionPacket) {
                updateTargetPosition(positionPacket);
            }

            packets.add((Packet<? super ClientPlayPacketListener>) p);
            event.cancel();
            return;
        }

        if (mode.is(Mode.Legit)) {
            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                jump = true;
            }
            return;
        }

        if (mode.is(Mode.BBTTGrim) || mode.is(Mode.Wall)) {
            if (!timer.passedMS(100)) {
                return;
            }

            if (mode.is(Mode.Wall) && !EntityUtil.isInsideBlock()) return;

            if (event.getPacket() instanceof ExplosionS2CPacket) {
                ((IExplosionS2CPacket) event.getPacket()).setPlayerKnockback(Optional.empty());
                flag = true;
            }
        } else {
            double h = horizontal.get() / 100;
            double v = vertical.get() / 100;
            if (event.getPacket() instanceof ExplosionS2CPacket) {
                IExplosionS2CPacket packet = (IExplosionS2CPacket) event.getPacket();

                if (packet.getPlayerKnockback().isPresent()) {
                    double x = packet.getPlayerKnockback().get().getX() * h;
                    double y = packet.getPlayerKnockback().get().getY() * v;
                    double z = packet.getPlayerKnockback().get().getZ() * h;

                    packet.setPlayerKnockback(Optional.of(new Vec3d(x, y, z)));
                }

                if (noExplosions.get()) event.cancel();
                return;
            }

            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
                if (packet.getEntityId() == mc.player.getId()) {
                    if (horizontal.get() == 0 && vertical.get() == 0) {
                        event.cancel();
                    } else {
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityX((int) (packet.getVelocityX() * h));
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityY((int) (packet.getVelocityY() * v));
                        ((IEntityVelocityUpdateS2CPacket) packet).setVelocityZ((int) (packet.getVelocityZ() * h));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.ATTACK) {
                if (mc.crosshairTarget instanceof EntityHitResult ehr && ehr.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
                    double motionXZ = 1.0;
                    for (int i = 0; i < attacks.get(); i++) {
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

        if ((mc.player.isTouchingWater() || mc.player.isSubmergedInWater() || mc.player.isInLava()) && pauseInLiquid.get())
            return;

        if (flag) {
            if (timer.passedMS(100) && (flagInWall.get() || !EntityUtil.isInsideBlock())) {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.isCrawling() ? mc.player.getBlockPos() : mc.player.getBlockPos().up(), Direction.DOWN));
            }
            flag = false;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;
        if (targets.isEmpty()) return;
        if (!mode.is(Mode.NoXZ)) return;
        if (stage == VelocityStage.NONE) return;

        for (Map.Entry<Entity, Vec3d> entry : targets.entrySet()) {
            Entity entity = entry.getKey();
            if (!(entity instanceof PlayerEntity)) continue;

            Vec3d pos = entry.getValue();
            double width = entity.getWidth();
            double height = entity.getHeight();

            Box box = new Box(
                    pos.x - width / 2.0, pos.y, pos.z - width / 2.0,
                    pos.x + width / 2.0, pos.y + height, pos.z + width / 2.0
            );

            int rgb = entity.equals(target) ? new Color(200, 0, 0, 60).getRGB() : new Color(0, 200, 0, 60).getRGB();
            Render3DUtil.drawFullBox(event.getMatrices(), box, rgb, rgb, 2f);
        }
    }

    @EventHandler
    public void onTickBJDPost(TickEvent.Post event) {
        if (nullCheck()) return;
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
        if (nullCheck()) return;

        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.DELAY && velocity != null && mc.crosshairTarget instanceof EntityHitResult ehr && ehr.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
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
        targets.clear();
        target = null;

        if (!handle) {
            packets.clear();
            return;
        }

        while (!packets.isEmpty()) {
            Packet<? super ClientPlayPacketListener> packet = packets.poll();
            if (packet != null) {
                packet.apply(mc.getNetworkHandler());
            }
        }
    }

    private void updateTargetPosition(EntityS2CPacket packet) {
        Entity entity = packet.getEntity(mc.world);
        if (entity == null) return;

        if (packet.isPositionChanged()) {
            Vec3d currentPos = targets.getOrDefault(entity, entity.getPos());
            double dx = packet.getDeltaX() / 4096.0;
            double dy = packet.getDeltaY() / 4096.0;
            double dz = packet.getDeltaZ() / 4096.0;
            targets.put(entity, currentPos.add(dx, dy, dz));
        }
    }

    private void updateTargetPosition(EntityPositionS2CPacket packet) {
        Entity entity = mc.world.getEntityById(packet.entityId());
        if (entity == null) return;
        targets.put(entity, packet.change().position());
    }
}
