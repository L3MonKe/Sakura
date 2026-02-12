package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.gui.panelgui.Colors;
import dev.sakura.client.gui.panelgui.SmoothAnimationTimer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Dropdown extends Component {
    private static final List<Dropdown> MENU_QUEUE = new ArrayList<>();
    private static Dropdown activeDropdown;

    private static final float OPTION_HEIGHT = 17.0f;
    private static final float MENU_GAP = 3.0f;
    private static final float TEXT_PADDING = 8.0f;

    private final SmoothAnimationTimer openAnimation = new SmoothAnimationTimer(0.0f);
    private final SmoothAnimationTimer hoverAnimation = new SmoothAnimationTimer(0.0f);

    private String selectedValue;
    private List<String> options = new ArrayList<>();
    private boolean open;
    private Consumer<String> onValueChanged;

    private final float fontSize = 9.0f;
    private final float cornerRadius = 4.0f;

    private String cachedSelectedSnapshot = "";
    private String cachedDisplayText = "";
    private float cachedMaxTextWidth = -1.0f;

    public Dropdown(float x, float y, float width, float height) {
        super(x, y, width, height);
        invalidateDisplayCache();
    }

    public Dropdown(float x, float y, float width, float height, String selectedValue, List<String> options) {
        this(x, y, width, height);
        this.selectedValue = selectedValue;
        this.options = options != null ? new ArrayList<>(options) : new ArrayList<>();
        invalidateDisplayCache();
    }

    public static void renderOpenMenus(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (MENU_QUEUE.isEmpty()) return;
        List<Dropdown> current = new ArrayList<>(MENU_QUEUE);
        MENU_QUEUE.clear();
        for (Dropdown dropdown : current) {
            dropdown.renderMenuLayer(context, mouseX, mouseY, deltaTicks);
        }
    }

    public static boolean interceptGlobalClick(double mouseX, double mouseY, int button) {
        if (activeDropdown != null && activeDropdown.isOpen()) {
            return activeDropdown.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;

        hoverAnimation.target = hovered ? 100.0f : 0.0f;
        hoverAnimation.update(true);
        openAnimation.update(true);

        float hoverProgress = hoverAnimation.value / 100.0f;
        float openProgress = openAnimation.value / 255.0f;

        NanoVGHelper.drawRoundRectBloom(x, y, width, height, cornerRadius, GlassmorphismColors.BACKGROUND);

        if (hoverProgress > 0.001f || open) {
            Color overlayColor = Colors.withAlpha(GlassmorphismColors.HOVER, (GlassmorphismColors.HOVER.getAlpha() / 255.0f) * hoverProgress);
            NanoVGHelper.drawRoundRect(x, y, width, height, cornerRadius, overlayColor);
        }

        if (selectedValue != null) {
            float maxTextWidth = Math.max(width - TEXT_PADDING * 2.0f - 12.0f, 0.0f);
            String displayText = resolveDisplayText(maxTextWidth);
            float textX = x + TEXT_PADDING;
            float textH = NanoVGHelper.getFontHeight(FontLoader.regular(), fontSize);
            float textTop = y + height / 2.0f - textH / 2.0f;
            NanoVGHelper.drawString(displayText, textX, textTop + textH, FontLoader.regular(), fontSize, GlassmorphismColors.TEXT_PRIMARY);
        }

        if (open && openProgress > 0.01f) {
            queueMenuLayer();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!visible || !enabled || button != 0) {
            return false;
        }

        boolean overButton = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        if (overButton) {
            toggleOpen();
            return true;
        }

        if (open) {
            if (isPointInsideMenu(mouseX, mouseY)) {
                int index = (int) ((mouseY - getMenuY()) / OPTION_HEIGHT);
                if (index >= 0 && index < options.size()) {
                    applySelection(options.get(index));
                }
                closeDropdown();
                return true;
            } else {
                closeDropdown();
                return true;
            }
        }

        return false;
    }

    @Override
    public void setWidth(float width) {
        if (this.width == width) return;
        super.setWidth(width);
        invalidateDisplayCache();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        if (open) {
            openDropdown();
        } else {
            closeDropdown();
        }
    }

    public void setSelectedValue(String value) {
        if (value == null) return;
        if (!options.contains(value)) return;
        selectedValue = value;
        invalidateDisplayCache();
    }

    public void setOptions(List<String> options) {
        this.options = options != null ? new ArrayList<>(options) : new ArrayList<>();
        invalidateDisplayCache();
    }

    public void setOnValueChanged(Consumer<String> onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    private void toggleOpen() {
        if (open) {
            closeDropdown();
        } else {
            openDropdown();
        }
    }

    private void openDropdown() {
        if (activeDropdown != null && activeDropdown != this) {
            activeDropdown.closeDropdown();
        }
        open = true;
        activeDropdown = this;
        openAnimation.target = 255.0f;
    }

    private void closeDropdown() {
        open = false;
        if (activeDropdown == this) {
            activeDropdown = null;
        }
        openAnimation.target = 0.0f;
    }

    private float getMenuY() {
        return y + height + MENU_GAP;
    }

    private boolean isPointInsideMenu(double mouseX, double mouseY) {
        if (!open || options.isEmpty()) return false;
        float menuY = getMenuY();
        float menuHeight = OPTION_HEIGHT * options.size();
        return mouseX >= x && mouseX <= x + width && mouseY >= menuY && mouseY <= menuY + menuHeight;
    }

    private void applySelection(String newValue) {
        if (newValue == null || newValue.equals(selectedValue)) return;
        selectedValue = newValue;
        invalidateDisplayCache();
        if (onValueChanged != null) {
            onValueChanged.accept(newValue);
        }
    }

    private void invalidateDisplayCache() {
        cachedSelectedSnapshot = selectedValue != null ? selectedValue : "";
        cachedDisplayText = cachedSelectedSnapshot;
        cachedMaxTextWidth = -1.0f;
    }

    private String resolveDisplayText(float maxTextWidth) {
        if (selectedValue == null) {
            return "";
        }
        if (maxTextWidth <= 0.0f) {
            cachedDisplayText = selectedValue;
            cachedMaxTextWidth = maxTextWidth;
            cachedSelectedSnapshot = selectedValue;
            return cachedDisplayText;
        }
        boolean cacheHit = selectedValue.equals(cachedSelectedSnapshot) && Math.abs(maxTextWidth - cachedMaxTextWidth) < 0.5f;
        if (cacheHit) return cachedDisplayText;

        cachedSelectedSnapshot = selectedValue;
        cachedMaxTextWidth = maxTextWidth;
        cachedDisplayText = selectedValue;

        float textWidth = NanoVGHelper.getTextWidth(selectedValue, FontLoader.regular(), fontSize);
        if (textWidth > maxTextWidth) {
            String truncated = selectedValue;
            while (!truncated.isEmpty() && NanoVGHelper.getTextWidth(truncated + "...", FontLoader.regular(), fontSize) > maxTextWidth) {
                truncated = truncated.substring(0, truncated.length() - 1);
            }
            cachedDisplayText = truncated + "...";
        }
        return cachedDisplayText;
    }

    private void queueMenuLayer() {
        if (!MENU_QUEUE.contains(this)) {
            MENU_QUEUE.add(this);
        }
    }

    private void renderMenuLayer(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!open || options.isEmpty()) return;

        float openProgress = openAnimation.value / 255.0f;
        float menuY = getMenuY();
        float menuHeight = OPTION_HEIGHT * options.size();

        NanoVGHelper.drawRoundRectBloom(x, menuY, width, menuHeight, cornerRadius, GlassmorphismColors.DROPDOWN_BACKGROUND);

        for (int i = 0; i < options.size(); i++) {
            float optionY = menuY + i * OPTION_HEIGHT;
            boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= optionY && mouseY <= optionY + OPTION_HEIGHT;
            boolean selected = options.get(i).equals(selectedValue);

            if (selected || hovered) {
                float highlightRadius = Math.max(cornerRadius - 2.0f, 1.0f);
                NanoVGHelper.drawRoundRect(x + 2.0f, optionY + 2.0f, width - 4.0f, OPTION_HEIGHT - 4.0f, highlightRadius, GlassmorphismColors.HOVER);
            }

            Color textColor = Colors.withAlpha(GlassmorphismColors.TEXT_PRIMARY, openProgress);
            float textH = NanoVGHelper.getFontHeight(FontLoader.regular(), fontSize);
            float textTop = optionY + OPTION_HEIGHT / 2.0f - textH / 2.0f;
            NanoVGHelper.drawString(options.get(i), x + TEXT_PADDING, textTop + textH, FontLoader.regular(), fontSize, textColor);
        }
    }
}
