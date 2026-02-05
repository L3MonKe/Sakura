package dev.mahiro.client.module.impl.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.color.ColorUtil;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.chunk.WorldChunk;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ChestESP extends Module {
    private final BoolValue chest = new BoolValue("Chest", "箱子", true);
    private final BoolValue enderChest = new BoolValue("EnderChest", "末影箱", true);
    private final BoolValue shulkerBox = new BoolValue("ShulkerBox", "潜影盒", false);

    private final BoolValue throughWalls = new BoolValue("ThroughWalls", "穿墙", true);

    private final BoolValue fill = new BoolValue("Fill", "填充", true);
    private final NumberValue<Double> fillOpacity = new NumberValue<>("FillOpacity", "填充透明度", 0.12, 0.0, 1.0, 0.01, fill::get);

    private final BoolValue outline = new BoolValue("Outline", "描边", true);
    private final NumberValue<Double> outlineWidth = new NumberValue<>("OutlineWidth", "描边粗细", 1.8, 0.5, 6.0, 0.1, outline::get);

    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 64.0, 8.0, 256.0, 1.0);

    private final ColorValue color = new ColorValue("Color", "颜色", new Color(160, 210, 255, 230));

    public ChestESP() {
        super("ChestESP", "箱子透视", Category.Render);
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;

        boolean disableDepth = throughWalls.get();
        if (disableDepth) {
            GlStateManager._disableDepthTest();
            GlStateManager._depthMask(false);
        }

        Vec3d playerPos = mc.player.getEntityPos();
        double maxSq = range.get() * range.get();

        for (BlockEntity blockEntity : getLoadedBlockEntities(range.get())) {
            if (blockEntity == null) continue;

            BlockPos pos = blockEntity.getPos();
            Vec3d center = Vec3d.ofCenter(pos);
            if (playerPos.squaredDistanceTo(center) > maxSq) continue;

            BlockState state = mc.world.getBlockState(pos);
            if (!isTarget(blockEntity, state)) continue;

            Box box = getOutlineBox(state, pos);
            if (box == null) continue;
            box = box.expand(0.002);

            renderBox(event, box);
        }

        if (disableDepth) {
            GlStateManager._enableDepthTest();
            GlStateManager._depthMask(true);
        }
    }

    private Collection<BlockEntity> getLoadedBlockEntities(double range) {
        ChunkPos center = mc.player.getChunkPos();
        int chunkRadius = Math.max(1, (int) Math.ceil(range / 16.0));

        ArrayList<BlockEntity> out = new ArrayList<>();
        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
                if (chunk == null) continue;
                out.addAll(chunk.getBlockEntities().values());
            }
        }
        return out;
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
}
