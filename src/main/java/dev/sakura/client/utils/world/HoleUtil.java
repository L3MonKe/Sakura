package dev.sakura.client.utils.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class HoleUtil {
    public static boolean isHole(BlockPos pos) {
        return isHole(pos, true, false, false);
    }

    public static boolean isHole(BlockPos pos, boolean canStand, boolean checkTrap, boolean anyBlock) {
        if (mc.world == null || mc.player == null) return false;

        int blockProgress = 0;
        for (Direction i : Direction.values()) {
            if (i == Direction.UP || i == Direction.DOWN) continue;
            if (anyBlock && !mc.world.isAir(pos.offset(i)) || isHard(pos.offset(i)))
                blockProgress++;
        }

        return (!checkTrap || (mc.world.isAir(pos)
                && mc.world.isAir(pos.up())
                && mc.world.isAir(pos.up(1))
                && mc.world.isAir(pos.up(2))
                && (mc.player.getBlockY() - 1 <= pos.getY() || mc.world.isAir(pos.up(3)))
                && (mc.player.getBlockY() - 2 <= pos.getY() || mc.world.isAir(pos.up(4)))))
                && blockProgress > 3
                && (!canStand || mc.world.getBlockState(pos.add(0, -1, 0)).blocksMovement());
    }

    public static BlockPos getHole(float range, boolean doubleHole, boolean any, boolean up) {
        if (mc.player == null || mc.world == null) return null;

        BlockPos bestPos = null;
        double bestDistance = range + 1;

        for (BlockPos pos : getSphere(range, mc.player.getPos())) {
            if (pos.getX() != mc.player.getBlockX() || pos.getZ() != mc.player.getBlockZ()) {
                if (!up && pos.getY() + 1 > mc.player.getY()) continue;
            }

            if (isHole(pos, true, true, any) || (doubleHole && isDoubleHole(pos))) {
                if (pos.getY() - mc.player.getBlockY() > 1) continue;

                double distance = MathHelper.sqrt((float) mc.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
                if (bestPos == null || distance < bestDistance) {
                    bestPos = pos;
                    bestDistance = distance;
                }
            }
        }
        return bestPos;
    }

    public static boolean isDoubleHole(BlockPos pos) {
        Direction unHardFacing = is3Block(pos);
        if (unHardFacing != null) {
            pos = pos.offset(unHardFacing);
            unHardFacing = is3Block(pos);
            return unHardFacing != null;
        }
        return false;
    }

    public static Direction is3Block(BlockPos pos) {
        if (mc.world == null) return null;

        if (!isHard(pos.down())) {
            return null;
        }
        if (!mc.world.isAir(pos) || !mc.world.isAir(pos.up()) || !mc.world.isAir(pos.up(2))) {
            return null;
        }

        int progress = 0;
        Direction unHardFacing = null;

        for (Direction facing : Direction.values()) {
            if (facing == Direction.UP || facing == Direction.DOWN) continue;

            if (isHard(pos.offset(facing))) {
                progress++;
                continue;
            }

            int progress2 = 0;
            for (Direction facing2 : Direction.values()) {
                if (facing2 == Direction.DOWN || facing2 == facing.getOpposite()) {
                    continue;
                }
                if (isHard(pos.offset(facing).offset(facing2))) {
                    progress2++;
                }
            }

            if (progress2 == 4) {
                progress++;
                continue;
            }
            unHardFacing = facing;
        }

        if (progress == 3) {
            return unHardFacing;
        }
        return null;
    }

    public static boolean isHard(BlockPos pos) {
        if (mc.world == null) return false;
        Block block = mc.world.getBlockState(pos).getBlock();
        return block == Blocks.OBSIDIAN
                || block == Blocks.NETHERITE_BLOCK
                || block == Blocks.ENDER_CHEST
                || block == Blocks.BEDROCK
                || block == Blocks.CRYING_OBSIDIAN
                || block == Blocks.RESPAWN_ANCHOR;
    }

    public static List<BlockPos> getSphere(float range, Vec3d center) {
        List<BlockPos> sphere = new ArrayList<>();
        int cx = (int) Math.floor(center.x);
        int cy = (int) Math.floor(center.y);
        int cz = (int) Math.floor(center.z);
        int r = (int) Math.ceil(range);

        for (int x = cx - r; x <= cx + r; x++) {
            for (int y = cy - r; y <= cy + r; y++) {
                for (int z = cz - r; z <= cz + r; z++) {
                    double dist = Math.sqrt((x - center.x) * (x - center.x)
                            + (y - center.y) * (y - center.y)
                            + (z - center.z) * (z - center.z));
                    if (dist <= range) {
                        sphere.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return sphere;
    }

    public static boolean isInHole() {
        if (mc.player == null) return false;
        return isHole(mc.player.getBlockPos(), true, true, true);
    }
}