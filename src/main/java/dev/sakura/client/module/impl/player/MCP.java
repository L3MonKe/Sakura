package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;

public class MCP extends Module {
    public MCP() {
        super("MCP", "中键珍珠", Category.Player);
    }

    private final BoolValue swingHand = new BoolValue("Swing Hand", "挥手", true);
    private final BoolValue swapBack = new BoolValue("Swap Back", "切换回原槽位", true);
    private final NumberValue<Integer> delay = new NumberValue<>("Swap Back Delay", "切换延迟", 200, 0, 500, 10);

    private boolean pendingSwapBack = false;
    private final TimerUtil swapTimer = new TimerUtil();

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.currentScreen != null) return;

        if (pendingSwapBack && swapTimer.passedMillise(delay.get())) {
            InvUtil.swapBack();
            pendingSwapBack = false;
        }

        if (mc.mouse.wasMiddleButtonClicked()) {
            if (mc.player.getItemCooldownManager().isCoolingDown(Items.ENDER_PEARL.getDefaultStack())) {
                return;
            }

            boolean shouldSwapBack = swapBack.get();
            FindItemResult result = InvUtil.findInHotbar(Items.ENDER_PEARL);
            if (!result.found()) return;

            boolean swapped = InvUtil.swap(result.slot(), shouldSwapBack);
            Hand hand = result.getHand();

            mc.interactionManager.interactItem(mc.player, hand);

            if (swingHand.get()) {
                mc.player.swingHand(hand);
            } else {
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
            }

            if (shouldSwapBack && swapped) {
                pendingSwapBack = true;
                swapTimer.reset();
            }
        }
    }
}
