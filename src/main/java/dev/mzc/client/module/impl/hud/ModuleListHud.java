package dev.mzc.client.module.impl.hud;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.module.impl.client.HudEditor;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.render.Shader2DUtil;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class ModuleListHud extends HudModule {
    public enum Style {
        Sakura("樱花"),
        Vape("Vape"),
        LiquidGlass("液态玻璃"),
        Gradient("渐变");

        private final String cnName;

        Style(String cnName) {
            this.cnName = cnName;
        }
    }
    public enum ColorMode {
        Client("客户端"),
        Rainbow("彩虹"),
        Double("双色"),
        Single("单色");

        private final String cnName;

        ColorMode(String cnName) {
            this.cnName = cnName;
        }
    }
    private final EnumValue<Style> style = new EnumValue<>("Style", "风格", Style.Sakura);

    // Sakura Settings
    private final EnumValue<ColorMode> sakuraColorMode = new EnumValue<>("SakuraColorMode", "颜色模式", ColorMode.Client, () -> style.get() == Style.Sakura);
    private final ColorValue sakuraColor1 = new ColorValue("SakuraColor1", "自定义颜色1", new Color(255, 255, 255), () -> style.get() == Style.Sakura && (sakuraColorMode.get() == ColorMode.Single || sakuraColorMode.get() == ColorMode.Double));
    private final ColorValue sakuraColor2 = new ColorValue("SakuraColor2", "自定义颜色2", new Color(255, 0, 0), () -> style.get() == Style.Sakura && sakuraColorMode.get() == ColorMode.Double);
    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true, () -> style.get() == Style.Sakura);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0, () -> style.get() == Style.Sakura);
    private final NumberValue<Double> animationSpeed = new NumberValue<>("AnimationSpeed", "动画速度", 0.2, 0.05, 0.5, 0.05, () -> style.get() == Style.Sakura);
    private final BoolValue showCategory = new BoolValue("ShowCategory", "显示分类", true, () -> style.get() == Style.Sakura);
    private final BoolValue hideHudModules = new BoolValue("HideHudModules", "隐藏HUD模块", false, () -> style.get() == Style.Sakura);
    private final NumberValue<Double> itemSpacing = new NumberValue<>("ItemSpacing", "项目间距", 7.0, 0.0, 10.0, 0.5, () -> style.get() == Style.Sakura);
    private final NumberValue<Double> hudScale = new NumberValue<>("HudScale", "HUD缩放", 1.1, 0.5, 2.0, 0.1, () -> style.get() == Style.Sakura);
    private final NumberValue<Integer> suffixStyle = new NumberValue<>("SuffixStyle", "后缀符号", 0, 0, 3, 1, () -> style.get() == Style.Sakura || style.get() == Style.Vape || style.get() == Style.LiquidGlass || style.get() == Style.Gradient);

    // Vape Settings
    private final EnumValue<ColorMode> vapeColorMode = new EnumValue<>("ColorMode", "颜色模式", ColorMode.Client, () -> style.get() == Style.Vape);
    private final ColorValue vapeColor1 = new ColorValue("Color1", "颜色1", new Color(26, 226, 107), () -> style.get() == Style.Vape && (vapeColorMode.get() == ColorMode.Single || vapeColorMode.get() == ColorMode.Double));
    private final ColorValue vapeColor2 = new ColorValue("Color2", "颜色2", new Color(255, 255, 255), () -> style.get() == Style.Vape && vapeColorMode.get() == ColorMode.Double);
    private final NumberValue<Double> vapeScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1, () -> style.get() == Style.Vape);
    private final NumberValue<Double> vapeSpacing = new NumberValue<>("Spacing", "间距", 2.0, 0.0, 10.0, 0.5, () -> style.get() == Style.Vape);
    private final BoolValue vapeHideHud = new BoolValue("HideHud", "隐藏HUD", false, () -> style.get() == Style.Vape);
    private final BoolValue vapeBloom = new BoolValue("Bloom", "光晕", false, () -> style.get() == Style.Vape);
    private final BoolValue vapeBackground = new BoolValue("Background", "背景", true, () -> style.get() == Style.Vape);
    private final NumberValue<Integer> vapeBgAlpha = new NumberValue<>("BgAlpha", "背景透明度", 100, 0, 255, 1, () -> style.get() == Style.Vape && vapeBackground.get());

    // LiquidGlass Settings
    private final EnumValue<ColorMode> liquidGlassColorMode = new EnumValue<>("LGColorMode", "颜色模式", ColorMode.Client, () -> style.get() == Style.LiquidGlass);
    private final ColorValue liquidGlassColor1 = new ColorValue("LGColor1", "自定义颜色1", new Color(0, 195, 255), () -> style.get() == Style.LiquidGlass && (liquidGlassColorMode.get() == ColorMode.Single || liquidGlassColorMode.get() == ColorMode.Double));
    private final ColorValue liquidGlassColor2 = new ColorValue("LGColor2", "自定义颜色2", new Color(255, 105, 180), () -> style.get() == Style.LiquidGlass && liquidGlassColorMode.get() == ColorMode.Double);
    private final NumberValue<Double> liquidGlassScale = new NumberValue<>("LGScale", "缩放", 1.0, 0.5, 2.0, 0.1, () -> style.get() == Style.LiquidGlass);
    private final NumberValue<Double> liquidGlassSpacing = new NumberValue<>("LGSpacing", "间距", 4.0, 0.0, 10.0, 0.5, () -> style.get() == Style.LiquidGlass);
    private final NumberValue<Integer> liquidGlassBgAlpha = new NumberValue<>("LGBgAlpha", "背景透明度", 40, 0, 255, 1, () -> style.get() == Style.LiquidGlass);
    private final BoolValue liquidGlassBgTint = new BoolValue("LGBgTint", "背景颜色开关", false, () -> style.get() == Style.LiquidGlass);
    private final ColorValue liquidGlassBgTintColor = new ColorValue("LGBgTintColor", "背景颜色", new Color(0, 0, 0, 100), () -> style.get() == Style.LiquidGlass && liquidGlassBgTint.get());
    private final NumberValue<Integer> liquidGlassBgTintAlpha = new NumberValue<>("LGBgTintAlpha", "背景颜色透明度", 100, 0, 255, 1, () -> style.get() == Style.LiquidGlass && liquidGlassBgTint.get());
    private final BoolValue liquidGlassGlow = new BoolValue("LGGlow", "发光", true, () -> style.get() == Style.LiquidGlass);
    private final BoolValue liquidGlassBlur = new BoolValue("LGBlur", "背景模糊", true, () -> style.get() == Style.LiquidGlass);
    private final BoolValue liquidGlassHideHud = new BoolValue("LGHideHud", "隐藏HUD模块", false, () -> style.get() == Style.LiquidGlass);
    private final NumberValue<Double> liquidGlassBlurStrength = new NumberValue<>("LGBlurStrength", "模糊强度", 16.0, 4.0, 280.0, 1.0, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassTextBlurStrength = new NumberValue<>("LGTextBlurStrength", "文字模糊强度", 16.0, 4.0, 280.0, 1.0, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassIconBlurStrength = new NumberValue<>("LGIconBlurStrength", "图标模糊强度", 16.0, 4.0, 280.0, 1.0, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassRefractionAmount = new NumberValue<>("LGRefractionAmount", "折射强度", 0.4, 0.0, 0.9, 0.001, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassRefractionBand = new NumberValue<>("LGRefractionBand", "折射带宽", 2.0, 0.0, 8.0, 0.1, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassRefractionStrength = new NumberValue<>("LGRefractionStrength", "折射混合强度", 0.8, 0.0, 1.5, 0.05, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassLensCurvature = new NumberValue<>("LGLensCurvature", "透镜曲率", 1.6, 0.5, 4.0, 0.1, () -> style.get() == Style.LiquidGlass && liquidGlassBlur.get());
    private final NumberValue<Double> liquidGlassTextOffsetY = new NumberValue<>("LGTextOffsetY", "文字Y偏移", 1.0, -5.0, 5.0, 0.5, () -> style.get() == Style.LiquidGlass);
    private final NumberValue<Double> liquidGlassIconOffsetX = new NumberValue<>("LGIconOffsetX", "图标X偏移", 1.0, -10.0, 10.0, 0.5, () -> style.get() == Style.LiquidGlass);
    private final NumberValue<Double> liquidGlassIconOffsetY = new NumberValue<>("LGIconOffsetY", "图标Y偏移", 0.0, -10.0, 10.0, 0.5, () -> style.get() == Style.LiquidGlass);
    private final BoolValue liquidGlassIconColor = new BoolValue("LGIconColor", "图标跟随文本颜色", false, () -> style.get() == Style.LiquidGlass);

    // Gradient Settings
    // 1. Text & Font
    private final NumberValue<Double> gradientScale = new NumberValue<>("GradientScale", "缩放", 1.0, 0.5, 2.0, 0.1, () -> style.get() == Style.Gradient);
    private final NumberValue<Double> gradientSpacing = new NumberValue<>("GradientSpacing", "间距", 7.0, 0.0, 10.0, 0.5, () -> style.get() == Style.Gradient);
    private final NumberValue<Double> customFontSize = new NumberValue<>("FontSize", "字体大小", 10.0, 5.0, 30.0, 0.5, () -> style.get() == Style.Gradient);
    private final NumberValue<Double> textOffsetY = new NumberValue<>("TextOffsetY", "文字Y偏移", 1.0, -10.0, 10.0, 0.5, () -> style.get() == Style.Gradient);
    private final BoolValue textGlow = new BoolValue("TextGlow", "文本发光", true, () -> style.get() == Style.Gradient);
    private final NumberValue<Double> glowRadius = new NumberValue<>("GlowRadius", "发光半径", 3.0, 1.0, 10.0, 0.5, () -> style.get() == Style.Gradient && textGlow.get());
    private final NumberValue<Integer> glowIntensity = new NumberValue<>("GlowIntensity", "发光强度", 2, 1, 10, 1, () -> style.get() == Style.Gradient && textGlow.get());

    // 2. Colors
    private final BoolValue autoColor = new BoolValue("AutoColor", "自动调色", false, () -> style.get() == Style.Gradient);
    private final ColorValue gradientColor1 = new ColorValue("GradientColor1", "颜色1", new Color(0, 255, 255), () -> style.get() == Style.Gradient);
    private final ColorValue gradientColor2 = new ColorValue("GradientColor2", "颜色2", new Color(255, 0, 255), () -> style.get() == Style.Gradient);
    private final NumberValue<Double> gradientSpeed = new NumberValue<>("GradientSpeed", "渐变速度", 1.0, 0.1, 10.0, 0.1, () -> style.get() == Style.Gradient);
    private final NumberValue<Double> colorStep = new NumberValue<>("ColorStep", "颜色跨度", 15.0, 1.0, 100.0, 1.0, () -> style.get() == Style.Gradient);

    // 3. Background
    private final BoolValue background = new BoolValue("GradientBackground", "背景", false, () -> style.get() == Style.Gradient);

    public enum BackgroundMode {Normal, Blur}

    private final EnumValue<BackgroundMode> backgroundMode = new EnumValue<>("BackgroundMode", "背景模式", BackgroundMode.Normal, () -> style.get() == Style.Gradient && background.get());
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 100), () -> style.get() == Style.Gradient && background.get());
    private final NumberValue<Double> backgroundRadius = new NumberValue<>("BackgroundRadius", "背景圆角", 0.0, 0.0, 10.0, 1.0, () -> style.get() == Style.Gradient && background.get());
    private final NumberValue<Double> backgroundOffsetY = new NumberValue<>("BackgroundOffsetY", "背景Y偏移", -3.0, -10.0, 10.0, 0.5, () -> style.get() == Style.Gradient && background.get());

    // 4. Lines
    private final BoolValue showGradientLine = new BoolValue("ShowLine", "显示线条", false, () -> style.get() == Style.Gradient);

    public enum LineMode {
        Left("左侧"),
        Box("方框");

        private final String cnName;

        LineMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<LineMode> lineMode = new EnumValue<>("LineMode", "线条模式", LineMode.Left, () -> style.get() == Style.Gradient && showGradientLine.get());
    private final NumberValue<Double> lineWidth = new NumberValue<>("LineWidth", "线条宽度", 2.0, 1.0, 5.0, 0.5, () -> style.get() == Style.Gradient && showGradientLine.get());

    // 5. Bloom
    private final BoolValue bloom = new BoolValue("GradientBloom", "光晕", false, () -> style.get() == Style.Gradient);

    public enum BloomMode {
        Stencil("模版"),
        Standard("标准"),
        Kawase("Kawase");

        private final String cnName;

        BloomMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<BloomMode> bloomMode = new EnumValue<>("BloomMode", "光晕模式", BloomMode.Stencil, () -> style.get() == Style.Gradient && bloom.get());

    public enum BloomStyle {
        Gradient("渐变"),
        Static("静态");

        private final String cnName;

        BloomStyle(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<BloomStyle> bloomStyle = new EnumValue<>("BloomStyle", "光晕样式", BloomStyle.Gradient, () -> style.get() == Style.Gradient && bloom.get());
    private final ColorValue bloomColor = new ColorValue("BloomColor", "光晕颜色", new Color(0, 255, 255), () -> style.get() == Style.Gradient && bloom.get() && bloomStyle.is(BloomStyle.Static));
    private final NumberValue<Double> bloomRadius = new NumberValue<>("BloomRadius", "光晕半径", 5.0, 1.0, 20.0, 1.0, () -> style.get() == Style.Gradient && bloom.get());
    private final BoolValue gradientHideHud = new BoolValue("GradientHideHud", "隐藏HUD模块", false, () -> style.get() == Style.Gradient);


    private final List<ModuleEntry> moduleEntries = new ArrayList<>();
    private static ModuleListHud instance;
    private final java.util.Map<Module, EaseInOutQuad> moduleAnimations = new java.util.HashMap<>();
    private float targetWidth = 0;
    private float targetHeight = 0;
    private float currentWidth = 0;
    private float currentHeight = 0;

    private int iconImage = -1;
    private final BoolValue showIcon = new BoolValue("ShowIcon", "显示图标", true, () -> style.get() == Style.Sakura);
    // 图标大小固定为15

    private float rotationAngle = 0.0f;
    private long lastUpdateTime = 0;

    private final List<Particle> particles = new ArrayList<>();
    private final BoolValue enableParticles = new BoolValue("Enable Particles", "启用粒子", true, () -> style.get() == Style.Sakura);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 1.0, 0.1, 5.0, 0.1, () -> style.get() == Style.Sakura && enableParticles.get());
    private final NumberValue<Integer> particleCount = new NumberValue<>("Particle Count", "粒子数量", 10, 0, 50, 1, () -> style.get() == Style.Sakura && enableParticles.get());
    private final NumberValue<Double> particleSize = new NumberValue<>("Particle Size", "粒子大小", 2.0, 1.0, 5.0, 0.1, () -> style.get() == Style.Sakura && enableParticles.get());
    private final NumberValue<Double> particleSpeed = new NumberValue<>("Particle Speed", "粒子速度", 1.0, 0.1, 3.0, 0.1, () -> style.get() == Style.Sakura && enableParticles.get());


    private static final float PADDING_X = 6f;
    private static final float PADDING_Y = 4f;

    private static final float CATEGORY_ICON_SPACING = 6f;
    private static final Color SUFFIX_COLOR = new Color(180, 180, 180);
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18, 70);

    private static final String[] ICON_SET = {"U"};
    private static final float ICON_BACKGROUND_WIDTH = 12f;
    private static final float ICON_BACKGROUND_HEIGHT = 12f;

    private static final java.util.Random RANDOM = new java.util.Random();

    public ModuleListHud() {
        super("ModuleList", "功能列表", 10, 10);
        this.currentWidth = 50;
        this.currentHeight = 20;
        this.width = currentWidth;
        this.height = currentHeight;
        this.lastUpdateTime = System.currentTimeMillis();
        instance = this;
    }

    public static void onModuleToggle(Module module, boolean enabled) {
        if (instance != null && !module.isHidden() && (!instance.hideHudModules.get() || !(module instanceof HudModule))) {
            if (enabled) {
                EaseInOutQuad animation = instance.moduleAnimations.computeIfAbsent(module, k -> new EaseInOutQuad(200, 1.0));
                animation.setDirection(Direction.FORWARDS);
                animation.reset();
            } else {
                EaseInOutQuad animation = instance.moduleAnimations.get(module);
                if (animation != null) {
                    animation.setDirection(Direction.BACKWARDS);
                    animation.reset();
                }
            }
        }
    }

    @Override
    public void renderInGame(DrawContext context) {
        if (isHudEditorOpen()) return;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            update();
            ensureWithinScreenBounds();
        });

        if (style.get() == Style.LiquidGlass && liquidGlassBlur.get()) {
            renderLiquidGlassBlurBackgrounds(context.getMatrices());
        }

        if (style.get() == Style.Gradient) {
            if (background.get() && backgroundMode.get() == BackgroundMode.Blur) {
                renderBlurBackgrounds(context.getMatrices());
            }
            if (bloom.get()) {
                renderGradientBloom(context.getMatrices());
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (style.get() == Style.Sakura) {
                renderContent();
            } else if (style.get() == Style.Vape) {
                renderVapeContent();
            } else if (style.get() == Style.LiquidGlass) {
                renderLiquidGlassContent();
            } else {
                renderGradientContent();
            }
        });
    }

    @Override
    public void renderInEditor(DrawContext context, float mouseX, float mouseY) {
        handleDrag(mouseX, mouseY);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            update();
        });

        if (style.get() == Style.Gradient && bloom.get()) {
            renderGradientBloom(context.getMatrices());
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            float currentScale = getScale();
            float scaledWidth = currentWidth * currentScale;
            float scaledHeight = currentHeight * currentScale;
            NanoVGHelper.drawRect(x, y - 5, scaledWidth, scaledHeight + 4,
                    dragging ? new Color(ClickGui.color(0).getRed(), ClickGui.color(0).getGreen(), ClickGui.color(0).getBlue(), 80) : BACKGROUND_COLOR);

            if (style.get() == Style.Sakura) {
                renderContent();
            } else if (style.get() == Style.Vape) {
                renderVapeContent();
            } else if (style.get() == Style.LiquidGlass) {
                renderLiquidGlassContent();
            } else {
                renderGradientContent();
            }
        });
    }

    public float getScale() {
        if (style.get() == Style.Sakura) return hudScale.get().floatValue();
        if (style.get() == Style.Vape) return vapeScale.get().floatValue();
        if (style.get() == Style.LiquidGlass) return liquidGlassScale.get().floatValue();
        return gradientScale.get().floatValue();
    }

    public boolean isAlignRight() {
        return x > (mc.getWindow().getScaledWidth() / 2f);
    }

    public float getRadius() {
        return radius.get().floatValue();
    }

    private boolean isHudEditorOpen() {
        HudEditor editor = Sakura.MODULES.getModule(HudEditor.class);
        return editor != null && editor.isEnabled();
    }

    private void handleDrag(float mouseX, float mouseY) {
        if (!dragging) return;
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        float currentScale = getScale();
        boolean align = isAlignRight();

        if (align && !isHudEditorOpen()) {
            x = sw - (currentWidth * currentScale);
        } else {
            x = clamp(mouseX - dragX, 0, sw - (currentWidth * currentScale));
        }
        y = clamp(mouseY - dragY, 0, sh - (currentHeight * currentScale));
        relativeX = x / sw;
        relativeY = y / sh;
    }

    private float rightOffset = -1;
    private boolean lastAlignRight = false;
    private boolean firstUpdate = true;

    private void update() {
        float oldWidth = currentWidth;
        int oldScreenWidth = mc.getWindow().getScaledWidth();
        int oldScreenHeight = mc.getWindow().getScaledHeight();
        updateModuleList();
        calculateTargetSize();

        if (firstUpdate) {
            currentWidth = targetWidth;
            currentHeight = targetHeight;
            firstUpdate = false;
        } else {
            float speed = animationSpeed.get().floatValue(); // Reuse animation speed for now
            currentWidth += (targetWidth - currentWidth) * speed;
            currentHeight += (targetHeight - currentHeight) * speed;
        }

        float currentScale = getScale();
        boolean align = isAlignRight();

        if (align != lastAlignRight) {
            rightOffset = -1;
            lastAlignRight = align;
        }

        this.width = currentWidth * currentScale;
        this.height = currentHeight * currentScale;

        if (align) {
            if (dragging) {
                rightOffset = oldScreenWidth - (x + this.width);
            } else {
                if (rightOffset == -1) {
                    rightOffset = oldScreenWidth - (x + this.width);
                }
                x = oldScreenWidth - rightOffset - this.width;
            }
        } else {
            // Reset offset when not right aligned to prevent stale state
            rightOffset = -1;
        }

        if (style.get() == Style.Sakura) {
            updateRotation();
            updateParticles();

            if (iconImage == -1 && showIcon.get()) {
                loadIcon();
            }
        }
    }

    private final java.util.Map<Module, String> moduleIconMap = new java.util.HashMap<>();
    
    private static class CachedWidth {
        final String text;
        final float width;
        final float fontSize;
        final int font;
        CachedWidth(String text, float width, float fontSize, int font) {
            this.text = text;
            this.width = width;
            this.fontSize = fontSize;
            this.font = font;
        }
    }
    private final java.util.Map<Module, CachedWidth> widthCache = new java.util.HashMap<>();

    private void updateModuleList() {
        int font;
        float fontSize;
        if (style.get() == Style.Gradient) {
            fontSize = customFontSize.get().floatValue();
            font = FontLoader.medium(fontSize);
        } else {
            fontSize = 10;
            font = FontLoader.medium(10);
        }

        moduleEntries.clear();
        
        for (Module module : Sakura.MODULES.getAllModules()) {
            if (module.isHidden()) continue;

            boolean isEnabled = module.isEnabled();
            EaseInOutQuad animation = moduleAnimations.get(module);
            boolean isAnimating = animation != null && animation.getOutput() > 0.01;

            if (!isEnabled && !isAnimating) continue;

            if (style.get() == Style.Sakura) {
                if (hideHudModules.get() && module instanceof HudModule) continue;
            } else if (style.get() == Style.Vape) {
                if (vapeHideHud.get() && module instanceof HudModule) continue;
            } else if (style.get() == Style.LiquidGlass) {
                if (liquidGlassHideHud.get() && module instanceof HudModule) continue;
            } else {
                if (gradientHideHud.get() && module instanceof HudModule) continue;
            }

            String displayText = getDisplayText(module);
            float width;
            CachedWidth cached = widthCache.get(module);
            if (cached != null && cached.text.equals(displayText) && cached.fontSize == fontSize && cached.font == font) {
                width = cached.width;
            } else {
                width = NanoVGHelper.getTextWidth(displayText, font, fontSize);
                widthCache.put(module, new CachedWidth(displayText, width, fontSize, font));
            }
            moduleEntries.add(new ModuleEntry(module, displayText, width));
        }

        moduleEntries.sort((e1, e2) -> Float.compare(e2.textWidth, e1.textWidth));

        // Cleanup icons for modules no longer in the list
        moduleIconMap.keySet().removeIf(module -> {
            for (ModuleEntry entry : moduleEntries) {
                if (entry.module == module) return false;
            }
            return true;
        });

        for (ModuleEntry entry : moduleEntries) {
            if (!moduleIconMap.containsKey(entry.module)) {
                String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
                moduleIconMap.put(entry.module, icon);
            }
        }
    }

    private void calculateTargetSize() {
        if (style.get() == Style.Vape) {
            calculateVapeTargetSize();
            return;
        }
        if (style.get() == Style.LiquidGlass) {
            calculateLiquidGlassTargetSize();
            return;
        }
        if (style.get() == Style.Gradient) {
            calculateGradientTargetSize();
            return;
        }

        if (moduleEntries.isEmpty()) {
            targetWidth = 50;
            targetHeight = 20;

            if (showIcon.get()) {
                float iconRenderSize = 13.0f;
                targetHeight = PADDING_Y * 2 + iconRenderSize + 4 - 2;
            }
            return;
        }
        
        float totalHeight = PADDING_Y * 2;

        if (showIcon.get()) {
            float iconRenderSize = 13.0f;
            totalHeight += iconRenderSize + 4;
        }

        float maxTextWidth = 0;

        int i = 0;
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                float textWidth = entry.textWidth;
                if (showCategory.get()) {
                    textWidth += ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING;
                }
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (10 + itemSpacing.get().floatValue()) * animationValue;
            }
        }

        if (!moduleEntries.isEmpty()) {
            long visibleModuleCount = moduleEntries.stream()
                    .map(entry -> moduleAnimations.get(entry.module))
                    .filter(animation -> animation != null && animation.getOutput() > 0.01)
                    .count();

            if (visibleModuleCount == 0 && !moduleEntries.isEmpty()) {
                for (ModuleEntry entry : moduleEntries) {
                    float textWidth = entry.textWidth;
                    if (showCategory.get()) {
                        textWidth += ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING;
                    }
                    maxTextWidth = Math.max(maxTextWidth, textWidth);
                    totalHeight += (10 + itemSpacing.get().floatValue());
                }
                totalHeight -= itemSpacing.get().floatValue();
            } else {
                totalHeight -= itemSpacing.get().floatValue();
            }
        }

        if (showIcon.get()) {
            float iconRenderSize = 13.0f;
            int sakuraFont = FontLoader.bold(13);
            float sakuraTextWidth = NanoVGHelper.getTextWidth("ModuleList", sakuraFont, 11);
            float totalRequiredWidth = iconRenderSize + sakuraTextWidth + 4;
            maxTextWidth = Math.max(maxTextWidth, totalRequiredWidth);
        }

        targetWidth = maxTextWidth + PADDING_X * 2;
        targetHeight = totalHeight;
    }

    private void ensureWithinScreenBounds() {
        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();
        float scale = getScale();

        float scaledWidth = currentWidth * scale;
        float scaledHeight = currentHeight * scale;

        if (x < 0) x = 0;
        if (x + scaledWidth > screenWidth) {
            x = screenWidth - scaledWidth;
            if (x < 0) x = 0;
        }

        if (y < 0) y = 0;
        float bottomEdge = y + scaledHeight;
        if (bottomEdge > screenHeight) {
            y = screenHeight - scaledHeight;
            if (y < 0) y = 0;
        }
    }

    private void renderContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float scale = hudScale.get().floatValue();

        float currentY = y + (PADDING_Y * scale);

        if (showIcon.get() && iconImage != -1) {
            float iconRenderSize = 13.0f * scale;
            float iconX = isAlignRight() ?
                    x + (currentWidth * scale) - iconRenderSize - (PADDING_X * scale) :
                    x + (PADDING_X * scale);
            float iconY = currentY - (2 * scale);

            // 添加纯黑背景
            String sakuraText = "ModuleList";
            int font = FontLoader.bold(11);
            float textWidth = NanoVGHelper.getTextWidth(sakuraText, font, 11 * scale);
            float textHeight = NanoVGHelper.getFontHeight(font, 11 * scale);
            float totalWidth = iconRenderSize + textWidth + (4 * scale) + 4;
            float bgX = isAlignRight() ?
                    x + (currentWidth * scale) - totalWidth - (PADDING_X * scale) :
                    x + (PADDING_X * scale);
            float bgY = currentY - (2 * scale) - (1.5f * scale);
            float bgHeight = Math.max(iconRenderSize, textHeight) + (2 * scale);

            NanoVGHelper.drawRoundRect(bgX, bgY - 1 - (1.5f * scale), totalWidth, bgHeight, getRadius(), new Color(0, 0, 0, 180));

            float centerX = iconX + iconRenderSize / 2;
            float centerY = iconY + iconRenderSize / 2 - (3 * scale);

            nvgSave(vg);
            nvgTranslate(vg, centerX, centerY);
            nvgRotate(vg, (float) Math.toRadians(rotationAngle));
            nvgTranslate(vg, -iconRenderSize / 2, -iconRenderSize / 2);

            NVGPaint paint = NVGPaint.create();
            nvgImagePattern(vg, 0, 0, iconRenderSize, iconRenderSize, 0, iconImage, 1.0f, paint);
            nvgBeginPath(vg);
            nvgRect(vg, 0, 0, iconRenderSize, iconRenderSize);
            nvgFillPaint(vg, paint);
            nvgFill(vg);

            nvgRestore(vg);


            float textX = isAlignRight() ?
                    x + (currentWidth * scale) - textWidth - iconRenderSize - (4 * scale) - (PADDING_X * scale) :
                    iconX + iconRenderSize + (4 * scale);
            float textY = currentY + iconRenderSize / 2 + textHeight / 4;

            NanoVGHelper.drawGlowingString(sakuraText, textX, textY - 1 - (3 * scale), font, 11 * scale, Color.WHITE, 3.0f * scale);

            currentY += iconRenderSize + (4 * scale) - (2 * scale);
        }

        if (enableParticles.get()) {
            renderParticles();
        }

        int font = FontLoader.medium(10);
        int i = 0;
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue < 0.01) {
                currentY += ((10 + itemSpacing.get().floatValue()) * scale);
                continue;
            }

            if (currentY + (10 * scale) < y || currentY > y + (currentHeight * scale)) {
                currentY += ((10 + itemSpacing.get().floatValue()) * scale);
                continue;
            }

            String text = entry.text;
            float totalTextWidth = entry.textWidth * scale;
            
            float textHeight = NanoVGHelper.getFontHeight(font, 10 * scale);
            
            String categoryIcon = "";
            float iconWidth = 0;
            float iconHeight = 0;
            if (showCategory.get()) {
                int iconFont = FontLoader.icons(10);
                categoryIcon = getRandomCategoryIcon(entry.module);
                iconWidth = NanoVGHelper.getTextWidth(categoryIcon, iconFont, 10 * scale);
                iconHeight = NanoVGHelper.getFontHeight(iconFont, 10 * scale);
                totalTextWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
            }
            float itemWidth = totalTextWidth + (PADDING_X * 2 * scale);
            float itemHeight = 10 * scale;
            float itemX = isAlignRight() ? x + (currentWidth * scale) - itemWidth : x;
            float textX;
            float iconBgX = 0;
            float iconX = 0;
            if (isAlignRight() && showCategory.get()) {
                iconBgX = x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale);
                textX = iconBgX - (CATEGORY_ICON_SPACING * scale) - (entry.textWidth * scale);
            } else if (!isAlignRight() && showCategory.get()) {
                iconBgX = itemX + (6 * scale);
                textX = iconBgX + (ICON_BACKGROUND_WIDTH * scale) + (CATEGORY_ICON_SPACING * scale);
            } else {
                textX = isAlignRight() ? x + (currentWidth * scale) - (PADDING_X * scale) - (entry.textWidth * scale) : itemX + (PADDING_X * scale);
            }
            float textY = currentY + textHeight / 2 + (2 * scale);

            int alpha = (int) (BACKGROUND_COLOR.getAlpha() * animationValue);
            Color animatedBackgroundColor = new Color(
                    BACKGROUND_COLOR.getRed(),
                    BACKGROUND_COLOR.getGreen(),
                    BACKGROUND_COLOR.getBlue(),
                    alpha
            );

            float animatedItemWidth = itemWidth * (float) animationValue;
            float animatedTextX = isAlignRight() ?
                    textX + (itemWidth - animatedItemWidth) : textX;

            if (enableBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(
                        isAlignRight() && showCategory.get() ?
                                (itemX + (4 * scale) + (itemWidth - animatedItemWidth)) :
                                (itemX + (showCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING + 4) * scale : (4 * scale)) + (itemWidth - animatedItemWidth)),
                        currentY - (3 * scale),
                        (itemWidth - (showCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale : 0) - (7 * scale)) * (float) animationValue,
                        itemHeight + (3 * scale),
                        getRadius() * scale,
                        animatedBackgroundColor
                );
            } else {
                NanoVGHelper.drawRoundRect(
                        isAlignRight() && showCategory.get() ?
                                (itemX + (4 * scale) + (itemWidth - animatedItemWidth)) :
                                (itemX + (showCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING + 4) * scale : (4 * scale)) + (itemWidth - animatedItemWidth)),
                        currentY - (3 * scale),
                        (itemWidth - (showCategory.get() ? (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale : 0) - (7 * scale)) * (float) animationValue,
                        itemHeight + (3 * scale),
                        getRadius() * scale,
                        animatedBackgroundColor
                );
            }

            if (showCategory.get()) {
                float animatedIconBgX = isAlignRight() ?
                        x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale) - (itemWidth - animatedItemWidth) :
                        iconBgX + (itemWidth - animatedItemWidth);

                if (enableBloom.get()) {
                    NanoVGHelper.drawRoundRectBloom(
                            animatedIconBgX,
                            currentY - (3 * scale),
                            ICON_BACKGROUND_WIDTH * scale,
                            ICON_BACKGROUND_HEIGHT * scale,
                            getRadius() * scale,
                            animatedBackgroundColor
                    );
                } else {
                    NanoVGHelper.drawRoundRect(
                            animatedIconBgX,
                            currentY - (3 * scale),
                            ICON_BACKGROUND_WIDTH * scale,
                            ICON_BACKGROUND_HEIGHT * scale,
                            getRadius() * scale,
                            animatedBackgroundColor
                    );
                }
                float iconY = currentY + ((ICON_BACKGROUND_HEIGHT * scale) - iconHeight) / 2;
                iconX = animatedIconBgX + ((ICON_BACKGROUND_WIDTH * scale) - iconWidth) / 2;
                int iconFont = FontLoader.icons(10);
                NanoVGHelper.drawGlowingString(categoryIcon, iconX + (0.5f * scale), iconY + (5 * scale), iconFont, 10 * scale, Color.WHITE, 2.0f * scale);
            }

            Color textColor = getModuleColor(i, moduleEntries.size(), 0, Style.Sakura);
            Color animatedTextColor = new Color(
                    textColor.getRed(),
                    textColor.getGreen(),
                    textColor.getBlue(),
                    (int) (textColor.getAlpha() * animationValue)
            );

            NanoVGHelper.drawString(text, animatedTextX, textY, font, 10 * scale, animatedTextColor);
            
            currentY += ((10 + itemSpacing.get().floatValue()) * scale);
            i++;
        }
    }

    private void calculateVapeTargetSize() {
        if (moduleEntries.isEmpty()) {
            targetWidth = 50;
            targetHeight = 25;
            return;
        }

        float totalHeight = 0;
        float maxTextWidth = 0;

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                float textWidth = entry.textWidth;
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (12 + vapeSpacing.get().floatValue()) * animationValue;
            }
        }

        // Layout constants (unscaled)
        float barWidth = 2.2f;
        float gap = 3.5f;
        float outerGap = 3.0f;

        float extraWidth;
        if (vapeBackground.get()) {
            extraWidth = barWidth + gap + outerGap;
        } else {
            extraWidth = outerGap * 2;
        }

        targetWidth = maxTextWidth + extraWidth;
        targetHeight = totalHeight;
    }

    private void calculateLiquidGlassTargetSize() {
        if (moduleEntries.isEmpty()) {
            targetWidth = 50;
            targetHeight = 25;
            return;
        }

        float totalHeight = 0;
        float maxTextWidth = 0;
        float scale = liquidGlassScale.get().floatValue();

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                float textWidth = entry.textWidth;
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (14 + liquidGlassSpacing.get().floatValue()) * animationValue;
            }
        }

        if (totalHeight > 0) {
            totalHeight -= liquidGlassSpacing.get().floatValue();
        }

        // Padding
        float paddingX = 6.0f;
        float iconGap = 3.0f;
        float squareSize = 14.0f;
        targetWidth = maxTextWidth + (paddingX * 2) + iconGap + squareSize;
        targetHeight = totalHeight;
    }

    private static class VapeRenderItem {
        float bgX, bgY, bgW, bgH;
        float barX, barW;
        float textX, textY;
        Color textColor;
        String text;
    }

    private void renderVapeContent() {
        float scale = vapeScale.get().floatValue();
        float currentY = y;
        boolean alignRight = isAlignRight();
        int font = FontLoader.medium(10);
        float spacing = vapeSpacing.get().floatValue() * scale;
        float baseItemHeight = 12 * scale;

        // Layout constants
        float barWidth = 2.2f * scale;
        float gap = 3.5f * scale;
        float outerGap = 3.0f * scale;

        List<VapeRenderItem> renderItems = new ArrayList<>();
        int i = 0;

        // 1. Calculate Layout
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            float fullRowHeight = baseItemHeight + spacing;
            float dynamicRowHeight = fullRowHeight * (float) animationValue;

            if (animationValue < 0.01) {
                currentY += dynamicRowHeight;
                i++;
                continue;
            }

            if (currentY + dynamicRowHeight < y || currentY > y + (currentHeight * scale)) {
                currentY += dynamicRowHeight;
                i++;
                continue;
            }

            String text = entry.text;
            float textWidth = entry.textWidth * scale;

            Color color = getModuleColor(i, moduleEntries.size(), 0, Style.Vape);
            int alpha = (int) (255 * animationValue);
            Color textColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);

            float textX;
            float boxWidth = currentWidth * scale;

            if (alignRight) {
                float rightEdge = x + boxWidth;
                if (vapeBackground.get()) {
                    textX = rightEdge - barWidth - gap - textWidth;
                } else {
                    textX = rightEdge - outerGap - textWidth;
                }
            } else {
                if (vapeBackground.get()) {
                    textX = x + barWidth + gap;
                } else {
                    textX = x + outerGap;
                }
            }

            VapeRenderItem item = new VapeRenderItem();
            item.text = text;
            item.textColor = textColor;
            item.textX = textX;
            // Center text vertically in the row
            item.textY = currentY + (dynamicRowHeight / 2) + (3.0f * scale);

            if (vapeBackground.get()) {
                if (alignRight) {
                    item.bgX = textX - outerGap;
                    item.bgW = outerGap + textWidth + gap + barWidth;
                    item.barX = item.bgX + item.bgW - barWidth;
                } else {
                    item.bgX = x;
                    item.bgW = barWidth + gap + textWidth + outerGap;
                    item.barX = x;
                }
                item.bgY = currentY;
                item.bgH = dynamicRowHeight;
                item.barW = barWidth;
            }

            renderItems.add(item);
            currentY += dynamicRowHeight;
            i++;
        }

        long vg = NanoVGRenderer.INSTANCE.getContext();

        int bgAlpha = (int) (vapeBgAlpha.get());
        Color bgColor = new Color(0, 0, 0, bgAlpha);
        float baseAlpha = bgAlpha / 255.0f;

        // 2. Render Unified Background & Bloom
        if (vapeBackground.get() && !renderItems.isEmpty()) {
            // Build Union Path
            nvgBeginPath(vg);

            if (alignRight) {
                // Right Aligned: Right edge is straight
                float rightX = renderItems.get(0).bgX + renderItems.get(0).bgW;
                float topY = renderItems.get(0).bgY;
                float bottomY = renderItems.get(renderItems.size() - 1).bgY + renderItems.get(renderItems.size() - 1).bgH;

                nvgMoveTo(vg, rightX, topY); // Top-Right

                // Trace Left "Steps"
                for (VapeRenderItem item : renderItems) {
                    nvgLineTo(vg, item.bgX, item.bgY); // Top-Left of item
                    nvgLineTo(vg, item.bgX, item.bgY + item.bgH); // Bottom-Left of item
                }

                nvgLineTo(vg, rightX, bottomY); // Bottom-Right
                nvgClosePath(vg);
            } else {
                // Left Aligned: Left edge is straight
                float leftX = renderItems.get(0).bgX;
                float topY = renderItems.get(0).bgY;
                float bottomY = renderItems.get(renderItems.size() - 1).bgY + renderItems.get(renderItems.size() - 1).bgH;

                nvgMoveTo(vg, leftX, topY); // Top-Left

                // Trace Right "Steps"
                for (VapeRenderItem item : renderItems) {
                    nvgLineTo(vg, item.bgX + item.bgW, item.bgY); // Top-Right of item
                    nvgLineTo(vg, item.bgX + item.bgW, item.bgY + item.bgH); // Bottom-Right of item
                }

                nvgLineTo(vg, leftX, bottomY); // Bottom-Left
                nvgClosePath(vg);
            }

            // Draw Bloom (Outer Glow)
            if (vapeBloom.get()) {
                nvgLineJoin(vg, NVG_ROUND);
                nvgLineCap(vg, NVG_ROUND);
                // Simulate bloom with multiple strokes
                float glowSize = 4.0f * scale;
                int glowSteps = 10;
                for (int step = 0; step < glowSteps; step++) {
                    float strokeWidth = (glowSize / glowSteps) * (step + 1) * 2; // Width increases
                    // Alpha decreases and scales with background alpha (Quadratic scaling to avoid harsh bloom at low opacities)
                    float alphaFactor = (1.0f - (float) step / glowSteps) * 0.05f * baseAlpha * baseAlpha;

                    Color glowColor = new Color(0, 0, 0, (int)(255 * alphaFactor));

                    nvgStrokeWidth(vg, strokeWidth);
                    nvgStrokeColor(vg, NanoVGHelper.nvgColor(glowColor));
                    nvgStroke(vg);
                }
            }
            
            // Note: Background fill is now handled per-item to ensure correct "one rect per function" look
            // and avoid the "square shadow" appearance of a unified fill.
        }

        // 3. Render Backgrounds, Bars and Text
        for (VapeRenderItem item : renderItems) {
            // Draw Background (Per Item)
            if (vapeBackground.get()) {
                // Adjust height slightly to overlap and remove gaps
                NanoVGHelper.drawRect(item.bgX, item.bgY, item.bgW, item.bgH + 0.0f, bgColor);
            }

            // Draw Bar
            if (vapeBackground.get()) {
                 if (vapeBloom.get()) {
                     // Use drawRoundRectBloom with 0 radius for rect glow
                     // Note: drawRoundRectBloom uses the color for both fill and glow
                     NanoVGHelper.drawRoundRectBloom(item.barX, item.bgY, item.barW, item.bgH, 0, item.textColor);
                 } else {
                     NanoVGHelper.drawRect(item.barX, item.bgY, item.barW, item.bgH, item.textColor);
                 }
            }

            // Draw Text
            if (vapeBloom.get()) {
                NanoVGHelper.drawGlowingString(item.text, item.textX, item.textY, font, 10 * scale, item.textColor, 2.0f * scale);
            } else {
                NanoVGHelper.drawString(item.text, item.textX, item.textY, font, 10 * scale, item.textColor);
            }
        }
    }

    private void renderLiquidGlassContent() {
        float scale = liquidGlassScale.get().floatValue();
        float currentY = y;
        boolean alignRight = isAlignRight();
        int font = FontLoader.medium(10);
        float spacing = liquidGlassSpacing.get().floatValue() * scale;
        float itemHeight = 14 * scale;
        float paddingX = 6 * scale;
        
        int i = 0;
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;
            
            float fullRowHeight = itemHeight + spacing;
            float dynamicRowHeight = fullRowHeight * (float) animationValue;
            
            if (animationValue < 0.01) {
                currentY += dynamicRowHeight;
                continue;
            }
            
            // Check bounds
            if (currentY + dynamicRowHeight < y || currentY > y + (currentHeight * scale)) {
                currentY += dynamicRowHeight;
                continue;
            }
            
            String text = entry.text;
            float textWidth = entry.textWidth * scale;
            
            // Color logic
            Color moduleColor = getModuleColor(i, moduleEntries.size(), 0, Style.LiquidGlass);
            int alpha = (int) (255 * animationValue);
            Color textColor = new Color(moduleColor.getRed(), moduleColor.getGreen(), moduleColor.getBlue(), alpha);
            
            // Background logic + right icon square (independent)
            float iconGap = 3 * scale;
            float squareSize = itemHeight;
            float itemWidth = textWidth + (paddingX * 2);
            float xPos;
            if (alignRight) {
                xPos = x + (currentWidth * scale) - itemWidth;
            } else {
                xPos = x;
            }
            
            float cornerRadius = 4.0f * scale;
            if (liquidGlassGlow.get()) {
                NanoVGHelper.drawRoundRectHaloFlow(xPos, currentY, itemWidth, itemHeight, cornerRadius, textColor, 6.0f * scale, 8.0f * scale);
            }
            
            float textX = xPos + paddingX;
            float fontH = NanoVGHelper.getFontHeight(font, 10 * scale);
            float textYOffset = liquidGlassTextOffsetY.get().floatValue() * scale;
            float textY = currentY + (itemHeight - fontH) / 2f + fontH + textYOffset;
            
             if (liquidGlassGlow.get()) {
                NanoVGHelper.drawGlowingString(text, textX, textY, font, 10 * scale, textColor, 2.0f * scale);
            } else {
                NanoVGHelper.drawString(text, textX, textY, font, 10 * scale, textColor);
            }
            
            float squareX = alignRight ? xPos + itemWidth + iconGap : xPos - iconGap - squareSize;
            if (liquidGlassGlow.get()) {
                NanoVGHelper.drawRoundRectHaloFlow(squareX, currentY, squareSize, squareSize, cornerRadius, textColor, 6.0f * scale, 8.0f * scale);
            }
            int iconFont = FontLoader.icons(10);
            String categoryIcon = entry.module.getCategory().icon;
            float iconW = NanoVGHelper.getTextWidth(categoryIcon, iconFont, 10 * scale);
            float iconH = NanoVGHelper.getFontHeight(iconFont, 10 * scale);
            float iconX = squareX + (squareSize - iconW) / 2f + liquidGlassIconOffsetX.get().floatValue() * scale;
            float iconY = currentY + (squareSize - iconH) / 2f + iconH + liquidGlassIconOffsetY.get().floatValue() * scale;
            Color iconColor = liquidGlassIconColor.get() ? textColor : new Color(255, 255, 255, (int)(255 * animationValue));
            NanoVGHelper.drawString(categoryIcon, iconX, iconY, iconFont, 10 * scale, iconColor);
            
            currentY += dynamicRowHeight;
            i++;
        }
    }

    private void renderLiquidGlassBlurBackgrounds(net.minecraft.client.util.math.MatrixStack matrices) {
        float scale = liquidGlassScale.get().floatValue();
        float currentY = y;
        boolean alignRight = isAlignRight();
        float spacing = liquidGlassSpacing.get().floatValue() * scale;
        float itemHeight = 14 * scale;
        float paddingX = 6 * scale;

        Shader2DUtil.setRefraction(liquidGlassRefractionAmount.get().floatValue(), liquidGlassRefractionBand.get().floatValue());
        Shader2DUtil.setRefractionStrength(liquidGlassRefractionStrength.get().floatValue());
        Shader2DUtil.setLensCurvature(liquidGlassLensCurvature.get().floatValue());

        int i = 0;
        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;
            float fullRowHeight = itemHeight + spacing;
            float dynamicRowHeight = fullRowHeight * (float) animationValue;

            if (animationValue < 0.01) {
                currentY += dynamicRowHeight;
                i++;
                continue;
            }

            if (currentY + dynamicRowHeight < y || currentY > y + (currentHeight * scale)) {
                currentY += dynamicRowHeight;
                i++;
                continue;
            }

            float textWidth = entry.textWidth * scale;
            float iconGap = 3 * scale;
            float squareSize = itemHeight;
            float itemWidth = textWidth + (paddingX * 2);
            float xPos = alignRight ? x + (currentWidth * scale) - itemWidth : x;

            Color moduleColor = getModuleColor(i, moduleEntries.size(), 0, Style.LiquidGlass);
            int bgAlpha = (int) (liquidGlassBgAlpha.get() * animationValue);
            Color glassColor = new Color(moduleColor.getRed(), moduleColor.getGreen(), moduleColor.getBlue(), bgAlpha);
            float cornerRadius = 4.0f * scale;
            float opacity = Math.max(0f, Math.min(1f, glassColor.getAlpha() / 255f)) * (float) animationValue;
            float blurText = liquidGlassTextBlurStrength.get().floatValue();
            float blurIcon = liquidGlassIconBlurStrength.get().floatValue();
            Color tint;
            if (liquidGlassBgTint.get()) {
                Color c = liquidGlassBgTintColor.get();
                int ta = Math.max(0, Math.min(255, (int) (liquidGlassBgTintAlpha.get() * animationValue)));
                tint = new Color(c.getRed(), c.getGreen(), c.getBlue(), ta);
            } else {
                tint = new Color(0, 0, 0, 0);
            }
            Shader2DUtil.drawRoundedBlur(matrices, xPos, currentY, itemWidth, itemHeight, cornerRadius, tint, blurText, opacity);
            float squareX = alignRight ? xPos + itemWidth + iconGap : xPos - iconGap - squareSize;
            Shader2DUtil.drawRoundedBlur(matrices, squareX, currentY, squareSize, squareSize, cornerRadius, tint, blurIcon, opacity);

            currentY += dynamicRowHeight;
            i++;
        }
    }

    private void renderParticles() {
        long vg = NanoVGRenderer.INSTANCE.getContext();

        for (Particle particle : particles) {
            if (particle.isAlive()) {
                Color particleColor = new Color(
                        particle.color.getRed(),
                        particle.color.getGreen(),
                        particle.color.getBlue(),
                        (int) (particle.color.getAlpha() * particle.alpha)
                );

                nvgBeginPath(vg);
                nvgCircle(vg, particle.x, particle.y, particle.size);

                nvgFillColor(vg, NanoVGHelper.nvgColor(particleColor));
                nvgFill(vg);

                nvgBeginPath(vg);
                nvgCircle(vg, particle.x, particle.y, particle.size * 1.5f);
                nvgFillColor(vg, NanoVGHelper.nvgColor(new Color(255, 255, 255, (int) (50 * particle.alpha))));
                nvgFill(vg);
            }
        }
    }

    private void updateRotation() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        rotationAngle += (deltaTime * 0.05f * rotationSpeed.get().floatValue()) % 360.0f;
        if (rotationAngle >= 360.0f) {
            rotationAngle -= 360.0f;
        }
    }

    private void updateParticles() {
        if (enableParticles.get()) {
            particles.removeIf(particle -> !particle.isAlive());

            if (particles.size() < particleCount.get()) {
                if (showIcon.get() && iconImage != -1) {
                    float scale = hudScale.get().floatValue();
                    float iconRenderSize = 13.0f * scale;
                    float iconX = isAlignRight() ?
                            x + (currentWidth * scale) - iconRenderSize - (PADDING_X * scale) :
                            x + (PADDING_X * scale);
                    float iconY = y + (PADDING_Y * scale) - (2 * scale);

                    for (int i = particles.size(); i < particleCount.get(); i++) {
                        float angle = (float) (Math.random() * Math.PI * 2);
                        float distance = (float) (Math.random() * iconRenderSize * 0.8f);
                        float particleX = iconX + iconRenderSize / 2 + (float) Math.cos(angle) * distance;
                        float particleY = iconY + iconRenderSize / 2 + (float) Math.sin(angle) * distance;

                        Particle newParticle = new Particle(particleX, particleY);
                        newParticle.size = particleSize.get().floatValue();
                        float speed = particleSpeed.get().floatValue();
                        double particleAngle = Math.random() * Math.PI * 2;
                        newParticle.velocityX = (float) (Math.cos(particleAngle) * speed);
                        newParticle.velocityY = (float) (Math.sin(particleAngle) * speed);
                        particles.add(newParticle);
                    }
                }
            }

            for (Particle particle : particles) {
                particle.update();
            }
        }
    }

    private void loadIcon() {
        iconImage = NanoVGHelper.loadTexture("/assets/sakura/icons/icon_32x321.png");
    }

    @Override
    public void onDisable() {
        if (iconImage != -1) {
            NanoVGHelper.deleteTexture(iconImage);
            iconImage = -1;
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

        switch (suffixStyle.get()) {
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

        switch (suffixStyle.get()) {
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

    private float scrollOffset = 0;

    private void renderBlurBackgrounds(net.minecraft.client.util.math.MatrixStack matrices) {
        float scale = gradientScale.get().floatValue();
        float spacing = gradientSpacing.get().floatValue();
        float currentY = y + (spacing * scale);
        currentY -= (scrollOffset * scale);

        float fontSize = customFontSize.get().floatValue();
        int font = FontLoader.medium(fontSize);

        float yPos = currentY;

        for (ModuleEntry entry : moduleEntries) {
            Module module = entry.module;
            float anim = (float) getAnim(module);
            if (anim <= 0.001f) continue;

            String suffix = getFormattedSuffix(module.getSuffix());
            String text = module.getDisplayName() + (suffix.isEmpty() ? "" : " " + suffix);
            
            float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize);
            float h = (fontSize + spacing) * scale;
            
            float itemBgX = x;
            if (isAlignRight()) {
                itemBgX = x + width - textWidth;
            }
            
            Color c = backgroundColor.get();
            Shader2DUtil.drawRoundedBlur(matrices, itemBgX - (2 * scale), yPos, textWidth + (4 * scale), h * anim, 
                backgroundRadius.get().floatValue() * scale, 
                c, 
                10, 
                (c.getAlpha() / 255.0f) * anim);
            
            yPos += h * anim;
        }
    }

    private Color getGradientColor(int index) {
        Color color1, color2;
        if (autoColor.get()) {
            float time = (System.currentTimeMillis() % 6000) / 6000f;
            color1 = Color.getHSBColor(time, 0.8f, 1f);
            color2 = Color.getHSBColor((time + 0.2f) % 1f, 0.8f, 1f);
        } else {
            color1 = gradientColor1.get();
            color2 = gradientColor2.get();
        }

        double ratio = (index * colorStep.get().doubleValue() / 100.0) % 1.0;
        if (gradientSpeed.get() > 0) {
            ratio += (System.currentTimeMillis() * gradientSpeed.get() / 1000.0);
            ratio %= 1.0;
        }
        return interpolateColors(color1, color2, Math.abs(Math.sin(ratio * Math.PI)));
    }

    private double getAnim(Module module) {
        EaseInOutQuad animation = moduleAnimations.get(module);
        return animation != null ? animation.getOutput() : 0.0;
    }

    private void renderGradientBloom(net.minecraft.client.util.math.MatrixStack matrices) {
        float scale = gradientScale.get().floatValue();
        float spacing = gradientSpacing.get().floatValue();
        float currentY = y + (spacing * scale);
        currentY -= (scrollOffset * scale);

        float fontSize = customFontSize.get().floatValue();
        int font = FontLoader.medium(fontSize);

        float yPos = currentY;
        int index = 0;

        for (ModuleEntry entry : moduleEntries) {
            Module module = entry.module;
            float anim = (float) getAnim(module);
            if (anim <= 0.001f) continue;

            String suffix = getFormattedSuffix(module.getSuffix());
            String text = module.getDisplayName() + (suffix.isEmpty() ? "" : " " + suffix);

            float textWidth = NanoVGHelper.getTextWidth(text, font, fontSize);
            float h = (fontSize + spacing) * scale;

            float itemBgX = x;
            if (isAlignRight()) {
                itemBgX = x + width - textWidth;
            }

            Color textColor = getGradientColor(index);
            Color bColor = bloomStyle.get() == BloomStyle.Static ? bloomColor.get() : textColor;
            
            Shader2DUtil.drawRoundedBlur(matrices, itemBgX - (2 * scale), yPos, textWidth + (4 * scale), h * anim,
                    backgroundRadius.get().floatValue() * scale,
                    bColor,
                    bloomRadius.get().floatValue() * scale,
                    1.0f); // Opacity 1.0, controlled by color alpha if needed, or we can add a BloomOpacity setting

            yPos += h * anim;
            index++;
        }
    }

    private void renderGradientContent() {
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float scale = gradientScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();
        float spacing = gradientSpacing.get().floatValue();
        
        float currentY = y + (spacing * scale); // Initial Y
        currentY -= (scrollOffset * scale);

        int font = FontLoader.medium(fontSize);
        
        // Logic from ZSpace
        float totalListHeight = 0;
        float maxItemWidth = 0;
        
        // Pre-calculate heights and widths
        for (ModuleEntry entry : moduleEntries) {
             Module module = entry.module;
             if (getAnim(module) <= 0.001) continue;
             
             float textWidth = entry.textWidth;
             float h = (fontSize + spacing) * scale; // Approximate height per item
             
             if (textWidth > maxItemWidth) maxItemWidth = textWidth;
             totalListHeight += h * getAnim(module);
        }

        // Draw Backgrounds (Normal Mode)
        if (background.get() && backgroundMode.get() == BackgroundMode.Normal) {
             float yPos = currentY;
             for (ModuleEntry entry : moduleEntries) {
                 Module module = entry.module;
                 double anim = getAnim(module);
                 if (anim <= 0.001) continue;
                 
                 float textWidth = entry.textWidth;
                 float h = (fontSize + spacing) * scale; // simplified
                 
                 float itemBgX = x;
                 if (isAlignRight()) {
                     itemBgX = x + width - textWidth; // Right align
                 }
                 
                 // Draw Rect
                 NanoVGHelper.drawRect(itemBgX - (2*scale), yPos, textWidth + (4*scale), h * (float)anim, backgroundColor.get());
                 
                 yPos += h * anim;
             }
        }
        
        // Draw Modules (Text, Lines)
        float yPos = currentY;
        int index = 0;
        for (ModuleEntry entry : moduleEntries) {
            Module module = entry.module;
            double anim = getAnim(module);
            if (anim <= 0.001) continue;
            
            String text = entry.text;
            float textWidth = entry.textWidth;
            float h = (fontSize + spacing) * scale;
            
            Color textColor = getGradientColor(index);
            
            float itemX = x;
            float itemBgX = x;
            if (isAlignRight()) {
                itemX = x + width - textWidth;
                itemBgX = x + width - textWidth;
            }
            
            float textY = yPos + textOffsetY.get().floatValue() * scale;
            
            // Bloom (Glow) handled in separate pass
            
            // Text
            if (textGlow.get()) {
                NanoVGHelper.drawGlowingString(text, itemX, textY, font, fontSize, textColor, glowRadius.get().floatValue());
            } else {
                NanoVGHelper.drawString(text, itemX, textY, font, fontSize, textColor);
            }

            // Lines
            if (showGradientLine.get()) {
                float lw = lineWidth.get().floatValue() * scale;
                float lineH = h * (float)anim;
                
                if (lineMode.get() == LineMode.Box) {
                     float boxW = textWidth + (4 * scale);
                     NanoVGHelper.drawRoundRectOutline(itemBgX - (2 * scale), yPos, boxW, lineH, 0, lw, textColor);
                } else {
                     // Side Line (Left or Right based on alignment)
                     float lx;
                     if (isAlignRight()) {
                         // Line on the Right edge
                         lx = itemBgX + textWidth + (2 * scale); 
                     } else {
                         // Line on the Left edge
                         lx = itemBgX - (2 * scale) - lw - (1 * scale);
                     }
                     NanoVGHelper.drawRect(lx, yPos, lw, lineH, textColor);
                }
            }
            
            yPos += h * anim;
            index++;
        }
    }

    private void calculateGradientTargetSize() {
        float scale = gradientScale.get().floatValue();
        float fontSize = customFontSize.get().floatValue();
        float spacing = gradientSpacing.get().floatValue();
        
        float maxW = 0;
        float totalH = 0;
        
        for (ModuleEntry entry : moduleEntries) {
            Module module = entry.module;
            if (getAnim(module) <= 0.001) continue;
            
            float textWidth = entry.textWidth;
            
            if (textWidth > maxW) maxW = textWidth;
            totalH += (fontSize + spacing) * scale * getAnim(module);
        }
        
        this.targetWidth = maxW;
        this.targetHeight = totalH;
    }

    private String getRandomCategoryIcon(Module module) {
        if (!moduleIconMap.containsKey(module)) {
            String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
            moduleIconMap.put(module, icon);
        }
        return moduleIconMap.get(module);
    }

    private Color getModuleColor(int index, int total, float offset, Style style) {
        ColorMode mode;
        if (style == Style.Sakura) mode = sakuraColorMode.get();
        else if (style == Style.Vape) mode = vapeColorMode.get();
        else mode = liquidGlassColorMode.get();

        switch (mode) {
            case Client:
                return ClickGui.color(index * 100);
            case Rainbow:
                float hue = (System.currentTimeMillis() % 2000) / 2000f;
                return Color.getHSBColor((hue - (index * 0.05f)) % 1.0f, 0.6f, 1.0f);
            case Double:
                Color c1, c2;
                if (style == Style.Sakura) { c1 = sakuraColor1.get(); c2 = sakuraColor2.get(); }
                else if (style == Style.Vape) { c1 = vapeColor1.get(); c2 = vapeColor2.get(); }
                else { c1 = liquidGlassColor1.get(); c2 = liquidGlassColor2.get(); }
                return interpolateColors(c1, c2, (Math.sin(System.currentTimeMillis() / 400.0 + index * 0.5) + 1) / 2.0);
            case Single:
                if (style == Style.Sakura) return sakuraColor1.get();
                else if (style == Style.Vape) return vapeColor1.get();
                else return liquidGlassColor1.get();
        }
        return Color.WHITE;
    }

    private Color interpolateColors(Color c1, Color c2, double ratio) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
        return new Color(r, g, b);
    }

    private static class ModuleEntry {
        final Module module;
        final String text;
        final float textWidth;

        ModuleEntry(Module module, String text, float textWidth) {
            this.module = module;
            this.text = text;
            this.textWidth = textWidth;
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
