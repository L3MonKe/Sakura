package dev.mzc.client.mixin.network;

import dev.mzc.client.Sakura;
import dev.mzc.client.command.impl.MZCCommand;
import dev.mzc.client.events.client.ChatMessageEvent;
import dev.mzc.client.events.client.GameJoinEvent;
import dev.mzc.client.events.entity.EntityVelocityUpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler extends ClientCommonNetworkHandler {
    @Shadow
    private ClientWorld world;

    @Shadow
    private CommandDispatcher<CommandSource> commandDispatcher;

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

    @Inject(method = "onCommandTree", at = @At(value = "TAIL"))
    private void hookOnCommandTree(CommandTreeS2CPacket packet, CallbackInfo ci) {
        MZCCommand command = new MZCCommand();
        command.getCommandBuilders().forEach(builder -> {
            command.buildCommand(builder);
            commandDispatcher.register(builder);
        });
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
