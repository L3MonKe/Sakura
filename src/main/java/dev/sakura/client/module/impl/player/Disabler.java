package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue logging = new BoolValue("Logging", "日志", false);

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.SEND) return;

        if (disAim360.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket && movePacket.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    if (logging.get()) {
                        log("Disabled aim 360");
                    }
                }
            }
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.clientMessage(message);
        }
    }
}
