package dev.sakura.client.gui.clickgui.component.values;

import dev.sakura.client.gui.Component;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.values.impl.StringValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class StringValueComponent extends Component {
    private final StringValue setting;
    private boolean editing = false;
    private String tempText = "";
    private int cursorPos = 0;
    private long lastBlinkTime = 0;
    private boolean cursorVisible = true;

    public StringValueComponent(StringValue setting) {
        this.setting = setting;
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float baseFontSize = (float) ClickGui.getFontSize();
        float titleFontSize = baseFontSize * 0.75f;
        setHeight(26 * scale);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlinkTime > 530) {
            cursorVisible = !cursorVisible;
            lastBlinkTime = currentTime;
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawString(setting.getDisplayName(), getX(), getY(), FontLoader.regular(titleFontSize), titleFontSize, new Color(255, 255, 255, 255));

            float inputWidth = getWidth();
            float inputX = getX();
            float inputY = getY() + 5 * scale;
            float inputHeight = 12 * scale;

            NanoVGHelper.drawRoundRect(inputX, inputY, inputWidth, inputHeight, 2 * scale,
                    editing ? new Color(60, 60, 80) : new Color(40, 40, 40));

            NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputWidth, inputHeight, 2 * scale, 0.5f * scale,
                    editing ? new Color(100, 100, 150) : new Color(80, 80, 80));

            String displayText = editing ? tempText : setting.get();
            if (displayText == null) displayText = "";

            float textFontSize = baseFontSize * 0.65f;
            float textWidth = NanoVGHelper.getTextWidth(displayText, FontLoader.regular(textFontSize), textFontSize);
            String trimmedText = displayText;

            if (textWidth > inputWidth - 6 * scale) {
                while (textWidth > inputWidth - 6 * scale && !trimmedText.isEmpty()) {
                    if (editing && cursorPos == displayText.length()) {
                        trimmedText = trimmedText.substring(1);
                    } else {
                        trimmedText = trimmedText.substring(0, trimmedText.length() - 1);
                    }
                    textWidth = NanoVGHelper.getTextWidth(trimmedText + (editing && cursorPos == displayText.length() ? "" : "..."),
                            FontLoader.regular(textFontSize), textFontSize);
                }
                if (!editing || cursorPos < displayText.length()) {
                    trimmedText = trimmedText + "...";
                }
            }

            NanoVGHelper.drawString(trimmedText, inputX + 2 * scale, inputY + 9 * scale,
                    FontLoader.regular(textFontSize), textFontSize,
                    editing ? new Color(255, 255, 255) : new Color(200, 200, 200));

            if (editing && cursorVisible) {
                String beforeCursor = tempText.substring(0, Math.min(cursorPos, tempText.length()));
                float cursorX = inputX + 2 * scale + NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(textFontSize), textFontSize);

                if (cursorX < inputX + inputWidth - 2 * scale) {
                    NanoVGHelper.drawRect(cursorX, inputY + 2 * scale, 0.5f * scale, inputHeight - 4 * scale, new Color(255, 255, 255));
                }
            }
        });

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        float inputWidth = getWidth() - 8 * scale;
        float inputX = getX() + 1 * scale;
        float inputY = getY() + 5 * scale;
        float inputHeight = 12 * scale;

        if (RenderUtil.isHovering(inputX, inputY, inputWidth, inputHeight, (float) mouseX, (float) mouseY) && mouseButton == 0) {
            if (!editing) {
                editing = true;
                tempText = setting.get();
                cursorPos = tempText.length();
                lastBlinkTime = System.currentTimeMillis();
                cursorVisible = true;
            }
            return true;
        } else if (editing && mouseButton == 0) {
            finishEditing();
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!editing) return false;

        switch (keyCode) {
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
    public boolean charTyped(char chr, int modifiers) {
        if (!editing) return false;

        if (setting.isOnlyNumber() && !Character.isDigit(chr) && chr != '.' && chr != '-') {
            return false;
        }

        tempText = tempText.substring(0, cursorPos) + chr + tempText.substring(cursorPos);
        cursorPos++;
        resetCursor();

        return true;
    }

    private void finishEditing() {
        editing = false;
        setting.setText(tempText);
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
