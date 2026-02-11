package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleGrid extends Component {
    private static final float MIN_DOUBLE_COLUMN_WIDTH = 128.0f;

    private final List<Module> modules = new ArrayList<>();
    private final List<Placement> placements = new ArrayList<>();

    private final float columnSpacing = 6.0f;
    private final float rowSpacing = 6.0f;
    private float columnWidth;

    public ModuleGrid(float x, float y, float width) {
        super(x, y, width, 0.0f);
    }

    public void setModules(List<Module> list) {
        modules.clear();
        if (list != null) {
            for (Module m : list) {
                if (m != null) modules.add(m);
            }
        }
        rebuildPanels();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        relayout();
    }

    private void rebuildPanels() {
        placements.clear();
        for (Module module : modules) {
            placements.add(new Placement(new ModulePanel(0.0f, 0.0f, 0.0f, module, module.getValues())));
        }
        relayout();
    }

    private void relayout() {
        if (placements.isEmpty()) {
            height = 0.0f;
            return;
        }

        float availableWidth = Math.max(width, 1.0f);
        columnWidth = (availableWidth - columnSpacing) / 2.0f;
        boolean forceSingleColumn = false;

        if (columnWidth < MIN_DOUBLE_COLUMN_WIDTH) {
            columnWidth = availableWidth;
            forceSingleColumn = true;
        }

        float leftColumnHeight = 0.0f;
        float rightColumnHeight = 0.0f;

        for (Placement placement : placements) {
            ModulePanel panel = placement.panel;
            panel.setWidth(columnWidth);

            if (forceSingleColumn || leftColumnHeight <= rightColumnHeight) {
                placement.offsetX = 0.0f;
                placement.offsetY = leftColumnHeight;
                leftColumnHeight += panel.getHeight() + rowSpacing;
            } else {
                placement.offsetX = columnWidth + columnSpacing;
                placement.offsetY = rightColumnHeight;
                rightColumnHeight += panel.getHeight() + rowSpacing;
            }
        }

        height = forceSingleColumn ? leftColumnHeight : Math.max(leftColumnHeight, rightColumnHeight);
        placements.sort((a, b) -> Float.compare(a.offsetY, b.offsetY));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        final float cullingMargin = 32.0f;
        float visibleTop = Float.isFinite(clipTop) ? clipTop - cullingMargin : Float.NEGATIVE_INFINITY;
        float visibleBottom = Float.isFinite(clipBottom) ? clipBottom + cullingMargin : Float.POSITIVE_INFINITY;

        for (int i = placements.size() - 1; i >= 0; i--) {
            Placement placement = placements.get(i);
            ModulePanel panel = placement.panel;
            float panelX = x + placement.offsetX;
            float panelY = y + placement.offsetY;
            panel.setX(panelX);
            panel.setY(panelY);
            panel.setClipBounds(clipTop, clipBottom);

            float panelBottom = panelY + panel.getHeight();
            if (panelBottom < visibleTop || panelY > visibleBottom) {
                continue;
            }
            panel.render(context, mouseX, mouseY, deltaTicks);
        }
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        for (Placement placement : placements) {
            if (placement.panel.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        boolean handled = false;
        for (Placement placement : placements) {
            if (placement.panel.mouseReleased(mouseX, mouseY, button)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double offsetX, double offsetY) {
        boolean handled = false;
        for (Placement placement : placements) {
            if (placement.panel.mouseDragged(mouseX, mouseY, button, offsetX, offsetY)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onMouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (Placement placement : placements) {
            if (placement.panel.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onKeyPressed(KeyInput input) {
        boolean handled = false;
        for (Placement placement : placements) {
            if (placement.panel.keyPressed(input)) {
                handled = true;
            }
        }
        return handled;
    }

    @Override
    protected boolean onCharTyped(CharInput input) {
        boolean handled = false;
        for (Placement placement : placements) {
            if (placement.panel.charTyped(input)) {
                handled = true;
            }
        }
        return handled;
    }

    private static class Placement {
        final ModulePanel panel;
        float offsetX;
        float offsetY;

        Placement(ModulePanel panel) {
            this.panel = Objects.requireNonNull(panel);
        }
    }
}
