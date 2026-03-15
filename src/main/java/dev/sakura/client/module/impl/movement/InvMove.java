package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
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
    private boolean resumeSprint;
    private int sprintSuppressTicks;
    private int inventoryActionDelayTicks;

    @Override
    public void onEnable() {
        peek = false;
        resumeSprint = false;
        sprintSuppressTicks = 0;
        inventoryActionDelayTicks = 0;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (sprintSuppressTicks > 0) {
            sprintSuppressTicks--;
            mc.player.setSprinting(false);
            KeyBinding.setKeyPressed(mc.options.sprintKey.getDefaultKey(), false);
            resumeSprint = false;
        }

        if (inventoryActionDelayTicks > 0) {
            inventoryActionDelayTicks--;
        }

        if (resumeSprint && sprintSuppressTicks == 0 && mc.currentScreen == null && MoveUtil.isMoving()) {
            PacketUtil.sendPacketNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            mc.player.setSprinting(true);
            resumeSprint = false;
        }

        if (onlyValue.get()) {
            peek = mc.currentScreen instanceof InventoryScreen;
        } else {
            if (mc.currentScreen instanceof InventoryScreen) {
                KeyBinding[] key = {mc.options.forwardKey, mc.options.backKey, mc.options.leftKey, mc.options.rightKey, mc.options.jumpKey};
                if (sprintValue.get()) {
                    setKeyPressed(mc.options.sprintKey);
                } else {
                    KeyBinding.setKeyPressed(mc.options.sprintKey.getDefaultKey(), false);
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
        if (packet instanceof ClickSlotC2SPacket || packet instanceof CloseHandledScreenC2SPacket) {
            boolean wasSprinting = mc.player.isSprinting();
            boolean keyPressed = mc.options.sprintKey.isPressed();
            if (wasSprinting || keyPressed) {
                mc.player.setSprinting(false);
                KeyBinding.setKeyPressed(mc.options.sprintKey.getDefaultKey(), false);
                sprintSuppressTicks = 4;
                resumeSprint = wasSprinting && mc.currentScreen instanceof InventoryScreen && sprintValue.get();
            }
        }
    }

    private void setKeyPressed(KeyBinding keyBinding) {
        boolean pressed = InputUtil.isKeyPressed(mc.getWindow(), keyBinding.getDefaultKey().getCode());
        KeyBinding.setKeyPressed(keyBinding.getDefaultKey(), pressed);
    }

    public boolean isSprintSuppressed() {
        return sprintSuppressTicks > 0;
    }

    public void suppressSprintForTicks(int ticks) {
        if (ticks > sprintSuppressTicks) {
            sprintSuppressTicks = ticks;
        }
        resumeSprint = false;
        if (mc.player != null) {
            mc.player.setSprinting(false);
            KeyBinding.setKeyPressed(mc.options.sprintKey.getDefaultKey(), false);
        }
    }

    public boolean prepareInventoryAction() {
        if (mc.player == null) {
            return false;
        }
        if (sprintSuppressTicks > 0) {
            return inventoryActionDelayTicks <= 0;
        }
        boolean wasSprinting = mc.player.isSprinting();
        boolean keyPressed = mc.options.sprintKey.isPressed();
        if (wasSprinting) {
            suppressSprintForTicks(6);
            if (inventoryActionDelayTicks < 1) {
                inventoryActionDelayTicks = 1;
            }
            return false;
        }
        if (keyPressed) {
            suppressSprintForTicks(2);
        }
        return inventoryActionDelayTicks <= 0;
    }

}
