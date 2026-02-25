package dev.sakura.client.utils.packet;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import static dev.sakura.client.Sakura.mc;

/**
 * @Author：Gu-Yuemang
 * @Date：12/7/2025 3:21 PM
 */
public class OldNaming {
    public static PlayerMoveC2SPacket.OnGroundOnly C03PacketPlayer() {
        return new PlayerMoveC2SPacket.OnGroundOnly(mc.player.isOnGround(), false);
    }
}
