package dev.sakura.client.mixin.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.input.HandleInputEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("TAIL"))
    private void onInit(RunArgs args, CallbackInfo ci) {
        Sakura.init((MinecraftClient) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Pre());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPostTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Post());
    }

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void onHandleInputEvents(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new HandleInputEvent());
    }
}
