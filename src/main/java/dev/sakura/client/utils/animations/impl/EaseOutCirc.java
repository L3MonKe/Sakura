package dev.sakura.client.utils.animations.impl;

import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;

public class EaseOutCirc extends Animation {
    public EaseOutCirc(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    @Override
    protected double getEquation(double x) {
        return Math.sqrt(1 - Math.pow(x - 1, 2));
    }
}
