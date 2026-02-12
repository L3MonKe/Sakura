package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.client.util.InputUtil;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("SafeWalk", "安全行走", Category.Movement);
    }

    public static boolean isOnBlockEdge(float sensitivity) {
        return !mc.world.getCollisions(mc.player, mc.player.getBoundingBox().offset(0.0, -0.5, 0.0).expand(-sensitivity, 0.0, -sensitivity)).iterator().hasNext();
    }

    @EventHandler
    public void onMotion(MotionEvent e) {
        if (e.getType() == EventType.PRE) {
            mc.options.sneakKey.setPressed(mc.player.isOnGround() && isOnBlockEdge(0.3F));
        }
    }

    @EventHandler
    public void onDisable() {
        boolean isHoldingShift = InputUtil.isKeyPressed(mc.getWindow(), mc.options.sneakKey.getDefaultKey().getCode());
        mc.options.sneakKey.setPressed(isHoldingShift);
    }
}
