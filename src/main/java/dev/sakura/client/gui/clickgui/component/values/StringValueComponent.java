package dev.sakura.client.gui.clickgui.component.values;

import dev.sakura.client.gui.Component;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.values.impl.StringValue;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
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
            NanoVGHelper.drawString(setting.getDisplayName(), getX(), getY(), FontLoader.regular(), titleFontSize, new Color(255, 255, 255, 255));

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
            float textWidth = NanoVGHelper.getTextWidth(displayText, FontLoader.regular(), textFontSize);
            String trimmedText = displayText;

            if (textWidth > inputWidth - 6 * scale) {
                while (textWidth > inputWidth - 6 * scale && !trimmedText.isEmpty()) {
                    if (editing && cursorPos == displayText.length()) {
                        int skip = 1;
                        if (trimmedText.length() > 1 && Character.isHighSurrogate(trimmedText.charAt(0)) && Character.isLowSurrogate(trimmedText.charAt(1))) {
                            skip = 2;
                        }
                        trimmedText = trimmedText.substring(skip);
                    } else {
                        int len = trimmedText.length();
                        int cut = 1;
                        if (len >= 2 && Character.isHighSurrogate(trimmedText.charAt(len - 2)) && Character.isLowSurrogate(trimmedText.charAt(len - 1))) {
                            cut = 2;
                        }
                        trimmedText = trimmedText.substring(0, len - cut);
                    }
                    textWidth = NanoVGHelper.getTextWidth(trimmedText + (editing && cursorPos == displayText.length() ? "" : "..."),
                            FontLoader.regular(), textFontSize);
                }
                if (!editing || cursorPos < displayText.length()) {
                    trimmedText = trimmedText + "...";
                }
            }

            NanoVGHelper.drawString(trimmedText, inputX + 2 * scale, inputY + 9 * scale,
                    FontLoader.regular(), textFontSize,
                    editing ? new Color(255, 255, 255) : new Color(200, 200, 200));

            if (editing && cursorVisible) {
                String beforeCursor = tempText.substring(0, Math.min(cursorPos, tempText.length()));
                float cursorX = inputX + 2 * scale + NanoVGHelper.getTextWidth(beforeCursor, FontLoader.regular(), textFontSize);

                if (cursorX < inputX + inputWidth - 2 * scale) {
                    NanoVGHelper.drawRect(cursorX, inputY + 2 * scale, 0.5f * scale, inputHeight - 4 * scale, new Color(255, 255, 255));
                }
            }
        });

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        float inputWidth = getWidth() - 8 * scale;
        float inputX = getX() + 1 * scale;
        float inputY = getY() + 5 * scale;
        float inputHeight = 12 * scale;

        if (RenderUtil.isHovering(inputX, inputY, inputWidth, inputHeight, (float) click.x(), (float) click.y()) && click.button() == 0) {
            if (!editing) {
                editing = true;
                tempText = setting.get();
                cursorPos = tempText.length();
                lastBlinkTime = System.currentTimeMillis();
                cursorVisible = true;
            }
            return true;
        } else if (editing && click.button() == 0) {
            finishEditing();
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (!editing) return false;

        if (input.isPaste()) {
            String clipboard = getClipboardText();
            if (!clipboard.isEmpty()) {
                String filtered = filterClipboardText(clipboard);
                if (!filtered.isEmpty()) {
                    tempText = tempText.substring(0, cursorPos) + filtered + tempText.substring(cursorPos);
                    cursorPos += filtered.length();
                    resetCursor();
                }
            }
            return true;
        }

        if (input.isCopy()) {
            setClipboardText(tempText);
            return true;
        }

        if (input.isCut()) {
            setClipboardText(tempText);
            tempText = "";
            cursorPos = 0;
            resetCursor();
            return true;
        }

        if (input.isSelectAll()) {
            cursorPos = tempText.length();
            resetCursor();
            return true;
        }

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
                    int deleteCount = 1;
                    if (cursorPos >= 2 && Character.isLowSurrogate(tempText.charAt(cursorPos - 1)) && Character.isHighSurrogate(tempText.charAt(cursorPos - 2))) {
                        deleteCount = 2;
                    }
                    tempText = tempText.substring(0, cursorPos - deleteCount) + tempText.substring(cursorPos);
                    cursorPos -= deleteCount;
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_DELETE -> {
                if (cursorPos < tempText.length()) {
                    int deleteCount = 1;
                    if (cursorPos + 1 < tempText.length() && Character.isHighSurrogate(tempText.charAt(cursorPos)) && Character.isLowSurrogate(tempText.charAt(cursorPos + 1))) {
                        deleteCount = 2;
                    }
                    tempText = tempText.substring(0, cursorPos) + tempText.substring(cursorPos + deleteCount);
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_LEFT -> {
                if (cursorPos > 0) {
                    cursorPos--;
                    if (cursorPos > 0 && Character.isLowSurrogate(tempText.charAt(cursorPos)) && Character.isHighSurrogate(tempText.charAt(cursorPos - 1))) {
                        cursorPos--;
                    }
                    resetCursor();
                }
                return true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                if (cursorPos < tempText.length()) {
                    cursorPos++;
                    if (cursorPos < tempText.length() && Character.isHighSurrogate(tempText.charAt(cursorPos - 1)) && Character.isLowSurrogate(tempText.charAt(cursorPos))) {
                        cursorPos++;
                    }
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

        int codepoint = input.codepoint();
        String str = Character.toString(codepoint);

        if (setting.isOnlyNumber()) {
            char chr = (char) codepoint;
            if (!Character.isDigit(chr) && chr != '.' && chr != '-') {
                return false;
            }
            str = String.valueOf(chr);
        }

        tempText = tempText.substring(0, cursorPos) + str + tempText.substring(cursorPos);
        cursorPos += str.length();
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

    private String getClipboardText() {
        try {
            long window = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            if (window == 0) return "";
            String text = org.lwjgl.glfw.GLFW.glfwGetClipboardString(window);
            return text != null ? text : "";
        } catch (Exception e) {
            return "";
        }
    }

    private void setClipboardText(String text) {
        try {
            long window = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            if (window == 0) return;
            org.lwjgl.glfw.GLFW.glfwSetClipboardString(window, text);
        } catch (Exception ignored) {
        }
    }

    private String filterClipboardText(String text) {
        if (setting.isOnlyNumber()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isDigit(c) || c == '.' || c == '-') {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            if (cp >= 32 && cp != 127 && cp != 167) {
                sb.appendCodePoint(cp);
            }
            i += Character.charCount(cp);
        }
        return sb.toString();
    }

    @Override
    public boolean isVisible() {
        return setting.isAvailable();
    }
}
