package dev.sakura.client.module.impl.client;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.StringValue;

import java.awt.*;

public class Chat extends Module {
    public Chat() {
        super("Chat", "聊天服务器", Category.Client);
    }

    public final StringValue prefix = new StringValue("Chat Prefix", "聊天前缀", "@");
    public final BoolValue enable = new BoolValue("Enable", "启用", true);
    public final BoolValue enableTab = new BoolValue("Enable Tab", "Tab显示别的用户", true);
    public final ColorValue color = new ColorValue("Color", "颜色", Color.RED);
}
