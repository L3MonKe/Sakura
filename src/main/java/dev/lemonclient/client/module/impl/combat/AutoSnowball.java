package dev.lemonclient.client.module.impl.combat;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.events.client.TickEvent;
import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;
import dev.lemonclient.client.utils.time.TimerUtil;
import dev.lemonclient.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class AutoSnowball extends Module {
    public AutoSnowball() {
        super("AutoSnowball", "自动雪球", Category.Combat);
    }

    private final NumberValue<Double> throwsPerSecond = new NumberValue<>("Throws Per Sec", "每秒发射", 5.0, 1.0, 20.0, 0.5);

    private final TimerUtil throwTimer = new TimerUtil();
    private final TimerUtil swapTimer = new TimerUtil();

    @Override
    public void onEnable() {
        throwTimer.reset();
        swapTimer.reset();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        KillAura killAura = LemonClient.MODULES.getModule(KillAura.class);
        if (!killAura.isEnabled() || killAura.getCurrentTarget() == null) return;

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