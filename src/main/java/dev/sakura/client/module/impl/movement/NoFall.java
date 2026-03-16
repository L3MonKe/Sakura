package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.state.property.Properties;

public class NoFall extends Module {
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);
    private final NumberValue<Integer> swapDelay = new NumberValue<>("Swap Back Delay", "切回延迟", 200, 0, 1000, 10);
    private final NumberValue<Integer> interactDelay = new NumberValue<>("Interact Delay", "交互延迟", 60, 0, 300, 5);
    private final NumberValue<Double> scaffoldRescueFall = new NumberValue<>("Scaffold Rescue Fall", "搭路自救距离", 5.5, 3.0, 12.0, 0.1);
    private boolean mlgCompleted = true;
    private BlockPos placedWaterPos = null;
    private final TimerUtil swapTimer = new TimerUtil();
    private final TimerUtil interactTimer = new TimerUtil();
    private boolean pendingSwapBack = false;
    private int quickCollectTicks = 0;

    // Post tick interaction
    private boolean shouldInteract = false;
    private int pendingSlot = -1;
    private BlockPos pendingBestPos = null;

    // Rotation lock for next tick
    private Rotation lockedRotation = null;
    private boolean waitingForRotation = false;

    public NoFall() {
        super("NoFall", "无摔落", Category.Movement);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @Override
    protected void onEnable() {
        mlgCompleted = true;
        placedWaterPos = null;
        pendingSwapBack = false;
        quickCollectTicks = 0;
        swapTimer.reset();
        interactTimer.reset();
        resetPending();
    }

    @Override
    protected void onDisable() {
        mlgCompleted = true;
        placedWaterPos = null;
        pendingSwapBack = false;
        quickCollectTicks = 0;
        swapTimer.reset();
        interactTimer.reset();
        resetPending();
    }

    private void resetPending() {
        shouldInteract = false;
        pendingSlot = -1;
        pendingBestPos = null;
        lockedRotation = null;
        waitingForRotation = false;
    }

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

    private boolean isScaffoldEnabled() {
        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
        return scaffold != null && scaffold.isEnabled();
    }

    private boolean shouldScaffoldRescue() {
        if (!isScaffoldEnabled()) return false;
        return mc.player.fallDistance >= scaffoldRescueFall.get();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (pendingSwapBack && swapTimer.passedMillise(swapDelay.get())) {
            InvUtil.swapBack();
            pendingSwapBack = false;
        }

        if (mode.is(Mode.MLG)) {
            boolean scaffoldEnabled = isScaffoldEnabled();
            boolean scaffoldRescue = shouldScaffoldRescue();
            if ((isFalling() || mc.player.isTouchingWater() || mc.player.isInFluid()) && !mlgCompleted && (!scaffoldEnabled || scaffoldRescue)) {
                mc.player.setSprinting(false);
                mc.options.sprintKey.setPressed(false);
            }

            if (quickCollectTicks > 0) {
                quickCollectTicks--;
                if (tryQuickCollectWater()) {
                    return;
                }
            }

            if ((mc.player.isTouchingWater() || mc.player.isInFluid()) && !mlgCompleted) {
                if (InvUtil.findInHotbar(Items.WATER_BUCKET).found()) {
                    completeMlgCycle();
                    return;
                }

                FindItemResult bucket = InvUtil.findInHotbar(Items.BUCKET);
                if (bucket.found()) {
                    BlockPos targetPos = placedWaterPos != null ? placedWaterPos : getWaterPos();
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
                        if (interactTimer.passedMillise(interactDelay.get()) && useItemLegit(rotation)) {
                            mc.player.swingHand(Hand.MAIN_HAND);
                            interactTimer.reset();
                            pendingSwapBack = true;
                            swapTimer.reset();
                        }
                    }
                } else {
                    if (InvUtil.testInHands(Items.WATER_BUCKET)) {
                        completeMlgCycle();
                    }
                }
                return;
            }

            if (scaffoldEnabled && !scaffoldRescue) {
                resetPending();
                return;
            }

            if (waitingForRotation && lockedRotation != null) {
                Managers.ROTATION.setRotations(lockedRotation, 180, MovementFix.NORMAL, Priority.High);

                if (!isFacing(lockedRotation, 1.6f, 2.0f)) {
                    return;
                }

                if (shouldInteract && pendingSlot != -1) {
                    InvUtil.swap(pendingSlot, true);
                    if (interactTimer.passedMillise(interactDelay.get()) && useItemLegit(lockedRotation)) {
                        mc.player.swingHand(Hand.MAIN_HAND);
                        interactTimer.reset();

                        if (pendingBestPos != null) {
                            placedWaterPos = pendingBestPos;
                        }

                        quickCollectTicks = 5;
                        resetPending();
                    }
                }
                return;
            }

            if (isFalling()) {
                mlgCompleted = false;
                placedWaterPos = null;

                FindItemResult waterBucket = InvUtil.findInHotbar(Items.WATER_BUCKET);

                if (waterBucket.found()) {
                    BlockPos bestPos = getBestPos();
                    if (bestPos != null) {
                        if (shouldSkipMlgPlacement(bestPos)) {
                            mlgCompleted = true;
                            resetPending();
                            return;
                        }

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

                                pendingSlot = waterBucket.slot();
                                shouldInteract = true;
                                pendingBestPos = bestPos;

                                lockedRotation = preciseRotation;
                                waitingForRotation = true;
                            }
                        }
                    }
                }
            }
            return;
        }

        if (!isFalling()) return;

        if (mode.is(Mode.BBTT)) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.000000001, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision));
            mc.player.onLanding();
        }
    }

    private boolean shouldSkipMlgPlacement(BlockPos bestPos) {
        BlockPos landingPos = bestPos.down();
        BlockState landingState = mc.world.getBlockState(landingPos);
        if (landingState.isAir()) return false;
        if (!landingState.contains(Properties.WATERLOGGED)) return false;

        if (landingState.getBlock() instanceof SlabBlock) {
            SlabType slabType = landingState.get(SlabBlock.TYPE);
            if (slabType == SlabType.BOTTOM) {
                return false;
            }
        }

        return true;
    }

    private void completeMlgCycle() {
        mlgCompleted = true;
        placedWaterPos = null;
        pendingSwapBack = false;
        quickCollectTicks = 0;
        resetPending();
    }

    private boolean tryQuickCollectWater() {
        if (InvUtil.findInHotbar(Items.WATER_BUCKET).found()) {
            completeMlgCycle();
            return true;
        }

        FindItemResult bucket = InvUtil.findInHotbar(Items.BUCKET);
        if (!bucket.found()) return false;

        BlockPos targetPos = findNearbyWaterTarget();
        if (targetPos == null) return false;

        Vec3d eyesPos = mc.player.getEyePos();
        double hitX = MathHelper.clamp(eyesPos.x, targetPos.getX(), targetPos.getX() + 1.0);
        double hitZ = MathHelper.clamp(eyesPos.z, targetPos.getZ(), targetPos.getZ() + 1.0);
        Vec3d hitVec = new Vec3d(hitX, targetPos.getY() + 1.0, hitZ);
        Rotation rotation = RotationUtil.calculate(hitVec);
        Managers.ROTATION.setRotations(rotation, 180, MovementFix.NORMAL, Priority.High);

        if (!isFacing(rotation, 2.0f, 2.5f)) return true;

        InvUtil.swap(bucket.slot(), true);
        if (interactTimer.passedMillise(interactDelay.get()) && useItemLegit(rotation)) {
            mc.player.swingHand(Hand.MAIN_HAND);
            interactTimer.reset();
            completeMlgCycle();
        }

        return true;
    }

    private BlockPos findNearbyWaterTarget() {
        BlockPos base = BlockPos.ofFloored(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        Vec3d eyesPos = mc.player.getEyePos();
        BlockPos bestPos = null;
        double bestDistance = Double.MAX_VALUE;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos check = base.add(x, y, z);
                    BlockState state = mc.world.getBlockState(check);
                    if (!state.getFluidState().isOf(Fluids.WATER)) continue;

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

    private BlockPos getBestPos() {
        Vec3d velocity = mc.player.getVelocity();
        Vec3d predictedPos = new Vec3d(mc.player.getX() + velocity.x * 3, mc.player.getY(), mc.player.getZ() + velocity.z * 3);

        BlockPos base = BlockPos.ofFloored(predictedPos.x, mc.player.getY() - 1, predictedPos.z);
        BlockPos direct = BlockPos.ofFloored(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ());

        if (!mc.world.getBlockState(base).isAir()) {
            return base.up();
        }
        if (!mc.world.getBlockState(direct).isAir()) {
            return direct.up();
        }

        for (int i = 0; i <= 20; i++) {
            BlockPos check = base.down(i);
            if (!mc.world.getBlockState(check).isAir()) {
                return check.up();
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                for (int i = 0; i <= 20; i++) {
                    BlockPos check = base.add(x, -i, z);
                    if (!mc.world.getBlockState(check).isAir()) {
                        return check.up();
                    }
                }
            }
        }

        BlockPos fallbackBase = BlockPos.ofFloored(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ());
        for (int i = 0; i <= 20; i++) {
            BlockPos check = fallbackBase.down(i);
            if (!mc.world.getBlockState(check).isAir()) {
                return check.up();
            }
        }

        return null;
    }

    private BlockPos getWaterPos() {
        BlockPos base = BlockPos.ofFloored(mc.player.getX(), mc.player.getY(), mc.player.getZ());

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos check = base.add(x, y, z);
                    if (mc.world.getBlockState(check).getBlock() instanceof FluidBlock) return check;
                }
            }
        }

        Vec3d velocity = mc.player.getVelocity();
        Vec3d predicted = new Vec3d(mc.player.getX() + velocity.x * 2, mc.player.getY() + velocity.y * 2, mc.player.getZ() + velocity.z * 2);
        BlockPos predictedBase = BlockPos.ofFloored(predicted);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos check = predictedBase.add(x, y, z);
                    if (mc.world.getBlockState(check).getBlock() instanceof FluidBlock) return check;
                }
            }
        }

        return null;
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (nullCheck() || event.getType() != EventType.SEND) return;

        for (EquipmentSlot slot : AttributeModifierSlot.ARMOR) {
            if (mc.player.getEquippedStack(slot).getItem() == Items.ELYTRA) {
                return;
            }
        }
        if (mode.is(Mode.Packet)) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && isFalling()) {
                ((IPlayerMoveC2SPacket) packet).setOnGround(true);
            }
        }
    }

    private boolean isFalling() {
        if (mode.is(Mode.MLG)) {
            double triggerDistance = isScaffoldEnabled() ? 2.2 : 3.0;
            boolean falling = mc.player.fallDistance > triggerDistance && !mc.player.isOnGround();
            if (mc.player.getVelocity().y > 0) return false;
            return falling;
        }
        return mc.player.fallDistance > mc.player.getSafeFallDistance() && !mc.player.isOnGround();
    }

    private enum Mode {
        BBTT,
        Packet,
        MLG
    }
}
