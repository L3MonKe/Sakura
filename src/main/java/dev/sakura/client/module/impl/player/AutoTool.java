package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.entity.AttackBlockEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.player.inventory.InvHelper;
import dev.sakura.client.utils.player.ItemSpoofUtils;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "自动工具", Category.Player);
        this.setHidden(true);
    }

    public static final BoolValue spoof = new BoolValue("Spoof", "鬼手", true);
    private int originalSlot = -1;
    private boolean hasStartedSpoofing = false;

    @EventHandler
    public void onClick(AttackBlockEvent event) {
        switchSlot(event.getPos());
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (mc.player == null || event.getType() != EventType.PRE) return;
        if (!mc.options.attackKey.isPressed() && BedBreaker.breakingBlockPos == null) {
            if (hasStartedSpoofing) {
                if (originalSlot != -1) {
                    mc.player.getInventory().setSelectedSlot(originalSlot);
                    originalSlot = -1;
                }
                ItemSpoofUtils.stopSpoof();
                hasStartedSpoofing = false;
            }
        }
    }

    public void switchSlot(BlockPos blockPos) {
        if (mc.world == null || mc.player == null) return;
        float bestSpeed = 1F;
        int bestSlot = -1;

        BlockState blockState = mc.world.getBlockState(blockPos);

        for (int i = 0; i <= 8; i++) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (InvHelper.isGodItem(item)) {
                continue;
            }
            if (!item.isEmpty()) {
                float speed = item.getMiningSpeedMultiplier(blockState);
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
        }
        if (bestSlot != -1 && mc.player.getInventory().getSelectedSlot() != bestSlot) {
            if (spoof.get() && !ItemSpoofUtils.isSpoofing) {
                ItemSpoofUtils.startSpoof();
                hasStartedSpoofing = true;
            }
            if (originalSlot == -1) {
                originalSlot = mc.player.getInventory().getSelectedSlot();
            }
            mc.player.getInventory().setSelectedSlot(bestSlot);
        }
    }

    @Override
    protected void onDisable() {
        if (hasStartedSpoofing) {
            if (originalSlot != -1 && mc.player != null) {
                mc.player.getInventory().setSelectedSlot(originalSlot);
                originalSlot = -1;
            }
            ItemSpoofUtils.reset();
            hasStartedSpoofing = false;
        }
    }
}
