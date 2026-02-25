package dev.mzc.client.mixin.client;

import dev.mzc.client.BuildConfig;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.client.OpenScreenEvent;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.entity.AttackEvent;
import dev.mzc.client.events.input.HandleInputEvent;
import dev.mzc.client.module.impl.misc.NoFPSLimit;
import dev.mzc.client.module.impl.movement.NoSlow;
import dev.mzc.client.utils.player.MovementUtil;
import dev.mzc.client.shaders.WindowResizeCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    public abstract boolean isWindowFocused();

    @Shadow @Final public GameOptions options;

    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    @Final
    private Window window;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Pre());
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void onPostTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Post());
    }

    @Inject(method = "handleInputEvents()V", at = @At(value = "HEAD"))
    private void onHandleInputEvents(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new HandleInputEvent());
    }

    @ModifyArg(method = "updateWindowTitle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"))
    private String onUpdateWindowTitle(String title) {
        return "MZC-Client " + BuildConfig.VERSION;
    }

    @Inject(method = "onResolutionChanged()V", at = @At("TAIL"))
    private void captureResize(CallbackInfo ci) {
        WindowResizeCallback.EVENT.invoker().onResized((MinecraftClient) (Object) this, this.window);
    }

    @Inject(method = "doAttack()Z", at = @At("HEAD"))
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        MovementUtil.isAttacking = true;
        if (player != null && ((MinecraftClient) (Object) this).crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            Sakura.EVENT_BUS.post(new AttackEvent(entity));
        }
    }

    @Inject(method = "doAttack()Z", at = @At("TAIL"))
    private void onAttackTail(CallbackInfoReturnable<Boolean> cir) {
        MovementUtil.isAttacking = false;
    }

    @Inject(method = "setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void onSetScreen(Screen screen, CallbackInfo info) {
        Sakura.EVENT_BUS.post(new OpenScreenEvent(screen));
    }

    @ModifyArg(method = "render(Z)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;limitDisplayFPS(I)V"))
    private int modifyFramerateLimit(int original) {
        if (NoFPSLimit.INSTANCE != null && NoFPSLimit.INSTANCE.isEnabled()) {
            if (!this.isWindowFocused()) {
                // If module is enabled and window is NOT focused, use the user's max FPS setting
                // effectively overriding the low limit (usually 10 or 15) applied by the game.
                return MinecraftClient.getInstance().options.getMaxFps().getValue();
            }
        }
        return original;
    }
}
