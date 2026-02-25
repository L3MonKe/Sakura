package dev.mzc.client.module.impl.render;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;

import java.awt.*;

public class Atmosphere extends Module {
    public Atmosphere() {
        super("Atmosphere", "天空渲染", Category.Render);
        this.setType(ModuleType.All);
    }

    public final BoolValue modifyTime = new BoolValue("Modify Time", "修改时间", false);
    public final BoolValue modifyFog = new BoolValue("Modify Fog", "修改天空", false);
    public final NumberValue<Integer> time = new NumberValue<>("Time", "时间", 12000, 0, 24000, 1000);
    public final ColorValue fogColor = new ColorValue("Fog Color", "天空颜色", new Color(255, 255, 255));


}