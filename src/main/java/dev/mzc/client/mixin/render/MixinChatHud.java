package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.mixin.accessor.IChatHudLineVisible;
import dev.mzc.client.module.impl.client.HudEditor;
import dev.mzc.client.module.impl.misc.BetterChat;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow public abstract int getLineHeight();
    @Shadow private int scrolledLines;
    @Shadow private java.util.List<net.minecraft.client.gui.hud.ChatHudLine.Visible> visibleMessages;
    @Shadow @Final private java.util.List<ChatHudLine> messages;
    @Shadow protected abstract void refresh();
    @Shadow @Final private MinecraftClient client;

    @Unique private long lastMessageTime = 0L;
    @Unique private Text lastMessageOriginal;
    @Unique private int sameMessageCount = 1;

    
    // Background rendering variables
    @Unique private static final int CHAT_MARGIN_LEFT = 4;
    @Unique private static int minX = Integer.MAX_VALUE;
    @Unique private static int minY = Integer.MAX_VALUE;
    @Unique private static int maxX = Integer.MIN_VALUE;
    @Unique private static int maxY = Integer.MIN_VALUE;
    @Unique private static boolean shouldRender = false;
    @Unique private static DrawContext cachedContext = null;
    @Unique private float currentDisplacement = 0f;
    @Unique private float backgroundAlpha = 0f;
    @Unique private int currentTick = 0;

    @Unique
    private float calculateDisplacement() {
        BetterChat chatAnimation = Sakura.MODULES.getModule(BetterChat.class);
        if (chatAnimation == null || !chatAnimation.isEnabled() || !chatAnimation.enableMessageAnim.get()) {
            return 0;
        }

        // If scrolled, no animation.
        if (scrolledLines != 0) return 0;

        float fadeTime = chatAnimation.messageAnimTime.get().floatValue();
        int lineHeight = getLineHeight();
        float fadeOffsetYScale = 0.8f;
        float maxDisplacement = (float)lineHeight * fadeOffsetYScale;
        long lifetime = System.currentTimeMillis() - lastMessageTime;
        float alpha = Math.min(lifetime / fadeTime, 1f);

        return (maxDisplacement - (alpha * maxDisplacement));
    }

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), argsOnly = true)
    private Text modifyMessageText(Text message) {
        BetterChat betterChat = Sakura.MODULES.getModule(BetterChat.class);
        if (betterChat != null && betterChat.isEnabled() && betterChat.stackDuplicates.get()) {
            if (lastMessageOriginal != null && message.getString().equals(lastMessageOriginal.getString())) {
                sameMessageCount++;
                if (!this.messages.isEmpty()) {
                    this.messages.remove(0);
                }
                this.refresh();
                return Text.empty().append(message).append(Text.literal(" (x" + sameMessageCount + ")").formatted(Formatting.GRAY));
            } else {
                sameMessageCount = 1;
                lastMessageOriginal = message;
            }
        } else {
             sameMessageCount = 1;
             lastMessageOriginal = message;
        }
        return message;
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        // Reset background bounds
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        shouldRender = false;
        cachedContext = context;
        backgroundAlpha = 0f;
        this.currentTick = currentTick;

        float displacement = calculateDisplacement();
        this.currentDisplacement = displacement;
        context.getMatrices().push();
        context.getMatrices().translate(0f, displacement, 0f);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        if (shouldRender) {
             renderCustomBackground();
        }

        context.getMatrices().pop();
    }
    
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectChatBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        // Just capture bounds
        minX = Math.min(minX, x1 + CHAT_MARGIN_LEFT);
        minY = Math.min(minY, y1);
        maxX = Math.max(maxX, x2 + CHAT_MARGIN_LEFT);
        maxY = Math.max(maxY, y2);
        shouldRender = true;
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("TAIL"))
    private void onAddMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        lastMessageTime = System.currentTimeMillis();
    }
    
    @Unique
    private float calculateMaxOpacity(int currentTick) {
        float maxOpacity = 0f;
        if (this.visibleMessages == null) return 0f;
        
        double chatOpacity = this.client.options.getChatOpacity().getValue();
        
        for (ChatHudLine.Visible line : this.visibleMessages) {
            int age = currentTick - ((IChatHudLineVisible) (Object) line).getAddedTime();
            if (age < 200) { // 200 ticks = 10 seconds
                double d = (double)age / 200.0;
                d = 1.0 - d;
                d *= 10.0;
                d = MathHelper.clamp(d, 0.0, 1.0);
                d *= d;
                maxOpacity = Math.max(maxOpacity, (float)(d * chatOpacity));
            }
        }
        return maxOpacity;
    }

    @Unique
    private void renderCustomBackground() {
        if (!shouldRender) return;

        float alpha = calculateMaxOpacity(this.currentTick);
        this.backgroundAlpha = alpha;
        
        // If alpha is too low, don't render to save performance and ensure clean exit
        if (this.backgroundAlpha <= 0.01f) return;

        float radius = getGlobalRadius();
        int width = maxX - minX;
        int height = maxY - minY;
        float padding = 4f;
        float finalWidth = width + padding * 2;
        float finalHeight = height + padding * 2;
        float currentX = minX - 4f + 6F;
        float currentY = minY - 4f + currentDisplacement;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            int baseAlpha = 70;
            int finalAlpha = (int) (baseAlpha * backgroundAlpha);
            Color backgroundColor = new Color(18, 18, 18, finalAlpha);

            HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
            boolean enableBloom = hudEditor != null ? hudEditor.enableChatBloom.get() : true;

            if (enableBloom) {
                NanoVGHelper.drawRoundRectBloom(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            } else {
                NanoVGHelper.drawRoundRect(currentX, currentY, finalWidth, finalHeight, radius, backgroundColor);
            }
        });
    }

    @Unique
    private float getGlobalRadius() {
        HudEditor hudEditor = Sakura.MODULES.getModule(HudEditor.class);
        if (hudEditor != null) {
            return hudEditor.globalCornerRadius.get().floatValue();
        }
        return 3f;
    }
}
