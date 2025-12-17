package dev.sakura.module.impl.hud;

import dev.sakura.Sakura;
import dev.sakura.manager.impl.NotificationManager;
import dev.sakura.module.HudModule;
import dev.sakura.module.impl.client.HudEditor;
import dev.sakura.nanovg.NanoVGRenderer;
import dev.sakura.values.Value;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.ColorValue;
import dev.sakura.values.impl.EnumValue;
import dev.sakura.values.impl.NumberValue;

import java.awt.*;

public class NotificationHud extends HudModule {
    public enum AlignedEnum {LEFT, RIGHT}

    private final Value<Double> maxWidthConfig = new NumberValue<>("MaxWidth", 300.0, 100.0, 500.0, 10.0);
    private final Value<Color> primaryColorConfig = new ColorValue("PrimaryColor", new Color(255, 183, 197, 255));
    private final Value<Color> backgroundColorConfig = new ColorValue("BackgroundColor", new Color(0, 0, 0, 180));
    private final EnumValue<AlignedEnum> aligned = new EnumValue<>("Aligned", AlignedEnum.RIGHT);
    private final Value<Boolean> backgroundBlur = new BoolValue("BackgroundBlur", false);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", 8.0, 1.0, 20.0, 0.5, backgroundBlur::get);

    public NotificationHud() {
        super("Notification", 10, 10);
    }

    @Override
    public void onRenderContent() {
        NanoVGRenderer.INSTANCE.withRawCoordsAndPause(() -> {
            if (Sakura.MODULE.getModule(HudEditor.class).isEnabled()) {
                float[] size = NotificationManager.renderPreview(
                        getMatrix(),
                        x, y,
                        aligned.is(AlignedEnum.LEFT),
                        primaryColorConfig.get(),
                        backgroundColorConfig.get(),
                        maxWidthConfig.getValue().floatValue(),
                        backgroundBlur.get(),
                        blurStrength.getValue().floatValue()
                );
                width = size[0];
                height = size[1];
            } else {
                NotificationManager.render(
                        getMatrix(),
                        x, y,
                        aligned.is(AlignedEnum.LEFT),
                        primaryColorConfig.get(),
                        backgroundColorConfig.get(),
                        maxWidthConfig.getValue().floatValue(),
                        backgroundBlur.get(),
                        blurStrength.getValue().floatValue()
                );
            }
        });
    }
}
