package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;

public class MotionBlur extends Module {
    public MotionBlur() {
        super("MotionBlur", "动态模糊", Category.Render);
    }

    public final NumberValue<Double> blurAmount = new NumberValue<>("Blur Amount", "模糊程度", 20.0, 1.0, 100.0, 10.0);
}
