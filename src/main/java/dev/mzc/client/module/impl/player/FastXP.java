package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.mixin.accessor.IMinecraftClient;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;

public class FastXP extends Module {

    private final NumberValue<Integer> cooldown = new NumberValue<>("Cooldown", "投掷冷却(Tick)", 0, 0, 4, 1);

    public FastXP() {
        super("FastXP", "快速经验瓶", Category.Player);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 检查主手或副手是否持有经验瓶
        boolean holdingXP = mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE ||
                            mc.player.getOffHandStack().getItem() == Items.EXPERIENCE_BOTTLE;

        if (holdingXP && mc.options.useKey.isPressed()) {
            if (mc instanceof IMinecraftClient accessor) {
                // 如果当前冷却时间大于我们设定的值，就强制减少冷却
                if (accessor.hookGetItemUseCooldown() > cooldown.get()) {
                    accessor.hookSetItemUseCooldown(cooldown.get());
                }
            }
        }
    }
}
