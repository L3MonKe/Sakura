package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.movement.GuiMove;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.shaders.MainMenuShader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
    private void onBeginNanoVgScreenBatch(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        NanoVGRenderer.INSTANCE.beginBatch();
    }

    @Inject(method = "renderWithTooltip", at = @At("RETURN"))
    private void onEndNanoVgScreenBatch(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        NanoVGRenderer.INSTANCE.endBatch();
    }

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    public void onRenderPanoramaBackgroundHook(DrawContext context, float delta, CallbackInfo ci) {
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

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ChatScreen) return;
        GuiMove guiMove = Sakura.MODULES.getModule(GuiMove.class);
        List<Integer> arrows = List.of(GLFW.GLFW_KEY_RIGHT, GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_UP);
        if ((guiMove.disableArrows() && arrows.contains(input.key())) || (guiMove.disableSpace() && input.key() == GLFW.GLFW_KEY_SPACE)) {
            cir.setReturnValue(true);
        }
    }
}
