package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import net.minecraft.util.math.MathHelper;

public class ViewLock extends Module {
    public float lockYaw;
    public float lockPitch;

    public float cameraYaw;
    public float cameraPitch;
    public float lastCameraYaw;
    public float lastCameraPitch;

    public ViewLock() {
        super("ViewLock", "视角锁定", Category.Player);
    }

    @Override
    protected void onEnable() {
        if (nullCheck()) return;

        lockYaw = mc.player.getYaw();
        lockPitch = mc.player.getPitch();

        cameraYaw = lastCameraYaw = lockYaw;
        cameraPitch = lastCameraPitch = lockPitch;
    }

    public void handleLookDelta(double cursorDeltaX, double cursorDeltaY) {
        lastCameraYaw = cameraYaw;
        lastCameraPitch = cameraPitch;

        float pitchDelta = (float) cursorDeltaY * 0.15f;
        float yawDelta = (float) cursorDeltaX * 0.15f;

        cameraPitch = MathHelper.clamp(cameraPitch + pitchDelta, -90.0f, 90.0f);
        cameraYaw = MathHelper.wrapDegrees(cameraYaw + yawDelta);
    }

    public float getRenderYaw(float tickProgress) {
        return MathHelper.lerpAngleDegrees(tickProgress, lastCameraYaw, cameraYaw);
    }

    public float getRenderPitch(float tickProgress) {
        return MathHelper.lerp(tickProgress, lastCameraPitch, cameraPitch);
    }

    @EventHandler
    private void onPlayerTick(PlayerTickEvent event) {
        if (nullCheck()) return;
        mc.player.setYaw(MathHelper.wrapDegrees(lockYaw));
        mc.player.setPitch(MathHelper.clamp(lockPitch, -90.0f, 90.0f));
    }
}
