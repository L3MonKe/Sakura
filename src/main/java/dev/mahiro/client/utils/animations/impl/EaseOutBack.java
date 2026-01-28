package dev.mahiro.client.utils.animations.impl;

import dev.mahiro.client.utils.animations.Animation;
import dev.mahiro.client.utils.animations.Direction;

public class EaseOutBack extends Animation {
    public EaseOutBack(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    @Override
    protected double getEquation(double x) {
        double c1 = 1.70158;
        double c3 = c1 + 1;
        return 1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2);
    }
}
