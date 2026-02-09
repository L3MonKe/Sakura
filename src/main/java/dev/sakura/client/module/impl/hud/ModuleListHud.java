package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ModuleListHud extends HudModule {
    public ModuleListHud() {
        super("ModuleList", "功能列表", 10, 10);
    }

    public enum ListMode {
        Normal,
        Gradient
    }

    public enum FontMode {
        Default,
        Minecraft,
        Comfortaa
    }

    // --- 核心设置 (Core Settings) ---
    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Normal);
    private final EnumValue<FontMode> fontMode = new EnumValue<>("Font Mode", "渐变-字体模式", FontMode.Default, () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> hudScale = new NumberValue<>("Hud Scale", "HUD缩放", 1.1, 0.5, 2.0, 0.1);
    private final BoolValue alignRight = new BoolValue("Align Right", "右对齐", false);
    private final BoolValue hideHudModules = new BoolValue("Hide HudModules", "隐藏HUD模块", true);

    // --- 布局与限制 (Layout & Limits) ---
    private final NumberValue<Double> maxWidth = new NumberValue<>("Max Width", "最大宽度", 150.0, 50.0, 300.0, 5.0);
    private final NumberValue<Double> maxHeight = new NumberValue<>("Max Height", "最大高度", 200.0, 50.0, 500.0, 10.0);
    private final NumberValue<Double> itemSpacing = new NumberValue<>("Item Spacing", "项目间距", 7.0, 0.0, 10.0, 0.5);
    private final NumberValue<Integer> suffixStyle = new NumberValue<>("Suffix Style", "后缀符号", 0, 0, 3, 1);

    // --- 动画控制 (Animation Control) ---
    private final NumberValue<Double> animationSpeed = new NumberValue<>("Animation Speed", "动画速度", 0.2, 0.05, 0.5, 0.05);
    private final NumberValue<Double> sliderSpeed = new NumberValue<>("Slider Speed", "滑动速度", 0.2, 0.01, 1.0, 0.01);

    // --- 普通模式设置 (Normal Mode) ---
    private final BoolValue normalRainbowColor = new BoolValue("Normal Rainbow", "普通-彩虹色", false, () -> mode.is(ListMode.Normal));
    private final BoolValue normalShowCategory = new BoolValue("Normal Show Category", "普通-显示分类", true, () -> mode.is(ListMode.Normal));
    private final NumberValue<Double> normalRadius = new NumberValue<>("Normal Radius", "普通-圆角半径", 6.0, 0.0, 15.0, 1.0, () -> mode.is(ListMode.Normal));

    // --- 渐变模式设置 (Gradient Mode) ---
    // 1. 文本与字体 (Text & Font)
    private final NumberValue<Double> customFontSize = new NumberValue<>("Font Size", "渐变-字体大小", 10.0, 5.0, 30.0, 0.5, () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> textOffsetY = new NumberValue<>("Text Offset Y", "渐变-文字Y偏移", 1.0, -10.0, 10.0, 0.5, () -> mode.is(ListMode.Gradient));
    private final BoolValue textGlow = new BoolValue("Text Glow", "渐变-文本发光", true, () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> glowRadius = new NumberValue<>("Glow Radius", "渐变-发光半径", 3.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Gradient) && textGlow.get());
    private final NumberValue<Integer> glowIntensity = new NumberValue<>("Glow Intensity", "渐变-发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Gradient) && textGlow.get());

    // 2. 渐变颜色 (Colors)
    private final BoolValue autoColor = new BoolValue("Auto Color", "渐变-自动调色", false, () -> mode.is(ListMode.Gradient));
    private final ColorValue gradientColor1 = new ColorValue("Color 1", "渐变-颜色1", new Color(0, 255, 255), () -> mode.is(ListMode.Gradient));
    private final ColorValue gradientColor2 = new ColorValue("Color 2", "渐变-颜色2", new Color(255, 0, 255), () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> gradientSpeed = new NumberValue<>("Gradient Speed", "渐变-速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(ListMode.Gradient));
    private final NumberValue<Double> colorStep = new NumberValue<>("Color Step", "渐变-颜色跨度", 15.0, 1.0, 100.0, 1.0, () -> mode.is(ListMode.Gradient));

    // 3. 背景设置 (Background)
    private final BoolValue background = new BoolValue("Background", "渐变-背景", false, () -> mode.is(ListMode.Gradient));

    public enum BackgroundMode {Normal, Blur}

    private final EnumValue<BackgroundMode> backgroundMode = new EnumValue<>("Background Mode", "渐变-背景模式", BackgroundMode.Normal, () -> mode.is(ListMode.Gradient) && background.get());
    private final ColorValue backgroundColor = new ColorValue("Background Color", "渐变-背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Gradient) && background.get());
    private final NumberValue<Double> backgroundRadius = new NumberValue<>("Background Radius", "渐变-背景圆角", 0.0, 0.0, 10.0, 1.0, () -> mode.is(ListMode.Gradient) && background.get());
    private final NumberValue<Double> backgroundOffsetY = new NumberValue<>("Background Offset Y", "渐变-背景Y偏移", -3.0, -10.0, 10.0, 0.5, () -> mode.is(ListMode.Gradient) && background.get());

    // 4. 线条设置 (Lines)
    private final BoolValue showGradientLine = new BoolValue("Show Line", "渐变-显示线条", false, () -> mode.is(ListMode.Gradient));

    public enum LineMode {Left, Box}

    private final EnumValue<LineMode> lineMode = new EnumValue<>("Line Mode", "渐变-线条模式", LineMode.Left, () -> mode.is(ListMode.Gradient) && showGradientLine.get());
    private final NumberValue<Double> lineWidth = new NumberValue<>("Line Width", "渐变-线条宽度", 2.0, 1.0, 5.0, 0.5, () -> mode.is(ListMode.Gradient) && showGradientLine.get());

    private float targetWidth = 0;
    private float targetHeight = 0;
    private float currentWidth = 0;
    private float currentHeight = 0;
    private float scrollOffset = 0;
    private boolean firstUpdate = true;

    private int lastScaledScreenWidth = -1;
    private int lastScaledScreenHeight = -1;
    private float anchorXRatio = Float.NaN;
    private float anchorYRatio = Float.NaN;
    private boolean anchorUsesRightEdge = false;

    private static final float PADDING_X = 6f;
    private static final float PADDING_Y = 4f;

    private static final float CATEGORY_ICON_SPACING = 6f;
    private static final Color SUFFIX_COLOR = new Color(180, 180, 180);
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18, 70);

    private static final String CATEGORY_ICON = "U";
    private static final float ICON_BACKGROUND_WIDTH = 12f;
    private static final float ICON_BACKGROUND_HEIGHT = 12f;
    private final TimerUtil sortTimer = new TimerUtil();
    private boolean dirty = true;

    private final List<ModuleEntry> moduleEntries = new ArrayList<>();
    private final Map<Module, Float> moduleYPositions = new HashMap<>();
    private final Map<Module, EaseInOutQuad> moduleAnimations = new HashMap<>();

    private final Map<Module, Float> moduleWidthCache = new HashMap<>();
    private final Map<Module, String> moduleTextCache = new HashMap<>();

    private float lastCacheScale = -1;
    private float lastCacheFontSize = -1;
    private FontMode lastCacheFontMode = null;
    private ListMode lastCacheMode = null;

    private final List<Module> tmpVisibleModules = new ArrayList<>();
    private final Map<Module, ModuleEntry> moduleEntryCache = new HashMap<>();

    public static void onModuleToggle(Module module, boolean enabled) {
        ModuleListHud instance = Sakura.MODULES.getModule(ModuleListHud.class);
        instance.dirty = true;

        if (!module.isHidden() && (!instance.hideHudModules.get() || !(module instanceof HudModule))) {
            if (enabled) {
                EaseInOutQuad animation = instance.moduleAnimations.computeIfAbsent(module, k -> new EaseInOutQuad(200, 1.0));
                animation.setDirection(Direction.FORWARDS);
                animation.reset();
            } else {
                EaseInOutQuad animation = instance.moduleAnimations.computeIfAbsent(module, k -> new EaseInOutQuad(200, 1.0));
                animation.setDirection(Direction.BACKWARDS);
                animation.reset();
            }
        }
    }

    @Override
    public void onRender(DrawContext context) {
        update();
        ensureWithinScreenBounds();

        if (mode.is(ListMode.Gradient) && background.get() && backgroundMode.is(BackgroundMode.Blur)) {
            renderBlurBackgrounds();
        }

        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());

        if (mode.is(ListMode.Gradient) && fontMode.is(FontMode.Minecraft)) {
            renderGradientTextVanilla(context);
        }
    }

    private void update() {
        if (firstUpdate) {
            updateModuleList();
            calculateTargetSize();
            currentWidth = targetWidth;
            currentHeight = targetHeight;
            firstUpdate = false;
        }

        float oldWidth = currentWidth;
        updateModuleList();
        calculateTargetSize();
        updateModulePositions();
        float speed = animationSpeed.get().floatValue();
        currentWidth += (targetWidth - currentWidth) * speed;
        currentHeight += (targetHeight - currentHeight) * speed;

        if (alignRight.get()) {
            float scale = hudScale.get().floatValue();
            x -= (currentWidth - oldWidth) * scale;
        }

        this.width = currentWidth * hudScale.get().floatValue();
        this.height = currentHeight * hudScale.get().floatValue();
        updateScroll();
    }

    private void updateModulePositions() {
        float scale = hudScale.get().floatValue();
        float fontSize = mode.is(ListMode.Gradient) ? customFontSize.get().floatValue() : 10f;
        float itemSpacing = this.itemSpacing.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        float totalListHeight = 0;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing) * scale;

            // Target Y is where it SHOULD be
            float targetY = currentY + totalListHeight;

            // Get current render Y
            float renderY = moduleYPositions.getOrDefault(entry.module, targetY);

            // Interpolate
            float diff = targetY - renderY;
            if (Math.abs(diff) > 0.1) {
                renderY += diff * sliderSpeed.get().floatValue();
            } else {
                renderY = targetY;
            }
            moduleYPositions.put(entry.module, renderY);

            if (animationValue > 0.01) {
                totalListHeight += (float) (itemFullHeight * animationValue);
            }
        }
    }

    private int getFontId() {
        if (mode.is(ListMode.Gradient) && fontMode.is(FontMode.Comfortaa)) {
            return FontLoader.comfortaa();
        }
        return FontLoader.medium();
    }

    private float getModuleTextWidth(String text) {
        float scale = hudScale.get().floatValue();
        if (mode.is(ListMode.Gradient) && fontMode.is(FontMode.Minecraft)) {
            float fontSize = customFontSize.get().floatValue();
            return mc.textRenderer.getWidth(text) * (fontSize / 9.0f) * scale;
        }
        float fontSize = mode.is(ListMode.Gradient) ? customFontSize.get().floatValue() : 10f;
        int font = getFontId();
        return NanoVGHelper.getTextWidth(text, font, fontSize * scale);
    }

    private float getCachedModuleWidth(Module module) {
        String displayText = getDisplayText(module);
        if (!displayText.equals(moduleTextCache.get(module)) || !moduleWidthCache.containsKey(module)) {
            float width = getModuleTextWidth(displayText);
            moduleTextCache.put(module, displayText);
            moduleWidthCache.put(module, width);
            return width;
        }
        return moduleWidthCache.get(module);
    }

    private boolean isModuleVisible(Module module) {
        if (module.isHidden()) return false;
        if (hideHudModules.get() && module instanceof HudModule) return false;
        if (module.isEnabled()) return true;
        EaseInOutQuad anim = moduleAnimations.get(module);
        return anim != null && anim.getOutput() > 0.001;
    }

    private ModuleEntry getModuleEntry(Module module) {
        return moduleEntryCache.computeIfAbsent(module, ModuleEntry::new);
    }

    private void pruneModuleState(Module module) {
        moduleYPositions.remove(module);
        EaseInOutQuad anim = moduleAnimations.get(module);
        if (anim != null && anim.getOutput() <= 0.001 && !module.isEnabled()) {
            moduleAnimations.remove(module);
        }
    }

    private void updateModuleList() {
        float currentScale = hudScale.get().floatValue();
        float currentFontSize = customFontSize.get().floatValue();
        FontMode currentFontMode = fontMode.get();
        ListMode currentMode = mode.get();

        if (currentScale != lastCacheScale ||
                currentFontSize != lastCacheFontSize ||
                currentFontMode != lastCacheFontMode ||
                currentMode != lastCacheMode) {

            moduleWidthCache.clear();
            moduleTextCache.clear();

            lastCacheScale = currentScale;
            lastCacheFontSize = currentFontSize;
            lastCacheFontMode = currentFontMode;
            lastCacheMode = currentMode;
            dirty = true;
        }

        boolean rebuildAndSort = dirty || sortTimer.passedMillise(500);
        if (rebuildAndSort) {
            dirty = false;
            sortTimer.reset();
            if (moduleWidthCache.size() > 200) {
                moduleWidthCache.clear();
                moduleTextCache.clear();
            }
            tmpVisibleModules.clear();
            for (Module module : Sakura.MODULES.getAllModules()) {
                if (isModuleVisible(module)) {
                    tmpVisibleModules.add(module);
                }
            }
            tmpVisibleModules.sort((m1, m2) -> Float.compare(getCachedModuleWidth(m2), getCachedModuleWidth(m1)));

            moduleEntries.clear();
            for (Module module : tmpVisibleModules) {
                moduleEntries.add(getModuleEntry(module));
            }
            Iterator<Map.Entry<Module, Float>> yIt = moduleYPositions.entrySet().iterator();
            while (yIt.hasNext()) {
                Module module = yIt.next().getKey();
                if (!isModuleVisible(module)) yIt.remove();
            }
            Iterator<Map.Entry<Module, EaseInOutQuad>> animIt = moduleAnimations.entrySet().iterator();
            while (animIt.hasNext()) {
                Map.Entry<Module, EaseInOutQuad> e = animIt.next();
                Module module = e.getKey();
                if (!module.isEnabled() && e.getValue().getOutput() <= 0.001) {
                    animIt.remove();
                }
            }
        } else {
            Iterator<ModuleEntry> it = moduleEntries.iterator();
            while (it.hasNext()) {
                ModuleEntry entry = it.next();
                if (!isModuleVisible(entry.module)) {
                    it.remove();
                    pruneModuleState(entry.module);
                }
            }
        }
    }

    private void calculateTargetSize() {
        boolean isGradient = mode.is(ListMode.Gradient);
        double spacing = itemSpacing.get();
        boolean showCat = isGradient ? false : normalShowCategory.get();

        if (moduleEntries.isEmpty()) {
            targetWidth = 50;
            targetHeight = 20;
            return;
        }
        float maxWidthValue = maxWidth.get().floatValue();
        float maxHeightValue = maxHeight.get().floatValue();
        float scale = hudScale.get().floatValue();
        float totalHeight = PADDING_Y * 2 * scale;

        float maxTextWidth = 0;
        float fontSize = isGradient ? customFontSize.get().floatValue() : 10f;
        int font = getFontId();

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                String text = getDisplayText(entry.module);
                float textWidth = getModuleTextWidth(text);
                if (showCat) {
                    textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                }
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (float) ((fontSize + spacing) * scale);
            }
        }

        if (!moduleEntries.isEmpty()) {
            long visibleModuleCount = 0;
            for (ModuleEntry entry : moduleEntries) {
                EaseInOutQuad animation = moduleAnimations.get(entry.module);
                if (animation != null && animation.getOutput() > 0.01) {
                    visibleModuleCount++;
                }
            }

            if (visibleModuleCount == 0 && !moduleEntries.isEmpty()) {
                for (ModuleEntry entry : moduleEntries) {
                    String text = getDisplayText(entry.module);
                    float textWidth = getModuleTextWidth(text);
                    if (showCat) {
                        textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                    }
                    maxTextWidth = Math.max(maxTextWidth, textWidth);
                    totalHeight += (float) ((fontSize + spacing) * scale);
                }
                totalHeight -= (float) (spacing * scale);
            } else {
                totalHeight -= (float) (spacing * scale);
            }
        }

        targetWidth = Math.min(maxTextWidth + PADDING_X * 2 * scale, maxWidthValue);
        targetHeight = Math.min(totalHeight, maxHeightValue);
    }

    private void updateScroll() {
        float maxHeightValue = maxHeight.get().floatValue();
        if (targetHeight > maxHeightValue) {
            scrollOffset = Math.max(0, scrollOffset);
        } else {
            scrollOffset = 0;
        }
    }

    private void updateAnchors(int screenWidth, int screenHeight, float scaledWidth, float scaledHeight) {
        if (screenWidth <= 0 || screenHeight <= 0) {
            return;
        }
        anchorUsesRightEdge = alignRight.get();
        if (anchorUsesRightEdge) {
            anchorXRatio = (x + scaledWidth) / screenWidth;
        } else {
            anchorXRatio = x / screenWidth;
        }
        anchorYRatio = y / screenHeight;
    }

    private void ensureWithinScreenBounds() {
        if (Float.isNaN(x) || Float.isInfinite(x)) x = 10;
        if (Float.isNaN(y) || Float.isInfinite(y)) y = 10;

        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();
        float scaledWidth = currentWidth * hudScale.get().floatValue();
        float scaledHeight = currentHeight * hudScale.get().floatValue();

        boolean alignChanged = anchorUsesRightEdge != alignRight.get();
        if (alignChanged || Float.isNaN(anchorXRatio) || Float.isNaN(anchorYRatio)) {
            updateAnchors(screenWidth, screenHeight, scaledWidth, scaledHeight);
        }

        boolean screenChanged = lastScaledScreenWidth > 0 && lastScaledScreenHeight > 0
                && (screenWidth != lastScaledScreenWidth || screenHeight != lastScaledScreenHeight);
        if (screenChanged) {
            if (anchorUsesRightEdge) {
                x = anchorXRatio * screenWidth - scaledWidth;
            } else {
                x = anchorXRatio * screenWidth;
            }
            y = anchorYRatio * screenHeight;
        }

        float oldX = x;
        float oldY = y;

        if (x < 0) x = 0;
        float rightEdge = x + scaledWidth;
        if (rightEdge > screenWidth) {
            x = screenWidth - scaledWidth;
            if (x < 0) x = 0;
        }

        if (y < 0) y = 0;
        float bottomEdge = y + scaledHeight;
        if (bottomEdge > screenHeight) {
            y = screenHeight - scaledHeight;
            if (y < 0) y = 0;
        }

        boolean clamped = x != oldX || y != oldY;
        if (!clamped) {
            updateAnchors(screenWidth, screenHeight, scaledWidth, scaledHeight);
        }
        lastScaledScreenWidth = screenWidth;
        lastScaledScreenHeight = screenHeight;
    }

    private void renderContent() {
        if (mode.is(ListMode.Gradient)) {
            renderGradientContent();
            return;
        }

        float scale = hudScale.get().floatValue();
        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        int font = getFontId();
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (10 + itemSpacing.get().floatValue()) * scale;

            // Calculate target Y for this module
            float targetY = currentY;

            // Use pre-calculated renderY
            Float renderYObj = moduleYPositions.get(entry.module);
            float renderY = (renderYObj != null) ? renderYObj : targetY;

            // Increment currentY for the next module based on this module's animated height
            if (animationValue > 0.01) {
                currentY += (float) (itemFullHeight * animationValue);
            }

            if (renderY + (10 * scale) < y || renderY > y + (currentHeight * scale)) {
                continue;
            }

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getEnglishName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);
            float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, 10 * scale);
            float suffixWidth = NanoVGHelper.getTextWidth(formattedSuffix, font, 10 * scale);
            float textHeight = NanoVGHelper.getFontHeight(font, 10 * scale);
            float totalTextWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : suffixWidth + (2 * scale));
            String categoryIcon = "";
            float iconWidth = 0;
            float iconHeight = 0;
            if (normalShowCategory.get()) {
                int iconFont = FontLoader.icons();
                categoryIcon = CATEGORY_ICON;
                iconWidth = NanoVGHelper.getTextWidth(categoryIcon, iconFont, 10 * scale);
                iconHeight = NanoVGHelper.getFontHeight(iconFont, 10 * scale);
                totalTextWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
            }
            float itemWidth = totalTextWidth + (PADDING_X * 2 * scale);
            float itemHeight = 10 * scale;
            float itemX = alignRight.get() ? x + (currentWidth * scale) - itemWidth : x;
            float textX;
            float iconBgX = 0;
            float iconX = 0;
            if (alignRight.get() && normalShowCategory.get()) {
                iconBgX = x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale);
                textX = iconBgX - (CATEGORY_ICON_SPACING * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : suffixWidth + (2 * scale));
            } else if (!alignRight.get() && normalShowCategory.get()) {
                iconBgX = itemX + (6 * scale);
                textX = iconBgX + (ICON_BACKGROUND_WIDTH * scale) + (CATEGORY_ICON_SPACING * scale);
            } else {
                textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : suffixWidth + (2 * scale)) : itemX + (PADDING_X * scale);
            }

            // Use renderY instead of currentY for drawing
            float drawY = renderY;
            float textY = drawY + textHeight / 2 + (2 * scale);

            int alpha = (int) (BACKGROUND_COLOR.getAlpha() * animationValue);
            Color animatedBackgroundColor = new Color(
                    BACKGROUND_COLOR.getRed(),
                    BACKGROUND_COLOR.getGreen(),
                    BACKGROUND_COLOR.getBlue(),
                    alpha
            );

            float animatedItemWidth = itemWidth * (float) animationValue;
            float animatedTextX = alignRight.get() ?
                    textX + (itemWidth - animatedItemWidth) : textX;

            NanoVGHelper.drawRoundRect(
                    alignRight.get() && normalShowCategory.get() ?
                            (itemX + (4 * scale) + (itemWidth - animatedItemWidth)) :
                            (itemX + (normalShowCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING + 4) * scale : (4 * scale)) + (itemWidth - animatedItemWidth)),
                    drawY - (3 * scale),
                    (itemWidth - (normalShowCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale : 0) - (7 * scale)) * (float) animationValue,
                    itemHeight + (3 * scale),
                    normalRadius.get().floatValue() * scale,
                    animatedBackgroundColor
            );

            if (normalShowCategory.get()) {
                float animatedIconBgX = alignRight.get() ?
                        x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale) - (itemWidth - animatedItemWidth) :
                        iconBgX + (itemWidth - animatedItemWidth);

                NanoVGHelper.drawRoundRect(
                        animatedIconBgX,
                        drawY - (3 * scale),
                        ICON_BACKGROUND_WIDTH * scale,
                        ICON_BACKGROUND_HEIGHT * scale,
                        normalRadius.get().floatValue() * scale,
                        animatedBackgroundColor
                );
                float iconY = drawY + ((ICON_BACKGROUND_HEIGHT * scale) - iconHeight) / 2;
                iconX = animatedIconBgX + ((ICON_BACKGROUND_WIDTH * scale) - iconWidth) / 2;
                int iconFont = FontLoader.icons();
                NanoVGHelper.drawGlowingString(categoryIcon, iconX + (0.5f * scale), iconY + (5 * scale), iconFont, 10 * scale, Color.WHITE, 2.0f * scale);
            }

            Color textColor = normalRainbowColor.get() ?
                    ClickGui.color(0) :
                    Color.WHITE;
            Color animatedTextColor = new Color(
                    textColor.getRed(),
                    textColor.getGreen(),
                    textColor.getBlue(),
                    (int) (textColor.getAlpha() * animationValue)
            );

            NanoVGHelper.drawString(moduleName, animatedTextX, textY, font, 10 * scale, animatedTextColor);
            if (!suffix.isEmpty()) {
                float suffixX = animatedTextX + moduleNameWidth + (2 * scale);
                Color animatedSuffixColor = new Color(
                        SUFFIX_COLOR.getRed(),
                        SUFFIX_COLOR.getGreen(),
                        SUFFIX_COLOR.getBlue(),
                        (int) (SUFFIX_COLOR.getAlpha() * animationValue)
                );
                NanoVGHelper.drawString(formattedSuffix, suffixX, textY, font, 10 * scale, animatedSuffixColor);
            }
        }
    }

    private String getDisplayText(Module module) {
        String name = module.getDisplayName();
        String suffix = module.getSuffix();
        if (suffix.isEmpty()) {
            return name;
        }

        String prefixSymbol = "";
        String suffixSymbol = "";

        int style = suffixStyle.get();

        switch (style) {
            case 1: // []
                prefixSymbol = "[";
                suffixSymbol = "]";
                break;
            case 2: // <>
                prefixSymbol = "<";
                suffixSymbol = ">";
                break;
            case 3: // ()
                prefixSymbol = "(";
                suffixSymbol = ")";
                break;
            default: // No symbols
                return name + " " + suffix;
        }

        return name + " " + prefixSymbol + suffix + suffixSymbol;
    }

    private String getFormattedSuffix(String suffix) {
        if (suffix.isEmpty()) {
            return "";
        }

        String prefixSymbol;
        String suffixSymbol;

        int style = suffixStyle.get();

        switch (style) {
            case 1 -> {
                prefixSymbol = "[";
                suffixSymbol = "]";
            }
            case 2 -> {
                prefixSymbol = "<";
                suffixSymbol = ">";
            }
            case 3 -> {
                prefixSymbol = "(";
                suffixSymbol = ")";
            }
            default -> {
                return suffix;
            }
        }

        return prefixSymbol + suffix + suffixSymbol;
    }

    private void renderBlurBackgrounds() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float startX = x;
        float startY = y;

        startY -= (scrollOffset * scale);
        startY += (PADDING_Y * scale);

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue <= 0.01) continue;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;

            // We use the stored renderY from update() logic
            Float renderYObj = moduleYPositions.get(entry.module);
            if (renderYObj == null) continue;
            float renderY = renderYObj;

            String moduleName = entry.module.getEnglishName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);
            float moduleNameWidth = getModuleTextWidth(moduleName);
            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

            float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
            float itemBgX = alignRight.get() ?
                    (startX + (currentWidth * scale) - itemWidth + (PADDING_X * scale)) :
                    (startX + (PADDING_X * scale));

            float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
            float bgOffset = backgroundOffsetY.get().floatValue() * scale;
            float bgY = renderY + bgOffset - heightAdjustment;
            float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;


            BlurShader.drawRoundedBlur(itemBgX - (4 * scale), bgY, bgWidth, bgH, backgroundRadius.get().floatValue() * scale, 10);
        }
    }

    private void renderGradientContent() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        int font = getFontId();
        int index = 0;

        float totalListHeight = 0;
        float maxItemWidth = 0;
        float startY = currentY;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;

            if (animationValue > 0.01) {
                totalListHeight += (float) (itemFullHeight * animationValue);

                String moduleName = entry.module.getEnglishName();
                String suffix = entry.module.getSuffix();
                String formattedSuffix = getFormattedSuffix(suffix);
                float moduleNameWidth = getModuleTextWidth(moduleName);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

                // Use current module's width directly, not maxItemWidth
                maxItemWidth = itemWidth;
            }
        }

        if (background.get() && totalListHeight > 0 && !backgroundMode.is(BackgroundMode.Blur)) {
            float currentBgY = startY;
            int bgIndex = 0;
            for (ModuleEntry entry : moduleEntries) {
                EaseInOutQuad animation = moduleAnimations.get(entry.module);
                double animationValue = animation != null ? animation.getOutput() : 1.0;
                if (animationValue <= 0.01) continue;

                double offset = (System.currentTimeMillis() * gradientSpeed.get()) / 50.0;
                double currentOffset = offset + (bgIndex * colorStep.get());
                double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

                float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
                float renderY = moduleYPositions.getOrDefault(entry.module, currentBgY);

                String moduleName = entry.module.getEnglishName();
                String suffix = entry.module.getSuffix();
                String formattedSuffix = getFormattedSuffix(suffix);
                float moduleNameWidth = getModuleTextWidth(moduleName);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

                float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
                float itemBgX = alignRight.get() ?
                        (x + (currentWidth * scale) - itemWidth + (PADDING_X * scale)) :
                        (x + (PADDING_X * scale));

                // If spacing is 0, add a tiny bit of height overlap to prevent gaps
                float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
                float bgOffset = backgroundOffsetY.get().floatValue() * scale;
                float bgY = renderY + bgOffset - heightAdjustment;
                float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;

                Color c1 = gradientColor1.get();
                Color c2 = gradientColor2.get();

                if (autoColor.get()) {
                    float hue = (float) ((System.currentTimeMillis() * gradientSpeed.get() / 5000.0) % 1.0);
                    c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
                    c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
                }

                NanoVGHelper.drawRoundRect(itemBgX - (4 * scale), bgY, bgWidth, bgH, backgroundRadius.get().floatValue() * scale, backgroundColor.get());

                if (showGradientLine.get()) {
                    float lineW = lineWidth.get().floatValue() * scale;
                    Color lineColor = interpolateColor(c1, c2, (float) factor);
                    lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * animationValue));

                    if (lineMode.is(LineMode.Left)) {
                        float lineX = alignRight.get() ? (itemBgX - (4 * scale) + bgWidth - lineW) : (itemBgX - (4 * scale));
                        NanoVGHelper.drawRoundRect(lineX, bgY, lineW, bgH, backgroundRadius.get().floatValue() * scale, lineColor);
                    } else if (lineMode.is(LineMode.Box)) {
                        NanoVGHelper.drawRoundRectOutline(itemBgX - (4 * scale), bgY, bgWidth, bgH, backgroundRadius.get().floatValue() * scale, lineW, lineColor);
                    }
                }

                currentBgY += (float) (itemFullHeight * animationValue);
                bgIndex++;
            }
        }

        if (background.get() && totalListHeight > 0 && backgroundMode.is(BackgroundMode.Blur)) {
            float currentBgY = startY;
            int bgIndex = 0;
            for (ModuleEntry entry : moduleEntries) {
                EaseInOutQuad animation = moduleAnimations.get(entry.module);
                double animationValue = animation != null ? animation.getOutput() : 1.0;

                if (animationValue <= 0.01) continue;

                double offset = (System.currentTimeMillis() * gradientSpeed.get()) / 50.0;
                double currentOffset = offset + (bgIndex * colorStep.get());
                double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

                float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
                float renderY = moduleYPositions.getOrDefault(entry.module, currentBgY);

                String moduleName = entry.module.getEnglishName();
                String suffix = entry.module.getSuffix();
                String formattedSuffix = getFormattedSuffix(suffix);
                float moduleNameWidth = getModuleTextWidth(moduleName);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

                float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
                float itemBgX = alignRight.get() ? (x + (currentWidth * scale) - itemWidth + (PADDING_X * scale)) : (x + (PADDING_X * scale));

                float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
                float bgOffset = backgroundOffsetY.get().floatValue() * scale;
                float bgY = renderY + bgOffset - heightAdjustment;
                float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;

                Color c1 = gradientColor1.get();
                Color c2 = gradientColor2.get();

                if (autoColor.get()) {
                    float hue = (float) ((System.currentTimeMillis() * gradientSpeed.get() / 5000.0) % 1.0);
                    c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
                    c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
                }

                NanoVGHelper.drawRoundRect(itemBgX - (4 * scale), bgY, bgWidth, bgH, backgroundRadius.get().floatValue() * scale, backgroundColor.get());

                if (showGradientLine.get()) {
                    float lineW = lineWidth.get().floatValue() * scale;
                    Color lineColor = interpolateColor(c1, c2, (float) factor);
                    lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * animationValue));

                    if (lineMode.is(LineMode.Left)) {
                        float lineX = alignRight.get() ? (itemBgX - (4 * scale) + bgWidth - lineW) : (itemBgX - (4 * scale));

                        NanoVGHelper.drawRoundRect(lineX, bgY, lineW, bgH, backgroundRadius.get().floatValue() * scale, lineColor);
                    } else if (lineMode.is(LineMode.Box)) {
                        NanoVGHelper.drawRoundRectOutline(itemBgX - (4 * scale), bgY, bgWidth, bgH, backgroundRadius.get().floatValue() * scale, lineW, lineColor);
                    }
                }

                currentBgY += (float) (itemFullHeight * animationValue);
                bgIndex++;
            }
        }

        currentY = startY;
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentY);

            if (animationValue > 0.01) {
                currentY += (float) (itemFullHeight * animationValue);
                index++;
            }

            if (renderY + (fontSize * scale) < y || renderY > y + (currentHeight * scale)) {
                continue;
            }

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getEnglishName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);
            float moduleNameWidth = getModuleTextWidth(moduleName);
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize * scale);
            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

            float itemX = alignRight.get() ? x + (currentWidth * scale) - itemWidth : x;
            float textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) : itemX + (PADDING_X * scale);

            // Use renderY
            float drawY = renderY;
            float textYOffset = textOffsetY.get().floatValue() * scale;
            float textY = drawY + textHeight / 2 + (2 * scale) + textYOffset;

            float animatedItemWidth = itemWidth * (float) animationValue;
            float animatedTextX = alignRight.get() ?
                    textX + (itemWidth - animatedItemWidth) : textX;

            Color c1 = gradientColor1.get();
            Color c2 = gradientColor2.get();

            if (autoColor.get()) {
                float hue = (float) ((System.currentTimeMillis() * gradientSpeed.get() / 5000.0) % 1.0);
                c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
                c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
            }

            double offset = (System.currentTimeMillis() * gradientSpeed.get()) / 50.0;
            double currentOffset = offset + (index * colorStep.get());
            double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

            Color textColor = interpolateColor(c1, c2, (float) factor);

            Color animatedTextColor = new Color(
                    textColor.getRed(),
                    textColor.getGreen(),
                    textColor.getBlue(),
                    (int) (textColor.getAlpha() * animationValue)
            );

            if (!fontMode.is(FontMode.Minecraft)) {
                if (textGlow.get()) {
                    NanoVGHelper.drawGlowingString(moduleName, animatedTextX, textY - (fontSize * scale / 2) + (1 * scale), font, fontSize * scale, animatedTextColor, glowRadius.get().floatValue() * scale, glowIntensity.get());
                } else {
                    NanoVGHelper.drawString(moduleName, animatedTextX, textY, font, fontSize * scale, animatedTextColor);
                }

                if (!suffix.isEmpty()) {
                    float suffixX = animatedTextX + moduleNameWidth + (2 * scale);
                    Color animatedSuffixColor = new Color(
                            SUFFIX_COLOR.getRed(),
                            SUFFIX_COLOR.getGreen(),
                            SUFFIX_COLOR.getBlue(),
                            (int) (SUFFIX_COLOR.getAlpha() * animationValue)
                    );
                    if (textGlow.get()) {
                        NanoVGHelper.drawGlowingString(formattedSuffix, suffixX, textY - (fontSize * scale / 2) + (1 * scale), font, fontSize * scale, animatedSuffixColor, glowRadius.get().floatValue() * scale, glowIntensity.get());
                    } else {
                        NanoVGHelper.drawString(formattedSuffix, suffixX, textY, font, fontSize * scale, animatedSuffixColor);
                    }
                }
            }
        }
    }

    private void renderGradientTextVanilla(DrawContext context) {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);
        float startY = currentY;
        int index = 0;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentY);

            if (animationValue > 0.01) {
                currentY += (float) (itemFullHeight * animationValue);
                index++;
            }

            if (renderY + (fontSize * scale) < y || renderY > y + (currentHeight * scale)) {
                continue;
            }

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getEnglishName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);

            float moduleNameWidth = getModuleTextWidth(moduleName);
            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) + (PADDING_X * 2 * scale);

            float itemX = alignRight.get() ? x + (currentWidth * scale) - itemWidth : x;
            float textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + (2 * scale)) : itemX + (PADDING_X * scale);

            float textYOffset = textOffsetY.get().floatValue() * scale;

            float finalFontSize = fontSize * scale;
            float textY_Center = renderY + (finalFontSize / 2.0f) + (2 * scale) + textYOffset;

            float animatedItemWidth = itemWidth * (float) animationValue;
            float animatedTextX = alignRight.get() ? textX + (itemWidth - animatedItemWidth) : textX;

            Color c1 = gradientColor1.get();
            Color c2 = gradientColor2.get();

            if (autoColor.get()) {
                float hue = (float) ((System.currentTimeMillis() * gradientSpeed.get() / 5000.0) % 1.0);
                c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
                c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
            }

            double offset = (System.currentTimeMillis() * gradientSpeed.get()) / 50.0;
            double currentOffset = offset + (index * colorStep.get());
            double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

            Color textColor = interpolateColor(c1, c2, (float) factor);
            Color animatedTextColor = new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), (int) (textColor.getAlpha() * animationValue));

            Matrix3x2fStack matrices = context.getMatrices();
            matrices.pushMatrix();

            float fontScale = (fontSize * scale) / 9.0f;

            matrices.translate(animatedTextX, textY_Center);
            matrices.scale(fontScale, fontScale);

            context.drawTextWithShadow(mc.textRenderer, moduleName, 0, (int) -4.5f, animatedTextColor.getRGB());

            matrices.popMatrix();

            if (!suffix.isEmpty()) {
                float suffixX = animatedTextX + moduleNameWidth + (2 * scale);
                Color animatedSuffixColor = new Color(SUFFIX_COLOR.getRed(), SUFFIX_COLOR.getGreen(), SUFFIX_COLOR.getBlue(), (int) (SUFFIX_COLOR.getAlpha() * animationValue));

                matrices.pushMatrix();
                matrices.translate(suffixX, textY_Center);
                matrices.scale(fontScale, fontScale);

                context.drawTextWithShadow(mc.textRenderer, formattedSuffix, 0, (int) -4.5f, animatedSuffixColor.getRGB());

                matrices.popMatrix();
            }
        }
    }

    private Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(r, g, b, a);
    }

    private record ModuleEntry(Module module) {
    }
}
