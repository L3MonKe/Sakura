package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;

public class MiddleClickPearl extends Module {
    public MiddleClickPearl() {
        super("MiddleClickPearl", "中键珍珠", Category.Player);
    }
    private final TimerUtil swapTimer = new TimerUtil();
    boolean click = false;

    private final BoolValue swapBack = new BoolValue("SwapBack", "切换回原槽位", true);
    private final NumberValue<Integer> delay = new NumberValue<>("SwapBackDelay", "切换延迟", 200, 0, 500, 10);

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.mouse.wasMiddleButtonClicked()) {
            if (!click) {
                boolean shouldSwapBack = swapBack.get();
                int pearl = InvUtil.findInHotbar(Items.ENDER_PEARL).slot();
                if (pearl == -1) return;
                InvUtil.swap(pearl, shouldSwapBack);
                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), mc.player.getYaw(), mc.player.getPitch()));
                if (shouldSwapBack) {
                    if (swapTimer.passedMS(delay.get())) {
                        InvUtil.swapBack();
                        swapTimer.reset();
                    }
                }
                click = true;
            }
        } else {
            click = false;
        }
    }
}
