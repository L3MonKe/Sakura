package dev.mzc.client.module.impl.render;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

public class AntiVanish extends Module {

    public AntiVanish() {
        super("AntiVanish", "反隐身", Category.Render);
        this.setType(ModuleType.All);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}