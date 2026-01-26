package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.NumberValue;

public class MotionBlur extends Module {
    public MotionBlur() {
        super("MotionBlur", "动态模糊", Category.Render);
    }

    public final NumberValue<Double> blurAmount = new NumberValue<>("Blur Amount", "模糊程度", 20.0, 1.0, 100.0, 10.0);
}
