package dev.mahiro.client.module.impl.client;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;

public class Targets extends Module {
    public Targets() {
        super("Targets", "目标设置", Category.Client);
    }

    public final BoolValue player = new BoolValue("player", "玩家", true);
    public final BoolValue mobs = new BoolValue("Mobs", "怪物", true);
    public final BoolValue animals = new BoolValue("Animals", "动物", true);

    @Override
    public void onEnable() {
        setState(false);
    }

    @Override
    public void onDisable() {
        setState(true);
    }
}
