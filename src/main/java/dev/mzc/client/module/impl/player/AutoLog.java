package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.time.TimerUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static dev.mzc.client.Sakura.mc;

public class AutoLog extends Module {
    public AutoLog() {
        super("AutoLog", "自动退出", Category.Player);
        this.setType(ModuleType.Safe);
    }

    public enum Mode {
        Hub("输入/hub"),
        Disconnect("退出游戏");
        private final String cnName;
        Mode(String cnName) { this.cnName = cnName; }
        public String getCnName() { return cnName; }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Hub);
    private final BoolValue requireNoTotem = new BoolValue("RequireNoTotem", "需要背包没有图腾", true);
    private final NumberValue<Double> healthThreshold = new NumberValue<>("Health", "血量阈值", 8.0, 1.0, 20.0, 0.5);
    private final NumberValue<Integer> delayMs = new NumberValue<>("DelayMs", "延迟毫秒", 1000, 0, 10000, 50);

    private final TimerUtil timer = new TimerUtil();
    private boolean scheduled = false;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!scheduled) {
            if (shouldTrigger()) {
                scheduled = true;
                timer.reset();
            }
            return;
        }

        if (timer.passedMS(delayMs.get())) {
            executeAction();
            scheduled = false;
        }
    }

    private boolean shouldTrigger() {
        float hp = mc.player.getHealth();
        if (hp > healthThreshold.get().floatValue()) return false;
        if (requireNoTotem.get() && hasTotem()) return false;
        return true;
    }

    private boolean hasTotem() {
        if (mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) return true;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack s = mc.player.getInventory().getStack(i);
            if (s.isOf(Items.TOTEM_OF_UNDYING)) return true;
        }
        return false;
    }

    private void executeAction() {
        if (mode.is(Mode.Hub)) {
            if (mc.player != null && mc.player.networkHandler != null) {
                mc.player.networkHandler.sendChatCommand("hub");
            }
        } else if (mode.is(Mode.Disconnect)) {
            if (mc.world != null) {
                mc.world.disconnect();
                mc.setScreen(new TitleScreen());
            }
        }
    }
}
