package dev.sakura.client.gui.panelgui;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.panelgui.components.*;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.client.PanelGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelGuiScreen extends Screen {
    private static final float DEFAULT_WINDOW_X = 50.0f;
    private static final float DEFAULT_WINDOW_Y = 50.0f;
    private static final float DEFAULT_WINDOW_WIDTH = 440.0f;
    private static final float DEFAULT_WINDOW_HEIGHT = 310.0f;
    private static final float SIDEBAR_WIDTH = 98.0f;
    private static final float HEADER_HEIGHT = 24.0f;
    private static final float CONTENT_VERTICAL_PADDING = 6.0f;
    private static final float CONTENT_TOP_MARGIN = 6.0f;
    private static final float CONTENT_BOTTOM_MARGIN = 4.0f;
    private static final float CONTENT_RIGHT_PADDING = 8.0f;
    private static final Color WINDOW_BACKGROUND_COLOR = new Color(0, 0, 0, 90);

    public static float windowX = DEFAULT_WINDOW_X;
    public static float windowY = DEFAULT_WINDOW_Y;
    public static float windowWidth = DEFAULT_WINDOW_WIDTH;
    public static float windowHeight = DEFAULT_WINDOW_HEIGHT;

    private Category selectedCategory = Category.Client;
    private Sidebar sidebar;
    private Card contentCard;
    private ModuleGrid moduleGrid;

    private boolean dragging;
    private float dragStartX;
    private float dragStartY;

    public PanelGuiScreen() {
        super(Text.literal("PanelGui"));
    }

    @Override
    public void init() {
        if (selectedCategory == null && Category.values().length > 0) {
            selectedCategory = Category.values()[0];
        }
        createSidebar();
        createContent();
        updateLayout();
    }

    private void createSidebar() {
        sidebar = new Sidebar(windowX, windowY, SIDEBAR_WIDTH, windowHeight);
        sidebar.setLogoText("O");
        sidebar.setTitle(Sakura.MOD_NAME);
        sidebar.setSubtitle("By Fin_LemonKe");

        List<NavItem> items = new ArrayList<>();
        NavItem toSelect = null;
        for (Category category : Category.values()) {
            String label = category.getName();
            String icon = category.icon;
            NavItem item = new NavItem(0, 0, 0, 0, label, icon, nav -> selectCategory(category));
            items.add(item);
            if (category == selectedCategory) {
                toSelect = item;
            }
        }
        sidebar.clearSections();
        sidebar.addSection(null, items);
        if (toSelect != null) {
            sidebar.setSelectedItem(toSelect);
        }
    }

    private void selectCategory(Category category) {
        selectedCategory = category;
        updateModules();
        if (contentCard != null) {
            contentCard.resetScroll();
        }
    }

    private void createContent() {
        moduleGrid = new ModuleGrid(0.0f, 0.0f, 1.0f);
        updateModules();

        contentCard = new Card(0.0f, 0.0f, 1.0f, 1.0f);
        contentCard.setPadding(0.0f);
        contentCard.setCornerRadius(0.0f);
        contentCard.setShowBackground(false);
        contentCard.clearChildren();
        contentCard.addChild(moduleGrid);
    }

    private void updateModules() {
        if (moduleGrid == null || selectedCategory == null) return;
        List<Module> modules = Sakura.MODULES.getModsByCategory(selectedCategory);
        moduleGrid.setModules(modules);
    }

    private void updateLayout() {
        float sx = windowX;
        float sy = windowY;
        float sw = windowWidth;
        float sh = windowHeight;

        if (sidebar != null) {
            sidebar.setX(sx);
            sidebar.setY(sy);
            sidebar.setWidth(SIDEBAR_WIDTH);
            sidebar.setHeight(sh);
        }

        float contentX = sx + SIDEBAR_WIDTH;
        float contentY = sy + HEADER_HEIGHT + CONTENT_VERTICAL_PADDING + CONTENT_TOP_MARGIN;
        float contentW = Math.max(sw - SIDEBAR_WIDTH - CONTENT_RIGHT_PADDING, 1.0f);
        float contentH = Math.max(sh - HEADER_HEIGHT - CONTENT_VERTICAL_PADDING * 2.0f - CONTENT_TOP_MARGIN - CONTENT_BOTTOM_MARGIN, 1.0f);

        if (contentCard != null) {
            contentCard.setX(contentX);
            contentCard.setY(contentY);
            contentCard.setWidth(contentW);
            contentCard.setHeight(contentH);
        }

        if (moduleGrid != null) {
            moduleGrid.setX(contentX);
            moduleGrid.setY(contentY);
            moduleGrid.setWidth(contentW);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (ClickGui.backgroundBlur.get()) {
            BlurShader.drawRoundedBlur(windowX, windowY, windowWidth, windowHeight, 12.0f, ClickGui.blurStrength.get().floatValue());
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRoundRectBloom(windowX, windowY, windowWidth, windowHeight, 12.0f, WINDOW_BACKGROUND_COLOR);

            if (sidebar != null) {
                sidebar.render(context, mouseX, mouseY, deltaTicks);
            }
            if (contentCard != null) {
                contentCard.render(context, mouseX, mouseY, deltaTicks);
            }

            Dropdown.renderOpenMenus(context, mouseX, mouseY, deltaTicks);
            SettingItem.renderQueuedTooltips(context);
        });
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == 0 && isInWindowHeader(click.x(), click.y())) {
            dragging = true;
            dragStartX = (float) (click.x() - windowX);
            dragStartY = (float) (click.y() - windowY);
            return true;
        }

        if (Dropdown.interceptGlobalClick(click.x(), click.y(), click.button())) {
            return true;
        }

        if (sidebar != null && sidebar.mouseClicked(click.x(), click.y(), click.button())) {
            return true;
        }
        if (contentCard != null && contentCard.mouseClicked(click.x(), click.y(), click.button())) {
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            dragging = false;
        }
        boolean handled = false;
        if (sidebar != null && sidebar.mouseReleased(click.x(), click.y(), click.button())) {
            handled = true;
        }
        if (contentCard != null && contentCard.mouseReleased(click.x(), click.y(), click.button())) {
            handled = true;
        }
        return handled || super.mouseReleased(click);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (dragging && click.button() == 0) {
            windowX = (float) click.x() - dragStartX;
            windowY = (float) click.y() - dragStartY;
            updateLayout();
            return true;
        }
        if (contentCard != null && contentCard.mouseDragged(click.x(), click.y(), click.button(), offsetX, offsetY)) {
            return true;
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (contentCard != null && contentCard.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (contentCard != null && moduleGrid != null) {
            if (moduleGrid.keyPressed(input)) {
                return true;
            }
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (moduleGrid != null && moduleGrid.charTyped(input)) {
            return true;
        }
        return super.charTyped(input);
    }

    private boolean isInWindowHeader(double mouseX, double mouseY) {
        return mouseX >= windowX && mouseX <= windowX + windowWidth && mouseY >= windowY && mouseY <= windowY + HEADER_HEIGHT;
    }

    @Override
    public void close() {
        Sakura.MODULES.getModule(PanelGui.class).setState(false);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
