package dev.sakura.client.module.impl.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.gui.hud.HudEditorScreen;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;

public class HudEditor extends Module {
    public static final BoolValue chatBloom = new BoolValue("Chat Bloom", "聊天栏光晕", true);
    public static final NumberValue<Double> radius = new NumberValue<>("Radius", "聊天栏圆角半径", 2.5, 0.0, 20.0, 0.5, chatBloom::get);


    public HudEditor() {
        super("HudEditor", "HUD编辑器", Category.Client);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen instanceof ClickGuiScreen) {
            mc.currentScreen.close();
        }

        if (mc.player != null && !(mc.currentScreen instanceof HudEditorScreen)) {
            mc.setScreen(Sakura.HUDEDITOR);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.currentScreen instanceof HudEditorScreen) {
            mc.setScreen(null);
        }

        Sakura.CONFIG.saveDefaultConfig();
    }
}
