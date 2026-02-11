package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;

public class MCP extends Module {
    public MCP() {
        super("MCP", "中键珍珠", Category.Player);
    }

    private final TimerUtil swapTimer = new TimerUtil();
    boolean click = false;

    private final BoolValue swapBack = new BoolValue("Swap Back", "切换回原槽位", true);
    private final NumberValue<Integer> delay = new NumberValue<>("Swap Back Delay", "切换延迟", 200, 0, 500, 10);

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
                    if (swapTimer.passedMillise(delay.get())) {
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
