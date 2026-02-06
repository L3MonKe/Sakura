package dev.sakura.client.values.impl;

import dev.sakura.client.values.Value;

public class EnumValue<E extends Enum<E>> extends Value<E> {
    private final E[] modes;
    private final Class<E> enumClass;

    public EnumValue(String name, String chineseName, E defaultValue, Class<E> enumClass, Dependency dependency) {
        super(name, chineseName, dependency);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.enumClass = enumClass;
        this.modes = enumClass.getEnumConstants();
    }

    public EnumValue(String name, String chineseName, E defaultValue, Class<E> enumClass) {
        this(name, chineseName, defaultValue, enumClass, () -> true);
    }

    public EnumValue(String name, String chineseName, E defaultValue, Dependency dependency) {
        this(name, chineseName, defaultValue, defaultValue.getDeclaringClass(), dependency);
    }

    public EnumValue(String name, String chineseName, E defaultValue) {
        this(name, chineseName, defaultValue, () -> true);
    }

    public boolean is(E mode) {
        return this.value == mode;
    }

    public boolean is(String modeName) {
        return this.value.name().equalsIgnoreCase(modeName);
    }

    public E[] getModes() {
        return modes;
    }

    public String[] getModeNames() {
        String[] names = new String[modes.length];
        for (int i = 0; i < modes.length; i++) {
            names[i] = modes[i].name();
        }
        return names;
    }

    public void setMode(E mode) {
        for (E e : modes) {
            if (e == mode) {
                this.set(mode);
                return;
            }
        }
    }

    public void setMode(String modeName) {
        for (E e : modes) {
            if (e.name().equalsIgnoreCase(modeName)) {
                this.set(e);
                return;
            }
        }
    }

    public void cycle() {
        int currentIndex = value.ordinal();
        int nextIndex = (currentIndex + 1) % modes.length;
        this.set(modes[nextIndex]);
    }

    public Class<E> getEnumClass() {
        return enumClass;
    }
}
