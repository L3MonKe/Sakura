package dev.mzc.client.utils.rotation;

import dev.mzc.client.manager.impl.RotationManager;
import dev.mzc.client.mixin.accessor.IEntity;
import dev.mzc.client.utils.math.MathUtil;
import dev.mzc.client.utils.vector.Rotation;
import dev.mzc.client.utils.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static dev.mzc.client.Sakura.mc;

public class RotationUtil {
    public static Rotation calculate(final Vector3d from, final Vector3d to) {
        final Vector3d diff = to.subtract(from);
        final double distance = Math.hypot(diff.getX(), diff.getZ());
        final float yaw = (float) (MathHelper.atan2(diff.getZ(), diff.getX()) * MathUtil.TO_DEGREES) - 90.0F;
        final float pitch = (float) (-(MathHelper.atan2(diff.getY(), distance) * MathUtil.TO_DEGREES));
        return new Rotation(yaw, pitch);
    }

    public static Rotation calculate(final Entity entity) {
        return calculate(new Vector3d(entity.getX(), entity.getY(), entity.getZ()).add(0, Math.max(0, Math.min(mc.player.getY() - entity.getY() +
                mc.player.getEyeHeight(mc.player.getPose()), (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * 0.9)), 0));
    }

    public static Rotation calculate(final Entity entity, final boolean adaptive, final double range) {
        Rotation normalRotations = calculate(entity);
        if (!adaptive || RaytraceUtil.facingEnemy(mc.player, entity, normalRotations, range, 0)) {
            return normalRotations;
        }

        for (double yPercent = 1; yPercent >= 0; yPercent -= 0.25 + Math.random() * 0.1) {
            for (double xPercent = 1; xPercent >= -0.5; xPercent -= 0.5) {
                for (double zPercent = 1; zPercent >= -0.5; zPercent -= 0.5) {
                    Rotation adaptiveRotations = calculate(new Vector3d(entity.getX(), entity.getY(), entity.getZ()).add(
                            (entity.getBoundingBox().maxX - entity.getBoundingBox().minX) * xPercent,
                            (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * yPercent,
                            (entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) * zPercent));

                    if (RaytraceUtil.facingEnemy(mc.player, entity, adaptiveRotations, range, 0)) {
                        return adaptiveRotations;
                    }
                }
            }
        }

        return normalRotations;
    }

    public static Rotation calculate(final Vec3d to, final Direction direction) {
        return calculate(new Vector3d(to.x, to.y, to.z), direction);
    }

    public static Rotation calculate(final Vec3d to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), new Vector3d(to.x, to.y, to.z));
    }

    public static Rotation calculate(final BlockPos to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), new Vector3d(to.getX(), to.getY(), to.getZ()).add(0.5, 0.5, 0.5));
    }

    public static Rotation calculate(final Vector3d to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), to);
    }

    public static Rotation calculate(final Vector3d position, final Direction direction) {
        double x = position.getX() + 0.5D;
        double y = position.getY() + 0.5D;
        double z = position.getZ() + 0.5D;

        x += (double) direction.getOffsetX() * 0.5D;
        y += (double) direction.getOffsetY() * 0.5D;
        z += (double) direction.getOffsetZ() * 0.5D;
        return calculate(new Vector3d(x, y, z));
    }

    public static Rotation applySensitivityPatch(final Rotation rotation) {
        final Rotation previousRotation = new Rotation(((IEntity) mc.player).getPrevYaw(), ((IEntity) mc.player).getPrevPitch());
        final float mouseSensitivity = (float) (mc.options.getMouseSensitivity().getValue() * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.yaw + (float) (Math.round((rotation.yaw - previousRotation.yaw) / multiplier) * multiplier);
        final float pitch = previousRotation.pitch + (float) (Math.round((rotation.pitch - previousRotation.pitch) / multiplier) * multiplier);
        return new Rotation(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public static Rotation applySensitivityPatch(final Rotation rotation, final Rotation previousRotation) {
        final float mouseSensitivity = (float) (mc.options.getMouseSensitivity().getValue() * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.yaw + (float) (Math.round((rotation.yaw - previousRotation.yaw) / multiplier) * multiplier);
        final float pitch = previousRotation.pitch + (float) (Math.round((rotation.pitch - previousRotation.pitch) / multiplier) * multiplier);
        return new Rotation(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public static Rotation relateToPlayerRotation(final Rotation rotation) {
        final Rotation previousRotation = new Rotation(((IEntity) mc.player).getPrevYaw(), ((IEntity) mc.player).getPrevPitch());
        final float yaw = previousRotation.yaw + MathHelper.wrapDegrees(rotation.yaw - previousRotation.yaw);
        final float pitch = MathHelper.clamp(rotation.pitch, -90, 90);
        return new Rotation(yaw, pitch);
    }

    public static Rotation resetRotation(final Rotation rotation) {
        if (rotation == null) {
            return null;
        }

        final float yaw = rotation.yaw + MathHelper.wrapDegrees(mc.player.getYaw() - rotation.yaw);
        final float pitch = mc.player.getPitch();
        return new Rotation(yaw, pitch);
    }

    public static Rotation move(final Rotation lastRotation, final Rotation targetRotation, double speed) {
        if (speed != 0) {

            double deltaYaw = MathHelper.wrapDegrees(targetRotation.yaw - lastRotation.yaw);
            final double deltaPitch = (targetRotation.pitch - lastRotation.pitch);

            final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
            if (distance < 1.0E-6) {
                return new Rotation(0, 0);
            }
            final double distributionYaw = Math.abs(deltaYaw / distance);
            final double distributionPitch = Math.abs(deltaPitch / distance);

            final double maxYaw = speed * distributionYaw;
            final double maxPitch = speed * distributionPitch;

            final float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
            final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

            return new Rotation(moveYaw, movePitch);
        }

        return new Rotation(0, 0);
    }

    public static Rotation smooth(final Rotation targetRotation, final double speed) {
        return smooth(RotationManager.lastRotations, targetRotation, speed);
    }

    public static Rotation smooth(final Rotation lastRotation, final Rotation targetRotation, final double speed) {
        float yaw = targetRotation.yaw;
        float pitch = targetRotation.pitch;
        final float lastYaw = lastRotation.yaw;
        final float lastPitch = lastRotation.pitch;

        if (speed != 0) {
            Rotation move = move(lastRotation, targetRotation, speed);

            yaw = lastYaw + move.yaw;
            pitch = lastPitch + move.pitch;

            float motion = Math.abs(move.yaw) + Math.abs(move.pitch);
            int iterations;
            if (motion < 0.02f) iterations = 1;
            else if (motion < 0.2f) iterations = 2;
            else if (motion < 2.0f) iterations = 3;
            else iterations = 4;

            for (int i = 0; i < iterations; i++) {
                if (motion > 0.0001f) {
                    yaw += (float) MathUtil.getRandom(-0.0006, 0.0006);
                    pitch += (float) MathUtil.getRandom(-0.0035, 0.0035);
                }

                final Rotation rotations = new Rotation(yaw, pitch);
                final Rotation fixedRotations = applySensitivityPatch(rotations);

                yaw = shortestYaw(lastYaw, fixedRotations.yaw);
                pitch = Math.max(-90, Math.min(90, fixedRotations.pitch));
            }
        }

        return new Rotation(yaw, pitch);
    }

    private static float shortestYaw(float from, float to) {
        return from + MathHelper.wrapDegrees(to - from);
    }
}
