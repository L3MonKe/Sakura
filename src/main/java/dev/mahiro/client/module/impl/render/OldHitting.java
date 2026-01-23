package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;

public class OldHitting extends Module {
    public OldHitting() {
        super("OldHitting", "防砍", Category.Render);
    }

    public final BoolValue visibleOffHand = new BoolValue("VisibleOffhand", "左手可视化", false);
}
