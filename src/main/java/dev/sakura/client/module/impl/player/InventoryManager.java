package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class InventoryManager extends Module {
    private final TimerUtil timer = new TimerUtil();

    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 3, 1, 10, 1);
    private final EnumValue<OffhandItem> offhandItem = new EnumValue<>("Offhand", "副手", OffhandItem.None);
    private final BoolValue autoArmor = new BoolValue("AutoArmor", "自动穿甲", true);
    private final BoolValue inventoryOnly = new BoolValue("InventoryOnly", "仅背包内", true);
    private final BoolValue swordSort = new BoolValue("SwordSort", "剑整理", true);
    private final NumberValue<Integer> swordSlot = new NumberValue<>("SwordSlot", "剑槽位", 1, 1, 9, 1, swordSort::get);
    private final BoolValue blockSort = new BoolValue("BlockSort", "方块整理", true);
    private final NumberValue<Integer> blockSlot = new NumberValue<>("BlockSlot", "方块槽位", 2, 1, 9, 1, blockSort::get);
    private final NumberValue<Integer> maxBlocks = new NumberValue<>("MaxBlocks", "最大方块", 256, 64, 512, 64, blockSort::get);
    private final BoolValue pickaxeSort = new BoolValue("PickaxeSort", "镐整理", true);
    private final NumberValue<Integer> pickaxeSlot = new NumberValue<>("PickaxeSlot", "镐槽位", 3, 1, 9, 1, pickaxeSort::get);
    private final BoolValue axeSort = new BoolValue("AxeSort", "斧整理", true);
    private final NumberValue<Integer> axeSlot = new NumberValue<>("AxeSlot", "斧槽位", 4, 1, 9, 1, axeSort::get);
    private final BoolValue goldenAppleSort = new BoolValue("GappleSort", "金苹果整理", true);
    private final NumberValue<Integer> goldenAppleSlot = new NumberValue<>("GappleSlot", "金苹果槽位", 5, 1, 9, 1, goldenAppleSort::get);
    private final BoolValue pearlSort = new BoolValue("PearlSort", "珍珠整理", true);
    private final NumberValue<Integer> pearlSlot = new NumberValue<>("PearlSlot", "珍珠槽位", 6, 1, 9, 1, pearlSort::get);
    private final BoolValue waterSort = new BoolValue("WaterSort", "水桶整理", true);
    private final NumberValue<Integer> waterSlot = new NumberValue<>("WaterSlot", "水桶槽位", 7, 1, 9, 1, waterSort::get);
    private final BoolValue throwUseless = new BoolValue("ThrowUseless", "丢弃无用", true);

    public InventoryManager() {
        super("InventoryManager", "背包管理", Category.Player);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) return;
        if (inventoryOnly.get() && mc.currentScreen == null) return;
        if (!timer.delay(delay.get())) return;

        if (autoArmor.get()) handleAutoArmor();
        if (swordSort.get()) sortItem(swordSlot.get() - 1, this::isSword, this::getSwordScore);
        if (blockSort.get()) sortBlock(blockSlot.get() - 1);
        if (pickaxeSort.get())
            sortItem(pickaxeSlot.get() - 1, stack -> stack.getItem() instanceof PickaxeItem, this::getToolScore);
        if (axeSort.get())
            sortItem(axeSlot.get() - 1, stack -> stack.getItem() instanceof AxeItem && !isSharpnessAxe(stack), this::getToolScore);
        if (goldenAppleSort.get())
            sortItem(goldenAppleSlot.get() - 1, stack -> stack.isOf(Items.GOLDEN_APPLE) || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE), stack -> stack.isOf(Items.ENCHANTED_GOLDEN_APPLE) ? 1000f : (float) stack.getCount());
        if (pearlSort.get())
            sortItem(pearlSlot.get() - 1, stack -> stack.isOf(Items.ENDER_PEARL), stack -> (float) stack.getCount());
        if (waterSort.get()) sortItem(waterSlot.get() - 1, stack -> stack.isOf(Items.WATER_BUCKET), stack -> 1f);
        handleOffhand();
        if (throwUseless.get()) throwUselessItems();
    }

    private void handleAutoArmor() {
        for (int armorSlot = 0; armorSlot < 4; armorSlot++) {
            ItemStack currentArmor = mc.player.getInventory().getArmorStack(armorSlot);
            EquipmentSlot slot = getEquipmentSlot(armorSlot);
            float currentScore = getArmorScore(currentArmor);
            int bestSlot = -1;
            float bestScore = currentScore;
            for (int i = 0; i < 36; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (!isArmor(stack)) continue;
                EquipmentSlot stackSlot = getArmorSlot(stack);
                if (stackSlot != slot) continue;
                float score = getArmorScore(stack);
                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = i;
                }
            }
            if (bestSlot != -1) {
                int containerSlot = bestSlot < 9 ? bestSlot + 36 : bestSlot;
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, 0, SlotActionType.QUICK_MOVE, mc.player);
                timer.reset();
                return;
            }
        }
    }

    private void handleOffhand() {
        if (offhandItem.is(OffhandItem.None)) return;
        ItemStack offhand = mc.player.getOffHandStack();
        Item targetItem = switch (offhandItem.get()) {
            case GoldenApple -> Items.GOLDEN_APPLE;
            case Totem -> Items.TOTEM_OF_UNDYING;
            case Shield -> Items.SHIELD;
            default -> null;
        };
        if (targetItem == null) return;
        if (offhand.isOf(targetItem)) return;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(targetItem) || (targetItem == Items.GOLDEN_APPLE && stack.isOf(Items.ENCHANTED_GOLDEN_APPLE))) {
                int containerSlot = i < 9 ? i + 36 : i;
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, 40, SlotActionType.SWAP, mc.player);
                timer.reset();
                return;
            }
        }
    }

    private void sortItem(int targetSlot, Predicate<ItemStack> filter, Function<ItemStack, Float> scorer) {
        ItemStack currentStack = mc.player.getInventory().getStack(targetSlot);
        int bestSlot = -1;
        float bestScore = filter.test(currentStack) ? scorer.apply(currentStack) : -1;
        for (int i = 0; i < 36; i++) {
            if (i == targetSlot) continue;
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!filter.test(stack)) continue;
            float score = scorer.apply(stack);
            if (score > bestScore) {
                bestScore = score;
                bestSlot = i;
            }
        }
        if (bestSlot != -1) {
            int containerSlot = bestSlot < 9 ? bestSlot + 36 : bestSlot;
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, targetSlot, SlotActionType.SWAP, mc.player);
            timer.reset();
        }
    }

    private void sortBlock(int targetSlot) {
        ItemStack currentStack = mc.player.getInventory().getStack(targetSlot);
        int bestSlot = -1;
        int bestCount = isValidBlock(currentStack) ? currentStack.getCount() : -1;
        for (int i = 0; i < 36; i++) {
            if (i == targetSlot) continue;
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!isValidBlock(stack)) continue;
            if (stack.getCount() > bestCount) {
                bestCount = stack.getCount();
                bestSlot = i;
            }
        }
        if (bestSlot != -1) {
            int containerSlot = bestSlot < 9 ? bestSlot + 36 : bestSlot;
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, targetSlot, SlotActionType.SWAP, mc.player);
            timer.reset();
        }
    }

    private void throwUselessItems() {
        List<Integer> slots = new ArrayList<>();
        for (int i = 9; i < 36; i++) slots.add(i);
        Collections.shuffle(slots);
        for (int slot : slots) {
            ItemStack stack = mc.player.getInventory().getStack(slot);
            if (stack.isEmpty()) continue;
            if (isItemUseful(stack)) continue;
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 1, SlotActionType.THROW, mc.player);
            timer.reset();
            return;
        }
    }

    private boolean isItemUseful(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (isArmor(stack)) {
            EquipmentSlot slot = getArmorSlot(stack);
            if (slot == null) return false;
            float score = getArmorScore(stack);
            float best = getBestArmorScore(slot);
            return score >= best;
        }
        if (isSword(stack)) return stack == getBestSword();
        if (stack.getItem() instanceof PickaxeItem) return stack == getBestTool(PickaxeItem.class);
        if (stack.getItem() instanceof AxeItem && !isSharpnessAxe(stack)) return stack == getBestTool(AxeItem.class);
        if (stack.getItem() instanceof ShovelItem) return stack == getBestTool(ShovelItem.class);
        if (isValidBlock(stack)) return getBlockCount() <= maxBlocks.get();
        if (stack.isOf(Items.GOLDEN_APPLE) || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return true;
        if (stack.isOf(Items.ENDER_PEARL)) return true;
        if (stack.isOf(Items.WATER_BUCKET)) return true;
        if (stack.isOf(Items.ARROW) || stack.isOf(Items.SPECTRAL_ARROW) || stack.isOf(Items.TIPPED_ARROW)) return true;
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) return true;
        if (stack.isOf(Items.TOTEM_OF_UNDYING)) return true;
        return false;
    }

    private boolean isArmor(ItemStack stack) {
        return stack.isIn(ItemTags.FOOT_ARMOR) || stack.isIn(ItemTags.LEG_ARMOR)
                || stack.isIn(ItemTags.CHEST_ARMOR) || stack.isIn(ItemTags.HEAD_ARMOR);
    }

    private EquipmentSlot getArmorSlot(ItemStack stack) {
        if (stack.isOf(Items.ELYTRA)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.FOOT_ARMOR)) return EquipmentSlot.FEET;
        if (stack.isIn(ItemTags.LEG_ARMOR)) return EquipmentSlot.LEGS;
        if (stack.isIn(ItemTags.CHEST_ARMOR)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.HEAD_ARMOR)) return EquipmentSlot.HEAD;
        return null;
    }

    private boolean isSword(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || isSharpnessAxe(stack);
    }

    private boolean isSharpnessAxe(ItemStack stack) {
        if (!(stack.getItem() instanceof AxeItem)) return false;
        return InvUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS) > 0;
    }

    private float getSwordScore(ItemStack stack) {
        float damage = 0;
        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (var entry : modifiers.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ATTACK_DAMAGE.value()) {
                    damage += (float) entry.modifier().value();
                }
            }
        }
        damage += InvUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS) * 1.25f;
        return damage;
    }

    private float getToolScore(ItemStack stack) {
        float speed = 1;
        speed += InvUtil.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) * 0.5f;
        return speed;
    }

    private float getArmorScore(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        float score = 0;
        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (var entry : modifiers.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ARMOR.value()) {
                    score += (float) entry.modifier().value();
                }
                if (entry.attribute().value() == EntityAttributes.ARMOR_TOUGHNESS.value()) {
                    score += (float) entry.modifier().value() * 0.5f;
                }
            }
        }
        score += getProtectionLevel(stack) * 0.5f;
        return score;
    }

    private int getProtectionLevel(ItemStack stack) {
        if (!stack.hasEnchantments() || mc.world == null) return 0;
        var registry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        var protectionEntry = registry.getOptional(Enchantments.PROTECTION);
        return protectionEntry.map(entry -> EnchantmentHelper.getLevel(entry, stack)).orElse(0);
    }

    private float getBestArmorScore(EquipmentSlot slot) {
        int armorIndex = switch (slot) {
            case FEET -> 0;
            case LEGS -> 1;
            case CHEST -> 2;
            case HEAD -> 3;
            default -> -1;
        };
        float best = armorIndex >= 0 ? getArmorScore(mc.player.getInventory().getArmorStack(armorIndex)) : 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!isArmor(stack)) continue;
            EquipmentSlot stackSlot = getArmorSlot(stack);
            if (stackSlot != slot) continue;
            float score = getArmorScore(stack);
            if (score > best) best = score;
        }
        return best;
    }

    private ItemStack getBestSword() {
        ItemStack best = null;
        float bestScore = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!isSword(stack)) continue;
            float score = getSwordScore(stack);
            if (score > bestScore) {
                bestScore = score;
                best = stack;
            }
        }
        return best;
    }

    private ItemStack getBestTool(Class<?> toolClass) {
        ItemStack best = null;
        float bestScore = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!toolClass.isInstance(stack.getItem())) continue;
            if (toolClass == AxeItem.class && isSharpnessAxe(stack)) continue;
            float score = getToolScore(stack);
            if (score > bestScore) {
                bestScore = score;
                best = stack;
            }
        }
        return best;
    }

    private boolean isValidBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem)) return false;
        return !(stack.getItem() instanceof BedItem);
    }

    private int getBlockCount() {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (isValidBlock(stack)) count += stack.getCount();
        }
        return count;
    }

    private EquipmentSlot getEquipmentSlot(int armorSlot) {
        return switch (armorSlot) {
            case 0 -> EquipmentSlot.FEET;
            case 1 -> EquipmentSlot.LEGS;
            case 2 -> EquipmentSlot.CHEST;
            case 3 -> EquipmentSlot.HEAD;
            default -> EquipmentSlot.MAINHAND;
        };
    }

    public int getMaxBlocks() {
        return maxBlocks.get();
    }

    private enum OffhandItem {
        None, GoldenApple, Totem, Shield
    }
}