package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class ModuleListHud extends HudModule {
    public ModuleListHud() {
        super("ModuleList", "功能列表", 10, 10);
    }

    public enum ListMode {
        OldSakura,
        Sakura,
        Old
    }

    public enum FontMode {
        Default,
        Minecraft,
        Comfortaa,
        Ax,
        Geologica,
        Material,
        Tenacity
    }

    public enum NewStyleColorMode {
        Astolfo,
        Static,
        DoubleColor
    }

    public enum BarMode {
        Right,
        Outline
    }

    // --- 核心设置 (Core Settings) ---
    private final EnumValue<ListMode> mode = new EnumValue<>("Mode", "模式", ListMode.Sakura);
    private final EnumValue<FontMode> fontMode = new EnumValue<>("Font Mode", "字体模式", FontMode.Default, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));
    private final NumberValue<Double> hudScale = new NumberValue<>("Hud Scale", "HUD缩放", 1.1, 0.5, 2.0, 0.1);
    private final BoolValue alignRight = new BoolValue("Align Right", "右对齐", false);
    private final BoolValue hideHudModules = new BoolValue("Hide HudModules", "隐藏HUD模块", true);

    // --- 布局与限制 (Layout & Limits) ---
    private final NumberValue<Double> maxWidth = new NumberValue<>("Max Width", "最大宽度", 150.0, 50.0, 300.0, 5.0);
    private final NumberValue<Double> maxHeight = new NumberValue<>("Max Height", "最大高度", 5000.0, 50.0, 10000.0, 10.0);
    private final NumberValue<Double> itemSpacing = new NumberValue<>("Item Spacing", "项目间距", 7.0, 0.0, 10.0, 0.5);
    private final NumberValue<Integer> suffixStyle = new NumberValue<>("Suffix Style", "后缀符号", 0, 0, 3, 1);

    // --- 动画控制 (Animation Control) ---
    private final NumberValue<Double> animationSpeed = new NumberValue<>("Animation Speed", "动画速度", 0.2, 0.05, 0.5, 0.05);
    private final NumberValue<Double> sliderSpeed = new NumberValue<>("Slider Speed", "滑动速度", 0.2, 0.01, 1.0, 0.01);

    // --- 普通模式设置 (Normal Mode) ---
    private final BoolValue normalRainbowColor = new BoolValue("Normal Rainbow", "彩虹色", false, () -> mode.is(ListMode.OldSakura));
    private final BoolValue normalShowCategory = new BoolValue("Normal Show Category", "显示分类", true, () -> mode.is(ListMode.OldSakura));
    private final NumberValue<Double> normalRadius = new NumberValue<>("Normal Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0, () -> mode.is(ListMode.OldSakura));

    // --- 新风格设置 (New Style) ---
    private final EnumValue<NewStyleColorMode> newStyleColorMode = new EnumValue<>("Color Mode", "颜色模式", NewStyleColorMode.Astolfo, () -> mode.is(ListMode.Old));
    private final ColorValue newStyleStaticColor = new ColorValue("Static Color", "静态颜色", new Color(255, 255, 255), () -> mode.is(ListMode.Old) && newStyleColorMode.is(NewStyleColorMode.Static));
    private final ColorValue newStyleColor1 = new ColorValue("Gradient Color 1", "渐变色 1", new Color(0, 255, 255), () -> mode.is(ListMode.Old) && newStyleColorMode.is(NewStyleColorMode.DoubleColor));
    private final ColorValue newStyleColor2 = new ColorValue("Gradient Color 2", "渐变色 2", new Color(255, 0, 255), () -> mode.is(ListMode.Old) && newStyleColorMode.is(NewStyleColorMode.DoubleColor));
    private final BoolValue newStyleBackground = new BoolValue("New Background", "背景", true, () -> mode.is(ListMode.Old));
    private final ColorValue newStyleBackgroundColor = new ColorValue("New Bg Color", "背景颜色", new Color(0, 0, 0, 180), () -> mode.is(ListMode.Old) && newStyleBackground.get());
    private final NumberValue<Double> newStyleBackgroundPadX = new NumberValue<>("Bg Pad X", "背景 X 边距", 4.0, -10.0, 20.0, 0.5, () -> mode.is(ListMode.Old) && newStyleBackground.get());
    private final NumberValue<Double> newStyleBackgroundPadY = new NumberValue<>("Bg Pad Y", "背景 Y 边距", 2.0, -10.0, 20.0, 0.5, () -> mode.is(ListMode.Old) && newStyleBackground.get());
    private final BoolValue newStyleBar = new BoolValue("Deco Bar", "新风格 - 装饰条", true, () -> mode.is(ListMode.Old));
    private final EnumValue<BarMode> newStyleBarMode = new EnumValue<>("Bar Mode", "装饰条模式", BarMode.Right, () -> mode.is(ListMode.Old) && newStyleBar.get());
    private final NumberValue<Double> newStyleBarWidth = new NumberValue<>("Bar Width", "装饰条宽度", 2.0, 1.0, 5.0, 0.5, () -> mode.is(ListMode.Old) && newStyleBar.get());
    private final NumberValue<Double> newStyleX = new NumberValue<>("Pos X", "X 轴距离", 0.0, 0.0, 1000.0, 1.0, () -> mode.is(ListMode.Old));
    private final NumberValue<Double> newStyleY = new NumberValue<>("Pos Y", "Y 轴距离", 0.0, -100.0, 1000.0, 1.0, () -> mode.is(ListMode.Old));

    // 1. 文本与字体 (Text & Font)
    private final NumberValue<Double> customFontSize = new NumberValue<>("Font Size", "字体大小", 10.0, 5.0, 30.0, 0.5, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));
    private final NumberValue<Double> textOffsetX = new NumberValue<>("Text Offset X", "文字 X 偏移", 0.0, -10.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));
    private final NumberValue<Double> textOffsetY = new NumberValue<>("Text Offset Y", "文字 Y 偏移", 1.0, -10.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));
    private final BoolValue textGlow = new BoolValue("Text Glow", "文本发光", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> glowRadius = new NumberValue<>("Glow Radius", "发光半径", 3.0, 1.0, 10.0, 0.5, () -> mode.is(ListMode.Sakura) && textGlow.get());
    private final NumberValue<Integer> glowIntensity = new NumberValue<>("Glow Intensity", "发光强度", 2, 1, 10, 1, () -> mode.is(ListMode.Sakura) && textGlow.get());

    // 字体阴影 (Font Shadow) - Sakura 模式同款
    private final BoolValue textShadow = new BoolValue("Text Shadow", "文本阴影", true, () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> textShadowDistance = new NumberValue<>("TextShadowDist", "阴影间距", 0.9, 0.0, 5.0, 0.1, () -> mode.is(ListMode.Sakura) && textShadow.get());
    private final NumberValue<Double> textShadowOffsetY = new NumberValue<>("ShadowOffsetY", "阴影Y偏移", -3.3, -10.0, 10.0, 0.1, () -> mode.is(ListMode.Sakura) && textShadow.get());

    // 2. 渐变颜色 (Colors)
    private final BoolValue autoColor = new BoolValue("Auto Color", "自动调色", false, () -> mode.is(ListMode.Sakura));
    private final ColorValue gradientColor1 = new ColorValue("Color 1", "颜色 1", new Color(0, 255, 255), () -> mode.is(ListMode.Sakura));
    private final ColorValue gradientColor2 = new ColorValue("Color 2", "颜色 2", new Color(255, 0, 255), () -> mode.is(ListMode.Sakura));
    private final NumberValue<Double> gradientSpeed = new NumberValue<>("Gradient Speed", "渐变速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));
    private final NumberValue<Double> colorStep = new NumberValue<>("Color Step", "颜色跨度", 15.0, 1.0, 100.0, 1.0, () -> mode.is(ListMode.Sakura) || mode.is(ListMode.Old));

    // 3. 背景设置 (Background)
    private final BoolValue background = new BoolValue("Background", "背景", false, () -> mode.is(ListMode.Sakura));

    public enum BackgroundMode {Normal, Blur}

    private final EnumValue<BackgroundMode> backgroundMode = new EnumValue<>("Background Mode", "背景模式", BackgroundMode.Normal, () -> mode.is(ListMode.Sakura) && background.get());
    private final ColorValue backgroundColor = new ColorValue("Background Color", "背景颜色", new Color(0, 0, 0, 100), () -> mode.is(ListMode.Sakura) && background.get());
    private final NumberValue<Double> backgroundRadius = new NumberValue<>("Background Radius", "背景圆角", 0.0, 0.0, 10.0, 1.0, () -> (mode.is(ListMode.Sakura) && background.get()) || (mode.is(ListMode.Old) && newStyleBackground.get()));
    private final NumberValue<Double> backgroundOffsetY = new NumberValue<>("Background Offset Y", "背景 Y 偏移", -3.0, -10.0, 10.0, 0.5, () -> (mode.is(ListMode.Sakura) && background.get()) || (mode.is(ListMode.Old) && newStyleBackground.get()));
    private final NumberValue<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 10.0, 0.0, 50.0, 1.0, () -> mode.is(ListMode.Sakura) && background.get() && backgroundMode.is(BackgroundMode.Blur));

    private final BoolValue backgroundShadow = new BoolValue("Background Shadow", "背景阴影", false, () -> mode.is(ListMode.Sakura) && background.get());
    private final NumberValue<Double> shadowRange = new NumberValue<>("Shadow Range", "阴影范围", 8.0, 0.0, 30.0, 1.0, () -> mode.is(ListMode.Sakura) && background.get() && backgroundShadow.get());
    private final NumberValue<Double> shadowStrength = new NumberValue<>("Shadow Strength", "阴影强度", 0.6, 0.0, 1.0, 0.05, () -> mode.is(ListMode.Sakura) && background.get() && backgroundShadow.get());

    public enum ShadowMode {Solid, Gradient}

    private final EnumValue<ShadowMode> shadowMode = new EnumValue<>("Shadow Mode", "阴影模式", ShadowMode.Solid, () -> mode.is(ListMode.Sakura) && background.get() && backgroundShadow.get());

    // 4. 线条设置 (Lines)
    private final BoolValue showGradientLine = new BoolValue("Show Line", "显示线条", false, () -> mode.is(ListMode.Sakura));

    public enum LineMode {Left, Box}

    private final EnumValue<LineMode> lineMode = new EnumValue<>("Line Mode", "线条模式", LineMode.Left, () -> mode.is(ListMode.Sakura) && showGradientLine.get());
    private final NumberValue<Double> lineWidth = new NumberValue<>("Line Width", "线条宽度", 2.0, 1.0, 5.0, 0.5, () -> mode.is(ListMode.Sakura) && showGradientLine.get());

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
    private static final int MAX_SEGMENTS = 64;

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

    private long frameTimeMs = 0L;
    private boolean frameGradientReady = false;
    private Color frameGradientC1 = Color.WHITE;
    private Color frameGradientC2 = Color.WHITE;
    private double frameGradientOffset = 0.0;

    private long frameSegmentsTimeMs = Long.MIN_VALUE;
    private float frameSegmentsScale = Float.NaN;
    private float frameSegmentsFontSize = Float.NaN;
    private float frameSegmentsStartY = Float.NaN;
    private final List<BackgroundSegment> frameSegments = new ArrayList<>();

    private final float[] tmpSegmentRects = new float[MAX_SEGMENTS * 4];
    private final float[] tmpSegmentRadii = new float[MAX_SEGMENTS];

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
        frameTimeMs = System.currentTimeMillis();
        frameGradientReady = false;
        frameSegmentsTimeMs = Long.MIN_VALUE;
        update();
        if (!mode.is(ListMode.Old)) {
            ensureWithinScreenBounds();
        }

        if (mode.is(ListMode.Sakura) && background.get() && backgroundShadow.get()) {
            renderBackgroundShadow();
        }

        if (mode.is(ListMode.Sakura) && background.get() && backgroundMode.is(BackgroundMode.Blur)) {
            renderBlurBackgrounds();
        }

        if (mode.is(ListMode.Old) && newStyleBackground.get() && backgroundMode.is(BackgroundMode.Blur)) {
            renderNewStyleBlur();
        }

        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());

        if (mode.is(ListMode.Sakura) && fontMode.is(FontMode.Minecraft)) {
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
        // updateModulePositions(); // Moved down
        float speed = animationSpeed.get().floatValue();
        currentWidth += (targetWidth - currentWidth) * speed;
        currentHeight += (targetHeight - currentHeight) * speed;

        if (mode.is(ListMode.Old)) {
            float scale = hudScale.get().floatValue();
            int screenWidth = mc.getWindow().getScaledWidth();

            float xCorrection = (PADDING_X - 4) * scale;
            this.x = screenWidth - (currentWidth * scale) - newStyleX.get().floatValue() + xCorrection;

            float topOffset = (PADDING_Y + backgroundOffsetY.get().floatValue()) * scale;
            this.y = newStyleY.get().floatValue() - topOffset - (2.0f * scale);
        } else if (alignRight.get()) {
            float scale = hudScale.get().floatValue();
            x -= (currentWidth - oldWidth) * scale;
        }

        updateModulePositions();

        this.width = currentWidth * hudScale.get().floatValue();
        this.height = currentHeight * hudScale.get().floatValue();
        updateScroll();
    }

    private void updateModulePositions() {
        float scale = hudScale.get().floatValue();
        float fontSize = (mode.is(ListMode.Sakura) || mode.is(ListMode.Old)) ? customFontSize.get().floatValue() : 10f;
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
        if (mode.is(ListMode.Sakura) || mode.is(ListMode.Old)) {
            return switch (fontMode.get()) {
                case Comfortaa -> FontLoader.comfortaa();
                case Ax -> FontLoader.ax();
                case Geologica -> FontLoader.geologica();
                case Material -> FontLoader.material();
                case Tenacity -> FontLoader.tenacity();
                default -> FontLoader.medium();
            };
        }
        return FontLoader.medium();
    }

    private float getModuleTextWidth(String text) {
        float scale = hudScale.get().floatValue();
        if ((mode.is(ListMode.Sakura) || mode.is(ListMode.Old)) && fontMode.is(FontMode.Minecraft)) {
            float fontSize = customFontSize.get().floatValue();
            return mc.textRenderer.getWidth(text) * (fontSize / 9.0f) * scale;
        }
        float fontSize = (mode.is(ListMode.Sakura) || mode.is(ListMode.Old)) ? customFontSize.get().floatValue() : 10f;
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
        boolean isGradient = mode.is(ListMode.Sakura);
        boolean isNewStyle = mode.is(ListMode.Old);
        double spacing = itemSpacing.get();
        boolean showCat = !isGradient && !isNewStyle && normalShowCategory.get();

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
        float fontSize = (isGradient || isNewStyle) ? customFontSize.get().floatValue() : 10f;
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
        targetHeight = totalHeight;
    }

    private void updateScroll() {
        scrollOffset = 0;
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

        updateAnchors(screenWidth, screenHeight, scaledWidth, scaledHeight);
        lastScaledScreenWidth = screenWidth;
        lastScaledScreenHeight = screenHeight;
    }

    private void renderContent() {
        if (mode.is(ListMode.Old)) {
            renderNewStyle();
            return;
        }

        if (mode.is(ListMode.Sakura)) {
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

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getDisplayName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);
            float moduleNameWidth = NanoVGHelper.getTextWidth(moduleName, font, 10 * scale);
            float suffixWidth = NanoVGHelper.getTextWidth(formattedSuffix, font, 10 * scale);
            float textHeight = NanoVGHelper.getFontHeight(font, 10 * scale);
            float v = suffix.isEmpty() ? 0 : suffixWidth + (2 * scale);
            float totalTextWidth = moduleNameWidth + v;
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
                textX = iconBgX - (CATEGORY_ICON_SPACING * scale) - moduleNameWidth - v;
            } else if (!alignRight.get() && normalShowCategory.get()) {
                iconBgX = itemX + (6 * scale);
                textX = iconBgX + (ICON_BACKGROUND_WIDTH * scale) + (CATEGORY_ICON_SPACING * scale);
            } else {
                textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - v : itemX + (PADDING_X * scale);
            }

            float textY = renderY + textHeight / 2 + (2 * scale);

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
                    renderY - (3 * scale),
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
                        renderY - (3 * scale),
                        ICON_BACKGROUND_WIDTH * scale,
                        ICON_BACKGROUND_HEIGHT * scale,
                        normalRadius.get().floatValue() * scale,
                        animatedBackgroundColor
                );
                float iconY = renderY + ((ICON_BACKGROUND_HEIGHT * scale) - iconHeight) / 2;
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

    private void ensureFrameGradient() {
        if (frameGradientReady) {
            return;
        }
        Color c1 = gradientColor1.get();
        Color c2 = gradientColor2.get();
        if (autoColor.get()) {
            double speed = gradientSpeed.get();
            float hue = (float) ((frameTimeMs * speed / 5000.0) % 1.0);
            c1 = Color.getHSBColor(hue, 0.7f, 1.0f);
            c2 = Color.getHSBColor((hue + 0.5f) % 1.0f, 0.7f, 1.0f);
        }
        frameGradientC1 = c1;
        frameGradientC2 = c2;
        frameGradientOffset = (frameTimeMs * gradientSpeed.get()) / 50.0;
        frameGradientReady = true;
    }

    private void renderBlurBackgrounds() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float startY = y + (PADDING_Y * scale) - (scrollOffset * scale);
        List<BackgroundSegment> segments = buildGradientBackgroundSegments(scale, fontSize, startY);
        if (segments.isEmpty()) {
            return;
        }

        float r = backgroundRadius.get().floatValue() * scale;
        float overlap = Math.max(0.5f, 0.5f * scale);
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (BackgroundSegment segment : segments) {
            float segX = segment.x - overlap;
            float segY = segment.y - overlap;
            float segW = segment.w + overlap * 2.0f;
            float segH = segment.h + overlap * 2.0f;
            minX = Math.min(minX, segX);
            minY = Math.min(minY, segY);
            maxX = Math.max(maxX, segX + segW);
            maxY = Math.max(maxY, segY + segH);
        }

        float blurX = minX;
        float blurY = minY;
        float blurW = Math.max(0.0f, maxX - minX);
        float blurH = Math.max(0.0f, maxY - minY);

        if (blurW <= 0.0f || blurH <= 0.0f) {
            return;
        }

        int count = Math.min(MAX_SEGMENTS, segments.size());
        for (int i = 0; i < count; i++) {
            BackgroundSegment segment = segments.get(i);
            int base = i * 4;
            tmpSegmentRects[base] = segment.x - overlap;
            tmpSegmentRects[base + 1] = segment.y - overlap;
            tmpSegmentRects[base + 2] = segment.w + overlap * 2.0f;
            tmpSegmentRects[base + 3] = segment.h + overlap * 2.0f;
            tmpSegmentRadii[i] = 0.0f;
        }
        tmpSegmentRadii[0] = r;
        tmpSegmentRadii[count - 1] = r;

        BlurShader.drawSegmentedBlur(blurX, blurY, blurW, blurH, 0.0f, new Color(0, 0, 0, 0), blurStrength.get().floatValue(), 1.0f, tmpSegmentRects, tmpSegmentRadii, count);
    }

    private void renderBackgroundShadow() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float startY = y + (PADDING_Y * scale) - (scrollOffset * scale);
        List<BackgroundSegment> segments = buildGradientBackgroundSegments(scale, fontSize, startY);
        if (segments.isEmpty()) {
            return;
        }

        float overlap = Math.max(0.5f, 0.5f * scale);
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (BackgroundSegment segment : segments) {
            minX = Math.min(minX, segment.x);
            minY = Math.min(minY, segment.y - overlap);
            maxX = Math.max(maxX, segment.x + segment.w);
            maxY = Math.max(maxY, segment.y + segment.h + overlap);
        }

        float shadowX = minX;
        float shadowY = minY;
        float shadowW = Math.max(0.0f, maxX - minX);
        float shadowH = Math.max(0.0f, maxY - minY);
        if (shadowW <= 0.0f || shadowH <= 0.0f) {
            return;
        }

        int count = Math.min(MAX_SEGMENTS, segments.size());
        for (int i = 0; i < count; i++) {
            BackgroundSegment segment = segments.get(i);
            int base = i * 4;
            tmpSegmentRects[base] = segment.x;
            tmpSegmentRects[base + 1] = segment.y - overlap;
            tmpSegmentRects[base + 2] = segment.w;
            tmpSegmentRects[base + 3] = segment.h + overlap * 2.0f;
            tmpSegmentRadii[i] = 0.0f;
        }
        float r = backgroundRadius.get().floatValue() * scale;
        tmpSegmentRadii[0] = r;
        tmpSegmentRadii[count - 1] = r;

        if (shadowMode.is(ShadowMode.Gradient)) {
            ensureFrameGradient();
            Color c1 = frameGradientC1;
            Color c2 = frameGradientC2;
            double offset = frameGradientOffset;
            int lastIndex = Math.max(0, count - 1);
            double bottomOffset = offset + (lastIndex * colorStep.get());
            double topFactor = (Math.sin(Math.toRadians(offset)) + 1) / 2;
            double bottomFactor = (Math.sin(Math.toRadians(bottomOffset)) + 1) / 2;

            Color start = interpolateColor(c1, c2, (float) topFactor);
            Color end = interpolateColor(c1, c2, (float) bottomFactor);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), 255);
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), 255);

            ShadowShader.drawStairShadowGradient(shadowX, shadowY, shadowW, shadowH, shadowRange.get().floatValue(), shadowStrength.get().floatValue(), start, end, tmpSegmentRects, tmpSegmentRadii, count);
            return;
        }

        ShadowShader.drawStairShadow(shadowX, shadowY, shadowW, shadowH, shadowRange.get().floatValue(), shadowStrength.get().floatValue(), new Color(0, 0, 0), tmpSegmentRects, tmpSegmentRadii, count);
    }

    private void renderGradientContent() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        int font = getFontId();
        float startY = currentY;
        List<BackgroundSegment> backgroundSegments = buildGradientBackgroundSegments(scale, fontSize, startY);
        if (background.get() && !backgroundSegments.isEmpty()) {
            drawMergedGradientBackground(backgroundSegments, scale);
            if (showGradientLine.get()) {
                drawGradientLines(backgroundSegments, scale);
            }
        }

        int index = 0;
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

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getDisplayName();
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

            ensureFrameGradient();
            Color c1 = frameGradientC1;
            Color c2 = frameGradientC2;
            double offset = frameGradientOffset;
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
                float y1 = textY - (fontSize * scale / 2) + (1 * scale);

                // 绘制文本阴影（Sakura 模式同款）
                if (textShadow.get()) {
                    // 计算渐变色的平均颜色并变暗作为阴影
                    Color avgColor = new Color((c1.getRed() + c2.getRed()) / 2, (c1.getGreen() + c2.getGreen()) / 2, (c1.getBlue() + c2.getBlue()) / 2);
                    Color coloredShadow = new Color((int) (avgColor.getRed() * 0.5), (int) (avgColor.getGreen() * 0.5), (int) (avgColor.getBlue() * 0.5), (int) (255 * animationValue));

                    float dist = textShadowDistance.get().floatValue();
                    float shadowYOffset = textShadowOffsetY.get().floatValue();
                    NanoVGHelper.drawString(moduleName, animatedTextX + dist, textY + dist + shadowYOffset, font, fontSize * scale, coloredShadow);
                }

                // 绘制文本（带发光效果或普通）
                if (textGlow.get()) {
                    NanoVGHelper.drawGlowingString(moduleName, animatedTextX, y1, font, fontSize * scale, animatedTextColor, glowRadius.get().floatValue() * scale, glowIntensity.get());
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

                    // 绘制后缀阴影
                    if (textShadow.get()) {
                        Color avgColorSuffix = new Color((c1.getRed() + c2.getRed()) / 2, (c1.getGreen() + c2.getGreen()) / 2, (c1.getBlue() + c2.getBlue()) / 2);
                        Color coloredShadowSuffix = new Color((int) (avgColorSuffix.getRed() * 0.5), (int) (avgColorSuffix.getGreen() * 0.5), (int) (avgColorSuffix.getBlue() * 0.5), (int) (SUFFIX_COLOR.getAlpha() * animationValue));

                        float dist = textShadowDistance.get().floatValue();
                        float shadowYOffset = textShadowOffsetY.get().floatValue();
                        NanoVGHelper.drawString(formattedSuffix, suffixX + dist, textY + dist + shadowYOffset, font, fontSize * scale, coloredShadowSuffix);
                    }

                    // 绘制后缀文本
                    if (textGlow.get()) {
                        NanoVGHelper.drawGlowingString(formattedSuffix, suffixX, y1, font, fontSize * scale, animatedSuffixColor, glowRadius.get().floatValue() * scale, glowIntensity.get());
                    } else {
                        NanoVGHelper.drawString(formattedSuffix, suffixX, textY, font, fontSize * scale, animatedSuffixColor);
                    }
                }
            }
        }
    }

    private List<BackgroundSegment> buildGradientBackgroundSegments(float scale, float fontSize, float startY) {
        if (frameSegmentsTimeMs == frameTimeMs
                && Float.compare(frameSegmentsScale, scale) == 0
                && Float.compare(frameSegmentsFontSize, fontSize) == 0
                && Float.compare(frameSegmentsStartY, startY) == 0) {
            return frameSegments;
        }

        frameSegmentsTimeMs = frameTimeMs;
        frameSegmentsScale = scale;
        frameSegmentsFontSize = fontSize;
        frameSegmentsStartY = startY;

        float currentBgY = startY;
        frameSegments.clear();
        int index = 0;

        boolean forceRight = mode.is(ListMode.Old);

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;
            if (animationValue <= 0.01) continue;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentBgY);

            String moduleName = entry.module.getDisplayName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);
            float moduleNameWidth = getModuleTextWidth(moduleName);

            // Sync gap logic with renderNewStyle
            float spaceWidth = getModuleTextWidth(" ");
            float gap = spaceWidth;

            float itemWidth = moduleNameWidth + (suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + gap) + (PADDING_X * 2 * scale);

            float bgWidth = itemWidth - (PADDING_X * 2 * scale) + (8 * scale);
            float itemBgX = (alignRight.get() || forceRight)
                    ? (x + (currentWidth * scale) - itemWidth + (PADDING_X * scale))
                    : (x + (PADDING_X * scale));

            float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
            float bgOffset = backgroundOffsetY.get().floatValue() * scale;
            float bgY = renderY + bgOffset - heightAdjustment;
            float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;

            frameSegments.add(new BackgroundSegment(index, itemBgX - (4 * scale), bgY, bgWidth, bgH, (float) animationValue));

            currentBgY += (float) (itemFullHeight * animationValue);
            index++;
        }

        return frameSegments;
    }

    private void drawMergedGradientBackground(List<BackgroundSegment> segments, float scale) {
        float r = backgroundRadius.get().floatValue() * scale;
        boolean line = showGradientLine.get() && !lineMode.is(LineMode.Box);

        long vg = NanoVGRenderer.INSTANCE.getContext();
        NVGColor nvgColor = NanoVGHelper.nvgColor(backgroundColor.get());

        nvgBeginPath(vg);
        buildMergedBackgroundPath(vg, segments, r, line);
        nvgFillColor(vg, nvgColor);
        nvgFill(vg);
    }

    private void buildMergedBackgroundPath(long vg, List<BackgroundSegment> segments, float r, boolean line) {
        int lastIndex = segments.size() - 1;
        boolean isNewStyle = mode.is(ListMode.Old);

        for (BackgroundSegment segment : segments) {
            boolean isFirst = segment.index == 0;
            boolean isLast = segment.index == lastIndex;

            float rTopLeft = isFirst ? r : 0;
            float rTopRight = (isFirst && !line) ? r : 0;
            float rBottomRight = (isLast && !line) ? r : 0;
            float rBottomLeft = isLast ? r : 0;

            if (isNewStyle) {
            }

            nvgRoundedRectVarying(vg, segment.x, segment.y, segment.w, segment.h, rTopLeft, rTopRight, rBottomRight, rBottomLeft);
        }
    }

    private void drawGradientLines(List<BackgroundSegment> segments, float scale) {
        ensureFrameGradient();
        Color c1 = frameGradientC1;
        Color c2 = frameGradientC2;

        float r = backgroundRadius.get().floatValue() * scale;
        float lineW = lineWidth.get().floatValue() * scale;

        double offset = frameGradientOffset;
        if (lineMode.is(LineMode.Box)) {
            long vg = NanoVGRenderer.INSTANCE.getContext();
            int lastIndex = segments.size() - 1;

            float topY = segments.getFirst().y;
            float bottomY = topY;
            float alphaFactor = 0f;
            for (BackgroundSegment segment : segments) {
                bottomY = Math.max(bottomY, segment.y + segment.h);
                alphaFactor = Math.max(alphaFactor, segment.alphaFactor);
            }

            double topOffset = offset;
            double bottomOffset = offset + (lastIndex * colorStep.get());
            double topFactor = (Math.sin(Math.toRadians(topOffset)) + 1) / 2;
            double bottomFactor = (Math.sin(Math.toRadians(bottomOffset)) + 1) / 2;

            Color start = interpolateColor(c1, c2, (float) topFactor);
            Color end = interpolateColor(c1, c2, (float) bottomFactor);
            start = new Color(start.getRed(), start.getGreen(), start.getBlue(), (int) (start.getAlpha() * alphaFactor));
            end = new Color(end.getRed(), end.getGreen(), end.getBlue(), (int) (end.getAlpha() * alphaFactor));

            if (Math.abs(bottomY - topY) < 1e-3f) {
                bottomY = topY + 1f;
            }

            NVGPaint paint = NVGPaint.create();
            NVGColor nvgStart = NanoVGHelper.nvgColor(start);
            NVGColor nvgEnd = NanoVGHelper.nvgColor(end);
            nvgLinearGradient(vg, 0, topY, 0, bottomY, nvgStart, nvgEnd, paint);

            nvgBeginPath(vg);
            buildStaircaseOutlinePath(vg, segments);
            nvgLineJoin(vg, NVG_ROUND);
            nvgStrokeWidth(vg, lineW);
            nvgStrokePaint(vg, paint);
            nvgStroke(vg);
            return;
        }

        int lastIndex = segments.size() - 1;
        for (BackgroundSegment segment : segments) {
            double currentOffset = offset + (segment.index * colorStep.get());
            double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;

            Color lineColor = interpolateColor(c1, c2, (float) factor);
            lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * segment.alphaFactor));

            boolean isFirst = segment.index == 0;
            boolean isLast = segment.index == lastIndex;

            float rTopLeft = isFirst ? r : 0;
            float rTopRight = 0;
            float rBottomRight = 0;
            float rBottomLeft = isLast ? r : 0;

            float lineX = alignRight.get() ? (segment.x + segment.w - lineW) : segment.x;
            NanoVGHelper.drawCustomRoundRect(lineX, segment.y, lineW, segment.h, rTopLeft, rTopRight, rBottomRight, rBottomLeft, lineColor);
        }
    }

    private void buildStaircaseOutlinePath(long vg, List<BackgroundSegment> segments) {
        if (segments.isEmpty()) {
            return;
        }

        BackgroundSegment first = segments.getFirst();
        BackgroundSegment last = segments.getLast();

        float topY = first.y;
        float bottomY = last.y + last.h;

        if (!alignRight.get()) {
            float leftX = first.x;
            float currentRight = first.x + first.w;

            nvgMoveTo(vg, leftX, topY);
            nvgLineTo(vg, currentRight, topY);

            for (int i = 0; i < segments.size(); i++) {
                BackgroundSegment seg = segments.get(i);
                float right = seg.x + seg.w;
                float yBottom = seg.y + seg.h;

                nvgLineTo(vg, right, yBottom);
                if (i + 1 < segments.size()) {
                    BackgroundSegment next = segments.get(i + 1);
                    float nextRight = next.x + next.w;
                    nvgLineTo(vg, nextRight, yBottom);
                }
            }

            nvgLineTo(vg, leftX, bottomY);
            nvgClosePath(vg);
            return;
        }

        float rightX = first.x + first.w;
        float currentLeft = first.x;

        nvgMoveTo(vg, rightX, topY);
        nvgLineTo(vg, currentLeft, topY);

        for (int i = 0; i < segments.size(); i++) {
            BackgroundSegment seg = segments.get(i);
            float left = seg.x;
            float yBottom = seg.y + seg.h;

            nvgLineTo(vg, left, yBottom);
            if (i + 1 < segments.size()) {
                BackgroundSegment next = segments.get(i + 1);
                float nextLeft = next.x;
                nvgLineTo(vg, nextLeft, yBottom);
            }
        }

        nvgLineTo(vg, rightX, bottomY);
        nvgClosePath(vg);
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

            if (animationValue < 0.01) {
                continue;
            }

            String moduleName = entry.module.getDisplayName();
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

            ensureFrameGradient();
            Color c1 = frameGradientC1;
            Color c2 = frameGradientC2;
            double offset = frameGradientOffset;
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

    private record BackgroundSegment(int index, float x, float y, float w, float h, float alphaFactor) {
    }

    private void renderNewStyleBlur() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();
        float currentY = y;
        List<BackgroundSegment> segments = new ArrayList<>();
        int segmentIndex = 0;

        float padX = newStyleBackgroundPadX.get().floatValue() * scale;
        float padY = newStyleBackgroundPadY.get().floatValue() * scale;
        float bgOffsetY = backgroundOffsetY.get().floatValue() * scale;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;
            if (animationValue < 0.01) {
                continue;
            }

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentY);

            String moduleName = entry.module.getDisplayName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);

            float gap = 3f * scale;
            float textWidth = getModuleTextWidth(moduleName);
            if (!suffix.isEmpty()) {
                textWidth += getModuleTextWidth(formattedSuffix) + gap;
            }

            float textLeft = x + (currentWidth * scale) - (PADDING_X * scale) - textWidth;
            float bgX = textLeft - padX;
            float bgY = renderY + bgOffsetY - padY;
            float bgW = textWidth + (padX * 2.0f);
            float bgH = (itemFullHeight * (float) animationValue) + (padY * 2.0f);

            segments.add(new BackgroundSegment(segmentIndex, bgX, bgY, bgW, bgH, (float) animationValue));

            currentY += itemFullHeight * (float) animationValue;
            segmentIndex++;
        }

        if (segments.isEmpty()) {
            return;
        }

        float overlap = Math.max(0.5f, 0.5f * scale);
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;

        for (BackgroundSegment segment : segments) {
            minX = Math.min(minX, segment.x - overlap);
            minY = Math.min(minY, segment.y - overlap);
            maxX = Math.max(maxX, segment.x + segment.w + overlap);
            maxY = Math.max(maxY, segment.y + segment.h + overlap);
        }

        float blurW = Math.max(0.0f, maxX - minX);
        float blurH = Math.max(0.0f, maxY - minY);
        if (blurW <= 0.0f || blurH <= 0.0f) {
            return;
        }

        int count = Math.min(MAX_SEGMENTS, segments.size());
        for (int i = 0; i < count; i++) {
            BackgroundSegment segment = segments.get(i);
            int base = i * 4;
            tmpSegmentRects[base] = segment.x - overlap;
            tmpSegmentRects[base + 1] = segment.y - overlap;
            tmpSegmentRects[base + 2] = segment.w + overlap * 2.0f;
            tmpSegmentRects[base + 3] = segment.h + overlap * 2.0f;
            tmpSegmentRadii[i] = 0.0f;
        }
        float r = backgroundRadius.get().floatValue() * scale;
        if (count > 0) {
            tmpSegmentRadii[0] = r;
            tmpSegmentRadii[count - 1] = r;
        }

        Color tint = newStyleBackgroundColor.get();
        float tintAlpha = tint.getAlpha() / 255.0f;
        BlurShader.drawSegmentedBlur(minX, minY, blurW, blurH, 0.0f, tint, blurStrength.get().floatValue(), tintAlpha, tmpSegmentRects, tmpSegmentRadii, count);
    }

    private void renderNewStyle() {
        float scale = hudScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();
        int font = getFontId();
        float currentY = y;
        int index = 0;

        List<BackgroundSegment> newStyleSegments = new ArrayList<>();
        float segmentsY = currentY;
        int segmentIndex = 0;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;
            if (animationValue < 0.01) continue;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, segmentsY);

            String moduleName = entry.module.getDisplayName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);

            float nameWidth = getModuleTextWidth(moduleName);
            float gap = 3f * scale;
            float padding = PADDING_X * scale;

            float suffixW = suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + gap;
            float contentWidth = nameWidth + suffixW;
            float itemWidth = contentWidth + padding * 2;

            float drawX = x + (currentWidth * scale) - itemWidth;

            float bgW = contentWidth + (8 * scale);
            float bgX = drawX + padding - (4 * scale);

            float heightAdjustment = itemSpacing.get() == 0 ? 0.5f * scale : 0;
            float bgOffset = backgroundOffsetY.get().floatValue() * scale;
            float bgY = renderY + bgOffset - heightAdjustment;
            float bgH = (itemFullHeight * (float) animationValue) + heightAdjustment;

            newStyleSegments.add(new BackgroundSegment(segmentIndex, bgX, bgY, bgW, bgH, (float) animationValue));

            segmentsY += itemFullHeight * (float) animationValue;
            segmentIndex++;
        }

        if (newStyleBar.get() && !newStyleSegments.isEmpty()) {
            float barWidth = newStyleBarWidth.get().floatValue() * scale;
            for (int i = 0; i < newStyleSegments.size(); i++) {
                BackgroundSegment segment = newStyleSegments.get(i);
                Color color = getNewStyleColor(i, segment.alphaFactor);

                if (newStyleBarMode.is(BarMode.Right)) {
                    float bgRight = segment.x + segment.w;
                    NanoVGHelper.drawRect(bgRight - barWidth, segment.y, barWidth, segment.h, color);
                    continue;
                }

                NanoVGHelper.drawRect(segment.x, segment.y, barWidth, segment.h, color);

                if (i < newStyleSegments.size() - 1) {
                    BackgroundSegment nextSegment = newStyleSegments.get(i + 1);
                    float currentLeft = segment.x;
                    float nextLeft = nextSegment.x;

                    if (nextLeft > currentLeft) {
                        NanoVGHelper.drawRect(currentLeft, segment.y + segment.h - barWidth, (nextLeft - currentLeft) + barWidth, barWidth, color);
                    } else {
                        NanoVGHelper.drawRect(nextLeft, segment.y + segment.h - barWidth, (currentLeft - nextLeft) + barWidth, barWidth, color);
                    }
                } else {
                    NanoVGHelper.drawRect(segment.x, segment.y + segment.h - barWidth, segment.w, barWidth, color);
                }
            }
        }

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue < 0.01) continue;

            float itemFullHeight = (fontSize + itemSpacing.get().floatValue()) * scale;
            float renderY = moduleYPositions.getOrDefault(entry.module, currentY);

            String moduleName = entry.module.getDisplayName();
            String suffix = entry.module.getSuffix();
            String formattedSuffix = getFormattedSuffix(suffix);

            float nameWidth = getModuleTextWidth(moduleName);
            float gap = 3f * scale;

            float suffixW = suffix.isEmpty() ? 0 : getModuleTextWidth(formattedSuffix) + gap;
            float contentWidth = nameWidth + suffixW;

            // Calculate Color
            Color color = getNewStyleColor(index, (float) animationValue);

            // Draw Text
            float textX = x + (currentWidth * scale) - (PADDING_X * scale) - contentWidth;

            float textXOffset = textOffsetX.get().floatValue() * scale;
            float textYOffset = textOffsetY.get().floatValue() * scale;

            float finalTextX = textX + textXOffset;
            float fontHeight = NanoVGHelper.getFontHeight(font, fontSize * scale);
            float finalTextY = renderY + fontHeight / 2 + (2 * scale) + textYOffset;

            NanoVGHelper.drawString(moduleName, finalTextX, finalTextY, font, fontSize * scale, color);

            if (!suffix.isEmpty()) {
                NanoVGHelper.drawString(formattedSuffix, finalTextX + nameWidth + gap, finalTextY, font, fontSize * scale, Color.GRAY);
            }

            currentY += itemFullHeight * (float) animationValue;
            index++;
        }
    }

    private Color getNewStyleColor(int index, float alphaFactor) {
        Color color = Color.WHITE;
        ensureFrameGradient();

        if (newStyleColorMode.is(NewStyleColorMode.Static)) {
            color = newStyleStaticColor.get();
        } else if (newStyleColorMode.is(NewStyleColorMode.Astolfo)) {
            float hue = (System.currentTimeMillis() % 3000) / 3000f;
            hue += (index * 0.05f);
            if (hue > 1) hue -= 1;
            color = Color.getHSBColor(hue, 0.6f, 1.0f);
        } else if (newStyleColorMode.is(NewStyleColorMode.DoubleColor)) {
            Color c1 = newStyleColor1.get();
            Color c2 = newStyleColor2.get();
            double offset = frameGradientOffset;
            double currentOffset = offset + (index * colorStep.get());
            double factor = (Math.sin(Math.toRadians(currentOffset)) + 1) / 2;
            color = interpolateColor(c1, c2, (float) factor);
        }

        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * alphaFactor));
    }

    private record ModuleEntry(Module module) {
    }
}
