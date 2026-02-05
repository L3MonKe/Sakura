package dev.mahiro.client.module.impl.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.awt.*;
import java.util.function.Function;

public class Chams extends Module {
    public Chams() {
        super("Chams", "产慕斯", Category.Render);
    }

    private final BoolValue keepTextures = new BoolValue("Keep Textures", "Keep Textures", true);
    public final BoolValue handItems = new BoolValue("Hand Items", "手持物品", false);
    private final BoolValue colorOverlay = new BoolValue("Color Overlay", "颜色显示", false);
    private final ColorValue color = new ColorValue("Color", "颜色", Color.WHITE, colorOverlay::get);

    public static final RenderPipeline MAHIRO_ENTITY_CHAMS_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET).withLocation("pipeline/mahiro_entity_chams").withShaderDefine("ALPHA_CUTOUT", 0.1f).withShaderDefine("PER_FACE_LIGHTING").withSampler("Sampler1").withBlend(BlendFunction.TRANSLUCENT).withCull(false).withDepthBias(-1.0f, -1100000.0f).build());
    private static final Function<Identifier, RenderLayer> MAHIRO_ENTITY_CHAMS_LAYER = Util.memoize(texture -> RenderLayer.of("mahiro_entity_chams", RenderSetup.builder(MAHIRO_ENTITY_CHAMS_PIPELINE).texture("Sampler0", texture).useLightmap().useOverlay().crumbling().translucent().outlineMode(RenderSetup.OutlineMode.AFFECTS_OUTLINE).build()));

    public static RenderLayer getChamsLayer(Identifier texture) {
        return MAHIRO_ENTITY_CHAMS_LAYER.apply(texture);
    }

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
