package com.zeta.client.module.impl.hud;

import com.zeta.client.Zeta;
import com.zeta.client.manager.impl.NotificationManager;
import com.zeta.client.module.HudModule;
import com.zeta.client.module.impl.client.HudEditor;
import com.zeta.client.values.Value;
import com.zeta.client.values.impl.BoolValue;
import com.zeta.client.values.impl.ColorValue;
import com.zeta.client.values.impl.EnumValue;
import com.zeta.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class NotificationHud extends HudModule {
    public enum AlignedEnum {LEFT, RIGHT}

    private final Value<Double> maxWidthConfig = new NumberValue<>("MaxWidth", "最大宽度", 300.0, 100.0, 500.0, 10.0);
    private final Value<Color> primaryColorConfig = new ColorValue("PrimaryColor", "主颜色", new Color(255, 183, 197, 255));
    private final Value<Color> backgroundColorConfig = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 180));
    private final EnumValue<AlignedEnum> aligned = new EnumValue<>("Aligned", "对齐方式", AlignedEnum.RIGHT);
    private final Value<Boolean> backgroundBlur = new BoolValue("BackgroundBlur", "背景模糊", false);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 8.0, 1.0, 20.0, 0.5, backgroundBlur::get);

    public NotificationHud() {
        super("Notification", "通知", 10, 10);
    }

    @Override
    public void onRender(DrawContext context) {
        if (Zeta.MODULES.getModule(HudEditor.class).isEnabled()) {
            float[] size = NotificationManager.renderPreview(
                    context.getMatrices(),
                    x, y,
                    aligned.is(AlignedEnum.LEFT),
                    primaryColorConfig.get(),
                    backgroundColorConfig.get(),
                    maxWidthConfig.get().floatValue(),
                    backgroundBlur.get(),
                    blurStrength.get().floatValue()
            );
            width = size[0];
            height = size[1];
        } else {
            NotificationManager.render(
                    context.getMatrices(),
                    x, y,
                    aligned.is(AlignedEnum.LEFT),
                    primaryColorConfig.get(),
                    backgroundColorConfig.get(),
                    maxWidthConfig.get().floatValue(),
                    backgroundBlur.get(),
                    blurStrength.get().floatValue()
            );
        }
    }
}