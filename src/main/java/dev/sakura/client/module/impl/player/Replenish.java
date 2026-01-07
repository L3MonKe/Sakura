package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class Replenish extends Module {
    private final NumberValue<Double> delay = new NumberValue<>("Delay", "延迟", 2.0, 0.0, 5.0, 0.01);
    private final NumberValue<Integer> min = new NumberValue<>("Min", "最小数量", 50, 1, 64, 1);
    private final NumberValue<Double> forceDelay = new NumberValue<>("ForceDelay", "强补延迟", 0.2, 0.0, 4.0, 0.01);
    private final NumberValue<Integer> forceMin = new NumberValue<>("ForceMin", "强补最小数量", 16, 1, 64, 1);

    private final TimerUtil timer = new TimerUtil();

    public Replenish() {
        super("Replenish", "自动补充", Category.Player);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        for (int i = 0; i < 9; ++i) {
            if (replenish(i)) {
                timer.reset();
                return;
            }
        }
    }

    private boolean replenish(int slot) {
        ItemStack stack = mc.player.getInventory().getStack(slot);

        if (stack.isEmpty()) return false;
        if (!stack.isStackable()) return false;
        if (stack.getCount() > min.get()) return false;
        if (stack.getCount() == stack.getMaxCount()) return false;

        for (int i = 9; i < 36; ++i) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item.isEmpty() || !canMerge(stack, item)) continue;
            if (stack.getCount() > forceMin.get()) {
                if (!timer.passedSecond(delay.get())) {
                    return false;
                }
            } else {
                if (!timer.passedSecond(forceDelay.get())) {
                    return false;
                }
            }
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
            return true;
        }
        return false;
    }

    private boolean canMerge(ItemStack source, ItemStack stack) {
        return source.getItem() == stack.getItem() && source.getName().equals(stack.getName());
    }
}
