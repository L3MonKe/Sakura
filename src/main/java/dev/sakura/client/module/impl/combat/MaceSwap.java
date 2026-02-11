package dev.sakura.client.module.impl.combat;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.EnchantmentUtil;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Random;

public class MaceSwap extends Module {

    private final NumberValue<Double> SwapBackDelay = new NumberValue<>("SwapBackDelay", "切回延迟", 2.0, 1.0, 10.0, 1.0);
    private final BoolValue smart = new BoolValue("Smart", "智能模式", true);
    private final BoolValue criticalFix = new BoolValue("CriticalFix", "暴击检测修复", true);
    private final BoolValue teamCheck = new BoolValue("Team Check", "队伍检测", true);
    private final BoolValue antiBot = new BoolValue("AntiBot", "防假人", true);
    private final Random random = new Random();

    private int swapBackDelay = 0;
    private int originalSlot = -1;
    private boolean waitingSwapBack = false;

    public MaceSwap() {
        super("MaceSwap", "重锤秒切", Category.Combat);
    }

    @Override
    public void onEnable() {
        swapBackDelay = 0;
        originalSlot = -1;
        waitingSwapBack = false;
    }

    @Override
    public void onDisable() {
        if (waitingSwapBack && mc.player != null && originalSlot != -1) {
            InvUtil.swap(originalSlot, false);
        }

        swapBackDelay = 0;
        waitingSwapBack = false;
        originalSlot = -1;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        /* ======== 正在吃食物时不执行 ======== */
        if (mc.player.isUsingItem()) return;

        /* ======== 处理延迟切回 ======== */
        if (swapBackDelay > 0) {
            swapBackDelay--;
            if (swapBackDelay <= 0 && waitingSwapBack && originalSlot != -1) {
                InvUtil.swap(originalSlot, false);
                waitingSwapBack = false;
                originalSlot = -1;
            }
            return;
        }

        // Critical detection: Mace deals more damage when falling.
        if (criticalFix.get()) {
            boolean isFalling = mc.player.fallDistance > 0 || mc.player.getVelocity().y < -0.01;
            if (!isFalling) return;
        }

        /* ======== 已经在等待切回，不再触发 ======== */
        if (waitingSwapBack) return;

        /* ======== 主手已经是重锤，不触发 ======== */
        if (mc.player.getMainHandStack().isOf(Items.MACE)) return;

        /* ======== 主手攻击冷却必须满 ======== */
        if (mc.player.getAttackCooldownProgress(0.0f) < 0.95f) return;

        /* ======== 准星检测 ======== */
        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult ehr)) return;

        Entity target = ehr.getEntity();
        if (!isValidTarget(target)) return;

        /* ======== 攻击距离检测 ======== */
        double range = mc.player.getEntityInteractionRange();
        if (mc.player.squaredDistanceTo(target) > range * range) return;

        /* ======== 找重锤 ======== */
        int hammerSlot = findHammerSlot();
        if (hammerSlot == -1) return;

        /* ======== 执行：切锤 → 攻击 ======== */
        originalSlot = mc.player.getInventory().getSelectedSlot();
        InvUtil.swap(hammerSlot, false);

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        /* ======== 设置切回延迟 ======== */
        swapBackDelay = SwapBackDelay.get().intValue();
        waitingSwapBack = true;
    }

    private int findHammerSlot() {
        int bestSlot = -1;
        double bestScore = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(Items.MACE)) {
                if (!smart.get()) return i;

                double score = 0;
                boolean isHighFall = mc.player.fallDistance > 2.0 || mc.player.getVelocity().y < -0.5;

                if (isHighFall) {
                    score += EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.DENSITY) * 10000.0;
                } else {
                    score += EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.BREACH) * 10000.0;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }

    private boolean isValidTarget(Entity e) {
        if (!(e instanceof LivingEntity)) return false;
        if (e == mc.player) return false;
        if (antiBot.get() && AntiBot.isBot(e)) return false;
        if (teamCheck.get() && !isEnemy(e)) return false;
        return e instanceof PlayerEntity || e instanceof Monster;
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
        for (int i = 0; i < 4; i++) {
            ItemStack stack = player.getInventory().getStack(36 + i);
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }
}
