package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.manager.impl.NotificationManager;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
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
    //private final BoolValue disAim360 = new BoolValue("Disable Loyisa Aim 360", "防止Loyisa抽风 强迫症最爱", false);
    private final BoolValue acaaimstep = new BoolValue("ACAAimStep", "AimStep", false);
    private final BoolValue acaperfectrotation = new BoolValue("ACAPerfectRotation", "完美转向修正", false);
    private final BoolValue grimDuplicateRotPlace = new BoolValue("Grim Duplicate Rototation Place", "重复转向放置", false);
    private final BoolValue roundRotation = new BoolValue("VulcanAim", "转向取整", true);

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
                float originalYaw = packet.getYaw(0.0f);

                if (originalYaw < 360.0f && originalYaw > -360.0f) {
                    ((IPlayerMoveC2SPacket) packet).setYaw(originalYaw + 720.0f);
                }

                float lastPlayerYaw = this.playerYaw;
                this.playerYaw = originalYaw;
                this.deltaYaw = Math.abs(this.playerYaw - lastPlayerYaw);
                this.rotated = true;

                if (this.deltaYaw > 2.0f) {
                    float xDiff = Math.abs(this.deltaYaw - this.lastPlacedDeltaYaw);
                    if (xDiff < 1.0E-4f) {
                        log("Disabling DuplicateRotPlace!");
                        ((IPlayerMoveC2SPacket) packet).setYaw(originalYaw + 0.002f);
                    }
                }
            } else if ((event.getPacket() instanceof PlayerInteractBlockC2SPacket || event.getPacket() instanceof PlayerInteractItemC2SPacket) && this.rotated) {
                this.lastPlacedDeltaYaw = this.deltaYaw;
                this.rotated = false;
            }
        }

        if ((acaaimstep.get() || acaperfectrotation.get() || roundRotation.get())
                && event.getPacket() instanceof PlayerMoveC2SPacket movePacket
                && movePacket.changesLook()) {
            float currentYaw = movePacket.getYaw(0.0f);
            float currentPitch = movePacket.getPitch(0.0f);

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
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                accessor.setYaw(currentYaw);
                accessor.setPitch(MathHelper.clamp(currentPitch, -90.0f, 90.0f));
            }

            this.lastYaw = currentYaw;
            this.lastPitch = currentPitch;
        }

        /*if (disAim360.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
                float yaw = packet.getYaw(0.0f);
                if (yaw < 360.0f && yaw > -360.0f) {
                    ((IPlayerMoveC2SPacket) packet).setYaw(yaw + 720.0f);
                }
            }
        }*/
    }

    private void log(String message) {
        if (logging.get()) {
            NotificationManager.send(message);
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
