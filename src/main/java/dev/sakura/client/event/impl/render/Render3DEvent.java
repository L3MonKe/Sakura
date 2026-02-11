package dev.sakura.client.event.impl.render;

import net.minecraft.client.util.math.MatrixStack;

public record Render3DEvent(MatrixStack getMatrices, float getTickDelta) {
    public Render3DEvent {
    }
}
