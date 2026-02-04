package dev.mahiro.client.gui.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.HudEditor;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
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
        for (Module module : Mahiro.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                hud.renderInEditor(context, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (hudPanel.mouseClicked(click, doubled)) {
            return true;
        }

        for (Module module : Mahiro.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                if (hud.mouseClicked((float) click.x(), (float) click.y(), click.button())) {
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        hudPanel.mouseReleased(click);

        for (Module module : Mahiro.MODULES.getAllModules()) {
            if (module instanceof HudModule hud && hud.isEnabled()) {
                hud.mouseReleased(click.button());
            }
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (hudPanel.keyPressed(input)) {
            return true;
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (hudPanel.charTyped(input)) {
            return true;
        }
        return super.charTyped(input);
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
        HudEditor hudEditor = Mahiro.MODULES.getModule(HudEditor.class);
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
