package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.BlockBreakingCooldownEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;

public class BreakCooldown extends Module {
    public BreakCooldown() {
        super("BreakCooldown", "挖掘冷却", Category.Movement);
    }

    public final NumberValue<Integer> cooldown = new NumberValue<>("Cooldown", "冷却时间", 0, 0, 5, 1);

    @EventHandler
    private void onBlockBreakingCooldown(BlockBreakingCooldownEvent event) {
        event.setCooldown(cooldown.get());
    }
}
