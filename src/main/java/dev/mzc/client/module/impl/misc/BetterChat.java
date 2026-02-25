package dev.mzc.client.module.impl.misc;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;

public class BetterChat extends Module {
    public BetterChat() {
        super("BetterChat", "聊天增强", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    public final BoolValue enableInputAnim = new BoolValue("InputAnimation", "输入栏动画", true);
    public final NumberValue<Integer> inputAnimTime = new NumberValue<>("InputTime", "输入栏时间", 300, 50, 1000, 50);

    public final BoolValue enableMessageAnim = new BoolValue("MessageAnimation", "消息动画", true);
    public final NumberValue<Integer> messageAnimTime = new NumberValue<>("MessageTime", "消息时间", 300, 50, 1000, 50);

    public final BoolValue stackDuplicates = new BoolValue("StackDuplicates", "堆叠重复消息", true);
    public final BoolValue removeMessageIndicator = new BoolValue("NoIndicator", "移除消息指示器", false);
}
