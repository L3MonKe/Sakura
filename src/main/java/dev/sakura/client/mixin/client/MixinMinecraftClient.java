package dev.sakura.client.mixin.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.entity.AttackEvent;
import dev.sakura.client.events.input.HandleInputEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
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
    @Final
    private Window window;

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

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player != null && ((MinecraftClient) (Object) this).crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            Sakura.EVENT_BUS.post(new AttackEvent(entity));
        }
    }
}
