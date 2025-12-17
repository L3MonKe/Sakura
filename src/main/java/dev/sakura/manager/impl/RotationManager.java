package dev.sakura.manager.impl;

import dev.sakura.events.input.MoveInputEvent;
import dev.sakura.events.player.*;
import dev.sakura.events.type.EventType;
import dev.sakura.utils.player.MovementUtils;
import dev.sakura.utils.rotation.MovementFix;
import dev.sakura.utils.rotation.RotationUtils;
import dev.sakura.utils.vector.Vector2f;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/17 20:51
 * @Filename：RotationManager
 */
public class RotationManager {

    private static final Vector2f offset = new Vector2f(0, 0);
    public static Vector2f rotations, lastRotations = new Vector2f(0, 0), targetRotations, lastServerRotations;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean active;
    private static boolean smoothed;
    private static double rotationSpeed;
    private static MovementFix correctMovement;
    private static Function<Vector2f, Boolean> raycast;
    private static float randomAngle;

    /*
     * This method must be called on Pre Update Event to work correctly
     */
    public static void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement) {
        setRotations(rotations, rotationSpeed, correctMovement, null);
    }

    /*
     * This method must be called on Pre Update Event to work correctly
     */
    public static void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement, final Function<Vector2f, Boolean> raycast) {
        RotationManager.targetRotations = rotations;
        RotationManager.rotationSpeed = rotationSpeed * 36;
        RotationManager.correctMovement = correctMovement;
        RotationManager.raycast = raycast;
        active = true;

        smooth();
    }

    public static void smooth() {
        if (!smoothed) {
            float targetYaw = targetRotations.x;
            float targetPitch = targetRotations.y;

            // Randomisation
            if (raycast != null && (Math.abs(targetYaw - rotations.x) > 5 || Math.abs(targetPitch - rotations.y) > 5)) {
                final Vector2f trueTargetRotations = new Vector2f(targetRotations.x, targetRotations.y);

                double speed = /*Math.min(*/(Math.random() * Math.random() * Math.random()) * 20/*, MoveUtil.speed() * 30)*/;
                randomAngle += (float) ((20 + (float) (Math.random() - 0.5) * (Math.random() * Math.random() * Math.random() * 360)) * (mc.player.age / 10 % 2 == 0 ? -1 : 1));

                offset.x = ((float) (offset.x + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                offset.y = ((float) (offset.y + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                targetYaw += offset.x;
                targetPitch += offset.y;

                if (!raycast.apply(new Vector2f(targetYaw, targetPitch))) {
                    randomAngle = (float) Math.toDegrees(Math.atan2(trueTargetRotations.x - targetYaw, targetPitch - trueTargetRotations.y)) - 180;

                    targetYaw -= offset.x;
                    targetPitch -= offset.y;

                    offset.x = ((float) (offset.x + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                    offset.y = ((float) (offset.y + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                    targetYaw = targetYaw + offset.x;
                    targetPitch = targetPitch + offset.y;
                }

                if (!raycast.apply(new Vector2f(targetYaw, targetPitch))) {
                    offset.x = 0;
                    offset.y = 0;

                    targetYaw = (float) (targetRotations.x + Math.random() * 2);
                    targetPitch = (float) (targetRotations.y + Math.random() * 2);
                }
            }

            rotations = RotationUtils.smooth(new Vector2f(targetYaw, targetPitch),
                    rotationSpeed + Math.random());

        }

        smoothed = true;

        /*
         * Updating MouseOver
         */
//        mc.entityRenderer.getMouseOver(1);
    }

    public static boolean isSmoothed() {
        return smoothed;
    }

    public static void setSmoothed(boolean smoothed) {
        RotationManager.smoothed = smoothed;
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        RotationManager.active = active;
    }

    public static float getYaw() {
        if (active) return rotations.x;
        else return mc.player.getYaw();
    }

    public static float getPitch() {
        if (active) return rotations.y;
        else return mc.player.getPitch();
    }

    @EventHandler
    public void onPlayerTick(PlayerTickEvent event) {
        if (!active || rotations == null || lastRotations == null || targetRotations == null || lastServerRotations == null) {
            rotations = lastRotations = targetRotations = lastServerRotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        }

        if (active) {
            smooth();
        }

//        mc.player.getYaw() = rotations.x;
//        mc.player.getPitch() = rotations.y;

        if (correctMovement == MovementFix.BACKWARDS_SPRINT && active) {
            if (Math.abs(rotations.x % 360 - Math.toDegrees(MovementUtils.getDirection()) % 360) > 45) {
                mc.options.sprintKey.setPressed(false);
                mc.player.setSprinting(false);
            }
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (active && correctMovement == MovementFix.NORMAL && rotations != null) {
            /*
             * Calculating movement fix
             */
            final float yaw = rotations.x;
            MovementUtils.fixMovement(event, yaw);
        }
    }

    @EventHandler
    public void onRaytrace(RayTraceEvent event) {
        if (active && rotations != null) {
            event.setYaw(rotations.x);
            event.setPitch(rotations.y);
        }
    }

    @EventHandler
    public void onStrafe(StrafeEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @EventHandler
    public void onJump(JumpEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL || correctMovement == MovementFix.BACKWARDS_SPRINT) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @EventHandler
    public void onPreMotion(MotionEvent event) {
        if (event.getType() == EventType.PRE) {
            if (active && rotations != null) {
                final float yaw = rotations.x;
                final float pitch = rotations.y;

                event.setYaw(yaw);
                event.setPitch(pitch);

                lastServerRotations = new Vector2f(yaw, pitch);

                if (Math.abs((rotations.x - mc.player.getYaw()) % 360) < 1 && Math.abs((rotations.y - mc.player.getPitch())) < 1) {
                    active = false;

                    this.correctDisabledRotations();
                }

                lastRotations = rotations;
            } else {
                lastRotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
            }

            targetRotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
            smoothed = false;
        }
    }

//    @EventHandler
//    public void onAnimation(RotationAnimationEvent e) {
//        if (rotations != null && rotations != null) {
//            e.setYaw(rotations.x);
//            e.setLastYaw(rotations.x);
//            e.setPitch(rotations.y);
//            e.setLastPitch(rotations.y);
//        }
//    }

    private void correctDisabledRotations() {
        final Vector2f rotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        final Vector2f fixedRotations = RotationUtils.resetRotation(RotationUtils.applySensitivityPatch(rotations, lastRotations));

        mc.player.setYaw(fixedRotations.x);
        mc.player.setPitch(fixedRotations.y);
    }
}
