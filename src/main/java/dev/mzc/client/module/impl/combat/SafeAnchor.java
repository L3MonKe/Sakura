package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.combat.DamageUtil;
import dev.mzc.client.utils.player.FindItemResult;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.vector.Rotation;
import dev.mzc.client.utils.rotation.RotationUtil;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SafeAnchor extends Module {

    public enum PlaceMode {
        Smart("智能"),
        AnchorSide("锚点侧面");

        private final String cnName;
        PlaceMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<PlaceMode> placeMode = new EnumValue<>("Place Mode", "放置模式", PlaceMode.Smart);
    private final NumberValue<Double> placeRotationSpeed = new NumberValue<>("Place Speed", "放置转向速度", 2.0, 0.1, 15.0, 0.1);
    private final NumberValue<Double> explodeRotationSpeed = new NumberValue<>("Explode Speed", "引爆转向速度", 4.0, 0.1, 15.0, 0.1);
    private final BoolValue dynamicSpeed = new BoolValue("DynamicSpeed", "速度变换", true);
    private final NumberValue<Double> farBoost = new NumberValue<>("FarBoost", "远距离加速%", 20.0, 0.0, 100.0, 1.0, dynamicSpeed::get);
    private final NumberValue<Double> farBoostThreshold = new NumberValue<>("FarBoostThreshold", "加速距离阈值", 5.0, 1.0, 20.0, 0.5, dynamicSpeed::get);
    private final NumberValue<Double> nearReduction = new NumberValue<>("NearReduction", "近距离减速%", 15.0, 0.0, 100.0, 1.0, dynamicSpeed::get);
    private final NumberValue<Double> nearReductionThreshold = new NumberValue<>("NearReductionThreshold", "减速距离阈值", 2.0, 0.1, 10.0, 0.5, dynamicSpeed::get);
    private final BoolValue autoCharge = new BoolValue("AutoCharge", "自动充能", true);
    private final BoolValue autoPlace = new BoolValue("AutoPlace", "自动防御块", true);
    private final BoolValue autoExplode = new BoolValue("AutoExplode", "自动引爆", true);
    private final BoolValue ownAnchorOnly = new BoolValue("OwnAnchorOnly", "仅限自己放置的锚", false);
    private final NumberValue<Double> minHealth = new NumberValue<>("Min Health", "最小安全血量", 4.0, 0.0, 20.0, 0.5);

    private final Set<BlockPos> ownAnchors = Collections.synchronizedSet(new LinkedHashSet<>());

    private BlockPos currentAnchorPos = null;
    private Rotation targetRotation = null;
    private BlockPos targetActionPos = null; // 待执行动作的方块位置
    private Direction targetPlaceSide = null; // 放置时的交互面
    private boolean isSidePlacement = false; // 是否正在执行侧面交互放置
    private boolean isDiagonalPlacement = false;
    private boolean explodeNoRotate = false;
    private double currentRotationSpeed = 2.0;
    private int stage = 0; // 0: None, 1: Charging, 2: RotatingToPlace, 3: RotatingToExplode
    private int delay = 0;
    private int originalSlot = -1;

    public SafeAnchor() {
        super("SafeAnchor", "安全重生锚", Category.Combat);
    }

    @Override
    public void onEnable() {
        stage = 0;
        delay = 0;
        currentAnchorPos = null;
        ownAnchors.clear();
        targetRotation = null;
        targetActionPos = null;
        targetPlaceSide = null;
        isSidePlacement = false;
        explodeNoRotate = false;
        originalSlot = -1;
    }

    @Override
    public void onDisable() {
        ownAnchors.clear();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;

        Packet<?> packet = event.getPacket();

        // 记录发送的放置包
        if (event.getType() == EventType.SEND && packet instanceof PlayerInteractBlockC2SPacket interactPacket) {
            if (mc.player.getStackInHand(interactPacket.getHand()).isOf(Items.RESPAWN_ANCHOR)) {
                BlockHitResult hit = interactPacket.getBlockHitResult();
                BlockPos pos = hit.getBlockPos();
                BlockState state = mc.world.getBlockState(pos);

                // 核心逻辑改进：同时记录点击位置和偏移位置
                // 1. 如果点击的是可替换方块（如火焰、草丛），锚点就在 pos
                // 2. 如果点击的是普通方块，锚点在 pos.offset(side)
                // 3. 如果点击的已经是锚点（可能是预测或充电），记录 pos

                if (state.isOf(Blocks.RESPAWN_ANCHOR) || state.isReplaceable()) {
                    ownAnchors.add(pos);
                } else {
                    BlockPos offsetPos = pos.offset(hit.getSide());
                    ownAnchors.add(offsetPos);

                    // 额外记录：防止某些特殊情况（如服务器判定 pos 为可替换但客户端不认为）
                    if (state.isOf(Blocks.FIRE) || state.isOf(Blocks.SOUL_FIRE)) {
                        ownAnchors.add(pos);
                    }
                }

                if (ownAnchors.size() > 30) { // 稍微增加容量
                    synchronized (ownAnchors) {
                        BlockPos oldest = ownAnchors.iterator().next();
                        ownAnchors.remove(oldest);
                    }
                }
            }
        }

        // 监听服务器回传的方块更新包，进行双重验证
        if (event.getType() == EventType.RECEIVE && packet instanceof BlockUpdateS2CPacket updatePacket) {
            if (updatePacket.getState().isOf(Blocks.RESPAWN_ANCHOR)) {
                // 如果后续需要补录逻辑，可以在这里添加
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        if (delay > 0) {
            delay--;
            return;
        }

        // 检测准星是否对准重生锚
        HitResult hit = mc.crosshairTarget;
        if (stage == 0) {
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos pos = blockHit.getBlockPos();
                if (mc.world.getBlockState(pos).isOf(Blocks.RESPAWN_ANCHOR)) {
                    // 如果开启了 OwnAnchorOnly，检查是否是自己放置的
                    if (ownAnchorOnly.get() && !ownAnchors.contains(pos)) {
                        return;
                    }

                    // 检查背包是否有萤石
                    FindItemResult glowstone = InvUtil.find(Items.GLOWSTONE);
                    if (!glowstone.found()) {
                        return;
                    }

                    currentAnchorPos = pos;
                    originalSlot = mc.player.getInventory().selectedSlot;
                    stage = 1;
                }
            }
        }

        if (currentAnchorPos == null) return;

        switch (stage) {
            case 1: // 充能
                if (autoCharge.get()) {
                    handleCharge();
                } else {
                    preparePlace();
                }
                break;
            case 2: // 转向并放置防御块
                if (autoPlace.get()) {
                    handleRotatingToPlace();
                } else {
                    prepareExplode();
                }
                break;
            case 3: // 转向并引爆
                if (autoExplode.get()) {
                    handleRotatingToExplode();
                } else {
                    resetState();
                }
                break;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (mc.player == null || targetRotation == null) return;

        if (isSidePlacement && targetPlaceSide != null && isLookingAtAnchorSide(targetPlaceSide)) {
            targetRotation = null;
            return;
        }

        smoothAim(targetRotation, event.getTickDelta());

        float yawDiff = Math.abs(MathHelper.wrapDegrees(targetRotation.yaw - mc.player.getYaw()));
        float pitchDiff = Math.abs(targetRotation.pitch - mc.player.getPitch());
        if (yawDiff < 1.0f && pitchDiff < 1.0f) {
            targetRotation = null;
        }
    }

    private void smoothAim(Rotation targetRotation, float tickDelta) {
        float currentYaw = mc.player.getYaw();
        float currentPitch = mc.player.getPitch();

        float targetYaw = targetRotation.yaw;
        float targetPitch = targetRotation.pitch;

        // 平滑逻辑
        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = targetPitch - currentPitch;

        // 速度限制
        double aimSpeed = currentRotationSpeed * 0.5;

        if (dynamicSpeed.get()) {
            double totalDiff = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);
            if (totalDiff > farBoostThreshold.get()) {
                aimSpeed *= (1.0 + farBoost.get() / 100.0);
            } else if (totalDiff < nearReductionThreshold.get()) {
                aimSpeed *= (1.0 - nearReduction.get() / 100.0);
            }
        }

        aimSpeed += ThreadLocalRandom.current().nextDouble(-0.1, 0.1);
        aimSpeed = Math.max(0.1, aimSpeed);

        float yawChange = (float) MathHelper.clamp(yawDiff, -aimSpeed, aimSpeed);
        float pitchChange = (float) MathHelper.clamp(pitchDiff, -aimSpeed, aimSpeed);

        // GCD 修复
        float sens = (float) (mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2);
        float gcd = sens * sens * sens * 8.0f * 0.15f;

        yawChange = Math.round(yawChange / gcd) * gcd;
        pitchChange = Math.round(pitchChange / gcd) * gcd;

        mc.player.setYaw(currentYaw + yawChange);
        mc.player.setPitch(currentPitch + pitchChange);
    }

    private Rotation getTargetRotation(Vec3d targetPos) {
        Rotation rot = RotationUtil.calculate(targetPos);

        // 随机偏移逻辑 (约10像素偏移)
        // 10像素在标准FOV下大约对应 0.1 到 0.2 度
        float yawOffset = (float) ThreadLocalRandom.current().nextDouble(-0.15, 0.15);
        float pitchOffset = (float) ThreadLocalRandom.current().nextDouble(-0.15, 0.15);

        return new Rotation(rot.yaw + yawOffset, rot.pitch + pitchOffset);
    }

    private void handleCharge() {
        int charges = mc.world.getBlockState(currentAnchorPos).get(RespawnAnchorBlock.CHARGES);
        if (placeMode.get() == PlaceMode.AnchorSide && isTooCloseToAnchor()) {
            BlockPos placePos = findPlacePos();
            if (placePos == null) {
                if (charges <= 0) {
                    FindItemResult glowstone = InvUtil.findInHotbar(Items.GLOWSTONE);
                    if (glowstone.found()) {
                        InvUtil.swap(glowstone.slot(), false);
                        interactBlock(currentAnchorPos, Direction.UP);
                        delay = 1;
                        explodeNoRotate = true;
                        prepareExplode();
                        return;
                    }
                } else {
                    explodeNoRotate = true;
                    prepareExplode();
                    return;
                }
            }
        }
        if (placeMode.get() == PlaceMode.Smart && isTooCloseToAnchor()) {
            BlockPos placePos = findPlacePos();
            if (placePos == null) {
                if (charges <= 0) {
                    FindItemResult glowstone = InvUtil.findInHotbar(Items.GLOWSTONE);
                    if (glowstone.found()) {
                        InvUtil.swap(glowstone.slot(), false);
                        interactBlock(currentAnchorPos, Direction.UP);
                        delay = 1;
                        prepareExplode();
                        return;
                    }
                } else {
                    prepareExplode();
                    return;
                }
            }
        }
        if (shouldExplodeNow()) {
            prepareExplode();
            return;
        }
        if (charges < 4) {
            FindItemResult glowstone = InvUtil.findInHotbar(Items.GLOWSTONE);
            if (glowstone.found()) {
                InvUtil.swap(glowstone.slot(), false);
                interactBlock(currentAnchorPos, Direction.UP);
                delay = 1;
            } else {
                resetState();
                return;
            }
        }

        if (autoPlace.get() && placeMode.get() == PlaceMode.AnchorSide) {
            BlockPos placePos = findPlacePos();

            if (isDiagonalPlacement) {
                preparePlace();
                return;
            }

            if (placePos != null) {
                FindItemResult block = InvUtil.findInHotbar(Items.GLOWSTONE, Items.RESPAWN_ANCHOR);
                if (block.found()) {
                    InvUtil.swap(block.slot(), false);
                    if (isSidePlacement && targetPlaceSide != null) {
                        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                        interactBlock(currentAnchorPos, targetPlaceSide);
                        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
                    } else {
                        interactBlock(placePos.down(), Direction.UP);
                    }
                    prepareExplode();
                    return;
                }
            }

            if (isExplosionSafe()) {
                prepareExplode();
            } else {
                resetState();
            }
        } else {
            preparePlace();
        }
    }

    private boolean shouldExplodeNow() {
        if (!autoExplode.get()) return false;
        if (!isTooCloseToAnchor()) return false;
        if (isExplosionSafe()) return true;
        return ownAnchors.contains(currentAnchorPos);
    }

    private boolean isTooCloseToAnchor() {
        double dx = mc.player.getX() - (currentAnchorPos.getX() + 0.5);
        double dz = mc.player.getZ() - (currentAnchorPos.getZ() + 0.5);
        return Math.sqrt(dx * dx + dz * dz) < 1.7;
    }

    private void preparePlace() {
        if (!autoPlace.get()) {
            if (isExplosionSafe()) {
                prepareExplode();
            } else {
                resetState();
            }
            return;
        }

        BlockPos placePos = findPlacePos();

        if (placePos != null) {
            FindItemResult block = InvUtil.findInHotbar(Items.GLOWSTONE, Items.RESPAWN_ANCHOR);
            if (block.found()) {
                InvUtil.swap(block.slot(), false);
                targetActionPos = placePos;
                currentRotationSpeed = placeRotationSpeed.get();

                // 如果是侧面交互放置，转向目标是重生锚本身，但偏向侧面
                if (isSidePlacement && targetPlaceSide != null) {
                    // 如果玩家视角已经在目标侧面，则不旋转视角
                    if (isLookingAtAnchorSide(targetPlaceSide)) {
                        targetRotation = null;
                    } else {
                        Vec3d sideVec = currentAnchorPos.toCenterPos().add(
                                targetPlaceSide.getOffsetX() * 0.45,
                                targetPlaceSide.getOffsetY() * 0.45,
                                targetPlaceSide.getOffsetZ() * 0.45
                        );
                        targetRotation = getTargetRotation(sideVec);
                    }
                } else {
                    targetRotation = getTargetRotation(placePos.toCenterPos());
                }

                stage = 2;
                return;
            }
        }

        // 没放置位置就检测重生锚引爆是否会杀死自己
        if (isExplosionSafe()) {
            prepareExplode();
        } else {
            resetState();
        }
    }

    private BlockPos findPlacePos() {
        isSidePlacement = false;
        isDiagonalPlacement = false;
        if (placeMode.get() == PlaceMode.AnchorSide) {
            // 计算玩家到重生锚的相对位置
            double dx = mc.player.getX() - (currentAnchorPos.getX() + 0.5);
            double dz = mc.player.getZ() - (currentAnchorPos.getZ() + 0.5);

            double absX = Math.abs(dx);
            double absZ = Math.abs(dz);

            // 1. 检测斜对角逻辑：如果在斜角，尝试在左边和右边侧面都放一个萤石
            Direction xDir = dx > 0 ? Direction.EAST : Direction.WEST;
            Direction zDir = dz > 0 ? Direction.SOUTH : Direction.NORTH;

            // 判定斜角：两个轴向距离的比例超过 0.5 认为进入对角区域
            boolean isDiagonalArea = (absX > 0 && absZ > 0) && (Math.min(absX, absZ) / Math.max(absX, absZ) > 0.5);

            if (isDiagonalArea) {
                Direction[] sides = {xDir, zDir};
                for (Direction side : sides) {
                    BlockPos neighbor = currentAnchorPos.offset(side);
                    // 检查该位置是否已经有萤石或者可以放置萤石
                    if (mc.world.getBlockState(neighbor).isReplaceable() &&
                            mc.world.getOtherEntities(null, new Box(neighbor)).isEmpty() &&
                            isSideShielding(side)) {
                        targetPlaceSide = side;
                        isSidePlacement = true;
                        isDiagonalPlacement = true;
                        return neighbor;
                    }
                }
            }

            // 2. 如果玩家距离重生锚太近 (水平距离 < 1.5)
            if (Math.sqrt(dx * dx + dz * dz) < 1.5) {
                Direction[] sides = {xDir, zDir};
                boolean canPlace = false;
                for (Direction side : sides) {
                    BlockPos neighbor = currentAnchorPos.offset(side);
                    if (mc.world.getBlockState(neighbor).isReplaceable() &&
                            mc.world.getOtherEntities(null, new Box(neighbor)).isEmpty() &&
                            isSideShielding(side)) {
                        targetPlaceSide = side;
                        isSidePlacement = true;
                        canPlace = true;
                        return neighbor;
                    }
                }

                // 如果太近且无法在侧面放置任何萤石（可能被方块堵住或实体占据），则直接跳过放置阶段尝试引爆
                if (!canPlace && isExplosionSafe()) {
                    prepareExplode();
                    return null;
                }
            }

            // 选择正对着玩家的那个面
            Direction bestDir;
            if (absX > absZ) {
                bestDir = dx > 0 ? Direction.EAST : Direction.WEST;
            } else {
                bestDir = dz > 0 ? Direction.SOUTH : Direction.NORTH;
            }

            BlockPos neighbor = currentAnchorPos.offset(bestDir);
            if (mc.world.getBlockState(neighbor).isReplaceable() &&
                    mc.world.getOtherEntities(null, new Box(neighbor)).isEmpty() &&
                    isSideShielding(bestDir)) {
                targetPlaceSide = bestDir;
                isSidePlacement = true;
                return neighbor;
            }
            // 如果是对角线或目标侧面被堵，回退到 Smart 模式逻辑 (即所谓的“放中间”)
        }

        Vec3d playerPos = mc.player.getPos();
        Vec3d anchorPosVec = currentAnchorPos.toCenterPos();

        // 1. 尝试沿着连线寻找 (从 0.3 到 0.7 比例)
        for (double i = 0.3; i <= 0.7; i += 0.1) {
            Vec3d posVec = playerPos.lerp(anchorPosVec, i);
            BlockPos pos = BlockPos.ofFloored(posVec);
            if (isValidPlacePos(pos)) {
                targetPlaceSide = Direction.UP;
                return pos;
            }
        }

        // 2. 尝试玩家面前
        BlockPos frontPos = mc.player.getBlockPos().offset(mc.player.getHorizontalFacing());
        if (isValidPlacePos(frontPos)) {
            targetPlaceSide = Direction.UP;
            return frontPos;
        }

        return null;
    }

    private boolean isSideShielding(Direction side) {
        Vec3d a = currentAnchorPos.toCenterPos();
        Vec3d p = mc.player.getPos().add(0.0, 0.0, 0.0);
        Vec3d b = currentAnchorPos.offset(side).toCenterPos();
        double ax = a.x, az = a.z;
        double px = p.x, pz = p.z;
        double bx = b.x, bz = b.z;
        double vx = px - ax, vz = pz - az;
        double wx = bx - ax, wz = bz - az;
        double vv = vx * vx + vz * vz;
        if (vv < 1.0e-6) return false;
        double t = (wx * vx + wz * vz) / vv;
        if (t <= 0.0 || t >= 1.0) return false;
        double nx = wx - t * vx;
        double nz = wz - t * vz;
        double d2 = nx * nx + nz * nz;
        return d2 <= 0.36;
    }

    private boolean isValidPlacePos(BlockPos pos) {
        if (pos.equals(currentAnchorPos)) return false;

        // 检查是否与任何实体碰撞箱冲突（包括玩家自己）
        Box blockBox = new Box(pos);
        if (!mc.world.getOtherEntities(null, blockBox).isEmpty()) return false;

        // 检查是否有支撑方块且当前位置可替换
        return !mc.world.getBlockState(pos.down()).isAir() &&
                !mc.world.getBlockState(pos.down()).isReplaceable() &&
                mc.world.getBlockState(pos).isReplaceable();
    }

    private boolean isExplosionSafe() {
        float damage = DamageUtil.calculateAnchorDamage(mc.player, currentAnchorPos);
        float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();

        // 检查引爆后剩余血量是否大于最小安全血量
        return (health - damage) > minHealth.get().floatValue();
    }

    private void handleRotatingToPlace() {
        if (targetRotation == null || isLookingAt(targetActionPos)) {
            if (isSidePlacement) {
                // 强制发送潜行包，防止打开重生锚界面
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                interactBlock(currentAnchorPos, targetPlaceSide);
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
            } else {
                interactBlock(targetActionPos.down(), Direction.UP);
            }

            // 视角拉回重生锚准备引爆
            prepareExplode();
        }
    }

    private void prepareExplode() {
        if (!autoExplode.get()) {
            resetState();
            return;
        }

        if (originalSlot != -1) {
            InvUtil.swap(originalSlot, false);
        }

        targetActionPos = currentAnchorPos;
        currentRotationSpeed = explodeRotationSpeed.get();
        targetRotation = explodeNoRotate ? null : getTargetRotation(currentAnchorPos.toCenterPos());
        stage = 3;
    }

    private void handleRotatingToExplode() {
        if (targetRotation == null || isLookingAt(targetActionPos)) {
            interactBlock(targetActionPos, Direction.UP);
            resetState();
        }
    }

    private boolean isLookingAt(BlockPos pos) {
        HitResult hit = mc.crosshairTarget;
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos hitPos = blockHit.getBlockPos();

            if (isSidePlacement && currentAnchorPos != null) {
                // 如果已经在看重生锚，且不是顶面，则认为已经对准侧面
                return hitPos.equals(currentAnchorPos) && blockHit.getSide() != Direction.UP;
            }

            return hitPos.equals(pos) || hitPos.equals(pos.down());
        }
        return false;
    }

    private boolean isLookingAtAnchorSide(Direction side) {
        HitResult hit = mc.crosshairTarget;
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            return blockHit.getBlockPos().equals(currentAnchorPos) && blockHit.getSide() == side;
        }
        return false;
    }

    private void interactBlock(BlockPos pos, Direction side) {
        // 使用 0.45 而不是 0.5，确保 hitVec 落在方块面上而不是边缘
        Vec3d hitVec = new Vec3d(pos.getX() + 0.5 + side.getOffsetX() * 0.45,
                pos.getY() + 0.5 + side.getOffsetY() * 0.45,
                pos.getZ() + 0.5 + side.getOffsetZ() * 0.45);
        BlockHitResult bhr = new BlockHitResult(hitVec, side, pos, false);
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private void resetState() {
        stage = 0;
        currentAnchorPos = null;
        delay = 2;
        explodeNoRotate = false;
    }
}
