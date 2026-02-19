package dev.sakura.client.event.impl.input;

import dev.sakura.client.event.Cancellable;
import dev.sakura.client.event.type.KeyAction;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.MouseInput;

public class MouseClickEvent extends Cancellable {
    private final Click click;
    private final MouseInput input;
    private final KeyAction action;

    public MouseClickEvent(Click click, KeyAction action) {
        this.click = click;
        this.input = click.buttonInfo();
        this.action = action;
    }

    public Click getClick() {
        return this.click;
    }

    public int getButton() {
        return this.input.button();
    }

    public KeyAction getAction() {
        return this.action;
    }
}