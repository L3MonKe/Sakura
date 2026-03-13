package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;

public class ScaffoldBlocksHud extends HudModule {
    public enum ShadowMode {
        Solid,
        Gradient
    }

    public enum FontMode {
        Default,
        Comfortaa,
        Ax,
        Geologica,
        Material,
        Tenacity
    }

    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 3.0, 0.1);
    private final Value<Color> backgroundColor = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 120));
    private final Value<Double> radius = new NumberValue<>("Radius", "圆角", 8.0, 0.0, 20.0, 1.0);
    private final Value<Boolean> blur = new BoolValue("Blur", "模糊", true);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final Value<Boolean> shadow = new BoolValue("Shadow", "阴影", true);
    private final EnumValue<ShadowMode> shadowMode = new EnumValue<>("ShadowMode", "阴影模式", ShadowMode.Solid, shadow::get);
    private final Value<Double> shadowRange = new NumberValue<>("ShadowRange", "阴影范围", 8.0, 0.0, 30.0, 0.5, shadow::get);
    private final Value<Double> shadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.01, shadow::get);
    private final EnumValue<FontMode> numberFontMode = new EnumValue<>("NumberFont", "数字字体", FontMode.Default);
    private final EnumValue<FontMode> blocksFontMode = new EnumValue<>("BlocksFont", "Blocks字体", FontMode.Default);
    private final Value<Double> numberFontSize = new NumberValue<>("NumberSize", "数字大小", 14.0, 8.0, 32.0, 0.5);
    private final Value<Double> blocksFontSize = new NumberValue<>("BlocksSize", "Blocks大小", 11.0, 8.0, 28.0, 0.5);
    private final Value<Double> textSpacing = new NumberValue<>("TextSpacing", "文本间距", 3.0, 0.0, 12.0, 0.5);
    private final Value<Double> middleGap = new NumberValue<>("MiddleGap", "中间间距", 6.0, 0.0, 24.0, 0.5);
    private final Value<Double> blockScale = new NumberValue<>("BlockScale", "方块大小", 1.0, 0.5, 3.0, 0.1);
    private final Value<Double> blockOffsetX = new NumberValue<>("BlockOffsetX", "方块偏移X", 0.0, -30.0, 30.0, 0.5);
    private final Value<Double> blockOffsetY = new NumberValue<>("BlockOffsetY", "方块偏移Y", 0.0, -20.0, 20.0, 0.5);
    private final Value<Double> numberOffsetX = new NumberValue<>("NumberOffsetX", "数字偏移X", 0.0, -30.0, 30.0, 0.5);
    private final Value<Double> numberOffsetY = new NumberValue<>("NumberOffsetY", "数字偏移Y", 0.0, -20.0, 20.0, 0.5);
    private final Value<Double> blocksOffsetX = new NumberValue<>("BlocksOffsetX", "Blocks偏移X", 0.0, -30.0, 30.0, 0.5);
    private final Value<Double> blocksOffsetY = new NumberValue<>("BlocksOffsetY", "Blocks偏移Y", 0.0, -20.0, 20.0, 0.5);
    private final Value<Color> numberColor = new ColorValue("NumberColor", "数字颜色", new Color(255, 255, 255));
    private final Value<Color> blocksColor = new ColorValue("BlocksColor", "Blocks颜色", new Color(255, 255, 255));
    private final Value<Boolean> numberGradient = new BoolValue("NumberGradient", "数字渐变", true);
    private final Value<Boolean> numberGradientFollowClickGui = new BoolValue("NumberFollowClickGui", "数字渐变跟随ClickGui", true, numberGradient::get);
    private final Value<Color> numberGradientColor1 = new ColorValue("NumberGradientColor1", "数字渐变色1", new Color(255, 183, 197), () -> numberGradient.get() && !numberGradientFollowClickGui.get());
    private final Value<Color> numberGradientColor2 = new ColorValue("NumberGradientColor2", "数字渐变色2", new Color(255, 105, 180), () -> numberGradient.get() && !numberGradientFollowClickGui.get());
    private final Value<Boolean> numberTextShadow = new BoolValue("NumberTextShadow", "数字阴影", true);
    private final Value<Double> numberTextShadowDistance = new NumberValue<>("NumberShadowDistance", "数字阴影间距", 1.0, 0.0, 8.0, 0.1, numberTextShadow::get);
    private final Value<Boolean> numberGlow = new BoolValue("NumberGlow", "数字Glow", false);
    private final Value<Double> numberGlowRange = new NumberValue<>("NumberGlowRange", "数字Glow范围", 4.0, 0.0, 20.0, 0.5, numberGlow::get);
    private final Value<Integer> numberGlowIntensity = new NumberValue<>("NumberGlowIntensity", "数字Glow强度", 2, 1, 10, 1, numberGlow::get);
    private final Value<Boolean> blocksGlow = new BoolValue("BlocksGlow", "BlocksGlow", false);
    private final Value<Double> blocksGlowRange = new NumberValue<>("BlocksGlowRange", "BlocksGlow范围", 4.0, 0.0, 20.0, 0.5, blocksGlow::get);
    private final Value<Integer> blocksGlowIntensity = new NumberValue<>("BlocksGlowIntensity", "BlocksGlow强度", 2, 1, 10, 1, blocksGlow::get);

    public ScaffoldBlocksHud() {
        super("ScaffoldBlocks", "搭路方块", 10, 80);
    }

    @Override
    public void onRender(DrawContext context) {
        float scale = hudScale.get().floatValue();
        int blocks = getHotbarBlockCount();
        boolean editorPreview = Sakura.MODULES.getModule(HudEditor.class).isEnabled();
        Scaffold scaffold = Sakura.MODULES.getModule(Scaffold.class);
        boolean scaffoldEnabled = scaffold != null && scaffold.isEnabled();

        if (!editorPreview && !scaffoldEnabled) {
            width = 0;
            height = 0;
            return;
        }

        int displayBlocks = scaffoldEnabled ? blocks : 99;
        String numberText = String.valueOf(displayBlocks);
        String blocksText = ClickGui.language.is(ClickGui.LanguageMode.Chinese) ? "方块" : "Blocks";
        ItemStack currentScaffoldBlock = getCurrentScaffoldBlock();

        int numberFont = getFontId(numberFontMode.get());
        int blocksFont = getFontId(blocksFontMode.get());
        float numberSize = numberFontSize.get().floatValue() * scale;
        float blocksSize = blocksFontSize.get().floatValue() * scale;
        float spacing = textSpacing.get().floatValue() * scale;

        float numberWidth = NanoVGHelper.getTextWidth(numberText, numberFont, numberSize);
        float blocksWidth = NanoVGHelper.getTextWidth(blocksText, blocksFont, blocksSize);
        float numberHeight = NanoVGHelper.getFontHeight(numberFont, numberSize);
        float blocksHeight = NanoVGHelper.getFontHeight(blocksFont, blocksSize);
        float textHeight = Math.max(numberHeight, blocksHeight);
        float textWidth = numberWidth + spacing + blocksWidth;

        float topPadding = 6f * scale;
        float bottomPadding = 6f * scale;
        float itemScale = Math.max(0.1f, blockScale.get().floatValue() * scale);
        float itemSize = 16f * itemScale;
        float centerGap = middleGap.get().floatValue() * scale;

        float boxWidth = Math.max(68f * scale, textWidth + 20f * scale);
        float boxHeight = Math.max(50f * scale, topPadding + itemSize + centerGap + textHeight + bottomPadding);
        float boxRadius = radius.get().floatValue() * scale;
        if (shadow.get()) {
            float[] rects = new float[]{x, y, boxWidth, boxHeight};
            float[] radii = new float[]{boxRadius};
            float range = shadowRange.get().floatValue() * scale;
            float strength = shadowStrength.get().floatValue();

            if (shadowMode.is(ShadowMode.Gradient)) {
                Color start = ClickGui.color(1);
                Color end = ClickGui.color2(1);
                start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
                end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);
                ShadowShader.drawStairShadowGradient(x, y, boxWidth, boxHeight, range, strength, start, end, rects, radii, 1);
            } else {
                Color c = new Color(128, 128, 128, 255);
                ShadowShader.drawStairShadow(x, y, boxWidth, boxHeight, range, strength, c, rects, radii, 1);
            }
        }

        if (blur.get()) {
            BlurShader.drawRoundedBlur(x, y, boxWidth, boxHeight, boxRadius, blurStrength.get().floatValue());
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRoundRect(x, y, boxWidth, boxHeight, boxRadius, backgroundColor.get());

            float textX = x + (boxWidth - textWidth) / 2f;
            float textTop = y + topPadding + itemSize + centerGap;
            float numberY = textTop + (textHeight - numberHeight) / 2f + numberHeight;
            float blocksY = textTop + (textHeight - blocksHeight) / 2f + blocksHeight;
            float blocksX = textX + numberWidth + spacing + blocksOffsetX.get().floatValue() * scale;
            float blocksRenderY = blocksY + blocksOffsetY.get().floatValue() * scale;
            float numberX = textX + numberOffsetX.get().floatValue() * scale;
            float numberRenderY = numberY + numberOffsetY.get().floatValue() * scale;

            if (numberTextShadow.get()) {
                float shadowDist = numberTextShadowDistance.get().floatValue() * scale;
                Color shadowColor = getNumberShadowColor();
                NanoVGHelper.drawString(numberText, numberX + shadowDist, numberRenderY + shadowDist, numberFont, numberSize, shadowColor);
            }

            if (numberGradient.get()) {
                Color[] gradientColors = getNumberGradientColors();
                drawGradientString(numberText, numberX, numberRenderY, numberFont, numberSize, gradientColors[0], gradientColors[1], numberGlow.get(), numberGlowRange.get().floatValue() * scale, numberGlowIntensity.get());
            } else if (numberGlow.get()) {
                NanoVGHelper.drawGlowingString(numberText, numberX, numberRenderY, numberFont, numberSize, numberColor.get(), numberGlowRange.get().floatValue() * scale, numberGlowIntensity.get());
            } else {
                NanoVGHelper.drawString(numberText, numberX, numberRenderY, numberFont, numberSize, numberColor.get());
            }

            if (blocksGlow.get()) {
                NanoVGHelper.drawGlowingString(blocksText, blocksX, blocksRenderY, blocksFont, blocksSize, blocksColor.get(), blocksGlowRange.get().floatValue() * scale, blocksGlowIntensity.get());
            } else {
                NanoVGHelper.drawString(blocksText, blocksX, blocksRenderY, blocksFont, blocksSize, blocksColor.get());
            }
        });

        if (!currentScaffoldBlock.isEmpty()) {
            float itemX = x + (boxWidth - itemSize) / 2f + blockOffsetX.get().floatValue() * scale;
            float itemY = y + topPadding + blockOffsetY.get().floatValue() * scale;

            context.getMatrices().pushMatrix();
            context.getMatrices().translate(itemX, itemY);
            context.getMatrices().scale(itemScale, itemScale);
            context.drawItem(currentScaffoldBlock, 0, 0);
            context.getMatrices().popMatrix();
        }

        width = boxWidth;
        height = boxHeight;
    }

    private int getHotbarBlockCount() {
        if (mc.player == null) return 0;

        int count = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private ItemStack getCurrentScaffoldBlock() {
        if (mc.player == null) return ItemStack.EMPTY;

        ItemStack main = mc.player.getMainHandStack();
        if (!main.isEmpty() && main.getItem() instanceof BlockItem) {
            return main;
        }

        ItemStack offhand = mc.player.getOffHandStack();
        if (!offhand.isEmpty() && offhand.getItem() instanceof BlockItem) {
            return offhand;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private int getFontId(FontMode mode) {
        return switch (mode) {
            case Comfortaa -> FontLoader.comfortaa();
            case Ax -> FontLoader.ax();
            case Geologica -> FontLoader.geologica();
            case Material -> FontLoader.material();
            case Tenacity -> FontLoader.tenacity();
            default -> FontLoader.medium();
        };
    }

    private Color[] getNumberGradientColors() {
        if (numberGradientFollowClickGui.get()) {
            Color c1 = ClickGui.color(1);
            Color c2 = ClickGui.color2(1);
            return new Color[]{
                    new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                    new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255)
            };
        }

        Color c1 = numberGradientColor1.get();
        Color c2 = numberGradientColor2.get();
        return new Color[]{
                new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255)
        };
    }

    private Color getNumberShadowColor() {
        Color[] colors = getNumberGradientColors();
        Color avg = new Color(
                (colors[0].getRed() + colors[1].getRed()) / 2,
                (colors[0].getGreen() + colors[1].getGreen()) / 2,
                (colors[0].getBlue() + colors[1].getBlue()) / 2
        );
        return new Color(
                (int) (avg.getRed() * 0.45f),
                (int) (avg.getGreen() * 0.45f),
                (int) (avg.getBlue() * 0.45f),
                255
        );
    }

    private void drawGradientString(String text, float x, float y, int font, float size, Color c1, Color c2, boolean glow, float glowRange, int glowIntensity) {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textWidth = NanoVGHelper.getTextWidth(text, font, size);
        float textHeight = NanoVGHelper.getFontHeight(font, size);

        NVGPaint paint = NVGPaint.create();
        nvgLinearGradient(vg, x, y - textHeight, x + Math.max(1f, textWidth), y, NanoVGHelper.nvgColor(c1), NanoVGHelper.nvgColor(c2), paint);

        nvgFontFaceId(vg, font);
        nvgFontSize(vg, size);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);

        if (glow) {
            nvgFontBlur(vg, glowRange);
            nvgFillPaint(vg, paint);
            for (int i = 0; i < glowIntensity; i++) {
                nvgText(vg, x, y, text);
            }
        }

        nvgFontBlur(vg, 0);
        nvgFillPaint(vg, paint);
        nvgText(vg, x, y, text);
    }
}
