package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.MathHelper;

public class KeepSprint extends Module {
    public KeepSprint() {
        super("KeepSprint", "保持疾跑", Category.Movement);
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (event.getType() == EventType.SEND && event.getPacket() instanceof ClientCommandC2SPacket packet) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                if (mc.player.forwardSpeed > 0) {
                    float serverYaw = Managers.ROTATION.isActive() ? Managers.ROTATION.rotations.yaw : mc.player.getYaw();
                    if (Math.abs(MathHelper.wrapDegrees(mc.player.getYaw() - serverYaw)) < 45) {
                        event.cancel();
                    }
                }
            }
        }
    }
}
