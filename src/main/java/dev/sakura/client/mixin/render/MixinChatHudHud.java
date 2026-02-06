package dev.sakura.client.mixin.render;

import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.hud.ChatHud$Hud")
public class MixinChatHudHud {
    @Redirect(method = "fill(IIIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectFill(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        if (x1 < 0 && x2 > 0) {
            return;
        }
        context.fill(x1, y1, x2, y2, color);
    }
}
