package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealer extends Module {
    public ChestStealer() {
        super("ChestStealer", "箱子小偷", Category.Player);
    }

    private final NumberValue<Integer> delay = new NumberValue<>("Steal Delay", "偷取延迟", 0, 0, 250, 1);
    public final BoolValue onlyImportant = new BoolValue("Important Only", "仅重要", true);

    private final TimerUtil timer = new TimerUtil();

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!(mc.player.currentScreenHandler instanceof GenericContainerScreenHandler inventory)) return;

        if (InvUtil.isInventoryFull()) return;

        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (inventory.getSlot(i).getStack().isEmpty()) {
                if (i == inventory.getInventory().size() - 1) {
                    mc.player.closeHandledScreen();
                }
                continue;
            }
            if (!timer.passedMS(delay.get())) return;
            if (isAllowed(inventory.getSlot(i).getStack())) {
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                timer.reset();
            }
        }
    }

    private boolean isAllowed(ItemStack stack) {
        if (onlyImportant.get()) {
            Item item = stack.getItem();
            return item instanceof SwordItem ||
                    item instanceof ArmorItem ||
                    item instanceof BowItem ||
                    item instanceof CrossbowItem ||
                    item instanceof FishingRodItem ||
                    item instanceof EnderPearlItem ||
                    item instanceof AxeItem ||
                    item instanceof PickaxeItem ||
                    item instanceof ShovelItem ||
                    item == Items.TNT ||
                    item instanceof PotionItem ||
                    item.getComponents().contains(DataComponentTypes.FOOD) ||
                    item == Items.WATER_BUCKET ||
                    item == Items.TOTEM_OF_UNDYING ||
                    item == Items.END_CRYSTAL ||
                    (item instanceof BlockItem && InvUtil.isBlockPlaceable(stack));
        } else {
            return true;
        }
    }
}
