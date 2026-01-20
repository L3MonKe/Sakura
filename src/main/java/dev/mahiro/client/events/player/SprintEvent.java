package dev.mahiro.client.events.player;

import dev.mahiro.client.events.Cancellable;

public class SprintEvent extends Cancellable {
    private boolean sprint = false;

    public boolean isSprint() {
        return sprint;
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }
}
