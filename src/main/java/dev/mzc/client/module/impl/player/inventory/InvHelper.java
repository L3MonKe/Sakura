package dev.mzc.client.module.impl.player.inventory;

import dev.mzc.client.utils.player.EnchantmentUtil;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static dev.mzc.client.Sakura.mc;

public class InvHelper {
    public static boolean shouldDisableFeatures() {
        return getAllItems().stream().anyMatch(item -> {
            if (item.isEmpty()) {
                return false;
            } else {
                String string = item.getName().getString();
                return string.contains("长按点击") || string.contains("点击使用") || string.contains("离开游戏") || string.contains("选择一个队伍") || string.contains("再来一局");
            }
        });
    }

    public static ItemStack getWorstArrow() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof ArrowItem && isItemValid(item))
                .min(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);
    }

    public static ItemStack getWorstProjectile() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && (item.getItem() == Items.EGG || item.getItem() == Items.SNOWBALL))
                .min(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);
    }

    public static int getItemStackSlot(ItemStack stack) {
        if (stack == null) return -1;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack s = mc.player.getInventory().getStack(i);
            if (!s.isEmpty() && ItemStack.areItemsEqual(s, stack) && ItemStack.areEqual(s, stack)) {
                return i;
            }
        }
        return -1;
    }

    public static ItemStack getBestProjectile() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && (item.getItem() == Items.EGG || item.getItem() == Items.SNOWBALL) && isItemValid(item))
                .max(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);
    }

    public static ItemStack getFishingRod() {
        return getAllItems().stream().filter(item -> !item.isEmpty() && item.getItem() instanceof FishingRodItem && isItemValid(item)).findAny().orElse(null);
    }

    public static float getAxeDamage(ItemStack stack) {
        float valence = 0.0F;
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else {
            if (stack.getItem() instanceof AxeItem axe && isSharpnessAxe(stack)) {
                if (axe == Items.WOODEN_AXE) {
                    valence += 4.0F;
                } else if (axe == Items.STONE_AXE) {
                    valence += 5.0F;
                } else if (axe == Items.IRON_AXE) {
                    valence += 6.0F;
                } else if (axe == Items.GOLDEN_AXE) {
                    valence += 4.0F;
                } else if (axe == Items.DIAMOND_AXE) {
                    valence += 7.0F;
                }
            }

            int itemEnchantmentLevel = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
            if (itemEnchantmentLevel > 0) {
                valence += itemEnchantmentLevel;
            }

            return valence;
        }
    }

    public static ItemStack getBestShapeAxe() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof AxeItem && isSharpnessAxe(item) && isItemValid(item) && !isGodAxe(item))
                .max(Comparator.comparingInt(s -> (int) (getAxeDamage(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getWorstBlock() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BlockItem && isValidStack(item) && isItemValid(item))
                .min(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);
    }

    public static ItemStack getBestBlock() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BlockItem && isValidStack(item) && isItemValid(item))
                .max(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);
    }

    public static int getItemSlot(Item item) {
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (!itemStack.isEmpty() && itemStack.getItem() == item) return i;
        }
        return -1;
    }

    public static List<ItemStack> getAllItems() {
        List<ItemStack> list = new ArrayList<>(mc.player.getInventory().size() + 4);
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            list.add(mc.player.getInventory().getStack(i));
        }
        list.add(mc.player.getEquippedStack(EquipmentSlot.HEAD));
        list.add(mc.player.getEquippedStack(EquipmentSlot.CHEST));
        list.add(mc.player.getEquippedStack(EquipmentSlot.LEGS));
        list.add(mc.player.getEquippedStack(EquipmentSlot.FEET));
        return list;
    }

    public static boolean isGodItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else if (stack.getItem() instanceof AxeItem
                && stack.getItem() == Items.GOLDEN_AXE
                && EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS) > 100) {
            return true;
        } else if (stack.getItem() == Items.SLIME_BALL && EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.KNOCKBACK) > 1) {
            return true;
        } else {
            return stack.getItem() == Items.TOTEM_OF_UNDYING || stack.getItem() == Items.END_CRYSTAL;
        }
    }

    public static boolean isGodAxe(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else {
            return stack.getItem() == Items.GOLDEN_AXE && EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS) > 100;
        }
    }

    public static boolean isSharpnessAxe(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else if (!(stack.getItem() instanceof AxeItem)) {
            return false;
        } else {
            int itemEnchantmentLevel = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
            return itemEnchantmentLevel >= 8 && itemEnchantmentLevel < 50;
        }
    }

    public static float getProtection(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0F;
        } else if (itemStack.isEmpty()) {
            return 0.0F;
        } else if (!isArmor(itemStack)) {
            return 0.0F;
        } else {
            float armor = 0.0F;
            float toughness = 0.0F;
            float knockbackResistance = 0.0F;

            AttributeModifiersComponent attrComp = itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            if (attrComp != null) {
                for (var entry : attrComp.modifiers()) {
                    if (entry.attribute().value() == EntityAttributes.ARMOR.value()) {
                        armor += (float) entry.modifier().value();
                    } else if (entry.attribute().value() == EntityAttributes.ARMOR_TOUGHNESS.value()) {
                        toughness += (float) entry.modifier().value();
                    } else if (entry.attribute().value() == EntityAttributes.KNOCKBACK_RESISTANCE.value()) {
                        knockbackResistance += (float) entry.modifier().value();
                    }
                }
            }

            int protection = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.PROTECTION);
            int blastProtection = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.BLAST_PROTECTION);
            int fireProtection = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.FIRE_PROTECTION);
            int projectileProtection = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.PROJECTILE_PROTECTION);
            int featherFalling = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.FEATHER_FALLING);
            int thorns = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.THORNS);
            int unbreaking = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.UNBREAKING);
            int mending = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.MENDING);
            int bindingCurse = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.BINDING_CURSE);
            int vanishingCurse = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.VANISHING_CURSE);

            float durabilityScore = 0.0F;
            if (itemStack.isDamageable() && itemStack.getMaxDamage() > 0) {
                float remaining = 1.0F - ((float) itemStack.getDamage() / (float) itemStack.getMaxDamage());
                durabilityScore = remaining * 0.75F;
            }

            float enchantScore = protection * 4.0F + (blastProtection + fireProtection + projectileProtection) * 3.0F + featherFalling * 2.5F + thorns * 0.5F + unbreaking * 0.25F + mending * 1.5F - (bindingCurse + vanishingCurse) * 50.0F;

            return armor * 10.0F + toughness * 8.0F + knockbackResistance * 30.0F + durabilityScore + enchantScore;
        }
    }

    public static float getBestArmorScore(EquipmentSlot slot) {
        return getAllItems()
                .stream()
                .filter(item -> {
                    if (item.isEmpty() || !isArmor(item)) return false;
                    var equippable = item.get(DataComponentTypes.EQUIPPABLE);
                    return equippable != null && equippable.slot() == slot;
                })
                .map(InvHelper::getProtection)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getSwordDamage(ItemStack stack) {
        float valence = 0.0F;
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else {
            AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            if (modifiers != null) {
                for (AttributeModifiersComponent.Entry entry : modifiers.modifiers()) {
                    if (entry.attribute().value() == EntityAttributes.ATTACK_DAMAGE.value()) {
                        valence += (float) entry.modifier().value();
                    }
                }
            }

            int itemEnchantmentLevel = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
            if (itemEnchantmentLevel > 0) {
                valence += itemEnchantmentLevel;
            }

            return valence;
        }
    }

    public static float getToolScore(ItemStack stack) {
        float valence = 0.0F;
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else if (isGodItem(stack)) {
            return 0.0F;
        } else if (isSharpnessAxe(stack)) {
            return 0.0F;
        } else {
            if (stack.isIn(ItemTags.PICKAXES)) {
                valence += stack.getMiningSpeedMultiplier(Blocks.STONE.getDefaultState());
            } else if (stack.getItem() instanceof AxeItem) {
                valence += stack.getMiningSpeedMultiplier(Blocks.OAK_LOG.getDefaultState());
            } else {
                if (!(stack.getItem() instanceof ShovelItem)) {
                    return 0.0F;
                }

                valence += stack.getMiningSpeedMultiplier(Blocks.DIRT.getDefaultState());
            }

            int efficiency = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            if (efficiency > 0) {
                valence += (float) efficiency * 0.0075F;
            }

            return valence;
        }
    }

    public static float getBestSwordDamage() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.isIn(ItemTags.SWORDS))
                .map(InvHelper::getSwordDamage)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getBestPickaxeScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.isIn(ItemTags.PICKAXES) && isItemValid(item))
                .map(InvHelper::getToolScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static boolean isItemValid(ItemStack s) {
        if (!s.isEmpty()) {
            if (s.getItem() instanceof PlayerHeadItem) {
                return false;
            }

            String string = s.getName().getString();
            if (string.contains("Click")) {
                return false;
            }

            if (string.contains("Right")) {
                return false;
            }

            if (string.contains("点击")) {
                return false;
            }

            if (string.contains("Teleport")) {
                return false;
            }

            if (string.contains("使用")) {
                return false;
            }

            if (string.contains("传送")) {
                return false;
            }

            return !string.contains("再来");
        }

        return true;
    }

    public static float getBestAxeScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof AxeItem && !isSharpnessAxe(item) && isItemValid(item))
                .map(InvHelper::getToolScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getBestShovelScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof ShovelItem && isItemValid(item))
                .map(InvHelper::getToolScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getCrossbowScore(ItemStack stack) {
        int valence = 0;
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else {
            if (stack.getItem() instanceof CrossbowItem) {
                valence += EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.QUICK_CHARGE);
                valence += EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.MULTISHOT);
                valence += EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.PIERCING);
            }

            return (float) valence;
        }
    }

    public static float getBestCrossbowScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof CrossbowItem && isItemValid(item))
                .map(InvHelper::getCrossbowScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getPunchBowScore(ItemStack stack) {
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else if (stack.getItem() instanceof BowItem) {
            float valence = 10.0F;
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.PUNCH);
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.INFINITY);
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FLAME);
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.POWER) / 10.0F;
            return valence + (float) stack.getDamage() / (float) stack.getMaxDamage();
        } else {
            return 0.0F;
        }
    }

    public static boolean isPunchBow(ItemStack stack) {
        return getPunchBowScore(stack) > 10.0F && isItemValid(stack);
    }

    public static float getPowerBowScore(ItemStack stack) {
        if (stack == null) {
            return 0.0F;
        } else if (stack.isEmpty()) {
            return 0.0F;
        } else if (stack.getItem() instanceof BowItem) {
            float valence = 10.0F;
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.PUNCH) / 10.0F;
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.INFINITY);
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FLAME);
            valence += (float) EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.POWER);
            return valence + (float) stack.getDamage() / (float) stack.getMaxDamage();
        } else {
            return 0.0F;
        }
    }

    public static float getBestPowerBowScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BowItem && isItemValid(item))
                .map(InvHelper::getPowerBowScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static float getBestPunchBowScore() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BowItem && isItemValid(item))
                .map(InvHelper::getPunchBowScore)
                .max(Float::compareTo)
                .orElse(0.0F);
    }

    public static boolean isPowerBow(ItemStack stack) {
        return getPowerBowScore(stack) > 10.0F && isItemValid(stack);
    }

    public static boolean hasItem(Item checkItem) {
        return getAllItems().stream().anyMatch(item -> !item.isEmpty() && item.getItem() == checkItem);
    }

    public static int getItemCount(Item checkItem) {
        return getAllItems().stream().filter(item -> !item.isEmpty() && item.getItem() == checkItem).mapToInt(ItemStack::getCount).sum();
    }

    public static boolean isValidStack(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof BlockItem) || stack.getCount() <= 1) {
            return false;
        } else if (!InvHelper.isItemValid(stack)) {
            return false;
        } else {
            String string = stack.getName().getString();
            if (string.contains("Click") || string.contains("点击")) {
                return false;
            } else if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                return false;
            } else {
                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (block instanceof FlowerBlock) {
                    return false;
                } else if (block instanceof PlantBlock) {
                    return false;
                } else if (block instanceof FungusBlock) {
                    return false;
                } else if (block instanceof CropBlock) {
                    return false;
                } else {
                    return !(block instanceof SlabBlock) && !blacklistedBlocks.contains(block);
                }
            }
        }
    }

    public static int getBlockCountInInventory() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BlockItem && isValidStack(item) && isItemValid(item))
                .mapToInt(ItemStack::getCount)
                .sum();
    }

    public static boolean isCommonItemUseful(ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        } else {
            Item item = stack.getItem();
            if (item instanceof BlockItem block) {
                if (block.getBlock() == Blocks.ENCHANTING_TABLE) {
                    return false;
                }

                return block.getBlock() != Blocks.COBWEB;
            } else {
                if (item == Items.BOOK || item instanceof WritableBookItem || item instanceof WrittenBookItem) {
                    return false;
                }

                if (item instanceof ExperienceBottleItem) {
                    return false;
                }

                if (item instanceof FireworkRocketItem) {
                    return false;
                }

                if (item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.MELON_SEEDS || item == Items.PUMPKIN_SEEDS) {
                    return false;
                }

                return item != Items.FLINT_AND_STEEL;
            }
        }
    }

    public static float getCurrentArmorScore(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            return getProtection(mc.player.getEquippedStack(EquipmentSlot.HEAD));
        } else if (slot == EquipmentSlot.CHEST) {
            return getProtection(mc.player.getEquippedStack(EquipmentSlot.CHEST));
        } else if (slot == EquipmentSlot.LEGS) {
            return getProtection(mc.player.getEquippedStack(EquipmentSlot.LEGS));
        } else {
            return slot == EquipmentSlot.FEET ? getProtection(mc.player.getEquippedStack(EquipmentSlot.FEET)) : 0.0F;
        }
    }

    public static ItemStack getBestPickaxe() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.isIn(ItemTags.PICKAXES) && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getToolScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestAxe() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof AxeItem && !isSharpnessAxe(item) && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getToolScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestShovel() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof ShovelItem && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getToolScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestCrossbow() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof CrossbowItem && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getCrossbowScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestPunchBow() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BowItem && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getPunchBowScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestPowerBow() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof BowItem && isItemValid(item))
                .max(Comparator.comparingInt(s -> (int) (getPowerBowScore(s) * 100.0F)))
                .orElse(null);
    }

    public static ItemStack getBestSword() {
        return getAllItems()
                .stream()
                .filter(item -> !item.isEmpty() && item.isIn(ItemTags.SWORDS))
                .max(Comparator.comparingInt(s -> (int) (getSwordDamage(s) * 100.0F)))
                .orElse(null);
    }

    public static boolean isArmor(ItemStack itemStack) {
        return itemStack.isIn(ItemTags.FOOT_ARMOR) || itemStack.isIn(ItemTags.LEG_ARMOR) || itemStack.isIn(ItemTags.CHEST_ARMOR) || itemStack.isIn(ItemTags.HEAD_ARMOR);
    }

    public static final List<Block> blacklistedBlocks = Arrays.asList(
            Blocks.AIR,
            Blocks.WATER,
            Blocks.LAVA,
            Blocks.ENCHANTING_TABLE,
            Blocks.GLASS_PANE,
            Blocks.GLASS_PANE,
            Blocks.IRON_BARS,
            Blocks.SNOW,
            Blocks.COAL_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.TORCH,
            Blocks.ANVIL,
            Blocks.TRAPPED_CHEST,
            Blocks.NOTE_BLOCK,
            Blocks.JUKEBOX,
            Blocks.TNT,
            Blocks.GOLD_ORE,
            Blocks.IRON_ORE,
            Blocks.LAPIS_ORE,
            Blocks.STONE_PRESSURE_PLATE,
            Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Blocks.STONE_BUTTON,
            Blocks.LEVER,
            Blocks.TALL_GRASS,
            Blocks.TRIPWIRE,
            Blocks.TRIPWIRE_HOOK,
            Blocks.RAIL,
            Blocks.CORNFLOWER,
            Blocks.RED_MUSHROOM,
            Blocks.BROWN_MUSHROOM,
            Blocks.VINE,
            Blocks.SUNFLOWER,
            Blocks.LADDER,
            Blocks.FURNACE,
            Blocks.SAND,
            Blocks.CACTUS,
            Blocks.DISPENSER,
            Blocks.DROPPER,
            Blocks.CRAFTING_TABLE,
            Blocks.COBWEB,
            Blocks.PUMPKIN,
            Blocks.COBBLESTONE_WALL,
            Blocks.OAK_FENCE,
            Blocks.REDSTONE_TORCH,
            Blocks.FLOWER_POT
    );
}
