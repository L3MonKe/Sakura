package dev.sakura.client.gui.clickgui.component.values;

import dev.sakura.client.gui.Component;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.SmoothStepAnimation;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class BoolValueComponent extends Component {
    private final BoolValue setting;
    private final SmoothStepAnimation toggleAnimation = new SmoothStepAnimation(175, 1);

    public BoolValueComponent(BoolValue setting) {
        this.setting = setting;
        this.toggleAnimation.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float scaledHeight = 14 * scale;
        setHeight(scaledHeight);
        this.toggleAnimation.setDirection(setting.get() ? Direction.FORWARDS : Direction.BACKWARDS);
        NanoVGRenderer.INSTANCE.draw(vg -> {
            float fontSize = baseFontSize * 0.75f;
            int font = FontLoader.regular(fontSize);
            String text = setting.getDisplayName();

            float toggleWidth = 15 * scale;
            float padding = 5 * scale;
            float availableWidth = getWidth() - toggleWidth - padding;
            float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize);

            if (textWidth > availableWidth) {
                float maxScroll = textWidth - availableWidth;
                double scrollDuration = (maxScroll / (20 * scale)) * 1000.0;
                double pauseDuration = 1000.0;
                double cycleDuration = scrollDuration * 2 + pauseDuration * 2;

                double timeInCycle = System.currentTimeMillis() % cycleDuration;

                float scrollX;
                if (timeInCycle < pauseDuration) {
                    scrollX = 0;
                } else if (timeInCycle < pauseDuration + scrollDuration) {
                    scrollX = (float) ((timeInCycle - pauseDuration) / scrollDuration * maxScroll);
                } else if (timeInCycle < pauseDuration * 2 + scrollDuration) {
                    scrollX = maxScroll;
                } else {
                    scrollX = (float) (maxScroll - ((timeInCycle - (pauseDuration * 2 + scrollDuration)) / scrollDuration * maxScroll));
                }

                NanoVG.nvgSave(vg);
                NanoVG.nvgIntersectScissor(vg, getX(), getY() - 10 * scale, availableWidth, 20 * scale);

                NanoVG.nvgTranslate(vg, -scrollX, 0);
                NanoVGHelper.drawString(text, getX(), getY(), font, fontSize, Color.WHITE);

                NanoVG.nvgRestore(vg);
            } else {
                NanoVGHelper.drawString(text, getX(), getY(), font, fontSize, Color.WHITE);
            }

            float toggleHeight = 8 * scale;
            float toggleRadius = 4 * scale;
            float circleRadius = 3 * scale;
            NanoVGHelper.drawRoundRect(getX() + getWidth() - toggleWidth, getY() - 7 * scale, toggleWidth, toggleHeight, toggleRadius, setting.get() ? ClickGui.color(0).darker() : new Color(70, 70, 70));
            NanoVGHelper.drawCircle(getX() + getWidth() - 11 * scale + 7 * scale * toggleAnimation.getOutput().floatValue(), getY() - 3 * scale, circleRadius, setting.get() ? Color.WHITE : new Color(150, 150, 150));
        });
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        float toggleWidth = 15 * scale;
        float toggleHeight = 8 * scale;
        if (RenderUtil.isHovering(getX() + getWidth() - toggleWidth, getY() - 7 * scale, toggleWidth, toggleHeight, (float) mouseX, (float) mouseY) && mouseButton == 0) {
            this.setting.set(!this.setting.get());
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isVisible() {
        return this.setting.isAvailable();
    }
}
