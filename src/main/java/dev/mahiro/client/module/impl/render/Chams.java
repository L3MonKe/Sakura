package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;

import java.awt.*;

public class Chams extends Module {
    public Chams() {
        super("Chams", "产慕斯", Category.Render);
    }

    private final BoolValue keepTextures = new BoolValue("Keep Textures", "Keep Textures", true);
    public final BoolValue handItems = new BoolValue("Hand Items", "手持物品", false);
    private final BoolValue colorOverlay = new BoolValue("Color Overlay", "颜色显示", false);
    private final ColorValue color = new ColorValue("Color", "颜色", Color.WHITE, colorOverlay::get);

    public int getRGBAColor() {
        if (!colorOverlay.get()) {
            return -1;
        }

        Color themeColor = ColorUtil.interpolateColorsBackAndForth(10, 1, ClickGui.mainColor.get(), ClickGui.secondColor.get(), false);
        Color nowSelected = color.get();
        return Color.WHITE.equals(nowSelected) ? themeColor.getRGB() : nowSelected.getRGB();
    }

    public boolean isColorOverlay() {
        return colorOverlay.get();
    }

    public boolean shouldKeepTextures() {
        return keepTextures.get();
    }
}
