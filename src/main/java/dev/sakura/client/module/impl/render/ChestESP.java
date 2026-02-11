package dev.sakura.client.module.impl.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.chunk.WorldChunk;

import java.awt.*;
import java.util.function.Function;

public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP", "箱子透视", Category.Render);
    }

    private final BoolValue chest = new BoolValue("Chest", "箱子", true);
    private final BoolValue enderChest = new BoolValue("Ender Chest", "末影箱", true);
    private final BoolValue shulkerBox = new BoolValue("Shulker Box", "潜影盒", false);

    private final BoolValue throughWalls = new BoolValue("Through Walls", "穿墙", true);

    private final BoolValue chams = new BoolValue("Chams", "模型透视", true);
    private final BoolValue chamsColorOverlay = new BoolValue("Chams Color", "模型颜色覆盖", false, chams::get);

    private final BoolValue fill = new BoolValue("Fill", "填充", true);
    private final NumberValue<Double> fillOpacity = new NumberValue<>("Fill Opacity", "填充透明度", 0.12, 0.0, 1.0, 0.01, fill::get);

    private final BoolValue outline = new BoolValue("Outline", "描边", true);
    private final NumberValue<Double> outlineWidth = new NumberValue<>("Outline Width", "描边粗细", 1.8, 0.5, 6.0, 0.1, outline::get);

    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 64.0, 8.0, 256.0, 1.0);

    private final ColorValue color = new ColorValue("Color", "颜色", new Color(160, 210, 255, 230));

    public static final RenderPipeline CHEST_CHAMS_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation("pipeline/sakura_chest_chams")
            .withShaderDefine("ALPHA_CUTOUT", 0.1f)
            .withShaderDefine("PER_FACE_LIGHTING")
            .withSampler("Sampler1")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withCull(false)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .build()
    );

    private static final Function<Identifier, RenderLayer> CHEST_CHAMS_LAYER = Util.memoize(atlas -> RenderLayer.of(
            "sakura_chest_chams",
            RenderSetup.builder(CHEST_CHAMS_PIPELINE)
                    .texture("Sampler0", atlas)
                    .useLightmap()
                    .useOverlay()
                    .crumbling()
                    .translucent()
                    .outlineMode(RenderSetup.OutlineMode.AFFECTS_OUTLINE)
                    .build()
    ));

    public static RenderLayer chestChams(Identifier chestAtlas) {
        return CHEST_CHAMS_LAYER.apply(chestAtlas);
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;

        Vec3d playerPos = mc.player.getEntityPos();
        double r = range.get();
        double maxSq = r * r;

        ChunkPos center = mc.player.getChunkPos();
        int chunkRadius = Math.max(1, (int) Math.ceil(r / 16.0));

        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
                if (chunk == null) continue;

                for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    if (blockEntity == null) continue;

                    BlockPos pos = blockEntity.getPos();
                    if (pos.getSquaredDistance(playerPos.x, playerPos.y, playerPos.z) > maxSq) continue;

                    BlockState state = mc.world.getBlockState(pos);
                    if (!isTarget(blockEntity, state)) continue;

                    Box box = getOutlineBox(state, pos);
                    if (box == null) continue;

                    renderBox(event, box.expand(0.002));
                }
            }
        }
    }

    private void renderBox(Render3DEvent event, Box box) {
        Color base = color.get();

        if (fill.get()) {
            int c = ColorUtil.applyOpacity(base, fillOpacity.get().floatValue()).getRGB();
            Render3DUtil.drawFilledBox(event.getMatrices(), box, c);
        }

        if (outline.get()) {
            Render3DUtil.drawOutlineBox(event.getMatrices(), box, base.getRGB(), outlineWidth.get().floatValue());
        }
    }

    private boolean isTarget(BlockEntity blockEntity, BlockState state) {
        if (blockEntity instanceof ChestBlockEntity) return chest.get();
        if (blockEntity instanceof EnderChestBlockEntity) return enderChest.get();
        if (blockEntity instanceof ShulkerBoxBlockEntity) return shulkerBox.get();
        return false;
    }

    private Box getOutlineBox(BlockState state, BlockPos pos) {
        VoxelShape shape = state.getOutlineShape(mc.world, pos);
        if (shape.isEmpty()) return null;
        return shape.getBoundingBox().offset(pos);
    }

    public boolean isThroughWalls() {
        return throughWalls.get();
    }

    public boolean isChamsEnabled() {
        return chams.get();
    }

    public int getChamsTintColor() {
        if (!chamsColorOverlay.get()) {
            return -1;
        }
        return color.get().getRGB();
    }
}
