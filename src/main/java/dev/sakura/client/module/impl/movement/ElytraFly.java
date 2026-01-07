package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.MotionEvent;
import dev.sakura.client.events.player.MoveEvent;
import dev.sakura.client.events.player.TravelEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ElytraFly extends Module {
    public static ElytraFly INSTANCE;

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Control);

    private final BoolValue autoStop = new BoolValue("AutoStop", "自动停止", true);
    private final BoolValue sprint = new BoolValue("Sprint", "冲刺", true, () -> mode.is(Mode.Bounce));
    private final BoolValue autoJump = new BoolValue("AutoJump", "自动跳跃", true, () -> mode.is(Mode.Bounce));
    private final NumberValue<Double> pitch = new NumberValue<>("Pitch", "俯仰角", 88.0, -90.0, 90.0, 0.1, () -> mode.is(Mode.Bounce));

    private final BoolValue instantFly = new BoolValue("AutoStart", "自动起飞", true, () -> !mode.is(Mode.Bounce));
    private final BoolValue firework = new BoolValue("Firework", "烟花加速", false, () -> !mode.is(Mode.Bounce));
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 1000, 0, 20000, 50, () -> !mode.is(Mode.Bounce) && firework.get());
    private final NumberValue<Double> timeout = new NumberValue<>("Timeout", "超时", 0.5, 0.1, 1.0, 0.1, () -> !mode.is(Mode.Bounce));

    private final NumberValue<Double> upPitch = new NumberValue<>("UpPitch", "上升俯仰", 0.0, 0.0, 90.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> upFactor = new NumberValue<>("UpFactor", "上升系数", 1.0, 0.0, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> downFactor = new NumberValue<>("FallSpeed", "下降速度", 1.0, 0.0, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final BoolValue speedLimit = new BoolValue("SpeedLimit", "速度限制", true, () -> mode.is(Mode.Control));
    private final NumberValue<Double> maxSpeed = new NumberValue<>("MaxSpeed", "最大速度", 2.5, 0.1, 10.0, 0.1, () -> speedLimit.get() && mode.is(Mode.Control));
    private final BoolValue noDrag = new BoolValue("NoDrag", "无阻力", false, () -> mode.is(Mode.Control));
    private final NumberValue<Double> sneakDownSpeed = new NumberValue<>("DownSpeed", "潜行下降", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Control));

    private final NumberValue<Double> boost = new NumberValue<>("Boost", "加速", 1.0, 0.1, 4.0, 0.1, () -> mode.is(Mode.Boost));

    private final TimerUtil instantFlyTimer = new TimerUtil();
    private final TimerUtil strictTimer = new TimerUtil();
    private final TimerUtil fireworkTimer = new TimerUtil();

    private boolean hasElytra = false;
    private boolean rubberbanded = false;
    private boolean prev = false;
    private float prePitch = 0;

    public ElytraFly() {
        super("ElytraFly", "鞘翅飞行", Category.Movement);
        INSTANCE = this;
    }

    public boolean isBounceMode() {
        return mode.is(Mode.Bounce);
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            if (!mc.player.isCreative()) mc.player.getAbilities().allowFlying = false;
            mc.player.getAbilities().flying = false;
        }
        hasElytra = false;
        rubberbanded = false;
    }

    @Override
    protected void onDisable() {
        rubberbanded = false;
        hasElytra = false;
        if (mc.player != null) {
            if (!mc.player.isCreative()) mc.player.getAbilities().allowFlying = false;
            mc.player.getAbilities().flying = false;
        }
    }

    private boolean isElytraUsable(ItemStack stack) {
        if (stack.isEmpty() || !stack.isOf(Items.ELYTRA)) return false;
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    private void boost() {
        if (hasElytra && mc.player.isGliding()) {
            float yaw = (float) Math.toRadians(mc.player.getYaw());
            if (mc.options.forwardKey.isPressed()) {
                mc.player.addVelocity(
                        -MathHelper.sin(yaw) * boost.get().floatValue() / 10,
                        0,
                        MathHelper.cos(yaw) * boost.get().floatValue() / 10
                );
            }
        }
    }

    @EventHandler
    public void onTickPre(TickEvent.Pre event) {
        if (nullCheck()) return;

        ItemStack chestStack = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        hasElytra = isElytraUsable(chestStack);

        setSuffix(mode.get().name());

        if (mode.is(Mode.Bounce)) {
            return;
        }

        if (firework.get() && fireworkTimer.passedMS(delay.get()) && MovementUtil.isMoving() && !mc.player.isUsingItem() && mc.player.isGliding()) {
            useFirework();
            fireworkTimer.reset();
        }

        if (!mc.player.isGliding()) {
            fireworkTimer.setTime(99999999);
            if (!mc.player.isOnGround() && instantFly.get() && mc.player.getVelocity().getY() < 0D) {
                if (!instantFlyTimer.passedMS((long) (1000 * timeout.get()))) return;
                instantFlyTimer.reset();
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                strictTimer.reset();
            }
        }

        if (mode.is(Mode.Boost)) {
            boost();
        }
    }

    @EventHandler
    public void onTickPost(TickEvent.Post event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Bounce) && hasElytra) {
            if (autoJump.get()) mc.options.jumpKey.setPressed(true);

            if (!mc.player.isGliding()) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }

            if (checkConditions(mc.player)) {
                if (!sprint.get()) {
                    if (mc.player.isGliding()) mc.player.setSprinting(mc.player.isOnGround());
                    else mc.player.setSprinting(true);
                } else {
                    mc.player.setSprinting(true);
                }
            }
        }
    }

    private void useFirework() {
        FindItemResult fireworkResult = InvUtil.findInHotbar(Items.FIREWORK_ROCKET);
        if (fireworkResult.found()) {
            boolean switched = InvUtil.swap(fireworkResult.slot(), true);
            if (switched) {
                mc.interactionManager.interactItem(mc.player, fireworkResult.getHand());
                mc.player.swingHand(Hand.MAIN_HAND);
                InvUtil.swapBack();
            }
        }
    }

    @EventHandler
    public void onMove(MoveEvent event) {
        if (nullCheck()) return;
        if (!hasElytra) return;

        if (mc.player.isGliding() && autoStop.get()) {
            int chunkX = (int) (mc.player.getX() / 16);
            int chunkZ = (int) (mc.player.getZ() / 16);
            if (!mc.world.getChunkManager().isChunkLoaded(chunkX, chunkZ)) {
                event.setX(0);
                event.setY(0);
                event.setZ(0);
            }
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (mc.player == null) return;
        if (event.getType() != EventType.SEND) return;

        if (mode.is(Mode.Bounce) && hasElytra && event.getPacket() instanceof ClientCommandC2SPacket packet) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.START_FALL_FLYING && !sprint.get()) {
                mc.player.setSprinting(true);
            }
        }
    }

    @EventHandler
    public void onPacketReceive(PacketEvent event) {
        if (mc.player == null) return;
        if (event.getType() != EventType.RECEIVE) return;

        if (mode.is(Mode.Bounce) && hasElytra && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            rubberbanded = true;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMotion(MotionEvent event) {
        if (event.getType() != EventType.PRE) return;
        if (mode.is(Mode.Bounce) && hasElytra) {
            event.setPitch(pitch.get().floatValue());
        }
    }

    @EventHandler
    public void onTravel(TravelEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Bounce) && hasElytra) {
            if (event.isPre()) {
                prev = true;
                prePitch = mc.player.getPitch();
                mc.player.setPitch(pitch.get().floatValue());
            } else {
                if (prev) {
                    prev = false;
                    mc.player.setPitch(prePitch);
                }
            }
            return;
        }

        if (!hasElytra || !mc.player.isGliding() || event.isPost()) return;

        if (mode.is(Mode.Freeze)) {
            if (!MovementUtil.isMoving()) {
                event.setCancelled(true);
                return;
            }
        }

        if (!mode.is(Mode.Control)) return;

        boolean jumping = mc.options.jumpKey.isPressed();
        boolean sneaking = mc.options.sneakKey.isPressed();

        if (firework.get()) {
            if (!(sneaking && jumping)) {
                if (sneaking) {
                    MovementUtil.setMotionY(-sneakDownSpeed.get());
                } else if (jumping) {
                    MovementUtil.setMotionY(upFactor.get());
                } else {
                    MovementUtil.setMotionY(-sneakDownSpeed.get());
                }
            } else {
                MovementUtil.setMotionY(0);
            }
            double[] dir = MovementUtil.directionSpeedKey(speed.get());
            MovementUtil.setMotionX(dir[0]);
            MovementUtil.setMotionZ(dir[1]);
        } else {
            Vec3d lookVec = getRotationVec(mc.getRenderTickCounter().getTickDelta(true));
            double lookDist = Math.sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z);
            double motionDist = Math.sqrt(MovementUtil.getMotionX() * MovementUtil.getMotionX() + MovementUtil.getMotionZ() * MovementUtil.getMotionZ());

            if (sneaking) {
                MovementUtil.setMotionY(-sneakDownSpeed.get());
            } else if (!jumping) {
                MovementUtil.setMotionY(-0.00000000003D * downFactor.get());
            }

            if (jumping) {
                if (motionDist > upFactor.get() / upFactor.getMax()) {
                    double rawUpSpeed = motionDist * 0.01325D;
                    MovementUtil.setMotionY(MovementUtil.getMotionY() + rawUpSpeed * 3.2D);
                    MovementUtil.setMotionX(MovementUtil.getMotionX() - lookVec.x * rawUpSpeed / lookDist);
                    MovementUtil.setMotionZ(MovementUtil.getMotionZ() - lookVec.z * rawUpSpeed / lookDist);
                } else {
                    double[] dir = MovementUtil.directionSpeedKey(speed.get());
                    MovementUtil.setMotionX(dir[0]);
                    MovementUtil.setMotionZ(dir[1]);
                }
            }

            if (lookDist > 0.0D) {
                MovementUtil.setMotionX(MovementUtil.getMotionX() + (lookVec.x / lookDist * motionDist - MovementUtil.getMotionX()) * 0.1D);
                MovementUtil.setMotionZ(MovementUtil.getMotionZ() + (lookVec.z / lookDist * motionDist - MovementUtil.getMotionZ()) * 0.1D);
            }

            if (!jumping) {
                double[] dir = MovementUtil.directionSpeedKey(speed.get());
                MovementUtil.setMotionX(dir[0]);
                MovementUtil.setMotionZ(dir[1]);
            }

            if (!noDrag.get()) {
                MovementUtil.setMotionY(MovementUtil.getMotionY() * 0.9900000095367432D);
                MovementUtil.setMotionX(MovementUtil.getMotionX() * 0.9800000190734863D);
                MovementUtil.setMotionZ(MovementUtil.getMotionZ() * 0.9900000095367432D);
            }

            double finalDist = Math.sqrt(MovementUtil.getMotionX() * MovementUtil.getMotionX() + MovementUtil.getMotionZ() * MovementUtil.getMotionZ());
            if (speedLimit.get() && finalDist > maxSpeed.get()) {
                MovementUtil.setMotionX(MovementUtil.getMotionX() * maxSpeed.get() / finalDist);
                MovementUtil.setMotionZ(MovementUtil.getMotionZ() * maxSpeed.get() / finalDist);
            }

            event.setCancelled(true);
            mc.player.move(MovementType.SELF, mc.player.getVelocity());
        }
    }

    private Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    private Vec3d getRotationVec(float tickDelta) {
        return getRotationVector(-upPitch.get().floatValue(), mc.player.getYaw(tickDelta));
    }

    public static boolean recastElytra(ClientPlayerEntity player) {
        if (player == null) return false;
        if (checkConditions(player) && ignoreGround(player)) {
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            return true;
        }
        return false;
    }

    public static boolean checkConditions(ClientPlayerEntity player) {
        if (player == null) return false;
        ItemStack itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (!itemStack.isOf(Items.ELYTRA)) return false;
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) return false;
        return !player.getAbilities().flying && !player.hasVehicle() && !player.isClimbing();
    }

    private static boolean ignoreGround(ClientPlayerEntity player) {
        if (player == null) return false;
        if (!player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
            ItemStack itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack.isOf(Items.ELYTRA) && itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
                return true;
            }
        }
        return false;
    }

    public enum Mode {
        Control,
        Boost,
        Bounce,
        Freeze,
        None
    }
}