package dev.sakura.values;

public abstract class Value<V> {
    protected final Dependency dependency;
    protected V value;
    protected V defaultValue;
    protected final String name;

    public Value(String name, Dependency dependency) {
        this.name = name;
        this.dependency = dependency;
    }

    public void reset() {
        this.value = this.defaultValue;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public Value(String name, String description) {
        this(name, () -> true);
    }

    public Value(String name) {
        this(name, () -> true);
    }

    public V get() {
        return this.value;
    }

    public void set(V value) {
        this.value = value;
    }

    public boolean isAvailable() {
        return dependency != null && this.dependency.check();
    }

    @FunctionalInterface
    public interface Dependency {
        boolean check();
    }

    // Getter methods
    public Dependency getDependency() {
        return dependency;
    }

    public V getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    // Setter methods
    public void setValue(V value) {
        this.value = value;
    }
}
