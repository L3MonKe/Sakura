package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.animations.Direction;
import dev.mahiro.client.utils.animations.impl.EaseInOutQuad;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ModuleListHud extends HudModule {
    public ModuleListHud() {
        super("ModuleList", "功能列表", 10, 10);
    }

    public enum ListMode {
        Normal,
        Gradient
    }

    // --- 核心设置 (Core Settings) ---
    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Normal);
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
    private final BoolValue normalEnableBloom = new BoolValue("Normal Enable Bloom", "普通-光晕", true, () -> mode.is(ListMode.Normal));
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

    // 5. 光晕设置 (Bloom)
    private final BoolValue bloom = new BoolValue("Bloom", "渐变-光晕", false, () -> mode.is(ListMode.Gradient));

    public enum BloomMode {Stencil, Standard, Kawase}

    private final EnumValue<BloomMode> bloomMode = new EnumValue<>("Bloom Mode", "渐变-光晕模式", BloomMode.Stencil, () -> mode.is(ListMode.Gradient) && bloom.get());

    public enum BloomStyle {Gradient, Static}

    private final EnumValue<BloomStyle> bloomStyle = new EnumValue<>("Bloom Style", "渐变-光晕样式", BloomStyle.Gradient, () -> mode.is(ListMode.Gradient) && bloom.get());
    private final ColorValue bloomColor = new ColorValue("Bloom Color", "渐变-光晕颜色", new Color(0, 255, 255), () -> mode.is(ListMode.Gradient) && bloom.get() && bloomStyle.is(BloomStyle.Static));
    private final NumberValue<Double> bloomRadius = new NumberValue<>("Bloom Radius", "渐变-光晕半径", 5.0, 1.0, 20.0, 1.0, () -> mode.is(ListMode.Gradient) && bloom.get());


    private final List<ModuleEntry> moduleEntries = new ArrayList<>();
    private final Map<Module, EaseInOutQuad> moduleAnimations = new java.util.HashMap<>();
    private final Map<Module, Float> moduleYPositions = new java.util.HashMap<>();
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

    private static final String[] ICON_SET = {"U"};
    private static final float ICON_BACKGROUND_WIDTH = 12f;
    private static final float ICON_BACKGROUND_HEIGHT = 12f;

    private static final Random RANDOM = new Random();

    public static void onModuleToggle(Module module, boolean enabled) {
        ModuleListHud instance = Mahiro.MODULES.getModule(ModuleListHud.class);

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

        // Use a temporary list to calculate target Ys without rendering
        float totalListHeight = 0;

        // We need to simulate the layout logic to get target Ys
        // This mirrors the logic in renderContent/renderGradientContent
        // Note: For Normal mode, it uses currentY += itemHeight * anim.
        // For Gradient mode, it does the same.

        float startY = currentY;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing) * scale;

            // Target Y is where it SHOULD be
            float targetY = startY + totalListHeight;

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
                totalListHeight += itemFullHeight * animationValue;
            }
        }
    }

    private final java.util.Map<Module, String> moduleIconMap = new java.util.HashMap<>();

    private void updateModuleList() {
        List<Module> visibleModules = Mahiro.MODULES.getAllModules().stream()
                .filter(module -> module.isEnabled() || (moduleAnimations.containsKey(module) && moduleAnimations.get(module).getOutput() > 0.001))
                .filter(module -> !module.isHidden())
                .filter(module -> !hideHudModules.get() || !(module instanceof HudModule))
                .sorted((m1, m2) -> {
                    String displayText1 = getDisplayText(m1);
                    String displayText2 = getDisplayText(m2);

                    float fontSize = mode.is(ListMode.Gradient) ? customFontSize.get().floatValue() : 10f;
                    int font = FontLoader.medium(fontSize);
                    float scale = hudScale.get().floatValue();
                    float width1 = NanoVGHelper.getTextWidth(displayText1, font, fontSize * scale);
                    float width2 = NanoVGHelper.getTextWidth(displayText2, font, fontSize * scale);
                    return Float.compare(width2, width1);
                })
                .toList();

        java.util.Iterator<java.util.Map.Entry<Module, String>> iterator = moduleIconMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<Module, String> entry = iterator.next();
            Module module = entry.getKey();
            if (!visibleModules.contains(module)) {
                iterator.remove();
            }
        }

        java.util.Iterator<java.util.Map.Entry<Module, Float>> yIterator = moduleYPositions.entrySet().iterator();
        while (yIterator.hasNext()) {
            java.util.Map.Entry<Module, Float> entry = yIterator.next();
            Module module = entry.getKey();
            if (!visibleModules.contains(module)) {
                yIterator.remove();
            }
        }

        moduleEntries.clear();
        for (Module module : visibleModules) {
            moduleEntries.add(new ModuleEntry(module));
            if (!moduleIconMap.containsKey(module)) {
                String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
                moduleIconMap.put(module, icon);
            }
        }
    }

    private void calculateTargetSize() {
        boolean isGradient = mode.is(ListMode.Gradient);
        double spacing = itemSpacing.get();
        boolean showCat = isGradient ? false : normalShowCategory.get();
        boolean showIconValue = false;

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
        int font = FontLoader.medium(fontSize);

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                String text = getDisplayText(entry.module);
                float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize * scale);
                if (showCat) {
                    textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                }
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (float) ((fontSize + spacing) * scale);
            }
        }

        if (!moduleEntries.isEmpty()) {
            long visibleModuleCount = moduleEntries.stream()
                    .map(entry -> moduleAnimations.get(entry.module))
                    .filter(animation -> animation != null && animation.getOutput() > 0.01)
                    .count();

            if (visibleModuleCount == 0 && !moduleEntries.isEmpty()) {
                for (ModuleEntry entry : moduleEntries) {
                    String text = getDisplayText(entry.module);
                    float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize * scale);
                    if (showCat) {
                        textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                    }
                    maxTextWidth = Math.max(maxTextWidth, textWidth);
                    totalHeight += (fontSize + spacing) * scale;
                }
                totalHeight -= spacing * scale;
            } else {
                totalHeight -= spacing * scale;
            }
        }

        if (showIconValue) {
            float iconRenderSize = 13.0f * scale;
            int sakuraFont = FontLoader.bold(13);
            float sakuraTextWidth = NanoVGHelper.getTextWidth("ModuleList", sakuraFont, 11 * scale);
            float totalRequiredWidth = iconRenderSize + sakuraTextWidth + 4 * scale;
            maxTextWidth = Math.max(maxTextWidth, totalRequiredWidth);
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
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float scale = hudScale.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        int font = FontLoader.medium(10);
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
                int iconFont = FontLoader.icons(10);
                categoryIcon = getRandomCategoryIcon(entry.module);
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

            if (normalEnableBloom.get()) {
                /*
                NanoVGHelper.drawRoundRectBloom(
                        alignRight.get() && normalShowCategory.get() ?
                                (itemX + (4 * scale) + (itemWidth - animatedItemWidth)) :
                                (itemX + (normalShowCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING + 4) * scale : (4 * scale)) + (itemWidth - animatedItemWidth)),
                        drawY - (3 * scale),
                        (itemWidth - (normalShowCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale : 0) - (7 * scale)) * (float) animationValue,
                        itemHeight + (3 * scale),
                        normalRadius.get().floatValue() * scale,
                        animatedBackgroundColor
                );
                */
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
            } else {
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
            }

            if (normalShowCategory.get()) {
                float animatedIconBgX = alignRight.get() ?
                        x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale) - (itemWidth - animatedItemWidth) :
                        iconBgX + (itemWidth - animatedItemWidth);

                if (normalEnableBloom.get()) {
                    /*
                    NanoVGHelper.drawRoundRectBloom(
                            animatedIconBgX,
                            drawY - (3 * scale),
                            ICON_BACKGROUND_WIDTH * scale,
                            ICON_BACKGROUND_HEIGHT * scale,
                            normalRadius.get().floatValue() * scale,
                            animatedBackgroundColor
                    );
                    */
                    NanoVGHelper.drawRoundRect(
                            animatedIconBgX,
                            drawY - (3 * scale),
                            ICON_BACKGROUND_WIDTH * scale,
                            ICON_BACKGROUND_HEIGHT * scale,
                            normalRadius.get().floatValue() * scale,
                            animatedBackgroundColor
                    );
                } else {
                    NanoVGHelper.drawRoundRect(
                            animatedIconBgX,
                            drawY - (3 * scale),
                            ICON_BACKGROUND_WIDTH * scale,
                            ICON_BACKGROUND_HEIGHT * scale,
                            normalRadius.get().floatValue() * scale,
                            animatedBackgroundColor
                    );
                }
                float iconY = drawY + ((ICON_BACKGROUND_HEIGHT * scale) - iconHeight) / 2;
                iconX = animatedIconBgX + ((ICON_BACKGROUND_WIDTH * scale) - iconWidth) / 2;
                int iconFont = FontLoader.icons(10);
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
                return suffix;
        }

        return prefixSymbol + suffix + suffixSymbol;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private String getRandomCategoryIcon(Module module) {
        if (!moduleIconMap.containsKey(module)) {
            String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
            moduleIconMap.put(module, icon);
        }
        return moduleIconMap.get(module);
    }

    private void renderBlurBackgrounds() {
        // This method renders blur backgrounds using Shader2DUtil directly (outside NanoVG context)
        // We need to manually calculate screen coordinates since we don't have NanoVG's transform stack

        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        // Base coordinates
        float startX = x;
        float startY = y;

        // Apply scrolling
        startY -= (scrollOffset * scale);

        // Initial padding offset
        startY += (PADDING_Y * scale);

        int font = FontLoader.medium(fontSize);
        float currentBgY = startY;

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
            float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, fontSize * scale);
            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) + (PADDING_X * 2 * scale);

            float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
            float itemBgX = alignRight.get() ?
                    (startX + (currentWidth * scale) - itemWidth + (PADDING_X * scale)) :
                    (startX + (PADDING_X * scale));

            float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
            float bgOffset = backgroundOffsetY.get().floatValue() * scale;
            float bgY = renderY + bgOffset - heightAdjustment;
            float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;

            float alpha = 1.0f;
            Color blurColor = new Color(0, 0, 0, 0);

            dev.mahiro.client.utils.render.Shader2DUtil.drawRoundedBlur(
                    new net.minecraft.client.util.math.MatrixStack(),
                    itemBgX - (4 * scale),
                    bgY,
                    bgWidth,
                    bgH,
                    backgroundRadius.get().floatValue() * scale,
                    blurColor,
                    10, // Blur radius
                    alpha // Use user-defined alpha for blur opacity
            );

            currentBgY += itemFullHeight * animationValue;
        }
    }

    private void renderGradientContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        int font = FontLoader.medium(fontSize);
        int index = 0;

        float totalListHeight = 0;
        float maxItemWidth = 0;
        float startY = currentY;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;

            // Calculate target Y
            float targetY = startY + totalListHeight;
            // Use pre-calculated renderY
            Float renderYObj = moduleYPositions.get(entry.module);
            float renderY = (renderYObj != null) ? renderYObj : targetY;

            if (animationValue > 0.01) {
                totalListHeight += (float) (itemFullHeight * animationValue);

                String moduleName = entry.module.getEnglishName();
                String suffix = entry.module.getSuffix();
                String formattedSuffix = getFormattedSuffix(suffix);
                float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, fontSize * scale);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) + (PADDING_X * 2 * scale);

                // Use current module's width directly, not maxItemWidth
                maxItemWidth = itemWidth;
            }
        }

        // 2. Draw the background (ONLY if NOT in Blur mode, as Blur is handled in Pass 1)
        if (background.get() && totalListHeight > 0 && !backgroundMode.is(BackgroundMode.Blur)) {
            float bgX = alignRight.get() ?
                    (x + (currentWidth * scale) - maxItemWidth + (PADDING_X * scale)) :
                    (x + (PADDING_X * scale));

            // Draw individual background for each item to match its width
            float currentBgY = startY;
            int bgIndex = 0; // Add index for gradient calculation
            for (ModuleEntry entry : moduleEntries) {
                EaseInOutQuad animation = moduleAnimations.get(entry.module);
                double animationValue = animation != null ? animation.getOutput() : 1.0;

                if (animationValue <= 0.01) continue;

                // Calculate color factor (copied from text rendering logic)
                double offset = (System.currentTimeMillis() * gradientSpeed.get()) / 50.0;
                double currentOffset = offset + (bgIndex * colorStep.get());
                double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

                float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
                float renderY = moduleYPositions.getOrDefault(entry.module, currentBgY);

                String moduleName = entry.module.getEnglishName();
                String suffix = entry.module.getSuffix();
                String formattedSuffix = getFormattedSuffix(suffix);
                float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, fontSize * scale);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) + (PADDING_X * 2 * scale);

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

                if (bloom.get() && bloomMode.is(BloomMode.Standard)) {
                    /*
                    Color bColor;
                    if (bloomStyle.is(BloomStyle.Static)) {
                        bColor = bloomColor.get();
                    } else {
                         Color currentGradientColor = interpolateColor(c1, c2, (float)factor);
                         bColor = currentGradientColor;
                    }
                    bColor = new Color(bColor.getRed(), bColor.getGreen(), bColor.getBlue(), (int)(bColor.getAlpha() * animationValue));
                    
                    // Use drawRoundRectBloom instead of drawBloomBox for Standard mode
                    // This draws the glow AND the rect, but we only want the glow?
                    // Wait, drawRoundRectBloom draws both. 
                    // But we want to draw the glow behind the background.
                    // The background is drawn by NanoVGHelper.drawRoundRect below.
                    // If we use drawRoundRectBloom, it will draw a solid rect on top of the glow.
                    // We can just use the glow part?
                    // No, let's use drawRoundRectBloom but with 0 alpha for the main rect?
                    // Actually, drawRoundRectBloom draws the main rect at the end.
                    // We can just rely on drawRoundRectBloom to draw EVERYTHING (Glow + Background)?
                    // But the background color logic is handled separately below (backgroundColor.get()).
                    // So we should use a method that ONLY draws glow.
                    
                    // Let's use drawHollowRoundRectBloom logic but without hole?
                    // Or just use drawBloomBox but with better visibility?
                    // Actually, let's use drawRoundRectBloom but modify it to NOT draw the center rect if we don't want it.
                    // But here we can just let it draw the glow, and let the background draw over it.
                    // But drawRoundRectBloom draws the center rect at the end.
                    // If we use it here, it will draw a colored rect (bColor) in the center.
                    // But we want the center to be backgroundColor.get().
                    
                    // So we should use drawBloomBox but ensure it's visible.
                    // Or use drawHollowRoundRectBloom? No, standard glow fills the center.
                    
                    // Let's use a modified call to just draw the glow layers.
                    // Since I cannot modify NanoVGHelper again in this thought process easily without losing context,
                    // I will use drawBloomBox but I suspect the issue was visibility.
                    // I will try to use drawHollowRoundRectBloom instead? No.
                    
                    // Wait, I updated drawRoundRectBloom to take glowRadius.
                    // I can use it. The center rect will be drawn with bColor.
                    // bColor is the GLOW color.
                    // If I draw this, the center will be GLOW color.
                    // Then I draw the actual background on top with backgroundColor.get().
                    // This works fine! The background will cover the glow center.
                    
                    NanoVGHelper.drawRoundRectBloom(
                        itemBgX - (4 * scale),
                        bgY, 
                        bgWidth,
                        bgH, 
                        backgroundRadius.get().floatValue() * scale,
                        bloomRadius.get().floatValue() * scale,
                        bColor
                    );
                    */
                }

                // Only draw Normal background here
                NanoVGHelper.drawRoundRect(
                        itemBgX - (4 * scale),
                        bgY,
                        bgWidth,
                        bgH,
                        backgroundRadius.get().floatValue() * scale,
                        backgroundColor.get()
                );

                if (showGradientLine.get()) {
                    float lineW = lineWidth.get().floatValue() * scale;
                    // Calculate dynamic color for the line based on the module's position/index
                    Color lineColor = interpolateColor(c1, c2, (float) factor);
                    // Apply animation alpha
                    lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * animationValue));

                    if (lineMode.is(LineMode.Left)) {
                        // Left side line (or right side if aligned right)
                        float lineX = alignRight.get() ?
                                (itemBgX - (4 * scale) + bgWidth - lineW) :
                                (itemBgX - (4 * scale));

                        NanoVGHelper.drawRoundRect(
                                lineX,
                                bgY,
                                lineW,
                                bgH,
                                backgroundRadius.get().floatValue() * scale,
                                lineColor
                        );
                    } else if (lineMode.is(LineMode.Box)) {
                        // Box style (hollow rectangle)
                        NanoVGHelper.drawRoundRectOutline(
                                itemBgX - (4 * scale),
                                bgY,
                                bgWidth,
                                bgH,
                                backgroundRadius.get().floatValue() * scale,
                                lineW,
                                lineColor
                        );
                    }
                }

                currentBgY += itemFullHeight * animationValue;
                bgIndex++;
            }
        }

        // If in Blur mode, we still need to render the gradient lines (since they are NanoVG)
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
                float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, fontSize * scale);
                float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) + (PADDING_X * 2 * scale);

                float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
                float itemBgX = alignRight.get() ?
                        (x + (currentWidth * scale) - itemWidth + (PADDING_X * scale)) :
                        (x + (PADDING_X * scale));

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

                // Only draw Normal background here
                NanoVGHelper.drawRoundRect(
                        itemBgX - (4 * scale),
                        bgY,
                        bgWidth,
                        bgH,
                        backgroundRadius.get().floatValue() * scale,
                        backgroundColor.get()
                );

                if (showGradientLine.get()) {
                    float lineW = lineWidth.get().floatValue() * scale;
                    Color lineColor = interpolateColor(c1, c2, (float) factor);
                    lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * animationValue));

                    if (lineMode.is(LineMode.Left)) {
                        float lineX = alignRight.get() ?
                                (itemBgX - (4 * scale) + bgWidth - lineW) :
                                (itemBgX - (4 * scale));

                        NanoVGHelper.drawRoundRect(
                                lineX,
                                bgY,
                                lineW,
                                bgH,
                                backgroundRadius.get().floatValue() * scale,
                                lineColor
                        );
                    } else if (lineMode.is(LineMode.Box)) {
                        NanoVGHelper.drawRoundRectOutline(
                                itemBgX - (4 * scale),
                                bgY,
                                bgWidth,
                                bgH,
                                backgroundRadius.get().floatValue() * scale,
                                lineW,
                                lineColor
                        );
                    }
                }

                currentBgY += itemFullHeight * animationValue;
                bgIndex++;
            }
        }

        // 3. Render the text items
        currentY = startY; // Reset Y for text rendering
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentY); // Use pre-calculated/interpolated Y

            // Increment currentY for next iteration logic (although we use renderY for drawing)
            if (animationValue > 0.01) {
                currentY += itemFullHeight * animationValue;
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
            float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, fontSize * scale);
            float textHeight = NanoVGHelper.getFontHeight(font, fontSize * scale);
            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) + (PADDING_X * 2 * scale);

            float itemX = alignRight.get() ? x + (currentWidth * scale) - itemWidth : x;
            float textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : NanoVGHelper.getTextWidth(formattedSuffix, font, fontSize * scale) + (2 * scale)) : itemX + (PADDING_X * scale);

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

    private Color interpolateColor(Color c1, Color c2, float t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * t);
        return new Color(r, g, b, a);
    }

    private static class ModuleEntry {
        final Module module;

        ModuleEntry(Module module) {
            this.module = module;
        }
    }

    private static class Particle {
        public float x, y;
        public float velocityX, velocityY;
        public float size;
        public Color color;
        public float alpha;
        public float life;
        public float maxLife;

        public Particle(float x, float y) {
            this.x = x;
            this.y = y;
            this.size = 1.0f + (float) (Math.random() * 2.0f);
            this.color = ClickGui.mainColor.get();
            this.alpha = 1.0f;
            this.maxLife = 100f + (float) (Math.random() * 100f);
            this.life = maxLife;
            float speed = 0.5f + (float) (Math.random() * 1.5f);
            double angle = Math.random() * Math.PI * 2;
            this.velocityX = (float) (Math.cos(angle) * speed);
            this.velocityY = (float) (Math.sin(angle) * speed);
        }

        public void update() {
            x += velocityX;
            y += velocityY;
            life -= 1.0f;
            alpha = life / maxLife;
        }

        public boolean isAlive() {
            return life > 0;
        }
    }
}
