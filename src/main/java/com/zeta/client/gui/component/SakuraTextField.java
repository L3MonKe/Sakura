package com.zeta.client.gui.component;

import com.zeta.client.gui.theme.SakuraTheme;
import com.zeta.client.nanovg.NanoVGRenderer;
import com.zeta.client.nanovg.font.FontLoader;
import com.zeta.client.nanovg.util.NanoVGHelper;
import com.zeta.client.utils.animations.Animation;
import com.zeta.client.utils.animations.Direction;
import com.zeta.client.utils.animations.impl.DecelerateAnimation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class SakuraTextField extends TextFieldWidget {
    private String placeholderText = "";
    private final Animation hoverAnim = new DecelerateAnimation(180, 1.0, Direction.BACKWARDS);
    private final Animation focusAnim = new DecelerateAnimation(220, 1.0, Direction.BACKWARDS);
    private final Animation errorAnim = new DecelerateAnimation(260, 1.0, Direction.BACKWARDS);
    private long errorUntilMs;
    private long errorStartMs;

    public SakuraTextField(TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
        super(textRenderer, x, y, width, height, message);
        this.setDrawsBackground(false);
    }

    public void setPlaceholder(String text) {
        this.placeholderText = text;
        this.setPlaceholder(Text.of(text));
    }

    public void pulseError() {
        errorStartMs = System.currentTimeMillis();
        errorUntilMs = errorStartMs + 900L;
        errorAnim.setDirection(Direction.FORWARDS);
        errorAnim.reset();
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
        hoverAnim.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        focusAnim.setDirection(isFocused() ? Direction.FORWARDS : Direction.BACKWARDS);

        long now = System.currentTimeMillis();
        boolean errorActive = now <= errorUntilMs;
        errorAnim.setDirection(errorActive ? Direction.FORWARDS : Direction.BACKWARDS);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float hoverT = MathHelper.clamp(hoverAnim.getOutput().floatValue(), 0f, 1f);
            float focusT = MathHelper.clamp(focusAnim.getOutput().floatValue(), 0f, 1f);
            float errorT = MathHelper.clamp(errorAnim.getOutput().floatValue(), 0f, 1f);

            float shake = 0.0f;
            if (errorActive) {
                float p = (now - errorStartMs) / 900.0f;
                p = MathHelper.clamp(p, 0f, 1f);
                float amp = (1.0f - p) * 2.2f;
                shake = (float) Math.sin((now - errorStartMs) / 28.0) * amp;
            }

            float x = getX() + shake;
            float y = getY();
            float w = getWidth();
            float h = getHeight();

            int bgA = (int) (235 + 10 * hoverT);
            NanoVGHelper.drawRoundRect(x, y, w, h, SakuraTheme.ROUNDING, new Color(
                    SakuraTheme.INPUT_BG.getRed(),
                    SakuraTheme.INPUT_BG.getGreen(),
                    SakuraTheme.INPUT_BG.getBlue(),
                    bgA
            ));

            Color baseBorder = new Color(
                    SakuraTheme.INPUT_BORDER.getRed(),
                    SakuraTheme.INPUT_BORDER.getGreen(),
                    SakuraTheme.INPUT_BORDER.getBlue(),
                    (int) (110 + 70 * hoverT)
            );

            Color focusBorder = new Color(
                    SakuraTheme.ACCENT.getRed(),
                    SakuraTheme.ACCENT.getGreen(),
                    SakuraTheme.ACCENT.getBlue(),
                    (int) (130 + 125 * focusT)
            );

            Color errorBorder = new Color(
                    SakuraTheme.DANGER.getRed(),
                    SakuraTheme.DANGER.getGreen(),
                    SakuraTheme.DANGER.getBlue(),
                    (int) (70 + 185 * errorT)
            );

            Color borderColor = mixColors(baseBorder, focusBorder, focusT);
            borderColor = mixColors(borderColor, errorBorder, errorT);
            NanoVGHelper.drawRoundRectOutline(x, y, w, h, SakuraTheme.ROUNDING, 1.1f, borderColor);

            NanoVG.nvgScissor(vg, x + 2, y, w - 4, h);

            String text = getText();
            String displayString = text;
            float validT = (!text.isEmpty() && !errorActive) ? (focusT * 0.35f) : 0f;

            if (text.isEmpty() && !isFocused() && !placeholderText.isEmpty()) {
                NanoVGHelper.drawString(placeholderText, x + 5, y + h / 2f,
                        FontLoader.regular(16), 16, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, SakuraTheme.TEXT_SECONDARY);
            } else {
                NanoVGHelper.drawString(displayString, x + 5, y + h / 2f, FontLoader.regular(16), 16, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, SakuraTheme.TEXT);

                if (isFocused() && (System.currentTimeMillis() / 500) % 2 == 0) {
                    int cursor = getCursor();
                    String beforeCursor = displayString.substring(0, Math.min(cursor, displayString.length()));
                    float textWidth = NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(16), 16);

                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgMoveTo(vg, x + 5 + textWidth + 1, y + 4);
                    NanoVG.nvgLineTo(vg, x + 5 + textWidth + 1, y + h - 4);
                    NanoVG.nvgStrokeColor(vg, SakuraTheme.color(SakuraTheme.TEXT));
                    NanoVG.nvgStrokeWidth(vg, 1.0f);
                    NanoVG.nvgStroke(vg);
                }
            }

            if (validT > 0.001f) {
                Color okGlow = new Color(
                        SakuraTheme.SUCCESS.getRed(),
                        SakuraTheme.SUCCESS.getGreen(),
                        SakuraTheme.SUCCESS.getBlue(),
                        (int) (150 * validT)
                );
                NanoVGHelper.drawRoundRectOutline(x, y, w, h, SakuraTheme.ROUNDING, 1.9f, okGlow);
            }

            NanoVG.nvgResetScissor(vg);
        });
    }

    private static Color mixColors(Color a, Color b, float t) {
        t = MathHelper.clamp(t, 0f, 1f);
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        int al = (int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t);
        return new Color(
                MathHelper.clamp(r, 0, 255),
                MathHelper.clamp(g, 0, 255),
                MathHelper.clamp(bl, 0, 255),
                MathHelper.clamp(al, 0, 255)
        );
    }
}
