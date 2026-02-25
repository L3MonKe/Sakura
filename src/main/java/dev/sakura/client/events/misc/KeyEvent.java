package dev.sakura.client.events.misc;

import dev.sakura.client.events.Cancellable;

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
