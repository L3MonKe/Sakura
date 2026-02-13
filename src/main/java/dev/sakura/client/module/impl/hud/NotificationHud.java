package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.manager.impl.NotificationManager;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class NotificationHud extends HudModule {
    public enum AlignedEnum {LEFT, RIGHT}

    private final EnumValue<NotificationManager.RenderMode> mode = new EnumValue<>("Mode", "模式", NotificationManager.RenderMode.Xylitol4);
    private final Value<Double> maxWidthConfig = new NumberValue<>("MaxWidth", "最大宽度", 300.0, 100.0, 500.0, 10.0, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Color> primaryColorConfig = new ColorValue("PrimaryColor", "主颜色", new Color(255, 183, 197, 255), () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Color> backgroundColorConfig = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 180), () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final EnumValue<AlignedEnum> aligned = new EnumValue<>("Aligned", "对齐方式", AlignedEnum.RIGHT, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Double> fontSize = new NumberValue<>("FontSize", "字体大小", 18.0, 10.0, 32.0, 1.0, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Double> scale = new NumberValue<>("Scale", "整体大小", 1.0, 0.5, 2.5, 0.05, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Double> xylitol4LineLength = new NumberValue<>("LineLength", "竖线长度", 8.0, 2.0, 40.0, 0.5, () -> false);
    private final Value<Boolean> backgroundBlur = new BoolValue("BackgroundBlur", "背景模糊", false, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> !mode.is(NotificationManager.RenderMode.Xylitol3) && !mode.is(NotificationManager.RenderMode.Xylitol4) && backgroundBlur.get());
    private final Value<Boolean> notificationShadow = new BoolValue("NotificationShadow", "通知阴影", true, () -> mode.is(NotificationManager.RenderMode.Xylitol3) || mode.is(NotificationManager.RenderMode.Xylitol4));
    private final Value<Double> shadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 0.5, () -> notificationShadow.get() && (mode.is(NotificationManager.RenderMode.Xylitol3) || mode.is(NotificationManager.RenderMode.Xylitol4)));
    private final Value<Double> shadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.01, () -> notificationShadow.get() && (mode.is(NotificationManager.RenderMode.Xylitol3) || mode.is(NotificationManager.RenderMode.Xylitol4)));

    public NotificationHud() {
        super("Notification", "通知", 10, 10);
    }

    @Override
    public void onRender(DrawContext context) {
        NotificationManager.RenderMode currentMode = mode.get();
        boolean hardcodedXylitol3 = currentMode == NotificationManager.RenderMode.Xylitol3;
        boolean hardcodedXylitol4 = currentMode == NotificationManager.RenderMode.Xylitol4;

        NotificationManager.Alignment alignmentValue = hardcodedXylitol3
                ? NotificationManager.XYLITOL3_HARDCODED_ALIGNMENT
                : hardcodedXylitol4
                ? NotificationManager.XYLITOL4_HARDCODED_ALIGNMENT
                : (aligned.is(AlignedEnum.LEFT) ? NotificationManager.Alignment.LEFT : NotificationManager.Alignment.RIGHT);

        Color primary = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_PRIMARY_COLOR : primaryColorConfig.get();
        Color background = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_BACKGROUND_COLOR : backgroundColorConfig.get();
        float maxWidth = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_MAX_WIDTH : maxWidthConfig.get().floatValue();
        boolean blur = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_BLUR : backgroundBlur.get();
        float blurStrengthValue = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_BLUR_STRENGTH : blurStrength.get().floatValue();
        float scaleValue = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_SCALE : scale.get().floatValue();
        float fontSizeValue = hardcodedXylitol3 ? NotificationManager.XYLITOL3_HARDCODED_FONT_SIZE : fontSize.get().floatValue();
        float lineLengthValue = hardcodedXylitol4 ? NotificationManager.XYLITOL4_HARDCODED_LINE_LENGTH : xylitol4LineLength.get().floatValue();
        if (hardcodedXylitol4) {
            maxWidth = NotificationManager.XYLITOL4_HARDCODED_MAX_WIDTH;
            blur = NotificationManager.XYLITOL4_HARDCODED_BLUR;
            blurStrengthValue = NotificationManager.XYLITOL4_HARDCODED_BLUR_STRENGTH;
            scaleValue = NotificationManager.XYLITOL4_HARDCODED_SCALE;
            fontSizeValue = NotificationManager.XYLITOL4_HARDCODED_FONT_SIZE;
        }
        NotificationManager.Xylitol4Offsets xylitol4Offsets = currentMode == NotificationManager.RenderMode.Xylitol4 ? NotificationManager.XYLITOL4_HARDCODED_OFFSETS : NotificationManager.Xylitol4Offsets.ZERO;
        NotificationManager.ShadowSettings shadowSettings = new NotificationManager.ShadowSettings(notificationShadow.get(), shadowRange.get().floatValue(), shadowStrength.get().floatValue());

        if (Sakura.MODULES.getModule(HudEditor.class).isEnabled()) {
            float[] size = NotificationManager.renderPreview(
                    context.getMatrices(),
                    x, y,
                    currentMode,
                    primary,
                    background,
                    maxWidth,
                    blur,
                    blurStrengthValue,
                    scaleValue,
                    fontSizeValue,
                    lineLengthValue,
                    alignmentValue,
                    xylitol4Offsets,
                    shadowSettings
            );
            width = size[0];
            height = size[1];
        } else {
            NotificationManager.render(
                    context.getMatrices(),
                    x, y,
                    currentMode,
                    primary,
                    background,
                    maxWidth,
                    blur,
                    blurStrengthValue,
                    scaleValue,
                    fontSizeValue,
                    lineLengthValue,
                    alignmentValue,
                    xylitol4Offsets,
                    shadowSettings
            );
        }
    }
}
