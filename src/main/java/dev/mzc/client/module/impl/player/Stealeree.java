package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.time.TimerUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class Stealeree extends Module {
    public Stealeree() {
        super("ChestStealer", "箱子小偷", Category.Player);
        this.setType(ModuleType.Safe);
    }

    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 90, 0, 500, 1);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 140, 0, 800, 1);
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
            int min = Math.min(minDelay.get(), maxDelay.get());
            int max = Math.max(minDelay.get(), maxDelay.get());
            int targetDelay = min + (int) (Math.random() * Math.max(1, (max - min)));
            if (!timer.passedMS(targetDelay)) return;
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
