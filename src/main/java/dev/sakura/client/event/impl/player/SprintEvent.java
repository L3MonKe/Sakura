package dev.sakura.client.event.impl.player;

import dev.sakura.client.event.Cancellable;

public class SprintEvent extends Cancellable {
    private boolean sprint = false;

    public boolean isSprint() {
        return sprint;
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }
}
