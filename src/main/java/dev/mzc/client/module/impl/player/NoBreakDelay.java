package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.mixin.accessor.IClientPlayerInteractionManager;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

public class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super("NoBreakDelay", "无破坏延迟", Category.Player);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.interactionManager != null) {
            ((IClientPlayerInteractionManager) mc.interactionManager).setBlockBreakingCooldown(0);
        }
    }
}
