package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

public class AutoJumpReset extends Module {
    private final NumberValue<Integer> cooldownTicks = new NumberValue<>("Cooldown", "冷却(tick)", 8, 0, 60, 1);
    private final BoolValue onlyGround = new BoolValue("OnlyGround", "仅地面触发", true);

    private int lastTriggerAge;

    public AutoJumpReset() {
        super("AutoJumpReset", "预测跳跃重置", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @Override
    protected void onEnable() {
        lastTriggerAge = 0;
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;
        if (!(event.getPacket() instanceof EntityStatusS2CPacket packet)) return;
        if (packet.getStatus() != 2) return;
        if (packet.getEntity(mc.world) != mc.player) return;
        if (mc.player.getAbilities().flying) return;
        if (!canTrigger()) return;

        if (onlyGround.get() && !mc.player.isOnGround()) return;
        doJump();
    }

    private boolean canTrigger() {
        return mc.player.age - lastTriggerAge >= cooldownTicks.get();
    }

    private void doJump() {
        mc.player.jump();
        lastTriggerAge = mc.player.age;
    }
}
