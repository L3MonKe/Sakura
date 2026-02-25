package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.entity.BlockPushEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.entity.EntityUtil;
import meteordevelopment.orbit.EventHandler;

import static dev.mzc.client.Sakura.mc;

public class AntiBlockPush extends Module {
    public AntiBlockPush() {
        super("AntiBlockPush", "方块灵活移动", Category.Movement);
    }

    @EventHandler
    private void onBlockPush(BlockPushEvent event) {
        event.cancel();

        if (mc.player != null && EntityUtil.isInsideBlock()) {
            mc.player.setVelocity(mc.player.getVelocity().multiply(0.3, 1, 0.3));
        }
    }
}
