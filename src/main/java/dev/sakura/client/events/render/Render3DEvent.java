package dev.sakura.client.events.render;

import net.minecraft.client.util.math.MatrixStack;

public record Render3DEvent(MatrixStack getMatrices, float getTickDelta) {
}
