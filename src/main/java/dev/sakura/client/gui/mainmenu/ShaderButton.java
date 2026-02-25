package dev.sakura.client.gui.mainmenu;

import dev.sakura.client.shaders.MainMenuShader;

public class ShaderButton extends MenuButton {
    public ShaderButton(int x, int y, int width, int height) {
        super(x, y, width, height, "背景: " + MainMenuShader.getSharedInstance().getCurrentShaderType().getDisplayName(), null, true);
    }

    public void nextShader() {
        MainMenuShader.getSharedInstance().nextShader();
        this.text = "背景: " + MainMenuShader.getSharedInstance().getCurrentShaderType().getDisplayName();
    }

    public void previousShader() {
        MainMenuShader.getSharedInstance().previousShader();
        this.text = "背景: " + MainMenuShader.getSharedInstance().getCurrentShaderType().getDisplayName();
    }
}
