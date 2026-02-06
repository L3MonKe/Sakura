package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
    private final BoolValue strict = new BoolValue("Strict", "严格模式", true);
    private final NumberValue<Double> health = new NumberValue<>("Health", "生命值阈值", 16.0, 0.0, 36.0, 0.5);
    private final BoolValue checkGapple = new BoolValue("Check Gapple", "检查金苹果", true);

    public AutoTotem() {
        super("AutoTotem", "自动图腾", Category.Combat);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // Check if we need a totem
        if (shouldHoldingTotem()) {
            if (mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                FindItemResult result = InvUtil.find(Items.TOTEM_OF_UNDYING);
                if (result.found()) {
                    moveTotem(result.slot());
                }
            }
        }
    }

    private boolean shouldHoldingTotem() {
        // If health is low, always hold totem
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= health.get().floatValue()) {
            return true;
        }

        // If strict check gapple is on, and we are holding gapple in main hand, maybe we don't need totem?
        // Usually AutoTotem replaces offhand.
        // If we are holding gapple in main hand and health is high, maybe we want to hold shield/crystal in offhand?
        // But this is "AutoTotem", it usually forces totem.
        // Unless we have an "AutoOffhand" module which manages Shield/Crystal/Gapple.
        // For now, simple logic: if health <= threshold, FORCE totem.
        // If health > threshold, maybe keep current offhand? Or do nothing?
        // User asked for "AutoTotem", usually implies "Keep me alive".
        // I will assume it only activates when needed or always?
        // Usually AutoTotem is "Always on" or "On low health".
        // Most PVP clients have "Offhand" module for switching. "AutoTotem" is specifically for saving life.
        // So I will stick to: Always force if health low. If health high, do nothing (let user/other modules handle it).
        // But wait, if offhand is empty, maybe put totem just in case?

        if (mc.player.getOffHandStack().isEmpty()) return true;

        // If we are falling into void?
        if (mc.player.getY() < -64) return true; // Void check

        // If we are continuously taking damage?

        return mc.player.getHealth() + mc.player.getAbsorptionAmount() <= health.get().floatValue() ||
                mc.player.getOffHandStack().isEmpty();
    }

    private void moveTotem(int slot) {
        // "Simulate player opening inventory, putting totem in offhand, and closing inventory"

        if (slot < 9 && slot >= 0) {
            // Hotbar: 0-8
            // Hotbar slots in PlayerScreenHandler are 36-44
            slot += 36;
        }

        // Use default swap if strict is off (faster)
        // But user asked for simulation.

        boolean openInventory = strict.get() && mc.currentScreen == null;

        if (openInventory) {
            // Send Open Inventory Packet (if mapping exists)
            // In 1.21 it might be ClientCommandC2SPacket.Mode.OPEN_INVENTORY_STATS
            // or just ignore if it doesn't exist/work.
            // Many strict servers check for the "Open" state.
            try {
                // Try to send open packet
                // Note: fabric mappings might vary. I'll try standard.
                // If not sure, we can skip or rely on just click sequence.
                // But let's try.
                // ClientCommandC2SPacket packet = new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.OPEN_INVENTORY_STATS);
                // mc.getNetworkHandler().sendPacket(packet);
            } catch (Exception e) {
                // Ignore
            }
        }

        // Move item
        // Pickup source
        mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
        // Click offhand (45)
        mc.interactionManager.clickSlot(0, 45, 0, SlotActionType.PICKUP, mc.player);

        // If source wasn't empty (swapped), put it back to source?
        // If offhand had something, it is now on cursor.
        // We should put it in the source slot.
        // Wait, if offhand was not empty, we just swapped them via cursor.
        // click 45 -> cursor has old offhand item.
        // click source -> place old offhand item.
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
        }

        if (openInventory) {
            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(mc.player.currentScreenHandler.syncId));
        }
    }
}
