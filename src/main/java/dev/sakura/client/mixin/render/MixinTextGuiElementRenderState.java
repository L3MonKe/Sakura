package dev.sakura.client.mixin.render;

import dev.sakura.client.module.impl.render.NameProtect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.TextGuiElementRenderState;
import net.minecraft.text.OrderedText;
import org.joml.Matrix3x2fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextGuiElementRenderState.class)
public class MixinTextGuiElementRenderState {
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(TextRenderer textRenderer, OrderedText orderedText, Matrix3x2fc matrix, int x, int y, int color, int backgroundColor, boolean shadow, boolean trackEmpty, ScreenRect clipBounds, CallbackInfo ci) {
        OrderedText replaced = NameProtect.replace(orderedText);
        if (replaced != orderedText) {
            try {
                java.lang.reflect.Field field = TextGuiElementRenderState.class.getField("orderedText");
                field.setAccessible(true);
                field.set(this, replaced);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
