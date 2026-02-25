package dev.sakura.client.gui.clickgui.component.values;

import dev.sakura.client.gui.Component;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.values.impl.EnumValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

import static dev.sakura.client.nanovg.NanoVGRenderer.INSTANCE;

public class EnumValueComponent extends Component {
    private final EnumValue<?> setting;
    private boolean expanded = false;
    private final Animation expandAnimation = new EaseInOutQuad(250, 1, Direction.BACKWARDS);
    private final Animation arrowAnimation = new EaseInOutQuad(250, 1, Direction.BACKWARDS);

    private static final float BOX_HEIGHT = 18;
    private static final float OPTION_HEIGHT = 16;
    private static final float ROUNDING = 4;
    private static final float PADDING = 10;
    private static final float EXPAND_BOTTOM_OFFSET = 3;

    public EnumValueComponent(EnumValue<?> setting) {
        this.setting = setting;
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        float textFontSize = baseFontSize * 0.7f;
        int fontTitle = FontLoader.bold(titleFontSize);
        int fontText = FontLoader.regular(textFontSize);

        float x = getX();
        float y = getY();
        float width = getWidth();

        float labelY = y;
        float boxY = y + 5 * scale;

        expandAnimation.setDirection(expanded ? Direction.FORWARDS : Direction.BACKWARDS);
        arrowAnimation.setDirection(expanded ? Direction.FORWARDS : Direction.BACKWARDS);

        String[] modes = setting.getModeNames();
        float maxExpandedHeight = (modes.length * OPTION_HEIGHT + EXPAND_BOTTOM_OFFSET) * scale;
        float currentExpandHeight = (float) (maxExpandedHeight * expandAnimation.getOutput());

        float totalBoxHeight = (BOX_HEIGHT * scale) + currentExpandHeight;

        setHeight((float) Math.ceil((boxY - y) + totalBoxHeight + PADDING * scale));

        INSTANCE.draw(vg -> {
            NanoVGHelper.drawString(setting.getDisplayName(), x, labelY, fontTitle, titleFontSize, new Color(240, 240, 240));

            NanoVGHelper.drawRoundRect(x, boxY, width, totalBoxHeight, ROUNDING * scale, new Color(35, 35, 39));

            NanoVGHelper.drawRoundRectOutline(x, boxY, width, totalBoxHeight, ROUNDING * scale, 1.0f, new Color(60, 60, 65));

            float headerCenterY = boxY + (BOX_HEIGHT * scale) / 2;

            String currentMode = setting.get().name();
            NanoVGHelper.drawString(currentMode, x + 6 * scale, headerCenterY + 2, fontText, textFontSize, new Color(200, 200, 200));

            float arrowX = x + width - 8 * scale;

            NanoVGHelper.save();
            NanoVGHelper.translate(arrowX, headerCenterY);
            NanoVGHelper.rotate(vg, (float) (Math.toRadians(90) * arrowAnimation.getOutput()));

            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgMoveTo(vg, -2.5f * scale, -3.5f * scale);
            NanoVG.nvgLineTo(vg, 1.5f * scale, 0);
            NanoVG.nvgLineTo(vg, -2.5f * scale, 3.5f * scale);
            NanoVG.nvgStrokeColor(vg, NanoVGHelper.nvgColor(new Color(150, 150, 150)));
            NanoVG.nvgStrokeWidth(vg, 1.5f);
            NanoVG.nvgLineCap(vg, NanoVG.NVG_ROUND);
            NanoVG.nvgStroke(vg);

            NanoVGHelper.restore();

            if (expandAnimation.getOutput() > 0.01) {
                NanoVGHelper.save();
                float separatorY = boxY + BOX_HEIGHT * scale;
                NanoVGHelper.scissor(x, separatorY, width, currentExpandHeight);

                NanoVGHelper.drawLine(x + 2 * scale, separatorY, x + width - 2 * scale, separatorY, 1.0f, new Color(60, 60, 65, (int) (255 * expandAnimation.getOutput())));

                float startY = separatorY;

                for (int i = 0; i < modes.length; i++) {
                    String modeName = modes[i];
                    float modeY = startY + (i * OPTION_HEIGHT * scale);
                    boolean isHovered = RenderUtil.isHovering(x, modeY, width, OPTION_HEIGHT * scale, mouseX, mouseY);
                    boolean isSelected = setting.is(modeName);

                    if (isSelected) {
                        Color themeColor = ClickGui.color(1);
                        Color activeBg = new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 40); // Low opacity background

                        NanoVGHelper.drawRect(x + 1, modeY, width - 2, OPTION_HEIGHT * scale, activeBg);

                        NanoVGHelper.drawRect(x + 2 * scale, modeY + 2 * scale, 2 * scale, (OPTION_HEIGHT - 4) * scale, themeColor);

                    } else if (isHovered) {
                        NanoVGHelper.drawRect(x + 1, modeY, width - 2, OPTION_HEIGHT * scale, new Color(255, 255, 255, 15));
                    }

                    Color textColor = isSelected ? new Color(255, 255, 255) : new Color(160, 160, 160);
                    float textY = modeY + (OPTION_HEIGHT * scale) / 2 + 2;
                    NanoVGHelper.drawString(modeName, x + 10 * scale, textY, fontText, textFontSize, textColor);
                }

                NanoVGHelper.restore();
            }
        });

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!isVisible()) return false;

        float x = getX();
        float y = getY();
        float width = getWidth();
        float boxY = y + 5 * scale;

        if (RenderUtil.isHovering(x, boxY, width, BOX_HEIGHT * scale, mouseX, mouseY)) {
            if (mouseButton == 0 || mouseButton == 1) {
                expanded = !expanded;
                return true;
            }
        }

        if (expanded && expandAnimation.getOutput() > 0.9) {
            float startY = boxY + BOX_HEIGHT * scale;
            String[] modes = setting.getModeNames();

            for (int i = 0; i < modes.length; i++) {
                float modeY = startY + (i * OPTION_HEIGHT * scale);
                if (RenderUtil.isHovering(x, modeY, width, OPTION_HEIGHT * scale, mouseX, mouseY)) {
                    if (mouseButton == 0) {
                        setting.setMode(modes[i]);
                        expanded = false;
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isVisible() {
        return setting.isAvailable();
    }
}
