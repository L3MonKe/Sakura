package dev.mzc.client.gui.mainmenu;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.gui.component.SakuraButton;
import dev.mzc.client.gui.component.SakuraTextField;
import dev.mzc.client.gui.theme.SakuraTheme;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class VerificationScreen extends Screen {
    public static boolean isVerified = false;
    private SakuraTextField username;
    private SakuraTextField password;
    private String errorMessage = "";
    private int failedAttempts = 0;
    private boolean rememberPassword = false;
    private static final String CREDENTIALS_FILE = "sakura_credentials.txt";

    public VerificationScreen() {
        super(Text.of("Verification"));
    }

    @Override
    protected void init() {
        clearChildren();

        float panelWidth = 300;
        float panelHeight = 320;
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        float inputWidth = 200;
        float inputX = panelX + (panelWidth - inputWidth) / 2;
        
        float usernameY = panelY + 50;
        float passwordY = panelY + 90;
        float rememberY = panelY + 130;
        float buttonsY = panelY + 160;
        float buttonHeight = 24;
        float buttonGap = 10;

        // Load credentials if available
        if (!rememberPassword) {
            File file = new File(client.runDirectory, CREDENTIALS_FILE);
            if (file.exists()) {
                try {
                    java.util.List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                    if (!lines.isEmpty()) {
                        String content = lines.get(0);
                        String[] parts = content.split(":", 2);
                        if (parts.length == 2) {
                            rememberPassword = true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 用户名输入框
        username = new SakuraTextField(client.textRenderer, (int) inputX, (int) usernameY, (int) inputWidth, 24, Text.of(""));
        username.setPlaceholder("Username");
        addDrawableChild(username);

        // 密码输入框
        password = new SakuraTextField(client.textRenderer, (int) inputX, (int) passwordY, (int) inputWidth, 24, Text.of(""));
        password.setPlaceholder("Password");
        password.setPasswordMode(true);
        addDrawableChild(password);

        // Fill fields if remember is on
        if (rememberPassword) {
            File file = new File(client.runDirectory, CREDENTIALS_FILE);
            if (file.exists()) {
                try {
                    java.util.List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                    if (!lines.isEmpty()) {
                        String[] parts = lines.get(0).split(":", 2);
                        if (parts.length == 2) {
                            username.setText(parts[0]);
                            password.setText(parts[1]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Remember Password Toggle
        addDrawableChild(new SakuraButton((int) inputX, (int) rememberY, (int) inputWidth, 20, "Remember Password: " + (rememberPassword ? "ON" : "OFF"), (button) -> {
            rememberPassword = !rememberPassword;
            button.setMessage(Text.of("Remember Password: " + (rememberPassword ? "ON" : "OFF")));
        }));

        // 登录按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) buttonsY, (int) inputWidth, (int) buttonHeight, "Login", (action) -> {
            String u = username.getText();
            String p = password.getText();
            if (p.isEmpty()) {
                errorMessage = "Password cannot be empty";
                return;
            }
            if ("MZC8865".equals(u) && "886578".equals(p)) {
                isVerified = true;

                // Save or Clear Credentials
                File f = new File(client.runDirectory, CREDENTIALS_FILE);
                if (rememberPassword) {
                    try {
                        Files.write(f.toPath(), java.util.Collections.singletonList(u + ":" + p), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.exists()) f.delete();
                }

                client.setScreen(new MainMenuScreen());
            } else {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    client.scheduleStop();
                } else {
                    errorMessage = "Invalid Credentials! (" + failedAttempts + "/3)";
                    password.setText("");
                }
            }
        }));

        // Sign Up 按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) (buttonsY + buttonHeight + buttonGap), (int) inputWidth, (int) buttonHeight, "Sign Up", (action) -> {
            client.setScreen(new SignUpScreen(this));
        }));

        // 退出游戏按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) (buttonsY + (buttonHeight + buttonGap) * 2), (int) inputWidth, (int) buttonHeight, "Exit Game", (action) -> {
            client.scheduleStop();
        }));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Shader2DUtil.drawQuadBlur(context.getMatrices(), 0, 0, width, height, 10, 0.5f);

        float panelWidth = 300;
        float panelHeight = 320;
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Background Blur
        Shader2DUtil.drawRoundedBlur(context.getMatrices(), panelX, panelY, panelWidth, panelHeight, SakuraTheme.PANEL_ROUNDING, new Color(0, 0, 0, 0), 10.0f, 1.0f);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            // Draw Panel Background
            NanoVGHelper.drawRoundRect(panelX, panelY, panelWidth, panelHeight, SakuraTheme.PANEL_ROUNDING, SakuraTheme.PANEL_BG);
            NanoVGHelper.drawRoundRectOutline(panelX, panelY, panelWidth, panelHeight, SakuraTheme.PANEL_ROUNDING, 1.5f, new Color(255, 255, 255, 30));

            // 标题
            NanoVGHelper.drawString("Client Verification", width / 2f, panelY + 25, FontLoader.regular(24), 24, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, SakuraTheme.TEXT);
        });

        super.render(context, mouseX, mouseY, delta);

        // 错误信息 - 绘制在最上层，显示在用户名输入框上方
        if (!errorMessage.isEmpty()) {
            NanoVGRenderer.INSTANCE.draw(vg -> {
                NanoVGHelper.drawString(errorMessage, width / 2f, username.getY() - 10, FontLoader.regular(14), 14, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_BOTTOM, new Color(255, 50, 50));
            });
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_ESCAPE) {
            return true; // 阻止 ESC 关闭
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
