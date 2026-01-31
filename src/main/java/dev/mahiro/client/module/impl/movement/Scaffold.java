package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.input.MouseButtonEvent;
import dev.mahiro.client.events.player.StrafeEvent;
import dev.mahiro.client.events.type.KeyAction;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.player.FindItemResult;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RaytraceUtil;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.utils.world.BlockUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Scaffold extends Module {
    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Normal);
    private final BoolValue swapBack = new BoolValue("SwapBack", "停用还原", true, () -> swapMode.is(SwapMode.Normal));
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final BoolValue telly = new BoolValue("Telly", "Telly搭路", true);
    private final NumberValue<Integer> tellyTick = new NumberValue<>("Telly Tick", "Telly延迟", 1, 0, 8, 1, telly::get);
    private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, telly::get);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, telly::get);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true);
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private int yLevel;
    private BlockCache blockCache;
    private int airTicks;
    private boolean shouldSwapBack;

    public Scaffold() {
        super("Scaffold", "自动搭路", Category.Movement);

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player == null || minecraftClient.world == null) return;
            if (!shouldSwapBack) return;

            // TODO:改成手动切换selected
            shouldSwapBack = false;
            InvUtil.swapBack();
        });
    }

    @Override
    public String getSuffix() {
        return telly.get() ? "Telly" : "GodBridge";
    }

    @EventHandler
    public void onMouseButton(MouseButtonEvent event) {
        if (mc.currentScreen != null) return;
        if (event.getButton() == 0 && event.getAction() == KeyAction.Press) {
            event.setCancelled(true);
            mc.options.attackKey.setPressed(false);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.options.attackKey.isPressed()) {
            mc.options.attackKey.setPressed(false);
        }

        getBlockInfo();

        MovementFix movementFix = moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF;
        if (telly.get()) {
            if (mc.player.isOnGround()) {
                yLevel = (int) Math.floor(mc.player.getY()) - 1;
                airTicks = 0;
                Rotation rotation = new Rotation(mc.player.getYaw(), mc.player.getPitch());
                Managers.ROTATION.setRotations(rotation, rotationBackSpeed.get(), movementFix, RotationManager.Priority.High);
            } else {
                if (onAir() && airTicks >= tellyTick.get() && blockCache != null) {
                    Rotation rotation = getRotation(blockCache);
                    Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), movementFix, RotationManager.Priority.High);
                    place();
                } else if (!onAir() && blockCache != null) {
                    Rotation rotation = getRotation(blockCache);
                    Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), movementFix, RotationManager.Priority.High);
                }
                airTicks++;
            }
        } else if (blockCache != null) {
            Rotation rotation = getRotation(blockCache);
            Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), movementFix, RotationManager.Priority.High);
            place();
        }

        if (swapMode.is(SwapMode.Silent)) {
            InvUtil.swapBack();
        }
    }

    @EventHandler
    public void onStrafe(StrafeEvent event) {
        if (mc.player.isOnGround() && MoveUtil.isMoving() && telly.get() && !mc.options.jumpKey.isPressed()) {
            mc.player.jump();
        }
    }

    public static Vec3d getVec3(BlockPos pos, Direction face) {
        double x = (double) pos.getX() + 0.5;
        double y = (double) pos.getY() + 0.5;
        double z = (double) pos.getZ() + 0.5;
        if (face != Direction.UP && face != Direction.DOWN) {
            y += 0.08;
        } else {
            x += MathUtil.getRandom(0.3, -0.3);
            z += MathUtil.getRandom(0.3, -0.3);
        }
        if (face == Direction.WEST || face == Direction.EAST) {
            z += MathUtil.getRandom(0.3, -0.3);
        }
        if (face == Direction.SOUTH || face == Direction.NORTH) {
            x += MathUtil.getRandom(0.3, -0.3);
        }
        return new Vec3d(x, y, z);
    }

    public int getYLevel() {
        if (keepY.get() && !mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && telly.get() && mc.player.fallDistance <= 1) {
            return yLevel;
        } else {
            return MathHelper.floor(mc.player.getY()) - 1;
        }
    }

    private boolean validItem(ItemStack itemStack, BlockPos pos) {
        if (!(itemStack.getItem() instanceof BlockItem)) return false;

        Block block = ((BlockItem) itemStack.getItem()).getBlock();

        if (block == Blocks.TNT) return false;

        if (!Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, pos))) return false;
        return !(block instanceof FallingBlock) || !FallingBlock.canFallThrough(mc.world.getBlockState(pos));
    }

    public void place() {
        if (!onAir()) return;

        boolean hasRotated = RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockCache.facing, blockCache.position, false);
        if (!hasRotated) return;

        BlockPos targetPos = blockCache.position.offset(blockCache.facing);
        if (!BlockUtil.canPlaceAt(targetPos)) return;

        Hand hand;
        boolean invSwapped = false;
        if (swapMode.is(SwapMode.None)) {
            if (validItem(mc.player.getOffHandStack(), blockCache.position)) {
                hand = Hand.OFF_HAND;
            } else if (validItem(mc.player.getMainHandStack(), blockCache.position)) {
                hand = Hand.MAIN_HAND;
            } else {
                return;
            }
        } else {
            if (swapMode.is(SwapMode.InvSwitch)) {
                if (validItem(mc.player.getOffHandStack(), blockCache.position)) {
                    hand = Hand.OFF_HAND;
                } else if (validItem(mc.player.getMainHandStack(), blockCache.position)) {
                    hand = Hand.MAIN_HAND;
                } else {
                    FindItemResult item = InvUtil.find(itemStack -> validItem(itemStack, blockCache.position), 0, 35);
                    if (!item.found()) return;
                    invSwapped = InvUtil.invSwap(item.slot());
                    hand = Hand.MAIN_HAND;
                }
            } else {
                FindItemResult item = InvUtil.findInHotbar(itemStack -> validItem(itemStack, blockCache.position));
                if (!item.found()) return;

                boolean remember = swapMode.is(SwapMode.Silent) || (swapMode.is(SwapMode.Normal) && swapBack.get());
                InvUtil.swap(item.isOffhand() ? mc.player.getInventory().selectedSlot : item.slot(), remember);
                hand = item.getHand();
            }
        }

        ActionResult result = mc.interactionManager.interactBlock(mc.player, hand, new BlockHitResult(blockCache.hitVec, blockCache.facing, blockCache.position, false));

        if (result.isAccepted()) {
            if (swingHand.get()) mc.player.swingHand(hand);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
        }

        if (invSwapped) {
            InvUtil.invSwapBack();
        }

        if (render.get()) {
            Managers.RENDER.add(targetPos, sideColor.get(), lineColor.get(), 1000, shrink.get());
        }
    }

    public void getBlockInfo() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        int baseX = base.getX();
        int baseZ = base.getZ();

        if (mc.world.getBlockState(base).hasSolidTopSurface(mc.world, base, mc.player)) {
            return;
        }

        if (checkBlock(baseVec, base)) {
            return;
        }

        for (int d = 1; d <= 6; d++) {
            if (checkBlock(baseVec, new BlockPos(baseX, getYLevel() - d, baseZ))) {
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
        if (BlockUtil.solid(mc.world.getBlockState(pos))) return false;

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (Direction dir : Direction.values()) {
            Vec3d hit = center.add(new Vec3d(dir.getVector()).multiply(0.5));
            BlockPos baseBlockPos = pos.offset(dir);

            if (!mc.world.getBlockState(baseBlockPos).hasSolidTopSurface(mc.world, baseBlockPos, mc.player)) continue;

            Vec3d relevant = hit.subtract(baseVec);
            if (relevant.lengthSquared() <= 4.5 * 4.5 && relevant.dotProduct(new Vec3d(dir.getVector())) >= 0) {
                if (dir.getOpposite() == Direction.UP && !telly.get() && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
                    continue;
                }

                blockCache = new BlockCache(baseBlockPos, dir.getOpposite(), getVec3(baseBlockPos, dir.getOpposite()));
                return true;
            }
        }
        return false;
    }

    private boolean onAir() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        return !mc.world.getBlockState(base).hasSolidTopSurface(mc.world, base, mc.player);
    }

    private Rotation getRotation(BlockCache blockCache) {
        Rotation rotations = onAir()
                ? RotationUtil.calculate(new Vec3d(blockCache.position.getX(), blockCache.position.getY(), blockCache.position.getZ()), blockCache.facing)
                : RotationUtil.calculate(blockCache.position);

        Rotation reverseYaw = new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 180), rotations.pitch);
        boolean hasRotated = RaytraceUtil.overBlock(reverseYaw, blockCache.facing, blockCache.position, false);
        if (hasRotated) return reverseYaw;
        return rotations;
    }

    @Override
    protected void onEnable() {
        blockCache = null;
        shouldSwapBack = false;
    }

    @Override
    protected void onDisable() {
        if (swapMode.is(SwapMode.Normal) && swapBack.get()) {
            shouldSwapBack = true;
        }
        blockCache = null;
    }

    private enum SwapMode {
        None,
        Normal,
        InvSwitch,
        Silent
    }

    private record BlockCache(BlockPos position, Direction facing, Vec3d hitVec) {
    }
}
