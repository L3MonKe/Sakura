package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class WorldTweaks extends Module {
    public WorldTweaks() {
        super("WorldTweaks", "世界调整", Category.Render);
    }

    public final BoolValue fogModify = new BoolValue("Fog Modify", "雾修改", true);
    public final NumberValue<Integer> fogStart = new NumberValue<>("Fog Start", "雾起点", 0, 0, 256, 1, fogModify::get);
    public final NumberValue<Integer> fogEnd = new NumberValue<>("Fog End", "雾终点", 64, 10, 256, 1, fogModify::get);
    public final ColorValue fogColor = new ColorValue("Fog Color", "雾颜色", new Color(0xA900FF), fogModify::get);

    public final BoolValue modifyTime = new BoolValue("Modify Time", "修改时间", false);
    public final NumberValue<Integer> time = new NumberValue<>("Time", "时间", 12000, 0, 24000, 1000);
}
