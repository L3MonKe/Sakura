package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.hud.NotifyHud;
import dev.sakura.client.utils.entity.EntityUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.utils.vector.Vector2f;
import dev.sakura.client.utils.world.BlockUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Surround extends Module {
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 0, 0, 10, 1);
    private final NumberValue<Integer> blocksPerTick = new NumberValue<>("Blocks Per Tick", "每刻方块", 4, 1, 10, 1);
    private final BoolValue rotate = new BoolValue("Rotate", "旋转", true);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, rotate::get);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, rotate::get);
    private final BoolValue center = new BoolValue("Center", "中心", false);
    private final BoolValue smartCenter = new BoolValue("Smart Center", "智能中心", true, center::get);
    private final BoolValue phaseCenter = new BoolValue("Phase Friendly", "相位友好", true, center::get);
    private final BoolValue extend = new BoolValue("Extend", "扩展", true);
    private final BoolValue support = new BoolValue("Support", "支持", true);
    private final BoolValue floor = new BoolValue("Floor", "地板", true);
    private final BoolValue attack = new BoolValue("Attack", "攻击", true);
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private boolean isCentered;
    private final TimerUtil timer = new TimerUtil();
    private final List<BlockPos> insideBlocks = new ArrayList<>();
    private final List<BlockPos> surroundBlocks = new ArrayList<>();
    private final List<BlockPos> supportPositions = new ArrayList<>();

    public Surround() {
        super("Surround", "自动围脚", Category.Combat);
    }

    @Override
    public void onEnable() {
        timer.reset();
        isCentered = false;
    }

    private int countBlocks() {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() == Blocks.OBSIDIAN || blockItem.getBlock() == Blocks.ENDER_CHEST) {
                    count += stack.getCount();
                }
            }
        }
        return count;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        int blockCount = countBlocks();
        if (NotifyHud.INSTANCE != null && NotifyHud.INSTANCE.isEnabled()) {
            NotifyHud.INSTANCE.updateBlockWarning(blockCount);
        }

        BlockPos currentPos = BlockPos.ofFloored(mc.player.getPos());

        if (!timer.delay(delay.get())) return;

        if (!mc.player.isOnGround()) {
            setState(false);
            return;
        }

        centerPlayer(currentPos);

        updateBlocks();
        if (support.get()) {
            updateSupport();
        }

        FindItemResult obsidian = InvUtil.findInHotbar(Items.OBSIDIAN);
        FindItemResult enderChest = InvUtil.findInHotbar(Items.ENDER_CHEST);
        FindItemResult result = obsidian.found() ? obsidian : enderChest;

        if (!result.found()) {
            toggle();
            return;
        }

        List<BlockPos> targets = new ArrayList<>(supportPositions);
        targets.addAll(surroundBlocks);
        targets.removeIf(blockPos -> !mc.world.getBlockState(blockPos).isReplaceable());

        if (targets.isEmpty()) {
            if (rotate.get()) {
                Vector2f current = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
                Managers.ROTATION.setRotations(current, rotationBackSpeed.get(), MovementFix.OFF, RotationManager.Priority.Highest);
            }
            return;
        }

        int placed = 0;
        for (BlockPos pos : targets) {
            if (placed >= blocksPerTick.get()) break;

            int outcome = tryPlace(pos, result);
            if (outcome == 1) {
                placed++;
            } else if (outcome == 2) {
                break;
            }
        }

        if (placed > 0) {
            timer.reset();
        }
    }

    private void centerPlayer(BlockPos currentPos) {
        if (!isCentered && center.get() && mc.player.isOnGround() && (!phaseCenter.get() || !mc.world.getBlockCollisions(mc.player, mc.player.getBoundingBox().shrink(0.01, 0.01, 0.01)).iterator().hasNext())) {
            double targetX, targetZ;

            if (smartCenter.get()) {
                targetX = MathHelper.clamp(mc.player.getX(), currentPos.getX() + 0.31, currentPos.getX() + 0.69);
                targetZ = MathHelper.clamp(mc.player.getZ(), currentPos.getZ() + 0.31, currentPos.getZ() + 0.69);
            } else {
                targetX = currentPos.getX() + 0.5;
                targetZ = currentPos.getZ() + 0.5;
            }

            Vec3d targetVec = new Vec3d(targetX, 0, targetZ);
            Vec3d playerVec = new Vec3d(mc.player.getX(), 0, mc.player.getZ());
            double dist = targetVec.distanceTo(playerVec);

            if (dist < 0.2873) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(targetX, mc.player.getY(), targetZ, mc.player.isOnGround(), mc.player.horizontalCollision));
            }

            double x = mc.player.getX(), z = mc.player.getZ();
            double dx = targetX - x;
            double dz = targetZ - z;
            double totalDist = Math.sqrt(dx * dx + dz * dz);

            if (totalDist > 0) {
                double moveX = (dx / totalDist) * 0.2873;
                double moveZ = (dz / totalDist) * 0.2873;

                for (int i = 0; i < Math.ceil(dist / 0.2873); i++) {
                    x += moveX;
                    z += moveZ;
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, mc.player.getY(), z, mc.player.isOnGround(), mc.player.horizontalCollision));
                }
            }

            mc.player.setPosition(targetX, mc.player.getY(), targetZ);
            mc.player.setBoundingBox(new Box(targetX - 0.3, mc.player.getY(), targetZ - 0.3, targetX + 0.3, mc.player.getY() + (mc.player.getBoundingBox().maxY - mc.player.getBoundingBox().minY), targetZ + 0.3));

            isCentered = true;
        }
    }

    private void updateBlocks() {
        insideBlocks.clear();
        surroundBlocks.clear();

        BlockPos playerPos = BlockPos.ofFloored(mc.player.getPos());

        if (extend.get()) {
            Box box = mc.player.getBoundingBox();
            int minX = MathHelper.floor(box.minX);
            int maxX = MathHelper.floor(box.maxX);
            int minZ = MathHelper.floor(box.minZ);
            int maxZ = MathHelper.floor(box.maxZ);

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, playerPos.getY(), z);
                    if (box.intersects(new Box(pos))) {
                        insideBlocks.add(pos);
                    }
                }
            }
        } else {
            insideBlocks.add(playerPos);
        }

        for (BlockPos pos : insideBlocks) {
            if (floor.get()) {
                BlockPos down = pos.down();
                if (!insideBlocks.contains(down) && !surroundBlocks.contains(down)) {
                    surroundBlocks.add(down);
                }
            }

            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos offset = pos.offset(dir);
                if (!insideBlocks.contains(offset) && !surroundBlocks.contains(offset)) {
                    surroundBlocks.add(offset);
                }
            }
        }
    }

    private void updateSupport() {
        supportPositions.clear();
        for (BlockPos pos : surroundBlocks) {
            addSupport(pos);
        }
    }

    private void addSupport(BlockPos pos) {
        if (!mc.world.getBlockState(pos).isReplaceable()) return;
        if (BlockUtil.calcSide(pos) != null) return;

        for (Direction dir : Direction.values()) {
            if (dir == Direction.UP) continue;
            BlockPos neighbor = pos.offset(dir);

            if (surroundBlocks.contains(neighbor) || insideBlocks.contains(neighbor)) continue;
            if (!mc.world.getBlockState(neighbor).isReplaceable()) continue;
            if (BlockUtil.calcSide(neighbor) == null) continue;

            supportPositions.add(neighbor);
            return;
        }
    }

    private int tryPlace(BlockPos pos, FindItemResult result) {
        if (!mc.world.getBlockState(pos).isReplaceable()) return 0;

        if (attack.get()) {
            for (Entity entity : mc.world.getOtherEntities(null, new Box(pos))) {
                if (entity instanceof EndCrystalEntity) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                }
            }
        }

        if (EntityUtil.intersectsWithEntity(new Box(pos), entity -> true)) return 0;

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
            if (swingHand.get()) mc.player.swingHand(hand);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
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

            if (rotate.get()) {
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