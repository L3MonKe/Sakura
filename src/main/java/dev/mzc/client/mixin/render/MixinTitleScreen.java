package dev.mzc.client.mixin.render;

import dev.mzc.client.gui.mainmenu.MainMenuScreen;
import dev.mzc.client.gui.mainmenu.VerificationScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mzc.client.Sakura.mc;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void redirectToMainMenu(CallbackInfo ci) {
        ci.cancel();
        if (VerificationScreen.isVerified) {
            mc.setScreen(new MainMenuScreen());
        } else {
            mc.setScreen(new VerificationScreen());
        }
    }
}
