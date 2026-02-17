package dev.sakura.client.mixin.network;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.utils.network.blockage.impl.InboundNetworkBlockage;
import dev.sakura.client.utils.player.PacketUtil;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class MixinClientConnection {
    @Shadow
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }

    @Shadow
    protected abstract void sendImmediately(Packet<?> packet, @Nullable ChannelFutureListener listener, boolean flush);

    @Redirect(method = "send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;sendImmediately(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;Z)V"))
    private void onSend(ClientConnection instance, Packet<?> packet, ChannelFutureListener listener, boolean flush) {
        if (PacketUtil.bypassPackets.contains(packet)) {
            PacketUtil.bypassPackets.remove(packet);
            this.sendImmediately(packet, listener, flush);
        } else {
            PacketEvent event = new PacketEvent(EventType.SEND, packet);
            Sakura.EVENT_BUS.post(event);
            if (!event.isCancelled()) {
                this.sendImmediately(event.getPacket(), listener, flush);
            }
        }
    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true, require = 1)
    private static void hookHandlePacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        if (packet instanceof BundleS2CPacket bundleS2CPacket) {
            ci.cancel();
            for (Packet<?> packetInBundle : bundleS2CPacket.getPackets()) {
                try {
                    handlePacket(packetInBundle, listener);
                } catch (OffThreadException ignored) {
                }
            }
            return;
        }

        PacketEvent event = new PacketEvent(EventType.RECEIVE, packet);
        Sakura.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
            return;
        }

        if (InboundNetworkBlockage.get().isBlocked(packet)) {
            ci.cancel();
        }
    }
}
