package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.EventPriority;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.client.gui.screen.Screen;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "自动疾跑", Category.Movement);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        Screen currentScreen = mc.currentScreen;
        if (currentScreen != null) {
            mc.options.sprintKey.setPressed(false);
            return;
        }
        InvMove invMove = Sakura.MODULES.getModule(InvMove.class);
        if (invMove != null && invMove.isEnabled() && invMove.isSprintSuppressed()) {
            mc.options.sprintKey.setPressed(false);
            return;
        }
        mc.options.sprintKey.setPressed(true);
    }

    @Override
    public void onDisable() {
        mc.options.sprintKey.setPressed(false);
    }
}
