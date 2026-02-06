package dev.sakura.client.values.impl;

import dev.sakura.client.values.Value;

public class NumberValue<T extends Number> extends Value<T> {
    private final T min;
    private final T max;
    private final T step;

    public NumberValue(String name, String chineseName, T defaultValue, T min, T max, T step, Dependency dependency) {
        super(name, chineseName, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public NumberValue(String name, String chineseName, T defaultValue, T min, T max, T step) {
        this(name, chineseName, defaultValue, min, max, step, () -> true);
    }

    @Override
    public void set(T value) {
        if (value.doubleValue() < min.doubleValue()) {
            super.set(min);
        } else if (value.doubleValue() > max.doubleValue()) {
            super.set(max);
        } else {
            super.set(value);
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
