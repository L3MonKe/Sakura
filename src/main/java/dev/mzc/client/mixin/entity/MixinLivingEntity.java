package dev.mzc.client.mixin.entity;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.entity.SwingSpeedEvent;
import dev.mzc.client.events.entity.UpdateServerPositionEvent;
import dev.mzc.client.events.player.JumpEvent;
import dev.mzc.client.events.player.JumpRotationEvent;
import dev.mzc.client.events.player.SprintEvent;
import dev.mzc.client.events.player.TravelEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.mzc.client.Sakura.mc;

import dev.mzc.client.module.impl.movement.MoveFix;
import dev.mzc.client.module.impl.movement.NoSlow;
import dev.mzc.client.module.impl.movement.AutoSprint;
import dev.mzc.client.utils.player.MovementUtil;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Final
    @Shadow
    private static EntityAttributeModifier SPRINTING_SPEED_BOOST;

    @Shadow
    public EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute) {
        return this.getAttributes().getCustomInstance(attribute);
    }

    @Shadow
    public AttributeContainer getAttributes() {
        return null;
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    public void setSprintingHook(boolean sprinting, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            if (!sprinting) {
                if (Sakura.MODULES.getModule(NoSlow.class).isEnabled() && Sakura.MODULES.getModule(NoSlow.class).isItem() && mc.player.isUsingItem() && MovementUtil.isMoving()) {
                    ci.cancel();
                    return;
                }
                if (Sakura.MODULES.getModule(AutoSprint.class).isEnabled() && Sakura.MODULES.getModule(AutoSprint.class).isNoSlowAttack() && MovementUtil.isAttacking) {
                    ci.cancel();
                    return;
                }
            }

            MoveFix moveFix = Sakura.MODULES.getModule(MoveFix.class);
            if (!sprinting && moveFix != null && moveFix.isEnabled() && moveFix.grim.get()) {
                if (mc.options.sprintKey.isPressed() && (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0)) {
                    // Don't stop sprinting if MoveFix is active, we are moving, and sprint key is held
                    // unless we have hunger or other issues (vanilla handles those before calling setSprinting,
                    // but if vanilla calls setSprinting(false) due to direction, we want to ignore it)
                    // However, we should respect hunger/blindness/collisions if possible.
                    // Vanilla check: if (input.forward >= 0.8F && !isSprinting() && ...) setSprinting(true)
                    // else if ((input.forward < 0.8F || collision || hunger) && isSprinting()) setSprinting(false)

                    // If we are here with sprinting=false, it means vanilla wants to stop sprinting.
                    // We only want to prevent stopping if it's due to direction.
                    // Hunger/Collision checks are hard to replicate exactly without accessing private fields,
                    // but we can check the most common ones.
                    boolean blocked = mc.player.horizontalCollision || mc.player.getHungerManager().getFoodLevel() <= 6;
                    if (!blocked) {
                        ci.cancel(); // Ignore the command to stop sprinting
                        return;
                    }
                }
            }

            SprintEvent event = new SprintEvent();
            Sakura.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
                sprinting = event.isSprint();
                super.setSprinting(sprinting);
                EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
                entityAttributeInstance.removeModifier(SPRINTING_SPEED_BOOST.id());
                if (sprinting) {
                    entityAttributeInstance.addTemporaryModifier(SPRINTING_SPEED_BOOST);
                }
            }
        }
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void onTravelPre(Vec3d movementInput, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            TravelEvent event = new TravelEvent(EventType.PRE, movementInput);
            Sakura.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "travel", at = @At("RETURN"))
    private void onTravelPost(Vec3d movementInput, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            TravelEvent event = new TravelEvent(EventType.POST, movementInput);
            Sakura.EVENT_BUS.post(event);
        }
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    private float redirectGetYawInJump(LivingEntity instance) {
        if (instance == mc.player) {
            JumpRotationEvent event = new JumpRotationEvent(instance.getYaw());
            Sakura.EVENT_BUS.post(event);
            return event.getYaw();
        }
        return instance.getYaw();
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void onJumpPre(CallbackInfo ci) {
        Sakura.EVENT_BUS.post(new JumpEvent(EventType.PRE));
    }

    @Inject(method = "jump", at = @At("RETURN"))
    private void onJumpPost(CallbackInfo ci) {
        Sakura.EVENT_BUS.post(new JumpEvent(EventType.POST));
    }

    @Inject(method = "jump", at = @At("TAIL"))
    private void afterJumpSetCooldown(CallbackInfo ci) {
        if ((Object) this == mc.player) {
            dev.mzc.client.module.impl.movement.NoJumpDelay module = Sakura.MODULES.getModule(dev.mzc.client.module.impl.movement.NoJumpDelay.class);
            if (module != null && module.isEnabled() && module.shouldApply()) {
                ((dev.mzc.client.mixin.accessor.ILivingEntity) (Object) this).setLastJumpCooldown(module.getLegitCooldown());
            }
        }
    }

    @Inject(method = "getHandSwingDuration", at = @At("HEAD"), cancellable = true)
    private void hookGetHandSwingDuration(CallbackInfoReturnable<Integer> cir) {
        SwingSpeedEvent swingSpeedEvent = new SwingSpeedEvent();
        Sakura.EVENT_BUS.post(swingSpeedEvent);
        if (swingSpeedEvent.isCancelled()) {
            if (swingSpeedEvent.getSelfOnly() && ((Object) this != mc.player)) {
                return;
            }
            cir.cancel();
            cir.setReturnValue(swingSpeedEvent.getSwingSpeed());
        }
    }

    @Inject(method = "updateTrackedPositionAndAngles", at = @At(value = "HEAD"))
    private void hookUpdateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, CallbackInfo ci) {
        UpdateServerPositionEvent updateServerPositionEvent = new UpdateServerPositionEvent((LivingEntity) (Object) this, x, y, z, yaw, pitch);
        Sakura.EVENT_BUS.post(updateServerPositionEvent);
    }
}
