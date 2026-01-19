package dev.lemonclient.client.module.impl.player;

import dev.lemonclient.client.events.EventType;
import dev.lemonclient.client.events.packet.PacketEvent;
import dev.lemonclient.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;
import dev.lemonclient.client.utils.client.ChatUtil;
import dev.lemonclient.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class Disabler extends Module {
    private static final double[] PERFECT_PATTERNS = new double[]{0.1, 0.25};
    private static final double EPSILON = 1.0E-10;

    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue logging = new BoolValue("Logging", "日志", false);
    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue acaaimstep = new BoolValue("ACAAimStep", "AimStep", false);
    private final BoolValue acaperfectrotation = new BoolValue("ACAPerfectRotation", "完美转向修正", false);
    private final BoolValue grimDuplicateRotPlace = new BoolValue("Grim Duplicate Rototation Place", "重复转向放置", false);
    private final BoolValue roundRotation = new BoolValue("VulcanAim", "转向取整", false);

    private final Random random = new Random();
    private float playerYaw;
    private float deltaYaw;
    private float lastPlacedDeltaYaw;
    private boolean rotated;
    private float lastYaw;
    private float lastPitch;

    @Override
    protected void onDisable() {
        rotated = false;
        deltaYaw = 0.0f;
        lastPlacedDeltaYaw = 0.0f;
    }

    @EventHandler
    private void onPacketSend(PacketEvent event) {
        if (event.getType() != EventType.SEND || nullCheck()) return;

        if (grimDuplicateRotPlace.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float originalYaw = accessor.getYaw();

                if (originalYaw < 360.0f && originalYaw > -360.0f) {
                    accessor.setYaw(originalYaw + 720.0f);
                }

                float lastPlayerYaw = this.playerYaw;
                this.playerYaw = originalYaw;
                this.deltaYaw = Math.abs(this.playerYaw - lastPlayerYaw);
                this.rotated = true;

                if (this.deltaYaw > 2.0f) {
                    float xDiff = Math.abs(this.deltaYaw - this.lastPlacedDeltaYaw);
                    if (xDiff < 1.0E-4f) {
                        log("Disabling DuplicateRotPlace!");
                        accessor.setYaw(originalYaw + 0.002f);
                    }
                }
            } else if (event.getPacket() instanceof PlayerInteractBlockC2SPacket && this.rotated) {
                this.lastPlacedDeltaYaw = this.deltaYaw;
                this.rotated = false;
            }
        }

        if ((acaaimstep.get() || acaperfectrotation.get())
                && event.getPacket() instanceof PlayerMoveC2SPacket movePacket) {
            IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
            float currentYaw = accessor.getYaw();
            float currentPitch = accessor.getPitch();

            boolean modified = false;

            if (acaaimstep.get() && shouldModifyRotation(currentYaw, currentPitch)) {
                float[] modifiedRotation = getModifiedRotation(currentYaw, currentPitch);
                currentYaw = modifiedRotation[0];
                currentPitch = modifiedRotation[1];
                modified = true;
            }

            if (acaperfectrotation.get()) {
                float[] antiPerfectRotation = getAntiPerfectRotation(currentYaw, currentPitch);
                if (antiPerfectRotation[0] != currentYaw || antiPerfectRotation[1] != currentPitch) {
                    currentYaw = antiPerfectRotation[0];
                    currentPitch = antiPerfectRotation[1];
                    modified = true;
                    log("PerfectRotation: Modified rotation");
                }
            }

            if (roundRotation.get()) {
                float roundedPitch = round(currentPitch);
                if (roundedPitch != currentPitch) {
                    currentPitch = roundedPitch;
                    modified = true;
                    log("Vulcan Aim (A): " + currentPitch);
                }
            }

            if (modified) {
                accessor.setYaw(currentYaw);
                accessor.setPitch(MathHelper.clamp(currentPitch, -90.0f, 90.0f));
            }

            this.lastYaw = accessor.getYaw();
            this.lastPitch = accessor.getPitch();
        }

        if (disAim360.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) packet;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                }
            }
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.addChatMessage(message);
            //NotificationManager.send(message);
        }
    }

    private float normalizeYaw(float yaw) {
        while (yaw > 180.0f) yaw -= 360.0f;
        while (yaw < -180.0f) yaw += 360.0f;
        return yaw;
    }

    private boolean shouldModifyRotation(float currentYaw, float currentPitch) {
        if (this.lastYaw == 0.0f && this.lastPitch == 0.0f) return false;

        double yawDelta = Math.abs(this.normalizeYaw(currentYaw - this.lastYaw));
        double pitchDelta = Math.abs(currentPitch - this.lastPitch);

        boolean isStepYaw = yawDelta < 1.0E-5 && pitchDelta > 1.0;
        boolean isStepPitch = pitchDelta < 1.0E-5 && yawDelta > 1.0;
        return isStepYaw || isStepPitch;
    }

    private float[] getModifiedRotation(float yaw, float pitch) {
        double yawDelta = Math.abs(this.normalizeYaw(yaw - this.lastYaw));
        double pitchDelta = Math.abs(pitch - this.lastPitch);

        float newYaw = yaw;
        float newPitch = pitch;

        if (yawDelta < 1.0E-5 && pitchDelta > 1.0) {
            newYaw = this.lastYaw + (float) (this.random.nextGaussian() * 0.001);
        }
        if (pitchDelta < 1.0E-5 && yawDelta > 1.0) {
            newPitch = this.lastPitch + (float) (this.random.nextGaussian() * 0.001);
        }

        return new float[]{newYaw, newPitch};
    }

    private float[] getAntiPerfectRotation(float yaw, float pitch) {
        if (this.lastYaw == 0.0f && this.lastPitch == 0.0f) return new float[]{yaw, pitch};

        double yawDelta = Math.abs(this.normalizeYaw(yaw - this.lastYaw));
        double pitchDelta = Math.abs(pitch - this.lastPitch);

        float newYaw = yaw;
        float newPitch = pitch;

        if (!this.isNoRotation(yawDelta) && this.isPerfectPattern(yawDelta)) {
            double jitter = this.random.nextGaussian() * 0.005;
            newYaw = yaw + (float) jitter;
        }

        if (!this.isNoRotation(pitchDelta) && this.isPerfectPattern(pitchDelta)) {
            double jitter = this.random.nextGaussian() * 0.005;
            newPitch = pitch + (float) jitter;
        }

        return new float[]{newYaw, newPitch};
    }

    private boolean isNoRotation(double rotation) {
        return Math.abs(rotation) <= EPSILON || this.isIntegerMultiple(360.0, rotation);
    }

    private boolean isPerfectPattern(double rotation) {
        if (Double.isInfinite(rotation) || Double.isNaN(rotation)) return false;
        for (double pattern : PERFECT_PATTERNS) {
            if (this.isIntegerMultiple(pattern, rotation)) return true;
        }
        return false;
    }

    private boolean isIntegerMultiple(double reference, double value) {
        if (reference == 0.0) return Math.abs(value) <= EPSILON;
        double multiple = value / reference;
        return Math.abs(multiple - Math.round(multiple)) <= EPSILON;
    }

    private float round(float value) {
        return (float) (Math.round(value * 1000000.0) / 1000000.0);
    }
}
