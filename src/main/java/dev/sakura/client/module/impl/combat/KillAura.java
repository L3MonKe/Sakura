package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.*;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class KillAura extends Module {

    public enum AttackMode {
        v1_8, v1_9
    }

    public enum BlockMode {
        Interact, Fake
    }

    // KillAura Settings
    private final EnumValue<AttackMode> mode = new EnumValue<>("Mode", "模式", AttackMode.v1_8);
    private final NumberValue<Double> aimRange = new NumberValue<>("Aim Range", "瞄准范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> searchRange = new NumberValue<>("Search Range", "搜索范围", 10.0, 1.0, 20.0, 0.1);
    private final NumberValue<Double> minCps = new NumberValue<>("Min CPS", "最小攻击速度", 10.0, 1.0, 20.0, 1.0, () -> mode.is(AttackMode.v1_8));
    private final NumberValue<Double> maxCps = new NumberValue<>("Max CPS", "最大攻击速度", 10.0, 1.0, 20.0, 1.0, () -> mode.is(AttackMode.v1_8));
    private final NumberValue<Integer> rotateSpeed = new NumberValue<>("Rotation Speed", "转向速度", 10, 1, 10, 1);
    private final BoolValue rayTrace = new BoolValue("RayTrace", "射线检测", true);
    private final BoolValue autoBlock = new BoolValue("Auto Block", "自动格挡", true);
    private final BoolValue debugRender = new BoolValue("Debug Render", "调试渲染", false);

    // AutoBlock Settings
    public final EnumValue<BlockMode> blockMode = new EnumValue<>("Block Mode", "格挡模式", BlockMode.Interact, () -> autoBlock.get());

    private List<LivingEntity> targets;
    private LivingEntity target;
    private long lastAttackTime = 0;

    private final Random random = new Random();
    private int currentTickOff;
    private int currentTickOn;
    private int blockingTicks = 0;
    private boolean blockingStateEnforced = false;
    private boolean blockVisual = false;
    private int flushTicks = 0;

    private final Queue<Packet<?>> blockedPackets = new LinkedList<>();
    private boolean isFlushing = false;

    public KillAura() {
        super("KillAura", "杀戮光环", Category.Combat);
        resetTicks();
    }

    @Override
    protected void onDisable() {
        target = null;
        targets = null;
        blockingStateEnforced = false;
        if (mc.options != null) {
            mc.options.useKey.setPressed(false);
        }
        stopBlocking(false);
        flushPackets();
    }

    public boolean isAutoBlock() {
        return autoBlock.get();
    }

    public LivingEntity getCurrentTarget() {
        return target;
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.RECEIVE) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof UpdateSelectedSlotC2SPacket) {
            blockVisual = false;
            blockingStateEnforced = false;
            flushPackets();
        }
        if (isFlushing) {
        }
    }

    private void flushPackets() {
        if (blockedPackets.isEmpty()) return;
        
        isFlushing = true;
        while (!blockedPackets.isEmpty()) {
            Packet<?> packet = blockedPackets.poll();
            if (mc.getNetworkHandler() != null) {
                mc.getNetworkHandler().sendPacket(packet);
            }
        }
        isFlushing = false;
        flushTicks = 0;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (autoBlock.get()) {
            flushTicks++;
            if (blockingStateEnforced) {
                blockingTicks++;
                mc.options.useKey.setPressed(true);
            }
        }

        boolean scaffoldEnable = Sakura.MODULES.getModule(Scaffold.class).isEnabled();
        if (scaffoldEnable) return;

        findTarget();

        if (target != null) {
            if (mc.player.squaredDistanceTo(target) <= aimRange.get() * aimRange.get()) {
                Rotation calculate = RotationUtil.calculate(target);
                Managers.ROTATION.setRotations(calculate, rotateSpeed.get(), MovementFix.NORMAL, Priority.Medium);
                if (rayTrace.get()) {
                    if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target)) {
                        attackTarget();
                    }
                } else {
                    if (RaytraceUtil.facingEnemy(mc.player, target, calculate, aimRange.get(), 0)) {
                        attackTarget();
                    }
                }
            }
            
            if (autoBlock.get()) {
                startBlocking();
            }
        } else {
            if (autoBlock.get()) {
                stopBlocking(false);
            }
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (!debugRender.get()) return;
        if (targets == null || targets.isEmpty()) return;
        for (Entity entity : targets) {
            if (entity.equals(target)) {
                Render3DUtil.drawFilledBox(event.getMatrices(), entity.getBoundingBox(), new Color(200, 0, 0, 60).getRGB());
                Render3DUtil.drawOutlineBox(event.getMatrices(), entity.getBoundingBox(), new Color(200, 0, 0, 60).getRGB(), 2f);
            } else {
                Render3DUtil.drawFilledBox(event.getMatrices(), entity.getBoundingBox(), new Color(0, 200, 0, 60).getRGB());
                Render3DUtil.drawOutlineBox(event.getMatrices(), entity.getBoundingBox(), new Color(0, 200, 0, 60).getRGB(), 2f);
            }
        }
    }

    private void attackTarget() {
        if (mode.is(AttackMode.v1_9)) {
            if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        } else {
            long time = System.currentTimeMillis();
            double baseDelay = 1000.0 / MathUtil.getRandom(minCps.get(), maxCps.get());
            long delay = (long) (baseDelay + (Math.random() - 0.5) * baseDelay * 0.4);
            if (time - lastAttackTime >= delay) {
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
                lastAttackTime = time;
            }
        }
    }

    private void findTarget() {
        double range = Math.max(aimRange.get(), searchRange.get());
        this.target = null;
        this.targets = Managers.COMBAT.getEntities(range);
        this.target = Managers.COMBAT.getClosestEnemy(range);
    }

    private void resetTicks() {
        currentTickOff = randomInRange(0, 5);
        currentTickOn = randomInRange(0, 5);
    }

    private int randomInRange(int min, int max) {
        if (min >= max) return min;
        return random.nextInt(max - min + 1) + min;
    }

    private void startBlocking() {
        if (!autoBlock.get() || mc.player.isUsingItem()) {
            return;
        }
        
        if (!isHoldingBlockingItem()) {
            return;
        }

        switch (blockMode.get()) {
            case Fake:
                blockVisual = true;
                return;
            default:
                break;
        }

        if (blockMode.is(BlockMode.Interact)) {
            interactWithFront();
        }

        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);

        currentTickOn = randomInRange(0, 5);
        mc.player.swingHand(Hand.MAIN_HAND);

        blockVisual = true;
        blockingStateEnforced = true;
    }

    private boolean stopBlocking(boolean pauses) {
        if (blockingStateEnforced) {
            blockingStateEnforced = false;
        }
        
        if (!pauses) {
            blockVisual = false;
            if (mc.options != null) {
                if (mc.options.useKey.isPressed()) {
                }
                mc.options.useKey.setPressed(false);
            }
        }

        if (mc.player != null && !mc.player.isUsingItem()) {
            return false;
        }

        currentTickOff = randomInRange(0, 5);

        mc.interactionManager.stopUsingItem(mc.player);
        return true;
    }

    private void interactWithFront() {
        Rotation rotation = Managers.ROTATION.isActive() ? Managers.ROTATION.rotations : new Rotation(mc.player.getYaw(), mc.player.getPitch());

        EntityHitResult entityHitResult = RaytraceUtil.rayTraceEntity(aimRange.get(), rotation, entity -> entity == target);
        
        if (entityHitResult != null) {
             Vec3d entityPos = new Vec3d(entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ());
             Vec3d hitVec = entityHitResult.getPos().subtract(entityPos);
             mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.interactAt(entityHitResult.getEntity(), mc.player.isSneaking(), Hand.MAIN_HAND, hitVec));
             mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.interact(entityHitResult.getEntity(), mc.player.isSneaking(), Hand.MAIN_HAND));
             return;
        }

        HitResult hitResult = RaytraceUtil.rayCast(rotation, aimRange.get(), false, 1.0f);
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        }
    }

    private boolean isHoldingBlockingItem() {
        return mc.player.getMainHandStack().isIn(ItemTags.SWORDS) || mc.player.getMainHandStack().getItem() instanceof ShieldItem;
    }
}
