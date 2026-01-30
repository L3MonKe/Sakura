package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.render.item.UpdateHeldItemEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.player.inventory.InvHelper;
import dev.mahiro.client.utils.player.EnchantmentUtil;
import dev.mahiro.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "自动工具", Category.Player);
    }

    private final BoolValue checkSword = new BoolValue("Check Sword", "检查剑", false);
    private final BoolValue switchBack = new BoolValue("Switch Back", "切回", false);
    private final BoolValue fakeSilent = new BoolValue("Fake Silent", "假鬼手", false);

    private int originSlot = -1;

    @EventHandler
    public void onUpdateHeldItem(UpdateHeldItemEvent event) {
        if (switchBack.get() && fakeSilent.get() && event.getHand() == Hand.MAIN_HAND && originSlot != -1) {
            event.setItem(mc.player.getInventory().getStack(originSlot));
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.interactionManager.isBreakingBlock()) {
            if (checkSword.get()) {
                ItemStack itemStack = mc.player.getMainHandStack();
                if (itemStack.getItem() instanceof SwordItem) {
                    return;
                }
            }

            if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hitResult = (BlockHitResult) mc.crosshairTarget;
                int bestTool = getBestTool(hitResult.getBlockPos());
                if (bestTool != -1 && bestTool != mc.player.getInventory().selectedSlot) {
                    this.originSlot = mc.player.getInventory().selectedSlot;
                    mc.player.getInventory().selectedSlot = bestTool;
                }
            }
        }
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (nullCheck()) return;

        if (!mc.interactionManager.isBreakingBlock() && this.switchBack.get() && this.originSlot != -1) {
            mc.player.getInventory().selectedSlot = this.originSlot;
            this.originSlot = -1;
        }
    }

    private int getBestTool(BlockPos pos) {
        BlockState blockState = mc.world.getBlockState(pos);
        Block block = blockState.getBlock();
        int slot = 0;
        float dmg = 1.0F;

        for (int index = 0; index < 9; index++) {
            ItemStack itemStack = mc.player.getInventory().getStack(index);
            if (!InvHelper.isGodItem(itemStack)
                    && !itemStack.isEmpty()
                    && !blockState.isAir()
                    && (!(itemStack.getItem() instanceof SwordItem) || block instanceof CobwebBlock)) {
                float strVsBlock = itemStack.getItem().getMiningSpeed(itemStack, blockState);
                if (strVsBlock > 1.0F && !(block instanceof ExperienceDroppingBlock) && !(block instanceof RedstoneOreBlock)) {
                    int i = EnchantmentUtil.getEnchantmentLevel(itemStack, Enchantments.EFFICIENCY);
                    if (i > 0) {
                        strVsBlock += (float) (i * i + 1);
                    }
                }

                if (strVsBlock > dmg) {
                    slot = index;
                    dmg = strVsBlock;
                }
            }
        }

        return dmg > 1.0F ? slot : -1;
    }
}

