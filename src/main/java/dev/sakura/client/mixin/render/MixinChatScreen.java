package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.HudEditor;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
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

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectContextFill(DrawContext context, int x1, int y1, int x2, int y2, int color) {
    }
}
