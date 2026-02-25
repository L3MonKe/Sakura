package dev.sakura.client.events.render.item;

import dev.sakura.client.events.Cancellable;

public class EatTransformationEvent extends Cancellable {
    private float factor;

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }
}
