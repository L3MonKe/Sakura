package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.time.TimerUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.utils.player.MovementUtil;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class Replenish extends Module {
    public enum Mode {
        InvOpen("背包打开时"),
        NoMove("非移动时"),
        Always("保持开启");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Always);
    private final NumberValue<Double> delay = new NumberValue<>("Delay", "延迟", 2.0, 0.0, 5.0, 0.01);
    private final NumberValue<Integer> min = new NumberValue<>("Min", "最小数量", 50, 1, 64, 1);
    private final NumberValue<Integer> nonStackableDurabilityMin = new NumberValue<>("Durability", "耐久下限", 10, 1, 100, 1);
    private final BoolValue nonStackableReplenish = new BoolValue("NonStackable", "不可堆叠补货", true);

    private final TimerUtil timer = new TimerUtil();
    private final ItemStack[] hotbarCache = new ItemStack[9];
    private boolean wasInScreen = false;

    public Replenish() {
        super("Replenish", "自动补充", Category.Player);
        this.setType(ModuleType.Safe);
        for (int i = 0; i < 9; i++) {
            hotbarCache[i] = ItemStack.EMPTY;
        }
    }

    @Override
    public void onEnable() {
        for (int i = 0; i < 9; i++) {
            hotbarCache[i] = ItemStack.EMPTY;
        }
        wasInScreen = false;
    }

    @Override
    public void onDisable() {
        for (int i = 0; i < 9; i++) {
            hotbarCache[i] = ItemStack.EMPTY;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // Mode Check
        if (mode.is(Mode.InvOpen)) {
            // Must be in InventoryScreen
            if (!(mc.currentScreen instanceof InventoryScreen)) {
                // If we just left inventory, we might want to update cache?
                // But wasInScreen logic below handles exiting screens.
                // If we are NOT in inventory, we just return.
                if (wasInScreen) {
                    for (int i = 0; i < 9; i++) {
                        hotbarCache[i] = mc.player.getInventory().getStack(i).copy();
                    }
                    wasInScreen = false;
                }
                return;
            }
        } else {
            // Always or NoMove: Must NOT be in HandledScreen (unless it's null, which is fine)
            // Existing logic:
            if (mc.currentScreen instanceof net.minecraft.client.gui.screen.ingame.HandledScreen) {
                wasInScreen = true;
                return;
            }
            
            // NoMove specific check
            if (mode.is(Mode.NoMove) && MovementUtil.isMoving()) {
                return;
            }
        }

        if (wasInScreen) {
            for (int i = 0; i < 9; i++) {
                hotbarCache[i] = mc.player.getInventory().getStack(i).copy();
            }
            wasInScreen = false;
        }

        // Update cache
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                hotbarCache[i] = stack.copy();
            }
        }

        for (int i = 0; i < 9; i++) {
            if (replenish(i)) {
                timer.reset();
            }
        }
    }

    private boolean replenish(int slot) {
        ItemStack stack = mc.player.getInventory().getStack(slot);

        if (stack.isEmpty()) {
            if (nonStackableReplenish.get()) {
                return tryRefillFromCache(slot);
            }
            return false;
        }
        if (!stack.isStackable()) {
            if (nonStackableReplenish.get()) {
                return replenishNonStackable(slot, stack);
            }
            return false;
        }
        if (stack.getCount() > min.get()) return false;
        if (stack.getCount() == stack.getMaxCount()) return false;

        for (int i = 9; i < 36; ++i) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item.isEmpty() || !canMerge(stack, item)) continue;
            if (!timer.passedSecond(delay.get())) {
                return false;
            }
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
            return true;
        }
        return false;
    }

    private boolean tryRefillFromCache(int hotbarSlot) {
        ItemStack cached = hotbarCache[hotbarSlot];
        if (cached.isEmpty()) return false;
        if (cached.isStackable()) return false;
        
        // Prevent refilling totems if cache has totem
        if (cached.isOf(Items.TOTEM_OF_UNDYING)) return false;

        if (!timer.passedSecond(delay.get())) return false;
        int bestSlot = -1;
        for (int i = 9; i < 36; ++i) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item.isEmpty()) continue;
            if (canMerge(cached, item)) {
                bestSlot = i;
                break;
            }
        }
        if (bestSlot == -1) return false;
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestSlot, hotbarSlot, SlotActionType.SWAP, mc.player);
        return true;
    }

    private boolean tryFillPotion(int hotbarSlot) {
        // Deprecated, logic moved to tryRefillFromCache
        return false;
    }

    private boolean replenishNonStackable(int hotbarSlot, ItemStack stack) {
        if (!stack.isDamageable()) return false;

        int max = stack.getMaxDamage();
        if (max <= 0) return false;
        int remaining = max - stack.getDamage();
        int remainingPct = Math.max(0, (int) (remaining * 100.0 / max));
        if (remainingPct > nonStackableDurabilityMin.get()) return false;

        int bestSlot = -1;
        int bestRemainingPct = -1;
        for (int i = 9; i < 36; ++i) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item.isEmpty()) continue;
            if (!canMerge(stack, item)) continue;
            if (!item.isDamageable()) continue;
            int imax = item.getMaxDamage();
            if (imax <= 0) continue;
            int iremaining = imax - item.getDamage();
            int ipct = Math.max(0, (int) (iremaining * 100.0 / imax));
            if (ipct <= remainingPct) continue;
            if (ipct > bestRemainingPct) {
                bestRemainingPct = ipct;
                bestSlot = i;
            }
        }

        if (bestSlot == -1) return false;
        if (!timer.passedSecond(delay.get())) return false;
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestSlot, hotbarSlot, SlotActionType.SWAP, mc.player);
        return true;
    }

    private boolean canMerge(ItemStack source, ItemStack stack) {
        return source.getItem() == stack.getItem() && source.getName().getString().equals(stack.getName().getString());
    }
}
