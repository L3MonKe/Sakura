package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.entity.AttackEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;

public class AttributeSwap extends Module {
    public enum Mode {
        Axe("斧"),
        Sword("剑"),
        Mace("锤");

        private final String cnName;
        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Sword);
    private final BoolValue smart = new BoolValue("Smart", "智能模式", true, () -> mode.is(Mode.Mace));
    private final BoolValue checkShield = new BoolValue("Check Shield", "检查举盾", true, () -> mode.is(Mode.Axe));
    private final BoolValue swapBack = new BoolValue("Swap Back", "切回原物品", true);
    private final NumberValue<Integer> swapBackDelay = new NumberValue<>("Swap Back Delay", "切回延迟", 1, 0, 10, 1, () -> swapBack.get());

    private int backTimer;
    private boolean awaitingBack;

    public AttributeSwap() {
        super("AttributeSwap", "属性切换", Category.Combat);
    }

    @Override
    public void onDisable() {
        backTimer = 0;
        awaitingBack = false;
    }

    @EventHandler
    private void onAttack(AttackEvent event) {
        performSwap(event.getTargetEntity());
    }

    private void performSwap(Entity target) {
        if (awaitingBack) return;

        int bestSlot = getBestSlot(target);

        if (bestSlot < 0 || bestSlot > 8) return;
        if (bestSlot == mc.player.getInventory().selectedSlot) {
            return;
        }

        if (!InvUtil.swap(bestSlot, swapBack.get())) return;

        if (swapBack.get()) {
            awaitingBack = true;
            backTimer = swapBackDelay.get();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!awaitingBack) return;
        if (backTimer-- > 0) return;
        InvUtil.swapBack();
        awaitingBack = false;
    }

    private int getBestSlot(Entity target) {
        int bestSlot = -1;
        double bestScore = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            double score = getItemScore(stack, target);
            if (score > bestScore) {
                bestScore = score;
                bestSlot = i;
            }
        }
        
        // If current held item is better or equal, don't swap (unless it's not the best slot found?)
        // Actually, loop covers current slot. If current slot is best, it returns current slot index.
        // The check `bestSlot == selectedSlot` in performSwap handles it.

        return bestSlot;
    }

    private double getItemScore(ItemStack stack, Entity target) {
        if (!isAllowed(stack)) return -1;

        if (mode.is(Mode.Axe) && checkShield.get()) {
             if (target instanceof PlayerEntity player && player.isBlocking()) {
                 // Proceed with scoring if target is blocking
             } else {
                 // If target is not blocking, don't swap to Axe
                 return -1;
             }
        }

        double score = 0;
        Item item = stack.getItem();

        // Base Damage Estimation
        float baseDamage = 0;
        if (item instanceof SwordItem) {
            baseDamage = 3 + getMaterialScore(item);
        } else if (item instanceof AxeItem) {
            baseDamage = 6 + getMaterialScore(item);
        } else if (item instanceof MaceItem) {
            baseDamage = 6;
        }
        score += baseDamage;

        // Enchantments
        score += InvUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS) * 0.5; // Sharpness adds 0.5 * level + 0.5
        score += InvUtil.getEnchantmentLevel(stack, Enchantments.BREACH) * 0.5;
        score += InvUtil.getEnchantmentLevel(stack, Enchantments.DENSITY) * 0.5;

        // Situational Bonuses
        if (item instanceof MaceItem) {
             if (mc.player.fallDistance > 1.5) {
                 score += 1000; // Massive bonus for Mace Smash
                 score += InvUtil.getEnchantmentLevel(stack, Enchantments.DENSITY) * mc.player.fallDistance; // Density scaling
             }

             if (smart.get() && mode.is(Mode.Mace)) {
                 boolean isHighFall = mc.player.fallDistance > 2.0 || mc.player.getVelocity().y < -0.5;
                 
                 if (!isHighFall) {
                     // Flat ground/Jump crit: Prioritize Breach (破甲)
                     score += InvUtil.getEnchantmentLevel(stack, Enchantments.BREACH) * 10000.0;
                 } else {
                     // High fall: Prioritize Density (风暴/密度)
                     score += InvUtil.getEnchantmentLevel(stack, Enchantments.DENSITY) * 10000.0;
                 }
             }
        }

        return score;
    }

    private int getMaterialScore(Item item) {
        String name = item.toString().toLowerCase();
        if (name.contains("netherite")) return 4;
        if (name.contains("diamond")) return 3;
        if (name.contains("iron")) return 2;
        if (name.contains("stone")) return 1;
        return 0; // Wood/Gold
    }

    private boolean isAllowed(ItemStack stack) {
        Item item = stack.getItem();
        if (mode.is(Mode.Axe) && stack.isIn(ItemTags.AXES)) return true;
        if (mode.is(Mode.Sword) && stack.isIn(ItemTags.SWORDS)) return true;
        if (mode.is(Mode.Mace) && item instanceof MaceItem) return true;
        return false;
    }
}