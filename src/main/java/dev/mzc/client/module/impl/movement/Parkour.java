package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.MovementUtil;
import meteordevelopment.orbit.EventHandler;

public class Parkour extends Module {
    public Parkour() {
        super("Parkour", "自动跑酷", Category.Movement);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.player.isOnGround() && !mc.player.isSneaking() && !mc.options.jumpKey.isPressed()) {
            if (mc.world.getCollisions(mc.player, mc.player.getBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001)).iterator().hasNext()) {
                return;
            }
            
            if (MovementUtil.isMoving()) {
                mc.player.jump();
            }
        }
    }
}
