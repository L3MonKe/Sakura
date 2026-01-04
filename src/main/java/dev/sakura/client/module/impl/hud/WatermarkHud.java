package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class WatermarkHud extends HudModule {
    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);
    private final Value<Color> backgroundColor = new ColorValue("Background Color", "背景颜色", new Color(0, 0, 0, 50));
    private final Value<Boolean> backgroundBlur = new BoolValue("Background Blur", "背景模糊", false);
    private final Value<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 8.0, 1.0, 20.0, 0.5, backgroundBlur::get);

    public WatermarkHud() {
        super("Watermark", "水印", 10, 10);
    }

    @Override
    public void onRenderContent() {
        float s = hudScale.get().floatValue();

        String text = Sakura.MOD_NAME + " " + Sakura.MOD_VER;
        float fontSize = 30 * s;
        int fontLoader = FontLoader.bold((int) fontSize);
        float fontW = NanoVGHelper.getTextWidth(text, fontLoader, fontSize);
        float fontH = NanoVGHelper.getFontHeight(fontLoader, fontSize);
        this.width = fontW + 5 * s;
        this.height = fontH + 10 * s;

        if (backgroundBlur.get()) {
            NanoVGRenderer.INSTANCE.withRawCoords(() -> Shader2DUtil.drawRoundedBlur(getMatrix(), x, y, width, height, 4f * s, new Color(0, 0, 0, 0), blurStrength.get().floatValue(), 1.0f));
        }

        NanoVGHelper.drawRoundRectBloom(x, y, width, height, 4 * s, backgroundColor.get());
        NanoVGHelper.drawGradientRRect(x - 2 * s, y, 2 * s, height, 0, ClickGui.color(0), ClickGui.color2(0));
        NanoVGHelper.drawString(text, x + 2.5f * s, y + fontH, fontLoader, fontSize, new Color(255, 255, 255, 255));
    }
}