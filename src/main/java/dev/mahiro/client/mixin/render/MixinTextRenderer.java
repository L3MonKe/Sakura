package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.client.TextReplacer;
import dev.mahiro.client.module.impl.render.NameProtect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer {

    @Shadow
    public abstract int draw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light);

    @Inject(method = "draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawString(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            boolean modified = false;
            Text resultText = null;

            // 1. Check NameProtect
            NameProtect nameProtect = (NameProtect) Mahiro.MODULES.getModule(NameProtect.class);
            if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(text)) {
                resultText = NameProtect.getGradientReplacement(text);
                modified = true;
            }

            // 2. Check TextReplacer (if not already modified, or check the modified text string)
            // Simplicity: if NameProtect modified it, we use that result. 
            // If TextReplacer also needs to run, we would need to run it on the result of NameProtect.
            // But TextReplacer.replace returns Text, and NameProtect.getGradientReplacement returns Text.
            // Merging them is complex. Let's assume they are exclusive for now or prioritize NameProtect if it triggers.
            
            if (!modified) {
                if (TextReplacer.containsTarget(text)) {
                    TextReplacer replacer = (TextReplacer) Mahiro.MODULES.getModule(TextReplacer.class);
                    if (replacer != null && replacer.isEnabled()) {
                        resultText = TextReplacer.replace(text);
                        modified = (resultText != null);
                    }
                }
            }

            if (modified && resultText != null) {
                int result = this.draw(resultText.asOrderedText(), x, y, color, true, matrix, vertexConsumers, layerType, backgroundColor, light);
                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawText(Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            String string = text.getString();
            boolean modified = false;
            Text resultText = null;

            NameProtect nameProtect = (NameProtect) Mahiro.MODULES.getModule(NameProtect.class);
            if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(string)) {
                resultText = NameProtect.getGradientReplacement(string);
                modified = true;
            }

            if (!modified) {
                if (TextReplacer.containsTarget(string)) {
                    TextReplacer replacer = (TextReplacer) Mahiro.MODULES.getModule(TextReplacer.class);
                    if (replacer != null && replacer.isEnabled()) {
                        resultText = TextReplacer.replace(string);
                        modified = (resultText != null);
                    }
                }
            }

            if (modified && resultText != null) {
                int result = this.draw(resultText.asOrderedText(), x, y, color, true, matrix, vertexConsumers, layerType, backgroundColor, light);
                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "draw(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawOrderedText(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            StringBuilder sb = new StringBuilder();
            text.accept((i, style, cp) -> {
                sb.appendCodePoint(cp);
                return true;
            });
            String string = sb.toString();
            
            boolean modified = false;
            Text resultText = null;

            NameProtect nameProtect = (NameProtect) Mahiro.MODULES.getModule(NameProtect.class);
            if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(string)) {
                resultText = NameProtect.getGradientReplacement(string);
                modified = true;
            }

            if (!modified) {
                if (TextReplacer.containsTarget(string)) {
                    TextReplacer replacer = (TextReplacer) Mahiro.MODULES.getModule(TextReplacer.class);
                    if (replacer != null && replacer.isEnabled()) {
                        resultText = TextReplacer.replace(string);
                        modified = (resultText != null);
                    }
                }
            }

            if (modified && resultText != null) {
                int result = this.draw(resultText.asOrderedText(), x, y, color, true, matrix, vertexConsumers, layerType, backgroundColor, light);
                cir.setReturnValue(result);
            }
        }
    }
}
