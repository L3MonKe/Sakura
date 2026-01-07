package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class AutoSnowball extends Module {

    private final NumberValue<Double> throwsPerSecond = new NumberValue<>("ThrowsPerSec", "每秒发射", 5.0, 1.0, 20.0, 0.5);

    private final TimerUtil throwTimer = new TimerUtil();
    private final TimerUtil swapTimer = new TimerUtil();

    public AutoSnowball() {
        super("AutoSnowball", "自动雪球", Category.Combat);
    }

    @Override
    public void onEnable() {
        throwTimer.reset();
        swapTimer.reset();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled() || killAura.getCurrentTarget() == null) return;

        ItemStack offhand = mc.player.getOffHandStack();

        if (!offhand.isOf(Items.SNOWBALL)) {
            int snowballSlot = getSnowballSlot();
            if (snowballSlot == -1) return;

            if (!(mc.currentScreen instanceof InventoryScreen)) {
                mc.setScreen(new InventoryScreen(mc.player));
                return;
            }

            if (swapTimer.delay(2)) {
                int containerSlot = snowballSlot < 9 ? snowballSlot + 36 : snowballSlot;
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, 40, SlotActionType.SWAP, mc.player);
                swapTimer.reset();
            }
            return;
        }

        if (mc.currentScreen instanceof InventoryScreen) {
            mc.player.closeHandledScreen();
        }

        double delayTicks = 20.0 / throwsPerSecond.get();
        if (!throwTimer.delay((float) delayTicks)) return;

        mc.interactionManager.interactItem(mc.player, Hand.OFF_HAND);
        throwTimer.reset();
    }

    private int getSnowballSlot() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(Items.SNOWBALL)) return i;
        }
        return -1;
    }
}