package dev.mahiro.client.module.impl.client;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.gui.clickgui.ClickGuiScreen;
import dev.mahiro.client.gui.hud.HudEditorScreen;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;

public class HudEditor extends Module {

    public final NumberValue<Double> globalCornerRadius = new NumberValue<>("GlobalCornerRadius", "聊天栏圆角半径", 6.0, 0.0, 20.0, 1.0);
    public final BoolValue enableChatBloom = new BoolValue("EnableChatBloom", "聊天栏光晕", true);


    public HudEditor() {
        super("HudEditor", "Hud编辑界面", Category.Client);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen instanceof ClickGuiScreen) {
            mc.currentScreen.close();
        }

        if (mc.player != null && !(mc.currentScreen instanceof HudEditorScreen)) {
            mc.setScreen(Mahiro.HUDEDITOR);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.currentScreen instanceof HudEditorScreen) {
            mc.setScreen(null);
        }

        Mahiro.CONFIG.saveDefaultConfig();
    }
}
