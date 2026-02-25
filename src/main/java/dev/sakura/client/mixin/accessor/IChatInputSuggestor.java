package dev.sakura.client.mixin.accessor;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatInputSuggestor.class)
public interface IChatInputSuggestor {
    @Accessor("window")
    ChatInputSuggestor.SuggestionWindow getWindow();
}
