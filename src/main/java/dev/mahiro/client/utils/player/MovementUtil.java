package dev.mahiro.client.utils.player;

import dev.mahiro.client.events.input.MoveInputEvent;
import dev.mahiro.client.utils.math.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static dev.mahiro.client.Mahiro.mc;

public class MovementUtil {
    public static boolean isMoving() {
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }

    public static double getSpeed() {
        return Math.sqrt(mc.player.getVelocity().x * mc.player.getVelocity().x + mc.player.getVelocity().z * mc.player.getVelocity().z);
    }

    public static double getBaseSpeed(boolean slow, double customSpeed) {
        double baseSpeed = customSpeed;

        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            int amplifier = mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }

        if (slow && mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            int amplifier = mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
            baseSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }

        return baseSpeed;
    }

    public static double getBaseSpeed(boolean slow) {
        return getBaseSpeed(slow, 0.2873);
    }

    public static double getDistance2D() {
        if (mc.player == null) return 0;
        double dx = mc.player.getX() - mc.player.prevX;
        double dz = mc.player.getZ() - mc.player.prevZ;
        return Math.sqrt(dx * dx + dz * dz);
    }

    public static double getJumpBoost() {
        if (mc.player == null) return 0;
        if (mc.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            return (mc.player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1;
        }
        return 0;
    }

    public static Vec3d getMotionVec(Entity entity, float ticks, boolean collision) {
        if (mc.world == null) return Vec3d.ZERO;
        double dX = entity.getX() - entity.prevX;
        double dY = entity.getY() - entity.prevY;
        double dZ = entity.getZ() - entity.prevZ;
        double entityMotionPosX = 0;
        double entityMotionPosY = 0;
        double entityMotionPosZ = 0;
        if (collision) {
            for (double i = 1; i <= ticks; i = i + 0.5) {
                if (!mc.world.canCollide(entity, entity.getBoundingBox().offset(new Vec3d(dX * i, dY * i, dZ * i)))) {
                    entityMotionPosX = dX * i;
                    entityMotionPosY = dY * i;
                    entityMotionPosZ = dZ * i;
                } else {
                    break;
                }
            }
        } else {
            entityMotionPosX = dX * ticks;
            entityMotionPosY = dY * ticks;
            entityMotionPosZ = dZ * ticks;
        }
        return new Vec3d(entityMotionPosX, entityMotionPosY, entityMotionPosZ);
    }

    public static double getMotionY() {
        if (mc.player == null) return 0;
        return mc.player.getVelocity().y;
    }

    public static void setMotionY(double y) {
        if (mc.player == null) return;
        mc.player.setVelocity(mc.player.getVelocity().x, y, mc.player.getVelocity().z);
    }

    public static void setMotionX(double x) {
        if (mc.player == null) return;
        mc.player.setVelocity(x, mc.player.getVelocity().y, mc.player.getVelocity().z);
    }

    public static void setMotionZ(double z) {
        if (mc.player == null) return;
        mc.player.setVelocity(mc.player.getVelocity().x, mc.player.getVelocity().y, z);
    }

    public static void setMotion(double x, double y, double z) {
        if (mc.player == null) return;
        mc.player.setVelocity(x, y, z);
    }

    public static void strafe(double speed) {
        if (mc.player == null || !isMoving()) return;

        double yaw = getDirection();
        mc.player.setVelocity(-Math.sin(yaw) * speed, mc.player.getVelocity().y, Math.cos(yaw) * speed);
    }

    public static double getDirection() {
        if (mc.player == null) return 0;

        float yaw = mc.player.getYaw();
        float forward = mc.player.input.movementForward;
        float strafe = mc.player.input.movementSideways;

        if (forward < 0) {
            yaw += 180;
        }

        float modifier = 1;
        if (forward != 0) {
            modifier = forward < 0 ? -0.5f : 0.5f;
        }

        if (strafe > 0) {
            yaw -= 90 * modifier;
        }
        if (strafe < 0) {
            yaw += 90 * modifier;
        }

        return Math.toRadians(yaw);
    }

    public static double getDirection(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public static float getMoveForward() {
        if (mc.player == null) return 0;
        return mc.player.input.movementForward;
    }

    public static float getMoveStrafe() {
        if (mc.player == null) return 0;
        return mc.player.input.movementSideways;
    }

    public static double[] getMotion(double speed) {
        if (mc.player == null) return new double[]{0, 0};

        float forward = mc.player.input.movementForward;
        float strafe = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw();

        if (forward == 0 && strafe == 0) {
            return new double[]{0, 0};
        }

        if (forward != 0) {
            if (strafe > 0) {
                yaw -= (forward > 0 ? 45 : -45);
            } else if (strafe < 0) {
                yaw += (forward > 0 ? 45 : -45);
            }
            strafe = 0;
            forward = forward > 0 ? 1 : -1;
        }

        double sin = Math.sin(Math.toRadians(yaw));
        double cos = Math.cos(Math.toRadians(yaw));

        double x = forward * speed * -sin + strafe * speed * cos;
        double z = forward * speed * cos - strafe * speed * -sin;

        return new double[]{x, z};
    }

    public static void fixMovement(final MoveInputEvent event, final float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapDegrees(Math.toDegrees(getDirection(mc.player.getYaw(), forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapDegrees(Math.toDegrees(getDirection(yaw, predictedForward, predictedStrafe)));
                final double difference = MathUtil.wrappedDifference(angle, predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }

        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }

    public static float getTickDelta() {
        return mc.getRenderTickCounter().getTickDelta(true);
    }

    public static double[] directionSpeedKey(double speed) {
        if (mc.player == null) return new double[]{0, 0};

        float forward = (mc.options.forwardKey.isPressed() ? 1 : 0) + (mc.options.backKey.isPressed() ? -1 : 0);
        float side = (mc.options.leftKey.isPressed() ? 1 : 0) + (mc.options.rightKey.isPressed() ? -1 : 0);
        float yaw = mc.player.prevYaw + (mc.player.getYaw() - mc.player.prevYaw) * getTickDelta();

        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;

        return new double[]{posX, posZ};
    }

    public static double[] directionSpeed(double speed) {
        if (mc.player == null) return new double[]{0, 0};

        float forward = mc.player.input.movementForward;
        float side = mc.player.input.movementSideways;
        float yaw = mc.player.prevYaw + (mc.player.getYaw() - mc.player.prevYaw) * getTickDelta();

        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;

        return new double[]{posX, posZ};
    }
}