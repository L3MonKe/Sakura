package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class WorldTweaks extends Module {
    public WorldTweaks() {
        super("WorldTweaks", "世界调整", Category.Render);
    }

    public enum Shader {
        None,
        Grid,
        Warp
    }

    public final BoolValue fogModify = new BoolValue("Fog Modify", "雾修改", true);
    public final NumberValue<Integer> fogStart = new NumberValue<>("Fog Start", "雾起点", 0, 0, 256, 1, fogModify::get);
    public final NumberValue<Integer> fogEnd = new NumberValue<>("Fog End", "雾终点", 64, 10, 256, 1, fogModify::get);
    public final ColorValue fogColor = new ColorValue("Fog Color", "雾颜色", new Color(0xA900FF), fogModify::get);

    public final EnumValue<Shader> shaderMode = new EnumValue<>("Shader Mode", "着色器模式", Shader.None);
    public final NumberValue<Float> skyGridAlpha = new NumberValue<>("Sky Grid Alpha", "天空网格不透明度", 1.0f, 0.0f, 1.0f, 0.01f, () -> shaderMode.is(Shader.Grid));
    public final EnumValue<SkyGridFacing> facing = new EnumValue<>("Facing", "方位", SkyGridFacing.South, () -> shaderMode.is(Shader.Grid));

    public final BoolValue modifyTime = new BoolValue("Modify Time", "修改时间", false);
    public final NumberValue<Integer> time = new NumberValue<>("Time", "时间", 12000, 0, 24000, 1000);

    public enum SkyGridFacing {
        South(0.0f),
        West(90.0f),
        North(180.0f),
        East(-90.0f);

        public final float yawOffsetDegrees;

        SkyGridFacing(float yawOffsetDegrees) {
            this.yawOffsetDegrees = yawOffsetDegrees;
        }
    }
}
