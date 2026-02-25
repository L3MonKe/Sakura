package dev.mzc.client.values.impl;

import dev.mzc.client.values.Value;

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
        Number val = value;
        if (min instanceof Integer) val = val.intValue();
        else if (min instanceof Float) val = val.floatValue();
        else if (min instanceof Double) val = val.doubleValue();
        else if (min instanceof Long) val = val.longValue();

        if (val.doubleValue() < min.doubleValue()) {
            super.set(min);
        } else if (val.doubleValue() > max.doubleValue()) {
            super.set(max);
        } else {
            super.set((T) val);
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
