package dev.sakura.client.gui.component;

import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.DecelerateAnimation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

public class SakuraButton extends ButtonWidget {
    private final Animation hoverAnim = new DecelerateAnimation(200, 1.0);

    public SakuraButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    public SakuraButton(int x, int y, int width, int height, String message, PressAction onPress) {
        super(x, y, width, height, Text.of(message), onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        hoverAnim.setDirection(isHovered() ? Direction.FORWARDS : Direction.BACKWARDS);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float alpha = hoverAnim.getOutput().floatValue();

            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRoundedRect(vg, getX(), getY(), width, height, SakuraTheme.ROUNDING);

            // Background
            boolean hovered = isHovered();
            NanoVG.nvgFillColor(vg, SakuraTheme.color(SakuraTheme.BUTTON_BG, hovered ? 1.0f : 0.8f));
            NanoVG.nvgFill(vg);

            // Border
            NanoVG.nvgStrokeColor(vg, SakuraTheme.color(SakuraTheme.BUTTON_BORDER, hovered ? 1.0f : 0.8f));
            NanoVG.nvgStrokeWidth(vg, 1.0f);
            NanoVG.nvgStroke(vg);

            // Text
            NanoVG.nvgFontSize(vg, 14.0f);
            NanoVG.nvgFontFaceId(vg, FontLoader.regular(14.0f));
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);

            NanoVG.nvgFillColor(vg, SakuraTheme.color(SakuraTheme.TEXT));

            NanoVG.nvgText(vg, getX() + width / 2.0f, getY() + height / 2.0f + 1, getMessage().getString());
        });
    }
}
