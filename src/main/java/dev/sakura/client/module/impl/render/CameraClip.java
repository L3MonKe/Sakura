package dev.sakura.client.module.impl.render;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.animations.AnimationUtil;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class CameraClip extends Module {
    public CameraClip() {
        super("CameraClip", "动态相机", Category.Render);
    }

    private enum Mode {
        NORMAL,
        ACTION
    }

    public final BoolValue disableFirstPers = new BoolValue("NoFirst", "禁止第一人称", true);
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.ACTION);
    private final Value<Double> distance = new NumberValue<>("Distance", "距离", 3.5, 1.0, 20.0, 0.5, () -> mode.is(Mode.NORMAL));
    private final Value<Double> speed = new NumberValue<>("Speed", "速度", 10.0, 1.0, 50.0, 0.5, () -> mode.is(Mode.NORMAL));
    private final Value<Double> actionDistance = new NumberValue<>("ActionDistance", "动作距离", 4.0, 0.5, 20.0, 0.5, () -> mode.is(Mode.ACTION));
    private final Value<Double> smoothness = new NumberValue<>("Smoothness", "平滑度", 0.3, 0.1, 0.95, 0.01, () -> mode.is(Mode.ACTION));
    private final Value<Double> maxDistance = new NumberValue<>("MaxDistance", "最大距离", 20.0, 5.0, 50.0, 0.5, () -> mode.is(Mode.ACTION));
    private final Value<Double> rotationSmoothness = new NumberValue<>("RotationSmoothness", "旋转平滑", 0.15, 0.01, 0.5, 0.01, () -> mode.is(Mode.ACTION));
    private final Value<Double> rotationOffset = new NumberValue<>("RotationOffset", "旋转偏移", 2.0, 0.0, 10.0, 0.1, () -> mode.is(Mode.ACTION));

    private Vec3d cameraPos;
    private int key = GLFW.GLFW_KEY_F6;
    private float animation;
    private Perspective lastPerspective = null;
    private float smoothYaw = 0f;
    private float smoothPitch = 0f;

    @Override
    public void onDisable() {
        cameraPos = null;
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        Perspective currentPerspective = mc.options.getPerspective();

        if (lastPerspective != null && lastPerspective != currentPerspective) {
            if (currentPerspective == Perspective.FIRST_PERSON) {
                animation = 0f;
                cameraPos = null;
                smoothYaw = 0f;
                smoothPitch = 0f;
            } else {
                animation = 0f;
            }
        }

        lastPerspective = currentPerspective;

        if (mode.is(Mode.NORMAL)) {
            float normalSpeed = speed.get().floatValue();
            if (currentPerspective == Perspective.FIRST_PERSON)
                animation = AnimationUtil.fast(animation, 0f, normalSpeed);
            else animation = AnimationUtil.fast(animation, 1f, normalSpeed);
        } else if (mode.is(Mode.ACTION)) {
            if (currentPerspective == Perspective.FIRST_PERSON) animation = AnimationUtil.fast(animation, 0f, 10);
            else animation = AnimationUtil.fast(animation, 1f, 10);
        }
    }

    public float getDistance() {
        return 1f + ((distance.get().floatValue() - 1f) * animation);
    }

    public float getActionDistance() {
        return 1f + ((actionDistance.get().floatValue() - 1f) * animation);
    }

    public boolean isNormal() {
        return isEnabled() && mode.is(Mode.NORMAL);
    }

    public boolean isAction() {
        return isEnabled() && mode.is(Mode.ACTION);
    }

    private boolean isFirstPerson() {
        return mc.options.getPerspective() == Perspective.FIRST_PERSON;
    }

    public Vec3d getCameraPos() {
        if (isFirstPerson()) {
            return new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        }
        return cameraPos;
    }

    public void update(Vec3d playerPos, float tickProgress, Entity focusedEntity) {
        if (isFirstPerson()) {
            cameraPos = null;
            return;
        }

        if (cameraPos == null) {
            cameraPos = focusedEntity.getClientCameraPosVec(tickProgress);
            smoothYaw = focusedEntity.getYaw();
            smoothPitch = focusedEntity.getPitch();
            return;
        }

        float currentYaw = focusedEntity.getYaw();
        float currentPitch = focusedEntity.getPitch();

        float rotSmooth = rotationSmoothness.get().floatValue();
        smoothYaw += MathHelper.wrapDegrees(currentYaw - smoothYaw) * rotSmooth;
        smoothPitch += (currentPitch - smoothPitch) * rotSmooth;

        float yawDelta = MathHelper.wrapDegrees(currentYaw - smoothYaw);
        float pitchDelta = currentPitch - smoothPitch;

        float offsetMultiplier = rotationOffset.get().floatValue();
        double yawRad = Math.toRadians(smoothYaw);

        // 使用玩家的右向量计算水平偏移，这样左转时向右偏移，右转时向左偏移
        double rightX = -Math.cos(yawRad);
        double rightZ = -Math.sin(yawRad);

        double rotOffsetX = rightX * yawDelta * offsetMultiplier * 0.02;
        double rotOffsetY = pitchDelta * offsetMultiplier * 0.02;
        double rotOffsetZ = rightZ * yawDelta * offsetMultiplier * 0.02;

        double distance = cameraPos.distanceTo(playerPos);
        float maxDist = maxDistance.get().floatValue();
        double eyeHeight = focusedEntity.getStandingEyeHeight();

        if (distance > maxDist) {
            cameraPos = new Vec3d(playerPos.x, playerPos.y + eyeHeight, playerPos.z);
            return;
        }

        float smoothFactor = smoothness.get().floatValue();
        double dynamicFactor = smoothFactor * (1.0 - Math.exp(-distance / maxDist));

        double dx = playerPos.x - cameraPos.x + rotOffsetX;
        double dy = playerPos.y + eyeHeight - cameraPos.y + rotOffsetY;
        double dz = playerPos.z - cameraPos.z + rotOffsetZ;

        cameraPos = new Vec3d(
                cameraPos.x + dx * dynamicFactor,
                cameraPos.y + dy * dynamicFactor,
                cameraPos.z + dz * dynamicFactor
        );
    }

    public boolean shouldModifyCamera() {
        return isEnabled() && mode.is(Mode.ACTION) && !isFirstPerson();
    }
}
