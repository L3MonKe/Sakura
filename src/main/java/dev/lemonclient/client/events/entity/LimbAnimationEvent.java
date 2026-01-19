package dev.lemonclient.client.events.entity;

import dev.lemonclient.client.events.Cancellable;

public final class LimbAnimationEvent extends Cancellable {
    float speed;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
