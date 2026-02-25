package dev.mzc.client.module.impl.player;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

import java.util.Random;


public class AutoWindBomb extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    private int swapBackDelay = 0;
    private int originalSlot = -1;
    private boolean waitingSwapBack = false;

    public AutoWindBomb() {
        super("AutoWindBomb", "自动风弹", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (!isEnabled() || mc.player == null || mc.interactionManager == null) return;
            onTick();
        });
    }

    @Override
    public void onEnable() {
        swapBackDelay = 0;
        originalSlot = -1;
        waitingSwapBack = false;
    }

    @Override
    public void onDisable() {
        if (waitingSwapBack && mc.player != null && originalSlot != -1) {
            mc.player.getInventory().setSelectedSlot(originalSlot);
        }

        swapBackDelay = 0;
        waitingSwapBack = false;
        originalSlot = -1;
    }

    private void onTick() {

        // ======== 处理延迟切回 ========
        if (swapBackDelay > 0) {
            swapBackDelay--;
            if (swapBackDelay == 0 && waitingSwapBack && originalSlot != -1) {
                mc.player.getInventory().setSelectedSlot(originalSlot);
                waitingSwapBack = false;
            }
            return;
        }

        // ======== 已经在等待切回，不再触发 ========
        if (waitingSwapBack) return;

        // ======== 右键触发一次 ========
        if (!mc.mouse.wasRightButtonClicked()) return;

        ItemStack main = mc.player.getMainHandStack();

        // ======== 主手已经是风弹 / 药水 / 经验瓶 / 食物 → 不触发（安全保护） ========
        if (isWindCharge(main)) return;
        if (main.getItem() instanceof PotionItem
                || main.getItem() instanceof ExperienceBottleItem
                || main.getItem() instanceof EnderPearlItem
                || main.isOf(Items.GOLDEN_APPLE)
                || main.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || main.getItem() instanceof BowItem){

            return;
        }

        /* ======== 正在举盾时不执行 ======== */
        if (mc.player.isBlocking()) return;

        // ======== 准星检测 ========
        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof BlockHitResult blockHit)) return;

        // ======== 找风弹 ========
        int bombSlot = findWindBombSlot();
        if (bombSlot == -1) return;

        // ======== 执行：切风弹 → 右键放置 ========
        originalSlot = mc.player.getInventory().selectedSlot;
        mc.player.getInventory().setSelectedSlot(bombSlot);

        if (mc.interactionManager != null) {
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        }

        // ======== 设置 1~3 tick 后切回 ========
        swapBackDelay = 1 + random.nextInt(3);
        waitingSwapBack = true;
    }

    private int findWindBombSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (isWindCharge(stack)) return i;
        }
        return -1;
    }

    private boolean isWindCharge(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return Registries.ITEM.getId(stack.getItem()).getPath().equalsIgnoreCase("wind_charge");
    }
}