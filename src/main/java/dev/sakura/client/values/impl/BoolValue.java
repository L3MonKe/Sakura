package dev.sakura.client.values.impl;

import dev.sakura.client.values.Value;

public class BoolValue extends Value<Boolean> {
    public BoolValue(String name, String chineseName, boolean defaultValue, Dependency dependency) {
        super(name, chineseName, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public BoolValue(String name, String chineseName, boolean defaultValue) {
        this(name, chineseName, defaultValue, () -> true);
    }
}
