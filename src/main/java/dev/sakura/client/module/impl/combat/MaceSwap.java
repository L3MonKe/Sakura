package dev.sakura.client.module.impl.combat;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
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
    private final BoolValue criticalFix = new BoolValue("CriticalFix", "暴击检测修复", true);
    private final BoolValue teamCheck = new BoolValue("Team Check", "队伍检测", true);
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    private int swapBackDelay = 0;
    private int originalSlot = -1;
    private boolean waitingSwapBack = false;

    public MaceSwap() {
        super("MaceSwap", "重锤秒切", Category.Combat);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (!isEnabled() || mc.player == null || mc.interactionManager == null) return;
            onTick();
        });
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
            mc.player.getInventory().setSelectedSlot(originalSlot);
        }

        swapBackDelay = 0;
        waitingSwapBack = false;
        originalSlot = -1;
    }

    private void onTick() {
        PlayerEntity v = mc.player;

        /* ======== 正在吃食物时不执行 ======== */
        if (mc.player.isUsingItem()) return;

        /* ======== 处理延迟切回 ======== */
        if (swapBackDelay > 0) {
            swapBackDelay--;
            if (swapBackDelay == 0 && waitingSwapBack && originalSlot != -1) {
                mc.player.getInventory().setSelectedSlot(originalSlot);
                waitingSwapBack = false;
            }
            return;
        }

        if (criticalFix.get() && v.getVelocity().y > 0) {
            return; // 玩家处于地面上，不允许暴击攻击
        }

        /* ======== 已经在等待切回，不再触发 ======== */
        if (waitingSwapBack) return;

        /* ======== 主手已经是重锤，不触发 ======== */
        if (mc.player.getMainHandStack().isOf(Items.MACE)) return;

        /* ======== 主手攻击冷却必须满 ======== */
        if (mc.player.getAttackCooldownProgress(0.0f) < 1.0f) return;

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
        mc.player.getInventory().setSelectedSlot(hammerSlot);

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        /* ======== 设置 1~2 tick 后切回 ======== */
        swapBackDelay = 1 + random.nextInt(SwapBackDelay.get().intValue());
        waitingSwapBack = true;
    }

    private int findHammerSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(Items.MACE)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isValidTarget(Entity e) {
        if (!(e instanceof LivingEntity)) return false;
        if (e == mc.player) return false;
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
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = player.getEquippedStack(slot);
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }
}