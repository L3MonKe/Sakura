package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.MotionEvent;
import dev.sakura.client.events.player.SlowdownEvent;
import dev.sakura.client.events.player.SprintEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlinkNoSlow extends Module {
    public BlinkNoSlow() {
        super("BlinkNoSlow", "Blink无减速", Category.Movement);
    }

    private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    private boolean bypassPacketEvent = false;

    @Override
    protected void onEnable() {
        packets.clear();
    }

    @Override
    protected void onDisable() {
        flush();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (bypassPacketEvent) return;
        if (nullCheck()) {
            packets.clear();
            return;
        }
        if (event.getType() != EventType.SEND) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof UpdateSelectedSlotC2SPacket || packet instanceof PlayerActionC2SPacket) {
            flush(false);
            return;
        }

        if (MoveUtil.isMoving() && mc.player.isUsingItem() && canNoSlow()) {
            event.setCancelled(true);
            packets.add(packet);
        } else {
            flush();
        }
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() == EventType.POST) return;
        if (nullCheck() || nullCheck()) {
            packets.clear();
            return;
        }

        if (mc.player.isUsingItem() && canNoSlow()) {
            if (MoveUtil.isMoving()) {
                PlayerInteractItemC2SPacket usePacket = new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), event.getYaw(), event.getPitch());
                packets.add(usePacket);
                flush();
                PlayerActionC2SPacket releasePacket = new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                        BlockPos.ORIGIN,
                        Direction.DOWN
                );
                packets.add(releasePacket);
            } else {
                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), event.getYaw(), event.getPitch()));
            }
        } else {
            flush();
        }
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) {
            packets.clear();
            return;
        }

        if (MoveUtil.isMoving() && mc.player.isUsingItem() && canNoSlow()) {
            event.setSlowdown(false);
        } else {
            flush();
        }
    }

    @EventHandler
    public void onSprint(SprintEvent event) {
        if (nullCheck()) return;
        if (mc.player.isUsingItem() && canNoSlow()) {
            event.setCancelled(true);
            event.setSprint(true);
        }
    }

    private void flush() {
        flush(true);
    }

    private void flush(boolean includeUsePackets) {
        if (packets.isEmpty()) return;

        if (!includeUsePackets) {
            packets.removeIf(packet -> packet instanceof PlayerInteractItemC2SPacket);
        }

        boolean old = bypassPacketEvent;
        bypassPacketEvent = true;
        try {
            while (!packets.isEmpty()) {
                Packet<?> packet = packets.poll();
                if (packet != null) {
                    mc.getNetworkHandler().sendPacket(packet);
                }
            }
        } finally {
            bypassPacketEvent = old;
        }
    }

    private boolean canNoSlow() {
        if (checkFood()) {
            return false;
        }
        if (checkItem(Items.BOW)) {
            return false;
        }
        return true;
    }

    private boolean checkFood() {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(Items.GOLDEN_APPLE)
                || offhandItem.isOf(Items.GOLDEN_APPLE)
                || mainHandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || offhandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || mainHandItem.isOf(Items.POTION)
                || offhandItem.isOf(Items.POTION);
    }

    private boolean checkItem(Item item) {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(item) || offhandItem.isOf(item);
    }
}
