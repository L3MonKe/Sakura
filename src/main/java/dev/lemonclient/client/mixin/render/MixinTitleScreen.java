package dev.lemonclient.client.mixin.render;

import dev.lemonclient.client.auth.AuthGate;
import dev.lemonclient.client.gui.auth.AuthScreen;
import dev.lemonclient.client.gui.mainmenu.MainMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.lemonclient.client.LemonClient.mc;

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
