package dev.sakura.client.module.impl.player.inventory;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SetPlayerInventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;

import java.util.Arrays;
import java.util.List;

public class InventorySync extends Module {
    public InventorySync() {
        super("InventorySync", "背包同步", Category.Player);
    }

    private final NumberValue<Integer> syncTolerance = new NumberValue<>("Sync Tolerance", "同步容忍Tick", 2, 1, 20, 1);
    private final ItemStack[] serverInventoryShadow = new ItemStack[36];
    private final boolean[] knownServerSlots = new boolean[36];
    private int serverSelectedSlot = -1;
    private int desyncTicks = 0;

    @Override
    protected void onEnable() {
        this.resetServerShadow();
    }

    @Override
    protected void onDisable() {
        this.resetServerShadow();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) {
            return;
        }
        this.enforceInventoryStateSync();
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (event.getType() != EventType.RECEIVE || mc.player == null) {
            return;
        }
        this.updateServerShadow(event.getPacket());
    }

    private void resetServerShadow() {
        Arrays.fill(this.serverInventoryShadow, ItemStack.EMPTY);
        Arrays.fill(this.knownServerSlots, false);
        this.serverSelectedSlot = -1;
        this.desyncTicks = 0;
    }

    private void updateServerShadow(Packet<?> packet) {
        if (packet instanceof InventoryS2CPacket inventoryPacket) {
            if (inventoryPacket.syncId() == 0) {
                List<ItemStack> contents = inventoryPacket.contents();
                int size = Math.min(contents.size(), 45);
                for (int slot = 0; slot < size; slot++) {
                    this.updateShadowSlot(this.mapPlayerScreenSlotToInventory(slot), contents.get(slot));
                }
            }
            return;
        }
        if (packet instanceof ScreenHandlerSlotUpdateS2CPacket slotUpdatePacket) {
            if (slotUpdatePacket.getSyncId() == 0) {
                this.updateShadowSlot(this.mapPlayerScreenSlotToInventory(slotUpdatePacket.getSlot()), slotUpdatePacket.getStack());
            }
            return;
        }
        if (packet instanceof SetPlayerInventoryS2CPacket setPlayerInventoryPacket) {
            int slot = setPlayerInventoryPacket.slot();
            if (slot >= 0 && slot < 36) {
                this.serverInventoryShadow[slot] = setPlayerInventoryPacket.contents().copy();
                this.knownServerSlots[slot] = true;
            }
            return;
        }
        if (packet instanceof UpdateSelectedSlotS2CPacket selectedSlotPacket) {
            int slot = selectedSlotPacket.slot();
            if (PlayerInventory.isValidHotbarIndex(slot)) {
                this.serverSelectedSlot = slot;
            }
        }
    }

    private void updateShadowSlot(int inventorySlot, ItemStack stack) {
        if (inventorySlot < 0 || inventorySlot >= 36) {
            return;
        }
        this.serverInventoryShadow[inventorySlot] = stack.copy();
        this.knownServerSlots[inventorySlot] = true;
    }

    private int mapPlayerScreenSlotToInventory(int screenSlot) {
        if (screenSlot >= 36 && screenSlot <= 44) {
            return screenSlot - 36;
        }
        if (screenSlot >= 9 && screenSlot <= 35) {
            return screenSlot;
        }
        return -1;
    }

    private void enforceInventoryStateSync() {
        PlayerInventory inventory = mc.player.getInventory();
        boolean mismatch = false;
        for (int slot = 0; slot < 36; slot++) {
            if (!this.knownServerSlots[slot]) {
                continue;
            }
            if (!ItemStack.areEqual(inventory.getStack(slot), this.serverInventoryShadow[slot])) {
                mismatch = true;
                break;
            }
        }
        if (!mismatch && this.serverSelectedSlot != -1 && inventory.getSelectedSlot() != this.serverSelectedSlot) {
            mismatch = true;
        }
        if (!mismatch) {
            this.desyncTicks = 0;
            return;
        }
        this.desyncTicks++;
        if (this.desyncTicks < this.syncTolerance.get()) {
            return;
        }
        for (int slot = 0; slot < 36; slot++) {
            if (!this.knownServerSlots[slot]) {
                continue;
            }
            if (!ItemStack.areEqual(inventory.getStack(slot), this.serverInventoryShadow[slot])) {
                inventory.setStack(slot, this.serverInventoryShadow[slot].copy());
            }
        }
        if (this.serverSelectedSlot != -1 && inventory.getSelectedSlot() != this.serverSelectedSlot) {
            inventory.setSelectedSlot(this.serverSelectedSlot);
            mc.interactionManager.syncSelectedSlot();
        }
        this.desyncTicks = 0;
    }
}
