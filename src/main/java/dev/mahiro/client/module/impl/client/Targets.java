package dev.mahiro.client.module.impl.client;

import dev.mahiro.client.manager.impl.CombatManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.MultiBoolValue;

import java.util.List;

public class Targets extends Module {
    public final List<BoolValue> targetOptions =
            List.of(
                    new BoolValue("Player", "玩家", true),
                    new BoolValue("Mobs", "怪物", true),
                    new BoolValue("Animals", "动物", true),
                    new BoolValue("Passive", "被动实体", false)
            );

    public final List<BoolValue> ignoreOptions =
            List.of(
                    new BoolValue("Invisible", "隐形", true),
                    new BoolValue("Dead", "死亡", true)
            );

    public Targets() {
        super("Targets", "目标设置", Category.Client);
    }

    public final EnumValue<CombatManager.TargetBy> targetBy = new EnumValue<>("Target By", "目标优先级", CombatManager.TargetBy.Distance);
    public final MultiBoolValue targets = new MultiBoolValue("Select", "选择", targetOptions);
    public final MultiBoolValue check = new MultiBoolValue("Ignore", "忽略", ignoreOptions);

    @Override
    public void onEnable() {
        setState(false);
    }

    @Override
    public void onDisable() {
        setState(false);
    }
}