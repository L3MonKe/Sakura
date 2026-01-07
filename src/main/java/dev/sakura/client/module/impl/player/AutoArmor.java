package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.ElytraFly;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;

import java.util.HashMap;
import java.util.Map;

public class AutoArmor extends Module {
    public static AutoArmor INSTANCE;

    private final BoolValue noMove = new BoolValue("NoMove", "移动时不换", false);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 3, 0, 10, 1);
    private final BoolValue autoElytra = new BoolValue("AutoElytra", "自动鞘翅", true);
    private final BoolValue snowBug = new BoolValue("SnowBug", "雪球漏洞", true);

    private int tickDelay = 0;

    public AutoArmor() {
        super("AutoArmor", "自动穿甲", Category.Player);
        INSTANCE = this;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.currentScreen != null
                && !(mc.currentScreen instanceof ChatScreen)
                && !(mc.currentScreen instanceof InventoryScreen)
                && !(mc.currentScreen instanceof ClickGuiScreen)) {
            return;
        }

        if (mc.player.playerScreenHandler != mc.player.currentScreenHandler) return;

        if (MovementUtil.isMoving() && noMove.get()) return;

        if (tickDelay > 0) {
            tickDelay--;
            return;
        }

        tickDelay = delay.get();

        Map<EquipmentSlot, int[]> armorMap = new HashMap<>(4);
        armorMap.put(EquipmentSlot.FEET, new int[]{36, getProtection(mc.player.getInventory().getStack(36)), -1, -1});
        armorMap.put(EquipmentSlot.LEGS, new int[]{37, getProtection(mc.player.getInventory().getStack(37)), -1, -1});
        armorMap.put(EquipmentSlot.CHEST, new int[]{38, getProtection(mc.player.getInventory().getStack(38)), -1, -1});
        armorMap.put(EquipmentSlot.HEAD, new int[]{39, getProtection(mc.player.getInventory().getStack(39)), -1, -1});

        for (int s = 0; s < 36; s++) {
            ItemStack stack = mc.player.getInventory().getStack(s);
            if (stack.isEmpty()) continue;
            if (!isArmor(stack) && !stack.isOf(Items.ELYTRA)) continue;

            int protection = getProtection(stack);
            EquipmentSlot slot = getArmorSlot(stack);
            if (slot == null) continue;

            for (Map.Entry<EquipmentSlot, int[]> entry : armorMap.entrySet()) {
                if (entry.getKey() == EquipmentSlot.FEET && snowBug.get() && mc.player.hurtTime > 1) {
                    ItemStack feetStack = mc.player.getInventory().getStack(36);
                    if (!feetStack.isEmpty() && feetStack.isOf(Items.LEATHER_BOOTS)) {
                        continue;
                    }
                    if (stack.isOf(Items.LEATHER_BOOTS)) {
                        entry.getValue()[2] = s;
                        continue;
                    }
                }

                if (autoElytra.get() && entry.getKey() == EquipmentSlot.CHEST
                        && ElytraFly.INSTANCE != null && ElytraFly.INSTANCE.isEnabled()) {

                    ItemStack chestStack = mc.player.getInventory().getStack(38);
                    if (!chestStack.isEmpty() && chestStack.isOf(Items.ELYTRA) && isElytraUsable(chestStack)) {
                        continue;
                    }
                    if (entry.getValue()[2] != -1) {
                        ItemStack foundStack = mc.player.getInventory().getStack(entry.getValue()[2]);
                        if (!foundStack.isEmpty() && foundStack.isOf(Items.ELYTRA) && isElytraUsable(foundStack)) {
                            continue;
                        }
                    }
                    if (stack.isOf(Items.ELYTRA) && isElytraUsable(stack)) {
                        entry.getValue()[2] = s;
                        continue;
                    }
                }

                if (protection > 0 && entry.getKey() == slot) {
                    if (protection > entry.getValue()[1] && protection > entry.getValue()[3]) {
                        entry.getValue()[2] = s;
                        entry.getValue()[3] = protection;
                    }
                }
            }
        }

        for (Map.Entry<EquipmentSlot, int[]> entry : armorMap.entrySet()) {
            int[] values = entry.getValue();
            if (values[2] != -1) {
                if (values[1] == -1 && values[2] < 9) {
                    mc.interactionManager.clickSlot(
                            mc.player.currentScreenHandler.syncId,
                            36 + values[2], 1, SlotActionType.QUICK_MOVE, mc.player
                    );
                    syncInventory();
                } else if (mc.player.playerScreenHandler == mc.player.currentScreenHandler) {
                    int armorSlot = (values[0] - 34) + (39 - values[0]) * 2;
                    int newArmorSlot = values[2] < 9 ? 36 + values[2] : values[2];

                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, newArmorSlot, 0, SlotActionType.PICKUP, mc.player);
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, armorSlot, 0, SlotActionType.PICKUP, mc.player);
                    if (values[1] != -1) {
                        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, newArmorSlot, 0, SlotActionType.PICKUP, mc.player);
                    }
                    syncInventory();
                }
                return;
            }
        }
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

    private boolean isElytraUsable(ItemStack stack) {
        if (!stack.isOf(Items.ELYTRA)) return false;
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    private int getProtection(ItemStack stack) {
        if (stack.isEmpty()) return -1;

        if (stack.isOf(Items.ELYTRA)) {
            if (!isElytraUsable(stack)) return 0;
            return 1;
        }

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

        if (stack.hasEnchantments() && mc.world != null) {
            var registry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            var protectionEntry = registry.getOptional(Enchantments.PROTECTION);
            if (protectionEntry.isPresent()) {
                prot += EnchantmentHelper.getLevel(protectionEntry.get(), stack);
            }
        }

        return prot;
    }

    private void syncInventory() {
        if (mc.player != null && mc.getNetworkHandler() != null) {
            mc.player.getInventory().updateItems();
        }
    }
}