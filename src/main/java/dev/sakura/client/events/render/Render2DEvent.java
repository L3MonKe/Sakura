package dev.sakura.client.events.render;

import net.minecraft.client.gui.DrawContext;

public record Render2DEvent(DrawContext getContext) {
}
