package dev.sakura.client.module.impl.player;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.ItemSpoofUtils;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AutoWebPlace extends Module {
    public static Rotation targetRotation;

    private enum SwapMode {
        Normal,
        Silent
    }

    public final NumberValue<Double> rangeSetting = new NumberValue<>("Range", "范围", 4.0, 3.0, 5.0, 0.1);
    public final NumberValue<Double> delaySetting = new NumberValue<>("Delay", "延迟", 4.0, 1.0, 20.0, 1.0);
    public final EnumValue<SwapMode> swapModeSetting = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Silent);
    public final BoolValue renderSetting = new BoolValue("Render", "渲染", true);
    public final BoolValue groundWebSetting = new BoolValue("Ground Web", "地面蜘蛛网", false);
    public final BoolValue lavaSetting = new BoolValue("Lava", "岩浆", true);
    public final BoolValue lavaWaitWaterBucketSetting = new BoolValue("Lava Wait Water Bucket", "岩浆等待水桶", true);
    public final BoolValue debugSetting = new BoolValue("Debug", "调试", true);
    public final BoolValue moveFixSetting = new BoolValue("MovementFix", "移动修复", true);

    private final TimerUtil placementTimer = new TimerUtil();
    private final TimerUtil swapTimer = new TimerUtil();
    private boolean pendingSwapBack = false;
    private Phase placementPhase = Phase.IDLE;
    private PlacementInfo currentPlacement;
    private PlacementType activePlacementType;
    private Entity target;
    private BlockPos targetBlockPos;
    private int waitTicks;
    private int retrieveTicks;
    private int retrieveAttempts;
    private int lavaReapplyCount;
    private int sourceWaitCount;
    private boolean lavaPlaced;
    private boolean retriedLavaPlace;
    private GroundWebData currentGroundWebData;
    private GroundWebPhase currentGroundWebPhase = GroundWebPhase.IDLE;
    private BlockPos groundWebFootPos;
    private int groundWebBreakTicks;
    private boolean groundWebBreakStarted;
    private BlockPos lastDebugSupportPos;
    private Direction lastDebugFace;
    private Vec3d lastDebugAimPoint;

    public AutoWebPlace() {
        super("AutoWebPlace", "自动放网", Category.Player);
    }

    @Override
    protected void onEnable() {
        if (this.swapModeSetting.is(SwapMode.Silent)) {
            ItemSpoofUtils.startSpoof();
        }
        this.resetState();
    }

    @Override
    protected void onDisable() {
        if (this.pendingSwapBack) {
            InvUtil.swapBack();
            this.pendingSwapBack = false;
        }
        if (ItemSpoofUtils.isSpoofing) {
            ItemSpoofUtils.stopSpoof();
        }
        this.resetState();
    }

    private void resetState() {
        this.placementPhase = Phase.IDLE;
        targetRotation = null;
        this.currentPlacement = null;
        this.activePlacementType = null;
        this.target = null;
        this.targetBlockPos = null;
        this.waitTicks = 0;
        this.retrieveTicks = 0;
        this.retrieveAttempts = 0;
        this.lavaReapplyCount = 0;
        this.sourceWaitCount = 0;
        this.lavaPlaced = false;
        this.retriedLavaPlace = false;
        this.currentGroundWebData = null;
        this.currentGroundWebPhase = GroundWebPhase.IDLE;
        this.groundWebFootPos = null;
        this.groundWebBreakTicks = 0;
        this.groundWebBreakStarted = false;
        this.lastDebugSupportPos = null;
        this.lastDebugFace = null;
        this.lastDebugAimPoint = null;
        this.pendingSwapBack = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck() || mc.interactionManager == null) {
            return;
        }
        if (this.pendingSwapBack && this.swapTimer.passedMillise(200)) {
            InvUtil.swapBack();
            this.pendingSwapBack = false;
        }
        if (!this.isPhaseActive() && this.isRotationBlocked()) {
            this.resetState();
            return;
        }
        switch (this.placementPhase) {
            case LAVA_RETRIEVE -> this.doLavaRetrieve();
            case GROUND_WEB_BREAK -> this.doGroundWebBreak();
            case IDLE -> this.tickSearch();
            case PLACE -> this.tickPlace();
        }
    }

    private void tickSearch() {
        if (!this.findCobweb().found() && (!this.lavaSetting.get() || !this.findLavaBucket().found())) {
            return;
        }
        if (!this.placementTimer.delay(this.delaySetting.get())) {
            return;
        }
        Optional<PlacementTarget> targetOpt = this.findPlacementTarget();
        if (targetOpt.isEmpty()) {
            return;
        }
        PlacementTarget placementTarget = targetOpt.get();
        this.target = placementTarget.target;
        this.currentPlacement = placementTarget.placement;
        this.activePlacementType = placementTarget.type;
        this.targetBlockPos = placementTarget.placement.place;
        this.lavaReapplyCount = 0;
        if (placementTarget.groundWebData != null) {
            this.applyGroundWebData(placementTarget.groundWebData);
        }
        targetRotation = RotationUtil.calculate(this.currentPlacement.aim);
        Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
        this.placementPhase = Phase.PLACE;
    }

    private void tickPlace() {
        if (!this.isPlacementValid()) {
            this.resetState();
            return;
        }
        if (!this.executePlacement()) {
            this.resetState();
            return;
        }
        if (this.isGroundWebMode()) {
            if (!this.advanceGroundWebBreak()) {
                this.placementTimer.reset();
            }
            return;
        }
        if (this.activePlacementType == PlacementType.WEB_EXIT_PREDICT) {
            this.placementTimer.reset();
            this.resetState();
            return;
        }
        if (this.activePlacementType == PlacementType.LAVA) {
            this.placementTimer.reset();
            targetRotation = RotationUtil.calculate(this.currentPlacement.support);
            Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
            this.waitTicks = 1;
            this.retrieveTicks = 0;
            this.retrieveAttempts = 0;
            this.sourceWaitCount = 0;
            this.lavaPlaced = false;
            this.retriedLavaPlace = false;
            this.placementPhase = Phase.LAVA_RETRIEVE;
            return;
        }
        if (!this.tryChainLava()) {
            this.placementTimer.reset();
            this.resetState();
        } else {
            this.placementPhase = Phase.PLACE;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!this.renderSetting.get() || (this.targetBlockPos == null && this.lastDebugSupportPos == null) || mc.gameRenderer == null) {
            return;
        }
        Color tint = this.activePlacementType == PlacementType.LAVA ? new Color(255, 100, 0) : new Color(255, 255, 255);
        if (this.targetBlockPos != null) {
            Box box = new Box(this.targetBlockPos);
            Render3DUtil.drawFilledBox(event.getMatrices(), box, new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 64).getRGB());
            Render3DUtil.drawOutlineBox(event.getMatrices(), box, new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 191).getRGB(), 2f);
        }
        if (this.currentPlacement != null) {
            this.renderFaceHighlight(event.getMatrices(), this.currentPlacement.support, this.currentPlacement.face, new Color(255, 230, 0));
            this.renderPoint(event.getMatrices(), this.currentPlacement.aim, new Color(255, 230, 0));
        } else if (this.lastDebugSupportPos != null && this.lastDebugFace != null) {
            this.renderFaceHighlight(event.getMatrices(), this.lastDebugSupportPos, this.lastDebugFace, new Color(255, 40, 40));
            if (this.lastDebugAimPoint != null) {
                this.renderPoint(event.getMatrices(), this.lastDebugAimPoint, new Color(255, 40, 40));
            }
        }
    }

    private void renderFaceHighlight(net.minecraft.client.util.math.MatrixStack matrices, BlockPos pos, Direction face, Color color) {
        Box faceBox = this.getFaceBox(pos, face);
        Render3DUtil.drawFilledBox(matrices, faceBox, new Color(color.getRed(), color.getGreen(), color.getBlue(), 115).getRGB());
        Render3DUtil.drawOutlineBox(matrices, faceBox, new Color(color.getRed(), color.getGreen(), color.getBlue(), 242).getRGB(), 2f);
    }

    private void renderPoint(net.minecraft.client.util.math.MatrixStack matrices, Vec3d point, Color color) {
        double r = 0.04;
        Box box = new Box(point.x - r, point.y - r, point.z - r, point.x + r, point.y + r, point.z + r);
        Render3DUtil.drawFilledBox(matrices, box, new Color(color.getRed(), color.getGreen(), color.getBlue(), 217).getRGB());
    }

    private Box getFaceBox(BlockPos pos, Direction face) {
        double minX = pos.getX();
        double minY = pos.getY();
        double minZ = pos.getZ();
        double maxX = minX + 1.0;
        double maxY = minY + 1.0;
        double maxZ = minZ + 1.0;
        double t = 0.002;
        return switch (face) {
            case UP -> new Box(minX, maxY - t, minZ, maxX, maxY + t, maxZ);
            case DOWN -> new Box(minX, minY - t, minZ, maxX, minY + t, maxZ);
            case EAST -> new Box(maxX - t, minY, minZ, maxX + t, maxY, maxZ);
            case WEST -> new Box(minX - t, minY, minZ, minX + t, maxY, maxZ);
            case SOUTH -> new Box(minX, minY, maxZ - t, maxX, maxY, maxZ + t);
            case NORTH -> new Box(minX, minY, minZ - t, maxX, maxY, minZ + t);
        };
    }

    private Optional<PlacementTarget> findPlacementTarget() {
        if (mc.world == null || mc.player == null) {
            return Optional.empty();
        }
        float range = this.rangeSetting.get().floatValue();
        List<Entity> candidates = new ArrayList<>();
        for (Entity e : mc.world.getEntities()) {
            if (e instanceof PlayerEntity && e != mc.player) {
                if (e.distanceTo(mc.player) <= range) {
                    if (Managers.COMBAT.isEnemy((LivingEntity) e, range)) {
                        candidates.add(e);
                    }
                }
            }
        }
        candidates.sort(Comparator.comparingDouble(e -> e.distanceTo(mc.player)));
        FindItemResult cobwebItem = this.findCobweb();
        FindItemResult lavaItem = this.lavaSetting.get() ? this.findLavaBucket() : new FindItemResult(-1, 0, 0);

        for (Entity entity : candidates) {
            boolean inCobweb = this.isEntityInCobweb(entity);
            if (inCobweb) {
                Optional<PlacementTarget> webExit = this.findWebExitPlacement(entity, cobwebItem);
                if (webExit.isPresent()) {
                    return webExit;
                }
                this.debugLog("Skip cobweb because target stays in cobweb: " + entity.getName().getString());
            }

            if (!inCobweb && this.groundWebSetting.get() && cobwebItem.found()) {
                Optional<GroundWebData> groundWeb = this.findGroundWebPlacement(entity);
                if (groundWeb.isPresent()) {
                    GroundWebData data = groundWeb.get();
                    this.debugLog("Ground web target=" + entity.getName().getString()
                            + " foot=" + this.formatBlockPos(data.footPos)
                            + " head=" + this.formatBlockPos(data.headPos));
                    return Optional.of(new PlacementTarget(entity, data.footPlacement, PlacementType.GROUND_WEB, data));
                }
            }

            Optional<PlacementInfo> cobwebPlace = !inCobweb && cobwebItem.found() ? this.findLavaPlacement(entity) : Optional.empty();
            if (cobwebPlace.isPresent()) {
                PlacementInfo info = cobwebPlace.get();
                this.debugLog("Cobweb target=" + entity.getName().getString()
                        + " place=" + this.formatBlockPos(info.place)
                        + " support=" + this.formatBlockPos(info.support)
                        + " face=" + info.face
                        + " aim=" + this.formatVec3(info.aim));
                return Optional.of(new PlacementTarget(entity, info, PlacementType.COBWEB, null));
            }

            if (lavaItem.found() && this.shouldPlaceLavaEx(entity, inCobweb)) {
                Optional<PlacementInfo> lavaPlace = this.findLavaSourcePlacement(entity);
                if (lavaPlace.isPresent()) {
                    PlacementInfo info = lavaPlace.get();
                    this.debugLog("Lava target=" + entity.getName().getString()
                            + " place=" + this.formatBlockPos(info.place)
                            + " support=" + this.formatBlockPos(info.support)
                            + " face=" + info.face
                            + " aim=" + this.formatVec3(info.aim));
                    return Optional.of(new PlacementTarget(entity, info, PlacementType.LAVA, null));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<PlacementTarget> findWebExitPlacement(Entity entity, FindItemResult cobwebItem) {
        if (!cobwebItem.found()) {
            return Optional.empty();
        }
        Vec3d movement = this.getEntityMovement(entity);
        if (movement.x * movement.x + movement.z * movement.z < 4.0e-4) {
            return Optional.empty();
        }
        for (int tickAhead = 2; tickAhead <= 3; tickAhead++) {
            Box predicted = entity.getBoundingBox().offset(movement.x * tickAhead, 0.0, movement.z * tickAhead);
            if (this.isAllCobweb(predicted)) {
                continue;
            }
            Optional<PlacementTarget> placement = this.tryPlacements(entity, this.getPositionsForBox(predicted, tickAhead), movement);
            if (placement.isPresent()) {
                return placement;
            }
        }
        return Optional.empty();
    }

    private Optional<PlacementTarget> tryPlacements(Entity entity, List<PredictionTick> ticks, Vec3d movement) {
        for (PredictionTick tick : ticks) {
            Optional<PlacementInfo> place = this.findPlacementForTick(entity, tick);
            if (place.isPresent()) {
                PlacementInfo info = place.get();
                this.debugLog("Predicted web exit target=" + entity.getName().getString()
                        + " ticks=" + tick.ticks
                        + " place=" + this.formatBlockPos(info.place)
                        + " support=" + this.formatBlockPos(info.support)
                        + " face=" + info.face
                        + " move=" + this.formatVec3(movement));
                return Optional.of(new PlacementTarget(entity, info, PlacementType.WEB_EXIT_PREDICT, null));
            }
            Optional<GroundWebData> groundWeb = this.findGroundWebAt(entity, tick.footPos, tick.headPos, "predicted web exit");
            if (groundWeb.isPresent()) {
                GroundWebData data = groundWeb.get();
                this.debugLog("Predicted web exit ground web target=" + entity.getName().getString()
                        + " ticks=" + tick.ticks
                        + " foot=" + this.formatBlockPos(data.footPos)
                        + " head=" + this.formatBlockPos(data.headPos)
                        + " move=" + this.formatVec3(movement));
                return Optional.of(new PlacementTarget(entity, data.footPlacement, PlacementType.GROUND_WEB, data));
            }
        }
        return Optional.empty();
    }

    private Optional<PlacementInfo> findPlacementForTick(Entity entity, PredictionTick tick) {
        if (mc.world.getBlockState(tick.headPos).isOf(Blocks.COBWEB)) {
            return Optional.empty();
        }
        return this.findLavaPlacementAt(entity, tick.headPos);
    }

    private List<PredictionTick> getPositionsForBox(Box box, int tickAhead) {
        ArrayList<PredictionTick> list = new ArrayList<>();
        int minX = (int) Math.floor(box.minX);
        int maxX = (int) Math.floor(box.maxX);
        int minY = (int) Math.floor(box.minY);
        int maxY = (int) Math.floor(box.maxY);
        int minZ = (int) Math.floor(box.minZ);
        int maxZ = (int) Math.floor(box.maxZ);
        int footY = (int) Math.floor(box.minY + 0.001);
        double centerX = (box.minX + box.maxX) * 0.5;
        double centerZ = (box.minZ + box.maxZ) * 0.5;
        BlockPos center = BlockPos.ofFloored(centerX, box.minY, centerZ);
        if (this.isBoxIntersecting(box, center)) {
            this.addPositionToList(list, tickAhead, center);
        }
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (box.intersects(new Box(pos)) && !mc.world.getBlockState(pos).isOf(Blocks.COBWEB)) {
                        int chosenY = y <= footY ? footY : y - 1;
                        this.addPositionToList(list, tickAhead, new BlockPos(x, chosenY, z));
                    }
                }
            }
        }
        list.sort(Comparator.comparingDouble(t -> Vec3d.ofCenter(t.footPos).squaredDistanceTo(centerX, footY + 0.5, centerZ)));
        return list;
    }

    private boolean isBoxIntersecting(Box box, BlockPos pos) {
        BlockPos above = pos.up();
        return this.isCobwebIntersecting(box, pos) || this.isCobwebIntersecting(box, above);
    }

    private boolean isCobwebIntersecting(Box box, BlockPos pos) {
        return box.intersects(new Box(pos)) && !mc.world.getBlockState(pos).isOf(Blocks.COBWEB);
    }

    private void addPositionToList(List<PredictionTick> list, int tickAhead, BlockPos foot) {
        BlockPos head = foot.up();
        if (mc.world.getBlockState(foot).isOf(Blocks.COBWEB) && mc.world.getBlockState(head).isOf(Blocks.COBWEB)) {
            return;
        }
        PredictionTick tick = new PredictionTick(tickAhead, foot, head);
        if (!list.contains(tick)) {
            list.add(tick);
        }
    }

    private boolean tryChainLava() {
        if (this.target == null || !this.lavaSetting.get() || !this.findLavaBucket().found()) {
            return false;
        }
        if (!this.shouldPlaceLava(this.target)) {
            return false;
        }
        Optional<PlacementInfo> placement = this.findLavaSourcePlacement(this.target);
        if (placement.isEmpty()) {
            this.debugLog("Chain lava skipped: no placement target=" + this.target.getName().getString());
            return false;
        }
        this.lavaReapplyCount = 0;
        this.applyLavaPlacement(placement.get());
        this.debugLog("Chain lava after cobweb target=" + this.target.getName().getString()
                + " place=" + this.formatBlockPos(this.currentPlacement.place)
                + " support=" + this.formatBlockPos(this.currentPlacement.support)
                + " face=" + this.currentPlacement.face
                + " aim=" + this.formatVec3(this.currentPlacement.aim));
        return true;
    }

    private void applyLavaPlacement(PlacementInfo placement) {
        this.currentPlacement = placement;
        this.activePlacementType = PlacementType.LAVA;
        this.targetBlockPos = placement.place;
        targetRotation = RotationUtil.calculate(placement.aim);
        Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
    }

    private void applyGroundWebData(GroundWebData data) {
        this.currentGroundWebData = data;
        this.currentGroundWebPhase = GroundWebPhase.FOOT_PLACED;
        this.groundWebFootPos = data.footPos;
        this.groundWebBreakTicks = 0;
        this.groundWebBreakStarted = false;
    }

    private boolean isGroundWebMode() {
        return this.activePlacementType == PlacementType.GROUND_WEB;
    }

    private boolean advanceGroundWebBreak() {
        if (this.currentGroundWebData == null) {
            this.resetState();
            return false;
        }
        switch (this.currentGroundWebPhase) {
            case FOOT_PLACED -> {
                this.currentGroundWebPhase = GroundWebPhase.HEAD_PLACED;
                this.applyPlacement(this.currentGroundWebData.headPlacement, PlacementType.GROUND_WEB);
                return true;
            }
            case HEAD_PLACED -> {
                this.startGroundWebBreak();
                return true;
            }
            default -> {
                this.resetState();
                return false;
            }
        }
    }

    private void applyPlacement(PlacementInfo placement, PlacementType type) {
        this.currentPlacement = placement;
        this.activePlacementType = type;
        this.targetBlockPos = placement.place;
        targetRotation = RotationUtil.calculate(placement.aim);
        Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
    }

    private void startGroundWebBreak() {
        if (!this.findSword().found()) {
            this.debugLog("Ground web break skipped: no sword");
            this.resetState();
            return;
        }
        this.currentPlacement = null;
        this.activePlacementType = null;
        this.targetBlockPos = this.groundWebFootPos;
        targetRotation = RotationUtil.calculate(Vec3d.ofCenter(this.groundWebFootPos));
        Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
        this.groundWebBreakTicks = 0;
        this.groundWebBreakStarted = false;
        this.placementPhase = Phase.GROUND_WEB_BREAK;
    }

    private boolean shouldPlaceLava(Entity entity) {
        return this.shouldPlaceLavaEx(entity, false);
    }

    private boolean shouldPlaceLavaEx(Entity entity, boolean targetInCobweb) {
        if (!this.lavaSetting.get()) {
            return false;
        }
        if (entity.isOnFire()) {
            this.debugLog("Skip lava because target is on fire: " + entity.getName().getString());
            return false;
        }
        boolean inWeb = targetInCobweb && this.isEntityInCobweb(entity);
        if (!inWeb && !this.isEntityGrounded(entity)) {
            this.debugLog("Skip lava because target is not grounded: " + entity.getName().getString()
                    + " y=" + String.format("%.2f", entity.getY())
                    + " motion=" + this.formatVec3(entity.getVelocity()));
            return false;
        }
        if (!inWeb && this.isEntityMoving(entity)) {
            this.debugLog("Skip lava because target is moving: " + entity.getName().getString()
                    + " move=" + this.formatVec3(this.getEntityMovement(entity)));
            return false;
        }
        return true;
    }

    private boolean isEntityMoving(Entity entity) {
        Vec3d movement = this.getEntityMovement(entity);
        return movement.x * movement.x + movement.z * movement.z >= 0.01;
    }

    private Vec3d getEntityMovement(Entity entity) {
        return new Vec3d(entity.getX() - entity.lastX, 0.0, entity.getZ() - entity.lastZ);
    }

    private boolean isEntityGrounded(Entity entity) {
        double yFrac = entity.getY() - Math.floor(entity.getY());
        double yDistance = Math.min(yFrac, 1.0 - yFrac);
        return entity.isOnGround() || (yDistance < 0.08 && Math.abs(entity.getVelocity().y) < 0.12);
    }

    private Optional<PlacementInfo> findLavaPlacement(Entity entity) {
        for (BlockPos pos : this.getLavaPositions(entity)) {
            Optional<PlacementInfo> placement = this.findLavaPlacementAt(entity, pos);
            if (placement.isPresent()) {
                return placement;
            }
        }
        return Optional.empty();
    }

    private Optional<PlacementInfo> findLavaPlacementAt(Entity entity, BlockPos place) {
        if (!mc.world.getBlockState(place).isReplaceable()) {
            return Optional.empty();
        }
        if (this.intersectsPlayer(place)) {
            return Optional.empty();
        }
        Vec3d eye = mc.player.getEyePos();
        double dx = entity.getX() - eye.x;
        double dz = entity.getZ() - eye.z;
        Direction nearest = Direction.getFacing((float) dx, 0.0f, (float) dz);
        List<Direction> directions = new ArrayList<>();
        directions.add(nearest);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (direction != nearest) {
                directions.add(direction);
            }
        }
        directions.add(Direction.DOWN);
        directions.add(Direction.UP);

        for (Direction direction : directions) {
            BlockPos supportPos = place.offset(direction);
            Direction face = direction.getOpposite();
            Optional<Vec3d> hit = this.findFaceHitPoint(supportPos, face);
            if (hit.isEmpty()) continue;
            return Optional.of(new PlacementInfo(supportPos, face, hit.get(), place));
        }
        return Optional.empty();
    }

    private List<BlockPos> getLavaPositions(Entity entity) {
        ArrayList<BlockPos> list = new ArrayList<>();
        this.addBlockPosToList(list, BlockPos.ofFloored(entity.getX(), entity.getY() + 1.0, entity.getZ()));
        Box box = entity.getBoundingBox();
        int minX = (int) Math.floor(box.minX - 0.45);
        int maxX = (int) Math.floor(box.maxX + 0.45);
        int minZ = (int) Math.floor(box.minZ - 0.45);
        int maxZ = (int) Math.floor(box.maxZ + 0.45);
        int headY = (int) Math.floor(entity.getY() + 1.0);
        int topY = (int) Math.floor(box.maxY - 0.05);
        for (int y = Math.min(headY, topY); y <= Math.max(headY, topY); y++) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    this.addBlockPosToList(list, new BlockPos(x, y, z));
                }
            }
        }
        return list;
    }

    private void addBlockPosToList(List<BlockPos> list, BlockPos pos) {
        if (!list.contains(pos)) {
            list.add(pos);
        }
    }

    private Optional<GroundWebData> findGroundWebPlacement(Entity entity) {
        if (!this.canPlaceGroundWeb(entity)) {
            return Optional.empty();
        }
        if (!this.findSword().found()) {
            this.debugLog("Ground web skipped: no sword");
            return Optional.empty();
        }
        BlockPos foot = BlockPos.ofFloored(entity.getX(), entity.getY(), entity.getZ());
        return this.findGroundWebAt(entity, foot, foot.up(), "ground web");
    }

    private Optional<GroundWebData> findGroundWebAt(Entity entity, BlockPos footPos, BlockPos headPos, String label) {
        if (!this.isBlockReplaceable(footPos) || !this.isBlockReplaceable(headPos)) {
            return Optional.empty();
        }
        Optional<PlacementInfo> footPlacement = this.raycastPlacement(footPos.down(), footPos, entity.getX(), entity.getZ(), label + " foot");
        if (footPlacement.isEmpty()) {
            return Optional.empty();
        }
        PlacementInfo headPlacement = this.createPlacementInfo(footPos, headPos);
        return Optional.of(new GroundWebData(footPos, headPos, footPlacement.get(), headPlacement));
    }

    private boolean canPlaceGroundWeb(Entity entity) {
        if (!this.isEntityGrounded(entity)) {
            this.debugLog("Skip ground web because target is not grounded: " + entity.getName().getString()
                    + " y=" + String.format("%.2f", entity.getY())
                    + " motion=" + this.formatVec3(entity.getVelocity()));
            return false;
        }
        if (this.isEntityMoving(entity)) {
            this.debugLog("Skip ground web because target is moving: " + entity.getName().getString()
                    + " move=" + this.formatVec3(this.getEntityMovement(entity)));
            return false;
        }
        return true;
    }

    private boolean isBlockReplaceable(BlockPos pos) {
        return mc.world.getBlockState(pos).isReplaceable()
                && mc.world.getFluidState(pos).isEmpty()
                && !this.intersectsPlayer(pos);
    }

    private Optional<PlacementInfo> findLavaSourcePlacement(Entity entity) {
        Vec3d foot = new Vec3d(entity.getX(), entity.getY(), entity.getZ());
        Optional<PlacementInfo> placement = this.findLavaGroundPlacement(entity.getX(), entity.getY(), entity.getZ(), "current");
        if (placement.isPresent()) {
            return placement;
        }
        this.debugLog("Lava fail: no lava placement foot=" + this.formatVec3(foot) + " motion=" + this.formatVec3(entity.getVelocity()));
        return Optional.empty();
    }

    private Optional<PlacementInfo> findLavaGroundPlacement(double x, double y, double z, String label) {
        Vec3d start = new Vec3d(x, y + 0.2, z);
        Vec3d end = new Vec3d(x, y - 2.2, z);
        BlockHitResult hit = mc.world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return Optional.empty();
        }
        if (hit.getSide() != Direction.UP) {
            this.debugLog("Lava fail: " + label + " ground probe face not up face=" + hit.getSide()
                    + " support=" + this.formatBlockPos(hit.getBlockPos())
                    + " foot=" + this.formatVec3(new Vec3d(x, y, z)));
            return Optional.empty();
        }
        return this.findAimAtSupport(hit.getBlockPos(), x, z, label);
    }

    private Vec3d clampAimPoint(BlockPos pos, double x, double z) {
        return new Vec3d(
                this.clampValue(x, pos.getX() + 0.1, pos.getX() + 0.9),
                pos.getY() + 0.99,
                this.clampValue(z, pos.getZ() + 0.1, pos.getZ() + 0.9));
    }

    private double clampValue(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Optional<PlacementInfo> findAimAtSupport(BlockPos supportPos, double x, double z, String label) {
        Vec3d aim = this.clampAimPoint(supportPos, x, z);
        return this.aimAndCheckPlacement(aim, supportPos, label);
    }

    private Optional<PlacementInfo> raycastPlacement(BlockPos supportPos, BlockPos placePos, double targetX, double targetZ, String label) {
        if (!this.isValidSupport(supportPos)) {
            return Optional.empty();
        }
        BlockHitResult lastHit = null;
        Vec3d lastAim = null;
        for (Vec3d candidate : this.getAimCandidates(supportPos, targetX, targetZ)) {
            if (mc.player.getEyePos().squaredDistanceTo(candidate) > 25.0) continue;
            Vec3d offset = this.offsetTowardsFace(candidate, Direction.UP);
            BlockHitResult hit = mc.world.raycast(new RaycastContext(mc.player.getEyePos(), offset, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
            if (hit.getType() != HitResult.Type.BLOCK) {
                lastAim = offset;
                continue;
            }
            lastHit = hit;
            lastAim = offset;
            if (hit.getBlockPos().equals(supportPos) && hit.getSide() == Direction.UP) {
                this.lastDebugSupportPos = supportPos;
                this.lastDebugFace = Direction.UP;
                this.lastDebugAimPoint = hit.getPos();
                return Optional.of(new PlacementInfo(supportPos, Direction.UP, hit.getPos(), placePos));
            }
        }
        if (lastHit != null) {
            this.lastDebugSupportPos = lastHit.getBlockPos();
            this.lastDebugFace = lastHit.getSide();
            this.lastDebugAimPoint = lastAim;
            this.debugLog("Placement fail: " + label + " support mismatch expected=" + this.formatBlockPos(supportPos)
                    + " hit=" + this.formatBlockPos(lastHit.getBlockPos())
                    + " face=" + lastHit.getSide()
                    + " aim=" + this.formatVec3(lastAim));
        } else if (lastAim != null) {
            this.lastDebugSupportPos = supportPos;
            this.lastDebugFace = Direction.UP;
            this.lastDebugAimPoint = lastAim;
            this.debugLog("Placement fail: " + label + " ray miss support=" + this.formatBlockPos(supportPos) + " aim=" + this.formatVec3(lastAim));
        }
        return Optional.empty();
    }

    private List<Vec3d> getAimCandidates(BlockPos pos, double targetX, double targetZ) {
        double y = pos.getY() + 1.0;
        ArrayList<Vec3d> list = new ArrayList<>();
        list.add(new Vec3d(pos.getX() + 0.5, y, pos.getZ() + 0.5));
        list.add(new Vec3d(
                this.clampValue(targetX, pos.getX() + 0.25, pos.getX() + 0.75),
                y,
                this.clampValue(targetZ, pos.getZ() + 0.25, pos.getZ() + 0.75)));
        list.add(new Vec3d(pos.getX() + 0.35, y, pos.getZ() + 0.35));
        list.add(new Vec3d(pos.getX() + 0.65, y, pos.getZ() + 0.35));
        list.add(new Vec3d(pos.getX() + 0.35, y, pos.getZ() + 0.65));
        list.add(new Vec3d(pos.getX() + 0.65, y, pos.getZ() + 0.65));
        return list;
    }

    private PlacementInfo createPlacementInfo(BlockPos supportPos, BlockPos placePos) {
        Vec3d aim = new Vec3d(supportPos.getX() + 0.5, supportPos.getY() + 1.0, supportPos.getZ() + 0.5);
        return new PlacementInfo(supportPos, Direction.UP, aim, placePos);
    }

    private Optional<PlacementInfo> aimAndCheckPlacement(Vec3d aim, BlockPos expectedSupport, String label) {
        BlockHitResult hit = mc.world.raycast(new RaycastContext(mc.player.getEyePos(), aim, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return Optional.empty();
        }
        if (hit.getSide() != Direction.UP) {
            this.debugLog("Lava fail: " + label + " face not up face=" + hit.getSide()
                    + " support=" + this.formatBlockPos(hit.getBlockPos())
                    + " aim=" + this.formatVec3(aim));
            return Optional.empty();
        }
        if (expectedSupport != null && !hit.getBlockPos().equals(expectedSupport)) {
            this.debugLog("Lava fail: support ray mismatch expected=" + this.formatBlockPos(expectedSupport)
                    + " hit=" + this.formatBlockPos(hit.getBlockPos())
                    + " aim=" + this.formatVec3(aim));
            return Optional.empty();
        }
        BlockPos supportPos = hit.getBlockPos();
        BlockPos placePos = supportPos.offset(hit.getSide());
        if (this.isWater(placePos)) {
            this.debugLog("Lava fail: " + label + " place is water place=" + this.formatBlockPos(placePos));
            return Optional.empty();
        }
        if (!mc.world.getBlockState(placePos).isReplaceable()) {
            this.debugLog("Lava fail: " + label + " place not replaceable place=" + this.formatBlockPos(placePos));
            return Optional.empty();
        }
        if (this.intersectsPlayer(placePos)) {
            this.debugLog("Lava fail: " + label + " place intersects self place=" + this.formatBlockPos(placePos));
            return Optional.empty();
        }
        if (!mc.world.getFluidState(placePos).isEmpty()) {
            this.debugLog("Lava fail: " + label + " place has fluid place=" + this.formatBlockPos(placePos));
            return Optional.empty();
        }
        if (!this.isValidSupport(supportPos)) {
            this.debugLog("Lava fail: " + label + " support invalid support=" + this.formatBlockPos(supportPos));
            return Optional.empty();
        }
        if (mc.player.getEyePos().squaredDistanceTo(aim) > 25.0) {
            this.debugLog("Lava fail: " + label + " aim too far support=" + this.formatBlockPos(supportPos)
                    + " aim=" + this.formatVec3(aim));
            return Optional.empty();
        }
        return Optional.of(new PlacementInfo(supportPos, hit.getSide(), aim, placePos));
    }

    private Optional<Vec3d> findFaceHitPoint(BlockPos pos, Direction face) {
        for (Vec3d point : this.getFacePoints(pos, face)) {
            if (mc.player.getEyePos().squaredDistanceTo(point) > 25.0) continue;
            Optional<Vec3d> hit = this.raycastFace(pos, face, point);
            if (hit.isPresent()) {
                return hit;
            }
        }
        return Optional.empty();
    }

    private Optional<Vec3d> raycastFace(BlockPos pos, Direction face, Vec3d aim) {
        if (mc.player == null || mc.world == null) {
            return Optional.empty();
        }
        Vec3d offset = this.offsetTowardsFace(aim, face);
        BlockHitResult hit = mc.world.raycast(new RaycastContext(mc.player.getEyePos(), offset, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return Optional.empty();
        }
        return hit.getBlockPos().equals(pos) && hit.getSide() == face ? Optional.of(hit.getPos()) : Optional.empty();
    }

    private Vec3d offsetTowardsFace(Vec3d point, Direction face) {
        double step = 0.01;
        return point.add(-face.getOffsetX() * step, -face.getOffsetY() * step, -face.getOffsetZ() * step);
    }

    private List<Vec3d> getFacePoints(BlockPos pos, Direction face) {
        ArrayList<Vec3d> list = new ArrayList<>();
        this.findFaceCenter(pos, face).ifPresent(list::add);
        Box faceBox = this.getFaceBoundingBox(pos, face);
        if (faceBox == null) {
            return list;
        }
        double[] fractions = {0.5, 0.25, 0.75};
        for (double u : fractions) {
            for (double v : fractions) {
                Vec3d point = switch (face) {
                    case UP -> new Vec3d(pos.getX() + lerp(faceBox.minX, faceBox.maxX, u), pos.getY() + faceBox.maxY, pos.getZ() + lerp(faceBox.minZ, faceBox.maxZ, v));
                    case DOWN -> new Vec3d(pos.getX() + lerp(faceBox.minX, faceBox.maxX, u), pos.getY() + faceBox.minY, pos.getZ() + lerp(faceBox.minZ, faceBox.maxZ, v));
                    case EAST -> new Vec3d(pos.getX() + faceBox.maxX, pos.getY() + lerp(faceBox.minY, faceBox.maxY, u), pos.getZ() + lerp(faceBox.minZ, faceBox.maxZ, v));
                    case WEST -> new Vec3d(pos.getX() + faceBox.minX, pos.getY() + lerp(faceBox.minY, faceBox.maxY, u), pos.getZ() + lerp(faceBox.minZ, faceBox.maxZ, v));
                    case SOUTH -> new Vec3d(pos.getX() + lerp(faceBox.minX, faceBox.maxX, u), pos.getY() + lerp(faceBox.minY, faceBox.maxY, v), pos.getZ() + faceBox.maxZ);
                    case NORTH -> new Vec3d(pos.getX() + lerp(faceBox.minX, faceBox.maxX, u), pos.getY() + lerp(faceBox.minY, faceBox.maxY, v), pos.getZ() + faceBox.minZ);
                };
                this.addUniquePoint(list, point);
            }
        }
        return list;
    }

    private void addUniquePoint(List<Vec3d> list, Vec3d point) {
        for (Vec3d existing : list) {
            if (existing.squaredDistanceTo(point) < 1.0e-6) {
                return;
            }
        }
        list.add(point);
    }

    private Box getFaceBoundingBox(BlockPos pos, Direction face) {
        VoxelShape shape = this.getBlockShape(pos);
        if (shape.isEmpty()) {
            return null;
        }
        Box result = null;
        double bestValue = isPositiveFace(face) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        for (Box box : shape.getBoundingBoxes()) {
            double faceCoord = switch (face) {
                case UP -> box.maxY;
                case DOWN -> box.minY;
                case EAST -> box.maxX;
                case WEST -> box.minX;
                case SOUTH -> box.maxZ;
                case NORTH -> box.minZ;
            };
            boolean better = isPositiveFace(face) ? faceCoord > bestValue : faceCoord < bestValue;
            if (better) {
                bestValue = faceCoord;
                result = box;
            }
        }
        return result;
    }

    private static boolean isPositiveFace(Direction face) {
        return face == Direction.UP || face == Direction.EAST || face == Direction.SOUTH;
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private Optional<Vec3d> findFaceCenter(BlockPos pos, Direction face) {
        VoxelShape shape = this.getBlockShape(pos);
        if (shape.isEmpty()) {
            return Optional.empty();
        }
        List<Box> boxes = shape.getBoundingBoxes();
        if (boxes.isEmpty()) {
            return Optional.empty();
        }
        Box chosen = null;
        double bestValue = isPositiveFace(face) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        for (Box box : boxes) {
            double faceCoord = switch (face) {
                case UP -> box.maxY;
                case DOWN -> box.minY;
                case EAST -> box.maxX;
                case WEST -> box.minX;
                case SOUTH -> box.maxZ;
                case NORTH -> box.minZ;
            };
            boolean better = isPositiveFace(face) ? faceCoord > bestValue : faceCoord < bestValue;
            if (better) {
                bestValue = faceCoord;
                chosen = box;
            }
        }
        if (chosen == null) {
            return Optional.empty();
        }
        double cx = pos.getX() + (chosen.minX + chosen.maxX) * 0.5;
        double cy = pos.getY() + (chosen.minY + chosen.maxY) * 0.5;
        double cz = pos.getZ() + (chosen.minZ + chosen.maxZ) * 0.5;
        switch (face) {
            case UP -> cy = pos.getY() + chosen.maxY;
            case DOWN -> cy = pos.getY() + chosen.minY;
            case EAST -> cx = pos.getX() + chosen.maxX;
            case WEST -> cx = pos.getX() + chosen.minX;
            case SOUTH -> cz = pos.getZ() + chosen.maxZ;
            case NORTH -> cz = pos.getZ() + chosen.minZ;
        }
        return Optional.of(new Vec3d(cx, cy, cz));
    }

    private VoxelShape getBlockShape(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        VoxelShape shape = state.getOutlineShape(mc.world, pos);
        return !shape.isEmpty() ? shape : state.getCollisionShape(mc.world, pos);
    }

    private boolean isValidSupport(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return !state.isReplaceable() && !this.getBlockShape(pos).isEmpty();
    }

    private boolean isPlacementValid() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return false;
        if (this.target == null || !this.target.isAlive() || this.target.isRemoved()) return false;
        if (this.currentPlacement == null) return false;
        if (this.activePlacementType == PlacementType.LAVA) {
            if (!this.shouldPlaceLavaEx(this.target, this.isEntityInCobweb(this.target))) return false;
            if (this.intersectsPlayer(this.currentPlacement.place)) return false;
            if (this.isWater(this.currentPlacement.place)) return false;
            if (!mc.world.getBlockState(this.currentPlacement.place).isReplaceable()) return false;
            if (!mc.world.getFluidState(this.currentPlacement.place).isEmpty()) return false;
            return this.isValidSupport(this.currentPlacement.support);
        }
        if (this.activePlacementType != PlacementType.GROUND_WEB
                && this.activePlacementType != PlacementType.WEB_EXIT_PREDICT
                && this.isEntityInCobweb(this.target)) {
            return false;
        }
        if (this.intersectsPlayer(this.currentPlacement.place)) return false;
        if (!mc.world.getBlockState(this.currentPlacement.place).isReplaceable()) return false;
        if (!mc.world.getFluidState(this.currentPlacement.place).isEmpty()) return false;
        return this.isValidSupport(this.currentPlacement.support);
    }

    private boolean executePlacement() {
        FindItemResult item = this.activePlacementType == PlacementType.LAVA ? this.findLavaBucket() : this.findCobweb();
        if (!item.found()) {
            this.debugLog("Execute fail: item slot missing type=" + this.activePlacementType);
            return false;
        }
        this.swapTo(item);
        if (this.activePlacementType == PlacementType.LAVA) {
            Rotation rotation = RotationUtil.calculate(this.currentPlacement.aim);
            targetRotation = rotation;
            Managers.ROTATION.setRotations(rotation, 180.0, getMovementFix(), Priority.Medium);
            this.debugLog("Lava place use bucket slot=" + item.slot()
                    + " place=" + this.formatBlockPos(this.currentPlacement.place)
                    + " support=" + this.formatBlockPos(this.currentPlacement.support));
            this.useBucket(rotation);
        } else {
            BlockHitResult hit = new BlockHitResult(this.currentPlacement.aim, this.currentPlacement.face, this.currentPlacement.support, false);
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        this.scheduleSwapBack(item);
        return true;
    }

    private boolean useBucket(Rotation rotation) {
        float yaw = mc.player.getYaw();
        float pitch = mc.player.getPitch();
        mc.player.setYaw(rotation.yaw);
        mc.player.setPitch(rotation.pitch);
        PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(rotation.yaw, rotation.pitch, mc.player.isOnGround(), mc.player.horizontalCollision));
        ActionResult result = mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        this.debugLog("Bucket use result=" + result + " yaw=" + rotation.yaw + " pitch=" + rotation.pitch);
        if (result.isAccepted()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
        return result.isAccepted();
    }

    private void doGroundWebBreak() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null || this.groundWebFootPos == null) {
            this.resetState();
            return;
        }
        if (!mc.world.getBlockState(this.groundWebFootPos).isOf(Blocks.COBWEB)) {
            this.debugLog("Ground web break done foot=" + this.formatBlockPos(this.groundWebFootPos));
            if (!this.tryLavaAfterBreak()) {
                this.resetState();
            }
            return;
        }
        FindItemResult swordItem = this.findSword();
        if (!swordItem.found()) {
            this.debugLog("Ground web break abort: no sword foot=" + this.formatBlockPos(this.groundWebFootPos));
            this.resetState();
            return;
        }
        this.swapTo(swordItem);
        targetRotation = RotationUtil.calculate(Vec3d.ofCenter(this.groundWebFootPos));
        Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
        if (!this.groundWebBreakStarted) {
            mc.interactionManager.attackBlock(this.groundWebFootPos, Direction.UP);
            this.groundWebBreakStarted = true;
        } else {
            mc.interactionManager.updateBlockBreakingProgress(this.groundWebFootPos, Direction.UP);
        }
        mc.player.swingHand(Hand.MAIN_HAND);
        this.scheduleSwapBack(swordItem);
        if (++this.groundWebBreakTicks >= 24) {
            this.debugLog("Ground web break abort: timeout foot=" + this.formatBlockPos(this.groundWebFootPos));
            this.resetState();
        }
    }

    private boolean tryLavaAfterBreak() {
        if (this.target == null || !this.lavaSetting.get() || !this.findLavaBucket().found()) return false;
        if (!this.shouldPlaceLavaEx(this.target, true)) return false;
        Optional<PlacementInfo> placement = this.findLavaSourcePlacement(this.target);
        if (placement.isEmpty()) {
            this.debugLog("Ground web lava skipped: no lava placement target=" + this.target.getName().getString());
            return false;
        }
        this.lavaReapplyCount = 0;
        this.applyLavaPlacement(placement.get());
        this.currentGroundWebData = null;
        this.currentGroundWebPhase = GroundWebPhase.IDLE;
        this.groundWebFootPos = null;
        this.groundWebBreakTicks = 0;
        this.groundWebBreakStarted = false;
        this.placementPhase = Phase.PLACE;
        this.debugLog("Ground web lava queued target=" + this.target.getName().getString()
                + " place=" + this.formatBlockPos(this.currentPlacement.place)
                + " support=" + this.formatBlockPos(this.currentPlacement.support)
                + " face=" + this.currentPlacement.face
                + " aim=" + this.formatVec3(this.currentPlacement.aim));
        return true;
    }

    private void doLavaRetrieve() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null || this.currentPlacement == null) {
            this.resetState();
            return;
        }
        this.retrieveTicks++;
        BlockPos expectedSource = this.currentPlacement.place;
        BlockPos actualSource = this.findNearbyLavaSource(expectedSource);
        BlockPos probe = actualSource != null ? actualSource : expectedSource;
        boolean flowingLava = this.hasNearbyFlowingLava(probe);
        boolean targetHasWaterBucket = this.targetHasWaterBucket(this.target);
        boolean timedOut = this.retrieveTicks >= this.getLavaTickDelay();
        boolean readyToRetrieve = targetHasWaterBucket || flowingLava || timedOut;
        boolean shouldAct = !this.lavaWaitWaterBucketSetting.get() || readyToRetrieve;
        if (this.waitTicks > 0 && !readyToRetrieve) {
            this.waitTicks--;
            return;
        }
        if (actualSource != null) {
            this.lavaPlaced = true;
            this.sourceWaitCount = 0;
            this.targetBlockPos = actualSource;
            targetRotation = RotationUtil.calculate(actualSource);
            Managers.ROTATION.setRotations(targetRotation, 180.0, getMovementFix(), Priority.Medium);
            if (!shouldAct) {
                this.debugLog("Lava retrieve wait target water bucket ticks=" + this.retrieveTicks + "/" + this.getLavaTickDelay()
                        + " source=" + this.formatBlockPos(actualSource));
                this.waitTicks = 1;
                return;
            }
            FindItemResult emptyBucketItem = this.findEmptyBucket();
            if (!emptyBucketItem.found()) {
                if (++this.retrieveAttempts >= 8) {
                    this.debugLog("Lava retrieve abort: no empty bucket source=" + this.formatBlockPos(actualSource));
                    this.resetState();
                    return;
                }
                this.debugLog("Lava retrieve wait bucket attempt=" + this.retrieveAttempts
                        + " source=" + this.formatBlockPos(actualSource)
                        + " lavaBucketSlot=" + this.findLavaBucket().slot());
                this.waitTicks = 1;
                return;
            }
            int attempt = this.retrieveAttempts + 1;
            this.debugLog("Lava retrieve use bucket attempt=" + attempt
                    + " bucketSlot=" + emptyBucketItem.slot()
                    + " source=" + this.formatBlockPos(actualSource)
                    + " reason=" + this.getRetrieveReason(targetHasWaterBucket, flowingLava, timedOut));
            this.swapTo(emptyBucketItem);
            this.useBucket(targetRotation);
            this.scheduleSwapBack(emptyBucketItem);
            if (++this.retrieveAttempts >= 8) {
                this.debugLog("Lava retrieve abort: max attempts source=" + this.formatBlockPos(actualSource));
                this.resetState();
            } else {
                this.waitTicks = 2;
            }
            return;
        }
        if (this.lavaPlaced) {
            this.debugLog("Lava retrieve done: source disappeared expected=" + this.formatBlockPos(expectedSource));
            if (!this.tryReapplyLava()) {
                this.resetState();
            }
            return;
        }
        if (++this.sourceWaitCount >= 8) {
            this.debugLog("Lava retrieve abort: source not found expected=" + this.formatBlockPos(expectedSource)
                    + " support=" + this.formatBlockPos(this.currentPlacement.support));
            this.resetState();
            return;
        }
        this.debugLog("Lava retrieve wait source attempt=" + this.sourceWaitCount
                + " expected=" + this.formatBlockPos(expectedSource)
                + " block=" + mc.world.getBlockState(expectedSource).getBlock());
        if (!this.retriedLavaPlace && this.sourceWaitCount >= 2 && this.findLavaBucket().found() && this.isPlacementValid()) {
            this.debugLog("Lava retry place after missing source expected=" + this.formatBlockPos(expectedSource));
            this.retriedLavaPlace = true;
            this.executePlacement();
        }
        this.waitTicks = 1;
    }

    private boolean isLavaSource(BlockPos pos) {
        return mc.world.getBlockState(pos).isOf(Blocks.LAVA) && mc.world.getFluidState(pos).isStill();
    }

    private boolean hasNearbyFlowingLava(BlockPos center) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (this.isFlowingLava(center.add(x, y, z))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean tryReapplyLava() {
        if (this.target == null || this.target.isRemoved() || !this.target.isAlive()) return false;
        if (this.target.isOnFire()) return false;
        if (this.lavaReapplyCount >= 1) return false;
        if (!this.findLavaBucket().found()) return false;
        if (!this.shouldPlaceLavaEx(this.target, true)) return false;
        Optional<PlacementInfo> placement = this.findLavaSourcePlacement(this.target);
        if (placement.isEmpty()) {
            this.debugLog("Lava reapply skipped: no placement target=" + this.target.getName().getString());
            return false;
        }
        this.lavaReapplyCount++;
        this.applyLavaPlacement(placement.get());
        this.placementPhase = Phase.PLACE;
        this.debugLog("Lava reapply queued target=" + this.target.getName().getString()
                + " attempt=" + this.lavaReapplyCount
                + " place=" + this.formatBlockPos(this.currentPlacement.place)
                + " support=" + this.formatBlockPos(this.currentPlacement.support)
                + " face=" + this.currentPlacement.face
                + " aim=" + this.formatVec3(this.currentPlacement.aim));
        return true;
    }

    private int getLavaTickDelay() {
        return mc.world == null ? 9 : Math.max(1, Fluids.LAVA.getTickRate(mc.world) - 1);
    }

    private boolean isFlowingLava(BlockPos pos) {
        FluidState fluidState = mc.world.getFluidState(pos);
        Fluid fluid = fluidState.getFluid();
        return (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) && !fluidState.isStill();
    }

    private boolean targetHasWaterBucket(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return false;
        return player.getMainHandStack().getItem() == Items.WATER_BUCKET
                || player.getOffHandStack().getItem() == Items.WATER_BUCKET;
    }

    private String getRetrieveReason(boolean targetHasWaterBucket, boolean flowing, boolean timedOut) {
        if (flowing) return "flowing";
        if (targetHasWaterBucket) return "target_water_bucket";
        return timedOut ? "before_flow" : "normal";
    }

    private boolean isWater(BlockPos pos) {
        Fluid fluid = mc.world.getFluidState(pos).getFluid();
        return mc.world.getBlockState(pos).isOf(Blocks.WATER) || fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER;
    }

    private boolean intersectsPlayer(BlockPos pos) {
        return mc.player != null && mc.player.getBoundingBox().intersects(new Box(pos));
    }

    private boolean isEntityInCobweb(Entity entity) {
        return entity != null && mc.world != null && this.hasAnyCobweb(entity.getBoundingBox());
    }

    private boolean isAllCobweb(Box box) {
        if (mc.world == null) return false;
        boolean any = false;
        int minX = (int) Math.floor(box.minX);
        int maxX = (int) Math.floor(box.maxX);
        int minY = (int) Math.floor(box.minY);
        int maxY = (int) Math.floor(box.maxY);
        int minZ = (int) Math.floor(box.minZ);
        int maxZ = (int) Math.floor(box.maxZ);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!box.intersects(new Box(pos))) continue;
                    if (!mc.world.getBlockState(pos).isOf(Blocks.COBWEB)) return false;
                    any = true;
                }
            }
        }
        return any;
    }

    private boolean hasAnyCobweb(Box box) {
        if (mc.world == null) return false;
        int minX = (int) Math.floor(box.minX);
        int maxX = (int) Math.floor(box.maxX);
        int minY = (int) Math.floor(box.minY);
        int maxY = (int) Math.floor(box.maxY);
        int minZ = (int) Math.floor(box.minZ);
        int maxZ = (int) Math.floor(box.maxZ);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (mc.world.getBlockState(pos).isOf(Blocks.COBWEB) && box.intersects(new Box(pos))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private BlockPos findNearbyLavaSource(BlockPos center) {
        if (this.isLavaSource(center)) {
            return center;
        }
        BlockPos best = null;
        double bestDistanceSq = Double.POSITIVE_INFINITY;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = center.add(x, y, z);
                    if (!this.isLavaSource(pos)) continue;
                    double distanceSq = Vec3d.ofCenter(center).squaredDistanceTo(Vec3d.ofCenter(pos));
                    if (distanceSq < bestDistanceSq) {
                        best = pos;
                        bestDistanceSq = distanceSq;
                    }
                }
            }
        }
        return best;
    }

    private void debugLog(String message) {
        if (this.debugSetting.get()) {
            ChatUtil.clientMessage("[AutoWebPlace] " + message);
        }
    }

    private String formatBlockPos(BlockPos pos) {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    private String formatVec3(Vec3d vec) {
        return String.format("%.2f,%.2f,%.2f", vec.x, vec.y, vec.z);
    }

    private FindItemResult findCobweb() {
        return InvUtil.find(Items.COBWEB);
    }

    private FindItemResult findLavaBucket() {
        return InvUtil.find(Items.LAVA_BUCKET);
    }

    private FindItemResult findEmptyBucket() {
        return InvUtil.find(Items.BUCKET);
    }

    private FindItemResult findSword() {
        return InvUtil.find(itemStack -> !itemStack.isEmpty() && itemStack.isIn(ItemTags.SWORDS));
    }

    private boolean swapTo(FindItemResult item) {
        if (!item.found()) return false;
        if (!item.isHotbar()) return false;
        switch (this.swapModeSetting.get()) {
            case Normal -> InvUtil.swap(item.slot(), true);
            case Silent -> mc.player.getInventory().setSelectedSlot(item.slot());
        }
        return true;
    }

    private void scheduleSwapBack(FindItemResult item) {
        if (!item.found()) return;
        if (!item.isHotbar()) return;
        switch (this.swapModeSetting.get()) {
            case Normal -> {
                this.pendingSwapBack = true;
                this.swapTimer.reset();
            }
        }
    }

    private MovementFix getMovementFix() {
        return this.moveFixSetting.get() ? MovementFix.NORMAL : MovementFix.OFF;
    }

    private boolean isRotationBlocked() {
        Helper helper = Sakura.MODULES.getModule(Helper.class);
        if (helper != null && helper.isEnabled() && helper.isUsingRotation()) return true;
        return false;
    }

    private boolean isPhaseActive() {
        return this.placementPhase == Phase.LAVA_RETRIEVE
                || this.placementPhase == Phase.GROUND_WEB_BREAK
                || this.currentGroundWebPhase != GroundWebPhase.IDLE
                || this.activePlacementType == PlacementType.GROUND_WEB;
    }

    private enum Phase {
        IDLE,
        PLACE,
        LAVA_RETRIEVE,
        GROUND_WEB_BREAK
    }

    private enum PlacementType {
        COBWEB,
        WEB_EXIT_PREDICT,
        GROUND_WEB,
        LAVA
    }

    private enum GroundWebPhase {
        IDLE,
        FOOT_PLACED,
        HEAD_PLACED
    }

    private static final class PlacementInfo {
        final BlockPos support;
        final Direction face;
        final Vec3d aim;
        final BlockPos place;

        PlacementInfo(BlockPos support, Direction face, Vec3d aim, BlockPos place) {
            this.support = support;
            this.face = face;
            this.aim = aim;
            this.place = place;
        }
    }

    private record PlacementTarget(Entity target, PlacementInfo placement, PlacementType type, GroundWebData groundWebData) {
    }

    private record GroundWebData(BlockPos footPos, BlockPos headPos, PlacementInfo footPlacement, PlacementInfo headPlacement) {
    }

    private record PredictionTick(int ticks, BlockPos footPos, BlockPos headPos) {
    }
}
