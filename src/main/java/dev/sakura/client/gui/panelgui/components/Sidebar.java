package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sidebar extends Component {
    private String logoText;
    private String title;
    private String subtitle;
    private final List<NavSection> sections = new ArrayList<>();
    private NavItem selectedItem = null;

    private final float logoSize = 16.64f;
    private final float titleSize = 10.24f;
    private final float sectionTitleSize = 7.072f;
    private final float padding = 4.0f;
    private final float logoOffsetX = 7.0f;
    private final float logoOffsetY = 7.0f;

    public Sidebar(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!visible) return;

        float currentY = y + padding;
        currentY = renderHeader(currentY);

        for (NavSection section : sections) {
            currentY = renderSectionTitle(section.title, currentY);
            for (NavItem item : section.items) {
                item.setX(x + padding);
                item.setY(currentY);
                item.setWidth(width - padding * 2.0f);
                item.setHeight(20.0f);
                item.setSelected(item == selectedItem);
                item.render(context, mouseX, mouseY, deltaTicks);
                currentY += 21.0f;
            }
            currentY += 6.0f;
        }
    }

    @Override
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        for (NavSection section : sections) {
            for (NavItem item : section.items) {
                if (item.mouseClicked(mouseX, mouseY, button)) {
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }
                    selectedItem = item;
                    item.setSelected(true);
                    return true;
                }
            }
        }
        return false;
    }

    private float renderHeader(float currentY) {
        if (logoText != null && !logoText.isEmpty()) {
            float logoX = x + padding + logoOffsetX;
            float logoY = currentY + logoOffsetY;

            int logoFont = FontLoader.newIc();
            float logoW = NanoVGHelper.getTextWidth(logoText, logoFont, logoSize);
            float logoH = NanoVGHelper.getFontHeight(logoFont, logoSize);
            NanoVGHelper.drawString(logoText, logoX, logoY + logoH, logoFont, logoSize, new Color(255, 255, 255, 255));

            float titleH = 0.0f;
            float subtitleH = 0.0f;
            float titleX = logoX + logoW + 6.0f;
            if (title != null && !title.isEmpty()) {
                titleH = NanoVGHelper.getFontHeight(FontLoader.regular(), titleSize);
                NanoVGHelper.drawString(title, titleX, logoY + titleH, FontLoader.regular(), titleSize, GlassmorphismColors.TEXT_PRIMARY);
                if (subtitle != null && !subtitle.isEmpty()) {
                    subtitleH = NanoVGHelper.getFontHeight(FontLoader.regular(), 7.68f);
                    float subY = logoY + titleH + 2.0f;
                    NanoVGHelper.drawString(subtitle, titleX, subY + subtitleH, FontLoader.regular(), 7.68f, GlassmorphismColors.TEXT_SECONDARY);
                }
            }

            float headerH = Math.max(logoH, titleH + (subtitleH > 0.0f ? subtitleH + 2.0f : 0.0f));
            return currentY + logoOffsetY + headerH + 8.0f;
        }

        if (title != null && !title.isEmpty()) {
            float titleH = NanoVGHelper.getFontHeight(FontLoader.regular(), titleSize);
            NanoVGHelper.drawString(title, x + padding, currentY + titleH, FontLoader.regular(), titleSize, GlassmorphismColors.TEXT_PRIMARY);
            currentY += titleH + 3.0f;
        }
        if (subtitle != null && !subtitle.isEmpty()) {
            float subH = NanoVGHelper.getFontHeight(FontLoader.regular(), 7.68f);
            NanoVGHelper.drawString(subtitle, x + padding, currentY + subH, FontLoader.regular(), 7.68f, GlassmorphismColors.TEXT_SECONDARY);
            currentY += subH + 10.0f;
        }
        return currentY;
    }

    private float renderSectionTitle(String titleText, float currentY) {
        if (titleText == null || titleText.isEmpty()) return currentY;
        float textH = NanoVGHelper.getFontHeight(FontLoader.regular(), sectionTitleSize);
        NanoVGHelper.drawString(titleText, x + padding, currentY + textH, FontLoader.regular(), sectionTitleSize, GlassmorphismColors.TEXT_TERTIARY);
        return currentY + textH + 4.0f;
    }

    public void addSection(String title, List<NavItem> items) {
        sections.add(new NavSection(title, items != null ? items : List.of()));
    }

    public void clearSections() {
        sections.clear();
    }

    public void setSelectedItem(NavItem item) {
        if (selectedItem != null) {
            selectedItem.setSelected(false);
        }
        selectedItem = item;
        if (selectedItem != null) {
            selectedItem.setSelected(true);
        }
    }

    public void setLogoText(String logoText) {
        this.logoText = logoText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public static class NavSection {
        final String title;
        final List<NavItem> items;

        public NavSection(String title, List<NavItem> items) {
            this.title = title;
            this.items = items;
        }
    }
}
