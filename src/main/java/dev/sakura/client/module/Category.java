package dev.sakura.client.module;

import dev.sakura.client.module.impl.client.ClickGui;

public enum Category {
    Combat("A", "战斗"),
    Movement("C", "移动"),
    Player("B", "玩家"),
    Render("M", "渲染"),
    Client("D", "客户端");

    public final String icon;
    public final String cnName;

    Category(String icon, String cnName) {
        this.icon = icon;
        this.cnName = cnName;
    }

    public String getName() {
        if (ClickGui.language.get() == ClickGui.Language.Chinese) {
            return cnName;
        }
        return name();
    }
}