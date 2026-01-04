package dev.sakura.client.gui.account;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.account.msa.exception.MSAAuthException;
import dev.sakura.client.account.type.MinecraftAccount;
import dev.sakura.client.account.type.impl.CrackedAccount;
import dev.sakura.client.account.type.impl.MicrosoftAccount;
import dev.sakura.client.gui.component.SakuraButton;
import dev.sakura.client.gui.component.SakuraTextField;
import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.AccountManager;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public final class AccountAddAccountScreen extends Screen {
    private final Screen parent;
    private SakuraTextField email, password;

    public AccountAddAccountScreen(final Screen parent) {
        super(Text.of(""));
        this.parent = parent;
    }

    @Override
    protected void init() {
        clearChildren();

        float panelWidth = 300;
        float panelHeight = 260;
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        // 背景面板
        addDrawable((context, mouseX, mouseY, delta) -> {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.disableCull();

            NanoVGRenderer.INSTANCE.draw(vg -> {
                // 阴影
                NVGPaint shadowPaint = NanoVG.nvgBoxGradient(vg, panelX, panelY + 2, panelWidth, panelHeight, SakuraTheme.ROUNDING, 20,
                        SakuraTheme.color(0, 0, 0, 128), SakuraTheme.color(0, 0, 0, 0), NVGPaint.create());
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

        // 相对于面板顶部的 Y 坐标
        float titleY = panelY + 25;
        float emailY = panelY + 60;
        float passwordY = panelY + 94;
        // 24 高度 + 10 间距
        float buttonsY = panelY + 140;
        float buttonGap = 8;
        float buttonHeight = 24;

        float inputWidth = 200;
        float inputX = panelX + (panelWidth - inputWidth) / 2;

        // 邮箱输入框
        addDrawableChild(email = new SakuraTextField(client.textRenderer, (int) inputX, (int) emailY, (int) inputWidth, 24, Text.of("")));
        email.setPlaceholder("Email or Username...");

        // 密码输入框
        addDrawableChild(password = new SakuraTextField(client.textRenderer, (int) inputX, (int) passwordY, (int) inputWidth, 24, Text.of("")));
        password.setPlaceholder("Password (Optional)");
        password.setPasswordMode(true);

        // 添加按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) buttonsY, (int) inputWidth, (int) buttonHeight, "Add", (action) ->
        {
            final String accountEmail = email.getText();
            if (accountEmail.length() >= 3) {
                final String accountPassword = password.getText();
                MinecraftAccount account;
                if (!accountPassword.isEmpty()) {
                    account = new MicrosoftAccount(accountEmail, accountPassword);
                } else {
                    account = new CrackedAccount(accountEmail);
                }

                Managers.ACCOUNT.register(account);
                client.setScreen(parent);
            }
        }));

        // 浏览器登录按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) (buttonsY + buttonHeight + buttonGap), (int) inputWidth, (int) buttonHeight, "Browser...", (action) ->
        {
            try {
                AccountManager.MSA_AUTHENTICATOR.loginWithBrowser((token) ->
                        Sakura.EXECUTOR.execute(() ->
                        {
                            final MicrosoftAccount account = new MicrosoftAccount(token);
                            final Session session = account.login();
                            if (session != null) {
                                Managers.ACCOUNT.setSession(session);
                                Managers.ACCOUNT.register(account);
                                client.setScreen(parent);
                            } else {
                                AccountManager.MSA_AUTHENTICATOR.setLoginStage("Could not login to account");
                            }
                        }));
            } catch (IOException | URISyntaxException | MSAAuthException e) {
                e.printStackTrace();
            }
        }));

        // 返回按钮
        addDrawableChild(new SakuraButton((int) inputX, (int) (buttonsY + (buttonHeight + buttonGap) * 2), (int) inputWidth, (int) buttonHeight, "Go Back", (action) -> client.setScreen(parent)));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Shader2DUtil.drawQuadBlur(context.getMatrices(), 0, 0, width, height, 10, 0.5f);

        float panelWidth = 300;
        float panelHeight = 260;
        float panelX = (width - panelWidth) / 2;
        float panelY = (height - panelHeight) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            // 验证状态（微型指示器）
            Color statusColor = email.getText().length() >= 3 ? Color.green : Color.red;
            // 如果可能的话，在邮箱输入框左侧绘制一个小点或图标代替文本星号，或者直接使用星号
            // 使用现有逻辑但清理了位置
            float inputX = email.getX();
            NanoVGHelper.drawText("*", inputX - 10, email.getY() + (email.getHeight() / 2f), FontLoader.regular(18), 18, NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_MIDDLE, statusColor);

            // 标题
            NanoVGHelper.drawText("Add Account", width / 2f, panelY + 25, FontLoader.regular(24), 24, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, Color.BLACK);
        });

        super.render(context, mouseX, mouseY, delta);
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
