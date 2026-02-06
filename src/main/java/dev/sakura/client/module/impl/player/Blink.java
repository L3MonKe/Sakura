package dev.sakura.client.module.impl.player;

import com.mojang.authlib.GameProfile;
import dev.sakura.client.events.client.GameJoinEvent;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.StreamSupport;

public class Blink extends Module {
    public Blink() {
        super("Blink", "瞬移", Category.Player);
    }

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final NumberValue<Double> releaseOnDamage = new NumberValue<>("ReleaseOnDamage", "受伤释放刻数", 20.0, 0.0, 50.0, 1.0);
    private final NumberValue<Double> releaseSpeed = new NumberValue<>("ReleaseSpeed", "每刻释放速度", 10.0, 3.0, 20.0, 1.0);
    private final NumberValue<Double> maxTicks = new NumberValue<>("MaxTicks", "最大缓存刻数", 20.0, 10.0, 200.0, 1.0);
    private final NumberValue<Double> playerDistance = new NumberValue<>("PlayerDistance", "玩家危险距离", 4.0, 3.0, 10.0, 0.1);
    private final NumberValue<Double> tntDistance = new NumberValue<>("TNTDistance", "TNT危险距离", 5.0, 3.0, 10.0, 0.1);
    private final NumberValue<Double> projectilesExpands = new NumberValue<>("FakePlayerHitBox", "假人弹射体判定膨胀", 0.2, 0.0, 3.0, 0.01);

    private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    private OtherClientPlayerEntity fakePlayer;
    private boolean disabling = false;
    private int shouldReleaseTicks = 0;
    private int releasedTicks = 0;

    public long getBlinkTicks() {
        return packets.stream().filter(packet -> packet instanceof PlayerMoveC2SPacket).count();
    }

    private void handleMove(PlayerMoveC2SPacket packet) {
        if (fakePlayer == null) return;
        double x = packet.getX(mc.player.getX());
        double y = packet.getY(mc.player.getY());
        double z = packet.getZ(mc.player.getZ());
        float yaw = packet.getYaw(mc.player.getYaw());
        float pitch = packet.getPitch(mc.player.getPitch());

        fakePlayer.updatePositionAndAngles(x, y, z, yaw, pitch);
        fakePlayer.lastX = x;
        fakePlayer.lastY = y;
        fakePlayer.lastZ = z;
        fakePlayer.setHeadYaw(yaw);
        fakePlayer.setBodyYaw(yaw);
    }

    private void releaseTick() {
        while (!packets.isEmpty()) {
            Packet<?> poll = packets.poll();
            if (poll == null) continue;
            mc.getNetworkHandler().sendPacket(poll);
            if (poll instanceof PlayerMoveC2SPacket move) {
                releasedTicks++;
                handleMove(move);
                break;
            }
        }
    }

    @Override
    protected void onEnable() {
        packets.clear();
        shouldReleaseTicks = 0;
        disabling = false;
        if (nullCheck()) {
            setState(false);
            return;
        }
        if (!render.get()) return;
        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("11451466-6666-6666-6666-666666666601"), mc.player.getGameProfile().name()));
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        fakePlayer.getInventory().clone(mc.player.getInventory());
        fakePlayer.setSprinting(mc.player.isSprinting());
        mc.world.addEntity(fakePlayer);
    }

    @Override
    protected void onDisable() {
        if (fakePlayer != null && mc.world != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }

    private boolean isPlayerNear(double distance) {
        if (mc.world == null || mc.player == null || fakePlayer == null) return false;
        return mc.world.getPlayers().stream().anyMatch(player -> {
            if (player == mc.player) return false;
            if (player == fakePlayer) return false;
            if (!player.isAlive()) return false;
            double d = player.distanceTo(fakePlayer);
            return d < distance;
        });
    }

    private boolean isTNTNear(double distance) {
        long tnt = StreamSupport.stream(mc.world.getEntities().spliterator(), true).filter(entity -> entity instanceof TntEntity && (double) this.fakePlayer.distanceTo(entity) <= distance).count();
        return tnt > 0L;
    }

    private boolean isArrowNear(double expands) {
        if (mc.world == null || mc.player == null || fakePlayer == null) return false;
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof ArrowEntity || entity instanceof EggEntity || entity instanceof SnowballEntity)) {
                continue;
            }
            if (checkProjectile(entity, expands)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkProjectile(Entity entity, double expands) {
        if (mc.world == null || fakePlayer == null) return false;
        double posX = entity.getX();
        double posY = entity.getY();
        double posZ = entity.getZ();
        double motionX = entity.getVelocity().x;
        double motionY = entity.getVelocity().y;
        double motionZ = entity.getVelocity().z;

        while (true) {
            float radius = 0.25f;
            float height = 0.25f;
            Box box = new Box(posX - radius, posY, posZ - radius, posX + radius, posY + height, posZ + radius);
            Vec3d start = new Vec3d(posX, posY, posZ);
            Vec3d end = start.add(motionX, motionY, motionZ);
            if (!mc.world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity)).getType().equals(HitResult.Type.MISS) || posY < -128.0) {
                return false;
            }

            Box expandedBox = box.stretch(motionX, motionY, motionZ).expand(expands, expands, expands);
            if (expandedBox.intersects(fakePlayer.getBoundingBox())) {
                return true;
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;

            double drag = entity.isTouchingWater() ? 0.8 : 0.99;
            motionX *= drag;
            motionZ *= drag;
            motionY = motionY * drag - 0.05;
        }
    }

    private boolean isPlayerInDanger() {
        return isTNTNear(tntDistance.get()) || isPlayerNear(playerDistance.get()) || isArrowNear(projectilesExpands.get());
    }

    @Override
    public void setState(boolean state) {
        if (mc.player != null) {
            if (state) {
                super.setState(true);
            } else if (!disabling) {
                disabling = true;
            } else if (packets.isEmpty()) {
                super.setState(false);
            }
        } else {
            super.setState(state);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.player.isDead()) {
            packets.clear();
            setState(false);
            return;
        }

        setSuffix(getBlinkTicks() + " Ticks Behind");
        releasedTicks = 0;

        if (mc.player.hurtTime == 10) {
            shouldReleaseTicks += releaseOnDamage.get().intValue();
        }

        while (releasedTicks < releaseSpeed.get() && shouldReleaseTicks > 0 && !packets.isEmpty()) {
            releaseTick();
            shouldReleaseTicks--;
        }

        while (releasedTicks < releaseSpeed.get() && isPlayerInDanger() && !packets.isEmpty()) {
            releaseTick();
        }

        while (releasedTicks < releaseSpeed.get()
                && getBlinkTicks() >= maxTicks.get()
                && !packets.isEmpty()) {
            releaseTick();
        }

        if (disabling) {
            while (releasedTicks < releaseSpeed.get() && !packets.isEmpty()) {
                releaseTick();
            }
            if (packets.isEmpty()) {
                setState(false);
            }
        }
    }

    @EventHandler
    private void onGameJoin(GameJoinEvent event) {
        if (isEnabled()) {
            packets.clear();
            setState(false);
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (event.getType() != EventType.SEND || nullCheck() || event.isCancelled()) return;

        Packet<?> packet = event.getPacket();
        if (packet instanceof ChatMessageC2SPacket
                || packet instanceof RequestCommandCompletionsC2SPacket
                || packet instanceof CommandExecutionC2SPacket
                || packet instanceof TeleportConfirmC2SPacket
                || packet instanceof KeepAliveC2SPacket
                || packet instanceof AdvancementTabC2SPacket
                || packet instanceof ClientStatusC2SPacket
                || packet instanceof ClickSlotC2SPacket
                || packet instanceof CommonPongC2SPacket) {
            return;
        }

        event.setCancelled(true);
        packets.offer(packet);
    }
}
