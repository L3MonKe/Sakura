package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MouseClickEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.FallingPlayer;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.player.SlotUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.world.BlockUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.*;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import java.awt.*;
import java.util.Arrays;

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

    private enum Raytrace {
        Normal,
        Hypixel
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.GodBridge);
    private final EnumValue<Raytrace> raytrace = new EnumValue<>("Raytrace", "光线追踪", Raytrace.Normal);
    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Normal);
    private final BoolValue swapBack = new BoolValue("SwapBack", "停用还原", true, () -> swapMode.is(SwapMode.Normal));
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final NumberValue<Integer> tellyTick = new NumberValue<>("Telly Tick", "Telly延迟", 0, 0, 8, 1, () -> mode.is(Mode.Telly));
    private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, () -> mode.is(Mode.Telly));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1, () -> !raytrace.is(Raytrace.Hypixel));
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, () -> mode.is(Mode.Telly));
    private final BoolValue sideCheck = new BoolValue("Strict Side", "严格放置面", false);
    private final BoolValue silentRotation = new BoolValue("Silent Rotation", "静默旋转", false);
    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true, () -> !silentRotation.get());
    private final BoolValue safeWalk = new BoolValue("Safe Walk", "安全行走", true);

    private final BoolValue legit = new BoolValue("Legit", "Legit模式", false, () -> mode.is(Mode.Telly));
    private final NumberValue<Integer> legitBlocks = new NumberValue<>("Legit Blocks", "Legit放置方块数", 3, 1, 10, 1, () -> mode.is(Mode.Telly) && legit.get());

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue fade = new BoolValue("Fade", "变淡", false, render::get);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private int yLevel;
    private int airTicks;
    private boolean hasJump;
    private int legitBlockCount;
    private boolean inLegitPhase;
    private boolean pendingTelly;
    private boolean swapped;
    private boolean invSwapped;
    private boolean shouldSwapBack;
    private BlockInfo blockInfo;
    private Rotation lastRotation;
    private int rotateCount;

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @Override
    protected void onEnable() {
        blockInfo = null;
        lastRotation = null;
        rotateCount = 0;
        swapped = false;
        invSwapped = false;
        shouldSwapBack = false;
        legitBlockCount = 0;
        inLegitPhase = false;
        pendingTelly = false;
    }

    @Override
    protected void onDisable() {
        blockInfo = null;
        if (shouldSwapBack) {
            InvUtil.swapBack();
        }
    }

    @EventHandler
    private void onMouse(MouseClickEvent event) {
        if (mc.currentScreen != null) return;
        if (event.getButton() == InputUtil.GLFW_MOUSE_BUTTON_LEFT || event.getButton() == InputUtil.GLFW_MOUSE_BUTTON_RIGHT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (e.getType() == EventType.PRE && safeWalk.get() && mode.is(Mode.GodBridge)) {
            mc.options.sneakKey.setPressed(mc.player.isOnGround() && SafeWalk.isOnBlockEdge(0.3F));
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        hasJump = false;
        boolean isLegitMode = mode.is(Mode.Telly) && legit.get();

        if (mode.is(Mode.Telly) && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
            if (isLegitMode) {
                if (mc.player.isOnGround() && pendingTelly) {
                    mc.options.jumpKey.setPressed(true);
                    hasJump = true;
                    pendingTelly = false;
                    inLegitPhase = false;
                }
            } else {
                if (mc.player.isOnGround()) {
                    mc.options.jumpKey.setPressed(true);
                    hasJump = true;
                }
            }
        }

        updateBlockInfo();

        if (blockInfo != null) {
            boolean reachable = true;
            if (mc.player.getVelocity().y < -0.1) {
                FallingPlayer fallingPlayer = new FallingPlayer(mc.player);
                fallingPlayer.calculate(2);
                if (blockInfo.position.getY() > fallingPlayer.y) {
                    reachable = false;
                }
            }
            double strength = mc.player.getVelocity().horizontalLength();
            if ((!reachable || strength >= 1.5D) && rotateCount <= 8 && findItem().found()) {
                Rotation rotation = getRotation(blockInfo);
                rotateCount++;
                Managers.ROTATION.rotations = rotation;
                Managers.ROTATION.setActive(true);
                PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(
                        rotation.yaw, MathHelper.clamp(rotation.pitch, -90.0f, 90.0f),
                        mc.player.isOnGround(), mc.player.horizontalCollision));
                ActionResult result = mc.interactionManager.interactBlock(
                        mc.player,
                        Hand.MAIN_HAND,
                        new BlockHitResult(getVec3(blockInfo.position, blockInfo.dir), blockInfo.dir, blockInfo.position, false)
                );
                if (result.isAccepted()) {
                    if (swingHand.get()) mc.player.swingHand(Hand.MAIN_HAND);
                    else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));

                    if (render.get()) {
                        Managers.RENDER.add(blockInfo.blockPos, sideColor.get(), lineColor.get(), fade.get(), shrink.get());
                    }
                }
                return;
            } else {
                rotateCount = 0;
            }
        }

        MovementFix movementFix;
        if (silentRotation.get()) {
            movementFix = MovementFix.OFF;
        } else {
            movementFix = moveFix.get() ? MovementFix.NORMAL : MovementFix.OFF;
        }
        boolean silent = silentRotation.get();
        if (mode.is(Mode.Telly)) {
            if (mc.player.isOnGround()) {
                yLevel = MathHelper.floor(mc.player.getY()) - 1;
                airTicks = 0;
                blockInfo = null;
                if (!silent) {
                    Rotation rotation = new Rotation(mc.player.getYaw(), lastRotation == null ? mc.player.getPitch() : lastRotation.pitch);
                    Managers.ROTATION.setRotations(rotation, rotationBackSpeed.get(), movementFix);
                }

                if (isLegitMode && !hasJump) {
                    inLegitPhase = true;
                }
            } else {
                if (blockInfo != null) {
                    int speed = rotationSpeed.get();
                    if (raytrace.is(Raytrace.Hypixel)) {
                        speed = airTicks == 0 ? 127 : 35;
                    }
                    if (!silent) {
                        Managers.ROTATION.setRotations(getRotation(blockInfo), speed, movementFix);
                    }
                }

                if (airTicks >= tellyTick.get() && blockInfo != null) {
                    FindItemResult item = findItem();
                    if (item.found()) {
                        if (silent) {
                            silentPlace(item);
                        } else {
                            place(item);
                        }
                        if (isLegitMode) {
                            pendingTelly = true;
                        }
                    }
                }
                airTicks++;
            }
        } else if (blockInfo != null) {
            FindItemResult item = findItem();
            if (item.found()) {
                if (silent) {
                    silentPlace(item);
                } else {
                    Managers.ROTATION.setRotations(getRotation(blockInfo), rotationSpeed.get(), movementFix);
                    place(item);
                }
            }
        }

        if (isLegitMode && inLegitPhase && blockInfo != null) {
            FindItemResult item = findItem();
            if (item.found()) {
                if (silent) {
                    silentPlace(item);
                } else {
                    Managers.ROTATION.setRotations(getRotation(blockInfo), rotationSpeed.get(), movementFix);
                    if (placeAndCheck(item)) {
                        legitBlockCount++;
                        if (legitBlockCount >= legitBlocks.get()) {
                            pendingTelly = true;
                            legitBlockCount = 0;
                            inLegitPhase = false;
                        }
                    }
                }
            }
        }

        switch (swapMode.get()) {
            case Silent -> {
                if (swapped) {
                    swapped = false;
                    InvUtil.swapBack();
                }
            }
            case InvSwitch -> {
                if (invSwapped) {
                    invSwapped = false;
                    InvUtil.invSwapBack();
                }
            }
        }
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (hasJump) {
            mc.options.jumpKey.setPressed(false);
            hasJump = false;
        }
    }

    private int getYLevel() {
        if (keepY.get() && !mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && mode.is(Mode.Telly) && mc.player.fallDistance <= 0.25) {
            return yLevel;
        } else {
            return MathHelper.floor(mc.player.getY()) - 1;
        }
    }

    public static Vec3d getVec3(BlockPos pos, Direction face) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        if (face != Direction.UP && face != Direction.DOWN) {
            y += 0.08;
        } else {
            x += MathUtil.getRandom(-0.3, 0.3);
            z += MathUtil.getRandom(-0.3, 0.3);
        }

        if (face == Direction.WEST || face == Direction.EAST) {
            z += MathUtil.getRandom(-0.3, 0.3);
        }

        if (face == Direction.SOUTH || face == Direction.NORTH) {
            x += MathUtil.getRandom(-0.3, 0.3);
        }

        return new Vec3d(x, y, z);
    }

    private boolean validItem(ItemStack itemStack, BlockPos pos) {
        if (!(itemStack.getItem() instanceof BlockItem blockItem)) return false;

        Block block = blockItem.getBlock();

        if (block instanceof TntBlock) return false;

        if (!Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, pos))) return false;
        return !(block instanceof FallingBlock) || !FallingBlock.canFallThrough(mc.world.getBlockState(pos));
    }

    private FindItemResult findItem() {
        switch (swapMode.get()) {
            case None -> {
                if (InvUtil.testInOffHand(itemStack -> validItem(itemStack, blockInfo.position))) {
                    return new FindItemResult(SlotUtil.OFFHAND, mc.player.getOffHandStack().getCount(), mc.player.getOffHandStack().getMaxCount());
                }
                if (InvUtil.testInMainHand(itemStack -> validItem(itemStack, blockInfo.position))) {
                    return new FindItemResult(mc.player.getInventory().getSelectedSlot(), mc.player.getMainHandStack().getCount(), mc.player.getMainHandStack().getMaxCount());
                }
                return new FindItemResult(-1, 0, 0);
            }
            case InvSwitch -> {
                return InvUtil.find(itemStack -> validItem(itemStack, blockInfo.position));
            }
            default -> {
                return InvUtil.findInHotbar(itemStack -> validItem(itemStack, blockInfo.position));
            }
        }
    }

    private void place(FindItemResult item) {
        placeAndCheck(item);
    }

    private void silentPlace(FindItemResult item) {
        if (!onAir()) return;
        if (!BlockUtil.canPlaceAt(blockInfo.blockPos)) return;

        Rotation rot = getRotation(blockInfo);
        float clampedPitch = MathHelper.clamp(rot.pitch, -90.0f, 90.0f);

        PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(
                rot.yaw, clampedPitch,
                mc.player.isOnGround(), mc.player.horizontalCollision));

        if (raytrace.is(Raytrace.Normal) && !RaytraceUtil.overBlock(rot, blockInfo.position, false)) return;

        switch (swapMode.get()) {
            case Normal -> {
                boolean should = swapBack.get();
                InvUtil.swap(item.slot(), should);
                shouldSwapBack = should;
            }
            case Silent -> swapped = InvUtil.swap(item.slot(), true);
            case InvSwitch -> invSwapped = InvUtil.invSwap(item.slot());
        }

        ActionResult result = mc.interactionManager.interactBlock(mc.player, item.getHand(), new BlockHitResult(getVec3(blockInfo.position, blockInfo.dir), blockInfo.dir, blockInfo.position, false));
        if (result.isAccepted()) {
            if (swingHand.get()) mc.player.swingHand(item.getHand());
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(item.getHand()));
        }

        if (render.get()) {
            Managers.RENDER.add(blockInfo.blockPos, sideColor.get(), lineColor.get(), fade.get(), shrink.get());
        }
    }

    private boolean placeAndCheck(FindItemResult item) {
        if (!onAir()) return false;
        if (!BlockUtil.canPlaceAt(blockInfo.blockPos)) return false;

        switch (swapMode.get()) {
            case Normal -> {
                boolean should = swapBack.get();
                InvUtil.swap(item.slot(), should);
                shouldSwapBack = should;
            }
            case Silent -> swapped = InvUtil.swap(item.slot(), true);
            case InvSwitch -> invSwapped = InvUtil.invSwap(item.slot());
        }

        if (raytrace.is(Raytrace.Normal) && !RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockInfo.position, false)) {
            return false;
        }

        ActionResult result = mc.interactionManager.interactBlock(mc.player, item.getHand(), new BlockHitResult(getVec3(blockInfo.position, blockInfo.dir), blockInfo.dir, blockInfo.position, false));
        if (result.isAccepted()) {
            if (swingHand.get()) mc.player.swingHand(item.getHand());
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(item.getHand()));
        }

        if (render.get()) {
            Managers.RENDER.add(blockInfo.blockPos, sideColor.get(), lineColor.get(), fade.get(), shrink.get());
        }
        return result.isAccepted();
    }

    private void updateBlockInfo() {
        blockInfo = null;

        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        int baseX = base.getX();
        int baseZ = base.getZ();
        if (mc.world.getBlockState(base).hasSolidTopSurface(mc.world, base, mc.player)) return;
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
        if (!onAir()) {
            return false;
        }

        if (pos.getY() > getYLevel()) {
            return false;
        }

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
                if (dir.getOpposite() == Direction.UP && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
                    continue;
                }
                blockInfo = new BlockInfo(pos, new BlockPos(baseBlock), dir.getOpposite());
                return true;
            }
        }
        return false;
    }

    private Rotation getRotation(BlockInfo blockCache) {
        if (lastRotation == null) {
            return new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 135.0F), 82.0F);
        }

        if (!onAir() || blockCache == null || blockCache.dir == null) {
            return lastRotation;
        }

        if (raytrace.is(Raytrace.Normal)) {
            Rotation calculate = RotationUtil.calculate(blockCache.position, blockCache.dir);
            Rotation reverseYaw = new Rotation(MathHelper.wrapDegrees(mc.player.getYaw() - 180), calculate.pitch);
            boolean hasRotated = RaytraceUtil.overBlock(reverseYaw, blockCache.position, false);
            if (hasRotated) {
                lastRotation = reverseYaw;
                return reverseYaw;
            } else {
                lastRotation = calculate;
                return calculate;
            }
        }

        Rotation calculate = RotationUtil.calculate(blockCache.position, blockCache.dir);

        Float[] yawArray = {
                -135F,
                -90F,
                -45F,
                0F,
                45F,
                90F,
                135F,
                180F,
                calculate.yaw
        };

        Arrays.sort(yawArray, (a, b) ->
                Float.compare(
                        Math.abs(MathHelper.wrapDegrees(mc.player.getYaw() - 180 - a)),
                        Math.abs(MathHelper.wrapDegrees(mc.player.getYaw() - 180 - b))
                )
        );

        Rotation result = new Rotation(yawArray[0], 82.0F);
        lastRotation = result;
        return result;
    }

    private boolean onAir() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        return mc.world.getBlockState(base).getBlock() instanceof AirBlock || mc.world.getBlockState(base).getBlock() instanceof LilyPadBlock;
    }

    private record BlockInfo(BlockPos blockPos, BlockPos position, Direction dir) {
    }
}
