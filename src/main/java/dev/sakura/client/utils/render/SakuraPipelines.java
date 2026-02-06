package dev.sakura.client.utils.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class SakuraPipelines {

    // Filled box
    private static final RenderPipeline FILLED_BOX_PIPELINE = RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET).withLocation(Identifier.of("sakura", "pipeline/filled_box")).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withCull(false).build();
    public static final RenderLayer FILLED_BOX = RenderLayer.of("sakura_filled_box", RenderSetup.builder(FILLED_BOX_PIPELINE).translucent().layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).build());

    // Lines
    private static final RenderPipeline LINES_PIPELINE = RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET).withLocation(Identifier.of("sakura", "pipeline/lines")).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).withCull(false).build();
    public static final RenderLayer LINES = RenderLayer.of("sakura_lines_no_depth", RenderSetup.builder(LINES_PIPELINE).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build());

    // Triangle fan
    private static final RenderPipeline TRIANGLE_FAN_PIPELINE = RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET).withLocation(Identifier.of("sakura", "pipeline/triangle_fan_no_depth")).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).withCull(false).withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_FAN).build();
    public static final RenderLayer TRIANGLE_FAN = RenderLayer.of("sakura_triangle_fan_no_depth", RenderSetup.builder(TRIANGLE_FAN_PIPELINE).translucent().layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).build());

}
