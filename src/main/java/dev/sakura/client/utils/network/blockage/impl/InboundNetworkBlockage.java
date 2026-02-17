package dev.sakura.client.utils.network.blockage.impl;

import dev.sakura.client.utils.network.blockage.DirectionalNetworkBlockage;
import dev.sakura.client.utils.network.blockage.block.PacketValidator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;

public final class InboundNetworkBlockage extends DirectionalNetworkBlockage<ClientPlayNetworkHandler> {
    public static InboundNetworkBlockage get() {
        return instance;
    }

    private static final InboundNetworkBlockage instance;

    static {
        instance = new InboundNetworkBlockage();
    }

    @Override
    protected void flushPacket(ClientConnection connection, Packet<?> packet) {
        if (connection != null && packet != null) {
            try {
                // Bypass event handling to avoid re-triggering interception
                // Using handlePacket instead of channelRead0 to simulate packet receiving
                if (MinecraftClient.getInstance().getNetworkHandler() != null) {
                   ((Packet) packet).apply(MinecraftClient.getInstance().getNetworkHandler());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final PacketValidator VISUAL_VALIDATOR = p -> {
        if (p instanceof EntityStatusS2CPacket status) {
            return status.getStatus() != 2 && status.getStatus() != 3;
        } else if (p instanceof EntityTrackerUpdateS2CPacket tracker) {
            final ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            return clientPlayer == null || tracker.id() == clientPlayer.getId();
        }
        return !(p instanceof EntityAnimationS2CPacket || p instanceof TitleS2CPacket || p instanceof TitleFadeS2CPacket || p instanceof ClearTitleS2CPacket ||
                p instanceof PlaySoundS2CPacket || p instanceof StopSoundS2CPacket || p instanceof ChatMessageS2CPacket || p instanceof ChatSuggestionsS2CPacket ||
                p instanceof EntityEquipmentUpdateS2CPacket || p instanceof SubtitleS2CPacket);
    };
}
