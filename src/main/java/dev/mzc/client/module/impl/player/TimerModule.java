package dev.mzc.client.module.impl.player;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;

public class TimerModule extends Module {
    public TimerModule() {
        super("Timer", "变速", Category.Player);
        this.setType(ModuleType.Hack);
    }

    public final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 5.0, 0.1);

    public float getTimerSpeed() {
        if (isEnabled()) {
            return speed.get().floatValue();
        }
        return 1.0f;
    }
}
