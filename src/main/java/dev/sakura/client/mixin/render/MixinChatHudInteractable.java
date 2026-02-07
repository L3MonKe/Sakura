package dev.sakura.client.mixin.render;

import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fc;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(targets = "net.minecraft.client.gui.hud.ChatHud$Interactable")
public class MixinChatHudInteractable {
    @Shadow
    private DrawContext context;

    @Shadow
    private TextRenderer textRenderer;

    @Shadow
    private int mouseX;

    @Shadow
    private int mouseY;

    @Shadow
    private Vector2f untransformedOffset;

    @Inject(method = "indicator", at = @At("HEAD"), cancellable = true)
    private void onIndicator(int x1, int y1, int x2, int y2, float opacity, MessageIndicator indicator, CallbackInfo ci) {
        int argb = ColorHelper.withAlpha(opacity, indicator.indicatorColor());
        Color color = new Color((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, (argb >> 24) & 0xFF);

        Matrix3x2f pose = new Matrix3x2f((Matrix3x2fc) this.context.getMatrices());
        Vector2f a = new Vector2f();
        Vector2f b = new Vector2f();
        pose.transformPosition((float) x1, (float) y1, a);
        pose.transformPosition((float) x2, (float) y2, b);

        float left = Math.min(a.x, b.x);
        float top = Math.min(a.y, b.y);
        float w = Math.abs(b.x - a.x);
        float h = Math.abs(b.y - a.y);

        boolean bloomEnabled = HudEditor.chatBloom.get();
        float radius = Math.min(2.0f, Math.min(w, h) * 0.5f);

        NanoVGRenderer.INSTANCE.drawImmediate(vg -> {
            if (bloomEnabled) {
                NanoVGHelper.drawRoundRectBloom(left, top, w, h, radius, 6.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * 0.75f)));
            }
            NanoVGHelper.drawRoundRect(left, top, w, h, radius, color);
        });

        if (DrawnTextConsumer.isWithinBounds(this.untransformedOffset.x, this.untransformedOffset.y, x1, y1, x2, y2)) {
            if (indicator.text() != null) {
                this.context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(indicator.text(), 210), this.mouseX, this.mouseY);
            }
        }

        ci.cancel();
    }

    @Inject(method = "fill", at = @At("HEAD"), cancellable = true)
    private void onFill(int x1, int y1, int x2, int y2, int color, CallbackInfo ci) {
        ci.cancel();
    }
}

