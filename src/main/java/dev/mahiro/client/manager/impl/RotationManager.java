package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.events.input.MoveInputEvent;
import dev.mahiro.client.events.player.*;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.rotation.Rotation;
import dev.mahiro.client.utils.rotation.RotationUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Function;

public class RotationManager {
    private static final Rotation offset = new Rotation(0, 0);
    public static Rotation rotations, lastRotations = new Rotation(0, 0), targetRotations, animationRotation, lastAnimationRotation;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean active;
    private static boolean smoothed;
    private static double rotationSpeed;
    private static MovementFix correctMovement;
    private static Function<Rotation, Boolean> raycast;
    private static float randomAngle;

    private static int priority;

    public RotationManager() {
        Mahiro.EVENT_BUS.subscribe(this);
    }

    // 婆罗门这一块
    public enum Priority {
        Lowest(0),
        Low(10),
        Medium(50),
        High(100),
        Highest(1000);

        public final int priority;

        Priority(int priority) {
            this.priority = priority;
        }
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed) {
        setRotations(rotations, rotationSpeed, MovementFix.OFF, null, Priority.Lowest);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement) {
        setRotations(rotations, rotationSpeed, correctMovement, null, Priority.Lowest);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement, Priority priority) {
        setRotations(rotations, rotationSpeed, correctMovement, null, priority);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement, final Function<Rotation, Boolean> raycast, Priority priority) {
        if (!AuthGate.isVerified()) return;

        if (rotations == null || Double.isNaN(rotations.yaw) || Double.isNaN(rotations.pitch) || Double.isInfinite(rotations.yaw) || Double.isInfinite(rotations.pitch)) {
            return;
        }

        if (active && priority.priority < RotationManager.priority) {
            return;
        }

        RotationManager.targetRotations = rotations;
        RotationManager.rotationSpeed = rotationSpeed * 18;
        RotationManager.correctMovement = correctMovement;
        RotationManager.raycast = raycast;
        RotationManager.priority = priority.priority;
        active = true;

        smooth();
    }

    public boolean inFov(Vec3d directionVec, double fov) {
        float[] angle = getRotation(new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ()), directionVec);
        return inFov(angle[0], angle[1], fov);
    }

    public boolean inFov(float yaw, float pitch, double fov) {
        return MathHelper.angleBetween(yaw, rotations.yaw) + Math.abs(pitch - rotations.pitch) <= fov;
    }

    private void smooth() {
        if (!smoothed) {
            float targetYaw = targetRotations.yaw;
            float targetPitch = targetRotations.pitch;

            if (raycast != null && (Math.abs(targetYaw - rotations.yaw) > 5 || Math.abs(targetPitch - rotations.pitch) > 5)) {
                final Rotation trueTargetRotations = new Rotation(targetRotations.yaw, targetRotations.pitch);

                double speed = (Math.random() * Math.random() * Math.random()) * 20;
                randomAngle += (float) ((20 + (float) (Math.random() - 0.5) * (Math.random() * Math.random() * Math.random() * 360)) * (mc.player.age / 10 % 2 == 0 ? -1 : 1));

                if (Float.isNaN(randomAngle) || Float.isInfinite(randomAngle)) randomAngle = 0;

                offset.yaw = ((float) (offset.yaw + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                offset.pitch = ((float) (offset.pitch + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                if (Float.isNaN(offset.yaw) || Float.isInfinite(offset.yaw)) offset.yaw = 0;
                if (Float.isNaN(offset.pitch) || Float.isInfinite(offset.pitch)) offset.pitch = 0;

                targetYaw += offset.yaw;
                targetPitch += offset.pitch;

                if (!raycast.apply(new Rotation(targetYaw, targetPitch))) {
                    randomAngle = (float) Math.toDegrees(Math.atan2(trueTargetRotations.yaw - targetYaw, targetPitch - trueTargetRotations.pitch)) - 180;
                    if (Float.isNaN(randomAngle)) randomAngle = 0;

                    targetYaw -= offset.yaw;
                    targetPitch -= offset.pitch;

                    offset.yaw = ((float) (offset.yaw + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                    offset.pitch = ((float) (offset.pitch + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                    if (Float.isNaN(offset.yaw) || Float.isInfinite(offset.yaw)) offset.yaw = 0;
                    if (Float.isNaN(offset.pitch) || Float.isInfinite(offset.pitch)) offset.pitch = 0;

                    targetYaw = targetYaw + offset.yaw;
                    targetPitch = targetPitch + offset.pitch;
                }

                if (!raycast.apply(new Rotation(targetYaw, targetPitch))) {
                    offset.yaw = 0;
                    offset.pitch = 0;

                    targetYaw = (float) (targetRotations.yaw + Math.random() * 2);
                    targetPitch = (float) (targetRotations.pitch + Math.random() * 2);
                }
            }

            rotations = RotationUtil.smooth(new Rotation(targetYaw, targetPitch),
                    rotationSpeed + Math.random());

            if (Float.isNaN(rotations.yaw) || Float.isInfinite(rotations.yaw)) rotations.yaw = mc.player.getYaw();
            if (Float.isNaN(rotations.pitch) || Float.isInfinite(rotations.pitch))
                rotations.pitch = mc.player.getPitch();
        }

        smoothed = true;

        mc.gameRenderer.updateCrosshairTarget(1.0f);
    }

    public boolean isSmoothed() {
        return smoothed;
    }

    public void setSmoothed(boolean smoothed) {
        RotationManager.smoothed = smoothed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        RotationManager.active = active;
    }

    public float getYaw() {
        if (active) return rotations.yaw;
        else return mc.player.getYaw();
    }

    public float getPitch() {
        if (active) return rotations.pitch;
        else return mc.player.getPitch();
    }

    public Rotation getRotation() {
        if (active) return rotations;
        else return new Rotation(mc.player.getYaw(), mc.player.getPitch());
    }

    public float[] getRotation(Vec3d vec) {
        return getRotation(mc.player.getEyePos(), vec);
    }

    public float[] getRotation(Vec3d eyesPos, Vec3d vec) {
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

    @EventHandler
    private void onPlayerTick(PlayerTickEvent event) {
        if (!active || rotations == null || lastRotations == null || targetRotations == null) {
            rotations = lastRotations = targetRotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        }

        if (active) {
            smooth();
        }

        if (correctMovement == MovementFix.BACKWARDS_SPRINT && active) {
            if (Math.abs(rotations.yaw % 360 - Math.toDegrees(MoveUtil.getDirection()) % 360) > 45) {
                mc.options.sprintKey.setPressed(false);
                mc.player.setSprinting(false);
            }
        }
    }

    @EventHandler
    private void onMoveInput(MoveInputEvent event) {
        if (active && correctMovement == MovementFix.NORMAL && rotations != null) {
            final float yaw = rotations.yaw;
            MoveUtil.fixMovement(event, yaw);
        }
    }

    @EventHandler
    private void onRaytrace(RayTraceEvent event) {
        if (active && rotations != null) {
            event.setYaw(rotations.yaw);
            event.setPitch(rotations.pitch);
        }
    }

    @EventHandler
    private void onStrafe(StrafeEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL) && rotations != null) {
            event.setYaw(rotations.yaw);
        }
    }

    @EventHandler
    private void onJump(JumpRotationEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL || correctMovement == MovementFix.BACKWARDS_SPRINT) && rotations != null) {
            event.setYaw(rotations.yaw);
        }
    }

    @EventHandler
    private void onMotion(MotionEvent event) {
        if (event.getType() == EventType.PRE) {
            if (active && rotations != null) {
                float yaw = rotations.yaw;
                float pitch = rotations.pitch;

                if (Float.isNaN(yaw) || Float.isInfinite(yaw)) yaw = mc.player.getYaw();
                if (Float.isNaN(pitch) || Float.isInfinite(pitch)) pitch = mc.player.getPitch();
                pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);

                event.setYaw(yaw);
                event.setPitch(pitch);

                if (Math.abs((rotations.yaw - mc.player.getYaw()) % 360) < 1 && Math.abs((rotations.pitch - mc.player.getPitch())) < 1) {
                    active = false;
                    priority = 0;

                    this.correctDisabledRotations();
                }

                lastRotations = rotations;
            } else {
                lastRotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
            }

            float eventYaw = event.getYaw();
            float eventPitch = event.getPitch();
            Rotation targetAnimation = new Rotation(eventYaw, eventPitch);
            if (!active) {
                lastAnimationRotation = targetAnimation;
                animationRotation = targetAnimation;
            } else {
                if (animationRotation == null) {
                    lastAnimationRotation = targetAnimation;
                    animationRotation = targetAnimation;
                } else {
                    lastAnimationRotation = animationRotation;
                    float renderYaw = animationRotation.yaw + (eventYaw - animationRotation.yaw) * 0.5f;
                    float renderPitch = animationRotation.pitch + (eventPitch - animationRotation.pitch) * 0.5f;
                    animationRotation = new Rotation(renderYaw, renderPitch);
                }
            }
            targetRotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
            smoothed = false;
        }
    }

    private void correctDisabledRotations() {
        if (mc.player == null || lastRotations == null) return;
        final Rotation rotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        final Rotation fixedRotations = RotationUtil.resetRotation(RotationUtil.applySensitivityPatch(rotations, lastRotations));

        if (!Float.isNaN(fixedRotations.yaw) && !Float.isNaN(fixedRotations.pitch)) {
            mc.player.setYaw(fixedRotations.yaw);
            mc.player.setPitch(MathHelper.clamp(fixedRotations.pitch, -90.0f, 90.0f));
        }
    }

    public void lookAt(Vec3d target, double speed) {
        lookAt(target, speed, Priority.Lowest);
    }

    public void lookAt(Vec3d target, double speed, Priority priority) {
        Rotation rotation = RotationUtil.calculate(target);
        setRotations(rotation, speed, MovementFix.OFF, priority);
    }
}
