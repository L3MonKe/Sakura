package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.player.EnchantmentUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stealer extends Module {
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 2, 0, 10, 1);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 6, 0, 10, 1);
    private final BoolValue smart = new BoolValue("Smart", "智能筛选", true);
    private final BoolValue enderChest = new BoolValue("Ender Chest", "末影箱", false);
    private final BoolValue closeWhenDone = new BoolValue("Auto Close", "拿完关闭", true);
    private final BoolValue takeBlocks = new BoolValue("Blocks", "方块", true);
    private final BoolValue takeFood = new BoolValue("Food", "食物", true);
    private final BoolValue takePotions = new BoolValue("Potions", "药水", true);
    private final BoolValue takeProjectiles = new BoolValue("Projectiles", "投掷物", true);
    private final BoolValue takeUtilities = new BoolValue("Utilities", "功能物品", true);

    private final TimerUtil timer = new TimerUtil();
    private int nextDelayTicks = 0;
    private Screen lastScreen;

    public Stealer() {
        super("Stealer", "箱子小偷", Category.Player);
    }

    @Override
    protected void onEnable() {
        timer.reset();
        nextDelayTicks = getRandomDelayTicks();
        lastScreen = null;
    }

    @Override
    protected void onDisable() {
        lastScreen = null;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        Screen currentScreen = mc.currentScreen;
        if (!(currentScreen instanceof GenericContainerScreen containerScreen)) {
            lastScreen = currentScreen;
            return;
        }

        GenericContainerScreenHandler handler = containerScreen.getScreenHandler();
        Inventory inventory = handler.getInventory();

        if (!isChestScreen(containerScreen)) {
            lastScreen = currentScreen;
            return;
        }

        if (currentScreen != lastScreen) {
            resetDelay();
            lastScreen = currentScreen;
        }

        if (shouldClose(inventory)) {
            if (timer.delay(nextDelayTicks)) {
                mc.player.closeHandledScreen();
                resetDelay();
            }
            return;
        }

        if (!timer.delay(nextDelayTicks)) return;

        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            slots.add(i);
        }
        Collections.shuffle(slots);

        for (int slot : slots) {
            ItemStack stack = inventory.getStack(slot);
            if (stack.isEmpty()) continue;

            if (shouldTake(stack, inventory)) {
                mc.interactionManager.clickSlot(handler.syncId, slot, 0, SlotActionType.QUICK_MOVE, mc.player);
                resetDelay();
                break;
            }
        }
    }

    private boolean shouldClose(Inventory inventory) {
        if (!closeWhenDone.get()) return false;
        if (inventory.isEmpty()) return true;
        if (!smart.get()) return false;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (shouldTake(stack, inventory)) return false;
        }
        return true;
    }

    private boolean shouldTake(ItemStack stack, Inventory inventory) {
        if (stack.isEmpty()) return false;
        if (!smart.get()) return true;

        if (isArmor(stack)) {
            EquipmentSlot slot = getArmorSlot(stack);
            if (slot == null) return false;
            ItemStack best = getBestArmorInChest(inventory, slot);
            if (stack != best) return false;
            return getProtection(stack) > getProtection(mc.player.getEquippedStack(slot));
        }

        if (stack.getItem() instanceof SwordItem) {
            ItemStack bestInChest = getBestSwordInChest(inventory);
            if (stack != bestInChest) return false;
            return getAttackDamage(stack) > getBestSwordDamageInInventory();
        }

        if (stack.getItem() instanceof AxeItem) {
            ItemStack bestInChest = getBestAxeInChest(inventory);
            if (stack != bestInChest) return false;
            return getAttackDamage(stack) > getBestAxeDamageInInventory();
        }

        if (stack.getItem() instanceof PickaxeItem) {
            ItemStack bestInChest = getBestToolInChest(inventory, PickaxeItem.class);
            if (stack != bestInChest) return false;
            return getMiningSpeed(stack, ToolType.PICKAXE) > getBestToolSpeedInInventory(ToolType.PICKAXE);
        }

        if (stack.getItem() instanceof ShovelItem) {
            ItemStack bestInChest = getBestToolInChest(inventory, ShovelItem.class);
            if (stack != bestInChest) return false;
            return getMiningSpeed(stack, ToolType.SHOVEL) > getBestToolSpeedInInventory(ToolType.SHOVEL);
        }

        return isUsefulMisc(stack);
    }

    private boolean isUsefulMisc(ItemStack stack) {
        if (takeBlocks.get() && stack.getItem() instanceof BlockItem) return true;
        if (takeFood.get() && stack.getComponents().contains(DataComponentTypes.FOOD)) return true;
        if (takePotions.get() && (stack.getItem() instanceof PotionItem
                || stack.getItem() instanceof SplashPotionItem
                || stack.getItem() instanceof LingeringPotionItem)) return true;
        if (takeProjectiles.get() && (stack.isIn(ItemTags.ARROWS)
                || stack.isOf(Items.SNOWBALL)
                || stack.isOf(Items.EGG))) return true;
        if (takeUtilities.get() && (stack.isOf(Items.ENDER_PEARL)
                || stack.isOf(Items.TOTEM_OF_UNDYING)
                || stack.isOf(Items.GOLDEN_APPLE)
                || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || stack.isOf(Items.WATER_BUCKET)
                || stack.isOf(Items.LAVA_BUCKET)
                || stack.isOf(Items.SHIELD)
                || stack.isOf(Items.FISHING_ROD)
                || stack.isOf(Items.FLINT_AND_STEEL)
                || stack.isOf(Items.SHEARS)
                || stack.isOf(Items.BOW)
                || stack.isOf(Items.CROSSBOW)
                || stack.isOf(Items.TRIDENT))) return true;
        return false;
    }

    private boolean isChestScreen(GenericContainerScreen screen) {
        String title = screen.getTitle().getString().toLowerCase();
        String chest = Text.translatable("container.chest").getString().toLowerCase();
        String largeChest = Text.translatable("container.chestDouble").getString().toLowerCase();
        String ender = Text.translatable("container.enderchest").getString().toLowerCase();
        if (title.contains(chest) || title.contains(largeChest) || title.contains("chest")) return true;
        return enderChest.get() && title.contains(ender);
    }

    private void resetDelay() {
        timer.reset();
        nextDelayTicks = getRandomDelayTicks();
    }

    private int getRandomDelayTicks() {
        int min = minDelay.get();
        int max = maxDelay.get();
        if (max < min) {
            int temp = min;
            min = max;
            max = temp;
        }
        if (max == min) return min;
        return MathUtil.getRandom(min, max + 1);
    }

    private boolean isArmor(ItemStack stack) {
        return stack.isIn(ItemTags.FOOT_ARMOR) || stack.isIn(ItemTags.LEG_ARMOR)
                || stack.isIn(ItemTags.CHEST_ARMOR) || stack.isIn(ItemTags.HEAD_ARMOR) || stack.isOf(Items.ELYTRA);
    }

    private EquipmentSlot getArmorSlot(ItemStack stack) {
        if (stack.isOf(Items.ELYTRA)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.FOOT_ARMOR)) return EquipmentSlot.FEET;
        if (stack.isIn(ItemTags.LEG_ARMOR)) return EquipmentSlot.LEGS;
        if (stack.isIn(ItemTags.CHEST_ARMOR)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.HEAD_ARMOR)) return EquipmentSlot.HEAD;
        return null;
    }

    private int getProtection(ItemStack stack) {
        if (stack.isEmpty()) return -1;
        if (!isArmor(stack)) return 0;
        int prot = 0;
        AttributeModifiersComponent attrComp = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (attrComp != null) {
            for (var entry : attrComp.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ARMOR.value()) {
                    prot += (int) entry.modifier().value();
                }
            }
        }
        int enchantProt = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.PROTECTION);
        prot += Math.max(0, enchantProt);
        return prot;
    }

    private ItemStack getBestArmorInChest(Inventory inventory, EquipmentSlot slot) {
        ItemStack best = ItemStack.EMPTY;
        int bestProt = -1;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !isArmor(stack)) continue;
            EquipmentSlot stackSlot = getArmorSlot(stack);
            if (stackSlot != slot) continue;
            int prot = getProtection(stack);
            if (prot > bestProt) {
                bestProt = prot;
                best = stack;
            }
        }
        return best;
    }

    private ItemStack getBestSwordInChest(Inventory inventory) {
        ItemStack best = ItemStack.EMPTY;
        double bestDamage = -1;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof SwordItem)) continue;
            double damage = getAttackDamage(stack);
            if (damage > bestDamage) {
                bestDamage = damage;
                best = stack;
            }
        }
        return best;
    }

    private ItemStack getBestAxeInChest(Inventory inventory) {
        ItemStack best = ItemStack.EMPTY;
        double bestDamage = -1;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof AxeItem)) continue;
            double damage = getAttackDamage(stack);
            if (damage > bestDamage) {
                bestDamage = damage;
                best = stack;
            }
        }
        return best;
    }

    private ItemStack getBestToolInChest(Inventory inventory, Class<?> toolClass) {
        ItemStack best = ItemStack.EMPTY;
        double bestSpeed = -1;
        ToolType type = toolClass == PickaxeItem.class ? ToolType.PICKAXE : ToolType.SHOVEL;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !toolClass.isInstance(stack.getItem())) continue;
            double speed = getMiningSpeed(stack, type);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                best = stack;
            }
        }
        return best;
    }

    private double getBestSwordDamageInInventory() {
        double best = -1;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof SwordItem)) continue;
            best = Math.max(best, getAttackDamage(stack));
        }
        return best;
    }

    private double getBestAxeDamageInInventory() {
        double best = -1;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof AxeItem)) continue;
            best = Math.max(best, getAttackDamage(stack));
        }
        return best;
    }

    private double getBestToolSpeedInInventory(ToolType type) {
        double best = -1;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;
            if (type == ToolType.PICKAXE && !(stack.getItem() instanceof PickaxeItem)) continue;
            if (type == ToolType.SHOVEL && !(stack.getItem() instanceof ShovelItem)) continue;
            best = Math.max(best, getMiningSpeed(stack, type));
        }
        return best;
    }

    private double getAttackDamage(ItemStack stack) {
        double damage = 0;
        AttributeModifiersComponent attrComp = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (attrComp != null) {
            for (var entry : attrComp.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ATTACK_DAMAGE.value()) {
                    damage += entry.modifier().value();
                }
            }
        }
        int sharpness = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
        if (sharpness > 0) {
            damage += 0.5 + (sharpness * 0.5);
        }
        return damage;
    }

    private double getMiningSpeed(ItemStack stack, ToolType type) {
        return switch (type) {
            case PICKAXE -> stack.getMiningSpeedMultiplier(Blocks.STONE.getDefaultState());
            case SHOVEL -> stack.getMiningSpeedMultiplier(Blocks.DIRT.getDefaultState());
        };
    }

    private enum ToolType {
        PICKAXE,
        SHOVEL
    }
}
