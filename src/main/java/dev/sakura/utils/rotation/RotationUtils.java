package dev.sakura.utils.rotation;

import dev.sakura.manager.impl.RotationManager;
import dev.sakura.utils.math.MathUtils;
import dev.sakura.utils.vector.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import dev.sakura.mixin.accessor.IEntity;

public class RotationUtils {
    
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static Vector2f calculate(final Vector3d from, final Vector3d to) {
        final Vector3d diff = to.subtract(from);
        final double distance = Math.hypot(diff.getX(), diff.getZ());
        final float yaw = (float) (MathHelper.atan2(diff.getZ(), diff.getX()) * MathUtils.TO_DEGREES) - 90.0F;
        final float pitch = (float) (-(MathHelper.atan2(diff.getY(), distance) * MathUtils.TO_DEGREES));
        return new Vector2f(yaw, pitch);
    }

    public static Vector2f calculate(final Entity entity) {
        return calculate(new Vector3d(entity.getX(), entity.getY(), entity.getZ()).add(0, Math.max(0, Math.min(mc.player.getY() - entity.getY() +
                mc.player.getEyeHeight(mc.player.getPose()), (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * 0.9)), 0));
    }

    public static Vector2f calculate(final Entity entity, final boolean adaptive, final double range) {
        Vector2f normalRotations = calculate(entity);
        if (!adaptive || RaytraceUtils.facingEnemy(mc.player, entity, normalRotations, range, 0)) {
            return normalRotations;
        }

        for (double yPercent = 1; yPercent >= 0; yPercent -= 0.25 + Math.random() * 0.1) {
            for (double xPercent = 1; xPercent >= -0.5; xPercent -= 0.5) {
                for (double zPercent = 1; zPercent >= -0.5; zPercent -= 0.5) {
                    Vector2f adaptiveRotations = calculate(new Vector3d(entity.getX(), entity.getY(), entity.getZ()).add(
                            (entity.getBoundingBox().maxX - entity.getBoundingBox().minX) * xPercent,
                            (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * yPercent,
                            (entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) * zPercent));

                    if (RaytraceUtils.facingEnemy(mc.player, entity, adaptiveRotations, range, 0)) {
                        return adaptiveRotations;
                    }
                }
            }
        }

        return normalRotations;
    }

    public static Vector2f calculate(final Vec3d to, final Direction direction) {
        return calculate(new Vector3d(to.x, to.y, to.z), direction);
    }

    public static Vector2f calculate(final Vec3d to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), new Vector3d(to.x, to.y, to.z));
    }

    public static Vector2f calculate(final BlockPos to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), new Vector3d(to.getX(), to.getY(), to.getZ()).add(0.5, 0.5, 0.5));
    }

    public static Vector2f calculate(final Vector3d to) {
        return calculate(new Vector3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), to);
    }

    public static Vector2f calculate(final Vector3d position, final Direction direction) {
        double x = position.getX() + 0.5D;
        double y = position.getY() + 0.5D;
        double z = position.getZ() + 0.5D;

        x += (double) direction.getOffsetX() * 0.5D;
        y += (double) direction.getOffsetY() * 0.5D;
        z += (double) direction.getOffsetZ() * 0.5D;
        return calculate(new Vector3d(x, y, z));
    }

    public static Vector2f applySensitivityPatch(final Vector2f rotation) {
        final Vector2f previousRotation = new Vector2f(((IEntity) mc.player).getPrevYaw(), ((IEntity) mc.player).getPrevPitch());
        final float mouseSensitivity = (float) (mc.options.getMouseSensitivity().getValue() * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public static Vector2f applySensitivityPatch(final Vector2f rotation, final Vector2f previousRotation) {
        final float mouseSensitivity = (float) (mc.options.getMouseSensitivity().getValue() * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public static Vector2f relateToPlayerRotation(final Vector2f rotation) {
        final Vector2f previousRotation = new Vector2f(((IEntity) mc.player).getPrevYaw(), ((IEntity) mc.player).getPrevPitch());
        final float yaw = previousRotation.x + MathHelper.wrapDegrees(rotation.x - previousRotation.x);
        final float pitch = MathHelper.clamp(rotation.y, -90, 90);
        return new Vector2f(yaw, pitch);
    }

    public static Vector2f resetRotation(final Vector2f rotation) {
        if (rotation == null) {
            return null;
        }

        final float yaw = rotation.x + MathHelper.wrapDegrees(mc.player.getYaw() - rotation.x);
        final float pitch = mc.player.getPitch();
        return new Vector2f(yaw, pitch);
    }

    public static Vector2f move(final Vector2f targetRotation, final double speed) {
        return move(RotationManager.lastRotations, targetRotation, speed);
    }

    public static Vector2f move(final Vector2f lastRotation, final Vector2f targetRotation, double speed) {
        if (speed != 0) {

            double deltaYaw = MathHelper.wrapDegrees(targetRotation.x - lastRotation.x);
            final double deltaPitch = (targetRotation.y - lastRotation.y);

            final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
            final double distributionYaw = Math.abs(deltaYaw / distance);
            final double distributionPitch = Math.abs(deltaPitch / distance);

            final double maxYaw = speed * distributionYaw;
            final double maxPitch = speed * distributionPitch;

            final float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
            final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

            return new Vector2f(moveYaw, movePitch);
        }

        return new Vector2f(0, 0);
    }

    public static Vector2f smooth(final Vector2f targetRotation, final double speed) {
        return smooth(RotationManager.lastRotations, targetRotation, speed);
    }

    public static Vector2f smooth(final Vector2f lastRotation, final Vector2f targetRotation, final double speed) {
        float yaw = targetRotation.x;
        float pitch = targetRotation.y;
        final float lastYaw = lastRotation.x;
        final float lastPitch = lastRotation.y;

        if (speed != 0) {
            Vector2f move = move(targetRotation, speed);

            yaw = lastYaw + move.x;
            pitch = lastPitch + move.y;

            // Minecraft.getDebugFPS() -> mc.getCurrentFps() in some versions or mixin access
            // Using a safe fallback or standard method
            for (int i = 1; i <= (int) (mc.getCurrentFps() / 20f + Math.random() * 10); ++i) {

                if (Math.abs(move.x) + Math.abs(move.y) > 0.0001) {
                    yaw += (Math.random() - 0.5) / 1000;
                    pitch -= Math.random() / 200;
                }

                /*
                 * Fixing GCD
                 */
                final Vector2f rotations = new Vector2f(yaw, pitch);
                final Vector2f fixedRotations = RotationUtils.applySensitivityPatch(rotations);

                /*
                 * Setting rotations
                 */
                yaw = fixedRotations.x;
                pitch = Math.max(-90, Math.min(90, fixedRotations.y));
            }
        }

        return new Vector2f(yaw, pitch);
    }
}