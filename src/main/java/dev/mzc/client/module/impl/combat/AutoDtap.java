package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.client.TickEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class AutoDtap extends Module {

    private final Random random = new Random();

    private int swapBackDelay = 0;     // 物品切回延迟
    private int stepDelay = 0;         // 每一步延迟
    private int step = 0;              // 状态机：0-未触发，1-黑曜石放置，2-末影水晶放置，3-恢复原手持
    private int originalSlot = -1;     // 原手持
    private boolean triggered = false; // 本次触发标记
    private final BoolValue swapBack = new BoolValue("SwapBack", "完成后切回原物品", true);

    public AutoDtap() {
        super("AutoDtap", "自动Dtap", Category.Combat);
        this.setType(ModuleType.Safe);
    }

    @Override
    public void onEnable() {
        swapBackDelay = 0;
        stepDelay = 0;
        step = 0;
        originalSlot = -1;
        triggered = false;
    }

    @Override
    public void onDisable() {
        resetState();
        swapBackDelay = 0;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 如果正在执行过程中（step 1, 2），彻底消耗掉右键点击事件，不让 case 0 接收到
        if (step != 0) {
            // 消耗掉 Minecraft 记录的所有右键点击状态
            while (mc.options.useKey.wasPressed()) {}
            if (mc.mouse != null) mc.mouse.wasRightButtonClicked();
            mc.options.useKey.setPressed(false); // 强制取消按住状态，防止 client-side 自动重复放置
            // 确保本 tick 不会再向下执行触发逻辑
        }

        // ======== 处理延迟 ========
        if (stepDelay > 0) {
            stepDelay--;
            return;
        }

        // ======== 状态机处理 ========
        switch (step) {
            case 0: // 未触发，准备放置黑曜石
                // 只有在 step 为 0 时才检查触发
                if (mc.mouse != null && mc.mouse.wasRightButtonClicked()) {
                    // 必须手持剑
                    ItemStack main = mc.player.getMainHandStack();
                    if (!(main.getItem() instanceof SwordItem)) return;

                    // 确保玩家正在看向一个方块（防止 air place）
                    HitResult hit = mc.crosshairTarget;
                    if (!(hit instanceof BlockHitResult blockHit)) return;
                    if (blockHit.getType() != HitResult.Type.BLOCK) return;
                    if (mc.world.getBlockState(blockHit.getBlockPos()).isAir()) return;

                    Block targetBlock = mc.world.getBlockState(blockHit.getBlockPos()).getBlock();
                    boolean isObsidian = targetBlock == Blocks.OBSIDIAN || targetBlock == Blocks.BEDROCK;

                    // 预先检查是否有水晶，没有就不开始流程
                    int endCrystalSlot = findEndCrystalSlot();
                    if (endCrystalSlot == -1) return;

                    originalSlot = mc.player.getInventory().selectedSlot;

                    if (isObsidian) {
                        // 如果原本就是黑曜石或基岩，直接切换到水晶并进入下一步
                        mc.player.getInventory().setSelectedSlot(endCrystalSlot);
                        mc.interactionManager.syncSelectedSlot();
                        
                        // 构造“点击方块顶面”的命中结果用于放置水晶
                        BlockHitResult topHit = new BlockHitResult(
                                blockHit.getPos(),
                                Direction.UP,
                                blockHit.getBlockPos(),
                                false
                        );
                        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, topHit);
                        mc.player.swingHand(Hand.MAIN_HAND);

                        triggered = true;
                        stepDelay = 1 + random.nextInt(2);
                        step = 2; // 直接跳到恢复原手持阶段，因为已经放完水晶了
                    } else {
                        // 找到黑曜石槽位
                        int obsidianSlot = findObsidianSlot();
                        if (obsidianSlot == -1) return;

                        mc.player.getInventory().setSelectedSlot(obsidianSlot);
                        mc.interactionManager.syncSelectedSlot();

                        // 直接对当前方块执行右键放置黑曜石
                        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHit);
                        mc.player.swingHand(Hand.MAIN_HAND);

                        // 【关键修复】立即切换到水晶槽位，防止 client 自动重复放置黑曜石
                        mc.player.getInventory().setSelectedSlot(endCrystalSlot);
                        mc.interactionManager.syncSelectedSlot();

                        triggered = true;
                        // 设置切换延迟 1~2 tick
                        stepDelay = 1 + random.nextInt(2);
                        step = 1;
                    }
                }
                break;

            case 1: // 等待黑曜石放置完成并放置水晶
                if (stepDelay > 0) {
                    stepDelay--;
                    return;
                }

                // 此时手持已经是水晶（在 case 0 中已切换）
                HitResult hit = mc.crosshairTarget;
                if (!(hit instanceof BlockHitResult baseHit)) {
                    resetState();
                    return;
                }

                if (baseHit.getType() != HitResult.Type.BLOCK) {
                    resetState();
                    return;
                }
                BlockPos base = baseHit.getBlockPos();

                // 检查方块是否已经变成黑曜石（或者原本就是基岩）
                // 注意：由于是 client-side 预测，这里可能还是空气，但 interactBlock 通常能处理
                if (mc.world.getBlockState(base).isAir()) {
                    // 如果还是空气，说明黑曜石还没放好，可以再等一 tick 或者直接尝试放置水晶（预测）
                }

                // 构造“点击方块顶面”的命中结果用于放置水晶
                BlockHitResult topHit = new BlockHitResult(
                        baseHit.getPos(),
                        Direction.UP,
                        base,
                        false
                );
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, topHit);
                mc.player.swingHand(Hand.MAIN_HAND);

                // 设置恢复原手持延迟 1~2 tick
                stepDelay = 1 + random.nextInt(2);
                step = 2;
                break;

            case 2: // 恢复原手持
                if (stepDelay > 0) {
                    stepDelay--;
                    return;
                }

                if (swapBack.get() && originalSlot != -1) {
                    mc.player.getInventory().setSelectedSlot(originalSlot);
                    mc.interactionManager.syncSelectedSlot();
                }

                // 完成切换，重置状态
                resetState();
                break;
        }
    }

    private boolean handleObsidianPlace() {
        // 确保玩家正在看向一个方块
        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof BlockHitResult)) return false;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos base = blockHit.getBlockPos();

        // 简化：直接放置黑曜石，不再检查是否合法的方块
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHit);
        mc.player.swingHand(Hand.MAIN_HAND);

        return true;
    }

    private int findObsidianSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(Items.OBSIDIAN)) return i;
        }
        return -1;
    }

    private int findEndCrystalSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isOf(Items.END_CRYSTAL)) return i;
        }
        return -1;
    }

    private void resetState() {
        if (swapBack.get() && originalSlot != -1) {
            mc.player.getInventory().setSelectedSlot(originalSlot);
            mc.interactionManager.syncSelectedSlot();
        }
        step = 0;
        stepDelay = 0;
        originalSlot = -1;
        triggered = false;
    }
}
