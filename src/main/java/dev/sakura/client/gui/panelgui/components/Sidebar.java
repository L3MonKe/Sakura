package dev.sakura.client.gui.panelgui.components;

import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.sakura.client.Sakura.mc;

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

    private final float footerHeight = 34.0f;
    private final float footerRadius = 10.0f;
    private final float footerInnerPadding = 8.0f;
    private final float footerAvatarSize = 18.0f;
    private final float footerTextSize = 9.0f;
    private final float footerGap = 7.0f;
    private final Map<Integer, Integer> skinImageCache = new HashMap<>();

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

        renderPlayerFooter();
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

    private void renderPlayerFooter() {
        float footerX = x + padding;
        float footerW = width - padding * 2.0f;
        float footerY = y + height - padding - footerHeight;
        if (footerW <= 1.0f) return;

        NanoVGHelper.drawRoundRect(footerX, footerY, footerW, footerHeight, footerRadius, new Color(255, 255, 255, 18));
        NanoVGHelper.drawRoundRectOutline(footerX, footerY, footerW, footerHeight, footerRadius, 1.0f, GlassmorphismColors.BORDER_COLOR);

        String name = mc.player != null ? mc.player.getName().getString() : (mc.getSession() != null ? mc.getSession().getUsername() : "Player");

        float maxTextW = Math.max(footerW - footerInnerPadding * 2.0f - footerAvatarSize - footerGap, 1.0f);
        String displayName = ellipsizeText(name, maxTextW, FontLoader.regular(), footerTextSize);

        float textW = NanoVGHelper.getTextWidth(displayName, FontLoader.regular(), footerTextSize);
        float groupW = footerAvatarSize + footerGap + textW;
        float avatarX = footerX + Math.max((footerW - groupW) / 2.0f, footerInnerPadding);
        float avatarY = footerY + (footerHeight - footerAvatarSize) / 2.0f;
        float textX = avatarX + footerAvatarSize + footerGap;
        float textH = NanoVGHelper.getFontHeight(FontLoader.regular(), footerTextSize);
        float textY = footerY + (footerHeight - textH) / 2.0f;

        renderPlayerAvatar(mc, avatarX, avatarY, footerAvatarSize);
        NanoVGHelper.drawString(displayName, textX, textY + textH, FontLoader.regular(), footerTextSize, GlassmorphismColors.TEXT_PRIMARY);
    }

    private String ellipsizeText(String text, float maxWidth, int font, float size) {
        if (text == null || text.isEmpty()) return "";
        if (NanoVGHelper.getTextWidth(text, font, size) <= maxWidth) return text;

        String ellipsis = "...";
        float ellipsisW = NanoVGHelper.getTextWidth(ellipsis, font, size);
        if (ellipsisW >= maxWidth) return ellipsis;

        int end = text.length();
        while (end > 0) {
            String candidate = text.substring(0, end) + ellipsis;
            if (NanoVGHelper.getTextWidth(candidate, font, size) <= maxWidth) {
                return candidate;
            }
            end--;
        }
        return ellipsis;
    }

    private void renderPlayerAvatar(MinecraftClient mc, float x, float y, float size) {
        float cx = x + size / 2.0f;
        float cy = y + size / 2.0f;
        float r = size / 2.0f;

        int imageId = -1;
        if (mc.player instanceof AbstractClientPlayerEntity clientPlayer) {
            Identifier skinTexture = clientPlayer.getSkin().body().texturePath();
            imageId = getSkinImageId(mc, skinTexture);
        }

        if (imageId != -1) {
            long vg = NanoVGRenderer.INSTANCE.getContext();
            NanoVGHelper.save();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                org.lwjgl.nanovg.NVGPaint paint = org.lwjgl.nanovg.NVGPaint.malloc(stack);

                float faceScale = 8.0f;
                float ox = x - size;
                float oy = y - size;
                float ex = size * faceScale;
                float ey = size * faceScale;

                NanoVG.nvgImagePattern(vg, ox, oy, ex, ey, 0.0f, imageId, 1.0f, paint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgCircle(vg, cx, cy, r);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);
            }
            NanoVGHelper.restore();
        } else {
            renderDefaultAvatar(cx, cy, r);
        }

        NanoVGHelper.drawCircleOutline(cx, cy, r, 1.0f, GlassmorphismColors.BORDER_COLOR);
    }

    private void renderDefaultAvatar(float cx, float cy, float r) {
        NanoVGHelper.drawCircle(cx, cy, r, new Color(0, 0, 0, 120));
        NanoVGHelper.drawCircle(cx, cy - r * 0.15f, r * 0.38f, new Color(255, 255, 255, 110));
        NanoVGHelper.drawRoundRect(cx - r * 0.6f, cy + r * 0.1f, r * 1.2f, r * 0.75f, r * 0.45f, new Color(255, 255, 255, 90));
    }

    private int getSkinImageId(MinecraftClient mc, Identifier skinTexture) {
        int glId = ((GlTexture) mc.getTextureManager().getTexture(skinTexture).getGlTexture()).getGlId();

        Integer cached = skinImageCache.get(glId);
        if (cached != null) {
            return cached;
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        int imageId = NanoVGHelper.createImageFromHandle(glId, 64, 64);
        if (imageId != -1) {
            skinImageCache.put(glId, imageId);
        }
        return imageId;
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
