package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.module.Module;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModulePanel extends Component {
    private static final float SLIDER_MAX_WIDTH = 88.0f;
    private static final float DROPDOWN_MAX_WIDTH = 72.0f;

    private final Module module;
    private final List<Value<?>> values;
    private final List<SettingItem> settingItems = new ArrayList<>();
    private final List<Runnable> syncTasks = new ArrayList<>();

    private final float titleFontSize = 9.6f;
    private final float titleSpacing = 2.0f;
    private final float blockCornerRadius = 7.0f;
    private final float blockPadding = 4.0f;
    private final float itemSpacing = 1.0f;

    private float bodyHeight = 0.0f;
    private boolean layoutDirty = true;

    public ModulePanel(float x, float y, float width, Module module, List<Value<?>> values) {
        super(x, y, width, 0.0f);
        this.module = module;
        this.values = values != null ? values : Collections.emptyList();
        buildItems();
    }

    public ModulePanel(float x, float y, float width, Module module) {
        this(x, y, width, module, module != null ? module.getValues() : Collections.emptyList());
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        buildItems();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        markLayoutDirty();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        markLayoutDirty();
    }

    private void buildItems() {
        settingItems.clear();
        syncTasks.clear();

        float innerWidth = Math.max(80.0f, width - blockPadding * 2.0f);

        Toggle moduleToggle = new Toggle(0.0f, 0.0f, module != null && module.isEnabled(), v -> {
            if (module == null) return;
            module.setState(v);
        });
        SettingItem enabledItem = new SettingItem(0.0f, 0.0f, innerWidth, "Enabled", moduleToggle);
        float enabledLabelWidth = Math.min(innerWidth * 0.43f, 112.0f);
        enabledItem.setLabelWidth(enabledLabelWidth);
        enabledItem.setControlWidth(Math.max(innerWidth - enabledLabelWidth - 8.0f, 80.0f));
        settingItems.add(enabledItem);
        syncTasks.add(() -> moduleToggle.setValue(module != null && module.isEnabled()));

        for (Value<?> value : values) {
            if (value == null || !value.isAvailable()) continue;

            Component control = createControl(innerWidth, value);
            if (control == null) continue;

            SettingItem item = new SettingItem(0.0f, 0.0f, innerWidth, value.getDisplayName(), control);
            float computedLabelWidth = Math.min(innerWidth * 0.42f, 110.0f);
            item.setLabelWidth(computedLabelWidth);
            float available = Math.max(innerWidth - computedLabelWidth - 8.0f, 72.0f);
            if (control instanceof Slider) {
                item.setControlWidth(Math.min(available, SLIDER_MAX_WIDTH));
            } else if (control instanceof Dropdown) {
                item.setControlWidth(Math.min(available, DROPDOWN_MAX_WIDTH));
            } else {
                item.setControlWidth(available);
            }
            settingItems.add(item);
        }

        bodyHeight = blockPadding * 2.0f;
        for (int i = 0; i < settingItems.size(); i++) {
            bodyHeight += settingItems.get(i).getItemHeight();
            if (i < settingItems.size() - 1) {
                bodyHeight += itemSpacing;
            }
        }

        height = getTitleHeight() + titleSpacing + bodyHeight;
        markLayoutDirty();
    }

    private Component createControl(float innerWidth, Value<?> value) {
        if (value instanceof BoolValue boolValue) {
            Toggle toggle = new Toggle(0.0f, 0.0f, boolValue.get(), v -> boolValue.set(v));
            syncTasks.add(() -> toggle.setValue(boolValue.get()));
            return toggle;
        }

        if (value instanceof NumberValue<?> numberValue) {
            float sliderWidth = Math.min(innerWidth * 0.30f, SLIDER_MAX_WIDTH);
            float min = numberValue.getMin().floatValue();
            float max = numberValue.getMax().floatValue();
            float val = numberValue.get().floatValue();
            Slider slider = new Slider(0.0f, 0.0f, sliderWidth, min, max, val, v -> setNumberValue(numberValue, v));
            slider.setStep(numberValue.getStep().floatValue());
            syncTasks.add(() -> slider.setValue(numberValue.get().floatValue()));
            return slider;
        }

        if (value instanceof EnumValue<?> enumValue) {
            List<String> options = enumValue.getModeNames() != null ? Arrays.asList(enumValue.getModeNames()) : Collections.emptyList();
            float dropdownWidth = Math.min(innerWidth * 0.28f, DROPDOWN_MAX_WIDTH);
            Dropdown dropdown = new Dropdown(0.0f, 0.0f, dropdownWidth, 18.0f, enumValue.get().name(), options);
            dropdown.setOnValueChanged(enumValue::setMode);
            syncTasks.add(() -> dropdown.setSelectedValue(enumValue.get().name()));
            return dropdown;
        }

        return null;
    }

    private float getTitleHeight() {
        return NanoVGHelper.getFontHeight(FontLoader.regular(), titleFontSize);
    }

    private void layoutItems() {
        float currentY = y + getTitleHeight() + titleSpacing + blockPadding;
        float startX = x + blockPadding;
        float innerWidth = Math.max(90.0f, width - blockPadding * 2.0f);

        for (SettingItem item : settingItems) {
            item.setX(startX);
            item.setY(currentY);
            item.setWidth(innerWidth);
            currentY += item.getItemHeight() + itemSpacing;
        }
    }

    private void ensureLayout() {
        if (!layoutDirty) return;
        layoutItems();
        layoutDirty = false;
    }

    private void markLayoutDirty() {
        layoutDirty = true;
    }

    private void syncControlStates() {
        for (Runnable task : syncTasks) {
            task.run();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        syncControlStates();
        ensureLayout();

        float titleHeight = getTitleHeight();
        String name = module != null ? module.getDisplayName() : "";
        NanoVGHelper.drawString(name, x, y - 1.0f + titleHeight, FontLoader.regular(), titleFontSize, GlassmorphismColors.TEXT_PRIMARY);

        float blockY = y + titleHeight + titleSpacing;
        NanoVGHelper.drawRoundRectBloom(x, blockY, width, bodyHeight, blockCornerRadius, GlassmorphismColors.BACKGROUND);

        float itemCullMargin = 24.0f;
        float visibleTop = Float.isFinite(clipTop) ? clipTop - itemCullMargin : Float.NEGATIVE_INFINITY;
        float visibleBottom = Float.isFinite(clipBottom) ? clipBottom + itemCullMargin : Float.POSITIVE_INFINITY;

        for (int i = settingItems.size() - 1; i >= 0; i--) {
            SettingItem item = settingItems.get(i);
            float itemTop = item.getY();
            float itemBottom = itemTop + item.getItemHeight();
            if (itemBottom < visibleTop || itemTop > visibleBottom) {
                continue;
            }
            item.setClipBounds(clipTop, clipBottom);
            item.render(context, mouseX, mouseY, deltaTicks);
        }
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        ensureLayout();
        SettingItem openDropdownItem = null;
        for (SettingItem item : settingItems) {
            if (item.getControl() instanceof Dropdown dropdown) {
                if (dropdown.isOpen()) {
                    openDropdownItem = item;
                    break;
                }
            }
        }
        if (openDropdownItem != null && openDropdownItem.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        for (SettingItem item : settingItems) {
            if (item == openDropdownItem) continue;
            if (item.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        ensureLayout();
        for (SettingItem item : settingItems) {
            if (item.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        ensureLayout();
        for (SettingItem item : settingItems) {
            if (item.mouseDragged(mouseX, mouseY, button, offsetX, offsetY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (SettingItem item : settingItems) {
            if (item.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onKeyPressed(KeyInput input) {
        boolean handled = false;
        for (SettingItem item : settingItems) {
            Component control = item.getControl();
            if (control != null && control.keyPressed(input)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onCharTyped(CharInput input) {
        boolean handled = false;
        for (SettingItem item : settingItems) {
            Component control = item.getControl();
            if (control != null && control.charTyped(input)) {
                handled = true;
            }
        }
        return handled;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setNumberValue(NumberValue<?> value, float newValue) {
        Number current = value.get();
        if (current instanceof Integer) {
            ((NumberValue) value).set(Math.round(newValue));
        } else if (current instanceof Long) {
            ((NumberValue) value).set((long) newValue);
        } else if (current instanceof Float) {
            ((NumberValue) value).set(newValue);
        } else {
            ((NumberValue) value).set((double) newValue);
        }
    }
}
