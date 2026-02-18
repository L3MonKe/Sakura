package dev.sakura.client.mixin.render;


import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.entity.EntitySpawnEvent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addEntity", at = @At(value = "HEAD"))
    private void addEntity(Entity entity, CallbackInfo info) {
        Sakura.EVENT_BUS.post(new EntitySpawnEvent(entity));
    }

    @Redirect(method = "tickEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
    public void onTickEntity(Entity instance) {
        if (Sakura.skipTicks > 0 && instance == mc.player) {
            Sakura.skipTicks--;
        } else {
            instance.tick();
        }
    }
}
