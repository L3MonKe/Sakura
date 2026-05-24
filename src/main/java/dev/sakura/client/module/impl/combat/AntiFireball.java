package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Rotation;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class AntiFireball extends Module {
    private static final double SCAN_RANGE = 8.0;
    private static final double ATTACK_RANGE = 3.0;

    private final List<FireballEntity> trackedFireballs = new CopyOnWriteArrayList<>();

    public AntiFireball() {
        super("AntiFireball", "防火球", Category.Combat);
    }

    @Override
    protected void onDisable() {
        trackedFireballs.clear();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck() || mc.getNetworkHandler() == null) return;
        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) return;

        for (var entity : mc.world.getEntities()) {
            if (!(entity instanceof FireballEntity fireball)) continue;

            Vec3d motion = new Vec3d(fireball.getX() - fireball.lastX, fireball.getY() - fireball.lastY, fireball.getZ() - fireball.lastZ);
            Vec3d toPlayer = new Vec3d(mc.player.getX() - fireball.getX(), mc.player.getY() - fireball.getY(), mc.player.getZ() - fireball.getZ());

            if (motion.dotProduct(toPlayer) > 0 && fireball.squaredDistanceTo(mc.player) <= SCAN_RANGE * SCAN_RANGE) {
                if (!trackedFireballs.contains(fireball)) trackedFireballs.add(fireball);
            }
        }

        trackedFireballs.removeIf(fireball -> {
            Vec3d motion = new Vec3d(fireball.getX() - fireball.lastX, fireball.getY() - fireball.lastY, fireball.getZ() - fireball.lastZ);
            Vec3d toPlayer = new Vec3d(mc.player.getX() - fireball.getX(), mc.player.getY() - fireball.getY(), mc.player.getZ() - fireball.getZ());
            return motion.dotProduct(toPlayer) <= 0 || fireball.squaredDistanceTo(mc.player) > SCAN_RANGE * SCAN_RANGE;
        });

        if (trackedFireballs.isEmpty()) return;

        FireballEntity closest = trackedFireballs.stream()
                .min(Comparator.comparingDouble(f -> f.squaredDistanceTo(mc.player)))
                .orElse(null);

        Rotation targetRot = calculateRotationToEntity(closest);
        Managers.ROTATION.setRotations(targetRot, 10.0, MovementFix.NORMAL);

        Rotation checkRotation = Managers.ROTATION.targetRotations != null ? Managers.ROTATION.targetRotations : targetRot;

        if (rayCastEntityHit(checkRotation, ATTACK_RANGE, false) != null && Objects.requireNonNull(rayCastEntityHit(checkRotation, ATTACK_RANGE, false)).getEntity() == closest) {
            Objects.requireNonNull(mc.interactionManager).syncSelectedSlot();
            mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(closest, mc.player.isSneaking()));
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    private Rotation calculateRotationToEntity(FireballEntity target) {
        Vec3d playerEye = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        Vec3d targetPos = new Vec3d(target.getX(), target.getY() + target.getHeight() / 2.0, target.getZ());

        Vec3d diff = targetPos.subtract(playerEye);
        double diffX = diff.x;
        double diffY = diff.y;
        double diffZ = diff.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        yaw = MathHelper.wrapDegrees(yaw);
        pitch = MathHelper.wrapDegrees(pitch);

        return new Rotation(yaw, pitch);
    }

    private EntityHitResult rayCastEntityHit(Rotation rotation, double reach, boolean throughWalls) {
        if (mc.player == null || mc.world == null) return null;
        Vec3d start = mc.player.getCameraPosVec(1);
        Vec3d look = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d end = start.add(look.multiply(reach));
        double maxSq = reach * reach;
        if (!throughWalls) {
            BlockHitResult blockHit = mc.world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
            if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {
                double blockDistSq = start.squaredDistanceTo(blockHit.getPos());
                if (blockDistSq < maxSq) maxSq = blockDistSq;
            }
        }
        return net.minecraft.entity.projectile.ProjectileUtil.raycast(mc.player, start, end, mc.player.getBoundingBox().stretch(look.multiply(reach)).expand(1.0, 1.0, 1.0), e -> e != mc.player && !e.isSpectator() && e.canHit(), maxSq);
    }
}
