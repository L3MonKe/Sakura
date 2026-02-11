package dev.sakura.client.module.impl.settings;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.animations.Easing;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;

public class RenderSetting extends Module {
    public RenderSetting() {
        super("Render", "渲染", Category.Settings);
    }

    public static final EnumValue<Easing> easing = new EnumValue<>("Easing", "Easing", Easing.EXPO_IN_OUT);
    public static final NumberValue<Integer> fadeTime = new NumberValue<>("FadeTime", "淡出时间", 500, 0, 3000, 50);
}
