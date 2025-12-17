package dev.sakura.events.render;

import net.minecraft.client.util.math.MatrixStack;

public record Render3DEvent(MatrixStack matrices, float tickDelta) {
}
