package dev.mzc.client.events.entity;

import dev.mzc.client.events.Cancellable;

public final class LimbAnimationEvent extends Cancellable {
    float speed;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
