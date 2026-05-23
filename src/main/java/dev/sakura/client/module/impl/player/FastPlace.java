package dev.sakura.client.module.impl.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.mixin.accessor.IMinecraftClient;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.HitResult;

public class FastPlace extends Module {
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "放置延迟", 0, 0, 3, 1);
    private int originalRightClickDelay;

    public FastPlace() {
        super("FastPlace", "快速放置", Category.Player);
    }

    @Override
    public void onEnable() {
        originalRightClickDelay = ((IMinecraftClient) mc).getItemUseCooldown();
    }

    @Override
    public void onDisable() {
        ((IMinecraftClient) mc).setItemUseCooldown(originalRightClickDelay);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player != null && mc.world != null) {
            if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) {
                return;
            }

            if (mc.crosshairTarget != null && (mc.player.getMainHandStack().getItem() instanceof BlockItem || mc.player.getOffHandStack().getItem() instanceof BlockItem) && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                if (((IMinecraftClient) mc).getItemUseCooldown() != 0) {
                    ((IMinecraftClient) mc).setItemUseCooldown(delay.get());
                }
            }
        }
    }
}
