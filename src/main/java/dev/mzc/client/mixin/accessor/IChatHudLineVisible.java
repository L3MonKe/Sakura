package dev.mzc.client.mixin.accessor;

import net.minecraft.client.gui.hud.ChatHudLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatHudLine.Visible.class)
public interface IChatHudLineVisible {
    @Accessor("addedTime")
    int getAddedTime();
}
