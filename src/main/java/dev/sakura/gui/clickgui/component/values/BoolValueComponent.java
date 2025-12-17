package dev.sakura.gui.clickgui.component.values;

import dev.sakura.gui.Component;
import dev.sakura.module.impl.client.ClickGui;
import dev.sakura.nanovg.NanoVGRenderer;
import dev.sakura.nanovg.font.FontLoader;
import dev.sakura.nanovg.util.NanoVGHelper;
import dev.sakura.utils.animations.Direction;
import dev.sakura.utils.animations.impl.SmoothStepAnimation;
import dev.sakura.utils.render.RenderUtils;
import dev.sakura.values.impl.BoolValue;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

/**
 * @Author：Gu-Yuemang
 * @Date：2025/11/14 18:49
 */
public class BoolValueComponent extends Component {
    private final BoolValue setting;
    private final SmoothStepAnimation toggleAnimation = new SmoothStepAnimation(175, 1);

    public BoolValueComponent(BoolValue setting) {
        this.setting = setting;
        this.toggleAnimation.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        setHeight(14);
        this.toggleAnimation.setDirection(setting.getValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawString(setting.getName(), getX(), getY(), FontLoader.greycliffRegular(7.5f), 7.5f, Color.WHITE);
            NanoVGHelper.drawRoundRect(getX() + getWidth() - 15, getY() - 7, 15, 8, 4, setting.get() ? ClickGui.color(0).darker() : new Color(70, 70, 70));
            NanoVGHelper.drawCircle(getX() + getWidth() - 11 + 7 * toggleAnimation.getOutput().floatValue(), getY() - 3, 3, setting.get() ? Color.WHITE : new Color(150, 150, 150));
        });
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (RenderUtils.isHovering(getX() + getWidth() - 15, getY() - 7, 15, 8, (float) mouseX, (float) mouseY) && mouseButton == 0) {
            this.setting.set(!this.setting.get());
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isVisible() {
        return this.setting.isAvailable();
    }
}
