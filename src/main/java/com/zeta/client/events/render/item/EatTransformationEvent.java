package com.zeta.client.events.render.item;

import com.zeta.client.events.Cancellable;

public class EatTransformationEvent extends Cancellable {
    private float factor;

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }
}
