package dev.sakura.client.mixin;

import dev.sakura.client.nanovg.NanoVGRenderer;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MixinMain {
    @Unique
    private static boolean nanovgInitialized = false;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        if (!nanovgInitialized) {
            NanoVGRenderer.INSTANCE.initNanoVG();
            nanovgInitialized = true;
        }
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/lang/System;setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String hookStaticInit(String key, String value) {
        // 你不加这个AltManager的跳转浏览器就爆炸了。
        return System.setProperty("java.awt.headless", "false");
    }
}
