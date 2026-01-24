package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RaytraceUtil;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;

import java.util.List;

public class AttackCrystal extends Module {
    private Entity targetCrystal;
    private int attackCooldown;

    public AttackCrystal() {
        super("AttackCrystal", "水晶光环", Category.Combat);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }

        double range = 4.0;
        double rangeSq = range * range;

        findNearestCrystal(range, rangeSq);

        if (targetCrystal != null) {
            handleCrystalAttack(range);
        }
    }

    private void findNearestCrystal(double range, double rangeSq) {
        targetCrystal = null;
        double closestDistSq = Double.MAX_VALUE;

        Box searchBox = mc.player.getBoundingBox().expand(range);
        List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(
                EndCrystalEntity.class,
                searchBox,
                crystal -> crystal != null && crystal.isAlive() && mc.player.squaredDistanceTo(crystal) <= rangeSq
        );

        for (EndCrystalEntity crystal : crystals) {
            if (crystal == null) continue;

            double distSq = mc.player.squaredDistanceTo(crystal);
            if (distSq < closestDistSq) {
                closestDistSq = distSq;
                targetCrystal = crystal;
            }
        }
    }

    private void handleCrystalAttack(double range) {
        if (targetCrystal == null) return;

        Rotation targetRot = RotationUtil.calculate(targetCrystal);

        boolean canSee = canSeeCrystal(targetRot, range);
        if (!canSee) return;

        Managers.ROTATION.setRotations(targetRot, 10.0, MovementFix.OFF, RotationManager.Priority.Highest);

        attackCrystal();
    }

    private boolean canSeeCrystal(Rotation rotation, double range) {
        if (targetCrystal == null) return false;

        HitResult hitResult = RaytraceUtil.rayTraceEntity(range, rotation, entity -> entity == targetCrystal);

        return hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() == targetCrystal;
    }

    private void attackCrystal() {
        if (targetCrystal == null || !targetCrystal.isAlive() || mc.player == null) {
            targetCrystal = null;
            return;
        }

        mc.player.attack(targetCrystal);
        //mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(, mc.player.isSneaking()));
        mc.player.swingHand(Hand.MAIN_HAND);

        targetCrystal = null;
        attackCooldown = 1;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (nullCheck()) return;

        if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof EndCrystalEntity) {
            if (attackCooldown == 0) {
                targetCrystal = entityHitResult.getEntity();
                attackCrystal();
            }
        }
    }

    @Override
    public void onEnable() {
        targetCrystal = null;
        attackCooldown = 0;
    }

    @Override
    public void onDisable() {
        targetCrystal = null;
        attackCooldown = 0;
    }
}