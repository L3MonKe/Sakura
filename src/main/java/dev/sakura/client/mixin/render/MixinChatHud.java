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
public class MixinChatHud {
    @Final
    @Shadow
    MinecraftClient client;
    @Final
    @Shadow
    private List<ChatHudLine.Visible> visibleMessages;
    @Shadow
    private int scrolledLines;

    @Shadow
    private boolean isChatHidden() {
        return false;
    }

    @Shadow
    public int getVisibleLineCount() {
        return 0;
    }

    @Shadow
    private int getLineHeight() {
        return 0;
    }

    @Shadow
    private int getWidth() {
        return 0;
    }

    @Shadow
    private double getChatScale() {
        return 0;
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;IIIZZ)V", at = @At("HEAD"))
    private void onRenderStart(DrawContext context, TextRenderer textRenderer, int currentTick, int mouseX, int mouseY, boolean interactable, boolean bl, CallbackInfo ci) {
        if (isChatHidden()) return;
        int i = getVisibleLineCount();
        int j = visibleMessages.size();
        if (j == 0) return;

        float f = (float) getChatScale();
        int k = MathHelper.ceil((float) getWidth() / f);
        int l = context.getScaledWindowHeight();
        int m = MathHelper.floor((float) (l - 40) / f);
        double d = client.options.getChatOpacity().getValue() * 0.9 + 0.1;
        int o = getLineHeight();

        final int CHAT_MARGIN_LEFT = 4;

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = -4 + CHAT_MARGIN_LEFT;
        int maxX = k + 8 + CHAT_MARGIN_LEFT;

        for (int r = 0; r + this.scrolledLines < this.visibleMessages.size() && r < i; r++) {
            int s = r + this.scrolledLines;
            ChatHudLine.Visible visible = this.visibleMessages.get(s);
            if (visible == null) continue;
            int t = currentTick - visible.addedTime();
            if (t < 200 || interactable) {
                double h = interactable ? 1.0 : getMessageOpacityMultiplierLocal(t);
                int u = (int) (255.0 * h * d);
                if (u > 3) {
                    int x = m - r * o;
                    int y1 = x - o;
                    int y2 = x;
                    minY = Math.min(minY, y1);
                    maxY = Math.max(maxY, y2);
                }
            }
        }

        if (minY == Integer.MAX_VALUE || maxY == Integer.MIN_VALUE) return;

        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        if (hudEditor == null) return;

        float radius = hudEditor.globalCornerRadius.get().floatValue();
        int width = maxX - minX;
        int height = maxY - minY;
        float padding = 4f;
        float finalWidth = width + padding * 2;
        float finalHeight = height + padding * 2;
        float currentX = minX - 4f + 6F;
        float currentY = minY - 4f;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color backgroundColor = new Color(18, 18, 18, 70);

            if (hudEditor.enableChatBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            } else {
                NanoVGHelper.drawRoundRect(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            }
        });
    }

    @Redirect(method = "render(Lnet/minecraft/client/gui/hud/ChatHud$Backend;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud$Backend;fill(IIIII)V"))
    private void redirectChatBackground(ChatHud.Backend drawer, int x1, int y1, int x2, int y2, int color) {
        if (x1 == -4 && x2 > 0) {
            return;
        }
        drawer.fill(x1, y1, x2, y2, color);
    }

    @Unique
    private static double getMessageOpacityMultiplierLocal(int age) {
        double d = 1.0 - (double) age / 200.0;
        d = MathHelper.clamp(d, 0.0, 1.0);
        return d * d;
    }
}
