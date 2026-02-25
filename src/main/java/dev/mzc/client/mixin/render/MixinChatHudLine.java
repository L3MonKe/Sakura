package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.impl.misc.BetterChat;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHudLine.Visible.class)
public class MixinChatHudLine {
    @Inject(method = "indicator", at = @At("HEAD"), cancellable = true)
    private void injectIndicator(CallbackInfoReturnable<MessageIndicator> cir) {
        BetterChat chatAnimation = Sakura.MODULES.getModule(BetterChat.class);
        if (chatAnimation != null && chatAnimation.isEnabled() && chatAnimation.removeMessageIndicator.get()) {
            cir.setReturnValue(null);
        }
    }
}
