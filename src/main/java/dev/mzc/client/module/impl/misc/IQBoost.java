package dev.mzc.client.module.impl.misc;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;

public class IQBoost extends Module {

    private final NumberValue<Double> IQBoost = new NumberValue<>("IQBoost", "智商提升", 250.0, 1.0, 1000.0, 1.0);

    public IQBoost() {
        super("IQBoost", "智商提升", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getSuffix() {
        // 获取 IQBoost 的值
        double iqBoostValue = IQBoost.get();

        // 根据 IQBoost 的值动态显示
        String iqBoostText = String.format(" IQ: %.1f", iqBoostValue); // 格式化为 IQBoost: <value>

        if (iqBoostValue > 0) {
            return iqBoostText;
        }
        return "None" + iqBoostText;
    }

}