package com.zeta.client.events.player;

import com.zeta.client.events.Cancellable;
import com.zeta.client.events.EventType;

public class JumpEvent extends Cancellable {
    private final EventType type;

    public EventType getType() {
        return type;
    }

    public JumpEvent(EventType type) {
        this.type = type;
    }
}
