package dev.mzc.client.gui.clickgui.vape;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.module.impl.client.Friend;
import dev.mzc.client.values.Value;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.StringValue;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Animation;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.DecelerateAnimation;
import dev.mzc.client.utils.animations.impl.EaseOutSine;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MZCClickGuiScreen extends Screen {

    private float x, y, width, height;
    private Category currentCategory = Category.Combat;
    private Module selectedModule;
    private float scrollY;
    private float settingsScrollY;
    
    // Dragging
    private boolean dragging;
    private float dragX, dragY;
    
    // Slider Dragging
    private boolean draggingSlider;
    private NumberValue draggingValue;
    
    // Color Picker Dragging
    private boolean draggingColor;
    private ColorValue draggingColorValue;
    private int draggingColorComponent; // 0=R, 1=G, 2=B, 3=A
    private boolean pickingHue;
    private boolean pickingSB;
    
    // Binding
    private boolean binding;
    
    // Layout
    private final float sidebarWidth = 100;
    private final float moduleListWidth = 160;

    // Animation
    private final Animation openingAnimation = new EaseOutSine(250, 1);
    private final Map<Module, Animation> moduleAnimations = new HashMap<>();
    private final Map<Value<?>, Animation> valueAnimations = new HashMap<>();
    private final Animation settingsAnimation = new DecelerateAnimation(250, 1);
    private float categorySelectorY = 0;
    private boolean firstRender = true;
    private float scale = 1.0f;
    
    // Search
    private String searchText = "";
    private boolean searching = false;
    
    // State
    private final Set<ColorValue> expandedColors = new HashSet<>();
    private boolean closing = false;
    private StringValue focusedStringValue;

    public MZCClickGuiScreen() {
        super(Text.literal("MZCClickGui"));
        this.width = 600;
        this.height = 400;
    }

    @Override
    protected void init() {
        if (x == 0 && y == 0) {
            this.x = (this.client.getWindow().getScaledWidth() - this.width) / 2;
            this.y = (this.client.getWindow().getScaledHeight() - this.height) / 2;
        }
        openingAnimation.setDirection(Direction.FORWARDS);
        openingAnimation.reset();
        settingsAnimation.setDirection(Direction.BACKWARDS);
        settingsAnimation.reset(); // Ensure settings panel starts hidden/ready
        searchText = "";
        searching = false;
        closing = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Handle window dragging
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
        
        // Handle Slider Dragging
        if (draggingSlider && draggingValue != null && selectedModule != null) {
            float settingsX = x + sidebarWidth + moduleListWidth;
            float settingsWidth = width - sidebarWidth - moduleListWidth;
            float sliderX = settingsX + 10;
            float sliderWidth = settingsWidth - 20;
            
            double min = draggingValue.getMin().doubleValue();
            double max = draggingValue.getMax().doubleValue();
            double percent = (mouseX - sliderX) / sliderWidth;
            percent = Math.max(0, Math.min(1, percent));
            double val = min + (max - min) * percent;
            
            if (draggingValue.get() instanceof Double) draggingValue.set(Math.round(val * 100.0) / 100.0);
            else if (draggingValue.get() instanceof Float) draggingValue.set((float)(Math.round(val * 100.0) / 100.0));
            else if (draggingValue.get() instanceof Integer) draggingValue.set((int)Math.round(val));
        }

        if (closing) {
            openingAnimation.setDirection(Direction.BACKWARDS);
            if (openingAnimation.getOutput().floatValue() < 0.05f) {
                Sakura.MODULES.getModule(ClickGui.class).setState(false);
                return;
            }
        }

        float animScale = openingAnimation.getOutput().floatValue();
        
        NanoVGRenderer.INSTANCE.draw(vg -> {
            float centerX = x + width / 2;
            float centerY = y + height / 2;

            NanoVGHelper.save();
            NanoVGHelper.translate(centerX, centerY);
            NanoVGHelper.scale(animScale, animScale);
            NanoVGHelper.translate(-centerX, -centerY);

            // Subtle black glow
            NanoVGHelper.drawShadow(x, y, width, height, 10, new Color(0, 0, 0, 150), 13.5f, 0, 0);

            if (ClickGui.mzcGlow.get()) {
                Color glowColor = ClickGui.mzcThemeColor.get();
                Color transparentGlow = new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 100);
                
                NanoVGHelper.drawRoundRectBloom(x, y, width, height, 10, transparentGlow);
            }

            NanoVGHelper.drawRoundRectScaled(x, y, width, height, 10, new Color(20, 20, 20, 255), 1.0f); // Use 1.0f as we already scaled the context
            
            // --- Sidebar ---
            NanoVGHelper.drawRoundRect(x, y, sidebarWidth, height, 10, new Color(30, 30, 30, 255));
            
            float catY = y + 20;
            
            // Calculate Target Y for Selector
            float targetY = y + 20;
            for (Category category : Category.values()) {
                 if (category == currentCategory) break;
                 targetY += 35;
            }
            
            // Interpolate Selector Y
             if (firstRender) {
                 categorySelectorY = targetY;
                 firstRender = false;
             } else {
                 categorySelectorY = categorySelectorY + (targetY - categorySelectorY) * 0.1f;
             }
            
            // Draw Selector
            NanoVGHelper.drawRoundRect(x, categorySelectorY + 5, 3, 15, 1.5f, ClickGui.mzcThemeColor.get());

            for (Category category : Category.values()) {
                boolean selected = category == currentCategory;
                Color color = selected ? ClickGui.mzcThemeColor.get() : new Color(150, 150, 150);
                
                if (selected) {
                     NanoVGHelper.drawRoundRect(x + 5, catY, sidebarWidth - 10, 25, 5, new Color(40, 40, 40));
                }

                NanoVGHelper.drawString(category.name(), x + 15, catY + 18, FontLoader.regular(16), 16, color);
                catY += 35;
            }
            
            // --- Module List ---
            float modListX = x + sidebarWidth;
            // Background for module list
            NanoVGHelper.drawRoundRect(modListX, y, moduleListWidth, height, 0, new Color(25, 25, 25, 255));
            
            // Search Input Logic
            float searchBoxHeight = 35;
            float listStartY = y + 10;
            
            if (currentCategory == Category.Search) {
                float searchY = y + 10;
                NanoVGHelper.drawRoundRect(modListX + 10, searchY, moduleListWidth - 20, 25, 5, new Color(45, 45, 45));
                
                String displaySearch = searchText.isEmpty() && !searching ? "Type to search..." : searchText + (searching && (System.currentTimeMillis() % 1000 < 500) ? "_" : "");
                Color searchColor = searchText.isEmpty() && !searching ? Color.GRAY : Color.WHITE;
                
                org.lwjgl.nanovg.NanoVG.nvgScissor(vg, modListX + 10, searchY, moduleListWidth - 20, 25);
                NanoVGHelper.drawString(displaySearch, modListX + 15, searchY + 17, FontLoader.regular(14), 14, searchColor);
                org.lwjgl.nanovg.NanoVG.nvgResetScissor(vg);
                
                listStartY += searchBoxHeight;
            }
            
            List<Module> modules = getFilteredModules();
            
            // Scroll Limit Logic
            float contentHeight = 10 + modules.size() * 30;
            float maxScroll = (height - (currentCategory == Category.Search ? searchBoxHeight : 0)) - contentHeight;
            if (maxScroll > 0) maxScroll = 0;
            if (scrollY < maxScroll) scrollY = maxScroll;
            if (scrollY > 0) scrollY = 0;
            
            float modY = listStartY + scrollY;
            
            // Scissor for module list
            org.lwjgl.nanovg.NanoVG.nvgScissor(vg, modListX, listStartY, moduleListWidth, height - (listStartY - y));
            
            for (Module module : modules) {
                Animation anim = moduleAnimations.computeIfAbsent(module, m -> new DecelerateAnimation(200, 1));
                anim.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
                float progress = anim.getOutput().floatValue();

                if (modY + 30 > listStartY && modY < y + height) {
                    boolean isSelected = module == selectedModule;
                    Color bgColor = isSelected ? new Color(45, 45, 45) : new Color(35, 35, 35);
                    
                    // Interpolate Text Color
                    Color targetColor = ClickGui.mzcThemeColor.get();
                    int r = (int) (200 + (targetColor.getRed() - 200) * progress);
                    int g = (int) (200 + (targetColor.getGreen() - 200) * progress);
                    int b = (int) (200 + (targetColor.getBlue() - 200) * progress);
                    Color textColor = new Color(r, g, b);
                    
                    NanoVGHelper.drawRoundRect(modListX + 5, modY, moduleListWidth - 10, 25, 5, bgColor);
                    NanoVGHelper.drawString(module.getDisplayName(), modListX + 10, modY + 18, FontLoader.regular(15), 15, textColor);
                    
                    // Active indicator dot
                    if (progress > 0.05) {
                        NanoVGHelper.drawCircle(modListX + moduleListWidth - 15, modY + 12.5f, 3 * progress, textColor);
                    }
                }
                modY += 30;
            }
            org.lwjgl.nanovg.NanoVG.nvgResetScissor(vg);
            
            // --- Settings Panel ---
            float settingsX = modListX + moduleListWidth;
            float settingsWidth = width - sidebarWidth - moduleListWidth;
            float cornerRadius = ClickGui.mzcCornerRadius.get().floatValue();
            
            // Animation Update
            settingsAnimation.setDirection(selectedModule != null ? Direction.FORWARDS : Direction.BACKWARDS);
            float settingsAnimVal = settingsAnimation.getOutput().floatValue();
            
            if (settingsAnimVal > 0.01) {
                float animOffsetX = (1 - settingsAnimVal) * 30;
                float drawSettingsX = settingsX + animOffsetX;
                float alphaMult = settingsAnimVal;
                
                int bgAlpha = (int)(255 * alphaMult);
                if (bgAlpha > 255) bgAlpha = 255;
                if (bgAlpha < 0) bgAlpha = 0;
                
                if (selectedModule != null) {
                     // Scissor Fixed to Settings Panel Area
                     org.lwjgl.nanovg.NanoVG.nvgScissor(vg, settingsX, y, settingsWidth, height);

                     // Draw Static Background (fading only)
                     NanoVGHelper.drawRoundRect(settingsX, y, settingsWidth, height, cornerRadius, new Color(25, 25, 25, bgAlpha));
                     
                     // Settings Scroll Limit Logic
                    float settingsContentHeight = 10 + 45; // Padding + Title(25) + Bind(20)
                    for (Value<?> value : selectedModule.getValues()) {
                         if (!value.isAvailable()) continue;
                         if (value instanceof NumberValue) settingsContentHeight += 35;
                         else if (value instanceof ColorValue colorValue) {
                             settingsContentHeight += 25; // Header
                             Animation anim = valueAnimations.computeIfAbsent(colorValue, v -> new DecelerateAnimation(200, 1));
                             anim.setDirection(expandedColors.contains(colorValue) ? Direction.FORWARDS : Direction.BACKWARDS);
                             float progress = anim.getOutput().floatValue();
                             if (progress > 0.01) {
                                 // Calculate square height based on width logic (approximated for scroll)
                                 float contentW = settingsWidth - 40;
                                 float hueBarWidth = 10;
                                 float gap = 5;
                                 
                                 float leftWidth = (contentW - gap) / 2;
                                 float sbWidth = leftWidth - hueBarWidth - gap;
                                 float panelHeight = sbWidth;
                                 
                                 settingsContentHeight += (panelHeight + 10) * progress; // Palette area (Square + padding)
                             }
                         }
                         else settingsContentHeight += 25;
                    }
                    
                    float maxSettingsScroll = height - settingsContentHeight;
                    if (maxSettingsScroll > 0) maxSettingsScroll = 0;
                    if (settingsScrollY < maxSettingsScroll) settingsScrollY = maxSettingsScroll;
                    if (settingsScrollY > 0) settingsScrollY = 0;
                    
                    float setY = y + 10 + settingsScrollY;
                    
                    // Title (Sliding)
                    NanoVGHelper.drawString(selectedModule.getDisplayName() + " Settings", drawSettingsX + 10, setY + 18, FontLoader.bold(18), 18, new Color(255, 255, 255, bgAlpha));
                    setY += 25;
                    
                    // Bind (Sliding)
            String bindText = "Bind: " + (binding ? "Listening..." : (selectedModule.getKey() == -1 ? "None" : 
                (selectedModule.getKey() < 0 ? "M" + (-100 - selectedModule.getKey()) : 
                org.lwjgl.glfw.GLFW.glfwGetKeyName(selectedModule.getKey(), 0))));
            if (bindText.contains("null")) bindText = "Bind: " + selectedModule.getKey();
                    Color bindColor = binding ? ClickGui.mzcThemeColor.get() : new Color(128, 128, 128);
                    NanoVGHelper.drawString(bindText, drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(bindColor.getRed(), bindColor.getGreen(), bindColor.getBlue(), bgAlpha));
                    setY += 20;
    
                    for (Value<?> value : selectedModule.getValues()) {
                        if (!value.isAvailable()) continue;
                        
                        if (setY + 100 > y && setY < y + height) { // Increased check range
                            if (value instanceof BoolValue boolValue) {
                                Animation boolAnim = valueAnimations.computeIfAbsent(value, v -> new DecelerateAnimation(200, 1));
                                boolAnim.setDirection(boolValue.get() ? Direction.FORWARDS : Direction.BACKWARDS);
                                float boolProgress = boolAnim.getOutput().floatValue();
                                
                                NanoVGHelper.drawString(value.getDisplayName(), drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(255, 255, 255, bgAlpha));
                                // Checkbox
                                NanoVGHelper.drawRoundRect(drawSettingsX + settingsWidth - 20, setY + 6, 12, 12, 3, new Color(60, 60, 60, bgAlpha));
                                if (boolProgress > 0.05) {
                                     float size = 8 * boolProgress;
                                     float cx = drawSettingsX + settingsWidth - 20 + 6;
                                     float cy = setY + 6 + 6;
                                     Color theme = ClickGui.mzcThemeColor.get();
                                     NanoVGHelper.drawRoundRect(cx - size/2, cy - size/2, size, size, 2, new Color(theme.getRed(), theme.getGreen(), theme.getBlue(), bgAlpha));
                                }
                            } else if (value instanceof NumberValue numberValue) {
                                String valStr = numberValue.get() instanceof Float || numberValue.get() instanceof Double ? String.format("%.2f", numberValue.get()) : numberValue.get().toString();
                                NanoVGHelper.drawString(value.getDisplayName() + ": " + valStr, drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(255, 255, 255, bgAlpha));
                                
                                // Slider bar
                                float sliderWidth = settingsWidth - 20;
                                float sliderX = drawSettingsX + 10;
                                float sliderY = setY + 22;
                                
                                double min = numberValue.getMin().doubleValue();
                                double max = numberValue.getMax().doubleValue();
                                double val = ((Number)numberValue.get()).doubleValue();
                                double percent = (val - min) / (max - min);
                                
                                NanoVGHelper.drawRoundRect(sliderX, sliderY, sliderWidth, 4, 2, new Color(60, 60, 60, bgAlpha));
                                Color theme = ClickGui.mzcThemeColor.get();
                                NanoVGHelper.drawRoundRect(sliderX, sliderY, (float)(sliderWidth * percent), 4, 2, new Color(theme.getRed(), theme.getGreen(), theme.getBlue(), bgAlpha));
                                
                                setY += 10; // Extra space for slider
                            } else if (value instanceof EnumValue enumValue) {
                                 NanoVGHelper.drawString(value.getDisplayName() + ": " + enumValue.get().toString(), drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(255, 255, 255, bgAlpha));
                            } else if (value instanceof StringValue stringValue) {
                                boolean focused = focusedStringValue == stringValue;
                                NanoVGHelper.drawString(value.getDisplayName() + ":", drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(255, 255, 255, bgAlpha));
                                
                                float inputW = 100;
                                float inputX = drawSettingsX + settingsWidth - inputW - 10;
                                float inputY = setY + 5;
                                float inputH = 15;
                                
                                NanoVGHelper.drawRoundRect(inputX, inputY, inputW, inputH, 3, new Color(40, 40, 40, bgAlpha));
                                NanoVGHelper.drawRoundRectOutline(inputX, inputY, inputW, inputH, 3, 1, new Color(60, 60, 60, bgAlpha));
                                
                                String text = stringValue.getText() + (focused && (System.currentTimeMillis() / 500 % 2 == 0) ? "_" : "");
                                
                                NanoVGHelper.save();
                                NanoVGHelper.intersectScissor(inputX, inputY, inputW, inputH);
                                NanoVGHelper.drawString(text, inputX + 5, inputY + 11.5f, FontLoader.regular(14), 14, new Color(230, 230, 230, bgAlpha));
                                NanoVGHelper.restore();
                            } else if (value instanceof ColorValue colorValue) {
                                NanoVGHelper.drawString(value.getDisplayName(), drawSettingsX + 10, setY + 18, FontLoader.regular(14), 14, new Color(255, 255, 255, bgAlpha));
                                
                                // Preview Box
                                float previewSize = 12;
                                NanoVGHelper.drawRoundRect(drawSettingsX + settingsWidth - 20, setY + 6, previewSize, previewSize, 3, colorValue.get());
                                
                                Animation anim = valueAnimations.computeIfAbsent(colorValue, v -> new DecelerateAnimation(200, 1));
                                anim.setDirection(expandedColors.contains(colorValue) ? Direction.FORWARDS : Direction.BACKWARDS);
                                float progress = anim.getOutput().floatValue();

                                if (progress > 0.01) {
                                    setY += 25;
                                    
                                    float contentX = drawSettingsX + 20;
                                    float contentW = settingsWidth - 40;
                                    float hueBarWidth = 10;
                                    float gap = 5;
                                    
                                    // Layout Calculation
                                    float leftWidth = (contentW - gap) / 2;
                                    float rightWidth = contentW - leftWidth - gap;
                                    
                                    float sbWidth = leftWidth - hueBarWidth - gap;
                                    float sbHeight = sbWidth; // Square
                                    float panelHeight = sbHeight;
                                    
                                    float panelX = contentX;
                                    float panelY = setY;
                                    float hueX = panelX + sbWidth + gap;
                                    float hueY = panelY;
                                    
                                    float slidersX = contentX + leftWidth + gap;
                                    float sliderTrackWidth = rightWidth - 6; // Reduce width to prevent knob clipping
                                    float sliderH = (panelHeight - (3 * gap)) / 4;
                                    
                                    float totalHeight = panelHeight + 10;
                                    float currentHeight = totalHeight * progress;
                                    
                                    // Scissor to animate expansion
                                    NanoVGHelper.save();
                                    NanoVGHelper.intersectScissor(contentX, setY, contentW, currentHeight);
                                    
                                    float[] hsb = {colorValue.getHue(), colorValue.getSaturation(), colorValue.getBrightness()};
                                    
                                    // Update Palette
                                    if (pickingSB && draggingColorValue == colorValue && sbWidth > 0 && sbHeight > 0) {
                                        colorValue.setSaturation(clamp01((float)((mouseX - panelX) / sbWidth)));
                                        colorValue.setBrightness(clamp01(1 - (float)((mouseY - panelY) / sbHeight)));
                                    }
                                    
                                    // Update Hue
                                    if (pickingHue && draggingColorValue == colorValue && panelHeight > 0) {
                                        colorValue.setHue(clamp01((float)((mouseY - hueY) / panelHeight)));
                                    }
                                    
                                    // Update Sliders
                                    if (draggingColor && draggingColorValue == colorValue && sliderTrackWidth > 0) {
                                        float val = clamp01((float)((mouseX - slidersX) / sliderTrackWidth));
                                        Color c = colorValue.get();
                                        int r = c.getRed();
                                        int g = c.getGreen();
                                        int b = c.getBlue();
                                        int a = c.getAlpha();
                                        
                                        switch (draggingColorComponent) {
                                            case 0: r = (int)(val * 255); break;
                                            case 1: g = (int)(val * 255); break;
                                            case 2: b = (int)(val * 255); break;
                                            case 3: a = (int)(val * 255); break;
                                        }
                                        colorValue.set(new Color(r, g, b, a));
                                    }
                                    
                                    // Refresh HSB
                                    hsb[0] = colorValue.getHue();
                                    hsb[1] = colorValue.getSaturation();
                                    hsb[2] = colorValue.getBrightness();
                                    
                                    // HSB Box
                                    drawRoundedGradientRect3(panelX, panelY, sbWidth, sbHeight, 3,
                                            Color.getHSBColor(0, 0, 0),
                                            Color.getHSBColor(0, 0, 1),
                                            Color.getHSBColor(0, 0, 0),
                                            Color.getHSBColor(hsb[0], 1, 1));
                                    NanoVGHelper.drawRoundRectOutline(panelX, panelY, sbWidth, sbHeight, 3, 0.75f * scale, new Color(0, 0, 0, 120));

                                    // Hue Bar
                                    drawVerticalHueBar(hueX, hueY, hueBarWidth, panelHeight, hsb[0], 3);
                                    
                                    // Sliders
                                    Color c = colorValue.get();
                                    int[] comps = {c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()};
                                    String[] labels = {"R", "G", "B", "A"};
                                    
                                    for (int i = 0; i < 4; i++) {
                                        float sy = panelY + i * (sliderH + gap);
                                        float sw = sliderTrackWidth;
                                        
                                        // Label
                                        NanoVGHelper.drawString(labels[i] + ": " + comps[i], slidersX, sy + 8, FontLoader.regular(12), 12, new Color(200, 200, 200));
                                        
                                        // Track
                                        float trackY = sy + 14;
                                        NanoVGHelper.drawRoundRect(slidersX, trackY, sw, 4, 2, new Color(60, 60, 60));
                                        NanoVGHelper.drawRoundRect(slidersX, trackY, sw * (comps[i] / 255f), 4, 2, ClickGui.mzcThemeColor.get());
                                        NanoVGHelper.drawCircle(slidersX + sw * (comps[i] / 255f), trackY + 2, 4, Color.WHITE);
                                    }
                                    
                                    // Picker Indicator
                                    float pickerY = panelY + (sbHeight * (1 - hsb[2]));
                                    float pickerX = panelX + (sbWidth * hsb[1] - 1);
                                    pickerY = Math.max(Math.min(panelY + sbHeight - 2, pickerY), panelY - 2);
                                    pickerX = Math.max(Math.min(panelX + sbWidth - 2, pickerX), panelX - 2);
                                    
                                    NanoVGHelper.drawRect(pickerX, pickerY, 2, 2, new Color(255, 255, 255));
                                    
                                    NanoVGHelper.restore();
                                    
                                    setY += currentHeight;
                                    setY -= 25; // Compensate for loop increment
                                }
                            }
                        }
                        setY += 25;
                    }
                    org.lwjgl.nanovg.NanoVG.nvgResetScissor(vg);
                 }
             } else {
                NanoVGHelper.drawCenteredString("Select a module to edit settings", settingsX + settingsWidth / 2, y + height / 2, FontLoader.regular(16), 16, Color.GRAY);
            }
            
            org.lwjgl.nanovg.NanoVG.nvgRestore(vg); // Restore scale
        });
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (searching) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!searchText.isEmpty()) {
                    searchText = searchText.substring(0, searchText.length() - 1);
                    scrollY = 0; // Reset scroll on search change
                }
                return true;
            } else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                searching = false;
                if (searchText.isEmpty()) {
                    // Reset to default view if search was cleared
                }
                return true;
            } else if (keyCode == GLFW.GLFW_KEY_V && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0) {
                 // Paste
                 String clipboard = this.client.keyboard.getClipboard();
                 if (clipboard != null) {
                     searchText += clipboard;
                     scrollY = 0;
                 }
                 return true;
            }
            return true; // Consume keys while searching
        }

        if (focusedStringValue != null) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                String text = focusedStringValue.getText();
                if (!text.isEmpty()) {
                    focusedStringValue.setText(text.substring(0, text.length() - 1));
                }
                return true;
            } else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                focusedStringValue = null;
                return true;
            }
            return true;
        }

        if (binding && selectedModule != null) {
            if (keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                selectedModule.setKey(-1);
            } else {
                selectedModule.setKey(keyCode);
            }
            binding = false;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (searching) {
            if (isValidChar(chr)) {
                searchText += chr;
                scrollY = 0;
                return true;
            }
        }
        if (focusedStringValue != null) {
            if (isValidChar(chr)) {
                focusedStringValue.setText(focusedStringValue.getText() + chr);
                return true;
            }
        }
        return super.charTyped(chr, modifiers);
    }

    private boolean isValidChar(char chr) {
        return chr >= 32 && chr != 127;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (binding && selectedModule != null) {
            // Handle mouse button binding
            // Button 0: Left, 1: Right, 2: Middle, 3+: Side buttons
            // Convention: key = -100 - button
            selectedModule.setKey(-100 - button);
            binding = false;
            return true;
        }

        // Dragging
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 20) {
            dragging = true;
            dragX = (float) (mouseX - x);
            dragY = (float) (mouseY - y);
            return true;
        }

        // Sidebar
        if (mouseX >= x && mouseX < x + sidebarWidth) {
             float catY = y + 20;
             for (Category category : Category.values()) {
                 if (mouseY >= catY && mouseY <= catY + 25) {
                     currentCategory = category;
                     scrollY = 0;
                     selectedModule = null; // Deselect when changing category
                     settingsAnimation.setDirection(Direction.BACKWARDS);
                     binding = false;
                     expandedColors.clear();
                     focusedStringValue = null;
                     
                     // Handle search reset
                     if (category != Category.Search) {
                         searchText = "";
                         searching = false;
                     } else {
                         searching = true; // Auto-focus search when entering Search category
                     }
                     return true;
                 }
                 catY += 35;
             }
        }
        
        // Module List
        float modListX = x + sidebarWidth;
        if (mouseX >= modListX && mouseX < modListX + moduleListWidth) {
             // Handle Search Box Click
             if (currentCategory == Category.Search) {
                 float searchY = y + 10;
                 if (mouseY >= searchY && mouseY <= searchY + 25) {
                     searching = true;
                     focusedStringValue = null;
                     binding = false;
                     return true;
                 }
             }

             float listStartY = y + 10 + (currentCategory == Category.Search ? 35 : 0);
             float modY = listStartY + scrollY;
             List<Module> modules = getFilteredModules();
             for (Module module : modules) {
                 if (mouseY >= modY && mouseY <= modY + 25) {
                     if (button == 0) {
                         module.toggle();
                     } else if (button == 1) {
                         if (selectedModule != module) {
                             selectedModule = module;
                             settingsScrollY = 0;
                             settingsAnimation.setDirection(Direction.FORWARDS);
                             settingsAnimation.reset();
                             binding = false;
                             expandedColors.clear();
                             focusedStringValue = null;
                             
                             // Refresh values for Friend module immediately upon selection
                             if (selectedModule instanceof Friend) {
                                 ((Friend) selectedModule).refreshFriends();
                             }
                         }
                     }
                     return true;
                 }
                 modY += 30;
             }
        }
        
        // Settings Panel
        float settingsX = modListX + moduleListWidth;
        float settingsWidth = width - sidebarWidth - moduleListWidth;
        if (selectedModule != null && mouseX >= settingsX && mouseX < x + width) {
             float setY = y + 10 + settingsScrollY;
             
             // Bind Click
             if (mouseY >= setY + 25 && mouseY <= setY + 45) {
                 if (button == 0) {
                     binding = !binding;
                     return true;
                 } else if (button == 1) {
                     selectedModule.setKey(-1);
                     binding = false;
                     return true;
                 }
             }
             
             // Unfocus StringValue if clicked elsewhere in settings
             if (mouseY >= y) { // Simple check
                 // We don't null it immediately here because we need to check if we clicked on the StringValue itself
             }

             setY += 45; // Title (25) + Bind (20)
             
             boolean clickedWidget = false;
             
             for (Value<?> value : selectedModule.getValues()) {
                 if (!value.isAvailable()) continue;
                 
                 // Handle ColorValue interactions
                 if (value instanceof ColorValue colorValue) {
                     // Header Click
                     if (mouseY >= setY && mouseY <= setY + 20) {
                        if (button == 1) { // Right click to expand/collapse
                             if (expandedColors.contains(colorValue)) expandedColors.remove(colorValue);
                             else expandedColors.add(colorValue);
                             clickedWidget = true;
                             return true;
                        }
                     }
                     
                     if (expandedColors.contains(colorValue)) {
                         setY += 25;
                         
                         // Replicate layout for interaction detection
                         float contentX = settingsX + 20;
                         float contentW = settingsWidth - 40;
                         float hueBarWidth = 10;
                         float gap = 5;
                         
                         float leftWidth = (contentW - gap) / 2;
                         float rightWidth = contentW - leftWidth - gap;
                         
                         float sbWidth = leftWidth - hueBarWidth - gap;
                         float sbHeight = sbWidth; 
                         float panelHeight = sbHeight;

                         float panelX = contentX;
                         float panelY = setY;
                         float hueX = panelX + sbWidth + gap;
                         float hueY = panelY;
                         
                         float slidersX = contentX + leftWidth + gap;
                         float sliderH = (panelHeight - (3 * gap)) / 4;
                         
                         // Check HSB Box
                         if (mouseX >= panelX && mouseX <= panelX + sbWidth && mouseY >= panelY && mouseY <= panelY + sbHeight) {
                             pickingSB = true;
                             draggingColorValue = colorValue;
                             return true;
                         }
                         
                         // Check Hue Bar
                         if (mouseX >= hueX && mouseX <= hueX + hueBarWidth && mouseY >= hueY && mouseY <= hueY + panelHeight) {
                             pickingHue = true;
                             draggingColorValue = colorValue;
                             return true;
                         }
                         
                         // Check Sliders
                         for (int i = 0; i < 4; i++) {
                             float sy = panelY + i * (sliderH + gap);
                             if (mouseX >= slidersX && mouseX <= slidersX + rightWidth && mouseY >= sy && mouseY <= sy + sliderH) {
                                 draggingColor = true;
                                 draggingColorValue = colorValue;
                                 draggingColorComponent = i;
                                 return true;
                             }
                         }
                         
                         setY += panelHeight + 10;
                         setY -= 25; // Compensate for loop
                     }
                 }

                 if (mouseY >= setY && mouseY <= setY + 20) {
                     if (value instanceof BoolValue boolValue) {
                         boolValue.set(!boolValue.get());
                         clickedWidget = true;
                         return true;
                     } else if (value instanceof EnumValue enumValue) {
                         if (button == 0) enumValue.cycle();
                         clickedWidget = true;
                         return true;
                     } else if (value instanceof StringValue stringValue) {
                         focusedStringValue = stringValue;
                         clickedWidget = true;
                         return true;
                     }
                 }
                 
                 // Handle slider click
                 if (value instanceof NumberValue numberValue) {
                     if (mouseY >= setY + 18 && mouseY <= setY + 28) {
                         draggingSlider = true;
                         draggingValue = numberValue;
                         clickedWidget = true;
                         return true;
                     }
                     setY += 10;
                 }
                 
                 setY += 25;
             }
             
             if (!clickedWidget) focusedStringValue = null;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        draggingSlider = false;
        draggingValue = null;
        draggingColor = false;
        draggingColorValue = null;
        pickingSB = false;
        pickingHue = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // Determine where we are scrolling
        if (mouseX >= x + sidebarWidth && mouseX < x + sidebarWidth + moduleListWidth) {
            scrollY += verticalAmount * 10;
        } else if (mouseX >= x + sidebarWidth + moduleListWidth) {
            settingsScrollY += verticalAmount * 10;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void close() {
        closing = true;
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }

    private float clamp01(float v) {
        if (Float.isNaN(v)) return 0.0f;
        return Math.max(0.0f, Math.min(1.0f, v));
    }

    private List<Module> getFilteredModules() {
        if (currentCategory != Category.Search) {
             return Sakura.MODULES.getModsByCategory(currentCategory);
        }
        if (searchText.isEmpty()) {
            return List.of(); 
        }
        String lower = searchText.toLowerCase();
        return Sakura.MODULES.getAllModules().stream()
                .filter(m -> m.getDisplayName().toLowerCase().contains(lower))
                .toList();
    }

    private void drawVerticalHueBar(float x, float y, float width, float height, float hue, float radius) {
        NanoVGHelper.drawRoundRect(x, y, width, height, radius, new Color(25, 25, 25));
        float inset = 1.0f * scale;
        float innerX = x + inset;
        float innerY = y + inset;
        float innerW = Math.max(0.0f, width - inset * 2);
        float innerH = Math.max(0.0f, height - inset * 2);

        if (innerW > 0.0f && innerH > 0.0f) {
            int segments = Math.max(64, Math.round(96.0f * scale));
            float segH = innerH / segments;
            float innerRadius = Math.max(0.0f, radius - inset);
            long vg = NanoVGRenderer.INSTANCE.getContext();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                NVGPaint paint = NVGPaint.malloc(stack);
                NVGColor c1 = NVGColor.malloc(stack);
                NVGColor c2 = NVGColor.malloc(stack);

                for (int i = 0; i < segments; i++) {
                    float h1 = i / (float) segments;
                    float h2 = (i + 1) / (float) segments;

                    Color col1 = Color.getHSBColor(h1, 1f, 1f);
                    Color col2 = Color.getHSBColor(h2, 1f, 1f);
                    NanoVG.nvgRGBA((byte) col1.getRed(), (byte) col1.getGreen(), (byte) col1.getBlue(), (byte) 255, c1);
                    NanoVG.nvgRGBA((byte) col2.getRed(), (byte) col2.getGreen(), (byte) col2.getBlue(), (byte) 255, c2);

                    float y0 = innerY + i * segH;
                    float y1 = (i == segments - 1) ? (innerY + innerH) : (y0 + segH);
                    float hSeg = (y1 - y0) + 0.75f * scale;

                    NanoVG.nvgLinearGradient(vg, innerX, y0, innerX, y0 + hSeg, c1, c2, paint);
                    NanoVG.nvgBeginPath(vg);

                    if (innerRadius > 0.0f) {
                        if (i == 0) {
                            NanoVG.nvgRoundedRectVarying(vg, innerX, y0, innerW, hSeg, innerRadius, innerRadius, 0.0f, 0.0f);
                        } else if (i == segments - 1) {
                            NanoVG.nvgRoundedRectVarying(vg, innerX, y0, innerW, hSeg, 0.0f, 0.0f, innerRadius, innerRadius);
                        } else {
                            NanoVG.nvgRect(vg, innerX, y0 - 0.5f, innerW, hSeg + 1.0f);
                        }
                    } else {
                        NanoVG.nvgRect(vg, innerX, y0 - 0.5f, innerW, hSeg + 1.0f);
                    }

                    NanoVG.nvgFillPaint(vg, paint);
                    NanoVG.nvgFill(vg);
                }
            }
        }
        NanoVGHelper.drawRoundRectOutline(x, y, width, height, radius, 0.75f * scale, new Color(0, 0, 0, 120));

        float handleY = y + hue * height;
        handleY = Math.max(y + 1, Math.min(y + height - 1, handleY));
        NanoVGHelper.drawRect(x - 1 * scale, handleY - 0.5f * scale, width + 2 * scale, 1.25f * scale, Color.WHITE);
        NanoVGHelper.drawRect(x - 1 * scale, handleY + 0.75f * scale, width + 2 * scale, 0.75f * scale, new Color(0, 0, 0, 100));
    }

    private void drawRoundedGradientRect3(float x, float y, float w, float h, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        float r = Math.max(0.0f, Math.min(radius, Math.min(w, h) / 2f));
        if (w <= 0.0f || h <= 0.0f) return;

        int strips = Math.max(64, Math.round(80.0f * scale));
        float stripH = h / strips;
        long vg = NanoVGRenderer.INSTANCE.getContext();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            NVGColor c1 = NVGColor.malloc(stack);
            NVGColor c2 = NVGColor.malloc(stack);

            for (int i = 0; i < strips; i++) {
                float y0 = y + i * stripH;
                float y1 = (i == strips - 1) ? (y + h) : (y0 + stripH);
                float yMid = (y0 + y1) * 0.5f;
                float t = (yMid - y) / h;

                float inset = roundedInsetAtY(yMid - y, h, r);
                float x0 = x + inset;
                float w0 = w - inset * 2f;
                if (w0 <= 0.0f) continue;

                Color leftColor = lerpColor(topLeft, bottomLeft, t);
                Color rightColor = lerpColor(topRight, bottomRight, t);

                NanoVG.nvgRGBA((byte) leftColor.getRed(), (byte) leftColor.getGreen(), (byte) leftColor.getBlue(), (byte) leftColor.getAlpha(), c1);
                NanoVG.nvgRGBA((byte) rightColor.getRed(), (byte) rightColor.getGreen(), (byte) rightColor.getBlue(), (byte) rightColor.getAlpha(), c2);

                float segH = (y1 - y0) + 1.0f;
                NanoVG.nvgLinearGradient(vg, x0, y0, x0 + w0, y0, c1, c2, paint);
                NanoVG.nvgBeginPath(vg);
                NanoVG.nvgRect(vg, x0, y0 - 0.5f, w0, segH + 1.0f);
                NanoVG.nvgFillPaint(vg, paint);
                NanoVG.nvgFill(vg);
            }
        }
    }

    private float roundedInsetAtY(float yFromTop, float height, float radius) {
        if (radius <= 0.0f) return 0.0f;
        float dyTop = Math.max(0.0f, Math.min(radius, yFromTop));
        float dyBottom = Math.max(0.0f, Math.min(radius, height - yFromTop));
        float dy = Math.min(dyTop, dyBottom);
        if (dy >= radius) return 0.0f;
        float v = radius - dy;
        float inside = Math.max(0.0f, radius * radius - v * v);
        return radius - (float) Math.sqrt(inside);
    }

    private Color lerpColor(Color a, Color b, float t) {
        float tt = Math.max(0.0f, Math.min(1.0f, t));
        int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * tt);
        int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * tt);
        int bl = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * tt);
        int al = Math.round(a.getAlpha() + (b.getAlpha() - a.getAlpha()) * tt);
        return new Color(r, g, bl, al);
    }
}
