package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.input.MoveInputEvent;
import dev.sakura.client.events.player.*;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.vector.Vector2f;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Function;

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

    private static float renderPitch;
    private static float renderYawOffset;
    private static float prevRenderPitch;
    private static float prevRenderYawOffset;
    private static float prevRotationYawHead;
    private static float rotationYawHead;
    private static int ticksExisted;

    private static int priority;

    public RotationManager() {
        Sakura.EVENT_BUS.subscribe(this);
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

    public void setRotations(final Vector2f rotations, final double rotationSpeed) {
        setRotations(rotations, rotationSpeed, MovementFix.OFF, null, Priority.Lowest);
    }

    public void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement) {
        setRotations(rotations, rotationSpeed, correctMovement, null, Priority.Lowest);
    }

    public void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement, Priority priority) {
        setRotations(rotations, rotationSpeed, correctMovement, null, priority);
    }

    /*
     * This method must be called on Pre Update Event to work correctly
     */
    public void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement, final Function<Vector2f, Boolean> raycast, Priority priority) {
        if (rotations == null || Double.isNaN(rotations.x) || Double.isNaN(rotations.y) || Double.isInfinite(rotations.x) || Double.isInfinite(rotations.y))
            return;
        if (active && priority.priority < RotationManager.priority) return;

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
        return MathHelper.angleBetween(yaw, rotations.x) + Math.abs(pitch - rotations.y) <= fov;
    }

    private void smooth() {
        if (!smoothed) {
            float targetYaw = targetRotations.x;
            float targetPitch = targetRotations.y;

            if (raycast != null && (Math.abs(targetYaw - rotations.x) > 5 || Math.abs(targetPitch - rotations.y) > 5)) {
                final Vector2f trueTargetRotations = new Vector2f(targetRotations.x, targetRotations.y);

                double speed = (Math.random() * Math.random() * Math.random()) * 20;
                randomAngle += (float) ((20 + (float) (Math.random() - 0.5) * (Math.random() * Math.random() * Math.random() * 360)) * (mc.player.age / 10 % 2 == 0 ? -1 : 1));

                if (Float.isNaN(randomAngle) || Float.isInfinite(randomAngle)) randomAngle = 0;

                offset.x = ((float) (offset.x + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                offset.y = ((float) (offset.y + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                if (Float.isNaN(offset.x) || Float.isInfinite(offset.x)) offset.x = 0;
                if (Float.isNaN(offset.y) || Float.isInfinite(offset.y)) offset.y = 0;

                targetYaw += offset.x;
                targetPitch += offset.y;

                if (!raycast.apply(new Vector2f(targetYaw, targetPitch))) {
                    randomAngle = (float) Math.toDegrees(Math.atan2(trueTargetRotations.x - targetYaw, targetPitch - trueTargetRotations.y)) - 180;
                    if (Float.isNaN(randomAngle)) randomAngle = 0;

                    targetYaw -= offset.x;
                    targetPitch -= offset.y;

                    offset.x = ((float) (offset.x + -MathHelper.sin((float) Math.toRadians(randomAngle)) * speed));
                    offset.y = ((float) (offset.y + MathHelper.cos((float) Math.toRadians(randomAngle)) * speed));

                    if (Float.isNaN(offset.x) || Float.isInfinite(offset.x)) offset.x = 0;
                    if (Float.isNaN(offset.y) || Float.isInfinite(offset.y)) offset.y = 0;

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

            rotations = RotationUtil.smooth(new Vector2f(targetYaw, targetPitch),
                    rotationSpeed + Math.random());

            if (Float.isNaN(rotations.x) || Float.isInfinite(rotations.x)) rotations.x = mc.player.getYaw();
            if (Float.isNaN(rotations.y) || Float.isInfinite(rotations.y)) rotations.y = mc.player.getPitch();
        }

        smoothed = true;
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
        if (active) return rotations.x;
        else return mc.player.getYaw();
    }

    public float getPitch() {
        if (active) return rotations.y;
        else return mc.player.getPitch();
    }

    public Vector2f getRotation() {
        if (active) return rotations;
        else return new Vector2f(mc.player.getYaw(), mc.player.getPitch());
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
        if (!active || rotations == null || lastRotations == null || targetRotations == null || lastServerRotations == null) {
            rotations = lastRotations = targetRotations = lastServerRotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        }

        if (active) {
            smooth();
        }

        if (correctMovement == MovementFix.BACKWARDS_SPRINT && active) {
            if (Math.abs(rotations.x % 360 - Math.toDegrees(MovementUtil.getDirection()) % 360) > 45) {
                mc.options.sprintKey.setPressed(false);
                mc.player.setSprinting(false);
            }
        }
    }

    @EventHandler
    private void onMoveInput(MoveInputEvent event) {
        if (active && correctMovement == MovementFix.NORMAL && rotations != null) {
            /*
             * Calculating movement fix
             */
            final float yaw = rotations.x;
            MovementUtil.fixMovement(event, yaw);
        }
    }

    @EventHandler
    private void onRaytrace(RayTraceEvent event) {
        if (active && rotations != null) {
            event.setYaw(rotations.x);
            event.setPitch(rotations.y);
        }
    }

    @EventHandler
    private void onStrafe(StrafeEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @EventHandler
    private void onJump(JumpRotationEvent event) {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL || correctMovement == MovementFix.BACKWARDS_SPRINT) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @EventHandler
    private void onMotion(MotionEvent event) {
        if (event.getType() == EventType.PRE) {
            if (active && rotations != null) {
                float yaw = rotations.x;
                float pitch = rotations.y;

                if (Float.isNaN(yaw) || Float.isInfinite(yaw)) yaw = mc.player.getYaw();
                if (Float.isNaN(pitch) || Float.isInfinite(pitch)) pitch = mc.player.getPitch();
                pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);

                event.setYaw(yaw);
                event.setPitch(pitch);

                lastServerRotations = new Vector2f(yaw, pitch);
                setRenderRotation(yaw, pitch);

                if (Math.abs((rotations.x - mc.player.getYaw()) % 360) < 1 && Math.abs((rotations.y - mc.player.getPitch())) < 1) {
                    active = false;
                    priority = 0;

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
        if (mc.player == null || lastRotations == null) return;
        final Vector2f rotations = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        final Vector2f fixedRotations = RotationUtil.resetRotation(RotationUtil.applySensitivityPatch(rotations, lastRotations));

        if (fixedRotations != null && !Float.isNaN(fixedRotations.x) && !Float.isNaN(fixedRotations.y)) {
            mc.player.setYaw(fixedRotations.x);
            mc.player.setPitch(MathHelper.clamp(fixedRotations.y, -90.0f, 90.0f));
        }
    }

    public void setRenderRotation(float yaw, float pitch) {
        if (mc.player == null) return;

        if (mc.player.age != ticksExisted) {
            ticksExisted = mc.player.age;
            prevRenderPitch = renderPitch;
            prevRenderYawOffset = renderYawOffset;
            prevRotationYawHead = rotationYawHead;
        }

        renderPitch = pitch;
        renderYawOffset = getRenderYawOffset(yaw, prevRenderYawOffset);
        rotationYawHead = yaw;
    }

    private float getRenderYawOffset(float yaw, float offsetIn) {
        float result = offsetIn;
        float offset;

        double xDif = mc.player.getX() - mc.player.prevX;
        double zDif = mc.player.getZ() - mc.player.prevZ;

        if (xDif * xDif + zDif * zDif > 0.0025000002f) {
            offset = (float) MathHelper.atan2(zDif, xDif) * 57.295776f - 90.0f;
            float wrap = MathHelper.abs(MathHelper.wrapDegrees(yaw) - offset);
            if (95.0F < wrap && wrap < 265.0F) {
                result = offset - 180.0F;
            } else {
                result = offset;
            }
        }

        if (mc.player.handSwingProgress > 0.0F) {
            result = yaw;
        }

        result = offsetIn + MathHelper.wrapDegrees(result - offsetIn) * 0.3f;
        offset = MathHelper.wrapDegrees(yaw - result);

        if (offset < -75.0f) {
            offset = -75.0f;
        } else if (offset >= 75.0f) {
            offset = 75.0f;
        }

        result = yaw - offset;
        if (offset * offset > 2500.0f) {
            result += offset * 0.2f;
        }

        return result;
    }

    public void lookAt(Vec3d target, double speed) {
        lookAt(target, speed, Priority.Lowest);
    }

    public void lookAt(Vec3d target, double speed, Priority priority) {
        Vector2f rotation = RotationUtil.calculate(target);
        setRotations(rotation, speed, MovementFix.OFF, priority);
    }

    public boolean isLookingAt(BlockPos pos, Direction side) {
        return RaytraceUtil.overBlock(getRotation(), side, pos, false);
    }

    public static float getRenderPitch() {
        return renderPitch;
    }

    public static float getRotationYawHead() {
        return rotationYawHead;
    }

    public static float getRenderYawOffset() {
        return renderYawOffset;
    }

    public static float getPrevRenderPitch() {
        return prevRenderPitch;
    }

    public static float getPrevRotationYawHead() {
        return prevRotationYawHead;
    }

    public static float getPrevRenderYawOffset() {
        return prevRenderYawOffset;
    }
}
