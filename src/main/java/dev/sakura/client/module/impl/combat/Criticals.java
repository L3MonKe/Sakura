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
import dev.sakura.client.module.impl.movement.Velocity;
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
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Criticals extends Module {
    private enum Mode {
        Packet, Grim
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);
    private final BoolValue groundOnly = new BoolValue("GroundOnly", "仅地面", false);
    private final NumberValue<Integer> grimDelay = new NumberValue<>("GrimDelay", "Grim延迟", 3, 0, 20, 1);
    private final NumberValue<Integer> maxPackets = new NumberValue<>("MaxPackets", "最大包数", 15, 5, 50, 1);

    // Stuck variables for Grim mode
    private int stage = 0;
    private Packet<?> packet;
    private float lastYaw;
    private float lastPitch;
    private boolean tryDisable = false;
    private final Queue<CommonPongC2SPacket> packets = new ConcurrentLinkedQueue<>();
    private boolean stuckEnabled = false;
    private int grimTimer = 0;
    private int packetCount = 0;

    private int attackTimer = 0;
    private double startY = 0;
    private boolean isBacking = false;

    public Criticals() {
        super("Criticals", "刀刀暴击", Category.Combat);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @Override
    public void onEnable() {
        resetStuck();
        attackTimer = 0;
        isBacking = false;
    }

    @Override
    public void onDisable() {
        disableStuck();
    }

    @EventHandler
    public void onAttack(AttackEntityEvent event) {
        if (nullCheck()) return;
        if (event.getEntity() instanceof LivingEntity) {
            if (!isCriticalHitAvailable() || (this.groundOnly.get() && !mc.player.isOnGround())) {
                return;
            }

            final Box box = mc.player.getBoundingBox().offset(0.0D, 0.0625D, 0.0D);
            if (!isBoxEmpty(box)) {
                return;
            }

            if (mode.is(Mode.Packet)) {
                doPacketCriticals();
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        
        if (attackTimer > 0) {
            attackTimer--;
        }

        if (mode.is(Mode.Grim)) {
            if (mc.player.hurtTime > 0) {
                if (stuckEnabled) {
                    disableStuck();
                }
                return;
            }

            if (mc.player.isOnGround()) {
                isBacking = false;
                startY = mc.player.getY();
            } else if (mc.player.input.playerInput.backward() && !isBacking) {
                 if (startY - mc.player.getY() > 0.5) {
                     isBacking = true;
                     disableStuck();
                 }
            }

            if (isBacking) {
                if (stuckEnabled) {
                    disableStuck();
                }
                return;
            }

            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
            Velocity velocity = Sakura.MODULES.getModule(Velocity.class);

            if (velocity != null && velocity.isEnabled() && velocity.isActive()) {
                if (stuckEnabled) {
                    disableStuck();
                }
                return;
            }

            if (killAura != null && killAura.isEnabled() && !mc.player.isOnGround() && (scaffold == null || !scaffold.isEnabled())) {
                // Prevent stuck when close to ground to avoid rubberband on landing/jumping
                if (!isBoxEmpty(mc.player.getBoundingBox().offset(0.0, -0.2, 0.0))) {
                    if (stuckEnabled) {
                        disableStuck();
                    }
                    return;
                }

                LivingEntity target = killAura.getCurrentTarget();
                if (target != null && mc.player.getVelocity().y < 0) { // Only trigger when falling
                    if (grimTimer > 0 || attackTimer > 0) { // Check attackTimer
                        if (attackTimer > 0 && stuckEnabled) {
                            disableStuck();
                        } else if (grimTimer > 0) {
                            grimTimer--;
                        }
                    } else {
                        if (stuckEnabled) {
                            disableStuck();
                            grimTimer = 1; // Short cooldown before re-enabling
                        } else {
                            enableStuck();
                            grimTimer = grimDelay.get();
                        }
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
    }

    private void enableStuck() {
        stuckEnabled = true;
        stage = 0;
        packet = null;
        lastYaw = Managers.ROTATION.rotations.yaw;
        lastPitch = Managers.ROTATION.rotations.pitch;
        tryDisable = false;
        packetCount = 0;
    }

    private void disableStuck() {
        if (stuckEnabled) {
            if (this.stage == 3) {
                stuckEnabled = false;
            } else {
                this.tryDisable = true;
            }
        } else {
            // Ensure packets are cleared if manually disabled
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
        packetCount = 0;
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (nullCheck()) return;
        if (!mode.is(Mode.Grim)) return;

        // Process disable logic even if stuckEnabled is false, to flush packets
        if (!stuckEnabled && !tryDisable) return;
        
        // If tryDisable is true, we need to flush one last time then actually disable
        if (!stuckEnabled && tryDisable) {
             // Let the logic below handle the flushing
        }

        Module scaffold = Sakura.MODULES.getModule(Scaffold.class);
        if (scaffold.isEnabled()) {
            // Don't interfere with scaffold
            if(stuckEnabled) disableStuck(); 
            return;
        }

        if (e.getType() == EventType.PRE) {
            if (stuckEnabled) {
                 mc.player.setVelocity(0.0, 0.0, 0.0);
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
        if (!mode.is(Mode.Grim) || !stuckEnabled) return;
        event.setForward(0.0F);
        event.setStrafe(0.0F);
        event.setJump(false);
        event.setSneak(false);
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
        
        // Always listen for PositionLook to unlock
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            while (!packets.isEmpty()) {
                PacketUtil.sendPacketNoEvent(packets.poll());
            }
            stage = 3;
            disableStuck();
            return;
        }

        // Detect if player is hurt (EntityStatus 2)
        if (event.getPacket() instanceof EntityStatusS2CPacket) {
            EntityStatusS2CPacket statusPacket = (EntityStatusS2CPacket) event.getPacket();
            if (statusPacket.getEntity(mc.world) == mc.player && statusPacket.getStatus() == 2) {
                attackTimer = 2; // Disable stuck for a few ticks to allow knockback
                disableStuck();
                return;
            }
        }

        if (!stuckEnabled) return;

        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            packetCount++;
            if (packetCount > maxPackets.get()) {
                disableStuck();
                return;
            }
            event.setCancelled(true);
        } else if (event.getPacket() instanceof CommonPongC2SPacket) {
            packets.offer((CommonPongC2SPacket) event.getPacket());
            event.setCancelled(true);
        } else if (event.getPacket() instanceof PlayerInteractItemC2SPacket || event.getPacket() instanceof PlayerActionC2SPacket) {
            packet = event.getPacket();
            stage = 1;
            event.setCancelled(true);
        }
    }

    // --- Stuck Logic End ---

    private void doPacketCriticals() {
        final Vec3d pos = mc.player.getEntityPos();
        final boolean ground = mc.player.isOnGround();
        final IClientPlayerEntity accessor = (IClientPlayerEntity) mc.player;

        mc.player.setPosition(pos.add(0.0D, 0.0625D, 0.0D));
        mc.player.setOnGround(false);
        accessor.invokeSendMovementPackets();

        mc.player.setPosition(pos.add(0.0D, 0.00125D, 0.0D));
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
