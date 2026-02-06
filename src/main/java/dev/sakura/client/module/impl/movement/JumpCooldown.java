package dev.sakura.client.module.impl.movement;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;

public class JumpCooldown extends Module {
    public JumpCooldown() {
        super("JumpCooldown", "跳跃冷却", Category.Movement);
    }

    public final NumberValue<Integer> cooldown = new NumberValue<>("Max Cooldown", "最大冷却", 0, 0, 9, 1);
}
