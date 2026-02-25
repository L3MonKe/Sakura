package dev.mzc.client.events.render.item;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public record HeldItemRendererEvent(MatrixStack getMatrices, Hand getHand) {
}