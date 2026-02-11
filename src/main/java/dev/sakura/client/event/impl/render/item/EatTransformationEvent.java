package dev.sakura.client.event.impl.render.item;

import dev.sakura.client.event.Cancellable;

public class EatTransformationEvent extends Cancellable {
    private float factor;

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }
}
