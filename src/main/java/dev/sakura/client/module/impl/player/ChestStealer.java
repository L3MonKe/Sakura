package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
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
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChestStealer extends Module {
    private static final TimerUtil timer = new TimerUtil();
    private static boolean working = false;

    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 3, 1, 10, 1);
    private final BoolValue autoClose = new BoolValue("AutoClose", "自动关闭", true);

    public ChestStealer() {
        super("ChestStealer", "箱子偷取", Category.Player);
    }

    public static boolean isWorking() {
        return working && !timer.delay(5);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!(mc.player.currentScreenHandler instanceof GenericContainerScreenHandler container)) {
            working = false;
            return;
        }
        working = true;
        int rows = container.getRows();
        int slots = rows * 9;
        if (isChestEmpty(container, slots)) {
            if (autoClose.get() && timer.delay(delay.get())) {
                mc.player.closeHandledScreen();
            }
            return;
        }
        List<Integer> slotList = new ArrayList<>();
        for (int i = 0; i < slots; i++) slotList.add(i);
        Collections.shuffle(slotList);
        for (int slot : slotList) {
            ItemStack stack = container.getSlot(slot).getStack();
            if (stack.isEmpty()) continue;
            if (!isItemUseful(stack)) continue;
            if (!isBestInChest(container, slots, stack)) continue;
            if (timer.delay(delay.get())) {
                mc.interactionManager.clickSlot(container.syncId, slot, 0, SlotActionType.QUICK_MOVE, mc.player);
                timer.reset();
                return;
            }
        }
    }

    private boolean isChestEmpty(GenericContainerScreenHandler container, int slots) {
        for (int i = 0; i < slots; i++) {
            ItemStack stack = container.getSlot(i).getStack();
            if (!stack.isEmpty() && isItemUseful(stack) && isBestInChest(container, slots, stack)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBestInChest(GenericContainerScreenHandler container, int slots, ItemStack stack) {
        for (int i = 0; i < slots; i++) {
            ItemStack other = container.getSlot(i).getStack();
            if (other == stack) continue;
            if (isArmor(stack) && isArmor(other)) {
                EquipmentSlot stackSlot = getArmorSlot(stack);
                EquipmentSlot otherSlot = getArmorSlot(other);
                if (stackSlot == otherSlot && stackSlot != null) {
                    if (getArmorScore(other) > getArmorScore(stack)) return false;
                }
            }
            if (stack.getItem() instanceof SwordItem && other.getItem() instanceof SwordItem) {
                if (getSwordScore(other) > getSwordScore(stack)) return false;
            }
            if (stack.getItem() instanceof PickaxeItem && other.getItem() instanceof PickaxeItem) {
                if (getToolScore(other) > getToolScore(stack)) return false;
            }
            if (stack.getItem() instanceof AxeItem && other.getItem() instanceof AxeItem) {
                if (getToolScore(other) > getToolScore(stack)) return false;
            }
            if (stack.getItem() instanceof ShovelItem && other.getItem() instanceof ShovelItem) {
                if (getToolScore(other) > getToolScore(stack)) return false;
            }
        }
        return true;
    }

    private boolean isItemUseful(ItemStack stack) {
        if (stack.isEmpty()) return false;
        InventoryManager invManager = Sakura.MODULES.getModule(InventoryManager.class);
        int maxBlocks = invManager != null ? invManager.getMaxBlocks() : 256;
        if (isArmor(stack)) {
            EquipmentSlot slot = getArmorSlot(stack);
            if (slot == null) return false;
            return getArmorScore(stack) > getBestArmorScore(slot);
        }
        if (stack.getItem() instanceof SwordItem) {
            return getSwordScore(stack) > getBestSwordScore();
        }
        if (stack.getItem() instanceof PickaxeItem) {
            return getToolScore(stack) > getBestToolScore(PickaxeItem.class);
        }
        if (stack.getItem() instanceof AxeItem) {
            return getToolScore(stack) > getBestToolScore(AxeItem.class);
        }
        if (stack.getItem() instanceof ShovelItem) {
            return getToolScore(stack) > getBestToolScore(ShovelItem.class);
        }
        if (stack.getItem() instanceof BlockItem && !(stack.getItem() instanceof BedItem)) {
            return getBlockCount() + stack.getCount() <= maxBlocks;
        }
        if (stack.isOf(Items.GOLDEN_APPLE) || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return true;
        if (stack.isOf(Items.ENDER_PEARL)) return true;
        if (stack.isOf(Items.WATER_BUCKET)) return !hasItem(Items.WATER_BUCKET);
        if (stack.isOf(Items.ARROW)) return true;
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

    private float getSwordScore(ItemStack stack) {
        if (!(stack.getItem() instanceof SwordItem)) return 0;
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

    private float getBestSwordScore() {
        float best = 0;
        for (int i = 0; i < 36; i++) {
            float score = getSwordScore(mc.player.getInventory().getStack(i));
            if (score > best) best = score;
        }
        return best;
    }

    private float getToolScore(ItemStack stack) {
        float speed = 1;
        speed += InvUtil.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) * 0.5f;
        return speed;
    }

    private float getBestToolScore(Class<?> toolClass) {
        float best = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!toolClass.isInstance(stack.getItem())) continue;
            float score = getToolScore(stack);
            if (score > best) best = score;
        }
        return best;
    }

    private int getBlockCount() {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem && !(stack.getItem() instanceof BedItem)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private boolean hasItem(Item item) {
        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getStack(i).isOf(item)) return true;
        }
        return false;
    }
}