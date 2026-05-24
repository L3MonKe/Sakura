package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue acaFastSwitch = new BoolValue("ACA Fast Switch", "ACA Fast Switch", true);
    private final BoolValue acaAimStep = new BoolValue("ACA Aim Step", "ACA Aim Step", true);
    private final BoolValue grimDuplicateRotPlace = new BoolValue("Grim Duplicate RotPlace", "Grim Duplicate RotPlace", true);
    private final BoolValue logging = new BoolValue("Logging", "日志", false);

    private int lastSentSlot = -1;
    private float lastYaw = 0.0f;
    private float lastPitch = 0.0f;
    private float currentYaw = 0.0f;
    private float currentPitch = 0.0f;
    private float yawDiff = 0.0f;
    private float pitchDiff = 0.0f;
    private float lastPlacedYawDiff = 0.0f;
    private float lastPlacedPitchDiff = 0.0f;
    private boolean rotated = false;
    private final Random random = new Random();

    @Override
    protected void onEnable() {
        resetState();
    }

    @Override
    protected void onDisable() {
        resetState();
    }

    private void resetState() {
        this.lastSentSlot = -1;
        this.lastYaw = 0.0f;
        this.lastPitch = 0.0f;
        this.currentYaw = 0.0f;
        this.currentPitch = 0.0f;
        this.yawDiff = 0.0f;
        this.pitchDiff = 0.0f;
        this.lastPlacedYawDiff = 0.0f;
        this.lastPlacedPitchDiff = 0.0f;
        this.rotated = false;
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof GameJoinS2CPacket) {
            resetState();
            return;
        }

        if (event.getType() != EventType.SEND) return;

        if (event.getPacket() instanceof UpdateSelectedSlotC2SPacket carriedItemPacket) {
            int slot = carriedItemPacket.getSelectedSlot();
            if (acaFastSwitch.get() && lastSentSlot != -1 && slot != lastSentSlot) {
                sendIntermediateSlots(lastSentSlot, slot);
            }
            lastSentSlot = slot;
            if (logging.get()) {
                log("Processed slot switch: " + lastSentSlot + " -> " + slot);
            }
        }

        if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket) {
            IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;

            if (disAim360.get() && movePacket.changesLook()) {
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    if (logging.get()) {
                        log("Disabled aim 360");
                    }
                }
            }

            if (grimDuplicateRotPlace.get() && movePacket.changesLook()) {
                float prevYaw = this.currentYaw;
                float prevPitch = this.currentPitch;
                this.currentYaw = accessor.getYaw();
                this.currentPitch = accessor.getPitch();
                this.yawDiff = Math.abs(this.currentYaw - prevYaw);
                this.pitchDiff = Math.abs(this.currentPitch - prevPitch);
                this.rotated = true;
                float yawDelta;
                if (this.yawDiff > 2.0f && (double) (yawDelta = Math.abs(this.yawDiff - this.lastPlacedYawDiff)) < 1.0E-4) {
                    float jitter = 0.001f + this.random.nextFloat() * 0.009f;
                    float newYaw = this.currentYaw - jitter;
                    accessor.setYaw(newYaw);
                    if (logging.get()) {
                        log("DuplicateRotPlace: Modified yaw from " + this.currentYaw + " to " + newYaw + " (yawDelta: " + yawDelta + ")");
                    }
                }
                float pitchDelta;
                if (this.pitchDiff > 2.0f && (double) (pitchDelta = Math.abs(this.pitchDiff - this.lastPlacedPitchDiff)) < 1.0E-4) {
                    float jitter = 0.001f + this.random.nextFloat() * 0.009f;
                    float newPitch = MathHelper.clamp(this.currentPitch - jitter, -90.0f, 90.0f);
                    accessor.setPitch(newPitch);
                    if (logging.get()) {
                        log("DuplicateRotPlace: Modified pitch from " + this.currentPitch + " to " + newPitch + " (pitchDelta: " + pitchDelta + ")");
                    }
                }
            }

            if (acaAimStep.get() && movePacket.changesLook()) {
                float yawAim = accessor.getYaw();
                float pitchAim = accessor.getPitch();
                boolean modified = false;
                if (isAimStepRotation(yawAim, pitchAim)) {
                    float[] result = applyAimStep(yawAim, pitchAim);
                    yawAim = result[0];
                    pitchAim = result[1];
                    modified = true;
                }
                if (modified) {
                    accessor.setYaw(yawAim);
                    accessor.setPitch(MathHelper.clamp(pitchAim, -90.0f, 90.0f));
                }
                this.lastYaw = accessor.getYaw();
                this.lastPitch = accessor.getPitch();
            }
        }

        if (grimDuplicateRotPlace.get() && event.getPacket() instanceof PlayerInteractBlockC2SPacket && rotated) {
            this.lastPlacedYawDiff = this.yawDiff;
            this.lastPlacedPitchDiff = this.pitchDiff;
            this.rotated = false;
        }
    }

    private boolean isAimStepRotation(float yaw, float pitch) {
        if (this.lastYaw == 0.0f && this.lastPitch == 0.0f) {
            return false;
        }
        double yawDelta = Math.abs(wrapDegrees(yaw - this.lastYaw));
        double pitchDelta = Math.abs(pitch - this.lastPitch);
        boolean yawStuck = yawDelta < 1.0E-5 && pitchDelta > 1.0;
        boolean pitchStuck = pitchDelta < 1.0E-5 && yawDelta > 1.0;
        return yawStuck || pitchStuck;
    }

    private float[] applyAimStep(float yaw, float pitch) {
        double yawDelta = Math.abs(wrapDegrees(yaw - this.lastYaw));
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

    private float wrapDegrees(float degrees) {
        while (degrees > 180.0f) {
            degrees -= 360.0f;
        }
        while (degrees < -180.0f) {
            degrees += 360.0f;
        }
        return degrees;
    }

    private void sendIntermediateSlots(int fromSlot, int toSlot) {
        int distance = Math.abs(fromSlot - toSlot);
        if (distance > 1 && !isWrapAroundSlot(fromSlot, toSlot)) {
            int step = fromSlot > toSlot ? -1 : 1;
            for (int slot = fromSlot + step; slot != toSlot; slot += step) {
                if (slot < 0 || slot > 8) continue;
                PacketUtil.sendPacketNoEvent(new UpdateSelectedSlotC2SPacket(slot));
                if (logging.get()) {
                    log("Sent intermediate slot: " + slot);
                }
            }
        }
    }

    private boolean isWrapAroundSlot(int fromSlot, int toSlot) {
        return fromSlot == 0 && toSlot == 8 || fromSlot == 8 && toSlot == 0;
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.clientMessage(message);
        }
    }
}
