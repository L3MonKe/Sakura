package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

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
