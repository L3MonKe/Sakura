package dev.mzc.client.module.impl.render;


import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;

public class AspectRatio extends Module {
    public AspectRatio() {
        super("AspectRatio", "画面比例", Category.Render);
        this.setType(ModuleType.All);
    }

    public NumberValue<Double> ratio = new NumberValue<>("Ratio", "比例", 1.78, 0.01, 5.0, 0.01);
}
