package dev.sakura.client.mixin.render;

import dev.sakura.client.module.impl.player.NameProtect;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatHud.class)
public class MixinChatHudNameProtect {
    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), argsOnly = true)
    private Text hookAddMessage(Text text) {
        Text replaced = NameProtect.getGradientReplacement(text);
        if (replaced != text) {
            // Log for debugging if needed
        }
        return replaced;
    }
}
