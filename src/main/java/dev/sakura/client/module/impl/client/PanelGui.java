package dev.sakura.client.module.impl.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.gui.panelgui.PanelGuiScreen;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import org.lwjgl.glfw.GLFW;

public class PanelGui extends Module {
    public PanelGui() {
        super("PanelGui", "面板GUI", Category.Client);
        setKey(GLFW.GLFW_KEY_INSERT);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null && mc.mouse == null) {
            toggle();
            return;
        }

        ClickGui clickGui = Sakura.MODULES.getModule(ClickGui.class);
        if (mc.currentScreen instanceof ClickGuiScreen && clickGui.isEnabled()) {
            clickGui.toggle();
        }
        mc.setScreen(Sakura.PANELGUI);
    }

    @Override
    protected void onDisable() {
        if (mc.currentScreen instanceof PanelGuiScreen) {
            mc.setScreen(null);
        }
    }
}

