package com.zeta.client.module.impl.client;

import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.values.impl.BoolValue;
import com.zeta.client.values.impl.ColorValue;
import com.zeta.client.values.impl.StringValue;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import java.awt.*;

public class Chat extends Module {
    public Chat() {
        super("Chat", "聊天服务器", Category.Client);
    }

    public final StringValue prefix = new StringValue("Chat Prefix", "聊天前缀", "@");
    public final BoolValue enable = new BoolValue("Enable", "启用", true);
    public final BoolValue enableTab = new BoolValue("Enable Tab", "启用Tab补全", false);
    public final ColorValue color = new ColorValue("Color", "颜色", Color.RED);

    public Text getPlayerName(PlayerListEntry playerListEntry) {
        Text name;
        name = playerListEntry.getDisplayName();
        if (name == null) name = Text.literal(playerListEntry.getProfile().getName());
        return name;
    }
}
