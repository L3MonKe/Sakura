package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.mixin.accessor.IParticle;
import dev.mzc.client.module.impl.render.AttackEffect;
import dev.mzc.client.module.impl.render.NoRender;
import dev.mzc.client.module.impl.render.TotemParticles;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {

    @Shadow
    public abstract void addBlockBreakParticles(BlockPos pos, BlockState state);

    @Shadow
    public abstract Particle addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

    private final ThreadLocal<Boolean> isSpawning = ThreadLocal.withInitial(() -> false);

    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void onAddBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender != null && noRender.noBlockBreakParticles()) {
            ci.cancel();
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (isSpawning.get()) return;

        AttackEffect attackEffect = Sakura.MODULES.getModule(AttackEffect.class);
        if (attackEffect != null && attackEffect.isEnabled()) {
            boolean isCrit = parameters.getType() == ParticleTypes.CRIT;
            boolean isAlwaysTrigger = attackEffect.always.get() && (
                parameters.getType() == ParticleTypes.DAMAGE_INDICATOR || 
                parameters.getType() == ParticleTypes.ENCHANTED_HIT
            );

            if (isCrit || isAlwaysTrigger) {
                isSpawning.set(true);
                try {
                    // Force burst effect for normal hits or if velocity is very low
                    double vx = velocityX;
                    double vy = velocityY;
                    double vz = velocityZ;
                    
                    // If it's a normal hit (always trigger) OR the velocity is negligible, force a random spread
                    // Standard CRIT particles usually have some velocity. Normal DAMAGE_INDICATOR particles usually have (0, 0.5, 0) or similar.
                    // We want to override the default "float up" behavior of DAMAGE_INDICATOR with a "burst out" behavior.
                    if (isAlwaysTrigger || (Math.abs(vx) + Math.abs(vy) + Math.abs(vz) < 0.1)) {
                         vx = (Math.random() * 2.0 - 1.0) * 0.4;
                         vy = Math.random() * 0.4;
                         vz = (Math.random() * 2.0 - 1.0) * 0.4;
                    }

                    Particle p = this.addParticle(attackEffect.particle.get().getEffect(), x, y, z, vx, vy, vz);
                    if (p != null) {
                        double multiplier = attackEffect.velocityMultiplier.get();
                        ((IParticle) p).setVelocityX(vx * multiplier);
                        ((IParticle) p).setVelocityY(vy * multiplier);
                        ((IParticle) p).setVelocityZ(vz * multiplier);
                        
                        int newMaxAge = (int) (p.getMaxAge() * attackEffect.lifeMultiplier.get());
                        p.setMaxAge(Math.max(1, newMaxAge));
                    }
                    cir.setReturnValue(p);
                } finally {
                    isSpawning.set(false);
                }
                return;
            }
        }

        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender != null && noRender.noExplosionParticles()) {
            if (parameters.getType() == ParticleTypes.EXPLOSION ||
                    parameters.getType() == ParticleTypes.EXPLOSION_EMITTER ||
                    parameters.getType() == ParticleTypes.SMOKE ||
                    parameters.getType() == ParticleTypes.LARGE_SMOKE) {
                cir.setReturnValue(null);
            }
        }
        // Removed block break particles check from here as it is handled by onAddBlockBreakParticles
        if (noRender != null && noRender.noEatParticles()) {
            if (parameters.getType() == ParticleTypes.ITEM) {
                cir.setReturnValue(null);
            }
        }
        if (noRender != null && noRender.noPotionParticles()) {
            if (parameters.getType() == ParticleTypes.ENTITY_EFFECT ||
                parameters.getType() == ParticleTypes.EFFECT) {
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