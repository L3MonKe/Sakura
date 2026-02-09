package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;

public class NoFov extends Module {
    public final NumberValue<Double> fov = new NumberValue<>("FOV", "视场角", 120.0, 30.0, 160.0, 1.0);

    public NoFov() {
        super("NoFov", "自定义视场", Category.Render);
    }
}
