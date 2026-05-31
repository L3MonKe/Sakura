package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
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
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoFall extends Module {
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);
    private final EnumValue<MlgMode> mlgMode = new EnumValue<>("MLG Mode", "MLG模式", MlgMode.Grim, () -> mode.is(Mode.MLG));
    private final NumberValue<Integer> swapDelay = new NumberValue<>("Swap Back Delay", "切回延迟", 200, 0, 1000, 10, () -> mode.is(Mode.MLG) && mlgMode.is(MlgMode.Grim));
    private final NumberValue<Integer> interactDelay = new NumberValue<>("Interact Delay", "交互延迟", 60, 0, 300, 5, () -> mode.is(Mode.MLG) && mlgMode.is(MlgMode.Grim));
    private final NumberValue<Integer> collectDelayTicks = new NumberValue<>("Collect Delay Ticks", "收水延后Tick", 2, 0, 10, 1, () -> mode.is(Mode.MLG) && mlgMode.is(MlgMode.Grim));
    private final NumberValue<Double> scaffoldRescueFall = new NumberValue<>("Scaffold Rescue Fall", "搭路自救距离", 5.5, 3.0, 12.0, 0.1, () -> mode.is(Mode.MLG) && mlgMode.is(MlgMode.Grim));

    private boolean mlgCompleted = true;
    private BlockPos placedWaterPos = null;
    private final TimerUtil swapTimer = new TimerUtil();
    private final TimerUtil interactTimer = new TimerUtil();
    private boolean pendingSwapBack = false;
    private int quickCollectDelayTicks = 0;

    private boolean shouldInteract = false;
    private int pendingSlot = -1;
    private BlockPos pendingBestPos = null;

    private Rotation lockedRotation = null;
    private boolean waitingForRotation = false;

    private int hypixelTicksExisted = -1;
    private boolean hypixelInteractRequired = false;
    private int hypixelOldSlot = -1;
    private boolean hypixelHandleStopMove = false;
    private boolean hypixelShouldReceive = false;
    private HypixelPlaceData hypixelLastData;
    private int hypixelTicksSinceTeleport = 0;
    private boolean hypixelPacketOnGround = false;
    private Rotation hypixelTargetRotation = null;

    public NoFall() {
        super("NoFall", "无摔落", Category.Movement);
    }

    public boolean isGrimMlgMode() {
        return isEnabled() && mode.is(Mode.MLG) && mlgMode.is(MlgMode.Grim);
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
        quickCollectDelayTicks = 0;
        swapTimer.reset();
        interactTimer.reset();
        resetPending();
        resetHypixel();
    }

    @Override
    protected void onDisable() {
        mlgCompleted = true;
        placedWaterPos = null;
        pendingSwapBack = false;
        quickCollectDelayTicks = 0;
        swapTimer.reset();
        interactTimer.reset();
        resetPending();
        resetHypixel();
    }

    private void resetPending() {
        shouldInteract = false;
        pendingSlot = -1;
        pendingBestPos = null;
        lockedRotation = null;
        waitingForRotation = false;
    }

    private void resetHypixel() {
        hypixelInteractRequired = false;
        hypixelShouldReceive = false;
        hypixelHandleStopMove = false;
        hypixelTicksExisted = -1;
        hypixelOldSlot = -1;
        hypixelLastData = null;
        hypixelTicksSinceTeleport = 0;
        hypixelPacketOnGround = false;
        hypixelTargetRotation = null;
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

    private boolean isScaffoldClutchAwaiting() {
        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
//        return scaffold != null && scaffold.isEnabled() && scaffold.isClutchAwaitingNoFall(); todo
        return true;
    }

    private boolean isNearGroundForClutch() {
        if (mc.player == null || mc.world == null) return false;
        BlockPos below = BlockPos.ofFloored(mc.player.getX(), mc.player.getY() - 1.0, mc.player.getZ());
        return !mc.world.getBlockState(below).isAir();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.MLG) && mlgMode.is(MlgMode.Hypixel)) {
            return;
        }

        if (pendingSwapBack && swapTimer.passedMillise(swapDelay.get())) {
            InvUtil.swapBack();
            pendingSwapBack = false;
        }

        if (mode.is(Mode.MLG)) {
            boolean scaffoldEnabled = isScaffoldEnabled();
            boolean scaffoldRescue = shouldScaffoldRescue();
            if ((isFalling() || mc.player.isTouchingWater() || mc.player.isInFluid()) && !mlgCompleted && (!scaffoldEnabled || scaffoldRescue || isScaffoldClutchAwaiting())) {
                mc.player.setSprinting(false);
                mc.options.sprintKey.setPressed(false);
            }

            if (tryContinuousCollectWater()) {
                return;
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

            if (scaffoldEnabled && !scaffoldRescue && !isScaffoldClutchAwaiting()) {
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

                        quickCollectDelayTicks = collectDelayTicks.get();
                        resetPending();

                        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
//                        if (scaffold != null && scaffold.isClutchAwaitingNoFall()) { todo
//                            scaffold.notifyNoFallPlaced();
//                        }
                    }
                }
                return;
            }

            if (isFalling()) {
                boolean clutchAwaiting = isScaffoldClutchAwaiting();
                if (clutchAwaiting && !isNearGroundForClutch()) {
                    return;
                }

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

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.MLG) && mlgMode.is(MlgMode.Hypixel)) {
            if (event.getType() == EventType.PRE) {
                onHypixelMotionPre();
            } else if (event.getType() == EventType.POST) {
                onHypixelMotionPost();
            }
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.MLG) && mlgMode.is(MlgMode.Hypixel)) {
            if (hypixelLastData != null) {
                mc.player.setSprinting(false);
            }
        }
    }

    private void onHypixelTick() {
        if (mc.player.isCreative()) return;
        if (mc.player.isUsingItem() || mc.currentScreen != null) return;

        hypixelTicksSinceTeleport++;

        if (hypixelTicksSinceTeleport < 3) return;

        boolean shouldMLG = mc.player.fallDistance > mc.player.getAttributeValue(EntityAttributes.SAFE_FALL_DISTANCE) && !mc.player.isTouchingWater() && hypixelNextTickWillLanding();

        if (hypixelTicksExisted >= 0) {
            hypixelTicksExisted--;
        }

        if (shouldMLG) {
            int waterBucketSlot = hypixelFindSlot(Items.WATER_BUCKET);
            if (waterBucketSlot == -1) {
                shouldMLG = false;
            }
            hypixelTicksExisted = 1;
            hypixelHandleStopMove = true;
            hypixelPlaceWaterBucket(waterBucketSlot);
        }
        if (!shouldMLG && hypixelHasEmptyBucket() && hypixelShouldReceive && hypixelTicksExisted <= 0) {
            hypixelRetrieveWaterBlock();
        }

        if (hypixelHandleStopMove) {
            if (!mc.player.isTouchingWater() && hypixelClientOnGround()) {
                hypixelHandleStopMove = false;
            }
        }
    }

    private void onHypixelMotionPre() {
        onHypixelTick();
    }

    private void onHypixelMotionPost() {
        if (hypixelTargetRotation == null || mc.player == null || mc.interactionManager == null) return;

        if (hypixelInteractRequired) {
            BlockHitResult result = hypixelRayCast(hypixelTargetRotation, 4.5);
            if (result == null) {
                if (mc.player.getInventory().getStack(mc.player.getInventory().getSelectedSlot()).getItem().equals(Items.BUCKET)) {
                    hypixelShouldReceive = true;
                }
                if (hypixelOldSlot != -1) {
                    mc.player.getInventory().setSelectedSlot(hypixelOldSlot);
                    hypixelOldSlot = -1;
                }
                return;
            }

            hypixelInteractRequired = false;
            mc.crosshairTarget = result;
            float currentYaw = mc.player.getYaw();
            float currentPitch = mc.player.getPitch();
            mc.player.setYaw(hypixelTargetRotation.yaw);
            mc.player.setPitch(hypixelTargetRotation.pitch);

            var res = mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            mc.player.setYaw(currentYaw);
            mc.player.setPitch(currentPitch);
            if (res.isAccepted()) {
                mc.player.swingHand(Hand.MAIN_HAND);
            }

            if (mc.player.getInventory().getStack(mc.player.getInventory().getSelectedSlot()).getItem().equals(Items.BUCKET)) {
                hypixelShouldReceive = true;
            }
            if (hypixelOldSlot != -1) {
                mc.player.getInventory().setSelectedSlot(hypixelOldSlot);
                hypixelOldSlot = -1;
            }
        }
    }

    private void hypixelRetrieveWaterBlock() {
        if (mc.player == null) return;

        hypixelOldSlot = mc.player.getInventory().getSelectedSlot();
        int emptyBucketSlot = hypixelFindSlot(Items.BUCKET);
        if (emptyBucketSlot == -1) return;
        BlockPos pos = hypixelFindScoopableWaterBlock();
        if (pos == null) {
            return;
        }
        var r = hypixelGetLookAtWaterBlock(pos);

        if (mc.player.getMainHandStack().getItem() != Items.BUCKET)
            mc.player.getInventory().setSelectedSlot(emptyBucketSlot);
        hypixelInteractItem(r);
        hypixelShouldReceive = false;
    }

    private BlockPos hypixelFindScoopableWaterBlock() {
        if (mc.player == null) return null;

        World world = mc.player.getEntityWorld();
        BlockPos playerBlockPos = mc.player.getBlockPos();
        double maxReachDistance = mc.player.getBlockInteractionRange();
        int reach = (int) Math.ceil(maxReachDistance);
        List<BlockPos> possible = new ArrayList<>();
        for (int x = -reach; x <= reach; x++) {
            for (int y = -reach; y <= reach; y++) {
                for (int z = -reach; z <= reach; z++) {
                    BlockPos currentPos = playerBlockPos.add(x, y, z);
                    FluidState fluidState = world.getFluidState(currentPos);
                    if (fluidState.isStill() && fluidState.getFluid() == Fluids.WATER) {
                        possible.add(currentPos);
                    }
                }
            }
        }
        possible.removeIf(blockPos -> {
            Rotation rotation = hypixelGetLookAtWaterBlock(blockPos);
            return hypixelRayCast(rotation, 4.5).getType() != HitResult.Type.BLOCK;
        });
        if (possible.isEmpty()) return null;
        return possible.getFirst();
    }

    private boolean hypixelNextTickWillLanding() {
        return !hypixelIsAirBlocksBelow(hypixelGetYMotion());
    }

    private boolean hypixelIsAirBlocksBelow(int high) {
        if (mc.player == null || mc.world == null) return false;

        List<HypixelPlaceData> possibleBoxes = new ArrayList<>();

        for (int x = -hypixelGetXMotion(); x <= hypixelGetXMotion(); x++) {
            for (int z = -hypixelGetZMotion(); z <= hypixelGetZMotion(); z++) {
                for (int y = 0; y <= high; y++) {
                    var pos = mc.player.getBlockPos().add(x, 0, z).down(y);
                    var state = mc.world.getBlockState(pos);

                    var bb = new Box(pos).withMaxY(pos.getY() + 3);
                    if (bb.intersects(mc.player.getBoundingBox()) && !state.isAir()) {
                        possibleBoxes.add(new HypixelPlaceData(pos, bb));
                    }
                }
            }
        }

        return possibleBoxes.isEmpty();
    }

    private int hypixelGetXMotion() {
        if (mc.player == null) return -1;
        return (int) Math.ceil(mc.player.getVelocity().x * mc.player.getVelocity().x);
    }

    private int hypixelGetYMotion() {
        if (mc.player == null) return -1;
        return (int) Math.ceil(mc.player.getVelocity().y * mc.player.getVelocity().y);
    }

    private int hypixelGetZMotion() {
        if (mc.player == null) return -1;
        return (int) Math.ceil(mc.player.getVelocity().z * mc.player.getVelocity().z);
    }

    private boolean hypixelHasEmptyBucket() {
        if (mc.player == null) return false;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == Items.BUCKET) {
                return true;
            }
        }
        return false;
    }

    private boolean hypixelClientOnGround() {
        if (mc.player == null) return false;
        return hypixelPacketOnGround && mc.player.isOnGround();
    }

    private void hypixelInteractItem(Rotation rotation) {
        Managers.ROTATION.setRotations(rotation, 10.0, MovementFix.NORMAL);
        hypixelTargetRotation = rotation;
        hypixelInteractRequired = true;
    }

    private HypixelPlaceData hypixelFindBestPlacePos() {
        if (mc.player == null || mc.world == null) return null;

        List<HypixelPlaceData> possibleBoxes = new ArrayList<>();

        var playerBox = mc.player.getBoundingBox();
        playerBox.offset(mc.player.getVelocity());

        for (int x = -hypixelGetXMotion(); x <= hypixelGetXMotion(); x++) {
            for (int z = -hypixelGetZMotion(); z <= hypixelGetZMotion(); z++) {
                for (int y = 0; y <= hypixelGetYMotion(); y++) {
                    var pos = mc.player.getBlockPos().add(x, 0, z).down(y);
                    var state = mc.world.getBlockState(pos);

                    var bb = new Box(pos).withMaxY(pos.getY() + 3);
                    if (bb.intersects(playerBox) && !state.isAir()) {
                        possibleBoxes.add(new HypixelPlaceData(pos, bb));
                    }
                }
            }
        }

        possibleBoxes.sort(Comparator.comparingDouble(p -> p.pos.getY()));
        Collections.reverse(possibleBoxes);
        var best = possibleBoxes.getFirst();

        possibleBoxes.removeIf(p -> p.pos.getY() != best.pos().getY());

        possibleBoxes.sort(Comparator.comparingDouble(p -> Vec3d.of(p.pos()).squaredDistanceTo(
                playerBox.getCenter().withAxis(Direction.Axis.Y, playerBox.minY)
        )));
        possibleBoxes.sort(Comparator.comparing(p -> !p.bb.contains(mc.player.getEntityPos())));
        return possibleBoxes.getFirst();
    }

    private Rotation hypixelGetLookAtWaterBlock(BlockPos targetPos) {
        if (mc.player == null || targetPos == null) {
            return new Rotation(0, 0);
        }

        return RotationUtil.calculate(Vec3d.of(targetPos));
    }

    private void hypixelPlaceWaterBucket(int waterBucketSlot) {
        if (waterBucketSlot == -1 || mc.player == null) {
            return;
        }
        hypixelOldSlot = mc.player.getInventory().getSelectedSlot();

        var best = hypixelFindBestPlacePos();
        hypixelLastData = best;

        if (best == null) return;

        var r = hypixelGetLookAtWaterBlock(best.pos());

        if (mc.player.getMainHandStack().getItem() != Items.WATER_BUCKET)
            mc.player.getInventory().setSelectedSlot(waterBucketSlot);
        hypixelInteractItem(r);
        hypixelShouldReceive = true;
    }

    private int hypixelFindSlot(net.minecraft.item.Item item) {
        if (mc.player == null) return -1;
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == item) {
                slot = i;
            }
        }
        return slot;
    }

    private BlockHitResult hypixelRayCast(Rotation rotation, double reach) {
        if (mc.player == null || mc.world == null) return null;
        Vec3d eyesPos = mc.player.getCameraPosVec(1f);
        Vec3d rotationVec = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d endPos = eyesPos.add(rotationVec.x * reach, rotationVec.y * reach, rotationVec.z * reach);
        return mc.world.raycast(new RaycastContext(eyesPos, endPos, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
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
        quickCollectDelayTicks = 0;
        resetPending();
    }

    private boolean tryContinuousCollectWater() {
        if (waitingForRotation || shouldInteract) return false;
        if (isFalling() && !mc.player.isTouchingWater() && !mc.player.isInFluid()) return false;

        if (quickCollectDelayTicks > 0) {
            quickCollectDelayTicks--;
            return false;
        }

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
        if (nullCheck()) return;

        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerMoveC2SPacket packet) {
            hypixelPacketOnGround = packet.isOnGround();
        }

        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            hypixelTicksSinceTeleport = 0;
        }

        for (EquipmentSlot slot : AttributeModifierSlot.ARMOR) {
            if (mc.player.getEquippedStack(slot).getItem() == Items.ELYTRA) {
                return;
            }
        }
        if (event.getType() == EventType.SEND && mode.is(Mode.Packet)) {
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

    private enum MlgMode {
        Grim,
        Hypixel
    }

    private record HypixelPlaceData(BlockPos pos, Box bb) {
    }
}
