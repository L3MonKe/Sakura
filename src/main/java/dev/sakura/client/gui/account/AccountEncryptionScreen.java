package dev.sakura.client.gui.account;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.gui.component.SakuraButton;
import dev.sakura.client.gui.component.SakuraTextField;
import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public final class AccountEncryptionScreen extends Screen {
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-={}[]\\|'\";:/?.>,<`";
    private static final String[] REQUIREMENTS = {"8+ Characters", "A Special Character", "A Number", "An Uppercase Letter"};

    private final Screen parent;
    private SakuraTextField passwordTextField;

    public AccountEncryptionScreen(final Screen parent) {
        super(Text.of(""));
        this.parent = parent;
    }

    @Override
    protected void init() {
        clearChildren();

        float panelWidth = 300;
        float panelHeight = 260; // Reduced from 320 to 260
        // 从 320 减少到 260
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        float inputWidth = 200;
        float inputX = panelX + (panelWidth - inputWidth) / 2;

        // 背景面板
        addDrawable((context, mouseX, mouseY, delta) -> {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.disableCull();

            NanoVGRenderer.INSTANCE.draw(vg -> {
                // 阴影
                NVGPaint shadowPaint = NanoVG.nvgBoxGradient(vg, panelX, panelY + 2, panelWidth, panelHeight, SakuraTheme.ROUNDING, 20, SakuraTheme.color(0, 0, 0, 128), SakuraTheme.color(0, 0, 0, 0), NVGPaint.create());
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, panelX - 20, panelY - 20, panelWidth + 40, panelHeight + 40);
                NanoVG.nvgFillPaint(vg, shadowPaint);
                NanoVG.nvgFill(vg);

                // 面板主体
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRoundedRect(vg, panelX, panelY, panelWidth, panelHeight, SakuraTheme.ROUNDING);
                NanoVG.nvgFillColor(vg, SakuraTheme.color(SakuraTheme.PANEL_BG));
                NanoVG.nvgFill(vg);

                // 边框
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRoundedRect(vg, panelX, panelY, panelWidth, panelHeight, SakuraTheme.ROUNDING);
                NanoVG.nvgStrokeColor(vg, SakuraTheme.color(SakuraTheme.PRIMARY, 0.3f));
                NanoVG.nvgStrokeWidth(vg, 1.0f);
                NanoVG.nvgStroke(vg);
            });
        });

        float startY = panelY + 60;

        // 密码输入框
        addDrawableChild(passwordTextField = new SakuraTextField(client.textRenderer, (int) inputX, (int) startY, (int) inputWidth, 24, Text.of("")));
        passwordTextField.setPlaceholder("Enter Password...");
        passwordTextField.setPasswordMode(true);

        float buttonY = panelY + panelHeight - 65;
        float buttonSpacing = 28;

        addDrawableChild(new SakuraButton((int) inputX, (int) buttonY, (int) inputWidth, 24, "Encrypt", (action) ->
        {
            if (isPasswordSecure(passwordTextField.getText())) {
                Sakura.CONFIG.setEncryptionPassword(passwordTextField.getText());
                client.setScreen(parent);
            }
        }));

        addDrawableChild(new SakuraButton((int) inputX, (int) (buttonY + buttonSpacing), (int) inputWidth, 24, "Go Back", (action) -> client.setScreen(parent)));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Shader2DUtil.drawQuadBlur(context.getMatrices(), 0, 0, width, height, 10, 0.5f);

        float panelWidth = 300;
        float panelHeight = 260;
        // 保持一致的缩减高度
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            // 标题
            NanoVGHelper.drawText("Encrypt Accounts (" + Managers.ACCOUNT.getAccounts().size() + ")", width / 2f, panelY + 25, FontLoader.regular(24), 24, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, Color.BLACK);

            // 验证指示器
            Color statusColor = isPasswordSecure(passwordTextField.getText()) ? Color.GREEN : Color.RED;
            NanoVGHelper.drawText("*", passwordTextField.getX() - 10, passwordTextField.getY() + (passwordTextField.getHeight() / 2f), FontLoader.regular(18), 18, NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_MIDDLE, statusColor);

            // 要求列表 - 更紧凑的布局
            float reqY = passwordTextField.getY() + passwordTextField.getHeight() + 10; // Reduced gap from 15 to 10
            // 间距从 15 减少到 10
            NanoVGHelper.drawText("Minimum Requirements:", passwordTextField.getX(), reqY, FontLoader.regular(15), 15, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, Color.BLACK);

            reqY += 18; // 间距从 20 减少到 18
            String password = passwordTextField.getText();

            // 检查每个要求以进行着色
            boolean len = password.length() >= 8;
            boolean special = false;
            boolean number = false;
            boolean upper = false;

            for (char c : password.toCharArray()) {
                if (SPECIAL_CHARACTERS.indexOf(c) != -1) special = true;
                if (Character.isDigit(c)) number = true;
                if (Character.isUpperCase(c)) upper = true;
            }

            float col1X = passwordTextField.getX() + 10;
            float col2X = passwordTextField.getX() + 110;

            drawRequirement(REQUIREMENTS[0], len, col1X, reqY, vg);
            drawRequirement(REQUIREMENTS[2], number, col1X, reqY + 16, vg);

            drawRequirement(REQUIREMENTS[1], special, col2X, reqY, vg);
            drawRequirement(REQUIREMENTS[3], upper, col2X, reqY + 16, vg);
        });

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawRequirement(String text, boolean met, float x, float y, long vg) {
        Color color = met ? new Color(0, 150, 0) : new Color(150, 0, 0);

        // 项目符号点
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgCircle(vg, x - 5, y + 8, 2);
        NanoVG.nvgFillColor(vg, SakuraTheme.color(color));
        NanoVG.nvgFill(vg);

        // 用于紧凑列表的较小字体大小
        NanoVGHelper.drawText(text, x, y, FontLoader.regular(14), 14, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, SakuraTheme.TEXT_SECONDARY);
    }

    private boolean isPasswordSecure(final String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        final char[] characters = password.toCharArray();
        for (final char c : characters) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
                hasSpecial = true;
            }
            if (c >= 48 && c <= 57) {
                hasNumber = true;
            }
        }
        return hasUppercase && hasNumber && hasSpecial;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_ESCAPE) {
            client.setScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
