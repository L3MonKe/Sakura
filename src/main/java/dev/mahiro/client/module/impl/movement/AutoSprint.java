package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
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
