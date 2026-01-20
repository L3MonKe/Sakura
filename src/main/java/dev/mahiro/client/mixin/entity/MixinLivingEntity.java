package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.LemonClient;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.entity.SwingSpeedEvent;
import dev.mahiro.client.events.entity.UpdateServerPositionEvent;
import dev.mahiro.client.events.player.JumpEvent;
import dev.mahiro.client.events.player.JumpRotationEvent;
import dev.mahiro.client.events.player.SprintEvent;
import dev.mahiro.client.events.player.TravelEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.mahiro.client.LemonClient.mc;

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
            SprintEvent event = new SprintEvent();
            LemonClient.EVENT_BUS.post(event);
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
            LemonClient.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "travel", at = @At("RETURN"))
    private void onTravelPost(Vec3d movementInput, CallbackInfo ci) {
        if ((Object) this == mc.player) {
            TravelEvent event = new TravelEvent(EventType.POST, movementInput);
            LemonClient.EVENT_BUS.post(event);
        }
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    private float redirectGetYawInJump(LivingEntity instance) {
        if (instance == mc.player) {
            JumpRotationEvent event = new JumpRotationEvent(instance.getYaw());
            LemonClient.EVENT_BUS.post(event);
            return event.getYaw();
        }
        return instance.getYaw();
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void onJumpPre(CallbackInfo ci) {
        LemonClient.EVENT_BUS.post(new JumpEvent(EventType.PRE));
    }

    @Inject(method = "jump", at = @At("RETURN"))
    private void onJumpPost(CallbackInfo ci) {
        LemonClient.EVENT_BUS.post(new JumpEvent(EventType.POST));
    }

    @Inject(method = "getHandSwingDuration", at = @At("HEAD"), cancellable = true)
    private void hookGetHandSwingDuration(CallbackInfoReturnable<Integer> cir) {
        SwingSpeedEvent swingSpeedEvent = new SwingSpeedEvent();
        LemonClient.EVENT_BUS.post(swingSpeedEvent);
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
        LemonClient.EVENT_BUS.post(updateServerPositionEvent);
    }
}
