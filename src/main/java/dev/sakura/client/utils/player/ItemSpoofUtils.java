package dev.sakura.client.utils.player;

import net.minecraft.item.ItemStack;

import static dev.sakura.client.Sakura.mc;

public class ItemSpoofUtils {
    public static boolean isSpoofing = false;
    public static int originalSlot = -1;
    private static int counter = 0;

    public static void startSpoof() {
        if (mc.player != null && mc.world != null) {
            ++counter;

            if (!isSpoofing) {
                originalSlot = mc.player.getInventory().getSelectedSlot();
                isSpoofing = true;
            }
        }
    }

    public static void stopSpoof() {
        if (mc.player != null && mc.world != null && isSpoofing) {
            --counter;

            if (counter <= 0) {
                mc.player.getInventory().setSelectedSlot(originalSlot);
                isSpoofing = false;
            }
        }
    }

    public static int getSpoofedSlot() {
        if (mc.player != null && mc.world != null) {
            return isSpoofing ? originalSlot : mc.player.getInventory().getSelectedSlot();
        } else {
            return -1;
        }
    }

    public static ItemStack getSpoofedStack() {
        if (mc.player != null && mc.world != null) {
            return isSpoofing ? mc.player.getInventory().getStack(originalSlot) : mc.player.getMainHandStack();
        } else {
            return null;
        }
    }

    public static void reset() {
        isSpoofing = false;
        originalSlot = -1;
        counter = 0;
    }
}
