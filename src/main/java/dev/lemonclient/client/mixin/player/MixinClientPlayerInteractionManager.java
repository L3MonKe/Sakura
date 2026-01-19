package dev.lemonclient.client.mixin.player;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.events.player.BlockEvent;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Inject(method = "attackBlock", at = @At("HEAD"))
    private void onPlayerAttackBlock(final BlockPos pos, final Direction face, final CallbackInfoReturnable<Boolean> info) {
        LemonClient.EVENT_BUS.post(new BlockEvent(pos, face));
    }
}
