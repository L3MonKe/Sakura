package dev.sakura.client.utils.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import static dev.sakura.client.Sakura.mc;

public class FallingPlayer {
    public double x;
    public double y;
    public double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private final float strafe;
    private final float forward;
    private float jumpMovementFactor;

    public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
    }

    public FallingPlayer(PlayerEntity player) {
        this(player.getX(), player.getY(), player.getZ(), player.getVelocity().x, player.getVelocity().y, player.getVelocity().z, player.getHeadYaw(), player.sidewaysSpeed, player.forwardSpeed);
        float f = player.getEntityWorld().getBlockState(player.getBlockPos()).getBlock().getJumpVelocityMultiplier();
        float f1 = player.getEntityWorld().getBlockState(player.getLandingPos()).getBlock().getJumpVelocityMultiplier();
        float jumpingVelocity = 0.42F * ((double) f == 1.0 ? f1 : f) + player.getJumpBoostVelocityModifier();
        this.jumpMovementFactor = jumpingVelocity;
    }

    private void calculateForTick2() {
        float sr = this.strafe;
        float fw = this.forward;
        float v = sr * sr + fw * fw;
        if (v >= 1.0E-4F) {
            v = MathHelper.sqrt(v);
            if (v < 1.0F) {
                v = 1.0F;
            }

            float fixedJumpFactor = this.jumpMovementFactor;
            if (mc.player.isSprinting()) {
                fixedJumpFactor *= 1.3F;
            }

            v = fixedJumpFactor / v;
            sr *= v;
            fw *= v;
            float f1 = MathHelper.sin(this.yaw * (float) Math.PI / 180.0F);
            float f2 = MathHelper.cos(this.yaw * (float) Math.PI / 180.0F);
            this.motionX += sr * f2 - fw * f1;
            this.motionZ += fw * f2 + sr * f1;
        }

        this.motionY -= 0.08;
        this.motionY *= 0.98F;
        this.x = this.x + this.motionX;
        this.y = this.y + this.motionY;
        this.z = this.z + this.motionZ;
    }

    private void calculateForTick() {
        float sr = this.strafe * 0.98F;
        float fw = this.forward * 0.98F;
        float v = sr * sr + fw * fw;
        if (v >= 1.0E-4F) {
            v = MathHelper.sqrt(v);
            if (v < 1.0F) {
                v = 1.0F;
            }

            float fixedJumpFactor = this.jumpMovementFactor;
            if (mc.player.isSprinting()) {
                fixedJumpFactor *= 1.3F;
            }

            v = fixedJumpFactor / v;
            sr *= v;
            fw *= v;
            float f1 = MathHelper.sin(this.yaw * (float) Math.PI / 180.0F);
            float f2 = MathHelper.cos(this.yaw * (float) Math.PI / 180.0F);
            this.motionX += sr * f2 - fw * f1;
            this.motionZ += fw * f2 + sr * f1;
        }

        this.motionY -= 0.08;
        this.motionY *= 0.98F;
        this.x = this.x + this.motionX;
        this.y = this.y + this.motionY;
        this.z = this.z + this.motionZ;
        this.motionX *= 0.91;
        this.motionZ *= 0.91;
    }

    public void calculateMLG(int ticks) {
        for (int i = 0; i < ticks; i++) {
            this.calculateForTick2();
        }
    }

    public void calculate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            this.calculateForTick();
        }
    }
}