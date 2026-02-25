package dev.mzc.client.module.impl.misc;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

public class AutoRespawn extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    private int savedSlot = 0;
    private float savedYaw = 0;
    private float savedPitch = 0;

    private boolean waitingRestore = false;
    private int restoreTicks = 0;

    public AutoRespawn() {
        super("AutoRespawn","自动重生", Category.Misc);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled() || mc.player == null) return;
            tick();
        });
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    private void tick() {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;

        // 检测死亡界面 → 先保存
        if (mc.currentScreen instanceof DeathScreen) {

            // 保存死亡前状态
            savedSlot = p.getInventory().selectedSlot;
            savedYaw = p.getYaw();
            savedPitch = p.getPitch();

            // 进行重生
            p.requestRespawn();
            mc.setScreen(null);

            // 标记进入恢复等待
            waitingRestore = true;
            restoreTicks = 2; // 延迟 2 tick 保证实体初始化完毕
            return;
        }

        // 重生完成 → 延迟恢复状态
        if (waitingRestore) {
            if (restoreTicks > 0) {
                restoreTicks--;
                return;
            }

            // 恢复视角和物品栏
            p.getInventory().setSelectedSlot(savedSlot);
            p.setYaw(savedYaw);
            p.setPitch(savedPitch);

            waitingRestore = false;
        }
    }
}