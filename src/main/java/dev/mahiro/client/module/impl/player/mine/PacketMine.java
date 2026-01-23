package dev.mahiro.client.module.impl.player.mine;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.player.BlockEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PacketMine extends Module {
    private final BoolValue autoConfig = new BoolValue("Auto", "自动", false);
    private final BoolValue avoidSelfConfig = new BoolValue("Avoid Self", "避开自身", false, autoConfig::get);
    private final NumberValue<Double> enemyRangeConfig = new NumberValue<>("EnemyRange", "敌人范围", 5.0, 1.0, 10.0, 0.1, autoConfig::get);
    private final BoolValue antiCrawlConfig = new BoolValue("Anti Crawl", "反爬行", false);
    private final BoolValue headConfig = new BoolValue("Target Body", "目标身体", false, autoConfig::get);
    private final BoolValue aboveHeadConfig = new BoolValue("Target Head", "目标头部", false, autoConfig::get);
    private final BoolValue strictDirectionConfig = new BoolValue("Strict Direction", "严格方向", false);
    private final EnumValue<RemineMode> remineConfig = new EnumValue<>("Remine", "重挖", RemineMode.Normal);
    private final BoolValue eatingPause = new BoolValue("Eating Pause", "吃东西时暂停", false);
    private final BoolValue miningFix = new BoolValue("Mining Fix", "挖掘修复", false);
    private final BoolValue doubleBreakConfig = new BoolValue("Double Break", "双重破坏", false);
    private final NumberValue<Integer> mineTicksConfig = new NumberValue<>("Mining Ticks", "挖掘刻数", 20, 5, 60, 1, doubleBreakConfig::get);
    private final NumberValue<Double> rangeConfig = new NumberValue<>("Range", "范围", 4.0, 0.1, 6.0, 0.1);
    private final EnumValue<Swap> swapConfig = new EnumValue<>("Auto Swap", "自动切换", Swap.Silent);
    private final BoolValue rotateConfig = new BoolValue("Rotate", "转头", true);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Back Speed", "回转速度", 10, 0, 10, 1, () -> rotateConfig.get());
    private final BoolValue reTry = new BoolValue("reTry", "重试", false);
    private final BoolValue grimConfig = new BoolValue("Grim", "Grim", false);
    private final BoolValue grimNewConfig = new BoolValue("GrimV3", "GrimV3", false, grimConfig::get);
    private final ColorValue fullColor = new ColorValue("Mine Full", "挖掘填充颜色", new Color(255, 0, 0, 31));
    private final ColorValue lineColor = new ColorValue("Mine Line", "挖掘边框颜色", new Color(255, 0, 0, 233));
    private final ColorValue doneFullColor = new ColorValue("Done Full", "完成面颜色", new Color(0, 255, 0, 23));
    private final ColorValue doneLineColor = new ColorValue("Done Line", "完成线颜色", new Color(0, 255, 0, 233));
    private final BoolValue debugConfig = new BoolValue("Debug", "调试", false);

    private BlockData blockData = null;
    private BlockData blockData2 = null;

    private TimerUtil resetTime = new TimerUtil();

    public PacketMine() {
        super("PacketMine", "发包挖掘", Category.Player);
    }

    public static int lerp(int start, int end, double pct) {
        pct = Math.max(0.0f, Math.min(1.0f, pct));

        return Math.toIntExact(Math.round(start + (end - start) * pct));
    }

    @EventHandler
    public void onClickBlock(BlockEvent event) {
        if (blockData == null || event.getBlockPos() != blockData.getCurrentPos()) {
            if (blockData != null && !mc.world.isAir(blockData.getCurrentPos())) {
                if (blockData.getCurrentPos() == event.getBlockPos()) return;
                blockData2 = blockData;
                if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] Setting fucking blockData2.");
            }
            if (canBreak(event.getBlockPos())) {
                if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] Setting fucking blockData.");
                blockData = new BlockData(
                        event.getBlockPos(),
                        event.getDirection(),
                        System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (nullCheck()) return; // fuck kong zhi zhen
        if (eatingCheck()) return;
        if (blockData == null) return;
        if (mc.player.squaredDistanceTo(blockData.getCurrentPos().toCenterPos()) > Math.pow(rangeConfig.get() + 4, 2)) {
            if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] set blockData = null.");
            blockData = null;
            blockData2 = null;
            return;
        }
        if (mc.world.isAir(blockData.getCurrentPos())) {
            resetTime.reset();
            return;
        }
        if (isReady(blockData)) {
            if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] BLOCK DATA TASKS IS READY.");
            int slot = InvUtil.findFastestTool(mc.world.getBlockState(blockData.getCurrentPos()), swapConfig.get() == Swap.SilentAlt).slot();
            performSwap(slot, false);
            mineTask(blockData);
            performSwap(slot, true);

            if (remineConfig.get() == RemineMode.Normal) {
                if (resetTime.passedMS(calcBreakTime(blockData.getCurrentPos(), swapConfig.get() == Swap.SilentAlt) + 5000L) && reTry.get()) {
                    hookPos(blockData.getCurrentPos(), true);
                }
            }
            if (remineConfig.get() == RemineMode.OFF)
                blockData = null;
        }
        if (blockData2 != null && isReady(blockData2)) {
            if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] BLOCK DATA2 TASKS IS READY.");
            int slot = InvUtil.findFastestTool(mc.world.getBlockState(blockData2.getCurrentPos()), swapConfig.get() == Swap.SilentAlt).slot();
            performSwap(slot, false);
            mineTask(blockData2);
            performSwap(slot, true);
//            hookPos(blockData2.getCurrentPos(), false);
            blockData2 = null;
        }
    }

    private boolean isReady(BlockData data) {
        return System.currentTimeMillis() - data.getStartTime() > calcBreakTime(data.getCurrentPos(), swapConfig.get() == Swap.SilentAlt);
    }

    private void mineTask(BlockData data) {
        if (rotateConfig.get()) {
            Managers.ROTATION.setRotations(RotationUtil.calculate(data.getCurrentPos()), rotationBackSpeed.get(), MovementFix.OFF, RotationManager.Priority.Medium);
            if (grimConfig.get()) {
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(
                        mc.player.getX(), mc.player.getY(), mc.player.getZ(), RotationUtil.calculate(data.getCurrentPos()).yaw, RotationUtil.calculate(data.getCurrentPos()).pitch, mc.player.isOnGround(), mc.player.horizontalCollision));
            }
        }
        stopMiningInternal(data);
    }

    private void performSwap(int slot, boolean back) {
        if (swapConfig.get() == Swap.Off) return;

        if (!back) {
            switch (swapConfig.get()) {
                case Silent -> InvUtil.swap(slot, true);
                case SilentAlt -> InvUtil.invSwap(slot);
                case Normal -> InvUtil.swap(slot, false);
            }
        } else {
            switch (swapConfig.get()) {
                case Silent -> InvUtil.swapBack();
                case SilentAlt -> InvUtil.invSwapBack();
            }
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (blockData != null) {
            renderTask(blockData, event.getMatrices());
        }
        if (blockData2 != null) {
            renderTask(blockData2, event.getMatrices());
        }
    }

    private void renderTask(BlockData data, MatrixStack matrices) {
        double progress = (System.currentTimeMillis() - data.getStartTime()) / calcBreakTime(data.getCurrentPos(), swapConfig.get() == Swap.SilentAlt);
        Box box = new Box(data.getCurrentPos());
        Color lerpedFullColor = new Color(lerp(fullColor.get().getRed(), doneFullColor.get().getRed(), progress),
                lerp(fullColor.get().getGreen(), doneFullColor.get().getGreen(), progress),
                lerp(fullColor.get().getBlue(), doneFullColor.get().getBlue(), progress),
                lerp(fullColor.get().getAlpha(), doneFullColor.get().getAlpha(), progress));
        Color lerpedLineColor = new Color(lerp(lineColor.get().getRed(), doneLineColor.get().getRed(), progress),
                lerp(lineColor.get().getGreen(), doneLineColor.get().getGreen(), progress),
                lerp(lineColor.get().getBlue(), doneLineColor.get().getBlue(), progress),
                lerp(lineColor.get().getAlpha(), doneLineColor.get().getAlpha(), progress));
        Render3DUtil.drawFilledBox(matrices, box, lerpedFullColor);
        Render3DUtil.drawBoxOutline(matrices, box, lerpedLineColor.getRGB(), 1f);
        Vec3d center = new Vec3d(
                box.minX + (box.maxX - box.minX) * 0.5,
                box.minY + (box.maxY - box.minY) * 0.5,
                box.minZ + (box.maxZ - box.minZ) * 0.5
        );
        Render3DUtil.drawText((progress > 1) ? "Completed" : String.format("%.1f", progress * 100.0) + "%", center, 0, 0, 0, Color.WHITE);
    }

    public boolean canBreak(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        List<Block> blackList = List.of(
                Blocks.AIR,
                Blocks.BEDROCK,
                Blocks.END_PORTAL_FRAME,
                Blocks.END_PORTAL,
                Blocks.WATER,
                Blocks.WATER_CAULDRON,
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON,
                Blocks.FIRE
        );
        return !blackList.contains(state.getBlock());
    }

    private void stopMiningInternal(BlockData data) {
        if (debugConfig.get()) ChatUtil.sendMessage("[PacketMine] SENDING MINE PACKET.");
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, data.getCurrentPos(), data.getDirection()));
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, data.getCurrentPos(), data.getDirection()));
    }

    public float calcBreakTime(BlockPos pos, boolean inventory) {
        BlockState blockState = mc.world.getBlockState(pos);

        float hardness = blockState.getHardness(mc.world, pos);

        float breakSpeed = +getBreakSpeed(blockState, inventory);

        if (breakSpeed == -1.0f) {
            return -1.0f;
        }

        float relativeDamage = breakSpeed / hardness / 30.0f;

        int ticks = MathHelper.ceil(0.7f / relativeDamage);

        return (float) ticks * 50.0f;
    }

    public float getBreakSpeed(BlockState blockState, boolean inventory) {
        float maxSpeed = 1.0f;

        int limit = inventory ? mc.player.getInventory().size() : 9;

        for (int i = 0; i < limit; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) {
                continue;
            }
            float speed = stack.getMiningSpeedMultiplier(blockState);

            if (speed > 1.0f) {
                var enchantmentRegistry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
                RegistryEntry<Enchantment> efficiencyEntry = enchantmentRegistry.getOrThrow(Enchantments.EFFICIENCY);

                int efficiencyLevel = EnchantmentHelper.getLevel(efficiencyEntry, stack);

                if (efficiencyLevel > 0) {
                    speed += (float) (efficiencyLevel * efficiencyLevel + 1);
                }

                if (speed > maxSpeed) {
                    maxSpeed = speed;
                }
            }
        }

        return maxSpeed;
    }

    private boolean eatingCheck() {
        return eatingPause.get() && onEating();
    }

    private boolean onEating() {
        return mc.player.isUsingItem() && mc.player.getMainHandStack().contains(DataComponentTypes.FOOD);
    }

    public void hookPos(BlockPos blockPos, boolean reset) {
        if (eatingCheck()) return;

        if (reset) {
            this.blockData = null;
        }

        mc.world.getBlockState(blockPos).onBlockBreakStart(mc.world, blockPos, mc.player);

        Vec3d eyePos = mc.player.getEyePos();
        double dx = eyePos.x - (blockPos.getX() + 0.5);
        double dy = eyePos.y - (blockPos.getY() + 0.5);
        double dz = eyePos.z - (blockPos.getZ() + 0.5);

        Direction side = getInteractDirection(blockPos, strictDirectionConfig.get());
        if (side == null) {
            side = Direction.getFacing((float) dx, (float) dy, (float) dz);
        }

        if (doubleBreakConfig.get()) {
            // https://github.com/GrimAnticheat/Grim/blob/2.0/src/main/java/ac/grim/grimac/checks/impl/misc/FastBreak.java#L76
            // https://github.com/GrimAnticheat/Grim/blob/2.0/src/main/java/ac/grim/grimac/checks/impl/misc/FastBreak.java#L98
            if (grimNewConfig.get()) {
                if (!miningFix.get()) {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                } else {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                }

                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            } else {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockData.getCurrentPos(), side));
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockData.getCurrentPos(), side));
        }

        if (blockPos != blockData2.getCurrentPos()) Mahiro.EVENT_BUS.post(new BlockEvent(blockPos, side));
    }

    public Direction getInteractDirection(final BlockPos blockPos, final boolean strictDirection) {
        Direction direction = getInteractDirectionInternal(blockPos, strictDirection);
        return direction == null ? Direction.UP : direction;
    }

    public Direction getInteractDirectionInternal(final BlockPos blockPos, final boolean strictDirection) {
        Set<Direction> validDirections = getPlaceDirectionsNCP(mc.player.getEyePos(), blockPos.toCenterPos());
        Direction interactDirection = null;
        for (final Direction direction : Direction.values()) {
            final BlockState state = mc.world.getBlockState(blockPos.offset(direction));
            if (state.isAir() || !state.getFluidState().isEmpty()) {
                continue;
            }

            if (state.getBlock() == Blocks.ANVIL || state.getBlock() == Blocks.CHIPPED_ANVIL
                    || state.getBlock() == Blocks.DAMAGED_ANVIL) {
                continue;
            }

            if (strictDirection && !validDirections.contains(direction.getOpposite())) {
                continue;
            }
            interactDirection = direction;
            break;
        }
        if (interactDirection == null) {
            return null;
        }
        return interactDirection.getOpposite();
    }

    public Set<Direction> getPlaceDirectionsNCP(Vec3d eyePos, Vec3d blockPos) {
        return getPlaceDirectionsNCP(eyePos.x, eyePos.y, eyePos.z, blockPos.x, blockPos.y, blockPos.z);
    }

    public Set<Direction> getPlaceDirectionsNCP(final double x, final double y, final double z,
                                                final double dx, final double dy, final double dz) {
        final double xdiff = x - dx;
        final double ydiff = y - dy;
        final double zdiff = z - dz;
        final Set<Direction> dirs = new HashSet<>(6);
        if (ydiff > 0.5) {
            dirs.add(Direction.UP);
        } else if (ydiff < -0.5) {
            dirs.add(Direction.DOWN);
        } else {
            dirs.add(Direction.UP);
            dirs.add(Direction.DOWN);
        }
        if (xdiff > 0.5) {
            dirs.add(Direction.EAST);
        } else if (xdiff < -0.5) {
            dirs.add(Direction.WEST);
        } else {
            dirs.add(Direction.EAST);
            dirs.add(Direction.WEST);
        }
        if (zdiff > 0.5) {
            dirs.add(Direction.SOUTH);
        } else if (zdiff < -0.5) {
            dirs.add(Direction.NORTH);
        } else {
            dirs.add(Direction.SOUTH);
            dirs.add(Direction.NORTH);
        }
        return dirs;
    }

    public enum RemineMode {
        Normal,
        Instant,
        OFF
    }

    public enum Swap {
        Normal,
        Silent,
        SilentAlt,
        Off
    }
}
