package dev.sakura.client.mixin.accessor;

import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CloseHandledScreenC2SPacket.class)
public interface ICloseHandledScreenC2SPacket {
    @Accessor("syncId")
    int getSyncId();
}
