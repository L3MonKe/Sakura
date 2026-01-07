package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.entity.EntityUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.utils.world.BlockUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelfTrap extends Module {
    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 3.0, 1.0, 6.0, 0.1);
    private final BoolValue face = new BoolValue("Face", "头部", true);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 0, 0, 5, 1);
    private final NumberValue<Integer> shiftTicks = new NumberValue<>("ShiftTicks", "每刻方块", 1, 1, 8, 1);
    private final BoolValue rotate = new BoolValue("Rotate", "旋转", true);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, rotate::get);
    private final BoolValue swing = new BoolValue("Swing", "挥手", false);
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue jumpDisable = new BoolValue("JumpDisable", "跳跃关闭", false);
    private final BoolValue selfToggle = new BoolValue("SelfToggle", "自动关闭", false);

    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private final TimerUtil timer = new TimerUtil();
    private List<BlockPos> surroundPositions = new ArrayList<>();

    public SelfTrap() {
        super("SelfTrap", "自动困住自己", Category.Combat);
    }

    @Override
    public void onEnable() {
        timer.reset();
        surroundPositions.clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (jumpDisable.get() && !mc.player.isOnGround()) {
            toggle();
            return;
        }

        if (!timer.delay(delay.get())) return;

        surroundPositions = getSurround(mc.player);

        if (surroundPositions.isEmpty() && selfToggle.get()) {
            toggle();
            return;
        }

        FindItemResult obsidian = InvUtil.findInHotbar(Items.OBSIDIAN);
        FindItemResult enderChest = InvUtil.findInHotbar(Items.ENDER_CHEST);
        FindItemResult result = obsidian.found() ? obsidian : enderChest;

        if (!result.found()) {
            return;
        }

        int blocksPlaced = 0;

        for (BlockPos pos : surroundPositions) {
            if (mc.world.getBlockState(pos).isReplaceable()) {
                BlockPos foundation = pos.down();
                if (mc.world.getBlockState(foundation).isReplaceable()) {
                    int outcome = tryPlace(foundation, result);
                    if (outcome == 1) {
                        blocksPlaced++;
                        if (blocksPlaced >= shiftTicks.get()) break;
                    } else if (outcome == 2) {
                        break;
                    }
                }

                int outcome = tryPlace(pos, result);
                if (outcome == 1) {
                    blocksPlaced++;
                    if (blocksPlaced >= shiftTicks.get()) break;
                } else if (outcome == 2) {
                    break;
                }
            }
        }

        if (blocksPlaced > 0) {
            timer.reset();
        }
    }

    private List<BlockPos> getSurround(PlayerEntity player) {
        Set<BlockPos> positions = new HashSet<>();

        Box bb = player.getBoundingBox();
        int yLegs = (int) Math.floor(player.getY());
        List<BlockPos> inside = new ArrayList<>();
        for (int x = (int) Math.floor(bb.minX); x < Math.ceil(bb.maxX); x++) {
            for (int z = (int) Math.floor(bb.minZ); z < Math.ceil(bb.maxZ); z++) {
                inside.add(new BlockPos(x, yLegs, z));
            }
        }

        for (BlockPos base : inside)
            addSurroundForBase(base, positions);

        if (face.get() && mc.player != null && !mc.player.isCrawling()) {
            int yFace = yLegs + 1;
            List<BlockPos> faceLevel = new ArrayList<>();
            for (int x = (int) Math.floor(bb.minX); x < Math.ceil(bb.maxX); x++) {
                for (int z = (int) Math.floor(bb.minZ); z < Math.ceil(bb.maxZ); z++) {
                    faceLevel.add(new BlockPos(x, yFace, z));
                }
            }
            for (BlockPos base : faceLevel)
                addSurroundForBase(base, positions);
        }

        expand(positions, player);

        List<BlockPos> result = new ArrayList<>();
        for (BlockPos pos : positions)
            if (mc.world != null && mc.world.getBlockState(pos).isReplaceable())
                result.add(pos);

        return result;
    }

    private void addSurroundForBase(BlockPos base, Set<BlockPos> positions) {
        BlockPos below = base.down();
        addIfValid(below, positions);

        BlockPos north = base.north();
        BlockPos south = base.south();
        BlockPos east = base.east();
        BlockPos west = base.west();

        addIfValid(north, positions);
        addIfValid(south, positions);
        addIfValid(east, positions);
        addIfValid(west, positions);
    }

    private void addIfValid(BlockPos pos, Set<BlockPos> positions) {
        if (mc.world != null && mc.world.getBlockState(pos).isReplaceable()) {
            positions.add(pos);
        }
    }

    private void expand(Set<BlockPos> positions, PlayerEntity player) {
        if (mc.world == null) return;
        Set<BlockPos> extra = new HashSet<>();
        for (BlockPos pos : positions) {
            Box blockBox = new Box(pos);
            for (Entity entity : mc.world.getEntities()) {
                if (entity.squaredDistanceTo(player) > 100) continue;
                if (entity instanceof EndCrystalEntity) continue;
                if (entity instanceof ItemEntity) continue;

                if (entity.getBoundingBox().intersects(blockBox)) {
                    int entY = (int) Math.floor(entity.getY());
                    Box entBox = entity.getBoundingBox();
                    for (int x = (int) Math.floor(entBox.minX); x < Math.ceil(entBox.maxX); x++) {
                        for (int z = (int) Math.floor(entBox.minZ); z < Math.ceil(entBox.maxZ); z++) {
                            BlockPos entBase = new BlockPos(x, entY, z);
                            addSurroundForBase(entBase, extra);
                        }
                    }
                }
            }
        }
        positions.addAll(extra);
    }

    private int tryPlace(BlockPos pos, FindItemResult result) {
        if (mc.world == null || mc.player == null || mc.interactionManager == null) return 0;
        if (!mc.world.getBlockState(pos).isReplaceable()) return 0;
        if (Math.sqrt(mc.player.squaredDistanceTo(pos.toCenterPos())) > range.get()) return 0;

        if (EntityUtil.intersectsWithEntity(new Box(pos), entity -> !(entity instanceof ItemEntity) && !(entity instanceof EndCrystalEntity)))
            return 0;

        PlaceData data = getPlaceData(pos);
        if (data == null) return 0;

        if (rotate.get()) {
            Managers.ROTATION.lookAt(data.hitVec, rotationSpeed.get(), RotationManager.Priority.Highest);
            if (!Managers.ROTATION.isLookingAt(data.lingju, data.mian)) {
                return 2;
            }
        }

        int slot = result.isOffhand() ? mc.player.getInventory().selectedSlot : result.slot();
        Hand hand = result.getHand();

        InvUtil.swap(slot, true);

        BlockHitResult hitResult = new BlockHitResult(data.hitVec, data.mian, data.lingju, false);
        ActionResult r = mc.interactionManager.interactBlock(mc.player, result.getHand(), hitResult);

        if (r.isAccepted()) {
            if (swing.get()) mc.player.swingHand(hand);
            else if (mc.getNetworkHandler() != null) mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
        }

        if (render.get()) {
            Managers.RENDER.add(pos, sideColor.get(), lineColor.get(), 1000);
        }

        InvUtil.swapBack();

        return 1;
    }

    private PlaceData getPlaceData(BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.offset(dir);
            if (!BlockUtil.solid(neighbor)) continue;

            Direction side = dir.getOpposite();
            Vec3d hitVec = Vec3d.ofCenter(neighbor).add(side.getOffsetX() * 0.5, side.getOffsetY() * 0.5, side.getOffsetZ() * 0.5);

            if (rotate.get() && mc.player != null) {
                BlockHitResult result = RaytraceUtil.rayTraceCollidingBlocks(mc.player.getEyePos(), hitVec);
                if (result != null && result.getType() == HitResult.Type.BLOCK && !result.getBlockPos().equals(neighbor)) {
                    continue;
                }
            }

            return new PlaceData(neighbor, side, hitVec);
        }
        return null;
    }

    private record PlaceData(BlockPos lingju, Direction mian, Vec3d hitVec) {
    }
}