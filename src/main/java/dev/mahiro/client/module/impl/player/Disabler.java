package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue logging = new BoolValue("Logging", "日志", false);
    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);

    @EventHandler
    private void onPacketSend(PacketEvent event) {
        if (event.getType() != EventType.SEND || nullCheck()) return;

        if (disAim360.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    log("Disabled aim 360");
                }
            }
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.addChatMessage(message);
            //NotificationManager.send(message);
        }
    }
}
