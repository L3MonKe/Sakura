package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ChatHud.class)
public class MixinChatHud {
    private static final int CHAT_MARGIN_LEFT = 4;
    private static int minX = Integer.MAX_VALUE;
    private static int minY = Integer.MAX_VALUE;
    private static int maxX = Integer.MIN_VALUE;
    private static int maxY = Integer.MIN_VALUE;
    private static boolean shouldRender = false;
    private static DrawContext cachedContext = null;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(CallbackInfo ci) {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        shouldRender = false;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectChatBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        minX = Math.min(minX, x1 + CHAT_MARGIN_LEFT);
        minY = Math.min(minY, y1);
        maxX = Math.max(maxX, x2 + CHAT_MARGIN_LEFT);
        maxY = Math.max(maxY, y2);
        shouldRender = true;
        cachedContext = context;
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(CallbackInfo ci) {
        if (!shouldRender || cachedContext == null) return;

        float radius = getGlobalRadius();
        int width = maxX - minX;
        int height = maxY - minY;
        float padding = 4f;
        float finalWidth = width + padding * 2;
        float finalHeight = height + padding * 2;
        float currentX = minX - 4f + 6F;
        float currentY = minY - 4f;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color backgroundColor = new Color(18, 18, 18, 70);

            HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
            boolean enableBloom = hudEditor != null ? hudEditor.enableChatBloom.get() : true;

            if (enableBloom) {
                NanoVGHelper.drawRoundRectBloom(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            } else {
                NanoVGHelper.drawRoundRect(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            }
        });
    }

    private float getGlobalRadius() {
        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        if (hudEditor != null) {
            return hudEditor.globalCornerRadius.get().floatValue();
        }
        return 3f;
    }
}