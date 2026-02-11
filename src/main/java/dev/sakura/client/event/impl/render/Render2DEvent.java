package dev.sakura.client.event.impl.render;

import net.minecraft.client.gui.DrawContext;

public record Render2DEvent(DrawContext getContext) {
}
