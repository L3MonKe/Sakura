package dev.sakura.module.impl.combat;

import dev.sakura.Sakura;
import dev.sakura.events.client.TickEvent;
import dev.sakura.manager.impl.RotationManager;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.utils.client.ChatUtils;
import dev.sakura.utils.combat.CombatUtil;
import dev.sakura.utils.entity.EntityUtil;
import dev.sakura.utils.entity.InventoryUtil;
import dev.sakura.utils.rotation.MovementFix;
import dev.sakura.utils.time.TimerUtil;
import dev.sakura.utils.vector.Vector2f;
import dev.sakura.utils.world.BlockPosX;
import dev.sakura.utils.world.BlockUtil;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.EnumValue;
import dev.sakura.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class Burrow extends Module {
    public static Burrow INSTANCE;
    private final TimerUtil timer = new TimerUtil();
    private final TimerUtil webTimer = new TimerUtil();
    private final BoolValue disable = new BoolValue("Disable", true);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", 500, 0, 1000, 1, () -> !disable.get());
    private final NumberValue<Integer> webTime = new NumberValue<>("WebTime", 0, 0, 500, 1);
    private final BoolValue enderChest = new BoolValue("EnderChest", true);
    private final BoolValue antiLag = new BoolValue("AntiLag", false);
    private final BoolValue detectMine = new BoolValue("DetectMining", false);
    private final BoolValue headFill = new BoolValue("HeadFill", false);
    private final BoolValue usingPause = new BoolValue("UsingPause", false);
    private final BoolValue down = new BoolValue("Down", true);
    private final BoolValue noSelfPos = new BoolValue("NoSelfPos", false);
    private final BoolValue packetPlace = new BoolValue("PacketPlace", true);
    private final BoolValue sound = new BoolValue("Sound", true);
    private final NumberValue<Integer> blocksPer = new NumberValue<>("BlocksPer", 4, 1, 4, 1);
    private final EnumValue<RotateMode> rotate = new EnumValue<>("RotateMode", RotateMode.Bypass);
    private final BoolValue breakCrystal = new BoolValue("Break", true);
    private final BoolValue wait = new BoolValue("Wait", true, () -> !disable.get());
    private final BoolValue fakeMove = new BoolValue("FakeMove", true);
    private final BoolValue center = new BoolValue("AllowCenter", false, () -> fakeMove.get());
    private final BoolValue inventory = new BoolValue("InventorySwap", true);
    private final EnumValue<LagBackMode> lagMode = new EnumValue<>("LagMode", LagBackMode.TrollHack);
    private final EnumValue<LagBackMode> aboveLagMode = new EnumValue<>("MoveLagMode", LagBackMode.Smart);
    private final NumberValue<Double> smartX = new NumberValue<>("SmartXZ", 3.0, 0.0, 10.0, 0.1, () -> lagMode.get() == LagBackMode.Smart || aboveLagMode.get() == LagBackMode.Smart);
    private final NumberValue<Double> smartUp = new NumberValue<>("SmartUp", 3.0, 0.0, 10.0, 0.1, () -> lagMode.get() == LagBackMode.Smart || aboveLagMode.get() == LagBackMode.Smart);
    private final NumberValue<Double> smartDown = new NumberValue<>("SmartDown", 3.0, 0.0, 10.0, 0.1, () -> lagMode.get() == LagBackMode.Smart || aboveLagMode.get() == LagBackMode.Smart);
    private final NumberValue<Double> smartDistance = new NumberValue<>("SmartDistance", 2.0, 0.0, 10.0, 0.1, () -> lagMode.get() == LagBackMode.Smart || aboveLagMode.get() == LagBackMode.Smart);
    private final BoolValue obsMode = new BoolValue("ObsMode", false);
    private int progress = 0;
    private final List<BlockPos> placePos = new ArrayList<>();

    public Burrow() {
        super("Burrow", Category.Combat);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    @Override
    protected void onDisable() {
        Sakura.EVENT_BUS.unsubscribe(this);
    }

    private void sendPositionPacket(double x, double y, double z, boolean onGround) {
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround, mc.player.horizontalCollision));
    }

    private void sendFullPacket(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(x, y, z, yaw, pitch, onGround, mc.player.horizontalCollision));
    }

    private void sendLookPacket(float yaw, float pitch, boolean onGround) {
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, onGround, mc.player.horizontalCollision));
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        if (EntityUtil.isInWeb(mc.player)) {
            webTimer.reset();
            return;
        }
        if (usingPause.get() && mc.player.isUsingItem()) {
            return;
        }
        if (!webTimer.hasReached(webTime.get())) {
            return;
        }
        if (!disable.get() && !timer.hasReached(delay.get())) {
            return;
        }
        if (!mc.player.isOnGround()) {
            return;
        }
        if (antiLag.get()) {
            if (!mc.world.getBlockState(EntityUtil.getPlayerPos(true).down()).blocksMovement()) return;
        }
        int oldSlot = mc.player.getInventory().selectedSlot;
        int block;
        if ((block = getBlock()) == -1) {
            ChatUtils.addChatMessage("§c§oObsidian" + (enderChest.get() ? "/EnderChest" : "") + "?");
            setState(false);
            return;
        }
        progress = 0;
        placePos.clear();
        double offset = 0.3;
        BlockPos pos1 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() + 0.5, mc.player.getZ() + offset);
        BlockPos pos2 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() + 0.5, mc.player.getZ() + offset);
        BlockPos pos3 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() + 0.5, mc.player.getZ() - offset);
        BlockPos pos4 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() + 0.5, mc.player.getZ() - offset);
        BlockPos pos5 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() + 1.5, mc.player.getZ() + offset);
        BlockPos pos6 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() + 1.5, mc.player.getZ() + offset);
        BlockPos pos7 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() + 1.5, mc.player.getZ() - offset);
        BlockPos pos8 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() + 1.5, mc.player.getZ() - offset);
        BlockPos pos9 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() - 1, mc.player.getZ() + offset);
        BlockPos pos10 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() - 1, mc.player.getZ() + offset);
        BlockPos pos11 = new BlockPosX(mc.player.getX() + offset, mc.player.getY() - 1, mc.player.getZ() - offset);
        BlockPos pos12 = new BlockPosX(mc.player.getX() - offset, mc.player.getY() - 1, mc.player.getZ() - offset);
        BlockPos playerPos = EntityUtil.getPlayerPos(true);
        boolean headFillFlag = false;
        if (!canPlace(pos1) && !canPlace(pos2) && !canPlace(pos3) && !canPlace(pos4)) {
            boolean cantHeadFill = !this.headFill.get() || !canPlace(pos5) && !canPlace(pos6) && !canPlace(pos7) && !canPlace(pos8);
            boolean cantDown = !down.get() || !canPlace(pos9) && !canPlace(pos10) && !canPlace(pos11) && !canPlace(pos12);
            if (cantHeadFill) {
                if (cantDown) {
                    if (!wait.get() && disable.get()) {
                        setState(false);
                    }
                    return;
                }
            } else {
                headFillFlag = true;
            }
        }
        boolean above = false;
        BlockPos headPos = EntityUtil.getPlayerPos(true).up(2);
        boolean rotateFlag = this.rotate.get() == RotateMode.Normal;
        CombatUtil.attackCrystal(pos1, rotateFlag, false);
        CombatUtil.attackCrystal(pos2, rotateFlag, false);
        CombatUtil.attackCrystal(pos3, rotateFlag, false);
        CombatUtil.attackCrystal(pos4, rotateFlag, false);
        if (headFillFlag || mc.player.isCrawling() || trapped(headPos) || trapped(headPos.add(1, 0, 0)) || trapped(headPos.add(-1, 0, 0)) || trapped(headPos.add(0, 0, 1)) || trapped(headPos.add(0, 0, -1)) || trapped(headPos.add(1, 0, -1)) || trapped(headPos.add(-1, 0, -1)) || trapped(headPos.add(1, 0, 1)) || trapped(headPos.add(-1, 0, 1))) {
            above = true;
            if (!fakeMove.get()) {
                if (!wait.get() && disable.get()) setState(false);
                return;
            }
            boolean moved = false;
            BlockPos offPos = playerPos;
            if (checkSelf(offPos) && !BlockUtil.canReplace(offPos) && (!this.headFill.get() || !BlockUtil.canReplace(offPos.up()))) {
                gotoPos(offPos);
            } else {
                for (final Direction facing : Direction.values()) {
                    if (facing == Direction.UP || facing == Direction.DOWN) continue;
                    offPos = playerPos.offset(facing);
                    if (checkSelf(offPos) && !BlockUtil.canReplace(offPos) && (!this.headFill.get() || !BlockUtil.canReplace(offPos.up()))) {
                        gotoPos(offPos);
                        moved = true;
                        break;
                    }
                }
                if (!moved) {
                    for (final Direction facing : Direction.values()) {
                        if (facing == Direction.UP || facing == Direction.DOWN) continue;
                        offPos = playerPos.offset(facing);
                        if (checkSelf(offPos)) {
                            gotoPos(offPos);
                            moved = true;
                            break;
                        }
                    }
                    if (!moved) {
                        if (!center.get()) {
                            return;
                        }
                        for (final Direction facing : Direction.values()) {
                            if (facing == Direction.UP || facing == Direction.DOWN) continue;
                            offPos = playerPos.offset(facing);
                            if (canMove(offPos)) {
                                gotoPos(offPos);
                                moved = true;
                                break;
                            }
                        }
                        if (!moved) {
                            if (!wait.get() && disable.get()) setState(false);
                            return;
                        }
                    }
                }
            }
        } else {
            sendPositionPacket(mc.player.getX(), mc.player.getY() + 0.4199999868869781, mc.player.getZ(), false);
            sendPositionPacket(mc.player.getX(), mc.player.getY() + 0.7531999805212017, mc.player.getZ(), false);
            sendPositionPacket(mc.player.getX(), mc.player.getY() + 0.9999957640154541, mc.player.getZ(), false);
            sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.1661092609382138, mc.player.getZ(), false);
        }
        timer.reset();
        doSwap(block);
        if (this.rotate.get() == RotateMode.Bypass) {
            RotationManager.setRotations(new Vector2f(RotationManager.getYaw(), 89.98f), 10f, MovementFix.NORMAL);
        }
        placeBlock(playerPos, rotateFlag);
        placeBlock(pos1, rotateFlag);
        placeBlock(pos2, rotateFlag);
        placeBlock(pos3, rotateFlag);
        placeBlock(pos4, rotateFlag);
        if (down.get()) {
            placeBlock(pos9, rotateFlag);
            placeBlock(pos10, rotateFlag);
            placeBlock(pos11, rotateFlag);
            placeBlock(pos12, rotateFlag);
        }
        if (this.headFill.get() && above) {
            placeBlock(pos5, rotateFlag);
            placeBlock(pos6, rotateFlag);
            placeBlock(pos7, rotateFlag);
            placeBlock(pos8, rotateFlag);
        }
        if (inventory.get()) {
            doSwap(block);
            EntityUtil.syncInventory();
        } else {
            doSwap(oldSlot);
        }
        switch (above ? aboveLagMode.get() : lagMode.get()) {
            case Smart -> {
                ArrayList<BlockPos> list = new ArrayList<>();
                for (double x = mc.player.getPos().getX() - smartX.get(); x < mc.player.getPos().getX() + smartX.get(); ++x) {
                    for (double z = mc.player.getPos().getZ() - smartX.get(); z < mc.player.getPos().getZ() + smartX.get(); ++z) {
                        for (double y = mc.player.getPos().getY() - smartDown.get(); y < mc.player.getPos().getY() + smartUp.get(); ++y) {
                            list.add(new BlockPosX(x, y, z));
                        }
                    }
                }

                double distance = 0;
                BlockPos bestPos = null;
                for (BlockPos pos : list) {
                    if (!canMove(pos)) continue;
                    if (MathHelper.sqrt((float) mc.player.squaredDistanceTo(pos.toCenterPos().add(0, -0.5, 0))) < smartDistance.get())
                        continue;
                    if (bestPos == null || mc.player.squaredDistanceTo(pos.toCenterPos()) < distance) {
                        bestPos = pos;
                        distance = mc.player.squaredDistanceTo(pos.toCenterPos());
                    }
                }
                if (bestPos != null) {
                    sendPositionPacket(bestPos.getX() + 0.5, bestPos.getY(), bestPos.getZ() + 0.5, false);
                }
            }
            case Invalid -> {
                for (int i = 0; i < 20; i++)
                    sendPositionPacket(mc.player.getX(), mc.player.getY() + 1337, mc.player.getZ(), false);
            }
            case Fly -> {
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.16610926093821, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.170005801788139, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.2426308013947485, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 2.3400880035762786, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 2.6400880035762786, mc.player.getZ(), false);
            }
            case Glide -> {
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.0001, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.0405, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.0802, mc.player.getZ(), false);
                sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.1027, mc.player.getZ(), false);
            }
            case TrollHack ->
                    sendPositionPacket(mc.player.getX(), mc.player.getY() + 2.3400880035762786, mc.player.getZ(), false);
            case Normal -> sendPositionPacket(mc.player.getX(), mc.player.getY() + 1.9, mc.player.getZ(), false);
            case ToVoid -> sendPositionPacket(mc.player.getX(), -70, mc.player.getZ(), false);
            case ToVoid2 -> sendPositionPacket(mc.player.getX(), -7, mc.player.getZ(), false);
            case Rotation -> {
                sendLookPacket(-180, -90, false);
                sendLookPacket(180, 90, false);
            }
        }
        if (disable.get()) setState(false);
    }

    private void placeBlock(BlockPos pos, boolean rotate) {
        if (canPlace(pos) && !placePos.contains(pos) && progress < blocksPer.get()) {
            placePos.add(pos);
            if (BlockUtil.airPlace()) {
                progress++;
                BlockUtil.placedPos.add(pos);
                if (sound.get())
                    mc.world.playSound(mc.player, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
                BlockUtil.clickBlock(pos, Direction.DOWN, rotate, packetPlace.get());
            }
            Direction side;
            if ((side = BlockUtil.getPlaceSide(pos)) == null) return;
            progress++;
            BlockUtil.placedPos.add(pos);
            if (sound.get())
                mc.world.playSound(mc.player, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
            BlockUtil.clickBlock(pos.offset(side), side.getOpposite(), rotate, packetPlace.get());
        }
    }

    private void doSwap(int slot) {
        if (inventory.get()) {
            InventoryUtil.inventorySwap(slot, mc.player.getInventory().selectedSlot);
        } else {
            InventoryUtil.switchToSlot(slot);
        }
    }

    private void gotoPos(BlockPos offPos) {
        if (rotate.get() == RotateMode.None) {
            sendPositionPacket(offPos.getX() + 0.5, mc.player.getY() + 0.1, offPos.getZ() + 0.5, false);
        } else {
            sendFullPacket(offPos.getX() + 0.5, mc.player.getY() + 0.1, offPos.getZ() + 0.5, RotationManager.getYaw(), 89.98f, false);
        }
    }

    private boolean canMove(BlockPos pos) {
        return mc.world.isAir(pos) && mc.world.isAir(pos.up());
    }

    private boolean canPlace(BlockPos pos) {
        if (noSelfPos.get() && pos.equals(EntityUtil.getPlayerPos(true))) {
            return false;
        }
        if (!BlockUtil.airPlace() && BlockUtil.getPlaceSide(pos) == null) {
            return false;
        }
        if (!BlockUtil.canReplace(pos)) {
            return false;
        }
        return !hasEntity(pos);
    }

    private boolean hasEntity(BlockPos pos) {
        for (Entity entity : BlockUtil.getEntities(new Box(pos))) {
            if (entity == mc.player) continue;
            if (!entity.isAlive() || entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity || entity instanceof ExperienceBottleEntity || entity instanceof ArrowEntity || entity instanceof EndCrystalEntity && breakCrystal.get() || entity instanceof ArmorStandEntity && obsMode.get())
                continue;
            return true;
        }
        return false;
    }

    private boolean checkSelf(BlockPos pos) {
        return mc.player.getBoundingBox().intersects(new Box(pos));
    }

    private boolean trapped(BlockPos pos) {
        return (mc.world.canCollide(mc.player, new Box(pos)) || BlockUtil.getBlock(pos) == Blocks.COBWEB) && checkSelf(pos.down(2));
    }

    private int getBlock() {
        if (inventory.get()) {
            if (InventoryUtil.findBlockInventorySlot(Blocks.OBSIDIAN) != -1 || !enderChest.get()) {
                return InventoryUtil.findBlockInventorySlot(Blocks.OBSIDIAN);
            }
            return InventoryUtil.findBlockInventorySlot(Blocks.ENDER_CHEST);
        } else {
            if (InventoryUtil.findBlock(Blocks.OBSIDIAN) != -1 || !enderChest.get()) {
                return InventoryUtil.findBlock(Blocks.OBSIDIAN);
            }
            return InventoryUtil.findBlock(Blocks.ENDER_CHEST);
        }
    }

    private enum RotateMode {
        Bypass, Normal, None
    }

    private enum LagBackMode {
        Smart, Invalid, TrollHack, ToVoid, ToVoid2, Normal, Rotation, Fly, Glide
    }
}