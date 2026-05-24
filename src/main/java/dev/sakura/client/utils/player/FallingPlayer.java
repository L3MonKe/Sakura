package dev.sakura.client.utils.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static dev.sakura.client.Sakura.mc;

public class FallingPlayer {
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private final float strafe;
    private final float forward;
    private final float jumpMovementFactor;

    public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward, float jumpMovementFactor) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
        this.jumpMovementFactor = jumpMovementFactor;
    }

    public FallingPlayer(PlayerEntity player) {
        this(
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getVelocity().x,
                player.getVelocity().y,
                player.getVelocity().z,
                player.getYaw(),
                0.0F,
                0.0F,
                player.getMovementSpeed()
        );
    }

    private void calculateForTick() {
        float sr = strafe * 0.9800000190734863F;
        float fw = forward * 0.9800000190734863F;
        float movement = sr * sr + fw * fw;

        if (movement >= 1.0E-4F) {
            movement = MathHelper.sqrt(movement);
            if (movement < 1.0F) {
                movement = 1.0F;
            }

            float fixedJumpFactor = jumpMovementFactor;
            if (mc.player != null && mc.player.isSprinting()) {
                fixedJumpFactor *= 1.3F;
            }

            movement = fixedJumpFactor / movement;
            sr *= movement;
            fw *= movement;

            float sin = MathHelper.sin(yaw * ((float) Math.PI / 180.0F));
            float cos = MathHelper.cos(yaw * ((float) Math.PI / 180.0F));
            motionX += sr * cos - fw * sin;
            motionZ += fw * cos + sr * sin;
        }

        motionY -= 0.08D;
        motionY *= 0.9800000190734863D;
        x += motionX;
        y += motionY;
        z += motionZ;
        motionX *= 0.91D;
        motionZ *= 0.91D;
    }

    public void calculate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            calculateForTick();
        }
    }

    public BlockPos findCollision(int ticks) {
        for (int i = 0; i < ticks; i++) {
            Vec3d start = new Vec3d(x, y, z);
            calculateForTick();
            Vec3d end = new Vec3d(x, y, z);
            BlockPos raytracedBlock;
            float halfWidth = mc.player.getWidth() / 2.0F;

            if ((raytracedBlock = raytrace(start, end)) != null) return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(halfWidth, 0.0, halfWidth), end)) != null) return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(-halfWidth, 0.0, halfWidth), end)) != null) return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(halfWidth, 0.0, -halfWidth), end)) != null) return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(-halfWidth, 0.0, -halfWidth), end)) != null) return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(halfWidth, 0.0, halfWidth / 2.0F), end)) != null)
                return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(-halfWidth, 0.0, halfWidth / 2.0F), end)) != null)
                return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(halfWidth / 2.0F, 0.0, halfWidth), end)) != null)
                return raytracedBlock;
            if ((raytracedBlock = raytrace(start.add(halfWidth / 2.0F, 0.0, -halfWidth), end)) != null)
                return raytracedBlock;
        }

        return null;
    }

    private BlockPos raytrace(Vec3d start, Vec3d end) {
        BlockHitResult result = mc.world.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                mc.player
        ));

        if (result.getType() == HitResult.Type.BLOCK && result.getSide() == Direction.UP) {
            return result.getBlockPos();
        }

        return null;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}