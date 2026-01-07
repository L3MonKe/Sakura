package dev.sakura.client.module.impl.combat;


import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.player.Blink;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.vector.Rotation;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.List;


public class KillAuraBJD extends Module {
    private final NumberValue<Double> aimRange = new NumberValue<>("Aim Range", "a", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> cps = new NumberValue<>("CPS", "", 10.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotation Speed", "", 180.0, 1.0, 180.0, 1.0);

    public Entity target;
    private List<Entity> targets;
    private long lastAttackTime = 0;

    public KillAuraBJD() {
        super("KillAuraBJD", "KillAuraBJD", Category.Combat);
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        boolean scaffoldEnable = Sakura.MODULES.getModule(Scaffold.class).isEnabled();
        boolean blinkEnable = Sakura.MODULES.getModule(Blink.class).isEnabled();
        //AttackCrystal attackCrystal = Naven.getInstance().getModuleManager().getModule(AttackCrystal.class);
        if (mc.player == null || mc.world == null || scaffoldEnable || blinkEnable)
            return;

        findTarget();

        if (target != null) {
            Rotation calculate = RotationUtil.calculate(target);
            Managers.ROTATION.setRotations(calculate, rotateSpeed.get(), MovementFix.NORMAL, RotationManager.Priority.Medium);
            HitResult hitResult = mc.crosshairTarget;
            if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target))
                attackTarget();
        }
    }

    private void attackTarget() {
        long time = System.currentTimeMillis();
        double baseDelay = 1000.0 / cps.get();

        long delay = (long) (baseDelay + (Math.random() - 0.5) * baseDelay * 0.4);

        if (time - lastAttackTime >= delay) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);

            lastAttackTime = time;
        }
    }

    private void findTarget() {
        float range = aimRange.get().floatValue();
        double rangeSq = range * range;

        this.target = null;
        double minDstSq = Double.MAX_VALUE;

        Box searchBox = mc.player.getBoundingBox().contract(range);

        List<Entity> candidates = mc.world.getOtherEntities(
                mc.player,
                searchBox,
                e -> e instanceof LivingEntity && e != mc.player && e.isAlive() && !e.isSpectator() && !AntiBotBJD.isBot(e)
        );

        targets = candidates;

        for (Entity entity : candidates) {
            double distSq = mc.player.squaredDistanceTo(entity);

            if (distSq < minDstSq && distSq <= rangeSq) {
                minDstSq = distSq;
                this.target = entity;
            }
        }

        this.setSuffix(candidates.size() + " Targets");
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (targets == null || targets.isEmpty()) return;
        for (Entity entity : targets) {

            Color s;
            Color l;

            if (entity.equals(target)) {
                s = new Color(200, 0, 0, 60);
                l = new Color(200, 0, 0, 60);
            } else {
                s = new Color(0, 200, 0, 60);
                l = new Color(0, 200, 0, 60);
            }

            Render3DUtil.drawFullBox(event.getMatrices(), entity.getBoundingBox(), s, l, 2F);
        }
    }
}
