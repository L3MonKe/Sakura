package dev.mahiro.client.gui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;

public interface IComponent {
    default void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
    }

    default boolean mouseClicked(Click click, boolean doubled) {
        return false;
    }

    default boolean mouseReleased(Click click) {
        return false;
    }

    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default boolean charTyped(CharInput input) {
        return false;
    }
}
