package dev.sakura.client.gui.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.HudEditor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private final HudPanel hudPanel;

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
        this.hudPanel = new HudPanel();
        hudPanel.setX(50);
        hudPanel.setY(20);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        hudPanel.render(context, mouseX, mouseY, delta);
        for (Module module : Sakura.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                hud.renderInEditor(context, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (hudPanel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        for (Module module : Sakura.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                if (hud.mouseClicked((float) mouseX, (float) mouseY, button)) {
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        hudPanel.mouseReleased(mouseX, mouseY, button);

        for (Module module : Sakura.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                hud.mouseReleased(button);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (hudPanel.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (hudPanel.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (!hudPanel.isDragging()) {
            hudPanel.setY(hudPanel.getY() + (scrollY > 0 ? 15 : -15));
        }
        return true;
    }

    @Override
    public void close() {
        super.close();
        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        if (hudEditor != null) {
            hudEditor.setState(false);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public HudPanel getHudPanel() {
        return hudPanel;
    }
}
