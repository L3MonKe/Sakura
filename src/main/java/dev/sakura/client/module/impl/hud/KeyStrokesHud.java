package dev.sakura.client.module.impl.hud;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.DecelerateAnimation;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;

public class KeyStrokesHud extends HudModule {

    private final NumberValue<Double> offsetValue = new NumberValue<>("Offset", "偏移", 3.0, 2.5, 10.0, 0.5);
    private final NumberValue<Double> sizeValue = new NumberValue<>("Size", "大小", 25.0, 15.0, 35.0, 1.0);
    private final NumberValue<Double> radiusValue = new NumberValue<>("Radius", "圆角", 4.0, 0.0, 10.0, 1.0);
    private final NumberValue<Double> opacityValue = new NumberValue<>("Opacity", "透明度", 0.5, 0.1, 1.0, 0.05);
    private final BoolValue whiteMode = new BoolValue("White", "白色模式", true);
    private final BoolValue blur = new BoolValue("Blur", "模糊", false);
    private final NumberValue<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 8.0, 1.0, 20.0, 0.5, blur::get);

    private Button keyBindForward;
    private Button keyBindLeft;
    private Button keyBindBack;
    private Button keyBindRight;
    private Button keyBindJump;

    private float offset, size, increment, radius;

    public KeyStrokesHud() {
        super("KeyStrokes", "按键显示", 10, 100);
    }

    @Override
    public void renderInGame(DrawContext context) {
        HudEditor editor = Sakura.MODULES.getModule(HudEditor.class);
        if (editor != null && editor.isEnabled()) return;

        this.currentContext = context;
        calculateLayout();
        renderBlur(context);
        NanoVGRenderer.INSTANCE.draw(vg -> renderContent());
    }

    @Override
    public void renderInEditor(DrawContext context, float mouseX, float mouseY) {
        if (dragging) {
            int gameWidth = mc.getWindow().getScaledWidth();
            int gameHeight = mc.getWindow().getScaledHeight();
            x = Math.max(0, Math.min(mouseX - dragX, gameWidth - width));
            y = Math.max(0, Math.min(mouseY - dragY, gameHeight - height));
            relativeX = x / gameWidth;
            relativeY = y / gameHeight;
        }

        this.currentContext = context;
        calculateLayout();
        renderBlur(context);
        NanoVGRenderer.INSTANCE.draw(vg -> {
            renderContent();
            NanoVGHelper.drawRect(x, y, width, height,
                    dragging ? new Color(100, 100, 255, 80) : new Color(0, 0, 0, 50));
        });
    }

    @Override
    public void onRenderContent() {
    }

    private void calculateLayout() {
        if (mc.player == null) return;

        if (keyBindForward == null) {
            keyBindForward = new Button(mc.options.forwardKey);
            keyBindLeft = new Button(mc.options.leftKey);
            keyBindBack = new Button(mc.options.backKey);
            keyBindRight = new Button(mc.options.rightKey);
            keyBindJump = new Button(mc.options.jumpKey);
        }

        offset = offsetValue.get().floatValue();
        size = sizeValue.get().floatValue();
        increment = size + offset;
        radius = radiusValue.get().floatValue();

        this.width = increment * 3 - offset;
        this.height = increment * 3 - offset;
    }

    private void renderBlur(DrawContext context) {
        if (!blur.get() || mc.player == null) return;

        float blurValue = blurStrength.get().floatValue();

        Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                x + width / 2f - size / 2f, y, size, size, radius,
                new Color(0, 0, 0, 0), blurValue, 1.0f);

        Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                x, y + increment, size, size, radius,
                new Color(0, 0, 0, 0), blurValue, 1.0f);

        Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                x + increment, y + increment, size, size, radius,
                new Color(0, 0, 0, 0), blurValue, 1.0f);

        Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                x + increment * 2, y + increment, size, size, radius,
                new Color(0, 0, 0, 0), blurValue, 1.0f);

        Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                x, y + increment * 2, width, size, radius,
                new Color(0, 0, 0, 0), blurValue, 1.0f);
    }

    private void renderContent() {
        if (mc.player == null) return;

        keyBindForward.render(x + width / 2f - size / 2f, y, size, size, radius);
        keyBindLeft.render(x, y + increment, size, size, radius);
        keyBindBack.render(x + increment, y + increment, size, size, radius);
        keyBindRight.render(x + increment * 2, y + increment, size, size, radius);
        keyBindJump.render(x, y + increment * 2, width, size, radius);
    }

    private Color getBaseColor() {
        int rgb = whiteMode.get() ? new Color(255, 255, 255).getRGB() : new Color(30, 30, 30).getRGB();
        return ColorUtil.applyOpacity(new Color(rgb), opacityValue.get().floatValue());
    }

    private class Button {
        private final KeyBinding binding;
        private final Animation clickAnimation = new DecelerateAnimation(125, 1);

        public Button(KeyBinding binding) {
            this.binding = binding;
        }

        public void render(float x, float y, float w, float h, float radius) {
            Color baseColor = getBaseColor();
            clickAnimation.setDirection(binding.isPressed() ? Direction.FORWARDS : Direction.BACKWARDS);

            NanoVGHelper.drawRoundRect(x, y, w, h, radius, baseColor);

            String keyName = getKeyName(binding);
            int font = FontLoader.bold(h * 0.55f);
            float textWidth = NanoVGHelper.getTextWidth(keyName, font, h * 0.55f);
            float fontHeight = NanoVGHelper.getFontHeight(font, h * 0.55f);

            NanoVGHelper.drawString(
                    keyName,
                    x + w / 2f - textWidth / 2f,
                    y + h / 2f + fontHeight / 3f,
                    font,
                    h * 0.55f,
                    new Color(255, 255, 255, 255)
            );

            if (!clickAnimation.finished(Direction.BACKWARDS)) {
                float animation = clickAnimation.getOutput().floatValue();
                Color pressColor = ColorUtil.applyOpacity(
                        whiteMode.get() ? new Color(255, 255, 255) : new Color(30, 30, 30),
                        0.5f * animation
                );

                long vg = NanoVGRenderer.INSTANCE.getContext();

                nvgSave(vg);

                float centerX = x + w / 2f;
                float centerY = y + h / 2f;
                nvgTranslate(vg, centerX, centerY);
                nvgScale(vg, animation, animation);
                nvgTranslate(vg, -centerX, -centerY);

                float diff = (h / 2f) - radius;
                float animRadius = (h / 2f) - (diff * animation);

                NanoVGHelper.drawRoundRect(x, y, w, h, animRadius, pressColor);

                nvgRestore(vg);
            }
        }

        private String getKeyName(KeyBinding binding) {
            String translationKey = binding.getBoundKeyLocalizedText().getString();
            if (translationKey.length() == 1) {
                return translationKey.toUpperCase();
            }
            if (translationKey.toLowerCase().contains("space")) {
                return "—";
            }
            if (translationKey.toLowerCase().contains("shift")) {
                return "Shift";
            }
            if (translationKey.toLowerCase().contains("control") || translationKey.toLowerCase().contains("ctrl")) {
                return "Ctrl";
            }
            return translationKey;
        }
    }
}