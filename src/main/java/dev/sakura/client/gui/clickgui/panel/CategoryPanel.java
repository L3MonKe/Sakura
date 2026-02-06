package dev.sakura.client.gui.clickgui.panel;

import dev.sakura.client.Sakura;
import dev.sakura.client.gui.IComponent;
import dev.sakura.client.gui.clickgui.component.ModuleComponent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.render.RenderUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

import java.awt.*;

public class CategoryPanel implements IComponent {
    private float x, y, dragX, dragY;
    private float width = 110, height;
    private final Category category;
    private boolean dragging, opened;
    private final ObjectArrayList<ModuleComponent> moduleComponents = new ObjectArrayList<>();
    public static int i;
    private final EaseInOutQuad openAnimation = new EaseInOutQuad(250, 1);

    public CategoryPanel(Category category) {
        this.category = category;
        this.opened = true;
        this.openAnimation.setDirection(Direction.BACKWARDS);
        for (i = 0; i < (Sakura.MODULES.getModsByCategory(category).size()); ++i) {
            Module module = Sakura.MODULES.getModsByCategory(category).get(i);
            moduleComponents.add(new ModuleComponent(module));
        }
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        update(mouseX, mouseY);

        float guiScale = (float) ClickGui.getGuiScale();
        float baseFontSize = (float) ClickGui.getFontSize();
        float scaledWidth = width * guiScale;
        float headerHeight = 18 * guiScale;

        float componentOffsetY = headerHeight;
        for (ModuleComponent component : moduleComponents) {
            component.setX(x);
            component.setY(y + componentOffsetY);
            component.setWidth(scaledWidth);
            component.setScale(guiScale);
            componentOffsetY += (float) (component.getHeight() * openAnimation.getOutput());
        }
        height = componentOffsetY + 9 * guiScale;

        if (ClickGui.backgroundBlur.get() && ClickGui.blurMode.is(ClickGui.BlurMode.OnlyCategory)) {
            BlurShader.drawRoundedBlur(x, y - 1, scaledWidth, height, 7.0f, ClickGui.blurStrength.get().floatValue());
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color bgColor = ClickGui.backgroundColor.get();
            NanoVGHelper.drawRoundRectBloom(x, y - 1, scaledWidth, height, 7, new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 100));
            NanoVGHelper.drawString(category.getName(), x + 4 * guiScale, y + 12f * guiScale, FontLoader.bold(), baseFontSize, new Color(255, 255, 255, 255));
            float iconSize = baseFontSize * 1.5f;
            NanoVGHelper.drawString(category.icon, x + 4 * guiScale + scaledWidth - NanoVGHelper.getTextWidth(category.icon, FontLoader.icons(), iconSize) - (category == Category.Render ? 7 : 3) * guiScale, y + 13f * guiScale, FontLoader.icons(), iconSize, new Color(255, 255, 255, 255));
        });

        for (ModuleComponent component : moduleComponents) {
            if (openAnimation.getOutput() > 0.7f) {
                component.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }

        IComponent.super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (isHovered((int) click.x(), (int) click.y())) {
            switch (click.button()) {
                case 0 -> {
                    dragging = true;
                    dragX = (float) (x - click.x());
                    dragY = (float) (y - click.y());
                }
                case 1 -> opened = !opened;
            }
            return true;
        }

        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.mouseClicked(click, doubled)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.keyPressed(input)) {
                handled = true;
            }
        }
        return handled || IComponent.super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.charTyped(input)) {
                handled = true;
            }
        }
        return handled || IComponent.super.charTyped(input);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            dragging = false;
        }

        boolean handled = false;
        for (ModuleComponent component : moduleComponents) {
            if (component.mouseReleased(click)) {
                handled = true;
            }
        }

        return handled || IComponent.super.mouseReleased(click);
    }

    public void update(int mouseX, int mouseY) {
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
