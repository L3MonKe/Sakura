package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import meteordevelopment.orbit.EventHandler;

public class AirJump extends Module {
    private boolean wasPressed = false;

    public AirJump() {
        super("AirJump", "空中跳跃", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean pressed = mc.options.jumpKey.isPressed();

        if (pressed && !wasPressed && !mc.player.isOnGround() && !mc.player.getAbilities().flying) {
            mc.player.jump();
        }

        wasPressed = pressed;
    }
}
