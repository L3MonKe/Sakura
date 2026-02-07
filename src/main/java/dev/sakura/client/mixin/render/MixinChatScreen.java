package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.mixin.accessor.IChatInputSuggestor;
import dev.sakura.client.mixin.accessor.ISuggestionWindow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
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

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow
    protected TextFieldWidget chatField;

    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        boolean bloomEnabled = hudEditor.chatBloom.get();

        MinecraftClient mc = MinecraftClient.getInstance();
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        float x = 2.0f;
        float y = height - 14.0f;
        float w = width - 4.0f;
        float h = 12.0f;

        float radius = hudEditor.radius.get().floatValue();
        float alpha = MathHelper.clamp(mc.options.getTextBackgroundOpacity().getValue().floatValue(), 0.0f, 1.0f);

        Color bg = new Color(18, 18, 18, (int) (alpha * 70.0f));
        Color bloom = new Color(0, 0, 0, (int) (alpha * 70.0f));

        NanoVGRenderer.INSTANCE.drawImmediate(vg -> {
            if (bloomEnabled) {
                NanoVGHelper.drawRoundRectBloom(x, y, w, h, radius, 12.0f, bloom);
            }
            NanoVGHelper.drawRoundRect(x, y, w, h, radius, bg);
        });
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        if (Sakura.COMMAND == null) return;
        if (chatField == null) return;

        String prefix = Sakura.COMMAND.getPrefix();
        if (prefix == null || prefix.isEmpty()) return;

        String text = chatField.getText();
        if (text == null || !text.startsWith(prefix)) return;

        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        float radius = hudEditor != null ? hudEditor.radius.get().floatValue() : 4.0f;

        MinecraftClient mc = MinecraftClient.getInstance();
        int screenW = mc.getWindow().getScaledWidth();
        int screenH = mc.getWindow().getScaledHeight();

        float chatLeft = 2.0f;
        float chatTop = screenH - 14.0f;
        float chatRight = screenW - 2.0f;
        float chatBottom = screenH - 2.0f;

        float suggestLeft = 0.0f;
        float suggestTop = 0.0f;
        float suggestRight = 0.0f;
        float suggestBottom = 0.0f;
        boolean hasSuggest = false;

        if (chatInputSuggestor != null) {
            ChatInputSuggestor.SuggestionWindow window = ((IChatInputSuggestor) chatInputSuggestor).getWindow();
            if (window != null) {
                Rect2i area = ((ISuggestionWindow) window).getArea();
                if (area != null && area.getWidth() > 0 && area.getHeight() > 0) {
                    suggestLeft = area.getX();
                    suggestTop = area.getY();
                    suggestRight = suggestLeft + area.getWidth();
                    suggestBottom = suggestTop + area.getHeight();
                    hasSuggest = true;
                }
            }
        }

        float pad = 2.0f;
        chatLeft = MathHelper.clamp(chatLeft - pad, 0.0f, screenW);
        chatTop = MathHelper.clamp(chatTop - pad, 0.0f, screenH);
        chatRight = MathHelper.clamp(chatRight + pad, 0.0f, screenW);
        chatBottom = MathHelper.clamp(chatBottom + pad, 0.0f, screenH);

        if (hasSuggest) {
            suggestLeft = MathHelper.clamp(suggestLeft - pad, 0.0f, screenW);
            suggestTop = MathHelper.clamp(suggestTop - pad, 0.0f, screenH);
            suggestRight = MathHelper.clamp(suggestRight + pad, 0.0f, screenW);
            suggestBottom = MathHelper.clamp(suggestBottom + pad, 0.0f, screenH);
        }

        float chatW = Math.max(0.0f, chatRight - chatLeft);
        float chatH = Math.max(0.0f, chatBottom - chatTop);

        float suggestW = hasSuggest ? Math.max(0.0f, suggestRight - suggestLeft) : 0.0f;
        float suggestH = hasSuggest ? Math.max(0.0f, suggestBottom - suggestTop) : 0.0f;

        float alpha = MathHelper.clamp(mc.options.getTextBackgroundOpacity().getValue().floatValue(), 0.0f, 1.0f);
        Color outline = new Color(255, 183, 197, (int) (alpha * 220.0f));

        final float cfx = chatLeft;
        final float cfy = chatTop;
        final float cfw = chatW;
        final float cfh = chatH;

        final boolean finalHasSuggest = hasSuggest;
        final float sfx = suggestLeft;
        final float sfy = suggestTop;
        final float sfw = suggestW;
        final float sfh = suggestH;

        final float fr = radius + 2.0f;
        final Color fc = outline;

        NanoVGRenderer.INSTANCE.drawImmediate(vg -> {
            NanoVGHelper.drawRoundRectOutline(cfx, cfy, cfw, cfh, fr, 1.25f, fc);
            if (finalHasSuggest) {
                NanoVGHelper.drawRoundRectOutline(sfx, sfy, sfw, sfh, fr, 1.25f, fc);
            }
        });
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectContextFill(DrawContext context, int x1, int y1, int x2, int y2, int color) {
    }
}
