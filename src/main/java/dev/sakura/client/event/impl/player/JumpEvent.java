package dev.sakura.client.event.impl.player;

import dev.sakura.client.event.Cancellable;
import dev.sakura.client.event.type.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
