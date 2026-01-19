package dev.lemonclient.client.gui.account.list;

import dev.lemonclient.client.account.type.MinecraftAccount;
import dev.lemonclient.client.account.type.impl.MicrosoftAccount;
import dev.lemonclient.client.gui.theme.SakuraTheme;
import dev.lemonclient.client.manager.Managers;
import dev.lemonclient.client.nanovg.NanoVGRenderer;
import dev.lemonclient.client.nanovg.font.FontLoader;
import dev.lemonclient.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public final class AccountListWidget extends AlwaysSelectedEntryListWidget<AccountEntry> {

    private String searchFilter;

    public AccountListWidget(MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
    }

    public void populateEntries() {
        clearEntries();
        final List<MinecraftAccount> accounts = Managers.ACCOUNT.getAccounts();
        if (!accounts.isEmpty()) {
            for (final MinecraftAccount account : accounts) {
                addEntry(new AccountEntry(account));
            }
            setSelected(getEntry(0));
        }
    }

    @Override
    public int getRowWidth() {
        return width - 10;
    }

    private int getContentsHeight() {
        return this.children().size() * this.itemHeight + 4;
    }

    @Override
    public int getMaxScrollY() {
        return Math.max(0, this.getContentsHeight() - (this.getBottom() - this.getY() - 4));
    }

    protected int getScrollbarX() {
        return this.getX() + this.width - 6;
    }

    protected void renderList(DrawContext context, int mouseX, int mouseY, float delta) {
        List<AccountEntry> entries = children();
        if (searchFilter != null && !searchFilter.isEmpty()) {
            entries = entries.stream()
                    .filter((entry) -> entry.getAccount().username()
                            .toLowerCase()
                            .contains(searchFilter.toLowerCase()))
                    .toList();
        }

        int x = getRowLeft();
        int width = getRowWidth();
        int height = itemHeight - 4;
        int size = entries.size();

        // 1. Render NanoVG elements (Backgrounds & Text) with Scissor
        final List<AccountEntry> finalEntries = entries;
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVG.nvgScissor(vg, getX(), getY(), getWidth(), getHeight());

            for (int i = 0; i < size; ++i) {
                int y = getRowTop(i);
                int m = getRowBottom(i);

                if (m >= getY() && y <= getBottom()) {
                    AccountEntry entry = finalEntries.get(i);
                    final boolean isHovered = Objects.equals(getHoveredEntry(), entry);
                    final boolean isSelected = Objects.equals(getSelectedOrNull(), entry);

                    // 背景/选中状态
                    if (isSelected || isHovered) {
                        NanoVGHelper.drawRoundRect(x, y, width, height, SakuraTheme.ROUNDING, SakuraTheme.SELECTION);
                    }

                    // 文本
                    Color color = isHovered ? SakuraTheme.PRIMARY_HOVER : SakuraTheme.TEXT;

                    // 检查账户是否处于活动状态
                    if (client.getSession() != null &&
                            client.getSession().getUsername().equalsIgnoreCase(entry.getAccount().username())) {
                        color = Color.GREEN;
                    }

                    float fontSize = 18f;
                    float textX = x + 24;
                    NanoVGHelper.drawString(entry.getAccount().username(), textX, y + height / 2f, FontLoader.regular(fontSize), fontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, color);

                    // Draw account typeccount type
                    float usernameWidth = NanoVGHelper.getTextWidth(entry.getAccount().username(), FontLoader.regular(fontSize), fontSize);
                    String typeText = (entry.getAccount() instanceof MicrosoftAccount) ? "Microsoft" : "Cracked";
                    Color typeColor = new Color(150, 150, 150);
                    float typeX = textX + usernameWidth + 8;
                    float typeFontSize = 14f;

                    NanoVGHelper.drawString(typeText, typeX, y + height / 2f, FontLoader.regular(typeFontSize), typeFontSize, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, typeColor);
                }
            }
            NanoVG.nvgResetScissor(vg);
        });

        // 2. Render Vanilla elements (Avatars)illa elements is already handled by caller) - GL Scissor is already handled by caller
        for (int i = 0; i < size; ++i) {
            int y = getRowTop(i);
            int m = getRowBottom(i);
            if (m >= getY() && y <= getBottom()) {
                AccountEntry entry = entries.get(i);
                boolean active = client.getSession() != null && client.getSession().getUsername().equalsIgnoreCase(entry.getAccount().username());
                entry.render(context, i, y, x, width, height, mouseX, mouseY, active, delta);
            }
        }
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // Custom render to avoid default scrollbar and styling
        // 自定义渲染以避免默认滚动条和样式

        // Clip content to list area
        // 将内容剪裁到列表区域
        context.enableScissor(getX(), getY(), getRight(), getBottom());

        this.renderList(context, mouseX, mouseY, delta);

        context.disableScissor();

        // Custom Scrollbar
        // 自定义滚动条
        int maxScroll = getMaxScrollY();
        if (maxScroll > 0) {
            int scrollbarX = getScrollbarX();
            int scrollbarY = getY();
            int scrollbarHeight = getHeight();
            int contentHeight = getContentsHeight();

            int barHeight = (int) ((float) (scrollbarHeight * scrollbarHeight) / (float) contentHeight);
            barHeight = MathHelper.clamp(barHeight, 32, scrollbarHeight - 8);

            int barTop = (int) this.getScrollY() * (scrollbarHeight - barHeight) / maxScroll + scrollbarY;
            if (barTop < scrollbarY) {
                barTop = scrollbarY;
            }

            int finalBarHeight = barHeight;
            int finalBarTop = barTop;

            NanoVGRenderer.INSTANCE.draw(vg -> {
                // Track
                // 轨道
                NanoVGHelper.drawRoundRect(scrollbarX, scrollbarY, 6, scrollbarHeight, 3, new Color(0, 0, 0, 30));

                // Thumb
                // 滑块
                NanoVGHelper.drawRoundRect(scrollbarX, finalBarTop, 6, finalBarHeight, 3, new Color(SakuraTheme.PRIMARY.getRed(), SakuraTheme.PRIMARY.getGreen(), SakuraTheme.PRIMARY.getBlue(), 200));
            });
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        final AccountEntry entry = getEntryAtPosition(mouseX, mouseY);
        if (entry != null) {
            setSelected(entry);
        }
        if (getSelectedOrNull() != null) {
            return getSelectedOrNull().mouseClicked(mouseX, mouseY, button);
        }
        return true;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }
}
