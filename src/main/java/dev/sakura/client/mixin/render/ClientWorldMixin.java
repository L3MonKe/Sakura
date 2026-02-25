package dev.sakura.client.mixin.render;


import dev.sakura.client.Sakura;
import dev.sakura.client.events.entity.EntitySpawnEvent;
import dev.sakura.client.module.impl.render.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void getSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Integer> info) {
        if (Sakura.MODULES.getModule(Atmosphere.class).isEnabled() && Sakura.MODULES.getModule(Atmosphere.class).modifyFog.get()) {
            info.setReturnValue(Sakura.MODULES.getModule(Atmosphere.class).fogColor.get().getRGB());
        }
    }

    @Inject(method = "addEntity", at = @At(value = "HEAD"))
    private void addEntity(Entity entity, CallbackInfo info) {
        Sakura.EVENT_BUS.post(new EntitySpawnEvent(entity));
    }
}
