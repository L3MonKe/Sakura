package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.player.StrafeEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.vector.Rotation;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import java.awt.*;

public class Scaffold extends Module {
    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Silent);
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final BoolValue telly = new BoolValue("Telly", "Telly搭路", false);
    private final NumberValue<Integer> tellyTick = new NumberValue<>("Telly Tick", "Telly延迟", 1, 0, 8, 1, telly::get);
    private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, telly::get);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, telly::get);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", false);
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private int yLevel;
    private BlockCache blockCache;
    private int airTicks;

    public Scaffold() {
        super("Scaffold", "自动搭路", Category.Movement);
    }

    public static Vec3d getVec3(BlockPos pos, Direction face) {
        double x = (double) pos.getX() + 0.5;
        double y = (double) pos.getY() + 0.5;
        double z = (double) pos.getZ() + 0.5;
        if (face == Direction.UP || face == Direction.DOWN) {
            x += MathUtil.getRandom(0.3, -0.3);
            z += MathUtil.getRandom(0.3, -0.3);
        } else {
            y += MathUtil.getRandom(0.3, -0.3);
        }
        if (face == Direction.WEST || face == Direction.EAST) {
            z += MathUtil.getRandom(0.3, -0.3);
        }
        if (face == Direction.SOUTH || face == Direction.NORTH) {
            x += MathUtil.getRandom(0.3, -0.3);
        }
        return new Vec3d(x, y, z);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        getBlockInfo();

        if (telly.get()) {
            if (mc.player.isOnGround()) {
                yLevel = (int) Math.floor(mc.player.getY()) - 1;
                airTicks = 0;
                blockCache = null;
                Rotation rotation = new Rotation(mc.player.getYaw(), mc.player.getPitch());
                Managers.ROTATION.setRotations(rotation, rotationBackSpeed.get(), moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF, RotationManager.Priority.High);
            } else {
                if (airTicks >= tellyTick.get() && blockCache != null) {
                    Managers.ROTATION.setRotations(getRotation(blockCache), rotationSpeed.get(), moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF, RotationManager.Priority.High);
                    place();
                }
                airTicks++;
            }
        } else if (blockCache != null) {
            MovementFix movementFix = moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF;
            Managers.ROTATION.setRotations(getRotation(blockCache), rotationSpeed.get(), movementFix, RotationManager.Priority.High);
            place();
        }

        if (swapMode.is(SwapMode.Silent)) {
            InvUtil.swapBack();
        }
    }

    @EventHandler
    public void onStrafe(StrafeEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isOnGround() && MovementUtil.isMoving() && telly.get() && !mc.options.jumpKey.isPressed())
            mc.player.jump();
    }

    public int getYLevel() {
        if (keepY.get() && !mc.options.jumpKey.isPressed() && MovementUtil.isMoving() && telly.get() && mc.player.fallDistance <= 1) {
            return yLevel;
        } else {
            return (int) Math.floor(mc.player.getY()) - 1;
        }
    }

    private boolean validItem(ItemStack itemStack, BlockPos pos) {
        if (!(itemStack.getItem() instanceof BlockItem)) return false;

        Block block = ((BlockItem) itemStack.getItem()).getBlock();

        if (!Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, pos))) return false;
        return !(block instanceof FallingBlock) || !FallingBlock.canFallThrough(mc.world.getBlockState(pos));
    }

    public void place() {
        if (!onAir()) return;

        BlockPos targetPos = blockCache.position.offset(blockCache.facing);
        if (!mc.world.getBlockState(targetPos).isReplaceable()) return;

        boolean hasRotated = RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockCache.facing, blockCache.position, false);
        FindItemResult item = InvUtil.findInHotbar(itemStack -> validItem(itemStack, blockCache.position));

        if (!item.found()) return;

        InvUtil.swap(item.isOffhand() ? mc.player.getInventory().selectedSlot : item.slot(), swapMode.is(SwapMode.Silent));

        if (hasRotated) {
            if (!mc.world.getOtherEntities(null, new Box(targetPos)).isEmpty()) return;

            ActionResult result = mc.interactionManager.interactBlock(mc.player, item.getHand(), new BlockHitResult(getVec3(blockCache.position, blockCache.facing), blockCache.facing, blockCache.position, false));

            if (result.isAccepted()) {
                if (swingHand.get()) mc.player.swingHand(item.getHand());
                else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(item.getHand()));
            }

            if (render.get()) {
                Managers.RENDER.add(targetPos, sideColor.get(), lineColor.get(), 1000, shrink.get());
            }
        }
    }

    public void getBlockInfo() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        int baseX = base.getX();
        int baseZ = base.getZ();
        if (mc.world.getBlockState(base).hasSolidTopSurface(mc.world, base, mc.player)) return;
        if (checkBlock(baseVec, base)) {
            return;
        }
        for (int d = 1; d <= 6; d++) {
            if (checkBlock(baseVec, new BlockPos(
                    baseX,
                    getYLevel() - d,
                    baseZ
            ))) {
                return;
            }
            for (int x = 0; x <= d; x++) {
                for (int z = 0; z <= d - x; z++) {
                    int y = d - x - z;
                    for (int rev1 = 0; rev1 <= 1; rev1++) {
                        for (int rev2 = 0; rev2 <= 1; rev2++) {
                            if (checkBlock(baseVec, new BlockPos(baseX + (rev1 == 0 ? x : -x), getYLevel() - y, baseZ + (rev2 == 0 ? z : -z))))
                                return;
                        }
                    }
                }
            }
        }
    }

    private boolean checkBlock(Vec3d baseVec, BlockPos pos) {
        if (!(mc.world.getBlockState(pos).getBlock() instanceof AirBlock) && !(mc.world.getBlockState(pos).getBlock() instanceof FluidBlock)) {
            return false;
        }

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (Direction dir : Direction.values()) {
            Vec3d hit = center.add(new Vec3d(dir.getVector()).multiply(0.5));
            Vec3i baseBlock = pos.add(dir.getVector());
            BlockPos baseBlockPos = new BlockPos(baseBlock.getX(), baseBlock.getY(), baseBlock.getZ());

            if (!mc.world.getBlockState(baseBlockPos).hasSolidTopSurface(mc.world, baseBlockPos, mc.player)) continue;

            Vec3d relevant = hit.subtract(baseVec);
            if (relevant.lengthSquared() <= 4.5 * 4.5 && relevant.dotProduct(new Vec3d(dir.getVector())) >= 0) {
                if (dir.getOpposite() == Direction.UP && !telly.get() && MovementUtil.isMoving() && !mc.options.jumpKey.isPressed())
                    continue;
                blockCache = new BlockCache(new BlockPos(baseBlock), dir.getOpposite());
                return true;
            }
        }
        return false;
    }

    private Rotation getRotation(BlockCache blockCache) {
        Rotation rotation = onAir() ? RotationUtil.calculate(getVec3(blockCache.position, blockCache.facing)) : RotationUtil.calculate(blockCache.position.toCenterPos());
        Rotation reverseYaw = new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 180), rotation.pitch);
        boolean hasRotated = RaytraceUtil.overBlock(reverseYaw, blockCache.facing, blockCache.position, false);
        if (hasRotated) return reverseYaw;
        else return rotation;
    }

    private boolean onAir() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        return mc.world.getBlockState(base).getBlock() instanceof AirBlock || mc.world.getBlockState(base).getBlock() instanceof LilyPadBlock;
    }

    @Override
    protected void onEnable() {
        blockCache = null;
    }

    @Override
    protected void onDisable() {
        blockCache = null;
    }

    private enum SwapMode {
        Normal,
        Silent
    }

    private record BlockCache(BlockPos position, Direction facing) {
    }
}
