package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public class Spider extends Module {
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "爬行速度", 0.2, 0.1, 1.0, 0.05);

    public enum Mode {
        Vanilla, Motion, Vulcan
    }

    public Spider() {
        super("Spider", "蜘蛛爬墙", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        setSuffix(mode.get().name());

        if (!mc.player.horizontalCollision) return;

        switch (mode.get()) {
            case Vanilla:
                mc.player.getVelocity();
                mc.player.setVelocity(mc.player.getVelocity().x, speed.get(), mc.player.getVelocity().z);
                break;
            case Motion:
                if (mc.player.getVelocity().y < speed.get()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, speed.get(), mc.player.getVelocity().z);
                }
                break;
            case Vulcan:
                if (mc.player.age % 2 == 0) {
                    mc.player.setVelocity(mc.player.getVelocity().x, speed.get(), mc.player.getVelocity().z);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x, -0.01, mc.player.getVelocity().z);
                }
                break;
        }
    }
}
