package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.PlayerTickEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GrimDisabler extends Module {
    public GrimDisabler() {
        super("GrimDisabler", "滚木禁用器", Category.Player);
    }

    private enum Mode {
        Transaction,
        S08,
        Full
    }

    private enum TransMode {
        Delay,
        Drop,
        Spam
    }

    private enum S08Mode {
        Cancel,
        Fake
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Transaction);
    private final EnumValue<TransMode> transMode = new EnumValue<>("Trans Mode", "穿死模式", TransMode.Delay, () -> mode.is(Mode.Transaction) || mode.is(Mode.Full));
    private final EnumValue<S08Mode> s08Mode = new EnumValue<>("S08 Mode", "S08模式", S08Mode.Cancel, () -> mode.is(Mode.S08) || mode.is(Mode.Full));
    private final BoolValue debug = new BoolValue("Debug", "调试", true);

    private final ConcurrentLinkedQueue<CommonPongC2SPacket> transQueue = new ConcurrentLinkedQueue<>();
    private int transID = 0;

    @Override
    public void onEnable() {
        transQueue.clear();
        transID = 0;
        ChatUtil.clientMessage("[Disabler] Module enabled");
    }

    @Override
    public void onDisable() {
        ChatUtil.clientMessage("[Disabler] Module disabled, releasing queued packets");
        while (!transQueue.isEmpty()) {
            PacketUtil.sendPacketNoEvent(transQueue.poll());
        }
    }

    @EventHandler
    public void onPacket(PacketEvent e) {
        if (nullCheck()) return;

        if (e.getType() == EventType.RECEIVE) {
            if (e.getPacket() instanceof CommonPingS2CPacket packet) {
                handleTransaction(e, packet);
            } else if (e.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
                handleS08(e, packet);
            }
        }
    }

    @EventHandler
    public void onUpdate(PlayerTickEvent e) {
        if ((mode.is(Mode.Transaction) || mode.is(Mode.Full)) && transMode.is(TransMode.Delay)) {

            if (!transQueue.isEmpty() && transID++ % 2 == 0) {
                CommonPongC2SPacket packet = transQueue.poll();
                if (packet != null) {
                    PacketUtil.sendPacketNoEvent(packet);
                    if (debug.get()) {
                        ChatUtil.clientMessage("[Disabler] Released trans: " + packet.getParameter());
                    }
                }
            }
        }
    }

    private void handleTransaction(PacketEvent e, CommonPingS2CPacket packet) {
        if (!mode.is(Mode.Transaction) && !mode.is(Mode.Full)) return;

        int id = packet.getParameter();

        if (debug.get()) {
            ChatUtil.clientMessage("[Disabler] Received ClientboundPingPacket ID: " + id);
        }

        switch (transMode.get()) {
            case Delay -> {
                e.setCancelled(true);
                transQueue.offer(new CommonPongC2SPacket(id));

                if (debug.get()) {
                    ChatUtil.clientMessage("[Disabler] Queued trans for delay: " + id);
                }
            }
            case Drop -> {
                if (id % 2 != 0) {
                    e.setCancelled(true);
                    if (debug.get()) {
                        ChatUtil.clientMessage("[Disabler] Dropped trans: " + id);
                    }
                }
            }

            case Spam -> {
                e.setCancelled(true);
                PacketUtil.sendPacketNoEvent(new CommonPongC2SPacket(id));
                PacketUtil.sendPacketNoEvent(new CommonPongC2SPacket(id));

                if (debug.get()) {
                    ChatUtil.clientMessage("[Disabler] Spammed trans: " + id);
                }
            }
        }
    }

    private void handleS08(PacketEvent e, PlayerPositionLookS2CPacket packet) {
        if (!mode.is(Mode.S08) && !mode.is(Mode.Full)) return;

        if (debug.get()) {
            ChatUtil.clientMessage("[Disabler] Received ClientboundPlayerPositionPacket ID: " + packet.teleportId());
        }

        switch (s08Mode.get()) {
            case Cancel -> {
                e.setCancelled(true);
                PacketUtil.sendPacketNoEvent(new TeleportConfirmC2SPacket(packet.teleportId()));

                if (debug.get()) {
                    ChatUtil.clientMessage("[Disabler] Cancelled S08 packet: " + packet.teleportId());
                }
            }
            case Fake -> {
                e.setCancelled(true);

                double oldX = mc.player.getX();
                double oldY = mc.player.getY();
                double oldZ = mc.player.getZ();

                PacketUtil.sendPacketNoEvent(new TeleportConfirmC2SPacket(packet.teleportId()));

                mc.player.setPos(oldX, oldY, oldZ);
                mc.player.setVelocity(0, 0, 0);

                if (debug.get()) {
                    ChatUtil.clientMessage("[Disabler] Faked S08 packet: " + packet.teleportId());
                }
            }
        }
    }
}
