package dev.sakura.client.module.impl.movement;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;

public class KeepSprint extends Module {

    public enum Mode {
        Prediction,
        Vanilla
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Prediction);

    public KeepSprint() {
        super("KeepSprint", "保持疾跑", Category.Movement);
    }

    @Override
    protected void onDisable() {
    }

    public void onAttackKnockback() {
        if (nullCheck()) return;
    }

    public boolean isVanilla() {
        return mode.get() == Mode.Vanilla;
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

}
