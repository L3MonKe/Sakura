package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.render.Render2DEvent;
import dev.mzc.client.module.impl.hud.HotbarHud;
import dev.mzc.client.module.impl.hud.PotionHud;
import dev.mzc.client.module.impl.hud.ScoreboardHud;
import dev.mzc.client.module.impl.render.NoRender;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Shadow @org.jetbrains.annotations.Nullable public net.minecraft.text.Text title;
    @Shadow @org.jetbrains.annotations.Nullable public net.minecraft.text.Text subtitle;
    @Shadow public int titleFadeInTicks;
    @Shadow public int titleStayTicks;
    @Shadow public int titleFadeOutTicks;
    @Shadow public int titleRemainTicks;
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!Sakura.UI_HIDDEN) {
            Sakura.EVENT_BUS.post(new Render2DEvent(context));
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void onRenderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        HotbarHud hotbarHud = Sakura.MODULES.getModule(HotbarHud.class);
        if (hotbarHud != null && hotbarHud.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusEffectOverlay(CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noPotionIcons()) ci.cancel();
        PotionHud potionHud = Sakura.MODULES.getModule(PotionHud.class);
        if (potionHud != null && potionHud.isEnabled()) ci.cancel();
    }

    @Inject(method = "renderPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderPortalOverlay(DrawContext context, float nauseaStrength, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noPortalOverlay()) ci.cancel();
    }

    @ModifyArgs(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private void onRenderPumpkinOverlay(Args args) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noPumpkinOverlay()) args.set(2, 0f);
    }

    @ModifyArgs(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    private void onRenderPowderedSnowOverlay(Args args) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noPowderedSnowOverlay()) args.set(2, 0f);
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderVignetteOverlay(DrawContext context, Entity entity, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noVignette()) ci.cancel();
    }

    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderScoreboardSidebar(DrawContext context, ScoreboardObjective objective, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noScoreboard()) ci.cancel();
        ScoreboardHud scoreboardHud = Sakura.MODULES.getModule(ScoreboardHud.class);
        if (scoreboardHud != null && scoreboardHud.isEnabled()) ci.cancel();
    }

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderSpyglassOverlay(DrawContext context, float scale, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noSpyglassOverlay()) ci.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noCrosshair()) ci.cancel();
    }

    @Inject(method = "renderTitleAndSubtitle", at = @At("HEAD"), cancellable = true)
    private void onRenderTitle(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noTitle()) {
            ci.cancel();
            return;
        }

        // Custom Title Rendering for Global Font
        if (dev.mzc.client.module.impl.client.ClickGui.globalFontReplacement.get()) {
            // Cancel vanilla rendering to handle it ourselves
            ci.cancel();

            net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
            if (this.title != null) {
                context.getMatrices().push();
                context.getMatrices().translate(0.0f, 0.0f, 800.0f); // Ensure it's on top
                
                int width = context.getScaledWindowWidth();
                int height = context.getScaledWindowHeight();
                
                // Title
                // Use a larger scale for title to mimic vanilla behavior (scale 4.0 for title)
                float titleScale = 3.0f; // Slightly smaller than vanilla 4.0 because custom font is usually larger/clearer
                context.getMatrices().scale(titleScale, titleScale, titleScale);
                
                float centerX = width / 2.0f / titleScale;
                float centerY = (height / 2.0f - 40.0f) / titleScale;
                
                int titleColor = 0xFFFFFF;
                if (this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks > 0) {
                     int alpha = 255;
                     if (this.titleRemainTicks > this.titleFadeOutTicks + this.titleStayTicks) {
                         float f = (float)(this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks - this.titleRemainTicks) / (float)this.titleFadeInTicks;
                         alpha = (int)(f * 255.0F);
                     } else if (this.titleRemainTicks <= this.titleFadeOutTicks) {
                         float f = (float)this.titleRemainTicks / (float)this.titleFadeOutTicks;
                         alpha = (int)(f * 255.0F);
                     }
                     alpha = net.minecraft.util.math.MathHelper.clamp(alpha, 0, 255);
                     
                     if (alpha > 8) {
                         int finalColor = (titleColor & 0xFFFFFF) | (alpha << 24);
                         context.drawCenteredTextWithShadow(mc.textRenderer, this.title, (int)centerX, (int)centerY, finalColor);
                     }
                }
                
                context.getMatrices().pop();
            }
            
            if (this.subtitle != null) {
                 context.getMatrices().push();
                 context.getMatrices().translate(0.0f, 0.0f, 800.0f);
                 
                 int width = context.getScaledWindowWidth();
                 int height = context.getScaledWindowHeight();
                 
                 float subtitleScale = 1.5f; // Vanilla is 2.0
                 context.getMatrices().scale(subtitleScale, subtitleScale, subtitleScale);
                 
                 float centerX = width / 2.0f / subtitleScale;
                 float centerY = (height / 2.0f + 10.0f) / subtitleScale;
                 
                 int subtitleColor = 0xFFFFFF;
                  int alpha = 255;
                     if (this.titleRemainTicks > this.titleFadeOutTicks + this.titleStayTicks) {
                         float f = (float)(this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks - this.titleRemainTicks) / (float)this.titleFadeInTicks;
                         alpha = (int)(f * 255.0F);
                     } else if (this.titleRemainTicks <= this.titleFadeOutTicks) {
                         float f = (float)this.titleRemainTicks / (float)this.titleFadeOutTicks;
                         alpha = (int)(f * 255.0F);
                     }
                     alpha = net.minecraft.util.math.MathHelper.clamp(alpha, 0, 255);
                 
                 if (alpha > 8) {
                     int finalColor = (subtitleColor & 0xFFFFFF) | (alpha << 24);
                     context.drawCenteredTextWithShadow(mc.textRenderer, this.subtitle, (int)centerX, (int)centerY, finalColor);
                 }
                 
                 context.getMatrices().pop();
            }
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    private void onRenderHeldItemTooltip(DrawContext context, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noHeldItemName()) ci.cancel();
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
        HotbarHud hotbarHud = Sakura.MODULES.getModule(HotbarHud.class);
        if (hotbarHud != null && hotbarHud.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void onRenderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        HotbarHud hotbarHud = Sakura.MODULES.getModule(HotbarHud.class);
        if (hotbarHud != null && hotbarHud.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderNauseaOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderNausea(DrawContext context, float distortionStrength, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noNausea()) ci.cancel();
    }
}
