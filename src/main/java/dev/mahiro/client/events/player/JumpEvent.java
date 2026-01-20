package dev.mahiro.client.events.player;

import dev.mahiro.client.events.Cancellable;
import dev.mahiro.client.events.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
