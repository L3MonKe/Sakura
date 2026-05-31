package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.movement.Velocity;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.*;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KillAura extends Module {
    public enum AutoBlockMode {
        None, Fake, Hypixel
    }

    public enum AttackMode {
        v1_8, v1_9
    }

    public enum MovementFixMode {
        Silent, Strict, Smart
    }

    public KillAura() {
        super("KillAura", "杀戮光环", Category.Combat);
    }

    private final EnumValue<AttackMode> mode = new EnumValue<>("Mode", "模式", AttackMode.v1_8);
    private final NumberValue<Double> aimRange = new NumberValue<>("Aim Range", "瞄准范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> searchRange = new NumberValue<>("Search Range", "搜索范围", 10.0, 1.0, 20.0, 0.1);
    private final NumberValue<Double> minCps = new NumberValue<>("Min CPS", "最小攻击速度", 10.0, 1.0, 20.0, 1.0, () -> mode.is(AttackMode.v1_8));
    private final NumberValue<Double> maxCps = new NumberValue<>("Max CPS", "最大攻击速度", 10.0, 1.0, 20.0, 1.0, () -> mode.is(AttackMode.v1_8));
    private final NumberValue<Integer> rotateSpeed = new NumberValue<>("Rotation Speed", "转向速度", 10, 1, 10, 1);
    private final BoolValue rayTrace = new BoolValue("RayTrace", "射线检测", true);
    private final BoolValue throughWall = new BoolValue("ThroughWall", "穿墙攻击", false);
    private final NumberValue<Double> throughWallRange = new NumberValue<>("ThroughWall Range", "穿墙范围", 3.0, 0.0, 6.0, 0.1, throughWall::get);
    private final EnumValue<AutoBlockMode> abMode = new EnumValue<>("Auto Block", "自动格挡", AutoBlockMode.Fake);

    private final EnumValue<MovementFixMode> movementFixMode = new EnumValue<>("MovementFix", "移动修正", MovementFixMode.Smart);
    private final BoolValue debugRender = new BoolValue("Debug Render", "调试渲染", false);

    private List<LivingEntity> targets;
    private LivingEntity target;

    private long lastAttackTime = 0;
    private final ConcurrentLinkedQueue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    private boolean blinking;

    @Override
    protected void onDisable() {
        target = null;
        targets = null;
        if (mc.player != null && mc.player.isUsingItem() && mc.player.getMainHandStack().isIn(ItemTags.SWORDS)) {
            mc.interactionManager.stopUsingItem(mc.player);
        }
        blinking = false;
        flush();
    }

    public boolean isAutoBlock() {
        return !abMode.is(AutoBlockMode.None);
    }

    public LivingEntity getCurrentTarget() {
        return target;
    }

    public List<LivingEntity> getTargets() {
        return targets;
    }

    public boolean isNoWorking() {
        if (mc.player == null) return true;
        if (mc.player.isSpectator() || !mc.player.isAlive()) return true;
        return false;
    }

    public double getSearchRange() {
        return searchRange.get();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (event.getType() == EventType.RECEIVE) {
            if (event.getPacket() instanceof DisconnectS2CPacket) {
                blinking = false;
                packets.clear();
            }
            return;
        }

        if (event.getType() != EventType.SEND) return;

        Packet<?> packet = event.getPacket();

        if (blinking) {
            if (packet instanceof PlayerMoveC2SPacket) {
                int movePacketCount = 0;
                for (Packet<?> p : packets) {
                    if (p instanceof PlayerMoveC2SPacket) {
                        movePacketCount++;
                    }
                }
                if (movePacketCount >= 3) {
                    mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, Managers.ROTATION.getYaw(), Managers.ROTATION.getPitch()));
                    flush();
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                }
            }
            packets.add(packet);
            event.setCancelled(true);
        } else {
            flush();
        }
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean scaffoldEnable = Sakura.MODULES.getModule(Scaffold.class).isEnabled();
        if (scaffoldEnable) return;

        findTarget();

        blinking = false;
        if (target != null && abMode.is(AutoBlockMode.Hypixel) && mc.player.getMainHandStack().isIn(ItemTags.SWORDS)) {
            blinking = true;
        }

        if (target != null) {
            double maxRange = throughWall.get() ? Math.max(aimRange.get(), throughWallRange.get()) : aimRange.get();
            if (mc.player.squaredDistanceTo(target) <= maxRange * maxRange) {
                if (mc.player.isUsingItem() && !abMode.is(AutoBlockMode.Hypixel)) return;
                Rotation calculate = RotationUtil.calculate(target);
                MovementFix fix = switch (movementFixMode.get()) {
                    case Silent -> MovementFix.NORMAL;
                    case Strict -> MovementFix.TRADITIONAL;
                    case Smart -> Velocity.shouldStrict ? MovementFix.TRADITIONAL : MovementFix.NORMAL;
                };
                Managers.ROTATION.setRotations(calculate, rotateSpeed.get(), fix, Priority.Medium);
                if (throughWall.get()) {
                    if (RaytraceUtil.facingEnemy(mc.player, target, calculate, aimRange.get(), throughWallRange.get())) {
                        attackTarget();
                    }
                } else if (rayTrace.get()) {
                    if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target)) {
                        attackTarget();
                    }
                } else {
                    if (RaytraceUtil.facingEnemy(mc.player, target, calculate, aimRange.get(), 0)) {
                        attackTarget();
                    }
                }
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

    public void flush() {
        Packet<?> packet;
        while ((packet = packets.poll()) != null) {
            PacketUtil.sendPacketNoEvent(packet);
        }
    }
}
