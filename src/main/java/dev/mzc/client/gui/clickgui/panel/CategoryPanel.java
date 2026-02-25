package dev.mzc.client.gui.clickgui.panel;

import dev.mzc.client.Sakura;
import dev.mzc.client.gui.IComponent;
import dev.mzc.client.gui.clickgui.component.ModuleComponent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.utils.render.RenderUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel implements IComponent {
    private float x, y, dragX, dragY;
    private float width = 110, height;
    private final Category category;
    private boolean dragging, opened;
    private final ObjectArrayList<ModuleComponent> moduleComponents = new ObjectArrayList<>();
    public static int i;
    private final EaseInOutQuad openAnimation = new EaseInOutQuad(250, 1);
    private String searchText = "";
    private boolean typing = false;
    private ClickGui.ModuleFilter lastFilter = null;

    public CategoryPanel(Category category) {
        this.category = category;
        this.opened = true;
        this.openAnimation.setDirection(Direction.BACKWARDS);
        if (category != Category.Search) {
            for (i = 0; i < (Sakura.MODULES.getModsByCategory(category).size()); ++i) {
                Module module = Sakura.MODULES.getModsByCategory(category).get(i);
                moduleComponents.add(new ModuleComponent(module));
            }
        }
    }

    private boolean shouldShow(Module module) {
        if (this.category == Category.Search) return true;
        ClickGui.ModuleFilter filter = ClickGui.moduleFilter.get();
        if (filter == ClickGui.ModuleFilter.All) return true;
        if (module.getType() == Module.ModuleType.All) return true;
        if (filter == ClickGui.ModuleFilter.Safe) return module.getType() == Module.ModuleType.Safe;
        if (filter == ClickGui.ModuleFilter.Hack) return module.getType() == Module.ModuleType.Hack;
        return true;
    }

    private void updateSearch() {
        if (searchText.isEmpty()) {
            moduleComponents.clear();
            return;
        }

        // Identify modules that should be displayed
        ObjectArrayList<Module> targetModules = new ObjectArrayList<>();
        for (Module module : Sakura.MODULES.getAllModules()) {
            if (!shouldShow(module)) continue;

            String cnName = module.getChineseName();
            if ((cnName != null && cnName.toLowerCase().contains(searchText.toLowerCase())) ||
                    module.getEnglishName().toLowerCase().contains(searchText.toLowerCase())) {
                targetModules.add(module);
            }
        }

        // Mark existing components for removal if they are not in target list
        for (ModuleComponent component : moduleComponents) {
            if (!targetModules.contains(component.getModule())) {
                component.setVisible(false);
            }
        }

        // Add new components for target modules if they don't exist
        for (Module module : targetModules) {
            boolean exists = false;
            for (ModuleComponent component : moduleComponents) {
                if (component.getModule() == module) {
                    exists = true;
                    component.setVisible(true);
                    break;
                }
            }
            if (!exists) {
                ModuleComponent component = new ModuleComponent(module);
                component.setOpened(false);
                component.setVisible(true);
                component.resetVisibilityAnimation(); // Start from 0
                moduleComponents.add(component);
            }
        }
        
        // Re-sort to maintain order? 
        // For now, let's keep them in insertion order but that might mess up the list visually if items are appended.
        // Ideally we want to sort by Sakura.MODULES.getAllModules() order.
        List<Module> allModules = new ArrayList<>(Sakura.MODULES.getAllModules());
        moduleComponents.sort((c1, c2) -> {
            int i1 = allModules.indexOf(c1.getModule());
            int i2 = allModules.indexOf(c2.getModule());
            return Integer.compare(i1, i2);
        });
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (category != Category.Search) {
            if (lastFilter != ClickGui.moduleFilter.get()) {
                lastFilter = ClickGui.moduleFilter.get();
                for (ModuleComponent component : moduleComponents) {
                    component.setVisible(shouldShow(component.getModule()));
                }
            }
        }

        if (category == Category.Search && !searchText.isEmpty()) {
            // Remove components that are done exiting
            moduleComponents.removeIf(ModuleComponent::shouldRemove);
        }

        update(mouseX, mouseY);

        float guiScale = (float) ClickGui.getGuiScale();
        float baseFontSize = (float) ClickGui.getFontSize();
        float scaledWidth = width * guiScale;
        float headerHeight = 18 * guiScale;
        boolean bjdOnlyEnabled = ClickGui.bjdOnly.get();

        float componentOffsetY = headerHeight;
        for (ModuleComponent component : moduleComponents) {
            component.setX(x);
            component.setY(y + componentOffsetY);
            component.setWidth(scaledWidth);
            component.setScale(guiScale);
            // Apply visibility animation to height
            float visibleHeight = (float) (component.getHeight() * component.getVisibilityOutput());
            componentOffsetY += (float) (visibleHeight * openAnimation.getOutput());
        }
        height = componentOffsetY + 9 * guiScale;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color bgColor = ClickGui.backgroundColor.get();
            NanoVGHelper.drawRoundRectBloom(x, y - 1, scaledWidth, height, 7, new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 100));
            if (category == Category.Search) {
                String display = searchText.isEmpty() ? (typing ? "_" : "Search...") : searchText + (typing ? "_" : "");
                NanoVGHelper.drawString(display, x + 4 * guiScale, y + 12f * guiScale, FontLoader.bold(baseFontSize), baseFontSize, new Color(255, 255, 255, 255));
            } else {
                NanoVGHelper.drawString(category.getName(), x + 4 * guiScale, y + 12f * guiScale, FontLoader.bold(baseFontSize), baseFontSize, new Color(255, 255, 255, 255));
            }
            float iconSize = baseFontSize * 1.5f;
            NanoVGHelper.drawString(category.icon, x + 4 * guiScale + scaledWidth - NanoVGHelper.getTextWidth(category.icon, FontLoader.icons(iconSize), iconSize) - (category == Category.Render ? 7 : 3) * guiScale, y + 13f * guiScale, FontLoader.icons(iconSize), iconSize, new Color(255, 255, 255, 255));
        });

        for (ModuleComponent component : moduleComponents) {
            if (openAnimation.getOutput() > 0.7f) {
                component.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }

        IComponent.super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovered((int) mouseX, (int) mouseY)) {
            switch (mouseButton) {
                case 0 -> {
                    if (category == Category.Search) {
                        typing = !typing;
                    }
                    dragging = true;
                    dragX = (float) (x - mouseX);
                    dragY = (float) (y - mouseY);
                }
                case 1 -> opened = !opened;
            }
            return true;
        } else if (category == Category.Search && mouseButton == 0) {
            typing = false;
        }

        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.mouseClicked(mouseX, mouseY, mouseButton)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (category == Category.Search && typing) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !searchText.isEmpty()) {
                searchText = searchText.substring(0, searchText.length() - 1);
                updateSearch();
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_ENTER) {
                typing = false;
                return true;
            }
        }
        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.keyPressed(keyCode, scanCode, modifiers)) {
                handled = true;
            }
        }
        return handled || IComponent.super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (category == Category.Search && typing) {
            searchText += chr;
            updateSearch();
            return true;
        }
        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.charTyped(chr, modifiers)) {
                handled = true;
            }
        }
        return handled || IComponent.super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        if (state == 0) dragging = false;

        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.mouseReleased(mouseX, mouseY, state)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseReleased(mouseX, mouseY, state);
    }

    public void update(int mouseX, int mouseY) {
        if (ClickGui.moduleFilter.get() != lastFilter) {
            lastFilter = ClickGui.moduleFilter.get();
            if (category == Category.Search && !searchText.isEmpty()) {
                updateSearch();
            }
        }

        if (category != Category.Search) {
            for (ModuleComponent component : moduleComponents) {
                component.setVisible(shouldShow(component.getModule()));
            }
        }
        this.openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        if (dragging) {
            x = mouseX + dragX;
            y = mouseY + dragY;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        float guiScale = (float) ClickGui.getGuiScale();
        return RenderUtil.isHovering(x, y, width * guiScale, 18 * guiScale, mouseX, mouseY);
    }

    // Getter methods
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDragX() {
        return dragX;
    }

    public float getDragY() {
        return dragY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isOpened() {
        return opened;
    }

    public ObjectArrayList<ModuleComponent> getModuleComponents() {
        return moduleComponents;
    }

    public EaseInOutQuad getOpenAnimation() {
        return openAnimation;
    }

    // Setter methods
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDragX(float dragX) {
        this.dragX = dragX;
    }

    public void setDragY(float dragY) {
        this.dragY = dragY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
