package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.MathHelper;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.entity.AttackEntityEvent;

public class KeepSprint extends Module {
    private boolean attacking;

    public KeepSprint() {
        super("KeepSprint", "保持疾跑", Category.Movement);
    }

    @EventHandler
    public void onAttack(AttackEntityEvent event) {
        attacking = true;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        attacking = false;
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() == EventType.SEND && event.getPacket() instanceof ClientCommandC2SPacket packet) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                if (attacking) {
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
}
