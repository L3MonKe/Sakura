package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.utils.render.Render3DUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;

public class TPPlace extends Module {
    public enum Mode {
        Vanilla("原版"),
        Paper("Paper");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);

    

    // Vanilla
    private final NumberValue<Double> vanillaMaxDistance = new NumberValue<>("Max Distance", "最大距离", 20.0, 0.0, 128.0, 0.1, () -> mode.get() == Mode.Vanilla);
    private final NumberValue<Double> vanillaMoveDistance = new NumberValue<>("Move Distance", "移动距离", 20.0, 1.0, 128.0, 0.1, () -> mode.get() == Mode.Vanilla);

    // Paper
    private final NumberValue<Double> paperMaxDistance = new NumberValue<>("Max Distance", "最大距离", 100.0, 0.0, 300.0, 0.1, () -> mode.get() == Mode.Paper);
    private final NumberValue<Double> paperMoveDistance = new NumberValue<>("Move Distance", "移动距离", 20.0, 1.0, 128.0, 0.1, () -> mode.get() == Mode.Paper);
    private final BoolValue interact = new BoolValue("Interact", "交互", true);
    private final BoolValue render = new BoolValue("Render", "渲染目标", true);

    // Color Settings
    public enum ColorMode {
        Static("静态"),
        Sync("同步"),
        Rainbow("彩虹");

        private final String cnName;

        ColorMode(String cnName) {
            this.cnName = cnName;
        }
    }
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Sync);

    // Static Colors
    private final ColorValue sideColor = new ColorValue("Side Color", "面颜色", new Color(255, 0, 0, 40), () -> colorMode.is(ColorMode.Static));
    private final ColorValue lineColor = new ColorValue("Line Color", "线颜色", new Color(255, 0, 0, 120), () -> colorMode.is(ColorMode.Static));

    // Alpha for non-static modes
    private final NumberValue<Integer> sideAlpha = new NumberValue<>("Side Alpha", "面透明度", 40, 0, 255, 1, () -> !colorMode.is(ColorMode.Static));
    private final NumberValue<Integer> lineAlpha = new NumberValue<>("Line Alpha", "线透明度", 120, 0, 255, 1, () -> !colorMode.is(ColorMode.Static));

    public TPPlace() {
        super("TPPlace", "TP放置", Category.Player);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        double maxDist = mode.get() == Mode.Vanilla ? vanillaMaxDistance.get() : paperMaxDistance.get();
        double moveDist = mode.get() == Mode.Vanilla ? vanillaMoveDistance.get() : paperMoveDistance.get();

        // 1. Raycast to find target
        HitResult hitResult = mc.player.raycast(maxDist, 0.05f, false);
        if (hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult blockHit = (BlockHitResult) hitResult;

        // 2. Range check (Only activate if target is out of normal reach)
        // Note: Using 4.5 as a safe default or using interaction manager's reach
        double reach = mc.player.getBlockInteractionRange();
        if (mc.player.getEyePos().distanceTo(hitResult.getPos()) <= reach) return;

        // 3. Hand item check
        ItemStack stack = mc.player.getMainHandStack();
        boolean isBlock = !stack.isEmpty() && stack.getItem() instanceof BlockItem;

        // 4. Calculate placement position
        Direction side = blockHit.getSide();
        BlockPos pos = blockHit.getBlockPos().offset(side);

        // 5. Replaceable / Interactable check
        if (isBlock) {
            BlockState state = mc.world.getBlockState(pos);
            if (!state.isReplaceable()) return;
        } else {
            if (!interact.get()) return;
            BlockState targetState = mc.world.getBlockState(blockHit.getBlockPos());
            if (!isInteractable(targetState)) return;
        }

        // 6. Input check
        if (!mc.options.useKey.isPressed()) return;

        // 7. Calculate Teleport Target (Offset based on side)
        Vec3d offset = getOffsetByDirection(side);
        Vec3d targetPos = pos.toCenterPos().add(offset);

        // 8. Execute TP (Start -> Start... -> End)
        Vec3d startPos = mc.player.getPos();
        doTp(startPos, targetPos, moveDist, false);

        // 9. Place Block
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHit);
        mc.player.swingHand(Hand.MAIN_HAND);

        // 10. Return TP (End -> Start)
        doTp(targetPos, startPos, moveDist, false);
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (!render.get()) return;

        double maxDist = mode.get() == Mode.Vanilla ? vanillaMaxDistance.get() : paperMaxDistance.get();

        HitResult hitResult = mc.player.raycast(maxDist, 0.05f, false);
        if (hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult blockHit = (BlockHitResult) hitResult;

        double reach = mc.player.getBlockInteractionRange();
        if (mc.player.getEyePos().distanceTo(hitResult.getPos()) <= reach) return;

        ItemStack stack = mc.player.getMainHandStack();
        boolean isBlock = !stack.isEmpty() && stack.getItem() instanceof BlockItem;

        Direction side = blockHit.getSide();
        BlockPos pos = blockHit.getBlockPos().offset(side);

        if (isBlock) {
            BlockState state = mc.world.getBlockState(pos);
            if (!state.isReplaceable()) return;
        } else {
            if (!interact.get()) return;
            BlockState targetState = mc.world.getBlockState(blockHit.getBlockPos());
            if (!isInteractable(targetState)) return;
            pos = blockHit.getBlockPos();
        }

        Box box = new Box(pos);
        Color fillColor = null;
        Color outlineColor = null;

        switch (colorMode.get()) {
            case Static -> {
                fillColor = sideColor.get();
                outlineColor = lineColor.get();
            }
            case Sync -> {
                Color c = new Color(ClickGui.color());
                fillColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), sideAlpha.get());
                outlineColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), lineAlpha.get());
            }
            case Rainbow -> {
                Color c = new Color(Color.HSBtoRGB((System.currentTimeMillis() % 2000) / 2000f, 0.8f, 1f));
                fillColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), sideAlpha.get());
                outlineColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), lineAlpha.get());
            }
        }
        
        Render3DUtil.drawFullBox(event.getMatrices(), box, fillColor, outlineColor);
    }

    private boolean isInteractable(BlockState state) {
        Block block = state.getBlock();
        return block instanceof BlockWithEntity ||
               block instanceof CraftingTableBlock ||
               block instanceof AnvilBlock ||
               block instanceof ButtonBlock ||
               block instanceof LeverBlock ||
               block instanceof DoorBlock ||
               block instanceof TrapdoorBlock ||
               block instanceof FenceGateBlock ||
               block instanceof NoteBlock ||
               block instanceof RepeaterBlock ||
               block instanceof ComparatorBlock ||
               block instanceof RespawnAnchorBlock ||
               block instanceof BedBlock ||
               block instanceof LecternBlock ||
               block instanceof BellBlock ||
               block instanceof GrindstoneBlock ||
               block instanceof CartographyTableBlock ||
               block instanceof LoomBlock ||
               block instanceof StonecutterBlock ||
               block instanceof ComposterBlock ||
               block instanceof CakeBlock ||
               block instanceof CandleBlock ||
               block instanceof CommandBlock ||
               block instanceof StructureBlock ||
               block instanceof JigsawBlock;
    }

    private Vec3d getOffsetByDirection(Direction dir) {
        switch (dir) {
            case DOWN: return new Vec3d(0, -1, 0);
            case UP: return new Vec3d(0, 1, 0);
            case NORTH: return new Vec3d(0, 0, -1);
            case SOUTH: return new Vec3d(0, 0, 1);
            case WEST: return new Vec3d(-1, 0, 0);
            case EAST: return new Vec3d(1, 0, 0);
            default: return Vec3d.ZERO;
        }
    }

    private void doTp(Vec3d start, Vec3d end, double maxDist, boolean onGround) {
        double dist = start.distanceTo(end);
        int steps = (int) Math.ceil(dist / maxDist);

        // Reference Logic: Send START packet 'steps' times
        for (int i = 1; i <= steps; i++) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                start.x, start.y, start.z, onGround, mc.player.horizontalCollision
            ));
        }

        // Send END packet
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
            end.x, end.y, end.z, onGround, mc.player.horizontalCollision
        ));
    }
}
