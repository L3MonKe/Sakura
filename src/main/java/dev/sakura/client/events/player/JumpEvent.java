package dev.sakura.client.events.player;

import dev.sakura.client.events.Cancellable;
import dev.sakura.client.events.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
