package dev.mahiro.client.mixin.client;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.entity.AttackEvent;
import dev.mahiro.client.events.input.HandleInputEvent;
import dev.mahiro.client.shaders.WindowResizeCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public Screen currentScreen;

    @Shadow
    @Final
    private Window window;

    @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("TAIL"))
    private void onInit(RunArgs args, CallbackInfo ci) {
        Mahiro.init((MinecraftClient) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        Mahiro.EVENT_BUS.post(new TickEvent.Pre());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPostTick(CallbackInfo info) {
        TickEvent.Post event = new TickEvent.Post();
        Mahiro.EVENT_BUS.post(event);
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void onSetScreen(Screen screen, CallbackInfo ci) {
        if (AuthGate.interceptSetScreen((MinecraftClient) (Object) this, screen)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void onHandleInputEvents(CallbackInfo info) {
        Mahiro.EVENT_BUS.post(new HandleInputEvent());
    }

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    private void onResolutionChanged(CallbackInfo ci) {
        WindowResizeCallback.EVENT.invoker().onResized((MinecraftClient) (Object) this, this.window);
    }

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player != null && ((MinecraftClient) (Object) this).crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            Mahiro.EVENT_BUS.post(new AttackEvent(entity));
        }
    }
}
