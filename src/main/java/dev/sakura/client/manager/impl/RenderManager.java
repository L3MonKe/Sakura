package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.module.impl.settings.RenderSetting;
import dev.sakura.client.utils.render.Render3DUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private final List<Renderer> renderBoxes = new ArrayList<>();

    public RenderManager() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    public void addFading(BlockPos pos, Color sideColor, Color lineColor) {
        add(new Box(pos), sideColor, lineColor, true, false);
    }

    public void addShrinking(BlockPos pos, Color sideColor, Color lineColor) {
        add(new Box(pos), sideColor, lineColor, false, true);
    }

    public void add(BlockPos pos, Color sideColor, Color lineColor, boolean fade, boolean shrink) {
        add(new Box(pos), sideColor, lineColor, fade, shrink);
    }

    public void add(Box box, Color sideColor, Color lineColor, boolean fade, boolean shrink) {
        renderBoxes.add(new Renderer(box, sideColor, lineColor, System.currentTimeMillis(), fade, shrink));
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (renderBoxes.isEmpty()) return;

        long time = System.currentTimeMillis();
        long fadeTime = RenderSetting.fadeTime.get();

        renderBoxes.removeIf(block -> time - block.startTime() > fadeTime);

        for (Renderer boxes : renderBoxes) {
            long age = time - boxes.startTime();
            float progress = (float) age / (float) fadeTime;
            progress = MathHelper.clamp(progress, 0, 1);

            double scale = 1.0;
            if (boxes.shrink()) {
                scale = 1.0 - RenderSetting.easing.get().ease(progress);
                if (scale < 0) scale = 0;
            }

            float alphaFactor = boxes.fade ? MathHelper.clamp(1.0f - progress, 0.0f, 1.0f) : 1.0f;

            Color sideColor = boxes.sideColor();
            Color lineColor = boxes.lineColor();

            Color side = new Color(sideColor.getRed(), sideColor.getGreen(), sideColor.getBlue(), (int) (sideColor.getAlpha() * alphaFactor));
            Color line = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) (lineColor.getAlpha() * alphaFactor));

            Box renderBox = getRenderBox(boxes, scale);

            Render3DUtil.drawFullBox(event.getMatrices(), renderBox, side, line);
        }
    }

    private static Box getRenderBox(Renderer boxes, double scale) {
        Box renderBox = boxes.box();
        if (boxes.shrink()) {
            double centerX = renderBox.minX + (renderBox.maxX - renderBox.minX) / 2.0;
            double centerY = renderBox.minY + (renderBox.maxY - renderBox.minY) / 2.0;
            double centerZ = renderBox.minZ + (renderBox.maxZ - renderBox.minZ) / 2.0;

            double dx = (renderBox.maxX - renderBox.minX) / 2.0 * scale;
            double dy = (renderBox.maxY - renderBox.minY) / 2.0 * scale;
            double dz = (renderBox.maxZ - renderBox.minZ) / 2.0 * scale;

            renderBox = new Box(centerX - dx, centerY - dy, centerZ - dz, centerX + dx, centerY + dy, centerZ + dz);
        }
        return renderBox;
    }

    private record Renderer(Box box, Color sideColor, Color lineColor, long startTime, boolean fade, boolean shrink) {
    }
}
