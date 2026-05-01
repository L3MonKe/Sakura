package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.EventPriority;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.player.inventory.InvManager;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.client.gui.screen.Screen;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "自动疾跑", Category.Movement);
    }

    private final BoolValue omnidirectional = new BoolValue("Omnidirectional", "全方位疾跑", false);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        Screen currentScreen = mc.currentScreen;
        if (currentScreen != null) {
            mc.options.sprintKey.setPressed(false);
            return;
        }
        InvManager invMove = Sakura.MODULES.getModule(InvManager.class);
        if (invMove != null && invMove.isEnabled() && invMove.isSprintSuppressed()) {
            mc.options.sprintKey.setPressed(false);
            return;
        }
        mc.options.sprintKey.setPressed(true);
    }

    public boolean isOmnidirectional() {
        return omnidirectional.get();
    }

    @Override
    public void onDisable() {
        mc.options.sprintKey.setPressed(false);
    }
}
