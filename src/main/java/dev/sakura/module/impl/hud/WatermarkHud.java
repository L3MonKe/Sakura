package dev.sakura.module.impl.hud;

import dev.sakura.Sakura;
import dev.sakura.module.HudModule;
import dev.sakura.module.impl.client.ClickGui;
import dev.sakura.nanovg.font.FontLoader;
import dev.sakura.nanovg.util.NanoVGHelper;
import dev.sakura.shaders.Shader2DUtils;
import dev.sakura.values.Value;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.ColorValue;
import dev.sakura.values.impl.NumberValue;

import java.awt.*;

public class WatermarkHud extends HudModule {
    private final Value<Double> hudScale = new NumberValue<>("Scale", 1.0, 0.5, 2.0, 0.1);
    private final Value<Color> backgroundColor = new ColorValue("Background Color", new Color(0, 0, 0, 50));
    private final Value<Boolean> backgroundBlur = new BoolValue("Background Blur", false);
    private final Value<Double> blurStrength = new NumberValue<>("Blur Strength", 8.0, 1.0, 20.0, 0.5, backgroundBlur::get);

    public WatermarkHud() {
        super("Watermark", 10, 10);
    }

    @Override
    public void onRenderContent() {
        float s = hudScale.getValue().floatValue();

        String text = Sakura.MOD_NAME + " " + Sakura.MOD_VER;
        float fontSize = 30 * s;
        int fontLoader = FontLoader.greycliffBold((int) fontSize);
        float fontW = NanoVGHelper.getTextWidth(text, fontLoader, fontSize);
        float fontH = NanoVGHelper.getFontHeight(fontLoader, fontSize);
        this.width = fontW + 5 * s;
        this.height = fontH + 10 * s;

        if (backgroundBlur.get()) {
            float radius = 4f * s;
            withPixelCoords(x, y, width, height, (px, py, pw, ph) ->
                    Shader2DUtils.drawRoundedBlur(
                            getMatrix(),
                            px, py, pw, ph,
                            (float) (radius * mc.getWindow().getScaleFactor()),
                            new Color(0, 0, 0, 0),
                            blurStrength.getValue().floatValue(),
                            1.0f
                    ));
        }

        NanoVGHelper.drawRoundRectBloom(x, y, width, height, 4 * s, backgroundColor.get());
        NanoVGHelper.drawGradientRRect(x - 2 * s, y, 2 * s, height, 0, ClickGui.color(0), ClickGui.color2(0));
        NanoVGHelper.drawString(text, x + 2.5f * s, y + fontH, fontLoader, fontSize, new Color(255, 255, 255, 255));
    }
}
