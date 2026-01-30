package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.player.FindItemResult;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RaytraceUtil;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.utils.world.BlockUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDick extends Module {
    public AutoDick() {
        super("AutoDick", "自动迪克", Category.Player);

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player == null || minecraftClient.world == null) return;
            if (!shouldSwapBack) return;
            shouldSwapBack = false;
            InvUtil.swapBack();
        });
    }

    private enum Mode {
        Level,
        Vertical
    }

    private enum SwitchMode {
        Normal,
        Silent,
        InvSilent
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Level);
    private final EnumValue<SwitchMode> switchMode = new EnumValue<>("Switch Mode", "切换模式", SwitchMode.Normal);
    private final BoolValue swapBack = new BoolValue("Swap Back", "切回", true, () -> switchMode.is(SwitchMode.Normal));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true);
    private final NumberValue<Integer> length = new NumberValue<>("Length", "长度", 3, 1, 5, 1);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 0, 0, 150, 1);
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private FindItemResult result;
    private boolean shouldSwapBack;
    private boolean invSwitched = false;
    private BlockPos supportBlock;
    private final List<BlockPos> blockList = new ArrayList<>();
    private final TimerUtil timer = new TimerUtil();
    private PlaceData placeData;
    private Direction breakSide;

    @Override
    protected void onEnable() {
        result = null;
        supportBlock = null;
        placeData = null;
        breakSide = null;

        if (nullCheck()) return;

        if (!mc.player.isOnGround()) {
            setState(false);
            return;
        }

        updateBlock();
    }

    @Override
    protected void onDisable() {
        supportBlock = null;
        placeData = null;
        breakSide = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (blockList.isEmpty() && supportBlock == null) {
            setState(false);
            return;
        }

        if (placeData == null) {
            for (BlockPos pos : blockList) {
                if (!BlockUtil.canPlaceAt(pos)) continue;

                result = switchMode.is(SwitchMode.InvSilent) ? InvUtil.find(stack -> validItem(stack, pos)) : InvUtil.findInHotbar(stack -> validItem(stack, pos));
                if (!result.found()) return;

                for (Direction side : Direction.values()) {
                    BlockPos neighbor = pos.offset(side);
                    if (!BlockUtil.solid(neighbor)) continue;

                    Direction opposite = side.getOpposite();
                    placeData = new PlaceData(pos, side, opposite, neighbor, randomRot(neighbor, opposite));
                    break;
                }
                if (placeData != null) break;
            }
        }

        if (placeData != null) {
            Rotation rotation = RotationUtil.calculate(placeData.hitVec);
            Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF, RotationManager.Priority.High);

            if (timer.passedMS(delay.get())) {
                boolean hasRotated = RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), placeData.opposite, placeData.neighbor, false);
                if (!hasRotated) return;

                if (placeBlock(placeData, new BlockHitResult(placeData.hitVec, placeData.opposite, placeData.neighbor, false))) {
                    blockList.remove(placeData.pos);
                    placeData = null;
                    timer.reset();
                }
            }
        }

        if (switchMode.is(SwitchMode.Silent)) {
            InvUtil.swapBack();
        } else if (switchMode.is(SwitchMode.InvSilent) && invSwitched) {
            InvUtil.invSwapBack();
        }

        if (blockList.isEmpty()) {
            if (supportBlock != null) {
                if (BlockUtil.solid(supportBlock)) {
                    Vec3d eyePos = mc.player.getEyePos();
                    double dx = eyePos.x - (supportBlock.getX() + 0.5);
                    double dy = eyePos.y - (supportBlock.getY() + 0.5);
                    double dz = eyePos.z - (supportBlock.getZ() + 0.5);

                    breakSide = Direction.getFacing(dx, dy, dz);

                    breakBlock();
                    return;
                } else {
                    supportBlock = null;
                }
            }
            setState(false);
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (!render.get()) return;

        if (supportBlock != null) {
            Render3DUtil.drawFullBox(event.getMatrices(), supportBlock, sideColor.get(), lineColor.get());
        }
    }

    private void updateBlock() {
        blockList.clear();

        Direction facing = mc.player.getHorizontalFacing();
        BlockPos center = mc.player.getBlockPos().offset(facing, 2);

        if (mode.is(Mode.Level)) {
            // Length
            for (int i = length.get() - 1; i > 0; i--) {
                addBlock(center.offset(facing, i));
            }

            // Left Egg
            addBlock(center.offset(facing.rotateYCounterclockwise()));

            // Right Egg
            addBlock(center.offset(facing.rotateYClockwise()));

        } else {
            // Support
            addBlock(supportBlock = center);

            // Left Egg
            addBlock(center.offset(facing.rotateYCounterclockwise()));
            
            // Right Egg
            addBlock(center.offset(facing.rotateYClockwise()));

            for (int i = 0; i < length.get() - 1; i++) {
                addBlock(center.up(i + 1));
            }
        }
    }

    private void breakBlock() {
        Rotation rotation = RotationUtil.calculate(randomRot(supportBlock, breakSide));
        Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF, RotationManager.Priority.High);

        mc.interactionManager.updateBlockBreakingProgress(supportBlock, breakSide);
        if (swingHand.get()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        } else {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    private boolean placeBlock(PlaceData data, BlockHitResult hitResult) {
        invSwitched = false;
        if (switchMode.get() == SwitchMode.InvSilent) {
            invSwitched = InvUtil.invSwap(result.slot());
        } else {
            InvUtil.swap(result.slot(), switchMode.is(SwitchMode.Silent) || swapBack.get());
            if (switchMode.is(SwitchMode.InvSilent) && swapBack.get()) {
                shouldSwapBack = true;
            }
        }

        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hitResult);

        if (swingHand.get()) mc.player.swingHand(Hand.MAIN_HAND);

        Managers.RENDER.add(data.pos, sideColor.get(), lineColor.get(), 1000, shrink.get());
        return true;
    }

    public static Vec3d randomRot(BlockPos pos, Direction face) {
        double x = (double) pos.getX() + 0.5;
        double y = (double) pos.getY() + 0.5;
        double z = (double) pos.getZ() + 0.5;
        if (face != Direction.UP && face != Direction.DOWN) {
            y += MathUtil.getRandom(0.1, -0.1);
        } else {
            x += MathUtil.getRandom(0.3, -0.1);
            z += MathUtil.getRandom(0.1, -0.1);
        }
        if (face == Direction.WEST || face == Direction.EAST) {
            z += MathUtil.getRandom(0.1, -0.1);
        }
        if (face == Direction.SOUTH || face == Direction.NORTH) {
            x += MathUtil.getRandom(0.1, -0.1);
        }
        return new Vec3d(x, y, z);
    }

    private void addBlock(BlockPos blockPos) {
        if (!blockList.contains(blockPos) && mc.world.getBlockState(blockPos).isReplaceable()) {
            blockList.add(blockPos);
        }
    }

    private boolean validItem(ItemStack itemStack, BlockPos pos) {
        if (!(itemStack.getItem() instanceof BlockItem)) return false;

        Block block = ((BlockItem) itemStack.getItem()).getBlock();

        if (!Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, pos))) return false;
        return !(block instanceof FallingBlock) || !FallingBlock.canFallThrough(mc.world.getBlockState(pos));
    }

    private record PlaceData(BlockPos pos, Direction side, Direction opposite, BlockPos neighbor, Vec3d hitVec) {
    }
}
