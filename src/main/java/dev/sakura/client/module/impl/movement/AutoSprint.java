package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.EventPriority;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "自动疾跑", Category.Movement);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        mc.options.sprintKey.setPressed(true);
    }

    @Override
    public void onDisable() {
        mc.options.sprintKey.setPressed(false);
    }
}
