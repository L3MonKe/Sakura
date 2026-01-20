package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.NumberValue;

import java.awt.*;

public class Atmosphere extends Module {
    public Atmosphere() {
        super("Atmosphere", "天空渲染", Category.Render);
    }

    public final BoolValue modifyTime = new BoolValue("Modify Time", "修改时间", false);
    public final BoolValue modifyFog = new BoolValue("Modify Fog", "修改天空", false);
    public final NumberValue<Integer> time = new NumberValue<>("Time", "时间", 12000, 0, 24000, 1000);
    public final ColorValue fogColor = new ColorValue("Fog Color", "天空颜色", new Color(255, 255, 255));


}