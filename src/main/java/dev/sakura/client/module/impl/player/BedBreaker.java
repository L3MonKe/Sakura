package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.GameJoinEvent;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.utils.player.BlinkUtils;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.*;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.utils.world.BreakUtils;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BedBreaker extends Module {

    public enum BreakMode {ThroughWall, Swap, Legit}

    public static final EnumValue<BreakMode> mode = new EnumValue<>("BreakMode", "破坏模式", BreakMode.Swap);
    public static final BoolValue teams = new BoolValue("Teams", "队伍", true);
    public static final BoolValue render = new BoolValue("Render", "渲染", true);
    public static final NumberValue<Double> range = new NumberValue<>("Range", "范围", 4.5, 0.0, 7.0, 0.01);
    public static final BoolValue noDelay = new BoolValue("NoBreakDelay", "无破坏延迟", true);

    public static Integer targetX;
    public static Integer targetZ;
    public static BlockPos pos, oldPos;
    private int blockHitDelay = 0;
    private static final TimerUtil searchTimer = new TimerUtil();
    public static boolean hitBlock = false;
    public static float currentDamage = 0F;
    public static BlockPos breakingBlockPos = null;
    public static BreakState breakState = BreakState.NONE;
    private static Rotation serverRotation = new Rotation(0, 0);

    public enum BreakState {
        NONE,
        PREPARE,
        BREAKING,
        FINISHING
    }

    public BedBreaker() {
        super("BedBreaker", "自动破床", Category.Player);
    }

    @EventHandler
    public void onGameJoin(GameJoinEvent event) {
        if (mc.player == null) return;
        mc.player.networkHandler.sendChatMessage("/lang English");
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerMoveC2SPacket packet) {
            if (packet.changesLook()) {
                serverRotation = new Rotation(packet.getYaw(0), packet.getPitch(0));
            }
        }
    }

    @Override
    public void onEnable() {
        breakState = BreakState.NONE;
        if (mc.player != null) {
            serverRotation = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        }
        if (mc.interactionManager == null || mc.getNetworkHandler() == null) return;
        if (pos != null && !mc.interactionManager.getCurrentGameMode().isCreative()) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, pos, Direction.DOWN));
        }
        hitBlock = false;
        breakingBlockPos = null;
        currentDamage = 0F;
        pos = null;
    }

    @Override
    public void onDisable() {
        breakState = BreakState.NONE;
        currentDamage = 0F;
        pos = null;
        breakingBlockPos = null;
        hitBlock = false;
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck() || event.getType() != EventType.POST) return;

        updateClosestBlockPos();

        if (teams.get() && pos != null && isSameTeam()) {
            pos = null;
            breakingBlockPos = null;
            hitBlock = false;
            breakState = BreakState.NONE;
            currentDamage = 0F;
            oldPos = null;
            return;
        }

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura != null && killAura.isEnabled() && hasNearbyPlayer()) {
            pos = null;
            breakingBlockPos = null;
            hitBlock = false;
            breakState = BreakState.NONE;
            currentDamage = 0F;
            oldPos = null;
            return;
        }

        if (pos == null || !(mc.world.getBlockState(pos).getBlock() instanceof BedBlock) || mc.player.squaredDistanceTo(Vec3d.ofCenter(pos)) > range.get() * range.get()) {
            if (breakingBlockPos != null) {
                pos = breakingBlockPos;
            } else pos = findBed();

            if (pos == null || mc.player.squaredDistanceTo(Vec3d.ofCenter(pos)) > range.get() * range.get() || mc.world.getBlockState(pos).isAir()) {
                hitBlock = false;
                breakingBlockPos = null;
                breakState = BreakState.NONE;
                pos = null;
            }
        }

        if (pos == null) {
            currentDamage = 0F;
            breakState = BreakState.NONE;
            return;
        }

        BlockPos currentPos = pos;
        VecRotation spot = getBestAimVecForBed(currentPos);
        if (mode.is(BreakMode.Legit) || mode.is(BreakMode.Swap)) {
            BlockPos blockPos;
            if (mode.is(BreakMode.Swap)) {
                ClientPlayerEntity player = mc.player;
                World world = mc.world;

                if (breakingBlockPos != null) {
                    blockPos = breakingBlockPos;
                } else {
                    BlockState bedState = world.getBlockState(currentPos);
                    if (!(bedState.getBlock() instanceof BedBlock)) return;

                    Direction facing = bedState.get(BedBlock.FACING);
                    boolean isHead = bedState.get(BedBlock.PART) == BedPart.HEAD;
                    BlockPos otherBedPos = isHead ? currentPos.offset(facing.getOpposite()) : currentPos.offset(facing);

                    List<BlockPos> bedParts = new ArrayList<>();
                    bedParts.add(currentPos);
                    bedParts.add(otherBedPos);

                    Direction[] directions = {Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

                    List<BlockPos> solidBlocks = new ArrayList<>();
                    boolean hasAir = false;

                    for (BlockPos bedPart : bedParts) {
                        for (Direction dir : directions) {
                            BlockPos offsetPos = bedPart.offset(dir);
                            BlockState offsetState = world.getBlockState(offsetPos);

                            if (offsetState.isAir()) {
                                hasAir = true;
                                break;
                            }

                            if (!(offsetState.getBlock() instanceof BedBlock)) {
                                solidBlocks.add(offsetPos);
                            }
                        }
                        if (hasAir) break;
                    }

                    if (hasAir) {
                        blockPos = currentPos;
                    } else {
                        double bestTime = Double.MAX_VALUE;
                        double bestDistance = Double.MAX_VALUE;
                        BlockPos bestPos = null;

                        for (BlockPos solidPos : solidBlocks) {
                            float relativeHardness = BreakUtils.calcBlockBreakingDelta(solidPos, world, solidPos);
                            if (relativeHardness <= 0) relativeHardness = 0.0001f;
                            double time = 1.0 / relativeHardness;
                            double distance = player.squaredDistanceTo(Vec3d.ofCenter(solidPos));

                            if (time < bestTime || (Math.abs(time - bestTime) < 1e-5 && distance < bestDistance)) {
                                bestTime = time;
                                bestDistance = distance;
                                bestPos = solidPos;
                            }
                        }

                        blockPos = bestPos != null ? bestPos : currentPos.up();
                    }

                    breakingBlockPos = blockPos;
                }
            } else {
                var hitResult = mc.world.raycast(new net.minecraft.world.RaycastContext(
                        mc.player.getEyePos(),
                        Vec3d.ofCenter(currentPos),
                        net.minecraft.world.RaycastContext.ShapeType.OUTLINE,
                        net.minecraft.world.RaycastContext.FluidHandling.NONE,
                        mc.player
                ));
                blockPos = hitResult != null && hitResult.getType() == HitResult.Type.BLOCK
                        ? hitResult.getBlockPos()
                        : null;
            }

            if (blockPos != null) {
                Block blockAtHit = mc.world.getBlockState(blockPos).getBlock();
                if (!(blockAtHit instanceof BedBlock)) {
                    pos = blockPos;
                    currentPos = pos;
                    spot = getBestAimVecForBed(currentPos);
                }
            }
        }

        if (oldPos != null && !oldPos.equals(currentPos)) {
            mc.world.setBlockBreakingInfo(mc.player.getId(), oldPos, -1);
            currentDamage = 0F;
        }
        oldPos = currentPos;
        if (blockHitDelay > 0 && !noDelay.get()) {
            blockHitDelay--;
            return;
        }
        if (spot != null && !hitBlock) {
            breakState = BreakState.PREPARE;
        }
        boolean validTarget =
                pos != null &&
                        mc.player.squaredDistanceTo(Vec3d.ofCenter(pos)) <= range.get() * range.get() &&
                        !mc.world.getBlockState(pos).isAir();

        if (spot != null && validTarget) {
            Managers.ROTATION.setRotations(spot.rotation, 10.0, MovementFix.NORMAL, Priority.Medium);
        }
    }

    @EventHandler
    public void onUpdate(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.player.isSpectator()) return;

        Blink blink = Sakura.MODULES.getModule(Blink.class);
        if ((blink != null && blink.isEnabled()) || BlinkUtils.blinking) return;

        Rotation currentServerRotation = serverRotation;
        BlockPos currentPos = pos;
        if (currentPos == null) return;

        if (mode.is(BreakMode.Swap) && currentPos != null && mc.world.getBlockState(currentPos).getBlock() instanceof BedBlock) {
            BlockState bedState = mc.world.getBlockState(currentPos);
            Direction facing = bedState.get(BedBlock.FACING);
            boolean isHead = bedState.get(BedBlock.PART) == BedPart.HEAD;
            BlockPos otherBedPos = isHead ? currentPos.offset(facing.getOpposite()) : currentPos.offset(facing);

            boolean hasAir = false;

            for (BlockPos bedPart : new BlockPos[]{currentPos, otherBedPos}) {
                for (Direction dir : Direction.values()) {
                    BlockPos offset = bedPart.offset(dir);
                    BlockState offsetState = mc.world.getBlockState(offset);
                    if (offsetState.isAir()) {
                        hasAir = true;
                        break;
                    }
                }
                if (hasAir) break;
            }

            if (!hasAir && breakingBlockPos != null && hitBlock) {
                breakingBlockPos = null;
                currentDamage = 0F;
                hitBlock = false;
                breakState = BreakState.NONE;
                mc.world.setBlockBreakingInfo(mc.player.getId(), currentPos, -1);
                return;
            }
        }

        BlockHitResult raytrace = performRaytrace(currentPos, currentServerRotation, range.get());

        if (raytrace == null && !hitBlock) {
            breakState = BreakState.NONE;
            return;
        }

        AutoTool autoTool = Sakura.MODULES.getModule(AutoTool.class);
        if (autoTool != null && autoTool.isEnabled()) {
            autoTool.switchSlot(currentPos);
        }

        Block block = mc.world.getBlockState(currentPos).getBlock();
        if (block == null) {
            return;
        }

        if (currentDamage == 0F) {
            breakState = BreakState.PREPARE;
            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            if (killAura == null || !killAura.isEnabled() || killAura.getCurrentTarget() == null || killAura.isNoWorking()) {
                if (raytrace == null) {
                    breakState = BreakState.NONE;
                    return;
                }
                mc.player.swingHand(Hand.MAIN_HAND);
                mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, Objects.requireNonNull(currentPos), raytrace.getSide()));
                hitBlock = true;
                breakState = BreakState.BREAKING;
                if (mc.player.isCreative() || mc.world.getBlockState(currentPos).getHardness(mc.world, currentPos) == 0.0f) {
                    mc.world.breakBlock(currentPos, true);
                    currentDamage = 0F;
                    pos = null;
                    return;
                }
            }
        }

        BlockState state = mc.world.getBlockState(currentPos);

        float relativeHardness = state.calcBlockBreakingDelta(mc.player, mc.world, currentPos);
        if (relativeHardness <= 0) return;

        currentDamage += relativeHardness;

        if (hitBlock) {
            breakState = BreakState.BREAKING;
        }

        int breakStage = (int) (currentDamage * 10F);
        if (breakStage > 9) breakStage = 9;

        mc.world.spawnBlockBreakingParticle(currentPos, Direction.byIndex(breakStage));
        mc.world.setBlockBreakingInfo(mc.player.getId(), currentPos, breakStage);

        if (currentDamage >= 1F) {
            breakState = BreakState.FINISHING;
            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            if (killAura == null || !killAura.isEnabled() || killAura.getCurrentTarget() == null || killAura.isNoWorking()) {
                hitBlock = false;
                if (raytrace == null) {
                    breakState = BreakState.NONE;
                    return;
                }

                mc.player.swingHand(Hand.MAIN_HAND);
                mc.player.networkHandler.sendPacket(
                        new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, Objects.requireNonNull(currentPos), raytrace.getSide())
                );

                mc.world.breakBlock(currentPos, true);

                blockHitDelay = 4;
                currentDamage = 0F;
                mc.world.setBlockBreakingInfo(mc.player.getId(), currentPos, -1);
                pos = null;

                breakingBlockPos = null;

                breakState = BreakState.NONE;
            }
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (mc.player == null) return;
        if (mc.player.isSpectator()) return;
        if (render.get() && pos != null) {
            Box originalBox = new Box(pos);

            float shrink = 0.002F;
            Box expandedBox = originalBox.expand(shrink);

            double centerX = (expandedBox.minX + expandedBox.maxX) / 2.0;
            double centerY = (expandedBox.minY + expandedBox.maxY) / 2.0;
            double centerZ = (expandedBox.minZ + expandedBox.maxZ) / 2.0;

            double newWidth = (expandedBox.maxX - expandedBox.minX) * currentDamage;
            double newHeight = (expandedBox.maxY - expandedBox.minY) * currentDamage;
            double newDepth = (expandedBox.maxZ - expandedBox.minZ) * currentDamage;

            Box scaledBox = new Box(
                    centerX - newWidth / 2.0, centerY - newHeight / 2.0, centerZ - newDepth / 2.0,
                    centerX + newWidth / 2.0, centerY + newHeight / 2.0, centerZ + newDepth / 2.0
            );

            Color bottomColor = new Color(ClickGui.color(0).getRed(), ClickGui.color(0).getGreen(), ClickGui.color(0).getBlue(), 50);
            Color topColor = new Color(ClickGui.color2(0).getRed(), ClickGui.color2(0).getGreen(), ClickGui.color2(0).getBlue(), 50);
            Render3DUtil.drawFilledFadeBox(event.getMatrices(), scaledBox, bottomColor.getRGB(), topColor.getRGB());
        }
    }

    public void updateClosestBlockPos() {
        if (mc.player == null || mc.world == null) return;
        if (!searchTimer.passedMillise(500L)) {
            return;
        }
        searchTimer.reset();

        double posX = mc.player.getX();
        double posY = mc.player.getY();
        double posZ = mc.player.getZ();
        List<BlockPos> targetBlockList = new ArrayList<>();
        int searchDistance = 10;
        for (int SearchX = (int) (posX - searchDistance); SearchX < (int) (posX + searchDistance); SearchX++) {
            for (int SearchY = (int) (posY - searchDistance); SearchY < (int) (posY + searchDistance); SearchY++) {
                for (int SearchZ = (int) (posZ - searchDistance); SearchZ < (int) (posZ + searchDistance); SearchZ++) {
                    BlockPos blp = new BlockPos(SearchX, SearchY, SearchZ);
                    if (mc.world.getBlockState(blp).getBlock() != net.minecraft.block.Blocks.AIR) {
                        Block block = mc.world.getBlockState(blp).getBlock();
                        if (block instanceof BedBlock bedBlock) {
                            if (teams.get() && isSameTeamBed(bedBlock)) continue;
                            targetBlockList.add(blp);
                        }
                    }
                }
            }
        }
        if (targetBlockList.isEmpty()) {
            targetX = null;
            targetZ = null;
        } else {
            BlockPos closestBlp = getClosestBlock(mc.player.getX(), mc.player.getY(), mc.player.getZ(), targetBlockList);
            if (closestBlp != null) {
                targetX = closestBlp.getX();
                targetZ = closestBlp.getZ();
            }
        }
    }

    private static BlockPos getClosestBlock(double posX, double posY, double posZ, List<BlockPos> blpList) {
        blpList.sort((blockPosA, blockPosB) -> {
            double distanceA = blockPosA.getSquaredDistance(posX, posY, posZ);
            double distanceB = blockPosB.getSquaredDistance(posX, posY, posZ);
            return Double.compare(distanceA, distanceB);
        });
        return blpList.isEmpty() ? null : blpList.get(0);
    }

    private BlockPos findBed() {
        ClientPlayerEntity player = mc.player;
        World world = mc.world;
        if (player == null || world == null) return null;

        int radius = (int) (range.get() + 3);
        double nearestDistance = Double.MAX_VALUE;
        BlockPos nearest = null;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = player.getBlockPos().add(x, y, z);
                    Block block = world.getBlockState(checkPos).getBlock();
                    if (!(block instanceof BedBlock bedBlock)) continue;

                    if (teams.get() && isSameTeamBed(bedBlock)) continue;

                    double distSq = player.squaredDistanceTo(Vec3d.ofCenter(checkPos));

                    if (distSq > range.get() * range.get()) continue;

                    if (distSq < nearestDistance && (isHittable(checkPos) || mode.is(BreakMode.Legit) || mode.is(BreakMode.Swap))) {
                        nearestDistance = distSq;
                        nearest = checkPos;
                    }
                }
            }
        }
        return nearest;
    }

    private boolean isHittable(BlockPos blockPos) {
        if (mc.world == null) return false;

        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = blockPos.offset(direction);
            BlockState offsetState = mc.world.getBlockState(offsetPos);

            if (!offsetState.isFullCube(mc.world, offsetPos)) {
                return true;
            }
        }
        return false;
    }

    private VecRotation getBestAimVecForBed(BlockPos bedPos) {
        if (mc.player == null) return null;
        Vec3d eyePos = mc.player.getEyePos();
        Vec3d bestHitVec = null;
        Rotation bestRot = null;
        double bestDist = Double.MAX_VALUE;

        for (double x = 0; x <= 1.0; x += 0.5) {
            for (double y = 0; y <= 1.0; y += 0.5) {
                for (double z = 0; z <= 1.0; z += 0.5) {
                    Vec3d hitVec = new Vec3d(bedPos.getX() + x, bedPos.getY() + y, bedPos.getZ() + z);
                    Rotation rot = RotationUtil.calculate(hitVec);
                    BlockHitResult hit = performRaytrace(bedPos, rot, range.get());
                    if (hit == null || !hit.getBlockPos().equals(bedPos)) continue;

                    double dist = eyePos.squaredDistanceTo(hitVec);
                    if (dist < bestDist) {
                        bestDist = dist;
                        bestHitVec = hitVec;
                        bestRot = rot;
                    }
                }
            }
        }
        if (bestHitVec == null) {
            Vec3d center = Vec3d.ofCenter(bedPos);
            bestRot = RotationUtil.calculate(center);
            bestHitVec = center;
        }

        return new VecRotation(bestHitVec, bestRot);
    }

    private BlockHitResult performRaytrace(BlockPos blockPos, Rotation rotation, double reach) {
        if (mc.player == null || mc.world == null) return null;

        Vec3d eyes = mc.player.getEyePos();
        Vec3d rotationVec = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d endPos = eyes.add(rotationVec.x * reach, rotationVec.y * reach, rotationVec.z * reach);

        BlockState state = mc.world.getBlockState(blockPos);
        return state.getCollisionShape(mc.world, blockPos).raycast(eyes, endPos, blockPos);
    }

    private boolean hasNearbyPlayer() {
        if (mc.player == null || mc.world == null) return false;
        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        double searchRange = killAura != null ? killAura.getSearchRange() : 10.0;
        double searchRangeSq = searchRange * searchRange;
        for (net.minecraft.entity.player.PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (mc.player.squaredDistanceTo(player) <= searchRangeSq) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameTeam() {
        if (mc.player == null || mc.world == null || pos == null) return false;

        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BedBlock bedBlock)) return false;

        return isSameTeamBed(bedBlock);
    }

    private boolean isSameTeamBed(BedBlock bedBlock) {
        if (mc.player == null) return false;

        DyeColor armorDyeColor = getArmorDyeColor();
        if (armorDyeColor == null) return false;

        DyeColor bedDyeColor = bedBlock.getColor();
        return armorDyeColor == bedDyeColor;
    }

    private DyeColor getArmorDyeColor() {
        if (mc.player == null) return null;

        ItemStack helmet = mc.player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = mc.player.getEquippedStack(EquipmentSlot.CHEST);

        Integer helmetColor = getLeatherColor(helmet);
        Integer chestplateColor = getLeatherColor(chestplate);

        int rgb;
        if (helmetColor != null) {
            rgb = helmetColor;
        } else if (chestplateColor != null) {
            rgb = chestplateColor;
        } else {
            return null;
        }

        return getClosestDyeColor(rgb);
    }

    private Integer getLeatherColor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        if (!stack.contains(net.minecraft.component.DataComponentTypes.DYED_COLOR)) return null;
        return DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
    }

    private DyeColor getClosestDyeColor(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        DyeColor closest = null;
        double minDist = Double.MAX_VALUE;

        for (DyeColor dyeColor : DyeColor.values()) {
            int entityColor = dyeColor.getEntityColor();
            int dr = (entityColor >> 16) & 0xFF;
            int dg = (entityColor >> 8) & 0xFF;
            int db = entityColor & 0xFF;

            double dist = Math.sqrt((r - dr) * (r - dr) + (g - dg) * (g - dg) + (b - db) * (b - db));
            if (dist < minDist) {
                minDist = dist;
                closest = dyeColor;
            }
        }

        return closest;
    }
}
