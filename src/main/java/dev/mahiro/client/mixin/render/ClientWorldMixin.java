package dev.mahiro.client.mixin.render;


import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.entity.EntitySpawnEvent;
import dev.mahiro.client.module.impl.render.Atmosphere;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void getSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Integer> info) {
        if (Mahiro.MODULES.getModule(Atmosphere.class).isEnabled() && Mahiro.MODULES.getModule(Atmosphere.class).modifyFog.get()) {
            info.setReturnValue(Mahiro.MODULES.getModule(Atmosphere.class).fogColor.get().getRGB());
        }
    }

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
