package dev.mahiro.client.mixin.render;

import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.gui.auth.AuthScreen;
import dev.mahiro.client.gui.mainmenu.MainMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void redirectToMainMenu(CallbackInfo ci) {
        ci.cancel();
        if (AuthGate.isVerified()) {
            mc.setScreen(new MainMenuScreen());
        } else {
            mc.setScreen(new AuthScreen(new MainMenuScreen()));
        }
    }
}
