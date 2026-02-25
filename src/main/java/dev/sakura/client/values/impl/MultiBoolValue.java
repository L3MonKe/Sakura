package dev.sakura.client.values.impl;

import dev.sakura.client.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultiBoolValue extends Value<List<BoolValue>> {
    public List<BoolValue> options;
    public int index;

    public MultiBoolValue(String name, String chineseName, Dependency dependency, List<BoolValue> options) {
        super(name, chineseName, dependency);
        this.options = options;
        index = options.size();
    }

    public MultiBoolValue(String name, String chineseName, List<BoolValue> options) {
        super(name, chineseName);
        this.options = options;
        index = options.size();
    }

    public boolean isEnabled(String name) {
        return Objects.requireNonNull(this.options.stream().filter((option) -> option.getName().equalsIgnoreCase(name)).findFirst().orElse(null)).get();
    }

    public void set(String name, boolean value) {
        Objects.requireNonNull(this.options.stream().filter((option) -> option.getName().equalsIgnoreCase(name)).findFirst().orElse(null)).set(value);
    }

    public List<BoolValue> getToggled() {
        return this.options.stream().filter(BoolValue::get).collect(Collectors.toList());
    }

    public String isEnabled() {
        List<String> includedOptions = new ArrayList<>();
        for (BoolValue option : options) {
            if (option.get()) {
                includedOptions.add(option.getName());
            }
        }
        return String.join(", ", includedOptions);
    }

    public void set(int index, boolean value) {
        this.options.get(index).set(value);
    }

    public boolean isEnabled(int index) {
        return this.options.get(index).get();
    }

    public List<BoolValue> getValues() {
        return this.options;
    }

    @Override
    public void reset() {
        for (BoolValue option : options) {
            option.reset();
        }
    }
}
