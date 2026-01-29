package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.movement.Scaffold;
import dev.mahiro.client.module.impl.player.Blink;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

public class AttackCrystal extends Module {

    public AttackCrystal() {
        super("AttackCrystal", "水晶光环", Category.Combat);
    }

    private List<Entity> targets;
    private Entity target;

    private long lastAttackTime = 0;

    @Override
    protected void onDisable() {
        target = null;
        targets = null;
    }

    public Entity getCurrentTarget() {
        return target;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean blinkEnable = Mahiro.MODULES.getModule(Blink.class).isEnabled();
        if (blinkEnable) return;

        findTarget();

        if (target != null) {
            Rotation calculate = RotationUtil.calculate(target);
            Managers.ROTATION.setRotations(calculate, 100, MovementFix.NORMAL, RotationManager.Priority.Medium);
            if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target)) {
                attackTarget();
            }
        }
    }

    private void attackTarget() {
        long time = System.currentTimeMillis();
        double baseDelay = 1000.0 / 20.0;
        long delay = (long) (baseDelay + (Math.random() - 0.5) * baseDelay * 0.4);
        if (time - lastAttackTime >= delay) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAttackTime = time;
        }
    }

    private void findTarget() {
        double range = 3.5;
        double rangeSq = range * range;

        this.target = null;
        double minDstSq = Double.MAX_VALUE;

        Box searchBox = mc.player.getBoundingBox().expand(range);

        List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(
                EndCrystalEntity.class,
                searchBox,
                crystal -> crystal != null && crystal.isAlive()
        );

        targets = new ArrayList<>(crystals);

        for (Entity entity : targets) {
            double distSq = mc.player.squaredDistanceTo(entity);
            if (distSq < minDstSq && distSq <= rangeSq) {
                minDstSq = distSq;
                this.target = entity;
            }
        }

        this.setSuffix(targets.size() + "");
    }
}