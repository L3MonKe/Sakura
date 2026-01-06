package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

import java.util.Optional;

public class GhostHand extends Module {
    public GhostHand() {
        super("GhostHand", "鬼手", Category.Player);
    }

    private final NumberValue<Double> reach = new NumberValue<>("Reach", "距离", 4.5, 1.0, 6.0, 0.1);
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final BoolValue chest = new BoolValue("Chest", "箱子", true);
    private final BoolValue enderChest = new BoolValue("Ender Chest", "末影箱", true);
    private final BoolValue shulkerBox = new BoolValue("Shulker Box", "潜影盒", true);

    private boolean lastUsePressed;

    @Override
    protected void onEnable() {
        lastUsePressed = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.currentScreen != null) return;

        boolean pressed = mc.options.useKey.isPressed();
        if (pressed && !lastUsePressed) {
            interact();
        }
        lastUsePressed = pressed;
    }

    private boolean interact() {
        if (mc.crosshairTarget instanceof BlockHitResult bhr && isAllowed(mc.world.getBlockState(bhr.getBlockPos()))) {
            return false;
        }
        if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            return false;
        }

        double r = reach.get();
        Vec3d eyePos = mc.player.getEyePos();
        Vec3d lookVec = mc.player.getRotationVec(1.0f);
        Vec3d reachEnd = eyePos.add(lookVec.multiply(r));

        int scan = (int) Math.ceil(r) + 1;
        BlockPos base = mc.player.getBlockPos();
        BlockPos min = base.add(-scan, -scan, -scan);
        BlockPos max = base.add(scan, scan, scan);

        BlockPos bestPos = null;
        Vec3d bestHit = null;
        double bestDist = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.iterate(min, max)) {
            BlockState state = mc.world.getBlockState(pos);
            if (!isAllowed(state)) continue;

            Box box = getOutlineBox(state, pos);
            if (box == null) continue;

            Optional<Vec3d> hit = box.raycast(eyePos, reachEnd);
            if (hit.isEmpty()) continue;

            double dist = hit.get().squaredDistanceTo(eyePos);
            if (dist < bestDist) {
                bestDist = dist;
                bestPos = pos.toImmutable();
                bestHit = hit.get();
            }
        }

        if (bestPos == null) return false;

        Direction side = getHitSide(bestPos, bestHit);
        BlockHitResult fakeHit = new BlockHitResult(bestHit, side, bestPos, false);
        ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, fakeHit);
        if (result.isAccepted() && swingHand.get()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        return result.isAccepted();
    }

    private boolean isAllowed(BlockState state) {
        if (state == null) return false;
        if (chest.get() && state.getBlock() instanceof ChestBlock) return true;
        if (enderChest.get() && state.getBlock() instanceof EnderChestBlock) return true;
        return shulkerBox.get() && state.getBlock() instanceof ShulkerBoxBlock;
    }

    private Box getOutlineBox(BlockState state, BlockPos pos) {
        if (state == null || mc.world == null || mc.player == null) return null;
        VoxelShape shape = state.getOutlineShape(mc.world, pos);
        if (shape.isEmpty()) return null;
        Box b = shape.getBoundingBox();
        return b.offset(pos);
    }

    private Direction getHitSide(BlockPos pos, Vec3d hit) {
        double cx = pos.getX() + 0.5;
        double cy = pos.getY() + 0.5;
        double cz = pos.getZ() + 0.5;
        double dx = hit.x - cx;
        double dy = hit.y - cy;
        double dz = hit.z - cz;

        double ax = Math.abs(dx);
        double ay = Math.abs(dy);
        double az = Math.abs(dz);

        if (ay >= ax && ay >= az) return dy > 0 ? Direction.UP : Direction.DOWN;
        if (ax >= az) return dx > 0 ? Direction.EAST : Direction.WEST;
        return dz > 0 ? Direction.SOUTH : Direction.NORTH;
    }
}

