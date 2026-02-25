package dev.mzc.client.module.impl.hud;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class InventoryHud extends HudModule {
    private final NumberValue<Double> invScale = new NumberValue<>("Scale", "缩放", 1.0, 0.6, 2.0, 0.1);
    private final NumberValue<Double> cellSize = new NumberValue<>("CellSize", "格子尺寸", 18.0, 14.0, 26.0, 0.5);
    private final NumberValue<Double> gap = new NumberValue<>("Gap", "格子间距", 4.0, 0.0, 10.0, 0.5);
    private final NumberValue<Double> padding = new NumberValue<>("Padding", "面板内边距", 8.0, 4.0, 24.0, 0.5);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 20.0, 1.0);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 18.0, 4.0, 180.0, 1.0);
    private final NumberValue<Double> refractionAmount = new NumberValue<>("RefractionAmount", "折射强度", 0.6, 0.0, 0.9, 0.001);
    private final NumberValue<Double> refractionBand = new NumberValue<>("RefractionBand", "折射带宽", 2.8, 0.0, 8.0, 0.1);
    private final NumberValue<Double> refractionStrength = new NumberValue<>("RefractionStrength", "折射混合强度", 0.85, 0.0, 1.5, 0.05);
    private final NumberValue<Double> lensCurvature = new NumberValue<>("LensCurvature", "透镜曲率", 1.6, 0.5, 4.0, 0.1);
    private final BoolValue showTitle = new BoolValue("ShowTitle", "显示标题", false);
    private final NumberValue<Double> titleSize = new NumberValue<>("TitleSize", "标题字号", 10.0, 6.0, 20.0, 0.5, showTitle::get);
    private final NumberValue<Double> titleOffsetY = new NumberValue<>("TitleOffsetY", "标题Y偏移", -4.0, -10.0, 10.0, 0.5, showTitle::get);
    private final BoolValue edgeGlow = new BoolValue("EdgeGlow", "边缘光晕", true);
    private final NumberValue<Integer> glowAlpha = new NumberValue<>("GlowAlpha", "光晕透明度", 50, 0, 255, 1, edgeGlow::get);
    private final NumberValue<Integer> innerBrightAlpha = new NumberValue<>("InnerBrightAlpha", "内部提亮", 30, 0, 255, 1);
    private final NumberValue<Double> saturation = new NumberValue<>("Saturation", "颜色饱和度", 100.0, 0.0, 200.0, 1.0);
    private final BoolValue bgTint = new BoolValue("BgTint", "背景颜色开关", false);
    private final ColorValue bgTintColor = new ColorValue("BgTintColor", "背景颜色", new Color(0, 0, 0, 100), bgTint::get);
    private final NumberValue<Integer> bgTintAlpha = new NumberValue<>("BgTintAlpha", "背景颜色透明度", 100, 0, 255, 1, bgTint::get);

    public InventoryHud() {
        super("InventoryHud", "背包显示", 12, 160);
        this.width = 200;
        this.height = 90;
    }

    @Override
    public void renderInGame(DrawContext context) {
        if (mc.player == null) return;

        float s = invScale.get().floatValue();
        float cs = cellSize.get().floatValue() * s;
        float g = gap.get().floatValue() * s;
        float pad = padding.get().floatValue() * s;
        float r = radius.get().floatValue() * s;

        int cols = 9;
        int rows = 3;

        float gridW = cols * cs + (cols - 1) * g;
        float gridH = rows * cs + (rows - 1) * g;
        float titleH = showTitle.get() ? (NanoVGHelper.getFontHeight(FontLoader.medium(10), titleSize.get().floatValue() * s) + 6f * s) : 0f;
        this.width = (int) (pad * 2 + gridW);
        this.height = (int) (pad * 2 + titleH + gridH);

        Shader2DUtil.setRefraction(refractionAmount.get().floatValue(), refractionBand.get().floatValue());
        Shader2DUtil.setRefractionStrength(refractionStrength.get().floatValue());
        Shader2DUtil.setLensCurvature(lensCurvature.get().floatValue());
        Color tint;
        float sat = saturation.get().floatValue() / 100.0f;
        if (bgTint.get()) {
            Color c = applySaturation(bgTintColor.get(), sat);
            tint = new Color(c.getRed(), c.getGreen(), c.getBlue(), bgTintAlpha.get());
        } else {
            tint = new Color(0, 0, 0, 0);
        }
        Shader2DUtil.drawRoundedBlur(context.getMatrices(), x, y, width, height, r, tint, blurStrength.get().floatValue(), 1.0f);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (innerBrightAlpha.get() > 0) {
                NanoVG.nvgGlobalCompositeOperation(vg, NanoVG.NVG_LIGHTER);
                NanoVGHelper.drawRoundRect(x, y, width, height, r, new Color(20, 20, 20, innerBrightAlpha.get()));
                NanoVG.nvgGlobalCompositeOperation(vg, NanoVG.NVG_SOURCE_OVER);
            }
            if (edgeGlow.get()) {
                NanoVGHelper.drawRoundRectBloomOutline(x, y, width, height, r, new Color(0, 0, 0, glowAlpha.get()));
            }
            if (showTitle.get()) {
                int font = FontLoader.medium(10);
                String title = "Inventory";
                float fs = titleSize.get().floatValue() * s;
                float th = NanoVGHelper.getFontHeight(font, fs);
                float ty = y + pad + th + titleOffsetY.get().floatValue() * s;
                float tx = x + pad;
                NanoVGHelper.drawString(title, tx, ty, font, fs, new Color(255, 255, 255, 230));
            }
        });

        float startY = y + pad + titleH;
        float startX = x + pad;
        float itemScale = cs / 16f;

        context.getMatrices().push();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = 9 + row * 9 + col;
                ItemStack stack = mc.player.getInventory().getStack(index);
                float cellX = startX + col * (cs + g);
                float cellY = startY + row * (cs + g);

                context.getMatrices().push();
                float drawX = cellX + (cs - 16f * itemScale) / 2f;
                float drawY = cellY + (cs - 16f * itemScale) / 2f;
                context.getMatrices().translate(drawX, drawY, 0);
                context.getMatrices().scale(itemScale, itemScale, 1f);
                context.drawItem(stack, 0, 0);
                context.getMatrices().pop();
            }
        }
        context.getMatrices().pop();
    }

    private Color applySaturation(Color color, float saturation) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float s = Math.max(0f, Math.min(1f, hsb[1] * saturation));
        int rgb = Color.HSBtoRGB(hsb[0], s, hsb[2]);
        Color rgbColor = new Color(rgb);
        return new Color(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), color.getAlpha());
    }
}
