package dev.lemonclient.client.events.player;

import dev.lemonclient.client.events.Cancellable;
import dev.lemonclient.client.events.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
