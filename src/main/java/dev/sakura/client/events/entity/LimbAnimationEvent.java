package dev.sakura.client.events.entity;

import dev.sakura.client.events.Cancellable;

public final class LimbAnimationEvent extends Cancellable {
    float speed;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
