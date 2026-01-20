package dev.mahiro.client.events.entity;

import dev.mahiro.client.events.Cancellable;

public final class LimbAnimationEvent extends Cancellable {
    float speed;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
