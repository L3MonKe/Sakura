package dev.sakura.client.values;

import dev.sakura.client.module.impl.client.ClickGui;

public abstract class Value<V> {
    protected final Dependency dependency;
    protected V value;
    protected V defaultValue;
    protected final String name;
    protected final String chineseName;

    public Value(String name, String chineseName, Dependency dependency) {
        this.name = name;
        this.chineseName = chineseName;
        this.dependency = dependency;
    }

    public void reset() {
        this.value = this.defaultValue;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public Value(String name, String chineseName) {
        this(name, chineseName, () -> true);
    }

    public V get() {
        return value;
    }

    public void set(V value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        if (ClickGui.language.get() == ClickGui.Language.Chinese) {
            return chineseName == null ? name : chineseName;
        }
        return name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public boolean isAvailable() {
        return dependency != null && this.dependency.check();
    }

    @FunctionalInterface
    public interface Dependency {
        boolean check();
    }

    public Dependency getDependency() {
        return dependency;
    }
}
