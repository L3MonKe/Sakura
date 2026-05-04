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

    public enum SakuraAnimationModeEnum {Classic, Enhanced}

    public enum NotificationShadowModeEnum {Solid, Gradient}

    private final EnumValue<NotificationManager.RenderMode> mode = new EnumValue<>("Mode", "模式", NotificationManager.RenderMode.Sakura);
    private final Value<Double> maxWidthConfig = new NumberValue<>("MaxWidth", "最大宽度", 300.0, 100.0, 500.0, 10.0);
    private final Value<Color> primaryColorConfig = new ColorValue("PrimaryColor", "主颜色", new Color(255, 183, 197, 255));
    private final Value<Color> backgroundColorConfig = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 180));
    private final EnumValue<AlignedEnum> aligned = new EnumValue<>("Aligned", "对齐方式", AlignedEnum.RIGHT);
    private final Value<Double> fontSize = new NumberValue<>("FontSize", "字体大小", 18.0, 10.0, 32.0, 1.0);
    private final Value<Double> scale = new NumberValue<>("Scale", "整体大小", 1.0, 0.5, 2.5, 0.05);
    private final Value<Boolean> backgroundBlur = new BoolValue("BackgroundBlur", "背景模糊", false);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 8.0, 1.0, 20.0, 0.5, () -> backgroundBlur.get());
    private final Value<Boolean> notificationShadow = new BoolValue("NotificationShadow", "通知阴影", true, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final EnumValue<NotificationShadowModeEnum> notificationShadowMode = new EnumValue<>("NotificationShadowMode", "阴影模式", NotificationShadowModeEnum.Solid, () -> notificationShadow.get());
    private final Value<Double> shadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 0.5, () -> notificationShadow.get());
    private final Value<Double> shadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.01, () -> notificationShadow.get());
    private final Value<Double> xylitol4CornerRadius = new NumberValue<>("CornerRadius", "圆角", 0.0, 0.0, 30.0, 0.5);
    private final Value<Double> simpleTextShadowDistance = new NumberValue<>("TextShadowDistance", "字体阴影间距", 1.0, 0.0, 8.0, 0.1, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final Value<Boolean> simpleIconGlow = new BoolValue("IconGlow", "图标Glow", false, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final Value<Double> simpleIconGlowRange = new NumberValue<>("IconGlowRange", "图标Glow范围", 4.0, 0.0, 20.0, 0.5, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleIconGlow.get());
    private final Value<Integer> simpleIconGlowIntensity = new NumberValue<>("IconGlowIntensity", "图标Glow强度", 2, 1, 10, 1, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleIconGlow.get());
    private final Value<Boolean> simpleLine1Glow = new BoolValue("Line1Glow", "第一行Glow", false, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final Value<Double> simpleLine1GlowRange = new NumberValue<>("Line1GlowRange", "第一行Glow范围", 4.0, 0.0, 20.0, 0.5, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleLine1Glow.get());
    private final Value<Integer> simpleLine1GlowIntensity = new NumberValue<>("Line1GlowIntensity", "第一行Glow强度", 2, 1, 10, 1, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleLine1Glow.get());
    private final Value<Boolean> simpleLine2Glow = new BoolValue("Line2Glow", "第二行Glow", false, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final Value<Double> simpleLine2GlowRange = new NumberValue<>("Line2GlowRange", "第二行Glow范围", 4.0, 0.0, 20.0, 0.5, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleLine2Glow.get());
    private final Value<Integer> simpleLine2GlowIntensity = new NumberValue<>("Line2GlowIntensity", "第二行Glow强度", 2, 1, 10, 1, () -> mode.is(NotificationManager.RenderMode.Sakura) && simpleLine2Glow.get());
    private final Value<Boolean> simpleImageEnabled = new BoolValue("Image", "图片", true, () -> mode.is(NotificationManager.RenderMode.Sakura));
    private final EnumValue<SakuraAnimationModeEnum> simpleAnimationMode = new EnumValue<>("SakuraAnim", "Sakura动画", SakuraAnimationModeEnum.Enhanced, () -> mode.is(NotificationManager.RenderMode.Sakura));

    public NotificationHud() {
        super("Notification", "通知", 10, 10);
    }

    @Override
    public void onRender(DrawContext context) {
        NotificationManager.RenderMode currentMode = mode.get();

        NotificationManager.Alignment alignmentValue = aligned.is(AlignedEnum.LEFT) ? NotificationManager.Alignment.LEFT : NotificationManager.Alignment.RIGHT;

        Color primary = primaryColorConfig.get();
        Color background = backgroundColorConfig.get();
        float maxWidth = maxWidthConfig.get().floatValue();
        boolean blur = backgroundBlur.get();
        float blurStrengthValue = blurStrength.get().floatValue();
        float scaleValue = scale.get().floatValue();
        float fontSizeValue = fontSize.get().floatValue();
        float lineLengthValue = 0.0f;
        NotificationManager.Xylitol4Offsets xylitol4Offsets = NotificationManager.Xylitol4Offsets.ZERO;
        NotificationManager.ShadowMode shadowModeValue = notificationShadowMode.is(NotificationShadowModeEnum.Gradient) ? NotificationManager.ShadowMode.Gradient : NotificationManager.ShadowMode.Solid;
        NotificationManager.ShadowSettings shadowSettings = new NotificationManager.ShadowSettings(notificationShadow.get(), shadowRange.get().floatValue(), shadowStrength.get().floatValue(), shadowModeValue);
        float cornerRadius = currentMode == NotificationManager.RenderMode.Sakura ? xylitol4CornerRadius.get().floatValue() : 0.0f;
        NotificationManager.SimpleIconSettings simpleIconSettings = new NotificationManager.SimpleIconSettings(
                simpleAnimationMode.is(SakuraAnimationModeEnum.Classic) ? NotificationManager.SakuraAnimationMode.Classic : NotificationManager.SakuraAnimationMode.Enhanced,
                simpleTextShadowDistance.get().floatValue(),
                simpleIconGlow.get(),
                simpleIconGlowRange.get().floatValue(),
                simpleIconGlowIntensity.get(),
                simpleLine1Glow.get(),
                simpleLine1GlowRange.get().floatValue(),
                simpleLine1GlowIntensity.get(),
                simpleLine2Glow.get(),
                simpleLine2GlowRange.get().floatValue(),
                simpleLine2GlowIntensity.get(),
                simpleImageEnabled.get()
        );

        float renderX = x;

        if (Sakura.MODULES.getModule(HudEditor.class).isEnabled()) {
            float[] size = NotificationManager.renderPreview(
                    context.getMatrices(),
                    renderX, y,
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
                    shadowSettings,
                    cornerRadius,
                    simpleIconSettings
            );
            width = size[0];
            height = size[1];
        } else {
            NotificationManager.render(
                    context.getMatrices(),
                    renderX, y,
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
                    shadowSettings,
                    cornerRadius,
                    simpleIconSettings
            );
        }
    }

    public boolean isSakuraMode() {
        return mode.is(NotificationManager.RenderMode.Sakura);
    }
}
