package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MouseButtonEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.player.StrafeEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.event.type.KeyAction;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.world.BlockUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.util.AuthUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.util.InputUtil;
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

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Telly);
    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Normal);
    private final BoolValue swapBack = new BoolValue("SwapBack", "停用还原", true, () -> swapMode.is(SwapMode.Normal));
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final NumberValue<Integer> tellyTick = new NumberValue<>("Telly Tick", "Telly延迟", 1, 0, 8, 1, () -> mode.is(Mode.Telly));
    private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, () -> mode.is(Mode.Telly));
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

    private int yLevel;
    private BlockCache blockCache;
    private int airTicks;
    private boolean shouldSwapBack;
    private FindItemResult findItemResult;

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
        return mode.get().name();
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
        findItemResult = null;
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (mc.currentScreen != null) return;
        if (event.getAction() != KeyAction.Press) return;
        if (event.getButton() == InputUtil.GLFW_MOUSE_BUTTON_LEFT) {
            event.setCancelled(true);
            mc.options.attackKey.setPressed(false);
        }
        if (event.getButton() == InputUtil.GLFW_MOUSE_BUTTON_RIGHT) {
            event.setCancelled(true);
            mc.options.useKey.setPressed(false);
        }
    }

    @EventHandler
    private void onMotion(MotionEvent e) {
        if (e.getType() == EventType.PRE && safeWalk.get() && mode.is(Mode.GodBridge)) {
            mc.options.sneakKey.setPressed(mc.player.isOnGround() && SafeWalk.isOnBlockEdge(0.3F));
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (VerificationClient.getTransport() == null || AuthUtil.authed.get().length() != 32) {
            return;
        }

        if (nullCheck()) return;

        getBlockInfo();

        MovementFix movementFix = moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF;
        if (mode.is(Mode.Telly)) {
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
        if (mc.player.isOnGround() && MoveUtil.isMoving() && mode.is(Mode.Telly) && !mc.options.jumpKey.isPressed()) {
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
        if (keepY.get() && !mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && mode.is(Mode.Telly) && mc.player.fallDistance <= 1) {
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

        boolean hasRotated = RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockCache.facing, blockCache.position, sideCheck.get());
        if (!hasRotated) return;

        BlockPos targetPos = blockCache.position.offset(blockCache.facing);
        if (!BlockUtil.canPlaceAt(targetPos)) return;

        boolean invSwapped = false;
        if (swapMode.is(SwapMode.InvSwitch)) {
            findItemResult = InvUtil.find(itemStack -> validItem(itemStack, blockCache.position), 0, 35);
            if (!findItemResult.found()) return;
            invSwapped = InvUtil.invSwap(findItemResult.slot());
        } else {
            findItemResult = InvUtil.findInHotbar(itemStack -> validItem(itemStack, blockCache.position));
            if (!findItemResult.found()) return;

            boolean remember = swapMode.is(SwapMode.Silent) || (swapMode.is(SwapMode.Normal) && swapBack.get());
            InvUtil.swap(findItemResult.isOffhand() ? mc.player.getInventory().getSelectedSlot() : findItemResult.slot(), remember);
        }

        Hand hand = findItemResult.getHand();

        ActionResult result = mc.interactionManager.interactBlock(mc.player, hand, new BlockHitResult(blockCache.hitVec, blockCache.facing, blockCache.position, false));

        if (result.isAccepted()) {
            if (swingHand.get()) mc.player.swingHand(hand);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
        }

        if (invSwapped) {
            InvUtil.invSwapBack();
        }

        if (render.get()) {
            Managers.RENDER.add(targetPos, sideColor.get(), lineColor.get(), fade.get(), shrink.get());
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
                            if (checkBlock(baseVec, new BlockPos(baseX + (rev1 == 0 ? x : -x), getYLevel() - y, baseZ + (rev2 == 0 ? z : -z)))) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkBlock(Vec3d baseVec, BlockPos pos) {
        if (BlockUtil.solid(mc.world.getBlockState(pos))) return false;

        if (pos.getY() > getYLevel()) return false;

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (Direction dir : Direction.values()) {
            Vec3d hit = center.add(new Vec3d(dir.getVector()).multiply(0.5));
            BlockPos baseBlockPos = pos.offset(dir);

            if (!mc.world.getBlockState(baseBlockPos).hasSolidTopSurface(mc.world, baseBlockPos, mc.player)) continue;

            Vec3d relevant = hit.subtract(baseVec);
            if (relevant.lengthSquared() <= 4.5 * 4.5 && relevant.dotProduct(new Vec3d(dir.getVector())) >= 0) {
                if (dir.getOpposite() == Direction.UP && mode.is(Mode.GodBridge) && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
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
        boolean hasRotated = RaytraceUtil.overBlock(reverseYaw, blockCache.facing, blockCache.position, sideCheck.get());
        if (hasRotated) return reverseYaw;
        return rotations;
    }

    private record BlockCache(BlockPos position, Direction facing, Vec3d hitVec) {
    }
}
