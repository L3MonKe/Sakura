package dev.sakura.client.mixin;

import dev.sakura.verify.LoginWindow;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MixinMain {
    @Inject(method = "main", at = @At("HEAD"))
    private static void onMain(String[] args, CallbackInfo ci) {
        LoginWindow.verifyOrExitBlocking();
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/lang/System;setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String hookStaticInit(String key, String value) {
        return System.setProperty("java.awt.headless", "false");
    }
}
