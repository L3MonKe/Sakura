package dev.lemonclient.client.module.impl.combat;

import dev.lemonclient.client.events.client.TickEvent;
import dev.lemonclient.client.events.entity.AttackEvent;
import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;
import dev.lemonclient.client.utils.player.EnchantmentUtil;
import dev.lemonclient.client.utils.player.InvUtil;
import dev.lemonclient.client.values.impl.BoolValue;
import dev.lemonclient.client.values.impl.EnumValue;
import dev.lemonclient.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;

public class AttributeSwap extends Module {
    private final EnumValue<Page> page = new EnumValue<>("Page", "页面", Page.General);

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Simple, () -> page.get() == Page.General);
    private final NumberValue<Integer> targetSlot = new NumberValue<>("Target Slot", "目标槽位", 1, 1, 9, 1, () -> page.get() == Page.General && mode.get() == Mode.Simple);
    private final BoolValue swapBack = new BoolValue("Swap Back", "交换回原槽位", true, () -> page.get() == Page.General);
    private final NumberValue<Integer> swapBackDelay = new NumberValue<>("Swap Back Delay", "交换回原槽位延迟", 2, 0, 100, 1, () -> page.get() == Page.General);

    private final BoolValue smartShieldBreak = new BoolValue("Smart Shield Breaker", "智能护盾破坏", true, () -> page.get() == Page.Swapping && mode.get() == Mode.Smart);
    private final BoolValue smartDurability = new BoolValue("Smart Durability Saver", "智能耐久保存", true, () -> page.get() == Page.Swapping && mode.get() == Mode.Smart);
    private final BoolValue swordSwapping = new BoolValue("Sword Swapping", "剑附魔交换", true, () -> page.get() == Page.Swapping && mode.get() == Mode.Smart);
    private final BoolValue maceSwapping = new BoolValue("Mace Swapping", "重锤附魔交换", true, () -> page.get() == Page.Swapping && mode.get() == Mode.Smart);
    private final BoolValue otherSwapping = new BoolValue("Other Swapping", "其他附魔交换", true, () -> page.get() == Page.Swapping && mode.get() == Mode.Smart);

    private final BoolValue enchantFireAspect = new BoolValue("Fire Aspect", "火焰附加交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());
    private final BoolValue enchantLooting = new BoolValue("Looting", "掠夺交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());
    private final BoolValue enchantSharpness = new BoolValue("Sharpness", "锋利交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());
    private final BoolValue enchantSmite = new BoolValue("Smite", "亡灵杀手交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());
    private final BoolValue enchantBaneOfArthropods = new BoolValue("Bane of Arthropods", "截肢杀手交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());

    private final BoolValue enchantSweepingEdge = new BoolValue("Sweeping Edge", "横扫交换", true, () -> page.get() == Page.SwordEnchants && mode.get() == Mode.Smart && swordSwapping.get());
    private final BoolValue regularMace = new BoolValue("Regular Mace", "重锤交换", true, () -> page.get() == Page.MaceEnchants && mode.get() == Mode.Smart && maceSwapping.get());
    private final BoolValue enchantDensity = new BoolValue("Density", "致密交换", true, () -> page.get() == Page.MaceEnchants && mode.get() == Mode.Smart && maceSwapping.get());
    private final BoolValue enchantBreach = new BoolValue("Breach", "突刺交换", true, () -> page.get() == Page.MaceEnchants && mode.get() == Mode.Smart && maceSwapping.get());
    private final BoolValue enchantWindBurst = new BoolValue("Wind Burst", "风爆交换", true, () -> page.get() == Page.MaceEnchants && mode.get() == Mode.Smart && maceSwapping.get());

    private final BoolValue enchantImpaling = new BoolValue("Impaling", "刺穿交换", true, () -> page.get() == Page.OtherEnchants && mode.get() == Mode.Smart && otherSwapping.get());

    private final BoolValue onlyOnWeapon = new BoolValue("Only On Weapon", "仅在武器上交换", false, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart);
    private final BoolValue sword = new BoolValue("Sword", "仅在剑上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue axe = new BoolValue("Axe", "仅在斧头上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue pickaxe = new BoolValue("Pickaxe", "仅在镐上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue shovel = new BoolValue("Shovel", "仅在铲子上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue hoe = new BoolValue("Hoe", "仅在锄头上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue mace = new BoolValue("Mace", "仅在重锤上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());
    private final BoolValue trident = new BoolValue("Trident", "仅在三叉戟上交换", true, () -> page.get() == Page.Weapon && mode.get() == Mode.Smart && onlyOnWeapon.get());

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
        if (!canSwapByWeapon()) return;
        performSwap(event.getTargetEntity());
    }

    private void performSwap(Entity target) {
        if (awaitingBack) return;

        int slotIndex;

        if (mode.get() == Mode.Simple) {
            slotIndex = targetSlot.get() - 1;
        } else {
            slotIndex = getSmartSlot(target);
        }

        if (slotIndex < 0 || slotIndex > 8) return;
        if (slotIndex == mc.player.getInventory().selectedSlot) return;

        if (!InvUtil.swap(slotIndex, swapBack.get())) return;

        awaitingBack = swapBack.get();
        if (awaitingBack) backTimer = swapBackDelay.get();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!awaitingBack) return;
        if (backTimer-- > 0) return;
        InvUtil.swapBack();
        awaitingBack = false;
    }

    private boolean canSwapByWeapon() {
        if (!onlyOnWeapon.get()) return true;
        return InvUtil.testInMainHand(item ->
                (sword.get() && item.isIn(ItemTags.SWORDS)) ||
                        (axe.get() && item.isIn(ItemTags.AXES)) ||
                        (pickaxe.get() && item.isIn(ItemTags.PICKAXES)) ||
                        (shovel.get() && item.isIn(ItemTags.SHOVELS)) ||
                        (hoe.get() && item.isIn(ItemTags.HOES)) ||
                        (mace.get() && item.getItem() instanceof MaceItem) ||
                        (trident.get() && item.getItem() instanceof TridentItem)
        );
    }

    private int getSmartSlot(Entity target) {
        ItemStack currentStack = mc.player.getMainHandStack();

        if (target != null && smartShieldBreak.get() && target instanceof LivingEntity living && living.isBlocking()) {
            if (currentStack.getItem() instanceof AxeItem) return -1;
            int axeSlot = InvUtil.findInHotbar(item -> item.getItem() instanceof AxeItem).slot();
            if (axeSlot != -1) return axeSlot;
        }

        boolean isFalling = mc.player.fallDistance > 1.5;
        boolean durability = smartDurability.get();

        boolean isLiving = target instanceof LivingEntity;
        boolean isPlayer = target instanceof PlayerEntity;
        boolean isOnFire = target != null && target.isOnFire();
        boolean isUndead = target != null && target.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE);
        boolean isArthropod = target != null && target.getType().isIn(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS);
        boolean isAquatic = target != null && target.getType().isIn(EntityTypeTags.SENSITIVE_TO_IMPALING);
        boolean hasFireResistance = isLiving && (((LivingEntity) target).hasStatusEffect(StatusEffects.FIRE_RESISTANCE) || hasFireProtectionArmor((LivingEntity) target));
        double armor = isLiving ? ((LivingEntity) target).getAttributeValue(EntityAttributes.ARMOR) : 0;
        float health = isLiving ? ((LivingEntity) target).getHealth() : 0;

        int bestSlot = -1;
        double bestScore = getItemScore(currentStack, isFalling, durability, isLiving, isPlayer, isOnFire, hasFireResistance, isUndead, isArthropod, isAquatic, armor, health);

        for (int i = 0; i < 9; i++) {
            if (i == mc.player.getInventory().selectedSlot) continue;

            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() && !durability) continue;

            double score = getItemScore(stack, isFalling, durability, isLiving, isPlayer, isOnFire, hasFireResistance, isUndead, isArthropod, isAquatic, armor, health);
            if (score > bestScore) {
                bestScore = score;
                bestSlot = i;
            }
        }

        return bestSlot;
    }

    private double getItemScore(ItemStack stack, boolean isFalling, boolean durability, boolean isLiving, boolean isPlayer, boolean isOnFire, boolean hasFireResistance, boolean isUndead, boolean isArthropod, boolean isAquatic, double armor, float health) {
        double score = 0;

        if (durability) {
            score += getDurabilityScore(stack);
        }

        if (stack.isEmpty()) return score;

        score += getCombatScore(stack, isFalling, isLiving, isPlayer, isOnFire, hasFireResistance, isUndead, isArthropod, isAquatic, armor, health);

        return score;
    }

    private double getDurabilityScore(ItemStack stack) {
        if (!stack.isDamageable()) return 4;

        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);
        if (unbreaking > 0) return unbreaking * 0.05;

        return 0;
    }

    private double getCombatScore(ItemStack stack, boolean isFalling, boolean isLiving, boolean isPlayer, boolean isOnFire, boolean hasFireResistance, boolean isUndead, boolean isArthropod, boolean isAquatic, double armor, float health) {
        double score = 0;

        if (swordSwapping.get()) {
            score += getFireAspectScore(stack, isOnFire, hasFireResistance);
            score += getLootingScore(stack, isPlayer, isLiving, isOnFire, health);
            score += getSharpnessScore(stack, isOnFire);
            score += getSmiteScore(stack, isUndead, isOnFire);
            score += getBaneOfArthropodsScore(stack, isArthropod, isOnFire);
            score += getSweepingEdgeScore(stack);
        }
        if (maceSwapping.get()) {
            score += getBreachScore(stack, isLiving, armor);
            score += getDensityScore(stack, isFalling);
            score += getWindBurstScore(stack, isFalling);
            score += getMaceScore(stack, isFalling);
        }
        if (otherSwapping.get()) {
            score += getImpalingScore(stack, isAquatic);
        }

        return score;
    }

    private double getFireAspectScore(ItemStack stack, boolean isOnFire, boolean hasFireResistance) {
        if (!enchantFireAspect.get() || isOnFire || hasFireResistance) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FIRE_ASPECT);
        return (level > 0) ? 30 : 0;
    }

    private double getLootingScore(ItemStack stack, boolean isPlayer, boolean isLiving, boolean isOnFire, float health) {
        if (!enchantLooting.get() || isPlayer) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.LOOTING);
        if (level > 0) {
            boolean execute = (isLiving && health < 20) || isOnFire;
            return level * (execute ? 10 : 5);
        }
        return 0;
    }

    private double getSharpnessScore(ItemStack stack, boolean isOnFire) {
        if (!enchantSharpness.get()) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
        if (level > 0) {
            double baseScore = (1 + 0.5 * (level - 1)) * 3;
            return isOnFire ? baseScore * 1.5 : baseScore;
        }
        return 0;
    }

    private double getSmiteScore(ItemStack stack, boolean isUndead, boolean isOnFire) {
        if (!enchantSmite.get() || !isUndead) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SMITE);
        if (level > 0) {
            double baseScore = level * 5;
            return isOnFire ? baseScore * 1.5 : baseScore;
        }
        return 0;
    }

    private double getBaneOfArthropodsScore(ItemStack stack, boolean isArthropod, boolean isOnFire) {
        if (!enchantBaneOfArthropods.get() || !isArthropod) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.BANE_OF_ARTHROPODS);
        if (level > 0) {
            double baseScore = level * 5;
            return isOnFire ? baseScore * 1.5 : baseScore;
        }
        return 0;
    }

    private double getSweepingEdgeScore(ItemStack stack) {
        if (!enchantSweepingEdge.get()) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SWEEPING_EDGE);
        if (level > 0) {
            return level * 3;
        }
        return 0;
    }

    private double getImpalingScore(ItemStack stack, boolean isAquatic) {
        if (!enchantImpaling.get() || !isAquatic) return 0;

        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.IMPALING);
        if (level > 0) {
            return level * 5;
        }
        return 0;
    }

    private double getBreachScore(ItemStack stack, boolean isLiving, double armor) {
        if (!enchantBreach.get() || !isLiving || armor <= 0) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.BREACH);
        if (level > 0) {
            return level * armor * 0.3;
        }
        return 0;
    }

    private double getDensityScore(ItemStack stack, boolean isFalling) {
        if (!enchantDensity.get() || !isFalling) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.DENSITY);
        if (level > 0) return 50 + (level * mc.player.fallDistance * 2);
        return 0;
    }

    private double getWindBurstScore(ItemStack stack, boolean isFalling) {
        if (!enchantWindBurst.get() || !isFalling) return 0;
        int level = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.WIND_BURST);
        if (level > 0) return level * 20;
        return 0;
    }

    private double getMaceScore(ItemStack stack, boolean isFalling) {
        if (!regularMace.get() || !isFalling) return 0;
        if (stack.getItem() instanceof MaceItem) return 40;
        return 0;
    }

    private boolean hasFireProtectionArmor(LivingEntity entity) {
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = entity.getEquippedStack(slot);
            if (stack.isEmpty()) continue;

            int fireProtection = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FIRE_PROTECTION);
            if (fireProtection > 0) return true;
        }
        return false;
    }

    public enum Mode {
        Simple,
        Smart
    }

    public enum Page {
        General,
        Swapping,
        SwordEnchants,
        MaceEnchants,
        OtherEnchants,
        Weapon
    }
}