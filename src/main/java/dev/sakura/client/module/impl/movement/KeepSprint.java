package dev.sakura.client.module.impl.movement;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;

public class KeepSprint extends Module {
    public KeepSprint() {
        super("KeepSprint", "保持疾跑", Category.Movement);
    }

    private final BoolValue keepSlowdown = new BoolValue("Keep Slowdown", "保持减速", true);
    private final NumberValue<Double> slowdownMultiplier = new NumberValue<>("Slowdown Multiplier", "减速倍数", 0.7, 0.0, 1.0, 0.05, () -> keepSlowdown.get());

    @Override
    protected void onDisable() {
    }

    /**
     * 当攻击造成击退时调用，用于保持疾跑状态和处理减速效果
     */
    public void onAttackKnockback() {
        if (nullCheck()) return;
        
        // 注意：我们不需要恢复疾跑状态，因为 mixin 已经取消了 setSprinting(false) 的调用
        // 疾跑状态保持原样，这让原版的 sendSprintingPacket() 逻辑正常工作，
        // 避免了 BadPacketsF 检测
        
        // 处理减速效果
        if (!keepSlowdown.get()) {
            // 如果不保持减速，需要恢复原来的速度
            double restoreMultiplier = 1.0 / 0.6;
            mc.player.setVelocity(
                mc.player.getVelocity().x * restoreMultiplier,
                mc.player.getVelocity().y,
                mc.player.getVelocity().z * restoreMultiplier
            );
        } else {
            // 如果需要自定义减速倍数
            double currentMultiplier = 0.6; // MC 原版的减速倍数
            double targetMultiplier = slowdownMultiplier.get();
            if (Math.abs(currentMultiplier - targetMultiplier) > 0.01) {
                double adjustMultiplier = targetMultiplier / currentMultiplier;
                mc.player.setVelocity(
                    mc.player.getVelocity().x * adjustMultiplier,
                    mc.player.getVelocity().y,
                    mc.player.getVelocity().z * adjustMultiplier
                );
            }
        }
    }

    @Override
    public String getSuffix() {
        if (keepSlowdown.get()) {
            return String.format("%.0f%%", slowdownMultiplier.get() * 100);
        }
        return "No Slowdown";
    }
}
