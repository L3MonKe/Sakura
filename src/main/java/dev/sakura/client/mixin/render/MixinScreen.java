package dev.sakura.client.mixin.render;

import dev.sakura.client.nanovg.NanoVGRenderer;
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

    @Inject(method = "renderWithTooltip", at = @At("HEAD"))
    private void sakura$beginNanoVgScreenBatch(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        NanoVGRenderer.INSTANCE.beginBatch();
    }

    @Inject(method = "renderWithTooltip", at = @At("RETURN"))
    private void sakura$endNanoVgScreenBatch(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        NanoVGRenderer.INSTANCE.endBatch();
    }

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    public void renderPanoramaBackgroundHook(DrawContext context, float delta, CallbackInfo ci) {
        if (mc.world == null) {
            MainMenuShader.getSharedInstance().render(this.width, this.height);
            ci.cancel();
        } else {
            MainMenuShader.cleanupSharedInstance();
        }
    }
}
