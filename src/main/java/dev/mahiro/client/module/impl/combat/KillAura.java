package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.movement.Scaffold;
import dev.mahiro.client.module.impl.player.Blink;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.RotationUtil;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;

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
    private final NumberValue<Double> cps = new NumberValue<>("CPS", "攻击速度", 10.0, 1.0, 20.0, 1.0);
    private final NumberValue<Integer> rotateSpeed = new NumberValue<>("Rotation Speed", "转向速度", 10, 1, 10, 1);
    private final BoolValue autoBlock = new BoolValue("AutoBlock", "自动格挡", false);
    private final EnumValue<AutoBlockMode> autoBlockMode = new EnumValue<>("Block Mode", "格挡模式", AutoBlockMode.Fake, autoBlock::get);
    private final BoolValue teamCheck = new BoolValue("Team Check", "队伍检测", true);
    private final BoolValue render = new BoolValue("Render", "渲染", true);

    private List<Entity> targets;
    private Entity target;

    private long lastAttackTime = 0;

    @Override
    protected void onDisable() {
        target = null;
        targets = null;
    }

    public boolean isAutoBlock() {
        return autoBlock.get();
    }

    public Entity getCurrentTarget() {
        return target;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean scaffoldEnable = Mahiro.MODULES.getModule(Scaffold.class).isEnabled();
        boolean blinkEnable = Mahiro.MODULES.getModule(Blink.class).isEnabled();
        if (scaffoldEnable || blinkEnable) return;

        findTarget();

        if (target != null) {
            Rotation calculate = RotationUtil.calculate(target);
            Managers.ROTATION.setRotations(calculate, rotateSpeed.get(), MovementFix.NORMAL, RotationManager.Priority.Medium);
            if (mc.crosshairTarget instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(target)) {
                attackTarget();
            }
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (!render.get()) return;
        if (targets == null || targets.isEmpty()) return;
        for (Entity entity : targets) {
            if (entity.equals(target)) {
                Render3DUtil.drawFilledBox(event.getMatrices(), entity.getBoundingBox(), new Color(200, 0, 0, 60).getRGB());
                Render3DUtil.drawBoxOutline(event.getMatrices(), entity.getBoundingBox(), new Color(200, 0, 0, 60).getRGB(), 2f);
            } else {
                Render3DUtil.drawFilledBox(event.getMatrices(), entity.getBoundingBox(), new Color(0, 200, 0, 60).getRGB());
                Render3DUtil.drawBoxOutline(event.getMatrices(), entity.getBoundingBox(), new Color(0, 200, 0, 60).getRGB(), 2f);
            }
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
        double range = aimRange.get();
        double rangeSq = range * range;

        this.target = null;
        double minDstSq = Double.MAX_VALUE;

        Box searchBox = mc.player.getBoundingBox().expand(range);

        List<Entity> candidates = mc.world.getOtherEntities(mc.player, searchBox, e -> e instanceof LivingEntity && e != mc.player && e.isAlive() && !e.isSpectator() && isEnemy(e));

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

    private boolean isEnemy(Entity entity) {
        if (!teamCheck.get()) return true;
        if (!(entity instanceof PlayerEntity player)) return true;
        if (mc.player == null) return false;

        int myColor = getLeatherArmorColor(mc.player);
        int theirColor = getLeatherArmorColor(player);

        if (myColor == -1 || theirColor == -1) return true;

        return myColor != theirColor;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }
}