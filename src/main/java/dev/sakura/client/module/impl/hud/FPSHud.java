package dev.sakura.client.module.impl.hud;

import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.math.FrameRateCounter;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class FPSHud extends HudModule {
    public enum FontMode {
        REGULAR, BOLD, MEDIUM, SEMI, COMFORTAA, AX, GEOLOGICA, MATERIAL, TENACITY, MONA_BOLD
    }

    public enum TextCase {
        UPPERCASE, LOWERCASE, CAPITALIZE
    }

    // 基础设置
    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 3.0, 0.1);
    private final Value<Integer> delay = new NumberValue<>("Delay", "延迟", 10, 0, 40, 1);

    // 字体设置
    private final Value<FontMode> fontMode = new EnumValue<>("Font", "字体", FontMode.BOLD);
    private final Value<Double> fontSize = new NumberValue<>("Font Size", "字体大小", 14.0, 8.0, 32.0, 0.5);
    private final Value<TextCase> textCase = new EnumValue<>("Text Case", "文本大小写", TextCase.UPPERCASE);

    // FPS文本颜色设置
    private final Value<Boolean> fpsTextGradient = new BoolValue("FPS Text Gradient", "FPS文本渐变", false);
    private final Value<Boolean> fpsFollowClickGui = new BoolValue("FPS Follow ClickGUI", "FPS跟随ClickGUI", true, fpsTextGradient::get);
    private final Value<Color> fpsGradientStart = new ColorValue("FPS Gradient Start", "FPS渐变起始", new Color(255, 183, 197), () -> fpsTextGradient.get() && !fpsFollowClickGui.get());
    private final Value<Color> fpsGradientEnd = new ColorValue("FPS Gradient End", "FPS渐变结束", new Color(255, 133, 161), () -> fpsTextGradient.get() && !fpsFollowClickGui.get());

    // 数字颜色设置
    private final Value<Color> numberColor = new ColorValue("Number Color", "数字颜色", new Color(255, 255, 255));

    // Glow效果设置
    private final Value<Boolean> glowEnabled = new BoolValue("Glow", "发光效果", false);
    private final Value<Double> glowRadius = new NumberValue<>("Glow Radius", "发光半径", 10.0, 1.0, 30.0, 0.5, glowEnabled::get);
    private final Value<Integer> glowIntensity = new NumberValue<>("Glow Intensity", "发光强度", 3, 1, 10, 1, glowEnabled::get);

    private int cachedFps;
    private final TimerUtil timer = new TimerUtil();

    public FPSHud() {
        super("FPS", "FPS显示", 10, 40);
    }

    @Override
    protected void onEnable() {
        timer.reset();
        cachedFps = 0;
    }

    @Override
    public void onRender(DrawContext context) {
        float s = hudScale.get().floatValue();

        if (timer.delay(delay.get().floatValue())) {
            cachedFps = FrameRateCounter.INSTANCE.getFps();
            timer.reset();
        }

        String fpsText = formatText("FPS");
        String numberText = String.valueOf(cachedFps);

        int font = getFontId();
        float size = fontSize.get().floatValue() * s;

        float currentX = x + 2 * s;
        float textY = y + size;

        // 渲染FPS文本
        if (fpsTextGradient.get()) {
            renderGradientText(fpsText, currentX, textY, font, size);
        } else {
            renderText(fpsText, currentX, textY, font, size, Color.WHITE);
        }

        currentX += NanoVGHelper.getTextWidth(fpsText + " ", font, size);

        // 渲染数字
        renderText(numberText, currentX, textY, font, size, numberColor.get());

        String fullText = fpsText + " " + numberText;
        width = NanoVGHelper.getTextWidth(fullText, font, size) + 4 * s;
        height = NanoVGHelper.getFontHeight(font, size) + 4 * s;
    }

    private void renderText(String text, float x, float y, int font, float size, Color color) {
        if (glowEnabled.get()) {
            NanoVGRenderer.INSTANCE.draw(vg ->
                    NanoVGHelper.drawGlowingString(text, x, y, font, size, color,
                            glowRadius.get().floatValue(), glowIntensity.get())
            );
        } else {
            NanoVGRenderer.INSTANCE.draw(vg ->
                    NanoVGHelper.drawString(text, x, y, font, size, color)
            );
        }
    }

    private void renderGradientText(String text, float x, float y, int font, float size) {
        Color startColor, endColor;

        if (fpsFollowClickGui.get()) {
            startColor = ClickGui.color(0);
            endColor = ClickGui.color2(0);
        } else {
            startColor = fpsGradientStart.get();
            endColor = fpsGradientEnd.get();
        }

        char[] chars = text.toCharArray();
        float currentX = x;

        for (int i = 0; i < chars.length; i++) {
            String charStr = String.valueOf(chars[i]);
            float t = chars.length > 1 ? (float) i / (chars.length - 1) : 0;
            Color charColor = interpolateColor(startColor, endColor, t);

            renderText(charStr, currentX, y, font, size, charColor);
            currentX += NanoVGHelper.getTextWidth(charStr, font, size);
        }
    }

    private String formatText(String text) {
        return switch (textCase.get()) {
            case UPPERCASE -> text.toUpperCase();
            case LOWERCASE -> text.toLowerCase();
            case CAPITALIZE -> text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        };
    }

    private int getFontId() {
        return switch (fontMode.get()) {
            case REGULAR -> FontLoader.regular();
            case BOLD -> FontLoader.bold();
            case MEDIUM -> FontLoader.medium();
            case SEMI -> FontLoader.greycliffSemi();
            case COMFORTAA -> FontLoader.comfortaa();
            case AX -> FontLoader.ax();
            case GEOLOGICA -> FontLoader.geologica();
            case MATERIAL -> FontLoader.material();
            case TENACITY -> FontLoader.tenacity();
            case MONA_BOLD -> FontLoader.monaBold();
        };
    }

    private Color interpolateColor(Color c1, Color c2, float t) {
        t = MathHelper.clamp(t, 0.0f, 1.0f);
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(r, g, b, a);
    }
}
