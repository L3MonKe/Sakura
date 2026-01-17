package com.zeta.client.mixin.render;

import com.zeta.client.auth.AuthGate;
import com.zeta.client.gui.auth.AuthScreen;
import com.zeta.client.gui.mainmenu.MainMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.zeta.client.Zeta.mc;

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
