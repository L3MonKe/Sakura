package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Final
    @Shadow
    MinecraftClient client;

    @Final
    @Shadow
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    private int scrolledLines;

    @Shadow
    private boolean hasUnreadNewMessages;

    @Shadow
    public abstract int getVisibleLineCount();

    @Shadow
    protected abstract int getWidth();

    @Shadow
    protected abstract double getChatScale();

    @Shadow
    private boolean isChatHidden() {
        return false;
    }

    ;

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;IIIZZ)V", at = @At("HEAD"))
    private void onRenderChatBackground(DrawContext context, TextRenderer textRenderer, int currentTick, int mouseX, int mouseY, boolean interactable, boolean bl, CallbackInfo ci) {
        if (this.isChatHidden()) return;
        if (this.visibleMessages.isEmpty()) return;

        float chatScale = (float) this.getChatScale();
        if (chatScale <= 0.0f) return;

        int windowHeight = context.getScaledWindowHeight();
        int scaledWidth = MathHelper.ceil((float) this.getWidth() / chatScale);
        int bottom = MathHelper.floor((float) (windowHeight - 40) / chatScale);

        float textBgOpacity = this.client.options.getTextBackgroundOpacity().getValue().floatValue();

        int fontHeight = this.client.textRenderer.fontHeight;
        double lineSpacing = this.client.options.getChatLineSpacing().getValue();
        int lineStride = (int) ((double) fontHeight * (lineSpacing + 1.0));

        long queued = this.client.getMessageHandler().getUnprocessedMessageCount();

        int maxVisible = Math.min(this.visibleMessages.size() - this.scrolledLines, this.getVisibleLineCount());
        if (maxVisible <= 0) return;

        float maxOpacity = 0.0f;
        int drawnLineCount = 0;
        int topY = Integer.MAX_VALUE;
        int bottomY = Integer.MIN_VALUE;

        for (int i = maxVisible - 1; i >= 0; i--) {
            int messageIndex = i + this.scrolledLines;
            ChatHudLine.Visible visible = this.visibleMessages.get(messageIndex);
            float opacity = interactable ? 1.0f : chatOpacity(currentTick, visible.addedTime());
            if (!(opacity > 1.0E-5f)) continue;

            int lineBottom = bottom - i * lineStride;
            int lineTop = lineBottom - lineStride;

            topY = Math.min(topY, lineTop);
            bottomY = Math.max(bottomY, lineBottom);
            maxOpacity = Math.max(maxOpacity, opacity);
            drawnLineCount++;
        }

        if (drawnLineCount == 0) return;

        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        boolean bloomEnabled = hudEditor.chatBloom.get();
        float radius = hudEditor.radius.get().floatValue() * chatScale;

        float x = 0.0f;
        float y = topY * chatScale;
        float w = (scaledWidth + 12) * chatScale;
        float h = (bottomY - topY) * chatScale;

        int baseAlpha = (int) (MathHelper.clamp(maxOpacity * textBgOpacity, 0.0f, 1.0f) * 70.0f);
        Color panelColor = new Color(18, 18, 18, baseAlpha);
        Color bloomColor = new Color(0, 0, 0, baseAlpha);

        float queueBarHeight = fontHeight * chatScale;
        float queueBarX = 2.0f * chatScale;
        float queueBarY = bottomY * chatScale;
        float queueBarW = (scaledWidth + 6) * chatScale;

        int finalDrawnLineCount = drawnLineCount;
        NanoVGRenderer.INSTANCE.drawImmediate(vg -> {
            if (bloomEnabled) {
                NanoVGHelper.drawRoundRectBloom(x, y, w, h, radius, 10.0f * chatScale, bloomColor);
            }
            NanoVGHelper.drawRoundRect(x, y, w, h, radius, panelColor);

            if (queued > 0L) {
                if (bloomEnabled) {
                    NanoVGHelper.drawRoundRectBloom(queueBarX, queueBarY, queueBarW, queueBarHeight, Math.max(2.0f, radius * 0.8f), 8.0f * chatScale, bloomColor);
                }
                NanoVGHelper.drawRoundRect(queueBarX, queueBarY, queueBarW, queueBarHeight, Math.max(2.0f, radius * 0.8f), panelColor);
            }

            if (interactable) {
                int total = this.visibleMessages.size();
                int s = finalDrawnLineCount * lineStride;
                int t = this.scrolledLines * s / total - bottom;
                int u = s * s / (total * lineStride);
                if (total * lineStride != s) {
                    float scrollX = (scaledWidth + 8) * chatScale;
                    float scrollW = 2.0f * chatScale;
                    float scrollTop = (-t - u) * chatScale;
                    float scrollH = u * chatScale;

                    int base = t > 0 ? 170 : 96;
                    int barRgb = this.hasUnreadNewMessages ? 0xCC3333 : 0x3333AA;
                    Color barColor = new Color((barRgb >> 16) & 0xFF, (barRgb >> 8) & 0xFF, barRgb & 0xFF, base);

                    if (bloomEnabled) {
                        NanoVGHelper.drawRoundRectBloom(scrollX, scrollTop, scrollW, scrollH, 1.5f * chatScale, 6.0f * chatScale, new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), (int) (barColor.getAlpha() * 0.75f)));
                    }
                    NanoVGHelper.drawRoundRect(scrollX, scrollTop, scrollW, scrollH, 1.5f * chatScale, barColor);
                }
            }
        });
    }

    @Redirect(method = "render(Lnet/minecraft/client/gui/hud/ChatHud$Backend;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud$Backend;fill(IIIII)V"))
    private void redirectChatFill(ChatHud.Backend backend, int x1, int y1, int x2, int y2, int color) {
    }

    @Unique
    private static float chatOpacity(int currentTick, int addedTime) {
        int age = currentTick - addedTime;
        double d = (double) age / 200.0;
        d = 1.0 - d;
        d *= 10.0;
        d = MathHelper.clamp(d, 0.0, 1.0);
        d *= d;
        return (float) d;
    }
}
