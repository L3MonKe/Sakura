package dev.mzc.client.mixin.render;

import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.module.impl.client.ClickGui;
import java.awt.Color;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.Sakura;
import dev.mzc.client.module.impl.client.TextReplacer;
import dev.mzc.client.module.impl.misc.NameProtect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.spongepowered.asm.mixin.Unique;

@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer {
    @Unique
    private static final ThreadLocal<Boolean> IS_RENDERING_MZC = ThreadLocal.withInitial(() -> false);

    @Shadow
    public abstract int draw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light);

    @Inject(method = "getWidth(Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true)
    private void onGetWidthString(String text, CallbackInfoReturnable<Integer> cir) {
        if (text != null && ClickGui.globalFontReplacement.get()) {
            float userSize = (float) ClickGui.getFontSize();
            float scaleFactor = 9.0f / userSize;
            int font = FontLoader.regular(userSize);
            
            // Strip formatting codes (e.g. §c, §l) before measuring width
            // This prevents the measured width from being larger than the visual width, which causes centering issues (left offset).
            String cleanText = text.replaceAll("(?i)§[0-9a-fk-or]", "");
            
            float width = NanoVGHelper.getTextWidth(cleanText, font, userSize);
            // Use round instead of ceil for better centering accuracy
            cir.setReturnValue((int) Math.round(width * scaleFactor));
        }
    }

    @Inject(method = "getWidth(Lnet/minecraft/text/OrderedText;)I", at = @At("HEAD"), cancellable = true)
    private void onGetWidthOrdered(OrderedText text, CallbackInfoReturnable<Integer> cir) {
        if (text != null && ClickGui.globalFontReplacement.get()) {
            StringBuilder sb = new StringBuilder();
            text.accept((i, style, cp) -> {
                sb.appendCodePoint(cp);
                return true;
            });
            String string = sb.toString();
            float userSize = (float) ClickGui.getFontSize();
            float scaleFactor = 9.0f / userSize;
            int font = FontLoader.regular(userSize);
            float width = NanoVGHelper.getTextWidth(string, font, userSize);
            // Use round instead of ceil for better centering accuracy
            cir.setReturnValue((int) Math.round(width * scaleFactor));
        }
    }

    @Inject(method = "draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawString(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            if (IS_RENDERING_MZC.get()) return;

            boolean modified = false;
            Text resultText = null;

            // 1. Check NameProtect
            NameProtect nameProtect = (NameProtect) Sakura.MODULES.getModule(NameProtect.class);
            if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(text)) {
                resultText = NameProtect.getGradientReplacement(text);
                modified = true;
            }

            // 2. Check TextReplacer (Automatic)
            if (!modified) {
                if (TextReplacer.containsTarget(text)) {
                    resultText = TextReplacer.replace(text);
                    modified = (resultText != null);
                }
            }

            if (modified && resultText != null) {
                IS_RENDERING_MZC.set(true);
                try {
                    int result = this.draw(resultText.asOrderedText(), x, y, color, true, matrix, vertexConsumers, layerType, backgroundColor, light);
                    cir.setReturnValue(result);
                } finally {
                    IS_RENDERING_MZC.set(false);
                }
                return;
            }

            // Force global font for String drawing (fixes Item Stack Count not using global font)
            if (ClickGui.globalFontReplacement.get()) {
                int result = this.draw(Text.of(text).asOrderedText(), x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light);
                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawText(Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            if (IS_RENDERING_MZC.get()) return;

            String string = text.getString();
            boolean modified = false;
            Text resultText = null;

            NameProtect nameProtect = Sakura.MODULES.getModule(NameProtect.class);
            if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(string)) {
                resultText = NameProtect.getGradientReplacement(text);
                modified = true;
            }

            if (!modified) {
                if (TextReplacer.containsTarget(string)) {
                    resultText = TextReplacer.replace(string);
                    modified = (resultText != null);
                }
            }





            if (modified && resultText != null) {
                IS_RENDERING_MZC.set(true);
                try {
                    int result = this.draw(resultText.asOrderedText(), x, y, color, true, matrix, vertexConsumers, layerType, backgroundColor, light);
                    cir.setReturnValue(result);
                } finally {
                    IS_RENDERING_MZC.set(false);
                }
            }
        }
    }

    @Inject(method = "draw(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
    private void onDrawOrderedText(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (text != null) {
            MutableText[] reconstructed = {Text.empty()};
            StringBuilder[] currentContent = {new StringBuilder()};
            Style[] currentStyle = {null};

            text.accept((i, style, cp) -> {
                if (currentStyle[0] != null && !style.equals(currentStyle[0])) {
                    if (currentContent[0].length() > 0) {
                        reconstructed[0].append(Text.literal(currentContent[0].toString()).setStyle(currentStyle[0]));
                        currentContent[0] = new StringBuilder();
                    }
                }
                currentStyle[0] = style;
                currentContent[0].appendCodePoint(cp);
                return true;
            });

            if (currentContent[0].length() > 0) {
                if (currentStyle[0] != null) {
                    reconstructed[0].append(Text.literal(currentContent[0].toString()).setStyle(currentStyle[0]));
                } else {
                    reconstructed[0].append(Text.literal(currentContent[0].toString()));
                }
            }

            if (!IS_RENDERING_MZC.get()) {
                Text sourceText = reconstructed[0];
                String string = sourceText.getString();

                boolean modified = false;
                Text resultText = null;

                NameProtect nameProtect = (NameProtect) Sakura.MODULES.getModule(NameProtect.class);
                if (nameProtect != null && nameProtect.isEnabled() && NameProtect.shouldReplace(string)) {
                    resultText = NameProtect.getGradientReplacement(sourceText);
                    modified = true;
                }

                if (!modified) {
                    if (TextReplacer.containsTarget(string)) {
                        resultText = TextReplacer.replace(string);
                        modified = (resultText != null);
                    }
                }

                // 2. Check MZC Prefix
                if (string.toLowerCase().contains("mzc")) {
                    Text input = (modified && resultText != null) ? resultText : sourceText;
                    Text mzcReplaced = ClickGui.getMZCGradientText(input);
                    if (mzcReplaced != null) {
                        resultText = mzcReplaced;
                        modified = true;
                    }
                }

                if (modified && resultText != null) {
                    IS_RENDERING_MZC.set(true);
                    try {
                        int result = this.draw(resultText.asOrderedText(), x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light);
                        cir.setReturnValue(result);
                    } finally {
                        IS_RENDERING_MZC.set(false);
                    }
                    return;
                }
            }

            if (text != null && ClickGui.globalFontReplacement.get()) {
                if (layerType == TextRenderer.TextLayerType.POLYGON_OFFSET) {
                    return;
                }

                if (vertexConsumers instanceof VertexConsumerProvider.Immediate immediate) {
                    immediate.draw();
                }
                
                // Fix for Chat Input visibility: Ensure alpha is set if missing
                if ((color & -67108864) == 0) {
                    color |= -16777216;
                }
                final int finalColor = color;

                // Fix for Item Stack Count visibility: Disable depth test to draw on top of items
                // NanoVG ignores Z-translation in matrix, so it draws at Z=0. Items are at Z=150 with depth test enabled.
                boolean wasDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
                RenderSystem.disableDepthTest();

                NanoVGRenderer.INSTANCE.draw(vg -> {
                    NanoVG.nvgTransform(vg, matrix.m00(), matrix.m01(), matrix.m10(), matrix.m11(), matrix.m30(), matrix.m31());
                    
                    float userSize = (float) ClickGui.getFontSize(); // e.g. 18
                    float vanillaHeight = 9.0f;
                    float scaleFactor = vanillaHeight / userSize; // e.g. 0.5

                    // Apply this scale to NanoVG
                    NanoVG.nvgScale(vg, scaleFactor, scaleFactor);
                    float currentX = x / scaleFactor;
                    float drawY = y / scaleFactor;
                    
                    // Adjust offset for centering.
                    float yOffset = userSize * 0.8f;
                    
                    int font = FontLoader.regular(userSize);
                    int fontBold = FontLoader.bold(userSize);
                    
                    if (shadow) {
                        float shadowX = currentX;
                        for (Text sibling : reconstructed[0].getSiblings()) {
                            String content = sibling.getString();
                            if (content.isEmpty()) continue;

                            int currentFont = sibling.getStyle().isBold() ? fontBold : font;
                            int alpha = (finalColor >> 24) & 0xFF;
                            Color shadowColor = new Color(0, 0, 0, (int)(alpha * 0.3f));

                            NanoVGHelper.drawString(content, shadowX + 1f, drawY + 1f + yOffset, currentFont, userSize, shadowColor);
                            shadowX += NanoVGHelper.getTextWidth(content, currentFont, userSize);
                        }
                    }

                    float mainX = currentX;
                    for (Text sibling : reconstructed[0].getSiblings()) {
                        String content = sibling.getString();
                        if (content.isEmpty()) continue;

                        Style style = sibling.getStyle();
                        int currentFont = style.isBold() ? fontBold : font;
                        
                        Color awtColor;
                        int alpha = (finalColor >> 24) & 0xFF;
                        
                        if (style.getColor() != null) {
                            int rgb = style.getColor().getRgb();
                            awtColor = new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, alpha);
                        } else {
                            awtColor = new Color((finalColor >> 16) & 0xFF, (finalColor >> 8) & 0xFF, finalColor & 0xFF, alpha);
                        }

                        NanoVGHelper.drawString(content, mainX, drawY + yOffset, currentFont, userSize, awtColor);
                        mainX += NanoVGHelper.getTextWidth(content, currentFont, userSize);
                    }
                });
                
                if (wasDepthTest) {
                    RenderSystem.enableDepthTest();
                }

                float userSize = (float) ClickGui.getFontSize();
                float scaleFactor = 9.0f / userSize;
                int font = FontLoader.regular(userSize);
                int fontBold = FontLoader.bold(userSize);
                float totalWidth = 0;
                
                for (Text sibling : reconstructed[0].getSiblings()) {
                    int currentFont = sibling.getStyle().isBold() ? fontBold : font;
                    totalWidth += NanoVGHelper.getTextWidth(sibling.getString(), currentFont, userSize);
                }
                
                float vanillaWidth = totalWidth * scaleFactor;
                // Fix for Title Offset: Use round instead of ceil to minimize centering errors
                cir.setReturnValue((int) Math.round(x + vanillaWidth));
                return;

            }


        }
    }
}
