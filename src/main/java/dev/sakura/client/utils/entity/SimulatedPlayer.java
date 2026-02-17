package dev.sakura.client.utils.entity;

import dev.sakura.client.utils.movement.DirectionalInput;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimulatedPlayer implements PlayerSimulation {
    private static final double STEP_HEIGHT = 0.6;

    private final PlayerEntity player;
    public SimulatedPlayerInput input;
    public Vec3d pos;
    public Vec3d velocity;
    public Box boundingBox;
    public float yaw;
    public float pitch;
    private boolean sprinting;

    public double fallDistance;
    private int jumpingCooldown;
    private boolean isJumping;
    private boolean isFallFlying;
    public boolean onGround;
    public boolean horizontalCollision;
    private boolean verticalCollision;

    private boolean touchingWater;
    private boolean isSwimming;
    private boolean submergedInWater;
    private final Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    private final Set<TagKey<Fluid>> submergedFluidTag;

    private int simulatedTicks = 0;
    private boolean clipLedged = false;

    public SimulatedPlayer(PlayerEntity player, SimulatedPlayerInput input, Vec3d pos, Vec3d velocity, Box boundingBox, float yaw, float pitch, boolean sprinting, double fallDistance, int jumpingCooldown, boolean isJumping, boolean isFallFlying, boolean onGround, boolean horizontalCollision, boolean verticalCollision, boolean touchingWater, boolean isSwimming, boolean submergedInWater) {
        this.player = player;
        this.input = input;
        this.pos = pos;
        this.velocity = velocity;
        this.boundingBox = boundingBox;
        this.yaw = yaw;
        this.pitch = pitch;
        this.sprinting = sprinting;
        this.fallDistance = fallDistance;
        this.jumpingCooldown = jumpingCooldown;
        this.isJumping = isJumping;
        this.isFallFlying = isFallFlying;
        this.onGround = onGround;
        this.horizontalCollision = horizontalCollision;
        this.verticalCollision = verticalCollision;
        this.touchingWater = touchingWater;
        this.isSwimming = isSwimming;
        this.submergedInWater = submergedInWater;
        
        this.fluidHeight = new Object2DoubleArrayMap<>();
        this.fluidHeight.put(FluidTags.WATER, player.getFluidHeight(FluidTags.WATER));
        this.fluidHeight.put(FluidTags.LAVA, player.getFluidHeight(FluidTags.LAVA));
        
        this.submergedFluidTag = new HashSet<>();
        if (player.isSubmergedIn(FluidTags.WATER)) this.submergedFluidTag.add(FluidTags.WATER);
        if (player.isSubmergedIn(FluidTags.LAVA)) this.submergedFluidTag.add(FluidTags.LAVA);
    }

    public static SimulatedPlayer fromClientPlayer(SimulatedPlayerInput input) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null) return null;

        return new SimulatedPlayer(
                player,
                input,
                player.getEntityPos(),
                player.getVelocity(),
                player.getBoundingBox(),
                player.getYaw(),
                player.getPitch(),
                player.isSprinting(),
                player.fallDistance,
                0, 
                input.playerInput.jump(), 
                player.isGliding(),
                player.isOnGround(),
                player.horizontalCollision,
                player.verticalCollision,
                player.isTouchingWater(),
                player.isSwimming(),
                player.isSubmergedInWater()
        );
    }

    public static SimulatedPlayer fromOtherPlayer(PlayerEntity player, SimulatedPlayerInput input) {
        return new SimulatedPlayer(
                player,
                input,
                player.getEntityPos(),
                player.getEntityPos().subtract(player.lastX, player.lastY, player.lastZ),
                player.getBoundingBox(),
                player.getYaw(),
                player.getPitch(),
                player.isSprinting(),
                player.fallDistance,
                0,
                false,
                player.isGliding(),
                player.isOnGround(),
                player.horizontalCollision,
                player.verticalCollision,
                player.isTouchingWater(),
                player.isSwimming(),
                player.isSubmergedInWater()
        );
    }

    @Override
    public Vec3d getPos() {
        return pos;
    }

    @Override
    public void tick() {
        clipLedged = false;
        if (pos.y <= -70) return;

        input.tick();
        
        checkWaterState();
        updateSubmergedInWaterState();
        updateSwimming();

        if (jumpingCooldown > 0) jumpingCooldown--;

        isJumping = input.playerInput.jump();

        Vec3d d = velocity;
        double h = Math.abs(d.x) < 0.003 ? 0.0 : d.x;
        double i = Math.abs(d.y) < 0.003 ? 0.0 : d.y;
        double j = Math.abs(d.z) < 0.003 ? 0.0 : d.z;

        if (onGround) isFallFlying = false;

        velocity = new Vec3d(h, i, j);

        if (isJumping) {
            double k = isInLava() ? getFluidHeight(FluidTags.LAVA) : getFluidHeight(FluidTags.WATER);
            boolean bl = isTouchingWater() && k > 0.0;
            double swimHeight = getSwimHeight();

            if (bl && (!onGround || k > swimHeight)) {
                swimUpward(FluidTags.WATER);
            } else if (isInLava() && (!onGround || k > swimHeight)) {
                swimUpward(FluidTags.LAVA);
            } else if ((onGround || bl && k <= swimHeight) && jumpingCooldown == 0) {
                jump();
                jumpingCooldown = 10;
            }
        }

        double sidewaysSpeed = input.movementSideways * 0.98;
        double forwardSpeed = input.movementForward * 0.98;
        
        if (hasStatusEffect(StatusEffects.SLOW_FALLING) || hasStatusEffect(StatusEffects.LEVITATION)) {
            onLanding();
        }

        travel(new Vec3d(sidewaysSpeed, 0.0, forwardSpeed));
        
        simulatedTicks++;
    }

    private void travel(Vec3d movementInput) {
        if (isSwimming && !player.hasVehicle()) {
            double g = getRotationVector().y;
            double h = g < -0.2 ? 0.085 : 0.06;
            if (g <= 0.0 || input.playerInput.jump() || !player.getEntityWorld().getBlockState(BlockPos.ofFloored(pos.x, pos.y + 1.0 - 0.1, pos.z)).getFluidState().isEmpty()) {
                velocity = velocity.add(0.0, (g - velocity.y) * h, 0.0);
            }
        }

        double d = 0.08;
        boolean bl = velocity.y <= 0.0;
        if (velocity.y <= 0.0 && hasStatusEffect(StatusEffects.SLOW_FALLING)) {
            d = 0.01;
            onLanding();
        }

        if (isTouchingWater() && player.shouldSwimInFluids()) {
            double e = pos.y;
            float f = sprinting ? 0.9f : 0.8f;
            float g = 0.02f;
            float h = (float) getAttributeValue(EntityAttributes.WATER_MOVEMENT_EFFICIENCY);
            
            if (!onGround) h *= 0.5f;
            if (h > 0.0f) {
                f += (0.54600006f - f) * h / 3.0f;
                g += (getMovementSpeed() - g) * h / 3.0f;
            }
            if (hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) f = 0.96f;
            
            updateVelocity(g, movementInput);
            move(velocity);
            
            Vec3d vec3d = velocity;
            if (horizontalCollision && isClimbing()) {
                vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
            }
            velocity = vec3d.multiply(f, 0.8, f);
            velocity = applyFluidMovingSpeed(d, bl, velocity);
            
            if (horizontalCollision && doesNotCollide(velocity.x, velocity.y + STEP_HEIGHT - pos.y + e, velocity.z)) {
                velocity = new Vec3d(velocity.x, 0.3, velocity.z);
            }
        } else if (isInLava() && player.shouldSwimInFluids()) {
            double e = pos.y;
            updateVelocity(0.02f, movementInput);
            move(velocity);
            
            if (getFluidHeight(FluidTags.LAVA) <= getSwimHeight()) {
                velocity = velocity.multiply(0.5, 0.8, 0.5);
                velocity = applyFluidMovingSpeed(d, bl, velocity);
            } else {
                velocity = velocity.multiply(0.5);
            }
            if (!player.hasNoGravity()) {
                velocity = velocity.add(0.0, -d / 4.0, 0.0);
            }
            if (horizontalCollision && doesNotCollide(velocity.x, velocity.y + STEP_HEIGHT - pos.y + e, velocity.z)) {
                velocity = new Vec3d(velocity.x, 0.3, velocity.z);
            }
        } else if (isFallFlying) {
             BlockPos blockPos = getVelocityAffectingPos();
             float p = player.getEntityWorld().getBlockState(blockPos).getBlock().getSlipperiness();
             float f = onGround ? p * 0.91f : 0.91f;
             Vec3d vec3d6 = applyMovementInput(movementInput, p);
             double q = vec3d6.y;
             
             if (hasStatusEffect(StatusEffects.LEVITATION)) {
                 q += (0.05 * (getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - vec3d6.y) * 0.2;
             } else if (!player.hasNoGravity()) {
                 q -= d;
             }
             
             velocity = new Vec3d(vec3d6.x * f, q * 0.98, vec3d6.z * f);
        } else {
            BlockPos blockPos = getVelocityAffectingPos();
            float p = player.getEntityWorld().getBlockState(blockPos).getBlock().getSlipperiness();
            float f = onGround ? p * 0.91f : 0.91f;
            Vec3d vec3d6 = applyMovementInput(movementInput, p);
            double q = vec3d6.y;
             
             if (hasStatusEffect(StatusEffects.LEVITATION)) {
                 q += (0.05 * (getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - vec3d6.y) * 0.2;
             } else if (!player.hasNoGravity()) {
                 q -= d;
             }
             
             velocity = new Vec3d(vec3d6.x * f, q * 0.98, vec3d6.z * f);
        }
    }

    private Vec3d applyFluidMovingSpeed(double d, boolean bl, Vec3d velocity) {
        if (!player.hasNoGravity() && !bl) {
            if (this.onGround && Math.abs(velocity.y - 0.005) >= 0.003 && Math.abs(velocity.y - d / 16.0) < 0.003) {
                 return new Vec3d(velocity.x, -0.003, velocity.z);
            }
             return new Vec3d(velocity.x, velocity.y - d / 16.0, velocity.z);
        }
        return velocity;
    }

    private Vec3d applyMovementInput(Vec3d movementInput, float slipperiness) {
        updateVelocity(getMovementSpeed(slipperiness), movementInput);
        velocity = applyClimbingSpeed(velocity);
        move(velocity);
        
        Vec3d vec3d = velocity;
        if ((horizontalCollision || isJumping) && (isClimbing() || player.getEntityWorld().getBlockState(BlockPos.ofFloored(pos.x, pos.y, pos.z)).isOf(Blocks.POWDER_SNOW) && PowderSnowBlock.canWalkOnPowderSnow(player))) {
            vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
        }
        return vec3d;
    }

    private void updateVelocity(float speed, Vec3d movementInput) {
        Vec3d vec3d = movementInputToVelocity(movementInput, speed, yaw);
        velocity = velocity.add(vec3d);
    }
    
    private static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        double d = movementInput.lengthSquared();
        if (d < 1.0E-7) {
            return Vec3d.ZERO;
        }
        Vec3d vec3d = (d > 1.0 ? movementInput.normalize() : movementInput).multiply(speed);
        float f = MathHelper.sin(yaw * ((float)Math.PI / 180));
        float g = MathHelper.cos(yaw * ((float)Math.PI / 180));
        return new Vec3d(vec3d.x * (double)g - vec3d.z * (double)f, vec3d.y, vec3d.z * (double)g + vec3d.x * (double)f);
    }

    private float getMovementSpeed(float slipperiness) {
        return onGround ? getMovementSpeed() * (0.21600002f / (slipperiness * slipperiness * slipperiness)) : getAirStrafingSpeed();
    }
    
    private float getMovementSpeed() {
        return (float) player.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
    }

    private float getAirStrafingSpeed() {
        return sprinting ? 0.025999999f : 0.02f;
    }

    private void move(Vec3d movement) {
        Vec3d adjustedMovement = adjustMovementForCollisions(movement);
        if (adjustedMovement.lengthSquared() > 1.0E-7) {
            pos = pos.add(adjustedMovement);
            boundingBox = player.getDimensions(player.getPose()).getBoxAt(pos);
        }
        
        boolean xCollision = !MathHelper.approximatelyEquals(movement.x, adjustedMovement.x);
        boolean zCollision = !MathHelper.approximatelyEquals(movement.z, adjustedMovement.z);
        horizontalCollision = xCollision || zCollision;
        verticalCollision = movement.y != adjustedMovement.y;
        onGround = verticalCollision && movement.y < 0.0;
        
        if (onGround) {
            fallDistance = 0.0;
        } else if (movement.y < 0) {
            fallDistance -= movement.y;
        }
        
        if (horizontalCollision || verticalCollision) {
            velocity = new Vec3d(
                xCollision ? 0.0 : velocity.x,
                verticalCollision ? 0.0 : velocity.y,
                zCollision ? 0.0 : velocity.z
            );
        }
    }

    private Vec3d adjustMovementForCollisions(Vec3d movement) {
        return Entity.adjustMovementForCollisions(player, movement, boundingBox, player.getEntityWorld(), Collections.emptyList());
    }
    
    private void jump() {
        double jumpVelocity = 0.42 * getJumpVelocityMultiplier() + getJumpBoostVelocityModifier();
        velocity = velocity.add(0.0, jumpVelocity, 0.0);
        if (sprinting) {
            float f = yaw * ((float)Math.PI / 180);
            velocity = velocity.add(-MathHelper.sin(f) * 0.2f, 0.0, MathHelper.cos(f) * 0.2f);
        }
    }
    
    private float getJumpVelocityMultiplier() {
        return 1.0f;
    }
    
    private float getJumpBoostVelocityModifier() {
        return hasStatusEffect(StatusEffects.JUMP_BOOST) ? 0.1f * (getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) : 0.0f;
    }
    
    private boolean isClimbing() {
        return false;
    }

    private void checkWaterState() {
        touchingWater = player.getEntityWorld().getBlockState(BlockPos.ofFloored(pos.x, pos.y, pos.z)).getFluidState().isIn(FluidTags.WATER);
        if (touchingWater) {
             // Simple fluid push simulation
             // In real game this is complex (updateMovementInFluid)
        }
    }
    
    private void updateSubmergedInWaterState() {
        submergedInWater = false;
        submergedFluidTag.clear();
        
        double eyeY = pos.y + player.getStandingEyeHeight();
        BlockPos blockPos = BlockPos.ofFloored(pos.x, eyeY, pos.z);
        FluidState fluidState = player.getEntityWorld().getFluidState(blockPos);
        double fluidHeight = (double)blockPos.getY() + fluidState.getHeight(player.getEntityWorld(), blockPos);
        
        if (fluidHeight > eyeY) {
            submergedInWater = true;
            fluidState.streamTags().forEach(submergedFluidTag::add);
        }
    }
    
    private void updateSwimming() {
        if (isSwimming) {
            isSwimming = sprinting && touchingWater && !player.hasVehicle();
        } else {
            isSwimming = sprinting && submergedInWater && !player.hasVehicle() && isInWater();
        }
    }
    
    private boolean isInWater() {
         return player.getEntityWorld().getBlockState(BlockPos.ofFloored(pos.x, pos.y, pos.z)).getFluidState().isIn(FluidTags.WATER);
    }
    
    private boolean isInLava() {
        return player.getEntityWorld().getBlockState(BlockPos.ofFloored(pos.x, pos.y, pos.z)).getFluidState().isIn(FluidTags.LAVA);
    }
    
    private boolean isTouchingWater() {
        return touchingWater;
    }
    
    private boolean isSubmergedInWater() {
        return submergedInWater;
    }
    
    private double getFluidHeight(TagKey<Fluid> tag) {
        return fluidHeight.getDouble(tag);
    }
    
    private void swimUpward(TagKey<Fluid> fluid) {
        velocity = velocity.add(0.0, fluid == FluidTags.WATER ? 0.04 : 0.02, 0.0);
    }
    
    private double getSwimHeight() {
        return player.getStandingEyeHeight() < 0.4 ? 0.0 : 0.4;
    }
    
    private void onLanding() {
        fallDistance = 0.0;
    }
    
    private Vec3d applyClimbingSpeed(Vec3d motion) {
        if (!isClimbing()) return motion;
        return motion;
    }
    
    private boolean doesNotCollide(double x, double y, double z) {
        return player.getEntityWorld().isSpaceEmpty(player, boundingBox.offset(x, y, z));
    }
    
    private Vec3d getRotationVector() {
        return getRotationVector(pitch, yaw);
    }
    
    private Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * ((float)Math.PI / 180);
        float g = -yaw * ((float)Math.PI / 180);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }
    
    private boolean hasStatusEffect(RegistryEntry<StatusEffect> effect) {
        StatusEffectInstance instance = player.getStatusEffect(effect);
        return instance != null && instance.getDuration() >= simulatedTicks;
    }
    
    private StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect) {
        return player.getStatusEffect(effect);
    }
    
    private double getAttributeValue(RegistryEntry<EntityAttribute> attribute) {
        return player.getAttributeValue(attribute);
    }
    
    private BlockPos getVelocityAffectingPos() {
        return BlockPos.ofFloored(pos.x, boundingBox.minY - 0.5000001, pos.z);
    }

    public static class SimulatedPlayerInput extends Input {
        public DirectionalInput directionalInput;
        public boolean sprinting;
        public boolean ignoreClippingAtLedge = false;
        public boolean forceSafeWalk = false;
        
        // Add movement fields to shadow super class fields if they are missing or named differently
        public float movementForward = 0.0f;
        public float movementSideways = 0.0f;

        public SimulatedPlayerInput(DirectionalInput directionalInput, boolean jumping, boolean sprinting, boolean sneaking) {
             this.directionalInput = directionalInput;
             this.sprinting = sprinting;
             this.playerInput = new net.minecraft.util.PlayerInput(
                 directionalInput.isForwards(),
                 directionalInput.isBackwards(),
                 directionalInput.isLeft(),
                 directionalInput.isRight(),
                 jumping,
                 sneaking,
                 sprinting
             );
        }

        public void tick() {
             if (this.playerInput.forward() != this.playerInput.backward()) {
                this.movementForward = this.playerInput.forward() ? 1.0f : -1.0f;
            } else {
                this.movementForward = 0.0f;
            }

            if (this.playerInput.left() != this.playerInput.right()) {
                this.movementSideways = this.playerInput.left() ? 1.0f : -1.0f;
            } else {
                this.movementSideways = 0.0f;
            }
            
            if (this.playerInput.sneak()) {
                 this.movementSideways = (float)((double)this.movementSideways * 0.3);
                 this.movementForward = (float)((double)this.movementForward * 0.3);
            }
            
            // Sync with super class vector if needed, but we use these fields in SimulatedPlayer
            this.movementVector = new net.minecraft.util.math.Vec2f(this.movementSideways, this.movementForward);
        }
        
    public static SimulatedPlayerInput fromClientPlayer(DirectionalInput directionalInput) {
        MinecraftClient mc = MinecraftClient.getInstance();
        return new SimulatedPlayerInput(
            directionalInput,
            mc.player.input.playerInput.jump(),
            mc.player.input.playerInput.sprint(),
            mc.player.input.playerInput.sneak()
        );
    }
    }
}
