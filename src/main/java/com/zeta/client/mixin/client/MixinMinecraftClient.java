package com.zeta.client.mixin.client;

import com.zeta.client.Zeta;
import com.zeta.client.auth.AuthGate;
import com.zeta.client.events.client.TickEvent;
import com.zeta.client.events.entity.AttackEvent;
import com.zeta.client.events.input.HandleInputEvent;
import com.zeta.client.shaders.WindowResizeCallback;
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
import org.spongepowered.asm.mixin.injection.ModifyArg;
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
        Zeta.init((MinecraftClient) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        Zeta.EVENT_BUS.post(new TickEvent.Pre());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPostTick(CallbackInfo info) {
        TickEvent.Post event = new TickEvent.Post();
        Zeta.EVENT_BUS.post(event);
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void onSetScreen(Screen screen, CallbackInfo ci) {
        if (AuthGate.interceptSetScreen((MinecraftClient) (Object) this, screen)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void onHandleInputEvents(CallbackInfo info) {
        Zeta.EVENT_BUS.post(new HandleInputEvent());
    }

    @ModifyArg(method = "updateWindowTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"))
    private String setTitle(String original) {
        return "桜";
    }

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    private void onResolutionChanged(CallbackInfo ci) {
        WindowResizeCallback.EVENT.invoker().onResized((MinecraftClient) (Object) this, this.window);
    }

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player != null && ((MinecraftClient) (Object) this).crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            Zeta.EVENT_BUS.post(new AttackEvent(entity));
        }
    }
}
