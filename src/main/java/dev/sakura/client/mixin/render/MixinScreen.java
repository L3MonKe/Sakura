package dev.sakura.client.mixin.render;

import dev.sakura.client.shaders.MainMenuShader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(Screen.class)
public class MixinScreen {
    @Unique
    private static MainMenuShader mainMenuShader;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    public void renderPanoramaBackgroundHook(DrawContext context, float delta, CallbackInfo ci) {
        if (mc.world == null) {
            if (mainMenuShader == null) {
                mainMenuShader = new MainMenuShader(MainMenuShader.MainMenuShaderType.CUTE);
            }
            mainMenuShader.render(this.width, this.height);
            ci.cancel();
        } else if (mainMenuShader != null) {
            mainMenuShader.cleanup();
            mainMenuShader = null;
        }
    }
}
