package dev.mzc.client.events.player;

import dev.mzc.client.events.Cancellable;
import dev.mzc.client.events.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
