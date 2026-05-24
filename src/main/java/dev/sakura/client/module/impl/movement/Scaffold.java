package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FallingPlayer;
import dev.sakura.client.utils.player.MoveUtil;
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
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", "自动搭路", Category.Movement);
    }

    private static final List<Block> BLACKLISTED_BLOCKS = List.of(
            Blocks.AIR,
            Blocks.WATER,
            Blocks.LAVA,
            Blocks.ENCHANTING_TABLE,
            Blocks.GLASS_PANE,
            Blocks.IRON_BARS,
            Blocks.SNOW,
            Blocks.COAL_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.TORCH,
            Blocks.ANVIL,
            Blocks.NOTE_BLOCK,
            Blocks.JUKEBOX,
            Blocks.TNT,
            Blocks.GOLD_ORE,
            Blocks.IRON_ORE,
            Blocks.LAPIS_ORE,
            Blocks.STONE_PRESSURE_PLATE,
            Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Blocks.STONE_BUTTON,
            Blocks.LEVER,
            Blocks.TALL_GRASS,
            Blocks.TRIPWIRE,
            Blocks.TRIPWIRE_HOOK,
            Blocks.RAIL,
            Blocks.CORNFLOWER,
            Blocks.RED_MUSHROOM,
            Blocks.BROWN_MUSHROOM,
            Blocks.VINE,
            Blocks.SUNFLOWER,
            Blocks.LADDER,
            Blocks.FURNACE,
            Blocks.SAND,
            Blocks.CACTUS,
            Blocks.DISPENSER,
            Blocks.DROPPER,
            Blocks.CRAFTING_TABLE,
            Blocks.COBWEB,
            Blocks.PUMPKIN,
            Blocks.COBBLESTONE_WALL,
            Blocks.OAK_FENCE,
            Blocks.REDSTONE_TORCH,
            Blocks.FLOWER_POT
    );

    private enum RaytraceMode {
        Hypixel,
        Normal,
        Strict
    }

    private final BoolValue telly = new BoolValue("Telly Bridge", "Telly Bridge", true);
    private final BoolValue snap = new BoolValue("Snap", "Snap", false, () -> !telly.get());
    private final EnumValue<RaytraceMode> raytrace = new EnumValue<>("Raytrace", "Raytrace", RaytraceMode.Hypixel);
    private final BoolValue spoofSwap = new BoolValue("Spoof Swap", "静默切换", true);
    //    private final EnumValue<SwapMode> swapMode = new EnumValue<>("Swap Mode", "切换模式", SwapMode.Normal);
//    private final BoolValue swapBack = new BoolValue("SwapBack", "停用还原", true, () -> swapMode.is(SwapMode.Normal));
    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final NumberValue<Integer> tellyTicks = new NumberValue<>("Telly Tick", "Telly延迟", 0, 0, 8, 1, () -> telly.get());
    //    private final BoolValue keepY = new BoolValue("Keep Y", "保持Y轴", true, () -> mode.is(Mode.Telly));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1, () -> telly.get());
    private final BoolValue sideCheck = new BoolValue("Strict Side", "严格放置面", false);
    //    private final BoolValue silentRotation = new BoolValue("Silent Rotation", "静默旋转", false);
//    private final BoolValue moveFix = new BoolValue("Movement Fix", "移动修复", true, () -> !silentRotation.get());
    private final BoolValue safeWalk = new BoolValue("Safe Walk", "安全行走", true);

//    private final BoolValue legit = new BoolValue("Legit", "Legit模式", false, () -> mode.is(Mode.Telly));
//    private final NumberValue<Integer> legitBlocks = new NumberValue<>("Legit Blocks", "Legit放置方块数", 3, 1, 10, 1, () -> mode.is(Mode.Telly) && legit.get());

    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue fade = new BoolValue("Fade", "变淡", false, render::get);
    private final BoolValue shrink = new BoolValue("Shrink", "收缩", true, render::get);
    private final ColorValue sideColor = new ColorValue("Side Color", "侧面颜色", new Color(255, 183, 197, 100), render::get);
    private final ColorValue lineColor = new ColorValue("Line Color", "线条颜色", new Color(255, 105, 180), render::get);

    private int airTick;
    private int yLevel;
    private BlockPos blockPos;
    private Direction enumFacing;
    private int oldSlot = -1;
    private Rotation rotation;
    private int rotateCount = 0;

    @Override
    public String getSuffix() {
        return telly.get() ? "Telly" : snap.get() ? "Snap" : "GodBridge";
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            oldSlot = mc.player.getInventory().getSelectedSlot();
        }

        airTick = 0;
        blockPos = null;
        enumFacing = null;
        rotation = null;
        rotateCount = 0;
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;

        boolean isHoldingShift = InputUtil.isKeyPressed(mc.getWindow(), mc.options.sneakKey.getDefaultKey().getCode());
        mc.options.sneakKey.setPressed(isHoldingShift);

        if (oldSlot != -1) {
            mc.player.getInventory().setSelectedSlot(oldSlot);
        }
        yLevel = 0;
    }

    @EventHandler
    private void onMotion(MotionEvent event) {
        if (telly.get() || !safeWalk.get()) return;
        mc.options.sneakKey.setPressed(mc.player.isOnGround() && SafeWalk.isOnBlockEdge(0.3F));
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        int slotId = findBlockSlot();
        if (slotId != -1 && mc.player.getInventory().getSelectedSlot() != slotId) {
            mc.player.getInventory().setSelectedSlot(slotId);
        }

        if (mc.player.isOnGround()) {
            airTick = 0;
            yLevel = MathHelper.floor(mc.player.getY()) - 1;
        } else {
            airTick++;
        }

        getBlockInfo();

        if (blockPos != null) {
            boolean reachable = true;
            if (mc.player.getVelocity().y < -0.1) {
                FallingPlayer fallingPlayer = new FallingPlayer(mc.player);
                fallingPlayer.calculate(2);
                if (blockPos.getY() > fallingPlayer.getY()) {
                    reachable = false;
                }
            }
            double strength = mc.player.getVelocity().getHorizontal().length();
            if ((!reachable || strength >= 1.5D) && rotateCount <= 8 && getBlockCount() >= 1 && isValidStack(mc.player.getInventory().getSelectedStack())) {
                Rotation rotation = getRotation(blockPos, enumFacing);
                Sakura.skipTicks++;
                rotateCount++;
                Managers.ROTATION.rotations = rotation;
                Managers.ROTATION.setActive(true);
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotation.yaw, rotation.pitch, mc.player.isOnGround(), mc.player.horizontalCollision));
                ActionResult result = mc.interactionManager.interactBlock(
                        mc.player,
                        Hand.MAIN_HAND,
                        new BlockHitResult(getVec3(blockPos, enumFacing), enumFacing, blockPos, false)
                );
                if (result.isAccepted()) {
                    if (swingHand.get()) mc.player.swingHand(Hand.MAIN_HAND);
                    else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));

                    if (render.get()) {
                        Managers.RENDER.add(blockPos.offset(enumFacing), sideColor.get(), lineColor.get(), fade.get(), shrink.get());
                    }
                }
                return;
            } else {
                rotateCount = 0;
            }
        }

        if (telly.get()) {
            handleTelly();
        } else {
            handleNormal();
        }
    }

    @EventHandler
    private void onMoveInput(MoveInputEvent event) {
        if (mc.player.isOnGround() && !mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && telly.get()) {
            event.setJump(true);
        }
    }

    @EventHandler
    private void onUpdateHeldItem(HeldItemRendererEvent event) {
        if (!spoofSwap.get() || oldSlot == -1 || event.getHand() != Hand.MAIN_HAND) {
            return;
        }

        event.setItem(mc.player.getInventory().getStack(oldSlot));
    }

    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (isValidStack(stack)) {
                return i;
            }
        }
        return -1;
    }

    private int getBlockCount() {
        int total = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (isValidStack(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    private void handleTelly() {
        if (mc.player.isOnGround()) {
            Managers.ROTATION.setRotations(new Rotation(mc.player.getYaw(), rotation == null ? mc.player.getPitch() : rotation.pitch), rotationBackSpeed.get());
            return;
        }

        rotation = getRotation(blockPos, enumFacing);
        int speed = rotationSpeed.get();

        if (raytrace.is(RaytraceMode.Hypixel)) {
            speed = airTick <= 1 ? 127 : 35;
        }

        Managers.ROTATION.setRotations(rotation, speed);

        if (airTick > tellyTicks.get()) {
            place();
        }
    }

    private void handleNormal() {
        if (onAir() || !snap.get()) {
            rotation = getRotation(blockPos, enumFacing);
            Managers.ROTATION.setRotations(rotation, rotationSpeed.get());
        }
        place();
    }

    private void place() {
        if (!onAir() || blockPos == null || enumFacing == null || !isValidStack(mc.player.getInventory().getSelectedStack())) {
            return;
        }

        if (switch (raytrace.get()) {
            case Hypixel -> !Managers.ROTATION.isDone();
            case Normal -> !RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), blockPos, false);
            case Strict -> !RaytraceUtil.overBlock(Managers.ROTATION.getRotation(), enumFacing, blockPos, false);
        }) {
            return;
        }

        ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(getVec3(blockPos, enumFacing), enumFacing, blockPos, false));
        if (result.isAccepted()) {
            if (swingHand.get()) mc.player.swingHand(Hand.MAIN_HAND);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));

            if (render.get()) {
                Managers.RENDER.add(blockPos.offset(enumFacing), sideColor.get(), lineColor.get(), fade.get(), shrink.get());
            }
        }
    }

    private int getYLevel() {
        if (!mc.options.jumpKey.isPressed() && MoveUtil.isMoving() && mc.player.fallDistance <= 0.25F && telly.get()) {
            return yLevel;
        }
        return MathHelper.floor(mc.player.getY()) - 1;
    }

    private void getBlockInfo() {
        blockPos = null;
        enumFacing = null;

        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        int baseX = base.getX();
        int baseZ = base.getZ();

        if (!onAir()) {
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
                            BlockPos pos = new BlockPos(baseX + (rev1 == 0 ? x : -x), getYLevel() - y, baseZ + (rev2 == 0 ? z : -z));
                            if (checkBlock(baseVec, pos)) {
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

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (Direction direction : Direction.values()) {
            Vec3d hit = center.add(new Vec3d(direction.getVector()).multiply(0.5));
            Vec3i baseBlock = pos.add(direction.getVector());
            BlockPos baseBlockPos = new BlockPos(baseBlock.getX(), baseBlock.getY(), baseBlock.getZ());

            if (!mc.world.getBlockState(baseBlockPos).hasSolidTopSurface(mc.world, baseBlockPos, mc.player)) continue;

            Vec3d relevant = hit.subtract(baseVec);
            if (relevant.lengthSquared() <= 4.5 * 4.5 && relevant.dotProduct(new Vec3d(direction.getVector())) >= 0) {
                if (direction.getOpposite() == Direction.UP && MoveUtil.isMoving() && !mc.options.jumpKey.isPressed()) {
                    continue;
                }

                blockPos = baseBlockPos;
                enumFacing = direction.getOpposite();
                return true;
            }
        }

        return false;
    }

    private Rotation getRotation(BlockPos pos, Direction direction) {
        if (rotation == null) {
            return new Rotation(MathHelper.wrapDegrees(mc.player.getPitch() - 135.0F), 82.0F);
        }

        if (!onAir() || pos == null || direction == null) {
            return rotation;
        }

        Rotation calculated = RotationUtil.calculate(pos, direction);
        Float[] yawArray = {
                -135F,
                -90F,
                -45F,
                0F,
                45F,
                90F,
                135F,
                180F,
                calculated.yaw
        };
        Arrays.sort(yawArray, (a, b) ->
                Float.compare(
                        Math.abs(MathHelper.wrapDegrees(mc.player.getYaw() - 180 - a)),
                        Math.abs(MathHelper.wrapDegrees(mc.player.getYaw() - 180 - b))
                )
        );

        if (raytrace.is(RaytraceMode.Hypixel)) {
            return new Rotation(yawArray[0], 82.0F);
        }

        float[] pitchArray = {75.0F, 82.0F, 87.0F};

        for (float yaw : yawArray) {
            for (float pitch : pitchArray) {
                Rotation candidate = new Rotation(yaw + MathUtil.getRandom(-0.3F, 0.3F), pitch + MathUtil.getRandom(-0.3F, 0.3F));
                boolean matches = raytrace.is(RaytraceMode.Normal) ? RaytraceUtil.overBlock(candidate, pos, false) : RaytraceUtil.overBlock(candidate, direction, pos, false);
                if (matches) {
                    return candidate;
                }
            }

            for (int pitch = -90; pitch < 90; pitch++) {
                Rotation candidate = new Rotation(yaw, pitch);
                boolean matches = raytrace.is(RaytraceMode.Normal) ? RaytraceUtil.overBlock(candidate, pos, false) : RaytraceUtil.overBlock(candidate, direction, pos, false);
                if (matches) {
                    return candidate;
                }
            }
        }

        return calculated;
    }

    private boolean onAir() {
        Vec3d baseVec = mc.player.getEyePos();
        BlockPos base = BlockPos.ofFloored(baseVec.x, getYLevel(), baseVec.z);
        return mc.world.getBlockState(base).isReplaceable();
    }

    private Vec3d getVec3(BlockPos pos, Direction face) {
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

    private boolean isValidStack(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) {
            return false;
        }

        String name = stack.toHoverableText().getString();
        if (name.contains("Click") || name.contains("点击")) {
            return false;
        }

        Block block = ((BlockItem) stack.getItem()).getBlock();
        if (block instanceof FlowerBlock || block instanceof BushBlock || block instanceof FungusBlock || block instanceof CropBlock) {
            return false;
        }

        return !(block instanceof SlabBlock) && !BLACKLISTED_BLOCKS.contains(block);
    }

}
