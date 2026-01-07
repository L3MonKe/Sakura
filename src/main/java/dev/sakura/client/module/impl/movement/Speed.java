package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.MoveEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;

public class Speed extends Module {
    public static Speed INSTANCE;

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Strafe);
    private final BoolValue inWater = new BoolValue("InWater", "水中启用", false, () -> mode.get() != Mode.GrimCollide);
    private final BoolValue airStop = new BoolValue("AirStop", "空中停止", false, () -> mode.get() != Mode.GrimCollide);
    private final BoolValue jump = new BoolValue("Jump", "跳跃", true, () -> mode.get() == Mode.Strafe);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 0.2873, 0.1, 1.0, 0.01, () -> mode.get() == Mode.Strafe);
    private final BoolValue velocityBoost = new BoolValue("VelocityBoost", "速度增强", true, () -> mode.get() == Mode.Strafe);
    private final NumberValue<Double> hFactor = new NumberValue<>("H-Factor", "水平因子", 1.0, 0.0, 5.0, 0.1, () -> mode.get() == Mode.Strafe);
    private final NumberValue<Double> vFactor = new NumberValue<>("V-Factor", "垂直因子", 1.0, 0.0, 5.0, 0.1, () -> mode.get() == Mode.Strafe);
    private final NumberValue<Integer> cooldown = new NumberValue<>("Cooldown", "冷却", 1000, 0, 5000, 100, () -> mode.get() == Mode.Strafe);
    private final BoolValue slowness = new BoolValue("Slowness", "缓慢", false, () -> mode.get() == Mode.Strafe);
    private final NumberValue<Double> collideSpeed = new NumberValue<>("CollideSpeed", "碰撞速度", 0.08, 0.01, 0.15, 0.01, () -> mode.get() == Mode.GrimCollide);
    private final NumberValue<Integer> lagTime = new NumberValue<>("LagTime", "滞后时间", 500, 0, 1000, 50);

    private final TimerUtil expTimer = new TimerUtil();
    private final TimerUtil lagTimer = new TimerUtil();

    private double moveSpeed;
    private double distance;
    private double lastExp;
    private int stage;
    private boolean boost;

    public Speed() {
        super("Speed", "速度", Category.Movement);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        Sakura.EVENT_BUS.subscribe(this);
        if (mc.player != null) {
            moveSpeed = MovementUtil.getBaseSpeed(false, speed.get());
            distance = MovementUtil.getDistance2D();
        }
        stage = 4;
        lagTimer.reset();
    }

    @Override
    protected void onDisable() {
        Sakura.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || event.getType() != EventType.POST) return;

        if (mode.get() == Mode.Strafe) {
            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
                if (packet.getEntityId() == mc.player.getId() && velocityBoost.get()) {
                    double vel = Math.sqrt(packet.getVelocityX() * packet.getVelocityX() +
                            packet.getVelocityZ() * packet.getVelocityZ()) / 8000.0;

                    lastExp = expTimer.delay(cooldown.get().floatValue()) ? vel : (vel - lastExp);

                    if (lastExp > 0) {
                        expTimer.reset();
                        moveSpeed += lastExp * hFactor.get();
                        distance += lastExp * hFactor.get();

                        if (MovementUtil.getMotionY() > 0 && vFactor.get() != 0) {
                            MovementUtil.setMotionY(MovementUtil.getMotionY() * vFactor.get());
                        }
                    }
                }
            }
        }

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            lagTimer.reset();
            distance = 0;
            moveSpeed = 0;
            stage = 4;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        setSuffix(mode.get().name());

        double dx = mc.player.getX() - mc.player.prevX;
        double dz = mc.player.getZ() - mc.player.prevZ;
        distance = Math.sqrt(dx * dx + dz * dz);

        if (mode.get() == Mode.GrimCollide) {
            if (!MovementUtil.isMoving()) return;

            int collisions = 0;
            Box box = mc.player.getBoundingBox().expand(1.0);

            for (Entity entity : mc.world.getEntities()) {
                if (canCauseSpeed(entity) && box.intersects(entity.getBoundingBox())) {
                    collisions++;
                }
            }

            if (collisions > 0) {
                double yaw = Math.toRadians(mc.player.getYaw());
                double boostAmount = collideSpeed.get() * collisions;
                mc.player.addVelocity(-Math.sin(yaw) * boostAmount, 0.0, Math.cos(yaw) * boostAmount);
            }
        }
    }

    @EventHandler
    public void onMove(MoveEvent event) {
        if (nullCheck()) return;

        if (!MovementUtil.isMoving() && airStop.get() && mode.get() != Mode.GrimCollide) {
            MovementUtil.setMotionX(0);
            MovementUtil.setMotionZ(0);
            return;
        }

        if (!inWater.get() && (mc.player.isSubmergedInWater() || mc.player.isTouchingWater() || mc.player.isInLava())) {
            return;
        }

        if (mc.player.isRiding() || mc.player.isHoldingOntoLadder() ||
                mc.player.getAbilities().flying || mc.player.isGliding() || !MovementUtil.isMoving()) {
            return;
        }

        if (mode.get() == Mode.GrimCollide) return;

        if (!lagTimer.delay(lagTime.get().floatValue())) return;

        double baseSpeed = MovementUtil.getBaseSpeed(slowness.get(), speed.get());

        if (stage == 1) {
            moveSpeed = 1.35 * baseSpeed - 0.01;
        } else if (stage == 2 && mc.player.isOnGround() && (mc.options.jumpKey.isPressed() || jump.get())) {
            double yMotion = 0.3999 + MovementUtil.getJumpBoost();
            MovementUtil.setMotionY(yMotion);
            event.setY(yMotion);
            moveSpeed *= boost ? 1.6835 : 1.395;
        } else if (stage == 3) {
            moveSpeed = distance - 0.66 * (distance - baseSpeed);
            boost = !boost;
        } else {
            if ((mc.world.canCollide(null, mc.player.getBoundingBox().offset(0.0, MovementUtil.getMotionY(), 0.0))
                    || mc.player.collidedSoftly) && stage > 0) {
                stage = 1;
            }
            moveSpeed = distance - distance / 159.0;
        }

        moveSpeed = Math.min(moveSpeed, 10);
        moveSpeed = Math.max(moveSpeed, baseSpeed);

        double forward = MovementUtil.getMoveForward();
        double strafe = MovementUtil.getMoveStrafe();
        double yaw = mc.player.getYaw();

        if (forward == 0 && strafe == 0) {
            event.setX(0);
            event.setZ(0);
        } else {
            if (forward != 0 && strafe != 0) {
                forward *= Math.sin(Math.PI / 4);
                strafe *= Math.cos(Math.PI / 4);
            }

            double rad = Math.toRadians(yaw);
            event.setX((forward * moveSpeed * -Math.sin(rad) + strafe * moveSpeed * Math.cos(rad)) * 0.99);
            event.setZ((forward * moveSpeed * Math.cos(rad) - strafe * moveSpeed * -Math.sin(rad)) * 0.99);
        }

        stage++;
    }

    private boolean canCauseSpeed(Entity entity) {
        return entity != mc.player && entity instanceof LivingEntity && !(entity instanceof ArmorStandEntity);
    }

    private enum Mode {
        Strafe,
        GrimCollide
    }
}