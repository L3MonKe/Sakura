package dev.mzc.client.events.input;

import dev.mzc.client.events.Cancellable;
import dev.mzc.client.events.misc.KeyAction;

public class MouseButtonEvent extends Cancellable {
    private final int button;
    private final KeyAction action;

    public MouseButtonEvent(int button, KeyAction action) {
        this.setCancelled(false);
        this.button = button;
        this.action = action;
    }

    public int getButton() {
        return button;
    }

    public KeyAction getAction() {
        return action;
    }
}