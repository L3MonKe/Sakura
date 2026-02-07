package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

import java.awt.*;
import java.util.List;

public class KillAura extends Module {
    public enum AutoBlockMode {
        Fake
    }

    public KillAura() {
        super("KillAura", "杀戮光环", Category.Combat);
    }

    private final NumberValue<Double> aimRange = new NumberValue<>("Aim Range", "瞄准范围", 5.0, 1.0, 6.0, 0.1);
    private final NumberValue<Double> searchRange = new NumberValue<>("Search Range", "搜索范围", 10.0, 1.0, 20.0, 0.1);
    private final NumberValue<Double> minCps = new NumberValue<>("Min CPS", "最小攻击速度", 10.0, 1.0, 20.0, 1.0);
    private final NumberValue<Double> maxCps = new NumberValue<>("Max CPS", "最大攻击速度", 10.0, 1.0, 20.0, 1.0);
    private final NumberValue<Integer> rotateSpeed = new NumberValue<>("Rotation Speed", "转向速度", 10, 1, 10, 1);
    private final BoolValue autoBlock = new BoolValue("Auto Block", "自动格挡", true);
    private final BoolValue debugRender = new BoolValue("Debug Render", "调试渲染", false);

    private List<LivingEntity> targets;
    private LivingEntity target;

    private long lastAttackTime = 0;

    @Override
    protected void onDisable() {
        target = null;
        targets = null;
    }

    public boolean isAutoBlock() {
        return autoBlock.get();
    }

    public LivingEntity getCurrentTarget() {
        return target;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean scaffoldEnable = Sakura.MODULES.getModule(Scaffold.class).isEnabled();
        //boolean blinkEnable = Sakura.MODULES.getModule(Blink.class).isEnabled();
        //if (scaffoldEnable || blinkEnable) return;
        if (scaffoldEnable) return;

        findTarget();

        if (target != null) {
            if (mc.player.squaredDistanceTo(target) <= aimRange.get() * aimRange.get()) {
                Rotation calculate = RotationUtil.calculate(target);
                Managers.ROTATION.setRotations(calculate, rotateSpeed.get(), MovementFix.NORMAL, RotationManager.Priority.Medium);
                if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target)) {
                    attackTarget();
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
        long time = System.currentTimeMillis();
        double baseDelay = 1000.0 / MathUtil.getRandom(minCps.get(), maxCps.get());
        long delay = (long) (baseDelay + (Math.random() - 0.5) * baseDelay * 0.4);
        if (time - lastAttackTime >= delay) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAttackTime = time;
        }
    }

    private void findTarget() {
        double range = Math.max(aimRange.get(), searchRange.get());
        this.target = null;
        this.targets = Managers.COMBAT.getEntities(range);
        this.target = Managers.COMBAT.getClosestEnemy(range);
    }
}
