package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Phase;
import dev.sakura.client.values.impl.BoolValue;
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
        if (AutoPearl.throwing || Sakura.MODULES.getModule(Phase.class).isEnabled()) return;
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
