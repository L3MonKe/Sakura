package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.PacketUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.*;

import java.util.concurrent.LinkedBlockingQueue;

public class Blink extends Module {
    public Blink() {
        super("Blink", "Blink", Category.Movement);
    }

    private final LinkedBlockingQueue<PacketTime> packetQueue = new LinkedBlockingQueue<>();

    @Override
    public void onEnable() {
        packetQueue.clear();
    }

    @Override
    public void onDisable() {
        while (!packetQueue.isEmpty()) {
            PacketTime pt = packetQueue.poll();
            if (pt != null) {
                PacketUtil.sendPacketNoEvent(pt.packet);
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent e) {
        if (nullCheck()) return;
        if (e.getType() == EventType.SEND) {
            Packet<?> packet = e.getPacket();

            if (!(packet instanceof PlayerMoveC2SPacket)
                    && !(packet instanceof CommonPongC2SPacket)
                    && !(packet instanceof PlayerInteractItemC2SPacket)
                    && !(packet instanceof PlayerInteractBlockC2SPacket)
                    && !(packet instanceof ClientCommandC2SPacket)
                    && !(packet instanceof ClickSlotC2SPacket)
                    && !(packet instanceof CloseHandledScreenC2SPacket)) {
                e.setCancelled(true);
                packetQueue.add(new PacketTime(packet, System.currentTimeMillis()));
            }
        }
    }

    @EventHandler
    public void onTick(PlayerTickEvent event) {
        if (nullCheck()) return;

        while (!packetQueue.isEmpty()) {
            PacketTime head = packetQueue.peek();

            long delayMs = 300L;
            if (System.currentTimeMillis() - head.time >= delayMs) {
                PacketUtil.sendPacketNoEvent(packetQueue.poll().packet);
            } else {
                break;
            }
        }
    }

    private static class PacketTime {
        Packet<?> packet;
        long time;

        public PacketTime(Packet<?> packet, long time) {
            this.packet = packet;
            this.time = time;
        }
    }
}
