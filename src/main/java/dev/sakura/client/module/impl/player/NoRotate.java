package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", "防强制旋转", Category.Player);
    }

    @EventHandler
    private void onReceivePacket(PacketEvent event) {
        if (event.getType() != EventType.RECEIVE || nullCheck()) return;

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
            PlayerPosition oldPosition = packet.change();
            PlayerPosition newPosition = new PlayerPosition(oldPosition.position(), oldPosition.deltaMovement(), mc.player.getYaw(), mc.player.getPitch());
            event.setPacket(PlayerPositionLookS2CPacket.of(packet.teleportId(), newPosition, packet.relatives()));
        }
    }
}
