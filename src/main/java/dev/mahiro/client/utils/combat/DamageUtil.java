package dev.mahiro.client.utils.combat;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.Objects;

import static dev.mahiro.client.Mahiro.mc;

public class DamageUtil {
    public static double applyArmor(LivingEntity entity, double damage) {
        double armor = entity.getArmor();
        double f = 2 + entity.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS) / 4;

        return damage * (1 - MathHelper.clamp(armor - damage / f, armor * 0.2, 20) / 25);
    }

    public static double applyResistance(LivingEntity entity, double damage) {
        if (entity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            int amplifier = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.RESISTANCE)).getAmplifier();
            return Math.max(damage * (25 - (amplifier + 1) * 5) / 25, 0);
        }
        return damage;
    }

    public static double applyProtection(LivingEntity entity, double damage, boolean explosions) {
        int i = getProtectionAmount(List.of(
            entity.getEquippedStack(EquipmentSlot.FEET),
            entity.getEquippedStack(EquipmentSlot.LEGS),
            entity.getEquippedStack(EquipmentSlot.CHEST),
            entity.getEquippedStack(EquipmentSlot.HEAD)
        ), explosions);
        if (i > 0)
            damage *= (1 - MathHelper.clamp(i, 0f, 20f) / 25);

        return damage;
    }

    public static int getProtectionAmount(Iterable<ItemStack> equipment, boolean explosion) {
        MutableInt mint = new MutableInt();

        for (ItemStack stack : equipment) {
            if (stack.isEmpty()) continue;

            ItemEnchantmentsComponent enchantments = stack.get(DataComponentTypes.ENCHANTMENTS);
            if (enchantments == null) continue;

            enchantments.getEnchantments().forEach(entry -> {
                int level = enchantments.getLevel(entry);
                if (entry.matchesKey(Enchantments.PROTECTION))
                    mint.add(level);
                else if (explosion && entry.matchesKey(Enchantments.BLAST_PROTECTION))
                    mint.add(level * 2);
            });
        }

        return mint.intValue();
    }
}
