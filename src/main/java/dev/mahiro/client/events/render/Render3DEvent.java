package dev.mahiro.client.events.render;

import dev.mahiro.client.auth.AuthGate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public record Render3DEvent(MatrixStack getMatrices, float getTickDelta) {
    public Render3DEvent {
        AuthGate.doTickCheck(MinecraftClient.getInstance());
    }
}
