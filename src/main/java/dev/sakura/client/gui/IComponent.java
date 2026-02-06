package dev.sakura.client.gui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

public interface IComponent {
    default void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    default boolean mouseClicked(Click click, boolean doubled) {
        return false;
    }

    default boolean mouseReleased(Click click) {
        return false;
    }

    default boolean keyPressed(KeyInput input) {
        return false;
    }

    default boolean charTyped(CharInput input) {
        return false;
    }
}
