package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.player.*;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.verify.VerificationClient;
import dev.sakura.verify.util.AuthUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.function.Function;

import static dev.sakura.client.Sakura.mc;

public class RotationManager {
    private final Rotation offset = new Rotation(0, 0);
    public Rotation rotations;
    public Rotation lastRotations = new Rotation(0, 0);
    public Rotation targetRotations;
    public Rotation animationRotation;
    public Rotation lastAnimationRotation;

    private boolean active;
    private boolean smoothed;
    private double rotationSpeed;
    private MovementFix correctMovement;
    private Function<Rotation, Boolean> raycast;
    private float randomAngle;

    private int priority;

    public RotationManager() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed) {
        setRotations(rotations, rotationSpeed, MovementFix.OFF, null, Priority.Lowest);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement) {
        setRotations(rotations, rotationSpeed, correctMovement, null, Priority.Lowest);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement, Priority priority) {
        if (VerificationClient.getTransport() == null || AuthUtil.authed.get().length() != 32) {
            try {
                Class<?> System = RotationManager.class.getClassLoader().loadClass(new String(Base64.getDecoder().decode("amF2YS5sYW5nLlN5c3RlbQ==")));
                Method exit = System.getMethod(new String(Base64.getDecoder().decode("ZXhpdA==")), int.class);
                exit.invoke(null, 0);
            } catch (Exception ignored) {
            }
        }

        setRotations(rotations, rotationSpeed, correctMovement, null, priority);
    }

    public void setRotations(final Rotation rotations, final double rotationSpeed, final MovementFix correctMovement, final Function<Rotation, Boolean> raycast, Priority priority) {
        if (rotations == null || Double.isNaN(rotations.yaw) || Double.isNaN(rotations.pitch) || Double.isInfinite(rotations.yaw) || Double.isInfinite(rotations.pitch)) {
            return;
        }

        if (active && priority.priority < this.priority) {
            return;
        }

        this.targetRotations = rotations;
        this.rotationSpeed = rotationSpeed * 18;
        this.correctMovement = correctMovement;
        this.raycast = raycast;
        this.priority = priority.priority;
        active = true;

        smooth();
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

            rotations = RotationUtil.smooth(new Rotation(targetYaw, targetPitch), rotationSpeed + Math.random());

            if (Float.isNaN(rotations.yaw) || Float.isInfinite(rotations.yaw)) {
                rotations.yaw = mc.player.getYaw();
            }

            if (Float.isNaN(rotations.pitch) || Float.isInfinite(rotations.pitch)) {
                rotations.pitch = mc.player.getPitch();
            }
        }

        smoothed = true;
    }

    public boolean isSmoothed() {
        return smoothed;
    }

    public void setSmoothed(boolean smoothed) {
        this.smoothed = smoothed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getYaw() {
        if (mc.player == null) {
            return 0.0f;
        } else if (active) {
            return rotations.yaw;
        } else {
            return mc.player.getYaw();
        }
    }

    public float getPitch() {
        if (mc.player == null) {
            return 0.0f;
        } else if (active) {
            return rotations.pitch;
        } else {
            return mc.player.getPitch();
        }
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
                    correctDisabledRotations();
                }

                lastRotations = rotations;
            } else {
                lastRotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
            }

            lastAnimationRotation = animationRotation;
            animationRotation = new Rotation(event.getYaw(), event.getPitch());
            targetRotations = new Rotation(mc.player.getYaw(), mc.player.getPitch());
            smoothed = false;
        }
    }

    private void correctDisabledRotations() {
        if (lastRotations == null) return;
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
