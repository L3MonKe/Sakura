package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.client.ChatMessageEvent;
import dev.mzc.client.mixin.accessor.IChatInputSuggestor;
import dev.mzc.client.mixin.accessor.ISuggestionWindow;
import dev.mzc.client.module.impl.client.HudEditor;
import dev.mzc.client.module.impl.misc.BetterChat;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.util.NanoVGHelper;
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

import static dev.mzc.client.Sakura.mc;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow
    protected TextFieldWidget chatField;
    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    @Unique
    private boolean wasOpenedLastFrame = false;
    @Unique
    private long lastOpenTime = 0;
    @Unique
    private float displacement = 0;

    @Unique
    private float calculateDisplacement() {
        BetterChat chatAnimation = Sakura.MODULES.getModule(BetterChat.class);
        if (chatAnimation == null || !chatAnimation.isEnabled() || !chatAnimation.enableInputAnim.get()) {
            return 0;
        }

        if (mc.player != null && !wasOpenedLastFrame && !mc.player.isSleeping()) {
            wasOpenedLastFrame = true;
            lastOpenTime = System.currentTimeMillis();
        }

        float FADE_TIME = chatAnimation.inputAnimTime.get().floatValue();
        float FADE_OFFSET = 8;
        float screenFactor = (float)mc.getWindow().getHeight() / 1080;
        float timeSinceOpen = Math.min((float)(System.currentTimeMillis() - lastOpenTime), FADE_TIME);
        float alpha = 1 - (timeSinceOpen/FADE_TIME);

        float c1 = 1.70158f;
        float c3 = c1 + 1;
        float modifiedAlpha = c3 * alpha * alpha * alpha - c1 * alpha * alpha;

        return modifiedAlpha * FADE_OFFSET * screenFactor;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", shift = At.Shift.BEFORE))
    private void renderStart(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        displacement = calculateDisplacement();

        if (displacement != 0) {
            context.getMatrices().push();
            context.getMatrices().translate(0f, displacement, 0f);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER))
    private void renderEnd(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (displacement != 0) {
            context.getMatrices().pop();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0))
    private void redirectInputBoxBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        int adjustedX1 = x1;
        float radius = getGlobalRadius();
        int width = 340;
        int height = y2 - y1;

        // Apply displacement manually for NanoVG
        float animatedY1 = y1 + displacement;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            Color backgroundColor = new Color(18, 18, 18, 70);

            HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
            boolean enableBloom = hudEditor != null ? hudEditor.enableChatBloom.get() : true;

            if (enableBloom) {
                NanoVGHelper.drawRoundRectBloom(adjustedX1, animatedY1, width, height, radius, backgroundColor);
            } else {
                NanoVGHelper.drawRoundRect(adjustedX1, animatedY1, width, height, radius, backgroundColor);
            }
        });
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRenderPost(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (chatField == null || !chatField.getText().startsWith(Sakura.COMMAND.getPrefix())) return;
        
        NanoVGRenderer.INSTANCE.draw(vg -> {
            final float PAD = 0.5F;
            final Color SAKURA = new Color(255, 183, 197, 255); // Alpha is 255 now, no fade-in alpha needed?
            // Reference doesn't have fade-in alpha for text, just slide.
            
            int marginLeft = 4;
            
            // Recalculate base Y or use what we know about chat screen layout
            // Usually bottom - 14.
            float baseY = mc.getWindow().getScaledHeight() - 14;
            float animatedY = baseY + displacement;

            NanoVGHelper.drawRoundRectOutline(
                    marginLeft - 1.5f,
                    animatedY - PAD, // Use animatedY
                    340,
                    12 + PAD * 2,
                    getGlobalRadius(),
                    0.6f,
                    SAKURA
            );
            
            var window = ((IChatInputSuggestor) chatInputSuggestor).getWindow();
            if (window != null) {
                Rect2i a = ((ISuggestionWindow) window).getArea();
                // Suggestions usually appear above, maybe they shouldn't move? 
                // Or they should move with the input box?
                // Reference ChatScreenMixin doesn't seem to touch suggestor specifically, 
                // but since we translated the whole matrix during render, suggestor might be affected if it renders during render()?
                // Suggestor usually renders in render() too.
                // If suggestor renders *after* our pop, it won't move.
                // ChatScreen.render() calls chatInputSuggestor.render().
                // If that call is *between* our push and pop, it moves.
                // Our pop is after chatField.render().
                // We need to check where suggestor is rendered.
                // Usually it's rendered after.
                // If we want it to move, we should keep the pop later?
                // Reference ChatScreenMixin pops after EditBox.render.
                // So suggestor likely doesn't move in reference.
                // I will leave it as is for now.
                NanoVGHelper.drawRectOutline(a.getX() - PAD, a.getY() - PAD,
                        a.getWidth() + PAD * 2, a.getHeight() + PAD * 2, 0.7f, SAKURA);
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

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void hookSendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (Sakura.EVENT_BUS.post(new ChatMessageEvent.Client(chatText)).isCancelled()) ci.cancel();
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void onClosed(CallbackInfo ci) {
        wasOpenedLastFrame = false;
    }
}
