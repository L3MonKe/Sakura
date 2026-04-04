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

import java.util.Random;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue duplicateRotPlace = new BoolValue("Duplicate Rot Place", "Duplicate Rot Place", true);
    private final BoolValue logging = new BoolValue("Logging", "日志", false);

    private float playerYaw;

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
                return;
            }
        }

        if (duplicateRotPlace.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float originalYaw = accessor.getYaw();

                if (originalYaw < 360.0F && originalYaw > -360.0F) {
                    ((IPlayerMoveC2SPacket) packet).setYaw(originalYaw + 720f);
                }

                float lastPlayerYaw = this.playerYaw;
                this.playerYaw = accessor.getYaw();

                float deltaYaw = Math.abs(this.playerYaw - lastPlayerYaw);
                if (deltaYaw > 2.0F) {
                    Random random = new Random();
                    float perturbation = 0.005f + random.nextFloat() * 0.015f;
                    if (random.nextBoolean()) {
                        ((IPlayerMoveC2SPacket) packet).setYaw(accessor.getYaw() + perturbation);
                    } else {
                        ((IPlayerMoveC2SPacket) packet).setYaw(accessor.getYaw() - perturbation);
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
