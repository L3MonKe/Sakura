package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.entity.BlockPushEvent;
import dev.mahiro.client.events.player.MotionEvent;
import dev.mahiro.client.events.player.PlayerTickEvent;
import dev.mahiro.client.events.player.SlowdownEvent;
import dev.mahiro.client.events.type.EventType;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Unique
    private MotionEvent motionEvent;

    @Inject(method = "sendMovementPackets", at = @At("HEAD"), cancellable = true)
    private void sendMovementPacketsHeadInject(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        Mahiro.EVENT_BUS.post(motionEvent = new MotionEvent(EventType.PRE, player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
        if (motionEvent.isCancelled()) ci.cancel();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
    private double xPositionSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.getX();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    private double yPositionSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.getY();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
    private double zPositionSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.getZ();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    private float yawSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.getYaw();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    private float pitchSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.getPitch();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
    private boolean onGroundPositionSendEventRedirect(ClientPlayerEntity player) {
        return motionEvent.isOnGround();
    }

    @Inject(method = "sendMovementPackets", at = @At("TAIL"))
    private void sendMovementPacketsTailInject(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        Mahiro.EVENT_BUS.post(new MotionEvent(EventType.POST, player.getYaw(), player.getPitch()));
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void onPushOutOfBlocks(double x, double z, CallbackInfo ci) {
        BlockPushEvent event = new BlockPushEvent((ClientPlayerEntity) (Object) this);
        Mahiro.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tickHeadHook(CallbackInfo ci) {
        if (Mahiro.EVENT_BUS.post(new PlayerTickEvent()).isCancelled()) ci.cancel();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean onSlowDown(ClientPlayerEntity instance) {
        SlowdownEvent event = new SlowdownEvent(instance.isUsingItem());
        Mahiro.EVENT_BUS.post(event);
        return event.isSlowdown();
    }
}
