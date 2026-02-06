package dev.sakura.client.mixin.accessor;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.util.math.Rect2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatInputSuggestor.SuggestionWindow.class)
public interface ISuggestionWindow {
    @Accessor("area")
    Rect2i getArea();
}
