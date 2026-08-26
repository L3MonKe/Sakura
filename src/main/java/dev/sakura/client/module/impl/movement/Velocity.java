package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.entity.AttackEntityEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IClientPlayerEntity;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.combat.AntiBot;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.player.PacketLockUtils;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Vector3d;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "反击退", Category.Movement);
    }

    private enum Mode {
        Legit,
        NoXZ,
        Hypixel
    }

    private enum VelocityStage {
        NONE,
        DELAY,
        ATTACK,
        CLEAR,
        LAG
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.NoXZ);
    private final NumberValue<Double> alinkTime = new NumberValue<>("Max Alink Time (ms)", "最大Alink时间(ms)", 2500.0, 50.0, 10000.0, 50.0, () -> mode.is(Mode.NoXZ));
    private final BoolValue render = new BoolValue("Render", "渲染", false, () -> mode.is(Mode.NoXZ));
    private final BoolValue debug = new BoolValue("Debug", "调试", false, () -> mode.is(Mode.NoXZ));
    private final BoolValue old = new BoolValue("Old", "旧逻辑", false, () -> mode.is(Mode.NoXZ));
    private final BoolValue airpushValue = new BoolValue("Airpush", "空气推", true, () -> mode.is(Mode.Hypixel));
    private final BoolValue attackReduce = new BoolValue("AttackReduce", "攻击减少", true, () -> mode.is(Mode.Hypixel));


    public boolean lag;
    private boolean jump;
    private Vec3d velocity;
    private long velocityTime;
    private PlayerEntity target;
    private VelocityStage stage;
    private final Map<Entity, Vector3d> targets = new ConcurrentHashMap<>();
    private final Queue<Packet<? super ClientPlayNetworkHandler>> packets = new ConcurrentLinkedQueue<>();

    public static boolean shouldStrict;
    private boolean hypForward;
    private boolean hypBuffer = false;
    private int hitCount;
    private Rotation oppositeRotation;
    private int ticksSinceVelocity = -1;
    private boolean handleReset = false;
    private boolean receiveVelocity = false;
    private int bufferTick = -1;
    private boolean packetOnGround = false;
    private final LinkedBlockingDeque<Packet<? super ClientPlayNetworkHandler>> serverPackets = new LinkedBlockingDeque<>();

    @Override
    public void onEnable() {
        jump = false;
        lag = false;
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;

        hypForward = false;
        hypBuffer = false;
        shouldStrict = false;
        hitCount = 0;
        oppositeRotation = null;
        ticksSinceVelocity = -1;
        handleReset = false;
        receiveVelocity = false;
        bufferTick = -1;
        packetOnGround = false;
        serverPackets.clear();
    }

    @Override
    public void onDisable() {
        jump = false;
        lag = false;
        targets.clear();
        target = null;
        stage = VelocityStage.NONE;
        clear(true);

        hypForward = false;
        hypBuffer = false;
        shouldStrict = false;
        hitCount = 0;
        oppositeRotation = null;
        ticksSinceVelocity = -1;
        handleReset = false;
        receiveVelocity = false;
        bufferTick = -1;
        packetOnGround = false;
        while (!serverPackets.isEmpty()) {
            Packet<? super ClientPlayNetworkHandler> p = serverPackets.poll();
            if (p != null && mc.getNetworkHandler() != null) {
                p.apply(mc.getNetworkHandler());
            }
        }
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Hypixel)) {
            onHypixelTick();
            return;
        }

        if (shouldDisableDueToNoSlow()) {
            if (stage != VelocityStage.NONE) {
                clear(true);
            }
            return;
        }

        switch (mode.get()) {
            case NoXZ -> {
                if (stage == VelocityStage.ATTACK) {
                    if ((old.get() || mc.player.isOnGround()) && mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
                        if (mc.player.isSprinting()) mc.player.setSprinting(false);
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        if (!old.get()) {
                            jump = true;
                            mc.interactionManager.attackEntity(mc.player, target);
                            mc.player.swingHand(Hand.MAIN_HAND);
                        }
                        mc.player.setVelocity(velocity.x * 0.6, velocity.y, velocity.z * 0.6);
                        debug("ATTACK");
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

        this.setSuffix(mode.get() + (stage == VelocityStage.DELAY ? " " + (System.currentTimeMillis() - velocityTime) / 50 + "Ticks" : ""));
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Hypixel)) {
            onHypixelPostTick();
            return;
        }

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

        if (event.getType() == EventType.SEND) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                packetOnGround = packet.isOnGround();
            }
            return;
        }

        if (event.getType() != EventType.RECEIVE) return;

        if (mode.is(Mode.Hypixel)) {
            onHypixelPacket(event);
            return;
        }

        if (shouldDisableDueToNoSlow()) return;

        switch (mode.get()) {
            case NoXZ -> {
                if (event.getPacket() instanceof PlayerPositionLookS2CPacket && stage == VelocityStage.NONE) {
                    lag = true;
                    debug("收到位置包，设置 lag");
                    return;
                }
                if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet && packet.getEntityId() == mc.player.getId()) {
                    if (old.get()) {
                        jump = true;
                    }
                    if (stage == VelocityStage.NONE) {
                        if (!lag) {
                            stage = VelocityStage.DELAY;
                            velocityTime = System.currentTimeMillis();
                            event.setCancelled(true);
                            velocity = new Vec3d(packet.getVelocity().getX(), packet.getVelocity().getY(), packet.getVelocity().getZ());
                            debug("进入 DELAY，velocity=" + formatVec(velocity));

                        } else {
                            lag = false;
                            debug("清除 lag 标记");
                        }
                        return;
                    } else {
                        velocity = new Vec3d(packet.getVelocity().getX(), packet.getVelocity().getY(), packet.getVelocity().getZ());
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

                                targets.put(entity, new Vector3d(currentPos.x + dx, currentPos.y + dy, currentPos.z + dz));
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
        if (mode.is(Mode.Hypixel)) {
            if (handleReset || hypBuffer || shouldStrict) {
                event.setForward(1);
            }
            return;
        }

        if (shouldDisableDueToNoSlow()) return;

        if (mode.is(Mode.NoXZ)) {
            if (stage == VelocityStage.DELAY && velocity != null && mc.player.isOnGround() && mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof PlayerEntity player && !AntiBot.isBot(player)) {
                event.setForward(1);
                event.setStrafe(0);
                stage = VelocityStage.ATTACK;
                this.target = player;
                debug("进入 ATTACK，目标=" + player.getName().getString());
            }
        }

        if (jump) {
            if (mc.player.isOnGround() && MoveUtil.isMoving()) {
                event.setJump(true);
            }
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

    public boolean isActive() {
        return stage != VelocityStage.NONE;
    }

    private void debug(String message) {
        if (debug.get()) {
            ChatUtil.clientMessage("§7[Velocity] §f" + message);
        }
    }

    private String formatVec(Vec3d vec) {
        return String.format("%.3f, %.3f, %.3f", vec.x, vec.y, vec.z);
    }

    private boolean shouldDisableDueToNoSlow() {
        NoSlow noSlow = Sakura.MODULES.getModule(NoSlow.class);
        return noSlow != null && noSlow.isEnabled() && mc.player.isUsingItem();
    }

    private boolean clientOnGround() {
        if (mc.player == null) return false;
        return packetOnGround && mc.player.isOnGround();
    }

    private boolean isScaffoldEnabled() {
        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
        return scaffold != null && scaffold.isEnabled();
    }

    private Rotation getRotationOrElseMC() {
        return Managers.ROTATION.getRotation();
    }

    private EntityHitResult rayCastEntityHit(Rotation rotation, double reach, boolean throughWalls) {
        if (mc.player == null || mc.world == null) return null;
        Vec3d start = mc.player.getCameraPosVec(1);
        Vec3d look = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d end = start.add(look.multiply(reach));
        double maxSq = reach * reach;
        if (!throughWalls) {
            BlockHitResult blockHit = mc.world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
            if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {
                double blockDistSq = start.squaredDistanceTo(blockHit.getPos());
                if (blockDistSq < maxSq) maxSq = blockDistSq;
            }
        }
        return net.minecraft.entity.projectile.ProjectileUtil.raycast(mc.player, start, end, mc.player.getBoundingBox().stretch(look.multiply(reach)).expand(1.0, 1.0, 1.0), e -> e != mc.player && !e.isSpectator() && e.canHit(), maxSq);
    }

    private void onHypixelTick() {
        PacketLockUtils.reset();

        if (ticksSinceVelocity >= 0) {
            ticksSinceVelocity++;
        }
        if (bufferTick >= 0) {
            bufferTick++;
        }
        if (ticksSinceVelocity >= 10) {
            if (receiveVelocity) receiveVelocity = false;
            shouldStrict = false;
            ticksSinceVelocity = -1;
        }
        if (ticksSinceVelocity >= hitCount) {
            if (receiveVelocity) {
                receiveVelocity = false;
            }
            shouldStrict = false;
        }

        handleJumpReset();

        if (hypBuffer || handleReset) {
            shouldStrict = true;
        }

        EntityHitResult hitResult = rayCastEntityHit(getRotationOrElseMC(), 3.1, false);
        if (hitResult == null && 0.0 > 0) {
            hitResult = rayCastEntityHit(getRotationOrElseMC(), 0.0, true);
        }
        Entity rayTarget = (hitResult != null) ? hitResult.getEntity() : null;

        boolean validTarget = (rayTarget != null && rayTarget != mc.player && (rayTarget instanceof PlayerEntity || !true))
                || (getKillAuraTarget() != null && (getKillAuraTarget() instanceof PlayerEntity || !true));

        if ((validTarget || getFarthestLivingEntity(true) != null) && receiveVelocity) {
            shouldStrict = true;
        }

        if (receiveVelocity) {
            if (mc.player.isSprinting() || ((IClientPlayerEntity) mc.player).getLastSprinting()) {
                if (validTarget && attackReduce.get()) {
                    if (PacketLockUtils.attackAndLock()) {
                        if (rayTarget != null && rayTarget != mc.player) {
                            Sakura.EVENT_BUS.post(new AttackEntityEvent(mc.player, rayTarget));
                            mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(rayTarget, mc.player.isSneaking()));
                        } else {
                            LivingEntity kaTarget = getKillAuraTarget();
                            if (kaTarget != null) {
                                Sakura.EVENT_BUS.post(new AttackEntityEvent(mc.player, kaTarget));
                                mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(kaTarget, mc.player.isSneaking()));
                            }
                        }

                        mc.player.swingHand(Hand.MAIN_HAND);
                        if (Objects.requireNonNull(mc.interactionManager).getCurrentGameMode() != GameMode.SPECTATOR) {
                            mc.player.setVelocity(mc.player.getVelocity().multiply(0.6, 1.0, 0.6));
                            mc.player.setSprinting(false);
                        }
                    }
                } else if (getFarthestLivingEntity(true) != null && airpushValue.get()) {
                    airPush();
                }
            }
        }

        this.setSuffix("Hypixel" + (hypBuffer ? " Buffer:" + bufferTick : ""));
    }

    private void airPush() {
        LivingEntity entity = getFarthestLivingEntity(true);
        if (entity != null && PacketLockUtils.attackAndLock()) {
            Sakura.EVENT_BUS.post(new AttackEntityEvent(mc.player, entity));
            mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            if (Objects.requireNonNull(mc.interactionManager).getCurrentGameMode() != GameMode.SPECTATOR) {
                mc.player.setVelocity(mc.player.getVelocity().multiply(0.6, 1.0, 0.6));
                mc.player.setSprinting(false);
            }
        }
    }

    private void onHypixelPostTick() {
        if (mc.player == null) return;

        EntityHitResult hitResult = rayCastEntityHit(getRotationOrElseMC(), 3.1, false);
        if (hitResult == null && 0.0 > 0) {
            hitResult = rayCastEntityHit(getRotationOrElseMC(), 0.0, true);
        }
        Entity attackTarget = (hitResult != null) ? hitResult.getEntity() : null;

        if (hypBuffer) {
            if (bufferTick >= 4 || mc.player.isSprinting() || ((IClientPlayerEntity) mc.player).getLastSprinting() || mc.player.hasStatusEffect(net.minecraft.entity.effect.StatusEffects.SLOWNESS) || clientOnGround() || getKillAuraTarget() == null && attackTarget == null && getFarthestLivingEntity(true) == null) {
                while (!serverPackets.isEmpty()) {
                    Packet<? super ClientPlayNetworkHandler> p = serverPackets.poll();
                    p.apply(mc.getNetworkHandler());
                }
                if (debug.get())
                    debug("Used Tick: " + bufferTick);
                receiveVelocity = true;
                ticksSinceVelocity = 0;
                if (clientOnGround() && !isScaffoldEnabled()) {
                    if (getClosestLivingEntity(3.1, 0.0, true) != null) {
                        LivingEntity target = getClosestLivingEntity(3.1, 0.0, true);
                        Rotation rotation1 = RotationUtil.calculate(newGetPointToEntityBoxSafe(mc.player, Objects.requireNonNull(target), 0.0));
                        Managers.ROTATION.setRotations(new Rotation(oppositeRotation.yaw, rotation1.pitch), 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                    } else {
                        Managers.ROTATION.setRotations(oppositeRotation, 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                    }
                    shouldStrict = true;
                }
                hypBuffer = false;
                bufferTick = -1;
            }
        }
    }

    private void onHypixelPacket(PacketEvent event) {
        if (event.getType() == EventType.RECEIVE) {
            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
                if (packet.getEntityId() == mc.player.getId()) {
                    double velocityX = packet.getVelocity().getX();
                    double velocityY = packet.getVelocity().getY();
                    double velocityZ = packet.getVelocity().getZ();
                    boolean falling = velocityY <= 0.0;
                    double strength = new Vec3d(velocityX, 0, velocityZ).length();
                    boolean lowStrength = strength < 0.1;

                    if (falling || lowStrength || mc.player.isClimbing() || mc.player.isInFluid()) {
                        if (debug.get())
                            debug("Abnormal knockback");
                        return;
                    }

                    Vec3d knockback = new Vec3d(velocityX, 0, velocityZ).normalize();
                    knockback = new Vec3d(knockback.x * -1, 0, knockback.z * -1);

                    int rawX = (int) Math.round(velocityX * 8000);
                    int rawZ = (int) Math.round(velocityZ * 8000);
                    hitCount = computeReduceTicks(rawX, rawZ);

                    Vec3d lookAt = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()).add(knockback.x, 0, knockback.z);
                    float currentPitch = getRotationOrElseMC().pitch;
                    Rotation rotation = RotationUtil.calculate(lookAt);
                    rotation = new Rotation(rotation.yaw, currentPitch);
                    oppositeRotation = rotation;

                    if (!hypBuffer) {
                        EntityHitResult hitResult = rayCastEntityHit(getRotationOrElseMC(), 3.1, false);
                        if (hitResult == null && 0.0 > 0) {
                            hitResult = rayCastEntityHit(getRotationOrElseMC(), 0.0, true);
                        }
                        Entity attackTarget = (hitResult != null) ? hitResult.getEntity() : null;
                        if ((attackTarget != null && attackTarget != mc.player && (attackTarget instanceof PlayerEntity ||
                                !true)) || (getKillAuraTarget() != null && (getKillAuraTarget() instanceof PlayerEntity || !true)) || (getFarthestLivingEntity(true)) != null && true) {
                            if (mc.player.isSprinting() || ((IClientPlayerEntity) mc.player).getLastSprinting()) {
                                if (clientOnGround() && !isScaffoldEnabled()) {
                                    if (getClosestLivingEntity(3.1, 0.0, true) != null) {
                                        LivingEntity target = getClosestLivingEntity(3.1, 0.0, true);
                                        Rotation rotation1 = RotationUtil.calculate(newGetPointToEntityBoxSafe(mc.player, Objects.requireNonNull(target), 0.0));
                                        Managers.ROTATION.setRotations(new Rotation(oppositeRotation.yaw, rotation1.pitch), 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                                    } else {
                                        Managers.ROTATION.setRotations(oppositeRotation, 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                                    }
                                    shouldStrict = true;
                                }
                                receiveVelocity = true;
                                ticksSinceVelocity = 0;
                            } else {
                                hypBuffer = true;
                                if (debug.get())
                                    debug("Buffer");
                                bufferTick = 0;
                            }
                        } else {
                            if (clientOnGround() && !isScaffoldEnabled()) {
                                if (getClosestLivingEntity(3.1, 0.0, true) != null) {
                                    LivingEntity target = getClosestLivingEntity(3.1, 0.0, true);
                                    Rotation rotation1 = RotationUtil.calculate(newGetPointToEntityBoxSafe(mc.player, Objects.requireNonNull(target), 0.0));
                                    Managers.ROTATION.setRotations(new Rotation(oppositeRotation.yaw, rotation1.pitch), 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                                } else {
                                    Managers.ROTATION.setRotations(oppositeRotation, 10.0, MovementFix.TRADITIONAL, Priority.Medium);
                                }
                                shouldStrict = true;
                            }
                            receiveVelocity = true;
                            ticksSinceVelocity = 0;
                        }
                    }
                }
            }

            if (event.getPacket() instanceof GameMessageS2CPacket packet) {
                if (packet.content().getString().contains("failed") && packet.content().getString().contains("Hitboxes") && ticksSinceVelocity >= 0) {
                    event.setCancelled(true);
                }
            }

            if (hypBuffer) {
                if (!isInBlackList(event)) {
                    event.setCancelled(true);
                    @SuppressWarnings("unchecked")
                    Packet<? super ClientPlayNetworkHandler> typedPacket = (Packet<? super ClientPlayNetworkHandler>) event.getPacket();
                    serverPackets.add(typedPacket);
                }
            }
        }
    }

    private int computeReduceTicks(int motionX, int motionZ) {
        double kb = Math.hypot(motionX, motionZ);
        double ticksExact = 0.000643153527 * kb + 2.9419087136;
        int ticks = (int) Math.round(ticksExact);

        if (ticks < 1) ticks = 1;
        if (ticks > 10) ticks = 10;

        return ticks;
    }

    private boolean isInBlackList(PacketEvent event) {
        if (mc.player == null) return false;

        Packet<?> packet = event.getPacket();
        return packet instanceof HealthUpdateS2CPacket ||
                packet instanceof PlayerPositionLookS2CPacket ||
                packet instanceof PlaySoundS2CPacket ||
                packet instanceof GameMessageS2CPacket ||
                packet instanceof DeathMessageS2CPacket ||
                packet instanceof CloseScreenS2CPacket ||
                packet instanceof DamageTiltS2CPacket ||
                packet instanceof TitleS2CPacket ||
                packet instanceof TeamS2CPacket ||
                (packet instanceof EntityAnimationS2CPacket entityAnimationS2CPacket && entityAnimationS2CPacket.getEntityId() != mc.player.getId());
    }

    private void handleJumpReset() {
        if (mc.player == null) return;
        if (!(mc.currentScreen instanceof InventoryScreen || mc.currentScreen instanceof GenericContainerScreen)) {
            if (ticksSinceVelocity >= 0 && !isScaffoldEnabled()) {
                handleReset = true;
                if (ticksSinceVelocity <= 2 && clientOnGround()) {
                    mc.options.jumpKey.setPressed(true);
                }
                if (ticksSinceVelocity <= 3) {
                    mc.options.forwardKey.setPressed(true);
                    hypForward = true;
                }
            }
            if (ticksSinceVelocity >= 4 && ticksSinceVelocity <= 9) {
                mc.options.jumpKey.setPressed(InputUtil.isKeyPressed(mc.getWindow(), mc.options.jumpKey.getDefaultKey().getCode()));
                if (hypForward) {
                    mc.options.forwardKey.setPressed(InputUtil.isKeyPressed(mc.getWindow(), mc.options.forwardKey.getDefaultKey().getCode()));
                    hypForward = false;
                }
                handleReset = false;
            }
        }
    }

    private LivingEntity getKillAuraTarget() {
        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura != null) {
            return killAura.getCurrentTarget();
        }
        return null;
    }

    private LivingEntity getClosestLivingEntity(double range, double throughWallRangeVal, boolean onlyPlayerVal) {
        if (mc.world == null || mc.player == null) return null;

        LivingEntity target = null;

        double closestDistance = range;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living == mc.player) continue;
            if (living instanceof ArmorStandEntity) continue;

            if (onlyPlayerVal && !(living instanceof PlayerEntity)) continue;

            if (!onlyPlayerVal) {
                if (!(living instanceof PlayerEntity ||
                        living instanceof MobEntity ||
                        living instanceof VillagerEntity)) {
                    continue;
                }
            }

            double dist = newGetDistanceToEntityBoxSafe(mc.player, living, throughWallRangeVal);

            if (dist <= closestDistance) {
                closestDistance = dist;
                target = living;
            }
        }

        return target;
    }

    private LivingEntity getFarthestLivingEntity(boolean onlyPlayerVal) {
        if (mc.world == null || mc.player == null) return null;

        LivingEntity target = null;
        double farthestDistance = 0;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living == mc.player) continue;
            if (living instanceof ArmorStandEntity) continue;

            if (onlyPlayerVal && !(living instanceof PlayerEntity)) continue;

            if (!onlyPlayerVal) {
                if (!(living instanceof PlayerEntity ||
                        living instanceof MobEntity ||
                        living instanceof VillagerEntity)) {
                    continue;
                }
            }

            double dist = newGetDistanceToEntityBoxSafe(mc.player, living, Double.MAX_VALUE);

            if (dist < 11 && !false) continue;

            if (dist > farthestDistance) {
                farthestDistance = dist;
                target = living;
            }
        }

        return target;
    }

    private static Vec3d getNearestPointBB(Vec3d eye, Box box) {
        double x = eye.x;
        double y = eye.y;
        double z = eye.z;

        if (x > box.maxX) x = box.maxX;
        else if (x < box.minX) x = box.minX;

        if (y > box.maxY) y = box.maxY;
        else if (y < box.minY) y = box.minY;

        if (z > box.maxZ) z = box.maxZ;
        else if (z < box.minZ) z = box.minZ;

        return new Vec3d(x, y, z);
    }

    private static boolean isNotBlocked(World world, Vec3d start, Vec3d end, Entity entity) {
        RaycastContext context = new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity);
        BlockHitResult hitResult = world.raycast(context);
        return hitResult.getType() == HitResult.Type.MISS;
    }

    private static double newGetDistanceToEntityBoxSafe(Entity from, Entity to, double throughwallRange) {
        Vec3d eye = from.getEyePos();
        World world = from.getEntityWorld();
        Box box = to.getBoundingBox();

        double best = Double.MAX_VALUE;

        Vec3d nearest = getNearestPointBB(eye, box);
        double d = eye.distanceTo(nearest);
        if (d <= throughwallRange) return d;
        if (isNotBlocked(world, eye, nearest, from)) {
            best = d;
        }

        Vec3d[] samples = new Vec3d[]{
                box.getCenter(),
                new Vec3d(box.getCenter().x, box.maxY - 0.1, box.getCenter().z),
                to.getEyePos(),
                new Vec3d(box.getCenter().x, (box.minY + box.maxY) * 0.5, box.getCenter().z),
                new Vec3d(box.getCenter().x, box.minY + 0.2, box.getCenter().z)
        };

        for (Vec3d p : samples) {
            double dist = eye.distanceTo(p);
            if (dist >= best) continue;

            if (dist <= throughwallRange || isNotBlocked(world, eye, nearest, from)) {
                best = dist;
            }
        }

        return best;
    }

    private static Vec3d newGetPointToEntityBoxSafe(Entity from, Entity to, double throughwallRange) {
        Vec3d eye = from.getEyePos();
        World world = from.getEntityWorld();
        Box box = to.getBoundingBox();

        double best = Double.MAX_VALUE;
        Vec3d bestPoint = null;

        Vec3d nearest = getNearestPointBB(eye, box);
        double d = eye.distanceTo(nearest);
        if (d <= throughwallRange) return nearest;

        if (isNotBlocked(world, eye, nearest, from)) return nearest;

        Vec3d[] samples = new Vec3d[]{
                box.getCenter(),
                new Vec3d(box.getCenter().x, box.maxY - 0.1, box.getCenter().z),
                to.getEyePos(),
                new Vec3d(box.getCenter().x, (box.minY + box.maxY) * 0.5, box.getCenter().z),
                new Vec3d(box.getCenter().x, box.minY + 0.2, box.getCenter().z)
        };

        for (Vec3d p : samples) {
            double dist = eye.distanceTo(p);
            if (dist >= best) continue;

            if (dist <= throughwallRange || isNotBlocked(world, eye, nearest, from)) {
                best = dist;
                bestPoint = p;
            }
        }

        return bestPoint;
    }
}
