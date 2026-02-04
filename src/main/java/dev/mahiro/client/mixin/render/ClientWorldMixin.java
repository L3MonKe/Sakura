package dev.mahiro.client.mixin.render;


import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.entity.EntitySpawnEvent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addEntity", at = @At(value = "HEAD"))
    private void addEntity(Entity entity, CallbackInfo info) {
        Mahiro.EVENT_BUS.post(new EntitySpawnEvent(entity));
    }

    @Redirect(method = "tickEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
    public void hookSkipTicks(Entity instance) {
        if (Mahiro.skipTicks > 0 && instance == mc.player) {
            Mahiro.skipTicks--;
        } else {
            instance.tick();
        }
    }
}
