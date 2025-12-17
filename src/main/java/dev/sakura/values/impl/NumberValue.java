package dev.sakura.values.impl;

import dev.sakura.values.Value;

public class NumberValue<T extends Number> extends Value<T> {
    private final T min;
    private final T max;
    private final T step;

    public NumberValue(String name, T defaultValue, T min, T max, T step, Dependency dependency) {
        super(name, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public NumberValue(String name, T defaultValue, T min, T max, T step) {
        this(name, defaultValue, min, max, step, () -> true);
    }

    @Override
    public void setValue(T value) {
        if (value.doubleValue() < min.doubleValue()) {
            super.setValue(min);
        } else if (value.doubleValue() > max.doubleValue()) {
            super.setValue(max);
        } else {
            super.setValue(value);
        }
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getStep() {
        return step;
    }
}
