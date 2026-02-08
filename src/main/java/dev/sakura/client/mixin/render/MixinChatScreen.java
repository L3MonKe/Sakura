package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.mixin.accessor.IChatInputSuggestor;
import dev.sakura.client.mixin.accessor.ISuggestionWindow;
import dev.sakura.client.mixin.accessor.ITextFieldWidget;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.ChatGradientText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static dev.sakura.client.Sakura.mc;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow
    protected TextFieldWidget chatField;

    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        int chatWidth = ChatHud.getWidth(mc.options.getChatWidth().getValue());
        int fieldWidth = Math.max(1, Math.min(chatWidth + 8, width - 4));
        if (chatField != null) {
            chatField.setX(4);
            chatField.setWidth(fieldWidth);
        }

        float x = 0.0f;
        float y = height - 14.0f;
        float w = Math.min(chatWidth + 12.0f, width);
        float h = 12.0f;

        float radius = HudEditor.radius.get().floatValue();
        float alpha = MathHelper.clamp(mc.options.getTextBackgroundOpacity().getValue().floatValue(), 0.0f, 1.0f);

        Color bg = new Color(18, 18, 18, (int) (alpha * 70.0f));
        Color bloom = new Color(0, 0, 0, (int) (alpha * 70.0f));

        NanoVGRenderer.INSTANCE.drawImmediate(vg -> {
            if (HudEditor.chatBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(x, y, w, h, radius, 12.0f, bloom);
            }
            NanoVGHelper.drawRoundRect(x, y, w, h, radius, bg);
        });
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        if (chatField == null) return;

        String prefix = Sakura.COMMAND.getPrefix();
        if (prefix == null || prefix.isEmpty()) return;

        String text = chatField.getText();
        if (text == null || !text.startsWith(prefix)) return;

        final int screenW = mc.getWindow().getScaledWidth();
        final int screenH = mc.getWindow().getScaledHeight();
        final float pad = 0.3f;

        int chatWidth = ChatHud.getWidth(mc.options.getChatWidth().getValue());
        float chatBgW = Math.min(chatWidth + 12.0f, screenW);

        final float chatLeft = MathHelper.clamp(0.0f - pad, 0.0f, screenW);
        final float chatTop = MathHelper.clamp((screenH - 14.0f) - pad, 0.0f, screenH);
        final float chatRight = MathHelper.clamp(chatBgW + pad, 0.0f, screenW);
        final float chatBottom = MathHelper.clamp((screenH - 2.0f) + pad, 0.0f, screenH);
        final float chatW = Math.max(0.0f, chatRight - chatLeft);
        final float chatH = Math.max(0.0f, chatBottom - chatTop);

        float alpha = MathHelper.clamp(mc.options.getTextBackgroundOpacity().getValue().floatValue(), 0.0f, 1.0f);
        final Color outline = new Color(255, 183, 197, (int) (alpha * 220.0f));

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.drawRoundRectOutline(chatLeft, chatTop, chatW, chatH, HudEditor.radius.get().floatValue(), 1.25f, outline);

            if (chatInputSuggestor != null) {
                ChatInputSuggestor.SuggestionWindow window = ((IChatInputSuggestor) chatInputSuggestor).getWindow();
                if (window != null) {
                    Rect2i area = ((ISuggestionWindow) window).getArea();
                    if (area != null && area.getWidth() > 0 && area.getHeight() > 0) {
                        float rawLeft = area.getX();
                        float rawTop = area.getY();
                        float l = MathHelper.clamp(rawLeft - pad, 0.0f, screenW);
                        float t = MathHelper.clamp(rawTop - pad, 0.0f, screenH);
                        float r = MathHelper.clamp(rawLeft + area.getWidth() + pad, 0.0f, screenW);
                        float b = MathHelper.clamp(rawTop + area.getHeight() + pad, 0.0f, screenH);

                        float suggestW = Math.max(0.0f, r - l);
                        float suggestH = Math.max(0.0f, b - t);
                        NanoVGHelper.drawRectOutline(l, t, suggestW, suggestH, 1.25f, outline);
                    }
                }
            }
        });

        int firstIndex = ((ITextFieldWidget) chatField).getFirstCharacterIndex();
        if (firstIndex != 0) return;

        int textX = ((ITextFieldWidget) chatField).getTextX();
        int textY = ((ITextFieldWidget) chatField).getTextY();
        ChatGradientText.drawPulsePlain(context, mc.textRenderer, textX, textY, 1.0f, prefix);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectContextFill(DrawContext context, int x1, int y1, int x2, int y2, int color) {
    }
}
