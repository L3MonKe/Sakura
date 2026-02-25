package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.mixin.accessor.IParticle;
import dev.sakura.client.module.impl.render.NoRender;
import dev.sakura.client.module.impl.render.TotemParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender != null && noRender.noExplosionParticles()) {
            if (parameters.getType() == ParticleTypes.EXPLOSION ||
                    parameters.getType() == ParticleTypes.EXPLOSION_EMITTER ||
                    parameters.getType() == ParticleTypes.SMOKE ||
                    parameters.getType() == ParticleTypes.LARGE_SMOKE) {
                cir.setReturnValue(null);
            }
        }

        TotemParticles totemParticles = Sakura.MODULES.getModule(TotemParticles.class);
        if (totemParticles != null && totemParticles.isEnabled() && totemParticles.isNoRender()) {
            if (parameters.getType() == ParticleTypes.TOTEM_OF_UNDYING) {
                cir.setReturnValue(null);
            }
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"))
    private void onAddParticleReturn(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        Particle particle = cir.getReturnValue();
        if (particle == null) return;

        TotemParticles totemParticles = Sakura.MODULES.getModule(TotemParticles.class);
        if (totemParticles != null && totemParticles.isEnabled() && !totemParticles.isNoRender()) {
            if (parameters.getType() == ParticleTypes.TOTEM_OF_UNDYING) {
                Color color = totemParticles.getNextColor();
                IParticle accessor = (IParticle) particle;
                accessor.setRed(color.getRed() / 255f);
                accessor.setGreen(color.getGreen() / 255f);
                accessor.setBlue(color.getBlue() / 255f);
            }
        }
    }

    @Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V", at = @At("HEAD"), cancellable = true)
    private void onAddEmitter(Entity entity, ParticleEffect parameters, int maxAge, CallbackInfo ci) {
        TotemParticles totemParticles = Sakura.MODULES.getModule(TotemParticles.class);
        if (totemParticles != null && totemParticles.isEnabled()) {
            if (parameters.getType() == ParticleTypes.TOTEM_OF_UNDYING) {
                if (totemParticles.isNoRender()) {
                    ci.cancel();
                } else {
                    totemParticles.resetIndex();
                }
            }
        }
    }

    @Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;)V", at = @At("HEAD"), cancellable = true)
    private void onAddEmitterNoAge(Entity entity, ParticleEffect parameters, CallbackInfo ci) {
        TotemParticles totemParticles = Sakura.MODULES.getModule(TotemParticles.class);
        if (totemParticles != null && totemParticles.isEnabled()) {
            if (parameters.getType() == ParticleTypes.TOTEM_OF_UNDYING) {
                if (totemParticles.isNoRender()) {
                    ci.cancel();
                } else {
                    totemParticles.resetIndex();
                }
            }
        }
    }
}