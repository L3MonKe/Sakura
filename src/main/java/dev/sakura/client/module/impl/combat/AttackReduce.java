package dev.sakura.client.module.impl.combat;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

/**
 * AttackReduce - 减少击退效果
 * 通过在被击退时攻击目标来减少击退距离
 * 移植自 Minecraft 1.8.9 到 Minecraft 1.21.11
 */
public class AttackReduce extends Module {
    private final BoolValue doRotation = new BoolValue("Rotation", "自动转向", true);
    private final BoolValue onlyPlayer = new BoolValue("OnlyPlayer", "仅玩家", true);
    private final BoolValue cancelSprint = new BoolValue("CancelSprint", "取消疾跑", true);
    private final BoolValue limitRange = new BoolValue("LimitRange", "限制范围(3格)", false);

    private int hitCount = 0;
    private double motionX = 0;
    private double motionZ = 0;
    private LivingEntity target = null;

    public AttackReduce() {
        super("AttackReduce", "减少击退", Category.Combat);
    }

    @Override
    protected void onDisable() {
        hitCount = 0;
        motionX = 0;
        motionZ = 0;
        target = null;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!isEnabled() || hitCount <= 0) return;

        hitCount--;

        // 查找目标
        findTarget();

        if (target == null) return;

        // 计算反向旋转以减少击退
        if (motionX != 0 && motionZ != 0 && doRotation.get()) {
            Vec3d knockback = new Vec3d(motionX, 0.0, motionZ).normalize();
            Vec3d playerPos = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
            Vec3d lookAt = playerPos.add(-knockback.x, 0.0, -knockback.z);
            Rotation rotation = RotationUtil.calculate(lookAt);
            Managers.ROTATION.setRotations(rotation, 10);
        }

        // 执行攻击
        attackTarget();

        // 取消疾跑以减少击退
        if (cancelSprint.get() && mc.player.isSprinting()) {
            mc.player.setSprinting(false);
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (!isEnabled()) return;
        if (event.getType() != EventType.RECEIVE) return;

        if (!(event.getPacket() instanceof EntityVelocityUpdateS2CPacket velocity)) return;

        // 检查是否是玩家的速度更新
        if (velocity.getEntityId() != mc.player.getId()) return;

        Vec3d vel = velocity.getVelocity();

        // 检查是否有足够的击退需要减少
        if (vel.y <= 0.1 || Math.hypot(vel.x, vel.z) <= 0.2) return;

        motionX = vel.x;
        motionZ = vel.z;
        hitCount = computeReduceTicks(vel.x, vel.z);
    }

    /**
     * 计算需要攻击的tick数来减少击退
     * 基于击退速度计算最优攻击次数
     */
    private static int computeReduceTicks(double motionX, double motionZ) {
        double kb = Math.hypot(motionX, motionZ);
        // 原始公式：ticks = 6.43153527E-4 * kb + 2.9419087136
        double ticksExact = 6.43153527E-4 * kb + 2.9419087136;
        int ticks = (int) Math.round(ticksExact);
        if (ticks < 1) ticks = 1;
        if (ticks > 10) ticks = 10;
        return ticks;
    }

    /**
     * 查找攻击目标
     */
    private void findTarget() {
        target = null;

        double maxDistance = limitRange.get() ? 3.0 : 7.0;
        double minDistance = maxDistance;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (entity == mc.player) continue;

            if (onlyPlayer.get() && !(entity instanceof PlayerEntity)) continue;

            double distance = mc.player.distanceTo(entity);
            if (distance < minDistance) {
                // 检查是否是有效目标
                if (Managers.COMBAT.isEnemy(living, maxDistance)) {
                    minDistance = distance;
                    target = living;
                }
            }
        }
    }

    /**
     * 攻击目标实体
     */
    private void attackTarget() {
        if (target == null) return;

        // 发送攻击包
        PlayerInteractEntityC2SPacket packet = PlayerInteractEntityC2SPacket.attack(target, mc.player.isSneaking());
        mc.getNetworkHandler().sendPacket(packet);

        // 本地攻击效果
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        // 应用疾跑惩罚（减少玩家速度）
        if (mc.player.isSprinting()) {
            Vec3d velocity = mc.player.getVelocity();
            mc.player.setVelocity(velocity.x * 0.6, velocity.y, velocity.z * 0.6);
        }
    }
}
