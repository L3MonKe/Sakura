package dev.sakura.client.mixin.network;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.ChatMessageEvent;
import dev.sakura.client.events.client.GameJoinEvent;
import dev.sakura.client.events.entity.EntityVelocityUpdateEvent;
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

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler extends ClientCommonNetworkHandler {
    @Shadow
    private ClientWorld world;

    protected MixinClientPlayNetworkHandler(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "sendChatMessage", at = @At(value = "HEAD"), cancellable = true)
    private void hookSendChatMessage(String content, CallbackInfo ci) {
        if (Sakura.EVENT_BUS.post(new ChatMessageEvent.Server(content)).isCancelled()) ci.cancel();
    }

    @Inject(method = "onGameJoin", at = @At(value = "TAIL"))
    private void hookOnGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        Sakura.EVENT_BUS.post(new GameJoinEvent());
    }

    @Inject(method = "onEntityVelocityUpdate", at = @At("HEAD"), cancellable = true)
    public void onEntityVelocityUpdate(EntityVelocityUpdateS2CPacket packet, CallbackInfo ci) {
        NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (entity == MinecraftClient.getInstance().player) {
                EntityVelocityUpdateEvent event = new EntityVelocityUpdateEvent();
                Sakura.EVENT_BUS.post(event);
                if (!event.isCancelled()) {
                    entity.setVelocityClient(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
                }
            } else {
                entity.setVelocityClient(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
            }
        }
        ci.cancel();
    }
}
