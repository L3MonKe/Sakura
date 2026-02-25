package dev.mzc.client.module.impl.misc;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.render.smoothswap.SmoothSwapManager;
import dev.mzc.client.values.impl.NumberValue;

public class SmoothSwap extends Module {

    public static SmoothSwap INSTANCE;
    public final NumberValue<Double> animationSpeed = new NumberValue<>("AnimationSpeed", "动画速度", 1.0, 0.1, 5.0, 0.1);

    public SmoothSwap() {
        super("SmoothSwap", "物品交换动画", Category.Misc);
        this.setType(ModuleType.Safe);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        SmoothSwapManager.init();
    }
}
