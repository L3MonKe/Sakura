package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.player.MotionEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.player.AutoPearl;
import dev.mahiro.client.utils.player.FindItemResult;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import static dev.mahiro.client.utils.packet.PacketUtil.sendSequencedPacket;

public class Phase extends Module {
    public Phase() {
        super("Phase", "穿墙", Category.Movement);
    }

    public enum Mode {
        Pearl,
        Clip
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Pearl);

    private final BoolValue attack = new BoolValue("Break", "破坏", true, () -> mode.is(Mode.Pearl));
    private final BoolValue scaffolding = new BoolValue("Scaffolding", "脚手架", true, () -> mode.is(Mode.Pearl) && attack.get());
    private final BoolValue itemFrame = new BoolValue("Item Frame", "物品展示框", true, () -> mode.is(Mode.Pearl) && attack.get());
    private final BoolValue painting = new BoolValue("Painting", "画", true, () -> mode.is(Mode.Pearl) && attack.get());

    private final BoolValue inventory = new BoolValue("Inv Swap", "背包切换", true, () -> mode.is(Mode.Pearl));

    private final BoolValue yawStep = new BoolValue("Yaw Step", "偏航步进", true, () -> mode.is(Mode.Pearl));
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, () -> mode.is(Mode.Pearl) && yawStep.get());
    private final NumberValue<Double> fov = new NumberValue<>("Fov", "视场角", 10.0, 0.0, 50.0, 1.0, () -> mode.is(Mode.Pearl) && yawStep.get());
    private final EnumValue<RotationManager.Priority> priority = new EnumValue<>("Priority", "优先级", RotationManager.Priority.Low, () -> mode.is(Mode.Pearl) && yawStep.get());

    private final BoolValue bypass = new BoolValue("Bypass", "绕过", false, () -> mode.is(Mode.Clip));
    private final NumberValue<Double> delay = new NumberValue<>("Delay", "延迟", 5.0, 1.0, 10.0, 1.0, () -> mode.is(Mode.Clip) && !bypass.get());
    private final NumberValue<Double> rotationDelay = new NumberValue<>("RotationDelay", "旋转延迟", 100.0, 0.0, 500.0, 1.0, () -> mode.is(Mode.Clip) && bypass.get());
    private final BoolValue obsidian = new BoolValue("Obsidian", "黑曜石", false, () -> mode.is(Mode.Clip) && bypass.get());
    private final BoolValue clipMode = new BoolValue("Move", "移动", true, () -> mode.is(Mode.Clip) && bypass.get());
    private final BoolValue clipIn = new BoolValue("Move In", "移入", true, () -> mode.is(Mode.Clip) && bypass.get() && clipMode.get());
    private final BoolValue invalid = new BoolValue("Invalid Packet", "无效数据包", true, () -> mode.is(Mode.Clip) && bypass.get());

    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);

    private Vec3d directionVec = null;
    private Vec3d targetPos;

    private final TimerUtil timer = new TimerUtil();
    private boolean cancel = true;

    private boolean faceVector(Vec3d directionVec) {
        this.directionVec = directionVec;
        return Managers.ROTATION.inFov(directionVec, fov.get());
    }

    private void updatePos() {
        targetPos = new Vec3d(mc.player.getX() + MathHelper.clamp(roundToClosest(mc.player.getX(), Math.floor(mc.player.getX()) + 0.241, Math.floor(mc.player.getX()) + 0.759) - mc.player.getX(), -0.2, 0.2), mc.player.getY() - 0.5, mc.player.getZ() + MathHelper.clamp(roundToClosest(mc.player.getZ(), Math.floor(mc.player.getZ()) + 0.241, Math.floor(mc.player.getZ()) + 0.759) - mc.player.getZ(), -0.2, 0.2));
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (mode.is(Mode.Pearl)) {
            updatePos();
            if (!faceVector(targetPos)) {
                return;
            }
            throwPearl();
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (directionVec != null) {
            Managers.ROTATION.lookAt(directionVec, rotationSpeed.get(), priority.get());
        }

        if (mode.is(Mode.Clip)) {
            if (bypass.get()) {
                if (!insideBlock()) {
                    if (clipMode.get()) {
                        setState(false);
                    }
                }
            } else {
                if (MoveUtil.isMoving()) return;

                if (mc.player.age % delay.get() == 0) {
                    mc.player.setPosition(mc.player.getX() + MathHelper.clamp(roundToClosest(mc.player.getX(), Math.floor(mc.player.getX()) + 0.241, Math.floor(mc.player.getX()) + 0.759) - mc.player.getX(), -0.03, 0.03), mc.player.getY(), mc.player.getZ() + MathHelper.clamp(roundToClosest(mc.player.getZ(), Math.floor(mc.player.getZ()) + 0.241, Math.floor(mc.player.getZ()) + 0.759) - mc.player.getZ(), -0.03, 0.03));
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true, mc.player.horizontalCollision));
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(roundToClosest(mc.player.getX(), Math.floor(mc.player.getX()) + 0.23, Math.floor(mc.player.getX()) + 0.77), mc.player.getY(), roundToClosest(mc.player.getZ(), Math.floor(mc.player.getZ()) + 0.23, Math.floor(mc.player.getZ()) + 0.77), true, mc.player.horizontalCollision));
                }
            }
        }

        if (mode.is(Mode.Pearl)) {
            updatePos();
            if (!faceVector(targetPos)) {
                return;
            }
            throwPearl();
        }
    }

    public void throwPearl() {
        AutoPearl.throwing = true;
        if (attack.get()) {
            BlockHitResult hitResult = (BlockHitResult) mc.player.raycast(3, mc.getRenderTickCounter().getTickDelta(true), false);
            for (Entity entity : mc.world.getOtherEntities(null, new Box(hitResult.getBlockPos()).expand(0.2))) {
                if (entity instanceof ItemFrameEntity itemFrameEntity && itemFrame.get()) {
                    if (!itemFrameEntity.getHeldItemStack().isEmpty()) {
                        mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));
                    }
                    mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));
                }
                if (entity instanceof PaintingEntity && painting.get()) {
                    mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));
                }
            }
            BlockState state = mc.world.getBlockState(mc.player.getBlockPos());
            if (state.getBlock() instanceof ScaffoldingBlock && scaffolding.get()) {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
            }


            mc.player.swingHand(Hand.MAIN_HAND);
        }

        FindItemResult pearl;

        if (!yawStep.get()) {
            Managers.ROTATION.setRotations(new Rotation(Managers.ROTATION.getRotation(targetPos)[0], 89f), rotationSpeed.get());
        }

        if (mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
            sendSequencedPacket(id -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, id, Managers.ROTATION.getYaw(), Managers.ROTATION.getPitch()));
        } else if (inventory.get() && (pearl = InvUtil.findInHotbar(Items.ENDER_PEARL)).found()) {
            InvUtil.swap(pearl.slot(), true);

            sendSequencedPacket(id -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, id, Managers.ROTATION.getYaw(), Managers.ROTATION.getPitch()));

            InvUtil.swapBack();
        } else if ((pearl = InvUtil.find(Items.ENDER_PEARL)).found()) {
            int old = mc.player.getInventory().selectedSlot;
            InvUtil.invSwap(pearl.slot());

            sendSequencedPacket(id -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, id, Managers.ROTATION.getYaw(), Managers.ROTATION.getPitch()));

            InvUtil.invSwapBack();
        }

        /*if (AntiCheat.INSTANCE.snapBack.getValue()) {
            Zenith.ROTATION.snapBack();
        }*/
        AutoPearl.throwing = false;

        setState(false);
    }

    @Override
    public void onEnable() {
        directionVec = null;
        if (nullCheck()) {
            setState(false);
            return;
        }

        if (mode.is(Mode.Clip) && bypass.get() && clipMode.get()) {
            cancel = false;
            if (clipIn.get()) {
                Direction f = mc.player.getHorizontalFacing();
                mc.player.setPosition(mc.player.getX() + f.getOffsetX() * 0.5, mc.player.getY(), mc.player.getZ() + f.getOffsetZ() * 0.5);
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true, mc.player.horizontalCollision));
            } else {
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true, mc.player.horizontalCollision));
                mc.player.setPosition(roundToClosest(mc.player.getX(), Math.floor(mc.player.getX()) + 0.23, Math.floor(mc.player.getX()) + 0.77), mc.player.getY(), roundToClosest(mc.player.getZ(), Math.floor(mc.player.getZ()) + 0.23, Math.floor(mc.player.getZ()) + 0.77));
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(roundToClosest(mc.player.getX(), Math.floor(mc.player.getX()) + 0.23, Math.floor(mc.player.getX()) + 0.77), mc.player.getY(), roundToClosest(mc.player.getZ(), Math.floor(mc.player.getZ()) + 0.23, Math.floor(mc.player.getZ()) + 0.77), true, mc.player.horizontalCollision));
            }
            cancel = true;
        }

        if (mode.is(Mode.Pearl)) {
            updatePos();
            if (yawStep.get()) {
                return;
            }
            throwPearl();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;

        if (mode.is(Mode.Clip)) {
            if (nullCheck() || !bypass.get()) return;
            if (cancel && event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                if (!insideBlock()) {
                    if (clipMode.get()) {
                        setState(false);
                    }
                    return;
                }
                if (packet.changesLook() && timer.passedMS(rotationDelay.get())) {
                    float packetYaw = packet.getYaw(0);
                    float packetPitch = packet.getPitch(0);

                    cancel = false;
                    if (invalid.get()) {
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 1337, mc.player.getZ(), packetYaw, packetPitch, false, mc.player.horizontalCollision));
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() - 1337, mc.player.getZ(), packetYaw, packetPitch, false, mc.player.horizontalCollision));
                    } else {
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 2, mc.player.getZ(), packetYaw, packetPitch, false, mc.player.horizontalCollision));
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() - 2, mc.player.getZ(), packetYaw, packetPitch, false, mc.player.horizontalCollision));
                    }
                    cancel = true;

                    timer.reset();
                }
                event.cancel();
            }
        }
    }

    public boolean insideBlock() {
        BlockPos playerBlockPos = BlockPos.ofFloored(mc.player.getPos());
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    BlockPos offsetPos = playerBlockPos.add(xOffset, yOffset, zOffset);
                    if (mc.world.getBlockState(offsetPos).getBlock() == Blocks.BEDROCK || (mc.world.getBlockState(offsetPos).getBlock() == Blocks.OBSIDIAN && obsidian.get())) {
                        if (mc.player.getBoundingBox().intersects(new Box(offsetPos))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private double roundToClosest(double num, double low, double high) {
        double d1 = num - low;
        double d2 = high - num;

        if (d2 > d1) {
            return low;

        } else {
            return high;
        }
    }
}
