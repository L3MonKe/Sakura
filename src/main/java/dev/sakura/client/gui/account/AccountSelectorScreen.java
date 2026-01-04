package dev.sakura.client.gui.account;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.gui.account.list.AccountEntry;
import dev.sakura.client.gui.account.list.AccountListWidget;
import dev.sakura.client.gui.component.SakuraButton;
import dev.sakura.client.gui.component.SakuraSearchField;
import dev.sakura.client.gui.theme.SakuraTheme;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.DecelerateAnimation;
import dev.sakura.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.session.Session;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public final class AccountSelectorScreen extends Screen {
    private final Screen parent;
    private AccountListWidget accountListWidget;
    private SakuraSearchField searchWidget;

    private boolean showDeleteConfirm = false;
    private AccountEntry accountToDelete = null;
    private SakuraButton confirmDeleteButton;
    private SakuraButton cancelDeleteButton;

    private final Animation fadeAnim = new DecelerateAnimation(300, 1.0);

    public AccountSelectorScreen(final Screen parent) {
        super(Text.of("Account Selector"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        fadeAnim.setDirection(Direction.FORWARDS);

        int panelWidth = Math.min(600, width - 40);
        int panelHeight = Math.min(400, height - 40);
        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        clearChildren();

        addDrawable((context, mouseX, mouseY, delta) -> {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.disableCull();

            NanoVGRenderer.INSTANCE.draw(vg -> {
                // 阴影
                NanoVGHelper.drawShadow(panelX, panelY, panelWidth, panelHeight, SakuraTheme.ROUNDING, new Color(0, 0, 0, 128), 20, 0, 2);

                // 面板主体
                NanoVGHelper.drawRoundRect(panelX, panelY, panelWidth, panelHeight, SakuraTheme.ROUNDING, SakuraTheme.PANEL_BG);

                // Border
                // 边框
                NanoVGHelper.drawRoundRectOutline(panelX, panelY, panelWidth, panelHeight, SakuraTheme.ROUNDING, 1.5f, new Color(0, 0, 0, 50));
            });
        });

        // 搜索组件
        searchWidget = new SakuraSearchField(client.textRenderer, panelX + 20, panelY + 20, panelWidth - 40, 24, Text.of("Search..."));
        searchWidget.setPlaceholderText("Search accounts...");
        searchWidget.setChangedListener(s -> {
            if (accountListWidget != null) {
                accountListWidget.setSearchFilter(s);
            }
        });
        addDrawableChild(searchWidget);

        // 列表组件
        int listY = panelY + 55;
        int listHeight = panelHeight - 110;
        accountListWidget = new AccountListWidget(client, panelWidth - 40, listHeight, listY, 30);
        accountListWidget.setX(panelX + 20);
        accountListWidget.populateEntries();
        addDrawableChild(accountListWidget);

        // 按钮布局
        initButtons(panelX, panelY, panelWidth, panelHeight);

        // 初始化弹出窗口按钮（不添加到可绘制子项）
        int popupWidth = 240;
        int popupHeight = 120;
        int popupX = (width - popupWidth) / 2;
        int popupY = (height - popupHeight) / 2;
        int btnWidth = 80;

        confirmDeleteButton = new SakuraButton(popupX + 20, popupY + popupHeight - 35, btnWidth, 24, "Yes", (b) -> {
            if (accountToDelete != null) {
                Managers.ACCOUNT.unregister(accountToDelete.getAccount());
                accountListWidget.populateEntries();
            }
            showDeleteConfirm = false;
            accountToDelete = null;
        });

        cancelDeleteButton = new SakuraButton(popupX + popupWidth - 20 - btnWidth, popupY + popupHeight - 35, btnWidth, 24, "No", (b) -> {
            showDeleteConfirm = false;
            accountToDelete = null;
        });
    }

    private void initButtons(int panelX, int panelY, int panelWidth, int panelHeight) {
        int buttonWidth = 100;
        int buttonHeight = 24;
        int bottomY = panelY + panelHeight - 35;
        int spacing = 10;

        // 导航按钮
        addDrawableChild(new SakuraButton(panelX + 20, bottomY, 80, buttonHeight, "Back",
                (b) -> client.setScreen(parent)));

        addDrawableChild(new SakuraButton(panelX + panelWidth - 100, bottomY, 80, buttonHeight, "Encrypt",
                (b) -> client.setScreen(new AccountEncryptionScreen(this))));

        // 操作按钮
        int centerGroupWidth = (buttonWidth * 3) + (spacing * 2);
        int centerStartX = panelX + (panelWidth - centerGroupWidth) / 2;

        addDrawableChild(new SakuraButton(centerStartX, bottomY, buttonWidth, buttonHeight, "Add",
                (b) -> client.setScreen(new AccountAddAccountScreen(this))));

        addDrawableChild(new SakuraButton(centerStartX + buttonWidth + spacing, bottomY, buttonWidth, buttonHeight, "Login",
                (b) -> loginSelected()));

        addDrawableChild(new SakuraButton(centerStartX + (buttonWidth + spacing) * 2, bottomY, buttonWidth, buttonHeight, "Delete",
                (b) -> deleteSelected()));
    }

    private void loginSelected() {
        AccountEntry entry = accountListWidget.getSelectedOrNull();
        if (entry != null) {
            Session session = entry.getAccount().login();
            if (session != null) Managers.ACCOUNT.setSession(session);
        }
    }

    private void deleteSelected() {
        AccountEntry entry = accountListWidget.getSelectedOrNull();
        if (entry == null) return;

        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), InputUtil.GLFW_KEY_LEFT_SHIFT)) {
            Managers.ACCOUNT.unregister(entry.getAccount());
            accountListWidget.populateEntries();
            return;
        }

        accountToDelete = entry;
        showDeleteConfirm = true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (showDeleteConfirm) {
            if (confirmDeleteButton.mouseClicked(mouseX, mouseY, button)) return true;
            if (cancelDeleteButton.mouseClicked(mouseX, mouseY, button)) return true;
            return true;
        }

        if (searchWidget != null && !searchWidget.isMouseOver(mouseX, mouseY)) {
            searchWidget.setFocused(false);
            if (getFocused() == searchWidget) {
                setFocused(null);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (showDeleteConfirm) {
            if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                showDeleteConfirm = false;
                accountToDelete = null;
                return true;
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Shader2DUtil.drawQuadBlur(context.getMatrices(), 0, 0, width, height, 10, 0.5f);

        super.render(context, mouseX, mouseY, delta);

        if (showDeleteConfirm) {
            int popupWidth = 240;
            int popupHeight = 120;
            int popupX = (width - popupWidth) / 2;
            int popupY = (height - popupHeight) / 2;

            NanoVGRenderer.INSTANCE.draw(vg -> {
                NanoVGHelper.drawShadow(popupX, popupY, popupWidth, popupHeight, SakuraTheme.ROUNDING, new Color(0, 0, 0, 128), 20, 0, 2);

                NanoVGHelper.drawRoundRect(popupX, popupY, popupWidth, popupHeight, SakuraTheme.ROUNDING, SakuraTheme.PANEL_BG);

                NanoVGHelper.drawRoundRectOutline(popupX, popupY, popupWidth, popupHeight, SakuraTheme.ROUNDING, 1.5f, new Color(0, 0, 0, 50));

                NanoVGHelper.drawText("Delete Account?", popupX + popupWidth / 2.0f, popupY + 15, FontLoader.bold(18.0f), 18.0f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP, SakuraTheme.TEXT);

                String name = accountToDelete != null ? accountToDelete.getAccount().username() : "Unknown";
                Color secondaryText = new Color(SakuraTheme.TEXT.getRed(), SakuraTheme.TEXT.getGreen(), SakuraTheme.TEXT.getBlue(), (int) (255 * 0.8f));

                NanoVGHelper.drawText("Are you sure you want to remove", popupX + popupWidth / 2.0f, popupY + 45, FontLoader.regular(14.0f), 14.0f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP, secondaryText);
                NanoVGHelper.drawText("'" + name + "'?", popupX + popupWidth / 2.0f, popupY + 60, FontLoader.regular(14.0f), 14.0f, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP, secondaryText);
            });

            confirmDeleteButton.render(context, mouseX, mouseY, delta);
            cancelDeleteButton.render(context, mouseX, mouseY, delta);
        }
    }
}
