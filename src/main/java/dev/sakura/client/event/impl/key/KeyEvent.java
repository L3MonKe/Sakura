package dev.sakura.client.event.impl.key;

import dev.sakura.client.event.Cancellable;
import dev.sakura.client.event.type.KeyAction;

public class KeyEvent extends Cancellable {
    private final int key;
    private final int modifiers;
    private final KeyAction action;

    public KeyEvent(int key, int modifiers, KeyAction action) {
        this.setCancelled(false);
        this.key = key;
        this.modifiers = modifiers;
        this.action = action;
    }

    public int getKey() {
        return key;
    }

    public int getModifiers() {
        return modifiers;
    }

    public KeyAction getAction() {
        return action;
    }
}
