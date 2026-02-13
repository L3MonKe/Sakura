package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.player.inventory.InvHelper;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.*;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", "自动搭路", Category.Movement);
    }

    private enum Mode {
        GodBridge,
        Telly
    }

    private enum SwapMode {
        None,
        Normal,
        InvSwitch,
        Silent
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.GodBridge);
    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Normal);
    //private final BoolValue swapBack = new BoolValue("SwapBack", "停用还原", true, () -> swapMode.is(SwapMode.Normal));
    //private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final NumberValue<Integer> tellyTick = new NumberValue<>("Telly Tick", "Telly延迟", 0, 0, 8, 1, () -> mode.is(Mode.Telly));
    //private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, () -> mode.is(Mode.Telly));
    private final BoolValue snap = new BoolValue("Snap", "滑行", false, () -> mode.is(Mode.GodBridge));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, () -> mode.is(Mode.Telly));
    private final BoolValue sideCheck = new BoolValue("Side Check", "放置面检测", false);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true);
    private final BoolValue safeWalk = new BoolValue("Safe Walk", "安全行走", true);

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue fade = new BoolValue("Fade", "变淡", false, render::get);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private int airTick;
    private int yLevel;
    private BlockPos blockPos;
    private Direction direction;
    private int oldSlot = -1;

    @Override
    public void onEnable() {
        if (mc.player != null) oldSlot = mc.player.getInventory().getSelectedSlot();
        airTick = 0;
        blockPos = null;
        direction = null;
    }

    @Override
    public void onDisable() {
        boolean isHoldingShift = InputUtil.isKeyPressed(mc.getWindow(), mc.options.sneakKey.getDefaultKey().getCode());
        mc.options.sneakKey.setPressed(isHoldingShift);
        if (mc.player != null && oldSlot != -1) {
            mc.player.getInventory().setSelectedSlot(oldSlot);
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

    public static boolean isValidStack(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof BlockItem) || stack.getCount() <= 1) {
            return false;
        } else if (!InvHelper.isItemValid(stack)) {
            return false;
        } else {
            String string = stack.getName().getString();
            if (string.contains("Click") || string.contains("点击")) {
                return false;
            } else {
                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (block instanceof FlowerBlock) {
                    return false;
                } else if (block instanceof BushBlock) {
                    return false;
                } else if (block instanceof FungusBlock) {
                    return false;
                } else if (block instanceof CropBlock) {
                    return false;
                } else {
                    return !(block instanceof SlabBlock) && !InvHelper.blacklistedBlocks.contains(block);
                }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        int slotID = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (isValidStack(stack)) {
                slotID = i;
                break;
            }
        }

        if (slotID != -1 && mc.player.getInventory().getSelectedSlot() != slotID) {
            mc.player.getInventory().setSelectedSlot(slotID);
        }

        if (mc.player.isOnGround()) yLevel = (int) Math.floor(mc.player.getY()) - 1;

        getBlockInfo();

        MovementFix movementFix = moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF;
        if (mode.is(Mode.Telly)) {
            if (mc.player.isOnGround()) {
                airTick = 0;
                blockPos = null;
                direction = null;
                Rotation rotation = new Rotation(mc.player.getYaw(), mc.player.getPitch());
                Managers.ROTATION.setRotations(rotation, rotationBackSpeed.get(), movementFix, RotationManager.Priority.Highest);
            } else {
                if (blockPos != null && airTick >= tellyTick.get()) {
                    Rotation rotation = getRotation(blockPos, direction);
                    Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), movementFix, RotationManager.Priority.Highest);
                    place();
                }
                airTick++;
            }
            this.setSuffix("Telly");
        } else {
            if (blockPos == null) {
                Managers.ROTATION.setRotations(new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 180), 89.64F), rotationSpeed.get(), movementFix, RotationManager.Priority.Highest);
            } else {
                if (onAir() || !snap.get()) {
                    Rotation rotation = getRotation(blockPos, direction);
                    Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), movementFix, RotationManager.Priority.Highest);
                }
                place();
            }

            this.setSuffix(snap.get() ? "Snap" : "Normal");
        }
    }

    public void place() {
        if (!onAir()) return;
        boolean hasRotated = RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockPos, sideCheck.get());

        Managers.RENDER.add(blockPos, sideColor.get(), lineColor.get(), fade.get(), shrink.get());

        if (hasRotated) {
            ActionResult interactionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(getVec3(blockPos, direction), direction, blockPos, false));
            if (interactionResult == ActionResult.SUCCESS) {
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (mc.player.isOnGround() && !mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && mode.is(Mode.Telly)) {
            event.setJump(true);
        }
    }

    public int getYLevel() {
        if (!mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && mc.player.fallDistance <= 0.25 && mode.is(Mode.Telly)) {
            return yLevel;
        } else {
            return (int) Math.floor(mc.player.getY()) - 1;
        }
    }

    public void getBlockInfo() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        int baseX = base.getX();
        int baseZ = base.getZ();
        if (isSolidAndNonInteractive(mc.world.getBlockState(base), mc.world, base)) return;
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

    public boolean isSolidAndNonInteractive(BlockState state, World level, BlockPos pos) {
        boolean hasCollision = !state.getCollisionShape(level, pos).isEmpty();

        boolean hasNoMenu = state.createScreenHandlerFactory(level, pos) == null;

        return hasCollision && hasNoMenu;
    }

    private boolean checkBlock(Vec3d baseVec, BlockPos pos) {
        if (!(mc.world.getBlockState(pos).getBlock() instanceof AirBlock) && !(mc.world.getBlockState(pos).getBlock() instanceof LilyPadBlock)) {
            return false;
        }

        if (pos.getY() > getYLevel()) return false;

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (Direction dir : Direction.values()) {
            Vec3d hit = center.add(new Vec3d(dir.getVector().getX(), dir.getVector().getY(), dir.getVector().getZ()).multiply(0.5));
            BlockPos baseBlock = pos.offset(dir);

            if (!isSolidAndNonInteractive(mc.world.getBlockState(baseBlock), mc.world, baseBlock)) continue;

            Vec3d relevant = hit.subtract(baseVec);
            if (relevant.lengthSquared() <= 4.5 * 4.5 && relevant.dotProduct(new Vec3d(dir.getVector().getX(), dir.getVector().getY(), dir.getVector().getZ())) >= 0) {
                if (dir.getOpposite() == Direction.UP && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
                    continue;
                }
                blockPos = new BlockPos(baseBlock);
                direction = dir.getOpposite();
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (e.getType() == EventType.PRE && safeWalk.get() && mode.is(Mode.GodBridge)) {
            mc.options.sneakKey.setPressed(mc.player.isOnGround() && SafeWalk.isOnBlockEdge(0.3F));
        }
    }

    private Rotation getRotation(BlockPos pos, Direction direction) {
        Rotation rotations = onAir() ? RotationUtil.calculate(pos, direction) : RotationUtil.calculate(pos.toCenterPos());
        Rotation reverseYaw = new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 180), rotations.pitch);
        boolean hasRotated = RaytraceUtil.overBlock(reverseYaw, pos, sideCheck.get());
        if (hasRotated) return reverseYaw;
        else return rotations;
    }

    private boolean onAir() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        return mc.world.getBlockState(base).getBlock() instanceof AirBlock || mc.world.getBlockState(base).getBlock() instanceof LilyPadBlock;
    }
}
