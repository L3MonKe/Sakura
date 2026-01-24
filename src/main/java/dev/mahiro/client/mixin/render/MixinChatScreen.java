package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.ChatMessageEvent;
import dev.mahiro.client.mixin.accessor.IChatInputSuggestor;
import dev.mahiro.client.mixin.accessor.ISuggestionWindow;
import dev.mahiro.client.module.impl.client.HudEditor;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.Rect2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow
    protected TextFieldWidget chatField;
    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    @Unique
    private static float inputCurrentY = 0;
    @Unique
    private static boolean inputInitialized = false;
    @Unique
    private static long openTime = 0;
    @Unique
    private static float inputAlpha = 0f;
    @Unique
    private static int inputFieldBaseY = Integer.MIN_VALUE;
    @Unique
    private static int inputBoxBaseY = Integer.MIN_VALUE;
    @Unique
    private static int inputBoxCurrentY = 0;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0))
    private void redirectInputBoxBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        int adjustedX1 = x1;
        float radius = getGlobalRadius();
        int width = 340;
        int height = y2 - y1;

        if (!inputInitialized) {
            inputCurrentY = mc.getWindow().getScaledHeight();
            inputInitialized = true;
            openTime = System.currentTimeMillis();
            inputAlpha = 0f;
            if (chatField != null) {
                inputFieldBaseY = chatField.getY();
            }
            inputBoxBaseY = y1;
        }

        long elapsed = System.currentTimeMillis() - openTime;
        float progress = Math.min(1.0f, elapsed / 300.0f);

        float easedProgress = (float) (1 - Math.pow(1 - progress, 3));
        inputCurrentY = (float) y1 + (mc.getWindow().getScaledHeight() - (float) y1) * (1 - easedProgress);

        inputAlpha = Math.min(1.0f, elapsed / 200.0f);

        int animatedY1 = (int) inputCurrentY;
        int animatedY2 = animatedY1 + height;
        inputBoxCurrentY = animatedY1;
        if (chatField != null && inputFieldBaseY != Integer.MIN_VALUE && inputBoxBaseY != Integer.MIN_VALUE) {
            int textOffset = inputFieldBaseY - inputBoxBaseY;
            chatField.setY(animatedY1 + textOffset);
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color backgroundColor = new Color(18, 18, 18, 70);

            HudEditor hudEditor = Mahiro.MODULES.getModule(HudEditor.class);
            boolean enableBloom = hudEditor != null ? hudEditor.enableChatBloom.get() : true;

            if (enableBloom) {
                NanoVGHelper.drawRoundRectBloom(adjustedX1, animatedY1, width, height, radius, backgroundColor);
            } else {
                NanoVGHelper.drawRoundRect(adjustedX1, animatedY1, width, height, radius, backgroundColor);
            }
        });
    }

    private float getGlobalRadius() {
        HudEditor hudEditor = Mahiro.MODULES.getModule(HudEditor.class);
        if (hudEditor != null) {
            return hudEditor.globalCornerRadius.get().floatValue();
        }
        return 3f;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRenderPost(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (chatField == null || !chatField.getText().startsWith(Mahiro.COMMAND.getPrefix())) return;
        NanoVGRenderer.INSTANCE.draw(vg -> {
            final float PAD = 0.5F;
            final Color SAKURA = new Color(255, 183, 197, (int) (255 * inputAlpha));
            int marginLeft = 4;

            int animatedY = (int) inputCurrentY;
            NanoVGHelper.drawRoundRectOutline(
                    marginLeft - 1.5f,
                    inputBoxCurrentY - PAD,
                    340,
                    12 + PAD * 2,
                    getGlobalRadius(),
                    0.6f,
                    SAKURA
            );
            var window = ((IChatInputSuggestor) chatInputSuggestor).getWindow();
            if (window != null) {
                Rect2i a = ((ISuggestionWindow) window).getArea();
                NanoVGHelper.drawRectOutline(a.getX() - PAD, a.getY() - PAD,
                        a.getWidth() + PAD * 2, a.getHeight() + PAD * 2, 0.7f, SAKURA);
            }
        });
    }

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void hookSendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (Mahiro.EVENT_BUS.post(new ChatMessageEvent.Client(chatText)).isCancelled()) ci.cancel();
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        inputInitialized = false;
        inputFieldBaseY = Integer.MIN_VALUE;
        inputBoxBaseY = Integer.MIN_VALUE;
        inputBoxCurrentY = 0;
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void onRemoved(CallbackInfo ci) {
        inputInitialized = false;
        inputAlpha = 0f;
        inputFieldBaseY = Integer.MIN_VALUE;
        inputBoxBaseY = Integer.MIN_VALUE;
        inputBoxCurrentY = 0;
    }
}
