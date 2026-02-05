package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.utils.render.Render3DUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private final List<Renderer> renderBoxes = new ArrayList<>();

    public RenderManager() {
        Mahiro.EVENT_BUS.subscribe(this);
    }

    public void add(BlockPos pos, Color sideColor, Color lineColor, int fadeTime) {
        add(new Box(pos), sideColor, lineColor, fadeTime, false);
    }

    public void add(BlockPos pos, Color sideColor, Color lineColor, int fadeTime, boolean shrink) {
        add(new Box(pos), sideColor, lineColor, fadeTime, shrink);
    }

    public void add(Box box, Color sideColor, Color lineColor, int fadeTime, boolean shrink) {
        renderBoxes.add(new Renderer(box, sideColor, lineColor, System.currentTimeMillis(), fadeTime, shrink));
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (renderBoxes.isEmpty()) return;

        long time = System.currentTimeMillis();
        renderBoxes.removeIf(block -> time - block.startTime > block.fadeTime);

        for (Renderer boxes : renderBoxes) {
            float factor = 1.0f - ((float) (time - boxes.startTime) / boxes.fadeTime);

            Color side = new Color(boxes.sideColor.getRed(), boxes.sideColor.getGreen(), boxes.sideColor.getBlue(), (int) (boxes.sideColor.getAlpha() * factor));
            Color line = new Color(boxes.lineColor.getRed(), boxes.lineColor.getGreen(), boxes.lineColor.getBlue(), (int) (boxes.lineColor.getAlpha() * factor));

            Box renderBox = boxes.box;
            if (boxes.shrink) {
                double centerX = (renderBox.minX + renderBox.maxX) / 2.0;
                double centerY = (renderBox.minY + renderBox.maxY) / 2.0;
                double centerZ = (renderBox.minZ + renderBox.maxZ) / 2.0;

                double sizeX = (renderBox.maxX - renderBox.minX) * factor;
                double sizeY = (renderBox.maxY - renderBox.minY) * factor;
                double sizeZ = (renderBox.maxZ - renderBox.minZ) * factor;

                double halfX = sizeX / 2.0;
                double halfY = sizeY / 2.0;
                double halfZ = sizeZ / 2.0;

                renderBox = new Box(centerX - halfX, centerY - halfY, centerZ - halfZ, centerX + halfX, centerY + halfY, centerZ + halfZ);
            }

            Render3DUtil.drawFullBox(event.getMatrices(), renderBox, side, line);
        }
    }

    private record Renderer(Box box, Color sideColor, Color lineColor, long startTime, int fadeTime, boolean shrink) {
    }
}
