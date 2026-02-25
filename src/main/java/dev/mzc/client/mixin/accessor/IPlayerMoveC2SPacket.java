package dev.mzc.client.mixin.accessor;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoveC2SPacket.class)
public interface IPlayerMoveC2SPacket {
    @Mutable
    @Accessor("onGround")
    void setOnGround(boolean onGround);

    @Accessor("pitch")
    float getPitch();

    @Mutable
    @Accessor("pitch")
    void setPitch(float pitch);

    @Accessor("yaw")
    float getYaw();

    @Mutable
    @Accessor("yaw")
    void setYaw(float yaw);
}
