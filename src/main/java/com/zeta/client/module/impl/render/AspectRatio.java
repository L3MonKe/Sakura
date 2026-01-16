package com.zeta.client.module.impl.render;


import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.values.impl.NumberValue;

public class AspectRatio extends Module {
    public AspectRatio() {
        super("AspectRatio", "画面比例", Category.Render);
    }

    public NumberValue<Double> ratio = new NumberValue<>("Ratio", "比例", 1.78, 0.01, 5.0, 0.01);
}
