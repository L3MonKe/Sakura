package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.player.TimerModule;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class TimeChargeHud extends HudModule {

    private final NumberValue<Double> widthValue = new NumberValue<>("Width", "宽度", 100.0, 50.0, 500.0, 1.0);
    private final NumberValue<Double> heightValue = new NumberValue<>("Height", "高度", 6.0, 2.0, 20.0, 0.5);
    private final NumberValue<Double> radiusValue = new NumberValue<>("Radius", "圆角", 3.0, 0.0, 10.0, 0.5);

    private final ColorValue chargeColor1 = new ColorValue("ChargeColor1", "充能颜色1", new Color(95, 155, 245, 230));
    private final ColorValue chargeColor2 = new ColorValue("ChargeColor2", "充能颜色2", new Color(140, 200, 255, 230));
    private final ColorValue activeColor1 = new ColorValue("ActiveColor1", "消耗颜色1", new Color(255, 100, 100, 230));
    private final ColorValue activeColor2 = new ColorValue("ActiveColor2", "消耗颜色2", new Color(255, 150, 50, 230));

    private final BoolValue glow = new BoolValue("Glow", "发光效果", true);
    private final NumberValue<Double> glowStrength = new NumberValue<>("GlowStrength", "发光强度", 5.0, 1.0, 20.0, 1.0, glow::get);

    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurRadius = new NumberValue<>("BlurRadius", "模糊半径", 10.0, 1.0, 20.0, 1.0, blur::get);
    private final NumberValue<Double> padding = new NumberValue<>("Padding", "背景边距", 5.0, 0.0, 20.0, 0.5);

    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18, 70);

    public TimeChargeHud() {
        super("TimeCharge", "蓄力条", 200, 200);

        // 默认位置：屏幕底部居中
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        this.x = (sw - 100) / 2f;
        this.y = sh - 40f;
    }

    @Override
    public void onRender(DrawContext context) {
        TimerModule timerModule = Mahiro.MODULES.getModule(TimerModule.class);

        // 仅当 TimerModule 和 TimeChargeHud 同时开启时显示
        if (timerModule == null || !timerModule.isEnabled()) return;

        // 更新尺寸
        this.width = widthValue.get().floatValue();
        this.height = heightValue.get().floatValue() + 15; // 增加高度以容纳标题和百分比
        float r = radiusValue.get().floatValue();
        float pad = padding.get().floatValue();

        double progress = timerModule.getProgress();
        boolean active = timerModule.isActive();

        // 1. 绘制模糊背景 (如果启用)
        if (blur.get()) {
            Shader2DUtil.drawRoundedBlur(
                    context.getMatrices(),
                    x - pad,
                    y - 5 - pad,
                    width + pad * 2,
                    height + 4 + pad * 2,
                    r + pad,
                    new Color(0, 0, 0, 0), // 纯模糊，无颜色叠加
                    blurRadius.get().floatValue(),
                    1.0f
            );
        }

        // 2. 绘制 NanoVG 内容
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.save();

            // 整个模块的背景
            NanoVGHelper.drawRoundRect(x - pad, y - 5 - pad, width + pad * 2, height + 4 + pad * 2, r + pad, BACKGROUND_COLOR);

            // 标题 "Timer"
            int font = FontLoader.medium(12);
            NanoVGHelper.drawCenteredString("Timer", x + width / 2f, y + 2, font, 12, Color.WHITE);

            // 进度条区域 Y 偏移
            float barY = y + 15;
            float barH = heightValue.get().floatValue();

            // 进度条背景
            Color bg = new Color(35, 35, 38, 160);
            NanoVGHelper.drawRoundRect(x + 5, barY, width - 10, barH, r, bg);

            // 边框
            Color outline = new Color(20, 20, 22, 220);
            NanoVGHelper.drawRoundRectOutline(x + 5, barY, width - 10, barH, r, 1.2f, outline);

            // 进度条
            float pw = (float) ((width - 10) * Math.max(0.0, Math.min(1.0, progress)));

            if (pw > 0f) {
                Color c1, c2;
                if (active) {
                    c1 = activeColor1.get();
                    c2 = activeColor2.get();
                } else {
                    c1 = chargeColor1.get();
                    c2 = chargeColor2.get();
                }

                // 发光效果
                if (glow.get()) {
                    float strength = glowStrength.get().floatValue();
                    for (float i = 0.5f; i <= strength; i += 0.5f) {
                        float normalizedDist = i / (strength + 2);
                        float alphaFactor = 1.0f - (normalizedDist * normalizedDist);
                        float alpha = alphaFactor * 0.15f;
                        int alphaInt = MathHelper.clamp((int) (alpha * 255), 0, 255);

                        if (alphaInt > 0) {
                            Color gc1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alphaInt);
                            Color gc2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alphaInt);
                            NanoVGHelper.drawGradientRRect2(x + 5 - i, barY - i, pw + i * 2, barH + i * 2, r + i, gc1, gc2);
                        }
                    }
                }

                // 绘制进度条
                NanoVGHelper.drawGradientRRect2(x + 5, barY, pw, barH, r, c1, c2);
            }

            // 百分比文本
            String percentText = String.format("%.0f%%", progress * 100);
            int smallFont = dev.mahiro.client.nanovg.font.FontLoader.medium(10);
            NanoVGHelper.drawCenteredString(percentText, x + width / 2f, barY + barH / 2f + 1, smallFont, 10, Color.WHITE);

            NanoVGHelper.restore();
        });
    }
}
