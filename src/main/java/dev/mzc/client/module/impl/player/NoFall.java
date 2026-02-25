package dev.mzc.client.module.impl.player;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.player.MotionEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.movement.Fly;
import dev.mzc.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.mixin.accessor.IPlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public enum Mode {
        Packet("发包"),
        Bucket("水桶");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);

    public NoFall() {
        super("NoFall", "无摔落", Category.Player);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerMoveC2SPacket packet) {
            if (mode.is(Mode.Packet)) {
                if (mc.player.getAbilities().creativeMode) return;

                if (!Sakura.MODULES.getModule(Fly.class).isEnabled()) {
                   if (mc.player.isGliding()) return;
                   if (mc.player.getVelocity().y > -0.5) return;
                   ((IPlayerMoveC2SPacket) packet).setOnGround(true);
                } else {
                    ((IPlayerMoveC2SPacket) packet).setOnGround(true);
                }
            }
        }
    }
}
