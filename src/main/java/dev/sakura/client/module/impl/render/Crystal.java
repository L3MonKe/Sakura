package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.util.Identifier;

import java.awt.*;

public class Crystal extends Module {


    public Crystal() {
        super("Crystal", "水晶模型", Category.Render);
    }

    public final BoolValue Texture = new BoolValue("Texture", "修改颜色", false);
    public final BoolValue modifyScale = new BoolValue("Modify Scale", "修改大小", false);
    public final NumberValue<Float> scale = new NumberValue<>("Scale", "大小", 1.0f, 0.1f, 3.0f, 0.1f);
    public final ColorValue crystalColor = new ColorValue("Crystal Color", "水晶颜色", new Color(255, 255, 255, 255));

    public final BoolValue enableBreathing = new BoolValue("Breathing Effect", "呼吸效果", true);
    public final NumberValue<Float> breathingSpeed = new NumberValue<>("Breathing Speed", "呼吸速度", 1.0f, 0.1f, 5.0f, 0.1f);
    public final NumberValue<Float> breathingAmount = new NumberValue<>("Breathing Amount", "呼吸幅度", 0.2f, 0.0f, 1.0f, 0.05f);

    public final BoolValue enableRotation = new BoolValue("Rotation Effect", "旋转效果", true);
    public final NumberValue<Float> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 1.0f, 0.1f, 10.0f, 0.1f);


    public static final Identifier BLANK = Identifier.of("textures/blank.png");
}