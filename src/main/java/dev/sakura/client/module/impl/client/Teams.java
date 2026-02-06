package dev.sakura.client.module.impl.client;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;

public class Teams extends Module {
    public Teams() {
        super("Teams", "团队", Category.Client);
    }

    public final BoolValue armorColor = new BoolValue("ArmorColor", "护甲颜色", true);
    public final BoolValue characterColor = new BoolValue("CharacterColor", "字符颜色", false);
}