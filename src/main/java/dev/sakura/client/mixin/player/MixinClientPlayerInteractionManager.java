package dev.sakura.client.mixin.player;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.player.BlockEvent;
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
        Sakura.EVENT_BUS.post(new BlockEvent(pos, face));
    }
}
