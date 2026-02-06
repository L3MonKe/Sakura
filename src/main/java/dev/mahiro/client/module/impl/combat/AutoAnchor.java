package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.entity.EntityUtil;
import dev.mahiro.client.utils.player.EatingUtil;
import dev.mahiro.client.utils.player.FindItemResult;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RaytraceUtil;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.utils.world.BlockUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AutoAnchor extends Module {
    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> targetRange = new NumberValue<>("Target Range", "目标范围", 10.0, 1.0, 20.0, 0.5);
    private final NumberValue<Double> wallRange = new NumberValue<>("Wall Range", "穿墙范围", 3.0, 0.0, 6.0, 0.1);
    private final NumberValue<Double> minCPS = new NumberValue<>("Min CPS", "最小CPS", 8.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> maxCPS = new NumberValue<>("Max CPS", "最大CPS", 12.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotate Speed", "旋转速度", 1.5, 0.1, 5.0, 0.1);
    private final NumberValue<Double> angleTolerance = new NumberValue<>("Angle Tolerance", "角度容差", 20.0, 1.0, 90.0, 1.0);
    private final BoolValue autoSwitch = new BoolValue("Auto Switch", "自动切换", true);
    private final BoolValue strict = new BoolValue("Strict", "严格模式", true);
    private final BoolValue jitter = new BoolValue("Jitter", "抖动模式", true);

    private Entity target;
    private BlockPos currentAnchorPos;
    private BlockPos currentSafetyPos;
    private Stage stage = Stage.Searching;
    private int chargeCount;
    private int chargedTimes;

    private long lastActionTime;
    private long actionDelay;

    private enum Stage {
        Searching,
        Charging,
        PlacingSafety,
        Exploding
    }

    public AutoAnchor() {
        super("AutoAnchor", "自动锚", Category.Combat);
    }

    @Override
    public void onEnable() {
        target = null;
        reset();
    }

    public void reset() {
        currentAnchorPos = null;
        currentSafetyPos = null;
        stage = Stage.Searching;
        chargedTimes = 0;
        resetTimer();
        determineChargeCount();
    }

    private void resetTimer() {
        lastActionTime = System.currentTimeMillis();
        double range = maxCPS.get().doubleValue() - minCPS.get().doubleValue();
        double cps = minCPS.get().doubleValue() + range * Math.random();
        actionDelay = (long) (1000.0 / cps);
    }

    private void determineChargeCount() {
        double random = Math.random();
        if (random < 0.8) {
            chargeCount = 1;
        } else if (random < 0.95) {
            chargeCount = 2;
        } else {
            chargeCount = 3;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 如果正在吃东西/喝药/使用物品，暂停一切动作
        if (EatingUtil.isEating()) {
            reset(); // 重置状态，避免下次开始时状态错乱
            return;
        }

        target = EntityUtil.getClosestPlayer(targetRange.get().doubleValue());
        if (target == null) {
            reset();
            return;
        }

        // 高度检查：必须在同高度或者相差不大
        if (Math.abs(target.getY() - mc.player.getY()) > 1.5) {
            reset();
            return;
        }

        if (System.currentTimeMillis() - lastActionTime < actionDelay) return;

        switch (stage) {
            case Searching -> findPlacement();
            case Charging -> chargeAnchor();
            case PlacingSafety -> placeSafety();
            case Exploding -> explodeAnchor();
        }
    }

    private void findPlacement() {
        BlockPos pPos = mc.player.getBlockPos();
        // 扫描范围内已有的重生锚
        BlockPos bestPos = null;
        BlockPos bestSafety = null;
        double bestDistToTarget = Double.MAX_VALUE;

        int r = (int) Math.ceil(range.get().doubleValue());
        double rangeSq = range.get().doubleValue() * range.get().doubleValue();

        // 扫描周围方块寻找重生锚
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = pPos.add(x, y, z);

                    // 必须是重生锚
                    if (mc.world.getBlockState(pos).getBlock() != Blocks.RESPAWN_ANCHOR) continue;

                    // 距离检测
                    if (mc.player.squaredDistanceTo(Vec3d.ofCenter(pos)) > rangeSq) continue;

                    // 墙体检测
                    net.minecraft.util.hit.BlockHitResult hit = RaytraceUtil.rayTraceCollidingBlocks(mc.player.getEyePos(), Vec3d.ofCenter(pos));
                    boolean canSee = hit != null && hit.getBlockPos().equals(pos);

                    if (strict.get()) {
                        if (!canSee) continue;
                    } else {
                        if (!canSee && mc.player.squaredDistanceTo(Vec3d.ofCenter(pos)) > wallRange.get().doubleValue() * wallRange.get().doubleValue()) {
                            continue;
                        }
                    }

                    BlockPos safety = findSafetyPos(pos);
                    // safety can be null if no space, but we still might want to explode it
                    // But user specifically asked for safety. Let's try to find one.

                    double distToTarget = target.squaredDistanceTo(Vec3d.ofCenter(pos));
                    if (distToTarget < bestDistToTarget) {
                        bestDistToTarget = distToTarget;
                        bestPos = pos;
                        bestSafety = safety;
                    }
                }
            }
        }

        if (bestPos != null) {
            currentAnchorPos = bestPos;
            currentSafetyPos = bestSafety;

            // Check charges
            try {
                int charges = mc.world.getBlockState(bestPos).get(RespawnAnchorBlock.CHARGES);
                if (charges > 0) {
                    stage = Stage.PlacingSafety; // Already charged, go to safety
                } else {
                    stage = Stage.Charging;
                }
            } catch (Exception e) {
                stage = Stage.Charging;
            }
        }
    }

    private BlockPos findSafetyPos(BlockPos anchorPos) {
        // 寻找一个在玩家和锚之间的位置放置萤石
        // 我们取从锚指向玩家的方向
        BlockPos pPos = mc.player.getBlockPos();

        // 简单的向量计算：
        // 目标位置 = 锚位置 + (玩家位置 - 锚位置) 的单位方向
        // 但是由于是格子世界，我们需要找到相邻的方块

        BlockPos best = null;
        double minDst = Double.MAX_VALUE;

        for (Direction dir : Direction.values()) {
            if (dir == Direction.UP || dir == Direction.DOWN) continue;

            BlockPos pos = anchorPos.offset(dir);
            if (!BlockUtil.canPlaceAt(pos)) continue;

            // 距离检测：必须离玩家更近（相比于锚）
            double distToPlayer = mc.player.squaredDistanceTo(Vec3d.ofCenter(pos));
            double anchorDistToPlayer = mc.player.squaredDistanceTo(Vec3d.ofCenter(anchorPos));

            if (distToPlayer < anchorDistToPlayer && distToPlayer < minDst) {
                minDst = distToPlayer;
                best = pos;
            }
        }
        return best;
    }

    // Removed placeAnchor method

    private void placeSafety() {
        if (currentSafetyPos == null) {
            stage = Stage.Exploding;
            return;
        }

        if (mc.world.getBlockState(currentSafetyPos).getBlock() == Blocks.GLOWSTONE ||
                mc.world.getBlockState(currentSafetyPos).getBlock() == Blocks.OBSIDIAN) {
            stage = Stage.Exploding; // After safety, go to explode (charging is done first now)
            return;
        }

        FindItemResult glowstone = InvUtil.findInHotbar(Items.GLOWSTONE);
        if (!glowstone.found()) {
            stage = Stage.Exploding; // No glowstone to place safety, try to explode anyway? 
            // Or maybe reset? Assuming player wants safety. But if no glowstone, we can't place safety.
            // But we need glowstone to charge too. If we are here, we might have charged already.
            // If we have no glowstone now, we can't place safety.
            // Let's just try to explode.
            return;
        }

        Direction side = getPlaceSideIfReady(currentSafetyPos);
        if (side != null) {
            boolean action = executeAction(() -> {
                BlockUtil.clickBlock(currentSafetyPos.offset(side), side.getOpposite(), false, false);
                mc.player.swingHand(Hand.MAIN_HAND);
                resetTimer();
            }, glowstone.slot(), autoSwitch.get());

            if (action) {
                stage = Stage.Exploding;
            }
        }
    }

    private void chargeAnchor() {
        // Check charges first
        try {
            if (mc.world.getBlockState(currentAnchorPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
                reset();
                return;
            }
            int charges = mc.world.getBlockState(currentAnchorPos).get(RespawnAnchorBlock.CHARGES);
            if (charges >= chargeCount) {
                stage = Stage.PlacingSafety; // Charged enough, go to safety
                return;
            }
        } catch (Exception e) {
            // Ignore
        }

        FindItemResult glowstone = InvUtil.findInHotbar(Items.GLOWSTONE);
        if (!glowstone.found()) {
            reset();
            return;
        }

        if (interactBlock(currentAnchorPos)) {
            boolean action = executeAction(() -> {
                BlockUtil.clickBlock(currentAnchorPos, Direction.UP, false, false);
                mc.player.swingHand(Hand.MAIN_HAND);
                resetTimer();
            }, glowstone.slot(), autoSwitch.get());

            if (action) {
                chargedTimes++;
                // Check state again next tick or assume it worked
                // We rely on block state check at top of method next tick
            }
        }
    }

    private void explodeAnchor() {
        // Need to switch to non-glowstone/anchor item
        int slot = -1;
        if (mc.player.getMainHandStack().getItem() == Items.GLOWSTONE || mc.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) {
            FindItemResult other = InvUtil.findInHotbar(itemStack -> itemStack.getItem() != Items.GLOWSTONE && itemStack.getItem() != Items.RESPAWN_ANCHOR);
            if (other.found()) slot = other.slot();
            else {
                reset();
                return;
            }
        } else {
            slot = mc.player.getInventory().getSelectedSlot();
        }

        if (interactBlock(currentAnchorPos)) {
            boolean action = executeAction(() -> {
                BlockUtil.clickBlock(currentAnchorPos, Direction.UP, false, false);
                mc.player.swingHand(Hand.MAIN_HAND);
                resetTimer();
            }, slot, autoSwitch.get());

            if (action) {
                reset(); // Done
            }
        }
    }

    private Direction getPlaceSideIfReady(BlockPos pos) {
        Direction side = BlockUtil.getPlaceSide(pos);
        if (side == null) return null;

        Rotation rot;
        if (jitter.get()) {
            Direction opp = side.getOpposite();
            double x = pos.offset(side).getX() + 0.5 + (Math.random() - 0.5) * 0.2;
            double y = pos.offset(side).getY() + 0.5 + (Math.random() - 0.5) * 0.2;
            double z = pos.offset(side).getZ() + 0.5 + (Math.random() - 0.5) * 0.2;

            // Adjust to face center
            x += opp.getOffsetX() * 0.5;
            y += opp.getOffsetY() * 0.5;
            z += opp.getOffsetZ() * 0.5;

            rot = RotationUtil.calculate(new Vec3d(x, y, z));
        } else {
            rot = RotationUtil.calculate(pos.offset(side), side.getOpposite());
        }

        Managers.ROTATION.setRotations(rot, rotateSpeed.get().doubleValue(), MovementFix.OFF, RotationManager.Priority.High);

        if (isFacing(rot)) {
            // Relax strict check: allow hitting any side of the neighbor block
            if (strict.get() && !RaytraceUtil.overBlock(Managers.ROTATION.lastRotations, side.getOpposite(), pos.offset(side), false)) {
                return null;
            }
            return side;
        }
        return null;
    }

    private boolean interactBlock(BlockPos pos) {
        // 交互也是点这个方块
        Rotation rot;
        if (jitter.get()) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * 0.2;
            double y = pos.getY() + 1.0; // Top face
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * 0.2;
            rot = RotationUtil.calculate(new Vec3d(x, y, z));
        } else {
            rot = RotationUtil.calculate(pos, Direction.UP);
        }

        Managers.ROTATION.setRotations(rot, rotateSpeed.get().doubleValue(), MovementFix.OFF, RotationManager.Priority.High);

        if (isFacing(rot)) {
            return true;
        }
        return false;
    }

    // Helper to execute action with safe switching
    private boolean executeAction(Runnable action, int slot, boolean autoSwitch) {
        int oldSlot = mc.player.getInventory().getSelectedSlot();
        if (autoSwitch) InvUtil.swap(slot, false);
        else if (mc.player.getInventory().getSelectedSlot() != slot) return false;

        action.run();

        // Don't swap back immediately here, let the caller handle it or next tick handle it
        // Actually, for single action per tick, we can swap back if we want to be clean, 
        // but staying on item is often safer for "Switch -> Interact" consistency.
        // However, if we want to "Hide" the item, we swap back.
        // Given the ghost hand issue, swapping back immediately after interaction IS fine (silent switch).
        // The problem was switching back WITHOUT interaction.
        if (autoSwitch) InvUtil.swap(oldSlot, false);
        return true;
    }

    private boolean isFacing(Rotation targetRot) {
        Rotation current;
        if (strict.get()) {
            current = Managers.ROTATION.lastRotations;
            if (current == null) current = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        } else {
            current = Managers.ROTATION.rotations;
        }

        float yawDiff = Math.abs(MathHelper.wrapDegrees(current.yaw - targetRot.yaw));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(current.pitch - targetRot.pitch));
        return yawDiff <= angleTolerance.get().doubleValue() && pitchDiff <= angleTolerance.get().doubleValue();
    }
}
