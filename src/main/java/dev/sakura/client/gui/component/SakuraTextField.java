package dev.sakura.client.gui.component;

import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class SakuraTextField extends TextFieldWidget {

    private String placeholderText = "";
    private boolean isPassword = false;

    public SakuraTextField(TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
        super(textRenderer, x, y, width, height, message);
        this.setDrawsBackground(false);
    }

    public void setPlaceholder(String text) {
        this.placeholderText = text;
        this.setPlaceholder(Text.of(text));
    }

    public void setPasswordMode(boolean enable) {
        this.isPassword = enable;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        NanoVGRenderer.INSTANCE.draw(vg -> {
            // Background
            NanoVGHelper.drawRoundRect(getX(), getY(), getWidth(), getHeight(), SakuraTheme.ROUNDING, SakuraTheme.INPUT_BG);

            // Border
            Color borderColor = isFocused() ? SakuraTheme.INPUT_BORDER : new Color(
                    SakuraTheme.INPUT_BORDER.getRed(),
                    SakuraTheme.INPUT_BORDER.getGreen(),
                    SakuraTheme.INPUT_BORDER.getBlue(),
                    128
            );
            NanoVGHelper.drawRoundRectOutline(getX(), getY(), getWidth(), getHeight(), SakuraTheme.ROUNDING, 1.0f, borderColor);

            // Clip content
            NanoVG.nvgScissor(vg, getX() + 2, getY(), getWidth() - 4, getHeight());

            String text = getText();
            String displayString = isPassword ? "*".repeat(text.length()) : text;

            if (text.isEmpty() && !isFocused() && !placeholderText.isEmpty()) {
                NanoVGHelper.drawString(placeholderText, getX() + 5, getY() + getHeight() / 2f,
                        FontLoader.regular(16), 16, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, SakuraTheme.TEXT_SECONDARY);
            } else {
                // Determine text color based on focus/state
                NanoVGHelper.drawString(displayString, getX() + 5, getY() + getHeight() / 2f,
                        FontLoader.regular(16), 16, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, SakuraTheme.TEXT);

                // Cursor
                if (isFocused() && (System.currentTimeMillis() / 500) % 2 == 0) {
                    // Calculate cursor position based on text width
                    // Note: This is a simple cursor implementation. For full cursor support (moving cursor),
                    // we'd need to map getCursor() position to width.
                    // For now, we assume cursor is at end for this simple aesthetic update.

                    // If we want real cursor pos:
                    int cursor = getCursor();
                    String beforeCursor = displayString.substring(0, Math.min(cursor, displayString.length()));
                    float textWidth = NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(16), 16);

                    NanoVG.nvgBeginPath(vg);
                    NanoVG.nvgMoveTo(vg, getX() + 5 + textWidth + 1, getY() + 4);
                    NanoVG.nvgLineTo(vg, getX() + 5 + textWidth + 1, getY() + getHeight() - 4);
                    NanoVG.nvgStrokeColor(vg, SakuraTheme.color(SakuraTheme.TEXT));
                    NanoVG.nvgStrokeWidth(vg, 1.0f);
                    NanoVG.nvgStroke(vg);
                }
            }

            NanoVG.nvgResetScissor(vg);
        });
    }
}
