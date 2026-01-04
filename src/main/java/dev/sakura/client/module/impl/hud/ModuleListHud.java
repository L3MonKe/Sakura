package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.EaseInOutQuad;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class ModuleListHud extends HudModule {
    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final NumberValue<Double> animationSpeed = new NumberValue<>("AnimationSpeed", "动画速度", 0.2, 0.05, 0.5, 0.05);
    private final BoolValue showCategory = new BoolValue("ShowCategory", "显示分类", true);
    private final NumberValue<Double> maxWidth = new NumberValue<>("MaxWidth", "最大宽度", 150.0, 50.0, 300.0, 5.0);
    private final NumberValue<Double> maxHeight = new NumberValue<>("MaxHeight", "最大高度", 200.0, 50.0, 500.0, 10.0);
    private final BoolValue alignRight = new BoolValue("AlignRight", "右对齐", false);
    private final BoolValue rainbowColor = new BoolValue("RainbowColor", "彩虹色", false);
    private final BoolValue hideHudModules = new BoolValue("HideHudModules", "隐藏HUD模块", false);
    private final NumberValue<Double> itemSpacing = new NumberValue<>("ItemSpacing", "项目间距", 7.0, 0.0, 10.0, 0.5);
    private final NumberValue<Double> hudScale = new NumberValue<>("HudScale", "HUD缩放", 1.1, 0.5, 2.0, 0.1);
    private final NumberValue<Integer> suffixStyle = new NumberValue<>("SuffixStyle", "后缀符号", 0, 0, 3, 1);


    private final List<ModuleEntry> moduleEntries = new ArrayList<>();
    private static ModuleListHud instance;
    private final java.util.Map<Module, EaseInOutQuad> moduleAnimations = new java.util.HashMap<>();
    private float targetWidth = 0;
    private float targetHeight = 0;
    private float currentWidth = 0;
    private float currentHeight = 0;
    private float scrollOffset = 0;

    private int iconImage = -1;
    private final BoolValue showIcon = new BoolValue("ShowIcon", "显示图标", true);
    // 图标大小固定为15

    private float rotationAngle = 0.0f;
    private long lastUpdateTime = 0;

    private final List<Particle> particles = new ArrayList<>();
    private final BoolValue enableParticles = new BoolValue("Enable Particles", "启用粒子", true);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 1.0, 0.1, 5.0, 0.1);
    private final NumberValue<Integer> particleCount = new NumberValue<>("Particle Count", "粒子数量", 10, 0, 50, 1, enableParticles::get);
    private final NumberValue<Double> particleSize = new NumberValue<>("Particle Size", "粒子大小", 2.0, 1.0, 5.0, 0.1, enableParticles::get);
    private final NumberValue<Double> particleSpeed = new NumberValue<>("Particle Speed", "粒子速度", 1.0, 0.1, 3.0, 0.1, enableParticles::get);


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
        this.currentContext = context;
        update();
        ensureWithinScreenBounds();
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
    }

    @Override
    public void renderInEditor(DrawContext context, float mouseX, float mouseY) {
        handleDrag(mouseX, mouseY);
        this.currentContext = context;
        update();
        NanoVGRenderer.INSTANCE.draw(vg -> {
            float scaledWidth = currentWidth * hudScale.get().floatValue();
            float scaledHeight = currentHeight * hudScale.get().floatValue();
            NanoVGHelper.drawRect(x, y - 5, scaledWidth, scaledHeight + 4,
                    dragging ? new Color(ClickGui.color(0).getRed(), ClickGui.color(0).getGreen(), ClickGui.color(0).getBlue(), 80) : BACKGROUND_COLOR);

            renderContent();
        });
    }

    public float getRadius() {
        return radius.get().floatValue();
    }

    @Override
    public void onRenderContent() {
    }

    private boolean isHudEditorOpen() {
        HudEditor editor = Sakura.MODULES.getModule(HudEditor.class);
        return editor != null && editor.isEnabled();
    }

    private void handleDrag(float mouseX, float mouseY) {
        if (!dragging) return;
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        if (alignRight.get() && !isHudEditorOpen()) {
            x = sw - (currentWidth * hudScale.get().floatValue());
        } else {
            x = clamp(mouseX - dragX, 0, sw - (currentWidth * hudScale.get().floatValue()));
        }
        y = clamp(mouseY - dragY, 0, sh - (currentHeight * hudScale.get().floatValue()));
        relativeX = x / sw;
        relativeY = y / sh;
    }

    private void update() {
        float oldWidth = currentWidth;
        int oldScreenWidth = mc.getWindow().getScaledWidth();
        int oldScreenHeight = mc.getWindow().getScaledHeight();
        updateModuleList();
        calculateTargetSize();
        float speed = animationSpeed.get().floatValue();
        currentWidth += (targetWidth - currentWidth) * speed;
        currentHeight += (targetHeight - currentHeight) * speed;

        if (alignRight.get() && !isHudEditorOpen()) {
            int screenWidth = mc.getWindow().getScaledWidth();
            int screenHeight = mc.getWindow().getScaledHeight();

            if (Math.abs(currentWidth - oldWidth) > 0.1f || screenWidth != oldScreenWidth) {
                x = screenWidth - (currentWidth * hudScale.get().floatValue());
                if (x < 0) x = 0;
            }

            if (screenHeight != oldScreenHeight) {
                float scaledHeight = currentHeight * hudScale.get().floatValue();
                if (y + scaledHeight > screenHeight) {
                    y = screenHeight - scaledHeight;
                    if (y < 0) y = 0;
                }
            }
        }

        this.width = currentWidth * hudScale.get().floatValue();
        this.height = currentHeight * hudScale.get().floatValue();
        updateScroll();

        updateRotation();

        updateParticles();

        if (iconImage == -1 && showIcon.get()) {
            loadIcon();
        }
    }

    private final java.util.Map<Module, String> moduleIconMap = new java.util.HashMap<>();

    private void updateModuleList() {
        List<Module> enabledModules = Sakura.MODULES.getAllModules().stream()
                .filter(Module::isEnabled)
                .filter(module -> !module.isHidden())
                .filter(module -> !hideHudModules.get() || !(module instanceof HudModule))
                .sorted((m1, m2) -> {
                    String displayText1 = getDisplayText(m1);
                    String displayText2 = getDisplayText(m2);
                    int font = FontLoader.medium(10);
                    float scale = hudScale.get().floatValue();
                    float width1 = NanoVGHelper.getTextWidth(displayText1, font, 10 * scale);
                    float width2 = NanoVGHelper.getTextWidth(displayText2, font, 10 * scale);
                    return Float.compare(width2, width1);
                })
                .toList();

        java.util.Iterator<java.util.Map.Entry<Module, String>> iterator = moduleIconMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<Module, String> entry = iterator.next();
            Module module = entry.getKey();
            if (!enabledModules.contains(module)) {
                iterator.remove();
            }
        }

        moduleEntries.clear();
        for (Module module : enabledModules) {
            moduleEntries.add(new ModuleEntry(module));
            if (!moduleIconMap.containsKey(module)) {
                String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
                moduleIconMap.put(module, icon);
            }
        }
    }

    private void calculateTargetSize() {
        if (moduleEntries.isEmpty()) {
            targetWidth = 50;
            targetHeight = 20;

            if (showIcon.get()) {
                float iconRenderSize = 13.0f * hudScale.get().floatValue();
                targetHeight = (PADDING_Y * 2 + iconRenderSize + 4 - 2) * hudScale.get().floatValue();
            }
            return;
        }
        float maxWidthValue = maxWidth.get().floatValue();
        float maxHeightValue = maxHeight.get().floatValue();
        float scale = hudScale.get().floatValue();
        float totalHeight = PADDING_Y * 2 * scale;

        if (showIcon.get()) {
            float iconRenderSize = 13.0f * scale;
            totalHeight += iconRenderSize + 4 * scale;
        }

        float maxTextWidth = 0;
        int font = FontLoader.medium(10);

        for (ModuleEntry entry : moduleEntries) {
            EaseInOutQuad animation = moduleAnimations.get(entry.module);
            double animationValue = animation != null ? animation.getOutput() : 1.0;

            if (animationValue > 0.01) {
                String text = getDisplayText(entry.module);
                float textWidth = NanoVGHelper.getTextWidth(text, font, 10 * scale);
                if (showCategory.get()) {
                    textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                }
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                totalHeight += (10 + itemSpacing.get().floatValue()) * scale;
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
                    float textWidth = NanoVGHelper.getTextWidth(text, font, 10 * scale);
                    if (showCategory.get()) {
                        textWidth += (ICON_BACKGROUND_WIDTH + CATEGORY_ICON_SPACING) * scale;
                    }
                    maxTextWidth = Math.max(maxTextWidth, textWidth);
                    totalHeight += (10 + itemSpacing.get().floatValue()) * scale;
                }
                totalHeight -= itemSpacing.get().floatValue() * scale;
            } else {
                totalHeight -= itemSpacing.get().floatValue() * scale;
            }
        }

        if (showIcon.get()) {
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

    private void ensureWithinScreenBounds() {
        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();
        float scaledWidth = currentWidth * hudScale.get().floatValue();
        float scaledHeight = currentHeight * hudScale.get().floatValue();

        if (alignRight.get()) {
            x = screenWidth - scaledWidth;
            if (x < 0) x = 0;
        } else {
            if (x < 0) x = 0;
            float rightEdge = x + scaledWidth;
            if (rightEdge > screenWidth) {
                x = screenWidth - scaledWidth;
                if (x < 0) x = 0;
            }
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

        float currentY = y + (PADDING_Y * scale) - (scrollOffset * scale);

        if (showIcon.get() && iconImage != -1) {
            float iconRenderSize = 13.0f * scale;
            float iconX = alignRight.get() ?
                    x + (currentWidth * scale) - iconRenderSize - (PADDING_X * scale) :
                    x + (PADDING_X * scale);
            float iconY = currentY - (2 * scale);

            // 添加纯黑背景
            String sakuraText = "ModuleList";
            int font = FontLoader.bold(11);
            float textWidth = NanoVGHelper.getTextWidth(sakuraText, font, 11 * scale);
            float textHeight = NanoVGHelper.getFontHeight(font, 11 * scale);
            float totalWidth = iconRenderSize + textWidth + (4 * scale) + 4;
            float bgX = alignRight.get() ?
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


            float textX = alignRight.get() ?
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
            if (showCategory.get()) {
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
            if (alignRight.get() && showCategory.get()) {
                iconBgX = x + (currentWidth * scale) - (PADDING_X * scale) - (ICON_BACKGROUND_WIDTH * scale);
                textX = iconBgX - (CATEGORY_ICON_SPACING * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : suffixWidth + (2 * scale));
            } else if (!alignRight.get() && showCategory.get()) {
                iconBgX = itemX + (6 * scale);
                textX = iconBgX + (ICON_BACKGROUND_WIDTH * scale) + (CATEGORY_ICON_SPACING * scale);
            } else {
                textX = alignRight.get() ? x + (currentWidth * scale) - (PADDING_X * scale) - moduleNameWidth - (suffix.isEmpty() ? 0 : suffixWidth + (2 * scale)) : itemX + (PADDING_X * scale);
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
            float animatedTextX = alignRight.get() ?
                    textX + (itemWidth - animatedItemWidth) : textX;

            if (enableBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(
                        alignRight.get() && showCategory.get() ?
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
                        alignRight.get() && showCategory.get() ?
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
                float animatedIconBgX = alignRight.get() ?
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

            Color textColor = rainbowColor.get() ?
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
            currentY += ((10 + itemSpacing.get().floatValue()) * scale);
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
                    float iconX = alignRight.get() ?
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
        iconImage = NanoVGHelper.loadTexture("/assets/sakura/icons/icon_32x32.png");
    }

    @Override
    public void onDisable() {
        super.onDisable();
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

    private String getRandomCategoryIcon(Module module) {
        if (!moduleIconMap.containsKey(module)) {
            String icon = ICON_SET[RANDOM.nextInt(ICON_SET.length)];
            moduleIconMap.put(module, icon);
        }
        return moduleIconMap.get(module);
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