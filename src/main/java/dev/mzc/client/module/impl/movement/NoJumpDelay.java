package dev.mzc.client.module.impl.movement;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.mixin.accessor.ILivingEntity;
import meteordevelopment.orbit.EventHandler;

import java.util.concurrent.ThreadLocalRandom;

public class NoJumpDelay extends Module {
    private final BoolValue sprintOnly = new BoolValue("SprintOnly", "仅疾跑时生效", false);
    private final BoolValue randomize = new BoolValue("Randomize", "随机抖动", true);
    private final NumberValue<Integer> minDelay = new NumberValue<>("MinDelay", "最小冷却(tick)", 2, 0, 10, 1);
    private final NumberValue<Integer> jitter = new NumberValue<>("Jitter", "抖动范围(tick)", 1, 0, 4, 1, randomize::get);
    private final NumberValue<Integer> chance = new NumberValue<>("Chance", "触发概率(%)", 100, 0, 100, 5);

    public NoJumpDelay() {
        super("NoJumpDelay", "无跳跃冷却", Category.Movement);
        this.setType(ModuleType.Safe);
    }

    public boolean shouldApply() {
        if (mc.player == null) return false;
        if (sprintOnly.get() && !mc.player.isSprinting()) return false;
        return ThreadLocalRandom.current().nextInt(100) < chance.get();
    }

    public int getLegitCooldown() {
        int base = minDelay.get();
        if (randomize.get()) {
            base += ThreadLocalRandom.current().nextInt(jitter.get() + 1);
        }
        if (base < 2) base = 2;
        if (base > 10) base = 10;
        return base;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck() || !isEnabled()) return;
        if (!shouldApply()) return;
        ILivingEntity accessor = (ILivingEntity) (Object) mc.player;
        int target = getLegitCooldown();
        int current = accessor.getLastJumpCooldown();
        if (current > target) {
            accessor.setLastJumpCooldown(target);
        }
    }
}
