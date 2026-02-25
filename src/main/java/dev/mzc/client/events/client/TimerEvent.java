package dev.mzc.client.events.client;

import dev.mzc.client.events.Cancellable;

public class TimerEvent extends Cancellable {
    private float timer;
    private boolean modified;

    public TimerEvent() {
        timer = 1f;
    }

    public float get() {
        return this.timer;
    }

    public void set(float timer) {
        this.modified = true;
        this.timer = timer;
    }

    public boolean isModified() {
        return this.modified;
    }
}
