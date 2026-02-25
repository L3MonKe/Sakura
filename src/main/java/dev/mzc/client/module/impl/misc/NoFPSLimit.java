package dev.mzc.client.module.impl.misc;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;

public class NoFPSLimit extends Module {
    public static NoFPSLimit INSTANCE;

    public NoFPSLimit() {
        super("NoFPSLimit", "后台不限帧", Category.Misc);
        this.setType(ModuleType.Safe);
        INSTANCE = this;
    }
}
