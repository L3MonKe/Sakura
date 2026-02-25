package dev.sakura.client.utils.player;

import net.minecraft.util.Hand;

public record FindItemResult(int slot, int count) {
    public boolean found() {
        return slot != -1;
    }

    public Hand getHand() {
        if (slot == SlotUtil.OFFHAND) {
            return Hand.OFF_HAND;
        }
        /*if (slot == mc.player.getInventory().selectedSlot) {
            return Hand.MAIN_HAND;
        }*/
        return Hand.MAIN_HAND;
    }

    public boolean isMainHand() {
        return getHand() == Hand.MAIN_HAND;
    }

    public boolean isOffhand() {
        return getHand() == Hand.OFF_HAND;
    }

    public boolean isHotbar() {
        return slot >= SlotUtil.HOTBAR_START && slot <= SlotUtil.HOTBAR_END;
    }

    public boolean isMain() {
        return slot >= SlotUtil.MAIN_START && slot <= SlotUtil.MAIN_END;
    }

    public boolean isArmor() {
        return slot >= SlotUtil.ARMOR_START && slot <= SlotUtil.ARMOR_END;
    }
}
