package dev.mahiro.client.gui.component;

import dev.mahiro.client.gui.theme.SakuraTheme;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.DecelerateAnimation;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class MButton extends ButtonWidget {
    private final Animation hoverAnim = new DecelerateAnimation(200, 1.0);
    private final Animation pressAnim = new DecelerateAnimation(120, 1.0, Direction.BACKWARDS);
    private final Animation loadingAnim = new DecelerateAnimation(160, 1.0, Direction.BACKWARDS);
    private boolean pressed;
    private boolean loading;
    private long loadingStartMs;
    private boolean primary;
    private boolean danger;
    private boolean selected;

    public MButton(int x, int y, int width, int height, net.minecraft.text.Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    public MButton(int x, int y, int width, int height, String message, PressAction onPress) {
        super(x, y, width, height, net.minecraft.text.Text.of(message), onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    public void setLoading(boolean loading) {
        if (this.loading == loading) return;
        this.loading = loading;
        this.loadingStartMs = System.currentTimeMillis();
        this.loadingAnim.setDirection(loading ? Direction.FORWARDS : Direction.BACKWARDS);
        this.loadingAnim.reset();
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
        if (primary) this.danger = false;
    }

    public void setDanger(boolean danger) {
        this.danger = danger;
        if (danger) this.primary = false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        boolean hovered = click.x() >= getX() && click.x() <= getX() + width && click.y() >= getY() && click.y() <= getY() + height;
        if (click.button() == 0 && hovered && this.active && this.visible) {
            pressed = true;
            pressAnim.setDirection(Direction.FORWARDS);
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            pressed = false;
            pressAnim.setDirection(Direction.BACKWARDS);
        }
        return super.mouseReleased(click);
    }

    @Override
    protected void drawIcon(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        boolean hovered = mouseX >= getX() && mouseX <= getX() + width && mouseY >= getY() && mouseY <= getY() + height;
        hoverAnim.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        if (!hovered && pressed) {
            pressed = false;
            pressAnim.setDirection(Direction.BACKWARDS);
        }
        if (!loadingAnim.finished(loading ? Direction.FORWARDS : Direction.BACKWARDS)) {
            loadingAnim.setDirection(loading ? Direction.FORWARDS : Direction.BACKWARDS);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float hoverT = MathHelper.clamp(hoverAnim.getOutput().floatValue(), 0f, 1f);
            float pressT = MathHelper.clamp(pressAnim.getOutput().floatValue(), 0f, 1f);
            float loadingT = MathHelper.clamp(loadingAnim.getOutput().floatValue(), 0f, 1f);

            float scale = 1.0f + 0.02f * hoverT - 0.02f * pressT;
            float cx = getX() + width / 2.0f;
            float cy = getY() + height / 2.0f;

            NanoVG.nvgSave(vg);
            NanoVG.nvgTranslate(vg, cx, cy);
            NanoVG.nvgScale(vg, scale, scale);
            NanoVG.nvgTranslate(vg, -cx, -cy);

            Color baseBg = SakuraTheme.BUTTON_BG;
            Color baseBorder = SakuraTheme.BUTTON_BORDER;
            Color textColor = SakuraTheme.TEXT;
            Color fillColor = baseBg;

            if (!this.active) {
                baseBg = new Color(baseBg.getRed(), baseBg.getGreen(), baseBg.getBlue(), 140);
                baseBorder = new Color(baseBorder.getRed(), baseBorder.getGreen(), baseBorder.getBlue(), 110);
                textColor = new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 120);
            } else if (primary) {
                baseBg = SakuraTheme.ACCENT;
                baseBorder = SakuraTheme.ACCENT;
                textColor = SakuraTheme.TEXT_ON_PRIMARY;
            } else if (danger) {
                baseBg = SakuraTheme.DANGER;
                baseBorder = SakuraTheme.DANGER;
                textColor = SakuraTheme.TEXT_ON_PRIMARY;
            }

            if (selected && this.active && !primary && !danger) {
                float tint = 0.08f + 0.07f * hoverT;
                fillColor = mixColors(baseBg, SakuraTheme.ACCENT, tint);
                baseBorder = SakuraTheme.ACCENT;
                textColor = SakuraTheme.TEXT;
            } else {
                fillColor = baseBg;
            }

            float bgAlpha = this.active ? (0.86f + 0.14f * hoverT) : 0.7f;
            float borderAlpha = this.active ? (0.55f + 0.45f * hoverT) : 0.55f;
            if (selected) {
                bgAlpha = Math.max(bgAlpha, 0.96f);
                borderAlpha = 1.0f;
            }
            if (pressT > 0f) {
                bgAlpha *= (1.0f - 0.12f * pressT);
            }

            NanoVGHelper.drawRoundRect(getX(), getY(), width, height, SakuraTheme.ROUNDING,
                    new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int) (255 * bgAlpha)));

            Color borderColor = primary || danger || selected ? baseBorder : SakuraTheme.BUTTON_BORDER;
            float borderW = selected ? 1.25f : 1.0f;
            NanoVGHelper.drawRoundRectOutline(getX(), getY(), width, height, SakuraTheme.ROUNDING, borderW,
                    new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), (int) (255 * borderAlpha)));

            float textX = cx;
            float spinnerAlpha = loadingT;
            if (spinnerAlpha > 0.001f) {
                textX -= 8.0f * spinnerAlpha;
            }

            NanoVG.nvgFontSize(vg, 14.0f);
            NanoVG.nvgFontFaceId(vg, FontLoader.regular(14.0f));
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
            NanoVG.nvgFillColor(vg, SakuraTheme.color(textColor));
            NanoVG.nvgText(vg, textX, cy + 1, getMessage().getString());

            if (spinnerAlpha > 0.001f) {
                float r = 5.0f;
                float sx = getX() + width - 18.0f;
                float sy = cy + 0.5f;
                float t = ((System.currentTimeMillis() - loadingStartMs) / 1000.0f) * 6.0f;
                float start = t;
                float end = t + 4.4f;

                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgArc(vg, sx, sy, r, start, end, NanoVG.NVG_CW);
                NanoVG.nvgStrokeWidth(vg, 1.75f);
                NanoVG.nvgStrokeColor(vg, SakuraTheme.color(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), (int) (180 * spinnerAlpha))));
                NanoVG.nvgStroke(vg);
            }

            NanoVG.nvgRestore(vg);
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
