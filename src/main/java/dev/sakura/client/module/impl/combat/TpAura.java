package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.player.GrimDisabler;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.path.AStarPathfinder;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TpAura extends Module {
    public enum Mode {
        AStar,
        Immediate
    }

    private final NumberValue<Double> attackRange = new NumberValue<>("Attack Range", "攻击范围", 4.2, 3.0, 5.0, 0.1);
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.AStar);

    private final NumberValue<Double> minCps = new NumberValue<>("Min CPS", "最小CPS", 5.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> maxCps = new NumberValue<>("Max CPS", "最大CPS", 8.0, 1.0, 20.0, 1.0);

    private final NumberValue<Integer> maxDistance = new NumberValue<>("Maximum Distance", "最大距离", 95, 50, 250, 1, () -> mode.is(Mode.AStar));
    private final NumberValue<Integer> maxCost = new NumberValue<>("Maximum Cost", "最大代价", 250, 50, 500, 1, () -> mode.is(Mode.AStar));
    private final NumberValue<Integer> tickDistance = new NumberValue<>("Tick Distance", "分段距离", 3, 1, 7, 1, () -> mode.is(Mode.AStar));
    private final BoolValue allowDiagonal = new BoolValue("Allow Diagonal", "允许对角", false, () -> mode.is(Mode.AStar));
    private final BoolValue tpBack = new BoolValue("Tp Back", "传回原位", true, () -> mode.is(Mode.AStar));
    private final NumberValue<Integer> stickTicks = new NumberValue<>("Stick", "停留Tick", 5, 1, 10, 1, () -> mode.is(Mode.AStar));
    private final BoolValue autoDisable = new BoolValue("Auto Disable", "自动关闭", true);

    private final BoolValue renderPath = new BoolValue("Render Path", "渲染路径", true);
    private final BoolValue renderDesync = new BoolValue("Render Desync", "渲染假位置", true);

    private Vec3d desyncPlayerPosition;
    private long lastStuckTime;

    private LivingEntity currentTarget;

    private long nextClickTimeMs;

    private ImmediateState immediateState = ImmediateState.IDLE;
    private Vec3d immediateReturnPos;
    private int immediateWaitTicks;

    private AStarState aStarState = AStarState.IDLE;
    private BlockPos aStarStart;
    private List<BlockPos> aStarPath;
    private int aStarWaitTicks;

    private final Random random = new Random();

    public TpAura() {
        super("TpAura", "TP光环", Category.Combat);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @Override
    protected void onEnable() {
        resetRuntime();
    }

    @Override
    protected void onDisable() {
        resetRuntime();
    }

    private void resetRuntime() {
        desyncPlayerPosition = null;
        currentTarget = null;
        lastStuckTime = 0L;
        nextClickTimeMs = 0L;

        immediateState = ImmediateState.IDLE;
        immediateReturnPos = null;
        immediateWaitTicks = 0;

        aStarState = AStarState.IDLE;
        aStarStart = null;
        aStarPath = null;
        aStarWaitTicks = 0;
    }

    private void resetSequenceKeepPosition() {
        currentTarget = null;
        immediateState = ImmediateState.IDLE;
        immediateReturnPos = null;
        immediateWaitTicks = 0;

        aStarState = AStarState.IDLE;
        aStarWaitTicks = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (autoDisable.get() && (mc.player.isDead() || mc.player.getHealth() <= 0)) {
            toggle();
            ChatUtil.clientMessage("§cTpAura disabled due to death.");
            return;
        }

        boolean clickTick = isClickTick();
        if (desyncPlayerPosition != null && clickTick) {
            tryAttack();
        }

        if (mode.is(Mode.Immediate)) {
            tickImmediate(clickTick);
        } else {
            tickAStar(clickTick);
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) {
            return;
        }

        if (event.getType() == EventType.SEND) {
            if (desyncPlayerPosition != null && event.getPacket() instanceof PlayerMoveC2SPacket movePacket) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                accessor.setX(desyncPlayerPosition.x);
                accessor.setY(desyncPlayerPosition.y);
                accessor.setZ(desyncPlayerPosition.z);
                accessor.setChangePosition(true);
            }
            return;
        }

        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
            if (Sakura.MODULES.getModule(GrimDisabler.class).isEnabled()) {
                // GrimDisabler handles the S08 packet and fakes the position.
                // We should sync our desync position to what the server expects (which is what GrimDisabler set us to).
                double x = packet.change().position().x;
                double y = packet.change().position().y;
                double z = packet.change().position().z;

                if (packet.relatives().contains(PositionFlag.X)) x += mc.player.getX();
                if (packet.relatives().contains(PositionFlag.Y)) y += mc.player.getY();
                if (packet.relatives().contains(PositionFlag.Z)) z += mc.player.getZ();

                desyncPlayerPosition = new Vec3d(x, y, z);

                // Don't reset runtime, just sync position and continue
                return;
            }

            if (desyncPlayerPosition != null) {
                lastStuckTime = System.currentTimeMillis();
                ChatUtil.clientMessage("§cTpAura 传送失败: " + formatVec(desyncPlayerPosition) + " (ID=" + packet.teleportId() + ")");
                resetRuntime();
            }
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (nullCheck()) {
            return;
        }

        if (renderPath.get() && aStarPath != null && !aStarPath.isEmpty()) {
            for (int i = 0; i < aStarPath.size() - 1; i++) {
                Vec3d a = toPlayerPos(aStarPath.get(i));
                Vec3d b = toPlayerPos(aStarPath.get(i + 1));
                dev.sakura.client.utils.render.Render3DUtil.drawLine(event.getMatrices(), a, b, new Color(120, 190, 255, 180), 2f);
            }
        }

        if (renderDesync.get() && desyncPlayerPosition != null) {
            Box box = buildPlayerBox(desyncPlayerPosition);
            Render3DUtil.drawOutlineBox(event.getMatrices(), box, new Color(255, 255, 255, 200).getRGB(), 2f);
            dev.sakura.client.utils.render.Render3DUtil.drawFilledBox(event.getMatrices(), box, new Color(255, 255, 255, 30).getRGB());
        }

        if (mode.is(Mode.Immediate) && desyncPlayerPosition != null) {
            Vec3d start = mc.player.getEntityPos();
            Vec3d end = desyncPlayerPosition;
            dev.sakura.client.utils.render.Render3DUtil.drawLine(event.getMatrices(), start, end, new Color(255, 255, 255, 180), 2f);
        }
    }

    private void tickImmediate(boolean clickTick) {
        if (immediateState == ImmediateState.IDLE) {
            if (!clickTick) {
                return;
            }
            LivingEntity target = findBestTarget(mc.player.getEntityPos(), Double.MAX_VALUE);
            if (target == null) {
                return;
            }

            immediateReturnPos = mc.player.getEntityPos();
            Vec3d enemyPos = target.getEntityPos();

            travelImmediate(enemyPos);
            desyncPlayerPosition = enemyPos;
            currentTarget = target;

            immediateWaitTicks = 20;
            immediateState = ImmediateState.WAITING;
            return;
        }

        if (immediateState == ImmediateState.WAITING) {
            immediateWaitTicks--;
            if (immediateWaitTicks <= 0) {
                immediateState = ImmediateState.RETURNING;
            }
            return;
        }

        if (immediateState == ImmediateState.RETURNING) {
            if (immediateReturnPos != null) {
                travelImmediate(immediateReturnPos);
            }
            resetRuntime();
        }
    }

    private void tickAStar(boolean clickTick) {
        if (aStarState != AStarState.IDLE) {
            if (aStarState == AStarState.WAITING) {
                aStarWaitTicks--;
                if (aStarWaitTicks <= 0) {
                    if (tpBack.get() && aStarPath != null && !aStarPath.isEmpty()) {
                        travelAStar(reversePath(aStarPath));
                        resetRuntime();
                        return;
                    } else if (aStarPath != null && !aStarPath.isEmpty()) {
                        BlockPos last = aStarPath.get(aStarPath.size() - 1).toImmutable();
                        aStarStart = last;
                        desyncPlayerPosition = toPlayerPos(last);
                    }
                    resetSequenceKeepPosition();
                }
            }
            return;
        }

        if (!clickTick) {
            return;
        }

        BlockPos start = aStarStart != null ? aStarStart : (desyncPlayerPosition != null ? BlockPos.ofFloored(desyncPlayerPosition) : mc.player.getBlockPos());
        LivingEntity target = findBestTarget(toPlayerPos(start), maxDistance.get());
        if (target == null) {
            return;
        }

        BlockPos end = target.getBlockPos();
        List<BlockPos> path = AStarPathfinder.findPath(mc.world, mc.player, start, end, maxCost.get(), allowDiagonal.get());
        if (path.isEmpty()) {
            return;
        }

        currentTarget = target;
        aStarPath = path;
        travelAStar(path);

        BlockPos last = path.get(path.size() - 1);
        desyncPlayerPosition = toPlayerPos(last);
        aStarStart = last.toImmutable();
        aStarWaitTicks = stickTicks.get();
        aStarState = AStarState.WAITING;
    }

    private LivingEntity findBestTarget(Vec3d from, double maxDistance) {
        List<LivingEntity> candidates = Managers.COMBAT.getEntities(maxDistance);
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        return candidates.stream()
                .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(from)))
                .orElse(null);
    }

    private void tryAttack() {
        if (currentTarget == null || !currentTarget.isAlive()) {
            return;
        }

        Vec3d serverPos = desyncPlayerPosition == null ? mc.player.getEntityPos() : desyncPlayerPosition;
        double range = attackRange.get();
        double distSq = squaredBoxDistanceToPoint(currentTarget.getBoundingBox(), serverPos);
        if (distSq > range * range) {
            return;
        }

        mc.interactionManager.attackEntity(mc.player, currentTarget);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean isClickTick() {
        long now = System.currentTimeMillis();
        if (now < nextClickTimeMs) {
            return false;
        }
        double min = Math.max(0.1, Math.min(minCps.get(), maxCps.get()));
        double max = Math.max(min, maxCps.get());
        double cps = min + (Math.random() * (max - min));
        long delay = (long) (1000.0 / cps);
        long jitter = (long) ((Math.random() - 0.5) * delay * 0.4);
        nextClickTimeMs = now + Math.max(1L, delay + jitter);
        return true;
    }

    private void travelImmediate(Vec3d position) {
        Vec3d playerPos = mc.player.getEntityPos();
        int times = (int) ((Math.abs(playerPos.x - position.x) + Math.abs(playerPos.y - position.y) + Math.abs(playerPos.z - position.z)) / 10.0);
        for (int i = 0; i < times; i++) {
            PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(
                    mc.player.getX(), mc.player.getY(), mc.player.getZ(),
                    getRandomizedYaw(), getRandomizedPitch(),
                    mc.player.isOnGround(), mc.player.horizontalCollision
            ));
        }
        PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(
                position.x, position.y, position.z,
                getRandomizedYaw(), getRandomizedPitch(),
                mc.player.isOnGround(), mc.player.horizontalCollision
        ));
    }

    private void travelAStar(List<BlockPos> path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        Vec3d startPos = desyncPlayerPosition != null ? desyncPlayerPosition : mc.player.getEntityPos();
        List<Vec3d> points = new ArrayList<>(path.size() + 1);
        points.add(startPos);
        for (BlockPos p : path) {
            points.add(toPlayerPos(p));
        }

        int step = Math.max(1, tickDistance.get());
        for (int i = 1; i < points.size(); i += step) {
            int endIndex = Math.min(points.size() - 1, i + step - 1);
            Vec3d end = points.get(endIndex);
            Vec3d start = points.get(i - 1);

            if (hasCollisionBetween(start, end)) {
                for (int j = i; j <= endIndex; j++) {
                    Vec3d p = points.get(j);
                    PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(
                            p.x, p.y, p.z, mc.player.isOnGround(), mc.player.horizontalCollision
                    ));
                    desyncPlayerPosition = p;
                }
            } else {
                PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(
                        end.x, end.y, end.z, mc.player.isOnGround(), mc.player.horizontalCollision
                ));
                desyncPlayerPosition = end;
            }
        }
    }

    private float getRandomizedYaw() {
        float yaw = mc.player.getYaw();
        // Add small random offset to break perfect angles if they are integers
        if (Math.abs(yaw % 90) < 0.0001) {
            yaw += (float) (random.nextDouble() * 0.0002 - 0.0001);
        }
        return yaw;
    }

    private float getRandomizedPitch() {
        // Pitch usually doesn't have the 90 degree check as strictly, but good to keep consistent
        return mc.player.getPitch();
    }

    private boolean hasCollisionBetween(Vec3d start, Vec3d end) {
        Box box = buildTravelBox(start, end);
        return mc.world.getBlockCollisions(mc.player, box).iterator().hasNext();
    }

    private static Box buildTravelBox(Vec3d start, Vec3d end) {
        double minX = Math.min(start.x, end.x) - 0.3;
        double minY = Math.min(start.y, end.y);
        double minZ = Math.min(start.z, end.z) - 0.3;
        double maxX = Math.max(start.x, end.x) + 0.3;
        double maxY = Math.max(start.y, end.y) + 1.8;
        double maxZ = Math.max(start.z, end.z) + 0.3;
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private Box buildPlayerBox(Vec3d pos) {
        Box bb = mc.player.getBoundingBox();
        double w = bb.getLengthX();
        double h = bb.getLengthY();
        double half = w / 2.0;
        return new Box(pos.x - half, pos.y, pos.z - half, pos.x + half, pos.y + h, pos.z + half);
    }

    private static double squaredBoxDistanceToPoint(Box box, Vec3d point) {
        double dx = 0.0;
        if (point.x < box.minX) dx = box.minX - point.x;
        else if (point.x > box.maxX) dx = point.x - box.maxX;

        double dy = 0.0;
        if (point.y < box.minY) dy = box.minY - point.y;
        else if (point.y > box.maxY) dy = point.y - box.maxY;

        double dz = 0.0;
        if (point.z < box.minZ) dz = box.minZ - point.z;
        else if (point.z > box.maxZ) dz = point.z - box.maxZ;

        return dx * dx + dy * dy + dz * dz;
    }

    private static Vec3d toPlayerPos(BlockPos pos) {
        return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    private static List<BlockPos> reversePath(List<BlockPos> path) {
        ArrayList<BlockPos> reversed = new ArrayList<>(path);
        java.util.Collections.reverse(reversed);
        return reversed;
    }

    private static String formatVec(Vec3d v) {
        return String.format("%.2f, %.2f, %.2f", v.x, v.y, v.z);
    }

    private enum ImmediateState {
        IDLE,
        WAITING,
        RETURNING
    }

    private enum AStarState {
        IDLE,
        WAITING
    }
}
