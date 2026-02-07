package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;

public class Rainy extends Module {
    public Rainy() {
        super("Rainy", "下雨", Category.Render);
    }

    private enum Mode {
        Sakura
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Sakura);
}
