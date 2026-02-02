package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.gui.mainmenu.MainMenuScreen;
import dev.mahiro.client.shaders.SplashShader;
import dev.mahiro.client.utils.animations.AnimationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {
    @Shadow
    @Final
    private ResourceReload reload;

    @Shadow
    private float progress;

    @Shadow
    private long reloadCompleteTime;

    @Shadow
    private long reloadStartTime;

    @Shadow
    @Final
    private boolean reloading;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private Consumer<Optional<Throwable>> exceptionHandler;

    @Unique
    private boolean shaderInitialized = false;

    @Unique
    private float sakura$displayProgress = 0f;

    @Unique
    private long sakura$startTime = -1L;

    @Unique
    private long sakura$handoffStartTime = -1L;

    @Unique
    private boolean sakura$handoffScreenReady = false;

    @Unique
    private static final float PROGRESS_SMOOTH_SPEED = 0.3f;

    @Unique
    private static final long HANDOFF_DURATION_MS = 420L;

    @Unique
    private static final long MIN_DISPLAY_MS = 5000L;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        long currentTime = Util.getMeasuringTimeMs();

        if (!shaderInitialized) {
            SplashShader.getInstance().init();
            shaderInitialized = true;
            sakura$startTime = currentTime;
        }

        if (this.reloading && this.reloadStartTime == -1L) {
            this.reloadStartTime = currentTime;
        }

        float fadeOutProgress = this.reloadCompleteTime > -1L ? (float) (currentTime - this.reloadCompleteTime) / 1000.0F : -1.0F;
        float fadeInProgress = this.reloadStartTime > -1L ? (float) (currentTime - this.reloadStartTime) / 500.0F : -1.0F;

        float loadProgress = this.reload.getProgress();
        this.progress = MathHelper.clamp(this.progress * 0.95F + loadProgress * 0.05F, 0.0F, 1.0F);

        float timeGate = 1.0f;
        if (!this.reloading && sakura$startTime > 0L) {
            timeGate = MathHelper.clamp((float) (currentTime - sakura$startTime) / (float) MIN_DISPLAY_MS, 0f, 1f);
        }
        float targetProgress = Math.min(loadProgress, timeGate);

        sakura$displayProgress += (targetProgress - sakura$displayProgress) * PROGRESS_SMOOTH_SPEED * delta;
        sakura$displayProgress = MathHelper.clamp(sakura$displayProgress, 0f, 1f);

        if (targetProgress >= 1.0f) {
            sakura$displayProgress += (1f - sakura$displayProgress) * 0.1f;
        }

        float zoom = 1.0f;
        float fadeOut = 0f;

        if (SplashShader.getInstance().isTransitionStarted()) {
            float p = sakura$handoffStartTime <= 0L ? 0f : (float) (currentTime - sakura$handoffStartTime) / (float) HANDOFF_DURATION_MS;
            p = MathHelper.clamp(p, 0f, 1f);

            float ease = AnimationUtil.easeInOutCubic(p);
            zoom = 1.0f + 0.12f * AnimationUtil.easeOutCubic(p);
            fadeOut = AnimationUtil.smoothstep(0.02f, 1.0f, ease);

            if (!this.reloading) {
                if (sakura$handoffScreenReady && this.client.currentScreen != null) {
                    if (this.client.currentScreen.width != width || this.client.currentScreen.height != height) {
                        this.client.currentScreen.init(this.client, width, height);
                    }
                    if (this.client.currentScreen instanceof MainMenuScreen menu) {
                        menu.setEntranceProgress(ease);
                    }
                    this.client.currentScreen.render(context, 0, 0, delta);
                }
            } else if (this.client.currentScreen != null) {
                this.client.currentScreen.render(context, 0, 0, delta);
            }
        }

        if (fadeOut < 0.99f) {
            SplashShader.getInstance().render(width, height, sakura$displayProgress, fadeOut, zoom);
        }

        if (fadeOutProgress >= 0.6F || (sakura$handoffStartTime > 0L && currentTime - sakura$handoffStartTime >= HANDOFF_DURATION_MS)) {
            this.client.setOverlay(null);
            if (!this.reloading) {
                if (this.client.currentScreen == null) {
                    Mahiro.redirectToMainMenu();
                }
            }
            sakura$handoffScreenReady = false;
            SplashShader.getInstance().cleanup();
            shaderInitialized = false;
            sakura$handoffStartTime = -1L;
        }

        boolean minTimeOk = this.reloading || (sakura$startTime > 0L && currentTime - sakura$startTime >= MIN_DISPLAY_MS);
        if (this.reloadCompleteTime == -1L && this.reload.isComplete() && sakura$displayProgress >= 0.95f && (!this.reloading || fadeInProgress >= 2.0F) && minTimeOk) {
            try {
                this.reload.throwException();
                this.exceptionHandler.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.exceptionHandler.accept(Optional.of(throwable));
            }

            this.reloadCompleteTime = Util.getMeasuringTimeMs();
            SplashShader.getInstance().startTransition();
            sakura$handoffStartTime = this.reloadCompleteTime;

            if (!this.reloading) {
                Mahiro.redirectToMainMenu();
                if (this.client.currentScreen != null) {
                    this.client.currentScreen.init(this.client, width, height);
                }
                sakura$handoffScreenReady = true;
            } else if (this.client.currentScreen != null) {
                this.client.currentScreen.init(this.client, width, height);
            }
        }

        ci.cancel();
    }
}
