package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.player.inventory.Stealer;
import dev.sakura.client.utils.math.VecCalculation;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.MultiBoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;

import java.util.*;

public class ChestAura extends Module {
    private final MultiBoolValue container = new MultiBoolValue("Container", "容器", List.of(
            new BoolValue("Chest", "箱子", true),
            new BoolValue("Furnace", "熔炉", false),
            new BoolValue("BlastFurnace", "高炉", false),
            new BoolValue("SmokerFurnace", "烟熏炉", false),
            new BoolValue("BrewingStand", "酿造台", false)
    ));
    private final BoolValue swing = new BoolValue("Swing", "挥手", true);
    private final BoolValue workOnStealerEnabled = new BoolValue("WorkOnStealerEnabled", "依赖Stealer", true);
    private final BoolValue ignoreOtherChestOpen = new BoolValue("IgnoreEnemyOpenChest", "忽略敌人开箱", false);
    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 4.5, 0.0, 7.0, 0.1);
    private final NumberValue<Double> throughRange = new NumberValue<>("ThroughWallRange", "穿墙范围", 4.5, 0.0, 7.0, 0.1);
    private final NumberValue<Double> cancelRange = new NumberValue<>("CancelRange", "取消范围", 0.0, 0.0, 20.0, 0.1);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 400, 0, 1000, 1);
    private final NumberValue<Double> turnSpeed = new NumberValue<>("TurnSpeed", "转向速度", 180.0, 0.0, 180.0, 0.1);
    private final BoolValue disableInLobby = new BoolValue("DisableInLobby", "大厅禁用", false);

    private boolean opened = false;
    private final TimerUtil timer = new TimerUtil();
    private BlockEntity targetBlock = null;

    private static final Set<BlockEntity> clickedContainers = new HashSet<>();
    private static final Set<BlockEntity> playerClickedContainers = new HashSet<>();

    public ChestAura() {
        super("ChestAura", "箱子光环", Category.Player);
    }

    @Override
    protected void onEnable() {
        targetBlock = null;
        opened = false;
        timer.reset();
    }

    @Override
    protected void onDisable() {
        targetBlock = null;
        opened = false;
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.PRE) return;
        if (mc.interactionManager == null || mc.getNetworkHandler() == null) return;
        if (isEnabled(dev.sakura.client.module.impl.player.Blink.class)) return;
        if (Sakura.MODULES.getModule(KillAura.class).getCurrentTarget() != null) return;
        if (isEnabled(Scaffold.class)) return;
        if (workOnStealerEnabled.get() && !isEnabled(Stealer.class)) return;
        if (!timer.passedMillise(delay.get())) return;
        if (opened || mc.currentScreen instanceof InventoryScreen) return;
        if (disableInLobby.get() && isInLobby()) return;

        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (entity == mc.player) continue;
            if (VecCalculation.getDistanceToEntityBox(mc.player, entity) <= cancelRange.get()) {
                if (mc.currentScreen instanceof GenericContainerScreen || mc.currentScreen instanceof FurnaceScreen || mc.currentScreen instanceof BrewingStandScreen || mc.currentScreen instanceof SmokerScreen || mc.currentScreen instanceof BlastFurnaceScreen || mc.currentScreen instanceof InventoryScreen)
                    mc.player.closeHandledScreen();
                return;
            }
        }

        if (mc.currentScreen instanceof GenericContainerScreen || mc.currentScreen instanceof FurnaceScreen || mc.currentScreen instanceof BrewingStandScreen || mc.currentScreen instanceof SmokerScreen || mc.currentScreen instanceof BlastFurnaceScreen || mc.currentScreen instanceof InventoryScreen)
            return;

        mc.gameRenderer.updateCrosshairTarget(1f);

        List<BlockEntity> nearbyContainers = getNearbyContainers(mc.world, mc.player);
        if (nearbyContainers.isEmpty())
            return;

        BlockEntity target = nearbyContainers.get(0);
        BlockPos targetPos = target.getPos();

        Vec3d eyes = mc.player.getEyePos();
        Vec3d targetCenter = Vec3d.ofCenter(targetPos);

        double distance = eyes.distanceTo(targetCenter);
        boolean throughWalls = distance <= throughRange.get();

        double dx = targetCenter.x - eyes.x;
        double dy = targetCenter.y - eyes.y;
        double dz = targetCenter.z - eyes.z;

        double distXZ = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(dz, dx)) - 90.0));
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, distXZ)));

        BlockHitResult predictHit = rayCastContainer(new Rotation(yaw, pitch), mc.player, mc.world, range.get(), throughWalls);

        if (predictHit == null || !predictHit.getBlockPos().equals(targetPos)) return;

        Managers.ROTATION.setRotations(new Rotation(yaw, pitch), turnSpeed.get() / 18.0, MovementFix.NORMAL);

        Rotation serverRotation = Managers.ROTATION.rotations != null ? Managers.ROTATION.rotations : new Rotation(mc.player.getYaw(), mc.player.getPitch());
        BlockHitResult hit = rayCastContainer(serverRotation, mc.player, mc.world, range.get(), throughWalls);
        if (hit == null) return;

        if (hit.getBlockPos().equals(targetPos)) {
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
            if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            opened = true;
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (event.getType() == EventType.SEND) {
            if (event.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
                BlockPos pos = packet.getBlockHitResult().getBlockPos();
                BlockEntity entity = mc.world.getBlockEntity(pos);
                if (entity != null) {
                    clickedContainers.add(entity);
                    playerClickedContainers.add(entity);
                }
            }
        }

        if (event.getType() == EventType.RECEIVE) {
            if (event.getPacket() instanceof PlaySoundS2CPacket packet) {
                BlockPos pos = BlockPos.ofFloored(packet.getX(), packet.getY(), packet.getZ());
                BlockEntity entity = mc.world.getBlockEntity(pos);
                if (entity != null) {
                    clickedContainers.add(entity);
                }
            }

            if (event.getPacket() instanceof BlockEventS2CPacket packet) {
                BlockEntity entity = mc.world.getBlockEntity(packet.getPos());
                if (entity != null) {
                    clickedContainers.add(entity);
                }
            }

            if (event.getPacket() instanceof EntitySpawnS2CPacket packet) {
                BlockPos pos = BlockPos.ofFloored(packet.getX(), packet.getY(), packet.getZ());
                BlockEntity entity = mc.world.getBlockEntity(pos);
                if (entity != null) {
                    clickedContainers.add(entity);
                }
            }
        }

        if (opened && (event.getPacket() instanceof CloseScreenS2CPacket || event.getPacket() instanceof CloseHandledScreenC2SPacket)) {
            timer.reset();
            opened = false;
        }
    }

    private boolean isInLobby() {
        if (mc.world == null) return true;
        for (net.minecraft.entity.Entity entity : mc.world.getEntities()) {
            if (entity != null && entity.getName().getString().contains("\u00a7e\u00a7lCLICK TO PLAY")) {
                return true;
            }
        }
        return mc.player.getInventory().getStack(8) != null && mc.player.getInventory().getStack(8).getItem() == Items.NETHER_STAR && mc.player.getInventory().getStack(0) != null && mc.player.getInventory().getStack(0).getItem() == Items.COMPASS;
    }

    private List<BlockEntity> getNearbyContainers(ClientWorld world, ClientPlayerEntity player) {
        List<BlockEntity> list = new ArrayList<>();
        BlockPos playerPos = player.getBlockPos();
        int radius = (int) Math.ceil(range.get());

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = playerPos.add(dx, dy, dz);
                    BlockEntity be = world.getBlockEntity(pos);
                    if (be == null) continue;

                    if ((be instanceof ChestBlockEntity && container.isEnabled("Chest")
                            || be instanceof FurnaceBlockEntity && container.isEnabled("Furnace")
                            || be instanceof BlastFurnaceBlockEntity && container.isEnabled("BlastFurnace")
                            || be instanceof SmokerBlockEntity && container.isEnabled("SmokerFurnace")
                            || be instanceof BrewingStandBlockEntity && container.isEnabled("BrewingStand"))
                            && (!clickedContainers.contains(Objects.requireNonNull(mc.world).getBlockEntity(pos)) || ignoreOtherChestOpen.get() && !playerClickedContainers.contains(Objects.requireNonNull(mc.world).getBlockEntity(pos)))) {

                        double dist = player.getEyePos().distanceTo(Vec3d.ofCenter(pos));
                        if (dist <= range.get()) {
                            list.add(be);
                        }
                    }
                }
            }
        }

        list.sort(Comparator.comparingDouble(be ->
                player.getEyePos().distanceTo(Vec3d.ofCenter(be.getPos()))
        ));

        return list;
    }

    private BlockHitResult rayCastContainer(Rotation rotation, net.minecraft.entity.Entity entity, net.minecraft.world.World world, double reach, boolean throughWalls) {
        Vec3d start = entity.getCameraPosVec(1.0F);

        Vec3d direction = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d end = start.add(direction.multiply(reach));

        if (!throughWalls) {
            BlockHitResult hit = world.raycast(new RaycastContext(
                    start, end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    entity
            ));

            if (hit != null && hit.getType() != HitResult.Type.MISS) {
                net.minecraft.block.BlockState state = world.getBlockState(hit.getBlockPos());
                if (state.getBlock() instanceof ChestBlock && container.isEnabled("Chest")
                        || state.getBlock() instanceof FurnaceBlock && container.isEnabled("Furnace")
                        || state.getBlock() instanceof BlastFurnaceBlock && container.isEnabled("BlastFurnace")
                        || state.getBlock() instanceof SmokerBlock && container.isEnabled("SmokerFurnace")
                        || state.getBlock() instanceof BrewingStandBlock && container.isEnabled("BrewingStand")) {
                    return hit;
                }
            }
            return null;
        }

        double step = 0.1;
        double closestDistanceSq = Double.MAX_VALUE;
        BlockHitResult closestHit = null;

        Vec3d current = start;
        while (start.distanceTo(current) <= reach) {
            BlockPos pos = BlockPos.ofFloored(current);
            net.minecraft.block.BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof ChestBlock && container.isEnabled("Chest")
                    || state.getBlock() instanceof FurnaceBlock && container.isEnabled("Furnace")
                    || state.getBlock() instanceof BlastFurnaceBlock && container.isEnabled("BlastFurnace")
                    || state.getBlock() instanceof SmokerBlock && container.isEnabled("SmokerFurnace")
                    || state.getBlock() instanceof BrewingStandBlock && container.isEnabled("BrewingStand")) {
                VoxelShape shape = state.getOutlineShape(world, pos);
                if (!shape.isEmpty()) {
                    BlockHitResult hitResult = shape.raycast(start, end, pos);
                    if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                        Vec3d hitVec = hitResult.getPos();
                        double distanceSq = start.squaredDistanceTo(hitVec);
                        if (distanceSq < closestDistanceSq) {
                            closestDistanceSq = distanceSq;
                            closestHit = new BlockHitResult(
                                    hitVec,
                                    hitResult.getSide(),
                                    pos,
                                    false
                            );
                        }
                    }
                }
            }

            current = current.add(direction.multiply(step));
        }

        return closestHit;
    }
}
