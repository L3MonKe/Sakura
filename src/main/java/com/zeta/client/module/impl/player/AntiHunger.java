package com.zeta.client.module.impl.player;

import com.zeta.client.Zeta;
import com.zeta.client.events.EventType;
import com.zeta.client.events.packet.PacketEvent;
import com.zeta.client.mixin.accessor.IPlayerMoveC2SPacket;
import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.module.impl.movement.Phase;
import com.zeta.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AntiHunger extends Module {
    public AntiHunger() {
        super("AntiHunger", "反饥饿", Category.Player);
    }

    public final BoolValue sprint = new BoolValue("Sprint", "冲刺时", false);
    public final BoolValue ground = new BoolValue("Ground", "地上时", true);

    @EventHandler(priority = EventPriority.LOW)
    public void onPacketSend(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;

        if (BowBomb.send) return;
        if (AutoPearl.throwing || Zeta.MODULES.getModule(Phase.class).isEnabled()) return;
        if (event.getPacket() instanceof ClientCommandC2SPacket packet && sprint.get()) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                event.cancel();
            }
        }
        if (event.getPacket() instanceof PlayerMoveC2SPacket && ground.get() && mc.player.fallDistance <= 0 && !mc.interactionManager.isBreakingBlock()) {
            ((IPlayerMoveC2SPacket) event.getPacket()).setOnGround(false);
        }
    }
}
