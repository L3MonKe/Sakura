package dev.mahiro.client.gui.clickgui.component.values;

import dev.mahiro.client.gui.Component;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.DecelerateAnimation;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.render.RenderUtil;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.text.DecimalFormat;

public class NumberValueComponent extends Component {
    private static NumberValueComponent currentEditing = null;

    private final NumberValue<? extends Number> setting;
    private boolean dragging;
    private final Animation drag = new DecelerateAnimation(250, 1);
    private float anim;
    private final boolean isInteger;

    private boolean editing = false;
    private String tempText = "";
    private int cursorPos = 0;
    private long lastBlinkTime = 0;
    private boolean cursorVisible = true;

    public NumberValueComponent(NumberValue<? extends Number> setting) {
        this.setting = setting;
        this.isInteger = setting.get() instanceof Integer;
        drag.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        setHeight(30 * scale);
        float w = getWidth();
        double min = setting.getMin().doubleValue();
        double max = setting.getMax().doubleValue();
        double current = setting.get().doubleValue();

        anim = RenderUtil.animate(anim, (float) (w * (current - min) / (max - min)), 50);
        float sliderWidth = anim;
        drag.setDirection(dragging ? Direction.FORWARDS : Direction.BACKWARDS);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlinkTime > 530) {
            cursorVisible = !cursorVisible;
            lastBlinkTime = currentTime;
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawString(setting.getDisplayName(), getX(), getY(), FontLoader.regular(), titleFontSize, Color.WHITE);

            if (editing) {
                float inputWidth = w;
                float inputX = getX();
                float inputY = getY() + 7 * scale;
                float inputHeight = 12 * scale;

                NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale, new Color(60, 60, 80));
                NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.75f * scale, new Color(100, 100, 150));

                float textFontSize = baseFontSize * 0.6f;
                String displayText = tempText;
                NanoVGHelper.drawString(displayText, inputX + 3 * scale, inputY + 9 * scale, FontLoader.regular(), textFontSize, new Color(255, 255, 255));

                if (cursorVisible) {
                    String beforeCursor = tempText.substring(0, Math.min(cursorPos, tempText.length()));
                    float cursorX = inputX + 3 * scale + NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(), textFontSize);
                    if (cursorX < inputX + inputWidth - 3 * scale) {
                        NanoVGHelper.drawRect(cursorX, inputY + 2 * scale, 0.5f * scale, inputHeight - 4 * scale, new Color(255, 255, 255));
                    }
                }

                String hint = "Enter";
                float hintFontSize = baseFontSize * 0.5f;
                float hintWidth = NanoVGHelper.getTextWidth(hint, FontLoader.regular(), hintFontSize);
                NanoVGHelper.drawString(hint, inputX + inputWidth - hintWidth - 2 * scale, inputY + inputHeight / 2 + 2 * scale, FontLoader.regular(), hintFontSize, new Color(150, 150, 150));
            } else {
                String minStr = isInteger ? String.valueOf(setting.getMin().intValue()) : String.valueOf(setting.getMin());
                String maxStr = isInteger ? String.valueOf(setting.getMax().intValue()) : String.valueOf(setting.getMax());
                String currentStr;
                if (isInteger) {
                    currentStr = String.valueOf(setting.get().intValue());
                } else {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    currentStr = df.format(current);
                }

                float labelFontSize = baseFontSize * 0.5f;
                NanoVGHelper.drawString(minStr, getX(), getY() + 18 * scale, FontLoader.regular(), labelFontSize, new Color(255, 255, 255, 255));
                float currentTextWidth = NanoVGHelper.getTextWidth(currentStr, FontLoader.regular(), labelFontSize);
                NanoVGHelper.drawString(currentStr, getX() + (getWidth() - currentTextWidth) / 2f, getY() + 18 * scale, FontLoader.regular(), labelFontSize, new Color(255, 255, 255, 255));
                NanoVGHelper.drawString(maxStr, getX() + getWidth() - NanoVGHelper.getTextWidth(maxStr, FontLoader.regular(), labelFontSize), getY() + 18 * scale, FontLoader.regular(), labelFontSize, new Color(255, 255, 255, 255));

                NanoVGHelper.drawRoundRect(getX(), getY() + 7 * scale, w, 4 * scale, 2 * scale, new Color(200, 200, 200, 255));
                NanoVGHelper.drawGradientRRect2(getX(), getY() + 7 * scale, sliderWidth, 4 * scale, 2 * scale, ClickGui.color(0), ClickGui.color2(0));
                NanoVGHelper.drawCircle(getX() + sliderWidth, getY() + 9 * scale, 3.5f * scale, new Color(255, 255, 255));
            }
        });

        if (dragging && !editing) {
            final double difference = max - min;
            final double value = min + MathHelper.clamp((mouseX - getX()) / w, 0, 1) * difference;
            setValueFromDouble(MathUtil.incValue(value, setting.getStep().doubleValue()));
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @SuppressWarnings("unchecked")
    private void setValueFromDouble(double value) {
        if (isInteger) {
            ((NumberValue<Integer>) setting).set((int) value);
        } else {
            ((NumberValue<Double>) setting).set(value);
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        float w = getWidth();

        if (RenderUtil.isHovering(getX(), getY() + 7 * scale, w, 4 * scale, (float) click.x(), (float) click.y())) {
            if (click.button() == 0 && !editing) {
                dragging = true;
            } else if (click.button() == 1 && !editing) {
                if (currentEditing != null && currentEditing != this) {
                    currentEditing.finishEditing();
                }
                currentEditing = this;
                editing = true;
                if (isInteger) {
                    tempText = String.valueOf(setting.get().intValue());
                } else {
                    tempText = String.valueOf(setting.get().doubleValue());
                }
                cursorPos = tempText.length();
                lastBlinkTime = System.currentTimeMillis();
                cursorVisible = true;
                return true;
            }
        } else if (editing && click.button() == 0) {
            finishEditing();
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            dragging = false;
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (!editing) return false;

        switch (input.getKeycode()) {
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                finishEditing();
                return true;
            }
            case GLFW.GLFW_KEY_ESCAPE -> {
                editing = false;
                return true;
            }
            case GLFW.GLFW_KEY_BACKSPACE -> {
                if (cursorPos > 0) {
                    tempText = tempText.substring(0, cursorPos - 1) + tempText.substring(cursorPos);
                    cursorPos--;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_DELETE -> {
                if (cursorPos < tempText.length()) {
                    tempText = tempText.substring(0, cursorPos) + tempText.substring(cursorPos + 1);
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_LEFT -> {
                if (cursorPos > 0) {
                    cursorPos--;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                if (cursorPos < tempText.length()) {
                    cursorPos++;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_HOME -> {
                cursorPos = 0;
                resetCursor();
                return true;
            }
            case GLFW.GLFW_KEY_END -> {
                cursorPos = tempText.length();
                resetCursor();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (!editing) return false;

        int chr = input.codepoint();
        if (!Character.isDigit(chr) && chr != '.' && chr != '-') {
            return false;
        }

        if (chr == '.' && (isInteger || tempText.contains("."))) {
            return false;
        }

        if (chr == '-' && cursorPos != 0) {
            return false;
        }

        tempText = tempText.substring(0, cursorPos) + chr + tempText.substring(cursorPos);
        cursorPos++;
        resetCursor();

        return true;
    }

    private void finishEditing() {
        editing = false;
        if (currentEditing == this) {
            currentEditing = null;
        }
        try {
            double value = Double.parseDouble(tempText);
            double min = setting.getMin().doubleValue();
            double max = setting.getMax().doubleValue();
            value = Math.max(min, Math.min(max, value));
            setValueFromDouble(value);
        } catch (NumberFormatException ignored) {
        }
    }

    private void resetCursor() {
        lastBlinkTime = System.currentTimeMillis();
        cursorVisible = true;
    }

    @Override
    public boolean isVisible() {
        return setting.isAvailable();
    }
}
