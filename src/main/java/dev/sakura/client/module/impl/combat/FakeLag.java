package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IExplosionS2CPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.Teams;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class FakeLag extends Module {

    private final NumberValue<Float> maxRange = new NumberValue<>("Max Range", "最大范围", 3.0f, 0.0f, 10.0f, 0.1f);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 300, 0, 1000, 10);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 600, 0, 1000, 10);
    private final NumberValue<Integer> recoilTime = new NumberValue<>("Recoil Time", "后坐力时间", 250, 0, 1000, 10);

    private enum Mode {
        Constant, Dynamic
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Dynamic);

    private final BoolValue flushEntityInteract = new BoolValue("EntityInteract", "实体交互", true);
    private final BoolValue flushBlockInteract = new BoolValue("BlockInteract", "方块交互", true);
    private final BoolValue flushAction = new BoolValue("Action", "动作", true);
    private final MultiBoolValue flushOn = new MultiBoolValue("Flush On", "刷新时机", Arrays.asList(flushEntityInteract, flushBlockInteract, flushAction));
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final ColorValue renderColor = new ColorValue("Render Color", "渲染颜色", new Color(255, 255, 255), render::get);

    private final BoolValue pulseFlush = new BoolValue("Pulse Flush", "脉冲释放", true);
    private final NumberValue<Integer> pulseSpeed = new NumberValue<>("Pulse Speed", "脉冲速度", 1, 1, 10, 1, pulseFlush::get);

    private final NumberValue<Integer> minPacketSize = new NumberValue<>("Min Packet Size", "最小包大小", 5, 1, 20, 1, pulseFlush::get);

    private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    private final TimerUtil chronometer = new TimerUtil();
    private long nextDelay = 0;
    private boolean isEnemyNearby = false;
    private Vec3d blinkedPos = null;
    private Vec3d renderPos = null;
    private boolean catchingUp = false;

    public FakeLag() {
        super("FakeLag", "假延迟", Category.Combat);
    }

    @Override
    public void onEnable() {
        packets.clear();
        chronometer.reset();
        nextDelay = getRandomDelay();
        isEnemyNearby = false;
        blinkedPos = null;
        renderPos = null;
    }

    @Override
    public void onDisable() {
        flush();
        isEnemyNearby = false;
        renderPos = null;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // Force initialize blinkedPos if null, to start distance calculation
        if (blinkedPos == null && !packets.isEmpty()) {
            // If we have packets but no blink pos, it means we haven't tracked yet.
            // But usually blinkPos is set when we add first packet.
            // Wait, if packets were added but blinkPos is null (e.g. non-move packets),
            // distance will be 0.
        }

        if (mode.is(Mode.Dynamic)) {
            if (pulseFlush.get()) {
                double currentDistance = 0;
                if (blinkedPos != null) {
                    currentDistance = mc.player.getEntityPos().distanceTo(blinkedPos);
                }

                float range = ((Number) maxRange.get()).floatValue();

                if (currentDistance > range) {
                    catchingUp = true;
                }

                if (catchingUp) {
                    pulse();
                    if (currentDistance > range * 1.5) {
                        pulse();
                    }
                    if (currentDistance < 0.5 || packets.isEmpty()) {
                        catchingUp = false;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        // Check conditions to bypass lag
        boolean scaffoldEnabled = Sakura.MODULES.getModule(Scaffold.class).isEnabled();
        boolean isAttacking = mc.options.attackKey.isPressed();
        if (scaffoldEnabled || isAttacking) {
            flush();
            return;
        }

        Packet<?> packet = event.getPacket();

        // Handle incoming packets for flush logic (S2C)
        if (event.getType() == EventType.RECEIVE) {
            if (packet instanceof PlayerPositionLookS2CPacket) {
                flush();
                chronometer.reset();
            } else if (packet instanceof ResourcePackSendS2CPacket) {
                flush();
                chronometer.reset();
            } else if (packet instanceof EntityVelocityUpdateS2CPacket) {
                EntityVelocityUpdateS2CPacket velocityPacket = (EntityVelocityUpdateS2CPacket) packet;
                if (velocityPacket.getEntityId() == mc.player.getId() && (velocityPacket.getVelocity().getX() != 0 || velocityPacket.getVelocity().getY() != 0 || velocityPacket.getVelocity().getZ() != 0)) {
                    flush();
                    chronometer.reset();
                }
            } else if (packet instanceof ExplosionS2CPacket) {
                IExplosionS2CPacket explosionPacket = (IExplosionS2CPacket) packet;
                Vec3d knockback = explosionPacket.getPlayerKnockback().orElse(Vec3d.ZERO);
                if (knockback != Vec3d.ZERO) {
                    flush();
                    chronometer.reset();
                }
            } else if (packet instanceof HealthUpdateS2CPacket) {
                flush();
                chronometer.reset();
            }
            return;
        }

        if (event.getType() == EventType.SEND) {
            if (PacketUtil.bypassPackets.contains(packet)) {
                return;
            }

            // TransactionOrder Bypass:
            // Grim checks order of transactions.
            // If we delay Pongs, we must delay them ALONG with moves to preserve order and relative time.
            // If we don't delay Pongs (pass through), we trigger Timer because movement burst exceeds time balance.
            // So we MUST queue Pongs.
            // BUT, we must ensure we don't drop them or send them out of order.
            // Our queue preserves order.

            // However, Grim also checks if we reply to a transaction too fast? No.
            // Too slow? Yes, if lag > 30s.

            // What about "BadPacketsN"?
            // It might trigger if we send certain packets while choking?

            // AimModulo360:
            // "if (player.xRot < 360 && player.xRot > -360 && Math.abs(rotationUpdate.getDeltaXRot()) > 320 && Math.abs(lastDeltaYaw) < 30)"
            // This detects 360-wrap.
            // FakeLag doesn't cause this directly unless we desync rotation.
            // We intercept all packets, so rotation should be consistent.

            if (packet instanceof RequestCommandCompletionsC2SPacket) {
                return;
            }

            if (mc.player.isDead() || mc.player.isTouchingWater() || mc.currentScreen != null) {
                return;
            }

            // Recoil time check
            if (!chronometer.passedMillise(recoilTime.get())) {
                return;
            }

            // Time delay check
            // If Pulse Flush is enabled, we IGNORE time delay and rely on distance pulse.
            // If Pulse Flush is disabled, we rely on time delay (random delay).
            if (!(pulseFlush.get() && mode.is(Mode.Dynamic))) {
                if (chronometer.passedMillise(nextDelay)) {
                    nextDelay = getRandomDelay();
                    flush();
                    return;
                }
            }

            // FlushOn checks
            if (packet instanceof PlayerInteractEntityC2SPacket || packet instanceof HandSwingC2SPacket) {
                if (flushEntityInteract.get()) {
                    flush();
                    chronometer.reset();
                    return;
                }
            }
            if (packet instanceof PlayerInteractBlockC2SPacket || packet instanceof UpdateSignC2SPacket || packet instanceof PlayerInteractItemC2SPacket) {
                if (flushBlockInteract.get()) {
                    flush();
                    chronometer.reset();
                    return;
                }
            }
            if (packet instanceof PlayerActionC2SPacket) {
                if (flushAction.get()) {
                    flush();
                    chronometer.reset();
                    return;
                }
            }

            // Consumable check
            if (mc.player.isUsingItem() && (mc.player.getActiveItem().contains(DataComponentTypes.FOOD) || mc.player.getActiveItem().getItem().toString().contains("potion") || mc.player.getActiveItem().getItem().toString().contains("milk"))) {
                return;
            }

            // Main Lag Logic
            if (mode.is(Mode.Constant)) {
                if (blinkedPos == null) {
                    blinkedPos = mc.player.getEntityPos();
                }
                event.setCancelled(true);
                packets.add(packet);
            } else if (mode.is(Mode.Dynamic)) {
                // Always intercept!
                if (blinkedPos == null) {
                    blinkedPos = mc.player.getEntityPos();
                }

                // Fix for BadPackets/TransactionOrder:
                // Limit the maximum queue size strictly to prevent huge lags that trigger Grim.
                // Grim usually flags if lag > 400ms-1s depending on config.
                // 3 blocks range might be ~600-800ms depending on speed.

                // Also, we must ensure we don't duplicate packets or send invalid combos.

                event.setCancelled(true);
                packets.add(packet);

                // Safety flush if queue is too big (anti-kick)
                if (packets.size() > 100) {
                    flush();
                }
            }
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (!render.get()) return;

        // Use current player position as target if no blinkedPos
        Vec3d targetPos = blinkedPos != null ? blinkedPos : mc.player.getEntityPos();

        // Initialize renderPos if null
        if (renderPos == null) {
            renderPos = targetPos;
        }

        // Interpolate renderPos towards targetPos for smooth movement
        // Using a simple lerp: current + (target - current) * speed * delta
        // To make it frame-rate independent, we should use delta time, but for simplicity here we use fixed factor
        double speed = 0.3; // Adjust for smoothness (0.1 = very slow, 1.0 = instant)
        renderPos = renderPos.add(targetPos.subtract(renderPos).multiply(speed));

        double width = mc.player.getWidth();
        double height = mc.player.getHeight();

        Box box = new Box(
                renderPos.x - width / 2.0, renderPos.y, renderPos.z - width / 2.0,
                renderPos.x + width / 2.0, renderPos.y + height, renderPos.z + width / 2.0
        );

        Color color = renderColor.get();
        Color filledColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        Render3DUtil.drawFilledBox(event.getMatrices(), box, filledColor);
        Render3DUtil.drawOutlineBox(event.getMatrices(), box, color.getRGB(), 2.0f);
    }

    private void flush() {
        while (!packets.isEmpty()) {
            PacketUtil.sendPacketNoEvent(packets.poll());
            if (blinkedPos != null && !packets.isEmpty()) {
                // Approximate new blinkedPos for smooth visual
                // This is a bit hacky, ideally we'd track position per packet
                // But for visual feedback, we can just clear it when fully flushed
                // or maybe interpolate? For now, keep it simple.
            }
        }
        blinkedPos = null;
    }

    private void pulse() {
        if (packets.isEmpty()) return;

        int speed = pulseSpeed.get();
        for (int i = 0; i < speed; i++) {
            if (packets.isEmpty()) break;

            Packet<?> packet = packets.poll();
            if (packet != null) {
                PacketUtil.sendPacketNoEvent(packet);
                if (packet instanceof PlayerMoveC2SPacket) {
                    PlayerMoveC2SPacket movePacket = (PlayerMoveC2SPacket) packet;
                    if (movePacket.changesPosition()) {
                        blinkedPos = new Vec3d(movePacket.getX(blinkedPos.x), movePacket.getY(blinkedPos.y), movePacket.getZ(blinkedPos.z));
                    }
                }
            }
        }

        if (packets.isEmpty()) {
            blinkedPos = null;
        }
    }

    private long getRandomDelay() {
        int min = minDelay.get();
        int max = maxDelay.get();
        if (min >= max) return min;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private Entity findEnemy(float range) {
        return mc.world.getEntitiesByClass(LivingEntity.class, mc.player.getBoundingBox().expand(range),
                e -> e != mc.player && e.isAlive() && mc.player.distanceTo(e) <= range && !AntiBot.isBot(e) && !Teams.isSameTeam(e)).stream().findFirst().orElse(null);
    }
}
