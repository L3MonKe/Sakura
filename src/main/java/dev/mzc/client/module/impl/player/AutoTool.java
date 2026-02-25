package dev.mzc.client.module.impl.player;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;

public class AutoTool extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    // Sakura 风格延迟配置
    private final NumberValue<Double> switchDelay = new NumberValue<>("SwitchDelay", "切换延迟", 2.0, 0.0, 10.0, 0.1);
    private final NumberValue<Double> switchBackDelay = new NumberValue<>("SwitchBackDelay", "切回延迟", 2.0, 0.0, 10.0, 0.1);

    private int lastSlot = -1;
    private boolean swapped = false;

    // 独立计数器
    private int switchCounter = 0;
    private int switchBackCounter = 0;

    public AutoTool() {
        super("AutoTool", "自动工具", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled() || mc.player == null || mc.world == null) return;
            handleAutoTool();
        });
    }

    @Override
    public void onEnable() {
        swapped = false;
        lastSlot = -1;
        switchCounter = 0;
        switchBackCounter = 0;
    }

    @Override
    public void onDisable() {
        switchCounter = 0;
        switchBackCounter = 0;
    }

    private void handleAutoTool() {
        PlayerEntity p = mc.player;
        if (p == null || mc.interactionManager == null) return;

        // 如果在切换延迟中，先等待
        if (switchCounter > 0) {
            switchCounter--;
            return;
        }

        // 如果在切回延迟中，先等待
        if (switchBackCounter > 0) {
            switchBackCounter--;
            return;
        }

        if (mc.crosshairTarget instanceof BlockHitResult hit && mc.options.attackKey.isPressed()) {
            BlockState state = mc.world.getBlockState(hit.getBlockPos());
            int bestSlot = getBestToolSlot(state);

            if (bestSlot != -1 && p.getInventory().selectedSlot != bestSlot) {
                if (!swapped) {
                    lastSlot = p.getInventory().selectedSlot;
                    swapped = true;
                }

                // 切换工具
                p.getInventory().selectedSlot = bestSlot;
                switchCounter = switchDelay.get().intValue(); // 切换延迟
            }
        } else {
            // 切回原来工具
            if (swapped && lastSlot != -1 && p.getInventory().selectedSlot != lastSlot) {
                p.getInventory().selectedSlot = lastSlot;
                swapped = false;
                switchBackCounter = switchBackDelay.get().intValue(); // 切回延迟
            }
        }
    }

    // 只选择挖掘速度最高的工具
    private int getBestToolSlot(BlockState state) {
        PlayerEntity p = mc.player;
        if (p == null) return -1;

        int bestSlot = -1;
        float bestSpeed = 1; // 小于等于1的速度 Minecraft 认为不适用

        for (int i = 0; i < 9; i++) {
            ItemStack stack = p.getInventory().getStack(i);
            float speed = stack.getMiningSpeedMultiplier(state);

            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }

        return bestSlot;
    }
}
