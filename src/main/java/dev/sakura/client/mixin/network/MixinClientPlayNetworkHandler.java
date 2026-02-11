package dev.sakura.client.mixin.network;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.client.GameJoinEvent;
import dev.sakura.client.event.impl.client.SendMessageEvent;
import dev.sakura.client.event.impl.entity.EntityVelocityUpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler extends ClientCommonNetworkHandler {
    @Shadow
    private ClientWorld world;

    protected MixinClientPlayNetworkHandler(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci, @Local(argsOnly = true) LocalRef<String> messageRef) {
        SendMessageEvent event = Sakura.EVENT_BUS.post(new SendMessageEvent(message));

        if (!event.isCancelled()) {
            messageRef.set(event.getMessage());
        } else {
            ci.cancel();
        }
    }

    @Inject(method = "onGameJoin", at = @At(value = "TAIL"))
    private void hookOnGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        Sakura.EVENT_BUS.post(new GameJoinEvent());
    }

    @Inject(method = "onEntityVelocityUpdate", at = @At("HEAD"), cancellable = true)
    public void onEntityVelocityUpdate(EntityVelocityUpdateS2CPacket packet, CallbackInfo ci) {
        NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, this.client.getPacketApplyBatcher());
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (entity == mc.player) {
                EntityVelocityUpdateEvent event = Sakura.EVENT_BUS.post(new EntityVelocityUpdateEvent());
                if (!event.isCancelled()) {
                    entity.setVelocityClient(packet.getVelocity());
                }
            } else {
                entity.setVelocityClient(packet.getVelocity());
            }
        }
        ci.cancel();
    }
}
