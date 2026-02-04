package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.player.TimerModule;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class TimeChargeHud extends HudModule {

    public enum Mode {
        Old, New
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Old);

    private final NumberValue<Double> widthValue = new NumberValue<>("Width", "宽度", 100.0, 50.0, 500.0, 1.0);
    private final NumberValue<Double> heightValue = new NumberValue<>("Height", "高度", 6.0, 2.0, 20.0, 0.5);
    private final NumberValue<Double> radiusValue = new NumberValue<>("Radius", "圆角", 3.0, 0.0, 10.0, 0.5);

    private final ColorValue chargeColor1 = new ColorValue("Charge Color1", "充能颜色1", new Color(95, 155, 245, 230));
    private final ColorValue chargeColor2 = new ColorValue("Charge Color2", "充能颜色2", new Color(140, 200, 255, 230));

    // Old 模式专用
    private final ColorValue activeColor1 = new ColorValue("Active Color1", "消耗颜色1", new Color(255, 100, 100, 230), () -> mode.is(Mode.Old));
    private final ColorValue activeColor2 = new ColorValue("Active Color2", "消耗颜色2", new Color(255, 150, 50, 230), () -> mode.is(Mode.Old));

    private final BoolValue glow = new BoolValue("Glow", "发光效果", true);
    private final NumberValue<Double> glowStrength = new NumberValue<>("Glow Strength", "发光强度", 5.0, 1.0, 20.0, 1.0, glow::get);

    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurRadius = new NumberValue<>("Blur Radius", "模糊半径", 10.0, 1.0, 20.0, 1.0, blur::get);

    private final BoolValue bloom = new BoolValue("Bloom", "外发光", true);
    private final NumberValue<Double> bloomRadius = new NumberValue<>("Bloom Radius", "发光半径", 5.0, 1.0, 20.0, 1.0, bloom::get);
    private final ColorValue bloomColor = new ColorValue("Bloom Color", "外发光颜色", new Color(0, 0, 0, 100), bloom::get);

    private final NumberValue<Double> padding = new NumberValue<>("Padding", "背景边距", 5.0, 0.0, 20.0, 0.5);

    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18, 70);

    public TimeChargeHud() {
        super("TimeCharge", "Timer蓄力条", 200, 200);

        // 默认位置：屏幕底部居中
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        this.x = (sw - 100) / 2f;
        this.y = sh - 40f;
    }

    @Override
    public void onRender(DrawContext context) {
        TimerModule timerModule = Mahiro.MODULES.getModule(TimerModule.class);
        if (!timerModule.isEnabled()) return;

        // 更新尺寸
        this.width = widthValue.get().floatValue();

        // New 模式下增加高度以容纳下方的百分比文本
        float extraHeight = mode.is(Mode.New) ? 25 : 15;
        this.height = heightValue.get().floatValue() + extraHeight;

        float r = radiusValue.get().floatValue();
        float pad = padding.get().floatValue();

        float bgX = x - pad;
        float bgY = y - 5 - pad;
        float bgW = width + pad * 2;
        float bgH = height + 4 + pad * 2;
        float bgR = r + pad;

        double progress = timerModule.getProgress();
        boolean active = timerModule.isActive();

        // 1. 绘制模糊背景 (如果启用)
        if (blur.get()) {
            Shader2DUtil.drawRoundedBlur(bgX, bgY, bgW, bgH, bgR, new Color(0, 0, 0, 0), blurRadius.get().floatValue(), 1.0f);
        }

        // 2. 绘制 NanoVG 内容
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.save();

            // 整个模块的背景
            if (bloom.get()) {
                NanoVGHelper.drawRoundRectBloom(bgX, bgY, bgW, bgH, bgR, bloomRadius.get().floatValue(), BACKGROUND_COLOR);
            } else {
                NanoVGHelper.drawRoundRect(bgX, bgY, bgW, bgH, bgR, BACKGROUND_COLOR);
            }

            // 标题 "Timer"
            int font = FontLoader.medium(12);
            NanoVGHelper.drawCenteredString("Timer", x + width / 2f, y + 2, font, 12, Color.WHITE);

            // 进度条区域 Y 偏移
            float barY = y + 15;
            float barH = heightValue.get().floatValue();

            // 进度条背景
            Color bg = new Color(35, 35, 38, 160);
            NanoVGHelper.drawRoundRect(x + 5, barY, width - 10, barH, r, bg);

            // 边框 (仅 Old 模式显示)
            if (mode.is(Mode.Old)) {
                Color outline = new Color(20, 20, 22, 220);
                NanoVGHelper.drawRoundRectOutline(x + 5, barY, width - 10, barH, r, 1.2f, outline);
            }

            // 进度条
            float pw = (float) ((width - 10) * Math.max(0.0, Math.min(1.0, progress)));

            if (pw > 0f) {
                Color c1, c2;
                if (mode.is(Mode.Old)) {
                    if (active) {
                        c1 = activeColor1.get();
                        c2 = activeColor2.get();
                    } else {
                        c1 = chargeColor1.get();
                        c2 = chargeColor2.get();
                    }
                } else {
                    // New Mode: 动态渐变
                    // 使用正弦波在 ChargeColor1 和 ChargeColor2 之间进行插值，产生平滑的呼吸/流动效果
                    float factor = (float) (Math.sin(System.currentTimeMillis() / 500.0) * 0.5 + 0.5);
                    c1 = ColorUtil.interpolateColor(chargeColor1.get(), chargeColor2.get(), factor);

                    // c2 使用稍有偏移的相位，形成从左到右的微弱渐变
                    float factor2 = (float) (Math.sin(System.currentTimeMillis() / 500.0 + 1.0) * 0.5 + 0.5);
                    c2 = ColorUtil.interpolateColor(chargeColor1.get(), chargeColor2.get(), factor2);
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
            int smallFont = FontLoader.medium(10);

            if (mode.is(Mode.Old)) {
                // Old: 居中显示
                NanoVGHelper.drawCenteredString(percentText, x + width / 2f, barY + barH / 2f + 1, smallFont, 10, Color.WHITE);
            } else {
                // New: 下方显示
                // barY + barH 是进度条底部，再加一点间距
                NanoVGHelper.drawCenteredString(percentText, x + width / 2f, barY + barH + 8, smallFont, 10, Color.WHITE);
            }

            NanoVGHelper.restore();
        });
    }
}
