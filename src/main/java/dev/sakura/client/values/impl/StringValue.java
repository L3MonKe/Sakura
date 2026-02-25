package dev.sakura.client.values.impl;

import dev.sakura.client.values.Value;

public class StringValue extends Value<String> {
    private boolean onlyNumber;

    public StringValue(String name, String chineseName, String defaultValue, Dependency dependency) {
        super(name, chineseName, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.onlyNumber = false;
    }

    public StringValue(String name, String chineseName, String defaultValue) {
        super(name, chineseName, () -> true);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.onlyNumber = false;
    }

    public StringValue(String name, String chineseName, String defaultValue, boolean onlyNumber, Dependency dependency) {
        super(name, chineseName, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.onlyNumber = onlyNumber;
    }

    public StringValue(String name, String chineseName, String defaultValue, boolean onlyNumber) {
        super(name, chineseName, () -> true);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.onlyNumber = onlyNumber;
    }

    public String getText() {
        return get();
    }

    public boolean isOnlyNumber() {
        return onlyNumber;
    }

    public void setText(String text) {
        set(text);
    }

    public void setOnlyNumber(boolean onlyNumber) {
        this.onlyNumber = onlyNumber;
    }
}
