package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class InvMove extends Module {
    public InvMove() {
        super("InvMove", "", Category.Movement);
    }

    public BoolValue sprintValue = new BoolValue("Sprint", "", true);
    public BoolValue onlyValue = new BoolValue("OnlySpoof", "", false);

    public boolean peek;
    public boolean jumped;

    @Override
    public void onEnable() {
        peek = false;
        jumped = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (onlyValue.get()) {
            peek = mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen);
        } else {
            if (mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen)) {
                KeyBinding[] key = {mc.options.forwardKey, mc.options.backKey, mc.options.leftKey, mc.options.rightKey, mc.options.sprintKey, mc.options.jumpKey};
                if (sprintValue.get()) {
                    setKeyPressed(mc.options.sprintKey);
                }
                for (KeyBinding b : key) {
                    setKeyPressed(b);
                }
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.SEND) return;

        Packet<?> packet = event.getPacket();
        if (mc.currentScreen instanceof InventoryScreen) {
            if (!jumped) {
                if (mc.player.isOnGround()) {
                    mc.player.jump();
                    jumped = true;
                }
            }
            if (jumped) {
                if (mc.player.isOnGround()) {
                    PacketUtil.sendPacketNoEvent(new CloseHandledScreenC2SPacket(mc.player.playerScreenHandler.syncId));
                }
            }
        }
        if (packet instanceof ClickSlotC2SPacket || packet instanceof CloseHandledScreenC2SPacket) {
            if (mc.player.isSprinting()) {
                event.setCancelled(true);
                PacketUtil.sendPacketNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                PacketUtil.sendPacketNoEvent(packet);
                PacketUtil.sendPacketNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            }
        }
        if (packet instanceof CloseHandledScreenC2SPacket) {
            jumped = false;
        }
    }

    private void setKeyPressed(KeyBinding keyBinding) {
        boolean pressed = InputUtil.isKeyPressed(mc.getWindow(), keyBinding.getDefaultKey().getCode());
        KeyBinding.setKeyPressed(keyBinding.getDefaultKey(), pressed);
    }

}
