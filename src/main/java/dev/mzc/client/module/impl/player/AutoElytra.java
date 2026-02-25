package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.player.SlotUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributes;

public class AutoElytra extends Module {

    private final BoolValue autoSwap = new BoolValue("AutoSwap", "自动换回胸甲", true);
    
    private boolean jumpWasPressed = false;
    private int timer = 0;

    public AutoElytra() {
        super("AutoElytra", "自动换鞘翅", Category.Player);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        timer = 0;
        jumpWasPressed = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 仅在没有打开其他容器（如箱子）时运行
        if (mc.player.playerScreenHandler != mc.player.currentScreenHandler) return;

        if (timer > 0) {
            timer--;
            return;
        }

        ItemStack chestStack = mc.player.getInventory().getArmorStack(2);
        boolean jumpPressed = mc.options.jumpKey.isPressed();

        // 落地自动换回胸甲逻辑 (高优先级)
        if (autoSwap.get() && mc.player.isOnGround() && chestStack.getItem() == Items.ELYTRA) {
            int chestplateSlot = findBestChestplate();
            if (chestplateSlot != -1) {
                swapWithChest(chestplateSlot);
                timer = 5; // 落地换装给 5 tick 延迟
                jumpWasPressed = jumpPressed;
                return;
            }
        }

        // 检测换装逻辑：仅在刚按下空格的一瞬间，或者在空中且满足特定条件时
        if (jumpPressed && !jumpWasPressed) {
            if (chestStack.getItem() != Items.ELYTRA) {
                // 情况 A: 没穿鞘翅 -> 尝试换上鞘翅起飞
                // 必须在空中且非爬墙/游泳
                if (!mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater()) {
                    int elytraSlot = findItem(Items.ELYTRA);
                    if (elytraSlot != -1) {
                        swapWithChest(elytraSlot);
                        timer = 3;
                        jumpWasPressed = true;
                        return;
                    }
                }
            } else if (autoSwap.get()) {
                // 情况 B: 穿着鞘翅 -> 尝试换回胸甲
                // 仅在空中手动再次按下空格时换回
                if (!mc.player.isOnGround()) {
                    int chestplateSlot = findBestChestplate();
                    if (chestplateSlot != -1) {
                        swapWithChest(chestplateSlot);
                        timer = 5;
                        jumpWasPressed = true;
                        return;
                    }
                }
            }
        }

        jumpWasPressed = jumpPressed;
    }

    private void swapWithChest(int slot) {
        // 盔甲槽 ID 在生存模式下，胸甲是 6
        int chestSlotId = 6;
        // 物品栏槽位转换
        int slotId = SlotUtil.indexToId(slot);
        
        if (slotId != -1) {
            // 使用 PICKUP 交换
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slotId, 0, SlotActionType.PICKUP, mc.player);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, chestSlotId, 0, SlotActionType.PICKUP, mc.player);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slotId, 0, SlotActionType.PICKUP, mc.player);
        }
    }

    private int findItem(net.minecraft.item.Item item) {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == item) return i;
        }
        return -1;
    }

    private int findBestChestplate() {
        int bestSlot = -1;
        int maxProtection = -1;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isIn(net.minecraft.registry.tag.ItemTags.CHEST_ARMOR) && stack.getItem() != Items.ELYTRA) {
                int protection = getProtectionValue(stack);
                if (protection > maxProtection) {
                    maxProtection = protection;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }

    private int getProtectionValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        int prot = 0;

        AttributeModifiersComponent attrComp = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (attrComp != null) {
            for (var entry : attrComp.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ARMOR.value()) {
                    prot += (int) entry.modifier().value();
                }
            }
        }
        return prot;
    }
}
