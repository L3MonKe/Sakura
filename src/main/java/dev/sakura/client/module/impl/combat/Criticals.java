package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.entity.AttackEntityEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IClientPlayerEntity;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Criticals extends Module {
    private enum Mode {
        Packet, Grim
    }

    private enum PacketMode {
        Pure, Grim
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);
    private final EnumValue<PacketMode> packetMode = new EnumValue<>("PacketMode", "包模式", PacketMode.Pure, () -> mode.is(Mode.Packet));
    private final BoolValue groundOnly = new BoolValue("GroundOnly", "仅地面", false, () -> mode.is(Mode.Packet) && packetMode.is(PacketMode.Pure));
    private final NumberValue<Integer> grimDelay = new NumberValue<>("Delay", "延迟", 0, 0, 20, 1, () -> mode.is(Mode.Grim));
    private final NumberValue<Integer> maxPackets = new NumberValue<>("Max Packets", "最大包数", 10, 5, 50, 1, () -> mode.is(Mode.Grim));

    // Stuck variables for top-level Grim mode
    private int stage = 0;
    private Packet<?> packet;
    private float lastYaw;
    private float lastPitch;
    private boolean tryDisable = false;
    private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    private boolean stuckEnabled = false;
    private int grimTimer = 0;
    private double startY = 0;
    private boolean isBacking = false;
    private final BoolValue predictSprint = new BoolValue("Predict", "预测", false, () -> mode.is(Mode.Packet) && packetMode.is(PacketMode.Grim));
    private final BoolValue alwaysShowCritParticles = new BoolValue("Show Crit Particles", "显示暴击粒子", true, () -> mode.is(Mode.Packet) && packetMode.is(PacketMode.Grim));
    private boolean wasOnGround = true;
    private boolean comboActive = false;
    private long nextAttackTime = 0;
    private boolean shouldStopSprint = false;
    private boolean shouldResumeSprint = false;
    private boolean shouldShowCritParticles = false;
    private net.minecraft.entity.Entity lastAttackedEntity = null;

    public Criticals() {
        super("Criticals", "刀刀暴击", Category.Combat);
    }

    @Override
    public String getSuffix() {
        if (mode.is(Mode.Packet)) {
            return packetMode.get().name();
        }
        return mode.get().name();
    }

    @Override
    public void onEnable() {
        resetStuck();
        isBacking = false;
        resetAirCombo();
    }

    @Override
    public void onDisable() {
        disableStuck();
        resetAirCombo();
    }

    @EventHandler
    public void onAttack(AttackEntityEvent event) {
        if (nullCheck()) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;

        if (mode.is(Mode.Packet)) {
            if (packetMode.is(PacketMode.Pure)) {
                if (!isCriticalHitAvailable() || (this.groundOnly.get() && !mc.player.isOnGround())) {
                    return;
                }
                final Box box = mc.player.getBoundingBox().offset(0.0D, 0.0625, 0.0D);
                if (!isBoxEmpty(box)) {
                    return;
                }
                doPacketCriticals();
            } else if (packetMode.is(PacketMode.Grim)) {
                if (comboActive && !mc.player.isOnGround()) {
                    shouldResumeSprint = true;
                    shouldStopSprint = false;
                }

                if (alwaysShowCritParticles.get()) {
                    lastAttackedEntity = event.getEntity();
                    shouldShowCritParticles = true;
                }
            }
        }
    }

    private void resetAirCombo() {
        wasOnGround = true;
        comboActive = false;
        nextAttackTime = 0;
        shouldStopSprint = false;
        shouldResumeSprint = false;
    }

    private boolean isMovingBackwards() {
        if (mc.player.getVelocity().x == 0 && mc.player.getVelocity().z == 0) return false;

        float yaw = mc.player.getYaw();
        double motionYaw = Math.toDegrees(Math.atan2(mc.player.getVelocity().z, mc.player.getVelocity().x)) - 90;

        double diff = Math.abs(yaw - motionYaw);
        diff = diff % 360;
        if (diff > 180) diff = 360 - diff;

        return diff > 135;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Grim)) {
            handleStuckTick();
        }

        if (mode.is(Mode.Packet) && packetMode.is(PacketMode.Grim)) {
            handleAirComboTick();

            if (shouldShowCritParticles && lastAttackedEntity != null) {
                mc.player.addCritParticles(lastAttackedEntity);
                shouldShowCritParticles = false;
            }
        }
    }

    private void handleAirComboTick() {
        boolean onGround = mc.player.isOnGround();

        if (onGround) {
            comboActive = false;
            nextAttackTime = 0;
            shouldStopSprint = false;
            shouldResumeSprint = false;
        } else {
            if (wasOnGround) {
                comboActive = true;
            }

            // 预测下一次攻击时间
            predictNextAttack();
        }

        wasOnGround = onGround;
    }

    private void predictNextAttack() {
        if (!predictSprint.get()) {
            return;
        }

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled() || killAura.getCurrentTarget() == null) {
            return;
        }

        if (mc.player.fallDistance <= 0) {
            return;
        }

        long now = System.currentTimeMillis();

        if (shouldResumeSprint && now - nextAttackTime >= 20) {
            shouldResumeSprint = false;
            shouldStopSprint = false;
            nextAttackTime = 0;
            return;
        }

        if (nextAttackTime > 0) {
            long timeUntilAttack = nextAttackTime - now;
            if (timeUntilAttack <= 20 && timeUntilAttack > 0) {
                shouldStopSprint = true;
            }
            return;
        }

        try {
            java.lang.reflect.Field modeField = KillAura.class.getDeclaredField("mode");
            modeField.setAccessible(true);
            Object modeValue = modeField.get(killAura);

            java.lang.reflect.Method isMethod = modeValue.getClass().getMethod("is", Object.class);
            boolean isV1_9 = (boolean) isMethod.invoke(modeValue, KillAura.AttackMode.v1_9);

            if (isV1_9) {
                float cooldown = mc.player.getAttackCooldownProgress(0.5f);
                if (cooldown >= 1.0f) {
                    shouldStopSprint = true;
                    nextAttackTime = now;
                } else if (cooldown >= 0.98f) {
                    nextAttackTime = now + 20;
                }
            } else {
                java.lang.reflect.Field minCpsField = KillAura.class.getDeclaredField("minCps");
                java.lang.reflect.Field maxCpsField = KillAura.class.getDeclaredField("maxCps");
                java.lang.reflect.Field lastAttackTimeField = KillAura.class.getDeclaredField("lastAttackTime");
                minCpsField.setAccessible(true);
                maxCpsField.setAccessible(true);
                lastAttackTimeField.setAccessible(true);

                Object minCpsValue = minCpsField.get(killAura);
                Object maxCpsValue = maxCpsField.get(killAura);
                long lastAttackTime = lastAttackTimeField.getLong(killAura);

                java.lang.reflect.Method getMethod = minCpsValue.getClass().getMethod("get");
                double minCps = (Double) getMethod.invoke(minCpsValue);
                double maxCps = (Double) getMethod.invoke(maxCpsValue);

                double minDelay = 1000.0 / maxCps;
                double maxDelay = 1000.0 / minCps;

                long timeSinceLastAttack = now - lastAttackTime;

                if (timeSinceLastAttack >= minDelay - 20) {
                    nextAttackTime = now + 20;
                }
            }
        } catch (Exception e) {
            float cooldown = mc.player.getAttackCooldownProgress(0.5f);
            if (cooldown >= 1.0f) {
                shouldStopSprint = true;
                nextAttackTime = now;
            } else if (cooldown >= 0.98f) {
                nextAttackTime = now + 20;
            }
        }
    }

    private void handleStuckTick() {
        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (!mc.player.isOnGround() && killAura != null && killAura.isEnabled() && killAura.getCurrentTarget() != null) {
            mc.player.setSprinting(false);
        }

        if (mc.player.isOnGround()) {
            isBacking = false;
            startY = mc.player.getY();
        } else if (isMovingBackwards()) {
            if (stuckEnabled) {
                disableStuck();
            }
            return;
        }

        if (isBacking) {
            if (stuckEnabled) {
                disableStuck();
            }
            return;
        }

        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);

        if (killAura != null && killAura.isEnabled() && !mc.player.isOnGround() && (scaffold == null || !scaffold.isEnabled())) {
            if (!isBoxEmpty(mc.player.getBoundingBox().offset(0.0, -0.2, 0.0))) {
                if (stuckEnabled) {
                    disableStuck();
                }
                return;
            }

            LivingEntity target = killAura.getCurrentTarget();
            if (target != null) {
                mc.player.setSprinting(false);
                if (stuckEnabled || mc.player.getVelocity().y < 0) {
                    if (mc.player.getVelocity().y > 0) {
                        if (stuckEnabled) {
                            disableStuck();
                        }
                        return;
                    }

                    if (isMovingBackwards()) {
                        if (stuckEnabled) {
                            disableStuck();
                        }
                        return;
                    }

                    if (grimTimer > 0) {
                        grimTimer--;
                    } else {
                        if (stuckEnabled) {
                            disableStuck();
                            grimTimer = 1;
                        } else {
                            enableStuck();
                            grimTimer = grimDelay.get();
                        }
                    }
                } else if (stuckEnabled) {
                    disableStuck();
                }
            } else {
                if (stuckEnabled) {
                    disableStuck();
                }
            }
        } else if (stuckEnabled) {
            disableStuck();
        }
    }

    private void enableStuck() {
        stuckEnabled = true;
        stage = 0;
        packet = null;
        lastYaw = Managers.ROTATION.rotations.yaw;
        lastPitch = Managers.ROTATION.rotations.pitch;
        tryDisable = false;
    }

    private void disableStuck() {
        if (stuckEnabled) {
            if (this.stage == 3) {
                stuckEnabled = false;
            } else {
                this.tryDisable = true;
            }
        } else {
            while (!packets.isEmpty()) {
                PacketUtil.sendPacketNoEvent(packets.poll());
            }
            stuckEnabled = false;
        }
    }

    private void resetStuck() {
        stuckEnabled = false;
        stage = 0;
        packet = null;
        tryDisable = false;
        packets.clear();
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (nullCheck()) return;
        if (!mode.is(Mode.Grim)) return;

        if (!stuckEnabled && !tryDisable) return;

        if (!stuckEnabled && tryDisable) {

        }

        Module scaffold = Sakura.MODULES.getModule(Scaffold.class);
        if (scaffold.isEnabled()) {
            if (stuckEnabled) disableStuck();
            return;
        }

        if (e.getType() == EventType.PRE) {
            if (stuckEnabled) {
                if (mc.player.getVelocity().y < 0) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.0, mc.player.getVelocity().z);
                }
                mc.player.setSprinting(false);
            }

            if (stage == 1) {
                stage = 2;
                float rotationYaw = mc.player.getYaw();
                float rotationPitch = mc.player.getPitch();
                if (shouldRotate() && (lastYaw != rotationYaw || lastPitch != rotationPitch)) {
                    PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(rotationYaw, rotationPitch, mc.player.isOnGround(), mc.player.horizontalCollision));

                    while (!packets.isEmpty()) {
                        PacketUtil.sendPacketNoEvent(packets.poll());
                    }

                    lastYaw = rotationYaw;
                    lastPitch = rotationPitch;
                }

                if (packet != null) {
                    PacketUtil.sendPacketNoEvent(packet);
                }
            }

            if (tryDisable) {
                PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 1337.0, mc.player.getY(), mc.player.getZ() + 1337.0, mc.player.isOnGround(), mc.player.horizontalCollision));

                while (!packets.isEmpty()) {
                    PacketUtil.sendPacketNoEvent(packets.poll());
                }

                this.tryDisable = false;
                this.stuckEnabled = false;
            }
        }
    }

    private boolean shouldRotate() {
        if (packet instanceof PlayerInteractItemC2SPacket blockPlacement) {
            ItemStack item = mc.player.getStackInHand(blockPlacement.getHand());
            boolean isBowlFood = item.contains(DataComponentTypes.FOOD) && item.get(DataComponentTypes.USE_REMAINDER) != null && item.get(DataComponentTypes.USE_REMAINDER).convertInto().isOf(Items.BOWL);
            return !isBowlFood && !(item.getItem() instanceof BowItem);
        } else if (packet instanceof PlayerActionC2SPacket playerDigging) {
            return playerDigging.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM && mc.player.getActiveItem().getItem() instanceof BowItem;
        }
        return false;
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (mode.is(Mode.Packet) && packetMode.is(PacketMode.Grim)) {
            if (comboActive && !mc.player.isOnGround()) {
                KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
                if (killAura != null && killAura.isEnabled() && killAura.getCurrentTarget() != null) {
                    if (mc.player.fallDistance > 0) {
                        if (predictSprint.get()) {
                            if (shouldStopSprint) {
                                event.setSprint(false);
                                mc.player.setSprinting(false);
                                mc.options.sprintKey.setPressed(false);
                            }
                        } else {
                            event.setSprint(false);
                            mc.player.setSprinting(false);
                            mc.options.sprintKey.setPressed(false);
                        }
                    }
                }
            }
            return;
        }

        if (!mode.is(Mode.Grim)) return;

        if (stuckEnabled) {
            event.setForward(0.0F);
            event.setStrafe(0.0F);
            event.setJump(false);
            event.setSneak(false);
            event.setSprint(false);
        }

        if (!mc.player.isOnGround()) {
            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            if (killAura != null && killAura.isEnabled() && killAura.getCurrentTarget() != null) {
                event.setSprint(false);
                mc.player.setSprinting(false);
            }
        }
    }

    @EventHandler
    public void onRespawnMotion(MotionEvent event) {
        if (!mode.is(Mode.Grim)) return;
        if (event.getType() == EventType.PRE && mc.player.age <= 1) {
            stage = 3;
            packet = null;
            disableStuck();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (!mode.is(Mode.Grim)) return;

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            while (!packets.isEmpty()) {
                PacketUtil.sendPacketNoEvent(packets.poll());
            }
            stage = 3;
            disableStuck();
            return;
        }

        if (!stuckEnabled) return;

        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            event.setCancelled(true);
        } else if (event.getPacket() instanceof CommonPongC2SPacket) {
            packets.offer(event.getPacket());
            event.setCancelled(true);
        } else if (event.getPacket() instanceof PlayerInteractItemC2SPacket || event.getPacket() instanceof PlayerActionC2SPacket) {
            packet = event.getPacket();
            stage = 1;
            event.setCancelled(true);
        } else if (event.getPacket() instanceof ClientCommandC2SPacket command) {
            if (command.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
                if (killAura != null && killAura.isEnabled() && killAura.getCurrentTarget() != null && !mc.player.isOnGround()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void doPacketCriticals() {
        final Vec3d pos = mc.player.getEntityPos();
        final boolean ground = mc.player.isOnGround();
        final IClientPlayerEntity accessor = (IClientPlayerEntity) mc.player;

        mc.player.setPosition(pos.add(0.0, 0.0625, 0.0));
        mc.player.setOnGround(false);
        accessor.invokeSendMovementPackets();

        mc.player.setPosition(pos.add(0.0, 0.00125, 0.0));
        mc.player.setOnGround(false);
        accessor.invokeSendMovementPackets();

        mc.player.setPosition(pos);
        mc.player.setOnGround(ground);
    }

    private boolean isCriticalHitAvailable() {
        return mc.player.isOnGround() &&
                !mc.player.isTouchingWater() &&
                !mc.player.isInLava() &&
                !mc.player.isClimbing() &&
                !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) &&
                !mc.player.hasVehicle();
    }

    private boolean isBoxEmpty(Box box) {
        return !mc.world.getBlockCollisions(mc.player, box).iterator().hasNext();
    }
}
