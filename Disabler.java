package com.heypixel.heypixelmod.obsoverlay.modules.impl.misc;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.types.EventType;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventPacket;
import com.heypixel.heypixelmod.obsoverlay.files.FileManager;
import com.heypixel.heypixelmod.obsoverlay.modules.Category;
import com.heypixel.heypixelmod.obsoverlay.modules.Module;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.heypixel.heypixelmod.obsoverlay.values.ValueBuilder;
import com.heypixel.heypixelmod.obsoverlay.values.impl.BooleanValue;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Random;

@ModuleInfo(
        name = "Disabler",
        cnName = "禁用器",
        category = Category.MISC,
        description = "Disables some checks of the anti cheat."
)
public class Disabler extends Module {
    private static final double[] PERFECT_PATTERNS = new double[]{0.1, 0.25};
    private static final double EPSILON = 1.0E-10;
    private static final Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    private final BooleanValue logging = ValueBuilder.create(this, "Logging").setDefaultBooleanValue(false).build().getBooleanValue();
    private final BooleanValue acaaimstep = ValueBuilder.create(this, "ACAAimStep").setDefaultBooleanValue(false).build().getBooleanValue();
    private final BooleanValue acaperfectrotation = ValueBuilder.create(this, "ACAPerfectRotation").setDefaultBooleanValue(false).build().getBooleanValue();
    private final BooleanValue grimDuplicateRotPlace = ValueBuilder.create(this, "GrimDuplicateRotPlace")
            .setDefaultBooleanValue(false)
            .build()
            .getBooleanValue();
    private final BooleanValue roundRotation = ValueBuilder.create(this, "VulcanAim")
            .setDefaultBooleanValue(true)
            .build()
            .getBooleanValue();
    private final Random random = new Random();
    private float playerYaw;
    private float deltaYaw;
    private float lastPlacedDeltaYaw;
    private boolean rotated = false;
    private float lastYaw = 0.0F;
    private float lastPitch = 0.0F;

    public static float getPacketYRot(ServerboundMovePlayerPacket packet) {
        if (mc.gameMode == null) {
            return 0.0F;
        } else {
            Field yRotField = findField(packet.getClass(), "f_134121_");

            try {
                return yRotField.getFloat(packet);
            } catch (Exception var3) {
                FileManager.logger.error("Failed to get yrot field", var3);
                var3.printStackTrace();
                return 0.0F;
            }
        }
    }

    public static float getPacketXRot(ServerboundMovePlayerPacket packet) {
        if (mc.gameMode == null) {
            return 0.0F;
        } else {
            Field xRotField = findField(packet.getClass(), "f_134122_");

            try {
                return xRotField.getFloat(packet);
            } catch (Exception var3) {
                FileManager.logger.error("Failed to get xrot field", var3);
                var3.printStackTrace();
                return 0.0F;
            }
        }
    }

    private static Field findField(Class<?> clazz, String... fieldNames) {
        if (clazz != null && fieldNames != null && fieldNames.length != 0) {
            Exception failed = null;

            for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
                for (String fieldName : fieldNames) {
                    if (fieldName != null) {
                        try {
                            Field f = currentClass.getDeclaredField(fieldName);
                            f.setAccessible(true);
                            if ((f.getModifiers() & 16) != 0) {
                                unsafe.putInt(f, unsafe.arrayBaseOffset(boolean[].class), f.getModifiers() & -17);
                            }

                            return f;
                        } catch (Exception var9) {
                            failed = var9;
                        }
                    }
                }
            }

            throw new Disabler.UnableToFindFieldException(failed);
        } else {
            throw new IllegalArgumentException("Class and fieldNames must not be null or empty");
        }
    }

    public static void setPacketYRot(ServerboundMovePlayerPacket packet, float yRot) {
        if (mc.gameMode != null) {
            Field yRotField = findField(packet.getClass(), "f_134121_");

            try {
                yRotField.setFloat(packet, yRot);
            } catch (Exception var4) {
                FileManager.logger.error("Failed to set yrot field", var4);
                var4.printStackTrace();
            }
        }
    }

    public static void setPacketXRot(ServerboundMovePlayerPacket packet, float xRot) {
        if (mc.gameMode != null) {
            Field xRotField = findField(packet.getClass(), "f_134122_");

            try {
                xRotField.setFloat(packet, xRot);
            } catch (Exception var4) {
                FileManager.logger.error("Failed to set xrot field", var4);
                var4.printStackTrace();
            }
        }
    }

    private void log(String message) {
        if (this.logging.getCurrentValue()) {
            ChatUtils.addChatMessage(message);
        }
    }

    private float normalizeYaw(float yaw) {
        while (yaw > 180.0F) {
            yaw -= 360.0F;
        }

        while (yaw < -180.0F) {
            yaw += 360.0F;
        }

        return yaw;
    }

    private boolean shouldModifyRotation(float currentYaw, float currentPitch) {
        if (this.lastYaw == 0.0F && this.lastPitch == 0.0F) {
            return false;
        } else {
            double yawDelta = Math.abs(this.normalizeYaw(currentYaw - this.lastYaw));
            double pitchDelta = Math.abs(currentPitch - this.lastPitch);
            boolean isStepYaw = yawDelta < 1.0E-5 && pitchDelta > 1.0;
            boolean isStepPitch = pitchDelta < 1.0E-5 && yawDelta > 1.0;
            return isStepYaw || isStepPitch;
        }
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
        if (this.lastYaw == 0.0F && this.lastPitch == 0.0F) {
            return new float[]{yaw, pitch};
        } else {
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
    }

    private boolean isNoRotation(double rotation) {
        return Math.abs(rotation) <= 1.0E-10 || this.isIntegerMultiple(360.0, rotation);
    }

    private boolean isPerfectPattern(double rotation) {
        if (!Double.isInfinite(rotation) && !Double.isNaN(rotation)) {
            for (double pattern : PERFECT_PATTERNS) {
                if (this.isIntegerMultiple(pattern, rotation)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    private boolean isIntegerMultiple(double reference, double value) {
        if (reference == 0.0) {
            return Math.abs(value) <= 1.0E-10;
        } else {
            double multiple = value / reference;
            return Math.abs(multiple - (double) Math.round(multiple)) <= 1.0E-10;
        }
    }

    private float round(float value) {
        return (float) (Math.round(value * 1000000.0) / 1000000.0);
    }

    @EventTarget(3)
    public void duplicateRotPlaceDisabler(EventPacket e) {
        if (this.grimDuplicateRotPlace.getCurrentValue() && e.getType() == EventType.SEND && !e.isCancelled() && mc.player != null) {
            if (e.getPacket() instanceof ServerboundMovePlayerPacket packet) {
                if (packet.hasRotation()) {
                    if (packet.getYRot(0.0F) < 360.0F && packet.getYRot(0.0F) > -360.0F) {
                        if (packet.hasPosition()) {
                            e.setPacket(
                                    new PosRot(
                                            packet.getX(0.0), packet.getY(0.0), packet.getZ(0.0), packet.getYRot(0.0F) + 720.0F, packet.getXRot(0.0F), packet.isOnGround()
                                    )
                            );
                        } else {
                            e.setPacket(new Rot(packet.getYRot(0.0F) + 720.0F, packet.getXRot(0.0F), packet.isOnGround()));
                        }
                    }

                    float lastPlayerYaw = this.playerYaw;
                    this.playerYaw = packet.getYRot(0.0F);
                    this.deltaYaw = Math.abs(this.playerYaw - lastPlayerYaw);
                    this.rotated = true;
                    if (this.deltaYaw > 2.0F) {
                        float xDiff = Math.abs(this.deltaYaw - this.lastPlacedDeltaYaw);
                        if ((double) xDiff < 1.0E-4) {
                            this.log("Disabling DuplicateRotPlace!");
                            if (packet.hasPosition()) {
                                e.setPacket(
                                        new PosRot(
                                                packet.getX(0.0), packet.getY(0.0), packet.getZ(0.0), packet.getYRot(0.0F) + 0.002F, packet.getXRot(0.0F), packet.isOnGround()
                                        )
                                );
                            } else {
                                e.setPacket(new Rot(packet.getYRot(0.0F) + 0.002F, packet.getXRot(0.0F), packet.isOnGround()));
                            }
                        }
                    }
                }
            } else if (e.getPacket() instanceof ServerboundUseItemOnPacket && this.rotated) {
                this.lastPlacedDeltaYaw = this.deltaYaw;
                this.rotated = false;
            }
        }

        if ((this.acaaimstep.getCurrentValue() || this.acaperfectrotation.getCurrentValue()) && e.getPacket() instanceof ServerboundMovePlayerPacket movePacket) {
            float currentYaw = getPacketYRot(movePacket);
            float currentPitch = getPacketXRot(movePacket);
            boolean modified = false;
            if (this.acaaimstep.getCurrentValue() && this.shouldModifyRotation(currentYaw, currentPitch)) {
                float[] modifiedRotation = this.getModifiedRotation(currentYaw, currentPitch);
                currentYaw = modifiedRotation[0];
                currentPitch = modifiedRotation[1];
                modified = true;
            }

            if (this.acaperfectrotation.getCurrentValue()) {
                float[] antiPerfectRotation = this.getAntiPerfectRotation(currentYaw, currentPitch);
                if (antiPerfectRotation[0] != currentYaw || antiPerfectRotation[1] != currentPitch) {
                    currentYaw = antiPerfectRotation[0];
                    currentPitch = antiPerfectRotation[1];
                    modified = true;
                    this.log("PerfectRotation: Modified rotation");
                }
            }

            if (this.roundRotation.getCurrentValue()) {
                float roundedPitch = round(currentPitch);

                if (roundedPitch != currentPitch) {
                    currentPitch = roundedPitch;
                    modified = true;
                    this.log("Vulcan Aim (A): " + currentPitch);
                }
            }

            if (modified) {
                setPacketYRot(movePacket, currentYaw);
                setPacketXRot(movePacket, MathUtils.clampPitch_To90(currentPitch));
            }

            this.lastYaw = getPacketYRot(movePacket);
            this.lastPitch = getPacketXRot(movePacket);
        }
    }

    private static class UnableToFindFieldException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnableToFindFieldException(Exception e) {
            super(e);
        }
    }
}
