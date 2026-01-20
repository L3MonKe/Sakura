package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.LemonClient;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.movement.Phase;
import dev.mahiro.client.values.impl.BoolValue;
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
        if (AutoPearl.throwing || LemonClient.MODULES.getModule(Phase.class).isEnabled()) return;
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
