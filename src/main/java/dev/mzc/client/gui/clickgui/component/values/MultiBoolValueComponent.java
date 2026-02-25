package dev.mzc.client.gui.clickgui.component.values;

import dev.mzc.client.gui.Component;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseOutSine;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.render.RenderUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.MultiBoolValue;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MultiBoolValueComponent extends Component {
    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color GRAY = new Color(150, 150, 150);
    private static final float FONT_SIZE = 7.5f;

    private final MultiBoolValue setting;
    private final Map<BoolValue, EaseOutSine> select = new HashMap<>();

    public MultiBoolValueComponent(MultiBoolValue setting) {
        this.setting = setting;
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        float offset = 4 * scale;
        float heightoff = 0;
        int font = FontLoader.regular(titleFontSize);
        float fontHeight = NanoVGHelper.getFontHeight(font, titleFontSize);

        NanoVGRenderer.INSTANCE.draw(vg -> NanoVGHelper.drawString(setting.getDisplayName(), getX(), getY(), font, titleFontSize, WHITE));

        for (BoolValue boolValue : setting.getValues()) {
            float off = NanoVGHelper.getTextWidth(boolValue.getName(), font, titleFontSize) + 4 * scale;
            if (offset + off >= getWidth() - 4 * scale) {
                offset = 4 * scale;
                heightoff += 10 * scale;
            }
            select.putIfAbsent(boolValue, new EaseOutSine(250, 1));
            EaseOutSine anim = select.get(boolValue);
            anim.setDirection(boolValue.get() ? Direction.FORWARDS : Direction.BACKWARDS);

            float finalOffset = offset;
            float finalHeightoff = heightoff;
            Color textColor = new Color(ColorUtil.interpolateColor2(GRAY, WHITE, anim.getOutput().floatValue()));
            NanoVGRenderer.INSTANCE.draw(vg -> NanoVGHelper.drawString(boolValue.getName(), getX() + finalOffset + 4 * scale, getY() + 2 * scale + finalHeightoff + fontHeight, font, titleFontSize, textColor));

            offset += off;
        }

        setHeight(23 * scale + heightoff);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        float offset = 4 * scale;
        float heightoff = 0;
        int font = FontLoader.regular(titleFontSize);
        float fontHeight = NanoVGHelper.getFontHeight(font, titleFontSize);

        for (BoolValue boolValue : setting.getValues()) {
            float textWidth = NanoVGHelper.getTextWidth(boolValue.getName(), font, titleFontSize);
            float off = textWidth + 4 * scale;
            if (offset + off >= getWidth() - 3 * scale) {
                offset = 4 * scale;
                heightoff += 10 * scale;
            }
            if (RenderUtil.isHovering(getX() + offset, getY() + 1 * scale + heightoff, textWidth, fontHeight, (float) mouseX, (float) mouseY) && mouseButton == 0) {
                boolValue.set(!boolValue.get());
            }
            offset += off;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isVisible() {
        return setting.isAvailable();
    }
}
