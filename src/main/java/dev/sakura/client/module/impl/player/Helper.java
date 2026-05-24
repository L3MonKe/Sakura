package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.NoFall;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.MultiBoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.*;
import java.util.List;

public class Helper extends Module {

    private final MultiBoolValue modulesSetting = new MultiBoolValue("Mode", "模式",
            List.of(
                    new BoolValue("Self Extinguish", "自我灭火", true),
                    new BoolValue("Block Lava", "挡岩浆", true),
                    new BoolValue("AntiTNT", "防TNT", true)
            ));

    private final BoolValue legit = new BoolValue("Legit", "合法旋转", false);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("Speed", "旋转速度", 45.0, 2.0, 180.0, 1.0, legit::get);
    private final NumberValue<Double> rotationFov = new NumberValue<>("FOV", "视野", 90.0, 30.0, 180.0, 1.0, legit::get);

    private final NumberValue<Integer> seSwapDelay = new NumberValue<>("Swap Back Delay", "切回延迟", 200, 0, 1000, 10, () -> modulesSetting.isEnabled("Self Extinguish"));
    private final NumberValue<Integer> seInteractDelay = new NumberValue<>("Interact Delay", "交互延迟", 60, 0, 300, 5, () -> modulesSetting.isEnabled("Self Extinguish"));
    private final NumberValue<Integer> seCollectDelayTicks = new NumberValue<>("Collect Delay Ticks", "收水延后Tick", 2, 0, 10, 1, () -> modulesSetting.isEnabled("Self Extinguish"));

    private final Map<BlockPos, Integer> waterPlacements = new HashMap<>();
    private final Map<BlockPos, Integer> lavaPlacements = new HashMap<>();
    private FluidTracker fluidTracker;

    private boolean seMlgCompleted = true;
    private BlockPos sePlacedWaterPos = null;
    private final TimerUtil seSwapTimer = new TimerUtil();
    private final TimerUtil seInteractTimer = new TimerUtil();
    private boolean sePendingSwapBack = false;
    private int seQuickCollectDelayTicks = 0;
    private boolean seShouldInteract = false;
    private int sePendingSlot = -1;
    private BlockPos sePendingBestPos = null;
    private Rotation seLockedRotation = null;
    private boolean seWaitingForRotation = false;

    private BlockPos blTargetPos = null;
    private int blState = 0;
    private BLPlacementData blCurrentPlacement = null;
    private boolean blWaitingForRotation = false;
    private Rotation blLockedRotation = null;
    private boolean blShouldPlace = false;
    private int blPendingSlot = -1;
    private BLPlacementData blPendingPlacement = null;
    private final TimerUtil blInteractTimer = new TimerUtil();

    private final TimerUtil atPlacementTimer = new TimerUtil();
    private final List<BlockPos> atBlockPositionQueue = new ArrayList<>();
    private TntEntity atTargetTnt = null;
    private int atSavedHotbarSlot = -1;
    private BlockPos atLastPlacedPos = null;

    private record BLPlacementData(BlockPos blockPos, Direction direction, Vec3d hitVec) {
    }

    private static class FluidTracker {
        final Block fluidBlock;
        final BlockPos sourcePos;
        final Set<BlockPos> connectedPositions;
        int tickCount;

        FluidTracker(Block fluidBlock, BlockPos sourcePos, Set<BlockPos> connectedPositions, int tickCount) {
            this.fluidBlock = fluidBlock;
            this.sourcePos = sourcePos;
            this.connectedPositions = connectedPositions;
            this.tickCount = tickCount;
        }
    }

    public Helper() {
        super("Helper", "助手", Category.Player);
    }

    public boolean isUsingRotation() {
        return Managers.ROTATION.isActive();
    }

    @Override
    protected void onEnable() {
        resetSelfExtinguish();
        resetBlockLava();
        resetAntiTNT();
        waterPlacements.clear();
        lavaPlacements.clear();
        fluidTracker = null;
    }

    @Override
    protected void onDisable() {
        waterPlacements.clear();
        lavaPlacements.clear();
        fluidTracker = null;
        resetSelfExtinguish();
        resetBlockLava();
        resetAntiTNT();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        processBucketTracker();
        cleanupPlacementMaps();

        if (modulesSetting.isEnabled("Self Extinguish")) {
            tickSelfExtinguish();
        }
        if (modulesSetting.isEnabled("Block Lava")) {
            tickBlockLava();
        }
        if (modulesSetting.isEnabled("AntiTNT")) {
            tickAntiTNT();
        }
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck()) return;

        if (event.getType() == EventType.PRE) {
            updateBucketTracker();
        }
    }

    // ========== Shared Utility Methods ==========

    private boolean isFacing(Rotation target, float yawTolerance, float pitchTolerance) {
        Rotation current = Managers.ROTATION.rotations;
        if (current == null) {
            current = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        }
        float yawDiff = Math.abs(MathHelper.wrapDegrees(current.yaw - target.yaw));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(current.pitch - target.pitch));
        return yawDiff <= yawTolerance && pitchDiff <= pitchTolerance;
    }

    private boolean useItemLegit(Rotation rotation) {
        if (mc.interactionManager == null) return false;
        float oldYaw = mc.player.getYaw();
        float oldPitch = mc.player.getPitch();
        mc.player.setYaw(rotation.yaw);
        mc.player.setPitch(rotation.pitch);
        ActionResult result = mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        mc.player.setYaw(oldYaw);
        mc.player.setPitch(oldPitch);
        return result.isAccepted();
    }

    private boolean placeBlockLegit(Rotation rotation, BlockHitResult hitResult) {
        if (mc.interactionManager == null) return false;
        float oldYaw = mc.player.getYaw();
        float oldPitch = mc.player.getPitch();
        mc.player.setYaw(rotation.yaw);
        mc.player.setPitch(rotation.pitch);
        ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hitResult);
        mc.player.setYaw(oldYaw);
        mc.player.setPitch(oldPitch);
        return result.isAccepted();
    }

    private boolean shouldCollectWater() {
        NoFall noFall = Sakura.MODULES.getModule(NoFall.class);
        return noFall == null || !noFall.isGrimMlgMode();
    }

    private boolean canSeeBlockFace(BlockPos blockPos, Direction direction) {
        if (mc.player == null || mc.world == null) {
            return false;
        }
        Vec3d eyePos = mc.player.getEyePos();
        Vec3d targetVec = Vec3d.ofCenter(blockPos).add(direction.getOffsetX() * 0.49, direction.getOffsetY() * 0.49, direction.getOffsetZ() * 0.49);
        BlockHitResult hit = mc.world.raycast(new RaycastContext(eyePos, targetVec, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        if (!hit.getBlockPos().equals(blockPos)) {
            return false;
        }
        return hit.getPos().squaredDistanceTo(targetVec) < 0.25;
    }

    private boolean isPositionInFov(Vec3d pos) {
        if (!legit.get()) return true;
        Rotation current = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        Rotation target = RotationUtil.calculate(pos);
        float yawDiff = MathHelper.wrapDegrees(current.yaw - target.yaw);
        float pitchDiff = current.pitch - target.pitch;
        double distance = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);
        return distance <= rotationFov.get() / 2.0;
    }

    // ========== Self Extinguish (Grim-style) ==========

    private void resetSelfExtinguish() {
        seMlgCompleted = true;
        sePlacedWaterPos = null;
        sePendingSwapBack = false;
        seQuickCollectDelayTicks = 0;
        seSwapTimer.reset();
        seInteractTimer.reset();
        seResetPending();
    }

    private void seResetPending() {
        seShouldInteract = false;
        sePendingSlot = -1;
        sePendingBestPos = null;
        seLockedRotation = null;
        seWaitingForRotation = false;
    }

    private void seCompleteMlgCycle() {
        seMlgCompleted = true;
        sePlacedWaterPos = null;
        sePendingSwapBack = false;
        seQuickCollectDelayTicks = 0;
        seResetPending();
    }

    private void tickSelfExtinguish() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        if (sePendingSwapBack && seSwapTimer.passedMillise(seSwapDelay.get())) {
            InvUtil.swapBack();
            sePendingSwapBack = false;
        }

        if (shouldCollectWater() && seTryContinuousCollectWater()) {
            return;
        }

        if ((mc.player.isTouchingWater() || mc.player.isInFluid()) && !seMlgCompleted) {
            if (!shouldCollectWater()) {
                seCompleteMlgCycle();
                return;
            }

            if (InvUtil.findInHotbar(Items.WATER_BUCKET).found()) {
                seCompleteMlgCycle();
                return;
            }

            FindItemResult bucket = InvUtil.findInHotbar(Items.BUCKET);
            if (bucket.found()) {
                BlockPos targetPos = sePlacedWaterPos != null ? sePlacedWaterPos : seGetWaterPos();
                if (targetPos != null) {
                    Vec3d eyesPos = mc.player.getEyePos();
                    double hitX = MathHelper.clamp(eyesPos.x, targetPos.getX(), targetPos.getX() + 1.0);
                    double hitZ = MathHelper.clamp(eyesPos.z, targetPos.getZ(), targetPos.getZ() + 1.0);
                    Vec3d hitVec = new Vec3d(hitX, targetPos.getY() + 1.0, hitZ);
                    Rotation rotation = RotationUtil.calculate(hitVec);
                    Managers.ROTATION.setRotations(rotation, 180, MovementFix.NORMAL, Priority.High);

                    if (!isFacing(rotation, 2.0f, 2.5f)) {
                        return;
                    }

                    InvUtil.swap(bucket.slot(), true);
                    if (seInteractTimer.passedMillise(seInteractDelay.get()) && useItemLegit(rotation)) {
                        mc.player.swingHand(Hand.MAIN_HAND);
                        seInteractTimer.reset();
                        sePendingSwapBack = true;
                        seSwapTimer.reset();
                    }
                }
            } else {
                if (InvUtil.testInHands(Items.WATER_BUCKET)) {
                    seCompleteMlgCycle();
                }
            }
            return;
        }

        if (seWaitingForRotation && seLockedRotation != null) {
            Managers.ROTATION.setRotations(seLockedRotation, 180, MovementFix.NORMAL, Priority.High);

            if (!isFacing(seLockedRotation, 1.6f, 2.0f)) {
                return;
            }

            if (seShouldInteract && sePendingSlot != -1) {
                InvUtil.swap(sePendingSlot, true);
                if (seInteractTimer.passedMillise(seInteractDelay.get()) && useItemLegit(seLockedRotation)) {
                    mc.player.swingHand(Hand.MAIN_HAND);
                    seInteractTimer.reset();

                    if (sePendingBestPos != null) {
                        sePlacedWaterPos = sePendingBestPos;
                    }

                    seQuickCollectDelayTicks = seCollectDelayTicks.get();
                    seResetPending();
                }
            }
            return;
        }

        if (mc.player.isOnFire() && mc.player.isOnGround()) {
            seMlgCompleted = false;
            sePlacedWaterPos = null;

            FindItemResult waterBucket = InvUtil.findInHotbar(Items.WATER_BUCKET);

            if (waterBucket.found()) {
                BlockPos bestPos = seGetBestPos();
                if (bestPos != null) {
                    double dist = mc.player.getEyePos().distanceTo(bestPos.toCenterPos().add(0, 0.5, 0));

                    if (dist < 10) {
                        Vec3d targetVec = new Vec3d(bestPos.getX() + 0.5, bestPos.getY() + 1.0, bestPos.getZ() + 0.5);
                        Rotation rotation = RotationUtil.calculate(targetVec);

                        Managers.ROTATION.setRotations(rotation, 180, MovementFix.NORMAL, Priority.High);

                        if (rotation.pitch > 45) {
                            Vec3d eyesPos = mc.player.getEyePos();
                            BlockPos neighbor = bestPos.down();

                            double hitX = MathHelper.clamp(eyesPos.x, neighbor.getX(), neighbor.getX() + 1.0);
                            double hitZ = MathHelper.clamp(eyesPos.z, neighbor.getZ(), neighbor.getZ() + 1.0);
                            Vec3d hitVec = new Vec3d(hitX, neighbor.getY() + 1.0, hitZ);

                            if (eyesPos.distanceTo(hitVec) > 4.5) {
                                return;
                            }

                            Rotation preciseRotation = RotationUtil.calculate(hitVec);
                            Managers.ROTATION.setRotations(preciseRotation, 180, MovementFix.NORMAL, Priority.High);

                            sePendingSlot = waterBucket.slot();
                            seShouldInteract = true;
                            sePendingBestPos = bestPos;

                            seLockedRotation = preciseRotation;
                            seWaitingForRotation = true;
                        }
                    }
                }
            }
        }
    }

    private boolean seTryContinuousCollectWater() {
        if (seWaitingForRotation || seShouldInteract) return false;

        if (seQuickCollectDelayTicks > 0) {
            seQuickCollectDelayTicks--;
            return false;
        }

        if (InvUtil.findInHotbar(Items.WATER_BUCKET).found()) {
            seCompleteMlgCycle();
            return true;
        }

        FindItemResult bucket = InvUtil.findInHotbar(Items.BUCKET);
        if (!bucket.found()) return false;

        BlockPos targetPos = seFindNearbyWaterTarget();
        if (targetPos == null) return false;

        Vec3d eyesPos = mc.player.getEyePos();
        double hitX = MathHelper.clamp(eyesPos.x, targetPos.getX(), targetPos.getX() + 1.0);
        double hitZ = MathHelper.clamp(eyesPos.z, targetPos.getZ(), targetPos.getZ() + 1.0);
        Vec3d hitVec = new Vec3d(hitX, targetPos.getY() + 1.0, hitZ);
        Rotation rotation = RotationUtil.calculate(hitVec);
        Managers.ROTATION.setRotations(rotation, 180, MovementFix.NORMAL, Priority.High);

        if (!isFacing(rotation, 2.0f, 2.5f)) return true;

        InvUtil.swap(bucket.slot(), true);
        if (seInteractTimer.passedMillise(seInteractDelay.get()) && useItemLegit(rotation)) {
            mc.player.swingHand(Hand.MAIN_HAND);
            seInteractTimer.reset();
            seCompleteMlgCycle();
        }

        return true;
    }

    private BlockPos seGetBestPos() {
        BlockPos direct = BlockPos.ofFloored(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ());

        if (!mc.world.getBlockState(direct).isAir()) {
            return direct.up();
        }

        for (int i = 0; i <= 20; i++) {
            BlockPos check = direct.down(i);
            if (!mc.world.getBlockState(check).isAir()) {
                return check.up();
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                for (int i = 0; i <= 20; i++) {
                    BlockPos check = direct.add(x, -i, z);
                    if (!mc.world.getBlockState(check).isAir()) {
                        return check.up();
                    }
                }
            }
        }

        return null;
    }

    private BlockPos seGetWaterPos() {
        BlockPos base = BlockPos.ofFloored(mc.player.getX(), mc.player.getY(), mc.player.getZ());

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos check = base.add(x, y, z);
                    if (mc.world.getBlockState(check).getBlock() instanceof net.minecraft.block.FluidBlock) return check;
                }
            }
        }

        return null;
    }

    private BlockPos seFindNearbyWaterTarget() {
        BlockPos base = BlockPos.ofFloored(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        Vec3d eyesPos = mc.player.getEyePos();
        BlockPos bestPos = null;
        double bestDistance = Double.MAX_VALUE;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos check = base.add(x, y, z);
                    BlockState state = mc.world.getBlockState(check);
                    if (!state.getFluidState().isOf(net.minecraft.fluid.Fluids.WATER)) continue;

                    double distance = eyesPos.squaredDistanceTo(check.toCenterPos().add(0, 0.5, 0));
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPos = check;
                    }
                }
            }
        }

        return bestPos;
    }

    // ========== Block Lava (Grim-style) ==========

    private void resetBlockLava() {
        blTargetPos = null;
        blState = 0;
        blCurrentPlacement = null;
        blWaitingForRotation = false;
        blLockedRotation = null;
        blShouldPlace = false;
        blPendingSlot = -1;
        blPendingPlacement = null;
        blInteractTimer.reset();
    }

    private void tickBlockLava() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) {
            return;
        }
        if (mc.player.isOnFire()) {
            resetBlockLava();
            return;
        }
        if (mc.player.isInLava()) {
            resetBlockLava();
            return;
        }

        if (blWaitingForRotation && blLockedRotation != null) {
            Managers.ROTATION.setRotations(blLockedRotation, 180, MovementFix.NORMAL, Priority.High);

            if (!isFacing(blLockedRotation, 1.6f, 2.0f)) {
                return;
            }

            if (blShouldPlace && blPendingSlot != -1 && blPendingPlacement != null) {
                InvUtil.swap(blPendingSlot, true);
                if (blInteractTimer.passedMillise(60)) {
                    BlockHitResult hitResult = new BlockHitResult(blPendingPlacement.hitVec(), blPendingPlacement.direction(), blPendingPlacement.blockPos(), false);
                    if (placeBlockLegit(blLockedRotation, hitResult)) {
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                    blInteractTimer.reset();
                    resetBlockLava();
                }
            }
            return;
        }

        if (blCurrentPlacement != null) {
            Rotation rotation = RotationUtil.calculate(blCurrentPlacement.hitVec());
            Managers.ROTATION.setRotations(rotation, 180, MovementFix.NORMAL, Priority.High);

            int blockSlot = findBlockSlotLava();
            if (blockSlot == -1) {
                resetBlockLava();
                return;
            }

            blLockedRotation = rotation;
            blWaitingForRotation = true;
            blShouldPlace = true;
            blPendingSlot = blockSlot;
            blPendingPlacement = blCurrentPlacement;
            return;
        }

        switch (blState) {
            case 1: {
                if (blTargetPos == null || !mc.world.getBlockState(blTargetPos).isOf(Blocks.LAVA)) {
                    resetBlockLava();
                    return;
                }
                if (mc.world.getBlockState(blTargetPos.down()).isAir()) {
                    blState = 2;
                    break;
                }
                if (!tryFindPlacementLava()) {
                    blState = 2;
                }
                break;
            }
            case 2: {
                if (blTargetPos == null || !mc.world.getBlockState(blTargetPos).isOf(Blocks.LAVA)) {
                    resetBlockLava();
                    return;
                }
                BlockPos blockPos = blTargetPos.down();
                if (mc.world.getBlockState(blockPos).isSolid()) {
                    blState = 1;
                    tryFindPlacementLava();
                    return;
                }
                Optional<BLPlacementData> placementOpt = findSuitableFaceLava(blockPos);
                if (placementOpt.isPresent()) {
                    blCurrentPlacement = placementOpt.get();
                } else {
                    resetBlockLava();
                }
                break;
            }
            case 0: {
                findTargetPosLava();
                if (blTargetPos == null) break;
                blState = 1;
            }
        }
    }

    private boolean tryFindPlacementLava() {
        Optional<BLPlacementData> placementOpt = findSuitableFaceLava(blTargetPos);
        if (placementOpt.isPresent()) {
            blCurrentPlacement = placementOpt.get();
            return true;
        }
        return false;
    }

    private void findTargetPosLava() {
        if (mc.player == null || mc.world == null) {
            blTargetPos = null;
            return;
        }
        BlockPos playerPos = mc.player.getBlockPos();
        List<BlockPos> candidates = new ArrayList<>();
        for (int dx = -3; dx <= 3; ++dx) {
            for (int dy = -2; dy <= 2; ++dy) {
                for (int dz = -3; dz <= 3; ++dz) {
                    BlockPos candidatePos = playerPos.add(dx, dy, dz);
                    if (hasLavaPlacement(candidatePos) || !mc.world.getBlockState(candidatePos).isOf(Blocks.LAVA) || !mc.world.getFluidState(candidatePos).isStill() || !isPositionInFov(Vec3d.ofCenter(candidatePos)))
                        continue;
                    candidates.add(candidatePos);
                }
            }
        }
        Vec3d eyePos = mc.player.getEyePos();
        blTargetPos = candidates.stream().min(Comparator.comparingDouble(blockPos -> Vec3d.ofCenter(blockPos).squaredDistanceTo(eyePos))).orElse(null);
    }

    private int findBlockSlotLava() {
        FindItemResult cobblestoneResult = InvUtil.findInHotbar(Items.COBBLESTONE);
        if (cobblestoneResult.found() && cobblestoneResult.slot() >= 0 && cobblestoneResult.slot() <= 8) {
            return cobblestoneResult.slot();
        }
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack itemStack = mc.player.getInventory().getStack(slot);
            if (!(itemStack.getItem() instanceof BlockItem)) continue;
            Block block = ((BlockItem) itemStack.getItem()).getBlock();
            if (!block.getDefaultState().isSolid()) continue;
            return slot;
        }
        return -1;
    }

    private Optional<BLPlacementData> findSuitableFaceLava(BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = blockPos.offset(direction);
            if (mc.world.getBlockState(neighborPos).isSolid() && !mc.world.getBlockState(neighborPos).isOf(Blocks.LAVA)) {
                Vec3d hitVec = Vec3d.ofCenter(neighborPos).add(direction.getOpposite().getOffsetX() * 0.5, direction.getOpposite().getOffsetY() * 0.5, direction.getOpposite().getOffsetZ() * 0.5);
                if (mc.player.getEyePos().squaredDistanceTo(hitVec) <= 25.0 && canSeeBlockFace(neighborPos, direction.getOpposite())) {
                    return Optional.of(new BLPlacementData(neighborPos, direction.getOpposite(), hitVec));
                }
            }
            if (direction != Direction.DOWN) continue;
            BlockPos belowPos = neighborPos;
            for (int i = 0; i < 3 && mc.world.getBlockState(belowPos).isOf(Blocks.LAVA); ++i) {
                belowPos = belowPos.down();
            }
            if (!mc.world.getBlockState(belowPos).isSolid() || mc.world.getBlockState(belowPos).isOf(Blocks.LAVA))
                continue;
            Vec3d topHitVec = Vec3d.ofCenter(belowPos).add(Direction.UP.getOffsetX() * 0.5, Direction.UP.getOffsetY() * 0.5, Direction.UP.getOffsetZ() * 0.5);
            if (!(mc.player.getEyePos().squaredDistanceTo(topHitVec) <= 25.0) || !canSeeBlockFace(belowPos, Direction.UP))
                continue;
            return Optional.of(new BLPlacementData(belowPos, Direction.UP, topHitVec));
        }
        return Optional.empty();
    }

    // ========== AntiTNT ==========

    private void resetAntiTNT() {
        atBlockPositionQueue.clear();
        atTargetTnt = null;
        atSavedHotbarSlot = -1;
        atLastPlacedPos = null;
        atPlacementTimer.reset();
    }

    private void tickAntiTNT() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) {
            return;
        }
        if (isMoving()) {
            if (!atBlockPositionQueue.isEmpty()) {
                atBlockPositionQueue.clear();
                restoreAntiTNTSlot();
            }
            atTargetTnt = null;
            return;
        }
        if (atBlockPositionQueue.isEmpty()) {
            atTargetTnt = findNearestTNT();
        }
        if (!atBlockPositionQueue.isEmpty()) {
            placeNextBlock();
            return;
        }
        if (atTargetTnt != null) {
            collectBlockPositions();
        }
    }

    private boolean isMoving() {
        if (mc.options == null) {
            return false;
        }
        return mc.options.forwardKey.isPressed() || mc.options.backKey.isPressed() || mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed() || mc.player.isSprinting();
    }

    private TntEntity findNearestTNT() {
        List<TntEntity> tntEntities = mc.world.getEntitiesByClass(TntEntity.class, mc.player.getBoundingBox().expand(20.0), tntEntity -> tntEntity.getFuse() > 0 && (isMovingTowardsPlayer(tntEntity) || hasLineOfSight(tntEntity)));
        return tntEntities.stream().min(Comparator.comparingDouble(tntEntity -> tntEntity.squaredDistanceTo(mc.player))).orElse(null);
    }

    private boolean hasLineOfSight(TntEntity tntEntity) {
        if (mc.player == null || mc.world == null) {
            return false;
        }
        if (tntEntity.squaredDistanceTo(mc.player) > 64.0) {
            return false;
        }
        Vec3d tntPos = tntEntity.getEntityPos();
        Vec3d eyePos = mc.player.getEyePos();
        BlockHitResult hit = mc.world.raycast(new RaycastContext(tntPos, eyePos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player));
        return hit.getType() == HitResult.Type.MISS;
    }

    private boolean isMovingTowardsPlayer(TntEntity tntEntity) {
        Vec3d toPlayer = mc.player.getEntityPos().subtract(tntEntity.getEntityPos()).normalize();
        return tntEntity.getVelocity().dotProduct(toPlayer) > 0.05;
    }

    private void collectBlockPositions() {
        if (!atBlockPositionQueue.isEmpty()) {
            return;
        }
        if (mc.currentScreen != null) {
            mc.player.closeHandledScreen();
            mc.setScreen(null);
        }
        BlockPos playerPos = mc.player.getBlockPos();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos sidePos = playerPos.offset(direction);
            if (!canPlaceAt(sidePos)) continue;
            atBlockPositionQueue.add(sidePos);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos sidePos = playerPos.up().offset(direction);
            if (!canPlaceAt(sidePos)) continue;
            atBlockPositionQueue.add(sidePos);
        }
        BlockPos abovePos = playerPos.up(2);
        if (canPlaceAt(abovePos)) {
            atBlockPositionQueue.add(abovePos);
        }
        atPlacementTimer.reset();
    }

    private void placeNextBlock() {
        if (!atPlacementTimer.delay(1)) {
            return;
        }
        if (atBlockPositionQueue.isEmpty()) {
            return;
        }
        BlockPos placePos = atBlockPositionQueue.get(0);
        BlockHitResult hit = getPlacementHitResult(placePos);
        if (hit == null) {
            atBlockPositionQueue.remove(0);
            return;
        }
        int blockSlot = findBlockSlotAntiTNT();
        if (blockSlot == -1) {
            atBlockPositionQueue.clear();
            restoreAntiTNTSlot();
            return;
        }
        if (atSavedHotbarSlot == -1) {
            atSavedHotbarSlot = mc.player.getInventory().getSelectedSlot();
        }
        mc.player.getInventory().setSelectedSlot(blockSlot);
        mc.interactionManager.syncSelectedSlot();
        Rotation targetRot = RotationUtil.calculate(hit.getBlockPos());
        Managers.ROTATION.setRotations(targetRot, legit.get() ? rotationSpeed.get() : 180.0, moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF);
        net.minecraft.util.ActionResult interactionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
        if (interactionResult.isAccepted()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        atLastPlacedPos = placePos;
        atBlockPositionQueue.remove(0);
        atPlacementTimer.reset();
        if (atBlockPositionQueue.isEmpty()) {
            restoreAntiTNTSlot();
        }
    }

    private void restoreAntiTNTSlot() {
        if (atSavedHotbarSlot != -1) {
            mc.player.getInventory().setSelectedSlot(atSavedHotbarSlot);
            mc.interactionManager.syncSelectedSlot();
            atSavedHotbarSlot = -1;
        }
    }

    private boolean canPlaceAt(BlockPos blockPos) {
        return mc.world.getBlockState(blockPos).isReplaceable() && !mc.player.getBoundingBox().intersects(new Box(blockPos));
    }

    private int findBlockSlotAntiTNT() {
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack itemStack = mc.player.getInventory().getStack(slot);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem) || itemStack.getCount() <= 1)
                continue;
            Block block = ((BlockItem) itemStack.getItem()).getBlock();
            BlockState defaultState = block.getDefaultState();
            if (!defaultState.isSolid()) continue;
            return slot;
        }
        return -1;
    }

    private boolean isSolidBlock(BlockPos blockPos) {
        return mc.world.getBlockState(blockPos).isSolid();
    }

    private BlockHitResult getPlacementHitResult(BlockPos placePos) {
        BlockPos belowPos = placePos.down();
        if (isSolidBlock(belowPos)) {
            return new BlockHitResult(getHitVec(belowPos, Direction.UP), Direction.UP, belowPos, false);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos sidePos = placePos.offset(direction);
            if (!isSolidBlock(sidePos)) continue;
            Direction hitFace = direction.getOpposite();
            return new BlockHitResult(getHitVec(sidePos, hitFace), hitFace, sidePos, false);
        }
        return null;
    }

    private Vec3d getHitVec(BlockPos blockPos, Direction direction) {
        double hitX = (double) blockPos.getX() + 0.5;
        double hitY = (double) blockPos.getY() + 0.5;
        double hitZ = (double) blockPos.getZ() + 0.5;
        if (direction == Direction.UP || direction == Direction.DOWN) {
            hitX += MathUtil.getRandom(-0.3, 0.3);
            hitZ += MathUtil.getRandom(-0.3, 0.3);
        } else {
            hitY += MathUtil.getRandom(-0.25, 0.25);
        }
        if (direction == Direction.WEST || direction == Direction.EAST) {
            hitZ += MathUtil.getRandom(-0.3, 0.3);
        }
        if (direction == Direction.SOUTH || direction == Direction.NORTH) {
            hitX += MathUtil.getRandom(-0.3, 0.3);
        }
        double clampedX = Math.max(blockPos.getX(), Math.min(blockPos.getX() + 1, hitX));
        double clampedY = Math.max(blockPos.getY(), Math.min(blockPos.getY() + 1, hitY));
        double clampedZ = Math.max(blockPos.getZ(), Math.min(blockPos.getZ() + 1, hitZ));
        return new Vec3d(clampedX, clampedY, clampedZ);
    }

    // ========== Fluid Tracking ==========

    private void markWaterPlaced(BlockPos pos) {
        if (pos != null) {
            waterPlacements.put(pos.toImmutable(), 20);
        }
    }

    private void markLavaPlaced(BlockPos pos) {
        if (pos != null) {
            lavaPlacements.put(pos.toImmutable(), 20);
        }
    }

    private boolean hasWaterPlacement(BlockPos pos) {
        return pos != null && waterPlacements.containsKey(pos);
    }

    private boolean hasLavaPlacement(BlockPos pos) {
        return pos != null && lavaPlacements.containsKey(pos);
    }

    private void updateBucketTracker() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        net.minecraft.item.Item item = mc.player.getMainHandStack().getItem();
        if (item != Items.WATER_BUCKET && item != Items.LAVA_BUCKET) {
            item = mc.player.getOffHandStack().getItem();
        }
        if (item == Items.WATER_BUCKET || item == Items.LAVA_BUCKET) {
            Block block = item == Items.WATER_BUCKET ? Blocks.WATER : Blocks.LAVA;
            fluidTracker = new FluidTracker(block, mc.player.getBlockPos(), findFluidBlocks(mc.player.getBlockPos(), block), 20);
        }
    }

    private void processBucketTracker() {
        if (fluidTracker == null || mc.world == null) {
            return;
        }
        Set<BlockPos> newSources = findFluidBlocks(fluidTracker.sourcePos, fluidTracker.fluidBlock);
        newSources.removeAll(fluidTracker.connectedPositions);
        for (BlockPos pos : newSources) {
            if (fluidTracker.fluidBlock == Blocks.WATER) {
                markWaterPlaced(pos);
            } else {
                markLavaPlaced(pos);
            }
        }
        if (!newSources.isEmpty() || --fluidTracker.tickCount <= 0) {
            fluidTracker = null;
        }
    }

    private Set<BlockPos> findFluidBlocks(BlockPos center, Block block) {
        HashSet<BlockPos> set = new HashSet<>();
        if (mc.world == null || center == null) {
            return set;
        }
        for (int dx = -6; dx <= 6; ++dx) {
            for (int dy = -5; dy <= 5; ++dy) {
                for (int dz = -6; dz <= 6; ++dz) {
                    BlockPos pos = center.add(dx, dy, dz);
                    if (isFluidSourceAt(pos, block)) {
                        set.add(pos.toImmutable());
                    }
                }
            }
        }
        return set;
    }

    private void cleanupPlacementMaps() {
        if (mc.world == null) {
            waterPlacements.clear();
            lavaPlacements.clear();
            return;
        }
        updatePlacementMap(waterPlacements, Blocks.WATER);
        updatePlacementMap(lavaPlacements, Blocks.LAVA);
    }

    private void updatePlacementMap(Map<BlockPos, Integer> map, Block block) {
        map.entrySet().removeIf(entry -> {
            if (isFluidSourceAt(entry.getKey(), block)) {
                entry.setValue(0);
                return false;
            }
            int remaining = entry.getValue();
            if (remaining <= 0) {
                return true;
            }
            entry.setValue(remaining - 1);
            return false;
        });
    }

    private boolean isFluidSourceAt(BlockPos pos, Block block) {
        return mc.world.getBlockState(pos).isOf(block) && mc.world.getFluidState(pos).isStill();
    }
}
