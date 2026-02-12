package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.player.MoveEvent;
import dev.sakura.client.event.impl.player.TravelEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IEntityFlag;
import dev.sakura.client.mixin.accessor.IFireworkRocketEntity;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.player.SlotUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ElytraFly extends Module {
    public static ElytraFly INSTANCE;

    private enum Mode {
        Control,
        Boost,
        Bounce,
        Freeze,
        None,
        Rotation,
        Pitch
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Control);
    private final BoolValue infiniteDura = new BoolValue("InfiniteDura", "无限耐久", false);
    private final BoolValue autoJump = new BoolValue("AutoJump", "自动跳跃", true, () -> mode.is(Mode.Bounce));

    private final NumberValue<Double> upPitch = new NumberValue<>("UpPitch", "上仰角", 0.0, 0.0, 90.0, 1.0, () -> mode.is(Mode.Control));
    private final NumberValue<Double> upFactor = new NumberValue<>("UpFactor", "上升倍率", 1.0, 0.0, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> downFactor = new NumberValue<>("FallSpeed", "下落倍率", 1.0, 0.0, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final BoolValue speedLimit = new BoolValue("SpeedLimit", "限速", true, () -> mode.is(Mode.Control));
    private final NumberValue<Double> maxSpeed = new NumberValue<>("MaxSpeed", "最大速度", 2.5, 0.1, 10.0, 0.1, () -> mode.is(Mode.Control) && speedLimit.get());
    private final BoolValue noDrag = new BoolValue("NoDrag", "无阻力", false, () -> mode.is(Mode.Control));

    private final NumberValue<Double> minSpeed = new NumberValue<>("MinSpeed", "最低速度(km/h)", 70.0, 0.1, 200.0, 0.1, () -> !mode.is(Mode.Bounce));
    private final BoolValue releaseSneak = new BoolValue("ReleaseSneak", "释放潜行", false);

    private final BoolValue packet = new BoolValue("Packet", "发包模式", false);
    private final NumberValue<Integer> packetDelay = new NumberValue<>("PacketDelay", "发包延迟(ticks)", 0, 0, 20, 1, packet::get);
    private final BoolValue setFlag = new BoolValue("SetFlag", "本地滑翔标记", false, () -> !mode.is(Mode.Bounce));

    private final BoolValue firework = new BoolValue("Firework", "烟花", false);
    private final NumberValue<Integer> fireworkBind = new NumberValue<>("FireworkBind", "烟花按键", GLFW.GLFW_KEY_R, -1, 500, 1, firework::get);
    private final BoolValue packetInteract = new BoolValue("PacketInteract", "发包使用", true, firework::get);
    private final BoolValue inventorySwap = new BoolValue("InventorySwap", "背包交换", true, firework::get);
    private final BoolValue onlyOne = new BoolValue("OnlyOne", "只允许一发", true, firework::get);
    private final BoolValue usingPause = new BoolValue("UsingPause", "使用中暂停", true, firework::get);

    private final BoolValue autoStop = new BoolValue("AutoStop", "未加载停止", true);
    private final BoolValue sprint = new BoolValue("Sprint", "疾跑", true, () -> mode.is(Mode.Bounce));
    private final NumberValue<Double> bouncePitch = new NumberValue<>("Pitch", "俯仰", 88.0, -90.0, 90.0, 0.1, () -> mode.is(Mode.Bounce));

    private final BoolValue instantFly = new BoolValue("AutoStart", "自动起飞", true, () -> !mode.is(Mode.Bounce));
    private final BoolValue checkSpeed = new BoolValue("CheckSpeed", "检查速度", false, () -> !mode.is(Mode.Bounce));
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "烟花延迟(ms)", 1000, 0, 20000, 50, () -> !mode.is(Mode.Bounce));
    private final NumberValue<Double> timeout = new NumberValue<>("Timeout", "起飞超时(s)", 0.0, 0.0, 1.0, 0.1, () -> !mode.is(Mode.Bounce));

    private final NumberValue<Double> sneakDownSpeed = new NumberValue<>("DownSpeed", "潜行下落速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Control));
    private final NumberValue<Double> boost = new NumberValue<>("Boost", "加速", 1.0, 0.1, 4.0, 0.1, () -> mode.is(Mode.Boost));

    private final BoolValue freeze = new BoolValue("Freeze", "冻结", false, () -> mode.is(Mode.Rotation));
    private final BoolValue motionStop = new BoolValue("MotionStop", "停止动量", false, () -> mode.is(Mode.Rotation));

    private final NumberValue<Double> infiniteMaxSpeed = new NumberValue<>("InfiniteMaxSpeed", "上摆速度(km/h)", 150.0, 50.0, 170.0, 1.0, () -> mode.is(Mode.Pitch));
    private final NumberValue<Double> infiniteMinSpeed = new NumberValue<>("InfiniteMinSpeed", "下摆速度(km/h)", 25.0, 10.0, 70.0, 1.0, () -> mode.is(Mode.Pitch));
    private final NumberValue<Double> infiniteMaxHeight = new NumberValue<>("InfiniteMaxHeight", "最大高度", 200.0, -50.0, 360.0, 1.0, () -> mode.is(Mode.Pitch));

    private final TimerUtil instantFlyTimer = new TimerUtil();
    private final TimerUtil fireworkTimer = new TimerUtil();

    private boolean savedPitch;
    private float prePitch;

    private float rotationYaw;
    private float rotationPitch;
    private boolean flying;
    private int packetDelayTicks;
    private boolean hasElytra;
    private boolean down;
    private float lastInfinitePitch;
    private float infinitePitch;
    private boolean fireworkPressed;

    public ElytraFly() {
        super("ElytraFly", "鞘翅飞行", Category.Movement);
        INSTANCE = this;
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @Override
    protected void onEnable() {
        if (nullCheck()) return;
        hasElytra = false;
        rotationYaw = mc.player.getYaw();
        rotationPitch = mc.player.getPitch();
        flying = false;
        packetDelayTicks = 0;
        down = false;
        infinitePitch = 0;
        lastInfinitePitch = 0;
        fireworkPressed = false;
        instantFlyTimer.reset();
        fireworkTimer.reset();
    }

    @Override
    protected void onDisable() {
        if (nullCheck()) return;
        if (autoJump.get() && mode.is(Mode.Bounce)) {
            boolean holdingJump = InputUtil.isKeyPressed(mc.getWindow(), mc.options.jumpKey.getDefaultKey().getCode());
            mc.options.jumpKey.setPressed(holdingJump);
        }
        if (releaseSneak.get()) {
            boolean holdingSneak = InputUtil.isKeyPressed(mc.getWindow(), mc.options.sneakKey.getDefaultKey().getCode());
            mc.options.sneakKey.setPressed(holdingSneak);
        }
    }

    @EventHandler
    private void onMotion(MotionEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.PRE) return;

        if (releaseSneak.get()) {
            mc.options.sneakKey.setPressed(false);
        }

        if (mode.is(Mode.Rotation) && isElytraFlying()) {
            updateRotationControl();
            event.setYaw(rotationYaw);
            event.setPitch(rotationPitch);
        } else if (mode.is(Mode.Pitch) && isElytraFlying()) {
            event.setPitch(infinitePitch);
        } else if (mode.is(Mode.Bounce) && isElytraFlying()) {
            event.setPitch(bouncePitch.get().floatValue());
        }
    }

    @EventHandler
    private void onTickPre(TickEvent.Pre event) {
        if (nullCheck()) return;

        updateInfinitePitch();
        flying = false;
        handleFireworkBind();

        if (packet.get()) {
            hasElytra = InvUtil.find(Items.ELYTRA).found();
        } else {
            hasElytra = isWearingElytra();
            if (infiniteDura.get() && !mc.player.isOnGround() && hasElytra && canClickPlayerInventory()) {
                flying = true;
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, mc.player);
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, mc.player);
                sendStartGliding();
            }

            if (mode.is(Mode.Bounce)) {
                if (autoJump.get()) {
                    mc.options.jumpKey.setPressed(true);
                }
                return;
            }
        }

        double speedKmh = computeSpeedKmh();

        if (mode.is(Mode.Boost)) {
            boostTick();
        }

        if (packet.get()) {
            if (mc.player.isOnGround()) return;
            packetDelayTicks++;
            if (packetDelayTicks <= packetDelay.get()) return;
            if (!canClickPlayerInventory()) return;

            int elytraSlot = findElytraSlotForClick();
            if (elytraSlot == -1) return;

            if (swapSlotWithChest(elytraSlot)) {
                sendStartGliding();
                if (shouldUseAutoFirework(speedKmh)) {
                    useFirework();
                    fireworkTimer.reset();
                }
                swapSlotWithChest(elytraSlot);
                packetDelayTicks = 0;
            }
            return;
        }

        if (shouldUseAutoFirework(speedKmh)) {
            useFirework();
            fireworkTimer.reset();
        }

        if (!isElytraFlying() && hasElytra) {
            fireworkTimer.lastMS = fireworkTimer.getCurrentMS() + 99_999_999L;
            if (!mc.player.isOnGround()
                    && instantFly.get()
                    && mc.player.getVelocity().y < 0.0
                    && !infiniteDura.get()) {
                long timeoutMs = (long) (timeout.get() * 1000.0);
                if (!instantFlyTimer.passedMillise(timeoutMs)) return;
                instantFlyTimer.reset();
                sendStartGliding();
            }
        }

    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (nullCheck()) return;
        if (mode.is(Mode.Bounce) && hasElytra) {
            if (!isElytraFlying()) {
                sendStartGliding();
            }
            if (checkConditions()) {
                if (!sprint.get()) {
                    if (isElytraFlying()) mc.player.setSprinting(mc.player.isOnGround());
                    else mc.player.setSprinting(true);
                }
            }
        }
    }

    @EventHandler
    private void onMove(MoveEvent event) {
        if (nullCheck()) return;
        if (!autoStop.get()) return;
        if (!isElytraFlying()) return;
        int chunkX = (int) (mc.player.getX() / 16.0);
        int chunkZ = (int) (mc.player.getZ() / 16.0);
        if (!mc.world.getChunkManager().isChunkLoaded(chunkX, chunkZ)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Bounce) && hasElytra && event.getType() == EventType.SEND && event.getPacket() instanceof ClientCommandC2SPacket packet) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.START_FALL_FLYING && !sprint.get()) {
                mc.player.setSprinting(true);
            }
        }

        if (mode.is(Mode.Bounce) && hasElytra && event.getType() == EventType.RECEIVE && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            mc.player.stopGliding();
        }
    }

    @EventHandler
    private void onTravel(TravelEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Bounce) && hasElytra) {
            if (event.isPre()) {
                savedPitch = true;
                prePitch = mc.player.getPitch();
                mc.player.setPitch(bouncePitch.get().floatValue());
            } else if (savedPitch) {
                savedPitch = false;
                mc.player.setPitch(prePitch);
            }
        } else if (mode.is(Mode.Pitch) && isElytraFlying()) {
            if (event.isPre()) {
                savedPitch = true;
                prePitch = mc.player.getPitch();
                mc.player.setPitch(lastInfinitePitch);
            } else if (savedPitch) {
                savedPitch = false;
                mc.player.setPitch(prePitch);
            }
        }

        if (nullCheck() || !hasElytra || !isElytraFlying() || event.isPost()) return;

        if (mode.is(Mode.Freeze) || (mode.is(Mode.Rotation) && freeze.get())) {
            if (!MoveUtil.isMoving() && !mc.options.jumpKey.isPressed() && !mc.options.sneakKey.isPressed()) {
                event.setCancelled(true);
                return;
            }
        }

        if (mode.is(Mode.Control)) {
            controlTravel();
            event.setCancelled(true);
            mc.player.move(MovementType.SELF, mc.player.getVelocity());
        }
    }

    private void updateRotationControl() {
        if (MoveUtil.isMoving()) {
            if (mc.options.jumpKey.isPressed()) {
                rotationPitch = -45.0f;
            } else if (mc.options.sneakKey.isPressed()) {
                rotationPitch = 45.0f;
            } else {
                rotationPitch = -1.9f;
                if (motionStop.get()) {
                    MoveUtil.setMotionY(0.0);
                }
            }
        } else {
            if (mc.options.jumpKey.isPressed()) {
                rotationPitch = -89.0f;
            } else if (mc.options.sneakKey.isPressed()) {
                rotationPitch = 89.0f;
            } else if (motionStop.get()) {
                MoveUtil.setMotionY(0.0);
            }
        }

        if (mc.options.forwardKey.isPressed() || mc.options.backKey.isPressed() || mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed()) {
            rotationYaw = getSprintYaw(mc.player.getYaw());
        } else if (motionStop.get()) {
            Vec3d v = mc.player.getVelocity();
            mc.player.setVelocity(0.0, v.y, 0.0);
        }
    }

    private void boostTick() {
        if (!hasElytra) return;
        if (!isElytraFlying()) return;

        float yawRad = (float) Math.toRadians(mc.player.getYaw());
        if (mc.options.forwardKey.isPressed()) {
            mc.player.addVelocity(-MathHelper.sin(yawRad) * (boost.get().floatValue() / 10.0f), 0.0, MathHelper.cos(yawRad) * (boost.get().floatValue() / 10.0f));
        }
    }

    private void controlTravel() {
        Vec3d vel = mc.player.getVelocity();
        double x = vel.x;
        double y = vel.y;
        double z = vel.z;

        if (firework.get()) {
            if (!(mc.options.sneakKey.isPressed() && mc.options.jumpKey.isPressed())) {
                if (mc.options.sneakKey.isPressed()) {
                    y = -sneakDownSpeed.get();
                } else if (mc.options.jumpKey.isPressed()) {
                    y = upFactor.get();
                } else {
                    y = -0.00000000003D * downFactor.get();
                }
            } else {
                y = 0.0;
            }
            double[] dir = MoveUtil.directionSpeed(speed.get());
            x = dir[0];
            z = dir[1];
            mc.player.setVelocity(x, y, z);
            return;
        }

        Vec3d lookVec = getRotationVec();
        double lookDist = Math.sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z);
        double motionDist = Math.sqrt(x * x + z * z);

        if (mc.options.sneakKey.isPressed()) {
            y = -sneakDownSpeed.get();
        } else if (!mc.options.jumpKey.isPressed()) {
            y = -0.00000000003D * downFactor.get();
        }

        if (mc.options.jumpKey.isPressed()) {
            double threshold = upFactor.get() / upFactor.getMax().doubleValue();
            if (motionDist > threshold && lookDist > 0.0) {
                double rawUpSpeed = motionDist * 0.01325D;
                y = y + rawUpSpeed * 3.2D;
                x = x - lookVec.x * rawUpSpeed / lookDist;
                z = z - lookVec.z * rawUpSpeed / lookDist;
            } else {
                double[] dir = MoveUtil.directionSpeed(speed.get());
                x = dir[0];
                z = dir[1];
            }
        }

        if (lookDist > 0.0D) {
            x = x + (lookVec.x / lookDist * motionDist - x) * 0.1D;
            z = z + (lookVec.z / lookDist * motionDist - z) * 0.1D;
        }

        if (!mc.options.jumpKey.isPressed()) {
            double[] dir = MoveUtil.directionSpeed(speed.get());
            x = dir[0];
            z = dir[1];
        }

        if (!noDrag.get()) {
            y = y * 0.9900000095367432D;
            x = x * 0.9800000190734863D;
            z = z * 0.9900000095367432D;
        }

        double finalDist = Math.sqrt(x * x + z * z);
        if (speedLimit.get() && finalDist > maxSpeed.get()) {
            x = x * maxSpeed.get() / finalDist;
            z = z * maxSpeed.get() / finalDist;
        }

        mc.player.setVelocity(x, y, z);
    }

    private void handleFireworkBind() {
        if (!firework.get()) return;
        if (mc.currentScreen != null) {
            fireworkPressed = false;
            return;
        }

        int key = fireworkBind.get();
        if (key < 0) return;
        boolean pressed = net.minecraft.client.util.InputUtil.isKeyPressed(mc.getWindow(), key);
        if (pressed) {
            if (!fireworkPressed) {
                if (fireworkTimer.passedMillise(delay.get()) && (!mc.player.isUsingItem() || !usingPause.get()) && isElytraFlying()) {
                    useFirework();
                    fireworkTimer.reset();
                }
            }
            fireworkPressed = true;
        } else {
            fireworkPressed = false;
        }
    }

    private boolean shouldUseAutoFirework(double speedKmh) {
        return firework.get()
                && (!checkSpeed.get() || speedKmh <= minSpeed.get())
                && fireworkTimer.passedMillise(delay.get())
                && (MoveUtil.isMoving() || (mode.is(Mode.Rotation) && mc.options.jumpKey.isPressed()))
                && (!mc.player.isUsingItem() || !usingPause.get())
                && isElytraFlying();
    }

    private void useFirework() {
        if (nullCheck()) return;
        if (onlyOne.get()) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof FireworkRocketEntity rocket) {
                    Entity shooter = ((IFireworkRocketEntity) rocket).getShooter();
                    if (shooter == mc.player) {
                        return;
                    }
                }
            }
        }

        fireworkTimer.reset();

        if (mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET)) {
            useItem(Hand.MAIN_HAND);
            return;
        }

        if (inventorySwap.get() && canClickPlayerInventory()) {
            var rocketInv = InvUtil.find(stack -> stack.isOf(Items.FIREWORK_ROCKET), SlotUtil.MAIN_START, SlotUtil.MAIN_END);
            if (rocketInv.found()) {
                if (InvUtil.invSwap(rocketInv.slot())) {
                    useItem(Hand.MAIN_HAND);
                    InvUtil.invSwapBack();
                }
                return;
            }
        }

        var rocketHotbar = InvUtil.findInHotbar(Items.FIREWORK_ROCKET);
        if (rocketHotbar.found() && rocketHotbar.isHotbar()) {
            InvUtil.swap(rocketHotbar.slot(), true);
            useItem(Hand.MAIN_HAND);
            InvUtil.swapBack();
        }
    }

    private void useItem(Hand hand) {
        if (packetInteract.get() && mc.world != null) {
            try (PendingUpdateManager pendingUpdateManager = mc.world.getPendingUpdateManager().incrementSequence()) {
                int sequence = pendingUpdateManager.getSequence();
                mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(hand, sequence, mc.player.getYaw(), mc.player.getPitch()));
            }
        } else {
            mc.interactionManager.interactItem(mc.player, hand);
        }
    }

    private boolean isWearingElytra() {
        ItemStack stack = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        return stack.isOf(Items.ELYTRA) && isUsableElytra(stack);
    }

    private boolean checkConditions() {
        if (mc.player.getAbilities().flying) return false;
        if (mc.player.hasVehicle()) return false;
        if (mc.player.isClimbing()) return false;
        ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        return chest.isOf(Items.ELYTRA) && isUsableElytra(chest);
    }

    private boolean ignoreGround() {
        if (mc.player.isTouchingWater()) return false;
        if (mc.player.hasStatusEffect(StatusEffects.LEVITATION)) return false;
        ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (!chest.isOf(Items.ELYTRA) || !isUsableElytra(chest)) return false;
        if (setFlag.get()) {
            ((IEntityFlag) mc.player).invokeSetFlag(7, true);
        }
        return true;
    }

    private boolean isUsableElytra(ItemStack stack) {
        if (!stack.isDamageable()) return true;
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    private void sendStartGliding() {
        if (!checkConditions()) return;
        if (!ignoreGround()) return;
        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
    }

    private boolean isElytraFlying() {
        return mc.player.isGliding() || (packet.get() && hasElytra && !mc.player.isOnGround()) || flying;
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

    private Vec3d getRotationVec() {
        float yaw = mc.player.lastYaw + (mc.player.getYaw() - mc.player.lastYaw) * MoveUtil.getTickDelta();
        return getRotationVector(-upPitch.get().floatValue(), yaw);
    }

    private float getSprintYaw(float baseYaw) {
        float forward = (mc.options.forwardKey.isPressed() ? 1.0f : 0.0f) + (mc.options.backKey.isPressed() ? -1.0f : 0.0f);
        float strafe = (mc.options.leftKey.isPressed() ? 1.0f : 0.0f) + (mc.options.rightKey.isPressed() ? -1.0f : 0.0f);

        float yaw = baseYaw;
        if (forward < 0.0f) {
            yaw += 180.0f;
        }

        float modifier = 1.0f;
        if (forward != 0.0f) {
            modifier = forward < 0.0f ? -0.5f : 0.5f;
        }

        if (strafe > 0.0f) {
            yaw -= 90.0f * modifier;
        }
        if (strafe < 0.0f) {
            yaw += 90.0f * modifier;
        }
        return yaw;
    }

    private void updateInfinitePitch() {
        lastInfinitePitch = infinitePitch;
        double dist = Math.hypot(mc.player.getX() - mc.player.lastX, mc.player.getZ() - mc.player.lastZ);
        double speedKmh = dist * 72.0;

        if (mc.player.getY() < infiniteMaxHeight.get()) {
            if (speedKmh < infiniteMinSpeed.get() && !down) down = true;
            if (speedKmh > infiniteMaxSpeed.get() && down) down = false;
        } else {
            down = true;
        }

        if (down) infinitePitch += 3.0f;
        else infinitePitch -= 3.0f;

        infinitePitch = (float) MathHelper.clamp(infinitePitch, -40.0, 40.0);
    }

    private double computeSpeedKmh() {
        double x = mc.player.getX() - mc.player.lastX;
        double y = mc.player.getY() - mc.player.lastY;
        double z = mc.player.getZ() - mc.player.lastZ;
        double dist = Math.sqrt(x * x + y * y + z * z);
        return dist * 72.0;
    }

    private boolean canClickPlayerInventory() {
        return mc.player.currentScreenHandler instanceof PlayerScreenHandler && mc.player.currentScreenHandler.getCursorStack().isEmpty();
    }

    private int findElytraSlotForClick() {
        var hotbar = InvUtil.findInHotbar(Items.ELYTRA);
        if (hotbar.found() && hotbar.isHotbar()) return hotbar.slot();
        var inv = InvUtil.find(stack -> stack.isOf(Items.ELYTRA), SlotUtil.MAIN_START, SlotUtil.MAIN_END);
        if (inv.found()) return inv.slot();
        return -1;
    }

    private boolean swapSlotWithChest(int invIndex) {
        if (!canClickPlayerInventory()) return false;

        int syncId = mc.player.currentScreenHandler.syncId;
        int fromSlotId = SlotUtil.indexToId(invIndex);
        if (fromSlotId == -1) return false;
        int chestSlotId = SlotUtil.indexToId(SlotUtil.ARMOR_START + 1);
        if (chestSlotId == -1) return false;

        mc.interactionManager.clickSlot(syncId, fromSlotId, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, chestSlotId, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, fromSlotId, 0, SlotActionType.PICKUP, mc.player);
        return true;
    }
}
